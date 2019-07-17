package de.fk.email;

import java.lang.management.ManagementFactory;
import java.util.Properties;

class FkString
{
  /**
   * @param pInhalt der Inhalt des Feldes
   * @param pMindestLaenge die vorgegebene Mindestlaenge des Rueckgabestrings
   * @return einen String, welcher mindestens pMindesLaenge breit ist (evtl. aufgefuellt mit Leerzeichen)
   */
  public static String getFeldLinksMin( String pInhalt, int pMindestLaenge )
  {
    if ( pInhalt == null )
    {
      pInhalt = "";
    }

    if ( pInhalt.length() >= pMindestLaenge )
    {
      return pInhalt;
    }

    return pInhalt + nZeichen( pMindestLaenge - pInhalt.length(), " " );
  }

  /**
   * <pre>
   * Gibt einen String in der angegebenen Laenge und der angegebenen Zeichenfolge zurueck.
   *  
   * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
   * 
   * Ist der Parameeter "pZeichen" gleich null, wird ein Leerstring zurueckgegeben.
   * </pre>
   *  
   * @param pAnzahlStellen die Laenge
   * @param pZeichen das zu wiederholende Zeichen
   * @return einen String der angegebenen Laenge mit dem uebergebenen Zeichen
   */
  public static String nZeichen( int pAnzahlStellen, String pZeichen )
  {
    if ( pZeichen == null )
    {
      return "";
    }

    /*
     * Ist die Laenge negativ oder 0, wird ein Leerstring zurueckgegeben
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    if ( pAnzahlStellen > 15000 )
    {
      pAnzahlStellen = 15000;
    }

    String ergebnis = pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen + pZeichen;

    /*
     * Der String "ergebnis" wird solange verdoppelt bis die Laenge groesser der Anzahl aus dem Parameter ist. 
     * Anschliessend wird ein Substring der Parameter-Laenge zurueckgegeben.
     */
    int zaehler = 1;

    while ( ( zaehler <= 50 ) && ( ergebnis.length() <= pAnzahlStellen ) )
    {
      ergebnis += ergebnis;

      zaehler++;
    }

    return ergebnis.substring( 0, pAnzahlStellen );
  }

  /**
   * @param pEingabe die Eingabe
   * @return einen String
   */
  public static String getJavaString( String pEingabe )
  {
    if ( pEingabe == null )
    {
      return "null";
    }
  
    StringBuffer str_buffer = new StringBuffer();
  
    str_buffer.append( "\"" );
  
    int akt_index = 0;
  
    while ( akt_index < pEingabe.length() )
    {
      switch ( pEingabe.charAt( akt_index ) )
      {
        case '"' :
          str_buffer.append( "\\\"" );
          break;
          
        case '\n' :
          str_buffer.append( "\\n" );
          break;
          
        case '\r' :
          str_buffer.append( "\\r" );
          break;
          
        default :
          str_buffer.append( pEingabe.charAt( akt_index ) );
      }
  
      akt_index++;
    }
  
    str_buffer.append( "\"" );
  
    return str_buffer.toString();
  }

  /**
   * <pre>
   * Schneidet die Anzahl-Stellen von dem uebergebenen String ab und gibt diesen zurueck.
   * 
   * Uebersteigt die Anazhl der abzuschneidenden Stellen die Stringlaenge, wird der 
   * Quellstring insgesamt zurueckgegeben.
   * 
   * Ist die Anzahl der abzuschneidenden Stellen negativ oder 0, wird ein Leerstring zurueckgegeben.
   * 
   * right( "ABC.DEF.GHI.JKL",  7 ) = "GHI.JKL"
   * right( "ABC.DEF.GHI.JKL", 20 ) = "ABC.DEF.GHI.JKL" = Anzahl Stellen uebersteigt Stringlaenge
   * right( "ABC.DEF.GHI.JKL",  0 ) = ""                = 0 Stellen abschneiden = Leerstring
   * right( "ABC.DEF.GHI.JKL", -7 ) = ""                = negative Anzahl       = Leerstring
   * 
   * </pre>
   * 
   * @param pString der Quellstring
   * @param pAnzahlStellen die Anzahl der von rechts abzuschneidenden Stellen
   * @return der ermittelte Teilstring
   */
  public static String right( String pString, int pAnzahlStellen )
  {
    if ( ( pString != null ) && ( pAnzahlStellen > 0 ) )
    {
      /*
       * Die Ab-Position ist die Laenge des Eingabestrings, abzueglich der von 
       * rechts abzuschneidenden Stellen. Die Ab-Postion darf aber nicht negativ
       * werden. Die minimale Ab-Position ist der Stringanfang (Position 0).
       * 
       * Die Bis-Position ist die laenge des Eingabestrings.
       */
      return pString.substring( Math.max( 0, pString.length() - pAnzahlStellen ), pString.length() );
    }
  
    /*
     * pString nicht gesetzt oder pAnzahlStellen < 0
     */
    return "";
  }

