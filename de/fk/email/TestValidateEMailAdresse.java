package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator; // https://github.com/bbottema/email-rfc2822-validator
// import emailvalidator4j.EmailValidator;                                 // https://github.com/egulias/EmailValidator4J

class TestValidateEMailAdresse
{
  /*
   * Testdaten
   *     0 "ABC.DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *     1 "A@B.CD"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *     2 "A@A.AA"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *     3 "\"ABC.DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     4 "\"ABC DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     5 "\"ABC@DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     6 "A.B@C.DE"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *     7 "A.\"B\"@C.DE"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     8 "A.B@[1.2.3.4]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *     9 "A.\"B\"@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *    10 "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    11 "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *    12 "(A)B@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  6 = eMail-Adresse korrekt (Kommentar)
   *    13 "A(B)@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  6 = eMail-Adresse korrekt (Kommentar)
   *    14 "(A)\"B\"@C.DE"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    15 "\"A\"(B)@C.DE"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    16 "(A)B@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    17 "A(B)@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    18 "(A)\"B\"@[1.2.3.4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    19 "\"A\"(B)@[1.2.3.4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    20 "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    21 "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    22 "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    23 "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    24 "ABC-DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *    25 "ABC\@DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *    26 "$ABCDEF@GHI.DE"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *    27 null                                                                                  Jmail EXP     REGEXP 1 EXP     REGEXP 2 EXP     REGEXP 3 EXP     B EXP     EA false    = 10 = Laenge: Eingabe ist null
   *    28 ""                                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 11 = Laenge: Eingabe ist Leerstring
   *    29 "               "                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    30 "ABCDEFGHIJKLMNO"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    31 "ABC.DEF@GHI.2KL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *    32 "ABC.DEF@GHI.JK2"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA true     =  0 = eMail-Adresse korrekt
   *    33 "ABC.DEF@2HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *    34 "ABC.DEF@GHI.JK-"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    35 "ABC.DEF@GHI.JK_"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    36 "ABC.DEF@-HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    37 "ABC.DEF@_HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    38 ".ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *    39 "ABC.DEF@GHI..JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    40 "ABC.DEF@.GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    41 "ABC.DEF.@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *    42 "ABC.DEF@@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    43 " A . B & C . D"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    44 "ABC.DEF@GHI.JKL."                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *    45 "\".ABC.DEF\"@GHI.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    46 "\"ABC.DEF.\"@GHI.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    47 "\"\"@GHI.DE"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 85 = String: Leerstring in Anfuehrungszeichen
   *    48 "\" \"@GHI.DE"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    49 "ABC.DEF@\"\""                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    50 "ABC.DEF\"GHI.DE"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    51 "ABC.DEF\"@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    52 "ABC.DEF.\""                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 85 = String: Leerstring in Anfuehrungszeichen
   *    53 "ABC.DE\"F@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *    54 "ABC.DEF@G\"HI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    55 "ABC.DEF@\"GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    56 "ABC.DEF@GHI.DE\""                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *    57 "\"ABC.DEF@G\"HI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    58 "\"ABC.DEF@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *    59 "\"ABC.D\"EF@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    60 "\"AB\"C.D\"EF@GHI.DE"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    61 "\"AB\"C.D\"EF\"@GHI.DE"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    62 "@G\"HI.DE"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    63 "\"@GHI.DE"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *    64 "\"\"@[]"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *    65 "\"@\".A(@)@a.aa"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    66 "ABC DEF@GHI.DE"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    67 "\"ABC@DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    68 "\"ABC DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    69 "\"ABC\".DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    70 "A\"BC\".DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *    71 "\"AB\"C.DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    72 "\"ABC\".DEF.\"GHI\"J@KL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    73 "\"ABC\"\".DEF.\"GHI\"@JKL.de"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *    74 "\"Gueltige\\"Escape\\Sequenz\"@korrekt.de"                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    75 "\"Falsche\#Escape\GSequenz\"@falsch.de"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 84 = String: Ungueltige Escape-Sequenz im String
   *    76 "\"Falsch.da.Escape.Zeichen.am.Ende.steht\"                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 83 = String: Escape-Zeichen nicht am Ende der Eingabe
   *    77 "ABC.DEF@[1.12.123.255]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    78 "ABC.DEF@[001.012.123.255]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    79 "\"ABC.DEF\"@[127.0.0.1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *    80 "ABC.DEF@[1.12.123.259]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA false    = 54 = IP4-Adressteil: Byte-Overflow
   *    81 "ABC.DEF@[1..123.255]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *    82 "ABC.DEF@[1.123.25.]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *    83 "ABC.DEF@[..]"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *    84 "ABC.DEF@[1.2.]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    85 "ABC.DEF@[]"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    86 "ABC.DEF@[1]"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    87 "ABC.DEF@[1.2]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    88 "ABC.DEF@[1.2.3]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *    89 "ABC.DEF@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    90 "ABC.DEF@[1.2.3.4.5]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *    91 "ABC.DEF@[MyDomain.de]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *    92 "ABC.DEF@[1.00002.3.4]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *    93 "ABC.DEF[1.12.123.255]"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *    94 "ABC.DEF@[1.12.123.255].de"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *    95 "ABC.DEF@[1.2.3.]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *    96 "ABC.DEF@[1.2.3. ]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *    97 "ABC.DEF@[.2.3.4]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *    98 "ABC.DEF@[ .2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *    99 "ABC.DEF@MyDomain[1.2.3.4]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   100 "ABC.DEF@[1.2.3.4][5.6.7.8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   101 "[1.2.3.4]@[5.6.7.8]"                                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   102 "\"[1.2.3.4]\"@[5.6.7.8]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   103 "ABC.DEF[@1.2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   104 "ABC.DEF@[1.2.3.4"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   105 "ABC.DEF@[0.0.0.0]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   106 "ABC.DEF@[000.000.000.000]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   107 "ABC@[IPv6:]"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   108 "ABC@[IPv6:1]"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   109 "ABC@[IPv6:1:2]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   110 "ABC@[IPv6:1:2:3]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   111 "ABC@[IPv6:1:2:3:4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   112 "ABC@[IPv6:1:2:3:4:5]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   113 "ABC@[IPv6:1:2:3:4:5:6]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   114 "ABC@[IPv6:1:2:3:4:5:6:7]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   115 "ABC@[IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   116 "ABC@[IPv6:1:2:3:4:5:6:7:8:9]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   117 "ABC@[IPv6:1:2:3:4:5:6:7:8"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   118 "ABC@[[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   119 "ABC@[IPv6:1:2:3:4:5:6:7:8 ]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   120 "ABC@[IPv6:1:2:3:4:5:6:7:]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   121 "ABC@[IPv6:2001:db8::1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   122 "ABC@[IPv6:a:b:c:d:e:f:1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   123 "ABC[@IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   124 "ABC@[IPv6:1:2:3::5:6:7:8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   125 "ABC@[IPv6:1:2:3::5::7:8]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   126 "ABC@[IPv6:1:2:(3::5):6:7:8]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   127 "ABC@[IPv6:1:2:(3::5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   128 "ABC@[IPv6:1:2:3::5:8].de"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   129 "ABC@[IPv61:2:3:4:5:6]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   130 "\"ABC\"@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *   131 "\"ABC\"@[IPv4:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   132 "\"ABC\"@[IPv6:1234]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   133 "\"ABC\"@[IPv6:1234:5678]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   134 "ABC@[IPv6::ffff:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   135 "ABC@[IPv6::fffff:127.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   136 "ABC@[IPv6:12:ffff:127.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   137 "ABC@[IPv6:00:ffff:127.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   138 "ABC@[IPv6:0:0:ffff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   139 "ABC@[IPv6:1:2:ffff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   140 "ABC@[IPv6::ffff:127A.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   141 "ABC@[IPv6:ffff:127.0.0.1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   142 "ABC@[IPv6::ffff:.127.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   143 "ABC@[IPv6::ffff:fff.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   144 "ABC@[IPv6::ffff:1211.0.0.1]"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   145 "ABC@[IPv6::ffff:12111.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   146 "ABC@[IPv6::ffff:999.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 54 = IP4-Adressteil: Byte-Overflow
   *   147 "ABC@[IPv6::fff:999.0.0.1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   148 "ABC@[IPv6::FFFF:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   149 "ABC@[IPv6::abcd:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   150 "ABC@[IPv6::1234:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   151 "ABC@[IPv6:a:b:c:d:127.0.0.1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   152 "ABC@[IPv6::ffff:127.0:0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   153 "ABC@[IPv6:127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   154 "ABC@[IPv6:::127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   155 "ABC.DEF@[1234.5.6.7]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   156 "ABC.DEF@[1.2...3.4]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   157 "ABC@[IPv6:12345:6:7:8:9]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   158 "ABC@[IPv6:1:2:3:::6:7:8]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   159 "ABC@[iPv6:2001:db8::1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   160 "ABC@[D127.0.0.1]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   161 "ABC@[I127.0.0.1]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   162 "(ABC)DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   163 "ABC(DEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   164 "AB(CD)EF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   165 "AB.(CD).EF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   166 "AB.\"(CD)\".EF@GHI.JKL"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   167 "ABC(DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   168 "ABC.DEF@GHI)JKL"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   169 "ABC.DEF@(GHI).JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   170 "ABC(DEF@GHI).JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   171 "(ABC.DEF@GHI.JKL)"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 95 = Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   172 "(ABC).DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   173 "ABC(DEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   174 ")ABC.DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   175 "(A(B(C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   176 "(A)B)C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   177 "(A)(B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   178 "(A))B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   179 "(A)BCDE(F)@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   180 "ABC.DEF@GHI.JKL(Kommentar an dieser Stelle korrekt?)"                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   181 "Drei*Vier@Ist.Zwoelf.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   182 "ABC DEF <ABC.DEF@GHJ.com>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   183 "ABC/DEF=GHI@JKL.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   184 "1234@5678.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   185 "000.000@000.de"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   186 "0\"00.000\"@wc.de"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   187 "me+100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   188 "me-100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   189 "me-100@yahoo-test.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   190 "me..2002@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   191 "me.100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   192 "me.@gmail.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   193 "me123@%*.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   194 "me123@%.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   195 "me123@.com"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   196 "me123@.com.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   197 "me@.com.my"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   198 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   199 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   200 "me@me.co.uk"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   201 "me@me@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   202 "me@yahoo.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   203 "invalid.email.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   204 "email@example.co.uk."                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   205 "email@example"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   206 " email@example.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   207 "email@example,com"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   208 "xxxx@.org.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   209 "test test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   210 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   211 "test@test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   212 "test%test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   213 "test_test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   214 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   215 "test+test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   216 "test@gmail"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   217 "test@gmail."                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   218 "test@gmail..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   219 "test@Gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   220 "test@gmail.Com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   221 "test@GMAIL.COM"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   222 "1234@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   223 "check@thiscom"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   224 "check@this..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   225 " check@this.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   226 "check@this.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   227 "test@sub-domain.000.0"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   228 "test.test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   229 "Test.Test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   230 "TEST.TEST@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   231 "test@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   232 "TEST@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   233 "test0@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   234 "0test@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   235 "test@subdomain.gmail.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   236 "test@sub-domain.gmail.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   237 "test@anothersub.sub-domain.gmail.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   238 "test@sub-domain.000.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   239 "test@sub-domain.000.0rg"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   240 "test@sub-domain.000.co-om"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA true     =  0 = eMail-Adresse korrekt
   *   241 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   242 "xyz@blabla.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   243 "jsr@prhoselware.com9"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *   244 "dasddas-@.com"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   245 "%2@gmail.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   246 "\"%2\"@gmail.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   247 "\"a..b\"@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   248 "\"a_b\"@gmail.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   249 "_@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   250 "1@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   251 "-asd@das.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   252 "as3d@dac.coas-"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   253 "dsq!a?@das.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   254 "_dasd@sd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   255 "dad@sds"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   256 "asd-@asd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   257 "dasd_-@jdas.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   258 "asd@dasd@asd.cm"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   259 "da23@das..com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   260 "_dasd_das_@9.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   261 "d23d@da9.co9"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *   262 "dasd.dadas@dasd.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   263 "dda_das@das-dasd.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   264 "dasd-dasd@das.com.das"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   265 "prettyandsimple@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   266 "very.common@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   267 "disposable.style.email.with+symbol@example.com"                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   268 "other.email-with-dash@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   269 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *   270 "\"much.more unusual\"@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   271 "\"very.unusual.@.unusual.com\"@example.com"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   272 "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com"       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   273 "\"()<>[]:,;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   274 "#!$%&'*+-/=?^_`{}|~@example.org"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   275 "{john'doe}@my.server"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   276 "hello7___@ca.com.pt"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   277 "peter_123@news24.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   278 "alirheza@test.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   279 "hallo2ww22@example....caaaao"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   280 "d@@.com"                                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   281 "hallo@example.coassjj#sswzazaaaa"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   282 "x._._y__z@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   283 "_______@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   284 "1234567890@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   285 "admin@mailserver1"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   286 "\" \"@example.org"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   287 "example@localhost"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   288 "example@xyz.solutions"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   289 "user@com"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   290 "user@localserver"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   291 "user@[IPv6:2001:db8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   292 "user@[192.168.2.1]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    B false   EA true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   293 "email@192.0.2.123"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   294 "email@example.name .name"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   295 "(comment and stuff)joe@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   296 "joe(comment and stuff)@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   297 "joe@(comment and stuff)gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   298 "joe@gmail.com(comment and stuff)"                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   299 "joe(fail me)smith@gmail.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   300 "joesmith@gma(fail me)il.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   301 "joe@gmail.com(comment and stuff"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   302 "Abc.example.com"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   303 "A@b@c@example.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   304 "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   305 "just\"not\"right@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   306 "this is\"not\allowed@example.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   307 "this\ still\\"not\\allowed@example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   308 "lots.of.iq@sagittarius.A*"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   309 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   310 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   311 " joe@gmail.com"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   312 "joe@gmail.com "                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   313 "jsmith@whizbang.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   314 ".ann..other.@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   315 "ann.other@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   316 "x.yz.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   317 "x_yz_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   318 "doysmith@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   319 "D.Oy'Smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   320 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *   321 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *   322 "Joe.\\Blow@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *   323 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  0 = eMail-Adresse korrekt
   *   324 "someone@somewhere.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   325 "someone@somewhere.co.uk"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   326 "someone+tag@somewhere.net"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   327 "futureTLD@somewhere.fooo"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   328 "fdsa"                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   329 "fdsa@"                                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   330 "fdsa@fdza"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   331 "fdsa@fdza."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   332 "a+b@c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   333 "a@b.c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   334 "( a @[]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   335 " a @[]"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   336 " a @[] "                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   337 " a @[ ]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   338 " a @[ ] "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   339 " a @ []"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   340 " a @a[].com "                                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   341 "DotAtStart@.atstart"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   342 "DomainHyphen@-atstart"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   343 "ExpectedATEXT@;atstart"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   344 "DomainNotAllowedCharacter@/atstart"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   345 "ConsecutiveDots@at..start"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   346 "ConsecutiveCRLF@test\r\n\r\nat"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   347 "CRLFAtEnd@test\r\nat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   348 "CRWithoutLF@test\rat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   349 "ATEXTAfterCFWS@test\r\n at"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   350 "ExpectedCTEXT@test\r\n \n"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   351 "UnclosedComment@a(comment"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   352 "DomainNotAllowedCharacter@a,start"                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   353 "ConsecutiveAT@@start"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   354 "ExpectedATEXT@at[start"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   355 "DomainHyphen@atstart-.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   356 "DomainHyphen@bb.-cc"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   357 "DomainHyphen@bb.-cc-"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   358 "DomainHyphen@bb.cc-"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B true    EA false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   359 "DomainNotAllowedCharacter@atst\art.com"                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   360 "DomainNotAllowedCharacter@example\"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   361 "DomainNotAllowedCharacter@exa\mple"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   362 "UnclosedDomainLiteral@example]"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   363 "DomainNotAllowedCharacter@example'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   364 "Test.Domain.Part@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   365 "Test.Domain.Part@subdomain.example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   366 "Test.Domain.Part@has-hyphen.example.com"                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   367 "Test.Domain.Part@1leadingnumber.example.com"                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   368 "Test.Domain.Part@example.co"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   369 "Test.Domain.Part@subdomain.example.co"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   370 "Test.Domain.Part@has-hyphen.example.co"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   371 "Test.Domain.Part@1leadingnumber.example.co"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   372 "Lat%ss\rtart%s@test.local.part"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   373 "at\"start\"test@test.local.part"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   374 "at(start)test@test.local.part"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   375 "example(comment)@test.local.part"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   376 "withdot.@test.local.part"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   377 "Test.IPv6@[[127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   378 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   379 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]"                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   380 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]"                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   381 "Test.IPv6@[IPv6:1::1::1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   382 "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   383 "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   384 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]"                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   385 "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   386 "Test.IPv6@[\n]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   387 "\"B3V3RLY H1LL$\"@example.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   388 "abc.\"defghi\".xyz@example.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   389 "\"abcdefghixyz\"@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   390 "abc\"defghi\"xyz@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   391 "abc\\"def\\"ghi@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   392 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   393 "jsmith@[IPv6:2001:db8::1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   394 "user@localhost"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   395 "aHZss...Ym8iZXJn@YWRtAW4g.au"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   396 "\"RmF0aGlh\"@SXp6YXRp.id"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   397 "\"hor\ror\"@nes.si"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   398 "!yoora@an.yang.ha.se.yo"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   399 "$8473790.$M7616E696B@example.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   400 "bob.mcspam@ABCD.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   401 "somecahrs@xyz.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   402 "person@registry.organization"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA false    = 15 = Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
   *   403 "user@host.network"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   404 "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 94 = Kommentar: kein Kommentar nach dem AT-Zeichen
   *   405 "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   B false   EA false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   406 "\"-- --- .. -.\"@sh.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   407 "Find#Me@NotesDocumentCollection.de"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   408 "\"\\" + \\"select * from user\\" + \\"\"@test.de"                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   409 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com"              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B false   EA true     =  0 = eMail-Adresse korrekt
   *   410 "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   411 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   412 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL"     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   413 "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   414 "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   B false   EA false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   415 "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   416 "zzzz.zzzzzz@zzzzzzzzzz.zz"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   417 "AAAA.AAAAAA@AAAAAAAAAA.AA"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   418 "aaaa.aaaaaa@aaaaaaaaaa.aa"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   419 "Vorname.Nachname@web.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   420 "m.fanin@fc-wohlenegg.at"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   421 "old.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   422 "new.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   423 "test1group@test1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   424 "test2group@test2.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   425 "test1@test1.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   426 "test2@test2.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   427 "junit.testEmailChange@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   428 "at@at.at"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   429 "easy@isnt.it"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   *   430 "yes@it.is"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    B true    EA true     =  0 = eMail-Adresse korrekt
   * 
   * Testlauf 1 Anzahl Testfaelle  12816000
   * 
   * ea234      anzahl_korrekt =   8960000 - anzahl_falsch =   3856000 - anzahl_fehler =         0  | MS    917 = 0.00007155118601747815     = 00:00:00:917
   * 
   * Jmail      anzahl_korrekt =  11376000 - anzahl_falsch =   1424000 - anzahl_fehler =     16000  | MS   1787 = 0.0001394350811485643      = 00:00:01:787
   * 
   * JAVA 1     anzahl_korrekt =   7760000 - anzahl_falsch =   5040000 - anzahl_fehler =     16000  | MS   3530 = 0.0002754369538077403      = 00:00:03:530
   * 
   * REGEXP 1   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   9331 = 0.0007280742821473158      = 00:00:09:331
   * 
   * REGEXP 2   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   3156 = 0.0002462546816479401      = 00:00:03:156
   * 
   * REGEXP 3   anzahl_korrekt =   8000000 - anzahl_falsch =   4800000 - anzahl_fehler =     16000  | MS  24789 = 0.0019342228464419475      = 00:00:24:789
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 2 Anzahl Testfaelle  12816000
   * 
   * ea234      anzahl_korrekt =   8960000 - anzahl_falsch =   3856000 - anzahl_fehler =         0  | MS    697 = 0.00005438514357053683     = 00:00:00:697
   * 
   * Jmail      anzahl_korrekt =  11376000 - anzahl_falsch =   1424000 - anzahl_fehler =     16000  | MS   1925 = 0.0001502028714107366      = 00:00:01:925
   * 
   * JAVA 1     anzahl_korrekt =   7760000 - anzahl_falsch =   5040000 - anzahl_fehler =     16000  | MS   3187 = 0.00024867353308364546     = 00:00:03:187
   * 
   * REGEXP 1   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   9541 = 0.000744460049937578       = 00:00:09:541
   * 
   * REGEXP 2   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   3248 = 0.00025343320848938825     = 00:00:03:248
   * 
   * REGEXP 3   anzahl_korrekt =   8000000 - anzahl_falsch =   4800000 - anzahl_fehler =     16000  | MS  22002 = 0.0017167602996254682      = 00:00:22:002
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 3 Anzahl Testfaelle  12816000
   * 
   * ea234      anzahl_korrekt =   8960000 - anzahl_falsch =   3856000 - anzahl_fehler =         0  | MS    772 = 0.00006023720349563046     = 00:00:00:772
   * 
   * Jmail      anzahl_korrekt =  11376000 - anzahl_falsch =   1424000 - anzahl_fehler =     16000  | MS   1670 = 0.00013030586766541824     = 00:00:01:670
   * 
   * JAVA 1     anzahl_korrekt =   7760000 - anzahl_falsch =   5040000 - anzahl_fehler =     16000  | MS   3222 = 0.00025140449438202246     = 00:00:03:222
   * 
   * REGEXP 1   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   8925 = 0.0006963951310861423      = 00:00:08:925
   * 
   * REGEXP 2   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   3117 = 0.00024321161048689139     = 00:00:03:117
   * 
   * REGEXP 3   anzahl_korrekt =   8000000 - anzahl_falsch =   4800000 - anzahl_fehler =     16000  | MS  23643 = 0.0018448033707865168      = 00:00:23:643
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 4 Anzahl Testfaelle  12816000
   * 
   * ea234      anzahl_korrekt =   8960000 - anzahl_falsch =   3856000 - anzahl_fehler =         0  | MS    718 = 0.00005602372034956305     = 00:00:00:718
   * 
   * Jmail      anzahl_korrekt =  11376000 - anzahl_falsch =   1424000 - anzahl_fehler =     16000  | MS   1826 = 0.00014247815230961297     = 00:00:01:826
   * 
   * JAVA 1     anzahl_korrekt =   7760000 - anzahl_falsch =   5040000 - anzahl_fehler =     16000  | MS   3195 = 0.00024929775280898875     = 00:00:03:195
   * 
   * REGEXP 1   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   9335 = 0.0007283863920099875      = 00:00:09:335
   * 
   * REGEXP 2   anzahl_korrekt =   9696000 - anzahl_falsch =   3104000 - anzahl_fehler =     16000  | MS   3256 = 0.0002540574282147316      = 00:00:03:256
   * 
   * REGEXP 3   anzahl_korrekt =   8000000 - anzahl_falsch =   4800000 - anzahl_fehler =     16000  | MS  22547 = 0.0017592852684144818      = 00:00:22:547
   * 
   * 
   */

