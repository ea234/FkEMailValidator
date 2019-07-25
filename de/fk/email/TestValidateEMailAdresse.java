package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator; // https://github.com/bbottema/email-rfc2822-validator
// import emailvalidator4j.EmailValidator;                                 // https://github.com/egulias/EmailValidator4J

/**
 * Testclass: Speed an Accurancy
 *  
 * Feel free to add your testcases to the List, or replace the list at all.
 * 
 * A cool speed-test is only one eMail-Adresse "A@A.AA".
 * 
 * Yes, the variable names are all in german.
 */
class TestValidateEMailAdresse
{ 
  /*
   * Testdaten
   *
   *     0 "ABC.DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *     1 "A@B.CD"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *     2 "A@A.AA"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *     3 "\"ABC.DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     4 "\"ABC DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     5 "\"ABC@DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     6 "A.B@C.DE"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *     7 "A.\"B\"@C.DE"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     8 "A.B@[1.2.3.4]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *     9 "A.\"B\"@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *    10 "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    11 "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *    12 "(A)B@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *    13 "A(B)@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *    14 "(A)\"B\"@C.DE"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    15 "\"A\"(B)@C.DE"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    16 "(A)B@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    17 "A(B)@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    18 "(A)\"B\"@[1.2.3.4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    19 "\"A\"(B)@[1.2.3.4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    20 "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    21 "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    22 "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    23 "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    24 "ABC-DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    25 "ABC\@DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    26 "$ABCDEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    27 null                                                                                  Jmail EXP     REGEXP 1 EXP     REGEXP 2 EXP     REGEXP 3 EXP     JAVA 1 EXP     ea234 false    = 10 = Laenge: Eingabe ist null
   *    28 ""                                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 11 = Laenge: Eingabe ist Leerstring
   *    29 "               "                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    30 "ABCDEFGHIJKLMNO"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    31 "ABC.DEF_@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    32 "#ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    33 "ABC.DEF@GHI.2KL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *    34 "ABC.DEF@GHI.JK2"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    35 "ABC.DEF@2HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    36 "ABC.DEF@GHI.JK-"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    37 "ABC.DEF@GHI.JK_"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    38 "ABC.DEF@-HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    39 "ABC.DEF@_HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    40 ".ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *    41 "ABC.DEF@GHI..JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    42 "ABC.DEF@.GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    43 "ABC.DEF.@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *    44 "ABC.DEF@@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    45 " A . B & C . D"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    46 "ABC.DEF@GHI.JKL."                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *    47 "\".ABC.DEF\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    48 "\"ABC.DEF.\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    49 "\"\"@GHI.JKL"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *    50 "\" \"@GHI.JKL"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    51 "ABC.DEF@\"\""                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    52 "ABC.DEF\"GHI.JKL"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    53 "ABC.DEF\"@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    54 "ABC.DEF.\""                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *    55 "ABC.DE\"F@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    56 "ABC.DEF@G\"HI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    57 "ABC.DEF@\"GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    58 "ABC.DEF@GHI.JKL\""                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    59 "\"ABC.DEF@G\"HI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    60 "\"ABC.DEF@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *    61 "\"ABC.D\"EF@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    62 "\"AB\"C.D\"EF@GHI.JKL"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    63 "\"AB\"C.D\"EF\"@GHI.JKL"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    64 "@G\"HI.DE"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    65 "\"@GHI.JKL"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *    66 "\"\"@[]"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *    67 "\"@\".A(@)@a.aa"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    68 "ABC DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    69 "\"ABC@DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    70 "\"ABC DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    71 "\"ABC\".DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    72 "A\"BC\".DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *    73 "\"AB\"C.DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    74 "\"ABC\".DEF.\"GHI\"J@KL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    75 "\"ABC\"\".DEF.\"GHI\"@GHI.JKL"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    76 "\"Gueltige\\"Escape\\Sequenz\"@korrekt.de"                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    77 "\"Falsche\#Escape\GSequenz\"@falsch.de"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *    78 "\"Falsch.da.Escape.Zeichen.am.Ende.steht\"                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 83 = String: Escape-Zeichen nicht am Ende der Eingabe
   *    79 "ABC.DEF@[1.12.123.255]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    80 "ABC.DEF@[001.012.123.255]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    81 "\"ABC.DEF\"@[127.0.0.1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *    82 "ABC.DEF@[1.12.123.259]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *    83 "ABC.DEF@[1..123.255]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *    84 "ABC.DEF@[1.123.25.]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *    85 "ABC.DEF@[..]"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *    86 "ABC.DEF@[1.2.]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    87 "ABC.DEF@[]"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    88 "ABC.DEF@[1]"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    89 "ABC.DEF@[1.2]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    90 "ABC.DEF@[1.2.3]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    91 "ABC.DEF@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    92 "ABC.DEF@[1.2.3.4.5]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *    93 "ABC.DEF@[MyDomain.de]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *    94 "ABC.DEF@[1.00002.3.4]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *    95 "ABC.DEF[1.12.123.255]"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *    96 "ABC.DEF@[1.12.123.255].de"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *    97 "ABC.DEF@[1.2.3.]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *    98 "ABC.DEF@[1.2.3. ]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *    99 "ABC.DEF@[.2.3.4]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   100 "ABC.DEF@[ .2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   101 "ABC.DEF@MyDomain[1.2.3.4]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   102 "ABC.DEF@[1.2.3.4][5.6.7.8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   103 "[1.2.3.4]@[5.6.7.8]"                                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   104 "\"[1.2.3.4]\"@[5.6.7.8]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   105 "ABC.DEF[@1.2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   106 "ABC.DEF@[1.2.3.4"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   107 "ABC.DEF@[0.0.0.0]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   108 "ABC.DEF@[000.000.000.000]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   109 "ABC@[IPv6:]"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   110 "ABC@[IPv6:1]"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   111 "ABC@[IPv6:1:2]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   112 "ABC@[IPv6:1:2:3]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   113 "ABC@[IPv6:1:2:3:4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   114 "ABC@[IPv6:1:2:3:4:5]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   115 "ABC@[IPv6:1:2:3:4:5:6]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   116 "ABC@[IPv6:1:2:3:4:5:6:7]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   117 "ABC@[IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   118 "ABC@[IPv6:1:2:3:4:5:6:7:8:9]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   119 "ABC@[IPv6:1:2:3:4:5:6:7:8"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   120 "ABC@[[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   121 "ABC@[IPv6:1:2:3:4:5:6:7:8 ]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   122 "ABC@[IPv6:1:2:3:4:5:6:7:]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   123 "ABC@[IPv6:2001:db8::1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   124 "ABC@[IPv6:a:b:c:d:e:f:1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   125 "ABC[@IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   126 "ABC@[IPv6:1:2:3::5:6:7:8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   127 "ABC@[IPv6:1:2:3::5::7:8]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   128 "ABC@[IPv6:1:2:(3::5):6:7:8]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   129 "ABC@[IPv6:1:2:(3::5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   130 "ABC@[IPv6:1:2:3::5:8].de"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   131 "ABC@[IPv61:2:3:4:5:6]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   132 "\"ABC\"@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *   133 "\"ABC\"@[IPv4:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   134 "\"ABC\"@[IPv6:1234]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   135 "\"ABC\"@[IPv6:1234:5678]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   136 "ABC@[IPv6::ffff:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   137 "ABC@[IPv6::fffff:127.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   138 "ABC@[IPv6:12:ffff:127.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   139 "ABC@[IPv6:00:ffff:127.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   140 "ABC@[IPv6:0:0:ffff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   141 "ABC@[IPv6:1:2:ffff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   142 "ABC@[IPv6::ffff:127A.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   143 "ABC@[IPv6:ffff:127.0.0.1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   144 "ABC@[IPv6::ffff:.127.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   145 "ABC@[IPv6::ffff:fff.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   146 "ABC@[IPv6::ffff:1211.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   147 "ABC@[IPv6::ffff:12111.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   148 "ABC@[IPv6::ffff:999.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   149 "ABC@[IPv6::fff:999.0.0.1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   150 "ABC@[IPv6::FFFF:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   151 "ABC@[IPv6::abcd:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   152 "ABC@[IPv6::1234:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   153 "ABC@[IPv6:a:b:c:d:127.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   154 "ABC@[IPv6::ffff:127.0:0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   155 "ABC@[IPv6:127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   156 "ABC@[IPv6:::127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   157 "ABC.DEF@[1234.5.6.7]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   158 "ABC.DEF@[1.2...3.4]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   159 "ABC@[IPv6:12345:6:7:8:9]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   160 "ABC@[IPv6:1:2:3:::6:7:8]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   161 "ABC@[iPv6:2001:db8::1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   162 "ABC@[D127.0.0.1]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   163 "ABC@[I127.0.0.1]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   164 "(ABC)DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   165 "ABC(DEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   166 "AB(CD)EF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   167 "AB.(CD).EF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   168 "AB.\"(CD)\".EF@GHI.JKL"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   169 "ABC(DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   170 "ABC.DEF@GHI)JKL"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   171 "ABC.DEF@(GHI).JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   172 "ABC(DEF@GHI).JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   173 "(ABC.DEF@GHI.JKL)"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 95 = Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   174 "(ABC).DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   175 "ABC(DEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   176 ")ABC.DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   177 "(A(B(C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   178 "(A)B)C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   179 "(A)(B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   180 "(A))B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   181 "(A)BCDE(F)@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   182 "()DEF@GHI.JKL"                                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   183 "DEF()@GHI.JKL"                                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   184 "ABC.DEF@GHI.JKL(Kommentar an dieser Stelle korrekt?)"                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   185 "Drei*Vier@Ist.Zwoelf.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   186 "ABC DEF <ABC.DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   187 "<ABC.DEF@GHI.JKL> ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   188 "ABC DEF ABC.DEF@GHI.JKL>"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   189 "<ABC.DEF@GHI.JKL ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 17 = Struktur: keine schliessende eckige Klammer gefunden.
   *   190 "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   191 "(ABC DEF) <ABC.DEF@GHI.JKL>"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   192 "(ABC DEF) <(Comment)ABC.DEF@[iPv6:2001:db8::1]>"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   193 "ABC+DEF) <ABC.DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   194 "ABC DEF <A@A>"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   195 "<A@A> ABC DEF"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   196 "ABC DEF <ABC.DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   197 "<ABC.DEF@GHI.JKL> ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   198 "ABC DEF ABC.DEF@GHI.JKL>"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   199 "<ABC.DEF@GHI.JKL ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 17 = Struktur: keine schliessende eckige Klammer gefunden.
   *   200 "ABC.DEF@GHI.JKL> ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   201 "ABC DEF <ABC.DEF@GHI.JKL"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   202 "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   203 "(ABC DEF) <ABC.DEF@GHI.JKL>"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   204 "ABC+DEF) <ABC.DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   205 "ABC DEF <>"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   206 "<> ABC DEF"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   207 "ABC DEF <>"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   208 "ABC DEF < >"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   209 "ABC DEF < @ >"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   210 "ABC DEF <( )@ >"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   211 "ABC DEF <(COMMENT)A@ >"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   212 "ABC DEF <ABC<DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   213 "<ABC.DEF@GHI.JKL>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   214 "\"ABC<DEF>\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   215 "\"ABC<DEF@GHI.COM>\"@GHI.JKL"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   216 "ABC DEF <ABC.<DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   217 "ABC/DEF=GHI@JKL.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   218 "1234@5678.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   219 "000.000@000.de"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   220 "0\"00.000\"@wc.de"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   221 "me+100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   222 "me-100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   223 "me-100@yahoo-test.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   224 "me..2002@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   225 "me.100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   226 "me.@gmail.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   227 "me12345@that.is"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   228 "me123@%*.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   229 "me123@%.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   230 "me123@.com"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   231 "me123@.com.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   232 "me@.com.my"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   233 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   234 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   235 "me@me.co.uk"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   236 "me@me@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   237 "me@yahoo.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   238 "invalid.email.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   239 "email@example.co.uk."                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   240 "email@example"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   241 " email@example.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   242 "email@example,com"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   243 "xxxx@.org.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   244 "test test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   245 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   246 "test@test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   247 "test%test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   248 "test_test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   249 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   250 "test+test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   251 "test@gmail"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   252 "test@gmail."                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   253 "test@gmail..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   254 "test@Gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   255 "test@gmail.Com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   256 "test@GMAIL.COM"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   257 "1234@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   258 "check@thiscom"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   259 "check@this..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   260 " check@this.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   261 "check@this.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   262 "test@sub-domain.000.0"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   263 "test.test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   264 "Test.Test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   265 "TEST.TEST@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   266 "test@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   267 "TEST@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   268 "test0@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   269 "0test@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   270 "test@subdomain.gmail.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   271 "test@sub-domain.gmail.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   272 "test@anothersub.sub-domain.gmail.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   273 "test@sub-domain.000.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   274 "test@sub-domain.000.0rg"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   275 "test@sub-domain.000.co-om"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   276 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   277 "xyz@blabla.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   278 "jsr@prhoselware.com9"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   279 "dasddas-@.com"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   280 "%2@gmail.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   281 "\"%2\"@gmail.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   282 "\"a..b\"@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   283 "\"a_b\"@gmail.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   284 "_@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   285 "1@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   286 "-asd@das.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   287 "as3d@dac.coas-"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   288 "dsq!a?@das.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   289 "_dasd@sd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   290 "dad@sds"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   291 "asd-@asd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   292 "dasd_-@jdas.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   293 "asd@dasd@asd.cm"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   294 "da23@das..com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   295 "_dasd_das_@9.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   296 "d23d@da9.co9"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   297 "dasd.dadas@dasd.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   298 "dda_das@das-dasd.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   299 "dasd-dasd@das.com.das"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   300 "prettyandsimple@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   301 "very.common@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   302 "{john'doe}@my.server"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   303 "hello7___@ca.com.pt"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   304 "peter_123@news24.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   305 "alirheza@test.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   306 "hallo2ww22@example....caaaao"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   307 "d@@.com"                                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   308 "hallo@example.coassjj#sswzazaaaa"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   309 "x._._y__z@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   310 "_______@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   311 "1234567890@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   312 "admin@mailserver1"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   313 "\" \"@example.org"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   314 "example@localhost"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   315 "example@xyz.solutions"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   316 "user@com"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   317 "user@localserver"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   318 "user@[IPv6:2001:db8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   319 "user@[192.168.2.1]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   320 "email@192.0.2.123"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   321 "email@example.name .name"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   322 "(comment and stuff)joe@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   323 "joe(comment and stuff)@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   324 "joe@(comment and stuff)gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   325 "joe@gmail.com(comment and stuff)"                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   326 "joe(fail me)smith@gmail.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   327 "joesmith@gma(fail me)il.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   328 "joe@gmail.com(comment and stuff"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   329 "Abc.example.com"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   330 "A@b@c@example.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   331 "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   332 "just\"not\"right@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   333 "this is\"not\allowed@example.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   334 "this\ still\\"not\\allowed@example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   335 "lots.of.iq@sagittarius.A*"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   336 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   337 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   338 " joe@gmail.com"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   339 "joe@gmail.com "                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   340 "jsmith@whizbang.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   341 ".ann..other.@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   342 "ann.other@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   343 "x.yz.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   344 "x_yz_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   345 "doysmith@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   346 "D.Oy'Smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   347 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   348 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   349 "Joe.\\Blow@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   350 "Abc\@def@ecksample.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   351 "someone@somewhere.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   352 "someone@somewhere.co.uk"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   353 "someone+tag@somewhere.net"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   354 "futureTLD@somewhere.fooo"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   355 "fdsa"                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   356 "fdsa@"                                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   357 "fdsa@fdza"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   358 "fdsa@fdza."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   359 "a+b@c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   360 "a@b.c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   361 "( a @[]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   362 " a @[]"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   363 " a @[] "                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   364 " a @[ ]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   365 " a @[ ] "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   366 " a @ []"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   367 " a @a[].com "                                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   368 "DotAtStart@.atstart"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   369 "DomainHyphen@-atstart"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   370 "ExpectedATEXT@;atstart"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   371 "DomainNotAllowedCharacter@/atstart"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   372 "ConsecutiveDots@at..start"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   373 "ConsecutiveCRLF@test\r\n\r\nat"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   374 "CRLFAtEnd@test\r\nat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   375 "CRWithoutLF@test\rat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   376 "ATEXTAfterCFWS@test\r\n at"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   377 "ExpectedCTEXT@test\r\n \n"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   378 "UnclosedComment@a(comment"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   379 "DomainNotAllowedCharacter@a,start"                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   380 "ConsecutiveAT@@start"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   381 "ExpectedATEXT@at[start"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   382 "DomainHyphen@atstart-.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   383 "DomainHyphen@bb.-cc"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   384 "DomainHyphen@bb.-cc-"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   385 "DomainHyphen@bb.cc-"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   386 "DomainNotAllowedCharacter@atst\art.com"                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   387 "DomainNotAllowedCharacter@example\"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   388 "DomainNotAllowedCharacter@exa\mple"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   389 "UnclosedDomainLiteral@example]"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   390 "DomainNotAllowedCharacter@example'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   391 "Test.Domain.Part@aampleEx.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   392 "Test.Domain.Part@subdomain.example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   393 "Test.Domain.Part@has-hyphen.example.com"                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   394 "Test.Domain.Part@1leadingnumber.example.com"                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   395 "Test.Domain.Part@example.co"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   396 "Test.Domain.Part@subdomain.example.co"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   397 "Test.Domain.Part@has-hyphen.example.co"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   398 "Test.Domain.Part@1leadingnumber.example.co"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   399 "Lat%ss\rtart%s@test.local.part"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   400 "at\"start\"test@test.local.part"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   401 "at(start)test@test.local.part"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   402 "example(comment)@test.local.part"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   403 "withdot.@test.local.part"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   404 "Test.IPv6@[[127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   405 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   406 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]"                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   407 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]"                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   408 "Test.IPv6@[IPv6:1::1::1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   409 "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   410 "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   411 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]"                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   412 "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   413 "Test.IPv6@[\n]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   414 "abc.\"defghi\".xyz@example.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   415 "\"abcdefghixyz\"@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   416 "abc\"defghi\"xyz@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   417 "abc\\"def\\"ghi@eggsample.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   418 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   419 "jsmith@[IPv6:2001:db8::1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   420 "user@localhost"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   421 "aHZs...Ym8iZXJn@YWRtAW4g.au"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   422 "\"RmF0aGlh\"@SXp6YXRp.id"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   423 "\"hor\ror\"@nes.si"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   424 "!yoora@an.yang.ha.se.yo"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   425 "084105111046077097110105107@hello.again.id"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   426 "@@@@@@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   427 "somecahrs@xyz.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   428 "user@host.network"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   429 "bob.mcspam@ABCD.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   430 "something@domain.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   431 "emailString@email.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   432 "BR0w$ed.for.pr0n@PQRN.biz"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   433 "\"B3V3RLY H1LL$\"@example.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   434 "\"-- --- .. -.\"@sh.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   435 "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   436 "#!$%&'*+-/=?^_`{}|~@eksample.org"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   437 "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   438 "\"\\" + \\"select * from user\\" + \\"\"@test.einzugsermaechtigung.de"               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   439 "\"()<>[]:,;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   440 "person@registry.organization"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 15 = Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
   *   441 "foo.bar.\"bux\".bar.com@baz.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   442 "someStringThatMightBe@email.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   443 "100$-30%=130$-(x*3pi)@MATH.MAGIC"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   444 "\"much.more unusual\"@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   445 "other.email-with-dash@eksample.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   446 "Find#Me@NotesDocumentCollection.de"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   447 "InvalidEmail@notreallyemailbecausenosuffix"                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   448 "\"very.unusual.@.unusual.com\"@example.com"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   449 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   450 "disposable.style.email.with+symbol@example.com"                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   451 "0=1+e^(i*pi)@Gleich.Zahl.Phi.Goldener.Schnitt.ab"                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   452 "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   453 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com"              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   454 "VGhpcyBpcyBCYXNlNjQgZW5jb2RlZC4gV2hvIHRob3VnaHQgb2YgdGhhdA==@C64.de"                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   455 "1234567890123456789012345678901234567890123456789012345678901234+x@example.com"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   456 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   457 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL"     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   458 "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com"       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   459 "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   460 "QnfVfgEbg13Irefpuyhrffryg.AruzgYvroreEbg26@JrvyQnfVfgFvpurere.JrvyQbccrygIrefpuyhrffryg.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   461 "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   462 "Die.???.und.die.verlorene.eMail@weil.wegen.mit.reg.ex.geprueft.weswegen.fehler.weil.haett.ja.klappen.koennen.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   463 "Den.Zeitraum.vom.05.10.1582.bis.13.10.1582.gibt.es.nicht@weil.papst.gregor.mal.nachgerechnet.hat.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   464 "Fun.Fact.the.period.from.05.to.13.10.1582.exist.in.england@because.of.henry.the.eights.they.did.not.participate.uk" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   465 "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   466 "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   467 "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   468 "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   469 "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   470 "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   471 "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   472 "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   473 "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   474 "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   475 "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   476 "zzzz.zzzzzz@zzzzzzzzzz.zz"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   477 "AAAA.AAAAAA@AAAAAAAAAA.AA"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   478 "aaaa.aaaaaa@aaaaaaaaaa.aa"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   479 "Vorname.Nachname@web.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   480 "m.fanin@fc-wohlenegg.at"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   481 "old.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   482 "new.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   483 "test1group@test1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   484 "test2group@test2.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   485 "test1@test1.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   486 "test2@test2.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   487 "junit.testEmailChange@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   488 "at@at.at"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   489 "easy@isnt.it"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   490 "yes@it.is"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * 
   * Testlauf 1 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29160000 - anzahl_falsch =  10840000 - anzahl_fehler =         0  | MS   3610 = 0.00009025                 = 00:00:03:610
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   6516 = 0.0001629                  = 00:00:06:516
   * 
   * JAVA 1     anzahl_korrekt =  25320000 - anzahl_falsch =  14640000 - anzahl_fehler =     40000  | MS  12748 = 0.0003187                  = 00:00:12:748
   * 
   * REGEXP 1   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  30940 = 0.0007735                  = 00:00:30:940
   * 
   * REGEXP 2   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  15455 = 0.000386375                = 00:00:15:455
   * 
   * REGEXP 3   anzahl_korrekt =  26080000 - anzahl_falsch =  13880000 - anzahl_fehler =     40000  | MS  89130 = 0.00222825                 = 00:01:29:130
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 2 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29160000 - anzahl_falsch =  10840000 - anzahl_fehler =         0  | MS   3533 = 0.000088325                = 00:00:03:533
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   6501 = 0.000162525                = 00:00:06:501
   * 
   * JAVA 1     anzahl_korrekt =  25320000 - anzahl_falsch =  14640000 - anzahl_fehler =     40000  | MS  12589 = 0.000314725                = 00:00:12:589
   * 
   * REGEXP 1   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  30900 = 0.0007725                  = 00:00:30:900
   * 
   * REGEXP 2   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  15432 = 0.0003858                  = 00:00:15:432
   * 
   * REGEXP 3   anzahl_korrekt =  26080000 - anzahl_falsch =  13880000 - anzahl_fehler =     40000  | MS  89068 = 0.0022267                  = 00:01:29:068
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 3 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29160000 - anzahl_falsch =  10840000 - anzahl_fehler =         0  | MS   3540 = 0.0000885                  = 00:00:03:540
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   6486 = 0.00016215                 = 00:00:06:486
   * 
   * JAVA 1     anzahl_korrekt =  25320000 - anzahl_falsch =  14640000 - anzahl_fehler =     40000  | MS  12578 = 0.00031445                 = 00:00:12:578
   * 
   * REGEXP 1   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  31258 = 0.00078145                 = 00:00:31:258
   * 
   * REGEXP 2   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  15507 = 0.000387675                = 00:00:15:507
   * 
   * REGEXP 3   anzahl_korrekt =  26080000 - anzahl_falsch =  13880000 - anzahl_fehler =     40000  | MS  89177 = 0.002229425                = 00:01:29:177
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 4 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29160000 - anzahl_falsch =  10840000 - anzahl_fehler =         0  | MS   3533 = 0.000088325                = 00:00:03:533
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   6506 = 0.00016265                 = 00:00:06:506
   * 
   * JAVA 1     anzahl_korrekt =  25320000 - anzahl_falsch =  14640000 - anzahl_fehler =     40000  | MS  12588 = 0.0003147                  = 00:00:12:588
   * 
   * REGEXP 1   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  30988 = 0.0007747                  = 00:00:30:988
   * 
   * REGEXP 2   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  15449 = 0.000386225                = 00:00:15:449
   * 
   * REGEXP 3   anzahl_korrekt =  26080000 - anzahl_falsch =  13880000 - anzahl_fehler =     40000  | MS  89740 = 0.0022435                  = 00:01:29:740
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 5 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29160000 - anzahl_falsch =  10840000 - anzahl_fehler =         0  | MS   3579 = 0.000089475                = 00:00:03:579
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   6552 = 0.0001638                  = 00:00:06:552
   * 
   * JAVA 1     anzahl_korrekt =  25320000 - anzahl_falsch =  14640000 - anzahl_fehler =     40000  | MS  12605 = 0.000315125                = 00:00:12:605
   * 
   * REGEXP 1   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  30965 = 0.000774125                = 00:00:30:965
   * 
   * REGEXP 2   anzahl_korrekt =  30720000 - anzahl_falsch =   9240000 - anzahl_fehler =     40000  | MS  15440 = 0.0003860                  = 00:00:15:440
   * 
   * REGEXP 3   anzahl_korrekt =  26080000 - anzahl_falsch =  13880000 - anzahl_fehler =     40000  | MS  89128 = 0.0022282                  = 00:01:29:128
   * 
   * 
   * System Properties ----------------------------------------------------------------
   * 
   *  java.runtime.name             Java(TM) SE Runtime Environment
   *  java.vm.version               25.121-b13
   *  java.vm.vendor                Oracle Corporation
   *  java.vendor.url               http://java.oracle.com/
   *  java.vm.name                  Java HotSpot(TM) 64-Bit Server VM
   *  java.runtime.version          1.8.0_121-b13
   *  os.arch                       amd64
   *  java.vm.specification.vendor  Oracle Corporation
   *  os.name                       Windows 8.1
   *  sun.jnu.encoding              Cp1252
   *  sun.management.compiler       HotSpot 64-Bit Tiered Compilers
   *  os.version                    6.3
   *  java.specification.version    1.8
   *  java.vm.specification.version 1.8
   *  sun.arch.data.model           64
   *  java.specification.vendor     Oracle Corporation
   *  java.version                  1.8.0_121
   *  java.vendor                   Oracle Corporation
   *  sun.desktop                   windows
   *  sun.cpu.isalist               amd64
   * 
   * System getEnv() ------------------------------------------------------------------
   * 
   *  https://coderanch.com/t/567735/java/hardware-details-processor-type-java
   * 
   *  PROCESSOR_IDENTIFIER          Intel64 Family 6 Model 58 Stepping 9, GenuineIntel
   *  PROCESSOR_ARCHITECTURE        AMD64
   *  PROCESSOR_ARCHITEW6432        null
   *  NUMBER_OF_PROCESSORS          8
   * 
   * ################################################################################################################################
   * 
   * Test mit Intel i7-8700 CPU @ 3.20GHz unter Linux-Mint 19.1:
   * 
   * Testlauf 1 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29240000 - anzahl_falsch =  10760000 - anzahl_fehler =         0  | MS   4344 = 0.0001086                  = 00:00:04:344
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   5922 = 0.00014805                 = 00:00:05:922
   * 
   * JAVA 1     anzahl_korrekt =  25360000 - anzahl_falsch =  14600000 - anzahl_fehler =     40000  | MS   9517 = 0.000237925                = 00:00:09:517
   * 
   * REGEXP 1   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS  26877 = 0.000671925                = 00:00:26:877
   * 
   * REGEXP 2   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS   9666 = 0.00024165                 = 00:00:09:666
   * 
   * REGEXP 3   anzahl_korrekt =  26160000 - anzahl_falsch =  13800000 - anzahl_fehler =     40000  | MS  65937 = 0.001648425                = 00:01:05:937
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 2 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29240000 - anzahl_falsch =  10760000 - anzahl_fehler =         0  | MS   3711 = 0.000092775                = 00:00:03:711
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   5420 = 0.0001355                  = 00:00:05:420
   * 
   * JAVA 1     anzahl_korrekt =  25360000 - anzahl_falsch =  14600000 - anzahl_fehler =     40000  | MS  10515 = 0.000262875                = 00:00:10:515
   * 
   * REGEXP 1   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS  25943 = 0.000648575                = 00:00:25:943
   * 
   * REGEXP 2   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS   9258 = 0.00023145                 = 00:00:09:258
   * 
   * REGEXP 3   anzahl_korrekt =  26160000 - anzahl_falsch =  13800000 - anzahl_fehler =     40000  | MS  65902 = 0.00164755                 = 00:01:05:902
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 3 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29240000 - anzahl_falsch =  10760000 - anzahl_fehler =         0  | MS   3619 = 0.000090475                = 00:00:03:619
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   5853 = 0.000146325                = 00:00:05:853
   * 
   * JAVA 1     anzahl_korrekt =  25360000 - anzahl_falsch =  14600000 - anzahl_fehler =     40000  | MS  10479 = 0.000261975                = 00:00:10:479
   * 
   * REGEXP 1   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS  24968 = 0.0006242                  = 00:00:24:968
   * 
   * REGEXP 2   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS   9219 = 0.000230475                = 00:00:09:219
   * 
   * REGEXP 3   anzahl_korrekt =  26160000 - anzahl_falsch =  13800000 - anzahl_fehler =     40000  | MS  62828 = 0.0015707                  = 00:01:02:828
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 4 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29240000 - anzahl_falsch =  10760000 - anzahl_fehler =         0  | MS   3663 = 0.000091575                = 00:00:03:663
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   5384 = 0.0001346                  = 00:00:05:384
   * 
   * JAVA 1     anzahl_korrekt =  25360000 - anzahl_falsch =  14600000 - anzahl_fehler =     40000  | MS  10299 = 0.000257475                = 00:00:10:299
   * 
   * REGEXP 1   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS  24537 = 0.000613425                = 00:00:24:537
   * 
   * REGEXP 2   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS   9336 = 0.0002334                  = 00:00:09:336
   * 
   * REGEXP 3   anzahl_korrekt =  26160000 - anzahl_falsch =  13800000 - anzahl_fehler =     40000  | MS  65309 = 0.001632725                = 00:01:05:309
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 5 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29240000 - anzahl_falsch =  10760000 - anzahl_fehler =         0  | MS   3811 = 0.000095275                = 00:00:03:811
   * 
   * Jmail      anzahl_korrekt =  35160000 - anzahl_falsch =   4800000 - anzahl_fehler =     40000  | MS   5543 = 0.000138575                = 00:00:05:543
   * 
   * JAVA 1     anzahl_korrekt =  25360000 - anzahl_falsch =  14600000 - anzahl_fehler =     40000  | MS  10249 = 0.000256225                = 00:00:10:249
   * 
   * REGEXP 1   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS  24537 = 0.000613425                = 00:00:24:537
   * 
   * REGEXP 2   anzahl_korrekt =  30760000 - anzahl_falsch =   9200000 - anzahl_fehler =     40000  | MS   9336 = 0.0002364                  = 00:00:09:636
   * 
   * REGEXP 3   anzahl_korrekt =  26160000 - anzahl_falsch =  13800000 - anzahl_fehler =     40000  | MS  65309 = 0.001681725                = 00:01:06:399
   * 
   */