  /**
   * <pre>
   * Schneidet Anzahl-Stellen von dem uebergebenen String ab und gibt diesen zurueck.
   * 
   * Ist der Parameter "pString" gleich \"null\", wird ein Leerstring zurueckgegeben.
   * 
   * Uebersteigt die Anazhl der abzuschneidenden Stellen die Stringlaenge, wird der
   * Quellstring insgesamt zurueckgegeben.
   * 
   * Ist die Anzahl der abzuschneidenden Stellen negativ oder 0, wird ein Leerstring zurueckgegeben.
   * 
   * FkString.left( "ABC.DEF.GHI.JKL",  3 ) = "ABC"
   * FkString.left( "ABC.DEF.GHI.JKL",  4 ) = "ABC."
   * FkString.left( "ABC.DEF.GHI.JKL", 20 ) = "ABC.DEF.GHI.JKL"
   * 
   * FkString.left( "ABC.DEF.GHI.JKL", -3 ) = "" = negative Anzahl von Stellen = Leerstring
   * FkString.left(                "", 10 ) = "" = pString ist Leerstring      = Leerstring
   * FkString.left(              null, 10 ) = "" = pString ist null            = Leerstring
   * </pre>
   * 
   * @param pString der Quellstring
   * @param pAnzahlStellen die Anzahl der von links abzuschneidenden Stellen
   * @return den sich ergebenden String, Leerstring wenn die Anzahl der Stellen negativ ist oder pString null ist
   */
  public static String left( String pString, int pAnzahlStellen )
  {
    /*
     * Pruefung: "pString" gleich "null" ?
     * 
     * Ist der Parameter "pString" gleich "null" gibt es keinen String. 
     * Der Aufrufder bekommt einen Leerstring zurueck
     */
    if ( pString == null )
    {
      return "";
    }

    /*
     * Pruefung: Anzahl der Stellen negativ?
     * Ist die Anzahl der abzuschneidenden Stellen negativ, bleibt 
     * kein Teil von pString uebrig. Dieser Fall wird analog einer 
     * Uebergabe von 0 Zeichen abschneiden behandelt.  
     * 
     * Der Aufrufer bekommt einen Leerstring zurueck.
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    /*
     * Pruefung: Teilstring zurueckgeben?
     * Ist die Anzahl der Stellen kleiner als die Laenge von "pString", 
     * wird ein Teilstring zurueckgegeben.
     *
     * Der Aufrufer bekommt den Teilstring ab der Position 0 bis zur
     * Anzahl der abzuschneidenden Stellen zuruek. 
     */
    if ( pAnzahlStellen < pString.length() )
    {
      return pString.substring( 0, pAnzahlStellen );
    }

    /*
     * Ueberschreitet die Anzahl der abzuschneidenden Stellen die 
     * Laenge des Eingabestrings, muss kein Zeichen vom Eingabestring
     * abgeschnitten werden. 
     * 
     * Der Aufrufer bekommt die Eingabe zuruek.
     */
    return pString;
  }
  
