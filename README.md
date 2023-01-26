# Distribuited-Systems_Registry
Progetto eclipse che simula un Registry remoto con lo scopo di permettere l'accesso a servizi remoti.

## Progettazione e Design

### Organizzazione delle classi
Il progetto è organizzato come un Java Project suddiviso in
due package principali:
	
	- registry, contentente la classi per l'implementazione e il lancio del Registry;

	- contoCorrente, a sua volta suddiviso nei due package:
		- server, contenente la logica del backend del servizio;
		- client, contenente le classi per simulare l'esecuzione di più client e verificare il comportamento del backend.

### Registry Design
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

### Client Design
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


