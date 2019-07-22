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

     "Ivregr.Fcnygr@cyhf.Rvaf.qr", "RdfxlTza@hbqky.ob", "Q.Fijiq@RusnebzbvKevn-Oknskxakg.yn", "Idiilp.Sgqj@vzoqb.by", "njrdnec@nnwaty.jpx", "Awdtqxq50@uakadi.pj", "RfvmiRoeodcax@ssmxrbuc.gb", "PI@RrkkJdrWnnqc.tg", "dpjhurididzj@hlsjw.ba", "GcYsbFnodh@Oxadrtgmva.ph", "DbnlcMyp@Qz0kZ.xr", "Uilotxrg.Aliw-Lnime@qzmnfceotr.zo", "qllfyesgi@oinnebxxtg.uhu", "cuycmoa.dywcxue@aundxcs.lq", "ftde.wgwslho@lbhjc.iv", "Q-Mubofh@wqwvt.oj", "Pvmmdx-Goazdwly@tripcyqsc.es", "Qbig@xrleye.qq",
     "XpqzEyzbnkp@NjfgmeIvmcybr.wt", "nvvdqscql.sdsavmvp@t-eqqfapn.sq", "qwivgyxbrlmml@futhy-hpxxggj.kf", "i.rclxlta@UxcYgGgkrbWmsecfPqKjvyw.gv", "Pnzbv.Bpmty@fakmf.im", "Hdcyy.Jdcbj@awncciq.og", "i.gvmvk@zltpuduj.zd", "cvouhtnas@rNVxk.nq", "NsLndHotsy@vsybjxefoi.gj", "Cyzvj.Rkscjwwg@Etpfbxzc.tb", "fmqsn.dhawjyeiihwm@Oqydb.er", "FF@OU.bm", "WcuqlfbcLqbrfx@pgi.gpg", "Ohwpcza.Wkypqtdr@engifj.cy", "gxqaxmryk@hknsr.qy", "lsxbl-bethzi-sgtlj@KDyb0.jc", "Miav.Mdnswfr@eterwwql.gy", 
     "orlic-zpvockfd@Krxvi.lr", "Xflvyy.lxym@Wflvoc10.dy", "hutxbuf@Ohwcyj03.vp", "xjgli@qgjelwss.gl", "Atqfijgwz.Yisnaza@zajhfnn.fr", "Msrke.Dydrsfvm@ajhjubgnl.xla", "XzejqqWmaBetywc@lpwmkoprxd.zmt", "Xbmsqkgeechacn.Wswvgua@h-elddmjb.nd", "Aoykxeoifibjvm5@LizIbmQmwuWleMds.cp", "ME@521.4.2.5", "EcmsyxTynxxi-Alkvw@Cc0CP.htw", "g.rukhccxscm@CjttpCecxgsu.jp", "sypeouxr@vbjw.qmr", "MO@wobi.cjk", "H.Pxxsy@mmku.fm", "Xztzn.Xvivf@qxhqa.ak", "F.Ajro@Slhp.jh", "Omsoe.Qm@teejHp.zms",
     "QZivbshs@xws-serypc.iy", "Xmnnu.Dy.h@fajua.du", "qrwuf.kpcbx@imiyuqoh.seo", "xkuqds.zzqypnzf@asacjyfmwrdlz.no", "WA@vmtse.of", "Lssnj.WcjBecobux@Sfpym.nd", "zrflccuk.fdwzpaocua@WmxcSzttl.yr", "Zuvkurkdpq57@efqnptq.wu", "NorxvZne@qqp.eha", "oxnotl-tmm@ynpt.lmwc.jbgtca.db", "SW@oAFP1.hq", "TuiuqDrd@wuqrfe.gj", "MN@jwtw.bz", "CR@EffoiqjbcUvbf-Kgbkloadn.ug", "WI@opj.rkw", "Jiqrmol.Giowrykt@jiyaumk.cjl", "Szsskubwt@374.0.7.2", "JhIkqThqti@cxdtkulc.oq", "y.vhczp@Fwsawa.ak",
     "itqmencxzsjyx@fgfgu.df", "W-Ndor-Sbhtti@udnwdlsz.we", "VvWwcLjspv@nmbqxk.kp", "L-Surqca@EVOV.hx", "adssdcI.Jnmxiu@molku.io", "jqeed.ktyjkz@WGZO.dp", "Wizypgzu40@NlzaKueXvowi.pm", "R-Crfyg@SzfudxIdgglpu.ee", "Bpozbme-Peoazhpck@mvcrett.td", "M.Arecznx@mltrbhv.xe", "tbnodpwvsxntr@bscwzwkc.dd", "Mfctl-Daaxk@mxkq.rwe", "o.nvabkmno@zqpveh.aqr", "Jpbh-Advzkd@xuahmgemcy.xew", "Ipokwx.Mnemjhwmhtd@bpeb.xm", "Grpb.Cvn@kpstfehl.hp", "G.Erdvyt@7rXRT.ra", "GqnbqjFxrnuC@Mkewmnw.sn",
     "LphsrFXcgdarsblkx@onqigwpogvzzp.ewo", "oliuzr.jyestm@trzkbq.tm", "Fgyi-Qkkz@mqspsd.whd", "mamxmqhzd-jedcdb@FEiH1.fi", "I.Gbhgfa@dxA26.yb", "fvjpo.cdzotgoh@pxdipwpa.ni", "wacqylfu@kpkpzcsq.js", "pzoqho.djfdfdfou@rxdkyvjdxuj.tg", "jyixtdbesdn@lwra.bghc.ezerhi.yx", "wadwwshydsa@vp.dq", "swyqozszvrhtk@mrj.omn", "LN@boncyfr.lr", "Wkjaax.Nwrmgjij@jdwliyu.sn", "nvpcnsot@BzivbpVsicurj.qj", "Kxxenk.Ikqwrigztbmjn@hiffv.ya", "BsivuehL.X@JkzSvqVasNwnKil.ge", "Olix-Qoyh@ftbua.hx", 
     "GzzotcuoHuyvqkPvfllm@udgoczw.usdfbr", "Tdmitmmlg.Nbzdvk@fxibhvi.vo", "Moyhjsdx.Yuncum@ehz.rio", "Yotpi.Mfvd@phfilrDls.eko", "NpAmtiafq45@rvq-tpjmiu.bo", "Uqhx-Tqtngm@QvSgbgSvRwRr.nq", "Tpgzi.Glfihh@VdhnWasfkpJkqmf.ok", "Ualfji34@HK.xj," , "WoZxfUogwv@Uzvaljpyzc.xz", "JvecrJnd@Jx0dL.mi", "Bhstlayb.Wela-Cmvev@xamoabgspp.hb", "idmidajfe@jffriclrql.wch", "sndsikr.wwahwpy@grcvxgf.id", "phsgorjff.htrmuhgu@ysqew.gi", "Dxqxmrvz-Txpspi@iobhz.gc", "Kwgyskg-Ahnwuohznm@phuvm.wq",
     "Jjbc.uebPdbwewTltdal@nbzsmwewwerbe.rh", "Tfmisqqtu.Oxhmqf@vukdkyi.mq", "Wcycinjb.Yezrzs@zdl.clb", "Wkgpp.Ppad@skkaefuov.ifh", "AbObnhwgp27@eqf-ztbmqp.nj", "Hahf-Prutgc@NkXwkrOnViZh.pe", "Oqolg.Gbnwfy@PpopUtxNquip.jr", "Spaeeo26@RY.dy," , "ZnAqaAyteo@Pearlgikmt.gr", "AoswuJfx@Fr5gA.ev", "Gadjzhdw.Xliu-Ddjmu@beusuqthio.jm", "rzcmktbdc@dwxiapzoaw.usb", "jmkfpve.ovodpel@uqehcsl.lt", "jriwftlsg.gjvqgjjuas@oomzv.wq", "Smizjjky-Jgopns@tlrfka.qo", "Evfmu-Ekhtlqxlve@glrmu.uh",
     "zhsmAeax@mqjzjfkt.qtz", "RyzetWpfr@jkkyu.fu", "Q.Pqjos@NcmfretxfWdsj-Yotoskzqg.vd", "Jthqfm.Sajj@jnzmi.vs", "d.jot.drei.ele@sdr.de", "Duuwm03@fctouf.ej", "LnxobHxt.yvmm@uovddwyx.so", "BJ@CovaQqoDobas.ep", "miowtvkrtvxa@dnkzi.bq", "XrSvdXqpou@Sitfrvehpb.vt", "KgtvvFib@Py2mS.kr", "Hdtxhuli.XBxj-Nnmau@eholqtqdgc.ls", "tbiszfpwt@djjcyyvkgj.tkf", "bjttyni.gqtrkcz@ldfakbx.ps", "blqa.cvojdrbxu@gbcqo.zf", "D-Aatqhb@nxamr.mj", "Kepmcj-Gbwwpsv@jqvdwdufx.hm", "Vi.Arxgi@hgpl.hh",
     "Yoaonfdeoab@MlzrljFhtjnny.hq", "gnwjolmmjvfcq.wqhbdouc@v-kgpujlc.bf", "xwnzsmykpnyzf@vxzvk-cwbgeaz.zw", "k.kwzcobot@GweHzAaigeQdywouGnGhjpz.ki", "Buofu.Vakns@kmwjj.tr", "Fnuob.Qltbd@ugbvwaz.sv", "s.tiihb@fejdnhim.yt", "znagltqpc@vNVak.ig", "MiXrvIqjqu@cquogekjkk.hj", "Raiuwa.Qcjtgcdi@Vnnivupo.tk", "liwec.swxdibmjdpdr@Pfpla.yr", "UJ@CA.kw", "WckmpmpzWkzwlk@ibn.mfc", "Nifouuk.Sqqxitrz@xtvgzz.xb", "wqcenbgvn@ixwsb.yf", "roims-ccdwms-nmvkh@QVdj2.wk", "Mdtt.Vcdob@opuo.mt", 
     "WOfudhpe@wnw-qwxwgf.dl", "Deryq.Ywcd@shayr.ij", "rmaym.abjvz@dipirmak.lik", "wstoir.cbpxlddf@dkntoiojakebf.ed", "ZsQ@sgjim.tx", "Ybzky.OdnHwqwxnh@Gzpdm.dy", "czvnlxih.tpoycakihp@ItslZualh.cm", "Buudbatroi21@laoewjw.kd", "JayrhOnp@yvk.zrd", "axdhgl-wer@cjvo.djof.sjnqtk.fo", "JY@qHJJ2.me", "HsltlBee@pqnzxw.nb", "LF@dvvf.xl", "KM@JkkvjmnwnPpmi-Jtyhnnedu.ny", "QA@cxj.lhf", "Yggaknu.Ttewuaiw@qfyuwv.otr", "Lmsssaqug@042.3.7.1", "OmQtlZsypn@keiibbhx.lg", "ba.swmdq@Zrcdl.re",
     "qlmsrnnzeanmg@kdyjy.cp", "A-Hihb-Ydpdcv@ggsjxtvkdlcqk.yu", "FwIsoTbojo@bmxcfb.mz", "X-Cfkygc@ATKF.cp", "Qfdmef.Lvuctbr@jybds.da", "xrqph.lrohduyzpf@RDMS.ze", "Rnqgttyr66@HnsbXodDrkly.kw", "G-Gjotm@WvvsgaTwbpzmr.ag", "Bsoxogk-Lfwaijdzd@dmpxitg.ow", "X.Rwgbsof@lbmxemi.br", "glox.tvde@gokd.gh", "cvtdc-Kwrzm@ulgk.zgc", "i.ihwnczbk@omafhe.nhn", "Ldot-Wxgqmp@vejuebmiyi.skp", "Iidyri.Gqwlhmrfoeh@gaj.hv", "Rper.Zbt@gsciswfr.wz", "H.Lurawx@3qOKN.ws", "UicmpwUpqojI@IGkeyoa.fr",
     "Qspnexwc.Kemphmvzfv@nrufixpt.fw", "WK@ZqupwspinMhjg-Knsivlpxr.hf", "Wsudy.Ybxidqof@yyxy.up", "pajotsja-qodrwqdm@jkhAk.flm", "kdabyra@jllh.gc", "Pvuapxw.Ngnk@uzaxn.jiwfvsv.ir", "sla-ljwthr@LQxhp.oj", "lvwmekdkvp@FerzSxwHkcsy.fx", "cpbdedlgh@sxlig.vi", "Xzosh.Jtlkg@WyshckZnkwx.guy", "Ziaiuhh.Ejgkhb@sbhfol.ntsbw.hm", "Vnzthllx.Ksbgfa@ftylg.uj", "N.Hnkj@hyvixum.tsk", "OV@oufdmyfvi.sh", "TK@LickjvzsuZuss-Ozpaidffo.cm", "dyspk-qivlncp@lxylafhro.ev", "Yiei23@qem-pbpecuz.nr",
     "frnhseka@kjeyjpdb.cla", "JlpbiAch@qhwjl.sh", "K.Qlgaj@WkuyxjxvxSaap-Unskxqzyw.lv", "Toluha.Xdjp@caljc.kn", "woidcweq@qqqewspc.dqu", "Zlja.kjksdf.jim33@ywzoby.lj", "Kwb.llE.dqzohls@zlwcbreq.nn", "FB@CpucQeoJjlrn.nn", "ioccvmbfshnh@jblhj.fk", "HaKqiWfmhu@Jvgcbazdsz.qw", "TkwvwKej@Gj0dI.bj", "Stsduapffy.Jhaz-Hqyet@gjnmjotoxt.cb", "ecwmsbobl@xbpbreeqcm.tqb", "efvbnjy.dgqdwip@xvabgad.cc", "rosy.pzipdyyob@befeq.vo", "Paiu-Hmcwcn@yrtig.se", "Uuohvz-Gvpsdfuswll@pgnsjhmct.ui", 
     "Legqechj.Aiqktvmdde@dgploxkg.sv", "SJ@OzecbjplovxyfmFibe-Paxilasuo.ce", "Ilgox.Qduqkftr@vqwp.vv", "xttcdebkqqgrudsr-zadjsppz@feoz.sqs", "hwcqwms@akxk.ak", "Ifrjrnykhsp.Yler@tmhyc.vyxordr.mj", "umd-taayzkqbhuzbn@XRzav.sp", "cdkxfrhzwjrwkjayip@VjrcWvuFgsgo.ef", "bltxcpakuwhrfot@wbtgh.gy", "Lvswb.Bcumpdrq@OdezioCznbv.khp", "Tnyaownrqty.Lsuftp@ryiskg.pvjlv.nd", "Sjhhivhg.Nchzle@sgpha.pc", "M.Ljrgwdnmwds@tffmokv.hjq", "LP@fzahdqyao.sb", "EZ@ZxuxesylfdRkxm-Tlmwhutimtmt.qh",
     "Funpndcaxtc@YqzdzhZosihpa.op", "xkqykucvd.njbskfir@h-jnjumux.bd", "llhimrwdvqfnq@sunvy-epyipli.zr", "o.gsgsdfrpqsd-fsdfr@BunGbQmzewHibphtWhDvcpm.dh", "Xbegw.Uoncm@mtloZ.xd", "Wcgpi.Nszcu@pmlmlnx.se", "j.jjmcr@mlkhbmen.xl", "ypyasfmhd@jFWqj.us", "IyLtlAclth@frpgtxupjm.do", "Ulruqh.Ajjdwahd@Vphboaih.bc", "mwhwf.xwepihggngbh@Scdgi.vl", "XN@AB.ub", "GcbqpywcPaudh@lha.jlt", "Gutfepy.Chyagi@nxjuvz.fi", "ikkolzjuo@mmn.cf", "dofkh-iqfthr-jxqtc@BE5.tc", "Fiwa.Zzlxrxx@ijhn.gr", 
     "ZImeAjgo@qmd-gridam.tk", "Svrcu.Sdty@gsoca.yq", "rdikw.fgloa@lldskpsf.lzp", "ynizmt.eazxatih@uxyusfpcjvnmu.ax", "ORB@amfep.je", "Xxfkc.WszImdfmqe@Ywyfd.la", "rrjaqkud.pqssznmkby@EpasIeosa.kc", "Neeilympgr00@vrjomru.ey", "GdylrHmz@vel.vlw", "povity-ksl@jvsf.jekv.pradpd.ju", "LH@cNEV4.iq", "AliomOpx@erglpr.un", "KC@xggv.gt", "NB@PswmfipsqHmzh-Dteytjzvk.oh", "NP@nbp.nmb", "Ypbgdin.Cxiwjhrp@concsu.xbo", "Hvlmrmgyx@071.6.2.7", "LlTgdJfukk@xhjjsatl.bp", "m.fkgvps@Dbpau.ss",
     "tftdT.pejyauda@ukrpn.ce", "R-Hmlu-Ftkrnj@kpiqxkcw.nq", "IrDzcIkslr@lwoatd.hm", "N-Jcoyhc@UBEI.sn", "Ekjzqj.Oovpuh@iqjejx.gu", "fszcx.druywx@TFTN.kb", "Whczfdac62@BdkgEunWxZkf.da", "J-Mznts@HvknzrEfwgafq.zy", "Lftmjcm-Jwjqjtljk@fppasgm.zz", "X.Ffoavta@kmldryi.ua", "grifrz.dvvnmxi@yqpjcadn.ec", "Hjewr-Kpeqg@bxgh.trn", "y.roadhgnb@licbkw.kum", "Eduj-Ctbicq@ebyfihoouj.beu", "Bcxhvl.Hfcubyauwbg@pjf.dd", "Kjdd.Qfk@fdysapdq.am", "Z.Vhmvtk@4rPBM.ig", "KrmyzfHruxwF@Rfmdet.lo",
     "ifjbwbqj@qrldzxeu.tpf", "SbpsbVfo@qxstb.sf", "V.Jospo@SjhpogrqxYsnr-Uejseupbb.kd", "Ivqwem.Miit@kovwp.oe", "gplcrvnl@nqtont.gzx", "Tvmtotb23@tzaxmx.he", "WlrldSagnkoyt@zhpmbqaa.eh", "EL@TlouDcjPjrfr.oz", "rcblvmoebnob@ctcem.lt", "JzCqiMudid@Zkgonklozc.lt", "CwhymGsd@Ux0hX.vt", "Fmrzkorn.Pvjx-Guauk@ewrruzssod.vr", "vbfdrwxdy@sqkbvacksb.otm", "lldsxwt.iyhhvmz@lqjmrzi.qg", "tegj.pzzyqxl@dwcik.kr", "G-Wlgkux@qqhxl.yj", "Zovgci-Hteiugwb@snghtdkka.un", "Zdtiv@sanlnbqmp.zd",
     "Uxixe.Ggrvtp@du.voco.uku.ijgu.aj", "zngrjtnim@LR.df", "Wxihunkmg@JmtggQdvtslm.ff", "orbkushbiuc@vmgfjn.hvo", "FT@ggxidv.fu", "bqonpa.akzxyoy.qycwqzjkmm@zkbeyvfv.wo", "RO@pxiacvphjzema.obd", "Prpko.Szwigdhoa@kwwfyomvaqjsw.vr", "I.Pffehseeqs@lrlz.wbs", "Vpmwj-Fbcisr@cvtd.doh", "Kbflx60@NolsiSlQwhngymSookhfw.co", "Lbxfsbv.Pbrmqf@szorwdhaje.usa", "Kvuq-Evdqffyrb@tcazjpxy.cuo", "pkce.gzhhch@vluye-vlsgk.tp", "BS@S4Mht.bw", "Gunz.Rom@opzyj.cv", "Jiunmyhb.Udhqv@cnyfmhjdf.xb",
     "Jbnpbznrmyq@MogizsBhaefms.jz", "hbdmwtgxr.gdkqbabz@k-cxvaymu.eo", "qvccgimisthbc@xzydd-pkyhpci.hm", "d.gnadnxl@DsaFuKlpwqLygotyRmXecym.ai", "Hsroi.Olphq@laauh.sk", "Phpci.Suoci@rcdhnmq.hg", "l.mslwl@hoxnbOGr.fo", "dqpwdcejh@cZUsq.bv", "GoWtkSzzlk@ondxwpckgx.bg", "Yqwds.Eyyctfnr@Ntupudgl.oy", "pjhkm.ixhusbfadrbw@Pords.uu", "WI@MH.wj", "TpaxekpmYrareu@nyh.zqs", "Wceqpni.Iqjzzczm@qsfffonjb.ef", "znzludppo@cwmdy.uj", "tuujy-qagrng-cqfrq@VPen2.eu", "Zivi.Uwymhtk@xwhxl.qp", 
     "PDcl.gsf@dqb-wkgxky.om", "Janle.Qxci@ittak.fo", "gaoiv.xjwse@chtkwxly.kyi", "mlzcyb.finpgoyn@dbdxjpbsmwsst.jn", "MC@sxqgu.bg", "Kpcvq.BjfCeavbit@Elbbs.my", "luvpaxke.gghbhmcqzq@CkrqHqxnz.qq", "Jnykjahzwv02@euagabz.kv", "AcgwlHet@cvo.ffc", "zfqbzb-wgs@ztte.awxi.vtnyca.df", "YR@xZUA2.xj", "NedyaLhu@codazg.jr", "HX@wuix.sb", "OF@BpietxqyrCpnp-Ourjousfj.lp", "GN@vlg.fsdsz", "Zfguv.Pqcehir@kacscnz.ewd", "Uxlojbmwc@030.0.0.3", "HhWfcDrbfg@dfymkyue.ys", "g.plbxw@Fissvhf.ws",
     "glafdcuxifcdj@hulnz.fw", "I-Puhz-Uvquly@hczefyvr.vw", "ZtKwaWqqja@ncunih.vr", "Y-Fdfpci@HMBR.oz", "vstzreW.Iqgsvi@zjehc.gk", "jsaku.ufrpmt@OTVU.nt", "Qbpdybjy15@BnxhSjcSxrwr.mp", "F-Ltdst@SekupqYvwbeqc.mt", "Omgenfj-Wloctasov@ieyhhnq.pv", "Y.Rypltzx@izwtnig.jn", "bzrnnojqdgvnr@ivgujuhu.ab", "Zpcsz-Dlecp@vtkp.hpr", "t.etsioplm@joufhv.cfs", "Qtgv-Qwsfmn@gkvzvzghcr.yfu", "Wdbmey.Htrvyhuonla@fzaj.vf", "Kpsl.Ncr@ersugech.fi", "W.Otsuss@6eEZN.pk", "AmwigcGmzgwV@Ovlndoh.yf",
     "Somae.Qfiguwkizh@ebxjqusligcdx.ps", "jinsmv.nmbnrx@jxmnqe.ae", "Baiw-Nkas@jzglgh.sxm", "xfwqsdbew-nqppaq@MJjV2.ma", "X.Dreogd@ojA42.kz", "xziur.gyehhkga@qizmovyl.es", "Lsj.Qhlt@dymystlc.lf", "fpwkyy.khwqjranv@zlpvlooctyz.nm", "xtlhzmvybjr@vgmx.umsl.uzsemk.nx", "gorrijpejxc@ra.uh", "uayndekwhdirf@tsg.pis", "DU@pqgrkgr.zb", "Xhvtix.Nfmslvuq@krhlgxp.nj", "unkzhijf@LebuxpVqjovxw.ou", "Jqysux.Ozxgtscxkhzqj@gtkbk.za", "KysbahoC.K@TjeWsyLducEtpTzh.or", "Ed-Ouxkj@gnlhfmf.yr", 
     "Vnxit.Souffbvq@fdozbmd.qbs", "AK@uphbrlh.otv", "QiopdtoykgnaJalodsku@257.5.1.1", "Lpna-Elhcyf@ZEPY.xi", "n.dvjhmxwvy@tansyfy.wo", "Gibbu.Xiooxlbq@fifcdrdtiaoje.wgr", "DF@qnt.dtz", "M.Wedzxst@rnwlnnrz.zz", "byir-yicndyz@b-cksscaj.rq", "IxRamZkhgu@rmxontvw.rg", "RO@hx5Pt.pjp", "Eoc-Zpykatu@fub.dl", "K-Vntlqivydzmfyajevi@rwh.yj", "Ezxna.Aiqjy@YvxGoqTbclOeoCrb.rr", "Pohoa.Mku@NmitAixsg.mi", "fmmzgs.iovpfkuhkg@bardthhj.vv", "Kcamew.Trumn@qIgpL.th", "Swsecv.Ajlu@dksgnj.lr",
     "Hzibp.Gcdoyo@dl.zobe.ney.tfad.hq", "uxyfwkfci@WQ.nb", "Wphgzgmft@EnftnMpcfnlx.vr", "ejyykswljug@zdnfwm.yvt", "UF@nefeb.ug", "ujifg.fomwmnk@secbblqs.lv", "GU@patidnlbv.url", "Vqykp.Qhqkc@pawckhaubefcv.ht", "G.Owgffhwvee@ekhl.qoo", "Vgiqs-Mleuix@xnux.cmj", "Vncen14@XbbeeIzMlixhpiRofqmkb.de", "Wmtcioi.Erardi@wzyvtfsndf.pje", "Kdki-Qtxvdmjsx@agwkzglfl.vmd", "osxr.xemljw@kaxnt-egqnyvg.sm", "FJ@R5Miw.hb", "Jqkr.Wkv@sgazr.ql", "Z.Wnrbppuzic@jzeggvsv.ec", "Y.Htsxz@cagc.gtx",
     "Xuzdz.Rfazbygj@aflvqpv.hlr", "ME@qwvstpk.sqp", "ZpjjrlzfqqtcOjjdysxd@262.2.5.1", "Zqew-Rtktry@JGEV.fp", "u.iwmscecqc@yjfntir.ol", "Tuckj.Xcnullac@kaznmbmkxt.vkb", "ZB@ybo.tvr", "J.Ajhatlo@scbxjjyk.sy", "ozen-hrmrzmu@q-lcdipcu.nq", "IiMkoNpcit@pkdpnkvp.oi", "EL@pa7Ci.arq", "Kuoxe-Zqqflzv@lwrdk.eh", "B-Vxlpfywxangryg@ydg.uk", "Xrjdn.Nwwzs@ZvlLpoLyrsEnaDcq.mj", "Prqfk.Pgq@OwhzGxggl.mj", "vekphq.tjpaawnbon@gjlnfpoc.ic", "Pjjtyb.Mxbci@xVoxP.qv", "Gcwgvxup.Rhnd@ethtxyw.pw",
     "qplzl-kilwzqvg@Prcjp.xd", "Oldhjf.Ludk@Rudtzc63.zx", "gtlycke@Wixequ30.om", "osekr@ztqecunn.xw", "Hwnpplffw.Xrwfeeq@fejqoig.de", "Sahfa.Vazfcyid@qtxmqxiad.hqz", "SjfwfoThcskhoak@smcuvdfooi.yrj", "Vctwznxqsr.Lwnfovw@o-ngzgnao.fv", "Bbeuqysrum1@BbnFhgPxhzNfkTvo.ba", "RK@560.5.1.1", "IwiqmrInjthl-Rbcdj@Sc1IE.yif", "l.hwtsnpuduh@HzelcZlhysmt.cr", "qhtgqgba@pagi.eca", "FC@rejd.hlo", "W.Stczg@nuad.jz", "Thwtq.Extsd@wthrj.yu", "J.Fcnp@Jiey.mr", "Gomluunil.Hacr@dckkgfpd.eim",
     "vlxusudvj@wdqt.tj", "E.Hwhs@plyefdtk.ux", "dkpbtj.okl@FswiafDeh.we", "eyxgsi.ogniyiyi@Iespkvk.tk", "n.fxlfctr@kh-0A.gdn", "exbg.xr@ezsjigek.fb", "MjskbEey@ewasae.ae", "Pyooz-Nsds@GddPh.ar", "Nivsl.Yfgyvgc@vkig.cap", "u-torbv@6e6LD.im", "S.Cftnhc@GymfSegyt.qh", "Ww.Ptlq@riyizj.cn", "Snav70@hpst.ck", "TP@pav.x", "And.To.all.you.Reg.Ex.eMail.Validator.offionardos.out.there@remember.the.soundtrack.is", "I.just.call.to.say.i.hate.you@sarkasm.com", "DANKE@terima.kasih.com"
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
       * ... not suitable for Linux
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
