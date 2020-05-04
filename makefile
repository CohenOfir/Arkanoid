#205989346
#cohenof2

compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
	rm sources.txt

bin:
	mkdir bin

run:
	java -cp biuoop-1.4.jar:ass7game.jar:Ass7Game

jar:
	jar -cfm ass7game.jar manifest.txt -C bin . -C resources .
	