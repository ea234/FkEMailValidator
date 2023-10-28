package de.fk.email;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import com.sanctionco.jmail.JMail;

class TestClassValidateEMailAdresse
{
  /*
   *   x_ergebnis_alt null                           = -1   
   *   x_ergebnis_neu null                           = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt ""                             = 0    
   *   x_ergebnis_neu ""                             = 0     = OK 
   *   x_ergebnis_alt "        "                     = 0    
   *   x_ergebnis_neu "        "                     = 0     = OK 
   *   x_ergebnis_alt "A.B@C.DE"                     = 1    
   *   x_ergebnis_neu "A.B@C.DE"                     = 1     = OK 
   *   x_ergebnis_alt "A."B"@C.DE"                   = 0    
   *   x_ergebnis_neu "A."B"@C.DE"                   = 0     = OK 
   *   x_ergebnis_alt "A.B@[1.2.3.4]"                = 0    
   *   x_ergebnis_neu "A.B@[1.2.3.4]"                = 0     = OK 
   *   x_ergebnis_alt "(A)B@C.DE"                    = 0    
   *   x_ergebnis_neu "(A)B@C.DE"                    = 0     = OK 
   *   x_ergebnis_alt "A(B)@C.DE"                    = 0    
   *   x_ergebnis_neu "A(B)@C.DE"                    = 0     = OK 
   *   x_ergebnis_alt "(A)B@[1.2.3.4]"               = 0    
   *   x_ergebnis_neu "(A)B@[1.2.3.4]"               = 0     = OK 
   *   x_ergebnis_alt "A(B)@[1.2.3.4]"               = 0    
   *   x_ergebnis_neu "A(B)@[1.2.3.4]"               = 0     = OK 
   *   x_ergebnis_alt "(A)B@[IPv6:1:2:3:4:5:6:7:8]"  = 0    
   *   x_ergebnis_neu "(A)B@[IPv6:1:2:3:4:5:6:7:8]"  = 0     = OK 
   *   x_ergebnis_alt "A(B)@[IPv6:1:2:3:4:5:6:7:8]"  = 0    
   *   x_ergebnis_neu "A(B)@[IPv6:1:2:3:4:5:6:7:8]"  = 0     = OK 
   *   x_ergebnis_alt "A@B.CD"                       = 1    
   *   x_ergebnis_neu "A@B.CD"                       = 1     = OK 
   *   x_ergebnis_alt "ABC1.DEF2@GHI3.JKL4"          = 0    
   *   x_ergebnis_neu "ABC1.DEF2@GHI3.JKL4"          = 1     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF_@GHI.JKL"             = 1    
   *   x_ergebnis_neu "ABC.DEF_@GHI.JKL"             = 1     = OK 
   *   x_ergebnis_alt "#ABC.DEF@GHI.JKL"             = 0    
   *   x_ergebnis_neu "#ABC.DEF@GHI.JKL"             = 1     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABCDEFGHIJKLMNOP"             = 0    
   *   x_ergebnis_neu "ABCDEFGHIJKLMNOP"             = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@GHI.J"                = 0    
   *   x_ergebnis_neu "ABC.DEF@GHI.J"                = 0     = OK 
   *   x_ergebnis_alt "ME@MYSELF.LOCALHORST"         = 0    
   *   x_ergebnis_neu "ME@MYSELF.LOCALHORST"         = 1     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF@GHI.2KL"              = 0    
   *   x_ergebnis_neu "ABC.DEF@GHI.2KL"              = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@GHI.JK-"              = 0    
   *   x_ergebnis_neu "ABC.DEF@GHI.JK-"              = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@GHI.JK_"              = 0    
   *   x_ergebnis_neu "ABC.DEF@GHI.JK_"              = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@GHI.JK2"              = 0    
   *   x_ergebnis_neu "ABC.DEF@GHI.JK2"              = 1     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF@2HI.JKL"              = 1    
   *   x_ergebnis_neu "ABC.DEF@2HI.JKL"              = 1     = OK 
   *   x_ergebnis_alt "ABC.DEF@-HI.JKL"              = 1    
   *   x_ergebnis_neu "ABC.DEF@-HI.JKL"              = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF@_HI.JKL"              = 0    
   *   x_ergebnis_neu "ABC.DEF@_HI.JKL"              = 0     = OK 
   *   x_ergebnis_alt "A . B & C . D"                = 0    
   *   x_ergebnis_neu "A . B & C . D"                = 0     = OK 
   *   x_ergebnis_alt "(?).[!]@{&}.<:>"              = 0    
   *   x_ergebnis_neu "(?).[!]@{&}.<:>"              = 0     = OK 
   *   x_ergebnis_alt ".ABC.DEF@GHI.JKL"             = 1    
   *   x_ergebnis_neu ".ABC.DEF@GHI.JKL"             = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC..DEF@GHI.JKL"             = 1    
   *   x_ergebnis_neu "ABC..DEF@GHI.JKL"             = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF@GHI..JKL"             = 1    
   *   x_ergebnis_neu "ABC.DEF@GHI..JKL"             = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF@GHI.JKL.."            = 0    
   *   x_ergebnis_neu "ABC.DEF@GHI.JKL.."            = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF.@GHI.JKL"             = 1    
   *   x_ergebnis_neu "ABC.DEF.@GHI.JKL"             = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF@."                    = 0    
   *   x_ergebnis_neu "ABC.DEF@."                    = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@.GHI.JKL"             = 1    
   *   x_ergebnis_neu "ABC.DEF@.GHI.JKL"             = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABC.DEF.@GHI.JKL"             = 1    
   *   x_ergebnis_neu "ABC.DEF.@GHI.JKL"             = 0     = ########## FEHLER ##########
   *   x_ergebnis_alt "ABCDEF@GHIJKL"                = 0    
   *   x_ergebnis_neu "ABCDEF@GHIJKL"                = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@GHI.JKL."             = 0    
   *   x_ergebnis_neu "ABC.DEF@GHI.JKL."             = 0     = OK 
   *   x_ergebnis_alt "@GHI.JKL"                     = 0    
   *   x_ergebnis_neu "@GHI.JKL"                     = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@"                     = 0    
   *   x_ergebnis_neu "ABC.DEF@"                     = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF\@"                    = 0    
   *   x_ergebnis_neu "ABC.DEF\@"                    = 0     = OK 
   *   x_ergebnis_alt "ABC.DEF@@GHI.JKL"             = 0    
   *   x_ergebnis_neu "ABC.DEF@@GHI.JKL"             = 0     = OK 
   *   x_ergebnis_alt "ABC\@DEF@GHI.JKL"             = 0    
   *   x_ergebnis_neu "ABC\@DEF@GHI.JKL"             = 1     = ########## FEHLER ##########
   *  
   *   ALT  - Anzahl 2000 = MS      772    0.3860000
   *  
   *   NEU  - Anzahl 2000 = MS      162    0.0810000
   *  
   *   ALT  - Anzahl 2000 = MS      682    0.3410000
   *  
   *   NEU  - Anzahl 2000 = MS       51    0.0255000
   *  
   *   NEU  - Anzahl 2000 = MS       50    0.0250000
   *  
   *   ALT  - Anzahl 2000 = MS      590    0.2950000
   *  
   *   ALT  - Anzahl 2000 = MS      651    0.3255000
   *  
   *   NEU  - Anzahl 2000 = MS       64    0.0320000
   *  
   *   NEU  - Anzahl 2000 = MS       50    0.0250000
   *  
   *   ALT  - Anzahl 2000 = MS      731    0.3655000
   *  
   *  MS ALT =    3426 = 1.7130000
   *  MS NEU =     377 = 0.1885000
   *  
   *  MS schneller = 9.087533156498674
   *  
   *  ALT Anzahl Aufrufe in 1 Sekunde =        583
   *  NEU Anzahl Aufrufe in 1 Sekunde =       5305
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
        x_erg = isValidEmailAddresseRegEx1a( pEingabe ) ? 1 : 0;
        //x_erg = isValidEmailAddresseRegEx1b( pEingabe ) ? 1 : 0;
        //x_erg = checkJMail( pEingabe ) ? 1 : 0;
      }
      else
      {
        x_erg = FkEMail.checkEMailAdresse( pEingabe ) == 0 ? 1 : 0;
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
}
