package com.example.cloart.gesaqua;

import android.bluetooth.BluetoothDevice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
//import java.util.logging.Handler;
import android.os.Handler;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;


/**
 * @class PeripheriqueBluetooth
 * @brief Assure le dialogue Bluetooth avec l'aquarium
 * @author Thierry Vaira <tvaira@free.fr>
 * @author Audrey Cloart
 */

public class PeripheriqueBluetooth extends Thread
{
    private String nom;
    private String adresse;
    private Handler handler = null;
    private BluetoothDevice device = null;

    private BluetoothSocket socket = null;
    private InputStream receiveStream = null;
    private OutputStream sendStream = null;
    private TReception tReception;

    public final static int CODE_CONNEXION = 0;
    public final static int CODE_RECEPTION = 1;
    public final static int CODE_DECONNEXION = 2;
    public final static int CODE_INACTIF = 3;
    public final static int CODE_ACTIF = 4;
    public final static int TAILLE_BUFFER = 100; // en octets
    public final static int PROCHAINE_LECTURE = 150; // en millis
    public final static int PERIODE_INACTIVE = 200000; // en millis

    /**
     * @brief Constructeur public de la classe PeripheriqueBluetooth
     * @param device un objet de type BluetoothDevice
     * @param handler un objet de type Handler
     */
    public PeripheriqueBluetooth(BluetoothDevice device, Handler handler)
    {
        if(device != null)
        {
            this.device = device;
            this.nom = device.getName();
            this.adresse = device.getAddress();
            this.handler = handler;
        }
        else
        {
            this.device = device;
            this.nom = "Aucun";
            this.adresse = "";
            this.handler = handler;
        }
        try
        {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            receiveStream = socket.getInputStream();
            sendStream = socket.getOutputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            socket = null;
        }

        if(socket != null)
            tReception = new TReception(handler);
    }

    /**
     * @brief Permet d'obtenir le nom du Périphérique Bluetooth
     * @return un String qui représente le nom du périphérique
     */
    public String getNom()
    {
        return nom;
    }

    /**
     * @brief Permet d'obtenir l'adresse du périphérique Bluetooth
     * @return un String qui représente l'adresse du périphérique
     */
    public String getAdresse()
    {
        return adresse;
    }

    /**
     * @brief Permet de connaître si la connexion a été effectuée
     * @return un booléen qui vaut true si la connection existe, et faux sinon
     */
    public boolean estConnecte()
    {
        return socket.isConnected();
    }

    /**
     * @brief Permet de modifier le nom du périphérique Bluetooth
     * @param nom un String qui représente le nom du périphérique
     */
    public void setNom(String nom)
    {
        this.nom = nom;
    }

    /**
     * @brief Permet d'obtenir le nom et l'adresse du périphérique
     * @return un String
     */
    public String toString()
    {
        return "\nNom : " + nom + "\nAdresse : " + adresse;
    }

    /**
     * @brief Envoie une trame via le Bluetooth vers l'aquarium
     * @author Thierry Vaira <tvaira@free.fr>
     */
    public void envoyer(String data)
    {
        if(socket == null)
            return;

        try
        {
            sendStream.write(data.getBytes());
            sendStream.flush();
        }
        catch (IOException e)
        {
            System.out.println("<Socket> error send");
            e.printStackTrace();
        }
    }

