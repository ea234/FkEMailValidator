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
 * ... but Programmer that you are, you will cope with that. 
 *
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
   *   192 "ABC+DEF) <ABC.DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   193 "ABC DEF <A@A>"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   194 "<A@A> ABC DEF"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   195 "ABC DEF <ABC.DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   196 "<ABC.DEF@GHI.JKL> ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   197 "ABC DEF ABC.DEF@GHI.JKL>"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   198 "<ABC.DEF@GHI.JKL ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 17 = Struktur: keine schliessende eckige Klammer gefunden.
   *   199 "ABC.DEF@GHI.JKL> ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   200 "ABC DEF <ABC.DEF@GHI.JKL"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   201 "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   202 "(ABC DEF) <ABC.DEF@GHI.JKL>"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   203 "ABC+DEF) <ABC.DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   204 "ABC DEF <>"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   205 "<> ABC DEF"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   206 "<ABC.DEF@GHI.JKL>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   207 "\"ABC<DEF>\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   208 "\"ABC<DEF@GHI.COM>\"@GHI.JKL"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   209 "ABC DEF <ABC.<DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   210 "ABC/DEF=GHI@JKL.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   211 "1234@5678.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   212 "000.000@000.de"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   213 "0\"00.000\"@wc.de"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   214 "me+100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   215 "me-100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   216 "me-100@yahoo-test.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   217 "me..2002@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   218 "me.100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   219 "me.@gmail.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   220 "me123@%*.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   221 "me123@%.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   222 "me123@.com"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   223 "me123@.com.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   224 "me@.com.my"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   225 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   226 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   227 "me@me.co.uk"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   228 "me@me@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   229 "me@yahoo.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   230 "invalid.email.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   231 "email@example.co.uk."                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   232 "email@example"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   233 " email@example.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   234 "email@example,com"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   235 "xxxx@.org.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   236 "test test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   237 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   238 "test@test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   239 "test%test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   240 "test_test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   241 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   242 "test+test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   243 "test@gmail"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   244 "test@gmail."                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   245 "test@gmail..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   246 "test@Gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   247 "test@gmail.Com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   248 "test@GMAIL.COM"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   249 "1234@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   250 "check@thiscom"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   251 "check@this..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   252 " check@this.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   253 "check@this.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   254 "test@sub-domain.000.0"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   255 "test.test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   256 "Test.Test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   257 "TEST.TEST@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   258 "test@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   259 "TEST@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   260 "test0@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   261 "0test@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   262 "test@subdomain.gmail.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   263 "test@sub-domain.gmail.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   264 "test@anothersub.sub-domain.gmail.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   265 "test@sub-domain.000.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   266 "test@sub-domain.000.0rg"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   267 "test@sub-domain.000.co-om"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   268 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   269 "xyz@blabla.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   270 "jsr@prhoselware.com9"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   271 "dasddas-@.com"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   272 "%2@gmail.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   273 "\"%2\"@gmail.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   274 "\"a..b\"@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   275 "\"a_b\"@gmail.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   276 "_@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   277 "1@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   278 "-asd@das.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   279 "as3d@dac.coas-"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   280 "dsq!a?@das.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   281 "_dasd@sd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   282 "dad@sds"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   283 "asd-@asd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   284 "dasd_-@jdas.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   285 "asd@dasd@asd.cm"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   286 "da23@das..com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   287 "_dasd_das_@9.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   288 "d23d@da9.co9"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   289 "dasd.dadas@dasd.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   290 "dda_das@das-dasd.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   291 "dasd-dasd@das.com.das"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   292 "prettyandsimple@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   293 "very.common@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   294 "{john'doe}@my.server"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   295 "hello7___@ca.com.pt"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   296 "peter_123@news24.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   297 "alirheza@test.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   298 "hallo2ww22@example....caaaao"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   299 "d@@.com"                                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   300 "hallo@example.coassjj#sswzazaaaa"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   301 "x._._y__z@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   302 "_______@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   303 "1234567890@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   304 "admin@mailserver1"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   305 "\" \"@example.org"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   306 "example@localhost"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   307 "example@xyz.solutions"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   308 "user@com"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   309 "user@localserver"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   310 "user@[IPv6:2001:db8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   311 "user@[192.168.2.1]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   312 "email@192.0.2.123"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   313 "email@example.name .name"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   314 "(comment and stuff)joe@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   315 "joe(comment and stuff)@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   316 "joe@(comment and stuff)gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   317 "joe@gmail.com(comment and stuff)"                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   318 "joe(fail me)smith@gmail.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   319 "joesmith@gma(fail me)il.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   320 "joe@gmail.com(comment and stuff"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   321 "Abc.example.com"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   322 "A@b@c@example.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   323 "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   324 "just\"not\"right@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   325 "this is\"not\allowed@example.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   326 "this\ still\\"not\\allowed@example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   327 "lots.of.iq@sagittarius.A*"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   328 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   329 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   330 " joe@gmail.com"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   331 "joe@gmail.com "                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   332 "jsmith@whizbang.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   333 ".ann..other.@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   334 "ann.other@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   335 "x.yz.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   336 "x_yz_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   337 "doysmith@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   338 "D.Oy'Smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   339 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   340 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   341 "Joe.\\Blow@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   342 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   343 "someone@somewhere.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   344 "someone@somewhere.co.uk"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   345 "someone+tag@somewhere.net"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   346 "futureTLD@somewhere.fooo"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   347 "fdsa"                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   348 "fdsa@"                                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   349 "fdsa@fdza"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   350 "fdsa@fdza."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   351 "a+b@c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   352 "a@b.c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   353 "( a @[]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   354 " a @[]"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   355 " a @[] "                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   356 " a @[ ]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   357 " a @[ ] "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   358 " a @ []"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   359 " a @a[].com "                                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   360 "DotAtStart@.atstart"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   361 "DomainHyphen@-atstart"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   362 "ExpectedATEXT@;atstart"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   363 "DomainNotAllowedCharacter@/atstart"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   364 "ConsecutiveDots@at..start"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   365 "ConsecutiveCRLF@test\r\n\r\nat"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   366 "CRLFAtEnd@test\r\nat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   367 "CRWithoutLF@test\rat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   368 "ATEXTAfterCFWS@test\r\n at"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   369 "ExpectedCTEXT@test\r\n \n"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   370 "UnclosedComment@a(comment"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   371 "DomainNotAllowedCharacter@a,start"                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   372 "ConsecutiveAT@@start"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   373 "ExpectedATEXT@at[start"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   374 "DomainHyphen@atstart-.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   375 "DomainHyphen@bb.-cc"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   376 "DomainHyphen@bb.-cc-"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   377 "DomainHyphen@bb.cc-"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   378 "DomainNotAllowedCharacter@atst\art.com"                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   379 "DomainNotAllowedCharacter@example\"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   380 "DomainNotAllowedCharacter@exa\mple"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   381 "UnclosedDomainLiteral@example]"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   382 "DomainNotAllowedCharacter@example'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   383 "Test.Domain.Part@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   384 "Test.Domain.Part@subdomain.example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   385 "Test.Domain.Part@has-hyphen.example.com"                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   386 "Test.Domain.Part@1leadingnumber.example.com"                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   387 "Test.Domain.Part@example.co"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   388 "Test.Domain.Part@subdomain.example.co"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   389 "Test.Domain.Part@has-hyphen.example.co"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   390 "Test.Domain.Part@1leadingnumber.example.co"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   391 "Lat%ss\rtart%s@test.local.part"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   392 "at\"start\"test@test.local.part"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   393 "at(start)test@test.local.part"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   394 "example(comment)@test.local.part"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   395 "withdot.@test.local.part"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   396 "Test.IPv6@[[127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   397 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   398 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]"                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   399 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]"                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   400 "Test.IPv6@[IPv6:1::1::1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   401 "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   402 "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   403 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]"                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   404 "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   405 "Test.IPv6@[\n]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   406 "abc.\"defghi\".xyz@example.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   407 "\"abcdefghixyz\"@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   408 "abc\"defghi\"xyz@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   409 "abc\\"def\\"ghi@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   410 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   411 "jsmith@[IPv6:2001:db8::1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   412 "user@localhost"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   413 "aHZs...Ym8iZXJn@YWRtAW4g.au"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   414 "\"RmF0aGlh\"@SXp6YXRp.id"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   415 "\"hor\ror\"@nes.si"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   416 "!yoora@an.yang.ha.se.yo"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   417 "$8473790.$M7686E696B@example.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   418 "bob.mcspam@ABCD.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   419 "somecahrs@xyz.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   420 "person@registry.organization"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 15 = Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
   *   421 "user@host.network"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   422 "\"-- --- .. -.\"@sh.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   423 "@@@@@@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   424 "something@domain.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   425 "emailString@email.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   426 "\"B3V3RLY H1LL$\"@example.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   427 "someStringThatMightBe@email.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   428 "#!$%&'*+-/=?^_`{}|~@example.org"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   429 "foo.bar.\"bux\".bar.com@baz.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   430 "\"much.more unusual\"@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   431 "other.email-with-dash@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   432 "Find#Me@NotesDocumentCollection.de"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   433 "\"very.unusual.@.unusual.com\"@example.com"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   434 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   435 "InvalidEmail@notreallyemailbecausenosuffix"                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   436 "disposable.style.email.with+symbol@example.com"                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   437 "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   438 "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   439 "\"\\" + \\"select * from user\\" + \\"\"@test.de"                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   440 "\"()<>[]:,;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   441 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com"              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   442 "1234567890123456789012345678901234567890123456789012345678901234+x@example.com"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   443 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   444 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL"     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   445 "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com"       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   446 "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   447 "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   448 "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   449 "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   450 "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   451 "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   452 "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   453 "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   454 "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   455 "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   456 "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   457 "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   458 "zzzz.zzzzzz@zzzzzzzzzz.zz"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   459 "AAAA.AAAAAA@AAAAAAAAAA.AA"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   460 "aaaa.aaaaaa@aaaaaaaaaa.aa"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   461 "Vorname.Nachname@web.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   462 "m.fanin@fc-wohlenegg.at"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   463 "old.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   464 "new.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   465 "test1group@test1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   466 "test2group@test2.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   467 "test1@test1.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   468 "test2@test2.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   469 "junit.testEmailChange@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   470 "at@at.at"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   471 "easy@isnt.it"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   472 "yes@it.is"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 1 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3047 = 0.000076175                = 00:00:03:047
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   6453 = 0.000161325                = 00:00:06:453
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  12517 = 0.000312925                = 00:00:12:517
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  35331 = 0.000883275                = 00:00:35:331
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  19549 = 0.000488725                = 00:00:19:549
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  90052 = 0.0022513                  = 00:01:30:052
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 2 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3033 = 0.000075825                = 00:00:03:033
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   6546 = 0.00016365                 = 00:00:06:546
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  12299 = 0.000307475                = 00:00:12:299
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  35511 = 0.000887775                = 00:00:35:511
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  19829 = 0.000495725                = 00:00:19:829
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  90113 = 0.002252825                = 00:01:30:113
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 3 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3018 = 0.00007545                 = 00:00:03:018
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   6545 = 0.000163625                = 00:00:06:545
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  12336 = 0.0003084                  = 00:00:12:336
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  35429 = 0.000885725                = 00:00:35:429
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  19668 = 0.0004917                  = 00:00:19:668
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  90830 = 0.00227075                 = 00:01:30:830
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 4 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3017 = 0.000075425                = 00:00:03:017
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   6508 = 0.0001627                  = 00:00:06:508
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  12272 = 0.0003068                  = 00:00:12:272
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  35448 = 0.0008862                  = 00:00:35:448
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  19621 = 0.000490525                = 00:00:19:621
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  90490 = 0.00226225                 = 00:01:30:490
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 5 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3014 = 0.00007535                 = 00:00:03:014
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   6501 = 0.000162525                = 00:00:06:501
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  12240 = 0.0003060                  = 00:00:12:240
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  35508 = 0.0008877                  = 00:00:35:508
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  19772 = 0.0004943                  = 00:00:19:772
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  90228 = 0.0022557                  = 00:01:30:228
   * 
   * PROCESSOR_IDENTIFIER   Intel64 Family 6 Model 58 Stepping 9, GenuineIntel
   * PROCESSOR_ARCHITECTURE AMD64
   * 
   * ################################################################################################################################
   * 
   * Test mit Intel i7-8700 CPU @ 3.20GHz unter Linux-Mint 19.1:
   * 
   * Testlauf 1 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3349 = 0.000083725                = 00:00:03:349
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   6162 = 0.00015405                 = 00:00:06:162
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  11156 = 0.0002789                  = 00:00:11:156
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  29536 = 0.0007384                  = 00:00:29:536
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS   9300 = 0.0002325                  = 00:00:09:300
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  72085 = 0.001802125                = 00:01:12:085
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 2 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   2978 = 0.00007445                 = 00:00:02:978
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   5232 = 0.0001308                  = 00:00:05:232
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  10655 = 0.000266375                = 00:00:10:655
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  28774 = 0.00071935                 = 00:00:28:774
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS   9305 = 0.000232625                = 00:00:09:305
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  72323 = 0.001808075                = 00:01:12:323
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 3 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3124 = 0.0000781                  = 00:00:03:124
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   5403 = 0.000135075                = 00:00:05:403
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  10546 = 0.00026365                 = 00:00:10:546
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  28749 = 0.000718725                = 00:00:28:749
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS   9771 = 0.000244275                = 00:00:09:771
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  72380 = 0.0018095                  = 00:01:12:380
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 4 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3153 = 0.000078825                = 00:00:03:153
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   5511 = 0.000137775                = 00:00:05:511
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  10746 = 0.00026865                 = 00:00:10:746
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  26559 = 0.000663975                = 00:00:26:559
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS   9176 = 0.0002294                  = 00:00:09:176
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  73527 = 0.001838175                = 00:01:13:527
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 5 Anzahl Testfaelle  40000000
   * 
   * ea234      anzahl_korrekt =  29480000 - anzahl_falsch =  10520000 - anzahl_fehler =         0  | MS   3208 = 0.0000802                  = 00:00:03:208
   * 
   * Jmail      anzahl_korrekt =  35480000 - anzahl_falsch =   4480000 - anzahl_fehler =     40000  | MS   5400 = 0.0001350                  = 00:00:05:400
   * 
   * JAVA 1     anzahl_korrekt =  25800000 - anzahl_falsch =  14160000 - anzahl_fehler =     40000  | MS  10792 = 0.0002698                  = 00:00:10:792
   * 
   * REGEXP 1   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS  26604 = 0.0006651                  = 00:00:26:604
   * 
   * REGEXP 2   anzahl_korrekt =  31040000 - anzahl_falsch =   8920000 - anzahl_fehler =     40000  | MS   9377 = 0.000234425                = 00:00:09:377
   * 
   * REGEXP 3   anzahl_korrekt =  26480000 - anzahl_falsch =  13480000 - anzahl_fehler =     40000  | MS  67110 = 0.00167775                 = 00:01:07:110

   * 
   */

  private static DecimalFormatSymbols otherSymbols             = null;

  private static DecimalFormat        number_format            = null;

  private static int                  durchlauf_anzahl         = 0;

  private static int                  test_daten_array_length  = 0;

  private static int                  stellen_anzahl           = 0;

  private static boolean              knz_ausgabe_test_daten   = true;

  private static int                  FAKTOR_ANZAHL_ARRAY_A    = 40000;

  private static String[]             array_test_daten_aktuell = null;

  private static String[]             array_test_daten_a       = {

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
     "Abc\\@def@example.com",
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
     "Test.Domain.Part@example.com",
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
     "abc\\\"def\\\"ghi@example.com",
     "this is not valid@email$com",
     "jsmith@[IPv6:2001:db8::1]",
     "user@localhost",
     "aHZs...Ym8iZXJn@YWRtAW4g.au",
     "\"RmF0aGlh\"@SXp6YXRp.id",     
     "\"hor\\ror\"@nes.si",         
     "!yoora@an.yang.ha.se.yo",
     "$8473790.$M7686E696B@example.com",
     "bob.mcspam@ABCD.org",
     "somecahrs@xyz.com",
     "person@registry.organization",
     "user@host.network",
     "\"-- --- .. -.\"@sh.de",
     "@@@@@@gmail.com",
     "something@domain.com",
     "emailString@email.com",
     "\"B3V3RLY H1LL$\"@example.com",
     "someStringThatMightBe@email.com",
     "#!$%&'*+-/=?^_`{}|~@example.org",
     "foo.bar.\"bux\".bar.com@baz.com",
     "\"much.more unusual\"@example.com",
     "other.email-with-dash@example.com",
     "Find#Me@NotesDocumentCollection.de",
     "\"very.unusual.@.unusual.com\"@example.com",
     "foo\\@bar@machine.subdomain.example.museum",
     "InvalidEmail@notreallyemailbecausenosuffix",
     "disposable.style.email.with+symbol@example.com",
     "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
     "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]",
     "\"\\\" + \\\"select * from user\\\" + \\\"\"@test.de",
     "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org",
     "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com",
     "1234567890123456789012345678901234567890123456789012345678901234+x@example.com",
     "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL",
     "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL",
     "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com",
     "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de",
     "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part",     
     "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de",

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

    "tbrsxtphgrx.jindpjp@nrluirskg.uv", "h-tghyfuzezf@twc.rfb", "etlnihgzmmawga@wbytss.rr", "kgpeg.pzcunyia@ebyjs-zxnkxjp.an", "f.kdevhltxs@kixypkz.tx", "yx@dthbd.rx", "u.ocqrs@jwxpkzebokpw.ep", "c-tqtskrauz@warzbwur.wc", "mzdxgmkukqkr@nm.okui.okh.semn.tq", "lqwngbey@cprmuvircnt.ek", "qieaw@weqlfisw.fq", "or@iexyspexxwcgu.xe", "exthairgblvkcke@vlsm.lg", "xyiyfpaizy.guhgthb@myqqauvxs.do", "cr@qlvkzahpyzkqc.yp", "tbrsxtp20@ivbjndva.vt", "dtlouwv25@exgwmxye.yt",
    "bk@jksymdm.xcv", "yqrmu-neuuzs@tqnd.sbo", "kilh.fcukfd@xonawyznjbbzvemnzpsttix.iz", "htghyfuzezft@wc.rfbw.ohw.njol.iw", "cxyn.lkchjy@qnmjjqhq.qwa", "erlflhoazcc@wzrxd.sg", "amfnd@zx.wd.gz", "blnqqliunsbprv@zbtpfb.yu", "guknvsqpg-tdnqpv@esosnf.qa", "vvjrvexs@tgfdq.iu", "rwtdpivu.igwklfe@wvz.shu", "gryx.otq@bhdgqynk.ga", "mmouoaurnpw@zylzx.po", "eew-dwdsvxwcb@pekqipnm.qj", "l.wepwb@hfeyjawopjuz.bg", "actyt.mfznrkewaflido@nxbftbm.tx", "mgkywnn.bmac@eq.fw", "cztpk@xyquoquz.ff",
    "l-taHCBvaloq@ungvs.ln", "upgvpfgmyj@ityosjfohu.oab", "sldihhhgrxjin@dpjpnrlui.rsk", "djayki.emcq@jytwljp.gug", "wrwse.qqvcmnw@blinyl.nnh", "q.oiwo@jlglucmf.lt", "cuuz.qxorvt@utlmfulk.zhu", "euptgvcqn-gzqaoa@zomvxa.uk", "t-fvgrgezpnr@vkj.hrw", "lvzjyolo.celgcbl@leg.xbn", "ldyipo.nnoj@fhxekfv.vmc", "kflyiccl@stzogrxatzw.se", "w.oxkhqyvpc@pwtcjps.ya", "nccfd.mcuxbiruwoiavb@tejsech.gd", "yxdthbdrxe.iant@piktulh.xe", "hqcud.sfiqujz@ufnjfp.ytq", "xggxw-ochkdav@cjsas.am", "ma@hceahlm.li",
    "tbrsx-tpjbivb@jndva.vt", "sukrthcmfrzxxfw@aonm.wc", "v.eqah@eszhawra.lc",  "wxet.uqt@bdwfkdfq.fq", "zbhgqadyfq@hkyesiaulu.bqo", "nwzjmzwewmoxmqkoybx@wby.zxi", "opncylwhthxpp@jyyowmogh.avg", "rewhe-vtjvgo@ebvi.odg", "opnc.ylwhth@xppjyyowmoghavgecvxvsjd.qw", "vynep.rmjhlcxs@fycyj-bakeeoo.ym", "m-jomracra@fzyrc.az", "b.lnqqliu@nsbprvzbtpf.by", "zhkchnjoffbfgkwcdop@reb.haf", "jtfhlqfpyh.ijgq@qivrock.mp", "v.yneprmj@hlcxsfycyjb.ak", "mrvjjnxtv-rfwqzf@ebbgmf.vq",
    "ouuru.yajbjgnh@xefbk-tqqlkoj.ti", "qffg.finmfm@abeqozdsqwkpkszyjagmwks.ph", "auztglanky.fglf@iebjdqn.rg", "ijym.rraoww@fpbuiewx.tum", "jjxlfckeqeif@li.hhqs.spa.vtkd.ce", "e-faetvmiwxb@ibh.kad", "urgrsryirlwdv@spvfducjh.qtv", "d.migjv@ppyhtwdbrsvg.oy", "pbbsxkng@xabrtflanra.nq", "q-uplkwoitw@lkxyzkem.pf", "e.qpzphlm@iorzjzzqcah.nb", "nvzxslwaddegbk@idrcwv.zn", "kltoepgnbuimyf@chfmct.ol", "lelmoodgzkpyvlv@pmav.rc", "dgekrijzyrzb@vu.oytm.kbv.lebg.ef", "omrnf.lbtojos@sibntc.bjq",
    "ifuywelyis@jvaxeiarcg.gdj", "tysy.gfyfvq@nkrybmoifjdxqvxrmgypugg.bn", "w.obdlpvhxo@dzdklcc.sd", "znssdanqohkc@yy.bjpb.wtn.frti.qh", "paxnz.wrpvfdxzshcwyx@ihvibcp.pz", "dpmtdiefmmqpfxv@psmf.me", "veuul.eplpmkvksejkxi@cuhphrn.su", "gmdaytdo.quystcm@xyi.kod", "tgvcusw62@pffhneew.pw", "lwewefe.tevfoqb@ygbjfznainn.ww", "hmczm.eoekbbjdqmasdk@jrrsvjv.di", "ezxewr.ibhgswihnsbg@gaac.yj", "qlmhvjpthw.tphj@wswfyzv.dt", "bnrpyizwws.rguevox@toliuamyy.ox", "hvkhg.blbnawky@lntfz-xrgnsfy.xu",
    "kcmczlnx@aewlauptvja.fl", "cacmtgtjszheo@sfdmuzgks.icc", "xpxes.zsgwihtbyaegjg@jlrtsdw.rp", "pebzc-ghvxpdf@mgfah.bg", "awzzliudk-nhudhc@ewtjwq.zy", "xnxw.vzahky@jtupagawsaozfzpjbvfjozd.vx", "lbhx.byqgzk@yrghlvfqxzcthzansxzzvjg.eg", "tdrnh.ckuipny@rjvkog.yzd", "vhzgnogsrw@kcwhchvasn.gsx", "zpsyiw.hgov@mqrzvjj.lcz", "cgfi.luazso@byxyjyoo.ddn", "wzitcmkdmxwbfomtvce@adz.gmt", "zdlgywuanr@ybanovfyte.lqx", "hjdpx.byvmviv@hzgfbm.pxa", "gmdyg.qffeycgf@ugznf-dljjpez.nt", "m-jtdcxbuwvr@pmc.woi",
    "uwklsbhogfvk@pq.nsvt.rws.jmpf.dp", "nvdaxvvhsc.hnhpgew@ssxoguwhh.dt", "siem.yqrszj@gfhghdrx.wxb", "ilyswouexl@hcuvbeqhzb.pff", "dpmslfgvaqudqpprgjx@jkh.emv", "unccf-bmhrle@wfnf.bxt", "n-zhosjjxgh@dhimkaty.jl", "zktio.xxhpbqpdgwnykb@hwegpjq.cl", "jvjhunwrghzaia@mwzuxm.um", "gmdygqf11@ycgfugzn.fd", "gcsjwcoipkoybevlymf@usb.bki", "uwkls-bhogfv@kpqn.svt", "djjwtszgabajij@mxeptm.hw", "jyjp.rmorts@xbpjmufikvrdmcmfglcxzct.ek", "rvtpd-rxfdcqh@mswnd.wl", "jrbnbmlkr-ienmqe@bmwdtf.pd",
    "utgxxotxsazqn@tjwznaimp.izz", "nzhos-jjxghd@himk.aty", "cdgaouizgedyqp@jaiaqu.qm", "tyswncav@jurxdqvocfx.gj", "bprxvi.rwid@pgboumd.gbu", "i.fvwea@nowwnimpykon.vd", "qniic.snsfqxjgxluawa@apjufxz.wj", "mflkkwqnhw.xbrsouv@snjwlrtgw.ir", "koxagzw61@ditltafq.pm", "sqhqmdqi.miyawvk@vzf.neb", "gwefesd.wssccei@qpsxmlvpnle.cy", "rbxkueev.drkbvya@hfq.pab", "r-xtxarpfpmo@htc.wlj", "nzho.sjjxgh@dhimkaty.jlt", "lsanpx.walp@hdwedeg.qco", "cccvq-gljeje@ewcf.bsh", "kipyrytn@btqfiasnxde.co",
    "axdfjv.rtlh@gnyugdm.lmc", "mipyejedvbtv@sp.sqry.exc.fwjl.jd", "vbrey.ndlpmso@uoupcn.kku", "ifqqgnrxvbporcumyur@ydr.whx", "yaitq-yuugezpmslfgvaq.udqp@prgjxjk.he", "cgfiluazsobyx@yjyooddnd.bkw", "hocgr-biulqdw@vsfan.nq", "hsgtloxlzv@aqxpjxjtgd.ato", "aahs.djfkzm@chzrkzjsdqfwtvtbsfvldqq.wo", "kguwslyeqdqyvex@niiu.ya", "lyipeyyh.mzarzyl@xzd.jqf", "xgpufyxaiorfsvt@ohjl.xc", "qijsisdt.hvbirem@yov.ziv", "fplrzq.qmux@xtaeoua.dxe", "azdn.gvj@tyrdofeg.as",
    "o.lngpuhtbt@umjnrrr.gr", "znsqlwpgc-lopkqv@ldyoqt.vu", "l.wighygh@apcrsolztpw.hi", "edbu.eswzyr@hvfqeelc.mkv", "m-clcnwynum@zbkjowpe.aa", "ejeqvfdpyg.cpowhyo@wqhytvhkf.hx", "awzzli.udkn@hudhcew.tjw", "ynwvj-klxdfmj@edaju.rs", "sohipdyizy.azpmkxu@ravehakxu.sb", "lyejh.kcqlfbxe@khyik-glwzckv.zx", "nrvhfvkadd.mamg@yjjtyez.jz", "lmxrx.kpkkanjo@yakex-yqrcrvi.xb", "ezxgj-eijkml@beta.ipw", "zhputji50@wuvcksrb.ek", "negfmjtwfczixfk@wezk.dn", "yzdnskydekayvp@hxyeaq.iv", "aahsd-jfkzmc@sdfhzrk.zjs",
    "ecxpgeaiwwjbl@dagvkiemf.mcz", "i.gzccokfyq@lcgccif.gy", "y.eddmk@icdfwyjigtkf.bi", "fugcvdeuryuau@kkrvmurjn.weg", "c.nehctcy@dtuzsbirfcr.ee", "c-kathgohpp@ygreevjw.bq", "a.bypfiecjh@dvsabah.uk", "lhlttlxwzzsmonophkb@xff.zhu", "knoplyem@aomgwtsjjef.ui", "gtcwxqhdqd.whaw@ftetrma.lf", "zlhtctuab-pjeybu@xffwku.lm", "ylhto.ijkhhgoo@mvulk-wpgmgsr.it", "gmrj.undyqn@wvaoekuzyphqvmgzffkvect.ul", "evzttcnokz.jtad@nxzetfb.ym", "xuti.ywahxw@vwscgjac.ugx", "wnmhvknkbfzs@gu.znnv.ojy.iedw.as",
    "t-rvbaanbxxr@psq.ifh", "oyijmsrsnmbqv@twpcouzqa.hdv", "s.clwib@bpogdqqtyrmk.ke", "voqntyjb@dzylaiemeor.og", "j-fawwxrafv@kktjoyow.jw", "ezdhopbfyefnbz@rtvkku.hs", "ivunwlmewurgzs@hfwbpq.vs", "jlesokyqflefbqr@sqqn.uu", "gqvxparpersp@bl.igrf.odn.kzbm.na", "msktf.hwdujcz@xmxqxs.umi", "kplluvunor@bigoypxkhi.xce", "ahweryz.jtjtlq@prdrmdsgcrforkvoqxlpyby.qr", "werwi.wysuyivyo@pztfldn.us", "gsqzomnxphrw@oh.egga.zhb.axwj.am", "ipqrf.yfowexmrmdluhl@tsgpjvh.rr",
    "xwolyjwqimudfyc@ipxf.km", "lmhxa.ifxanfofrutbdq@uriabyo.oz", "ewezrpjf.muhmuqr@vqx.wlk", "fprsdbk22@pqfxjffg.rm", "s.ydcqaqi@zgjdujqxzlq.kk", "cpxyo.ewiqcmiiikadpp@nkonsmx.ix", "hffchzdbmifodit@tlun.hm", "ymdwnvftsx.gvbj@quaegtu.cd", "iwysuyivyo.pztfldn@usveuynfj.gv", "whegg.kkezvhed@bsvvi-ggmuvysdfm.zd", "ffgct.sdfdsosl@tbcxtutqrzn.my", "rlwltpsmeusyu@ikgbdiuqz.lvq", "qfpkq.dfpjdxuhbdlpvb@xqmwmfc.ip", "jefwsdfsdleso-kyqflef@bqrsq.qn", "zdrhdhnwc-jsnxte@glmicd.uq",
    "qyii.adcjht@egfhvswjtufynhvatlcgbto.zx", "zrkp.thaqbe@dzhhxrhyaixweeldadqowmw.fi", "vvyoj.ymifjqz@dblexa.yrs", "tszgynjszr@pjmdmdxsln.hjk", "bzinyr.nfgr@aqmywos.ioe", "wnic.xxquak@bymhvpxc.kep", "ukjunmoeusaivjwoxux@aeq.tjr", "lmhxaifxan@fofrutbdqu.ria", "yjzis.iqdeskb@ammyey.qfo", "zxoll.uhgctxtp@nbmjs-efaixme.ek", "d-ipwydtcott@ufp.chk", "wkiubxyunady@fy.vrrp.xvs.oanr.nx", "mynpxudtvz.wtfsbkz@myklervml.gj", "ancv.bgpjmf@jllxpxuz.goi", "sclwibbpog@dqqtyrmkke.zrx",
    "txyxsnurxmljawmzjtm@fbs.msm", "ytkxd-viauhy@jecy.mtr", "z-dvmbukned@qhsgjece.mi", "dmtkt.qhscxqoglgehjz@zfuicgj.ov", "vaxfdywheemaru@laibzj.mi", "zxolluh20@txtpnbmj.se", "vrubolyrreufbehiaui@cmf.zpt", "wkiub-xyunad@yfyv.rrp", "hljyymkrnyzilo@wnnnre.pm", "uhfg.szzogn@eounssbnhlywptmehdfmtsx.oj", "rxddc-pfsfzgn@luruf.qq", "chttzrxtd-duossh@iviyhl.kg", "jfawwxrafvkkt@joyowjwsw.ksn", "zdvmb-uknedq@hsgj.ece", "wgaaiwnnybjkjo@nxeqdb.ds", "wijlexgu@bpfyyowtlbj.lv", "hdgukz.lbay@hnwztvf.gzy",
    "g.qvxpa@rperspbligrf.od", "gvunj.abodmoqpeiccgp@vhubmon.gv", "kplluvunor.bigoypx@khixceqec.jb", "qbmwvqq76@yaagezos.pk", "lfzwkicq.ydnbcynw.efyjkse@nljewssxpvt.xp", "cjtcwqps.qnsozcg@gbv.lqi", "q-elesqiyhjy@ano.ymy", "zdvm.bukned@qhsgjece.mil", "bamswf.jwil@ykglamj.brj", "qsemi-pvtgek@mwbr.yup", "rrwbnpcl@dpozibjcnej.mk", "mbrcrh.rjje@tnhofhv.spy", "xutiywahxwvw@sc.gjac.ugx.vtmh.xd", "uhjmq.mwehich@oaxqsg.krh", "trvbaanbxxrpsqifhzi@hyg.tkt", "ufke.tmsdfsefvy@ehhqxsmt.wik",
    "angbz-tmanzhf@fkuvl.sj", "txyxsnurxm.ljaw@mzjtmfb.sm", "wnicxxquakbym@hvpxckepg.ogf", "xwoly-jwqimudobnvpcizpv@cxiiuonrdl.clo", "dgqm.jadsqn@ahkyxmtmnyiztjcwprxjqft.zr", "jieizhihgfkrgyl@wzzs.id", "ejtzrycz.yzzsvja@ljn.dic", "wniafuskopfmxap@rmzd.bv", "mhvwrrap.xxrvbmc@dmi.lsa", "damqrn.wcpx@gnbstsr.sjb", "qhpq.uaz@fjsziacw.lj", "z.tkvydugct@gmzjrsb.ig", "gsrywjpod-mwjaay@ivwreh.ra", "f.dlycyzs@wphespstrbw.fq", "ukoy.swmljr@dobpupdj.tcs", "p-iuwsnvwlm@xbupbdfddjay.ki",
    "fwdwmxwluf.oqpwkay@oodepmumc.bd", "dchtqz.slan@fuooorg.ntf", "tqrtk-jtbjgxj@jvyjf.dx", "qyigizezvy.jtqapul@gmrlpqvko.ed", "rlted.yylreysl@nzkzh-xmnnymp.la", "yeaqgeiutc.soxl@hehuhka.pa", "unngp.wekvcbpi@yuniw-hlqbbmm.ti", "hfgao-wfranj@bpan.vaq", "cqfhrbr12@wmjiamyz.xp", "cqbbtoupgboponi@aiel.pz", "rowrfgkrhystgxxmmcfjumnk@dgwnfgkrhystgxoa.th", "dgqmj-adsqna@hkyx.mtm", "hmncewiycwcpr@tuosdngef.hdf", "x.omfrsarjr@hvbbttw.mg", "q.bmudn@spjcfnswtrhm.dg", "pobxioycnwppx@jibslslol.nul",
    "z.gvsfred@essukicewps.oo", "m-bnvbqxjvn@zhytultw.eq", "c.pxwvayyeh@qwtaecs.mi", "jrmslhdnvzbgpbtmyzn@tmn.qsi", "udbrfinn@gnmheijytej.uy", "cqbbtoupgb.opon@iaielpz.hh", "vuuhtishr-twtugb@iveoip.nr", "shkkk.ynebfee@qerpmi.dtz", "x.dwlvgbnya@retkedx.gf", "liei.nst@xamnipnl.zt", "nxbtino,jhhdi52@jcnxxrox.qn", "o.vjzxobjpg@idacqul.xz", "d.amqrnwc@pxgnbstsrsj.bf", "mvofhrnkwtdgkavpivd@yud.grn", "gtzjr-kivgbsw@oagec.iy", "oeqikpw41@ayrjeykk.xu", "fgkrhystgxy-kdmyspktr@migrkyjo.mh", "tuvhfxhrxl@tdkrcfbjon.bpl",
    "jcbmhtbrgp.qxxsicx@fokbvaops.zb", "hmncewiycw.cprt@uosdnge.fh", "webqt.tyezupxonkbw@axphapydumyq.ap", "fflg.zyr@siafezzs.rw", "f.gpmwl@nikudytmwpnh.nt", "zuobwrrx.dneguwv@qsb.jtj", "xrqtlgohx-vdpdyw@pstwpy.rs", "g-yzzlksufv@oxctganj.zg", "amyolpvjyppf@cc.fbsd.vgf.pwjn.ci", "vlnxghxb@dzgflduswet.xh", "d.tepbm@twoonzzylzyg.cz", "dbtjvgtbb-uirhbw@fkwgeq.fp", "muvqo.krjvfhk@jbzevl.msi", "d.smaifyvna@sdxzppm.wl", "bvvtn.vymisja@juygfa.frh", "bsrg.shj@wunngixu.kj",
    "pjapwgs66@jzfazewt.ch", "g.xgksdwuei@sbbtjpb.zi", "y.fhjkxft@fzusdvkraye.nl", "ucnhjpoegupfxwqmxnv@tuv.fwy", "kxaev-mvjqdao@ajdxy.xd", "njbpivh10@cwpqlftz.hh", "b-rmauunftt@pnokqmsb.ly", "fesrfrvrhm@jiyzvgketi.bvj", "ilumyaxnvr.jjaixga@owvgdopqh.gl", "xrsgrlophy.haou@veveqnz.hq", "jkqha.nbwuwlpg@vtijv-sqxbyrz.hb", "f.bodke@hbtinoxdrkoy.cu", "ekvn.xec@hicdcgfz.am", "r.necef@rbewyqlkhvjd.xu", "nsnsjyb.sqlxubq@ioa.ghq", "oxuxyvuzc-xibazy@fvuzwt.tb", "s-fnqtdvnzx@kpurrhjf.jh",
    "vpxeeniq@cbedskbbmog.ox", "r.sdtyf@badruqzdgrve.yn", "nsnfsjyb.sqlxubq@ioa.ghq", "oxuxyvuzc-xibazy@fvuzwt.tb", "s-fnqtdvnzx@kpurrhjf.jh", "vvbawbogdqyx@aq.evoi.kla.ffvx.cg", "vpxeeniq@cbedskbbmog.ox", "b.pavrxzu@sndjcfszlumhrxvkfbqpaqw.bj", "xngtro.ieuix@vwgytijdnblmf-hyntzmnhb.lh", "micb-ymisgg@frottbq.es", "c.foghjhxczo@byqglw.boq", "wxwozpws@lrkxu.jo", "tbfiefwj.hjbuge@wxhzzict.fe", "fvylmte-zexfqkbpbkl@mpnd.jeh", "e.akkdh@lxvrxxaycuo.dj", "olwdlkor.ldipkeo@ezucoah.xl",
    "qkrybh.cqdqm@lafjjcwy.ef", "fsux-rgqjqs@iw.jf", "htxcd-exqmu@kgngpgysybfk.cq", "sryzetqnzii@pmnqeoefbf.pbc", "dq@zvfijkmvgz.mur", "dtdjavyaaactnqvy@bvdqlpge.kj", "edoxxuanj@wtrb.uf", "fpupvgyj.eilw@bv.mhir.irl.rrwo.qd", "xhdxzbbp-ttdbnau@zgqzqyztyoxc.xx", "hppjkdmj.wvpsr@whmluehqng.exl", "l.vnzfar@oifrazseo.kle", "nhtqd.lpkdri@eztlfitmfgpmrnykjjdyuhr.km", "gdjsns.onbjyoqw@aunlwswspkjod.qe", "fn@vhyg.gkr", "onwmijgqxofpr@nnceip.xx", "wavwwposxci@xbsne.tis", "dtdjavya@aactnqv.yb",
    "khy.wnnblleeiks@qafr.qpb", "hwxlaeppyf.iguq@oeegmkcbqi.hmk", "phoksjdjvenbpugk@mviull.cz", "yrnm.xadcdp@uzbefr.pd", "ioldmqpsv@brzxjzqeu.qp", "l.ymhczjo@vpp01.cel", "xd@zk.en", "xbzchnv@zte.jhf", "xqkhmbwvintpx@dc.pe", "f.pupvgyj@eilwbvmhirir.lr", "thie.wscz@hzvooka.rjs", "cuhoxiurbuamhd@zwso.gv", "qxcvi.vmmfp@ztlfuv.xd", "djrtphg13@lnljbjkm.dez", "hwxl.aeppyfi@guqoeegm.kc", "uuxgo.etcfajgdl@pfomsfq.fwz", "orefak.rxnnac@nxwwrhjrmvgqd.iq", "wt.sovg@ollvspj.yzf",
    "kczgtnrlmj@ihwzqeuqp.xhj", "wusaechcxnodtm@kkrpovvb.lhj", "u-rtturemrv@nmcoljsgbrkl.rk", "fpupv17@jeilwbvmhiri.rl", "imlvqqxsiqtyqadq@pnntktc.or", "m.ixway@lbfl.hyt", "yduega52@euiosqmftrfwj.dy", "lvnzf-aroifrazs@eoklep.mtvyz.bt", "so@uljgcwl.dmv", "calk46@bhcjuhqjcrui.kt", "dfdvdsetk.msqfrpv@dripheo.fzu", "g.lmwcfnc@ojqigle.mv", "vaacuck.jzwgrvf@rc.kb", "ecfmcthgghxem@uxtdxu.rz", "ilhqsd-bchwvfvnzr@yvkobrlraxwr.ty", "cfoghjhxczob@yqglwboqapiq.om", "klcki04@bzjaclouwc.xgd",
    "vee-qpqyznbckx@cvyghmm.njz", "ydueg.aqieu@iosqmftrf.wjd", "nc@uajlf.lew", "fjrtd.srtdtrbt@pingye.uig", "nymgql.uepxs@qqqdp.kt", "dkwznvdu-lgmfeonid@vi.ec", "htxcdexqmuk@gngpgys.yb", "bhxrc-kaef@olonq.oh", "le@uisamuxzhoivm.ej", "kogycoer@now.wml", "jpoj74@xzqd.sj", "tbfieasdfasf.fwjhj@bugefgdwer4wx.hz", "kfyoq-njly@t.1.h.di", "uxxplelcrkhgkuo@duafon.xk", "ktbpic.yfqrwv@cnsip.ma", "alodw.zolnpj@ezrgwjivmrjgrqzgldamxto.dj", "dxuuhwwxbjdzj@glqbmc.wz", "p.jjtbxg@ymxpaynbc.xrx",
    "bxzary.iyjwzcue@qlyjggt.hoi", "laiqlomc.mirzpvz@dbuhaij.jg", "wcrfedqgov@jwkliweei.vxl", "uwokzfzauym@msrfzipecg.bft", "g.qhnhth@qsmq.iw", "sc@xmburrcbcj.lvo", "c.ikp@pxkltsqahi.bdh", "j.drjqdm@thaiwbqivuxbz.jh", "ktbpi.cyfqrwvc@nsipma.djx", "frlsfhe-hthobqbcbcu@uobn.bqs", "ndldtmlit@vqja.gc", "enifx21@aluprfqzbmte.fh", "inczardqxpxefg@ujjq.zq", "y.cmzubeg@giotand.bi", "la@mvjbi.tn", "rfbzwighqfydmssk@endyklni.vu", "xpbczckt.kczv@fz.tltf.tpa.jmwd.to", "gndzn.gtaehuovr@ugpvavm.rgd",
    "q.tuizwil@wth.ym", "yhlqo-boff@dkuof.cw", "e.ebvkik@uxazdwnslo.tju", "mktejnopvfixdz@kvkhemop.dfa", "bxuuul-lhibajzrmn@hrdbkfyldwyw.ve", "zuashnfi.deymdgr@kybtci.ru", "btrh77@xruxepyvirlk.mn", "q.qruejtv@iokekvixjyeu.kv", "xiub-kchmis@ghcglta.sl", "tizh.gtix@wxzudqr.ldv", "qtuizwil@wthym.rl", "nxdcemicrl.ywxn@ukziphrnlw.eql", "u.vkebscjse@osyebbs.wb", "t.mdvbg@xnkcvoem.vh", "gkdsz.lstejqdyutv@qktp.jhg", "nahpyzxs.fqdupzj@ygvogvwh.pl",
    "itgusek.gyerynm@xd.lk", "xxfg-opjixb@rp.uo", "z.uashn@fideymdgrky.bt", "pi@fndkjhk.drh", "zyegc.atyrj@ufedvg.vh", "uayicj.kopoa@tkcpm.xs", "nfggszhrel@qqncyc.gph", "hascfsjdbihgksj@uvpovy.re", "lueutb.fmltxf@gpwjzaddgxrzq.iz", "cecwx.xzedt@wnmztiuyk.zkr", "fxpoxukxtwiwzhgv@eodsdfdymezt.xm", "rsmxdvpd-teklxrdxy@to.yy", "wvjepdnzmlr@ihsae.mra", "xrxfy-pgcvsybsv@oliadt.ozjra.fa", "cewrffddk@jxmxclfcobos.aa", "pifndk.jhkdr@heawdgyu.rc", "gt.lachwgg@rmcxkcz.iih",
    "d-omkvwbrnp@nlzdeadmhkqx.ht", "yemhrbwft@tmthwdz.nt", "yhlqoboffdkuofcw@thfcxr.wx", "z-pwwpaysc@peruaoqwhpfkqkcchoroox.oj", "tgaz.jtahpyz@xsfq.up", "kocyocog-rwauout@mdlzlpnsnwmt.bk", "lchy.ioecyt@pawezb.hh", "ckjxmxclfcobo@saan.krb", "btrhzyxruxep@yvirlkmnfltw.yi", "ufwtmfdfsfseeugiscac@tnphpz.op", "zs@akkn.njq", "tcwlpfch@wif.zv", "zltjunkc@pkhxtek.ua", "y.veyfbbz@eyw17.tvi", "voyxoe13@paxlbxpkirqpd.jp", "clod.kfgkrhystgxkyb@hxm.ujf",
    "mwecqaxmyevfoyvh14@qmdpfrevq.jj", "h.ukaqsgx@zzps.hzb", "j.gqsnd@esul.gxi", "ngll.qmdigfo@khpcbxik.tz", "gkerqg.barxwn@oivik.in", "bt@rhzyx.rux", "qc@xmnjgaity.tb", "pmjby@xyxyinb.fxa", "u.wokzfzauym@msrfzi.pec", "ubnat12@zttimgkfvu.qfu", "k.tfugpvw@rmupxawgffdirzkuqnexmjj.fd", "sztyghd@aow.fpj", "hgvxxfq60@ybasynox.jts", "buvmxlti.vrcis@impcdpsnnk.tpi", "stpcou.wkogl@vbtgyjhryvllw-rratnqupt.db", "pgg-fgkrhystgx@rxfakhi.fpx"
  };

  public static void main( String[] args )
  {
    try
    {
      otherSymbols = new DecimalFormatSymbols( Locale.getDefault() );

      otherSymbols.setDecimalSeparator( '.' );
      otherSymbols.setGroupingSeparator( '.' );

      number_format = new DecimalFormat( "###.##", otherSymbols );

      number_format.setMaximumFractionDigits( 30 );
      number_format.setMinimumFractionDigits( 7 );

      startTest( "1" );
      startTest( "2" );
      startTest( "3" );
      startTest( "4" );
      startTest( "5" );

      /*
       * https://coderanch.com/t/567735/java/hardware-details-processor-type-java
       */
      wl( "" );
      wl( "PROCESSOR_IDENTIFIER   " + System.getenv( "PROCESSOR_IDENTIFIER" ) );
      wl( "PROCESSOR_ARCHITECTURE " + System.getenv( "PROCESSOR_ARCHITECTURE" ) );
      wl( "PROCESSOR_ARCHITEW6432 " + System.getenv( "PROCESSOR_ARCHITEW6432" ) );
      wl( "NUMBER_OF_PROCESSORS   " + System.getenv( "NUMBER_OF_PROCESSORS" ) );

      wl( FkString.getSystemInfo() );

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
      array_test_daten_aktuell = array_test_daten_a;

      test_daten_array_length = array_test_daten_aktuell.length;

      durchlauf_anzahl = test_daten_array_length * FAKTOR_ANZAHL_ARRAY_A;

      // durchlauf_anzahl = 3000000;

      stellen_anzahl = 9;

      int test_daten_akt_index = 0;

      String test_daten_akt_string = null;

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
      String test_daten_akt_string = null;

      boolean knz_is_valid = false;

      int anzahl_korrekt = 0;
      int anzahl_falsch = 0;
      int anzahl_fehler = 0;

      int stellen_ms = 6;

      int durchlauf_zaehler = 0;

      int test_daten_akt_index = 0;

      long millisekunden_start = System.currentTimeMillis();

      while ( durchlauf_zaehler < durchlauf_anzahl )
      {
        test_daten_akt_index = 0;

        while ( ( test_daten_akt_index < test_daten_array_length ) && ( durchlauf_zaehler < durchlauf_anzahl ) )
        {
          test_daten_akt_string = array_test_daten_aktuell[ test_daten_akt_index ];

          try
          {
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
            anzahl_fehler++;
          }
          
          test_daten_akt_index++;

          durchlauf_zaehler++;
        }
      }

      long millisekunden_ende = System.currentTimeMillis();

      long millisekunden_zeit_differenz = millisekunden_ende - millisekunden_start;

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
     */
    Pattern p = Pattern.compile( m_static_laxString );

    Matcher m = p.matcher( email );

    return m.matches();
  }

  private static boolean isValidEmailAddresseFkt1a( String enteredEmail )
  {
    /*
     * matt.writes.code
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

  private static boolean checkEmail( String s )
  {
    int len_eingabe = s.length();
    int akt_position = 0;
    String teil_str;

    if ( ( s.indexOf( "@" ) == -1 ) || ( s.indexOf( "." ) == -1 ) || ( s.indexOf( " " ) != -1 ) || ( s.endsWith( "." ) ) || ( s.startsWith( "." ) ) )
    {
      return false;
    }

    if ( s.indexOf( ".." ) > 0 )
    {
      return false;
    }

    akt_position = s.indexOf( "@" );

    teil_str = s.substring( 0, akt_position );

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

    akt_position = s.indexOf( "@" );

    teil_str = s.substring( akt_position + 1 );

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
