# Report Tools v.1.0.0
Es un proyecto desarrollado con *Java* para simplificar la extracción de información de un objeto mediante la utilización 
de anotaciones. Cuenta con un par de utilerías. `ReportableTool` y `ReportGenerator`.

## ReportableTool
Esta es la utilería que se encarga de realizar la extracción de una la información de una clase mediante la reflexión de 
la misma y el uso de la anotación `ColumnTitle`. Cuenta con 4 métodos para extraer la información.

- `getTitles`: Extrae en un arreglo de *String* el valor de ColumnTitle de todos los campos anotados.
- `getValues`: Extrae en un arreglo de *String* el valor de todos los campos anotados.
- `getSheetTitle`: Extrae el valor de SheetTitle de la clase anotado. este valor es utilizado como nombre de libro en los
reportes generados de tipo *xls*, *xlsx*
- `getValuesRow` Extrae en objeto tipo *Row* de *POI* el valor de todos los campos anotados. estos valores son utilizados 
en los reportes generados de tipo *xls*, *xlsx*

### Dependencias
```
<dependency>
    <groupId>javax.inject</groupId>
    <artifactId>javax.inject</artifactId>
    <version>1</version>
</dependency>
```

### Implementación
Para poder ser utilizada la utilería debe ser eqtiquetada la clase mediante la intefaz `Reportable`, se accede y extrae 
la información solo de los campos que se encuentren anotados con `ColumnTitle`

Example:
```
public class ExampleAnnotations implements Reportable {
    @ColumnTitle
    private String name;
    @ColumnTitle
    private String lastName;
    @ColumnTitle
    private Integer age;
    @ColumnTitle
    private Date birthday;
}
```