    /**
     * @brief Connecte la socket Bluetooth et démarre le thread Réception
     * @author Thierry Vaira <tvaira@free.fr>
     */
    public void connecter()
    {
        /* démarre le thread connexion */
        new Thread()
        {
            @Override public void run()
            {
                while(!socket.isConnected())
                {
                    try
                    {
                        /* Demande de connexion */
                        socket.connect();

                        /* connecté ? */
                        if (socket.isConnected())
                        {
                            /* informe l'activité principale */
                            Message msg = Message.obtain();
                            msg.what = PeripheriqueBluetooth.CODE_CONNEXION;
                            if (handler.sendMessage(msg))
                            {
                                Log.d("connecter()", "Socket connecte : " + socket.isConnected()); // d = debug
                            } else
                            {
                                Log.e("connecter()", "Message non envoyé !");
                            }

                            /* démarre le thread réception */
                            tReception.start();
                        }
                        else
                        {
                            Log.e("connecter()", "Socket connecte : " + socket.isConnected()); // e = error
                        }
                    } catch (IOException e)
                    {
                        System.out.println("<Socket> Erreur connect");
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * @brief Déconnecte la socket Bluetooth et arrête le thread Réception
     * @author Thierry Vaira <tvaira@free.fr>
     */
    public boolean deconnecter()
    {
        try
        {
            /* informe l'activité principale */
            Message msg = Message.obtain();
            msg.what = PeripheriqueBluetooth.CODE_DECONNEXION;
            if(handler.sendMessageAtFrontOfQueue(msg))
            {
                Log.d("deconnecter()", "Demande déconnexion"); // d = debug
            }
            else
            {
                Log.e("deconnecter()", "Message déconnexion non envoyé !");
            }

            /* arrête le thread réception */
            tReception.arreter();

            /* ferme la socket */
            socket.close();
            Log.d("deconnecter()", "Socket deconnecte : " + !socket.isConnected()); // d = debug

            return !socket.isConnected();
        }
        catch (IOException e)
        {
            System.out.println("<Socket> Erreur close");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @class TReception
     * @brief Thread de réception des trames en provenance de l'aquarium via le Bluetooth
     * @author Thierry Vaira <tvaira@free.fr>
     * @author Audrey Cloart
     */
    public class TReception extends Thread {
        Handler handlerUI;
        private boolean fini;
        private long nbTrames = 0;
        private long link = 0;
        private boolean envoye = false;
        private boolean inactif = false;

        /**
         * @brief Constructeur de la classe TReception
         * @param h
         */
        TReception(Handler h)
        {
            handlerUI = h;
            fini = false;
        }

        /**
         * @brief Réceptionne les trames en provenance de l'aquarium via le Bluetooth
         * @author Thierry Vaira <tvaira@free.fr>
         */
        @Override public void run()
        {
            attendreConnexion();

            viderBuffer();

            /* boucle de réception des trames */
            while(!fini)
            {
                recevoirTrame();
            }
        }

        /**
         * @brief Permet de recevoir les trames Bluetooth
         */
        private void recevoirTrame()
        {
            long nbErreurs = 0;

            try
            {
                if(receiveStream.available() > 0)
                {
                    byte buffer[] = new byte[PeripheriqueBluetooth.TAILLE_BUFFER];
                    if(!fini && socket.isConnected())
                    {
                        int k = receiveStream.read(buffer, 0, PeripheriqueBluetooth.TAILLE_BUFFER);

                        if (k > 0)
                        {
                            byte rawdata[] = new byte[k];
                            for (int i = 0; i < k; i++)
                                rawdata[i] = buffer[i];

                            String datas = new String(rawdata);
                            StringBuffer d = new StringBuffer(datas);
                            /* trame valide ? */
                            if (d.indexOf("$") == -1 && d.indexOf("\r") == -1)
                            {
                                nbErreurs++;
                                //Log.d("run()", "Datas : " + datas + " (" + k + ")"); // d = debug
                                Log.d("Réception run()", "Nb erreurs : " + nbErreurs); // d = debug
                            }
                            /* extrait les trames du buffer et les transmet à l'activité principale */
                            while (d.indexOf("$") != -1 && d.indexOf("\r") != -1)
                            {
                                d.delete(0, d.indexOf("$"));
                                String trame = d.substring(d.indexOf("$"), d.indexOf("\r") + 1);
                                d.delete(0, d.indexOf("\r") + 1);
                                if (!fini && !trame.isEmpty())
                                {
                                    /* transmet la trame à l'activité principale */
                                    Message msg = Message.obtain();
                                    msg.what = PeripheriqueBluetooth.CODE_RECEPTION;
                                    msg.obj = trame;
                                    handlerUI.sendMessage(msg);
                                    Log.d("Réception run()", "Trame reçue : " + trame); // d = debug
                                    nbTrames++;
                                    envoye = true;
                                }
                            }
                        }
                    }
                }
                attendreProchaineLecture(PeripheriqueBluetooth.PROCHAINE_LECTURE);
            }
            catch (IOException e)
            {
                System.out.println("<Socket> Erreur read");
                e.printStackTrace();
            }
        }

        /**
         * @brief Permet d'attendre la prochaine lecture de trames Bluetooth
         * @param millis un int qui représente une durée en millisecondes (ms)
         */
        private void attendreProchaineLecture(int millis)
        {
            try
            {
                /* attente prochaine lecture */
                Thread.sleep(millis);
                if(envoye)
                {
                    envoye = false;
                    link = 0;
                    if(inactif)
                    {
                        /* informe l'activité principale */
                        Message msg = Message.obtain();
                        msg.what = PeripheriqueBluetooth.CODE_ACTIF;
                        if (handler.sendMessage(msg))
                        {
                            Log.d("run()", "Socket active : " + nbTrames); // d = debug
                        } else
                        {
                            Log.e("run()", "Message non envoyé !");
                        }
                        inactif = false;
                    }
                }
                else
                {
                    link += PeripheriqueBluetooth.PROCHAINE_LECTURE;
                    /* perte du lien */
                    if(link >= PeripheriqueBluetooth.PERIODE_INACTIVE)
                    {
                        /* informe l'activité principale */
                        Message msg = Message.obtain();
                        msg.what = PeripheriqueBluetooth.CODE_INACTIF;
                        if (handler.sendMessage(msg))
                        {
                            Log.d("run()", "Socket inactive : " + nbTrames); // d = debug
                        } else
                        {
                            Log.e("run()", "Message non envoyé !");
                        }
                        inactif = true;
                        link = 0;
                    }
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        /**
         * @brief Attend la prochaine connexion Bluetooth
         */
        private void attendreConnexion()
        {
            /* attente connexion */
            if(!fini)
            {
                while (!socket.isConnected())
                {
                    try
                    {
                        Log.d("Réception run()", "Attente connexion ..."); // d = debug
                        Thread.sleep(150);
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * @brief Permet de vider le buffer
         */
        private void viderBuffer()
        {
            try
            {
                Thread.sleep(1000);
                /* doit-on vider le buffer ? */
                if(!fini && socket.isConnected())
                {
                    while (receiveStream.available() > PeripheriqueBluetooth.TAILLE_BUFFER)
                    {
                        Log.d("Réception run()", "Vide le buffer : " + receiveStream.skip(receiveStream.available()) + " octets"); // d = debug
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        /**
         * @brief Permet d'arrêter le thread de réception des trames
         * @author Thierry Vaira <tvaira@free.fr>
         */
        public void arreter()
        {
            if(fini == false)
            {
                fini = true;
            }
            try
            {
                Thread.sleep(250);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
