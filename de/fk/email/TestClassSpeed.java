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
class TestClassSpeed
{ 
  /*
   * Testdaten
   * 
   *     0 "A.B@C.DE"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *     1 "A.\"B\"@C.DE"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *     2 "A.B@[1.2.3.4]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *     3 "A.\"B\"@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *     4 "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *     5 "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *     6 "(A)B@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *     7 "A(B)@C.DE"                                                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *     8 "(A)\"B\"@C.DE"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *     9 "\"A\"(B)@C.DE"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *    10 "(A)B@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    11 "A(B)@[1.2.3.4]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    12 "(A)\"B\"@[1.2.3.4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    13 "\"A\"(B)@[1.2.3.4]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  8 = eMail-Adresse korrekt (Kommentar, String, IP4-Adresse)
   *    14 "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    15 "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *    16 "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    17 "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *    18 "firstname.lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    19 "firstname+lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    20 "firstname-lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    21 "first-name-last-name@d-a-n.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    22 "a.b.c.d@domain.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    23 "1@domain.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    24 "a@domain.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    25 "email@domain.co.de"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    26 "email@domain.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    27 "email@subdomain.domain.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    28 "2@bde.cc"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    29 "-@bde.cc"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    30 "a2@bde.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    31 "a-b@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    32 "ab@b-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    33 "a+b@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    34 "f.f.f@bde.cc"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    35 "ab_c@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    36 "_-_@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    37 "w.b.f@test.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    38 "w.b.f@test.museum"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    39 "a.a@test.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    40 "ab@288.120.150.10.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    41 "ab@[120.254.254.120]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    42 "1234567890@domain.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    43 "john.smith@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    44 null                                                                                  Jmail EXP     REGEXP 1 EXP     REGEXP 2 EXP     REGEXP 3 EXP     JAVA 1 EXP     ea234 false    = 10 = Laenge: Eingabe ist null
   *    45 ""                                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 11 = Laenge: Eingabe ist Leerstring
   *    46 "        "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    47 "ABCDEFGHIJKLMNOP"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    48 "ABC.DEF.GHI.JKL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *    49 "@GHI.JKL"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    50 "ABC.DEF@"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 27 = AT-Zeichen: kein AT-Zeichen am Ende
   *    51 "ABC.DEF@@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    52 "@%^%#$@#$@#.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    53 "@domain.com"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    54 "email.domain.com"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *    55 "email@domain@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    56 "ABCDEF@GHIJKL"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    57 "ABC.DEF@GHIJKL"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    58 ".ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *    59 "ABC.DEF@GHI.JKL."                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *    60 "ABC..DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    61 "ABC.DEF@GHI..JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    62 "ABC.DEF@GHI.JKL.."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    63 "ABC.DEF.@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *    64 "ABC.DEF@.GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    65 "ABC.DEF@."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    66 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    67 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    68 "..........@domain."                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *    69 "ABC1.DEF2@GHI3.JKL4"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    70 "ABC.DEF_@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    71 "#ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    72 "ABC.DEF@GHI.JK2"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    73 "ABC.DEF@2HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    74 "ABC.DEF@GHI.2KL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *    75 "ABC.DEF@GHI.JK-"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    76 "ABC.DEF@GHI.JK_"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    77 "ABC.DEF@-HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    78 "ABC.DEF@_HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    79 "ABC DEF@GHI.DE"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    80 "ABC.DEF@GHI DE"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *    81 "A . B & C . D"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    82 " A . B & C . D"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    83 "(?).[!]@{&}.<:>"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *    84 "&local&&name&with&$@amp.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    85 "*local**name*with*@asterisk.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    86 "$local$$name$with$@dollar.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    87 "=local==name=with=@equality.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    88 "!local!!name!with!@exclamation.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    89 "`local``name`with`@grave-accent.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    90 "#local##name#with#@hash.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    91 "-local--name-with-@hypen.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    92 "{local{name{{with{@leftbracket.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    93 "%local%%name%with%@percentage.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    94 "|local||name|with|@pipe.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    95 "+local++name+with+@plus.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    96 "?local??name?with?@question.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    97 "}local}name}}with}@rightbracket.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    98 "~local~~name~with~@tilde.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    99 "^local^^name^with^@xor.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   100 "_local__name_with_@underscore.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   101 ":local::name:with:@colon.com"                                                        Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   102 "domain.part@with-hyphen.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   103 "domain.part@with_underscore.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   104 "domain.part@-starts.with.hyphen.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   105 "domain.part@ends.with.hyphen.com-"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   106 "domain.part@with&amp.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   107 "domain.part@with*asterisk.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   108 "domain.part@with$dollar.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   109 "domain.part@with=equality.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   110 "domain.part@with!exclamation.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   111 "domain.part@with?question.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   112 "domain.part@with`grave-accent.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   113 "domain.part@with#hash.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   114 "domain.part@with%percentage.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   115 "domain.part@with|pipe.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   116 "domain.part@with+plus.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   117 "domain.part@with{leftbracket.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   118 "domain.part@with}rightbracket.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   119 "domain.part@with(leftbracket.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   120 "domain.part@with)rightbracket.com"                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   121 "domain.part@with[leftbracket.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   122 "domain.part@with]rightbracket.com"                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   123 "domain.part@with~tilde.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   124 "domain.part@with^xor.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   125 "domain.part@with:colon.com"                                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   126 "DomainHyphen@-atstart"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   127 "DomainHyphen@atend-.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   128 "DomainHyphen@bb.-cc"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   129 "DomainHyphen@bb.-cc-"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   130 "DomainHyphen@bb.cc-"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   131 "DomainNotAllowedCharacter@/atstart"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   132 "DomainNotAllowedCharacter@a,start"                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   133 "DomainNotAllowedCharacter@atst\art.com"                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   134 "DomainNotAllowedCharacter@exa\mple"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   135 "DomainNotAllowedCharacter@example'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   136 "DomainNotAllowedCharacter@100%.de'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   137 "domain.starts.with.digit@2domain.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   138 "domain.ends.with.digit@domain2.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   139 "tld.starts.with.digit@domain.2com"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   140 "tld.ends.with.digit@domain.com2"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   141 "email@=qowaiv.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   142 "email@plus+.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   143 "email@domain.com>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   144 "email@mailto:domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   145 "mailto:mailto:email@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   146 "email@-domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   147 "email@domain-.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   148 "email@domain.com-"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   149 "email@{leftbracket.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   150 "email@rightbracket}.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   151 "email@pp|e.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   152 "email@domain.domain.domain.com.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   153 "email@domain.domain.domain.domain.com.com"                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   154 "email@domain.domain.domain.domain.domain.com.com"                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   155 "unescaped white space@fake$com"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   156 "\"Joe Smith email@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   157 "\"Joe Smith' email@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   158 "\"Joe Smith\"email@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   159 "Joe Smith &lt;email@domain.com&gt;"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   160 "{john'doe}@my.server"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   161 "email@domain-one.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   162 "_______@domain.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   163 "?????@domain.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   164 "local@?????.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   165 "\"B3V3RLY H1LL$\"@example.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   166 "\"-- --- .. -.\"@sh.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   167 "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   168 "\"\\" + \\"select * from user\\" + \\"\"@example.de"                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   169 "#!$%&'*+-/=?^_`{}|~@eksample.org"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   170 "eksample@#!$%&'*+-/=?^_`{}|~.org"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   171 "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   172 "\"()<>[]:,;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   173 "ABC.DEF@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   174 "ABC.DEF@[001.002.003.004]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   175 "\"ABC.DEF\"@[127.0.0.1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *   176 "ABC.DEF[1.2.3.4]"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   177 "[1.2.3.4]@[5.6.7.8]"                                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   178 "ABC.DEF[@1.2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   179 "\"[1.2.3.4]\"@[5.6.7.8]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   180 "ABC.DEF@MyDomain[1.2.3.4]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   181 "ABC.DEF@[1.00002.3.4]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   182 "ABC.DEF@[1.2.3.456]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   183 "ABC.DEF@[..]"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   184 "ABC.DEF@[.2.3.4]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   185 "ABC.DEF@[]"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   186 "ABC.DEF@[1]"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   187 "ABC.DEF@[1.2]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   188 "ABC.DEF@[1.2.3]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   189 "ABC.DEF@[1.2.3.4.5]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *   190 "ABC.DEF@[1.2.3.4.5.6]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *   191 "ABC.DEF@[MyDomain.de]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   192 "ABC.DEF@[1.2.3.]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *   193 "ABC.DEF@[1.2.3. ]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   194 "ABC.DEF@[1.2.3.4].de"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   195 "ABC.DE@[1.2.3.4][5.6.7.8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   196 "ABC.DEF@[1.2.3.4"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   197 "ABC.DEF@1.2.3.4]"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   198 "ABC.DEF@[1.2.3.Z]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   199 "ABC.DEF@[12.34]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   200 "ABC.DEF@[1.2.3.4]ABC"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   201 "ABC.DEF@[1234.5.6.7]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   202 "ABC.DEF@[1.2...3.4]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   203 "email@[123.123.123.123]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   204 "email@111.222.333"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   205 "email@111.222.333.256"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   206 "email@[123.123.123.123"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   207 "email@[123.123.123].123"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   208 "email@123.123.123.123]"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   209 "email@123.123.[123.123]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   210 "ab@988.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   211 "ab@120.256.256.120"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   212 "ab@120.25.1111.120"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   213 "ab@[188.120.150.10"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   214 "ab@188.120.150.10]"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   215 "ab@[188.120.150.10].com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   216 "ab@188.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   217 "ab@1.0.0.10"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   218 "ab@120.25.254.120"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   219 "ab@01.120.150.1"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   220 "ab@88.120.150.021"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   221 "ab@88.120.150.01"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   222 "email@123.123.123.123"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   223 "ABC.DEF@[IPv6:2001:db8::1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   224 "ABC.DEF@[IP"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   225 "ABC.DEF@[IPv6]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   226 "ABC.DEF@[IPv6:]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   227 "ABC.DEF@[IPv6:1]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   228 "ABC.DEF@[IPv6:1:2]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   229 "ABC.DEF@[IPv6:1:2:3]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   230 "ABC.DEF@[IPv6:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   231 "ABC.DEF@[IPv6:1:2:3:4:5:]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   232 "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   233 "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   234 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   235 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   236 "ABC.DEF@[IPv4:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   237 "ABC.DEF@[I127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   238 "ABC.DEF@[D127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   239 "ABC.DEF@[iPv6:2001:db8::1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   240 "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   241 "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   242 "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   243 "ABC.DEF@[IPv6:12:34]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   244 "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   245 "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   246 "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   247 "ABC.DEF@[IPv6:1:2:3:4:5:6"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   248 "ABC.DEF@[IPv6:12345:6:7:8:9]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   249 "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   250 "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   251 "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   252 "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   253 "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   254 "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   255 "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   256 "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   257 "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   258 "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   259 "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   260 "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   261 "ABC.DEF@[::FFFF:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   262 "ABC.DEF@[::ffff:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   263 "ABC.DEF@[IPv6::ffff:.127.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   264 "ABC.DEF@[IPv6::fff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   265 "ABC.DEF@[IPv6::1234:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   266 "ABC.DEF@[IPv6:127.0.0.1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   267 "ABC.DEF@[IPv6:::127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   268 "\"ABC.DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   269 "\"ABC DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   270 "\"ABC@DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   271 "\"ABC DEF@G\"HI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   272 "\"\"@GHI.DE"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   273 "\"ABC.DEF@G\"HI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   274 "A@G\"HI.DE"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   275 "\"@GHI.DE"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   276 "ABC.DEF.\""                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   277 "ABC.DEF@\"\""                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   278 "ABC.DEF@G\"HI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   279 "ABC.DEF@GHI.DE\""                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   280 "ABC.DEF@\"GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   281 "\"Escape.Sequenz.Ende\""                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   282 "ABC.DEF\"GHI.DE"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   283 "ABC.DEF\"@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   284 "ABC.DE\"F@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   285 "\"ABC.DEF@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   286 "\"ABC.DEF@GHI.DE\""                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   287 "\".ABC.DEF\"@GHI.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   288 "\"ABC.DEF.\"@GHI.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   289 "\"ABC\".DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   290 "A\"BC\".DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   291 "\"ABC\".DEF.G\"HI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   292 "\"AB\"C.DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   293 "\"ABC\".DEF.\"GHI\"J@KL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   294 "\"AB\"C.D\"EF\"@GHI.DE"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   295 "\"Ende.am.Eingabeende\""                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   296 "0\"00.000\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   297 "\"Joe Smith\" email@domain.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   298 "\"Joe\tSmith\" email@domain.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   299 "\"Joe\"Smith\" email@domain.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   300 "(ABC)DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   301 "(ABC) DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   302 "ABC(DEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   303 "ABC.DEF@GHI.JKL(MNO)"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   304 "ABC.DEF@GHI.JKL      (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   305 "ABC.DEF@             (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   306 "ABC.DEF@   .         (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   307 "ABC.DEF              (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   308 "ABC.DEF@GHI.         (MNO)"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   309 "ABC.DEF@GHI.JKL       MNO"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   310 "ABC.DEF@GHI.JKL          "                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   311 "ABC.DEF@GHI.JKL       .  "                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   312 "ABC.DEF@GHI.JKL ()"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   313 "ABC.DEF@GHI.JKL()"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   314 "ABC.DEF@()GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   315 "ABC.DEF()@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   316 "()ABC.DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   317 "(ABC).DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   318 "ABC.(DEF)@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 101 = Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   319 "ABC.DEF@(GHI).JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   320 "ABC.DEF@GHI.(JKL).MNO"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 102 = Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   321 "ABC.DEF@GHI.JK(L.M)NO"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   322 "AB(CD)EF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   323 "AB.(CD).EF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 101 = Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   324 "AB.\"(CD)\".EF@GHI.JKL"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   325 "(ABCDEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 98 = Kommentar: Kein lokaler Part vorhanden
   *   326 "(ABCDEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   327 "(AB\"C)DEF@GHI.JKL"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   328 "(AB\C)DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   329 "(AB\@C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   330 "ABC(DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   331 "ABC.DEF@GHI)JKL"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   332 ")ABC.DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   333 "ABC(DEF@GHI).JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   334 "ABC(DEF.GHI).JKL"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   335 "(ABC.DEF@GHI.JKL)"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 95 = Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   336 "(A(B(C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   337 "(A)B)C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   338 "(A)BCDE(F)@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   339 "ABC.DEF@(GH)I.JK(LM)"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   340 "ABC.DEF@(GH(I.JK)L)M"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   341 "ABC.DEF@(comment)[1.2.3.4]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   342 "ABC.DEF@(comment) [1.2.3.4]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 106 = Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
   *   343 "ABC.DEF@[1.2.3.4](comment)"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   344 "ABC.DEF@[1.2.3.4]    (comment)"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   345 "ABC.DEF@[1.2.3(comment).4]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   346 "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   347 "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   348 "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   349 "(comment)john.smith@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   350 "john.smith(comment)@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   351 "john.smith@(comment)example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   352 "john.smith@example.com(comment)"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   353 "john.smith@exampl(comment)e.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   354 "john.s(comment)mith@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   355 "john.smith(comment)@(comment)example.com"                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   356 "john.smith(com@ment)example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   357 "email( (nested) )@plus.com"                                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   358 "email)mirror(@plus.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   359 "email@plus.com (not closed comment"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   360 "email(with @ in comment)plus.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   361 "email@domain.com (joe Smith)"                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   362 "ABC DEF <ABC.DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   363 "<ABC.DEF@GHI.JKL> ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   364 "ABC DEF ABC.DEF@GHI.JKL>"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   365 "<ABC.DEF@GHI.JKL ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 17 = Struktur: keine schliessende eckige Klammer gefunden.
   *   366 "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   367 "\"ABC<DEF>\"@JKL.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   368 ">"                                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   369 "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   370 "ABC DEF <ABC.<DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   371 "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   372 "ABC DEF <ABC.DEF@GHI.JKL"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   373 "ABC.DEF@GHI.JKL> ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   374 "ABC DEF >ABC.DEF@GHI.JKL<"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   375 ">ABC.DEF@GHI.JKL< ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   376 "ABC DEF <A@A>"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   377 "<A@A> ABC DEF"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   378 "ABC DEF <>"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   379 "<> ABC DEF"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   380 "<ABC.DEF@GHI.JKL>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   381 "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   382 "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   383 "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   384 "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   385 "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   386 "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   387 "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   388 "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   389 "Joe Smith <email@domain.com>"                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   390 "Joe Smith <mailto:email@domain.com>"                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   391 "Test |<gaaf <email@domain.com>"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   392 "\"With extra < within quotes\" Display Name<email@domain.com>"                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   393 "A@B.CD"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   394 "A@B.C"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   395 "A@COM"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   396 "a@bde.c-c"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   397 "MailTo:casesensitve@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   398 "mailto:email@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   399 "email@domain"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   400 "Joe Smith <mailto:email(with comment)@domain.com>"                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   401 "..@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   402 ".a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   403 "ab@sd@dd"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   404 ".@s.dd"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   405 "a@b.-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   406 "a@bde-.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   407 "a@b._de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   408 "a@bde_.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   409 "a@bde.cc."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   410 "ab@b+de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   411 "a..b@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   412 "_@bde.cc,"                                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   413 "plainaddress"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   414 "plain.address"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   415 ".email@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   416 "email.@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   417 "email..email@domain.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   418 "email@.domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   419 "email@domain.com."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   420 "email@domain..com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   421 "Display Name <email@plus.com> (after name with display)"                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   422 "k.haak@12move.nl"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   423 "K.HAAK@12MOVE.NL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   424 "aap.123.noot.mies@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   425 "email@domain.co.jp"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   426 "firstname-lastname@d.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   427 "FIRSTNAME-LASTNAME@d--n.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   428 "email@p|pe.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   429 "isis@100%.nl"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   430 "email@dollar$.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   431 "email@r&amp;d.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   432 "email@#hash.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   433 "email@wave~tilde.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   434 "email@exclamation!mark.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   435 "email@question?mark.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   436 "email@obelix*asterisk.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   437 "email@grave`accent.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   438 "email@colon:colon.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   439 "email@caret^xor.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   440 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   441 "Joe.\\Blow@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   442 "Lat%ss\rtart%s@test.local.part"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   443 "ABC.DEF@GHI.JKL       (MNO)"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   444 "(A)(B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   445 "(A))B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   446 "(ABC DEF) <ABC.DEF@GHI.JKL>"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   447 "(comment and stuff)joe@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   448 "UnclosedComment@a(comment"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   449 "at(start)test@test.local.part"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   450 "example(comment)@test.local.part"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   451 "joe(comment and stuff)@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   452 "joe(fail me)smith@gmail.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   453 "joe@(comment and stuff)gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   454 "joe@gmail.com(comment and stuff"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   455 "joe@gmail.com(comment and stuff)"                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   456 "joesmith@gma(fail me)il.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   457 " a @ []"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   458 " a @[ ] "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   459 " a @[ ]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   460 " a @[] "                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   461 " a @[]"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   462 " a @a[].com "                                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   463 "ABC.DEF@[ .2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   464 "ABC.DEF@[0.0.0.0]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   465 "ABC.DEF@[000.000.000.000]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   466 "ABC.DEF@[001.012.123.255]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   467 "ABC.DEF@[1..123.255]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   468 "ABC.DEF@[1.12.123.255]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   469 "ABC.DEF@[1.12.123.255].de"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   470 "ABC.DEF@[1.12.123.259]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   471 "ABC.DEF@[1.123.25.]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *   472 "ABC.DEF@[1.2.3.4] "                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   473 "ABC.DEF@[1.2.3.4][5.6.7.8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   474 "ABC.DEF@[1.2.]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   475 "ABC.DEF@[1:2:3:4:5:6:7:8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   476 "ABC.DEF@[::ffff:127.0:0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   477 "ABC.DEF@[IPv61:2:3:4:5:6]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   478 "ABC.DEF@[IPv6:00:ffff:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   479 "ABC.DEF@[IPv6:0:0:ffff:127.0.0.1]"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   480 "ABC.DEF@[IPv6:12:ffff:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   481 "ABC.DEF@[IPv6:1:2:(3::5):6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   482 "ABC.DEF@[IPv6:1:2:(3::5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   483 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8 ]"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   484 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   485 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   486 "ABC.DEF@[IPv6:1:2:3:4:5:6] "                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   487 "ABC.DEF@[IPv6:1:2:3:4:5]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   488 "ABC.DEF@[IPv6:1:2:3::5:8].de"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   489 "ABC.DEF@[IPv6:1:2:ffff:127.0.0.1]"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   490 "ABC.DEF@[IPv6::abcd:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   491 "ABC.DEF@[IPv6::fff:999.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   492 "ABC.DEF@[IPv6::ffff:.127.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   493 "ABC.DEF@[IPv6::ffff:1211.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   494 "ABC.DEF@[IPv6::ffff:12111.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   495 "ABC.DEF@[IPv6::ffff:127.0:0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   496 "ABC.DEF@[IPv6::ffff:127A.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   497 "ABC.DEF@[IPv6::ffff:999.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   498 "ABC.DEF@[IPv6::ffff:fff.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   499 "ABC.DEF@[IPv6::fffff:127.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   500 "ABC.DEF@[IPv6:a:b:c:d:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   501 "ABC.DEF@[IPv6:a:b:c:d:e:f:1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   502 "ABC.DEF@[IPv6:ffff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   503 "ABC.DEF@[IPv6]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   504 "ABC.DEF@[[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   505 "ABC.DEF[1.12.123.255]"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   506 "ABC[@IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   507 "( a @[]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   508 "(ABC DEF) <(Comment)ABC.DEF@[iPv6:2001:db8::1]>"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   509 "ExpectedATEXT@at[start"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   510 "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   511 "Test.IPv6@[IPv6:1::1::1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   512 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]"                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   513 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   514 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]"                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   515 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]"                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   516 "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   517 "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   518 "Test.IPv6@[[127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   519 "Test.IPv6@[\n]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   520 "\"ABC\"@[IPv4:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   521 "\"ABC\"@[IPv6:1234:5678]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   522 "\"ABC\"@[IPv6:1234]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   523 "\"ABC\"@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *   524 "\"\"@[]"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   525 "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   526 "jsmith@[IPv6:2001:db8::1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   527 "user@[192.168.2.1]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   528 "user@[IPv6:2001:db8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   529 "UnclosedDomainLiteral@example]"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   530 "ABC DEF <( )@ >"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   531 "ABC DEF <(COMMENT)A@ >"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   532 "ABC(DEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   533 "ABC.DEF@(GHI).JKL.MNO"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   534 "ABC.DEF@(GHI)JKL.MNO"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   535 "\"@\".A(@)@a.aa"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *   536 "ABC+DEF) <ABC.DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   537 "\"ABC<DEF>\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   538 "\"ABC<DEF@GHI.COM>\"@GHI.JKL"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   539 "<ABC.DEF@GHI.JKL>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   540 "ABC DEF < >"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   541 "ABC DEF < @ >"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   542 "ABC DEF <ABC<DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   543 "0\"00.000\"@wc.de"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   544 "@G\"HI.DE"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   545 "ABC.DEF@GHI.JKL\""                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   546 "ABC.DEF@\"GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   547 "ABC.DEF\"@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   548 "ABC.DEF\"GHI.JKL"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   549 "ABC.DE\"F@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   550 "A\"BC\".DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   551 "DomainNotAllowedCharacter@example\"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   552 "\" \"@GHI.JKL"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   553 "\" \"@example.org"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   554 "\"%2\"@gmail.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   555 "\".ABC.DEF\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   556 "\"@GHI.JKL"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   557 "\"ABC DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   558 "\"ABC.DEF.\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   559 "\"ABC.DEF@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   560 "\"ABC.DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   561 "\"ABC.D\"EF@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   562 "\"ABC@DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   563 "\"ABC\".DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   564 "\"ABC\"\".DEF.\"GHI\"@GHI.JKL"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   565 "\"AB\"C.DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   566 "\"AB\"C.D\"EF@GHI.JKL"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   567 "\"AB\"C.D\"EF\"@GHI.JKL"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   568 "\"Falsch.da.Escape.Zeichen.am.Ende.steht\"                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 83 = String: Escape-Zeichen nicht am Ende der Eingabe
   *   569 "\"Falsche\#Escape\GSequenz\"@falsch.de"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   570 "\"Gueltige\\"Escape\\Sequenz\"@korrekt.de"                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   571 "\"\"@GHI.JKL"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   572 "\"a..b\"@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   573 "\"a_b\"@gmail.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   574 "\"abcdefghixyz\"@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   575 "abc.\"defghi\".xyz@example.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   576 "abc\"defghi\"xyz@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   577 "abc\\"def\\"ghi@eggsample.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   578 "at\"start\"test@test.local.part"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   579 "just\"not\"right@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   580 "this is\"not\allowed@example.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   581 "this\ still\\"not\\allowed@example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   582 "               "                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   583 "..@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   584 ".a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   585 "ab@sd@dd"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   586 ".@s.dd"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   587 "a@b.-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   588 "a@bde-.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   589 "a@b._de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   590 "a@bde_.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   591 "a@bde.cc."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   592 "ab@b+de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   593 "a..b@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   594 "_@bde.cc,"                                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   595 "plainaddress"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   596 "plain.address"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   597 ".email@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   598 "email.@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   599 "email..email@domain.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   600 "email@.domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   601 "email@domain.com."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   602 "email@domain..com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   603 "MailTo:casesensitve@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   604 "mailto:email@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   605 "email@domain"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   606 "someone@somewhere.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   607 "someone@somewhere.co.uk"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   608 "someone+tag@somewhere.net"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   609 "futureTLD@somewhere.fooo"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   610 "fdsa"                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   611 "fdsa@"                                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   612 "fdsa@fdsa"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   613 "fdsa@fdsa."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   614 "Foobar Some@thing.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   615 "david.jones@proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   616 "d.j@server1.proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   617 "jones@ms1.proseware.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   618 "j.@server1.proseware.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   619 "j@proseware.com9"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   620 "js#internal@proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   621 "j_9@[129.126.118.1]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   622 "j..s@proseware.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   623 "js*@proseware.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   624 "js@proseware..com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   625 "js@proseware.com9"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   626 "j.s@server1.proseware.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   627 "\"j\\"s\"@proseware.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   628 "cog@wheel.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   629 "\"cogwheel the orange\"@example.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   630 "123@$.xyz"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   631 "dasddas-@.com"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   632 "-asd@das.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   633 "as3d@dac.coas-"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   634 "dsq!a?@das.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   635 "_dasd@sd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   636 "dad@sds"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   637 "asd-@asd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   638 "dasd_-@jdas.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   639 "asd@dasd@asd.cm"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   640 "da23@das..com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   641 "_dasd_das_@9.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   642 "d23d@da9.co9"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   643 "dasd.dadas@dasd.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   644 "dda_das@das-dasd.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   645 "dasd-dasd@das.com.das"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   646 "@b.com"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   647 "a@.com"                                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   648 "a@bcom"                                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   649 "a.b@com"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   650 "a@b."                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   651 "ab@c.com"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   652 "a@bc.com"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   653 "a@b.com"                                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   654 "a@b.c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   655 "a+b@c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   656 "a@123.45.67.89"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   657 "%2@gmail.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   658 "_@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   659 "1@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   660 "1_example@something.gmail.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   661 "d._.___d@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   662 "d.oy.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   663 "d_oy_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   664 "doysmith@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   665 "D.Oy'Smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   666 "%20f3v34g34@gvvre.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   667 "piskvor@example.lighting"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   668 "--@ooo.ooo"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   669 "foo@bar@machine.subdomain.example.museum"                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   670 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   671 "foo.bar@machine.sub\@domain.example.museum"                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   672 "check@thiscom"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   673 "check@this..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   674 " check@this.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   675 "check@this.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   676 "Abc@example.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   677 "Abc@example.com."                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   678 "Abc@10.42.0.1"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   679 "user@localserver"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   680 "Abc.123@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   681 "user+mailbox/department=shipping@example.com"                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   682 "Loïc.Accentué@voilà.fr"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   683 "user@[IPv6:2001:DB8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   684 "Abc.example.com"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   685 "A@b@c@example.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   686 "this\ still\\"not\allowed@example.com"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   687 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   688 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   689 "email@example.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   690 "email@example.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   691 "email@mail.gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   692 "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   693 "email@example.co.uk."                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   694 "email@example"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   695 " email@example.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   696 "email@example.com "                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   697 "email@example,com "                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   698 "invalid.email.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   699 "invalid@email@domain.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   700 "email@example..com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   701 "yoursite@ourearth.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   702 "my.ownsite@ourearth.org"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   703 "mysite@you.me.net"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   704 "xxxx@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   705 "xxxxxx@yahoo.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   706 "xxxx.ourearth.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   707 "xxxx@.com.my"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   708 "@you.me.net"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   709 "xxxx123@gmail.b"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   710 "xxxx@.org.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   711 ".xxxx@mysite.org"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   712 "xxxxx()*@gmail.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   713 "xxxx..1234@yahoo.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   714 "alex@example.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   715 "alireza@test.co.uk"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   716 "peter.example@yahoo.com.au"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   717 "peter_123@news.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   718 "hello7___@ca.com.pt"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   719 "example@example.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   720 "hallo@example.coassjj#sswzazaaaa"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   721 "hallo2ww22@example....caaaao"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   722 "abcxyz123@qwert.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   723 "abc123xyz@asdf.co.in"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   724 "abc1_xyz1@gmail1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   725 "abc.xyz@gmail.com.in"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   726 "pio_pio@factory.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   727 "~pio_pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   728 "pio_~pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   729 "pio_#pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   730 "pio_pio@#factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   731 "pio_pio@factory.c#om"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   732 "pio_pio@factory.c*om"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   733 "pio^_pio@factory.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   734 "\"Abc\@def\"@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   735 "\"Fred Bloggs\"@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   736 "\"Fred\ Bloggs\"@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   737 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   738 "\"Joe\Blow\"@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   739 "\"Joe\\Blow\"@example.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   740 "\"Abc@def\"@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   741 "customer/department=shipping@example.com"                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   742 "\$A12345@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   743 "!def!xyz%abc@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   744 "_somename@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   745 "abc\\"def\\"ghi@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   746 " joe@gmail.com"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   747 "$ABCDEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   748 ".ann..other.@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   749 "000.000@000.de"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   750 "0test@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   751 "1234567890@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   752 "1234@5678.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   753 "1234@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   754 "A@A.AA"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   755 "ABC DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   756 "ABC-DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   757 "ABC/DEF=GHI@JKL.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   758 "ABCDEFGHIJKLMNO"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   759 "ABC\@DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   760 "AT-Character"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   761 "ATEXTAfterCFWS@test\r\n at"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   762 "Abc\@def@ecksample.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   763 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   764 "CRLFAtEnd@test\r\nat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   765 "CRWithoutLF@test\rat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   766 "ConsecutiveAT@@start"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   767 "ConsecutiveCRLF@test\r\n\r\nat"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   768 "ConsecutiveDots@at..start"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   769 "DomainHyphen@atstart-.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   770 "DotAtStart@.atstart"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   771 "Drei*Vier@Ist.Zwoelf.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   772 "ExpectedATEXT@;atstart"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   773 "ExpectedCTEXT@test\r\n \n"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   774 "Length"                                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   775 "No Input"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   776 "Pointy Brackets"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   777 "Seperator"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   778 "Strings"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   779 "TEST.TEST@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   780 "TEST@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   781 "Test.Domain.Part@1leadingnumber.example.co"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   782 "Test.Domain.Part@1leadingnumber.example.com"                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   783 "Test.Domain.Part@aampleEx.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   784 "Test.Domain.Part@example.co"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   785 "Test.Domain.Part@has-hyphen.example.co"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   786 "Test.Domain.Part@has-hyphen.example.com"                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   787 "Test.Domain.Part@subdomain.example.co"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   788 "Test.Domain.Part@subdomain.example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   789 "Test.Test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   790 "_______@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   791 "admin@mailserver1"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   792 "alirheza@test.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   793 "ann.other@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   794 "d@@.com"                                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   795 "email@192.0.2.123"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   796 "email@example,com"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   797 "email@example.name .name"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   798 "example@localhost"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   799 "example@xyz.solutions"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   800 "fdsa@fdza"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   801 "fdsa@fdza."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   802 "joe@gmail.com "                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   803 "jsmith@whizbang.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   804 "jsr@prhoselware.com9"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   805 "lots.of.iq@sagittarius.A*"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   806 "me+100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   807 "me-100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   808 "me-100@yahoo-test.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   809 "me..2002@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   810 "me.100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   811 "me.@gmail.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   812 "me12345@that.is"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   813 "me123@%*.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   814 "me123@%.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   815 "me123@.com"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   816 "me123@.com.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   817 "me@.com.my"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   818 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   819 "me@me.co.uk"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   820 "me@me@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   821 "me@yahoo.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   822 "peter_123@news24.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   823 "prettyandsimple@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   824 "test test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   825 "test%test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   826 "test+test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   827 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   828 "test.test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   829 "test0@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   830 "test@GMAIL.COM"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   831 "test@Gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   832 "test@anothersub.sub-domain.gmail.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   833 "test@gmail"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   834 "test@gmail."                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   835 "test@gmail..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   836 "test@gmail.Com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   837 "test@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   838 "test@sub-domain.000.0"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   839 "test@sub-domain.000.0rg"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   840 "test@sub-domain.000.co-om"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   841 "test@sub-domain.000.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   842 "test@sub-domain.gmail.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   843 "test@subdomain.gmail.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   844 "test@test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   845 "test_test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   846 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   847 "user@com"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   848 "user@localhost"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   849 "very.common@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   850 "x._._y__z@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   851 "x.yz.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   852 "x_yz_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   853 "xyz@blabla.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   854 null                                                                                  Jmail EXP     REGEXP 1 EXP     REGEXP 2 EXP     REGEXP 3 EXP     JAVA 1 EXP     ea234 false    = 10 = Laenge: Eingabe ist null
   *   855 ""                                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 11 = Laenge: Eingabe ist Leerstring
   *   856 "    "                                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   857 "ABC.DEF@GHI.J"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   858 "email@domain.topleveldomain"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   859 "unknown errors"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   860 "local@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   861 "MailTo:casesensitve@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   862 "mailto:email@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   863 "ab@188.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   864 "ab@1.0.0.10"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   865 "ab@120.25.254.120"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   866 "ab@01.120.150.1"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   867 "ab@88.120.150.021"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   868 "ab@88.120.150.01"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   869 "email@123.123.123.123"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   870 "email@domain"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   871 "unsorted"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   872 "..@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   873 ".a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   874 "ab@sd@dd"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   875 ".@s.dd"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   876 "withdot.@test.local.part"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   877 "domain.starts.with.digit@2domain.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   878 "domain.ends.with.digit@domain2.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   879 "aHZs...Ym8iZXJn@YWRtAW4g.au"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   880 "\"RmF0aGlh\"@SXp6YXRp.id"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   881 "\"hor\ror\"@nes.si"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   882 "!yoora@an.yang.ha.se.yo"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   883 "084105111046077097110105107@hello.again.id"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   884 "@@@@@@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   885 "somecahrs@xyz.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   886 "user@host.network"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   887 "bob.mcspam@ABCD.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   888 "something@domain.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   889 "emailString@email.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   890 "person@registry.organization"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   891 "foo.bar.\"bux\".bar.com@baz.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   892 "someStringThatMightBe@email.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   893 "100$-30%=130$-(x*3pi)@MATH.MAGIC"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   894 "\"much.more unusual\"@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   895 "other.email-with-dash@eksample.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   896 "Find#Me@NotesDocumentCollection.de"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   897 "InvalidEmail@notreallyemailbecausenosuffix"                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   898 "\"very.unusual.@.unusual.com\"@example.com"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   899 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   900 "disposable.style.email.with+symbol@example.com"                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   901 "0=1+e^(i*pi)@Gleich.Zahl.Phi.Goldener.Schnitt.aua"                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   902 "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   903 "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123"         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   904 "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A"        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 15 = Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
   *   905 "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   906 "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   907 "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   908 "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   909 "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   910 "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   911 "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   912 "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   913 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   914 "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   915 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   916 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL"     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   917 "1234567890123456789012345678901234567890123456789012345678901234+x@example.com"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   918 "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com"       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   919 "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   920 "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   921 "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   922 "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   923 "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   924 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com"              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   925 "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   926 "zzzz.zzzzzz@zzzzzzzzzz.zz"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   927 "AAAA.AAAAAA@AAAAAAAAAA.AA"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   928 "aaaa.aaaaaa@aaaaaaaaaa.aa"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   929 "Vorname.Nachname@web.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   930 "michi.fanin@fc-wohlenegg.at"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   931 "old.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   932 "new.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   933 "test1group@test1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   934 "test2group@test2.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   935 "test1@test1.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   936 "test2@test2.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   937 "junit.testEmailChange@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   938 "at@at.at"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   939 "easy@isnt.it"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   940 "yes@it.is"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 1 Anzahl Testfaelle  43500000
   * 
   * ea234      anzahl_korrekt =  26520000 - anzahl_falsch =  16980000 - anzahl_fehler =         0  | MS   5603 = 0.00012880459770114942     = 00:00:05:603
   * 
   * Jmail      anzahl_korrekt =  36810000 - anzahl_falsch =   6630000 - anzahl_fehler =     60000  | MS   7267 = 0.0001670574712643678      = 00:00:07:267
   * 
   * JAVA 1     anzahl_korrekt =  22680000 - anzahl_falsch =  20760000 - anzahl_fehler =     60000  | MS  11581 = 0.00026622988505747126     = 00:00:11:581
   * 
   * REGEXP 1   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  30656 = 0.000704735632183908       = 00:00:30:656
   * 
   * REGEXP 2   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  13404 = 0.00030813793103448274     = 00:00:13:404
   * 
   * REGEXP 3   anzahl_korrekt =  23940000 - anzahl_falsch =  19500000 - anzahl_fehler =     60000  | MS  76763 = 0.0017646666666666668      = 00:01:16:763
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 2 Anzahl Testfaelle  43500000
   * 
   * ea234      anzahl_korrekt =  26520000 - anzahl_falsch =  16980000 - anzahl_fehler =         0  | MS   5331 = 0.00012255172413793103     = 00:00:05:331
   * 
   * Jmail      anzahl_korrekt =  36810000 - anzahl_falsch =   6630000 - anzahl_fehler =     60000  | MS   6933 = 0.00015937931034482757     = 00:00:06:933
   * 
   * JAVA 1     anzahl_korrekt =  22680000 - anzahl_falsch =  20760000 - anzahl_fehler =     60000  | MS  11088 = 0.00025489655172413795     = 00:00:11:088
   * 
   * REGEXP 1   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  29784 = 0.0006846896551724138      = 00:00:29:784
   * 
   * REGEXP 2   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  13433 = 0.0003088045977011494      = 00:00:13:433
   * 
   * REGEXP 3   anzahl_korrekt =  23940000 - anzahl_falsch =  19500000 - anzahl_fehler =     60000  | MS  78253 = 0.001798919540229885       = 00:01:18:253
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 3 Anzahl Testfaelle  43500000
   * 
   * ea234      anzahl_korrekt =  26520000 - anzahl_falsch =  16980000 - anzahl_fehler =         0  | MS   5338 = 0.00012271264367816093     = 00:00:05:338
   * 
   * Jmail      anzahl_korrekt =  36810000 - anzahl_falsch =   6630000 - anzahl_fehler =     60000  | MS   6906 = 0.00015875862068965516     = 00:00:06:906
   * 
   * JAVA 1     anzahl_korrekt =  22680000 - anzahl_falsch =  20760000 - anzahl_fehler =     60000  | MS  10929 = 0.00025124137931034483     = 00:00:10:929
   * 
   * REGEXP 1   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  29585 = 0.0006801149425287356      = 00:00:29:585
   * 
   * REGEXP 2   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  13531 = 0.0003110574712643678      = 00:00:13:531
   * 
   * REGEXP 3   anzahl_korrekt =  23940000 - anzahl_falsch =  19500000 - anzahl_fehler =     60000  | MS  74779 = 0.0017190574712643678      = 00:01:14:779
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 4 Anzahl Testfaelle  43500000
   * 
   * ea234      anzahl_korrekt =  26520000 - anzahl_falsch =  16980000 - anzahl_fehler =         0  | MS   5276 = 0.00012128735632183908     = 00:00:05:276
   * 
   * Jmail      anzahl_korrekt =  36810000 - anzahl_falsch =   6630000 - anzahl_fehler =     60000  | MS   6947 = 0.00015970114942528735     = 00:00:06:947
   * 
   * JAVA 1     anzahl_korrekt =  22680000 - anzahl_falsch =  20760000 - anzahl_fehler =     60000  | MS  11052 = 0.0002540689655172414      = 00:00:11:052
   * 
   * REGEXP 1   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  29542 = 0.0006791264367816092      = 00:00:29:542
   * 
   * REGEXP 2   anzahl_korrekt =  31320000 - anzahl_falsch =  12120000 - anzahl_fehler =     60000  | MS  13522 = 0.0003108505747126437      = 00:00:13:522
   * 
   * REGEXP 3   anzahl_korrekt =  23940000 - anzahl_falsch =  19500000 - anzahl_fehler =     60000  | MS  73382 = 0.0016869425287356322      = 00:01:13:382   * 
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

  private static int                  FAKTOR_ANZAHL_ARRAY_A    =30000 ;

  /*
   * Array, mit welchem die Tests ausgefuehrt werden.
   */
  private static String[]             array_test_daten_aktuell = null;