  public static String getSystemInfo()
  {
    Properties prop_sys = System.getProperties();

    String str_nicht_vorhanden = "";

    String str_ergebnis = "";

    str_ergebnis += "\n";
    str_ergebnis += "\nSystem Properties ----------------------------------------------------------------";
    str_ergebnis += "\n";

    try
    {

      str_ergebnis += "\n java.runtime.name             " + prop_sys.getProperty( "java.runtime.name", str_nicht_vorhanden );
      str_ergebnis += "\n java.vm.version               " + prop_sys.getProperty( "java.vm.version", str_nicht_vorhanden );
      str_ergebnis += "\n java.vm.vendor                " + prop_sys.getProperty( "java.vm.vendor", str_nicht_vorhanden );
      str_ergebnis += "\n java.vendor.url               " + prop_sys.getProperty( "java.vendor.url", str_nicht_vorhanden );
      str_ergebnis += "\n java.vm.name                  " + prop_sys.getProperty( "java.vm.name", str_nicht_vorhanden );
      str_ergebnis += "\n java.runtime.version          " + prop_sys.getProperty( "java.runtime.version", str_nicht_vorhanden );
      str_ergebnis += "\n os.arch                       " + prop_sys.getProperty( "os.arch", str_nicht_vorhanden );
      str_ergebnis += "\n java.vm.specification.vendor  " + prop_sys.getProperty( "java.vm.specification.vendor", str_nicht_vorhanden );
      str_ergebnis += "\n os.name                       " + prop_sys.getProperty( "os.name", str_nicht_vorhanden );
      str_ergebnis += "\n sun.jnu.encoding              " + prop_sys.getProperty( "sun.jnu.encoding", str_nicht_vorhanden );
      str_ergebnis += "\n sun.management.compiler       " + prop_sys.getProperty( "sun.management.compiler", str_nicht_vorhanden );
      str_ergebnis += "\n os.version                    " + prop_sys.getProperty( "os.version", str_nicht_vorhanden );
      str_ergebnis += "\n java.specification.version    " + prop_sys.getProperty( "java.specification.version", str_nicht_vorhanden );
      str_ergebnis += "\n java.vm.specification.version " + prop_sys.getProperty( "java.vm.specification.version", str_nicht_vorhanden );
      str_ergebnis += "\n sun.arch.data.model           " + prop_sys.getProperty( "sun.arch.data.model", str_nicht_vorhanden );
      str_ergebnis += "\n java.specification.vendor     " + prop_sys.getProperty( "java.specification.vendor", str_nicht_vorhanden );
      str_ergebnis += "\n java.version                  " + prop_sys.getProperty( "java.version", str_nicht_vorhanden );
      str_ergebnis += "\n java.vendor                   " + prop_sys.getProperty( "java.vendor", str_nicht_vorhanden );
      str_ergebnis += "\n sun.desktop                   " + prop_sys.getProperty( "sun.desktop", str_nicht_vorhanden );
      str_ergebnis += "\n sun.cpu.isalist               " + prop_sys.getProperty( "sun.cpu.isalist", str_nicht_vorhanden );
    }
    catch ( Exception err_inst )
    {
      str_ergebnis += "\n Fehler - System Properties - " + err_inst.getMessage();
    }

    str_ergebnis += "\n";
    str_ergebnis += "\nSystem getEnv() ------------------------------------------------------------------";
    str_ergebnis += "\n";
    str_ergebnis += "\n https://coderanch.com/t/567735/java/hardware-details-processor-type-java";
    str_ergebnis += "\n";

    try
    {
      str_ergebnis += "\n PROCESSOR_IDENTIFIER          " + System.getenv( "PROCESSOR_IDENTIFIER" );
      str_ergebnis += "\n PROCESSOR_ARCHITECTURE        " + System.getenv( "PROCESSOR_ARCHITECTURE" );
      str_ergebnis += "\n PROCESSOR_ARCHITEW6432        " + System.getenv( "PROCESSOR_ARCHITEW6432" );
      str_ergebnis += "\n NUMBER_OF_PROCESSORS          " + System.getenv( "NUMBER_OF_PROCESSORS" );
    }
    catch ( Exception err_inst )
    {
      str_ergebnis += "\n Fehler " + err_inst.getMessage();
    }
    catch ( Throwable err_inst )
    {
      str_ergebnis += "\n Fehler - java.lang.Error - " + err_inst.getMessage();
    }

    Runtime runtime = Runtime.getRuntime();

    int byte_anzahl_ein_mb = 1048576; // =  1024 * 1024;

    int nr_of_threads = -1;
    int thread_active_count = -1;
    int available_processors = -1;

    try
    {
      available_processors = runtime.availableProcessors();
    }
    catch ( Exception err_inst )
    {
      str_ergebnis += "\n Fehler - available_processors - " + err_inst.getMessage();
    }
    catch ( Throwable err_inst )
    {
      str_ergebnis += "\n Fehler - java.lang.Error - " + err_inst.getMessage();
    }

    try
    {
      nr_of_threads = ManagementFactory.getThreadMXBean().getThreadCount();
    }
    catch ( Exception err_inst )
    {
      str_ergebnis += "\n Fehler - nr_of_threads - " + err_inst.getMessage();
    }
    catch ( Throwable err_inst )
    {
      str_ergebnis += "\n Fehler - java.lang.Error - " + err_inst.getMessage();
    }

    try
    {
      thread_active_count = Thread.activeCount();
    }
    catch ( Exception err_inst )
    {
      str_ergebnis += "\n Fehler - thread_active_count - " + err_inst.getMessage();
    }
    catch ( Throwable err_inst )
    {
      str_ergebnis += "\n Fehler - java.lang.Error - " + err_inst.getMessage();
    }

    str_ergebnis += "\n";
    str_ergebnis += "\nRuntime --------------------------------------------------------------------------";
    str_ergebnis += "\n";

    try
    {
      str_ergebnis += "\n runtime.totalMemory()      MB " + ( runtime.totalMemory() / byte_anzahl_ein_mb );
    }
    catch ( Exception err_inst )
    {
      str_ergebnis += "\n Fehler - runtime - " + err_inst.getMessage();
    }
    catch ( Throwable err_inst )
    {
      str_ergebnis += "\n Fehler - java.lang.Error - " + err_inst.getMessage();
    }

    try
    {
      str_ergebnis += "\n runtime.maxMemory()        MB " + ( runtime.maxMemory() / byte_anzahl_ein_mb );
      str_ergebnis += "\n runtime.freeMemory()       MB " + ( runtime.freeMemory() / byte_anzahl_ein_mb );
      str_ergebnis += "\n used                       MB " + ( ( runtime.totalMemory() - runtime.freeMemory() ) / byte_anzahl_ein_mb );
    }
    catch ( Exception err_inst )
    {
      str_ergebnis += "\n Fehler - runtime - " + err_inst.getMessage();
    }
    catch ( Throwable err_inst )
    {
      str_ergebnis += "\n Fehler - java.lang.Error - " + err_inst.getMessage();
    }

    str_ergebnis += "\n";
    str_ergebnis += "\n runtime.availableProcessors() " + available_processors;
    str_ergebnis += "\n nr_of_threads                 " + nr_of_threads;
    str_ergebnis += "\n thread_active_count           " + thread_active_count;
    str_ergebnis += "\n";

    return str_ergebnis;
  }


}
