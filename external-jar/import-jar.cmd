mvn install:install-file -Dfile=%CD%/pinyin4j-2.5.0.jar -DgroupId=net.sourceforge.pinyin4j -DartifactId=pinyin4j -Dversion=2.5.0 -Dpackaging=jar
rem nohup java -jar  /root/openjweb-sys-0.0.1-SNAPSHOT.jar  --spring.config.additional-location=file:/root/openjweb-dev.yml &
