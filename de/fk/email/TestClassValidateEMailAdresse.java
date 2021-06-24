package de.fk.email;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.sanctionco.jmail.JMail;

class TestClassValidateEMailAdresse
{
  /*
   *  x_ergebnis_alt null                           = -1   
   *  x_ergebnis_neu null                           = 0     = ########## FEHLER ##########
   *  x_ergebnis_alt ""                             = 0    
   *  x_ergebnis_neu ""                             = 0     = OK 
   *  x_ergebnis_alt "        "                     = 0    
   *  x_ergebnis_neu "        "                     = 0     = OK 
   *  x_ergebnis_alt "A.B@C.DE"                     = 1    
   *  x_ergebnis_neu "A.B@C.DE"                     = 1     = OK 
   *  x_ergebnis_alt "A."B"@C.DE"                   = 0    
   *  x_ergebnis_neu "A."B"@C.DE"                   = 0     = OK 
   *  x_ergebnis_alt "A.B@[1.2.3.4]"                = 0    
   *  x_ergebnis_neu "A.B@[1.2.3.4]"                = 0     = OK 
   *  x_ergebnis_alt "(A)B@C.DE"                    = 0    
   *  x_ergebnis_neu "(A)B@C.DE"                    = 0     = OK 
   *  x_ergebnis_alt "A(B)@C.DE"                    = 0    
   *  x_ergebnis_neu "A(B)@C.DE"                    = 0     = OK 
   *  x_ergebnis_alt "(A)B@[1.2.3.4]"               = 0    
   *  x_ergebnis_neu "(A)B@[1.2.3.4]"               = 0     = OK 
   *  x_ergebnis_alt "A(B)@[1.2.3.4]"               = 0    
   *  x_ergebnis_neu "A(B)@[1.2.3.4]"               = 0     = OK 
   *  x_ergebnis_alt "(A)B@[IPv6:1:2:3:4:5:6:7:8]"  = 0    
   *  x_ergebnis_neu "(A)B@[IPv6:1:2:3:4:5:6:7:8]"  = 0     = OK 
   *  x_ergebnis_alt "A(B)@[IPv6:1:2:3:4:5:6:7:8]"  = 0    
   *  x_ergebnis_neu "A(B)@[IPv6:1:2:3:4:5:6:7:8]"  = 0     = OK 
   *  x_ergebnis_alt "A@B.CD"                       = 1    
   *  x_ergebnis_neu "A@B.CD"                       = 1     = OK 
   *  x_ergebnis_alt "ABC1.DEF2@GHI3.JKL4"          = 1    
   *  x_ergebnis_neu "ABC1.DEF2@GHI3.JKL4"          = 1     = OK 
   *  x_ergebnis_alt "ABC.DEF_@GHI.JKL"             = 1    
   *  x_ergebnis_neu "ABC.DEF_@GHI.JKL"             = 1     = OK 
   *  x_ergebnis_alt "#ABC.DEF@GHI.JKL"             = 0    
   *  x_ergebnis_neu "#ABC.DEF@GHI.JKL"             = 1     = ########## FEHLER ##########
   *  x_ergebnis_alt "ABCDEFGHIJKLMNOP"             = 0    
   *  x_ergebnis_neu "ABCDEFGHIJKLMNOP"             = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@GHI.J"                = 1    
   *  x_ergebnis_neu "ABC.DEF@GHI.J"                = 0     = ########## FEHLER ##########
   *  x_ergebnis_alt "ME@MYSELF.LOCALHORST"         = 1    
   *  x_ergebnis_neu "ME@MYSELF.LOCALHORST"         = 1     = OK 
   *  x_ergebnis_alt "ABC.DEF@GHI.2KL"              = 1    
   *  x_ergebnis_neu "ABC.DEF@GHI.2KL"              = 0     = ########## FEHLER ##########
   *  x_ergebnis_alt "ABC.DEF@GHI.JK-"              = 1    
   *  x_ergebnis_neu "ABC.DEF@GHI.JK-"              = 0     = ########## FEHLER ##########
   *  x_ergebnis_alt "ABC.DEF@GHI.JK_"              = 0    
   *  x_ergebnis_neu "ABC.DEF@GHI.JK_"              = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@GHI.JK2"              = 1    
   *  x_ergebnis_neu "ABC.DEF@GHI.JK2"              = 1     = OK 
   *  x_ergebnis_alt "ABC.DEF@2HI.JKL"              = 1    
   *  x_ergebnis_neu "ABC.DEF@2HI.JKL"              = 1     = OK 
   *  x_ergebnis_alt "ABC.DEF@-HI.JKL"              = 1    
   *  x_ergebnis_neu "ABC.DEF@-HI.JKL"              = 0     = ########## FEHLER ##########
   *  x_ergebnis_alt "ABC.DEF@_HI.JKL"              = 0    
   *  x_ergebnis_neu "ABC.DEF@_HI.JKL"              = 0     = OK 
   *  x_ergebnis_alt "A . B & C . D"                = 0    
   *  x_ergebnis_neu "A . B & C . D"                = 0     = OK 
   *  x_ergebnis_alt "(?).[!]@{&}.<:>"              = 0    
   *  x_ergebnis_neu "(?).[!]@{&}.<:>"              = 0     = OK 
   *  x_ergebnis_alt ".ABC.DEF@GHI.JKL"             = 0    
   *  x_ergebnis_neu ".ABC.DEF@GHI.JKL"             = 0     = OK 
   *  x_ergebnis_alt "ABC..DEF@GHI.JKL"             = 0    
   *  x_ergebnis_neu "ABC..DEF@GHI.JKL"             = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@GHI..JKL"             = 0    
   *  x_ergebnis_neu "ABC.DEF@GHI..JKL"             = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@GHI.JKL.."            = 0    
   *  x_ergebnis_neu "ABC.DEF@GHI.JKL.."            = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF.@GHI.JKL"             = 0    
   *  x_ergebnis_neu "ABC.DEF.@GHI.JKL"             = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@."                    = 0    
   *  x_ergebnis_neu "ABC.DEF@."                    = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@.GHI.JKL"             = 0    
   *  x_ergebnis_neu "ABC.DEF@.GHI.JKL"             = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF.@GHI.JKL"             = 0    
   *  x_ergebnis_neu "ABC.DEF.@GHI.JKL"             = 0     = OK 
   *  x_ergebnis_alt "ABCDEF@GHIJKL"                = 0    
   *  x_ergebnis_neu "ABCDEF@GHIJKL"                = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@GHI.JKL."             = 0    
   *  x_ergebnis_neu "ABC.DEF@GHI.JKL."             = 0     = OK 
   *  x_ergebnis_alt "@GHI.JKL"                     = 1    
   *  x_ergebnis_neu "@GHI.JKL"                     = 0     = ########## FEHLER ##########
   *  x_ergebnis_alt "ABC.DEF@"                     = 0    
   *  x_ergebnis_neu "ABC.DEF@"                     = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF\@"                    = 0    
   *  x_ergebnis_neu "ABC.DEF\@"                    = 0     = OK 
   *  x_ergebnis_alt "ABC.DEF@@GHI.JKL"             = 0    
   *  x_ergebnis_neu "ABC.DEF@@GHI.JKL"             = 0     = OK 
   *  x_ergebnis_alt "ABC\@DEF@GHI.JKL"             = 0    
   *  x_ergebnis_neu "ABC\@DEF@GHI.JKL"             = 1     = ########## FEHLER ##########
   * 
   *  ALT  - Anzahl 60000 = MS     4797    0.0799500
   * 
   *  NEU  - Anzahl 60000 = MS      973    0.016216666666666667
   * 
   *  ALT  - Anzahl 60000 = MS     4735    0.07891666666666666
   * 
   *  NEU  - Anzahl 60000 = MS      842    0.014033333333333333
   * 
   *  NEU  - Anzahl 60000 = MS      862    0.014366666666666666
   * 
   *  ALT  - Anzahl 60000 = MS     4734    0.0789000
   * 
   *  ALT  - Anzahl 60000 = MS     4707    0.0784500
   * 
   *  NEU  - Anzahl 60000 = MS      888    0.0148000
   * 
   *  NEU  - Anzahl 60000 = MS      854    0.014233333333333334
   * 
   *  ALT  - Anzahl 60000 = MS     4743    0.0790500
   * 
   * MS ALT =   23716 = 0.39526666666666666
   * MS NEU =    4419 = 0.0736500
   * 
   * MS schneller = 5.366825073546051
   * 
   * ALT Anzahl Aufrufe in 1 Sekunde =       2529
   * NEU Anzahl Aufrufe in 1 Sekunde =      13577
   * 
   */
  private static final String         STR_BEZ_ALT        = "ALT"; // " + STR_BEZ_ALT + "

  private static final String         STR_BEZ_NEU        = "NEU"; // " + STR_BEZ_NEU + "

  private static DecimalFormatSymbols m_other_symbols    = null;

  private static DecimalFormat        m_number_format    = null;

  private static int                  m_durchlauf_anzahl = 2000;

  public static void main( String[] args )
  {
    startTest();

    /*
     * Programmende mit System.exit(0) 
     */
    System.exit( 0 );
  }

  public static void startTest()
  {
    wl( "" );
    wl( "startTest TestClassValidateEMailAdresse" );
    wl( "" );

    m_other_symbols = new DecimalFormatSymbols( Locale.getDefault() );

    m_other_symbols.setDecimalSeparator( '.' );
    m_other_symbols.setGroupingSeparator( '.' );

    m_number_format = new DecimalFormat( "###.##", m_other_symbols );

    m_number_format.setMaximumFractionDigits( 30 );
    m_number_format.setMinimumFractionDigits( 7 );

    try
    {
      assertEquals( null );
      assertEquals( "" );
      assertEquals( "        " );
      assertEquals( "A.B@C.DE" );
      assertEquals( "A.\"B\"@C.DE" );
      assertEquals( "A.B@[1.2.3.4]" );
      assertEquals( "(A)B@C.DE" );
      assertEquals( "A(B)@C.DE" );
      assertEquals( "(A)B@[1.2.3.4]" );
      assertEquals( "A(B)@[1.2.3.4]" );
      assertEquals( "(A)B@[IPv6:1:2:3:4:5:6:7:8]" );
      assertEquals( "A(B)@[IPv6:1:2:3:4:5:6:7:8]" );
      assertEquals( "A@B.CD" );
      assertEquals( "ABC1.DEF2@GHI3.JKL4" );
      assertEquals( "ABC.DEF_@GHI.JKL" );
      assertEquals( "#ABC.DEF@GHI.JKL" );
      assertEquals( "ABCDEFGHIJKLMNOP" );
      assertEquals( "ABC.DEF@GHI.J" );
      assertEquals( "ME@MYSELF.LOCALHORST" );
      assertEquals( "ABC.DEF@GHI.2KL" );
      assertEquals( "ABC.DEF@GHI.JK-" );
      assertEquals( "ABC.DEF@GHI.JK_" );
      assertEquals( "ABC.DEF@GHI.JK2" );
      assertEquals( "ABC.DEF@2HI.JKL" );
      assertEquals( "ABC.DEF@-HI.JKL" );
      assertEquals( "ABC.DEF@_HI.JKL" );
      assertEquals( "A . B & C . D" );
      assertEquals( "(?).[!]@{&}.<:>" );
      assertEquals( ".ABC.DEF@GHI.JKL" );
      assertEquals( "ABC..DEF@GHI.JKL" );
      assertEquals( "ABC.DEF@GHI..JKL" );
      assertEquals( "ABC.DEF@GHI.JKL.." );
      assertEquals( "ABC.DEF.@GHI.JKL" );
      assertEquals( "ABC.DEF@." );
      assertEquals( "ABC.DEF@.GHI.JKL" );
      assertEquals( "ABC.DEF.@GHI.JKL" );
      assertEquals( "ABCDEF@GHIJKL" );
      assertEquals( "ABC.DEF@GHI.JKL." );
      assertEquals( "@GHI.JKL" );
      assertEquals( "ABC.DEF@" );
      assertEquals( "ABC.DEF\\@" );
      assertEquals( "ABC.DEF@@GHI.JKL" );
      assertEquals( "ABC\\@DEF@GHI.JKL" );

      long ms_alt = startTestF( 1 );
      long ms_neu = startTestF( 0 );

      ms_alt += startTestF( 1 );
      ms_neu += startTestF( 0 );

      ms_neu += startTestF( 0 );
      ms_alt += startTestF( 1 );

      ms_alt += startTestF( 1 );
      ms_neu += startTestF( 0 );

      ms_neu += startTestF( 0 );
      ms_alt += startTestF( 1 );

      double ms_schneller = ( (double) ms_alt ) / ( (double) ms_neu );

      double ms_zeit_je_aufruf_alt = ( (double) ms_alt ) / m_durchlauf_anzahl;

      double ms_zeit_je_aufruf_neu = ( (double) ms_neu ) / m_durchlauf_anzahl;

      double ms_zeit_x = 1000.0; // = 1 Sekunde als Millisekunden

      wl( "" );
      wl( "MS " + STR_BEZ_ALT + " = " + FkString.getFeldRechtsMin( "" + ms_alt, 7 ) + " = " + m_number_format.format( ms_zeit_je_aufruf_alt ) );
      wl( "MS " + STR_BEZ_NEU + " = " + FkString.getFeldRechtsMin( "" + ms_neu, 7 ) + " = " + m_number_format.format( ms_zeit_je_aufruf_neu ) );
      wl( "" );
      wl( "MS schneller = " + m_number_format.format( ms_schneller ) );
      wl( "" );
      wl( STR_BEZ_ALT + " Anzahl Aufrufe in 1 Sekunde = " + FkString.getFeldRechtsMin( (int) ( ms_zeit_x / ms_zeit_je_aufruf_alt ), 10 ) );
      wl( STR_BEZ_NEU + " Anzahl Aufrufe in 1 Sekunde = " + FkString.getFeldRechtsMin( (int) ( ms_zeit_x / ms_zeit_je_aufruf_neu ), 10 ) );
      wl( "" );
    }
    catch ( Exception err_inst )
    {
      wl( "Fehler: errTest ", err_inst );
    }
  }

  /**
   * Ausgabe des Strings auf System.out
   *
   * @param  pString der auszugebende String
   */
  private static void wl( String pString )
  {
    System.out.println( pString );
  }

  /**
   * Ausgabe des Strings und der Fehlermeldung auf System.out
   *
   * @param  pString der auszugebende String
   * @param  pThrowable die auszugebende Fehlermeldung (bei null wird der Parameter ignoriert)
   */
  private static void wl( String pString, Throwable pThrowable )
  {
    System.out.println( pString );

    /*
     * Pruefung: Parameter "pThrowable" ungleich null ? 
     */
    if ( pThrowable != null )
    {
      /*
       * Ist ein auszugebender Fehler vorhanden, wird die Fehlermeldung
       * und der Stacktrace auf System.out ausgegeben.
       */
      System.out.println( pThrowable.toString() );

      pThrowable.printStackTrace( System.out );
    }
  }

  private static void assertEquals( String pFormat ) throws Exception
  {
    int x_ergebnis_alt = startTest( 1, pFormat, 0 );

    int x_ergebnis_neu = startTest( 2, pFormat, 0 );

    boolean knz_ergebnis_gleich = true;

    if ( ( x_ergebnis_alt == -1 ) && ( x_ergebnis_neu == -1 ) )
    {
      knz_ergebnis_gleich = true;
    }
    else if ( ( x_ergebnis_alt != -1 ) && ( x_ergebnis_neu == -1 ) )
    {
      knz_ergebnis_gleich = false;
    }
    else if ( ( x_ergebnis_alt == -1 ) && ( x_ergebnis_neu != -1 ) )
    {
      knz_ergebnis_gleich = false;
    }
    else
    {
      knz_ergebnis_gleich = x_ergebnis_alt == x_ergebnis_neu;
    }

    wl( " x_ergebnis_alt " + FkString.getFeldLinksMin( ( pFormat == null ? "null" : "\"" + ( pFormat.length() > 40 ? pFormat.substring( 0, 40 ) + "..." : pFormat ) + "\"" ), 30 ) + " = " + FkString.getFeldLinksMin( x_ergebnis_alt, 5 ) );
    wl( " x_ergebnis_neu " + FkString.getFeldLinksMin( ( pFormat == null ? "null" : "\"" + ( pFormat.length() > 40 ? pFormat.substring( 0, 40 ) + "..." : pFormat ) + "\"" ), 30 ) + " = " + FkString.getFeldLinksMin( x_ergebnis_neu, 5 ) + ( knz_ergebnis_gleich ? " = OK " : " = ########## FEHLER ##########" ) );

    if ( knz_ergebnis_gleich == false )
    {
      // throw new Exception( "Formatfehler " + pFormat + " " );
    }
  }

  private static long startTestF( int pNr )
  {
    int durchlauf_zaehler = 0;

    long millisekunden_zeit_differenz = 0;

    long millisekunden_start = 0;
    long millisekunden_ende = 0;

    millisekunden_start = System.currentTimeMillis();

    while ( durchlauf_zaehler < m_durchlauf_anzahl )
    {
      startTestEinzelnd( pNr, 0 );

      durchlauf_zaehler++;
    }

    millisekunden_ende = System.currentTimeMillis();

    millisekunden_zeit_differenz = millisekunden_ende - millisekunden_start;

    wl( "" );
    wl( " " + ( pNr == 1 ? STR_BEZ_ALT : STR_BEZ_NEU ) + " " + " - Anzahl " + m_durchlauf_anzahl + " = MS  " + FkString.getFeldRechtsMin( millisekunden_zeit_differenz, 7 ) + "    " + m_number_format.format( ( (double) millisekunden_zeit_differenz ) / m_durchlauf_anzahl ) );

    return millisekunden_zeit_differenz;
  }