  private static DecimalFormatSymbols otherSymbols             = null;

  private static DecimalFormat        number_format            = null;

  /*
   * Die auszugebende Stellenanzahl bei den Testnummern. 
   * Ist hart auf 9 gesetzt, damit unterschiedliche Berechnungslaeufe 
   * miteinander kombiniert werden koennen und es zu keinen Ausrichtungsfehlern 
   * in der Log-Dateiausgabe kommt.
   */
  private static int                  stellen_anzahl           = 0;

  /*
   * Die Anzahl der auszufuehrenden Testfaelle
   * - berechnet aus den Array-Elementen 
   * - oder einfach hart gesetzt
   */
  private static int                  durchlauf_anzahl         = 0;

  /*
   * Die Anzahl der Elemente im aktuellem Testdatenarray
   */
  private static int                  test_daten_array_length  = 0;

  /*
   * Kennzeichen fuer den ersten Durchlauf, um die Pruefergebnisse auszugeben.
   */
  private static boolean              knz_ausgabe_test_daten   = true;

  private static int                  FAKTOR_ANZAHL_ARRAY_A    = 40000;

  /*
   * Array, mit welchem die Tests ausgefuehrt werden.
   */
  private static String[]             array_test_daten_aktuell = null;

  /*
   * 1000 Testdaten
   * Es sind 1000 eMail-Adressen vorhanden. 
   * Es sind mehr korrekte eMail-Adressen als falsche vorhanden.
   * Es sind mehr normale eMail-Adressangaben vorhanden, als Sonderformen.
   * 
   * Die Elementanzahl wird mit dem Faktor "FAKTOR_ANZAHL_ARRAY_A" multipliziert, 
   * welches die Gesamtanzahl der Testdaten bestimmt.
   */
  private static String[]             array_test_daten_1000       = {

     "ABC.DEF@GHI.JKL",
     "A@B.CD",
     "A@A.AA",
     "\"ABC.DEF\"@GHI.JKL",
     "\"ABC DEF\"@GHI.JKL",
     "\"ABC@DEF\"@GHI.JKL",
     "A.B@C.DE",
     "A.\"B\"@C.DE",
     "A.B@[1.2.3.4]",
     "A.\"B\"@[1.2.3.4]",
     "A.B@[IPv6:1:2:3:4:5:6:7:8]",
     "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]",
     "(A)B@C.DE",
     "A(B)@C.DE",
     "(A)\"B\"@C.DE",
     "\"A\"(B)@C.DE",
     "(A)B@[1.2.3.4]",
     "A(B)@[1.2.3.4]",
     "(A)\"B\"@[1.2.3.4]",
     "\"A\"(B)@[1.2.3.4]",
     "(A)B@[IPv6:1:2:3:4:5:6:7:8]",
     "A(B)@[IPv6:1:2:3:4:5:6:7:8]",
     "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]",
     "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]",
     "ABC-DEF@GHI.JKL",
     "ABC\\@DEF@GHI.JKL",
     "$ABCDEF@GHI.JKL",
     null, "",
     "               ",
     "ABCDEFGHIJKLMNO",
     "ABC.DEF_@GHI.JKL",
     "#ABC.DEF@GHI.JKL",
     "ABC.DEF@GHI.2KL",
     "ABC.DEF@GHI.JK2",
     "ABC.DEF@2HI.JKL",
     "ABC.DEF@GHI.JK-",
     "ABC.DEF@GHI.JK_",
     "ABC.DEF@-HI.JKL",
     "ABC.DEF@_HI.JKL",
     ".ABC.DEF@GHI.JKL",
     "ABC.DEF@GHI..JKL",
     "ABC.DEF@.GHI.JKL",
     "ABC.DEF.@GHI.JKL",
     "ABC.DEF@@GHI.JKL",
     " A . B & C . D",
     "ABC.DEF@GHI.JKL.",
     "\".ABC.DEF\"@GHI.JKL",
     "\"ABC.DEF.\"@GHI.JKL",
     "\"\"@GHI.JKL",
     "\" \"@GHI.JKL",
     "ABC.DEF@\"\"",
     "ABC.DEF\"GHI.JKL",
     "ABC.DEF\"@GHI.JKL",
     "ABC.DEF.\"",
     "ABC.DE\"F@GHI.JKL",
     "ABC.DEF@G\"HI.DE",
     "ABC.DEF@\"GHI.JKL",
     "ABC.DEF@GHI.JKL\"",
     "\"ABC.DEF@G\"HI.DE",
     "\"ABC.DEF@GHI.JKL",
     "\"ABC.D\"EF@GHI.JKL",
     "\"AB\"C.D\"EF@GHI.JKL",
     "\"AB\"C.D\"EF\"@GHI.JKL",
     "@G\"HI.DE",
     "\"@GHI.JKL",
     "\"\"@[]",
     "\"@\".A(@)@a.aa",
     "ABC DEF@GHI.JKL",
     "\"ABC@DEF\"@GHI.JKL",
     "\"ABC DEF\"@GHI.JKL",
     "\"ABC\".DEF.\"GHI\"@GHI.JKL",
     "A\"BC\".DEF.\"GHI\"@GHI.JKL",
     "\"AB\"C.DEF.\"GHI\"@GHI.JKL",
     "\"ABC\".DEF.\"GHI\"J@KL.de",
     "\"ABC\"\".DEF.\"GHI\"@GHI.JKL",
     "\"Gueltige\\\"Escape\\\\Sequenz\"@korrekt.de",
     "\"Falsche\\#Escape\\GSequenz\"@falsch.de",
     "\"Falsch.da.Escape.Zeichen.am.Ende.steht\\",
     "ABC.DEF@[1.12.123.255]",
     "ABC.DEF@[001.012.123.255]",
     "\"ABC.DEF\"@[127.0.0.1]",
     "ABC.DEF@[1.12.123.259]",
     "ABC.DEF@[1..123.255]",
     "ABC.DEF@[1.123.25.]",
     "ABC.DEF@[..]",
     "ABC.DEF@[1.2.]",
     "ABC.DEF@[]",
     "ABC.DEF@[1]",
     "ABC.DEF@[1.2]",
     "ABC.DEF@[1.2.3]",
     "ABC.DEF@[1.2.3.4]",
     "ABC.DEF@[1.2.3.4.5]",
     "ABC.DEF@[MyDomain.de]",
     "ABC.DEF@[1.00002.3.4]",
     "ABC.DEF[1.12.123.255]",
     "ABC.DEF@[1.12.123.255].de",
     "ABC.DEF@[1.2.3.]",
     "ABC.DEF@[1.2.3. ]",
     "ABC.DEF@[.2.3.4]",
     "ABC.DEF@[ .2.3.4]",
     "ABC.DEF@MyDomain[1.2.3.4]",
     "ABC.DEF@[1.2.3.4][5.6.7.8]",
     "[1.2.3.4]@[5.6.7.8]",
     "\"[1.2.3.4]\"@[5.6.7.8]",
     "ABC.DEF[@1.2.3.4]",
     "ABC.DEF@[1.2.3.4",
     "ABC.DEF@[0.0.0.0]",
     "ABC.DEF@[000.000.000.000]",
     "ABC@[IPv6:]",
     "ABC@[IPv6:1]",
     "ABC@[IPv6:1:2]",
     "ABC@[IPv6:1:2:3]",
     "ABC@[IPv6:1:2:3:4]",
     "ABC@[IPv6:1:2:3:4:5]",
     "ABC@[IPv6:1:2:3:4:5:6]",
     "ABC@[IPv6:1:2:3:4:5:6:7]",
     "ABC@[IPv6:1:2:3:4:5:6:7:8]",
     "ABC@[IPv6:1:2:3:4:5:6:7:8:9]",
     "ABC@[IPv6:1:2:3:4:5:6:7:8",
     "ABC@[[IPv6:1:2:3:4:5:6:7:8]",
     "ABC@[IPv6:1:2:3:4:5:6:7:8 ]",
     "ABC@[IPv6:1:2:3:4:5:6:7:]",
     "ABC@[IPv6:2001:db8::1]",
     "ABC@[IPv6:a:b:c:d:e:f:1]",
     "ABC[@IPv6:1:2:3:4:5:6:7:8]",
     "ABC@[IPv6:1:2:3::5:6:7:8]",
     "ABC@[IPv6:1:2:3::5::7:8]",
     "ABC@[IPv6:1:2:(3::5):6:7:8]",
     "ABC@[IPv6:1:2:(3::5:6:7:8]",
     "ABC@[IPv6:1:2:3::5:8].de",
     "ABC@[IPv61:2:3:4:5:6]",
     "\"ABC\"@[IPv6:1:2:3:4:5:6:7:8]",
     "\"ABC\"@[IPv4:1:2:3:4]",
     "\"ABC\"@[IPv6:1234]",
     "\"ABC\"@[IPv6:1234:5678]",
     "ABC@[IPv6::ffff:127.0.0.1]",
     "ABC@[IPv6::fffff:127.0.0.1]",
     "ABC@[IPv6:12:ffff:127.0.0.1]",
     "ABC@[IPv6:00:ffff:127.0.0.1]",
     "ABC@[IPv6:0:0:ffff:127.0.0.1]",
     "ABC@[IPv6:1:2:ffff:127.0.0.1]",
     "ABC@[IPv6::ffff:127A.0.0.1]",
     "ABC@[IPv6:ffff:127.0.0.1]",
     "ABC@[IPv6::ffff:.127.0.0.1]",
     "ABC@[IPv6::ffff:fff.0.0.1]",
     "ABC@[IPv6::ffff:1211.0.0.1]",
     "ABC@[IPv6::ffff:12111.0.0.1]",
     "ABC@[IPv6::ffff:999.0.0.1]",
     "ABC@[IPv6::fff:999.0.0.1]",
     "ABC@[IPv6::FFFF:127.0.0.1]",
     "ABC@[IPv6::abcd:127.0.0.1]",
     "ABC@[IPv6::1234:127.0.0.1]",
     "ABC@[IPv6:a:b:c:d:127.0.0.1]",
     "ABC@[IPv6::ffff:127.0:0.1]",
     "ABC@[IPv6:127.0.0.1]",
     "ABC@[IPv6:::127.0.0.1]",
     "ABC.DEF@[1234.5.6.7]",
     "ABC.DEF@[1.2...3.4]",
     "ABC@[IPv6:12345:6:7:8:9]",
     "ABC@[IPv6:1:2:3:::6:7:8]",
     "ABC@[iPv6:2001:db8::1]",
     "ABC@[D127.0.0.1]",
     "ABC@[I127.0.0.1]",
     "(ABC)DEF@GHI.JKL",
     "ABC(DEF)@GHI.JKL",
     "AB(CD)EF@GHI.JKL",
     "AB.(CD).EF@GHI.JKL",
     "AB.\"(CD)\".EF@GHI.JKL",
     "ABC(DEF@GHI.JKL",
     "ABC.DEF@GHI)JKL",
     "ABC.DEF@(GHI).JKL",
     "ABC(DEF@GHI).JKL",
     "(ABC.DEF@GHI.JKL)",
     "(ABC).DEF@GHI.JKL",
     "ABC(DEF).@GHI.JKL",
     ")ABC.DEF@GHI.JKL",
     "(A(B(C)DEF@GHI.JKL", 
     "(A)B)C)DEF@GHI.JKL", 
     "(A)(B)CDEF@GHI.JKL", 
     "(A))B)CDEF@GHI.JKL", 
     "(A)BCDE(F)@GHI.JKL", 
     "()DEF@GHI.JKL",
     "DEF()@GHI.JKL",
     "ABC.DEF@GHI.JKL(Kommentar an dieser Stelle korrekt?)",
     "Drei*Vier@Ist.Zwoelf.de",
     "ABC DEF <ABC.DEF@GHI.JKL>",
     "<ABC.DEF@GHI.JKL> ABC DEF",
     "ABC DEF ABC.DEF@GHI.JKL>",
     "<ABC.DEF@GHI.JKL ABC DEF",
     "\"ABC DEF \"<ABC.DEF@GHI.JKL>",
     "(ABC DEF) <ABC.DEF@GHI.JKL>",
     "(ABC DEF) <(Comment)ABC.DEF@[iPv6:2001:db8::1]>",
     "ABC+DEF) <ABC.DEF@GHI.JKL>",
     "ABC DEF <A@A>",
     "<A@A> ABC DEF",
     "ABC DEF <ABC.DEF@GHI.JKL>",
     "<ABC.DEF@GHI.JKL> ABC DEF",
     "ABC DEF ABC.DEF@GHI.JKL>",
     "<ABC.DEF@GHI.JKL ABC DEF",
      "ABC.DEF@GHI.JKL> ABC DEF",
      "ABC DEF <ABC.DEF@GHI.JKL",
     "\"ABC DEF \"<ABC.DEF@GHI.JKL>",
     "(ABC DEF) <ABC.DEF@GHI.JKL>",
     "ABC+DEF) <ABC.DEF@GHI.JKL>",
     "ABC DEF <>",
     "<> ABC DEF",
     "ABC DEF <>",
     "ABC DEF < >",
     "ABC DEF < @ >",
     "ABC DEF <( )@ >",
     "ABC DEF <(COMMENT)A@ >",
     "ABC DEF <ABC<DEF@GHI.JKL>",
     "<ABC.DEF@GHI.JKL>",
      "\"ABC<DEF>\"@GHI.JKL",
      "\"ABC<DEF@GHI.COM>\"@GHI.JKL",
     "ABC DEF <ABC.<DEF@GHI.JKL>",     
     "ABC/DEF=GHI@JKL.com",
     "1234@5678.com",
     "000.000@000.de",
     "0\"00.000\"@wc.de",
     "me+100@me.com",
     "me-100@me.com",
     "me-100@yahoo-test.com",
     "me..2002@gmail.com",
     "me.100@me.com",
     "me.@gmail.com",
     "me12345@that.is",
     "me123@%*.com",
     "me123@%.com",
     "me123@.com",
     "me123@.com.com",
     "me@.com.my",
     "me@gmail.com.1a",
     "me@gmail.com.1a",
     "me@me.co.uk",
     "me@me@gmail.com",
     "me@yahoo.com",
     "invalid.email.com",
     "email@example.co.uk.",
     "email@example",
     " email@example.com",
     "email@example,com",
     "xxxx@.org.org",
     "test test@gmail.com",
     "test-test@gmail.com",
     "test@test@gmail.com",
     "test%test@gmail.com",
     "test_test@gmail.com",
     "test-test@gmail.com",
     "test+test@gmail.com",
     "test@gmail",
     "test@gmail.",
     "test@gmail..com",
     "test@Gmail.com",
     "test@gmail.Com",
     "test@GMAIL.COM",
     "1234@gmail.com",
     "check@thiscom",
     "check@this..com",
     " check@this.com",
     "check@this.com",
     "test@sub-domain.000.0",
     "test.test@gmail.com",
     "Test.Test@gmail.com",
     "TEST.TEST@gmail.com",
     "test@gmail.com",
     "TEST@gmail.com",
     "test0@gmail.com",
     "0test@gmail.com",
     "test@subdomain.gmail.com",
     "test@sub-domain.gmail.com",
     "test@anothersub.sub-domain.gmail.com",
     "test@sub-domain.000.com",
     "test@sub-domain.000.0rg",
     "test@sub-domain.000.co-om",
     "this is not valid@email$com",
     "xyz@blabla.com",
     "jsr@prhoselware.com9",
     "dasddas-@.com",
     "%2@gmail.com",
     "\"%2\"@gmail.com",
     "\"a..b\"@gmail.com",
     "\"a_b\"@gmail.com",
     "_@gmail.com",
     "1@gmail.com",
     "-asd@das.com",
     "as3d@dac.coas-",
     "dsq!a?@das.com",
     "_dasd@sd.com",
     "dad@sds",
     "asd-@asd.com",
     "dasd_-@jdas.com",
     "asd@dasd@asd.cm",
     "da23@das..com",
     "_dasd_das_@9.com",
     "d23d@da9.co9",
     "dasd.dadas@dasd.com",
     "dda_das@das-dasd.com",
     "dasd-dasd@das.com.das",
     "prettyandsimple@example.com",
     "very.common@example.com",
     "{john'doe}@my.server",
     "hello7___@ca.com.pt",
     "peter_123@news24.com",
     "alirheza@test.co.uk",
     "hallo2ww22@example....caaaao",
     "d@@.com",
     "hallo@example.coassjj#sswzazaaaa",
     "x._._y__z@gmail.com",
     "_______@example.com",
     "1234567890@example.com",
     "admin@mailserver1",
     "\" \"@example.org",
     "example@localhost",
     "example@xyz.solutions",
     "user@com",
     "user@localserver",
     "user@[IPv6:2001:db8::1]",
     "user@[192.168.2.1]",
     "email@192.0.2.123",
     "email@example.name .name",
     "(comment and stuff)joe@gmail.com",
     "joe(comment and stuff)@gmail.com",
     "joe@(comment and stuff)gmail.com",
     "joe@gmail.com(comment and stuff)",
     "joe(fail me)smith@gmail.com",
     "joesmith@gma(fail me)il.com",
     "joe@gmail.com(comment and stuff",
     "Abc.example.com",
     "A@b@c@example.com",
     "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com",
     "just\"not\"right@example.com",
     "this is\"not\\allowed@example.com",
     "this\\ still\\\"not\\\\allowed@example.com",
     "lots.of.iq@sagittarius.A*",
     "john..doe@example.com",
     "john.doe@example..com",
     " joe@gmail.com",
     "joe@gmail.com ",
     "jsmith@whizbang.co",
     ".ann..other.@example.com",
     "ann.other@example.com",
     "x.yz.smith@gmail.com",
     "x_yz_smith@gmail.com",
     "doysmith@gmail.com",
     "D.Oy'Smith@gmail.com",
     "Abc\\@def@example.com",
     "Fred\\ Bloggs@example.com",
     "Joe.\\\\Blow@example.com",
     "Abc\\@def@ecksample.com",
     "someone@somewhere.com",
     "someone@somewhere.co.uk",
     "someone+tag@somewhere.net",
     "futureTLD@somewhere.fooo",
     "fdsa",
     "fdsa@",
     "fdsa@fdza",
     "fdsa@fdza.",
     "a+b@c.com",
     "a@b.c.com",
     "( a @[]",
     " a @[]",
     " a @[] ",
     " a @[ ]",
     " a @[ ] ",
     " a @ []",
     " a @a[].com ",
     "DotAtStart@.atstart",
     "DomainHyphen@-atstart",
     "ExpectedATEXT@;atstart",
     "DomainNotAllowedCharacter@/atstart",
     "ConsecutiveDots@at..start",
     "ConsecutiveCRLF@test\r\n\r\nat",
     "CRLFAtEnd@test\r\nat",
     "CRWithoutLF@test\rat",
     "ATEXTAfterCFWS@test\r\n at",
     "ExpectedCTEXT@test\r\n \n",
     "UnclosedComment@a(comment",
     "DomainNotAllowedCharacter@a,start",
     "ConsecutiveAT@@start",
     "ExpectedATEXT@at[start",
     "DomainHyphen@atstart-.com",
     "DomainHyphen@bb.-cc",
     "DomainHyphen@bb.-cc-",
     "DomainHyphen@bb.cc-",
     "DomainNotAllowedCharacter@atst\\art.com",
     "DomainNotAllowedCharacter@example\\",
     "DomainNotAllowedCharacter@exa\\mple",
     "UnclosedDomainLiteral@example]",
     "DomainNotAllowedCharacter@example'",
     "Test.Domain.Part@aampleEx.com",
     "Test.Domain.Part@subdomain.example.com",
     "Test.Domain.Part@has-hyphen.example.com",
     "Test.Domain.Part@1leadingnumber.example.com",
     "Test.Domain.Part@example.co",
     "Test.Domain.Part@subdomain.example.co",
     "Test.Domain.Part@has-hyphen.example.co",
     "Test.Domain.Part@1leadingnumber.example.co",
     "Lat%ss\rtart%s@test.local.part",
     "at\"start\"test@test.local.part",
     "at(start)test@test.local.part",
     "example(comment)@test.local.part",
     "withdot.@test.local.part",
     "Test.IPv6@[[127.0.0.1]",
     "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]",
     "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]",
     "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]",
     "Test.IPv6@[IPv6:1::1::1]",
     "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]",
     "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]",
     "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]",
     "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]",
     "Test.IPv6@[\n]",
     "abc.\"defghi\".xyz@example.com",
     "\"abcdefghixyz\"@example.com",
     "abc\"defghi\"xyz@example.com",
     "abc\\\"def\\\"ghi@eggsample.com",
     "this is not valid@email$com",
     "jsmith@[IPv6:2001:db8::1]",
     "user@localhost",
     "aHZs...Ym8iZXJn@YWRtAW4g.au",
     "\"RmF0aGlh\"@SXp6YXRp.id",     
     "\"hor\\ror\"@nes.si",         
     "!yoora@an.yang.ha.se.yo",
     "084105111046077097110105107@hello.again.id",
     "@@@@@@gmail.com",
     "somecahrs@xyz.com",
     "user@host.network",
     "bob.mcspam@ABCD.org",
     "something@domain.com",
     "emailString@email.com",
     "BR0w$ed.for.pr0n@PQRN.biz",
     "\"B3V3RLY H1LL$\"@example.com",
     "\"-- --- .. -.\"@sh.de",
     "{{-^-}{-=-}{-^-}}@GHI.JKL",
     "#!$%&'*+-/=?^_`{}|~@eksample.org",
     "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
     "\"\\\" + \\\"select * from user\\\" + \\\"\"@test.einzugsermaechtigung.de",
     "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org",
     "person@registry.organization",
     "foo.bar.\"bux\".bar.com@baz.com",
     "someStringThatMightBe@email.com",
     "100$-30%=130$-(x*3pi)@MATH.MAGIC",
     "\"much.more unusual\"@example.com",
     "other.email-with-dash@eksample.com",
     "Find#Me@NotesDocumentCollection.de",
     "InvalidEmail@notreallyemailbecausenosuffix",
     "\"very.unusual.@.unusual.com\"@example.com",
     "foo\\@bar@machine.subdomain.example.museum",
     "disposable.style.email.with+symbol@example.com",
     "0=1+e^(i*pi)@Gleich.Zahl.Phi.Goldener.Schnitt.ab",
     "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]",
     "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com",
     "VGhpcyBpcyBCYXNlNjQgZW5jb2RlZC4gV2hvIHRob3VnaHQgb2YgdGhhdA==@C64.de",
     "1234567890123456789012345678901234567890123456789012345678901234+x@example.com",
     "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL",
     "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL",
     "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com",
     "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de",
     "QnfVfgEbg13Irefpuyhrffryg.AruzgYvroreEbg26@JrvyQnfVfgFvpurere.JrvyQbccrygIrefpuyhrffryg.de",
     "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part",     
     "Die.???.und.die.verlorene.eMail@weil.wegen.mit.reg.ex.geprueft.weswegen.fehler.weil.haett.ja.klappen.koennen.de",
     "Den.Zeitraum.vom.05.10.1582.bis.13.10.1582.gibt.es.nicht@weil.papst.gregor.mal.nachgerechnet.hat.de",
     "Fun.Fact.the.period.from.05.to.13.10.1582.exist.in.england@because.of.henry.the.eights.they.did.not.participate.uk",
     "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de",
     "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu",
     "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>",
     "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>",
     "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part",
     "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part ",
     "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>",
     "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>",
     "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part",
     "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part ",
     "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ", 
     "zzzz.zzzzzz@zzzzzzzzzz.zz", 
     "AAAA.AAAAAA@AAAAAAAAAA.AA", 
     "aaaa.aaaaaa@aaaaaaaaaa.aa",
     "Vorname.Nachname@web.de",
     "m.fanin@fc-wohlenegg.at",
     "old.email@test.com",
     "new.email@test.com",
     "test1group@test1.com",
     "test2group@test2.com",
     "test1@test1.com",
     "test2@test2.com",
     "junit.testEmailChange@example.com",
     "at@at.at",
     "easy@isnt.it",
     "yes@it.is",

     "M.jrvgr.fcnygr@ervur.mjrv", "Hwdgfl.Raykqr@kzggsaq.vdq", "Hmqbm64@ND.zd", "V.Sahwlpk@hcfhulyzhmlxp.pb", "Gfdko.Kdxvoixjqlu@hesljipy.nc", "Wohzx.Zdtzoyrj@jdqau.qx", "eaktnhwvgsbdx@eo.xspc.hbh.xkit.vu", "WPmveql@rpqcjy.pj", "bodap.ojxmkmsuqnsq@vedoz-dgoyjid.fb", "Gmwj.Yezqkaaa@ratbovznw.gdv", "p-dqlvh@omncwto.um", "WH@mzWbe.fs", "Eail.ycefg75@txnh.xooff.qql", "kqhwel.ozhxy@yspp.zu", "Anew-Fwkrwfn@bradfsaev.zu", "Maazgajgs@duvkt.ym", "QFurasdsdadsfrm@qf.asdfqi.ar",
     "NEhj.Jdbqzcwgizcixtfxxa@cK7n7.rp", "Kebxf.Jtisbooi@2qdMp.tv", "O.Fqnsj@PjiIlZiapdFknotvIxPxrhi.hy", "Qxwps.Wktckcfm@dmninvj.ri", "Jedjddjpaf42@qoepd.ei", "Hqvu.Wlerqacj@3mMKg.kv", "Ahsvpgwhn@wkdfnlbn.nw", "uickngdc@KX5bN.zl", "Hbcj-Apksdx@6qVqJ.ep", "Slmkk03@eitjozwcqe.ccl", "g.nfgtmegk@yvo.ddf", "Knkjpmdr.Gahgrmu@ApanIraeq.cd", "Xmqkqyu.Ryygdf@1pjZY.ihj", "rwsz-ehydyg@uuadaphg.bq", "D.Kavoglv.rcrsaun@mtn.scx", "Onimdehxf@aXW15.dz", "kpfjy-eztwmfcsdcf@ohzgq.ek",
     "bcebjmem@aczfpjix.qa", "Crksl.Vnvdyy@tdyefiyvwt.pd", "HvnzAzifzl@cwf.gi", "d-pvwdcg@dcdgq.ai", "Jwdca07@djwp.qx", "jays.fwjro@jpdf.psr", "Fvcetk21@xlcpbgue.nt", "dyaazjannzraq@fsfoiwit.bz", "hcvmofwpy.oex@dvsmdzxark.ef", "KllgqyrRqoein@mtvihvccjt.fne", "Ljsyiieum-Aauyfqm@nuhomyhsu.scd", "NAyolnoj@AbsDmDshwp.lt", "Oidgrtdwdsoefs@wkdnkmxep.tgt", "HN@vub.ytp", "f-bwdeuc@Gqzwdm20.zj", "Oqzcnmmxn-Kkend@egpijolvbb.ddj", "z.heymkjc@yiw.zjf", "Cyxnkhy-Kiizemj@gink.gim",
     "Pkic.Knhy@Rlhx.fn", "ddibc-vysf@PpyeBeoOmjsz.wu", "Aqfa.Yjy@finvtljx.cc", "q.rdxkvpd@dtsjate.um", "Wvcb.Hwfdyn@uhdlw.bv", "MF@tthj.pne", "J.Zmgnnzm@XQEO.bf", "slbmd.swjaqpwtborm@tdgb.cz", "BqwuvcfSmfcq@xmxk.fve", "Giyqom-Lsbguy@uvuxz.ji", "Flsazx.Mpec@Abqf.co", "EymcqExv@wmgcduv.ar", "Xdsp.Kemtu@fbi-bod.ow", "o.kyubjs@xxrtwb.ye", "hbesxpvfeqfuo@qjloep.hm", "gfct-acdnlc.tcnned@pcbak.mq", "lsial-okoqyt@kswlaa.xa", "Rgxtk.Tmcatr@lrqfpxb.tq", "x.poiyqc@swsufbpb.cq",
     "Abev.Ljxngsdg@6wHIk.ah", "D.Tseyims@PKCN.wa", "Maqkpa-Qiznlj@wlljh.af", "LvfkyGhj@voxlgchfgk.shb", "qcgiuanqxzz@joupvqge.nm", "Dpvvm25@uxupdhlksx.wik", "NF@dwusbeas.dwe", "SygzvfUljbbn@ufqburicrmv.db", "Ujsbui67@zuvf.ls", "l-hiqec@loint.ea", "dzyg-usgf@tlcnzr.ui", "Zmgr.Dspriunv@kjtlq-nxk.vbj", "Bgcq.Jew@wmnolrs.sy", "Oxwv.Ggvfww@rQ2o3ds.vn", "Czcm.Fhqzo@fnob.dfv", "igsq.djn@jaiy.scv", "Dgp.bvbxj40@rzdfsbuz.qp", "Towfep.Zpkghf@dlszbdl.txi", "c.lixuapkx@fxa.nao",
     "aetaf.zedxzbzyfuux@njuyn-ozizmks.vc", "Klxjkbi-Jpeeskn@yhmi.tfs", "xrbti.agotiwkngkwy@xgjw.hw", "PA@cqtg.uxi", "y.txrcymq@hnskyyt.sz", "eedrikwqgikhq@iygjnniw.bw", "Mabdhzh.Eitpuf@2glMX.rfd", "j-lvzzid@Quxpng23.hk", "XoebwAekiqt@peykzchg.er", "Frsycnuwb@nFP15.tl", "Rfnmt47@EkvNF.pg", "Lbpcs04@RF.du", "TmegzgsMyrsa@bhbj.zat", "adiva-pvbl@CznkNgoCgdbg.cy", "VT@ljHoo.nn", "Wbjji.Uazxlroms@zpyyju.fdw", "ogwfanjdcegiksgmvhk@nhmfffstdy.ns", "mjwj.mjpo@wpaooyfdgyd.st",
     "Xrjaqahl-Uciswnk@qzskgqpor.ybf", "serx-nlitpqtxe@qmosuxvo.pw", "Faqweq.Uout@Sidd.nc", "IEtqhzot@LaeExUnetyKypkluJaQzpie.ll", "Zawmr.Uecquv@ehdzlkfstf.yk", "Zvkl-Cffale@6lIlH.gz", "Jifko.Kwwdd@wm.pfkw.saq.mdfu.tj", "wgrc.kvtrgnaz@m-mghvysq.ts", "Z.Gzyvl@GtuOePqdmpCrncdcVrEageq.wa", "Eylhp.stq@gui.ea", "Kpsia.kfu@Figg.wl", "Uknkh.Tyguc@dczrasi.ay", "Rrcol.Uwjctifzh@pbxif-ceamp.ur", "Dxc-Cofx@tuluuqzydq.ect", "WpclzvoQurwqk@egxniktftb.hhr", "Alzhhlsfq@lcgdillh.ad",
     "gts.rgs.onruq@qo.sdfuzx.ay", "caryjqojhp.ywhmseipat@lwskqhr.cbs", "Mwmkmaynv15@gydlsqctoz.qlr", "Khaz.Ayxyjffs@1ndUv.ma", "C.Dzosxhe@mernihvqhrdwl.cq", "Ikipo.Rxekt@yidwqfxu.rih", "SXmfyqr@dcnd.le", "K-Caqyddn@omth.xw", "QmcigUuq@onyjb-bfcksmw.uw", "n.vadbgpd@etb.hnp", "Rslsltqfy-Osyzr@owfmcpwemp.aoz", "E.Zuzbxojbqgsazrnb@zsr.ynq", "Hxkhu.Khodp@Easdfg.un", "Oexwcanp.Ckfbp@rrlkmsdxj.id", "td-hjvlf@ufpdeyh.oq", "Sniblr.Dapkvq@lbAAU.se", "Kefbq.Bcyps@YxnwIltow.zz",
     "SPfwrwr-Ookpesja@mhhby-ljm.chn", "NVkcc.sdfadwems@wymeddafi.yf", "Cvzzjing14@jfboktwhdw.sdm", "MlxsDipwov@aav.kss.yd", "Jdjexw22@zqejrvcg.nj", "utomrpubkxidl@pm.ktlp.iiu.kigl.mf", "MocvrdcbmmHccxvbv@lkdblnmidh.weq", "xdhug-zrhvak.xcihvtc@btpgs.ni", "zytxu-fcvjocajbgrondkfbdxvky@jxavrdw.to", "vfyxop@SZ7mK.ao", "Mccpaoc.Wqsrsuezsi@sjsakvAb.qt", "Sfwh.Pbfxh@hpofcqh.rw", "aidcs.sctzn@xvkkerfd.vn", "NZ@jxm.whm", "Zyrcg00@FvyNG.bi", "Lskio.Bhgkccnzn@kunwq-pykruvq.wt",
     "JAlmbnyyc.Ixzjpbcqfjutmv@xnfwilwu.cx", "Kqkpxaea54@spslrqdyht.aou", "qqlcrorwlj.foumryqfyw@ucicgkq.ri", "Cwdov.Xmhhnzgtqhl@Yxded.kv", "Ukriu.Fysdfj@gkvxo-atxcfsdjh.ou", "BuspnsicjNjlvucg-Orql@ywbyreof.fvz", "Thvpj.Grcze@tqfbngdn.xil", "GbtqliVwqlum@nvlfqqxpihz.kb", "AJ@owfkdfyx.vaf", "GjuzyMgrcvb@ujdznn.gt", "orgmelfcdms@cwfyfjlf.wi", "ZofjMut.Grbncubz@urMIC.fw", "Srmnr.Jvfjb@xa.iivd.mtb.figx.zdh", "rvxq.ssedfkdp@f-noaxrrn.ko", "Ycxvlzypmb.Iueot@imxzuqdmcnd.sv",
     "Lnhffmy-Cjyamqfc@rvnxr-uxi.uoq", "Kwyav-Jfzqxv@NaablVcmlimw.tz", "Zwnos-Xccjett@xqjdenvziiz.ae", "G.Jnzgcjejsvb@gvjft-nyujwsk.ao", "i.ppulbak@dpsl.yqm", "N-Eutgimm@yboxadqx.ho", "Tovwk-Dqysxp@oskrywgm.sjo", "Udfbcc03@aocnuhbm.ru", "Iqua-Joxbfk@Rftdq.ag", "XT@ogh-mzncms.cg", "xllcty.bxqnah@QdhkXxqPxoyd.ik", "lhyho.kpmsyharl@vqjzn-ulmgdbc.daa", "Bdfse.Fnmxc@vsddzaj.hj", "ZH@ciojaguv.jkf", "Wkhx.Hau@QulzBesdsjmj.ooa", "Wgdj.Mnfaggdelfan@qdxao.gh", "Rwwsf2@ypm.yjv",
     "Xdksmy-Lwohwiewh@GiyRhRyankLpwqpzHpUcaqo.mb", "UU@XS.pb", "Voqqab.Exkkulilmd@ziiipkwn.nd", "LvrpeKcg@rutma.zo", "D.Whivgbsvz@bfwgdlbkrk.bcu", "DqemdsuCukhloaa@rzqmmyq.ce", "wrtdi@zeeswtvt.wp", "Y.Usltk@dfksjs.adskfj.kd", "sagtr@ravuw.bwdjclr.mx", "Jfef04@jN.Nb.edu", "zhcioja.guvjk.fiyt@etwqwqj.vlr", "Gnvsyd-Ulysssdqocf@Rzgmsdso75.nx", "j-edznx@msdfsgUka.ndw", "qxhg.xfoea@GM.ld", "Fckda.Epwyq@obghs.paa", "Kpk-Obypkvc@xior.hhte.ycfcge.de", "Qyltw.Pyhyi@iadiw.otv",
     "grreeq-gvknuua@insdfsdkgb.aas", "Ndsfbq.fMvxwkw@Rdyg.mb", "Reonkrl.Cjvvk@ZskfeLrMmkdefzOrphlct.gt", "EkwkosRwsapxlhsy@HefknctaiUilk-Soaieqrxh.gg", "kjhsjliuvn@dogl.dn", "PepnwGal@qost.pel", "T.Lri@pqciesturn.nc", "G.Jnzgcj@ejsvb.gv", "Qblz.Btlvc@2WaOs.hhg", "Tejhapzbciag46@fkhnnbsgtmoug.bt", "Tovwkdq75@xposkj.oh", "Llcv.Iye@dgddushz.ng", "iqRajoxb@fkrftd.qa", "Dqemdsu.Cukhl@oaar.zqm", "Reonkrlcj66@kzskfel.rm", "Yusltk.Guyoudps@lvusb-ehu.jxg", "Tfcv51@dzaajh.ji",
     "Ietqdo.Hadem@fhwpwrw.gkd", "TbJzdPhbpy@cghkgmb.aas", "tlripqciest@UrnNcaAvedUcrZvv.ff", "Dtduas-Mchufmv@Japfea.vv", "Glmhdc.Bevasfasadtb@nsdffjd.zb", "Wbetsnvbh-Lhdxmbrfcwia.Kvbi@bhrokmr.az", "NF@yzewidzv.mq", "Poachuri10@hbtu.xrz", "Dqemdsu0@ukh.loa", "Xllct70@Xqnah.qd", "oymsr@Ijfwrvj.xj", "ZT@NG.vx", "JI@Dnynbn03.ce", "Fzgpg51@Ibrcw.ne", "qvgdkgffbuc@ffbemmqxfq.gad", "BtiaLvycoh@ApfifkCbegq.map", "Mrjwlj.Rzbuy@Yfpgis.nk", "GJ@nzgc.jej", "Wdav-Szrks@BHkkI.wq",
     "Ismytnuud-Ayrqkiin27@Pxuyp.aj", "Voqqab-Exkkulilm@dziiipkw.nn", "qylrettwpyyz@HY.ii", "dwhivgbs@Vzbfwg13.bk", "Zzrcnk.Qtxjpm@ynnzbtmn.oc", "KR@kwyy.hj", "ybzniljaaymam@nwwiesy.fg", "x.qoykm@HnqcUczjl.ez", "Einzsrz44@lskwh-kczsyzv.ym", "dtizy.gjsio@Dfpxen.ww", "Tbzdp-Hbpy@kG3Ba.as", "Tlrip.Qcigfdfstur@nnca.aved.ucrzvv.ff", "Dtdua26@fmvjapfe.av", "yhdjayq@qang.uvt", "veig-najrv@qdxyienb.in", "osiwzvcqvcb@ppdflmi.kb", "nfyzewidzv@mquq.uwx", "pbpfz.gimv@tiucei.buj",
     "CepjLgezwjtt@ZZ.ld", "Ggnqjc.Rfwixazm@jhoaklm.ee", "Fwghj.Rmiogewg@vdovfqf.paa", "MVnkgx@gqpzusgmjeo.tb", "Waanvz.Zkgrjjd@zwtojtdrie.era", "fcgydrwhn.bawun@CkhgrxjkcBpsl-Seximovpz.oj", "edojy.ropqqx@KioKg.or", "Yhdjayqqa@nguv.tzqk.ywvisd.rl", "JL@Hbtaqdo.qy", "FS@yoqqbbzh.kik", "Llcviy.Eddus@Hzngfrgls.gqv", "DxlnTzzq@tijs.cup", "xlcxqn@Ahqdhk.xx", "uryj.sbpfoy@d-vuymhcc.dg", "Ekwks.Rwap@Xlhyh.ef", "Mvnkgx.Gqpzusgm@jeotbvw.mf", "Wkhx.Hauq@Ulzx.mb", "PE@pnwg.alq",
     "DnzgGfyltp@JstnwtJxjhp.olh", "gpuavdmllco@ojorxmafxv.kbp", "IppulbaKdpsly@qmcpcbnagq.xuk", "sNvb@hlhdx-mbrfcwi.ak", "mofirwdjnpcf@DS.cm", "Q-Rdqpsqp@npgl.ef", "mmgauw-ljbfee@wjcoqt.gj", "e.sdfql@fzh.saw", "teu45@OY.pq", "t.quehd@IyehNpztr.wc", "Sar.Avuw@Bwj.cl", "WE@ebav.fde", "A.Jxlnww@pfljr.re", "Kjhs.Jliuvn@dogldnq.kt", "Aayiy-Aslrwco@ralg.ool", "Rife-Zsxasea@Poari.as", "Qbllv02@waoh.hgo", "Aeyvt63@Qgqnq.dl", "Uuxspb.Hemum@zkeuvsv.pa", "Osv.Cqcbp@pdmikb.fdq",
     "ZAkk.Dkd@nwfbyhbg.lw", "Vhipkab.Riabx@fsel.hwa", "Eszodfqlf72@sawvqolxqm.gza", "Eakvee-Azclaqhrh@kslhtvuf.gs", "Epva.Isc@vrvlyzas.us", "Lfdfoyudh-Ihueozruedrx.Wjhe@qondavq.fu", "Gnvsy16@LysQO.cf", "Pfttutwcr-Irnjr@pabjvwhoox.wfx", "wDav.szrkS@Bhk.kiw56.xg", "Vttmr.Olnkzofq@rpxbcmf.rw", "k.pkobyp@kvcxiorh.ht", "Qoacu.Fczzkkrd@2wuTe.pd", "Ubftfcklo@dEN47.bo", "Pbpfzgi31@dvtiu.le", "adsdfL.Czr@ulfntdldads.vv", "GdjdO.pdoi@DBgyP.fl", "cdtpjtapydsnt@mp.blws.uopy.cx",
     "ETnzsrz44@lskwhkczsyzv.ym", "dtizygjsi.odfpx@EnwwdbekgUzjs-Rprasfmmgthn.gw", "Tlripq.Ciestu@rnncaav.edu", "BZ@mwc.ifs", "QblzBtlvcjwa@OH.hg", "Tejhapz-Bciagouf@khnnb-sgt.mou", "Rhovhrdbwg63@gckdk.yf", "Opfi.Ehozhxfv@nhbpq-btn.zwc", "Poach.Urigc@hb.tuxr.zjq.iqul.uv", "BQ@Jsovgdc.xc", "R.Eonkkfe@lrm.mkd", "ztgvxkdwuv@sqhkfzzh.ql", "bunf-iivqbyiz@vjisuhlz.or", "vlqidbm@yxadw.lbb", "Dvdcx.Sebte@dmctznlr.nes", "Hzfp.Tcoyuyfm@7ssdfsCPv.nd", "Jcexe.Nbqgdn@qafpkqhc.bg",
     "vwtm.roln@kzofsdfqr.px", "IRpc-iasdfd.bsk@PaqLfZlavpPwxyepIxTwdby.nm", "lvrp.ekcgr@utma.zoc", "Rrzkr16@ymuiskswrz.rdm", "LFzzrlx@nfoj.ak", "Cdpjlge77@jttzz-lduwymnd.qd", "Tqueh.Diyehnpzt@rwctz-bggvysw.gc", "Thnvu.Cnrqnwaq@qjgdt.eq", "Wyafy.Ahltgljwpxv@Iwfuu.qq", "Tbjzd.Phbpyc@ghkgmbaasv.of", "Nlbvw24@Tzkgl.cw", "Hfjllpvo64@zhfzfjxh.hg", "jwwvo.mogoplxtxc@jika.sg", "Irpci05@kpaq.lf", "zkgwi-wpxi@AfegEcpAznec.kn", "Cjxkbst@vnjfpzemviz.nu", "Qasfd.Udymsip@AZEM.lo", 
     "ne-yhbwa@Clgkrb76.td", "Sekh.Meoli@0XqsdfF.fgh", "ekwkosrwsapxl@hsyhefk.nsc", "asdf-aqbavb@jqsdffrtd.ib", "vqpyxb-wxwlpumbfwv@rmckgbqxml.nl", "hzfptcoyuyf@MxsCpvNdjaQuoBzq.pj", "wdavszrksb@hkki.wqu", "Ippulbakd-yqmc@pcbnagqxu.kcc", "Fdhr.Zw0dtry@OIdqm.ke", "cjxkah.hfysm@hbstv.nj", "p-oachu@rigchbt.ux", "Lfzz-rfoja30@vsdlzlosknpk.gst", "G.Gnqjcrf@widzmjhoaklm.ee", "FwghJr.dsfmiog@edsfsdwg.vwd", "Fwcc.Ldprnmgclhyqfmnzgy@kU6r0.wc", "kjhsj-liuvndoqnxjdj@pwzdmwq.qc",
     "SbcxzdTfuhhc@vdzrpstvvcq.kp", "Qblzb.Tlvcjwaohhg@okbdsstk.zq", "E.Jsfqe@VfrFrEruoaQlcfeaCdRteea.ug", "YavhkxnKyeog@sarx.rlp", "Qrdqpsqpn@pglef.aq", "UvcxnoevnO@ksFry.uj", "Bqjsovgdc@xcoy.zfmw.oqxtum.xk", "eakveeaz.claqh@rhkslhtv.udf", "Hii-tihyxhm@ifhduub.njn", "w.yafyah@ltgljwpx.vi", "Wawixmcte-Wlqilsux54@Yigoh.aa", "Tlrii15@turnncaave.duc", "qxhgxf@OE0gM.ld", "Fckdae-Sypwyq@obix.yu", "Neutgi.Mmybho@wbtqi-swk.bqx", "jcarg-zvxzwsed@nasdfehdey.ny", "IE@kkQzs.lu",
     "BPiiiuqcbrYpmseiip@rpneohnbnu.ktr", "cadfas.dpjlgezw@jtdsfsdft.zzl", "uyfli.cslbtgmg@ocqftjep.ua", "Syglsdur.Ivimdw@wzaadsffdn.ly", "Weebpn-Nauvfde@vAdfdd4Kp.kr", "dtiz.dsfsdsfy@Gjsiodf.px", "Iiit.Wayebfax@wwekxpuko.shs", "tlrip.qciest@UrnNc.aa", "Gpuavdm.Llcooj@4rxMA.fxv", "MyrxbKugder@mhinalzs.jc", "irpci.bskaq@lfzlpw.xye", "Qoacufczz33@rdiute.pd", "Ubftfck.LO@den.NXBol.hx", "Ybztg15@bbtiznazwg.oku", "Cnklrlrnq-Dflsw@kyixvikddu.gac", "XayflZjbpnw@lfawhgdy.ix", 
     "Zdvf.Hkeutk@dozjnra.lc", "Srmi.Fxu@kjsypcuz.wj", "z.tsopqdi@fhdrlyd.bl", "s.wbbfmb@tnwgjgea.mb", "Rgubv.Ixytaccm@5stDx.ny", "v.gmgyho@vwrdbrzg.yj", "dneys-wdiawkwtfionixmfiydxjo@mswpwgl.sl", "Vqkut23@PQ.zp", "Wjrgcaoydf.Pjqno@hmebjxkiech.ks", "C.Pvbwhyd@NVEA.ze", "FfwkfIkn@vxxlvrigqx.zro", "ytxn-hiqcnwgqy@ukyttcc.ez", "Cgrcj.Vwwdpqrvmfl@Eszag.hc", "Oclh.Tjczda@hmGLG.sd", "Hnvhjw-Kznakx@dcmbw.qc", "S.Kqirhewg@nhi.suz", "k-mufcf@enbziay.mq", "vkqy.ubhgg@xhqf.ysl",
     "wehxbvkpfxwophilnas@yfpbomrnnz.in", "Zobjm76@DqlGK.cp", "Osuwy.Bhuzrqsu@xdvsqtt.au", "Jadzp.Buquiw@fbqwtxuiqw.ry", "Mjbfqd.Ghnl@Szly.ve", "P-Ocjjrpk@vsnu.mr", "dtiexabpwnqsg@ob.bmbh.hdf.rbog.gp", "Fntmy73@jsgk.nj", "Jezy-Zjjvcu@7uPrP.ga", "Cmfrzq67@vytbehnm.ce", "Blxhr.Zckhr@xs.nkkh.tkr.poto.th", "Qzic.Nngvaeeu@hhoxowvda.fwc", "xkuzu.rspdhbxelcpy@tmys.ih", "ykeh.wonephso@u-uesyrrw.sa", "x-pqjcpx@yhosl.gn", "Mrjcmtb-Jwkcsrl@qeiu.xre", "Kvxmpetsyta@unlaoxgmp.aps",
     "Imdmfoflw32@bhlnbzmfuk.old", "psbigg-hlvzqs@daalvo.vy", "oaxuamzmpia@xwcksprr.ja", "qbpixxjfqtwyk@bnmmyubp.gx", "t.uarnowz@iez.dtl", "Aumlsvk-Wdrtgsvr@riqsq-nfy.fcv", "ekbkg-xpfz@TkxuThwGqapi.wk", "Pcjoiuxmm@ydzlupse.cj", "ldbhr.pjjndykqkiuu@dywek-ackffuo.ei", "bnmbulkngu.lkpenvrwpi@lifvczv.mu", "Iuzphbt.Nuudbo@4rsEM.avm", "Uuxdj.Vxihmwcnn@tkwbu-vioplqp.au", "WhzgrIns@kiiep-cgcxijn.xu", "Vtnhpdndw@eeayw.ju", "Aqjy.Yeenkkacjybd@fv1.zb", "gannzqjcfjbor@wzzzdh.dj", 
     "WecuicizlhRqjxxyur@mqpyzmmhhx.gdp", "Xaxbcvjfh.Mxizpmeeznwvci@fwblacpn.yy", "jiisry.kxaix@upcmm.en", "AY@atmjmjqx.ghp", "lplvx-yjjnso.hkmihrb@svhmt.fu", "J.Eaqjw@XfiFlAwdmyFqjcjsYlIdgeq.xu", "YjucpcwhzGtcgovcze-Pddm@sxroudwrtp.oei", "Taqudpybrx17@skglk.uy", "XnclTkpstc@mlt.wg", "BewteedHyzrz@cfdk.xvg", "Mqazs.Xnqkdknpnx@indisxok.mj", "z.feoalquq@tlm.nrf", "xngdkkqa@RY1jI.di", "TSsokaz@oipqc.ni", "Ppkx.Hioimqgq@ophun.bll", "Gzirbnz@jzimz.hlr", "Z.Nipm@ajdqih.vr",
     "TTldvlw@rwnj.fp", "VgewxwKmggal@ctqgzlunbrs.zi", "Lfmcjwnci@pVF11.ec", "Ckpqbc.Xsetak@cbdhayt.xat", "Csnurdcx.Hwllclt@NbdcMoygn.ez", "RTaa.xgbi@XsrUxYqekzFrlfoxFvJvpnu.bl",  "Gdkfu.Aryase@mrqcrosu.etm", "Vfhhpymtj-Ijgibsos@ecckjbxfn.dec", "b-eeubah@Ulbesng73.sn", "Ylgx.Aybzmsip@7cVsdfMa.lw", "Gflb.Jdgn@Rgydfdm.zf", "Ltzdsl.Xizqjbpi@vissdubd.el", "AoKqb.Emnxax@Xio3mvp.vp", "ELr.CwZ.infse@ZedL.XM.el", "Raftzy.nwdfmao@Fhizjzyf.mmeixzs.ah", "No.uvre@fcnygr.RVaf.Ur",
     "EwzsvpcpvQxlochrel-Qscb@ylcuuafvpz.ryh", "cabir-sxkun@ithothmxixri.ie", "kEzcdygr.knsd@ugzbwosgsfk.le", "bhupdvd60@qgndarog.ev", "erdgvetBnu@cyoy.bfr", "rWzasdfopro@tfpdnu.hu", "tuwtnFdfdrn@gkfkxljsdl.ify", "Ppgsdfk-Jmwfno@7rSpJ.kk", "Akhsfddfdsfvvg-Adoezhy@kG6Aw.yp", "nzvsdffw.mztotlwxwyep@btuyr-yfxgatf.xu", "dzoxuxyqhrb@djtfwxi.hm", "ravevakd@Ensznf41.sv", "ecbdl.Ivytgvye@duCsdfRA.kdu", "shLi-uasdfJdzwb@mxpqohq.gvn", "enkf.ON@rodsu.grn", "nwvkc.xbfob@yrag.xj",  
     "Afom.iqfsekdw@vjqtisdfwmj.jdg", "Tlufsbzxba.gxxfueqlaa@ugclozu.yv", "wedr.eebtsgb@jmnzdvus.bs", "eaflfnkyk.Bnpbpplitoofoc@izfkuvgv.ju", "bkkdo-sdasdffwR@gfdb.asdf.ttm", "Paadf.klddsksdfP@gkjmwfasdfno.zr", "dnbmdt.Elkyfg.swxsbrbl@lxhh.dfsgrgo.adyk", "eotamr-slkdffxlaS@TctvdsvsadfzvO.cwd", "And.To.all.you.Reg.Ex.eMail.Validator.offionardos.out.there@remember.the.soundtrack.is", "I.just.call.to.say.i.hate.you@sarkasm.com", "DANKE@terima.kasih.com",
   };

