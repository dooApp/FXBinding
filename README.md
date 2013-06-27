FXBinding
=========

The binding you need.

FXBinding allow you to create advanced JavaFX [binding](http://docs.oracle.com/javafx/2/binding/jfxpub-binding.htm) on complex model.

Let me show an example : 
```java
private StringBinding nameBinding = new FXStringBinding() {
        @Override
        protected void configure() {
            try {
                addObservable(aProperty);
                addObservable(aProperty.get().bProperty());
                addObservable(aProperty.get().bProperty().get().cProperty());
                addObservable(aProperty.get().bProperty().get().cProperty().get().stringProperty());
            } catch (NullPointerException ignored) {
            }
        }
        @Override
        protected String compute() {
            try {
                return aProperty.get().bProperty().get().cProperty().get().stringProperty().get();
            } catch (NullPointerException e) {
                return null;
            }
        }
    };
```

Every time the model is changing, we will reconfigure the binding to always get the value you want.


### Maven
To add FXBinding to your project, add this on the pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>com.dooapp</groupId>
        <artifactId>FXBinding</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
</dependencies>
<repositories>
    <repository>
        <id>fxbinding</id>
        <url>https://github.com/dooApp/FXBinding/blob/maven/</url>
        <name>fxbinding</name>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
     </repository>
</repositories>
```
