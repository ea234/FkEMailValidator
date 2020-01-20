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
   *    18 "2@bde.cc"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    19 "-@bde.cc"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    20 "a2@bde.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    21 "a-b@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    22 "ab@b-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    23 "a+b@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    24 "f.f.f@bde.cc"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    25 "ab_c@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    26 "_-_@bde.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    27 "k.haak@12move.nl"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    28 "K.HAAK@12MOVE.NL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    29 "email@domain.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    30 "w.b.f@test.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    31 "w.b.f@test.museum"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    32 "a.a@test.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    33 "ab@288.120.150.10.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    34 "ab@[120.254.254.120]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *    35 "firstname.lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    36 "email@subdomain.domain.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    37 "firstname+lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    38 "1234567890@domain.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    39 "a@domain.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    40 "a.b.c.d@domain.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    41 "aap.123.noot.mies@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    42 "1@domain.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    43 "email@domain.co.jp"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    44 "firstname-lastname@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    45 "firstname-lastname@d.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    46 "FIRSTNAME-LASTNAME@d--n.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    47 "first-name-last-name@d-a-n.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    48 "john.smith@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    49 "ABCDEFGHIJKLMNOP"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    50 "ABC.DEF.GHI.JKL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *    51 "@GHI.JKL"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    52 "ABC.DEF@"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 27 = AT-Zeichen: kein AT-Zeichen am Ende
   *    53 "ABC.DEF@@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    54 "@%^%#$@#$@#.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    55 "@domain.com"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *    56 "email.domain.com"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *    57 "email@domain@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    58 "ABCDEF@GHIJKL"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    59 "ABC.DEF@GHIJKL"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    60 ".ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *    61 "ABC.DEF@GHI.JKL."                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *    62 "ABC..DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    63 "ABC.DEF@GHI..JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    64 "ABC.DEF@GHI.JKL.."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *    65 "ABC.DEF.@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *    66 "ABC.DEF@.GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    67 "ABC.DEF@."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *    68 "ABC1.DEF2@GHI3.JKL4"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    69 "ABC.DEF_@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    70 "#ABC.DEF@GHI.JKL"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    71 "ABC.DEF@GHI.JK2"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    72 "ABC.DEF@2HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    73 "ABC.DEF@GHI.2KL"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *    74 "ABC.DEF@GHI.JK-"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    75 "ABC.DEF@GHI.JK_"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *    76 "ABC.DEF@-HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    77 "ABC.DEF@_HI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *    78 "ABC DEF@GHI.DE"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    79 "A . B & C . D"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *    80 "(?).[!]@{&}.<:>"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *    81 "{local{name{{with{@leftbracket.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    82 "}local}name}}with{@rightbracket.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    83 "|local||name|with|@pipe.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    84 "%local%%name%with%@percentage.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    85 "$local$$name$with$@dollar.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    86 "&local&&name&with&$@amp.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    87 "ABC.DEF##name#with#@hash.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    88 "~local~~name~with~@tilde.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    89 "!local!!name!with!@exclamation.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    90 "?local??name?with?@question.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    91 "*local**name*with*@asterisk.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    92 "`local``name`with`@grave-accent.com"                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    93 "^local^^name^with^@xor.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    94 "=local==name=with=@equality.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    95 "+local++name+with+@equality.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *    96 "\"B3V3RLY H1LL$\"@example.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    97 "\"-- --- .. -.\"@sh.de"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *    98 "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *    99 "#!$%&'*+-/=?^_`{}|~@eksample.org"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   100 "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   101 "\"\\" + \\"select * from user\\" + \\"\"@example.de"                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   102 "\"()<>[]:,;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   103 "email@{leftbracket.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   104 "email@rightbracket}.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   105 "email@p|pe.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   106 "isis@100%.nl"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   107 "email@dollar$.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   108 "email@r&amp;d.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   109 "email@#hash.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   110 "email@wave~tilde.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   111 "email@exclamation!mark.com"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   112 "email@question?mark.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   113 "email@obelix*asterisk.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   114 "email@grave`accent.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   115 "email@colon:colon.com"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   116 "email@caret^xor.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   117 "email@=qowaiv.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   118 "email@plus+.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   119 "email@domain.com>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   120 "email@mailto:domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   121 "mailto:mailto:email@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   122 "email@-domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   123 "email@domain-.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   124 "email@domain.com-"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   125 "Joe Smith &lt;email@domain.com&gt;"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   126 "?????@domain.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   127 "local@?????.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   128 "email@domain-one.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   129 "_______@domain.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   130 "Fred\ Bloggs@example.com"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   131 "Joe.\\Blow@example.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   132 "Lat%ss\rtart%s@test.local.part"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   133 "ABC.DEF@[1.2.3.4]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   134 "ABC.DEF@[001.002.003.004]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   135 "\"ABC.DEF\"@[127.0.0.1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  3 = eMail-Adresse korrekt (Local Part mit String und IP4-Adresse)
   *   136 "ABC.DEF[1.2.3.4]"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   137 "[1.2.3.4]@[5.6.7.8]"                                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   138 "ABC.DEF[@1.2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   139 "\"[1.2.3.4]\"@[5.6.7.8]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   140 "ABC.DEF@MyDomain[1.2.3.4]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   141 "ABC.DEF@[1.00002.3.4]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   142 "ABC.DEF@[1.2.3.456]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   143 "ABC.DEF@[..]"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   144 "ABC.DEF@[.2.3.4]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   145 "ABC.DEF@[]"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   146 "ABC.DEF@[1]"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   147 "ABC.DEF@[1.2]"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   148 "ABC.DEF@[1.2.3]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   149 "ABC.DEF@[1.2.3.4.5]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *   150 "ABC.DEF@[1.2.3.4.5.6]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 56 = IP4-Adressteil: zu viele Trennzeichen
   *   151 "ABC.DEF@[MyDomain.de]"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   152 "ABC.DEF@[1.2.3.]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *   153 "ABC.DEF@[1.2.3. ]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   154 "ABC.DEF@[1.2.3.4].de"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   155 "ABC.DE@[1.2.3.4][5.6.7.8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   156 "ABC.DEF@[1.2.3.4"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   157 "ABC.DEF@1.2.3.4]"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   158 "ABC.DEF@[1.2.3.Z]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   159 "ABC.DEF@[12.34]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   160 "ABC.DEF@[1.2.3.4]ABC"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   161 "ABC.DEF@[1234.5.6.7]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 53 = IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *   162 "ABC.DEF@[1.2...3.4]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   163 "email@[123.123.123.123]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   164 "email@111.222.333"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   165 "email@111.222.333.256"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   166 "email@[123.123.123.123"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   167 "email@[123.123.123].123"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   168 "email@123.123.123.123]"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   169 "email@123.123.[123.123]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   170 "ab@988.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   171 "ab@120.256.256.120"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   172 "ab@120.25.1111.120"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   173 "ab@[188.120.150.10"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   174 "ab@188.120.150.10]"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   175 "ab@[188.120.150.10].com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   176 "ABC.DEF@[IPv6:2001:db8::1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   177 "ABC.DEF@[IP"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   178 "ABC.DEF@[IPv6]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   179 "ABC.DEF@[IPv6:]"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   180 "ABC.DEF@[IPv6:1]"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   181 "ABC.DEF@[IPv6:1:2]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   182 "ABC.DEF@[IPv6:1:2:3]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   183 "ABC.DEF@[IPv6:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   184 "ABC.DEF@[IPv6:1:2:3:4:5:]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   185 "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   186 "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   187 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   188 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   189 "ABC.DEF@[IPv4:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   190 "ABC.DEF@[I127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   191 "ABC.DEF@[D127.0.0.1]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   192 "ABC.DEF@[iPv6:2001:db8::1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   193 "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   194 "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   195 "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   196 "ABC.DEF@[IPv6:12:34]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   197 "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   198 "ABC.DEF@[IPv6:1:2:3:4:5:6"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   199 "ABC.DEF@[IPv6:12345:6:7:8:9]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   200 "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   201 "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   202 "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   203 "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   204 "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   205 "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   206 "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   207 "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   208 "ABC.DEF@[::FFFF:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   209 "ABC.DEF@[::ffff:127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   210 "ABC.DEF@[IPv6::ffff:.127.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   211 "ABC.DEF@[IPv6::fff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   212 "ABC.DEF@[IPv6::1234:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   213 "ABC.DEF@[IPv6:127.0.0.1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   214 "ABC.DEF@[IPv6:::127.0.0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   215 "\"ABC.DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   216 "\"ABC DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   217 "\"ABC@DEF\"@GHI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   218 "\"ABC DEF@G\"HI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   219 "\"\"@GHI.DE"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   220 "\"ABC.DEF@G\"HI.DE"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   221 "A@G\"HI.DE"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   222 "\"@GHI.DE"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   223 "ABC.DEF.\""                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   224 "ABC.DEF@\"\""                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   225 "ABC.DEF@G\"HI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   226 "ABC.DEF@GHI.DE\""                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   227 "ABC.DEF@\"GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   228 "\"Escape.Sequenz.Ende\""                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   229 "ABC.DEF\"GHI.DE"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   230 "ABC.DEF\"@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   231 "ABC.DE\"F@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   232 "\"ABC.DEF@GHI.DE"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   233 "\"ABC.DEF@GHI.DE\""                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   234 "\".ABC.DEF\"@GHI.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   235 "\"ABC.DEF.\"@GHI.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   236 "\"ABC\".DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   237 "A\"BC\".DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   238 "\"ABC\".DEF.G\"HI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   239 "\"AB\"C.DEF.\"GHI\"@JKL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   240 "\"ABC\".DEF.\"GHI\"J@KL.de"                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   241 "\"AB\"C.D\"EF\"@GHI.DE"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   242 "\"Ende.am.Eingabeende\""                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 88 = String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   243 "0\"00.000\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   244 "(ABC)DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   245 "(ABC) DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   246 "ABC(DEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   247 "ABC.DEF@GHI.JKL(MNO)"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   248 "ABC.DEF@GHI.JKL       (MNO)"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   249 "ABC.DEF@GHI.JKL       MNO"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   250 "ABC.DEF@GHI.JKL          "                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   251 "ABC.DEF@GHI.JKL       .  "                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   252 "ABC.DEF@GHI.JKL ()"                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   253 "ABC.DEF@GHI.JKL()"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   254 "ABC.DEF@()GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   255 "ABC.DEF()@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   256 "()ABC.DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   257 "(ABC).DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   258 "ABC.(DEF)@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 101 = Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   259 "ABC.DEF@(GHI).JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   260 "ABC.DEF@GHI.(JKL).MNO"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 102 = Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   261 "ABC.DEF@GHI.JK(L.M)NO"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   262 "AB(CD)EF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   263 "AB.(CD).EF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 101 = Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   264 "AB.\"(CD)\".EF@GHI.JKL"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   265 "(ABCDEF)@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 98 = Kommentar: Kein lokaler Part vorhanden
   *   266 "(ABCDEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   267 "(AB\"C)DEF@GHI.JKL"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   268 "(AB\C)DEF@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   269 "(AB\@C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 91 = Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   270 "ABC(DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   271 "ABC.DEF@GHI)JKL"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   272 ")ABC.DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   273 "ABC(DEF@GHI).JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   274 "ABC(DEF.GHI).JKL"                                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   275 "(ABC.DEF@GHI.JKL)"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 95 = Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *   276 "(A(B(C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   277 "(A)B)C)DEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   278 "(A)BCDE(F)@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   279 "ABC.DEF@(GH)I.JK(LM)"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   280 "ABC.DEF@(GH(I.JK)L)M"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   281 "ABC.DEF@(comment)[1.2.3.4]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   282 "ABC.DEF@(comment) [1.2.3.4]"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 106 = Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
   *   283 "ABC.DEF@[1.2.3.4](comment)"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   284 "ABC.DEF@[1.2.3.4]    (comment)"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   285 "ABC.DEF@[1.2.3(comment).4]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   286 "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   287 "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   288 "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   289 "(comment)john.smith@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   290 "john.smith(comment)@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   291 "john.smith@(comment)example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   292 "john.smith@example.com(comment)"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   293 "john.smith@exampl(comment)e.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   294 "john.s(comment)mith@example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   295 "john.smith(comment)@(comment)example.com"                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   296 "john.smith(com@ment)example.com"                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   297 "email( (nested) )@plus.com"                                                          Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   298 "email)mirror(@plus.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   299 "email@plus.com (not closed comment"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   300 "email(with @ in comment)plus.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   301 "email@domain.com (joe Smith)"                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   302 "(A)(B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   303 "(A))B)CDEF@GHI.JKL"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   304 "(ABC DEF) <ABC.DEF@GHI.JKL>"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   305 "(comment and stuff)joe@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   306 "UnclosedComment@a(comment"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   307 "at(start)test@test.local.part"                                                       Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   308 "example(comment)@test.local.part"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   309 "joe(comment and stuff)@gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   310 "joe(fail me)smith@gmail.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   311 "joe@(comment and stuff)gmail.com"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   312 "joe@gmail.com(comment and stuff"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 93 = Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   313 "joe@gmail.com(comment and stuff)"                                                    Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   314 "joesmith@gma(fail me)il.com"                                                         Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 100 = Kommentar: Kommentar muss am Stringende enden
   *   315 " a @ []"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   316 " a @[ ] "                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   317 " a @[ ]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   318 " a @[] "                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   319 " a @[]"                                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   320 " a @a[].com "                                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   321 "ABC.DEF@[ .2.3.4]"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   322 "ABC.DEF@[0.0.0.0]"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   323 "ABC.DEF@[000.000.000.000]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   324 "ABC.DEF@[001.012.123.255]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   325 "ABC.DEF@[1..123.255]"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   326 "ABC.DEF@[1.12.123.255]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   327 "ABC.DEF@[1.12.123.255].de"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   328 "ABC.DEF@[1.12.123.259]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   329 "ABC.DEF@[1.123.25.]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 58 = IP4-Adressteil: ungueltige Kombination ".]"
   *   330 "ABC.DEF@[1.2.3.4] "                                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   331 "ABC.DEF@[1.2.3.4][5.6.7.8]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 60 = IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   332 "ABC.DEF@[1.2.]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 57 = IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   333 "ABC.DEF@[1:2:3:4:5:6:7:8]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   334 "ABC.DEF@[::ffff:127.0:0.1]"                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   335 "ABC.DEF@[IPv61:2:3:4:5:6]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   336 "ABC.DEF@[IPv6:00:ffff:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   337 "ABC.DEF@[IPv6:0:0:ffff:127.0.0.1]"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   338 "ABC.DEF@[IPv6:12:ffff:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   339 "ABC.DEF@[IPv6:1:2:(3::5):6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   340 "ABC.DEF@[IPv6:1:2:(3::5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   341 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8 ]"                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   342 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 61 = IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *   343 "ABC.DEF@[IPv6:1:2:3:4:5:6:7:]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   344 "ABC.DEF@[IPv6:1:2:3:4:5:6] "                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   345 "ABC.DEF@[IPv6:1:2:3:4:5]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   346 "ABC.DEF@[IPv6:1:2:3::5:8].de"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 45 = IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *   347 "ABC.DEF@[IPv6:1:2:ffff:127.0.0.1]"                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   348 "ABC.DEF@[IPv6::abcd:127.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   349 "ABC.DEF@[IPv6::fff:999.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 62 = IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *   350 "ABC.DEF@[IPv6::ffff:.127.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 55 = IP4-Adressteil: keine Ziffern vorhanden
   *   351 "ABC.DEF@[IPv6::ffff:1211.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   352 "ABC.DEF@[IPv6::ffff:12111.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   353 "ABC.DEF@[IPv6::ffff:127.0:0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   354 "ABC.DEF@[IPv6::ffff:127A.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 48 = IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *   355 "ABC.DEF@[IPv6::ffff:999.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 54 = IP4-Adressteil: Byte-Overflow
   *   356 "ABC.DEF@[IPv6::ffff:fff.0.0.1]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   357 "ABC.DEF@[IPv6::fffff:127.0.0.1]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 46 = IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *   358 "ABC.DEF@[IPv6:a:b:c:d:127.0.0.1]"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   359 "ABC.DEF@[IPv6:a:b:c:d:e:f:1]"                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   360 "ABC.DEF@[IPv6:ffff:127.0.0.1]"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 47 = IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *   361 "ABC.DEF@[IPv6]"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   362 "ABC.DEF@[[IPv6:1:2:3:4:5:6:7:8]"                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   363 "ABC.DEF[1.12.123.255]"                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   364 "ABC[@IPv6:1:2:3:4:5:6:7:8]"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 51 = IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   365 "( a @[]"                                                                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 92 = Kommentar: Ungueltiges Zeichen im Kommentar
   *   366 "(ABC DEF) <(Comment)ABC.DEF@[iPv6:2001:db8::1]>"                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   367 "ExpectedATEXT@at[start"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 52 = IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   368 "Test.IPv6@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   369 "Test.IPv6@[IPv6:1::1::1]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 50 = IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *   370 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334::]"                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   371 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   372 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370::]"                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   373 "Test.IPv6@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:]"                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 44 = IP6-Adressteil: ungueltige Kombination ":]"
   *   374 "Test.IPv6@[IPv6::2001:0db8:85a3:0000:0000:8a2e:0370:7334]"                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 42 = IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *   375 "Test.IPv6@[IPv6:z001:0db8:85a3:0000:0000:8a2e:0370:7334]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 49 = IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *   376 "Test.IPv6@[[127.0.0.1]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   377 "Test.IPv6@[\n]"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 59 = IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   378 "\"ABC\"@[IPv4:1:2:3:4]"                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 40 = IP6-Adressteil: String "IPv6:" erwartet
   *   379 "\"ABC\"@[IPv6:1234:5678]"                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   380 "\"ABC\"@[IPv6:1234]"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 43 = IP6-Adressteil: Zu wenig Trennzeichen
   *   381 "\"ABC\"@[IPv6:1:2:3:4:5:6:7:8]"                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  5 = eMail-Adresse korrekt (Local Part mit String und IP6-Adresse)
   *   382 "\"\"@[]"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   383 "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   384 "jsmith@[IPv6:2001:db8::1]"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   385 "user@[192.168.2.1]"                                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 true    JAVA 1 false   ea234 true     =  2 = eMail-Adresse korrekt (IP4-Adresse)
   *   386 "user@[IPv6:2001:db8::1]"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  4 = eMail-Adresse korrekt (IP6-Adresse)
   *   387 "UnclosedDomainLiteral@example]"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   388 "ABC DEF <( )@ >"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   389 "ABC DEF <(COMMENT)A@ >"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   390 "ABC(DEF).@GHI.JKL"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   391 "ABC.DEF@(GHI).JKL.MNO"                                                               Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 103 = Kommentar: Falsche Zeichenkombination ")."
   *   392 "ABC.DEF@(GHI)JKL.MNO"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   393 "\"@\".A(@)@a.aa"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  7 = eMail-Adresse korrekt (Kommentar, String)
   *   394 "Joe Smith <mailto:email(with comment)@domain.com>"                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   395 "Display Name <email@plus.com> (after name with display)"                             Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   396 "ABC+DEF) <ABC.DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   397 "\"ABC<DEF>\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   398 "\"ABC<DEF@GHI.COM>\"@GHI.JKL"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   399 "<ABC.DEF@GHI.JKL>"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   400 "ABC DEF < >"                                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   401 "ABC DEF < @ >"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   402 "ABC DEF <ABC<DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   403 "ABC DEF <ABC.DEF@GHI.JKL>"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   404 "<ABC.DEF@GHI.JKL> ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   405 "ABC DEF ABC.DEF@GHI.JKL>"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   406 "<ABC.DEF@GHI.JKL ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 17 = Struktur: keine schliessende eckige Klammer gefunden.
   *   407 "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   408 "\"ABC<DEF>\"@JKL.DE"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   409 ">"                                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 16 = Struktur: keine oeffnende eckige Klammer gefunden.
   *   410 "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   411 "ABC DEF <ABC.<DEF@GHI.JKL>"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   412 "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   413 "ABC DEF <ABC.DEF@GHI.JKL"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   414 "ABC.DEF@GHI.JKL> ABC DEF"                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   415 "ABC DEF >ABC.DEF@GHI.JKL<"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   416 ">ABC.DEF@GHI.JKL< ABC DEF"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   417 "ABC DEF <A@A>"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   418 "<A@A> ABC DEF"                                                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   419 "ABC DEF <>"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   420 "<> ABC DEF"                                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   421 "Joe Smith <email@domain.com>"                                                        Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   422 "Test |<gaaf <email@domain.com>"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   423 "Joe Smith <mailto:email@domain.com>"                                                 Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   424 "\"With extra < within quotes\" Display Name<email@domain.com>"                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 18 = Struktur: Fehler in Adress-String-X
   *   425 "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   426 "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   427 "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   428 "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  9 = eMail-Adresse korrekt (Kommentar, String, IP6-Adresse)
   *   429 "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   430 "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   431 "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   432 "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 97 = Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   433 "0\"00.000\"@wc.de"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   434 "@G\"HI.DE"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   435 "ABC.DEF@GHI.JKL\""                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   436 "ABC.DEF@\"GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 82 = String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *   437 "ABC.DEF\"@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   438 "ABC.DEF\"GHI.JKL"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   439 "ABC.DE\"F@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 81 = String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *   440 "A\"BC\".DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   441 "DomainNotAllowedCharacter@example\"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   442 "\" \"@GHI.JKL"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   443 "\" \"@example.org"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   444 "\"%2\"@gmail.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   445 "\".ABC.DEF\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   446 "\"@GHI.JKL"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   447 "\"ABC DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   448 "\"ABC.DEF.\"@GHI.JKL"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   449 "\"ABC.DEF@GHI.JKL"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   450 "\"ABC.DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   451 "\"ABC.D\"EF@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   452 "\"ABC@DEF\"@GHI.JKL"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   453 "\"ABC\".DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   454 "\"ABC\"\".DEF.\"GHI\"@GHI.JKL"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   455 "\"AB\"C.DEF.\"GHI\"@GHI.JKL"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   456 "\"AB\"C.D\"EF@GHI.JKL"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   457 "\"AB\"C.D\"EF\"@GHI.JKL"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   458 "\"Falsch.da.Escape.Zeichen.am.Ende.steht\"                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 83 = String: Escape-Zeichen nicht am Ende der Eingabe
   *   459 "\"Falsche\#Escape\GSequenz\"@falsch.de"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   460 "\"Gueltige\\"Escape\\Sequenz\"@korrekt.de"                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   461 "\"\"@GHI.JKL"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 85 = String: Leerstring in Anfuehrungszeichen
   *   462 "\"a..b\"@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   463 "\"a_b\"@gmail.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   464 "\"abcdefghixyz\"@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   465 "abc.\"defghi\".xyz@example.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   466 "abc\"defghi\"xyz@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   467 "abc\\"def\\"ghi@eggsample.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   468 "at\"start\"test@test.local.part"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   469 "just\"not\"right@example.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 80 = String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *   470 "this is\"not\allowed@example.com"                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   471 "this\ still\\"not\\allowed@example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   472 "\"Joe Smith\" email@domain.com"                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   473 "\"Joe\tSmith\" email@domain.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   474 "\"Joe\"Smith\" email@domain.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   475 "\"Joe Smith email@domain.com"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   476 "\"Joe Smith' email@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 86 = String: kein abschliessendes Anfuehrungszeichen gefunden.
   *   477 "\"Joe Smith\"email@domain.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 87 = String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *   478 "               "                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   479 " A . B & C . D"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   480 " check@this.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   481 " email@example.com"                                                                  Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   482 " joe@gmail.com"                                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   483 "$ABCDEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   484 "%2@gmail.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   485 "-asd@das.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   486 ".ann..other.@example.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   487 ".email@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   488 "000.000@000.de"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   489 "0test@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   490 "1234567890@example.com"                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   491 "1234@5678.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   492 "1234@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   493 "1@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   494 "A@A.AA"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   495 "A@B.C"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   496 "A@B.CD"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   497 "A@COM"                                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   498 "A@b@c@example.com"                                                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   499 "ABC DEF@GHI.JKL"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   500 "ABC-DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   501 "ABC.DEF@GHI.JKL"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   502 "ABC/DEF=GHI@JKL.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   503 "ABCDEFGHIJKLMNO"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   504 "ABC\@DEF@GHI.JKL"                                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   505 "AT-Character"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   506 "ATEXTAfterCFWS@test\r\n at"                                                          Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   507 "Abc.example.com"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   508 "Abc\@def@ecksample.com"                                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   509 "Abc\@def@example.com"                                                                Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   510 "CRLFAtEnd@test\r\nat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   511 "CRWithoutLF@test\rat"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   512 "ConsecutiveAT@@start"                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   513 "ConsecutiveCRLF@test\r\n\r\nat"                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   514 "ConsecutiveDots@at..start"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   515 "D.Oy'Smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   516 "DomainHyphen@-atstart"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   517 "DomainHyphen@atstart-.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   518 "DomainHyphen@bb.-cc"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   519 "DomainHyphen@bb.-cc-"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   520 "DomainHyphen@bb.cc-"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   521 "DomainNotAllowedCharacter@/atstart"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   522 "DomainNotAllowedCharacter@a,start"                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   523 "DomainNotAllowedCharacter@atst\art.com"                                              Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   524 "DomainNotAllowedCharacter@exa\mple"                                                  Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   525 "DomainNotAllowedCharacter@example'"                                                  Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   526 "DotAtStart@.atstart"                                                                 Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   527 "Drei*Vier@Ist.Zwoelf.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   528 "ExpectedATEXT@;atstart"                                                              Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   529 "ExpectedCTEXT@test\r\n \n"                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   530 "Length"                                                                              Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   531 "No Input"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   532 "Pointy Brackets"                                                                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   533 "Seperator"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   534 "Strings"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   535 "TEST.TEST@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   536 "TEST@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   537 "Test.Domain.Part@1leadingnumber.example.co"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   538 "Test.Domain.Part@1leadingnumber.example.com"                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   539 "Test.Domain.Part@aampleEx.com"                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   540 "Test.Domain.Part@example.co"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   541 "Test.Domain.Part@has-hyphen.example.co"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   542 "Test.Domain.Part@has-hyphen.example.com"                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   543 "Test.Domain.Part@subdomain.example.co"                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   544 "Test.Domain.Part@subdomain.example.com"                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   545 "Test.Test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   546 "_@bde.cc,"                                                                           Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   547 "_@gmail.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   548 "_______@example.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   549 "_dasd@sd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   550 "_dasd_das_@9.com"                                                                    Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   551 "a+b@c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   552 "a..b@bde.cc"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   553 "a@b.-de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   554 "a@b._de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   555 "a@b.c.com"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   556 "a@bde-.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   557 "a@bde.c-c"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   558 "a@bde.cc."                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   559 "a@bde_.cc"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 20 = Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   560 "ab@b+de.cc"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   561 "admin@mailserver1"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   562 "alirheza@test.co.uk"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   563 "ann.other@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   564 "as3d@dac.coas-"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 24 = Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   565 "asd-@asd.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   566 "asd@dasd@asd.cm"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   567 "check@this..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   568 "check@this.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   569 "check@thiscom"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   570 "d23d@da9.co9"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   571 "d@@.com"                                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   572 "da23@das..com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   573 "dad@sds"                                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   574 "dasd-dasd@das.com.das"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   575 "dasd.dadas@dasd.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   576 "dasd_-@jdas.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   577 "dasddas-@.com"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   578 "dda_das@das-dasd.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   579 "doysmith@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   580 "dsq!a?@das.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   581 "email..email@domain.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   582 "email.@domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   583 "email@.domain.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   584 "email@192.0.2.123"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   585 "email@domain..com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   586 "email@domain.com."                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   587 "email@example"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   588 "email@example,com"                                                                   Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   589 "email@example.co.uk."                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   590 "email@example.name .name"                                                            Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 105 = Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   591 "example@localhost"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   592 "example@xyz.solutions"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   593 "fdsa"                                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   594 "fdsa@"                                                                               Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   595 "fdsa@fdza"                                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   596 "fdsa@fdza."                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   597 "futureTLD@somewhere.fooo"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   598 "hallo2ww22@example....caaaao"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   599 "hallo@example.coassjj#sswzazaaaa"                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   600 "hello7___@ca.com.pt"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   601 "invalid.email.com"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   602 "joe@gmail.com "                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   603 "john..doe@example.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   604 "john.doe@example..com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   605 "jsmith@whizbang.co"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   606 "jsr@prhoselware.com9"                                                                Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   607 "lots.of.iq@sagittarius.A*"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   608 "me+100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   609 "me-100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   610 "me-100@yahoo-test.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   611 "me..2002@gmail.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   612 "me.100@me.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   613 "me.@gmail.com"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   614 "me12345@that.is"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   615 "me123@%*.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   616 "me123@%.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 21 = Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   617 "me123@.com"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   618 "me123@.com.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   619 "me@.com.my"                                                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   620 "me@gmail.com.1a"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   621 "me@me.co.uk"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   622 "me@me@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   623 "me@yahoo.com"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   624 "peter_123@news24.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   625 "plain.address"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 28 = AT-Zeichen: kein AT-Zeichen gefunden
   *   626 "plainaddress"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   627 "prettyandsimple@example.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   628 "someone+tag@somewhere.net"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   629 "someone@somewhere.co.uk"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   630 "someone@somewhere.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   631 "test test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   632 "test%test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   633 "test+test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   634 "test-test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   635 "test.test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   636 "test0@gmail.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   637 "test@GMAIL.COM"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   638 "test@Gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   639 "test@anothersub.sub-domain.gmail.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   640 "test@gmail"                                                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   641 "test@gmail."                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 36 = Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   642 "test@gmail..com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   643 "test@gmail.Com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   644 "test@gmail.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   645 "test@sub-domain.000.0"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   646 "test@sub-domain.000.0rg"                                                             Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   647 "test@sub-domain.000.co-om"                                                           Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   648 "test@sub-domain.000.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   649 "test@sub-domain.gmail.com"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   650 "test@subdomain.gmail.com"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   651 "test@test@gmail.com"                                                                 Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   652 "test_test@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   653 "this is not valid@email$com"                                                         Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   654 "user@com"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   655 "user@localhost"                                                                      Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   656 "user@localserver"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   657 "very.common@example.com"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   658 "x._._y__z@gmail.com"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   659 "x.yz.smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   660 "x_yz_smith@gmail.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   661 "xxxx@.org.org"                                                                       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 33 = Trennzeichen: ungueltige Zeichenkombination "@."
   *   662 "xyz@blabla.com"                                                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   663 "{john'doe}@my.server"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   664 null                                                                                  Jmail EXP     REGEXP 1 EXP     REGEXP 2 EXP     REGEXP 3 EXP     JAVA 1 EXP     ea234 false    = 10 = Laenge: Eingabe ist null
   *   665 ""                                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 11 = Laenge: Eingabe ist Leerstring
   *   666 "    "                                                                                Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   667 "ABC.DEF@GHI.J"                                                                       Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   668 "email@domain.topleveldomain"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   669 "unknown errors"                                                                      Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   670 "eMail-Adresses which should be true according to Corniel Nobel."                     Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   671 "https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top" Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   672 "local@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                                       Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   673 "MailTo:casesensitve@domain.com"                                                      Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   674 "mailto:email@domain.com"                                                             Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 22 = Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   675 "ab@188.120.150.10"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   676 "ab@1.0.0.10"                                                                         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   677 "ab@120.25.254.120"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   678 "ab@01.120.150.1"                                                                     Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 14 = Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *   679 "ab@88.120.150.021"                                                                   Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   680 "ab@88.120.150.01"                                                                    Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   681 "email@123.123.123.123"                                                               Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 true    ea234 false    = 23 = Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *   682 "email@domain"                                                                        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   683 "unsorted"                                                                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   684 "..@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   685 ".a@test.com"                                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   686 "ab@sd@dd"                                                                            Jmail false   REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 29 = AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   687 ".@s.dd"                                                                              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 30 = Trennzeichen: kein Beginn mit einem Punkt
   *   688 "#local##name#with#@hash.com"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   689 "withdot.@test.local.part"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 32 = Trennzeichen: ungueltige Zeichenkombination ".@"
   *   690 "domain.starts.with.digit@2domain.com"                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   691 "domain.ends.with.digit@domain2.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   692 "aHZs...Ym8iZXJn@YWRtAW4g.au"                                                         Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 31 = Trennzeichen: keine zwei Punkte hintereinander
   *   693 "\"RmF0aGlh\"@SXp6YXRp.id"                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   694 "\"hor\ror\"@nes.si"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 84 = String: Ungueltige Escape-Sequenz im String
   *   695 "!yoora@an.yang.ha.se.yo"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   696 "084105111046077097110105107@hello.again.id"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   697 "@@@@@@gmail.com"                                                                     Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 26 = AT-Zeichen: kein AT-Zeichen am Anfang
   *   698 "somecahrs@xyz.com"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   699 "user@host.network"                                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   700 "bob.mcspam@ABCD.org"                                                                 Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   701 "something@domain.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   702 "emailString@email.com"                                                               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   703 "person@registry.organization"                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   704 "foo.bar.\"bux\".bar.com@baz.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   705 "someStringThatMightBe@email.com"                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   706 "100$-30%=130$-(x*3pi)@MATH.MAGIC"                                                    Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   707 "\"much.more unusual\"@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   708 "other.email-with-dash@eksample.com"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   709 "Find#Me@NotesDocumentCollection.de"                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   710 "InvalidEmail@notreallyemailbecausenosuffix"                                          Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 34 = Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   711 "\"very.unusual.@.unusual.com\"@example.com"                                          Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  1 = eMail-Adresse korrekt (Local Part mit String)
   *   712 "foo\@bar@machine.subdomain.example.museum"                                           Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   713 "disposable.style.email.with+symbol@example.com"                                      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   714 "0=1+e^(i*pi)@Gleich.Zahl.Phi.Goldener.Schnitt.aua"                                   Jmail false   REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 true     =  6 = eMail-Adresse korrekt (Kommentar)
   *   715 "john.\"M@c\".\"Smith!\"(coolguy)@(thefantastic)[1.2.3.4]"                            Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 99 = Kommentar: kein zweiter Kommentar gueltig
   *   716 "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123"         Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   717 "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A"        Jmail true    REGEXP 1 false   REGEXP 2 false   REGEXP 3 false   JAVA 1 false   ea234 false    = 15 = Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
   *   718 "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   719 "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   720 "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   721 "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   722 "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   723 "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   724 "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   725 "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   726 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"               Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   727 "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   728 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   729 "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL"     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   730 "1234567890123456789012345678901234567890123456789012345678901234+x@example.com"      Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   731 "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com"       Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 89 = String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *   732 "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   733 "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 false   JAVA 1 false   ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   734 "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 false    = 63 = Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *   735 "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 13 = Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *   736 "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 false    = 12 = Laenge: Laengenbegrenzungen stimmen nicht
   *   737 "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@aol.com"              Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 false   ea234 true     =  0 = eMail-Adresse korrekt
   *   738 "ZZZZ.ZZZZZZ@ZZZZZZZZZZ.ZZ"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   739 "zzzz.zzzzzz@zzzzzzzzzz.zz"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   740 "AAAA.AAAAAA@AAAAAAAAAA.AA"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   741 "aaaa.aaaaaa@aaaaaaaaaa.aa"                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   742 "Vorname.Nachname@web.de"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   743 "m.fanin@fc-wohlenegg.at"                                                             Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   744 "old.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   745 "new.email@test.com"                                                                  Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   746 "test1group@test1.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   747 "test2group@test2.com"                                                                Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   748 "test1@test1.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   749 "test2@test2.com"                                                                     Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   750 "junit.testEmailChange@example.com"                                                   Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   751 "at@at.at"                                                                            Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   752 "easy@isnt.it"                                                                        Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *   753 "yes@it.is"                                                                           Jmail true    REGEXP 1 true    REGEXP 2 true    REGEXP 3 true    JAVA 1 true    ea234 true     =  0 = eMail-Adresse korrekt
   *
   * --------------------------------------------------------------------------------------------------------------------------------
   * 
   * Testlauf 1 Anzahl Testfaelle  31950000 (18.01.2020)
   * 
   * ea234      anzahl_korrekt =  22680000 - anzahl_falsch =   9270000 - anzahl_fehler =         0  | MS   3497 = 0.00010945226917057904     = 00:00:03:497
   * 
   * Jmail      anzahl_korrekt =  28050000 - anzahl_falsch =   3870000 - anzahl_fehler =     30000  | MS   5183 = 0.0001622222222222222      = 00:00:05:183
   * 
   * JAVA 1     anzahl_korrekt =  19380000 - anzahl_falsch =  12540000 - anzahl_fehler =     30000  | MS   8670 = 0.00027136150234741786     = 00:00:08:670
   * 
   * REGEXP 1   anzahl_korrekt =  24510000 - anzahl_falsch =   7410000 - anzahl_fehler =     30000  | MS  19946 = 0.0006242879499217528      = 00:00:19:946
   * 
   * REGEXP 2   anzahl_korrekt =  24510000 - anzahl_falsch =   7410000 - anzahl_fehler =     30000  | MS   7993 = 0.00025017214397496086     = 00:00:07:993
   * 
   * REGEXP 3   anzahl_korrekt =  20370000 - anzahl_falsch =  11550000 - anzahl_fehler =     30000  | MS  52348 = 0.001638435054773083       = 00:00:52:348
   * 
   * --------------------------------------------------------------------------------------------------------------------------------
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

  private static int                  FAKTOR_ANZAHL_ARRAY_A    =30000 ;

  /*
   * Array, mit welchem die Tests ausgefuehrt werden.
   */
  private static String[]             array_test_daten_aktuell = null;

  private static String str_50 = "12345678901234567890123456789012345678901234567890";
private static  String str_63 = "A23456789012345678901234567890123456789012345678901234567890123";

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
    "2@bde.cc",
    "-@bde.cc",
    "a2@bde.cc",
    "a-b@bde.cc",
    "ab@b-de.cc",
    "a+b@bde.cc",
    "f.f.f@bde.cc",
    "ab_c@bde.cc",
    "_-_@bde.cc",
    "k.haak@12move.nl",
    "K.HAAK@12MOVE.NL",
    "email@domain.com",
    "w.b.f@test.com",
    "w.b.f@test.museum",
    "a.a@test.com",
    "ab@288.120.150.10.com",
    "ab@[120.254.254.120]",
    "firstname.lastname@domain.com",
    "email@subdomain.domain.com",
    "firstname+lastname@domain.com",
    "1234567890@domain.com",
    "a@domain.com",
    "a.b.c.d@domain.com",
    "aap.123.noot.mies@domain.com",
    "1@domain.com",
    "email@domain.co.jp",
    "firstname-lastname@domain.com",
    "firstname-lastname@d.com",
    "FIRSTNAME-LASTNAME@d--n.com",
    "first-name-last-name@d-a-n.com",
    "john.smith@example.com",
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
    "A . B & C . D",
    "(?).[!]@{&}.<:>",
    "{local{name{{with{@leftbracket.com",
    "}local}name}}with{@rightbracket.com",
    "|local||name|with|@pipe.com",
    "%local%%name%with%@percentage.com",
    "$local$$name$with$@dollar.com",
    "&local&&name&with&$@amp.com",
    "ABC.DEF##name#with#@hash.com",
    "~local~~name~with~@tilde.com",
    "!local!!name!with!@exclamation.com",
    "?local??name?with?@question.com",
    "*local**name*with*@asterisk.com",
    "`local``name`with`@grave-accent.com",
    "^local^^name^with^@xor.com",
    "=local==name=with=@equality.com",
    "+local++name+with+@equality.com",
    "\"B3V3RLY H1LL$\"@example.com",
    "\"-- --- .. -.\"@sh.de",
    "{{-^-}{-=-}{-^-}}@GHI.JKL",
    "#!$%&'*+-/=?^_`{}|~@eksample.org",
    "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
    "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de",
    "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org",
    "email@{leftbracket.com",
    "email@rightbracket}.com",
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
    "email@=qowaiv.com",
    "email@plus+.com",
    "email@domain.com>",
    "email@mailto:domain.com",
    "mailto:mailto:email@domain.com",
    "email@-domain.com",
    "email@domain-.com",
    "email@domain.com-",
    "Joe Smith &lt;email@domain.com&gt;",
    "?????@domain.com",
    "local@?????.com",
    "email@domain-one.com",
    "_______@domain.com",
    "Fred\\ Bloggs@example.com",
    "Joe.\\\\Blow@example.com",
    "Lat%ss\rtart%s@test.local.part",
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
    "ABC.DEF@[IPv6:1:2:3:4:5:6",
    "ABC.DEF@[IPv6:12345:6:7:8:9]",
    "ABC.DEF@[IPv6:1:2:3:::6:7:8]",
    "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]",
    "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]",
    "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]",
    "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]",
    "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]",
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
    "(ABC)DEF@GHI.JKL",
    "(ABC) DEF@GHI.JKL",
    "ABC(DEF)@GHI.JKL",
    "ABC.DEF@GHI.JKL(MNO)",
    "ABC.DEF@GHI.JKL       (MNO)",
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
    "Joe Smith <mailto:email(with comment)@domain.com>",
    "Display Name <email@plus.com> (after name with display)",
    "ABC+DEF) <ABC.DEF@GHI.JKL>",
    "\"ABC<DEF>\"@GHI.JKL",
    "\"ABC<DEF@GHI.COM>\"@GHI.JKL",
    "<ABC.DEF@GHI.JKL>",
    "ABC DEF < >",
    "ABC DEF < @ >",
    "ABC DEF <ABC<DEF@GHI.JKL>",
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
    "Joe Smith <email@domain.com>",
    "Test |<gaaf <email@domain.com>",
    "Joe Smith <mailto:email@domain.com>",
    "\"With extra < within quotes\" Display Name<email@domain.com>",
    "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>",
    "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>",
    "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part",
    "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part ",
    "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>",
    "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>",
    "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part",
    "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part ",
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
    "\"Joe Smith\" email@domain.com",
    "\"Joe\\tSmith\" email@domain.com",
    "\"Joe\"Smith\" email@domain.com",
    "\"Joe Smith email@domain.com",
    "\"Joe Smith' email@domain.com",
    "\"Joe Smith\"email@domain.com",
    "               ",
    " A . B & C . D",
    " check@this.com",
    " email@example.com",
    " joe@gmail.com",
    "$ABCDEF@GHI.JKL",
    "%2@gmail.com",
    "-asd@das.com",
    ".ann..other.@example.com",
    ".email@domain.com",
    "000.000@000.de",
    "0test@gmail.com",
    "1234567890@example.com",
    "1234@5678.com",
    "1234@gmail.com",
    "1@gmail.com",
    "A@A.AA",
    "A@B.C",
    "A@B.CD",
    "A@COM",
    "A@b@c@example.com",
    "ABC DEF@GHI.JKL",
    "ABC-DEF@GHI.JKL",
    "ABC.DEF@GHI.JKL",
    "ABC/DEF=GHI@JKL.com",
    "ABCDEFGHIJKLMNO",
    "ABC\\@DEF@GHI.JKL",
    "AT-Character",
    "ATEXTAfterCFWS@test\r\n at",
    "Abc.example.com",
    "Abc\\@def@ecksample.com",
    "Abc\\@def@example.com",
    "CRLFAtEnd@test\r\nat",
    "CRWithoutLF@test\rat",
    "ConsecutiveAT@@start",
    "ConsecutiveCRLF@test\r\n\r\nat",
    "ConsecutiveDots@at..start",
    "D.Oy'Smith@gmail.com",
    "DomainHyphen@-atstart",
    "DomainHyphen@atstart-.com",
    "DomainHyphen@bb.-cc",
    "DomainHyphen@bb.-cc-",
    "DomainHyphen@bb.cc-",
    "DomainNotAllowedCharacter@/atstart",
    "DomainNotAllowedCharacter@a,start",
    "DomainNotAllowedCharacter@atst\\art.com",
    "DomainNotAllowedCharacter@exa\\mple",
    "DomainNotAllowedCharacter@example'",
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
    "_@bde.cc,",
    "_@gmail.com",
    "_______@example.com",
    "_dasd@sd.com",
    "_dasd_das_@9.com",
    "a+b@c.com",
    "a..b@bde.cc",
    "a@b.-de.cc",
    "a@b._de.cc",
    "a@b.c.com",
    "a@bde-.cc",
    "a@bde.c-c",
    "a@bde.cc.",
    "a@bde_.cc",
    "ab@b+de.cc",
    "admin@mailserver1",
    "alirheza@test.co.uk",
    "ann.other@example.com",
    "as3d@dac.coas-",
    "asd-@asd.com",
    "asd@dasd@asd.cm",
    "check@this..com",
    "check@this.com",
    "check@thiscom",
    "d23d@da9.co9",
    "d@@.com",
    "da23@das..com",
    "dad@sds",
    "dasd-dasd@das.com.das",
    "dasd.dadas@dasd.com",
    "dasd_-@jdas.com",
    "dasddas-@.com",
    "dda_das@das-dasd.com",
    "doysmith@gmail.com",
    "dsq!a?@das.com",
    "email..email@domain.com",
    "email.@domain.com",
    "email@.domain.com",
    "email@192.0.2.123",
    "email@domain..com",
    "email@domain.com.",
    "email@example",
    "email@example,com",
    "email@example.co.uk.",
    "email@example.name .name",
    "example@localhost",
    "example@xyz.solutions",
    "fdsa",
    "fdsa@",
    "fdsa@fdza",
    "fdsa@fdza.",
    "futureTLD@somewhere.fooo",
    "hallo2ww22@example....caaaao",
    "hallo@example.coassjj#sswzazaaaa",
    "hello7___@ca.com.pt",
    "invalid.email.com",
    "joe@gmail.com ",
    "john..doe@example.com",
    "john.doe@example..com",
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
    "plain.address",
    "plainaddress",
    "prettyandsimple@example.com",
    "someone+tag@somewhere.net",
    "someone@somewhere.co.uk",
    "someone@somewhere.com",
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
    "user@localserver",
    "very.common@example.com",
    "x._._y__z@gmail.com",
    "x.yz.smith@gmail.com",
    "x_yz_smith@gmail.com",
    "xxxx@.org.org",
    "xyz@blabla.com",
    "{john'doe}@my.server",
    null, "", "    ",
    "ABC.DEF@GHI.J",
    "email@domain.topleveldomain",
    "unknown errors",
    "eMail-Adresses which should be true according to Corniel Nobel.",
    "https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top",
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
    "#local##name#with#@hash.com",
    "withdot.@test.local.part",
    "domain.starts.with.digit@2domain.com",
    "domain.ends.with.digit@domain2.com",
    "aHZs...Ym8iZXJn@YWRtAW4g.au",
    "\"RmF0aGlh\"@SXp6YXRp.id",
    "\"hor\\ror\"@nes.si",
    "!yoora@an.yang.ha.se.yo",
    "084105111046077097110105107@hello.again.id",
    "@@@@@@gmail.com",     "somecahrs@xyz.com",
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
     "Afom.iqfsekdw@vjqtisdfwmj.jdg", "Tlufsbzxba.gxxfueqlaa@ugclozu.yv", "wedr.eebtsgb@jmnzdvus.bs", "eaflfnkyk.Bnpbpplitoofoc@izfkuvgv.ju", "bkkdo-sdasdffwR@gfdb.asdf.ttm", "Paadf.klddsksdfP@gkjmwfasdfno.zr", "dnbmdt.Elkyfg.swxsbrbl@lxhh.dfsgrgo.adyk", "eotamr-slkdffxlaS@TctvdsvsadfzvO.cwd", "And.To.all.you.Reg.Ex.eMail.Validator.offionardos.out.there@remember.the.soundtrack.is", "I.just.call.to.say.i.hate.you@ksdlfje.ksx", "DANKE@terima.kasih.com",
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
//      startTest( "2" );
//      startTest( "3" );
//      startTest( "4" );
//      startTest( "5" );

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