  private static DecimalFormatSymbols otherSymbols             = null;

  private static DecimalFormat        number_format            = null;

  private static int                  durchlauf_anzahl         = 0;

  private static int                  test_daten_array_length  = 0;

  private static int                  stellen_anzahl           = 0;

  private static boolean              knz_ausgabe_test_daten   = true;

  private static int                  FAKTOR_ANZAHL_ARRAY_A    = 16000;

  private static String[]             array_test_daten_aktuell = null;

  private static String[]             array_test_daten_a       = {

     "ABC.DEF@GHI.JKL",
     "A@B.CD",
     "A@A.AA",
     "\"ABC.DEF\"@GHI.DE",
     "\"ABC DEF\"@GHI.DE",
     "\"ABC@DEF\"@GHI.DE",
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
     "$ABCDEF@GHI.DE",
     null, "",
     "               ",
     "ABCDEFGHIJKLMNO",     
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
     "\".ABC.DEF\"@GHI.DE",
     "\"ABC.DEF.\"@GHI.DE",
     "\"\"@GHI.DE",
     "\" \"@GHI.DE",
     "ABC.DEF@\"\"",
     "ABC.DEF\"GHI.DE",
     "ABC.DEF\"@GHI.DE",
     "ABC.DEF.\"",
     "ABC.DE\"F@GHI.DE",
     "ABC.DEF@G\"HI.DE",
     "ABC.DEF@\"GHI.DE",
     "ABC.DEF@GHI.DE\"",
     "\"ABC.DEF@G\"HI.DE",
     "\"ABC.DEF@GHI.DE",
     "\"ABC.D\"EF@GHI.DE",
     "\"AB\"C.D\"EF@GHI.DE",
     "\"AB\"C.D\"EF\"@GHI.DE",
     "@G\"HI.DE",
     "\"@GHI.DE",
     "\"\"@[]",
     "\"@\".A(@)@a.aa",
     "ABC DEF@GHI.DE",
     "\"ABC@DEF\"@GHI.DE",
     "\"ABC DEF\"@GHI.DE",
     "\"ABC\".DEF.\"GHI\"@JKL.de",
     "A\"BC\".DEF.\"GHI\"@JKL.de",
     "\"AB\"C.DEF.\"GHI\"@JKL.de",
     "\"ABC\".DEF.\"GHI\"J@KL.de",
     "\"ABC\"\".DEF.\"GHI\"@JKL.de",
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
     "ABC.DEF@GHI.JKL(Kommentar an dieser Stelle korrekt?)",
     "Drei*Vier@Ist.Zwoelf.de",
     "ABC DEF <ABC.DEF@GHJ.com>",
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
      "disposable.style.email.with+symbol@example.com",
     "other.email-with-dash@example.com",
     "foo\\@bar@machine.subdomain.example.museum",
     "\"much.more unusual\"@example.com",
     "\"very.unusual.@.unusual.com\"@example.com",
     "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com",
     "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org",
     "#!$%&'*+-/=?^_`{}|~@example.org",
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
     "\"B3V3RLY H1LL$\"@example.com",
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
     "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]",
     "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
     "\"-- --- .. -.\"@sh.de",
     "Find#Me@NotesDocumentCollection.de",
     "\"\\\" + \\\"select * from user\\\" + \\\"\"@test.de",
     "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com",
     "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de",
     "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL",
     "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL",
     "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part",     
     "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de",
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

      "tbrsxtpj@bivbj.nd", "sldihhhgrx.jindpjp@nrluirskg.uv", "h-tghyfuzezf@twc.rfb", "etlnihgzmmawga@wbytss.rr", "kgpeg.pzcunyia@ebyjs-zxnkxjp.an", "f.kdevhltxs@kixypkz.tx", "yx@dthbd.rx", "u.ocqrs@jwxpkzebokpw.ep", "c-tqtskrauz@warzbwur.wc", "mzdxgmkukqkr@nm.okui.okh.semn.tq", "lqwngbey@cprmuvircnt.ek", "qieaw@weqlfisw.fq", "or@iexyspexxwcgu.xe", "exthairgblvkcke@vlsm.lg", "xyiyfpaizy.guhgthb@myqqauvxs.do", "cr@qlvkzahpyzkqc.yp", "tbrsxtp20@ivbjndva.vt", "dtlouwv25@exgwmxye.yt",
      "bk@jksymdm.xcv", "yqrmu-neuuzs@tqnd.sbo", "kilh.fcukfd@xonawyznjbbzvemnzpsttix.iz", "htghyfuzezft@wc.rfbw.ohw.njol.iw", "cxyn.lkchjy@qnmjjqhq.qwa", "erlflhoazcc@wzrxd.sg", "amfnd@zx.wd.gz", "blnqqliunsbprv@zbtpfb.yu", "guknvsqpg-tdnqpv@esosnf.qa", "vvjrvexs@tgfdq.iu", "rwtdpivu.igwklfe@wvz.shu", "gryx.otq@bhdgqynk.ga", "mmouoaurnpw@zylzx.po", "e-dwdsvxwcb@pekqipnm.qj", "l.wepwb@hfeyjawopjuz.bg", "actyt.mfznrkewaflido@nxbftbm.tx", "mgkywnn.bmac@eq.fw", "cztpk@xyquoquz.ff",
      "l-taHCBvaloq@ungvs.ln", "upgvpfgmyj@ityosjfohu.oab", "sldihhhgrxjin@dpjpnrlui.rsk", "djayki.emcq@jytwljp.gug", "wrwse.qqvcmnw@blinyl.nnh", "q.oiwo@jlglucmf.lt", "cuuz.qxorvt@utlmfulk.zhu", "euptgvcqn-gzqaoa@zomvxa.uk", "t-fvgrgezpnr@vkj.hrw", "lvzjyolo.celgcbl@leg.xbn", "ldyipo.nnoj@fhxekfv.vmc", "kflyiccl@stzogrxatzw.se", "w.oxkhqyvpc@pwtcjps.ya", "nccfd.mcuxbiruwoiavb@tejsech.gd", "yxdthbdrxe.iant@piktulh.xe", "hqcud.sfiqujz@ufnjfp.ytq", "xggxw-ochkdav@cjsas.am", "ma@hceahlm.li",
      "tbrsx-tpjbivb@jndva.vt", "sukrthcmfrzxxfw@aonm.wc", "v.eqah@eszhawra.lc", "nauuimj.muml@eg.vf", "wxet.uqt@bdwfkdfq.fq", "zbhgqadyfq@hkyesiaulu.bqo", "nwzjmzwewmoxmqkoybx@wby.zxi", "opncylwhthxpp@jyyowmogh.avg", "rewhe-vtjvgo@ebvi.odg", "opnc.ylwhth@xppjyyowmoghavgecvxvsjd.qw", "vynep.rmjhlcxs@fycyj-bakeeoo.ym", "m-jomracra@fzyrc.az", "b.lnqqliu@nsbprvzbtpf.by", "zhkchnjoffbfgkwcdop@reb.haf", "jtfhlqfpyh.ijgq@qivrock.mp", "v.yneprmj@hlcxsfycyjb.ak",
      "mrvjjnxtv-rfwqzf@ebbgmf.vq", "ouuru.yajbjgnh@xefbk-tqqlkoj.ti", "qffg.finmfm@abeqozdsqwkpkszyjagmwks.ph", "auztglanky.fglf@iebjdqn.rg", "ijym.rraoww@fpbuiewx.tum", "jjxlfckeqeif@li.hhqs.spa.vtkd.ce", "e-faetvmiwxb@ibh.kad", "urgrsryirlwdv@spvfducjh.qtv", "d.migjv@ppyhtwdbrsvg.oy", "pbbsxkng@xabrtflanra.nq", "q-uplkwoitw@lkxyzkem.pf", "e.qpzphlm@iorzjzzqcah.nb", "nvzxslwaddegbk@idrcwv.zn", "kltoepgnbuimyf@chfmct.ol", "lelmoodgzkpyvlv@pmav.rc", "dgekrijzyrzb@vu.oytm.kbv.lebg.ef",
      "omrnf.lbtojos@sibntc.bjq", "ifuywelyis@jvaxeiarcg.gdj", "tysy.gfyfvq@nkrybmoifjdxqvxrmgypugg.bn", "w.obdlpvhxo@dzdklcc.sd", "znssdanqohkc@yy.bjpb.wtn.frti.qh", "paxnz.wrpvfdxzshcwyx@ihvibcp.pz", "dpmtdiefmmqpfxv@psmf.me", "veuul.eplpmkvksejkxi@cuhphrn.su", "gmdaytdo.quystcm@xyi.kod", "tgvcusw62@pffhneew.pw", "l.tevfoqb@ygbjfznainn.ww", "hmczm.eoekbbjdqmasdk@jrrsvjv.di", "ezxibhgswihnsbg@gaac.yj", "qlmhvjpthw.tphj@wswfyzv.dt", "bnrpyizwws.rguevox@toliuamyy.ox",
      "hvkhg.blbnawky@lntfz-xrgnsfy.xu", "kcmczlnx@aewlauptvja.fl", "cacmtgtjszheo@sfdmuzgks.icc", "xpxes.zsgwihtbyaegjg@jlrtsdw.rp", "pebzc-ghvxpdf@mgfah.bg", "awzzliudk-nhudhc@ewtjwq.zy", "xnxw.vzahky@jtupagawsaozfzpjbvfjozd.vx", "lbhx.byqgzk@yrghlvfqxzcthzansxzzvjg.eg", "tdrnh.ckuipny@rjvkog.yzd", "vhzgnogsrw@kcwhchvasn.gsx", "zpsyiw.hgov@mqrzvjj.lcz", "cgfi.luazso@byxyjyoo.ddn", "wzitcmkdmxwbfomtvce@adz.gmt", "zdlgywuanr@ybanovfyte.lqx", "hjdpx.byvmviv@hzgfbm.pxa",
      "gmdyg.qffeycgf@ugznf-dljjpez.nt", "m-jtdcxbuwvr@pmc.woi", "uwklsbhogfvk@pq.nsvt.rws.jmpf.dp", "nvdaxvvhsc.hnhpgew@ssxoguwhh.dt", "siem.yqrszj@gfhghdrx.wxb", "ilyswouexl@hcuvbeqhzb.pff", "dpmslfgvaqudqpprgjx@jkh.emv", "unccf-bmhrle@wfnf.bxt", "n-zhosjjxgh@dhimkaty.jl", "zktio.xxhpbqpdgwnykb@hwegpjq.cl", "jvjhunwrghzaia@mwzuxm.um", "gmdygqf11@ycgfugzn.fd", "gcsjwcoipkoybevlymf@usb.bki", "uwkls-bhogfv@kpqn.svt", "djjwtszgabajij@mxeptm.hw", "jyjp.rmorts@xbpjmufikvrdmcmfglcxzct.ek",
      "rvtpd-rxfdcqh@mswnd.wl", "jrbnbmlkr-ienmqe@bmwdtf.pd", "utgxxotxsazqn@tjwznaimp.izz", "nzhos-jjxghd@himk.aty", "cdgaouizgedyqp@jaiaqu.qm", "tyswncav@jurxdqvocfx.gj", "bprxvi.rwid@pgboumd.gbu", "i.fvwea@nowwnimpykon.vd", "qniic.snsfqxjgxluawa@apjufxz.wj", "mflkkwqnhw.xbrsouv@snjwlrtgw.ir", "koxagzw61@ditltafq.pm", "sqhqmdqi.miyawvk@vzf.neb", "g.wssccei@qpsxmlvpnle.cy", "rbxkueev.drkbvya@hfq.pab", "r-xtxarpfpmo@htc.wlj", "nzho.sjjxgh@dhimkaty.jlt", "lsanpx.walp@hdwedeg.qco",
      "cccvq-gljeje@ewcf.bsh", "kipyrytn@btqfiasnxde.co", "axdfjv.rtlh@gnyugdm.lmc", "mipyejedvbtv@sp.sqry.exc.fwjl.jd", "vbrey.ndlpmso@uoupcn.kku", "ifqqgnrxvbporcumyur@ydr.whx", "nrvh.fvkadd@mamgyjjt.yez", "yaitq-yuugezr@pclwp.xd", "dpmslfgvaq.udqp@prgjxjk.he", "cgfiluazsobyx@yjyooddnd.bkw", "hocgr-biulqdw@vsfan.nq", "hsgtloxlzv@aqxpjxjtgd.ato", "aahs.djfkzm@chzrkzjsdqfwtvtbsfvldqq.wo", "kguwslyeqdqyvex@niiu.ya", "lyipeyyh.mzarzyl@xzd.jqf", "xgpufyxaiorfsvt@ohjl.xc",
      "qijsisdt.hvbirem@yov.ziv", "fplrzq.qmux@xtaeoua.dxe", "azdn.gvj@tyrdofeg.as", "o.lngpuhtbt@umjnrrr.gr", "znsqlwpgc-lopkqv@ldyoqt.vu", "l.wighygh@apcrsolztpw.hi", "edbu.eswzyr@hvfqeelc.mkv", "m-clcnwynum@zbkjowpe.aa", "ejeqvfdpyg.cpowhyo@wqhytvhkf.hx", "awzzli.udkn@hudhcew.tjw", "ynwvj-klxdfmj@edaju.rs", "sohipdyizy.azpmkxu@ravehakxu.sb", "lyejh.kcqlfbxe@khyik-glwzckv.zx", "nrvhfvkadd.mamg@yjjtyez.jz", "lmxrx.kpkkanjo@yakex-yqrcrvi.xb", "ezxgj-eijkml@beta.ipw", "zhputji50@wuvcksrb.ek",
      "negfmjtwfczixfk@wezk.dn", "yzdnskydekayvp@hxyeaq.iv", "aahsd-jfkzmc@hzrk.zjs", "ecxpgeaiwwjbl@dagvkiemf.mcz", "i.gzccokfyq@lcgccif.gy", "y.eddmk@icdfwyjigtkf.bi", "fugcvdeuryuau@kkrvmurjn.weg", "c.nehctcy@dtuzsbirfcr.ee", "c-kathgohpp@ygreevjw.bq", "a.bypfiecjh@dvsabah.uk", "lhlttlxwzzsmonophkb@xff.zhu", "knoplyem@aomgwtsjjef.ui", "gtcwxqhdqd.whaw@ftetrma.lf", "zlhtctuab-pjeybu@xffwku.lm", "ylhto.ijkhhgoo@mvulk-wpgmgsr.it", "gmrj.undyqn@wvaoekuzyphqvmgzffkvect.ul",
      "evzttcnokz.jtad@nxzetfb.ym", "xuti.ywahxw@vwscgjac.ugx", "wnmhvknkbfzs@gu.znnv.ojy.iedw.as", "t-rvbaanbxxr@psq.ifh", "oyijmsrsnmbqv@twpcouzqa.hdv", "s.clwib@bpogdqqtyrmk.ke", "voqntyjb@dzylaiemeor.og", "j-fawwxrafv@kktjoyow.jw", "j.ohgjxmg@tpaycmvonwe.ia", "ezdhopbfyefnbz@rtvkku.hs", "ivunwlmewurgzs@hfwbpq.vs", "jlesokyqflefbqr@sqqn.uu", "gqvxparpersp@bl.igrf.odn.kzbm.na", "msktf.hwdujcz@xmxqxs.umi", "kplluvunor@bigoypxkhi.xce", "ahyz.jtjtlq@prdrmdsgcrforkvoqxlpyby.qr",
      "i.wysuyivyo@pztfldn.us", "gsqzomnxphrw@oh.egga.zhb.axwj.am", "ipqrf.yfowexmrmdluhl@tsgpjvh.rr", "xwolyjwqimudfyc@ipxf.km", "lmhxa.ifxanfofrutbdq@uriabyo.oz", "ewezrpjf.muhmuqr@vqx.wlk", "fprsdbk22@pqfxjffg.rm", "s.ydcqaqi@zgjdujqxzlq.kk", "cpxyo.ewiqcmiiikadpp@nkonsmx.ix", "hffchzdbmifodit@tlun.hm", "ymdwnvftsx.gvbj@quaegtu.cd", "iwysuyivyo.pztfldn@usveuynfj.gv", "whegg.kkezvhed@bsvvi-ggmuvym.zd", "ffgctosl@tbcxtutqrzn.my", "rlwltpsmeusyu@ikgbdiuqz.lvq", "qfpkq.dfpjdxuhbdlpvb@xqmwmfc.ip",
      "jleso-kyqflef@bqrsq.qn", "zdrhdhnwc-jsnxte@glmicd.uq", "qyii.adcjht@egfhvswjtufynhvatlcgbto.zx", "zrkp.thaqbe@dzhhxrhyaixweeldadqowmw.fi", "vvyoj.ymifjqz@dblexa.yrs", "tszgynjszr@pjmdmdxsln.hjk", "bzinyr.nfgr@aqmywos.ioe", "wnic.xxquak@bymhvpxc.kep", "ukjunmoeusaivjwoxux@aeq.tjr", "lmhxaifxan@fofrutbdqu.ria", "yjzis.iqdeskb@ammyey.qfo", "zxoll.uhgctxtp@nbmjs-efaixme.ek", "d-ipwydtcott@ufp.chk", "wkiubxyunady@fy.vrrp.xvs.oanr.nx", "mynpxudtvz.wtfsbkz@myklervml.gj",
      "ancv.bgpjmf@jllxpxuz.goi", "sclwibbpog@dqqtyrmkke.zrx", "txyxsnurxmljawmzjtm@fbs.msm", "ytkxd-viauhy@jecy.mtr", "z-dvmbukned@qhsgjece.mi", "dmtkt.qhscxqoglgehjz@zfuicgj.ov", "vaxfdywheemaru@laibzj.mi", "zxolluh20@txtpnbmj.se", "vrubolyrreufbehiaui@cmf.zpt", "wkiub-xyunad@yfyv.rrp", "hljyymkrnyzilo@wnnnre.pm", "uhfg.szzogn@eounssbnhlywptmehdfmtsx.oj", "rxddc-pfsfzgn@luruf.qq", "chttzrxtd-duossh@iviyhl.kg", "jfawwxrafvkkt@joyowjwsw.ksn", "zdvmb-uknedq@hsgj.ece", "wgaaiwnnybjkjo@nxeqdb.ds",
      "wijlexgu@bpfyyowtlbj.lv", "hdgukz.lbay@hnwztvf.gzy", "g.qvxpa@rperspbligrf.od", "gvunj.abodmoqpeiccgp@vhubmon.gv", "kplluvunor.bigoypx@khixceqec.jb", "qbmwvqq76@yaagezos.pk", "lfzwkicq.ydnbcyn@bir.jrg", "w.efyjkse@nljewssxpvt.xp", "cjtcwqps.qnsozcg@gbv.lqi", "q-elesqiyhjy@ano.ymy", "zdvm.bukned@qhsgjece.mil", "bamswf.jwil@ykglamj.brj", "qsemi-pvtgek@mwbr.yup", "rrwbnpcl@dpozibjcnej.mk", "mbrcrh.rjje@tnhofhv.spy", "xutiywahxwvw@sc.gjac.ugx.vtmh.xd", "uhjmq.mwehich@oaxqsg.krh",
      "trvbaanbxxrpsqifhzi@hyg.tkt", "ufke.tmefvy@ehhqxsmt.wik", "angbz-tmanzhf@fkuvl.sj", "txyxsnurxm.ljaw@mzjtmfb.sm", "wnicxxquakbym@hvpxckepg.ogf", "xwoly-jwqimud@fycip.xf", "obnvpcizpv@cxiiuonrdl.clo", "dgqm.jadsqn@ahkyxmtmnyiztjcwprxjqft.zr", "jieizhihgfkrgyl@wzzs.id", "ejtzrycz.yzzsvja@ljn.dic", "wniafuskopfmxap@rmzd.bv", "mhvwrrap.xxrvbmc@dmi.lsa", "damqrn.wcpx@gnbstsr.sjb", "qhpq.uaz@fjsziacw.lj", "z.tkvydugct@gmzjrsb.ig", "gsrywjpod-mwjaay@ivwreh.ra", "f.dlycyzs@wphespstrbw.fq",
      "ukoy.swmljr@dobpupdj.tcs", "p-iuwsnvwlm@xbupbjay.ki", "fwdwmxwluf.oqpwkay@oodepmumc.bd", "dchtqz.slan@fuooorg.ntf", "tqrtk-jtbjgxj@jvyjf.dx", "qyigizezvy.jtqapul@gmrlpqvko.ed", "rlted.yylreysl@nzkzh-xmnnymp.la", "yeaqgeiutc.soxl@hehuhka.pa", "unngp.wekvcbpi@yuniw-hlqbbmm.ti", "hfgao-wfranj@bpan.vaq", "cqfhrbr12@wmjiamyz.xp", "cqbbtoupgboponi@aiel.pz", "rowrxmmcfjumnk@dgwnoa.th", "dgqmj-adsqna@hkyx.mtm", "hmncewiycwcpr@tuosdngef.hdf", "x.omfrsarjr@hvbbttw.mg", "q.bmudn@spjcfnswtrhm.dg",
      "pobxioycnwppx@jibslslol.nul", "z.gvsfred@essukicewps.oo", "m-bnvbqxjvn@zhytultw.eq", "c.pxwvayyeh@qwtaecs.mi", "jrmslhdnvzbgpbtmyzn@tmn.qsi", "udbrfinn@gnmheijytej.uy", "cqbbtoupgb.opon@iaielpz.hh", "vuuhtishr-twtugb@iveoip.nr", "shkkk.ynebfee@qerpmi.dtz", "x.dwlvgbnya@retkedx.gf", "xomfr.sarjrhv@bbttwm.gbs", "liei.nst@xamnipnl.zt", "nxjhhdi52@jcnxxrox.qn", "o.vjzxobjpg@idacqul.xz", "d.amqrnwc@pxgnbstsrsj.bf", "mvofhrnkwtdgkavpivd@yud.grn", "gtzjr-kivgbsw@oagec.iy",
      "oeqikpw41@ayrjeykk.xu", "y-kdmyspktr@migrkyjo.mh", "tuvhfxhrxl@tdkrcfbjon.bpl", "jcbmhtbrgp.qxxsicx@fokbvaops.zb", "hmncewiycw.cprt@uosdnge.fh", "webqt.tyezupxo@xibnz-ipmruxw.zu", "h.onkbw@axphapydumyq.ap", "fflg.zyr@siafezzs.rw", "f.gpmwl@nikudytmwpnh.nt", "zuobwrrx.dneguwv@qsb.jtj", "xrqtlgohx-vdpdyw@pstwpy.rs", "g-yzzlksufv@oxctganj.zg", "amyolpvjyppf@cc.fbsd.vgf.pwjn.ci", "vlnxghxb@dzgflduswet.xh", "d.tepbm@twoonzzylzyg.cz", "dbtjvgtbb-uirhbw@fkwgeq.fp", "muvqo.krjvfhk@jbzevl.msi",
      "d.smaifyvna@sdxzppm.wl", "bvvtn.vymisja@juygfa.frh", "bsrg.shj@wunngixu.kj", "pjapwgs66@jzfazewt.ch", "g.xgksdwuei@sbbtjpb.zi", "y.fhjkxft@fzusdvkraye.nl", "ucnhjpoegupfxwqmxnv@tuv.fwy", "kxaev-mvjqdao@ajdxy.xd", "njbpivh10@cwpqlftz.hh", "b-rmauunftt@pnokqmsb.ly", "fesrfrvrhm@jiyzvgketi.bvj", "ilumyaxnvr.jjaixga@owvgdopqh.gl", "xrsgrlophy.haou@veveqnz.hq", "jkqha.nbwuwlpg@vtijv-sqxbyrz.hb", "f.bodke@hbtinoxdrkoy.cu", "ekvn.xec@hicdcgfz.am", "r.necef@rbewyqlkhvjd.xu",
      "nsnfsjyb.sqlxubq@ioa.ghq", "oxuxyvuzc-xibazy@fvuzwt.tb", "s-fnqtdvnzx@kpurrhjf.jh", "vvbawbogdqyx@aq.evoi.kla.ffvx.cg", "vpxeeniq@cbedskbbmog.ox", "r.sdtyf@badruqzdgrve.yn",
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
      wl( "Available processors (cores): " + Runtime.getRuntime().availableProcessors() );

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

  private static String  m_static_laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";

  private static Pattern m_pattern          = null;

  private static boolean isValidEmailAddresseFkt1( String email )
  {
    /*
     * matt.writes.code
     */
//    String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
//
//    String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
//
//    String emailRegex = stricterFilter ? stricterFilterString : laxString;

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
//      String stricterFilterString = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
//
//      String laxString = ".+@.+\\.[A-Za-z]{2}[A-Za-z]*";
//
//      String emailRegex = stricterFilter ? stricterFilterString : laxString;

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

  /*
   * David Silva Smith
   *  string validEmailPattern = @"^(?!\.)(""([^""\r\\]|\\[""\r\\])*""|"
            + @"([-a-z0-9!#$%&'*+/=?^_`{|}~]|(?<!\.)\.)*)(?<!\.)"
            + @"@[a-z0-9][\w\.-]*[a-z0-9]\.[a-z][a-z\.]*[a-z]$";
            
            
   */

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
    getStringBuffer().append( pString + "\n" );

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

      /*
       * Aufruf von "stream.flush()"
       */
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