#### ColumnTitle
La anotación `ColumnTitle` es utilizado por la utilería para la extracción de la información del elemento que se 
encuentre anotado por ella. cuenta con los siguientes attributos.
- [value](#Value)
- [alternativeText](#AlternativeText)
- [format](#Format)
- [alignment](#Alignment)

##### Value
Establece el valor del titulo de la columna, si no se estable un valor por default toma como valor el nombre del campo 
anotado.

```
@ColumnTitle("Nombre")
private String name;
```
o
```
@ColumnTitle(value = "Nombre")
private String name;
```

##### AlternativeText
Establece un valor alternativoa mostrar en caso de que el valor extraido del campo anotado sea null. Su valor se 
establece mediante la enumeracion `AlternateText` cuyos valores establecidos son:

- `DEFAULT` establece como texto alterno (-)
- `NOT_APPLY` establece como texto alterno (N/A),
- `NOT_AVAILABLE` establece como texto alterno (N/D),
- `EMPTY` establece vacio el texto alterno 

Si no se establece un valor para el atributo por defecto será el valor de `AlternateText.DEFAULT`

```
public class ExampleAlterText implements Reportable {
    @ColumnTitle(alternativeText = AlternateText.DEFAULT)
    private String alterDefault;
    
    @ColumnTitle(alternativeText = AlternateText.EMPTY)
    private String alterEmpty;

    @ColumnTitle(alternativeText = AlternateText.NOT_APPLY)
    private String alterNotApply;

    @ColumnTitle(alternativeText = AlternateText.NOT_AVAILABLE)
    private String alterNotAvailable;
}
```

##### Format
Establece un formato al valor extraido del campo anotado. Su valor se establece mediante la enumeracion `Formatter` 
cuyos valores establecidos son:

- `DEFAULT` Se obtiene el valor sin ningún tipo de formato.
- `NUMBER` Se Establece un formato numérico `1,000`
- `DECIMAL`  Se Establece un formato numérico con dos decimales `1,000.00`
- `PERCENTAGE` Se Establece un formato numérico con dos decimales y símbolo de porcentaje`90%`
- `CURRENCY` Se Establece un formato de moneda`$1,000.14`
- `DATETIME` Se Establece un formato de fecha y hora `08/05/2020 12:52:46`

Si no se establece un valor para el atributo por defecto será el valor de `Formatter.DEFAULT`

```
public class ExampleFormat implements Reportable {
    @ColumnTitle(format = Formatter.NUMBER)
    private double varNumber;

    @ColumnTitle(format = Formatter.DECIMAL)
    private double varDecimal;

    @ColumnTitle(format = Formatter.PERCENTAGE)
    private double varPercentage;

    @ColumnTitle(format = Formatter.CURRENCY)
    private double varCurrency;

    @ColumnTitle
    private Date varDate;

    @ColumnTitle(format = Formatter.DATETIME)
    private Date varDateTime;
}
```

##### Alignment
Establece un formato de alineación horizontal en los reporte de tipo excel del valor extraido del campo anotado. 
Su valor se establece mediante la enumeracion `HorizontalAlignment` contenida dentro de la librería `POI`. Si no se 
establece un valor para el atributo por defecto los valores son:

  - `Boolean` el valor de `HorizontalAlignment.CENTER`
  - `Date` el valor de `HorizontalAlignment.CENTER`
  - `Currency` el valor de `HorizontalAlignment.RIGHT`
  - `Number` el valor de `HorizontalAlignment.CENTER`
  - `default` el valor de `HorizontalAlignment.LEFT`
  
```
public class ExampleAligment implements Reportable {
    @ColumnTitle(format = Formatter.NUMBER)
    private double varNumber;

    @ColumnTitle(format = Formatter.DECIMAL)
    private double varDecimal;

    @ColumnTitle(format = Formatter.PERCENTAGE)
    private double varPercentage;

    @ColumnTitle(format = Formatter.CURRENCY)
    private double varCurrency;

    @ColumnTitle
    private Date varDate;

    @ColumnTitle(format = Formatter.DATETIME)
    private Date varDateTime;
}
```

## ReportGenerator
Esta es la utilería que se encarga de realizar que junto con la utilería `ReportableTool` reportes en formato *csv*, 
*xls*, *xlsx*. Utiliza como apoyo para los reporte de tipo *xls*, *xlsx* la anotación `SheetTitle` .

### Dependencias
```
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>4.6</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml-schemas</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>ooxml-schemas</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-scratchpad</artifactId>
</dependency>
```

### Implementación
Ya que la generación de reportes esta basado en la utilería `ReportableTool` deben de implementarse las clases de igual
forma con las anotación `ColumnTitle`, en el caso de los reportes de tipo *xls*, *xlsx* para poder definir el nombre del
libro se anota la clase con la anotación `SheetTitle`. 

Example:
```
@SheetTitle("Example")
public class ExampleAnnotations implements Reportable {
    @ColumnTitle
    private String name;
    @ColumnTitle
    private String lastName;
    @ColumnTitle
    private Integer age;
    private Date birthday;
}
```

Cuenta con un método *generateReport* que realiza la generación del reporte y que recibe como parámetro una instancia de 
la clase *ReportParameter* que cuenta con los siguientes atributos. 

- `reportName`: Nombre que recibirá el archivo
- `extension`: La extensión del archivo. este es validado con el enumerador `ReportType` para determinar el tipo de 
reporte que se esta generando
- `data`: La información que contendrá el reporte esta es una lista de eitquetados como `Reportable`.

#### SheetTitle
La anotación `SheetTitle` es utilizado por la utilería `ReportableTool` y `ReportGenerator` para establecer el nombre del 
libro en los reportes de tipo  *xls*, *xlsx*.

## Build
La construcción del proyecto se hace mediante *Maven*. para ello a la altura de la raiz del proyecto se puede ejecutar 
el comando para construir el proyecto.
```
mvn clear compile
```

Pueden correrse las pruebas unitarias con el comando 
```
mvn test
```

Pueden instalar el jar en el repositorio local con el comando 
```
mvn clean install
```

Pueden instalar el jar en el repositorio local excluyendo las pruebas unitarias con el comando 
```
mvn clean install -DskipTests
```