  public static void main( String[] args )
  {
    try
    {
      /*
       * Initialisierung des Formatters fuer die Laufzeitwerte
       */
      otherSymbols = new DecimalFormatSymbols( Locale.getDefault() );

      otherSymbols.setDecimalSeparator( '.' );
      otherSymbols.setGroupingSeparator( '.' );

      number_format = new DecimalFormat( "###.##", otherSymbols );

      number_format.setMaximumFractionDigits( 30 );
      number_format.setMinimumFractionDigits( 7 );

      /*
       * Tests ausfuehren
       */
      startTest( "1" );
      startTest( "2" );
      startTest( "3" );
      startTest( "4" );
      startTest( "5" );

      /*
       * System-Informationen fuer die Vergleichbarkeit ins Log schreiben
       */
      wl( FkString.getSystemInfo() );

      /*
       * Log-Datei erstellen
       */
      String home_dir = "/home/ea234";
      
      schreibeDatei( home_dir + "/log_test_validate_email.txt", getStringBuffer().toString() );
    }
    catch ( Exception err_inst )
    {
      wl( "TestTemp", err_inst );
    }

    System.exit( 0 );
  }

  private static void startTest( String nr )
  {
    try
    {
      /*
       * Arbeitsarray auf den 1000 Testdatenarray stellen
       */
      array_test_daten_aktuell = array_test_daten_1000;

      /*
       * Ermittlung der Anzahl der Elemente im Arbeitsarray
       */
      test_daten_array_length = array_test_daten_aktuell.length;

      /*
       * Durchlaufanzahl berechnen
       */
      durchlauf_anzahl = test_daten_array_length * FAKTOR_ANZAHL_ARRAY_A;

      /*
       * Die Durchlaufanzahl kann auch auf eine feste Grenze gesetzt werden
       */
      // durchlauf_anzahl = 3000000;

      stellen_anzahl = 9;

      int test_daten_akt_index = 0;

      String test_daten_akt_string = null;

      /*
       * Im erstem Durchlauf werden die Pruefergebnisse ins Log geschrieben
       */
      if ( knz_ausgabe_test_daten )
      {
        wl( "" );
        wl( "--------------------------------------------------------------------------------------------------------------------------------" );
        wl( "" );
        wl( "Testdaten" );
        wl( "" );

        int breite_x_feld = 7;

        String akt_dbg_string = "";

        knz_ausgabe_test_daten = false;

        test_daten_akt_index = 0;

        while ( test_daten_akt_index < test_daten_array_length )
        {
          test_daten_akt_string = array_test_daten_aktuell[ test_daten_akt_index ];

          akt_dbg_string = " ";

          akt_dbg_string += FkString.right(  "          " + test_daten_akt_index, 4 ) + " " + FkString.getFeldLinksMin( FkString.getJavaString( test_daten_akt_string ), 85 ) + " ";

          //akt_dbg_string += "EV4J " + FkString.getFeldLinksMin( getStringIsValid( 6, test_daten_akt_string ), breite_x_feld ) + " ";
          //akt_dbg_string += "HCBval " + FkString.getFeldLinksMin( getStringIsValid( 1, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "Jmail " + FkString.getFeldLinksMin( getStringIsValid( 8, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 1 " + FkString.getFeldLinksMin( getStringIsValid( 2, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 2 " + FkString.getFeldLinksMin( getStringIsValid( 3, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 3 " + FkString.getFeldLinksMin( getStringIsValid( 4, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "JAVA 1 " + FkString.getFeldLinksMin( getStringIsValid( 5, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "ea234 " + FkString.getFeldLinksMin( getStringIsValid( 7, test_daten_akt_string ), breite_x_feld ) + " ";

          akt_dbg_string += " = " + FkEMail.checkEMailAdresseX( test_daten_akt_string );

          wl( akt_dbg_string );

          test_daten_akt_index++;
        }
      }

      /*
       * Alle hinterlegten Validierungsfunktionen aufrufen
       */
      wl( "" );
      wl( "--------------------------------------------------------------------------------------------------------------------------------" );
      wl( "" );
      wl( "Testlauf " + nr + " Anzahl Testfaelle " +  FkString.right(  "          " + durchlauf_anzahl, stellen_anzahl ) );

      startTestNr( 7, "ea234" );

      startTestNr( 8, "Jmail" );

      startTestNr( 5, "JAVA 1" );

      startTestNr( 2, "REGEXP 1" );

      startTestNr( 3, "REGEXP 2" );

      startTestNr( 4, "REGEXP 3" );

      /*
       * Diese sind nicht implementiert und muessten selber eingebaut werden.
       */
      
      //startTestNr( 1, "HCBval" );

      //startTestNr( 6, "EV4J" );
    }
    catch ( Exception err_inst )
    {
      wl( "Fehler: errStartTest1 ", err_inst );
    }
  }

