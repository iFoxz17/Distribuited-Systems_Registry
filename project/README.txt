Progetto 1 (Concorrenza) Sistemi Distribuiti 2021/2022

Volpato Mattia 866316

Il progetto è stato organizzato come un Java Project suddiviso in
due package principali:
	
	- registry, contentente la classi per la realizzazione e 
	  il lancio del Registry;

	- contoCorrente, a sua volta suddiviso nei due package:
		- server, contenente la logica del backend del servizio;
		- client, contenente le classi per simulare l'esecuzione di più client 
		  e verificare il comportamento del backend.

Per gestire la distribuzione del sistema sono stati creati due classi header 
(RegHeader e Header) che contengono gli indirizzi del Registry (noto) e del server 
che gestisce il servizio ContoCorrente (utilizzato soltanto per la fase di 
registrazione del servizio presso il Registry), oltre ai caratteri utilizzati 
nei rispettivi protocolli.

Il Registry è realizzato come un server sequenziale, mentre il servizio 
ContoCorrente genera un thread slave per ogni connessione, con il compito 
di gestire l'intera sessione con il client e terminare nel momento in cui 
viene comunicato un messaggio con sintassi errata.

Ogni richiesta viene interpretata da una apposita classe Parser che crea 
un opportuno Comando, classe astratta estesa dalle operazioni che è possibile 
effettuare (CreazioneConto, Versamento, Prelievo, Bonifico); in questa 
maniera, sfruttando il polimorfismo ogni comando viene passato alla classe 
GestoreContoCorrente, che provvede a eseguire l'operazione richiesta.

Per quanto riguarda il package client, la classe Client si occupa di
	- reperire l'indirizzo e la porta di accesso del 
	  servizio presso il Registry;
	- effettuare la creazione di un nuovo conto presso il servizio;
	- creare un un'istanza della classe OperationFactory e lanciarla 
        come nuovo Thread;
	- prelevare le richieste generate dal buffer condiviso OperationBuffer 
	  e comunicarle al servizio;

La classe OperationFactory genera un certo numero di richieste casuali: 
in particolare, il codice del conto viene passato da Client, le quantità 
numeriche vengono generate casualmente e i codici dei clienti estratti in maniera 
aleatoria da una lista statica. Essendo generate casualmente, molte richieste 
non vanno a buon fine, ma vengono comunque gestite correttamente dal servizio.

--------------------------------------------------------------
ISTRUZIONI DI LANCIO
--------------------------------------------------------------
Le 4 classi da lanciare sono nell'ordine
	
	1) registry.RegistryLauncher (avvio del Registry);
	2) contoCorrente.server.ServerLauncher 
	   (avvio del servizio ContoCorrente);
	3) contoCorrente.server.ContoCorrenteRegister 
	   (registrazione del servizio ContoCorrente presso il Registry);
	4) contoCorrente.client.Client 
	   (simulazione delle richieste dei client al servizio ContoCorrente);

E' possibile lanciare più istanze parallele della classe Client, modificando nel caso 
le costanti che regolano il numero minimo di richieste inviate 
(sia nella classe Client che nella classe OperationFactory) e la pausa 
tra una richiesta e la successiva.