  private static String str_50 = "12345678901234567890123456789012345678901234567890";
  
  private static  String str_63 = "A23456789012345678901234567890123456789012345678901234567890123";

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
    "Afom.iqfsekdw@vjqtisdfwmj.jdg", "Tlufsbzxba.gfueqlaa@ugclozu.yv", "wedr.eebtsgb@jmndsdfewr3434v.ashf333vdus.bs", "eaflfnkyk.Bnpblitoofoc@izfkujik.zyvgv.ju", "bkkdo-sdasdffwR@gfdb.asfdsdf.tetm", "Paadf.klddsksdfP@gkjmwfa.sdfno.zr", "dnbmdt.Elkyfg.swxsbrabl@lxhh.drgo.adyk", "eotamr-slkdffxlaS@TssfzvO.cwd", "And.To.all.you.Reg.Ex.eMail.Validator.offionardos.out.there@remember.the.soundtrack.is", "I.just.call.to.say.i.hate.you@ksdlfje.ksx", "DANKE@terima.kasih.com",
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
      
      //home_dir = "c:/Daten/";
      
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
          akt_dbg_string += FkString.right(  "          " + test_daten_akt_index, 4 ) + " " + FkString.getFeldLinksMin( FkString.getJavaString( test_daten_akt_string ), 85 ) + " ";