  private static void startTestEinzelnd( int pNr, int pKnzAusgabe )
  {
    try
    {
      startTest( pNr, null, pKnzAusgabe );
      startTest( pNr, null, pKnzAusgabe );
      startTest( pNr, "", pKnzAusgabe );
      startTest( pNr, "        ", pKnzAusgabe );
      startTest( pNr, "A.B@C.DE", pKnzAusgabe );
      startTest( pNr, "A.\"B\"@C.DE", pKnzAusgabe );
      startTest( pNr, "A.B@[1.2.3.4]", pKnzAusgabe );
      startTest( pNr, "(A)B@C.DE", pKnzAusgabe );
      startTest( pNr, "A(B)@C.DE", pKnzAusgabe );
      startTest( pNr, "(A)B@[1.2.3.4]", pKnzAusgabe );
      startTest( pNr, "A(B)@[1.2.3.4]", pKnzAusgabe );
      startTest( pNr, "(A)B@[IPv6:1:2:3:4:5:6:7:8]", pKnzAusgabe );
      startTest( pNr, "A(B)@[IPv6:1:2:3:4:5:6:7:8]", pKnzAusgabe );
      startTest( pNr, "A@B.CD", pKnzAusgabe );
      startTest( pNr, "ABC1.DEF2@GHI3.JKL4", pKnzAusgabe );
      startTest( pNr, "ABC.DEF_@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "#ABC.DEF@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "ABCDEFGHIJKLMNOP", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI.J", pKnzAusgabe );
      startTest( pNr, "ME@MYSELF.LOCALHORST", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI.2KL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI.JK-", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI.JK_", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI.JK2", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@2HI.JKL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@-HI.JKL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@_HI.JKL", pKnzAusgabe );
      startTest( pNr, "A . B & C . D", pKnzAusgabe );
      startTest( pNr, "(?).[!]@{&}.<:>", pKnzAusgabe );
      startTest( pNr, ".ABC.DEF@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "ABC..DEF@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI..JKL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI.JKL..", pKnzAusgabe );
      startTest( pNr, "ABC.DEF.@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@.", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@.GHI.JKL", pKnzAusgabe );
      startTest( pNr, "ABCDEF@GHIJKL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@GHI.JKL.", pKnzAusgabe );
      startTest( pNr, "@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@", pKnzAusgabe );
      startTest( pNr, "ABC.DEF\\@", pKnzAusgabe );
      startTest( pNr, "ABC.DEF@@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "ABC\\@DEF@GHI.JKL", pKnzAusgabe );
      startTest( pNr, "email@domain.com", pKnzAusgabe );
      startTest( pNr, "email.email@domain.com", pKnzAusgabe );
      startTest( pNr, "email..email@domain.com", pKnzAusgabe );

      startTest( pNr, "Dlbyyu66.htgqnnz.mbhaayetubec@aapdbjdx.wz", pKnzAusgabe );
      startTest( pNr, "ngp.difhwuetlfxno.swi@dgvxkbzeqi.km", pKnzAusgabe );
      startTest( pNr, "GyiqfesJnwycj@qxtcowerewrdepzn.lzo", pKnzAusgabe );
      startTest( pNr, "Vonoalspg-Etgliag@fwdwrxbiv.zku", pKnzAusgabe );
      startTest( pNr, "COekxqpl@ZugHjIotdu.gp", pKnzAusgabe );
      startTest( pNr, "Yomphxtfndkkpc@htruqrfld.dfp", pKnzAusgabe );
      startTest( pNr, "KJ@tro.aro", pKnzAusgabe );
      startTest( pNr, "f-lrrkir@Cilbzq65.lm", pKnzAusgabe );
      startTest( pNr, "Vvhrhiics-Ivfrw@gbczcjvwez.nod", pKnzAusgabe );
      startTest( pNr, "k.pnavquf@dzg.xkm", pKnzAusgabe );
      startTest( pNr, "Yfmseaw-Febxypw@dkbm.ylq", pKnzAusgabe );

      startTest( pNr, "Xpxh.Xxjv@Ozdx.su", pKnzAusgabe );
      startTest( pNr, "jpnzz-gnjx@RwweRpnWwofk.qf", pKnzAusgabe );
      startTest( pNr, "Tjiy.Lyx@koishiyi.hb", pKnzAusgabe );
      startTest( pNr, "z.aglfcqk@fwisnlj.jh", pKnzAusgabe );
      startTest( pNr, "Ekwd.Aavdlk@yikie.bn", pKnzAusgabe );
      startTest( pNr, "KX@zrow.aup", pKnzAusgabe );
      startTest( pNr, "X.Wugbiyk@AJGM.vz", pKnzAusgabe );
      startTest( pNr, "kfqun.nyiruzcjhflc@vncr.ua", pKnzAusgabe );
      startTest( pNr, "QehshrsWaaee@zrws.zbv", pKnzAusgabe );
      startTest( pNr, "Lfgstq-Kjjxcw@qjjnr.oh", pKnzAusgabe );
      startTest( pNr, "Oekxsh.Pxes@Cfvc.bj", pKnzAusgabe );
      startTest( pNr, "SssfrGyd@jdrblsv.dy", pKnzAusgabe );
      startTest( pNr, "Cgne.Vfgjg@xqh-tfs.gj", pKnzAusgabe );
      startTest( pNr, "w.bdxwvh@utvnqt.gv", pKnzAusgabe );
      startTest( pNr, "wtlzootkpmvod@iiebhm.ed", pKnzAusgabe );
      startTest( pNr, "hdkx-rdcfar.bubfog@gkebb.jb", pKnzAusgabe );
      startTest( pNr, "bdhlb-bzdsxe@ttroqe.gk", pKnzAusgabe );
      startTest( pNr, "Odjih.Ryhowu@ughfoyp.jc", pKnzAusgabe );
      startTest( pNr, "n.ljxdpw@pnjdmgft.jp", pKnzAusgabe );

      startTest( pNr, "Ktxt.Fxxiupay@4iJMj.hg", pKnzAusgabe );
      startTest( pNr, "T.Wxfsrqq@VPDJ.xq", pKnzAusgabe );
      startTest( pNr, "Naycyx-Izjulq@uodrm.gw", pKnzAusgabe );
      startTest( pNr, "BojwpVgl@kwzyzikmfo.ifv", pKnzAusgabe );
      startTest( pNr, "jzwagzjlmcu@dxsobphy.td", pKnzAusgabe );
      startTest( pNr, "Btese16@xtjciredxd.qmz", pKnzAusgabe );
      startTest( pNr, "TK@icvgwrvh.yee", pKnzAusgabe );
      startTest( pNr, "UalgeeEsepxv@gfwohppiryg.gi", pKnzAusgabe );
      startTest( pNr, "Uqxhgc63@hcue.pn", pKnzAusgabe );
      startTest( pNr, "s-ccroy@ohvtm.zu", pKnzAusgabe );
      startTest( pNr, "khom-epdn@yycpbu.js", pKnzAusgabe );
      startTest( pNr, "Ekch.Mkjuglkl@kaueo-pdb.fbx", pKnzAusgabe );
      startTest( pNr, "Rdih.Ydh@xcauaau.gp", pKnzAusgabe );
      startTest( pNr, "Radg.Xsxsob@eF4w7nm.sr", pKnzAusgabe );
      startTest( pNr, "Wlzo.Jzqcm@jjma.dey", pKnzAusgabe );
      startTest( pNr, "buaz.jyv@hxlm.esx", pKnzAusgabe );
      startTest( pNr, "Qgz.pcjkj74@rxruigqd.zq", pKnzAusgabe );
      startTest( pNr, "Ualjrg.Igojbz@vjtzwnr.iwo", pKnzAusgabe );
      startTest( pNr, "r.ebbebgze@ykt.owp", pKnzAusgabe );

      startTest( pNr, "bdyag.hnqnjwzpfkju@yquks-skgbdfu.vw", pKnzAusgabe );
      startTest( pNr, "Zzemmvc-Uwzomot@ahaa.rdz", pKnzAusgabe );
      startTest( pNr, "trsat.vfbidhoolons@bubv.xw", pKnzAusgabe );
      startTest( pNr, "AP@zuzn.tyn", pKnzAusgabe );
      startTest( pNr, "k.cmnthhz@xmxxcds.qt", pKnzAusgabe );
      startTest( pNr, "wpchppsxvzeri@oubrbvnr.ai", pKnzAusgabe );
      startTest( pNr, "Jnsywil.Etdfys@5ecLF.xnq", pKnzAusgabe );
      startTest( pNr, "m-ofnbmx@Jcwolw54.eg", pKnzAusgabe );
      startTest( pNr, "JoknrLimdna@tnwulmep.wu", pKnzAusgabe );
      startTest( pNr, "Ppsibygzp@gBX14.sb", pKnzAusgabe );
      startTest( pNr, "Nlzam30@YdkIV.cb", pKnzAusgabe );
      startTest( pNr, "Vzukr02@RH.ob", pKnzAusgabe );
      startTest( pNr, "KvqnaijDiljh@jyeg.ric", pKnzAusgabe );
      startTest( pNr, "krodm-eeko@IpkvXvdXnbpc.gz", pKnzAusgabe );
      startTest( pNr, "SK@syFdz.px", pKnzAusgabe );
      startTest( pNr, "Lgsgm.Chbiuxqvc@dwtvov.knu", pKnzAusgabe );
      startTest( pNr, "ofescrpgsrnyiunhnat@vgxexjltlb.xk", pKnzAusgabe );
      startTest( pNr, "ylcp.yxus@lbmhxmfsfga.ba", pKnzAusgabe );

      startTest( pNr, "Cxqzrgkk-Lzelmmd@qbljryejg.wzo", pKnzAusgabe );
      startTest( pNr, "biwg-jrmtggjss@xjxqxkzc.ui", pKnzAusgabe );
      startTest( pNr, "Ryteer.Cpso@Zrqu.nq", pKnzAusgabe );
      startTest( pNr, "IGyhseel@VlrKwRggdyMsoofwGfHtmfp.hs", pKnzAusgabe );
      startTest( pNr, "Chnel.Jcgajx@oclcaxpeip.fq", pKnzAusgabe );
      startTest( pNr, "Kgum-Mhzhfy@6jYdM.gl", pKnzAusgabe );
      startTest( pNr, "Oohva.Muqys@ag.jewl.uzi.mrqq.az", pKnzAusgabe );
      startTest( pNr, "disx.nurerymb@i-fggircp.cm", pKnzAusgabe );
      startTest( pNr, "S.Alrfb@RenJrSjmoqGajihxBiEzyls.xz", pKnzAusgabe );
      startTest( pNr, "Hlbsy.nac@vxf.aa", pKnzAusgabe );
      startTest( pNr, "Plpsm.gbu@Aerz.qg", pKnzAusgabe );
      startTest( pNr, "Dvuyy.Ixbgm@dhymmsr.pg", pKnzAusgabe );
      startTest( pNr, "Lswob.Jumpfdygk@fejyk-lfrtw.mf", pKnzAusgabe );
      startTest( pNr, "Boz-Fwbf@wcvptslttq.jdh", pKnzAusgabe );
      startTest( pNr, "ZpwalhqLemhyl@kgcfvjdfoz.uyq", pKnzAusgabe );
      startTest( pNr, "Ifkebinxb@gktndcti.fp", pKnzAusgabe );

      startTest( pNr, "alo.nsi.jeshl@ag.ybyuve.hn", pKnzAusgabe );
      startTest( pNr, "lijzdbzaqb.otnoedzndm@cvtnxyz.hfi", pKnzAusgabe );
      startTest( pNr, "Btizngbch41@gjczmdvaxt.ofl", pKnzAusgabe );
      startTest( pNr, "Zkgy.Bbcfvexv@2zxRj.gz", pKnzAusgabe );
      startTest( pNr, "B.Qibyuqa@nxgvvfsqaaiqj.ao", pKnzAusgabe );
      startTest( pNr, "Uyokv.Goqqm@htwlpisj.ntb", pKnzAusgabe );
      startTest( pNr, "NTeshul@ziar.ri", pKnzAusgabe );
      startTest( pNr, "G-Wykiquo@epon.yq", pKnzAusgabe );
      startTest( pNr, "TnnldAcp@dshqi-qhjfagp.mr", pKnzAusgabe );
      startTest( pNr, "b.zeigfhr@gjs.auo", pKnzAusgabe );
      startTest( pNr, "Expupilfu-Vfjba@nwooeljvkv.gmx", pKnzAusgabe );
      startTest( pNr, "H.Ivfjirqnsfclnjel@wlw.aip", pKnzAusgabe );
      startTest( pNr, "Bvcbo.Solhg@Gbtetw.sn", pKnzAusgabe );
      startTest( pNr, "Eieprwpk.Hghiy@hdxycpfkc.vy", pKnzAusgabe );
      startTest( pNr, "cs-ggpzd@qpuqzlv.xa", pKnzAusgabe );
      startTest( pNr, "Zmqapg.Umqzko@xaYEN.vr", pKnzAusgabe );
      startTest( pNr, "Wbqjp.Roiwz@UlcpEvkjy.li", pKnzAusgabe );

      startTest( pNr, "ZVaccwa-Biwaikom@mjody-etw.dqs", pKnzAusgabe );
      startTest( pNr, "LFfpa.zrcezmpdw@qwfdvukxq.gn", pKnzAusgabe );
      startTest( pNr, "Dgohagwv45@ygnegvoada.onk", pKnzAusgabe );
      startTest( pNr, "AihcInnkxr@ozg.suo.ul", pKnzAusgabe );
      startTest( pNr, "Rpsrci71@wbcxqoif.za", pKnzAusgabe );
      startTest( pNr, "rdqzevfnbywwf@en.eufc.kiz.dvut.ph", pKnzAusgabe );
      startTest( pNr, "CbacwosbfxKrfneua@zidxywmzia.amu", pKnzAusgabe );
      startTest( pNr, "dqfep-vgmiqy.ehbagij@defal.pa", pKnzAusgabe );
      startTest( pNr, "qtose-owvrouwthueuhxadzwaqla@jjvzgup.wh", pKnzAusgabe );
      startTest( pNr, "vmtkka@EY3jF.yn", pKnzAusgabe );
      startTest( pNr, "Cjtllfw.Pvvytmwfck@ozlpfmXo.sa", pKnzAusgabe );
      startTest( pNr, "Jxng.Wiwro@wzoigvc.qt", pKnzAusgabe );
      startTest( pNr, "bnnby.wqjas@dcohdhnt.ww", pKnzAusgabe );
      startTest( pNr, "RW@ypa.tvo", pKnzAusgabe );
      startTest( pNr, "Jzpch60@LgeXE.zt", pKnzAusgabe );
      startTest( pNr, "Elcve.Yzcnqcoeb@ccdss-sexkxvo.va", pKnzAusgabe );

      startTest( pNr, "VOyhjmzfu.Sahifkmtealzlr@mwbizbna.ym", pKnzAusgabe );
      startTest( pNr, "Tgtqpujq42@nxuxlqezpi.yis", pKnzAusgabe );
      startTest( pNr, "lgwhmcrzgr.pniuptxkzb@tuivmqv.jc", pKnzAusgabe );
      startTest( pNr, "Ndvtp.Cttnynturfk@Yrpcq.tp", pKnzAusgabe );
      startTest( pNr, "Wdyvt.Zikhbi@ygoua-uuatllhmy.bi", pKnzAusgabe );
      startTest( pNr, "AuqiuitcpAohjykf-Fnmt@urshvybg.bzy", pKnzAusgabe );
      startTest( pNr, "Venwp.Genag@vvlxcocu.cvt", pKnzAusgabe );
      startTest( pNr, "QmgywrBinnnk@gurhrkizzre.qs", pKnzAusgabe );
      startTest( pNr, "TI@gwlzphdi.hbh", pKnzAusgabe );
      startTest( pNr, "WhoptKjdnfr@bdhxda.pk", pKnzAusgabe );
      startTest( pNr, "asxpdjajuvq@ofwyvgmb.sb", pKnzAusgabe );
      startTest( pNr, "UczeUgf.Stcqvokc@siVNK.qg", pKnzAusgabe );
      startTest( pNr, "Malby.Nqrke@wu.fugc.cxu.zchu.zns", pKnzAusgabe );
      startTest( pNr, "jvla.skbgajqk@d-oqyeazx.jd", pKnzAusgabe );
      startTest( pNr, "Qhozkkhycq.Vttrx@kjlatmzxyox.cp", pKnzAusgabe );

      startTest( pNr, "Mtwstii-Yowdozwi@pccgo-kvl.xrl", pKnzAusgabe );
      startTest( pNr, "Lndef-Wtzdur@ClhhgJvdozkh.qg", pKnzAusgabe );
      startTest( pNr, "Qaqzd-Jbpgoen@vajjkgdzqiw.cv", pKnzAusgabe );
      startTest( pNr, "I.Huajupwjnrg@sxewp-meaawjv.qz", pKnzAusgabe );
      startTest( pNr, "j.skthwtp@eezq.gnp", pKnzAusgabe );
      startTest( pNr, "Z-Wakcdpl@qpdjncjz.ez", pKnzAusgabe );
      startTest( pNr, "Icytd-Pznste@aywnzebo.juh", pKnzAusgabe );
      startTest( pNr, "Dfatll13@gghcmmay.zf", pKnzAusgabe );
      startTest( pNr, "Jjlx-Jjsmoi@Kurfn.jk", pKnzAusgabe );
      startTest( pNr, "UO@iug-rpibki.tz", pKnzAusgabe );
      startTest( pNr, "dszmlu.cfbsyx@QholJndEyqgm.bj", pKnzAusgabe );
      startTest( pNr, "fqsfp.xzjgtfnko@ddckh-usbdbyn.qiz", pKnzAusgabe );
      startTest( pNr, "Hxzum.Lbwvb@qavvcpp.kc", pKnzAusgabe );
      startTest( pNr, "UN@kekikkpm.rfe", pKnzAusgabe );
      startTest( pNr, "Vtxu.Hrs@VxxxBkhixmlb.vwz", pKnzAusgabe );
      startTest( pNr, "Kyhr.Xyuchdidmaqj@etiao.cb", pKnzAusgabe );
      startTest( pNr, "Qfpit2@yec.hpe", pKnzAusgabe );

      startTest( pNr, "Kqxlix-Fihecxiyj@OdtTkZfradDzbgbjOgZhbed.fi", pKnzAusgabe );
      startTest( pNr, "QK@WC.dq", pKnzAusgabe );
      startTest( pNr, "Beckkv.Oxlhrgfofx@uofgdlzt.he", pKnzAusgabe );
      startTest( pNr, "ObthaHcc@kdxwp.ky", pKnzAusgabe );
      startTest( pNr, "N.Qmklhqslu@vhhtrucqml.gbw", pKnzAusgabe );
      startTest( pNr, "SmqkdddRnwmfiin@fjioeme.ot", pKnzAusgabe );
      startTest( pNr, "zekcu@nfefliqt.if", pKnzAusgabe );
      startTest( pNr, "O.Kmuvu@yxnaka.qnulyd.pj", pKnzAusgabe );
      startTest( pNr, "iowof@wezeq.bwzxncu.nx", pKnzAusgabe );
      startTest( pNr, "Mmav54@fV.Kc.onc", pKnzAusgabe );
      startTest( pNr, "pmwdwzv.cmnad.xhdw@ljvhaot.eog", pKnzAusgabe );
      startTest( pNr, "Wlfmdc-Hkeyzvnhyfw@Zdvnsgbm20.zi", pKnzAusgabe );
      startTest( pNr, "y-wnqqc@gnxvbnAoq.may", pKnzAusgabe );
      startTest( pNr, "mfnr.wtcpj@KS.jo", pKnzAusgabe );
      startTest( pNr, "Royst.Ascxa@ogmll.uvw", pKnzAusgabe );
      startTest( pNr, "Yct-Ujpbpxd@ikzo.aner.xqyhla.zt", pKnzAusgabe );
      startTest( pNr, "Tenms.Ihvds@djotn.cwl", pKnzAusgabe );

      startTest( pNr, "maeuij-lfpgvcc@pqhvonrwju.bcm", pKnzAusgabe );
      startTest( pNr, "Arpdvm.aSxagvn@Ljly.tz", pKnzAusgabe );
      startTest( pNr, "Ehinzsr.Adoma@GbuxrEpCepsaxcNzidkgw.ti", pKnzAusgabe );
      startTest( pNr, "RiklhyPehjgpfgps@NsikciukiGpnh-Npgewulal.pg", pKnzAusgabe );
      startTest( pNr, "scurbrvtho@ipwx.hd", pKnzAusgabe );
      startTest( pNr, "IkmrmWof@xwyf.rog", pKnzAusgabe );
      startTest( pNr, "S.Guu@otrlvgbtvt.lf", pKnzAusgabe );
      startTest( pNr, "U.Xdykgt@gevqd.hq", pKnzAusgabe );
      startTest( pNr, "Yeov.Rlssc@0ZiLs.qdr", pKnzAusgabe );
      startTest( pNr, "Hjytcfaxjful31@ilawiaxukrnel.zk", pKnzAusgabe );
      startTest( pNr, "Pmkcxil04@fdmito.ao", pKnzAusgabe );
      startTest( pNr, "Dvmq.Qdh@fdgimmay.ec", pKnzAusgabe );
      startTest( pNr, "kjLboeee@fjpphb.tl", pKnzAusgabe );
      startTest( pNr, "Xexrivo.Lnexj@tnxm.lrz", pKnzAusgabe );
      startTest( pNr, "Xjlljxozg75@kskutjv.pi", pKnzAusgabe );
      startTest( pNr, "Pirnzo.Gjzjsoxd@bxdav-oor.cbi", pKnzAusgabe );
      startTest( pNr, "Dydp76@ccloig.qj", pKnzAusgabe );

      startTest( pNr, "Gzhsgi.Rqqb@Ukfc.xw", pKnzAusgabe );
      startTest( pNr, "A.Awkloas@OOGU.ky", pKnzAusgabe );
      startTest( pNr, "Pbrimmkezgjqqe@hlcoaovic.lcz", pKnzAusgabe );
      startTest( pNr, "olc.ywacbpvyyutsz.rhy@fnqqftollk.yj", pKnzAusgabe );
      startTest( pNr, "Lnd-Hsey@uoqqsiaurj.eny", pKnzAusgabe );
      startTest( pNr, "Xejkn.Rxzlav@kqgzxtp.iy", pKnzAusgabe );
      startTest( pNr, "Qlcx.Klmrjcbo@1iSCl.cy", pKnzAusgabe );
      startTest( pNr, "BPgqcnm-Ewftsmvw@vehjy-ego.efi", pKnzAusgabe );
      startTest( pNr, "azsfnb@EB3uH.aq", pKnzAusgabe );
      startTest( pNr, "hyyh-khyinmyxr@cozmnsfd.ag", pKnzAusgabe );
      startTest( pNr, "ciuip-zngtbclppkeucyqkqpcdgh@lypgyot.eh", pKnzAusgabe );
      startTest( pNr, "AfvbfokRnjbuz@rldvtlmyav.fpy", pKnzAusgabe );
      startTest( pNr, "Zmdcu.Pnxptuibk@tvngt-pkoek.az", pKnzAusgabe );
      startTest( pNr, "WgfnzTiscjs@btovto.pt", pKnzAusgabe );
      startTest( pNr, "h-uuuqu@kszhbdPfl.dph", pKnzAusgabe );
      startTest( pNr, "Fpweijpd.Hfdmr@ymeklbdpy.ut", pKnzAusgabe );
      startTest( pNr, "ZhreqQvj@eryl.czh", pKnzAusgabe );
      startTest( pNr, "w.rzntsd@npugmwso.he", pKnzAusgabe );
      startTest( pNr, "AL@zxw.crh", pKnzAusgabe );
      startTest( pNr, "Bmcci0@yoi.axy", pKnzAusgabe );
      startTest( pNr, "Qilbfzqmj51@yrtbhdk.lk", pKnzAusgabe );
      startTest( pNr, "P.Tbwnaezba@szqwrtjvng.esg", pKnzAusgabe );
      startTest( pNr, "Yrdtaen.Gugmt@goto.fmu", pKnzAusgabe );
      startTest( pNr, "nhvfqogegoa@upvlclnf.rk", pKnzAusgabe );
      startTest( pNr, "Gtro16@clefat.ld", pKnzAusgabe );
      startTest( pNr, "Jxtbafmigz.Ctgdj@epqpuiwwfob.yj", pKnzAusgabe );
      startTest( pNr, "RV@nmikpetz.sms", pKnzAusgabe );
      startTest( pNr, "Nxovd.Olxsu@kgftemm.nk", pKnzAusgabe );
      startTest( pNr, "Gclgb61@gegqegybsp.fqd", pKnzAusgabe );
      startTest( pNr, "gwhkjizqerwjy@sxynllgx.mz", pKnzAusgabe );
      startTest( pNr, "Cjyo.Nnjxuvbe@3coCl.ub", pKnzAusgabe );
      startTest( pNr, "Duaam.Llcnn@uq.qpqv.skg.gdmg.wr", pKnzAusgabe );
      startTest( pNr, "Gjtiqw-Ingqjr@jriki.sx", pKnzAusgabe );
      startTest( pNr, "Uacru.Uqmim@vqbts.wsa", pKnzAusgabe );
      startTest( pNr, "ZmdcuPnx@ptuib-ktvngtp.ko", pKnzAusgabe );
      startTest( pNr, "qqboe@lxqixxps.br", pKnzAusgabe );
      startTest( pNr, "fxzi.xwnsu@FV.gh", pKnzAusgabe );
      startTest( pNr, "KL@mkre.zcf", pKnzAusgabe );
      startTest( pNr, "vqr.cjh.zpsxg@jx.ehidhr.yf", pKnzAusgabe );
      startTest( pNr, "lewxfddoawzey@xm.bles.nzg.vriw.ex", pKnzAusgabe );
      startTest( pNr, "katkxfe.hskau.nmjc@vdcdfok.xco", pKnzAusgabe );
      startTest( pNr, "EOsyx.kiwmdbcnw@jdayzdsyx.rg", pKnzAusgabe );
      startTest( pNr, "K.Lietrdl@daqlveysukdut.wu", pKnzAusgabe );
      startTest( pNr, "Glokpie-Wrgiywoe@kxmhk-acz.gaj", pKnzAusgabe );
      startTest( pNr, "IninCgkvwd@lvs.zzm.gc", pKnzAusgabe );
      startTest( pNr, "Hvzz.Nhgjbcawjspb@gafwg.is", pKnzAusgabe );
      startTest( pNr, "owfki.nsqof@otrsvpfc.eu", pKnzAusgabe );
      startTest( pNr, "g.gpuwiu@wslmxi.vf", pKnzAusgabe );
      startTest( pNr, "Vfop.Ytjatnro@bfesq-nmi.juj", pKnzAusgabe );
      startTest( pNr, "Hyyhkh72@nmyxrcoz.mn", pKnzAusgabe );
      startTest( pNr, "C.Guaszvhgqflifkgp@olh.nyb", pKnzAusgabe );
      startTest( pNr, "Bmquybq.Zxuxc@AggzvPwIhzulplOztvefa.bi", pKnzAusgabe );
      startTest( pNr, "Ywt.xcqbl20@gjznjitr.ul", pKnzAusgabe );
      startTest( pNr, "T-Tvqqjdp@mvzmpmmb.gt", pKnzAusgabe );
      startTest( pNr, "Ktlii.Ebbqa@igltbkc.ru", pKnzAusgabe );
      startTest( pNr, "Fvefyjs.Jjpvauruwd@yjgamdXi.yz", pKnzAusgabe );
      startTest( pNr, "Vwuybugft-Nadgk@kcfbvvicwr.hzh", pKnzAusgabe );
      startTest( pNr, "M.Mmjeqks@ZCRV.zf", pKnzAusgabe );
      startTest( pNr, "Fhlxh.Kfpkt@joldblhl.rbg", pKnzAusgabe );
      startTest( pNr, "Pnyavn.Xkalyfhqoz@gcjjbgna.qb", pKnzAusgabe );
      startTest( pNr, "TVigyidd@LmxJhNxzxs.sc", pKnzAusgabe );
      startTest( pNr, "P.Jphdm@NdlMdSmggfUdppmkSoTmyng.fm", pKnzAusgabe );
      startTest( pNr, "Orgu-Onirqk@5sDaW.ut", pKnzAusgabe );
      startTest( pNr, "WF@bhbflbbo.uhz", pKnzAusgabe );
      startTest( pNr, "Tepqkiykz-Gnxrqfp@wsnnuwcnr.cjj", pKnzAusgabe );
      startTest( pNr, "Eitidkyx42@fbzdjhoxyl.yov", pKnzAusgabe );
      startTest( pNr, "Oand-Aodxee@Ihmcd.ks", pKnzAusgabe );
      startTest( pNr, "Dqfpqy76.qnwjwjv.yxcitsevifdw@pimazefg.cm", pKnzAusgabe );
      startTest( pNr, "Iwqw.Pgf@bkxbjcbg.df", pKnzAusgabe );
      startTest( pNr, "A.Worso@plyplg.poenwt.qa", pKnzAusgabe );
      startTest( pNr, "LewxFdd.Oawzeyxm@blESN.zg", pKnzAusgabe );
      startTest( pNr, "jsul-gfcjhf.htskki@epoco.mu", pKnzAusgabe );
      startTest( pNr, "Yledg10@HvlRS.rv", pKnzAusgabe );
      startTest( pNr, "wovzy-fowbpk@suwlli.hg", pKnzAusgabe );
      startTest( pNr, "IM@rqbh.ldn", pKnzAusgabe );
      startTest( pNr, "qoglf-ybhx@HxifFvzIipoh.ib", pKnzAusgabe );
      startTest( pNr, "Ccmha.zka@Rrkx.nv", pKnzAusgabe );
      startTest( pNr, "Dtjdl33@KE.rs", pKnzAusgabe );
      startTest( pNr, "Jhfr35@eF.Wo.rbk", pKnzAusgabe );
      startTest( pNr, "Yqyndq.Qqslozlb@pqotr-hhx.jsl", pKnzAusgabe );
      startTest( pNr, "Qxjptm07@khpdvuzr.pp", pKnzAusgabe );
      startTest( pNr, "Djyobyu.Nodevt@5nfFP.bqh", pKnzAusgabe );
      startTest( pNr, "UZ@ylwhuvhs.xxh", pKnzAusgabe );
      startTest( pNr, "bkhzj-ncfx@KxsyCxzSgigk.wm", pKnzAusgabe );
      startTest( pNr, "Z.Xfogce@modkd.yj", pKnzAusgabe );
      startTest( pNr, "jvyzbsrzvjdmt@goeave.ru", pKnzAusgabe );
      startTest( pNr, "Jvhltgmthifw40@ygxrfktrsjoep.to", pKnzAusgabe );
      startTest( pNr, "Hecdc.Ufyocs@rzist-ohxcfmzfz.hr", pKnzAusgabe );
      startTest( pNr, "HUuuquk@szhb.dp", pKnzAusgabe );
      startTest( pNr, "Dpbksl.Bdpsyp@cwRZH.jf", pKnzAusgabe );
      startTest( pNr, "Btkdx.Bbukk@PmorArana.mk", pKnzAusgabe );
      startTest( pNr, "jebdq@fpoij.vhjwrwi.cw", pKnzAusgabe );
      startTest( pNr, "Ilnkd.Tpmlnlogc@dnrhh-foaflbe.gz", pKnzAusgabe );
      startTest( pNr, "Jmeheto16@jncymu.oq", pKnzAusgabe );
      startTest( pNr, "Tyho.Uiv@dxwvjoxx.xd", pKnzAusgabe );
      startTest( pNr, "yaxlx.dpbuhddbjnep@xlzq.sn", pKnzAusgabe );
      startTest( pNr, "M-Upntdji@lcjq.pv", pKnzAusgabe );
      startTest( pNr, "kdjfc.rbmsvwkabzyd@vptcu-eqtlbaq.qz", pKnzAusgabe );
      startTest( pNr, "Igou.Jtfnmt@porje.rf", pKnzAusgabe );
      startTest( pNr, "ikjef-gsvjtn.vlpzpap@dzfyo.oj", pKnzAusgabe );
      startTest( pNr, "Ionsat.Hlxy@Jpnm.dl", pKnzAusgabe );
      startTest( pNr, "rbqi-zxvu@tonhas.go", pKnzAusgabe );
      startTest( pNr, "ixucn.ucsmszdwo@kbvvp-zuyqaog.crs", pKnzAusgabe );
      startTest( pNr, "kafshemweqa@qzfvchpy.fx", pKnzAusgabe );
      startTest( pNr, "Qtaw.Blpqu@ivqzkrw.cq", pKnzAusgabe );
      startTest( pNr, "TcpinIgk@nvqxkhfrqe.zex", pKnzAusgabe );
      startTest( pNr, "Njunn-Oydsyyv@wflaqasahod.gk", pKnzAusgabe );
      startTest( pNr, "ZS@gyMcu.np", pKnzAusgabe );
      startTest( pNr, "n.vonhoty@fkm.gyj", pKnzAusgabe );
      startTest( pNr, "Eosyx.kiw@mdb.cn", pKnzAusgabe );
      startTest( pNr, "Lstl.Uqnzam@kJ0y3lx.kn", pKnzAusgabe );
      startTest( pNr, "RqxigyazgdYwyonag@kwnmwixejj.jgf", pKnzAusgabe );
      startTest( pNr, "Pptduz.tSlbxmu@Mjzw.wt", pKnzAusgabe );
      startTest( pNr, "qdml.lzgnkwjx@d-aclrjks.jy", pKnzAusgabe );
      startTest( pNr, "FZ@IT.sx", pKnzAusgabe );
      startTest( pNr, "azsf.nbebmuha@q-lijtzhp.wo", pKnzAusgabe );
      startTest( pNr, "EguaglgXayib@sukj.ijk", pKnzAusgabe );
      startTest( pNr, "Aockr-Pfxbds@IitttTtgyhgs.co", pKnzAusgabe );
      startTest( pNr, "r.eqqwxnuf@ykh.hcg", pKnzAusgabe );
      startTest( pNr, "Ljatx.Qkeal@uyhdqvny.wkc", pKnzAusgabe );
      startTest( pNr, "Jzcnwgf-Pjozgvd@sawb.vfa", pKnzAusgabe );
      startTest( pNr, "Iakyrgyxm-Dvtjd@axdncprekt.ecp", pKnzAusgabe );
      startTest( pNr, "Aqt-Hsoepky@twbd.kvsv.nwntza.uz", pKnzAusgabe );
      startTest( pNr, "Oncgta-Uacfwzbdsxx@Umokiexh00.ft", pKnzAusgabe );
      startTest( pNr, "EOxeuyen@NyaViJnbqqCibaooXcOzork.ar", pKnzAusgabe );
      startTest( pNr, "Ptbwn.Aezba@sz.qwrt.jvn.gesg.ijb", pKnzAusgabe );
      startTest( pNr, "KUpofsyrs.Vgzfggitkygccr@qjndhnxq.cn", pKnzAusgabe );
      startTest( pNr, "Vpmwl.Tdyrdipdrdk@Nposx.on", pKnzAusgabe );
      startTest( pNr, "mukivp.mrkilw@UibnWmuWhyhx.sh", pKnzAusgabe );
      startTest( pNr, "i-fkolgi@Wlyrux60.tp", pKnzAusgabe );
      startTest( pNr, "XpmwzenOkklfnfc@ngknnlm.tr", pKnzAusgabe );
      startTest( pNr, "Sahx.Exc@MqzqUmsxspyt.zsc", pKnzAusgabe );
      startTest( pNr, "i.fkolgiw@lyruxwa.tp", pKnzAusgabe );
      startTest( pNr, "Zmhi.Sck@oqnscvx.xu", pKnzAusgabe );
      startTest( pNr, "s.dhfbxum@cjou.tbx", pKnzAusgabe );
      startTest( pNr, "Ynva.Idcze@2SfKf.lus", pKnzAusgabe );
      startTest( pNr, "kfsarf-ogrfwub@fiygedvzxm.ifo", pKnzAusgabe );
      startTest( pNr, "Ywtxc50@LgcGJ.zn", pKnzAusgabe );
      startTest( pNr, "Xjiahr.Hykvrl@fzyteqj.qia", pKnzAusgabe );
      startTest( pNr, "p-pkrcmx@Yycvca50.xy", pKnzAusgabe );
      startTest( pNr, "Mzamxc-Bwwdfn@ogqhk.ru", pKnzAusgabe );
      startTest( pNr, "h.ayqhhqi@axv.xyl", pKnzAusgabe );
      startTest( pNr, "cqThgzpf@egwmlr.jy", pKnzAusgabe );
      startTest( pNr, "PpxisNpj@nvvfo.zn", pKnzAusgabe );
      startTest( pNr, "wlrl.dsa@gnko.ytz", pKnzAusgabe );
      startTest( pNr, "Huuuqu-Kszhbdpfl@DphCqXgciaSmxjvcQrQrcse.ei", pKnzAusgabe );
      startTest( pNr, "Peqrj-Mcdmrh@ryngjico.xsa", pKnzAusgabe );
      startTest( pNr, "YI@apm-qorhgv.yq", pKnzAusgabe );
      startTest( pNr, "KcfaeEew@qbyrgov.am", pKnzAusgabe );
      startTest( pNr, "Hvmrd.Iyupv@aayad.buo", pKnzAusgabe );
      startTest( pNr, "CdvsvzdHughkb@wlijbxyqgnrbgbxg.lgf", pKnzAusgabe );
      startTest( pNr, "Sueb.Mjwxq@kwz-dmu.yn", pKnzAusgabe );
      startTest( pNr, "WledttSrbeocihvp@AquzissazKttl-Mjkentzlx.pa", pKnzAusgabe );
      startTest( pNr, "Jnvv.Mfsji@klse.rlm", pKnzAusgabe );
      startTest( pNr, "Jmrpos65@gpny.ke", pKnzAusgabe );
      startTest( pNr, "Wzxlj.Sflaza@nrvnbvcmec.ug", pKnzAusgabe );
      startTest( pNr, "Bezytnzyw@aFE62.pg", pKnzAusgabe );
      startTest( pNr, "TcpinigKnvqx@khfr.qez", pKnzAusgabe );
      startTest( pNr, "vetwsuiees@uebm.jw", pKnzAusgabe );
      startTest( pNr, "U.Cyu@thmvtcuxoq.ow", pKnzAusgabe );
      startTest( pNr, "Ldsriqhx-Ycbkeki@dumphqeeu.rwa", pKnzAusgabe );
      startTest( pNr, "Yiapmqorh@gvyqzqwx.ed", pKnzAusgabe );
      startTest( pNr, "jg-blnfh@ouuthqg.ru", pKnzAusgabe );
      startTest( pNr, "mtgc.xcqa@inndavwoftr.qz", pKnzAusgabe );
      startTest( pNr, "Jebdqfpoi26@hjwrwicwxu.kfk", pKnzAusgabe );
      startTest( pNr, "IgoujTfnmtp@orjerfgg.nu", pKnzAusgabe );
      startTest( pNr, "Alzxwcrh47@wphxpxxnpj.bmn", pKnzAusgabe );
      startTest( pNr, "Qdmll.Zgnkw@Jxdacl.rj", pKnzAusgabe );
      startTest( pNr, "Z.Vkmhpiednkw@lmamm-goatubg.zo", pKnzAusgabe );
      startTest( pNr, "JpvdjfKjuujs@lakkybatrug.cx", pKnzAusgabe );
      startTest( pNr, "mukivpmrki.lwuibnwmuw@hyhxshv.rr", pKnzAusgabe );
      startTest( pNr, "Yuyuddf-Cvmhdmq@niew.khw", pKnzAusgabe );
      startTest( pNr, "Katkx.Fehskaunm@jcvdcd.fok", pKnzAusgabe );
      startTest( pNr, "olhdu.nysrpwsphxyi@ackw.sg", pKnzAusgabe );
      startTest( pNr, "qtawblpquivqzkrwcqm@bsqmnjwelz.ef", pKnzAusgabe );
      startTest( pNr, "BK@dul.ago", pKnzAusgabe );
      startTest( pNr, "wifvwtarpt.qbbxvrucgq@laeilwd.zck", pKnzAusgabe );
      startTest( pNr, "ClywknBxhgzf@rloaripepkt.rj", pKnzAusgabe );
      startTest( pNr, "f.nbbjwtu@wpdfebr.ev", pKnzAusgabe );
      startTest( pNr, "Oplr.Pbni@Fusm.qd", pKnzAusgabe );
      startTest( pNr, "LbwpidlonMcdrond-Frfe@cohztjod.nxb", pKnzAusgabe );
      startTest( pNr, "u-isdsh@xqhsn.hc", pKnzAusgabe );

    }
    catch ( Exception err_inst )
    {
      wl( "Fehler: errTest ", err_inst );
    }
  }

  private static int startTest( int pNr, String pEingabe, int pKnzAusgabe )
  {
    int x_erg = -1;

    try
    {
      if ( pNr == 1 )
      {
        //x_erg = isValidEmailAddresseRegEx1b( pEingabe ) ? 1 : 0;
        //x_erg = checkJMail( pEingabe ) ? 1 : 0;
        x_erg = checkEmail( pEingabe ) ? 1 : 0;
      }
      else
      {
        x_erg = checkEMailAdresse( pEingabe ) == 0 ? 1 : 0;
      }
    }
    catch ( Exception err_inst )
    {
      //wl( "Fehler: errTest ", err_inst );
    }

    if ( pKnzAusgabe == 1 )
    {
      wl( pNr + " " + ( pNr == 1 ? STR_BEZ_ALT : STR_BEZ_NEU ) + " " + FkString.getFeldLinksMin( ( pEingabe == null ? "null" : "\"" + ( pEingabe.length() > 70 ? pEingabe.substring( 0, 70 ) + "..." : pEingabe ) + "\"" ), 20 ) + " = " + x_erg );
    }

    return x_erg;
  }

  private static String  m_stricter_filter_string = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";

  private static String  m_static_laxString       = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";

  private static Pattern m_pattern                = null;

  private static boolean isValidEmailAddresseRegEx1a( String email )
  {
    /*
     * matt.writes.code
     * https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     * 
     * Pattern wird immer neu erstellt.
     */
    Pattern p = Pattern.compile( m_stricter_filter_string );

    Matcher m = p.matcher( email );

    return m.matches();
  }

  private static boolean isValidEmailAddresseRegEx1b( String enteredEmail )
  {
    if ( m_pattern == null )
    {
      m_pattern = Pattern.compile( m_static_laxString );
    }

    return m_pattern.matcher( enteredEmail ).matches();
  }

  
//  private static boolean checkJMail( String pInput ) 
//  {
//    if ( JMail.isValid( pInput ) )
//    {
//      return true;
//    }
//
//    return false;
//  }

  private static final String LETTERS_NUMBERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345678";

  private static final String EMAIL_BACK      = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345678.-";

  private static final String EMAIL_ALL       = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345678.-%&*+-/=?_";

  private static boolean checkEmail( String pEMail )
  {
    int len_eingabe = pEMail.length();
    int akt_position = 0;
    String teil_str;

    if ( ( pEMail.indexOf( "@" ) == -1 ) || ( pEMail.indexOf( "." ) == -1 ) || ( pEMail.indexOf( " " ) != -1 ) || ( pEMail.endsWith( "." ) ) || ( pEMail.startsWith( "." ) ) )
    {
      return false;
    }

    if ( pEMail.indexOf( ".." ) > 0 )
    {
      return false;
    }

    akt_position = pEMail.indexOf( "@" );

    teil_str = pEMail.substring( 0, akt_position );

    len_eingabe = teil_str.length();

    if ( ( teil_str.startsWith( "\"" ) ) && ( teil_str.endsWith( ".\"" ) ) )
    {
      String teil_str_2 = teil_str.substring( 1, len_eingabe - 2 );

      int len = teil_str_2.length();

      for ( ; len > 0; len-- )
      {
        akt_position = LETTERS_NUMBERS.indexOf( teil_str_2.charAt( len - 1 ) );

        if ( akt_position < 0 )
        {
          return false;
        }
      }
    }
    else
    {
      if ( teil_str.endsWith( "." ) )
      {
        return false;
      }

      for ( ; len_eingabe > 0; len_eingabe-- )
      {
        akt_position = EMAIL_ALL.indexOf( teil_str.charAt( len_eingabe - 1 ) );

        if ( akt_position < 0 )
        {
          return false;
        }
      }
    }

    akt_position = pEMail.indexOf( "@" );

    teil_str = pEMail.substring( akt_position + 1 );

    len_eingabe = teil_str.length();

    if ( ( teil_str.indexOf( "." ) == -1 ) || ( teil_str.startsWith( "." ) ) )
    {
      return false;
    }

    for ( ; len_eingabe > 0; len_eingabe-- )
    {
      akt_position = EMAIL_BACK.indexOf( teil_str.charAt( len_eingabe - 1 ) );

      if ( akt_position < 0 )
      {
        return false;
      }
    }

    return true;
  }

  /**
   * <pre>
   * Validierung einer eMail-Adresse.
   * </pre>
   * 
   * @param pEingabe die auf eine eMail-Adresse zu pruefende Eingabe
   * @return 0 wenn die Eingabe nach der Struktur her eine eMail-Adresse ergeben kann, ansonsten eine der oben genannten Rueckgabewerte 
   */
  private static int checkEMailAdresseAltVersion1( String pEingabe )
  {
    if ( pEingabe == null ) return 1; // Eingabe ist null

    if ( pEingabe.length() == 0 ) return 1; // Eingabe ist Leerstring

    String pruef_str = pEingabe.trim();

    int len_pruef_str = pruef_str.length();

    if ( ( len_pruef_str < 6 ) || ( len_pruef_str > 254 ) ) return 2; // Laengenbegrenzungen

    int pos_at_zeichen = -1;

    int letzter_punkt = -1;

    int zeichen_zaehler = 0;

    // int zahlen_zaehler = 0;

    int akt_index = 0;

    while ( akt_index < len_pruef_str )
    {
      char aktuelles_zeichen = pruef_str.charAt( akt_index );

      if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) )
      {
        zeichen_zaehler++;
      }
      else if ( ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) || ( aktuelles_zeichen == '_' ) || ( aktuelles_zeichen == '-' ) )
      {
        if ( zeichen_zaehler == 0 ) return 3; // Zahl oder Sonderzeichen nur nach einem Zeichen aus dem Alphabeth (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)

        //zahlen_zaehler++;
      }
      else if ( aktuelles_zeichen == '.' )
      {
        if ( letzter_punkt == -1 )
        {
          if ( akt_index == 0 ) return 5; // kein Beginn mit einem Punkt
        }
        else
        {
          if ( akt_index - letzter_punkt == 1 ) return 4; // keine zwei Punkte hintereinander
        }

        letzter_punkt = akt_index;

        zeichen_zaehler = 0;

        //zahlen_zaehler = 0;
      }
      else if ( aktuelles_zeichen == '@' )
      {
        if ( pos_at_zeichen == -1 )
        {
          if ( akt_index == 0 ) return 6; // kein AT-Zeichen am Anfang

          if ( akt_index > 64 ) return 7; // RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes

          if ( ( akt_index + 1 ) == len_pruef_str ) return 8; // kein AT-Zeichen am Ende

          if ( akt_index - letzter_punkt == 1 ) return 10; // ungueltige Zeichenkombination ".@"

          if ( pruef_str.charAt( akt_index + 1 ) == '.' ) return 9; // ungueltige Zeichenkombination "@."

          pos_at_zeichen = akt_index;

          zeichen_zaehler = 0;
        }
        else
        {
          return 11; // kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
        }
      }
      else
      {
        return 12; // ungueltiges Zeichen in der Eingabe gefunden 
      }

      akt_index++;
    }

    if ( letzter_punkt == -1 ) return 13; // keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)

    if ( pos_at_zeichen == -1 ) return 14; // kein AT-Zeichen gefunden

    if ( letzter_punkt < pos_at_zeichen ) return 15; // der letzte Punkt muss nach dem AT-Zeichen liegen (... hier eben die negative Form, wenn der letzte Punkt vor dem AT-Zeichen stand ist es ein Fehler)

    if ( ( letzter_punkt + 1 ) == len_pruef_str ) return 16; // der letzte Punkt darf nicht am Ende liegen

    if ( ( letzter_punkt + 1 ) > ( len_pruef_str - 2 ) ) return 17; // Top-Level-Domain muss mindestens 2 Stellen lang sein.

    if ( ( len_pruef_str - letzter_punkt ) > 10 ) return 18; // Top-Level-Domain darf nicht mehr als 10 Stellen lang sein.

    //if ( zahlen_zaehler > 0 ) return 19; // Top-Level-Domain darf keine Zahlen haben ... oder doch

    return 0;
  }

  /**
   * <pre>
   * Validierung einer eMail-Adresse.
   * 
   * Auf den Eingabeparameter wird kein TRIM gemacht. 
   * 
   * Rueckgabewerte kleiner 10 sind OK.
   * 
   * Ein Rueckgabe von 0, ist eine eMail-Adresse ohne weitere Besonderheiten
   * (... 0 kann nun auch eine eMail-Angabe in spitzen Klammern sein)
   * 
   * Rueckgabewerte von 1 bis 9, sind eMail-Adressen mit Sonderangaben String, Kommentar oder IP-Adresse.
   * 
   * Folgende Rueckgaben sind moeglich:
   * 
   *     0 = eMail-Adresse korrekt
   *     1 = eMail-Adresse korrekt (Local Part mit String)
   *     2 = eMail-Adresse korrekt (IP4-Adresse)
   *     3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *     4 = eMail-Adresse korrekt (IP6-Adresse)
   *     5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *     6 = eMail-Adresse korrekt (Kommentar)
   *     7 = eMail-Adresse korrekt (Kommentar, String)
   *     8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *     9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *         
   *    
   *    10 Laenge: Eingabe ist null
   *    11 Laenge: Eingabe ist Leerstring
   *    12 Laenge: Laengenbegrenzungen stimmen nicht
   *    13 Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *    14 Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *    15 Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
   *    
   *    16 Struktur: keine oeffnende eckige Klammer gefunden.
   *    17 Struktur: keine schliessende eckige Klammer gefunden.
   *    18 Struktur: Fehler in Adress-String-X
   *    
   *    20 Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    21 Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *    22 Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    23 Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *    24 Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    
   *    26 AT-Zeichen: kein AT-Zeichen am Anfang
   *    27 AT-Zeichen: kein AT-Zeichen am Ende
   *    28 AT-Zeichen: kein AT-Zeichen gefunden
   *    29 AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    
   *    30 Trennzeichen: kein Beginn mit einem Punkt
   *    31 Trennzeichen: keine zwei Punkte hintereinander
   *    32 Trennzeichen: ungueltige Zeichenkombination ".@"
   *    33 Trennzeichen: ungueltige Zeichenkombination "@."
   *    34 Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    35 Trennzeichen: der letzte Punkt muss nach dem AT-Zeichen liegen
   *    36 Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *    
   * Domain-Name mit IP-Adresse:
   * 
   *    40 IP6-Adressteil: String "IPv6:" erwartet
   *    41 IP6-Adressteil: Trennzeichenanzahl ist 0
   *    42 IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *    43 IP6-Adressteil: Zu wenig Trennzeichen
   *    44 IP6-Adressteil: ungueltige Kombination ":]"
   *    45 IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *    46 IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *    47 IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *    48 IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *    49 IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *    50 IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *    51 IP-Adressteil: IP-Adresse vor AT-Zeichen
   *    52 IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *    53 IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *    54 IP4-Adressteil: Byte-Overflow
   *    55 IP4-Adressteil: keine Ziffern vorhanden
   *    56 IP4-Adressteil: zu viele Trennzeichen
   *    57 IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    58 IP4-Adressteil: ungueltige Kombination ".]"
   *    59 IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *    60 IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *    61 IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *        
   * Local-Part mit Anfuehrungszeichen 
   *        
   *    80 String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *    81 String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    82 String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    83 String: Escape-Zeichen nicht am Ende der Eingabe
   *    84 String: Ungueltige Escape-Sequenz im String
   *    85 String: Leerstring in Anfuehrungszeichen
   *    86 String: kein abschliessendes Anfuehrungszeichen gefunden.
   *    87 String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    88 String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *    89 String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *    
   * Kommentare
   * 
   *    91 Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *    92 Kommentar: Ungueltiges Zeichen im Kommentar
   *    93 Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *    94 Kommentar: kein Kommentar nach dem AT-Zeichen
   *    95 Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *    96 Kommentar: Escape-Zeichen nicht am Ende der Eingabe
   *    97 Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *    98 Kommentar: Kein lokaler Part vorhanden
   *    99 Kommentar: kein zweiter Kommentar gueltig
   *    
   *    
   * FkEMail.checkEMailAdresse( "A.B@C.DE"                       ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "A."B"@C.DE"                     ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * 
   * FkEMail.checkEMailAdresse( "A.B@[1.2.3.4]"                  ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "A."B"@[1.2.3.4]"                ) =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "A.B@[IPv6:1:2:3:4:5:6:7:8]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "A."B"@[IPv6:1:2:3:4:5:6:7:8]"   ) =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)B@C.DE"                      ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "A(B)@C.DE"                      ) =  6 = eMail-Adresse korrekt (Kommentar)
   * 
   * FkEMail.checkEMailAdresse( "(A)"B"@C.DE"                    ) =  7 = eMail-Adresse korrekt (Kommentar, String)
   * FkEMail.checkEMailAdresse( ""A"(B)@C.DE"                    ) =  7 = eMail-Adresse korrekt (Kommentar, String)
   * 
   * FkEMail.checkEMailAdresse( "(A)B@[1.2.3.4]"                 ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "A(B)@[1.2.3.4]"                 ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)"B"@[1.2.3.4]"               ) =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   * FkEMail.checkEMailAdresse( ""A"(B)@[1.2.3.4]"               ) =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)B@[IPv6:1:2:3:4:5:6:7:8]"    ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "A(B)@[IPv6:1:2:3:4:5:6:7:8]"    ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "(A)"B"@[IPv6:1:2:3:4:5:6:7:8]"  ) =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   * FkEMail.checkEMailAdresse( ""A"(B)@[IPv6:1:2:3:4:5:6:7:8]"  ) =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   * 
   * 
   * Grundlegendes ----------------------------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "A@B.CD"                         ) =  0 = eMail-Adresse korrekt (Minimal gueltige eMail-Adresslaenge)
   * FkEMail.checkEMailAdresse( "ABC1.DEF2@GHI3.JKL4"            ) =  0 = eMail-Adresse korrekt 
   * FkEMail.checkEMailAdresse( "ABC.DEF_@GHI.JKL"               ) =  0 = eMail-Adresse korrekt (Underscore vor AT-Zeichen)
   * FkEMail.checkEMailAdresse( "#ABC.DEF@GHI.JKL"               ) =  0 = eMail-Adresse korrekt (Beginn mit #)
   * FkEMail.checkEMailAdresse( null                             ) = 10 = Laenge: Eingabe ist null
   * FkEMail.checkEMailAdresse( " "                              ) = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   * FkEMail.checkEMailAdresse( "                "               ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "ABCDEFGHIJKLMNOP"               ) = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.J"                  ) = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   * FkEMail.checkEMailAdresse( "ME@MYSELF.LOCALHOST"            ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "ME@MYSELF.LOCALHORST"           ) = 15 = Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.2KL"                ) = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JK-"                ) = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JK_"                ) = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JK2"                ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "ABC.DEF@2HI.JKL"                ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "ABC.DEF@-HI.JKL"                ) = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   * FkEMail.checkEMailAdresse( "ABC.DEF@_HI.JKL"                ) = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   * 
   * FkEMail.checkEMailAdresse( "A . B & C . D"                  ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "(?).[!]@{&}.<:>"                ) = 30 = Trennzeichen: kein Beginn mit einem Punkt
   * FkEMail.checkEMailAdresse( ".ABC.DEF@GHI.JKL"               ) = 30 = Trennzeichen: kein Beginn mit einem Punkt
   * FkEMail.checkEMailAdresse( "ABC..DEF@GHI.JKL"               ) = 31 = Trennzeichen: keine zwei Punkte hintereinander
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI..JKL"               ) = 31 = Trennzeichen: keine zwei Punkte hintereinander
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JKL.."              ) = 31 = Trennzeichen: keine zwei Punkte hintereinander
   * FkEMail.checkEMailAdresse( "ABC.DEF.@GHI.JKL"               ) = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   * FkEMail.checkEMailAdresse( "ABC.DEF@."                      ) = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   * FkEMail.checkEMailAdresse( "ABC.DEF@.GHI.JKL"               ) = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   * FkEMail.checkEMailAdresse( "ABCDEF@GHIJKL"                  ) = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JKL."               ) = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   * FkEMail.checkEMailAdresse( "@GHI.JKL"                       ) = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   * FkEMail.checkEMailAdresse( "ABC.DEF@"                       ) = 27 = AT-Zeichen: kein AT-Zeichen am Ende
   * FkEMail.checkEMailAdresse( "ABC.DEF\@"                      ) = 28 = AT-Zeichen: kein AT-Zeichen gefunden (... da hier das AT-Zeichen maskiert ist)
   * FkEMail.checkEMailAdresse( "ABC.DEF@@GHI.JKL"               ) = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   * FkEMail.checkEMailAdresse( "ABC\@DEF@GHI.JKL"               ) =  0 = eMail-Adresse korrekt (erstes AT-Zeichen im lokal Part wurde maskiert)
   * 
   * Local-Part mit Anfuehrungszeichen --------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( ""ABC.DEF"@GHI.DE"               ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( ""ABC DEF"@GHI.DE"               ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( "ABC DEF@GHI.DE"                 ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( ""ABC@DEF"@GHI.DE"               ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( ""ABC DEF@G"HI.DE"               ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( """@GHI.DE"                      ) = 85 = String: Leerstring in Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( ""ABC.DEF@G"HI.DE"               ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( "A@G"HI.DE"                      ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( ""@GHI.DE"                       ) = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   * FkEMail.checkEMailAdresse( "ABC.DEF.""                      ) = 85 = String: Leerstring in Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@"""                     ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@G"HI.DE"                ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.DE""                ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@"GHI.DE"                ) = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( ""Escape.Sequenz.Ende\"          ) = 83 = String: Escape-Zeichen nicht am Ende der Eingabe
   * FkEMail.checkEMailAdresse( "ABC.DEF"GHI.DE"                 ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( "ABC.DEF"@GHI.DE"                ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( "ABC.DE"F@GHI.DE"                ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( ""ABC.DEF@GHI.DE"                ) = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   * FkEMail.checkEMailAdresse( ""ABC.DEF@GHI.DE""               ) = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   * 
   * FkEMail.checkEMailAdresse( "".ABC.DEF"@GHI.DE"              ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( ""ABC.DEF."@GHI.DE"              ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * 
   * FkEMail.checkEMailAdresse( ""ABC".DEF."GHI"@JKL.de"         ) =  1 = eMail-Adresse korrekt (Local Part mit String)
   * FkEMail.checkEMailAdresse( "A"BC".DEF."GHI"@JKL.de"         ) = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   * FkEMail.checkEMailAdresse( ""ABC".DEF.G"HI"@JKL.de"         ) = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   * FkEMail.checkEMailAdresse( ""AB"C.DEF."GHI"@JKL.de"         ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( ""ABC".DEF."GHI"J@KL.de"         ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( ""AB"C.D"EF"@GHI.DE"             ) = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   * FkEMail.checkEMailAdresse( ""Ende.am.Eingabeende""          ) = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   * FkEMail.checkEMailAdresse( "0"00.000"@GHI.JKL"              ) = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   * 
   * Domain-Name mit IP-Adresse ---------------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4]"              ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[001.002.003.004]"      ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( ""ABC.DEF"@[127.0.0.1]"          ) =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF[1.2.3.4]"               ) = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   * FkEMail.checkEMailAdresse( "[1.2.3.4]@[5.6.7.8]"            ) = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF[@1.2.3.4]"              ) = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   * FkEMail.checkEMailAdresse( ""[1.2.3.4]"@[5.6.7.8]"          ) = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@MyDomain[1.2.3.4]"      ) = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.00002.3.4]"          ) = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.456]"            ) = 54 = IP4-Adressteil: Byte-Overflow
   * FkEMail.checkEMailAdresse( "ABC.DEF@[..]"                   ) = 55 = IP4-Adressteil: keine Ziffern vorhanden
   * FkEMail.checkEMailAdresse( "ABC.DEF@[.2.3.4]"               ) = 55 = IP4-Adressteil: keine Ziffern vorhanden
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[]"                     ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1]"                    ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2]"                  ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3]"                ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4]"              ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4.5]"            ) = 56 = IP4-Adressteil: zu viele Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4.5.6]"          ) = 56 = IP4-Adressteil: zu viele Trennzeichen
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[MyDomain.de]"          ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.]"               ) = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3. ]"              ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4].de"           ) = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC.DE@[1.2.3.4][5.6.7.8]"      ) = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4"               ) = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   * FkEMail.checkEMailAdresse( "ABC.DEF@1.2.3.4]"               ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:2001:db8::1]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * 
   * FkEMail.checkEMailAdresse( "ABC@[IP"                        ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC@[IPv6]"                     ) = 40 = IP6-Adressteil: String "IPv6:" erwartet
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:]"                    ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1]"                   ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2]"                 ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3]"               ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4]"             ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:]"          ) = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6]"         ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6:7]"       ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6:7:8]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC@[IPv6:1:2:3:4:5:6:7:8:9]"   ) = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   * 
   * FkEMail.checkEMailAdresse( "ABC@[IPv4:1:2:3:4]"             ) = 40 = IP6-Adressteil: String "IPv6:" erwartet
   * FkEMail.checkEMailAdresse( "ABC@[I127.0.0.1]"               ) = 40 = IP6-Adressteil: String "IPv6:" erwartet
   * FkEMail.checkEMailAdresse( "ABC@[D127.0.0.1]"               ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC@[iPv6:2001:db8::1]"         ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   *                                                                      Eine IPv6 Adresse muss mit einem grossem "I" eingeleitet werden
   *                                                                      Es wurde hier keine IPv6-Adresse erkannt, daher wird versucht eine IPv4-Adresse zu lesen.
   *  
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"  ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3::5::7:8]"   ) = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6::ffff:.127.0.1]"  ) = 55 = IP4-Adressteil: keine Ziffern vorhanden (erste Ziffer IPv4-Adresse fehlt)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6::FFFF:127.0.0.1]" ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6::ffff:127.0.0.1]" ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6::fff:127.0.0.1]"  ) = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6::1234:127.0.0.1]" ) = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:127.0.0.1]"       ) = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:::127.0.0.1]"     ) = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4]"              ) =  2 = eMail-Adresse korrekt (IP4-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.Z]"              ) = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@[12.34]"                ) = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.]"               ) = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4] "             ) = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2.3.4"               ) = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1234.5.6.7]"           ) = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   * FkEMail.checkEMailAdresse( "ABC.DEF@[1.2...3.4]"            ) = 55 = IP4-Adressteil: keine Ziffern vorhanden
   * 
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3:4:5:6]"     ) =  4 = eMail-Adresse korrekt (IP6-Adresse)
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3:4:5:Z]"     ) = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:12:34]"           ) = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3:4:5:]"      ) = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3:4:5:6] "    ) = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3:4:5:6"      ) = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:12345:6:7:8:9]"   ) = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   * FkEMail.checkEMailAdresse( "ABC.DEF@[IPv6:1:2:3:::6:7:8]"   ) = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   * 
   * 
   * Kommentare -------------------------------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "(ABC)DEF@GHI.JKL"               ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "ABC(DEF)@GHI.JKL"               ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "AB(CD)EF@GHI.JKL"               ) = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   * FkEMail.checkEMailAdresse( "AB.(CD).EF@GHI.JKL"             ) = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   * FkEMail.checkEMailAdresse( "AB."(CD)".EF@GHI.JKL"           ) = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( "(ABCDEF)@GHI.JKL"               ) = 98 = Kommentar: Kein lokaler Part vorhanden
   * FkEMail.checkEMailAdresse( "(ABCDEF).@GHI.JKL"              ) = 30 = Trennzeichen: kein Beginn mit einem Punkt
   * FkEMail.checkEMailAdresse( "(AB\"C)DEF@GHI.JKL"             ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "(AB\\C)DEF@GHI.JKL"             ) =  6 = eMail-Adresse korrekt (Kommentar)
   * FkEMail.checkEMailAdresse( "(AB\@C)DEF@GHI.JKL"             ) = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   * FkEMail.checkEMailAdresse( "ABC(DEF@GHI.JKL"                ) = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI)JKL"                ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( ")ABC.DEF@GHI.JKL"               ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "ABC.DEF@(GHI).JKL"              ) = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   * FkEMail.checkEMailAdresse( "ABC(DEF@GHI).JKL"               ) = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   * FkEMail.checkEMailAdresse( "(ABC.DEF@GHI.JKL)"              ) = 95 = Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   * FkEMail.checkEMailAdresse( "(A(B(C)DEF@GHI.JKL"             ) = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   * FkEMail.checkEMailAdresse( "(A)B)C)DEF@GHI.JKL"             ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( "(A)BCDE(F)@GHI.JKL"             ) = 99 = Kommentar: kein zweiter Kommentar gueltig
   *
   *
   * eMail-Adressen mit eckigen Klammern ------------------------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "ABC DEF <ABC.DEF@GHI.JKL>"      ) =  0 = eMail-Adresse korrekt
   * FkEMail.checkEMailAdresse( "<ABC.DEF@GHI.JKL> ABC DEF"      ) =  0 = eMail-Adresse korrekt
   * 
   * FkEMail.checkEMailAdresse( "ABC DEF ABC.DEF@GHI.JKL>"       ) = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   * FkEMail.checkEMailAdresse( "<ABC.DEF@GHI.JKL ABC DEF"       ) = 17 = Struktur: keine schliessende eckige Klammer gefunden.
   * FkEMail.checkEMailAdresse( ""ABC DEF "<ABC.DEF@GHI.JKL>"    ) = 18 = Struktur: Fehler in Adress-String-X
   * FkEMail.checkEMailAdresse( ""ABC<DEF>"@JKL.DE"              ) = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( ">"                              ) = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *
   * Mehr eckige Klammern als erwartet:
   * 
   * FkEMail.checkEMailAdresse( ""ABC<DEF@GHI.COM>"@JKL.DE"      ) = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   * FkEMail.checkEMailAdresse( "ABC DEF <ABC.<DEF@GHI.JKL>"     ) = 18 = Struktur: Fehler in Adress-String-X
   * 
   * FkEMail.checkEMailAdresse( "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>" ) = 18 = Struktur: Fehler in Adress-String-X 
   *
   *
   * Wird keine oeffnende oder schliessende eckige Klammer gefunden, wird der Rest  
   * der Eingabe nicht nach einer eckigen Klammer durchsucht. In diesem wird Fall 
   * die Fehlernummer 22 - fuer ein ungueltiges Zeichen - zurueckgegeben. 
   * 
   * FkEMail.checkEMailAdresse( "ABC DEF <ABC.DEF@GHI.JKL"       ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden 
   * FkEMail.checkEMailAdresse( "ABC.DEF@GHI.JKL> ABC DEF"       ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *
   * FkEMail.checkEMailAdresse( "ABC DEF >ABC.DEF@GHI.JKL<"      ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * FkEMail.checkEMailAdresse( ">ABC.DEF@GHI.JKL< ABC DEF"      ) = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * eMail-Adresse in eckigen Klammern zu kurz:
   *  
   * FkEMail.checkEMailAdresse( "ABC DEF <A@A>"                  ) = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   * FkEMail.checkEMailAdresse( "<A@A> ABC DEF"                  ) = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *
   * FkEMail.checkEMailAdresse( "ABC DEF <>"                     ) = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   * FkEMail.checkEMailAdresse( "<> ABC DEF"                     ) = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *
   * Die gesamte Eingabe ohne spitzen Klammern darf 255 Zeichen nicht ueberschreiten.
   * 
   * 
   * Korrekte eMail-Adressen, welche nicht erkannt werden -------------------------------------------------------------
   * 
   * FkEMail.checkEMailAdresse( "ABC@localhost"             ) = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * FkEMail.checkEMailAdresse( "ABC.DEF@localhost"         ) = 35 = Trennzeichen: der letzte Punkt muss nach dem AT-Zeichen liegen
   * 
   *                                                          ALTERNATIV: Fehler 34 und Fehler 35 spezifizieren genau diesen FALL.
   *                                                                      Diese Fehlernummern koennen als korrekt akzeptiert werden, wenn
   *                                                                      solche eMail-Adressangaben zugelassen werden sollen
   *
   * 
   * nicht geklaert ---------------------------------------------------------------------------------------------------
   *
   * Kommentar am Ende der eMail-Adresse korrekt?
   * Darf eine eMail-Domain mit einer Zahl enden? 
   * 
   * IPv6-Adressangaben werden nicht 100%ig korrekt erkannt
   * - Overflow in den Angaben werden nicht erkannt
   * 
   * ABC@[IPv6123::ffff:127.0.0.1] = Praefix "IPv6" oder "IPv6:" (Ohne oder mit Doppelpunkt als Trennzeichen?)
   * 
   * ABC.DEF@192.0.2.123         = IP4-Adressangabe ohne eckige Klammern gueltig ?
   * 
   * "-- --- .. -."@sh.de        = Zeichenkombination ". oder ." im String korrekt ?
   *                               (sonst gibt es einen "... .... .. -"@storm.de)
   * 
   * "(A(B(C)DEF@GHI.JKL"        = In einem Kommentar auch oeffnende Klammer '(' zulassen ?
   * 
   * "<ABC.DEF@GHI.JKL>"         = korrekte eMail-Adressangabe ?
   * "<ABC.DEF@GHI.JKL> ABC DEF" = korrekte eMail-Adressangabe ? (... das die Klammern am Start des Strings kommen)
   * 
   *
   * ------------------------------------------------------------------------------------------------------------------
   * 
   * " + ( FkEMail.checkEMailAdresse( str_email_adresse ) < 10 ? "eMail-Adresse OK" : " eMail-Adresse ungueltig " ) + "
   *   
   * 
   * http://www.ex-parrot.com/~pdw/Mail-RFC822-Address.html
   * - It is impossible to match these restrictions with a single technique. 
   * - Using regular expressions results in long patterns giving incomplete results.
   * 
   * https://davidcel.is/posts/stop-validating-email-addresses-with-regex/
   * - Some people, when confronted with a problem, think, "I know, I'll use regular expressions." Now they have two problems.
   * 
   * https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
   * - There don't seem to be any perfect libraries or ways to do this yourself ...
   * - Another option is use the Hibernate email validator, using the annotation Email or using the validator class programatically
   * - I agree that the Apache Commons validator works well, but I find it to be quite slow - over 3ms per call. 
   * - After actually trying to build my project, it seems apache commons doesn't work with Android very well, hundreds of warnings and some errors, it didn't even compile.
   * - Same problem with me as of Benjiko99. After adding the dependency, the project wont compile, says java.exe finished with non zero exit code 2.
   * - You may also want to check for the length - emails are a maximum of 254 chars long. I use the apache commons validator and it doesn't check for this.
   * - But really what you want is a lexer that properly parses a string and breaks it up into the component structure according to the RFC grammar. 
   *   EmailValidator4J seems promising in that regard, but is still young and limited.
   *
   * https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript?rq=1
   * - Technically some emails can include quotes in the section before the @ symbol with escape characters inside the quotes.
   *   NOBODY DOES THIS EVER! It's obsolete. But, it IS included in the true RFC 2822 standard, and omitted here.
   * 
   * 
   * 
   * https://docs.microsoft.com/en-us/dotnet/standard/base-types/how-to-verify-that-strings-are-in-valid-email-format
   *
   * https://www.regular-expressions.info/email.html
   * https://en.wikipedia.org/wiki/Email_address
   * https://de.wikipedia.org/wiki/Top-Level-Domain
   * 
   * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address?rq=1
   * https://tools.ietf.org/id/draft-ietf-behave-address-format-10.html
   * https://www.cocoanetics.com/2014/06/e-mail-validation/
   * https://stackoverflow.com/questions/800123/what-are-best-practices-for-validating-email-addresses-on-ios-2-0
   * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?rq=1
   * </pre>
   * 
   * @param pEingabe die auf eine eMail-Adresse zu pruefende Eingabe
   * @return wenn die Eingabe nach der Struktur her eine eMail-Adresse ergeben kann einen Wert kleiner 10, ansonsten einen der oben genannten Rueckgabewerte 
   */
  private static int checkEMailAdresse( String pEingabe )
  {
    /*
     * Grober Ablaufplan:
     * 
     * Laengenpruefungen 1 - Eingabe gesamt 
     * 
     * Ende auf '>'
     *    JA = suche '<'
     * 
     * Start auf '<'
     *    JA = suche '>'
     *    
     * Eckige Klamern vorhanden?
     *    JA = Pruefe den nicht eMail-String
     * 
     * Laengenprufungen 2 - eMail-Adresse
     * 
     * While-Schleife 1
     * 
     *    Zeichen A-Z, a-z und Zalen
     *    
     *    Sonderzeichen Domain-Part - _
     *    
     *    Zeichen .
     *    
     *    Zeichen @
     *    
     *    Zeichen \
     *    
     *    Sonderzeichen Local-Part
     * 
     *    Zeichen "
     *       
     *       While-Schleife 2 - String
     *       
     *          Zeichen A-Z, a-z, Zahlen und Sonderzeichen 
     *          
     *          Zeichen "
     *          
     *          Zeichen \
     *       
     *    Zeichen [
     *    
     *       Zeichen "IPv6:"
     *    
     *       While-Schleife 3 - IP-Adresse
     *          
     *          IPv6
     *          
     *              Zeichen A-F, a-f und Zahlen
     *              
     *              Zeichen .
     *              
     *              Zeichen :
     *              
     *              Zeichen ]
     *              
     *          IPv4
     *          
     *              Zahlen
     *          
     *              Zeichen .
     *          
     *              Zeichen ]
     *       
     *    Zeichen (
     *    
     *       While-Schleife 4 - Kommentare
     *          
     *          Zeichen A-Z, a-z, Zahlen und Sonderzeichen 
     *          
     *          Zeichen \
     *          
     *          Zeichen )
     * 
     * Abschlusspruefungen
     */

    /*
     * Pruefung: Eingabe gleich "null" ? 
     * 
     * Ist die Eingabe gleich "null", ist es keine gueltige eMail-Adresse. 
     * Es wird der Fehler 10 zurueckgegeben.
     */
    if ( pEingabe == null )
    {
      return 10; // Laenge: Eingabe ist null
    }

    /*
     * Pruefung: Eingabelaenge gleich 0 ?
     * 
     * Es wird kein Trim gemacht, da ein Trim Zeit verbrauchen wuerde. 
     * Diese Funktion soll den String so pruefen, wie dieser uebergeben wurde.
     * Es ist nicht die Aufgabe dieser Funktion, die Eingabe zu manipulieren.
     * Besteht die Eingabe nur aus Leerzeichen, wird beim ersten Leerzeichen  
     * ein ungueltiges Zeichen erkannt und der Fehler 29 zurueckgegeben.
     * 
     * Ist die Eingabe ohne Trim schon ein Leerstring, wird der 
     * Fehler 11 zurueckgegeben.
     */
    if ( pEingabe.length() == 0 )
    {
      return 11; // Laenge: Eingabe ist Leerstring
    }

    /*
     * Laenge Eingabestring
     * Die Variable "laenge_eingabe_string" bezeichnete in der ersten Version 
     * die tatsaechliche Laenge des Eingabestrings. Durch den Einbau der Pruefroutine
     * fuer eckige Klammern, kann der Inhalt aber auch die Bedeutung einer 
     * Positonsangabe haben. 
     */
    int laenge_eingabe_string = pEingabe.length();

    /*
     * Generell darf die Eingabe nicht laenger als 255 Zeichen sein.
     * Die Pruefung auf die minimale Laenge der eMail-Adresse folgt weiter unten.
     */
    if ( laenge_eingabe_string > 255 )
    {
      return 12;
    }

    /*
     * Position AT-Zeichen
     * Initialwert -1 steht fuer "kein AT-Zeichen gefunden"
     */
    int position_at_zeichen = -1;

    /*
     * Merker "letzte Position eines Punktes"
     * Initialwert -1 steht fuer "keinen Punkt gefunden"
     */
    int position_letzter_punkt = -1;

    /*
     * Speichert die Position des zuletzt gefundenen Anfuehrungszeichens. 
     * Start- oder Endzeichen. 
     */
    int position_anf_zeichen_akt = -1;

    /*
     * Speichert die Position der letzten geschlossenen Klammer ')' eines gueltigen Kommentares. 
     */
    int position_kommentar_ende = -1;

    int position_kommentar_start = -1;

    /*
     * Zaehler fuer Zeichen zwischen zwei Trennzeichen.
     * Die Trennzeichen sind Punkt und AT-Zeichen
     */
    int zeichen_zaehler = 0;

    /*
     * Start Leseposition
     * Die Startposition fuer die While-Schleife ist hier 0. Das ist
     * das erste Zeichen der Eingabe. 
     * 
     * Bei eMail-Adressen in spitzen Klammern ist die Startposition 
     * immer die Position nach der oeffnenden eckigen Klammer.
     */
    int akt_index = 0;

    char aktuelles_zeichen = ' ';

    /*
     * Pruefung: Eckige Klammern
     * 
     * Rudimentaer eingebaut
     *  
     * ABC DEF <ABC.DEF@GHI.JKL>
     * 
     * <ABC.DEF@GHI.JKL> ABC DEF
     * 
     * Startet die eMail-Adresse mit einer oeffnenden eckigen Klammer, wird 
     * eine schliessende eckige Klammmer gesucht. Von vorne nach hinten.
     * 
     * Ende die eMail-Adresse mit einer schliessenden eckigen Klammer, wird 
     * eine oeffnende eckige Klammer gesucht. Von hinten nach vorne.
     * 
     * Wird keine korrospondierende Klammer gefunden, wird die Funktion 
     * mit einem Fehlercode (16 oder 17) beendet.
     * 
     * Wird die korrospondierende Klammer gefunden, wird die Start- und 
     * Endposition fuer die eigentliche Pruefroutine auf die in den 
     * eckigen Klammern enthaltende eMail-Adresse beschraenkt.
     * 
     * Der String ausserhalb der eckigen Klammern wird durch eine eigene 
     * While-Schleife geprueft. Hierzu werden vorhandene Variablen 
     * zweckentfremded um nicht mehr Variablen deklarieren zu muessen. 
     * Sind die Zeichen in dem nicht eMail-String OK, werden diese 
     * Variablen wieder auf deren Initialwert von -1 gesetzt. 
     * 
     * Ist im "nicht eMail-Adress-String" ein ungueltiges Zeichen vorhanden, 
     * wird der Fehler 18 zurueckgegeben. 
     */

    aktuelles_zeichen = pEingabe.charAt( laenge_eingabe_string - 1 );

    /*
     * Pruefung: Ende mit einer schliessenden eckigen Klammer ?
     */
    if ( aktuelles_zeichen == '>' )
    {
      /*
       * 
       * Das letzte Zeichen ist in diesem Fall eine schliessende eckige Klammer.
       *  
       * Dieses Zeichen darf von der unten stehende while-Schleife nicht 
       * geprueft werden, da ansonsten ein ungueltiges Zeichen erkannt 
       * werden wuerde.
       * 
       * Es wird die BIS-Poisition fuer die eMail-Adress-Pruefschleife um 
       * ein Zeichen vermindert. 
       */
      laenge_eingabe_string--;

      /*
       * In einer While-Schleife wird die oefnende eckige Klammer gesucht.
       * Es wird von hinten nach vorne gesucht.
       */
      akt_index = laenge_eingabe_string;

      while ( ( akt_index > 0 ) && ( aktuelles_zeichen != '<' ) )
      {
        akt_index--;

        aktuelles_zeichen = pEingabe.charAt( akt_index );
      }

      /*
       * Ist das letzte Zeichen eine schliessende eckige Klammer, muss es eine 
       * eckige startende Klammer geben. 
       * 
       * Nach der While-Schleife muss in der Variablen "aktuelles_zeichen"
       * die oeffnede eckige Klammer enthalten sein. 
       * 
       * Ist es ein anderes Zeichen, stimmt die Struktur nicht.
       */
      if ( aktuelles_zeichen != '<' )
      {
        return 16; // Struktur: keine oeffnende eckige Klammer gefunden. 
      }

      /*
       * Bestimmung der Positionen, des seperat zu pruefenden "nicht eMail-Adress-Strings"
       */
      position_letzter_punkt = 0;
      position_kommentar_ende = akt_index;

      /*
       * Der aktuelle Index steht nun auf der Position der oeffnenden eckigen Klammer. 
       * Das dortige Zeichen wurde geprueft und ist OK, daher wird der Leseprozess 
       * um ein Zeichen weiter gestellt. (Ausserdem Fehlervermeidung Nr. 22 )
       */
      akt_index++;
    }
    else
    {
      /*
       * Eingabe endete nicht auf einer schliessenden eckigen Klammer. 
       * Es wird geprueft, ob die Eingabe mit einer eckigen oeffnenden 
       * Klammer startet. 
       * 
       * Es wird das aktuelle Zeichen an der Position 0 gelesen.
       */
      aktuelles_zeichen = pEingabe.charAt( akt_index );

      if ( aktuelles_zeichen == '<' )
      {
        /*
         * Startet die Eingabe mit einer eckigen oeffnenden Klammer, wird
         * in einer While-Schleife die schliessende eckige Klammer gesucht.
         * Es wird von vorne nach hinten gesucht.
         */
        while ( ( akt_index < ( laenge_eingabe_string - 1 ) ) && ( aktuelles_zeichen != '>' ) )
        {
          akt_index++;

          aktuelles_zeichen = pEingabe.charAt( akt_index );
        }

        /*
         * Ist das erste Zeichen eine oeffnende eckige Klammer, muss es eine 
         * eckige schliessende Klammer geben. 
         * 
         * Nach der While-Schleife muss in der Variablen "aktuelles_zeichen"
         * die schliessende eckige Klammer enthalten sein. 
         * 
         * Ist es ein anderes Zeichen, stimmt die Struktur nicht.
         */
        if ( aktuelles_zeichen != '>' )
        {
          return 17; // Struktur: keine schliessende eckige Klammer gefunden. 
        }

        /*
         * Bestimmung der Positionen, des seperat zu pruefenden "nicht eMail-Adress-Strings"
         * 
         * Der zu pruefende String startet nach dem Zeichen hinter der aktuellen Position.
         * Der String endet am Index des letzten Zeichens.
         */
        position_letzter_punkt = akt_index + 1;
        position_kommentar_ende = laenge_eingabe_string;

        /*
         * Der Leseprozess muss ein Zeichen vor der gefundenden schliessenden eckigen Klammer enden.
         * Die Laenge des Eingabestrings wird entsprechend angepasst.  
         */
        laenge_eingabe_string = akt_index;

        /*
         * Das Zeichen an Position 0 ist die oeffnende eckige Klammer. 
         * Der Leseprozess muss bei Index 1 starten.  
         */
        akt_index = 1;
      }
    }

    int email_local_part_gesamt_start = akt_index;

    /*
     * Pruefung: gibt es einen seperat zu pruefenden "nicht eMail-Adress-String" ?
     */
    if ( position_letzter_punkt != -1 )
    {
      /*
       * Eingabe = "<ABC@DEF.GHI>"
       * 
       * Von der Suchroutine wird eine spitze Klammer erkannt. Es wird auch 
       * eine - in diesem Fall - oeffnende Klammer gefunden. 
       * 
       * Es wird die Position des letzten Punktes auf 0 gestellt. 
       * Es bleibt jedoch kein "nicht eMail-String" uebrig. 
       * 
       * Ist das ein Fehler oder nicht ?
       * Im Moment wird eine solche Eingabe als korrekte eMail-Adresse durchgelassen. 
       */
      //if ( position_letzter_punkt == position_kommentar_ende )
      //{
      //  return 19; // Struktur: es gibt keinen "nicht eMail-String" 
      //}

      /*
       * Ueber eine While-Schleife werden die Zeichen im "nicht eMail-Adress-String" geprueft.
       * Wird ein ungueltiges Zeichen erkannt, wir der Fehler 18 zurueckgegeben.
       */
      while ( position_letzter_punkt < position_kommentar_ende )
      {
        aktuelles_zeichen = pEingabe.charAt( position_letzter_punkt );

        if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) || ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) )
        {
          // OK
        }
        else if ( ( aktuelles_zeichen == ' ' ) || ( aktuelles_zeichen == '(' ) || ( aktuelles_zeichen == ')' ) || ( aktuelles_zeichen == '\"' ) )
        {
          // OK ... eventuell weitere Zeichen hier zulaessig, welche zu ergaenzen waeren
        }
        else
        {
          //System.out.println( aktuelles_zeichen + " Fehler " );

          return 18; // Struktur: Fehler in Adress-String-X
        }

        position_letzter_punkt++;
      }

