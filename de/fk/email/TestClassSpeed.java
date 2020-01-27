package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// import org.hazlewood.connor.bottema.emailaddress.EmailAddressValidator; // https://github.com/bbottema/email-rfc2822-validator
// import emailvalidator4j.EmailValidator; // https://github.com/egulias/EmailValidator4J

/**
 * Testclass: Speed an Accurancy
 * 
 * Feel free to add your testcases to the List, or replace the list at all.
 * 
 * A cool speed-test is only one eMail-Adresse "A@A.AA".
 * 
 * Yes, the variable names are all in german.
 * ... and I have no problem with that.
 */
class TestClassSpeed
{
  /*
   * Testdaten
   * Testdaten
   * 
   *     0 "A.B@C.DE"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *     1 "A."B"@C.DE"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     2 "A.B@[1.2.3.4]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *     3 "A."B"@[1.2.3.4]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *     4 "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *     5 "A."B"@[IPv6:1:2:3:4:5:6:7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *     6 "(A)B@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *     7 "A(B)@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *     8 "(A)"B"@C.DE"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *     9 ""A"(B)@C.DE"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    10 "(A)B@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    11 "A(B)@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    12 "(A)"B"@[1.2.3.4]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    13 ""A"(B)@[1.2.3.4]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    14 "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    15 "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    16 "(A)"B"@[IPv6:1:2:3:4:5:6:7:8]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    17 ""A"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    18 "firstname.lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    19 "firstname+lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    20 "firstname-lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    21 "first-name-last-name@d-a-n.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    22 "a.b.c.d@domain.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    23 "1@domain.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    24 "a@domain.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    25 "email@domain.co.de"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    26 "email@domain.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    27 "email@subdomain.domain.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    28 "2@bde.cc"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    29 "-@bde.cc"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    30 "a2@bde.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    31 "a-b@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    32 "ab@b-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    33 "a+b@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    34 "f.f.f@bde.cc"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    35 "ab_c@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    36 "_-_@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    37 "w.b.f@test.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    38 "w.b.f@test.museum"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    39 "a.a@test.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    40 "ab@288.120.150.10.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    41 "ab@[120.254.254.120]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    42 "1234567890@domain.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    43 "john.smith@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    44 null                                                                                  Jmail EXP     REGEXP 1 EXP     REGEXP 2 EXP     REGEXP 3 EXP     REGEXP 4 false   JAVA 1 EXP     ea234 false    = 10 = Laenge: Eingabe ist null
   *    45 ""                                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 11 = Laenge: Eingabe ist Leerstring
   *    46 "        "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    47 "ABCDEFGHIJKLMNOP"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    48 "ABC.DEF.GHI.JKL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *    49 "@GHI.JKL"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    50 "ABC.DEF@"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 27 = AT-Zeichen: kein AT-Zeichen am Ende
   *    51 "ABC.DEF@@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    52 "@%^%#$@#$@#.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    53 "@domain.com"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    54 "ABC.DEF@ GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *    55 "ABC.DEF @GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    56 "ABC.DEF @ GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    57 "@"                                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *    58 "@.@.@."                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    59 "@.@.@GHI.JKL"                                                                        Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    60 "@@@GHI.JKL"                                                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    61 "ABC.DEF@.@.@GHI.JKL"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    62 "email.domain.com"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *    63 "email@domain@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    64 "ABCDEF@GHIJKL"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    65 "ABC.DEF@GHIJKL"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    66 ".ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *    67 "ABC.DEF@GHI.JKL."                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *    68 "ABC..DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    69 "ABC.DEF@GHI..JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    70 "ABC.DEF@GHI.JKL.."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    71 "ABC.DEF.@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *    72 "ABC.DEF@.GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    73 "ABC.DEF@."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    74 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    75 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    76 "..........@domain."                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *    77 "ABC1.DEF2@GHI3.JKL4"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    78 "ABC.DEF_@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    79 "#ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    80 "ABC.DEF@GHI.JK2"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    81 "ABC.DEF@2HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    82 "ABC.DEF@GHI.2KL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *    83 "ABC.DEF@GHI.JK-"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    84 "ABC.DEF@GHI.JK_"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    85 "ABC.DEF@-HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    86 "ABC.DEF@_HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    87 "ABC DEF@GHI.DE"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    88 "ABC.DEF@GHI DE"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *    89 "A . B & C . D"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    90 " A . B & C . D"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    91 "(?).[!]@{&}.<:>"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *    92 "&local&&name&with&$@amp.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    93 "*local**name*with*@asterisk.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    94 "$local$$name$with$@dollar.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    95 "=local==name=with=@equality.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    96 "!local!!name!with!@exclamation.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    97 "`local``name`with`@grave-accent.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    98 "#local##name#with#@hash.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    99 "-local--name-with-@hypen.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   100 "{local{name{{with{@leftbracket.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   101 "%local%%name%with%@percentage.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   102 "|local||name|with|@pipe.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   103 "+local++name+with+@plus.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   104 "?local??name?with?@question.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   105 "}local}name}}with}@rightbracket.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   106 "~local~~name~with~@tilde.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   107 "^local^^name^with^@xor.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   108 "_local__name_with_@underscore.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   109 ":local::name:with:@colon.com"                                                        Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   110 "domain.part@with-hyphen.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   111 "domain.part@with_underscore.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   112 "domain.part@-starts.with.hyphen.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   113 "domain.part@ends.with.hyphen.com-"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   114 "domain.part@with&amp.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   115 "domain.part@with*asterisk.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   116 "domain.part@with$dollar.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   117 "domain.part@with=equality.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   118 "domain.part@with!exclamation.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   119 "domain.part@with?question.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   120 "domain.part@with`grave-accent.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   121 "domain.part@with#hash.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   122 "domain.part@with%percentage.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   123 "domain.part@with|pipe.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   124 "domain.part@with+plus.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   125 "domain.part@with{leftbracket.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   126 "domain.part@with}rightbracket.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   127 "domain.part@with(leftbracket.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   128 "domain.part@with)rightbracket.com"                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   129 "domain.part@with[leftbracket.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   130 "domain.part@with]rightbracket.com"                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   131 "domain.part@with~tilde.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   132 "domain.part@with^xor.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   133 "domain.part@with:colon.com"                                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   134 "DomainHyphen@-atstart"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   135 "DomainHyphen@atend-.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   136 "DomainHyphen@bb.-cc"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   137 "DomainHyphen@bb.-cc-"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   138 "DomainHyphen@bb.cc-"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   139 "DomainNotAllowedCharacter@/atstart"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   140 "DomainNotAllowedCharacter@a,start"                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   141 "DomainNotAllowedCharacter@atst\art.com"                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   142 "DomainNotAllowedCharacter@exa\mple"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   143 "DomainNotAllowedCharacter@example'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   144 "DomainNotAllowedCharacter@100%.de'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   145 "domain.starts.with.digit@2domain.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   146 "domain.ends.with.digit@domain2.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   147 "tld.starts.with.digit@domain.2com"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   148 "tld.ends.with.digit@domain.com2"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   149 "email@=qowaiv.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   150 "email@plus+.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   151 "email@domain.com>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   152 "email@mailto:domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   153 "mailto:mailto:email@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   154 "email@-domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   155 "email@domain-.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   156 "email@domain.com-"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   157 "email@{leftbracket.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   158 "email@rightbracket}.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   159 "email@pp|e.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   160 "email@domain.domain.domain.com.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   161 "email@domain.domain.domain.domain.com.com"                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   162 "email@domain.domain.domain.domain.domain.com.com"                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   163 "unescaped white space@fake$com"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   164 ""Joe Smith email@domain.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   165 ""Joe Smith' email@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   166 ""Joe Smith"email@domain.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   167 "Joe Smith &lt;email@domain.com&gt;"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   168 "{john'doe}@my.server"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   169 "email@domain-one.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   170 "_______@domain.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   171 "?????@domain.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   172 "local@?????.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   173 ""B3V3RLY H1LL$"@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   174 ""-- --- .. -."@sh.de"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   175 "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   176 ""\" + \"select * from user\" + \""@example.de"                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   177 "#!$%&'*+-/=?^_`{}|~@eksample.org"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   178 "eksample@#!$%&'*+-/=?^_`{}|~.org"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   179 "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   180 ""()<>[]:,;@\\\"!#$%&'*+-/=?^_`{}| ~.a"@example.org"                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   181 "ABC.DEF@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   182 "ABC.DEF@[001.002.003.004]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   183 ""ABC.DEF"@[127.0.0.1]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *   184 "ABC.DEF[1.2.3.4]"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   185 "[1.2.3.4]@[5.6.7.8]"                                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   186 "ABC.DEF[@1.2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   187 ""[1.2.3.4]"@[5.6.7.8]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   188 "ABC.DEF@MyDomain[1.2.3.4]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   189 "ABC.DEF@[1.00002.3.4]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   190 "ABC.DEF@[1.2.3.456]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   191 "ABC.DEF@[..]"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   192 "ABC.DEF@[.2.3.4]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   193 "ABC.DEF@[]"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   194 "ABC.DEF@[1]"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   195 "ABC.DEF@[1.2]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   196 "ABC.DEF@[1.2.3]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   197 "ABC.DEF@[1.2.3.4.5]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *   198 "ABC.DEF@[1.2.3.4.5.6]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *   199 "ABC.DEF@[MyDomain.de]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   200 "ABC.DEF@[1.2.3.]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *   201 "ABC.DEF@[1.2.3. ]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   202 "ABC.DEF@[1.2.3.4].de"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   203 "ABC.DE@[1.2.3.4][5.6.7.8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   204 "ABC.DEF@[1.2.3.4"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   205 "ABC.DEF@1.2.3.4]"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   206 "ABC.DEF@[1.2.3.Z]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   207 "ABC.DEF@[12.34]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   208 "ABC.DEF@[1.2.3.4]ABC"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   209 "ABC.DEF@[1234.5.6.7]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   210 "ABC.DEF@[1.2...3.4]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   211 "email@[123.123.123.123]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   212 "email@111.222.333"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   213 "email@111.222.333.256"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   214 "email@[123.123.123.123"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   215 "email@[123.123.123].123"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   216 "email@123.123.123.123]"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   217 "email@123.123.[123.123]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   218 "ab@988.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   219 "ab@120.256.256.120"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   220 "ab@120.25.1111.120"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   221 "ab@[188.120.150.10"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   222 "ab@188.120.150.10]"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   223 "ab@[188.120.150.10].com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   224 "ab@188.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   225 "ab@1.0.0.10"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   226 "ab@120.25.254.120"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   227 "ab@01.120.150.1"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   228 "ab@88.120.150.021"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   229 "ab@88.120.150.01"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   230 "email@123.123.123.123"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   231 "ABC.DEF@[IPv6:2001:db8::1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   232 "ABC.DEF@[IP"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   233 "ABC.DEF@[IPv6]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   234 "ABC.DEF@[IPv6:]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   235 "ABC.DEF@[IPv6:1]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   236 "ABC.DEF@[IPv6:1:2]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   237 "ABC.DEF@[IPv6:1:2:3]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   238 "ABC.DEF@[IPv6:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   239 "ABC.DEF@[IPv6:1:2:3:4:5:]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   240 "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   241 "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   242 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   243 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   244 "ABC.DEF@[IPv4:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   245 "ABC.DEF@[I127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   246 "ABC.DEF@[D127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   247 "ABC.DEF@[iPv6:2001:db8::1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   248 "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   249 "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   250 "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   251 "ABC.DEF@[IPv6:12:34]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   252 "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   253 "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   254 "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   255 "ABC.DEF@[IPv6:1:2:3:4:5:6"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   256 "ABC.DEF@[IPv6:12345:6:7:8:9]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   257 "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   258 "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   259 "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   260 "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   261 "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   262 "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   263 "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   264 "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   265 "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   266 "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   267 "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   268 "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   269 "ABC.DEF@[::FFFF:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   270 "ABC.DEF@[::ffff:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   271 "ABC.DEF@[IPv6::ffff:.127.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   272 "ABC.DEF@[IPv6::fff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   273 "ABC.DEF@[IPv6::1234:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   274 "ABC.DEF@[IPv6:127.0.0.1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   275 "ABC.DEF@[IPv6:::127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   276 ""ABC.DEF"@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   277 ""ABC DEF"@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   278 ""ABC@DEF"@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   279 ""ABC DEF@G"HI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   280 """@GHI.DE"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   281 ""ABC.DEF@G"HI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   282 "A@G"HI.DE"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   283 ""@GHI.DE"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   284 "ABC.DEF.""                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   285 "ABC.DEF@"""                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   286 "ABC.DEF@G"HI.DE"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   287 "ABC.DEF@GHI.DE""                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   288 "ABC.DEF@"GHI.DE"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   289 ""Escape.Sequenz.Ende""                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   290 "ABC.DEF"GHI.DE"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   291 "ABC.DEF"@GHI.DE"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   292 "ABC.DE"F@GHI.DE"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   293 ""ABC.DEF@GHI.DE"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   294 ""ABC.DEF@GHI.DE""                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   295 "".ABC.DEF"@GHI.DE"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   296 ""ABC.DEF."@GHI.DE"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   297 ""ABC".DEF."GHI"@JKL.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   298 "A"BC".DEF."GHI"@JKL.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   299 ""ABC".DEF.G"HI"@JKL.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   300 ""AB"C.DEF."GHI"@JKL.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   301 ""ABC".DEF."GHI"J@KL.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   302 ""AB"C.D"EF"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   303 ""Ende.am.Eingabeende""                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   304 "0"00.000"@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   305 ""Joe Smith" email@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   306 ""Joe\tSmith" email@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   307 ""Joe"Smith" email@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   308 "(ABC)DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   309 "(ABC) DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   310 "ABC(DEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   311 "ABC.DEF@GHI.JKL(MNO)"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   312 "ABC.DEF@GHI.JKL      (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   313 "ABC.DEF@             (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   314 "ABC.DEF@   .         (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   315 "ABC.DEF              (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   316 "ABC.DEF@GHI.         (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   317 "ABC.DEF@GHI.JKL       MNO"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   318 "ABC.DEF@GHI.JKL          "                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   319 "ABC.DEF@GHI.JKL       .  "                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   320 "ABC.DEF@GHI.JKL ()"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   321 "ABC.DEF@GHI.JKL()"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   322 "ABC.DEF@()GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   323 "ABC.DEF()@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   324 "()ABC.DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   325 "(ABC).DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   326 "ABC.(DEF)@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 101 = Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   327 "ABC.DEF@(GHI).JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   328 "ABC.DEF@GHI.(JKL).MNO"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 102 = Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   329 "ABC.DEF@GHI.JK(L.M)NO"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   330 "AB(CD)EF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   331 "AB.(CD).EF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 101 = Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   332 "AB."(CD)".EF@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   333 "(ABCDEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 98 = Kommentar: Kein lokaler Part vorhanden
   *   334 "(ABCDEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   335 "(AB"C)DEF@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   336 "(AB\C)DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   337 "(AB\@C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   338 "ABC(DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   339 "ABC.DEF@GHI)JKL"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   340 ")ABC.DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   341 "ABC(DEF@GHI).JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   342 "ABC(DEF.GHI).JKL"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   343 "(ABC.DEF@GHI.JKL)"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 95 = Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   344 "(A(B(C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   345 "(A)B)C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   346 "(A)BCDE(F)@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   347 "ABC.DEF@(GH)I.JK(LM)"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   348 "ABC.DEF@(GH(I.JK)L)M"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   349 "ABC.DEF@(comment)[1.2.3.4]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   350 "ABC.DEF@(comment) [1.2.3.4]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 106 = Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
   *   351 "ABC.DEF@[1.2.3.4](comment)"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   352 "ABC.DEF@[1.2.3.4]    (comment)"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   353 "ABC.DEF@[1.2.3(comment).4]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   354 "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   355 "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   356 "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   357 "(comment)john.smith@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   358 "john.smith(comment)@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   359 "john.smith@(comment)example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   360 "john.smith@example.com(comment)"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   361 "john.smith@exampl(comment)e.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   362 "john.s(comment)mith@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   363 "john.smith(comment)@(comment)example.com"                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   364 "john.smith(com@ment)example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   365 "email( (nested) )@plus.com"                                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   366 "email)mirror(@plus.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   367 "email@plus.com (not closed comment"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   368 "email(with @ in comment)plus.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   369 "email@domain.com (joe Smith)"                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   370 "ABC DEF <ABC.DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   371 "<ABC.DEF@GHI.JKL> ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   372 "ABC DEF ABC.DEF@GHI.JKL>"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   373 "<ABC.DEF@GHI.JKL ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 17 = Struktur: keine schliessende eckige Klammer gefunden.
   *   374 ""ABC DEF "<ABC.DEF@GHI.JKL>"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   375 ""ABC<DEF>"@JKL.DE"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   376 ">"                                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   377 ""ABC<DEF@GHI.COM>"@JKL.DE"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   378 "ABC DEF <ABC.<DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   379 "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   380 "ABC DEF <ABC.DEF@GHI.JKL"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   381 "ABC.DEF@GHI.JKL> ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   382 "ABC DEF >ABC.DEF@GHI.JKL<"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   383 ">ABC.DEF@GHI.JKL< ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   384 "ABC DEF <A@A>"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   385 "<A@A> ABC DEF"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   386 "ABC DEF <>"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   387 "<> ABC DEF"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   388 "<ABC.DEF@GHI.JKL>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   389 "Non EMail part <(comment)Local."Part"@[IPv6::ffff:127.0.0.1]>"                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   390 "Non EMail part <Local."Part"(comment)@[IPv6::ffff:127.0.0.1]>"                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   391 "<(comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part"                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   392 "<Local."Part"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   393 "Non EMail part < (comment)Local."Part"@[IPv6::ffff:127.0.0.1]>"                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   394 "Non EMail part <Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]>"                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   395 "< (comment)Local."Part"@[IPv6::ffff:127.0.0.1]> Non EMail part"                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   396 "<Local."Part"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   397 "Joe Smith <email@domain.com>"                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   398 "Joe Smith <mailto:email@domain.com>"                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   399 "Test |<gaaf <email@domain.com>"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   400 ""With extra < within quotes" Display Name<email@domain.com>"                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   401 "A@B.CD"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   402 "A@B.C"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   403 "A@COM"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   404 "a@bde.c-c"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   405 "MailTo:casesensitve@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   406 "mailto:email@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   407 "email@domain"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   408 "Joe Smith <mailto:email(with comment)@domain.com>"                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   409 "..@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   410 ".a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   411 "ab@sd@dd"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   412 ".@s.dd"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   413 "a@b.-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   414 "a@bde-.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   415 "a@b._de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   416 "a@bde_.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   417 "a@bde.cc."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   418 "ab@b+de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   419 "a..b@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   420 "_@bde.cc,"                                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   421 "plainaddress"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   422 "plain.address"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   423 ".email@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   424 "email.@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   425 "email..email@domain.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   426 "email@.domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   427 "email@domain.com."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   428 "email@domain..com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   429 "Display Name <email@plus.com> (after name with display)"                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   430 "k.haak@12move.nl"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   431 "K.HAAK@12MOVE.NL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   432 "aap.123.noot.mies@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   433 "email@domain.co.jp"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   434 "firstname-lastname@d.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   435 "FIRSTNAME-LASTNAME@d--n.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   436 "email@p|pe.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   437 "isis@100%.nl"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   438 "email@dollar$.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   439 "email@r&amp;d.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   440 "email@#hash.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   441 "email@wave~tilde.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   442 "email@exclamation!mark.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   443 "email@question?mark.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   444 "email@obelix*asterisk.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   445 "email@grave`accent.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   446 "email@colon:colon.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   447 "email@caret^xor.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   448 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   449 "Joe.\\Blow@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   450 "Lat%ss\rtart%s@test.local.part"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   451 "ABC.DEF@GHI.JKL       (MNO)"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   452 "(A)(B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   453 "(A))B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   454 "(ABC DEF) <ABC.DEF@GHI.JKL>"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   455 "(comment and stuff)joe@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   456 "UnclosedComment@a(comment"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   457 "at(start)test@test.local.part"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   458 "example(comment)@test.local.part"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   459 "joe(comment and stuff)@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   460 "joe(fail me)smith@gmail.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   461 "joe@(comment and stuff)gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   462 "joe@gmail.com(comment and stuff"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   463 "joe@gmail.com(comment and stuff)"                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   464 "joesmith@gma(fail me)il.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   465 " a @ []"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   466 " a @[ ] "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   467 " a @[ ]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   468 " a @[] "                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   469 " a @[]"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   470 " a @a[].com "                                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   471 "ABC.DEF@[ .2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   472 "ABC.DEF@[0.0.0.0]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   473 "ABC.DEF@[000.000.000.000]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   474 "ABC.DEF@[001.012.123.255]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   475 "ABC.DEF@[1..123.255]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   476 "ABC.DEF@[1.12.123.255]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   477 "ABC.DEF@[1.12.123.255].de"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   478 "ABC.DEF@[1.12.123.259]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   479 "ABC.DEF@[1.123.25.]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *   480 "ABC.DEF@[1.2.3.4] "                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   481 "ABC.DEF@[1.2.3.4][5.6.7.8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   482 "ABC.DEF@[1.2.]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   483 "ABC.DEF@[1:2:3:4:5:6:7:8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   484 "ABC.DEF@[::ffff:127.0:0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   485 "ABC.DEF@[IPv61:2:3:4:5:6]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   486 "ABC.DEF@[IPv6:00:ffff:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   487 "ABC.DEF@[IPv6:0:0:ffff:127.0.0.1]"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   488 "ABC.DEF@[IPv6:12:ffff:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   489 "ABC.DEF@[IPv6:1:2:(3::5):6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   490 "ABC.DEF@[IPv6:1:2:(3::5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   491 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8 ]"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   492 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   493 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   494 "ABC.DEF@[IPv6:1:2:3:4:5:6] "                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   495 "ABC.DEF@[IPv6:1:2:3:4:5]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   496 "ABC.DEF@[IPv6:1:2:3::5:8].de"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   497 "ABC.DEF@[IPv6:1:2:ffff:127.0.0.1]"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   498 "ABC.DEF@[IPv6::abcd:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   499 "ABC.DEF@[IPv6::fff:999.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   500 "ABC.DEF@[IPv6::ffff:.127.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   501 "ABC.DEF@[IPv6::ffff:1211.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   502 "ABC.DEF@[IPv6::ffff:12111.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   503 "ABC.DEF@[IPv6::ffff:127.0:0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   504 "ABC.DEF@[IPv6::ffff:127A.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   505 "ABC.DEF@[IPv6::ffff:999.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   506 "ABC.DEF@[IPv6::ffff:fff.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   507 "ABC.DEF@[IPv6::fffff:127.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   508 "ABC.DEF@[IPv6:a:b:c:d:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   509 "ABC.DEF@[IPv6:a:b:c:d:e:f:1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   510 "ABC.DEF@[IPv6:ffff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   511 "ABC.DEF@[IPv6]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   512 "ABC.DEF@[[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   513 "ABC.DEF[1.12.123.255]"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   514 "ABC[@IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   515 "( a @[]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   516 "(ABC DEF) <(Comment)ABC.DEF@[iPv6:2001:db8::1]>"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   517 "ExpectedATEXT@at[start"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   518 "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   519 "Test.IPv6@[IPv6:1::1::1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   520 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]"                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   521 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   522 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]"                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   523 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]"                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   524 "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   525 "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   526 "Test.IPv6@[[127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   527 "Test.IPv6@[\n]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   528 ""ABC"@[IPv4:1:2:3:4]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   529 ""ABC"@[IPv6:1234:5678]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   530 ""ABC"@[IPv6:1234]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   531 ""ABC"@[IPv6:1:2:3:4:5:6:7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *   532 """@[]"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   533 "a"b(c)d,e:f;g<h>i[j\k]l@example.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   534 "jsmith@[IPv6:2001:db8::1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   535 "user@[192.168.2.1]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   536 "user@[IPv6:2001:db8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   537 "UnclosedDomainLiteral@example]"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   538 "ABC DEF <( )@ >"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   539 "ABC DEF <(COMMENT)A@ >"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   540 "ABC(DEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   541 "ABC.DEF@(GHI).JKL.MNO"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   542 "ABC.DEF@(GHI)JKL.MNO"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   543 ""@".A(@)@a.aa"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *   544 "ABC+DEF) <ABC.DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   545 ""ABC<DEF>"@GHI.JKL"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   546 ""ABC<DEF@GHI.COM>"@GHI.JKL"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   547 "<ABC.DEF@GHI.JKL>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   548 "ABC DEF < >"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   549 "ABC DEF < @ >"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   550 "ABC DEF <ABC<DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   551 "0"00.000"@wc.de"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   552 "@G"HI.DE"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   553 "ABC.DEF@GHI.JKL""                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   554 "ABC.DEF@"GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   555 "ABC.DEF"@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   556 "ABC.DEF"GHI.JKL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   557 "ABC.DE"F@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   558 "A"BC".DEF."GHI"@GHI.JKL"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   559 "DomainNotAllowedCharacter@example\"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   560 "" "@GHI.JKL"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   561 "" "@example.org"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   562 ""%2"@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   563 "".ABC.DEF"@GHI.JKL"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   564 ""@GHI.JKL"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   565 ""ABC DEF"@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   566 ""ABC.DEF."@GHI.JKL"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   567 ""ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   568 ""ABC.DEF"@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   569 ""ABC.D"EF@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   570 ""ABC@DEF"@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   571 ""ABC".DEF."GHI"@GHI.JKL"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   572 ""ABC"".DEF."GHI"@GHI.JKL"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   573 ""AB"C.DEF."GHI"@GHI.JKL"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   574 ""AB"C.D"EF@GHI.JKL"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   575 ""AB"C.D"EF"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   576 ""Falsch.da.Escape.Zeichen.am.Ende.steht\"                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 83 = String: Escape-Zeichen nicht am Ende der Eingabe
   *   577 ""Falsche\#Escape\GSequenz"@falsch.de"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   578 ""Gueltige\"Escape\\Sequenz"@korrekt.de"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   579 """@GHI.JKL"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   580 ""a..b"@gmail.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   581 ""a_b"@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   582 ""abcdefghixyz"@example.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   583 "abc."defghi".xyz@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   584 "abc"defghi"xyz@example.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   585 "abc\"def\"ghi@eggsample.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   586 "at"start"test@test.local.part"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   587 "just"not"right@example.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   588 "this is"not\allowed@example.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   589 "this\ still\"not\\allowed@example.com"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   590 "               "                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   591 "..@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   592 ".a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   593 "ab@sd@dd"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   594 ".@s.dd"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   595 "a@b.-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   596 "a@bde-.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   597 "a@b._de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   598 "a@bde_.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   599 "a@bde.cc."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   600 "ab@b+de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   601 "a..b@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   602 "_@bde.cc,"                                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   603 "plainaddress"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   604 "plain.address"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   605 ".email@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   606 "email.@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   607 "email..email@domain.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   608 "email@.domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   609 "email@domain.com."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   610 "email@domain..com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   611 "MailTo:casesensitve@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   612 "mailto:email@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   613 "email@domain"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   614 "someone@somewhere.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   615 "someone@somewhere.co.uk"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   616 "someone+tag@somewhere.net"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   617 "futureTLD@somewhere.fooo"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   618 "fdsa"                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   619 "fdsa@"                                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   620 "fdsa@fdsa"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   621 "fdsa@fdsa."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   622 "Foobar Some@thing.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   623 "david.jones@proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   624 "d.j@server1.proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   625 "jones@ms1.proseware.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   626 "j.@server1.proseware.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   627 "j@proseware.com9"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   628 "js#internal@proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   629 "j_9@[129.126.118.1]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   630 "j..s@proseware.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   631 "js*@proseware.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   632 "js@proseware..com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   633 "js@proseware.com9"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   634 "j.s@server1.proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   635 ""j\"s"@proseware.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   636 "cog@wheel.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   637 ""cogwheel the orange"@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   638 "123@$.xyz"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   639 "dasddas-@.com"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   640 "-asd@das.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   641 "as3d@dac.coas-"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   642 "dsq!a?@das.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   643 "_dasd@sd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   644 "dad@sds"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   645 "asd-@asd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   646 "dasd_-@jdas.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   647 "asd@dasd@asd.cm"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   648 "da23@das..com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   649 "_dasd_das_@9.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   650 "d23d@da9.co9"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   651 "dasd.dadas@dasd.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   652 "dda_das@das-dasd.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   653 "dasd-dasd@das.com.das"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   654 "@b.com"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   655 "a@.com"                                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   656 "a@bcom"                                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   657 "a.b@com"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   658 "a@b."                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   659 "ab@c.com"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   660 "a@bc.com"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   661 "a@b.com"                                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   662 "a@b.c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   663 "a+b@c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   664 "a@123.45.67.89"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   665 "%2@gmail.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   666 "_@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   667 "1@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   668 "1_example@something.gmail.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   669 "d._.___d@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   670 "d.oy.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   671 "d_oy_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   672 "doysmith@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   673 "D.Oy'Smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   674 "%20f3v34g34@gvvre.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   675 "piskvor@example.lighting"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   676 "--@ooo.ooo"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   677 "foo@bar@machine.subdomain.example.museum"                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   678 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   679 "foo.bar@machine.sub\@domain.example.museum"                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   680 "check@thiscom"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   681 "check@this..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   682 " check@this.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   683 "check@this.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   684 "Abc@example.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   685 "Abc@example.com."                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   686 "Abc@10.42.0.1"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   687 "user@localserver"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   688 "Abc.123@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   689 "user+mailbox/department=shipping@example.com"                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   690 "Loïc.Accentué@voilà.fr"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   691 "user@[IPv6:2001:DB8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   692 "Abc.example.com"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   693 "A@b@c@example.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   694 "this\ still\"not\allowed@example.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   695 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   696 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   697 "email@example.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   698 "email@example.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   699 "email@mail.gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   700 "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   701 "email@example.co.uk."                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   702 "email@example"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   703 " email@example.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   704 "email@example.com "                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   705 "email@example,com "                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   706 "invalid.email.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   707 "invalid@email@domain.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   708 "email@example..com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   709 "yoursite@ourearth.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   710 "my.ownsite@ourearth.org"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   711 "mysite@you.me.net"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   712 "xxxx@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   713 "xxxxxx@yahoo.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   714 "xxxx.ourearth.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   715 "xxxx@.com.my"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   716 "@you.me.net"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   717 "xxxx123@gmail.b"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   718 "xxxx@.org.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   719 ".xxxx@mysite.org"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   720 "xxxxx()*@gmail.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   721 "xxxx..1234@yahoo.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   722 "alex@example.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   723 "alireza@test.co.uk"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   724 "peter.example@yahoo.com.au"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   725 "peter_123@news.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   726 "hello7___@ca.com.pt"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   727 "example@example.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   728 "hallo@example.coassjj#sswzazaaaa"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   729 "hallo2ww22@example....caaaao"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   730 "abcxyz123@qwert.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   731 "abc123xyz@asdf.co.in"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   732 "abc1_xyz1@gmail1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   733 "abc.xyz@gmail.com.in"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   734 "pio_pio@factory.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   735 "~pio_pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   736 "pio_~pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   737 "pio_#pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   738 "pio_pio@#factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   739 "pio_pio@factory.c#om"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   740 "pio_pio@factory.c*om"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   741 "pio^_pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   742 ""Abc\@def"@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   743 ""Fred Bloggs"@example.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   744 ""Fred\ Bloggs"@example.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   745 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   746 ""Joe\Blow"@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   747 ""Joe\\Blow"@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   748 ""Abc@def"@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   749 "customer/department=shipping@example.com"                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   750 "\$A12345@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   751 "!def!xyz%abc@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   752 "_somename@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   753 "abc\"def\"ghi@example.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   754 " joe@gmail.com"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   755 "$ABCDEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   756 ".ann..other.@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   757 "000.000@000.de"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   758 "0test@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   759 "1234567890@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   760 "1234@5678.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   761 "1234@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   762 "A@A.AA"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   763 "ABC DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   764 "ABC-DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   765 "ABC/DEF=GHI@JKL.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   766 "ABCDEFGHIJKLMNO"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   767 "ABC\@DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   768 "AT-Character"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   769 "ATEXTAfterCFWS@test\r\n at"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   770 "Abc\@def@ecksample.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   771 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   772 "CRLFAtEnd@test\r\nat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   773 "CRWithoutLF@test\rat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   774 "ConsecutiveAT@@start"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   775 "ConsecutiveCRLF@test\r\n\r\nat"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   776 "ConsecutiveDots@at..start"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   777 "DomainHyphen@atstart-.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   778 "DotAtStart@.atstart"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   779 "Drei*Vier@Ist.Zwoelf.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   780 "ExpectedATEXT@;atstart"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   781 "ExpectedCTEXT@test\r\n \n"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   782 "Length"                                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   783 "No Input"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   784 "Pointy Brackets"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   785 "Seperator"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   786 "Strings"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   787 "TEST.TEST@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   788 "TEST@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   789 "Test.Domain.Part@1leadingnumber.example.co"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   790 "Test.Domain.Part@1leadingnumber.example.com"                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   791 "Test.Domain.Part@aampleEx.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   792 "Test.Domain.Part@example.co"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   793 "Test.Domain.Part@has-hyphen.example.co"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   794 "Test.Domain.Part@has-hyphen.example.com"                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   795 "Test.Domain.Part@subdomain.example.co"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   796 "Test.Domain.Part@subdomain.example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   797 "Test.Test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   798 "_______@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   799 "admin@mailserver1"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   800 "alirheza@test.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   801 "ann.other@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   802 "d@@.com"                                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   803 "email@192.0.2.123"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   804 "email@example,com"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   805 "email@example.name .name"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   806 "example@localhost"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   807 "example@xyz.solutions"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   808 "fdsa@fdza"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   809 "fdsa@fdza."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   810 "joe@gmail.com "                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   811 "jsmith@whizbang.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   812 "jsr@prhoselware.com9"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   813 "lots.of.iq@sagittarius.A*"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   814 "me+100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   815 "me-100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   816 "me-100@yahoo-test.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   817 "me..2002@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   818 "me.100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   819 "me.@gmail.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   820 "me12345@that.is"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   821 "me123@%*.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   822 "me123@%.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   823 "me123@.com"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   824 "me123@.com.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   825 "me@.com.my"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   826 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   827 "me@me.co.uk"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   828 "me@me@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   829 "me@yahoo.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   830 "peter_123@news24.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   831 "prettyandsimple@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   832 "test test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   833 "test%test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   834 "test+test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   835 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   836 "test.test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   837 "test0@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   838 "test@GMAIL.COM"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   839 "test@Gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   840 "test@anothersub.sub-domain.gmail.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   841 "test@gmail"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   842 "test@gmail."                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   843 "test@gmail..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   844 "test@gmail.Com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   845 "test@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   846 "test@sub-domain.000.0"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   847 "test@sub-domain.000.0rg"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   848 "test@sub-domain.000.co-om"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   849 "test@sub-domain.000.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   850 "test@sub-domain.gmail.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   851 "test@subdomain.gmail.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   852 "test@test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   853 "test_test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   854 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   855 "user@com"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   856 "user@localhost"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   857 "very.common@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   858 "x._._y__z@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   859 "x.yz.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   860 "x_yz_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   861 "xyz@blabla.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   862 "nolocalpart.com"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   863 "test@example.com test"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   864 "user  name@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   865 "user   name@example.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   866 "example.@example.co.uk"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   867 "example@example@example.co.uk"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   868 "(test_exampel@example.fr}"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   869 "example(example)example@example.co.uk"                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   870 ".example@localhost"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   871 "ex\ample@localhost"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   872 "example@local\host"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   873 "example@localhost."                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   874 "user name@example.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   875 "username@ example . com"                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   876 "example@(fake}.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   877 "example@(fake.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   878 "username@example,com"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   879 "usern,ame@example.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   880 "user[na]me@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   881 """"@iana.org"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   882 ""\"@iana.org"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   883 ""test"test@iana.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   884 ""test""test"@iana.org"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   885 ""test"."test"@iana.org"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   886 ""test".test@iana.org"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   887 ""test\"@iana.org"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   888 "\r\ntest@iana.org"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   889 "\r\n test@iana.org"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   890 "\r\n \r\ntest@iana.org"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   891 "\r\n \r\ntest@iana.org"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   892 "\r\n \r\n test@iana.org"                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   893 "test@iana.org \r\n"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   894 "test@iana.org \r\n "                                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   895 "test@iana.org \r\n \r\n"                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   896 "test@iana.org \r\n\r\n"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   897 "test@iana.org  \r\n\r\n "                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   898 "test@iana/icann.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   899 "test@foo;bar.com"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   900 "a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   901 "comment)example@example.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   902 "comment(example))@example.com"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   903 "example@example)comment.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   904 "example@example(comment)).com"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   905 "example@[1.2.3.4"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   906 "example@[IPv6:1:2:3:4:5:6:7:8"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   907 "exam(ple@exam).ple"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   908 "example@(example))comment.com"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   909 "example@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   910 "example@example.co.uk"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   911 "example_underscore@example.fr"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   912 "example@localhost"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   913 "exam'ple@example.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   914 "exam\ ple@example.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   915 "example((example))@fakedfake.co.uk"                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   916 "example@faked(fake).co.uk"                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   917 "example+@example.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   918 "example@with-hyphen.example.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   919 "with-hyphen@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   920 "example@1leadingnum.example.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   921 "1leadingnum@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   922 "????@??????.??"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   923 ""username"@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   924 ""user,name"@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   925 ""user name"@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   926 ""user@name"@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   927 ""\a"@iana.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   928 ""test\ test"@iana.org"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   929 """@iana.org"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   930 """@[]"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   931 ""\""@iana.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   932 "Ärger.Öde@Übel.de"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   933 "Smørrebrød@danmark.dk"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   934 "ip.without.brackets@1.2.3.4"                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   935 "ip.without.brackets@1:2:3:4:5:6:7:8"                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   936 "(space after comment) john.smith@example.com"                                        Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   937 "email.address.without@topleveldomain"                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   938 "EmailAddressWithout@PointSeperator"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   939 ""use of komma, in double qoutes"@RFC2822.com"                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   940 "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   941 "ABC.DEF@[1.2.3.4][5.6.7.8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   942 "ABC.DEF@[][][][]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   943 "ABC.DEF@[....]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   944 null                                                                                  Jmail EXP     REGEXP 1 EXP     REGEXP 2 EXP     REGEXP 3 EXP     REGEXP 4 false   JAVA 1 EXP     ea234 false    = 10 = Laenge: Eingabe ist null
   *   945 ""                                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 11 = Laenge: Eingabe ist Leerstring
   *   946 "    "                                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   947 "ABC.DEF@GHI.J"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   948 "email@domain.topleveldomain"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   949 "unknown errors"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   950 "local@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   951 "MailTo:casesensitve@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   952 "mailto:email@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   953 "ab@188.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   954 "ab@1.0.0.10"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   955 "ab@120.25.254.120"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   956 "ab@01.120.150.1"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   957 "ab@88.120.150.021"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   958 "ab@88.120.150.01"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   959 "email@123.123.123.123"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 true    JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   960 "email@domain"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   961 "unsorted"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   962 "..@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   963 ".a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   964 "ab@sd@dd"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   965 ".@s.dd"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   966 "withdot.@test.local.part"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   967 "domain.starts.with.digit@2domain.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   968 "domain.ends.with.digit@domain2.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   969 "aHZs...Ym8iZXJn@YWRtAW4g.au"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   970 ""RmF0aGlh"@SXp6YXRp.id"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   971 ""hor\ror"@nes.si"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   972 "!yoora@an.yang.ha.se.yo"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   973 "084105111046077097110105107@hello.again.id"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   974 "@@@@@@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   975 "somecahrs@xyz.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   976 "user@host.network"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   977 "bob.mcspam@ABCD.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   978 "something@domain.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   979 "emailString@email.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   980 "person@registry.organization"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   981 "foo.bar."bux".bar.com@baz.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   982 "someStringThatMightBe@email.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   983 "100$-30%=130$-(x*3pi)@MATH.MAGIC"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   984 ""much.more unusual"@example.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   985 "other.email-with-dash@eksample.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   986 "Find#Me@NotesDocumentCollection.de"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   987 "InvalidEmail@notreallyemailbecausenosuffix"                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   988 ""very.unusual.@.unusual.com"@example.com"                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 true    JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   989 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   990 "disposable.style.email.with+symbol@example.com"                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   991 "0=1+e^(i*pi)@Gleich.Zahl.Phi.Goldener.Schnitt.aua"                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   992 "john."M@c"."Smith!"(coolguy)@(thefantastic)[1.2.3.4]"                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   993 "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123"         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   994 "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A"        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 15 = Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
   *   995 "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   996 "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   997 "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   998 "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   999 "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *  1000 "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *  1001 "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *  1002 "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *  1003 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *  1004 "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1005 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1006 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL"     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  1007 "1234567890123456789012345678901234567890123456789012345678901234+x@example.com"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  1008 ""very.(z),:;<>[]\".VERY.\"very@\\ \"very\".unusual"@strange.example.com"             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  1009 "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  1010 "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   REGEXP 4 false   JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  1011 "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  1012 "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  1013 "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *  1014 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com"              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *  1015 "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1016 "zzzz.zzzzzz@zzzzzzzzzz.zz"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1017 "AAAA.AAAAAA@AAAAAAAAAA.AA"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1018 "aaaa.aaaaaa@aaaaaaaaaa.aa"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1019 "Vorname.Nachname@web.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1020 "michi.fanin@fc-wohlenegg.at"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1021 "old.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1022 "new.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1023 "test1group@test1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1024 "test2group@test2.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1025 "test1@test1.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1026 "test2@test2.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1027 "junit.testEmailChange@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1028 "at@at.at"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1029 "easy@isnt.it"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  1030 "yes@it.is"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    REGEXP 4 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *  
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 1 Anzahl Testfaelle  20080000
   * 
   * ea234      anzahl_korrekt =  13690000 - anzahl_falsch =   6390000 - anzahl_fehler =         0  | MS   2672 = 0.00013306772908366533     = 00:00:02:672
   * 
   * Jmail      anzahl_korrekt =  17390000 - anzahl_falsch =   2670000 - anzahl_fehler =     20000  | MS   3643 = 0.00018142430278884463     = 00:00:03:643
   * 
   * JAVA 1     anzahl_korrekt =  12330000 - anzahl_falsch =   7730000 - anzahl_fehler =     20000  | MS   5926 = 0.0002951195219123506      = 00:00:05:926
   * 
   * REGEXP 1   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS  13768 = 0.0006856573705179283      = 00:00:13:768
   * 
   * REGEXP 2   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS   5986 = 0.00029810756972111554     = 00:00:05:986
   * 
   * REGEXP 3   anzahl_korrekt =  12760000 - anzahl_falsch =   7300000 - anzahl_fehler =     20000  | MS  33965 = 0.00169148406374502        = 00:00:33:965
   * 
   * REGEXP 4   anzahl_korrekt =   4620000 - anzahl_falsch =  15460000 - anzahl_fehler =         0  | MS   6343 = 0.00031588645418326693     = 00:00:06:343
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 2 Anzahl Testfaelle  20080000
   * 
   * ea234      anzahl_korrekt =  13690000 - anzahl_falsch =   6390000 - anzahl_fehler =         0  | MS   2439 = 0.00012146414342629482     = 00:00:02:439
   * 
   * Jmail      anzahl_korrekt =  17390000 - anzahl_falsch =   2670000 - anzahl_fehler =     20000  | MS   3494 = 0.000174003984063745       = 00:00:03:494
   * 
   * JAVA 1     anzahl_korrekt =  12330000 - anzahl_falsch =   7730000 - anzahl_fehler =     20000  | MS   5791 = 0.0002883964143426295      = 00:00:05:791
   * 
   * REGEXP 1   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS  13475 = 0.0006710657370517928      = 00:00:13:475
   * 
   * REGEXP 2   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS   5893 = 0.0002934760956175299      = 00:00:05:893
   * 
   * REGEXP 3   anzahl_korrekt =  12760000 - anzahl_falsch =   7300000 - anzahl_fehler =     20000  | MS  33856 = 0.0016860557768924303      = 00:00:33:856
   * 
   * REGEXP 4   anzahl_korrekt =   4620000 - anzahl_falsch =  15460000 - anzahl_fehler =         0  | MS   6263 = 0.000311902390438247       = 00:00:06:263
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 3 Anzahl Testfaelle  20080000
   * 
   * ea234      anzahl_korrekt =  13690000 - anzahl_falsch =   6390000 - anzahl_fehler =         0  | MS   2415 = 0.00012026892430278884     = 00:00:02:415
   * 
   * Jmail      anzahl_korrekt =  17390000 - anzahl_falsch =   2670000 - anzahl_fehler =     20000  | MS   3517 = 0.00017514940239043826     = 00:00:03:517
   * 
   * JAVA 1     anzahl_korrekt =  12330000 - anzahl_falsch =   7730000 - anzahl_fehler =     20000  | MS   5795 = 0.00028859561752988047     = 00:00:05:795
   * 
   * REGEXP 1   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS  13308 = 0.0006627490039840638      = 00:00:13:308
   * 
   * REGEXP 2   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS   5954 = 0.00029651394422310756     = 00:00:05:954
   * 
   * REGEXP 3   anzahl_korrekt =  12760000 - anzahl_falsch =   7300000 - anzahl_fehler =     20000  | MS  33897 = 0.001688097609561753       = 00:00:33:897
   * 
   * REGEXP 4   anzahl_korrekt =   4620000 - anzahl_falsch =  15460000 - anzahl_fehler =         0  | MS   6279 = 0.000312699203187251       = 00:00:06:279
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 4 Anzahl Testfaelle  20080000
   * 
   * ea234      anzahl_korrekt =  13690000 - anzahl_falsch =   6390000 - anzahl_fehler =         0  | MS   2449 = 0.00012196215139442232     = 00:00:02:449
   * 
   * Jmail      anzahl_korrekt =  17390000 - anzahl_falsch =   2670000 - anzahl_fehler =     20000  | MS   3503 = 0.00017445219123505976     = 00:00:03:503
   * 
   * JAVA 1     anzahl_korrekt =  12330000 - anzahl_falsch =   7730000 - anzahl_fehler =     20000  | MS   5783 = 0.0002879980079681275      = 00:00:05:783
   * 
   * REGEXP 1   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS  13358 = 0.0006652390438247012      = 00:00:13:358
   * 
   * REGEXP 2   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS   5934 = 0.0002955179282868526      = 00:00:05:934
   * 
   * REGEXP 3   anzahl_korrekt =  12760000 - anzahl_falsch =   7300000 - anzahl_fehler =     20000  | MS  33905 = 0.001688496015936255       = 00:00:33:905
   * 
   * REGEXP 4   anzahl_korrekt =   4620000 - anzahl_falsch =  15460000 - anzahl_fehler =         0  | MS   6208 = 0.0003091633466135458      = 00:00:06:208
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 9 Anzahl Testfaelle  20080000
   * 
   * ea234      anzahl_korrekt =  13690000 - anzahl_falsch =   6390000 - anzahl_fehler =         0  | MS   2406 = 0.0001198207171314741      = 00:00:02:406
   * 
   * Jmail      anzahl_korrekt =  17390000 - anzahl_falsch =   2670000 - anzahl_fehler =     20000  | MS   3526 = 0.00017559760956175298     = 00:00:03:526
   * 
   * JAVA 1     anzahl_korrekt =  12330000 - anzahl_falsch =   7730000 - anzahl_fehler =     20000  | MS   5790 = 0.0002883466135458167      = 00:00:05:790
   * 
   * REGEXP 1   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS  13282 = 0.0006614541832669322      = 00:00:13:282
   * 
   * REGEXP 2   anzahl_korrekt =  15680000 - anzahl_falsch =   4380000 - anzahl_fehler =     20000  | MS   5947 = 0.00029616533864541833     = 00:00:05:947
   * 
   * REGEXP 3   anzahl_korrekt =  12760000 - anzahl_falsch =   7300000 - anzahl_fehler =     20000  | MS  33941 = 0.0016902888446215139      = 00:00:33:941
   * 
   * REGEXP 4   anzahl_korrekt =   4620000 - anzahl_falsch =  15460000 - anzahl_fehler =         0  | MS   6268 = 0.00031215139442231077     = 00:00:06:268
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

  private static int                  FAKTOR_ANZAHL_ARRAY_A    = 10000;

  /*
   * Array, mit welchem die Tests ausgefuehrt werden.
   */
  private static String[]             array_test_daten_aktuell = null;