  private static void startTestNr( int pTestNummer, String pTestBezeichnung )
  {
    try
    {
      String test_daten_akt_string = null; // aktuelle eMail-Testadresse aus dem Arbeitsarray

      boolean knz_is_valid = false;

      int anzahl_korrekt = 0; // Anzahl der von der Routine als korrekt gemeldeten eMail-Adressen
      int anzahl_falsch = 0; // Anzahl der von der Testroutine als falsch gemeldeten eMail-Adressen
      int anzahl_fehler = 0; // Anzahl der aufgetretenen Exceptions waehrend der Pruefung

      int stellen_ms = 6;

      int durchlauf_zaehler = 0;

      int test_daten_akt_index = 0;

      /*
       * Die Startzeit merken
       */
      long millisekunden_start = System.currentTimeMillis();

      /*
       * While-Schleife 1:
       * Diese While-Schleife laeuft solange, bis die Durchlaufanzahl erreicht ist.
       */
      while ( durchlauf_zaehler < durchlauf_anzahl )
      {
        /*
         * Innere While-Schleife 
         * 
         * In einer inneren While-Schleife werden die Testdaten aus dem 
         * Arbeitsarray durchlaufen. Diese While-Schleife laeuft solange, 
         * bis die Elementanzahl des aktuellen Arbeitsarrays erreicht ist, 
         * oder bis die Durchlaufanzahl erreicht ist.
         */
        test_daten_akt_index = 0;

        while ( ( test_daten_akt_index < test_daten_array_length ) && ( durchlauf_zaehler < durchlauf_anzahl ) )
        {
          test_daten_akt_string = array_test_daten_aktuell[ test_daten_akt_index ];

          try
          {
            /*
             * Die auszufuehrende Testfunktion wird ueber eine Testnummer identifiziert und ausgefuehrt.
             */
            if ( pTestNummer == 1 )
            {
              //knz_is_valid = EmailAddressValidator.isValid( test_daten_akt_string );
            }
            else if ( pTestNummer == 2 )
            {
              knz_is_valid = isValidEmailAddresseFkt1( test_daten_akt_string );
            }
            else if ( pTestNummer == 3 )
            {
              knz_is_valid = isValidEmailAddresseFkt1a( test_daten_akt_string );
            }
            else if ( pTestNummer == 4 )
            {
              knz_is_valid = isValidEmailAddresseFkt2( test_daten_akt_string );
            }
            else if ( pTestNummer == 5 )
            {
              knz_is_valid = checkEmail( test_daten_akt_string );
            }
            else if ( pTestNummer == 6 )
            {
              knz_is_valid = checkEmail4J( test_daten_akt_string );
            }
            else if ( pTestNummer == 8 )
            {
              knz_is_valid = JavaMail_InternetAddress_checkAddress( test_daten_akt_string );
            }
            else if ( pTestNummer == 7 )
            {
              knz_is_valid = FkEMail.checkEMailAdresse( test_daten_akt_string ) < 10;
            }
            else
            {
              return;
            }

            /*
             * Auswertung des Pruefergebnisses.
             * Ist die eMail-Adresse von der Funktion als korrekt erkannt worden, wird 
             * der Zaehler fuer die korrekten eMail-Adressen hochgezaehlt. Andernfalls 
             * wird der Zaehler fuer die falschen eMail-Adressen hochgezaehlt. 
             */
            if ( knz_is_valid )
            {
              anzahl_korrekt++;
            }
            else
            {
              anzahl_falsch++;
            }
          }
          catch ( Exception err_inst )
          {
            /*
             * Kam es zu einer Exception, wird der Zaehler fuer die Fehler hochgezaehlet.
             */
            anzahl_fehler++;
          }
          
          /*
           * Index fuer den naechsten Index innerhalb des Testarrays hochzaehlen.
           */
          test_daten_akt_index++;

          /*
           * Zahler fuer die insgesamt durchlaufenen Testfaelle hochzaehlen
           */
          durchlauf_zaehler++;
        }
      }

      /*
       * Millisekunden Test-Ende ermitteln
       */
      long millisekunden_ende = System.currentTimeMillis();

      /*
       * Zeitdifferenz zwischen Start und Ende berechnen
       */
      long millisekunden_zeit_differenz = millisekunden_ende - millisekunden_start;

      /*
       * Ausgabe der Testdurchlaufwerte im Log
       */
      wl( "" );
      wl( FkString.getFeldLinksMin( pTestBezeichnung, 10 ) + " anzahl_korrekt = " +  FkString.right(  "          " + anzahl_korrekt, stellen_anzahl ) + " - anzahl_falsch = " +  FkString.right(  "          " + anzahl_falsch, stellen_anzahl ) + " - anzahl_fehler = " +  FkString.right(  "          " + anzahl_fehler, stellen_anzahl ) + "  | MS " +  FkString.right(  "          " + millisekunden_zeit_differenz, stellen_ms ) + " = " + FkString.getFeldLinksMin( number_format.format( ( (double) millisekunden_zeit_differenz ) / durchlauf_anzahl ), 25 ) + "  = " + getStringLaufzeitAusMillisekunden( millisekunden_zeit_differenz ) );
    }
    catch ( Exception err_inst )
    {
      wl( "Fehler: errStartTest2 ", err_inst );
    }
  }

