.DEFAULT_GOAL := run

compile :
	mkdir -p ../bin
	cp -r resources/* ../bin/
	javac -classpath ../bin/ -d ../bin/ --module-path ${JAVAFX_HOME}/lib --add-modules=javafx.base,javafx.controls,javafx.fxml,com.google.gson,javafx.media eu/telecomnancy/application/*.java

	
run : compile
	java -classpath ../bin/ --module-path ${JAVAFX_HOME}/lib --add-modules=javafx.base,javafx.controls,javafx.fxml,com.google.gson,javafx.media eu.telecomnancy.application.Main

clean :
	rm -rf ../build/eu/telecomnancy/application/*.class
	rm -rf ../build/*.fxml
