# Or-Mapping mit JDBC

## Wichtige Pom.xml dependencies und plugins

        <properties>
            <java.version>15</java.version>
            <maven.compiler.source>15</maven.compiler.source>
            <maven.compiler.target>15</maven.compiler.target>

        </properties>
    
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <configuration>
                        <source>10</source>
                        <target>10</target>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.asciidoctor</groupId>
                    <artifactId>asciidoctor-maven-plugin</artifactId>
                    <version>${asciidoctor.maven.plugin.version}</version>
                </plugin>
            </plugins>
        </build>
    
        <dependencies>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>5.7.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>5.7.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-core</artifactId>
                <version>3.6.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.assertj</groupId>
                <artifactId>assertj-core</artifactId>
                <version>3.18.1</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.xerial</groupId>
                <artifactId>sqlite-jdbc</artifactId>
                <version>3.34.0</version>
            </dependency>
        </dependencies>

## Starte meinen Mapper
Install:
    
    git init
    git pull https://github.com/Zeljko-Predjeskovic/JDBC_OR-Mapping.git main

Start:

    mvn clean 
    mvn install


```plantuml
@startuml

entity Buch {
    *ISBN : String
    --
    *Titel : String
    Verlag : String
    AutorId : Integer <<FK>>
    Auflage : Integer
    *Erscheinungsjahr : Ingeger
}

entity Zeitschrift {
    *ISBN : String
    --
    *Titel : String
    Verlag : String
    AutorId : Integer <<FK>>
    *Erscheinungsdatum: Date
}

entity Disc {
    *ISBN : String
    --
    *Titel : String
    Verlag : String
    AutorId : Integer <<FK>>
}

entity Autor {
    *Id : Integer
    --
    *Name : String
    *Vorname : String
}

entity Titel {
    *WerkId : Integer
    *Nr : Integer
    --
    Name : String,
    Startposition : Integer,
    Dauer : Integer
}

Autor ||--o{ Buch
Autor ||--o{ Zeitschrift
Autor ||--o{ Disc

Disc ||--o{ Titel

@enduml
```