  private static String getStringIsValid( int pTestNummer, String test_daten_akt_string )
  {
    boolean knz_is_valid = false;

    String ergebnis = null;

    try
    {
      if ( pTestNummer == 1 )
      {
        knz_is_valid = false; // EmailAddressValidator.isValid( test_daten_akt_string );
      }
      else if ( pTestNummer == 2 )
      {
        knz_is_valid = isValidEmailAddresseFkt1( test_daten_akt_string );
      }
      else if ( pTestNummer == 3 )
      {
        knz_is_valid = isValidEmailAddresseFkt1a( test_daten_akt_string );
      }
      else if ( pTestNummer == 4 )
      {
        knz_is_valid = isValidEmailAddresseFkt2( test_daten_akt_string );
      }
      else if ( pTestNummer == 5 )
      {
        knz_is_valid = checkEmail( test_daten_akt_string );
      }
      else if ( pTestNummer == 6 )
      {
        knz_is_valid = checkEmail4J( test_daten_akt_string );
      }
      else if ( pTestNummer == 8 )
      {
        knz_is_valid = JavaMail_InternetAddress_checkAddress( test_daten_akt_string );
      }
      else if ( pTestNummer == 7 )
      {
        knz_is_valid = FkEMail.checkEMailAdresse( test_daten_akt_string ) < 10;
      }
      else
      {
        ergebnis = "----";
      }

      if ( ergebnis == null )
      {
        ergebnis = "" + knz_is_valid;
      }
    }
    catch ( Exception err_inst )
    {
      ergebnis = "EXP";
    }

    return ergebnis;
  }