  private static String               str_50                   = "12345678901234567890123456789012345678901234567890";

  private static String               str_63                   = "A23456789012345678901234567890123456789012345678901234567890123";

  /*
   * Testdaten
   *  
   * Es sind mehr korrekte eMail-Adressen als falsche vorhanden.
   * Es sind mehr normale eMail-Adressangaben vorhanden, als Sonderformen.
   * 
   * Die Elementanzahl wird mit dem Faktor "FAKTOR_ANZAHL_ARRAY_A" multipliziert, 
   * welches die Gesamtanzahl der Testdaten bestimmt.
   */
  private static String[]             array_test_daten_1       = {

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
      "firstname.lastname@domain.com",
      "firstname+lastname@domain.com",
      "firstname-lastname@domain.com",
      "first-name-last-name@d-a-n.com",
      "a.b.c.d@domain.com",
      "1@domain.com",
      "a@domain.com",
      "email@domain.co.de",
      "email@domain.com",
      "email@subdomain.domain.com",
      "2@bde.cc",
      "-@bde.cc",
      "a2@bde.cc",
      "a-b@bde.cc",
      "ab@b-de.cc",
      "a+b@bde.cc",
      "f.f.f@bde.cc",
      "ab_c@bde.cc",
      "_-_@bde.cc",
      "w.b.f@test.com",
      "w.b.f@test.museum",
      "a.a@test.com",
      "ab@288.120.150.10.com",
      "ab@[120.254.254.120]",
      "1234567890@domain.com",
      "john.smith@example.com",
      null,
      "",
      "        ",
      "ABCDEFGHIJKLMNOP",
      "ABC.DEF.GHI.JKL",
      "@GHI.JKL",
      "ABC.DEF@",
      "ABC.DEF@@GHI.JKL",
      "@%^%#$@#$@#.com",
      "@domain.com",
      "ABC.DEF@ GHI.JKL",
      "ABC.DEF @GHI.JKL",
      "ABC.DEF @ GHI.JKL",
      "@",
      "@.@.@.",
      "@.@.@GHI.JKL",
      "@@@GHI.JKL",
      "ABC.DEF@.@.@GHI.JKL",
      "email.domain.com",
      "email@domain@domain.com",
      "ABCDEF@GHIJKL",
      "ABC.DEF@GHIJKL",
      ".ABC.DEF@GHI.JKL",
      "ABC.DEF@GHI.JKL.",
      "ABC..DEF@GHI.JKL",
      "ABC.DEF@GHI..JKL",
      "ABC.DEF@GHI.JKL..",
      "ABC.DEF.@GHI.JKL",
      "ABC.DEF@.GHI.JKL",
      "ABC.DEF@.",
      "john..doe@example.com",
      "john.doe@example..com",
      "..........@domain.",
      "ABC1.DEF2@GHI3.JKL4",
      "ABC.DEF_@GHI.JKL",
      "#ABC.DEF@GHI.JKL",
      "ABC.DEF@GHI.JK2",
      "ABC.DEF@2HI.JKL",
      "ABC.DEF@GHI.2KL",
      "ABC.DEF@GHI.JK-",
      "ABC.DEF@GHI.JK_",
      "ABC.DEF@-HI.JKL",
      "ABC.DEF@_HI.JKL",
      "ABC DEF@GHI.DE",
      "ABC.DEF@GHI DE",
      "A . B & C . D",
      " A . B & C . D",
      "(?).[!]@{&}.<:>",
      "&local&&name&with&$@amp.com",
      "*local**name*with*@asterisk.com",
      "$local$$name$with$@dollar.com",
      "=local==name=with=@equality.com",
      "!local!!name!with!@exclamation.com",
      "`local``name`with`@grave-accent.com",
      "#local##name#with#@hash.com",
      "-local--name-with-@hypen.com",
      "{local{name{{with{@leftbracket.com",
      "%local%%name%with%@percentage.com",
      "|local||name|with|@pipe.com",
      "+local++name+with+@plus.com",
      "?local??name?with?@question.com",
      "}local}name}}with}@rightbracket.com",
      "~local~~name~with~@tilde.com",
      "^local^^name^with^@xor.com",
      "_local__name_with_@underscore.com",
      ":local::name:with:@colon.com",
      "domain.part@with-hyphen.com",
      "domain.part@with_underscore.com",
      "domain.part@-starts.with.hyphen.com",
      "domain.part@ends.with.hyphen.com-",
      "domain.part@with&amp.com",
      "domain.part@with*asterisk.com",
      "domain.part@with$dollar.com",
      "domain.part@with=equality.com",
      "domain.part@with!exclamation.com",
      "domain.part@with?question.com",
      "domain.part@with`grave-accent.com",
      "domain.part@with#hash.com",
      "domain.part@with%percentage.com",
      "domain.part@with|pipe.com",
      "domain.part@with+plus.com",
      "domain.part@with{leftbracket.com",
      "domain.part@with}rightbracket.com",
      "domain.part@with(leftbracket.com",
      "domain.part@with)rightbracket.com",
      "domain.part@with[leftbracket.com",
      "domain.part@with]rightbracket.com",
      "domain.part@with~tilde.com",
      "domain.part@with^xor.com",
      "domain.part@with:colon.com",
      "DomainHyphen@-atstart",
      "DomainHyphen@atend-.com",
      "DomainHyphen@bb.-cc",
      "DomainHyphen@bb.-cc-",
      "DomainHyphen@bb.cc-",
      "DomainNotAllowedCharacter@/atstart",
      "DomainNotAllowedCharacter@a,start",
      "DomainNotAllowedCharacter@atst\\art.com",
      "DomainNotAllowedCharacter@exa\\mple",
      "DomainNotAllowedCharacter@example'",
      "DomainNotAllowedCharacter@100%.de'",
      "domain.starts.with.digit@2domain.com",
      "domain.ends.with.digit@domain2.com",
      "tld.starts.with.digit@domain.2com",
      "tld.ends.with.digit@domain.com2",
      "email@=qowaiv.com",
      "email@plus+.com",
      "email@domain.com>",
      "email@mailto:domain.com",
      "mailto:mailto:email@domain.com",
      "email@-domain.com",
      "email@domain-.com",
      "email@domain.com-",
      "email@{leftbracket.com",
      "email@rightbracket}.com",
      "email@pp|e.com",
      "email@domain.domain.domain.com.com",
      "email@domain.domain.domain.domain.com.com",
      "email@domain.domain.domain.domain.domain.com.com",
      "unescaped white space@fake$com",
      "\"Joe Smith email@domain.com",
      "\"Joe Smith' email@domain.com",
      "\"Joe Smith\"email@domain.com",
      "Joe Smith &lt;email@domain.com&gt;",
      "{john'doe}@my.server",
      "email@domain-one.com",
      "_______@domain.com",
      "?????@domain.com",
      "local@?????.com",
      "\"B3V3RLY H1LL$\"@example.com",
      "\"-- --- .. -.\"@sh.de",
      "{{-^-}{-=-}{-^-}}@GHI.JKL",
      "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de",
      "#!$%&'*+-/=?^_`{}|~@eksample.org",
      "eksample@#!$%&'*+-/=?^_`{}|~.org",
      "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
      "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org",
      "ABC.DEF@[1.2.3.4]",
      "ABC.DEF@[001.002.003.004]",
      "\"ABC.DEF\"@[127.0.0.1]",
      "ABC.DEF[1.2.3.4]",
      "[1.2.3.4]@[5.6.7.8]",
      "ABC.DEF[@1.2.3.4]",
      "\"[1.2.3.4]\"@[5.6.7.8]",
      "ABC.DEF@MyDomain[1.2.3.4]",
      "ABC.DEF@[1.00002.3.4]",
      "ABC.DEF@[1.2.3.456]",
      "ABC.DEF@[..]",
      "ABC.DEF@[.2.3.4]",
      "ABC.DEF@[]",
      "ABC.DEF@[1]",
      "ABC.DEF@[1.2]",
      "ABC.DEF@[1.2.3]",
      "ABC.DEF@[1.2.3.4.5]",
      "ABC.DEF@[1.2.3.4.5.6]",
      "ABC.DEF@[MyDomain.de]",
      "ABC.DEF@[1.2.3.]",
      "ABC.DEF@[1.2.3. ]",
      "ABC.DEF@[1.2.3.4].de",
      "ABC.DE@[1.2.3.4][5.6.7.8]",
      "ABC.DEF@[1.2.3.4",
      "ABC.DEF@1.2.3.4]",
      "ABC.DEF@[1.2.3.Z]",
      "ABC.DEF@[12.34]",
      "ABC.DEF@[1.2.3.4]ABC",
      "ABC.DEF@[1234.5.6.7]",
      "ABC.DEF@[1.2...3.4]",
      "email@[123.123.123.123]",
      "email@111.222.333",
      "email@111.222.333.256",
      "email@[123.123.123.123",
      "email@[123.123.123].123",
      "email@123.123.123.123]",
      "email@123.123.[123.123]",
      "ab@988.120.150.10",
      "ab@120.256.256.120",
      "ab@120.25.1111.120",
      "ab@[188.120.150.10",
      "ab@188.120.150.10]",
      "ab@[188.120.150.10].com",
      "ab@188.120.150.10",
      "ab@1.0.0.10",
      "ab@120.25.254.120",
      "ab@01.120.150.1",
      "ab@88.120.150.021",
      "ab@88.120.150.01",
      "email@123.123.123.123",
      "ABC.DEF@[IPv6:2001:db8::1]",
      "ABC.DEF@[IP",
      "ABC.DEF@[IPv6]",
      "ABC.DEF@[IPv6:]",
      "ABC.DEF@[IPv6:1]",
      "ABC.DEF@[IPv6:1:2]",
      "ABC.DEF@[IPv6:1:2:3]",
      "ABC.DEF@[IPv6:1:2:3:4]",
      "ABC.DEF@[IPv6:1:2:3:4:5:]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6:7]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]",
      "ABC.DEF@[IPv4:1:2:3:4]",
      "ABC.DEF@[I127.0.0.1]",
      "ABC.DEF@[D127.0.0.1]",
      "ABC.DEF@[iPv6:2001:db8::1]",
      "ABC.DEF@[IPv6:1:2:3::5:6:7:8]",
      "ABC.DEF@[IPv6:1:2:3::5::7:8]",
      "ABC.DEF@[IPv6:1:2:3:4:5:Z]",
      "ABC.DEF@[IPv6:12:34]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC",
      "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]",
      "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6",
      "ABC.DEF@[IPv6:12345:6:7:8:9]",
      "ABC.DEF@[IPv6:1:2:3:::6:7:8]",
      "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]",
      "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])",
      "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])",
      "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]",
      "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]",
      "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]",
      "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]",
      "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]",
      "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334",
      "ABC.DEF@[IPv6::FFFF:127.0.0.1]",
      "ABC.DEF@[IPv6::ffff:127.0.0.1]",
      "ABC.DEF@[::FFFF:127.0.0.1]",
      "ABC.DEF@[::ffff:127.0.0.1]",
      "ABC.DEF@[IPv6::ffff:.127.0.1]",
      "ABC.DEF@[IPv6::fff:127.0.0.1]",
      "ABC.DEF@[IPv6::1234:127.0.0.1]",
      "ABC.DEF@[IPv6:127.0.0.1]",
      "ABC.DEF@[IPv6:::127.0.0.1]",
      "\"ABC.DEF\"@GHI.DE",
      "\"ABC DEF\"@GHI.DE",
      "\"ABC@DEF\"@GHI.DE",
      "\"ABC DEF@G\"HI.DE",
      "\"\"@GHI.DE",
      "\"ABC.DEF@G\"HI.DE",
      "A@G\"HI.DE",
      "\"@GHI.DE",
      "ABC.DEF.\"",
      "ABC.DEF@\"\"",
      "ABC.DEF@G\"HI.DE",
      "ABC.DEF@GHI.DE\"",
      "ABC.DEF@\"GHI.DE",
      "\"Escape.Sequenz.Ende\"",
      "ABC.DEF\"GHI.DE",
      "ABC.DEF\"@GHI.DE",
      "ABC.DE\"F@GHI.DE",
      "\"ABC.DEF@GHI.DE",
      "\"ABC.DEF@GHI.DE\"",
      "\".ABC.DEF\"@GHI.DE",
      "\"ABC.DEF.\"@GHI.DE",
      "\"ABC\".DEF.\"GHI\"@JKL.de",
      "A\"BC\".DEF.\"GHI\"@JKL.de",
      "\"ABC\".DEF.G\"HI\"@JKL.de",
      "\"AB\"C.DEF.\"GHI\"@JKL.de",
      "\"ABC\".DEF.\"GHI\"J@KL.de",
      "\"AB\"C.D\"EF\"@GHI.DE",
      "\"Ende.am.Eingabeende\"",
      "0\"00.000\"@GHI.JKL",
      "\"Joe Smith\" email@domain.com",
      "\"Joe\\tSmith\" email@domain.com",
      "\"Joe\"Smith\" email@domain.com",
      "(ABC)DEF@GHI.JKL",
      "(ABC) DEF@GHI.JKL",
      "ABC(DEF)@GHI.JKL",
      "ABC.DEF@GHI.JKL(MNO)",
      "ABC.DEF@GHI.JKL      (MNO)",
      "ABC.DEF@             (MNO)",
      "ABC.DEF@   .         (MNO)",
      "ABC.DEF              (MNO)",
      "ABC.DEF@GHI.         (MNO)",
      "ABC.DEF@GHI.JKL       MNO",
      "ABC.DEF@GHI.JKL          ",
      "ABC.DEF@GHI.JKL       .  ",
      "ABC.DEF@GHI.JKL ()",
      "ABC.DEF@GHI.JKL()",
      "ABC.DEF@()GHI.JKL",
      "ABC.DEF()@GHI.JKL",
      "()ABC.DEF@GHI.JKL",
      "(ABC).DEF@GHI.JKL",
      "ABC.(DEF)@GHI.JKL",
      "ABC.DEF@(GHI).JKL",
      "ABC.DEF@GHI.(JKL).MNO",
      "ABC.DEF@GHI.JK(L.M)NO",
      "AB(CD)EF@GHI.JKL",
      "AB.(CD).EF@GHI.JKL",
      "AB.\"(CD)\".EF@GHI.JKL",
      "(ABCDEF)@GHI.JKL",
      "(ABCDEF).@GHI.JKL",
      "(AB\"C)DEF@GHI.JKL",
      "(AB\\C)DEF@GHI.JKL",
      "(AB\\@C)DEF@GHI.JKL",
      "ABC(DEF@GHI.JKL",
      "ABC.DEF@GHI)JKL",
      ")ABC.DEF@GHI.JKL",
      "ABC(DEF@GHI).JKL",
      "ABC(DEF.GHI).JKL",
      "(ABC.DEF@GHI.JKL)",
      "(A(B(C)DEF@GHI.JKL",
      "(A)B)C)DEF@GHI.JKL",
      "(A)BCDE(F)@GHI.JKL",
      "ABC.DEF@(GH)I.JK(LM)",
      "ABC.DEF@(GH(I.JK)L)M",
      "ABC.DEF@(comment)[1.2.3.4]",
      "ABC.DEF@(comment) [1.2.3.4]",
      "ABC.DEF@[1.2.3.4](comment)",
      "ABC.DEF@[1.2.3.4]    (comment)",
      "ABC.DEF@[1.2.3(comment).4]",
      "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]",
      "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)",
      "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)",
      "(comment)john.smith@example.com",
      "john.smith(comment)@example.com",
      "john.smith@(comment)example.com",
      "john.smith@example.com(comment)",
      "john.smith@exampl(comment)e.com",
      "john.s(comment)mith@example.com",
      "john.smith(comment)@(comment)example.com",
      "john.smith(com@ment)example.com",
      "email( (nested) )@plus.com",
      "email)mirror(@plus.com",
      "email@plus.com (not closed comment",
      "email(with @ in comment)plus.com",
      "email@domain.com (joe Smith)",
      "ABC DEF <ABC.DEF@GHI.JKL>",
      "<ABC.DEF@GHI.JKL> ABC DEF",
      "ABC DEF ABC.DEF@GHI.JKL>",
      "<ABC.DEF@GHI.JKL ABC DEF",
      "\"ABC DEF \"<ABC.DEF@GHI.JKL>",
      "\"ABC<DEF>\"@JKL.DE",
      ">",
      "\"ABC<DEF@GHI.COM>\"@JKL.DE",
      "ABC DEF <ABC.<DEF@GHI.JKL>",
      "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>",
      "ABC DEF <ABC.DEF@GHI.JKL",
      "ABC.DEF@GHI.JKL> ABC DEF",
      "ABC DEF >ABC.DEF@GHI.JKL<",
      ">ABC.DEF@GHI.JKL< ABC DEF",
      "ABC DEF <A@A>",
      "<A@A> ABC DEF",
      "ABC DEF <>",
      "<> ABC DEF",
      "<ABC.DEF@GHI.JKL>",
      "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>",
      "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>",
      "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part",
      "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part ",
      "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>",
      "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>",
      "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part",
      "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part ",
      "Joe Smith <email@domain.com>",
      "Joe Smith <mailto:email@domain.com>",
      "Test |<gaaf <email@domain.com>",
      "\"With extra < within quotes\" Display Name<email@domain.com>",
      "A@B.CD",
      "A@B.C",
      "A@COM",
      "a@bde.c-c",
      "MailTo:casesensitve@domain.com",
      "mailto:email@domain.com",
      "email@domain",
      "Joe Smith <mailto:email(with comment)@domain.com>",
      "..@test.com",
      ".a@test.com",
      "ab@sd@dd",
      ".@s.dd",
      "a@b.-de.cc",
      "a@bde-.cc",
      "a@b._de.cc",
      "a@bde_.cc",
      "a@bde.cc.",
      "ab@b+de.cc",
      "a..b@bde.cc",
      "_@bde.cc,",
      "plainaddress",
      "plain.address",
      ".email@domain.com",
      "email.@domain.com",
      "email..email@domain.com",
      "email@.domain.com",
      "email@domain.com.",
      "email@domain..com",
      "Display Name <email@plus.com> (after name with display)",
      "k.haak@12move.nl",
      "K.HAAK@12MOVE.NL",
      "aap.123.noot.mies@domain.com",
      "email@domain.co.jp",
      "firstname-lastname@d.com",
      "FIRSTNAME-LASTNAME@d--n.com",
      "email@p|pe.com",
      "isis@100%.nl",
      "email@dollar$.com",
      "email@r&amp;d.com",
      "email@#hash.com",
      "email@wave~tilde.com",
      "email@exclamation!mark.com",
      "email@question?mark.com",
      "email@obelix*asterisk.com",
      "email@grave`accent.com",
      "email@colon:colon.com",
      "email@caret^xor.com",
      "Fred\\ Bloggs@example.com",
      "Joe.\\\\Blow@example.com",
      "Lat%ss\rtart%s@test.local.part",
      "ABC.DEF@GHI.JKL       (MNO)",
      "(A)(B)CDEF@GHI.JKL",
      "(A))B)CDEF@GHI.JKL",
      "(ABC DEF) <ABC.DEF@GHI.JKL>",
      "(comment and stuff)joe@gmail.com",
      "UnclosedComment@a(comment",
      "at(start)test@test.local.part",
      "example(comment)@test.local.part",
      "joe(comment and stuff)@gmail.com",
      "joe(fail me)smith@gmail.com",
      "joe@(comment and stuff)gmail.com",
      "joe@gmail.com(comment and stuff",
      "joe@gmail.com(comment and stuff)",
      "joesmith@gma(fail me)il.com",
      " a @ []",
      " a @[ ] ",
      " a @[ ]",
      " a @[] ",
      " a @[]",
      " a @a[].com ",
      "ABC.DEF@[ .2.3.4]",
      "ABC.DEF@[0.0.0.0]",
      "ABC.DEF@[000.000.000.000]",
      "ABC.DEF@[001.012.123.255]",
      "ABC.DEF@[1..123.255]",
      "ABC.DEF@[1.12.123.255]",
      "ABC.DEF@[1.12.123.255].de",
      "ABC.DEF@[1.12.123.259]",
      "ABC.DEF@[1.123.25.]",
      "ABC.DEF@[1.2.3.4] ",
      "ABC.DEF@[1.2.3.4][5.6.7.8]",
      "ABC.DEF@[1.2.]",
      "ABC.DEF@[1:2:3:4:5:6:7:8]",
      "ABC.DEF@[::ffff:127.0:0.1]",
      "ABC.DEF@[IPv61:2:3:4:5:6]",
      "ABC.DEF@[IPv6:00:ffff:127.0.0.1]",
      "ABC.DEF@[IPv6:0:0:ffff:127.0.0.1]",
      "ABC.DEF@[IPv6:12:ffff:127.0.0.1]",
      "ABC.DEF@[IPv6:1:2:(3::5):6:7:8]",
      "ABC.DEF@[IPv6:1:2:(3::5:6:7:8]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8 ]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8",
      "ABC.DEF@[IPv6:1:2:3:4:5:6:7:]",
      "ABC.DEF@[IPv6:1:2:3:4:5:6] ",
      "ABC.DEF@[IPv6:1:2:3:4:5]",
      "ABC.DEF@[IPv6:1:2:3::5:8].de",
      "ABC.DEF@[IPv6:1:2:ffff:127.0.0.1]",
      "ABC.DEF@[IPv6::abcd:127.0.0.1]",
      "ABC.DEF@[IPv6::fff:999.0.0.1]",
      "ABC.DEF@[IPv6::ffff:.127.0.0.1]",
      "ABC.DEF@[IPv6::ffff:1211.0.0.1]",
      "ABC.DEF@[IPv6::ffff:12111.0.0.1]",

      "ABC.DEF@[IPv6::ffff:127.0:0.1]",
      "ABC.DEF@[IPv6::ffff:127A.0.0.1]",
      "ABC.DEF@[IPv6::ffff:999.0.0.1]",
      "ABC.DEF@[IPv6::ffff:fff.0.0.1]",
      "ABC.DEF@[IPv6::fffff:127.0.0.1]",
      "ABC.DEF@[IPv6:a:b:c:d:127.0.0.1]",
      "ABC.DEF@[IPv6:a:b:c:d:e:f:1]",
      "ABC.DEF@[IPv6:ffff:127.0.0.1]",     "ABC.DEF@[IPv6]",
      "ABC.DEF@[[IPv6:1:2:3:4:5:6:7:8]",
      "ABC.DEF[1.12.123.255]",
      "ABC[@IPv6:1:2:3:4:5:6:7:8]",
      "( a @[]",
      "(ABC DEF) <(Comment)ABC.DEF@[iPv6:2001:db8::1]>",
      "ExpectedATEXT@at[start",
      "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]",
      "Test.IPv6@[IPv6:1::1::1]",
      "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]",
      "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]",
      "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]",
      "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]",
      "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]",
      "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]",
      "Test.IPv6@[[127.0.0.1]",
      "Test.IPv6@[\n]",
      "\"ABC\"@[IPv4:1:2:3:4]",
      "\"ABC\"@[IPv6:1234:5678]",
      "\"ABC\"@[IPv6:1234]",
      "\"ABC\"@[IPv6:1:2:3:4:5:6:7:8]",
      "\"\"@[]",
      "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com",
      "jsmith@[IPv6:2001:db8::1]",
      "user@[192.168.2.1]",
      "user@[IPv6:2001:db8::1]",
      "UnclosedDomainLiteral@example]",
      "ABC DEF <( )@ >",
      "ABC DEF <(COMMENT)A@ >",
      "ABC(DEF).@GHI.JKL",
      "ABC.DEF@(GHI).JKL.MNO",
      "ABC.DEF@(GHI)JKL.MNO",
      "\"@\".A(@)@a.aa",
      "ABC+DEF) <ABC.DEF@GHI.JKL>",
      "\"ABC<DEF>\"@GHI.JKL",
      "\"ABC<DEF@GHI.COM>\"@GHI.JKL",
      "<ABC.DEF@GHI.JKL>",
      "ABC DEF < >",
      "ABC DEF < @ >",
      "ABC DEF <ABC<DEF@GHI.JKL>",
      "0\"00.000\"@wc.de",
      "@G\"HI.DE",
      "ABC.DEF@GHI.JKL\"",
      "ABC.DEF@\"GHI.JKL",
      "ABC.DEF\"@GHI.JKL",
      "ABC.DEF\"GHI.JKL",
      "ABC.DE\"F@GHI.JKL",
      "A\"BC\".DEF.\"GHI\"@GHI.JKL",
      "DomainNotAllowedCharacter@example\\",
      "\" \"@GHI.JKL",
      "\" \"@example.org",
      "\"%2\"@gmail.com",
      "\".ABC.DEF\"@GHI.JKL",
      "\"@GHI.JKL",
      "\"ABC DEF\"@GHI.JKL",
      "\"ABC.DEF.\"@GHI.JKL",
      "\"ABC.DEF@GHI.JKL",
      "\"ABC.DEF\"@GHI.JKL",
      "\"ABC.D\"EF@GHI.JKL",
      "\"ABC@DEF\"@GHI.JKL",
      "\"ABC\".DEF.\"GHI\"@GHI.JKL",
      "\"ABC\"\".DEF.\"GHI\"@GHI.JKL",
      "\"AB\"C.DEF.\"GHI\"@GHI.JKL",
      "\"AB\"C.D\"EF@GHI.JKL",
      "\"AB\"C.D\"EF\"@GHI.JKL",
      "\"Falsch.da.Escape.Zeichen.am.Ende.steht\\",
      "\"Falsche\\#Escape\\GSequenz\"@falsch.de",
      "\"Gueltige\\\"Escape\\\\Sequenz\"@korrekt.de",
      "\"\"@GHI.JKL",
      "\"a..b\"@gmail.com",
      "\"a_b\"@gmail.com",
      "\"abcdefghixyz\"@example.com",
      "abc.\"defghi\".xyz@example.com",
      "abc\"defghi\"xyz@example.com",
      "abc\\\"def\\\"ghi@eggsample.com",
      "at\"start\"test@test.local.part",
      "just\"not\"right@example.com",
      "this is\"not\\allowed@example.com",
      "this\\ still\\\"not\\\\allowed@example.com",
      "               ",
      "..@test.com",
      ".a@test.com",
      "ab@sd@dd",
      ".@s.dd",
      "a@b.-de.cc",
      "a@bde-.cc",
      "a@b._de.cc",
      "a@bde_.cc",
      "a@bde.cc.",
      "ab@b+de.cc",
      "a..b@bde.cc",
      "_@bde.cc,",
      "plainaddress",
      "plain.address",
      ".email@domain.com",
      "email.@domain.com",
      "email..email@domain.com",
      "email@.domain.com",
      "email@domain.com.",
      "email@domain..com",
      "MailTo:casesensitve@domain.com",
      "mailto:email@domain.com",
      "email@domain",
      "someone@somewhere.com",
      "someone@somewhere.co.uk",
      "someone+tag@somewhere.net",
      "futureTLD@somewhere.fooo",
      "fdsa",
      "fdsa@",
      "fdsa@fdsa",
      "fdsa@fdsa.",
      "Foobar Some@thing.com",
      "david.jones@proseware.com",
      "d.j@server1.proseware.com",
      "jones@ms1.proseware.com",
      "j.@server1.proseware.com",
      "j@proseware.com9",
      "js#internal@proseware.com",
      "j_9@[129.126.118.1]",
      "j..s@proseware.com",
      "js*@proseware.com",
      "js@proseware..com",
      "js@proseware.com9",
      "j.s@server1.proseware.com",
      "\"j\\\"s\"@proseware.com",
      "cog@wheel.com",
      "\"cogwheel the orange\"@example.com",
      "123@$.xyz",
      "dasddas-@.com",
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
      "@b.com",
      "a@.com",
      "a@bcom",
      "a.b@com",
      "a@b.",
      "ab@c.com",
      "a@bc.com",
      "a@b.com",
      "a@b.c.com",
      "a+b@c.com",
      "a@123.45.67.89",
      "%2@gmail.com",
      "_@gmail.com",
      "1@gmail.com",
      "1_example@something.gmail.com",
      "d._.___d@gmail.com",
      "d.oy.smith@gmail.com",
      "d_oy_smith@gmail.com",
      "doysmith@gmail.com",
      "D.Oy'Smith@gmail.com",
      "%20f3v34g34@gvvre.com",
      "piskvor@example.lighting",
      "--@ooo.ooo",
      "foo@bar@machine.subdomain.example.museum",
      "foo\\@bar@machine.subdomain.example.museum",
      "foo.bar@machine.sub\\@domain.example.museum",
      "check@thiscom",
      "check@this..com",
      " check@this.com",
      "check@this.com",
      "Abc@example.com",
      "Abc@example.com.",
      "Abc@10.42.0.1",
      "user@localserver",
      "Abc.123@example.com",
      "user+mailbox/department=shipping@example.com",
      "Loïc.Accentué@voilà.fr",
      "user@[IPv6:2001:DB8::1]",
      "Abc.example.com",
      "A@b@c@example.com",
      "this\\ still\\\"not\\allowed@example.com",
      "john..doe@example.com",
      "john.doe@example..com",
      "email@example.com",
      "email@example.co.uk",
      "email@mail.gmail.com",
      "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com",
      "email@example.co.uk.",
      "email@example",
      " email@example.com",
      "email@example.com ",
      "email@example,com ",
      "invalid.email.com",
      "invalid@email@domain.com",
      "email@example..com",
      "yoursite@ourearth.com",
      "my.ownsite@ourearth.org",
      "mysite@you.me.net",
      "xxxx@gmail.com",
      "xxxxxx@yahoo.com",
      "xxxx.ourearth.com",
      "xxxx@.com.my",
      "@you.me.net",
      "xxxx123@gmail.b",
      "xxxx@.org.org",
      ".xxxx@mysite.org",
      "xxxxx()*@gmail.com",
      "xxxx..1234@yahoo.com",
      "alex@example.com",
      "alireza@test.co.uk",
      "peter.example@yahoo.com.au",
      "peter_123@news.com",
      "hello7___@ca.com.pt",
      "example@example.co",
      "hallo@example.coassjj#sswzazaaaa",
      "hallo2ww22@example....caaaao",
      "abcxyz123@qwert.com",
      "abc123xyz@asdf.co.in",
      "abc1_xyz1@gmail1.com",
      "abc.xyz@gmail.com.in",
      "pio_pio@factory.com",
      "~pio_pio@factory.com",
      "pio_~pio@factory.com",
      "pio_#pio@factory.com",
      "pio_pio@#factory.com",
      "pio_pio@factory.c#om",
      "pio_pio@factory.c*om",
      "pio^_pio@factory.com",
      "\"Abc\\@def\"@example.com",
      "\"Fred Bloggs\"@example.com",
      "\"Fred\\ Bloggs\"@example.com",
      "Fred\\ Bloggs@example.com",
      "\"Joe\\Blow\"@example.com",
      "\"Joe\\\\Blow\"@example.com",
      "\"Abc@def\"@example.com",
      "customer/department=shipping@example.com",
      "\\$A12345@example.com",
      "!def!xyz%abc@example.com",
      "_somename@example.com",
      "abc\\\"def\\\"ghi@example.com",
      " joe@gmail.com",
      "$ABCDEF@GHI.JKL",
      ".ann..other.@example.com",
      "000.000@000.de",
      "0test@gmail.com",
      "1234567890@example.com",
      "1234@5678.com",
      "1234@gmail.com",
      "A@A.AA",
      "ABC DEF@GHI.JKL",
      "ABC-DEF@GHI.JKL",
      "ABC/DEF=GHI@JKL.com",
      "ABCDEFGHIJKLMNO",
      "ABC\\@DEF@GHI.JKL",
      "AT-Character",
      "ATEXTAfterCFWS@test\r\n at",
      "Abc\\@def@ecksample.com",
      "Abc\\@def@example.com",
      "CRLFAtEnd@test\r\nat",
      "CRWithoutLF@test\rat",
      "ConsecutiveAT@@start",
      "ConsecutiveCRLF@test\r\n\r\nat",
      "ConsecutiveDots@at..start",
      "DomainHyphen@atstart-.com",
      "DotAtStart@.atstart",
      "Drei*Vier@Ist.Zwoelf.de",
      "ExpectedATEXT@;atstart",
      "ExpectedCTEXT@test\r\n \n",
      "Length",
      "No Input",
      "Pointy Brackets",
      "Seperator",
      "Strings",
      "TEST.TEST@gmail.com",
      "TEST@gmail.com",
      "Test.Domain.Part@1leadingnumber.example.co",
      "Test.Domain.Part@1leadingnumber.example.com",
      "Test.Domain.Part@aampleEx.com",
      "Test.Domain.Part@example.co",
      "Test.Domain.Part@has-hyphen.example.co",
      "Test.Domain.Part@has-hyphen.example.com",
      "Test.Domain.Part@subdomain.example.co",
      "Test.Domain.Part@subdomain.example.com",
      "Test.Test@gmail.com",
      "_______@example.com",
      "admin@mailserver1",
      "alirheza@test.co.uk",
      "ann.other@example.com",
      "d@@.com",
      "email@192.0.2.123",
      "email@example,com",
      "email@example.name .name",
      "example@localhost",
      "example@xyz.solutions",
      "fdsa@fdza",
      "fdsa@fdza.",
      "joe@gmail.com ",
      "jsmith@whizbang.co",
      "jsr@prhoselware.com9",
      "lots.of.iq@sagittarius.A*",
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
      "me@me.co.uk",
      "me@me@gmail.com",
      "me@yahoo.com",
      "peter_123@news24.com",
      "prettyandsimple@example.com",
      "test test@gmail.com",
      "test%test@gmail.com",
      "test+test@gmail.com",
      "test-test@gmail.com",
      "test.test@gmail.com",
      "test0@gmail.com",
      "test@GMAIL.COM",
      "test@Gmail.com",
      "test@anothersub.sub-domain.gmail.com",
      "test@gmail",
      "test@gmail.",
      "test@gmail..com",
      "test@gmail.Com",
      "test@gmail.com",
      "test@sub-domain.000.0",
      "test@sub-domain.000.0rg",
      "test@sub-domain.000.co-om",
      "test@sub-domain.000.com",
      "test@sub-domain.gmail.com",
      "test@subdomain.gmail.com",
      "test@test@gmail.com",
      "test_test@gmail.com",
      "this is not valid@email$com",
      "user@com",
      "user@localhost",
      "very.common@example.com",
      "x._._y__z@gmail.com",
      "x.yz.smith@gmail.com",
      "x_yz_smith@gmail.com",
      "xyz@blabla.com",      
      "nolocalpart.com",
      "test@example.com test",
      "user  name@example.com",
      "user   name@example.com",
      "example.@example.co.uk",
      "example@example@example.co.uk",
      "(test_exampel@example.fr}",
      "example(example)example@example.co.uk",
      ".example@localhost",
      "ex\\ample@localhost",
      "example@local\\host",
      "example@localhost.",
      "user name@example.com",
      "username@ example . com",
      "example@(fake}.com",
      "example@(fake.com",
      "username@example,com",
      "usern,ame@example.com",
      "user[na]me@example.com",
      "\"\"\"@iana.org",
      "\"\\\"@iana.org",
      "\"test\"test@iana.org",
      "\"test\"\"test\"@iana.org",
      "\"test\".\"test\"@iana.org",
      "\"test\".test@iana.org",
      String.format( "\"test\\%s@iana.org", "\"" ),
      "\r\ntest@iana.org",
      "\r\n test@iana.org",
      "\r\n \r\ntest@iana.org",
      "\r\n \r\ntest@iana.org",
      "\r\n \r\n test@iana.org",
      "test@iana.org \r\n",
      "test@iana.org \r\n ",
      "test@iana.org \r\n \r\n",
      "test@iana.org \r\n\r\n",
      "test@iana.org  \r\n\r\n ",
      "test@iana/icann.org",
      "test@foo;bar.com",
      (char) 1 + "a@test.com",
      "comment)example@example.com",
      "comment(example))@example.com",
      "example@example)comment.com",
      "example@example(comment)).com",
      "example@[1.2.3.4",
      "example@[IPv6:1:2:3:4:5:6:7:8",
      "exam(ple@exam).ple",
      "example@(example))comment.com",
      "example@example.com",
      "example@example.co.uk",
      "example_underscore@example.fr",
      "example@localhost",
      "exam'ple@example.com",
      String.format( "exam\\%sple@example.com", " " ),
      "example((example))@fakedfake.co.uk",
      "example@faked(fake).co.uk",
      "example+@example.com",
      "example@with-hyphen.example.com",
      "with-hyphen@example.com",
      "example@1leadingnum.example.com",
      "1leadingnum@example.com",
      "????@??????.??",
      "\"username\"@example.com",
      "\"user,name\"@example.com",
      "\"user name\"@example.com",
      "\"user@name\"@example.com",
      "\"\\a\"@iana.org",
      "\"test\\ test\"@iana.org",
      "\"\"@iana.org",
      "\"\"@[]",
      String.format( "\"\\%s\"@iana.org", "\"" ),
      "Ärger.Öde@Übel.de",
      "Smørrebrød@danmark.dk",
      "ip.without.brackets@1.2.3.4",
      "ip.without.brackets@1:2:3:4:5:6:7:8",
      "(space after comment) john.smith@example.com",
      "email.address.without@topleveldomain",
      "EmailAddressWithout@PointSeperator",
      "\"use of komma, in double qoutes\"@RFC2822.com",      
      "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]",
      "ABC.DEF@[1.2.3.4][5.6.7.8]",
      "ABC.DEF@[][][][]",
      "ABC.DEF@[....]",

      null, "", "    ",    "ABC.DEF@GHI.J",
    "email@domain.topleveldomain",
    "unknown errors",
    "local@2001:0db8:85a3:0000:0000:8a2e:0370:7334",
    "MailTo:casesensitve@domain.com",
    "mailto:email@domain.com",
    "ab@188.120.150.10",
    "ab@1.0.0.10",
    "ab@120.25.254.120",
    "ab@01.120.150.1",
    "ab@88.120.150.021",
    "ab@88.120.150.01",
    "email@123.123.123.123",
    "email@domain",
    "unsorted",
    "..@test.com",
    ".a@test.com",
    "ab@sd@dd",
    ".@s.dd",
    "withdot.@test.local.part",
    "domain.starts.with.digit@2domain.com",
    "domain.ends.with.digit@domain2.com",
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
    "0=1+e^(i*pi)@Gleich.Zahl.Phi.Goldener.Schnitt.aua",
    "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]",
    "ABC.DEF@GHI." + str_63,
    "ABC.DEF@GHI." + str_63 + "A",
    "domain.label.with.63.characters@" + str_63 + ".com",
    "domain.label.with.64.characters@" + str_63 + "A.com",
    "domain.label.with.63.and.64.characters@" + str_63 + "." + str_63 + "A.com",
    "two.domain.labels.with.63.characters@" + str_63 + "." + str_63 + ".com",
    "63.character.domain.label@" + str_63 + ".com",
    "63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com",
    "" + str_50 + ".1234567@" + str_63 + "." + str_63 + "." + str_63 + ".com",
    "" + str_50 + ".12345678@" + str_63 + "." + str_63 + "." + str_63 + ".com",
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com",
    "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de",
    "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL",
    "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL",
    "1234567890123456789012345678901234567890123456789012345678901234+x@example.com",
    "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com",
    "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part",
    "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de",
    "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu",
    "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com",
    "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ", 
    "zzzz.zzzzzz@zzzzzzzzzz.zz", 
    "AAAA.AAAAAA@AAAAAAAAAA.AA", 
    "aaaa.aaaaaa@aaaaaaaaaa.aa",
    "Vorname.Nachname@web.de",
    "michi.fanin@fc-wohlenegg.at",
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
      

    "Dlbyyu66.htgqnnz.mbhaayetubec@aapdbjdx.wz", "ngp.difhwuetlfxno.swi@dgvxkbzeqi.km", "GyiqfesJnwycj@qxtcowerewrdepzn.lzo", "Vonoalspg-Etgliag@fwdwrxbiv.zku", "COekxqpl@ZugHjIotdu.gp", "Yomphxtfndkkpc@htruqrfld.dfp", "KJ@tro.aro", "f-lrrkir@Cilbzq65.lm", "Vvhrhiics-Ivfrw@gbczcjvwez.nod", "k.pnavquf@dzg.xkm", "Yfmseaw-Febxypw@dkbm.ylq",
    "Xpxh.Xxjv@Ozdx.su", "jpnzz-gnjx@RwweRpnWwofk.qf", "Tjiy.Lyx@koishiyi.hb", "z.aglfcqk@fwisnlj.jh", "Ekwd.Aavdlk@yikie.bn", "KX@zrow.aup", "X.Wugbiyk@AJGM.vz", "kfqun.nyiruzcjhflc@vncr.ua", "QehshrsWaaee@zrws.zbv", "Lfgstq-Kjjxcw@qjjnr.oh", "Oekxsh.Pxes@Cfvc.bj", "SssfrGyd@jdrblsv.dy", "Cgne.Vfgjg@xqh-tfs.gj", "w.bdxwvh@utvnqt.gv", "wtlzootkpmvod@iiebhm.ed", "hdkx-rdcfar.bubfog@gkebb.jb", "bdhlb-bzdsxe@ttroqe.gk", "Odjih.Ryhowu@ughfoyp.jc", "n.ljxdpw@pnjdmgft.jp",
    "Ktxt.Fxxiupay@4iJMj.hg", "T.Wxfsrqq@VPDJ.xq", "Naycyx-Izjulq@uodrm.gw", "BojwpVgl@kwzyzikmfo.ifv", "jzwagzjlmcu@dxsobphy.td", "Btese16@xtjciredxd.qmz", "TK@icvgwrvh.yee", "UalgeeEsepxv@gfwohppiryg.gi", "Uqxhgc63@hcue.pn", "s-ccroy@ohvtm.zu", "khom-epdn@yycpbu.js", "Ekch.Mkjuglkl@kaueo-pdb.fbx", "Rdih.Ydh@xcauaau.gp", "Radg.Xsxsob@eF4w7nm.sr", "Wlzo.Jzqcm@jjma.dey", "buaz.jyv@hxlm.esx", "Qgz.pcjkj74@rxruigqd.zq", "Ualjrg.Igojbz@vjtzwnr.iwo", "r.ebbebgze@ykt.owp",
    "bdyag.hnqnjwzpfkju@yquks-skgbdfu.vw", "Zzemmvc-Uwzomot@ahaa.rdz", "trsat.vfbidhoolons@bubv.xw", "AP@zuzn.tyn", "k.cmnthhz@xmxxcds.qt", "wpchppsxvzeri@oubrbvnr.ai", "Jnsywil.Etdfys@5ecLF.xnq", "m-ofnbmx@Jcwolw54.eg", "JoknrLimdna@tnwulmep.wu", "Ppsibygzp@gBX14.sb", "Nlzam30@YdkIV.cb", "Vzukr02@RH.ob", "KvqnaijDiljh@jyeg.ric", "krodm-eeko@IpkvXvdXnbpc.gz", "SK@syFdz.px", "Lgsgm.Chbiuxqvc@dwtvov.knu", "ofescrpgsrnyiunhnat@vgxexjltlb.xk", "ylcp.yxus@lbmhxmfsfga.ba",
    "Cxqzrgkk-Lzelmmd@qbljryejg.wzo", "biwg-jrmtggjss@xjxqxkzc.ui", "Ryteer.Cpso@Zrqu.nq", "IGyhseel@VlrKwRggdyMsoofwGfHtmfp.hs", "Chnel.Jcgajx@oclcaxpeip.fq", "Kgum-Mhzhfy@6jYdM.gl", "Oohva.Muqys@ag.jewl.uzi.mrqq.az", "disx.nurerymb@i-fggircp.cm", "S.Alrfb@RenJrSjmoqGajihxBiEzyls.xz", "Hlbsy.nac@vxf.aa", "Plpsm.gbu@Aerz.qg", "Dvuyy.Ixbgm@dhymmsr.pg", "Lswob.Jumpfdygk@fejyk-lfrtw.mf", "Boz-Fwbf@wcvptslttq.jdh", "ZpwalhqLemhyl@kgcfvjdfoz.uyq", "Ifkebinxb@gktndcti.fp",
    "alo.nsi.jeshl@ag.ybyuve.hn", "lijzdbzaqb.otnoedzndm@cvtnxyz.hfi", "Btizngbch41@gjczmdvaxt.ofl", "Zkgy.Bbcfvexv@2zxRj.gz", "B.Qibyuqa@nxgvvfsqaaiqj.ao", "Uyokv.Goqqm@htwlpisj.ntb", "NTeshul@ziar.ri", "G-Wykiquo@epon.yq", "TnnldAcp@dshqi-qhjfagp.mr", "b.zeigfhr@gjs.auo", "Expupilfu-Vfjba@nwooeljvkv.gmx", "H.Ivfjirqnsfclnjel@wlw.aip", "Bvcbo.Solhg@Gbtetw.sn", "Eieprwpk.Hghiy@hdxycpfkc.vy", "cs-ggpzd@qpuqzlv.xa", "Zmqapg.Umqzko@xaYEN.vr", "Wbqjp.Roiwz@UlcpEvkjy.li",
    "ZVaccwa-Biwaikom@mjody-etw.dqs", "LFfpa.zrcezmpdw@qwfdvukxq.gn", "Dgohagwv45@ygnegvoada.onk", "AihcInnkxr@ozg.suo.ul", "Rpsrci71@wbcxqoif.za", "rdqzevfnbywwf@en.eufc.kiz.dvut.ph", "CbacwosbfxKrfneua@zidxywmzia.amu", "dqfep-vgmiqy.ehbagij@defal.pa", "qtose-owvrouwthueuhxadzwaqla@jjvzgup.wh", "vmtkka@EY3jF.yn", "Cjtllfw.Pvvytmwfck@ozlpfmXo.sa", "Jxng.Wiwro@wzoigvc.qt", "bnnby.wqjas@dcohdhnt.ww", "RW@ypa.tvo", "Jzpch60@LgeXE.zt", "Elcve.Yzcnqcoeb@ccdss-sexkxvo.va",
    "VOyhjmzfu.Sahifkmtealzlr@mwbizbna.ym", "Tgtqpujq42@nxuxlqezpi.yis", "lgwhmcrzgr.pniuptxkzb@tuivmqv.jc", "Ndvtp.Cttnynturfk@Yrpcq.tp", "Wdyvt.Zikhbi@ygoua-uuatllhmy.bi", "AuqiuitcpAohjykf-Fnmt@urshvybg.bzy", "Venwp.Genag@vvlxcocu.cvt", "QmgywrBinnnk@gurhrkizzre.qs", "TI@gwlzphdi.hbh", "WhoptKjdnfr@bdhxda.pk", "asxpdjajuvq@ofwyvgmb.sb", "UczeUgf.Stcqvokc@siVNK.qg", "Malby.Nqrke@wu.fugc.cxu.zchu.zns", "jvla.skbgajqk@d-oqyeazx.jd", "Qhozkkhycq.Vttrx@kjlatmzxyox.cp",
    "Mtwstii-Yowdozwi@pccgo-kvl.xrl", "Lndef-Wtzdur@ClhhgJvdozkh.qg", "Qaqzd-Jbpgoen@vajjkgdzqiw.cv", "I.Huajupwjnrg@sxewp-meaawjv.qz", "j.skthwtp@eezq.gnp", "Z-Wakcdpl@qpdjncjz.ez", "Icytd-Pznste@aywnzebo.juh", "Dfatll13@gghcmmay.zf", "Jjlx-Jjsmoi@Kurfn.jk", "UO@iug-rpibki.tz", "dszmlu.cfbsyx@QholJndEyqgm.bj", "fqsfp.xzjgtfnko@ddckh-usbdbyn.qiz", "Hxzum.Lbwvb@qavvcpp.kc", "UN@kekikkpm.rfe", "Vtxu.Hrs@VxxxBkhixmlb.vwz", "Kyhr.Xyuchdidmaqj@etiao.cb", "Qfpit2@yec.hpe",
    "Kqxlix-Fihecxiyj@OdtTkZfradDzbgbjOgZhbed.fi", "QK@WC.dq", "Beckkv.Oxlhrgfofx@uofgdlzt.he", "ObthaHcc@kdxwp.ky", "N.Qmklhqslu@vhhtrucqml.gbw", "SmqkdddRnwmfiin@fjioeme.ot", "zekcu@nfefliqt.if", "O.Kmuvu@yxnaka.qnulyd.pj", "iowof@wezeq.bwzxncu.nx", "Mmav54@fV.Kc.onc", "pmwdwzv.cmnad.xhdw@ljvhaot.eog", "Wlfmdc-Hkeyzvnhyfw@Zdvnsgbm20.zi", "y-wnqqc@gnxvbnAoq.may", "mfnr.wtcpj@KS.jo", "Royst.Ascxa@ogmll.uvw", "Yct-Ujpbpxd@ikzo.aner.xqyhla.zt", "Tenms.Ihvds@djotn.cwl",
    "maeuij-lfpgvcc@pqhvonrwju.bcm", "Arpdvm.aSxagvn@Ljly.tz", "Ehinzsr.Adoma@GbuxrEpCepsaxcNzidkgw.ti", "RiklhyPehjgpfgps@NsikciukiGpnh-Npgewulal.pg", "scurbrvtho@ipwx.hd", "IkmrmWof@xwyf.rog", "S.Guu@otrlvgbtvt.lf", "U.Xdykgt@gevqd.hq", "Yeov.Rlssc@0ZiLs.qdr", "Hjytcfaxjful31@ilawiaxukrnel.zk", "Pmkcxil04@fdmito.ao", "Dvmq.Qdh@fdgimmay.ec", "kjLboeee@fjpphb.tl", "Xexrivo.Lnexj@tnxm.lrz", "Xjlljxozg75@kskutjv.pi", "Pirnzo.Gjzjsoxd@bxdav-oor.cbi", "Dydp76@ccloig.qj",
    "Cnjpef.Pdxfo@xdjvadl.ogp", "ZmJxlJdnvq@jzfgetw.yol", "gljoreezwiv@FarUnvGdlfVqrTvy.yu", "Phhpoi-Txbcxje@Lavvpf.hh", "Yemres.Nwouyvmzssku@yswkiwt.zh", "Fzndggtnl-Fpzesjfyhbzr.Iqrk@rbdejei.da", "ZW@yrefkwwn.ma", "Snfxaugr20@mrjf.yaq", "Wvzcwcx3@nxh.bop", "Duuxl34@Edzae.bv", "awijv@Pxjmnor.id", "QS@LI.tr", "WI@Ctrhko36.yb", "Wuxkb50@Kfzhw.nl", "cjqknkstzpo@ibcdpmmgmm.vmn", "MskhUaactn@NqbzflHlqcv.pdx", "Vtnsrp.Aflul@Fsesiw.sn", "UB@mgse.whj", "Iehy-Ehcge@UOdaE.uo",
    "Ynpvbxqol-Kgkdtbsg45@Vweqx.ov", "Wbvsgy-Bpcdmpreo@ddrmgzrf.ef", "iixgvkktkrwx@WV.av", "eaegjhbo@Pdezse16.rm", "Trrttr.Zwditw@vpevfvvk.hl", "ME@zkqs.nn", "emigfgwmepdxj@vbklcet.jm", "v.qowhs@FojeBnckf.ou", "Bfzjapl50@hxqnv-ercdgoh.fi", "qqrsp.pnioj@Kfiddh.wo", "Znclx-Fxsm@pJ4Mq.kw", "Gfkev.Gggakpptaot@tzca.kdnj.xzgvsg.tt", "Bfaoz76@ritsxhjt.py", "nfodsoa@rorr.tsp", "flvz-dsain@qkdjnxox.vh", "xowobobogji@tbepgvj.wg", "vrenmkeiyr@qkuj.jha", "vjcmk.hzdm@xpbpef.ich",
    "YiqfHxgpmqkj@PA.cx", "Unrdcv.Omlvpndo@gggnrkq.ah", "Evgjz.Ozatbzmg@hokrpgt.phx", "OCgxhe@pqfhukclcgf.dc", "Lnvktd.Owlxyyd@pccbwgusxg.mna", "pzxohakgp.cmplm@YmhsffmxmWlxh-Jjbfoduyz.na", "lbkas.sopfvm@GfkPw.tp", "Nplkvszcx@fkic.gcjz.wmkfiw.kh", "FD@Zrdjohm.dz", "ZO@jbsxiphv.vod", "Gwpswj.Pmhbw@Awwcmhmzd.uxw", "GnsqJyfb@pwxd.hih", "vvczay@Euqypk.im", "ocwi.srjquu@h-fiozgxd.nd", "Ixhrh.Qpkd@Bxjzm.nu", "Ddrklb.Ptixbwtm@aepyqnh.zk", "Smjq.Bhqf@Jlfm.xc", "PL@zmye.lzu",
    "PmjlTftrea@PxmevbNrube.dsa", "qeotwhcshlk@sumcouhmev.pls", "FvwhyefMbbgsw@sfyuffyiko.hel", "yAtk@clxeo-zsoyoas.we", "lsznnplbhiji@TH.pl", "F-Xzuznnc@jpbu.wx", "ahnnnz-wjuepn@phyodl.cj", "f.gxrks@ums.qzm", "jzz30@WF.hw", "h.dflrd@CgdiBakci.ww", "Xpa.Qvep@Jss.pr", "AE@jmuu.bzt", "R.Nlvglt@wjonq.ef", "Juuj.Kqrlyr@uxrnlou.ea", "Rmlht-Vsnduvn@kjgb.her", "Psgo-Xeaenep@Uyhgd.ja", "Faogr36@tdrv.brs", "Sobch63@Sgbyb.gc", "Ttwmje.Hofjn@kekwhzr.id", "Ysu.Mcaxh@ijrsbg.zfv",
    "CKfb.Ksb@njasdbnr.eh", "Fxrjfhu.Rxobu@vioi.jko", "Yzkzkqqxd30@fwfpusgxqe.gak", "Uymgri-Suokojzwa@nuppytpg.dn", "Cjdy.Bgp@jplvlcxu.bh", "Qdnmnbalm-Vifputbvvjdy.Slzi@xdmoolw.ux", "Lgvor17@LzuFT.gi", "Mhwgsuylh-Ycfif@olfdcynzsf.yhd", "dVbz.hmzvG@Gws.gnn60.xs", "Jhbly.Jikzqwye@abtudyl.rv", "f.ysrddo@yycrslgk.hv", "Wkvtx.Nfowqebg@6ugBv.zi", "Iwlcknzoe@vNT37.bt", "Spnzkks77@qyzgy.lf", "pbmzrR.Jos@qutakqbwvsa.kz", "AwznA.dnak@YOpuI.ye", "dpkgxlttuuynp@iy.ijvy.ywhz.xc",
    "FDsukpm17@demovfrrvmfd.no", "xpqqhmyfk.xuqmb@IbgxrtgvkMntk-Gmrudzwfrvcn.fi", "Lephqr.Dzvmvs@fswjgzg.hxd", "RL@gvy.ygv", "GjyvZatppuxa@WR.mu", "Hkicxjn-Qbpvzukn@xgtph-gka.ejw", "Hwfwfqpzdg44@ehfsu.es", "Aqtc.Upbthflk@odohc-cgt.hva", "Unnea.Bizbo@fl.ttas.yjw.kncq.qn", "DU@Tpsoyve.fg", "L.Xoyhuxz@byc.tfn", "scrvxphtyd@cmmmdgxo.in", "rotn-kkzqysyl@kouzczau.fj", "dwufdnf@eqguj.wnj", "Tjbsc.Ozovs@vkjdygpq.eor", "Gtwd.Fibxbokb@1oonmxWYk.sz", "Fabst.Msakwt@mkzbrssl.fj",
    "vubd.bzfv@vhubrlogv.ye", "ICwk-tpctvd.pic@NewFjFayacAiufnjIzPqjja.ls", "kcyj.hetbe@yryy.rsm", "Mtksa52@qtxocsjily.ara", "XNfjcny@ewvf.at", "Cwsrpbs51@pssbl-uapnngzn.jf", "Sqmve.Kqeggixzr@jwuip-csuqwba.hb", "Ftywq.Ghqikdai@awmmf.ps", "Rfdsa.Lvvossdxwag@Nhzzz.fn", "Pmutg.Trgmcv@uhrkicfefh.ry", "Nsniy56@Wqeyb.ly", "Mdhleryq25@baewegfz.hn", "sxqqj.ilbtihjgoo@xbia.nn", "Kwxrj76@ebhx.in", "klktt-meds@IuazCyhJsams.yu", "Edufekf@nsszobpthbd.ns", "Omiyc.Lalbmmr@VYYY.jn", 
    "gk-zvnba@Dvvaqb03.me", "Ctcd.Zdgam@0DmxrzH.zno", "fgrjitxpeugtq@xrhqjip.iqm", "dpik-anbtol@axhzkagli.rh", "mvoxbe-nblowosxuhh@veebaqudxu.ap", "kubazxjgavp@RltUpjWtxkIutXzv.ak", "whlqpxmogb@xpfc.hjp", "Gcisfoatx-jixg@ybrnusete.sdk", "Xosl.Pn0izlh@RPtyw.qz", "nzeaub.jhnnj@pbvyn.ko", "v-bmlzj@rzgvnck.lu", "Yzlt-jzarc22@psvfhiheozxu.wcw", "I.Xzkmtar@ncuknslkeglj.fj", "RhzuKn.iywsmsy@kqmdnjiu.lmo", "Jueo.Mifsnbrrbqqwfaklkg@qZ1v1.zs", "wpjwv-ncfmwjtyjtkak@tbcgpsr.dg",
    "TvhjlcRwdupe@bktbswisxau.mg", "Iclzn.Vqrawcrdhnl@wiagptcf.km", "X.Kjgti@FzaSgVwgmwWbyafzKxRjikn.vw", "CwuhdbqTgpny@hzjm.leb", "Flxtslptk@ijmnk.em", "QbkbdctwiV@bzJto.au", "Irobdkxof@qarm.ivdt.wmtrbd.fe", "bzjxpjkf.nitco@dqrkkpnv.hpu", "Jvu-czskxbg@uctoxvm.zzn", "q.xjjroo@rhhuwbap.oc", "Aqtbrwmpx-Aupldodp47@Lhvce.dn", "Rxzkf73@rhdgxgrxou.xpl", "jqrydv@PT7aI.ou", "Yooexm-Jghufm@mxgm.li", "Fbpljn.Ujnjkg@qyfsg-qsk.lfp", "gbjoi-ymfpsvqk@oujmmnvhps.th", "VT@vsSxz.ns",
    "DExausyhhqApztkmot@qpyryphwzx.ivs", "ybqqij.givhlbzg@ushqvdsst.ihn", "aowlm.vlpmmmtr@lmjkzzwa.qp", "Lblopskf.Kpewtv@adygxfpyfm.pz", "Fknnbt-Dzbxioc@pBcirw7Ch.gl", "hioc.ncftcaui@Vyubhnl.ig", "Tqbi.Amxbdppa@qhfgqigbp.jkx", "mrxpe.rbuziz@QufRt.zg", "Fnvlhjj.Caoyyw@1rwZK.qrt", "QoglqStidch@xfusjsos.oy", "wcrpv.nwbtc@lssoyt.ntw", "Buwclfrmf42@kqcoya.dv", "Bafofxu.LA@jcv.ILHfk.sy", "Bqunz00@kzqjyzcrwt.bdb", "Afxdrsfyk-Ijtmx@jwzeoqyunn.aok", "NizgtMbtlzd@gaqkujqh.vu", 
    "Jdii.Plpfkd@oftyvay.ze", "Koft.Mhf@zbudhjpc.qt", "l.nmbcddv@xrabznm.zp", "u.rjpqys@kpzcbotx.jr", "Latpo.Fxdjwxuu@5nzDz.wh", "k.bfutuy@adpzxjjm.ce", "fkhut-uiogzuzqisuwvsnsjzenzh@dklckob.ge", "Avqfn52@DP.cl", "Nyykfeobgo.Avqju@esaozkyehcg.fj", "N.Diazbfy@SJQR.ul", "TowpsWll@nokuzvqggf.ape", "yoyh-hyrsmfzun@qhisaop.zc", "Vlshg.Muoevusmuov@Haxxy.vs", "Xvue.Wemhkj@qoKBO.da", "Ekbvjl-Rgprnh@bofol.hw", "I.Bunesuvk@mbh.jyc", "h-ikwtc@twvueoa.nj", "yhxz.friba@csgt.hkr",
    "vbjeqaicxrmptewyoll@loktyaeizp.oy", "Tuwpl77@CcdHP.gr", "Vvhoi.Bsjwpujp@omruwnc.ze", "Ysrtp.Czuzpp@ppyhsdadig.ak", "Bkqryx.Wymn@Qfgw.yg", "L-Ogpadxw@unde.ur", "cbgocheehgywj@pl.fwqz.ufv.aeoo.so", "Kdzhv03@utpl.ol", "Myvo-Viyysu@3eHnA.by", "Xclysk20@eufsqkpd.ye", "Wzshk.Yggua@ky.pnty.xrz.itmu.kd", "Qszu.Tgrybnni@yvbscpfhu.qum", "jqghw.rjgjmnhwjclc@szzd.xq", "qnzd.ijanvaon@b-uhmczyj.ez", "q-gadexk@ewtdi.my", "Vshvpst-Kfuzolm@yupr.mxj", "Fupvmefynzz@zjvnklafy.ifi",
    "Woublzcot70@nosgodmwfr.ibp", "fberrq-smflca@umyuyx.lk", "yqfkcpcpjwm@dafqlaun.za", "isjdepvvaswum@szjgczei.lk", "x.nyyjebn@wgn.ksn", "Lncguff-Sdynkssg@fhemz-zly.ftx", "melkp-cxpk@SnskZyeFewbt.cl", "Hkrrgvnzx@lfiqakjx.jj", "wrnsl.kdnmhwmiliox@rfpdi-mjdlbah.dr", "gtismdlyml.srxjmxtpyg@yrxfrta.yk", "Aeylaad.Vjbgdr@4kxYE.ver", "Jcdyl.Hecsudasw@ypzyg-ozulcde.al", "GszafWrk@tkvbu-utqodul.nh", "Hccdmptdt@hofdo.tu", "Dlpe.Uomathnizkyi@nl0.zh", "bqmqwwaqeiaal@shzmds.rl", 
    "JllmeynuxdGajnysgh@wqniamptar.ggq", "Wabyccykq.Wpszhgdxgjycdx@iqtjeglt.qg", "qckbfp.napao@dwipp.cg", "AV@brdnaufm.yrr", "vyfwh-ublgwc.tljwwdp@nctpw.an", "F.Qchad@HytBiRdnrvLljsbbKyLwgvb.qm", "AeqyoxjzbWiwdmctce-Slyg@bvmmylxrca.kpj", "Sfylgpaqmp17@entoz.ze", "ZrcmKtxulg@ehn.nd", "JnsxbqgXminl@arat.ebj", "Ffxfu.Puqqatifmv@wlraearg.ih", "s.roemjogl@cvj.lis", "gqselpme@PT3gV.ay", "CNqgsdl@dspaq.rp", "Pfak.Wbcqyqht@znofu.ojl", "Pblogmj@rqesk.jpd", "M.Mlet@ulqify.cd",
    "NUjkwul@zvxg.iy", "SvskljHhgcyg@xpkjnivkqkm.zp", "Pqnepvtgl@hHY45.pc", "Jxaoxo.Hhfebj@bzyczyk.anu", "Mgxrbyos.Dkiaqvp@XaxzGudvk.ny", "OVvn.ppxj@FsbNzVsyaeKwfujzPzXchbo.uk",  "Ebxjq.Vhkiox@ikchngqw.gqb", "Dcgpkxlth-Nyjihjaq@nmvfcxccu.epz", "f-vycozg@Ggwyczz77.ze", "Hcoz.Dpxrlbxv@4iSutmZa.bt", "Wvxw.Tghu@Xkiipvu.ge", "Dyuxet.Cfyhykjj@bfrjhyqn.fq", "OaYrw.Bhnsja@Tbn3hri.dw", "VKj.XvF.ynaxf@RzcQ.TA.fx", "Lvniok.dllkqtj@Knulsdoo.ivbocjb.yf", "Of.mvdk@idwjfn.AKzx.Yn",
    "ZgdoojrriGqdhnpcxj-Inbo@rvmtcpzvjw.npf", "fautm-thrqr@nasmfxhiiwxh.eb", "aWccbnvt.sbvd@opfgvgglntf.it", "qeahjfl37@aaamlnvr.ld", "cmldkaqErh@tjqa.alf", "dHandokxyth@dwmbev.kc", "yaomiOgvnnt@juftozrqmv.lgv", "Hrxcafm-Xehzrp@1cLdE.id", "Ecbwklcpanhzae-Pqtfdks@wT0Sp.sa", "dapznhps.loxzorimxilw@nhshf-tuttjmj.cb", "nfajuvvzxyr@lscrgkd.mz", "pjjzagya@Vgtiyc66.bc", "eubtm.Kwqxxhsv@whLuqiSK.dek", "twVx-zgtfcAgrda@fxhjdws.ldi", "erpl.HE@wrssb.yud", "iziqj.auweq@mpsw.fg",  
    "Gycp.woirhpnl@eloaxklbxmm.qlb", "Jhqfafnahr.rfzztchl@nrinbyx.lf", "uuah.otmzllx@rsxssmaiin3233q.puzd756cslj.lb", "jvmycupvw.Qqgkrvdtbrtn@iskgyngi.hbcrt.hw", "fbazb-zyhlixwmU@pnqs.jaujgeh.cgpv", "Kcdkg.vgeucztgkQ@nyolnyt.tfjkz.vo", "sprdzi.Dfrrcj.ivvfyksur@aqum.qkgf.ghmb", "dfeezt-rfhuxsjaaG@FapevkB.frh", "Ism.Cq.zgg.utp.Slc.Bd.uLetm.Ydhrekdnq.yuvefqvdzui.ysj.hzcag@qslhsdng.vfi.zkmubsziud.iq", "B.vwea.njjo.no.nid.i.nqhw.ied@hghlhoh.riu", "FQQNJ@jycbeg.ihyrg.dsv",
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
    "Afom.iqfsekdw@vjqtisdfwmj.jdg", "Tlufsbzxba.gfueqlaa@ugclozu.yv", "wedr.eebtsgb@jmndsdfewr3434v.ashf333vdus.bs", "eaflfnkyk.Bnpblitoofoc@izfkujik.zyvgv.ju", "bkkdo-sdasdffwR@gfdb.asfdsdf.tetm", "Paadf.klddsksdfP@gkjmwfa.sdfno.zr", "dnbmdt.Elkyfg.swxsbrabl@lxhh.drgo.adyk", "eotamr-slkdffxlaS@TssfzvO.cwd", "And.To.all.you.Reg.Ex.eMail.Validator.offionardos.out.there@remember.the.soundtrack.is", "I.just.call.to.say.i.hate.you@ksdlfje.ksx", "DANKE@terima.kasih.com"
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
      startTest( "9" );
      startTest( "5" );

      /*
       * System-Informationen fuer die Vergleichbarkeit ins Log schreiben
       */
      wl( FkString.getSystemInfo() );

      /*
       * Log-Datei erstellen
       */
      String home_dir = "/home/ea234";

      home_dir = "c:/Daten/";

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
      array_test_daten_aktuell = array_test_daten_1;

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

        /*
         * Kennzeichen fuer die Testdatenausgabe auf FALSE stellen, 
         * damit die Ausgabe nur einmal kommt.
         */

        knz_ausgabe_test_daten = false;

        /*
         * Index fuer die Ausgabe der Testdaten auf Index 0 setzen 
         */
        test_daten_akt_index = 0;

        /*
         * While-Schleife ueber die Testdaten fuer die Ausgabe der 
         * Ergebnisse der Prueffunktionen. 
         */
        while ( test_daten_akt_index < test_daten_array_length )
        {
          test_daten_akt_string = array_test_daten_aktuell[ test_daten_akt_index ];

          akt_dbg_string = " ";

          /*
           * Ausgabe des Indexes und der aktuellen Teststrings
           */
          akt_dbg_string += FkString.right( "          " + test_daten_akt_index, 4 ) + " " + FkString.getFeldLinksMin( FkString.getJavaString( test_daten_akt_string ), 85 ) + " ";

          /*
           * Jede Testfunktion wird einmal aufgerufen und die Ergebnisse zusammengestringt.
           */
          //akt_dbg_string += "EV4J "   + FkString.getFeldLinksMin( getStringIsValid( 6, test_daten_akt_string ), breite_x_feld ) + " ";
          //akt_dbg_string += "HCBval " + FkString.getFeldLinksMin( getStringIsValid( 1, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "Jmail " + FkString.getFeldLinksMin( getStringIsValid( 8, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 1 " + FkString.getFeldLinksMin( getStringIsValid( 2, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 2 " + FkString.getFeldLinksMin( getStringIsValid( 3, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 3 " + FkString.getFeldLinksMin( getStringIsValid( 4, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 4 " + FkString.getFeldLinksMin( getStringIsValid( 9, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "JAVA 1 " + FkString.getFeldLinksMin( getStringIsValid( 5, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "ea234 " + FkString.getFeldLinksMin( getStringIsValid( 7, test_daten_akt_string ), breite_x_feld ) + " ";

          /*
           * Am Ende wird noch der String hinzugefuegt, warum die eMail-Adresse korrekt oder falsch ist. 
           */
          akt_dbg_string += " = " + FkEMail.checkEMailAdresseX( test_daten_akt_string );

          /*
           * Die so aufgebaute Zeile wird in das Log geschrieben
           */
          wl( akt_dbg_string );

          /*
           * Der Index wird erhoeht und mit der naechsten eMail-Adresse aus dem Array weitergemacht.
           */
          test_daten_akt_index++;
        }
      }

