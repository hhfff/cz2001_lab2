Java release Version 5 is not supported

!!! SOLUTION !!!!

Go on your pom.xml file and write
```
<properties>
  <java.version>8</java.version>
  <maven.compiler.source>8</maven.compiler.source>
  <maven.compiler.target>8</maven.compiler.target>
</properties>
```

Then go on File => Settings => Build and Execution => Java compiler

Type 8 in Java compiler, Then Click on Apply and then "OK".


Rebuild your project and then run it.