  private static String m_stricter_filter_string = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
  private static String m_static_laxString       = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";

  private static Pattern m_pattern          = null;

  private static boolean isValidEmailAddresseFkt1( String email )
  {
    /*
     * matt.writes.code
     * https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     * 
     * Pattern wird immer neu erstellt.
     */
    Pattern p = Pattern.compile( m_static_laxString );

    Matcher m = p.matcher( email );

    return m.matches();
  }

  private static boolean isValidEmailAddresseFkt1a( String enteredEmail )
  {
    /*
     * matt.writes.code
     * Pattern wird nur einmal erstellt und dann wiederverwendet.
     */
    if ( m_pattern == null )
    {
      m_pattern = Pattern.compile( m_static_laxString );
    }

    return m_pattern.matcher( enteredEmail ).matches();
  }

  private static boolean isValidEmailAddresseFkt2( String email )
  {
    /*
     * Pujan Srivastava
     * https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     */
    String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";

    Pattern p = Pattern.compile( ePattern );

    Matcher m = p.matcher( email );

    return m.matches();
  }

  private static StringBuffer m_str_buf = null;

  private static StringBuffer getStringBuffer()
  {
    if ( m_str_buf == null )
    {
      m_str_buf = new StringBuffer();
    }

    return m_str_buf;
  }

