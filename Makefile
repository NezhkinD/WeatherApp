
build:
	$(MAKE) copy
	bash /opt/maven/bin/mvn compile
	bash /opt/maven/bin/mvn package

rebuild:
	$(MAKE) copy
	bash /opt/maven/bin/mvn clean package

copy:
	cp -f .env ./src/main/resources
	cp -f MANIFEST.MF ./src/main/resources/META-INF

package:
	bash /opt/maven/bin/mvn package

clean-install:
	bash /opt/maven/bin/mvn clean install

clean-package:
	bash /opt/maven/bin/mvn clean package

run:
	java -jar target/WeatherApp-1.0.jar