          /*
           * Jede Testfunktion wird einmal aufgerufen und die Ergebnisse zusammengestringt.
           */
          //akt_dbg_string += "EV4J "   + FkString.getFeldLinksMin( getStringIsValid( 6, test_daten_akt_string ), breite_x_feld ) + " ";
          //akt_dbg_string += "HCBval " + FkString.getFeldLinksMin( getStringIsValid( 1, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "Jmail "    + FkString.getFeldLinksMin( getStringIsValid( 8, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 1 " + FkString.getFeldLinksMin( getStringIsValid( 2, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 2 " + FkString.getFeldLinksMin( getStringIsValid( 3, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "REGEXP 3 " + FkString.getFeldLinksMin( getStringIsValid( 4, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "JAVA 1 "   + FkString.getFeldLinksMin( getStringIsValid( 5, test_daten_akt_string ), breite_x_feld ) + " ";
          akt_dbg_string += "ea234 "    + FkString.getFeldLinksMin( getStringIsValid( 7, test_daten_akt_string ), breite_x_feld ) + " ";

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
      int anzahl_falsch  = 0; // Anzahl der von der Testroutine als falsch gemeldeten eMail-Adressen
      int anzahl_fehler  = 0; // Anzahl der aufgetretenen Exceptions waehrend der Pruefung

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

    return knz_is_valid;
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