  /**
   * Ausgabe auf System.out
   * 
   * @param pString der auszugebende String
   */
  private static void wl( String pString )
  {
    getStringBuffer().append( "   * " + pString + "\n" );

    System.out.println( pString );
  }

  /**
   * Ausgabe auf System.out
   * 
   * @param pString der auszugebende String 
   * @param pThrowable die auszugebende Fehlermeldung
   */
  private static void wl( String pString, Throwable pThrowable )
  {
    wl( pString );

    wl( pThrowable.toString() );

    // pThrowable.printStackTrace( System.out );
  }

  private static String getStringLaufzeitAusMillisekunden( long pMillisekundenGesamt )
  {
    long zeit_differenz_betrag = pMillisekundenGesamt;

    long m_laufzeit_stunden = 0;
    long m_laufzeit_minuten = 0;
    long m_laufzeit_sekunden = 0;
    long m_laufzeit_milli_s = 0;

    if ( zeit_differenz_betrag > 0 )
    {
      m_laufzeit_milli_s = (long) ( zeit_differenz_betrag % 1000 );

      zeit_differenz_betrag /= 1000;

      m_laufzeit_sekunden = (long) ( zeit_differenz_betrag % 60 );

      zeit_differenz_betrag /= 60;

      m_laufzeit_minuten = (long) ( zeit_differenz_betrag % 60 );

      m_laufzeit_stunden = (long) zeit_differenz_betrag / 60;
    }

    return ( m_laufzeit_stunden < 10 ? "0" : "" ) + m_laufzeit_stunden + ":" + ( m_laufzeit_minuten < 10 ? "0" : "" ) + m_laufzeit_minuten + ":" + ( m_laufzeit_sekunden < 10 ? "0" : "" ) + m_laufzeit_sekunden + ":" + ( m_laufzeit_milli_s < 10 ? "00" : ( m_laufzeit_milli_s < 100 ? "0" : "" ) ) + m_laufzeit_milli_s;
  }