      String home_dir = "/home/ea234";

      //home_dir = "c:/Daten/";

      schreibeDatei( home_dir + "/log_test_validate_email.txt", getStringBuffer().toString() );

      /*
       * Alle hinterlegten Validierungsfunktionen aufrufen
       */
      wl( "" );
      wl( "--------------------------------------------------------------------------------------------------------------------------------" );
      wl( "" );
      wl( "Testlauf " + nr + " Anzahl Testfaelle " + FkString.right( "          " + durchlauf_anzahl, stellen_anzahl ) );

      startTestNr( 7, "ea234" );

      startTestNr( 8, "Jmail" );

      startTestNr( 5, "JAVA 1" );

      startTestNr( 2, "REGEXP 1" );

      startTestNr( 3, "REGEXP 2" );

      startTestNr( 4, "REGEXP 3" );

      startTestNr( 9, "REGEXP 4" );

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
              knz_is_valid = isValidEmailAddresseRegEx1a( test_daten_akt_string );
            }
            else if ( pTestNummer == 3 )
            {
              knz_is_valid = isValidEmailAddresseRegEx1b( test_daten_akt_string );
            }
            else if ( pTestNummer == 4 )
            {
              knz_is_valid = isValidEmailAddresseRegExComplicated1( test_daten_akt_string );
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
            else if ( pTestNummer == 9 )
            {
              knz_is_valid = isValidEmailAddresseRegExComplicated2( test_daten_akt_string );
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
      wl( FkString.getFeldLinksMin( pTestBezeichnung, 10 ) + " anzahl_korrekt = " + FkString.right( "          " + anzahl_korrekt, stellen_anzahl ) + " - anzahl_falsch = " + FkString.right( "          " + anzahl_falsch, stellen_anzahl ) + " - anzahl_fehler = " + FkString.right( "          " + anzahl_fehler, stellen_anzahl ) + "  | MS " + FkString.right( "          " + millisekunden_zeit_differenz, stellen_ms ) + " = " + FkString.getFeldLinksMin( number_format.format( ( (double) millisekunden_zeit_differenz ) / durchlauf_anzahl ), 25 ) + "  = " + getStringLaufzeitAusMillisekunden( millisekunden_zeit_differenz ) );
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
        knz_is_valid = isValidEmailAddresseRegEx1a( test_daten_akt_string );
      }
      else if ( pTestNummer == 3 )
      {
        knz_is_valid = isValidEmailAddresseRegEx1b( test_daten_akt_string );
      }
      else if ( pTestNummer == 4 )
      {
        knz_is_valid = isValidEmailAddresseRegExComplicated1( test_daten_akt_string );
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
      else if ( pTestNummer == 9 )
      {
        knz_is_valid = isValidEmailAddresseRegExComplicated2( test_daten_akt_string );
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

  public static boolean isEmailValid( int pTestNummer, String test_daten_akt_string )
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
        knz_is_valid = isValidEmailAddresseRegEx1a( test_daten_akt_string );
      }
      else if ( pTestNummer == 3 )
      {
        knz_is_valid = isValidEmailAddresseRegEx1b( test_daten_akt_string );
      }
      else if ( pTestNummer == 4 )
      {
        knz_is_valid = isValidEmailAddresseRegExComplicated1( test_daten_akt_string );
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

    return knz_is_valid;
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
    Pattern p = Pattern.compile( m_static_laxString );

    Matcher m = p.matcher( email );

    return m.matches();
  }

  private static boolean isValidEmailAddresseRegEx1b( String enteredEmail )
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

  private static boolean isValidEmailAddresseRegExComplicated1( String email )
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

  /*
   * https://www.techiedelight.com/validate-email-address-java/
   */
  private static final String  EMAIL_REGEX   = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

  private static final Pattern EMAIL_PATTERN = Pattern.compile( EMAIL_REGEX );

  private static boolean isValidEmailAddresseRegExComplicated2( String email )
  {
    if ( email == null )
    {
      return false;
    }

    Matcher matcher = EMAIL_PATTERN.matcher( email );
    
    return matcher.matches();
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