      /*
       * Restaurierung der Vorgabewerte
       * Die temporaer fuer andere Zwecke verwendeten Variablen, werden wieder auf deren 
       * Vorgabewerte von -1 gestellt, damit die eigentliche Pruefroutine korrekt arbeitet.
       */
      position_letzter_punkt = -1;
      position_kommentar_ende = -1;
    }

    aktuelles_zeichen = ' ';

    /*
     * Berechnung der Laenge der reinen eMail-Adressangabe.
     * 
     * Die Variable "akt_index" steht hier auf dem ersten Zeichen der eMail-Adresse. 
     * Die Variable "laenge_eingabe_string" steht nach dem letzten zu pruefenden 
     * Zeichen der eMail-Adresse. Dieses um mit der bisherigen Variablenbezeichnung 
     * konform zu sein, welches die Laenge des Eingabestrings war. 
     */
    zeichen_zaehler = laenge_eingabe_string - akt_index;

    /*
     * http://de.wikipedia.org/wiki/E-Mail-Adresse
     * Innerhalb von RFC 5322 gibt es keine Laengenbegrenzung fuer eMail-Adressen. 
     * 
     * Im RFC 5321 wird die maximale Laenge des Local-Parts mit 64 Bytes und
     * die maximale Laenge des Domainnamens mit 255 Bytes angegeben. Zusammen 
     * mit dem "@"-Zeichen ergaebe sich daraus die maximale Laenge einer 
     * E-Mail-Adresse von 320 Bytes. 
     * 
     * Im RFC 5321 wird auch die maximale Laenge des "Path"-Elements definiert, 
     * welches die Elemente "FROM" und "RCPT TO" im Envelope bestimmt und die 
     * maximale Laenge von Bytes einschliesslich der Separatoren "<" und ">" hat. 
     * Daraus ergibt sich eine maximale Laenge der E-Mail-Adresse von 254 Bytes 
     * einschliesslich des "@". Eine E-Mail mit einer laengeren Adresse, kann 
     * ueber RFC-konforme SMTP-Server weder verschickt noch empfangen werden.
     * 
     * Minimal moegliche eMail-Adresse ist "A@B.CD", gleich 6 Stellen.
     */
    if ( ( zeichen_zaehler < 6 ) || ( zeichen_zaehler > 254 ) )
    {
      return 12; // Laenge: Laengenbegrenzungen stimmen nicht 
    }

    zeichen_zaehler = 0;

    /*
     * Variable "fkt_ergebnis_email_ok"
     * 
     * Speicherung des Funktionsergebnisses fuer korrekte eMail-Angaben. 
     * 
     * Ist die eMail-Adresse rein Text ohne Sonderformen, bleibt der Wert auf 0.
     * 
     * Ist im Local-Part ein String vorhanden, wird der Wert auf 1 geaendert.
     * 
     * Ist eine IP4-Adressangabe vorhanden, wird der Wert um 2 erhoeht.
     * 
     * Ist eine IP6-Adressangabe vorhanden, wird der Wert um 4 erhoeht.
     *
     * Ist ein Kommentar vorhanden, wird der Rueckgabewert konvertiert.
     * 
     * Es werden nur die unten stehenden 10 Ergebniswerte geliefert, 
     * damit korrekte eMail-Adressen unter dem Wert 10 bleiben.
     * 
     * Bezueglich der Erhoehung des Wertes bei einer Stringangabe ist es 
     * so, dass die IP-Adresse erst nach dem AT-Zeichen korrekt erkannt
     * wird. Eine korrekt gelesene String-Angabe wird immer vor einer 
     * moeglichen IP-Adresse gelesen werden und somit wird der Ergebnis-
     * wert auf 1 gestellt, bevor die Erhoehung bei der IP-Adresse 
     * gemacht werden kann.   
     * 
     * Folgende Ergebnisse sind moeglich:
     * 
     *  0 = eMail-Adresse korrekt
     *  1 = eMail-Adresse korrekt (Local Part mit String)
     *  2 = eMail-Adresse korrekt (IP4-Adresse)
     *  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
     *  4 = eMail-Adresse korrekt (IP6-Adresse)
     *  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
     *  6 = eMail-Adresse korrekt (Kommentar)
     *  7 = eMail-Adresse korrekt (Kommentar, String)
     *  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
     *  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
     *  
     * Durch die verschiedenen Rueckgabewerte, kann der Aufrufer eMail-Adressen 
     * mit IP-Adresse oder String-Teilen im Nachgang noch abweisen.  
     */
    int fkt_ergebnis_email_ok = 0;

    boolean knz_kommentar_abschluss_am_stringende = false;

    /*
     * While-Schleife 1
     * 
     * In der aeusseren While-Schleife wird eine grundlegende eMail-Adressstruktur geparst. 
     * 
     * Es gibt drei innere While-Schleifen zum parsen von String-Bestandteilen, Kommentaren und IP-Adressen.
     * 
     * In den inneren While-Schleifen werden alle Bedingungen abgeprueft, welche dort zu pruefen sind.
     * 
     * Bedingungen aus einer inneren While-Schleifen, sollen (moeglichst) nicht auf die aeussere While-Schleife durchgreifen.
     */
    while ( akt_index < laenge_eingabe_string )
    {
      /*
       * Aktuelles Pruefzeichen
       * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
       */
      aktuelles_zeichen = pEingabe.charAt( akt_index );

      /*
       * Bedingungen Zeichen A-Z, a-z und Zahlen
       */
      if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) || ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) )
      {
        /*
         * Buchstaben ("A" bis "Z" und "a" bis "z") und Zahlen duerfen an jeder Stelle der eMail-Adresse vorkommen.
         * 
         * Ein solches Zeichen kann selber keinen Fehler produzieren.
         *   
         * Es wird der Zeichenzaehler um eins erhoeht.
         */
        zeichen_zaehler++;
      }
      else if ( ( aktuelles_zeichen == '_' ) || ( aktuelles_zeichen == '-' ) )
      {
        /*
         * Im Domain-Part duerfen die Sonderzeichen '_' und '-' nicht am Start stehen.  
         * 
         * Steht der Zeichenzaehler auf 0, steht das aktuelle Zeichen an einer ungueltigen Position.
         * 
         * Nach RFC 952 darf im Domain-Part kein Teilstring mit einer Zahl oder einem Punkt starten.
         * Nach RFC 1123 duerfen Hostnamen mit Zahlen starten.
         */

        if ( position_at_zeichen > 0 ) // kein Beginn mit einer Zahl oder Sonderzeichen im Domain-Part
        {
          if ( zeichen_zaehler == 0 )
          {
            return 20; // Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
          }

          if ( ( akt_index + 1 ) == laenge_eingabe_string )
          {
            return 24; // Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
          }
          else
          {
            /*
             * https://en.wikipedia.org/wiki/Email_address
             * 
             * Domain-Part:
             * hyphen -, provided that it is not the first or last character.
             */
            if ( pEingabe.charAt( akt_index + 1 ) == '.' )
            {
              return 20; // Trennzeichen: ungueltige Zeichenkombination "-."
            }
            //else if ( pEingabe.charAt( akt_index - 1 ) == '.' )
            //{
            //  return 20; // Trennzeichen: ungueltige Zeichenkombination ".-"
            //}
          }
        }
      }
      /*
       * Bedingungen fuer einen Punkt
       */
      else if ( aktuelles_zeichen == '.' )
      {
        /*
         * Pruefung: Wurde schon ein Punkt gefunden?
         * 
         * Nein, wenn in der Speichervariablen "position_letzter_punkt" noch der Initialwert von -1 steht.
         */
        if ( position_letzter_punkt == -1 )
        {
          /*
           * Pruefung: Leseposition gleich 0 ?
           * 
           * Ist der aktuelle Index gleich 0 und das aktuelle Zeichen ein Punkt,
           * wird der Fehler 30 zurueckgegeben. 
           * 
           * Es darf nicht mit dem Zeichenzaehler geprueft werden, da der erste 
           * Local-Part auch ein String sein kann. Dort wird der Zeichenzaehler 
           * nicht erhoeht.
           */
          if ( akt_index == ( position_kommentar_ende + 1 ) )
          {
            return 30; // Trennzeichen: kein Beginn mit einem Punkt
          }
        }
        else
        {
          /*
           * Pruefung: Zwei Punkte hintereinander ?
           * 
           * Ist die Differenz von der aktuellen Leseposition und der letzten 
           * Punkt-Position gleich 1, stehen 2 Punkte hintereinander. Es wird 
           * in diesem Fall der Fehler 31 zurueckgegeben.
           */
          if ( ( akt_index - position_letzter_punkt ) == 1 )
          {
            return 31; // Trennzeichen: keine zwei Punkte hintereinander
          }
        }

        if ( position_at_zeichen > 0 )
        {
          /*
           * Domain-Part-Labellaenge
           * https://de.wikipedia.org/wiki/Hostname
           * https://en.wikipedia.org/wiki/Hostname
           * 
           * Ein Domain-Label muss 1 Zeichen umfassen und darf maximal 63 Zeichen lang sein. 
           * Bei der Berechnung wird auf 64 Zeichen geprueft, da so die Subtraktion nicht 
           * verkompliziert werden muss (es wird die Position des letzten Punktes mitgezaehlt).
           * 
           * Ist der aktuelle Domain-Label zu lang, wird der Fehler 63 zurueckgegeben.
           */
          if ( ( akt_index - position_letzter_punkt ) > 64 )
          {
            // System.out.println( " " + ( akt_index - position_letzter_punkt ));

            return 63; // Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
          }
        }

        /*
         * Index des letzten Punktes speichern
         */
        position_letzter_punkt = akt_index;

        /*
         * Zeichen- und Zahlenzaehler nach einem Punkt auf 0 stellen
         */
        zeichen_zaehler = 0;
      }
      /*
       * Bedingungen fuer das AT-Zeichen
       */
      else if ( aktuelles_zeichen == '@' )
      {
        /*
         * Pruefung: Position AT-Zeichen ungleich -1 ?
         * 
         * Wurde bereits ein AT-Zeichen gefunden, ist in der Positionsvariablen 
         * ein Wert groesser 0 vorhanden. Es darf im Leseprozess nur einmal 
         * ein (unmaskiertes) AT-Zeichen als Trennerzeichen gefunden werden. 
         * Ist schon eine AT-Zeichenposition vorhanden, wird der Fehler
         * 29 zurueckgegeben. 
         * 
         * Wurde noch kein AT-Zeichen gefunden, werden weitere Pruefungen gemacht. 
         */
        if ( position_at_zeichen != -1 )
        {
          return 29; // AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
        }

        if ( akt_index == 0 )
        {
          return 26; // AT-Zeichen: kein AT-Zeichen am Anfang
        }

        if ( akt_index > 64 )
        {
          return 13; // Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
        }

        if ( ( akt_index + 1 ) == laenge_eingabe_string )
        {
          return 27; // AT-Zeichen: kein AT-Zeichen am Ende
        }

        if ( akt_index - position_letzter_punkt == 1 )
        {
          return 32; // Trennzeichen: ungueltige Zeichenkombination ".@"
        }

        /*
         * Kombination "@."
         * An dieser Position ist sichergestellt, dass in der Eingabe noch 
         * mindestens 1 Zeichen folgt. Es gibt hier keine Index-Out-Of-Bounds Exception.
         * 
         * Ansonsten wuerde das AT-Zeichen am Ende stehen und der Aufrufer 
         * wuerde 8 als Funktionsergebnis bekommen.
         */
        if ( pEingabe.charAt( akt_index + 1 ) == '.' )
        {
          return 33; // Trennzeichen: ungueltige Zeichenkombination "@."
        }

        /*
         * Position des AT-Zeichens merken
         */
        position_at_zeichen = akt_index;

        /*
         * Zeichenzaehler nach dem AT-Zeichen auf 0 stellen
         */
        zeichen_zaehler = 0;

        /*
         * Position letzer Punkt
         * 
         * Das AT-Zeichen trennt den Local- und Domain-Part. 
         * Die Position des letzen Punkts muss ausgenullt werden, um 
         * Seiteneffekte bei der Laengenberechnung der einzelnen 
         * Domain-Parts zu vermeiden.
         * 
         * Der Domain-Part startet am AT-Zeichen und auf dessen Index 
         * wird auch die Position des letzten Punktes gesetzt.
         */
        position_letzter_punkt = akt_index;
      }
      else if ( aktuelles_zeichen == '\\' )
      {
        /*
         * Sonderzeichen mit Qoutierung, welche nur im Local-Part vorkommen duerfen
         * 
         * \ @
         * 
         * Ist die Positon fuer das AT-Zeichen groesser als 0, befindet sich 
         * der Leseprozess im Domain-Part der eMail-Adresse. Dort sind diese 
         * Zeichen nicht erlaubt und es wird 21 zurueckgegeben.
         */
        if ( position_at_zeichen > 0 )
        {
          return 21; // Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
        }

        /*
         * Maskiertes Zeichen 
         * Der Leseprozess muss noch das naechste Zeichen pruefen. 
         * Der Leseprozessindex wird um ein Zeichen weiter gestellt.
         */
        akt_index++;

        /*
         * Pruefung: Stringende ?
         */
        if ( akt_index == laenge_eingabe_string )
        {
          return 83; // String: Escape-Zeichen nicht am Ende der Eingabe
        }

        /*
         * Zeichen nach dem Backslash lesen. 
         * Das Zeichen darf ein Backslash oder ein Anfuehrungszeichen sein. 
         * Alle anderen Zeichen fuehren zum Fehler 84.
         */
        aktuelles_zeichen = pEingabe.charAt( akt_index );

        if ( ( aktuelles_zeichen != '\\' ) && ( aktuelles_zeichen != '@' ) && ( aktuelles_zeichen != ' ' ) && ( aktuelles_zeichen != '\'' ) )
        {
          return 84; // String: Ungueltige Escape-Sequenz im String
        }
      }
      else if ( ( aktuelles_zeichen == '!' ) || ( aktuelles_zeichen == '#' ) || ( aktuelles_zeichen == '$' ) || ( aktuelles_zeichen == '%' ) || ( aktuelles_zeichen == '&' ) || ( aktuelles_zeichen == '\'' ) || ( aktuelles_zeichen == '*' ) || ( aktuelles_zeichen == '+' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '/' ) || ( aktuelles_zeichen == '=' ) || ( aktuelles_zeichen == '?' ) || ( aktuelles_zeichen == '^' ) || ( aktuelles_zeichen == '`' ) || ( aktuelles_zeichen == '{' ) || ( aktuelles_zeichen == '|' ) || ( aktuelles_zeichen == '}' ) || ( aktuelles_zeichen == '~' ) )
      {
        /*
         *   asc("!") = 033   asc("*") = 042 
         *   asc("#") = 035   asc("+") = 043 
         *   asc("$") = 036   asc("-") = 045 
         *   asc("%") = 037   asc("/") = 047
         *   asc("&") = 038 
         *   asc("'") = 039
         *   
         *   asc("=") = 061   asc("{") = 123 
         *   asc("?") = 063   asc("|") = 124 
         *   asc("^") = 094   asc("}") = 125 
         *   asc("_") = 095   asc("~") = 126 
         *   asc("`") = 096   asc(" ") = 032 
         */

        /*
         * Sonderzeichen, welche nur im Local-Part vorkommen duerfen
         * 
         * !#$%&'*+-/=?^_`{|}~
         * 
         * Ist die Positon fuer das AT-Zeichen groesser als 0, befindet sich 
         * der Leseprozess im Domain-Part der eMail-Adresse. Dort sind diese 
         * Zeichen nicht erlaubt und es wird 21 zurueckgegeben.
         */
        if ( position_at_zeichen > 0 )
        {
          return 21; // Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
        }
      }
      else if ( aktuelles_zeichen == '"' )
      {
        /*
         * While-Schleife 2 - String
         */

        /*
         * Pruefung: Leseprozess im Domain-Part ?
         * Im Domain-Part sind keine Anfuehrungszeichen erlaubt. 
         * Es wird der Fehler 82 zurueckgegeben.
         */
        if ( position_at_zeichen > 0 )
        {
          return 82; // String: kein Anfuehrungszeichen nach dem AT-Zeichen
        }

        /*
         * Pruefung: Zeichenkombination ."
         * 
         * Ein lokaler Part in Anfuehrungszeichen muss nach einem Trennzeichen starten.
         * 
         * Wurde schon ein Punkt gefunden und die Differenz des Leseprozesses 
         * ist nicht 1, wird der Fehler 81 zurueckgegeben.
         */
        if ( position_letzter_punkt > 0 )
        {
          if ( akt_index - position_letzter_punkt != 1 )
          {
            return 81; // String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
          }
        }

        /*
         * Pruefung: Start mit Anfuehrungszeichen 
         * Sind noch keine Zeichen gelesen worden, wird der Fehler 80 zurueckgegeben.
         */
        if ( zeichen_zaehler > 0 )
        {
          return 80; // String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
        }

        /*
         * Das aktuelle Zeichen ist hier noch ein Anfuehrungszeichen. 
         * Die nachfolgende While-Schleife wuerde nicht starten, wenn 
         * das Zeichen nicht geaendert wuerde. Darum wird der Variablen 
         * "aktuelles_zeichen" das Zeichen 'A' zugewiesen.
         */
        aktuelles_zeichen = 'A';

        /*
         * Position des aktuellen Anfuehrungszeichens merken
         */
        position_anf_zeichen_akt = akt_index;

        /*
         * Der Leseprozess wird um ein Zeichen weitergestellt. 
         * Damit wird das einleitende Anfuehrungszeichen vom Parser verarbeitet.
         * 
         * Steht das einleitende Anfuehrungszeichen am Stringende, wird die 
         * While-Schleife nicht ausgefuehrt, da die Variable "akt_index" jetzt 
         * gleich der Laenge des Eingabestrings ist. Die Differenz zur 
         * gespeicherten Position des einleitenden Anfuehrungszeichens ist 1. 
         * Das ist die erste Pruefung nach der While-Schleife. Es werden 
         * somit Eingaben wie:
         * 
         *         ABC.DEF."
         * 
         * erkannt. Der Aufrufer bekommt in diesem Fall den Fehler 85 zurueck.
         */
        akt_index++;

        /*
         * Innere While-Schleife zum Einlesen des Strings.
         * Laeuft bis zum Stringende oder bis zum naechsten - nicht maskierten - Anfuehrungszeichen.
         */
        while ( ( akt_index < laenge_eingabe_string ) && ( aktuelles_zeichen != '"' ) )
        {
          /*
           * Aktuelles Pruefzeichen
           * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
           */
          aktuelles_zeichen = pEingabe.charAt( akt_index );

          /*
           * Alle erlaubten Stringzeichen werden durchgelassen. 
           * Falsche Zeichen erzeugen einen Fehler.
           */

          if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) )
          {
            // OK - Buchstaben
          }
          else if ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) )
          {
            // OK - Zahlen
          }
          else if ( ( aktuelles_zeichen == '_' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '@' ) || ( aktuelles_zeichen == '.' ) || ( aktuelles_zeichen == ' ' ) || ( aktuelles_zeichen == '!' ) || ( aktuelles_zeichen == '#' ) || ( aktuelles_zeichen == '$' ) || ( aktuelles_zeichen == '%' ) || ( aktuelles_zeichen == '&' ) || ( aktuelles_zeichen == '\'' ) || ( aktuelles_zeichen == '*' ) || ( aktuelles_zeichen == '+' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '/' ) || ( aktuelles_zeichen == '=' ) || ( aktuelles_zeichen == '?' ) || ( aktuelles_zeichen == '^' ) || ( aktuelles_zeichen == '`' ) || ( aktuelles_zeichen == '{' ) || ( aktuelles_zeichen == '|' ) || ( aktuelles_zeichen == '}' ) || ( aktuelles_zeichen == '~' ) )
          {
            // OK - Sonderzeichen 1
          }
          else if ( ( aktuelles_zeichen == '(' ) || ( aktuelles_zeichen == ')' ) || ( aktuelles_zeichen == ',' ) || ( aktuelles_zeichen == ':' ) || ( aktuelles_zeichen == ';' ) || ( aktuelles_zeichen == '<' ) || ( aktuelles_zeichen == '>' ) || ( aktuelles_zeichen == '@' ) || ( aktuelles_zeichen == '[' ) || ( aktuelles_zeichen == ']' ) )
          {
            // OK - Sonderzeichen 2 = (),:;<>@[\]
          }
          else if ( aktuelles_zeichen == '"' )
          {
            // OK - abschliessendes Anfuehrungszeichen 

            /*
             * Der Leseprozessindex muss nach der While-Schleife auf dem jetzt
             * gerade gueltigen Index stehen. 
             * 
             * Da am Ende dieser inneren While-Schleife der Leseprozess um 
             * eine Position weiter gestellt wird, muss der Index hier um 
             * eine Position verringert werden.
             * 
             * Die aeussere While-Schleife erhoeht den Leseprozessindex nochmals. 
             */
            akt_index--;
          }
          else if ( aktuelles_zeichen == '\\' )
          {
            /*
             * Maskiertes Zeichen 
             * Der Leseprozess muss nach naechste Zeichen pruefen. 
             * Der Leseprozessindex wird um ein Zeichen weiter gestellt.
             */
            akt_index++;

            /*
             * Pruefung: Stringende ?
             */
            if ( akt_index == laenge_eingabe_string )
            {
              return 83; // String: Escape-Zeichen nicht am Ende der Eingabe
            }

            /*
             * Zeichen nach dem Backslash lesen. 
             * Das Zeichen darf ein Backslash oder ein Anfuehrungszeichen sein. 
             * Alle anderen Zeichen fuehren zum Fehler 84.
             */
            aktuelles_zeichen = pEingabe.charAt( akt_index );

            if ( ( aktuelles_zeichen != '\\' ) && ( aktuelles_zeichen != '@' ) && ( aktuelles_zeichen != ' ' ) && ( aktuelles_zeichen != '\'' ) && ( aktuelles_zeichen != '"' ) )
            {
              return 84; // String: Ungueltige Escape-Sequenz im String
            }

            /*
             * Vermeidung dass die While-Schleife fruehzeitig beendet wird. 
             * Im Falle dass ein Anfuehrungszeichen gefunden wurde, wuerde 
             * die While-Schleife zum String einlesen beendet werden. 
             */
            aktuelles_zeichen = 'A';
          }
          else
          {
            /*
             * Fehler 89
             * Dieser Fehler wird zurueckgegeben, wenn ein ungueltiges 
             * Zeichen im String vorhanden ist.
             */
            return 89; // String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
          }

          /*
           * Der Leseprozessindex wird um eine Position weitergestellt.
           */
          akt_index++;
        }

        /*
         * Leerstring 
         * Wird ein abschliessendes Leerzeichen gefunden, darf die Differenz zur
         * letzten Position eines Anfuehrungszeichens nicht 1 sein.
         */
        if ( ( akt_index - position_anf_zeichen_akt ) == 1 )
        {
          return 85; // String: Leerstring in Anfuehrungszeichen "", oder Start mit Leerzeichen am Ende der Eingabe
        }

        /*
         * Pruefung: abschliessendes Anfuehrungszeichen vorhanden ?
         * 
         * Nach dem Ende der While-Schleife, muss die Variable "aktuelles_zeichen" ein 
         * Anfuehrungszeichen beinhalten. 
         * 
         * Wurde kein abschliessendes Anfuehrungszeichen gefunden? 
         */
        if ( aktuelles_zeichen != '"' )
        {
          return 86; // String: kein abschliessendes Anfuehrungszeichen gefunden.
        }

        /*
         * Pruefung: Leseprozess am Stringende angelangt ? 
         */
        if ( akt_index + 1 >= laenge_eingabe_string )
        {
          return 88; // String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
        }

        /*
         * Gueltige Abschlusszeichenkombinationen pruefen
         */
        if ( pEingabe.charAt( akt_index + 1 ) == '.' )
        {
          // gueltige Zeichenkombination ".
        }
        else if ( pEingabe.charAt( akt_index + 1 ) == '@' )
        {
          // gueltige Zeichenkombination "@
        }
        else if ( pEingabe.charAt( akt_index + 1 ) == '(' )
        {
          // gueltige Zeichenkombination "(
        }
        else
        {
          return 87; // String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
        }

        /*
         * Position des letzten Anfuehrungszeichens merken
         */
        position_anf_zeichen_akt = akt_index;

        /*
         * Es wurde ein String im Local-Part der eMail-Adresse erkannt. 
         * Die Variable "fkt_ergebnis_email_ok" wird auf 1 gesetzt.
         */
        fkt_ergebnis_email_ok = 1;
      }
      else if ( aktuelles_zeichen == '[' )
      {
        /*
         * While-Schleife 3 - IP-Adresse
         */

        /*
         * Pruefung: AT-Zeichen noch nicht vorgekommen ?
         * 
         * Die IP-Adresse muss sich im Domain-Teil der eMail-Adresse befinden. 
         * Wurde noch kein AT-Zeichen gefunden, befindet sich der Leseprozess 
         * noch nicht im Domain-Teil. Es wird 51 als Fehler zurueckgegeben. 
         */
        if ( position_at_zeichen == -1 )
        {
          return 51; // IP-Adressteil: IP-Adresse vor AT-Zeichen
        }

        if ( ( position_kommentar_ende > position_at_zeichen ) )
        {
          /*
           * Pruefung: Domain-Part mit Kommentar nach AT-Zeichen
           * 
           * ABC.DEF@(comment)[1.2.3.4]
           * 
           * Ist ein Kommentar direkt nach dem AT-Zeichen vorhanden, muss die einleitende
           * eckige Klammer fuer die IP-Adresse direkt nach der schliessenden Kommentarklammer
           * kommen. 
           * 
           * Der Abstand der aktuellen Lesepositon zur Position der Kommentarklammer muss 1 betragen. 
           * Ist die Differenz groesser, wird der Fehler 106 zurueckgegeben.
           */
          if ( ( akt_index - position_kommentar_ende ) != 1 )
          {
            return 106; // Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
          }
        }
        else
        {
          /*
           * Pruefung: Startzeichen direkt nach AT-Zeichen ?
           * 
           * Das Startzeichen "[" muss direkt nach dem AT-Zeichen kommen. 
           * Die aktuelle Leseposition muss genau 1 Zeichen nach der Position  
           * des AT-Zeichens liegen. Ist das nicht der Fall, wird 
           * 52 als Fehler zurueckgegeben.
           */
          if ( ( akt_index - position_at_zeichen ) != 1 )
          {
            return 52; // IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
          }
        }

        /*
         * Kennzeichenfeld IPv6
         * 
         * Ist keine IPv6-Adresse vorhanden, ist der Wert 0.
         * 
         * Ist eine normale IPv6-Adresse vorhanden, ist der Wert 1.
         * 
         * Ist eine IPv6-Adresse mit einer IPv4-Adressangabe vorhanden, ist der Wert 2.
         */
        int knz_ipv6 = 0;

        int ip_adresse_zaehler_trennzeichen = 0;

        int ip_adresse_anzahl_trennzeichen_hintereinander = 0;

        /*
         * Pruefung: IP-V6 Adressangabe ?
         * 
         * Es wird auf vorhandensein des Strings "IPv6:" ab der aktuellen Leseposition geprueft.
         * 
         * Ist der String vorhanden, wird eine IP-V6 Adresse gelesen. 
         * Ist der String nicht vorhanden, wird eine IP-4 Adresse gelesen.
         * 
         * Die Pruefung wird nur gemacht, wenn ab der aktuellen Position noch mindestens 5 Stellen vorhanden sind.
         * 
         *      1234567890
         * ABC@[IPv6:1:2::3]
         * ABC@[123.456.789.012]
         */
        if ( akt_index + 5 < laenge_eingabe_string )
        {
          /*
           * Ist das naechste Zeichen ein "I", werden die darauffolgenden Zeichen auf 
           * die Zeichen "P", "v", "6" und ":" geprueft. 
           * 
           * Sind diese Zeichen vorhanden, wird eine IPv6-Adresse gelesen.
           * 
           * Sind diese Zeichen nicht vorhanden, ist es ein Fehler, da das einleitende Zeichen "I" falsch ist.
           * In diesem Fall wird der Fehler 40 zurueckgegeben.
           */
          if ( pEingabe.charAt( akt_index + 1 ) == 'I' )
          {
            if ( ( pEingabe.charAt( akt_index + 2 ) == 'P' ) && ( pEingabe.charAt( akt_index + 3 ) == 'v' ) && ( pEingabe.charAt( akt_index + 4 ) == '6' ) && ( pEingabe.charAt( akt_index + 5 ) == ':' ) )
            {
              /*
               * Der Index des Leseprozesses wird um 5 erhoeht. 
               * Am schleifenende wird der Prozess nochmals um eine Position weitergestellt.
               * 
               * 01234567890123456789
               * ABC@[IPv6::ffff:127.0.0.1]" );
               */
              akt_index += 5;

              /*
               * Der Leseprozess befindet sich in einer IPv6 Adresse.
               * Die Kennzeichenvariable wird auf 1 gestellt.
               */
              knz_ipv6 = 1;

              /*
               * Es wurde 1 Trennzeichen gelesen.
               * (Trennzeichen zaehlt?)
               */
              ip_adresse_zaehler_trennzeichen++;
            }
            else
            {
              return 40; // IP6-Adressteil: String "IPv6:" erwartet
            }
          }
          else if ( ( pEingabe.charAt( akt_index + 1 ) == ':' ) || ( pEingabe.charAt( akt_index + 2 ) == ':' ) || ( pEingabe.charAt( akt_index + 3 ) == ':' ) || ( pEingabe.charAt( akt_index + 4 ) == ':' ) || ( pEingabe.charAt( akt_index + 5 ) == ':' ) )
          {
            /*
             * Einbettung IP-V6-Adresse ohne Praefix "IPv6"
             * 
             * Wird der Praefix "IPv6" nicht gefunden, wird auf das vorhandensein 
             * eines Doppelpunktes innerhalb der naechsten 5 Zeichen geprueft.
             * Wird ein Doppelpunkt gefunden, ist dieses das Kennzeichen, dass 
             * eine IP-V6 Adresse vorhanden ist. 
             * 
             * Das erste Zeichen wird auch mit geprueft, da die IP-V6-Adresse mit "::" starten koennte.
             */

            /*
             * Der Leseprozess befindet sich in einer IPv6 Adresse.
             * Die Kennzeichenvariable wird auf 1 gestellt.
             */
            knz_ipv6 = 1;
          }
        }

        /*
         * https://de.wikipedia.org/wiki/IPv6#Adressaufbau_von_IPv6
         * 
         * Die textuelle Notation von IPv6-Adressen ist in Abschnitt 2.2 von RFC 4291 beschrieben:
         * 
         * IPv6-Adressen werden fuer gewoehnlich hexadezimal (IPv4: dezimal) notiert, 
         * wobei die Zahl in acht Bloecke zu jeweils 16 Bit (4 Hexadezimalstellen) unterteilt wird.
         *  
         * Diese Bloecke werden durch Doppelpunkte (IPv4: Punkte) getrennt 
         * notiert: 2001:0db8:85a3:08d3:1319:8a2e:0370:7344.
         * 
         * Fuehrende Nullen innerhalb eines Blockes duerfen ausgelassen werden: 
         * 2001:0db8:0000:08d3:0000:8a2e:0070:7344 ist gleichbedeutend mit 2001:db8:0:8d3:0:8a2e:70:7344.
         * 
         * Ein oder mehrere aufeinander folgende Bloecke, deren Wert 0 (bzw. 0000) betraegt, duerfen ausgelassen werden. 
         * Dies wird durch zwei aufeinander folgende Doppelpunkte angezeigt: 2001:0db8:0:0:0:0:1428:57ab ist gleichbedeutend mit 2001:db8::1428:57ab.[15]
         * 
         * Die Reduktion durch Regel 3 darf nur einmal durchgefuehrt werden.
         * 
         * Es darf hoechstens eine zusammenhaengende Gruppe aus Null-Bloecken in der Adresse ersetzt werden.
         *  
         * Die Adresse 2001:0db8:0:0:8d3:0:0:0 darf demnach entweder zu 2001:db8:0:0:8d3:: oder 2001:db8::8d3:0:0:0 gekuerzt werden.
         * 2001:db8::8d3:: ist unzulaessig, da dies mehrdeutig ist und faelschlicherweise z.B. auch als 2001:db8:0:0:0:8d3:0:0 interpretiert werden koennte.
         *  
         * Die Reduktion darf auch dann nicht mehrfach durchgefuehrt werden, wenn das Ergebnis eindeutig waere. 
         * 
         * Ebenfalls darf fuer die letzten beiden Bloecke (vier Bytes, also 32 Bits) der Adresse die herkoemmliche dezimale Notation mit Punkten als Trennzeichen verwendet werden. 
         * So ist ::ffff:127.0.0.1 eine alternative Schreibweise fuer ::ffff:7f00:1. Diese Schreibweise wird vor allem bei Einbettung des IPv4-Adressraums in den IPv6-Adressraum verwendet.
         */

        /*
         * Innere While-Schleife
         * In einer inneren While-Schleife wird die IP-Adresse geparst und 
         * auf Gueltigkeit geprueft. 
         * 
         * Die innere While-Schleife laeuft bis zum Ende des Eingabestrings.
         */
        int ip_adresse_akt_zahl = 0;

        int ip_adresse_zahlen_zaehler = 0;

        akt_index++;

        while ( ( akt_index < laenge_eingabe_string ) && ( aktuelles_zeichen != ']' ) )
        {
          /*
           * Aktuelles Pruefzeichen
           * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
           */
          aktuelles_zeichen = pEingabe.charAt( akt_index );

          if ( knz_ipv6 == 1 )
          {
            if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'f' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'F' ) ) || ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) )
            {
              /*
               * Zahlenzaehler
               * Der Zahlenzaehler wird erhoeht.
               * Anschliessend wird geprueft, ob schon mehr als 4 Zeichen gelsen wurden. 
               * Ist das der Fall, wird 46 zurueckgegeben. 
               */
              ip_adresse_zahlen_zaehler++;

              if ( ip_adresse_zahlen_zaehler > 4 )
              {
                return 46; // IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
              }
            }
            else if ( aktuelles_zeichen == '.' )
            {
              /*
               * IPv4 Adressangabe
               * Wird innerhalb der IPv6-Adresse eine IPv4-Adresse angegeben, wird 
               * dieses durch einen Punkt erkannt.  
               * 
               * Der Leseprozess wird in die Erkennungsroutine fuer die IPv4-Adresse umgelenkt.
               * 
               * Die erste Zahl der IPv4-Adresse muss nochmal gelesen werden, damit die 
               * IPv4-Erkennungsroutine korrekt pruefen kann. Dazu wird der Leseprozessindex 
               * auf die Position des letzten Trennzeichens zurueckgesetzt.
               * 
               * Die erste Angabe der IPv4-Adresse wird doppelt gelesen. 
               */

              /*
               * Ist eine IP4-Adresse eingebettet worden, muessen Trennzeichen 
               * der IPv6-Adresse gefunden worden sein.  
               * 
               * (Nach dem aktuellem Verstaendnis von IP6-Adressen, muessen es 3 sein)
               */
              if ( ip_adresse_zaehler_trennzeichen != 3 )
              {
                return 47; // IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch 
              }

              /*
               * Der Zahlenzaehler muss bei einem Ruecksprung in die IP4-Routine
               * kleiner als 4 sein, da die erste Zahl der IP4-Adresse hier 
               * schon gelesen worden ist. 
               */
              if ( ip_adresse_zahlen_zaehler > 3 )
              {
                return 48; // IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
              }

              /*
               * Der Leseprozess wird auf die letzte Trennzeichenposition gestellt.  
               * Am Schleifenende wird der Leseprozess nochmals um eine Position 
               * weitergestellt, welches dann die korrekte Position des ersten 
               * Zeichens der IP4-Adresse ergibt.
               */
              akt_index = position_letzter_punkt; // +1 am Schleifenende

              /*
               * Sicherstellung, dass die Einbettung der IP4-Adresse mit "ffff" startet.
               * Es ist hier sichergestellt, dass mindestens 5 Zeichen vor dem Trennzeichen vorhanden sind.
               * Es muessen 5 Zeichen mindestens vorhanden sein, damit eine IP6-Adresse geparst wird.
               */
              if ( ( pEingabe.charAt( akt_index - 1 ) == 'f' ) && ( pEingabe.charAt( akt_index - 2 ) == 'f' ) && ( pEingabe.charAt( akt_index - 3 ) == 'f' ) && ( pEingabe.charAt( akt_index - 4 ) == 'f' ) )
              {
                // OK - ffff gefunden
              }
              else
              {
                if ( ( pEingabe.charAt( akt_index - 1 ) == 'F' ) && ( pEingabe.charAt( akt_index - 2 ) == 'F' ) && ( pEingabe.charAt( akt_index - 3 ) == 'F' ) && ( pEingabe.charAt( akt_index - 4 ) == 'F' ) )
                {
                  // OK - FFFF gefunden - Wobei die Frage offen bleibt, ob die Grossschreibung hier so in Ordnung ist. 
                }
                else
                {
                  return 62; // IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
                }
              }

              /*
               * Der Zahlenzaehler wird auf 0 gestellt, da nun eine IP4-Adresse gelesen wird
               * und der Zahlenzaehler global in dieser While-Schleife benutzt wird.
               */
              ip_adresse_zahlen_zaehler = 0;

              /*
               * Der Trennzeichenzaehler wird auf 0 gestellt.
               */
              ip_adresse_zaehler_trennzeichen = 0;

              /*
               * Die Kennzeichenvariable wird auf 2 gestellt, damit der Leseprozess 
               * in die IP4-Leseroutine verzweigt.
               */
              knz_ipv6 = 2;
            }
            else if ( aktuelles_zeichen == ':' )
            {
              /*
               * Doppel-Punkt (Trennzeichen IP-Adressangaben)
               */

              /*
               * Anzahl Trennzeichen
               * Es gibt maximal 8 Bloecke. 
               * Das ergibt eine maximale Anzahl von 7 Trennzeichen.
               * 
               * Es duerfen nicht mehr als 7 Trennzeichen gelesen werden. 
               * Beim 8ten Trennzeichen, wird der Fehler 42 zurueckgegeben.
               */
              ip_adresse_zaehler_trennzeichen++;

              if ( ip_adresse_zaehler_trennzeichen > 8 )
              {
                return 42; // IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
              }

              /*
               * Es duerfen einmal 2 Doppelpunkte hintereinander kommen.
               */

              /*
               * Pruefung: 2 Doppelpunkte hintereinander ?
               * Liegt die letzte Position eines Trennzeichens vor der aktuellen 
               * Lesepostion, wird der Zaehler fuer aufeinanderfolgende Trennzeichen 
               * um 1 erhoeht. Dieser Zaehler wird anschliessend auf den Wert 2 
               * geprueft. Ist der Wert 2, wird der Fehler 50 zurueckgegeben. 
               */
              if ( ( akt_index - position_letzter_punkt ) == 1 )
              {
                ip_adresse_anzahl_trennzeichen_hintereinander++;

                if ( ip_adresse_anzahl_trennzeichen_hintereinander == 2 )
                {
                  return 50; // IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
                }
              }

              /*
               * Sind alle Pruefungen fuer einen Punkt durchgefuehrt worden, 
               * wird die Position des letzten Punktes aktualisiert. 
               * 
               * Es wird der Zahlenzaehler und der Wert der aktuellen Zahl auf 0 gestellt.
               */
              position_letzter_punkt = akt_index;

              ip_adresse_zahlen_zaehler = 0;

              ip_adresse_akt_zahl = 0;
            }
            else if ( aktuelles_zeichen == ']' )
            {
              /*
               * IP6-Adressteil - Abschlusszeichen "]" 
               */

              if ( ip_adresse_zaehler_trennzeichen == 0 )
              {
                return 41; // IP6-Adressteil: Trennzeichenanzahl ist 0 
              }

              /*
               * Anzahl Trennzeichen
               * Fuer eine IP6-Adresse muessen mindestens 3 Trennzeichen gelesen worden sein. 
               * Ist das nicht der Fall, wird der Fehler 43 zurueckgegeben.
               */
              if ( ip_adresse_zaehler_trennzeichen < 3 )
              {
                return 43; // IP6-Adressteil: Zu wenig Trennzeichen  
              }

              /*
               * Kombination ":]"
               * 
               * "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]" 
               * "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"
               * 
               * - Es darf die Kombination "::" vorkommen, dass nur einmal. 
               *   Das wird weiter oben geprueft.
               *   
               * - Bei der Kombination ":]" kann es sein, dass eben der letzte Wert
               *   ausgelassen wurde und somit diese Kombination gueltig ist.
               * 
               */

              ///*
              // * Der letzte Punkt darf nicht auf der vorhergehenden Position 
              // * liegen. Ist das der Fall, wird der Fehler 44 zurueckgegeben. 
              // */
              //if ( ( akt_index - position_letzter_punkt ) == 1 )
              //{
              //  return 44; // IP6-Adressteil: ungueltige Kombination ":]"
              //}

              /*
               * Das Abschlusszeichen muss auf der letzten Stelle des
               * Eingabestrings liegen. Ist das nicht der Fall, wird 
               * 45 als Fehler zurueckgegeben.
               */
              if ( ( akt_index + 1 ) != laenge_eingabe_string )
              {
                /*
                 * Nach der IP-Adresse kann noch ein Kommentar kommen. 
                 * Aktuell muss der Kommentar sofort nach dem Abschlusszeichen kommen.
                 */
                if ( pEingabe.charAt( akt_index + 1 ) == '(' )
                {
                  /*
                   * Ist das naechste Zeichen nach "]" gleich die oeffnende Klammer,
                   * ist das zeichen OK. Es wird der Leseindex um eine Positon 
                   * verringert.
                   * 
                   * Der Leseprozess wird danach sofort in den Lesevorgang fuer die 
                   * Kommentare gehen.
                   * 
                   * ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)
                   */
                  akt_index--;
                }
                else if ( pEingabe.charAt( akt_index + 1 ) == ' ' )
                {
                  /*
                   * Ist das naechste Zeichen nach "]" ein Leerzeichen, ist der 
                   * Kommentar vom Ende der IP-Adresse durch Leerzeichen getrennt. 
                   * Das Leerzeichen ist OK, der Leseindes wird verringert. 
                   * 
                   * Der Leseprozess wird im naechsten Durchgang das Leerzeichen 
                   * erkennen und in den letzten else-Zweig gefuehrt werden. Dort 
                   * wird erkannt, dass der Leseprozess im Domain-Part ist und wird 
                   * die Leerzeichen ueberspringen.
                   * 
                   * ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)
                   */
                  akt_index--;
                }
                else
                {
                  return 45; // IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
                }
              }

              /*
               * Eine Pruefung auf den Zahlenzaehler bringt nichts. 
               * Der Zahlenzaehler ist im eventuellen Fehlerfall hier 0. 
               * Wuerde eine Zahl gelesen, wuerde ein Fehler bei den  
               * Zahlen geprueft werden.
               * 
               * Wuerde versucht werden, den Punkt und das Abschlusszeichen 
               * mit anderen Zeichen zu trennen, wuerde ein ungueltiges 
               * Zeichen erkannt werden. 
               */
            }
            else
            {
              return 49; // IP6-Adressteil: Falsches Zeichen in der IP-Adresse
            }
          }
          else
          {
            /*
             * Die IP-Adresse besteht nur aus Zahlen und Punkten mit einem 
             * abschliessendem "]"-Zeichen. Alle anderen Zeichen fuehren 
             * zu dem Fehler 59 = ungueltiges Zeichen.
             * 
             * Es wird nur eine IP4 Adresse geprueft.
             */
            if ( ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) ) )
            {
              /*
               * Zahlen
               * - nicht mehr als 3 Ziffern
               * - nicht groesser als 255 (= 1 Byte)
               */

              /*
               * Zahlenzaehler
               * Der Zahlenzaehler wird erhoeht.
               * Anschliessend wird geprueft, ob schon mehr als 3 Zahlen gelsen wurden. 
               * Ist das der Fall, wird 53 zurueckgegeben. 
               */
              ip_adresse_zahlen_zaehler++;

              if ( ip_adresse_zahlen_zaehler > 3 )
              {
                return 53; // IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
              }

              /*
               * Berechnung Akt-Zahl
               * Der Wert in der Variablen "akt_zahl" wird mit 10 multipliziert und 
               * der Wert des aktullen Zeichens hinzugezaehlt. 
               * 
               * Der Wert des aktuellen Zeichens ist der Wert des ASCII-Code abzueglich 48.
               * 
               * Anschliessend wird geprueft, ob die Zahl groesser als 255 ist. 
               * Ist die Zahl groesser, wird 54 zurueckgegeben. (Byteoverflow)
               */
              ip_adresse_akt_zahl = ( ip_adresse_akt_zahl * 10 ) + ( ( (int) aktuelles_zeichen ) - 48 );

              if ( ip_adresse_akt_zahl > 255 )
              {
                return 54; // IP4-Adressteil: Byte-Overflow
              }
            }
            else if ( aktuelles_zeichen == '.' )
            {
              /*
               * Punkt (Trennzeichen Zahlen)
               */

              /*
               * Pruefung: Zahlen vorhanden ?
               * 
               * Steht der Zahlenzaehler auf 0, wurden keine Zahlen gelesen. 
               * In diesem Fall wird 55 zurueckgegeben.
               */
              if ( ip_adresse_zahlen_zaehler == 0 )
              {
                return 55; // IP4-Adressteil: keine Ziffern vorhanden
              }

              /*
               * ANMERKUNG FEHLER 63 
               * Dieser Fehlercode kann nicht kommen, da bei 2 hintereinanderkommenden 
               * Punkten keine Zahl gelesen worden sein kann. Dieses ist die  
               * vorhergehende Pruefung.
               * 
               * Es kann auch kein anderes Zeichen gelesen worden sein, das 
               * wuerde einen anderen Fehler verursachen.
               */
              // 
              // /*
              //  * Pruefung: 2 Punkte hintereinander ?
              //  * Die letzte Position eines Punktes, darf nicht vor der aktuellen 
              //  * Lesepostion liegen. Ist das der Fall, wird 63 zurueckgegeben.
              //  */
              // if ( ( akt_index - position_letzter_punkt ) == 1 )
              // {
              //   return 63; // IP4-Adressteil: keine 2 Punkte hintereinander
              // }

              /*
               * Anzahl Trennzeichen
               * Es duerfen nicht mehr als 3 Punkte (=Trennzeichen) gelesen werden. 
               * Beim 4ten Punkt, wird 56 zurueckgegeben.
               */
              ip_adresse_zaehler_trennzeichen++;

              if ( ip_adresse_zaehler_trennzeichen > 3 )
              {
                return 56; // IP4-Adressteil: zu viele Trennzeichen
              }

              /*
               * Sind alle Pruefungen fuer einen Punkt durchgefuehrt worden, 
               * wird die Position des letzten Punktes aktualisiert. 
               * 
               * Es wird der Zahlenzaehler und der Wert der aktuellen Zahl auf 0 gestellt.
               */
              position_letzter_punkt = akt_index;

              ip_adresse_zahlen_zaehler = 0;

              ip_adresse_akt_zahl = 0;
            }
            else if ( aktuelles_zeichen == ']' )
            {
              /*
               * IP4-Adressteil - Abschlusszeichen "]"
               */

              /*
               * Anzahl Trennzeichen
               * Fuer eine IP4-Adresse muessen 3 Punkte gelesen worden sein. 
               * Ist das nicht der Fall, wird 57 zurueckgegeben.
               */
              if ( ip_adresse_zaehler_trennzeichen != 3 )
              {
                return 57; // IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein 
              }

              /*
               * Der letzte Punkt darf nicht auf der vorhergehenden Position 
               * liegen. Ist das der Fall, wird 58 zurueckgegeben. 
               */
              if ( ( akt_index - position_letzter_punkt ) == 1 )
              {
                return 58; // IP4-Adressteil: ungueltige Kombination ".]"
              }

              /*
               * Das Abschlusszeichen muss auf der letzten Stelle des
               * Eingabestrings liegen. Ist das nicht der Fall, wird 
               * 60 als Fehler zurueckgegeben.
               */
              if ( ( akt_index + 1 ) != laenge_eingabe_string )
              {
                /*
                 * Nach der IP-Adresse kann noch ein Kommentar kommen. 
                 * Aktuell muss der Kommentar sofort nach dem Abschlusszeichen kommen.
                 * 
                 * ABC.DEF@[1.2.3.4](comment)
                 */
                if ( pEingabe.charAt( akt_index + 1 ) == '(' )
                {
                  /*
                   * Korrektur des Leseindexes
                   */
                  akt_index--;
                }
                else if ( pEingabe.charAt( akt_index + 1 ) == ' ' )
                {
                  /*
                   * Korrektur des Leseindexes
                   */
                  akt_index--;
                }
                else
                {
                  return 60; // IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
                }
              }

              /*
               * Eine Pruefung auf den Zahlenzaehler bringt nichts. 
               * Der Zahlenzaehler ist im eventuellen Fehlerfall hier 0. 
               * Wuerde eine Zahl gelesen, wuerde ein Fehler bei den  
               * Zahlen geprueft werden.
               * 
               * Wuerde versucht werden, den Punkt und das Abschlusszeichen 
               * mit anderen Zeichen zu trennen, wuerde ein ungueltiges 
               * Zeichen erkannt werden. 
               */
            }
            else
            {
              return 59; // IP4-Adressteil: Falsches Zeichen in der IP-Adresse
            }
          }

          akt_index++;
        }

        /*
         * Pruefung: Abschluss mit ']' ?
         * 
         * Ist die IP-Adressangabe korrekt, steht nach der While-Schleife das abschliessende 
         * Zeichen ']' in der Variablen "aktuelles_zeichen". Ist in der Variablen ein anderes 
         * Zeichen vorhanden, wird der Fehler 61 zurueckgebeben. 
         */
        if ( aktuelles_zeichen != ']' )
        {
          return 61; // IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
        }

        /*
         * Es wurde eine IP-Adresse erkannt, der Wert in der 
         * Variablen "fkt_ergebnis_email_ok" wird um 2 erhoeht.
         */
        fkt_ergebnis_email_ok += ( knz_ipv6 == 0 ? 2 : 4 );

        /*
         * Index Leseposition nach IP-Adresseinlesung
         * 
         * Bei einer korrekten IP-Adresse, wurde in der While-Schleife die Leseposition um 
         * eine Position zu weit erhoeht. Da die eMail-Adresse in diesem Fall auch korrekt
         * beendet wurde, wird auch in der auesseren While-Schleife die Position nochmals
         * erhoeht. Die aeussere While-Schleife ist aber dann auch beendet. 
         * 
         * Nach der aeusseren While-Schleife kommen keine weiteren Pruefungen auf den 
         * Leseindex, weshalb auf eine Korrektur der Leseposition verzichtet werden kann.
         * 
         * Ist die IP-Adressangabe falsch, wird die innere While-Schleife vorzeitig
         * mit einem Fehlercode verlassen. 
         */
      }
      else if ( aktuelles_zeichen == '(' )
      {
        /*
         * While-Schleife 4 - Kommentare
         */

        /*
         * Kommentare sind am Anfang oder am Ende des Local-Parts erlaubt.
         * Im Domain-Part sind keine Kommentare zugelassen
         */
        if ( position_at_zeichen > 0 )
        {
          /*
           * Kombination ".(" pruefen Domain-Part.
           */
          if ( ( position_letzter_punkt > position_at_zeichen ) && ( ( akt_index - position_letzter_punkt ) == 1 ) )
          {
            return 102; // Kommentar: Falsche Zeichenkombination ".(" im Domain Part
          }
        }
        else
        {
          /*
           * Kombination ".(" pruefen Local Part
           * 
           * Wird eine einleitende Klammer gefunden, darf der letzte Punkt nicht 
           * vor der Klammer liegen. 
           * 
           * Die Position des letzten Punktes muss groesser als 0 sein. Bei weglassen 
           * dieser Pruefung, kommt es zu einem Seiteneffekt mit dem Initialwert 
           * von -1 der Variablen fuer den letzten Punkt. 
           * 
           * Liegt eine falsche Zeichenkombination vor, wird der Fehler 101 zurueckgegeben.
           */
          if ( ( position_letzter_punkt > 0 ) && ( ( akt_index - position_letzter_punkt ) == 1 ) )
          {
            return 101; // Kommentar: Falsche Zeichenkombination ".(" im Local Part
          }
        }

        /*
         * Wurde schon ein Kommentar gelesen, darf kein zweiter Kommentar
         * zugelassen werden. Es wird der Feler 99 zurueckgegeben.
         */
        if ( position_kommentar_ende > 0 )
        {
          return 99; // Kommentar: kein zweiter Kommentar gueltig
        }

        /*
         * Innere While-Schleife
         * In einer inneren While-Schleife wird die IP-Adresse geparst und 
         * auf Gueltigkeit geprueft. Die innere While-Schleife laeuft bis
         * zum Stringende der Eingabe.
         */

        /*
         * Befindet sich der Leseprozess nicht am Anfang der eMail-Adresse, 
         * muss nach dem Abschlusszeichen das AT-Zeichen folgen. 
         * 
         * Kommentart innerhalb des lokalen Parts sind nicht erlaubt. 
         */
        boolean knz_abschluss_mit_at_zeichen = ( akt_index > 0 );

        knz_abschluss_mit_at_zeichen = ( akt_index == email_local_part_gesamt_start ) == false;

        if ( position_at_zeichen > 0 )
        {
          /*
           * Wurde schon ein AT-Zeichen gelesen, muss der Kommentar nicht auch einem AT-Zeichen enden. 
           */
          knz_abschluss_mit_at_zeichen = false;

          /*
           * Sind schon Zeichen nach dem AT-Zeichen gelesen worden, muss der 
           * Kommentar am Stringende enden.
           * 
           * Endet der Kommentar vor dem Stringende, steht der Kommentar mitten 
           * im eMail-String und ist somit falsch.
           */
          if ( ( akt_index - position_at_zeichen ) > 1 )
          {
            knz_kommentar_abschluss_am_stringende = true;
          }
        }

        position_kommentar_start = akt_index;

        akt_index++;

        aktuelles_zeichen = 'A';

        while ( ( akt_index < laenge_eingabe_string ) && ( aktuelles_zeichen != ')' ) )
        {
          /*
           * Aktuelles Pruefzeichen
           * Das aktuelle Zeichen wird aus der Eingabe am aktuellen Index herausgelesen. 
           */
          aktuelles_zeichen = pEingabe.charAt( akt_index );

          /*
           * Alle erlaubten Stringzeichen werden durchgelassen. 
           * Falsche Zeichen erzeugen einen Fehler.
           */

          if ( ( ( aktuelles_zeichen >= 'a' ) && ( aktuelles_zeichen <= 'z' ) ) || ( ( aktuelles_zeichen >= 'A' ) && ( aktuelles_zeichen <= 'Z' ) ) )
          {
            // OK - Buchstaben
          }
          else if ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) )
          {
            // OK - Zahlen
          }
          else if ( ( aktuelles_zeichen == '_' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '@' ) || ( aktuelles_zeichen == '.' ) || ( aktuelles_zeichen == ' ' ) || ( aktuelles_zeichen == '!' ) || ( aktuelles_zeichen == '#' ) || ( aktuelles_zeichen == '$' ) || ( aktuelles_zeichen == '%' ) || ( aktuelles_zeichen == '&' ) || ( aktuelles_zeichen == '\'' ) || ( aktuelles_zeichen == '*' ) || ( aktuelles_zeichen == '+' ) || ( aktuelles_zeichen == '-' ) || ( aktuelles_zeichen == '/' ) || ( aktuelles_zeichen == '=' ) || ( aktuelles_zeichen == '?' ) || ( aktuelles_zeichen == '^' ) || ( aktuelles_zeichen == '`' ) || ( aktuelles_zeichen == '{' ) || ( aktuelles_zeichen == '|' ) || ( aktuelles_zeichen == '}' ) || ( aktuelles_zeichen == '~' ) )
          {
            // OK - Sonderzeichen
          }
          else if ( aktuelles_zeichen == ')' )
          {
            // OK - abschliessendes Anfuehrungszeichen 

            /*
             * Der Leseprozessindex muss nach der While-Schleife auf dem jetzt
             * gerade gueltigen Index stehen. 
             * 
             * Am Ende dieser inneren While-Schleife wird der Leseprozess um 
             * eine Position weiter gestellt. Der Leseprozessindex wird zum 
             * Ausgleich hier um eine Position verringert.
             * 
             * Die aeussere While-Schleife erhoeht schlussendlich den Leseprozess, 
             * damit dieser dann auf das Zeichen nach dem hier gefundenen 
             * Anfuehrungszeichen verarbeiten kann. 
             */
            akt_index--;
          }
          else if ( aktuelles_zeichen == '\\' )
          {
            /*
             * Maskiertes Zeichen 
             * Der Leseprozess muss nach naechste Zeichen pruefen. 
             * Der Leseprozessindex wird um ein Zeichen weiter gestellt.
             */
            akt_index++;

            /*
             * Pruefung: Stringende ?
             */
            if ( akt_index == laenge_eingabe_string )
            {
              return 96; // Kommentar: Escape-Zeichen nicht am Ende der Eingabe
            }

            /*
             * Zeichen nach dem Backslash lesen. 
             * Das Zeichen darf ein Backslash oder ein Anfuehrungszeichen sein. 
             * Alle anderen Zeichen fuehren zum Fehler 91.
             */
            aktuelles_zeichen = pEingabe.charAt( akt_index );

            if ( ( aktuelles_zeichen != '\\' ) && ( aktuelles_zeichen != '"' ) )
            {
              return 91; // Kommentar: Ungueltige Escape-Sequenz im Kommentar
            }
          }
          else
          {
            /*
             * Fehler 92
             * Dieser Fehler wird zurueckgegeben, wenn ein ungueltiges 
             * Zeichen im Kommentar vorhanden ist.
             */
            return 92;// Kommentar: Ungueltiges Zeichen im Kommentar
          }

          /*
           * Der Leseprozessindex wird um eine Position weitergestellt.
           */
          akt_index++;
        }

        /*
         * Pruefung: Wurde eine abschliessende Klammer gefunden ? 
         * 
         * Nach der While-Schleife muss die Variablen "aktuelles_zeichen" eine 
         * abschliessende Klammer sein. Nur dann wurde die Einleseschleife 
         * korrekt beendet. 
         * 
         * Steht ein anderes Zeichen in der Variablen, ist der Leseprozess 
         * am Stringende der Eingabe angekommen. Das ist die zweite Moeglichkeit, 
         * wie die While-Schleife beendet werden kann. 
         */
        if ( aktuelles_zeichen != ')' )
        {
          return 93; // Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
        }

        /*
         * Pruefung: Leseprozess am Stringende angelangt ?
         * 
         * Wurde die While-Schleife korrekt beendet, kann es sein, dass die 
         * abschliessende Klammer am Stringende steht.  
         */
        if ( akt_index + 1 >= laenge_eingabe_string )
        {
          if ( position_at_zeichen > 0 )
          {
            /*
             * Im Domain-Part darf der Kommentar am Stringende aufhoeren
             */
          }
          else
          {
            return 95; // Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
          }
        }
        else
        {
          /*
           * Pruefung: naechstes Zeichen gleich AT-Zeichen ?
           */
          if ( pEingabe.charAt( akt_index + 1 ) == '@' )
          {
            if ( knz_abschluss_mit_at_zeichen == false )
            {
              return 98; // Kommentar: Kein lokaler Part vorhanden
            }
          }
          else if ( pEingabe.charAt( akt_index + 1 ) == '.' )
          {
            return 103; // Kommentar: Falsche Zeichenkombination ")."
          }
          else
          {
            if ( knz_abschluss_mit_at_zeichen )
            {
              return 97; // Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
            }
          }

          if ( knz_kommentar_abschluss_am_stringende )
          {
            return 100; // Kommentar: Kommentar muss am Strinende enden
          }
        }

        /*
         * Die Position der abschliessenden Klammer wird gespeichert. 
         */
        position_kommentar_ende = akt_index;
      }
      else
      {
        /*
         * Sonderbedingung: Leerzeichentrennung bis Kommentar im Domain-Part
         * 
         * "email@domain.com (joe Smith)"
         * 
         * Ist das aktuelle Zeichen ein Leerzeichen und der Leseprozess befindet 
         * sich im Domainpart (position_at_zeichen > 0), dann muss geprueft werden, 
         * ob nach den Leerzeichen eine oeffnende Klammer kommt.
         */
        if ( ( aktuelles_zeichen == ' ' ) && ( position_at_zeichen > 0 ) )
        {
          /*
           * aktuelles Zeichen konsumieren, bzw. Lespositon 1 weiterstellen
           */
          akt_index++;

          /*
           * Ueberlese alle Leerzeichen in einer While-Schleife. 
           */
          while ( ( akt_index < laenge_eingabe_string ) && ( pEingabe.charAt( akt_index ) == ' ' ) )
          {
            akt_index++;
          }

          /*
           * Wurde in der While-Schleife bis zum Eingabeende gelesen, 
           * wird der Fehler 22 zurueckgegeben, da das Leerzeichen 
           * falsch ist.
           */
          if ( akt_index == laenge_eingabe_string )
          {
            return 22;
          }

          /*
           * Nach der While-Schleife muss das Zeichen an der aktuellen 
           * Leseposition eine oeffnende Klammer sein. Alle anderen 
           * Zeichen fuehren zu einem Fehler, da das einleitende
           * Leerzeichen ein falsches Zeichen war. Es wird in diesem 
           * Fall der Fehler 105 zurueckgegeben. 
           */
          if ( pEingabe.charAt( akt_index ) == '[' )
          {
            // return 106; // Kommentar: Leerzeichentrennung im Domain-Part. 

            akt_index--;
          }
          else if ( pEingabe.charAt( akt_index ) != '(' )
          {
            return 105; // Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
          }
          else
          {
            /*
             * In der While-Schleife wurde die Leseposition einmal zu viel erhoeht.
             * Die Leseposition wird um eine Position verringert.
             * Da sich der Leseprozess im Domain-Part befindet gibt es ein vorhergehendes Zeichen.
             */
            akt_index--;
          }
        }
        else
        {
          /*
           * Hier koennen noch die Zeichen fuer internationale eMail-Adressen hinterlegt werden,
           * welche durchgelassen werden sollen. 
           * 
           * Die If-Abfrage waere denn einzukommentieren. 
           */
          //if ( ( aktuelles_zeichen == 'Ã¤' ) || ( aktuelles_zeichen == 'Ã¶' ) ) 
          //{
          //  OK 
          //}
          //else
          //{

          return 22; // Zeichen: ungueltiges Zeichen in der Eingabe gefunden

          //}
        }
      }

      /*
       * Leseposition erhoehen
       * Sind alle Pruefungen fuer das aktuelle Zeichen gemacht worden, 
       * wird die Leseposition um eins erhoeht und das naechste Zeichen
       * der Eingabe wird geprueft. 
       */
      akt_index++;
    }

    /*
     * Pruefungen nach der While-Schleife
     */

    /*
     * Pruefung: Punkt gefunden ?
     * Bei einer IP-Adressangabe OK, da auch dort Punkte gefunden werden muessen.
     * Bei einer IP6-Adressangabe wird die Variable "position_letzter_punkt" fuer 
     * die Doppelpunkte ":" in der Adressangabe benutzt. 
     */
    if ( ( position_letzter_punkt == -1 ) || ( position_letzter_punkt == position_at_zeichen ) )
    {
      return 34; // Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
    }

    /*
     * Pruefung: AT-Zeichen gefunden ?
     * Ist der Wert der Variablen "position_at_zeichen" gleich -1, wurde kein AT-Zeichen 
     * gefunden. Es wird der Fehler 28 zurueckgegeben.
     *  
     * Bei einer IP-Adressangabe OK, da auch dort ein AT-Zeichen vorhanden sein muss.
     */
    if ( position_at_zeichen == -1 )
    {
      return 28; // AT-Zeichen: kein AT-Zeichen gefunden
    }

    /*
     * Pruefung: EMail-Adresse ohne IP-Adressangabe ?
     * 
     * Handelt es sich um eine eMail-Adresse ohne IP-Adresse, ist der Wert in der 
     * Variablen "fkt_ergebnis_email_ok" kleiner als 2. 
     * 
     * Wenn dem so ist, muessen noch Abschlusspruefungen bezueglich des letzten 
     * Punktes gemacht werden.
     */
    if ( fkt_ergebnis_email_ok < 2 )
    {
      /*
       * Pruefung: Letzter Punkt nach AT-Zeichen ?
       * Die Position des letzten Punktes muss groesser sein als die Position 
       * des AT-Zeichens. Ist die Punkt-Position kleiner als die Position des 
       * AT-Zeichens wird der Fehler 35 zurueckgegeben.
       *  
       * Bei einer IP-Adressangabe OK, da dort die gleichen Bedingungen gelten.
       */
      if ( position_letzter_punkt < position_at_zeichen )
      {
        return 35; // Trennzeichen: der letzte Punkt muss nach dem AT-Zeichen liegen (... hier eben die negative Form, wenn der letzte Punkt vor dem AT-Zeichen stand ist es ein Fehler)
      }

      /*
       * Pruefung: Letzter Punkt gleich Stringende ?
       * Die Position des letzten Punktes darf nicht am Stringende liegen. 
       * Liegt der letzte Punkt am Stringende, wird der Fehler 36 zurueckgegeben.
       *  
       * Bei einer IP-Adressangabe OK, da dort die gleichen Bedingungen gelten.
       */
      if ( ( position_letzter_punkt + 1 ) == laenge_eingabe_string )
      {
        return 36; // Trennzeichen: der letzte Punkt darf nicht am Ende liegen
      }

      int laenge_tld = 0;

      if ( ( position_letzter_punkt > position_at_zeichen ) && ( position_kommentar_start > position_letzter_punkt ) )
      {
        //laenge_tld = position_kommentar_start - position_letzter_punkt;

        laenge_tld = pEingabe.substring( position_letzter_punkt, position_kommentar_start ).trim().length();
      }
      else
      {
        laenge_tld = laenge_eingabe_string - ( position_letzter_punkt + 1 );
      }

      /*
       * https://stackoverflow.com/questions/15537384/email-address-validation-of-top-level-domain
       */
      if ( laenge_tld < 2 )
      {
        return 14; // Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
      }

      /*
       * https://stackoverflow.com/questions/9238640/how-long-can-a-tld-possibly-be/9239264
       */
      if ( laenge_tld > 63 )
      {
        return 15; // Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
      }

      /*
       * https://stackoverflow.com/questions/9071279/number-in-the-top-level-domain
       */

      aktuelles_zeichen = pEingabe.charAt( position_letzter_punkt + 1 );

      if ( ( aktuelles_zeichen >= '0' ) && ( aktuelles_zeichen <= '9' ) )
      {
        return 23; // Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
      }

      //if ( zahlen_zaehler > 0 ) { return 19; } // Laenge: Top-Level-Domain darf keine Zahlen haben ... oder doch ?
    }

    /*
     * Ergebnisanpassung
     * 
     * Pruefung: Wurde ein Kommentar gefunden ?
     * 
     * Enthaelt die eMail-Adresse einen Kommentar, wird der Ergebniswert angepasst. 
     * Diese Anpassung erfolgt ueber eine If-Konstruktion.
     */
    if ( position_kommentar_ende > 0 )
    {
      if ( fkt_ergebnis_email_ok == 0 )
      {
        fkt_ergebnis_email_ok = 6; // "eMail-Adresse korrekt (Kommentar)" 
      }
      else if ( fkt_ergebnis_email_ok == 1 )
      {
        fkt_ergebnis_email_ok = 7; // "eMail-Adresse korrekt (Kommentar, String)"
      }
      else if ( fkt_ergebnis_email_ok == 3 )
      {
        fkt_ergebnis_email_ok = 8; // "eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)";
      }
      else if ( fkt_ergebnis_email_ok == 5 )
      {
        fkt_ergebnis_email_ok = 9; // "eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)"
      }

      /*
       * Die Rueckgabewerte 2 und 4 werden nicht konvertiert, da 
       * ... mir die Ergebniszahlen ausgehen (eMail-Adresse OK muss kleiner 10 bleiben)
       * ... es die Sonderfaelle einer eMail-Adresse mit einer IP-Angabe sind (das zaehlt mehr, als ein Kommentar)
       */
    }

    /*
     * Sind alle Pruefungen bis hier ohne Fehler durchgefuehrt worden, stimmt 
     * die Eingabe mit der Struktur einer eMail-Adresse ueberein.
     * 
     * Es ist hinreichend geprueft, dass die Eingabe eine eMail-Adresse ist (sein kann).
     * Der Aufrufer bekommt den Wert der Variablen "fkt_ergebnis_email_ok" zurueck.
     * 
     * ALTERNATIV: - alle Fehler als negative Zahlen zurueckgeben.
     *             - Position des At-Zeichens zurueckgeben, damit die eMail-Adresse 
     *               schneller in Lokal- und Domain-Part zu trennen ist
     */
    return fkt_ergebnis_email_ok; // eMail-Adresse korrekt
  }

}