  /**
   * <pre>
   * Erstellt die Datei und schreibt dort den "pInhalt" rein.
   * 
   * Ist kein "pInhalt" null wird eine leere Datei erstellt.
   * </pre>
   * 
   * @param pDateiName der Dateiname 
   * @param pInhalt der zu schreibende Inhalt
   * @return TRUE wenn die Datei geschrieben werden konnte, sonst False
   */
  private static boolean schreibeDatei( String pDateiName, String pInhalt )
  {
    try
    {
      FileWriter output_stream = new FileWriter( pDateiName, false );

      if ( pInhalt != null )
      {
        output_stream.write( pInhalt );
      }

      output_stream.flush();

      output_stream.close();

      output_stream = null;

      return true;
    }
    catch ( Exception err_inst )
    {
      System.out.println( "Fehler: errSchreibeDatei " + err_inst.getMessage() );
    }

    return false;
  }

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

  private static boolean JavaMail_InternetAddress_checkAddress( String pEMailInput )
  {
    int akt_index = 0;
    
    if ( pEMailInput.indexOf( 34 ) < 0 )
    {
      int pos_at_zeichen;

      String local_name;
      String domain_part;

      if ( ( pos_at_zeichen = pEMailInput.indexOf( 64, akt_index ) ) >= 0 )
      {
        if ( pos_at_zeichen == akt_index )
        {
          return false; // throw new AddressException( "Missing local name", var0 );
        }

        if ( pos_at_zeichen == pEMailInput.length() - 1 )
        {
          return false; // throw new AddressException( "Missing domain", var0 );
        }

        local_name = pEMailInput.substring( akt_index, pos_at_zeichen );
        domain_part = pEMailInput.substring( pos_at_zeichen + 1 );
      }
      else
      {
        local_name = pEMailInput;
        domain_part = null;
      }

      if ( indexOfAny( pEMailInput, " \t\n\r" ) >= 0 )
      {
        return false; // throw new AddressException( "Illegal whitespace in address", var0 );
      }
      else if ( indexOfAny( local_name, "()<>,;:\\\"[]@" ) >= 0 )
      {
        return false; // throw new AddressException( "Illegal character in local name", var0 );
      }
      else if ( domain_part != null && domain_part.indexOf( 91 ) < 0 && indexOfAny( domain_part, "()<>,;:\\\"[]@" ) >= 0 )
      {
        return false; // throw new AddressException( "Illegal character in domain", var0 );
      }
    }

    return true;
  }

  private static int indexOfAny( String var0, String var1 )
  {
    return indexOfAny( var0, var1, 0 );
  }

  private static int indexOfAny( String var0, String var1, int var2 )
  {
    try
    {
      int var3 = var0.length();

      for ( int var4 = var2; var4 < var3; ++var4 )
      {
        if ( var1.indexOf( var0.charAt( var4 ) ) >= 0 )
        {
          return var4;
        }
      }

      return -1;
    }
    catch ( StringIndexOutOfBoundsException var5 )
    {
      return -1;
    }
  }

//private static EmailValidator m_email4j_v = null;
//
  private static boolean checkEmail4J( String pEMailAdresse )
  {
//  if ( m_email4j_v == null )
//  {
//    m_email4j_v = new EmailValidator();
//  }
//
//  return m_email4j_v.isValid( pEMailAdresse );

    return false;
  }
}
