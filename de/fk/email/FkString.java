package de.fk.email;

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

}
