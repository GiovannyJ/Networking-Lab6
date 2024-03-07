server:
	javac IMMultiServer.java IMMultiServerThread.java IMProtocol.java

runserver:
	'C:\Program Files\Java\jdk-19\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\15163\AppData\Roaming\Code\User\workspaceStorage\b669c4eb8b20d3dc079c8d4da63828f1\redhat.java\jdt_ws\Networking-Lab4_6d02bc67\bin' 'IMMultiServer' 2002

client:
	javac IMClient.java

runclient:
	'C:\Program Files\Java\jdk-19\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\15163\AppData\Roaming\Code\User\workspaceStorage\b669c4eb8b20d3dc079c8d4da63828f1\redhat.java\jdt_ws\Networking-Lab4_6d02bc67\bin' 'IMClient' localhost 2002 nicole