package demo;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class TestClassAssertTrueFalse
{
  private static int          LAUFENDE_ZAHL                 = 0;

  private static int          COUNT_ASSERT_IS_TRUE          = 0;

  private static int          COUNT_ASSERT_IS_FALSE         = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          F_RESULT_COUNT_ERROR          = 0;

  private static int          T_RESULT_COUNT_ERROR          = 0;

  private static int          BREITE_SPALTE_EMAIL_AUSGABE   = 60;

  private static boolean      KNZ_LOG_AUSGABE               = true;

  private static boolean      TEST_B_KNZ_AKTIV              = false;

  private static StringBuffer m_str_buffer                  = null;

  /*
   * <pre>
   * 
   * IPV6
   * 
   * https://www.regextester.com/104037
   * (([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))
   * 
   * http://sqa.fyicenter.com/1000334_IPv6_Address_Validator.html#Result
   * 
   * https://formvalidation.io/guide/validators/ip/
   * 
   * https://de.wikipedia.org/wiki/Internationalisierter_Domainname
   * 
   * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
   * 
   * https://stackoverflow.com/questions/297420/list-of-email-addresses-that-can-be-used-to-test-a-javascript-validation-script/297494#297494
   *  
   * </pre>
   */

  public static void main( String[] args )
  {
    /*
     * <pre> 
     * 
     * ---- General Correct ----------------------------------------------------------------------------------------------------
     * 
     *     1 - assertIsTrue  "name1.name2@domain1.tld"                                    =   0 =  OK 
     *     2 - assertIsTrue  "name1.name2@subdomain1.domain1.tld"                         =   0 =  OK 
     *     3 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                       =   2 =  OK 
     *     4 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                          =   4 =  OK 
     *     5 - assertIsTrue  "\"quote1\".name1@domain1.tld"                               =   1 =  OK 
     *     6 - assertIsTrue  "name1.\"quote1\"@domain1.tld"                               =   1 =  OK 
     *     7 - assertIsTrue  "(comment1)name1@domain1.tld"                                =   6 =  OK 
     *     8 - assertIsTrue  "name1(comment1)@domain1.tld"                                =   6 =  OK 
     *     9 - assertIsTrue  "(comment1)\"quote1\".name1@domain1.tld"                     =   7 =  OK 
     *    10 - assertIsTrue  "(comment1)name1.\"quote1\"@domain1.tld"                     =   7 =  OK 
     *    11 - assertIsTrue  "name1.\"quote1\"(comment1)@domain1.tld"                     =   7 =  OK 
     *    12 - assertIsTrue  "\"quote1\".name1(comment1)@domain1.tld"                     =   7 =  OK 
     *    13 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                            =   0 =  OK 
     *    14 - assertIsTrue  "<name1.name2@domain1.tld> name3"                            =   0 =  OK 
     *    15 - assertIsTrue  "A.B@C.DE"                                                   =   0 =  OK 
     *    16 - assertIsTrue  "A.\"B\"@C.DE"                                               =   1 =  OK 
     *    17 - assertIsTrue  "A.B@[1.2.3.4]"                                              =   2 =  OK 
     *    18 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                          =   3 =  OK 
     *    19 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                 =   4 =  OK 
     *    20 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                             =   5 =  OK 
     *    21 - assertIsTrue  "(A)B@C.DE"                                                  =   6 =  OK 
     *    22 - assertIsTrue  "A(B)@C.DE"                                                  =   6 =  OK 
     *    23 - assertIsTrue  "(A)\"B\"@C.DE"                                              =   7 =  OK 
     *    24 - assertIsTrue  "\"A\"(B)@C.DE"                                              =   7 =  OK 
     *    25 - assertIsTrue  "(A)B@[1.2.3.4]"                                             =   2 =  OK 
     *    26 - assertIsTrue  "A(B)@[1.2.3.4]"                                             =   2 =  OK 
     *    27 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                         =   8 =  OK 
     *    28 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                         =   8 =  OK 
     *    29 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                =   4 =  OK 
     *    30 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                =   4 =  OK 
     *    31 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                            =   9 =  OK 
     *    32 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                            =   9 =  OK 
     *    33 - assertIsTrue  "firstname.lastname@domain.com"                              =   0 =  OK 
     *    34 - assertIsTrue  "firstname+lastname@domain.com"                              =   0 =  OK 
     *    35 - assertIsTrue  "firstname-lastname@domain.com"                              =   0 =  OK 
     *    36 - assertIsTrue  "first-name-last-name@d-a-n.com"                             =   0 =  OK 
     *    37 - assertIsTrue  "a.b.c.d@domain.com"                                         =   0 =  OK 
     *    38 - assertIsTrue  "1@domain.com"                                               =   0 =  OK 
     *    39 - assertIsTrue  "a@domain.com"                                               =   0 =  OK 
     *    40 - assertIsTrue  "email@domain.co.de"                                         =   0 =  OK 
     *    41 - assertIsTrue  "email@domain.com"                                           =   0 =  OK 
     *    42 - assertIsTrue  "email@subdomain.domain.com"                                 =   0 =  OK 
     *    43 - assertIsTrue  "2@bde.cc"                                                   =   0 =  OK 
     *    44 - assertIsTrue  "-@bde.cc"                                                   =   0 =  OK 
     *    45 - assertIsTrue  "a2@bde.cc"                                                  =   0 =  OK 
     *    46 - assertIsTrue  "a-b@bde.cc"                                                 =   0 =  OK 
     *    47 - assertIsTrue  "ab@b-de.cc"                                                 =   0 =  OK 
     *    48 - assertIsTrue  "a+b@bde.cc"                                                 =   0 =  OK 
     *    49 - assertIsTrue  "f.f.f@bde.cc"                                               =   0 =  OK 
     *    50 - assertIsTrue  "ab_c@bde.cc"                                                =   0 =  OK 
     *    51 - assertIsTrue  "_-_@bde.cc"                                                 =   0 =  OK 
     *    52 - assertIsTrue  "w.b.f@test.com"                                             =   0 =  OK 
     *    53 - assertIsTrue  "w.b.f@test.museum"                                          =   0 =  OK 
     *    54 - assertIsTrue  "a.a@test.com"                                               =   0 =  OK 
     *    55 - assertIsTrue  "ab@288.120.150.10.com"                                      =   0 =  OK 
     *    56 - assertIsTrue  "ab@[120.254.254.120]"                                       =   2 =  OK 
     *    57 - assertIsTrue  "1234567890@domain.com"                                      =   0 =  OK 
     *    58 - assertIsTrue  "john.smith@example.com"                                     =   0 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    59 - assertIsFalse null                                                         =  10 =  OK    Laenge: Eingabe ist null
     *    60 - assertIsFalse ""                                                           =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    61 - assertIsFalse "        "                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    62 - assertIsFalse "email.with.no.at.character"                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    63 - assertIsFalse "@.local.name.starts.with.at@domain.com"                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    64 - assertIsFalse "@no.local.email.part.domain.com"                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    65 - assertIsFalse "local.name.ends.with.at@@domain.com"                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    66 - assertIsFalse "local.name.with.at.before@.point@domain.com"                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    67 - assertIsFalse "local.name.with.at.after.@point@domain.com"                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    68 - assertIsFalse "local.name.with.double.at@@test@domain.com"                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    69 - assertIsFalse "(comment @) local.name.with.comment.with.at@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    70 - assertIsTrue  "\"quote@\".local.name.with.qoute.with.at@domain.com"        =   1 =  OK 
     *    71 - assertIsTrue  "qouted.\@.character@at.domain.com"                          =   0 =  OK 
     *    72 - assertIsTrue  "\@@at.domain.com"                                           =   0 =  OK 
     *    73 - assertIsFalse "@@at.domain.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    74 - assertIsFalse "@@@@@@@at.domain.com"                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    75 - assertIsTrue  "\@.\@.\@.\@.\@.\@@at.domain.com"                            =   0 =  OK 
     *    76 - assertIsFalse "@.@.@.@.@.@@at.domain.com"                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    77 - assertIsFalse "name @ <pointy.brackets1.with.at@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    78 - assertIsFalse "<pointy.brackets2.with.at@domain.com> name @"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    79 - assertIsFalse "ABCDEFGHIJKLMNOP"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    80 - assertIsFalse "ABC.DEF.GHI.JKL"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    81 - assertIsFalse "ABC.DEF@ GHI.JKL"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *    82 - assertIsFalse "ABC.DEF @GHI.JKL"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    83 - assertIsFalse "ABC.DEF @ GHI.JKL"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    84 - assertIsFalse "@"                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    85 - assertIsFalse "@.@.@."                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    86 - assertIsFalse "@.@.@GHI.JKL"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    87 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    88 - assertIsFalse "@@@GHI.JKL"                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    89 - assertIsFalse "@GHI.JKL"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    90 - assertIsFalse "ABC.DEF@"                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    91 - assertIsFalse "ABC.DEF@@GHI.JKL"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    92 - assertIsFalse "ABC@DEF@GHI.JKL"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    93 - assertIsFalse "@%^%#$@#$@#.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    94 - assertIsFalse "@domain.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    95 - assertIsFalse "email.domain.com"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    96 - assertIsFalse "email@domain@domain.com"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    97 - assertIsFalse "@@@@@@gmail.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    98 - assertIsFalse "first@last@test.org"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    99 - assertIsFalse "@test@a.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   100 - assertIsFalse "@\"someStringThatMightBe@email.com"                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   101 - assertIsFalse "test@@test.com"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *   102 - assertIsFalse "ABCDEF@GHIJKL"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   103 - assertIsFalse "ABC.DEF@GHIJKL"                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   104 - assertIsFalse ".ABC.DEF@GHI.JKL"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   105 - assertIsFalse "ABC.DEF@GHI.JKL."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   106 - assertIsFalse "ABC..DEF@GHI.JKL"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   107 - assertIsFalse "ABC.DEF@GHI..JKL"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   108 - assertIsFalse "ABC.DEF@GHI.JKL.."                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   109 - assertIsFalse "ABC.DEF.@GHI.JKL"                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   110 - assertIsFalse "ABC.DEF@.GHI.JKL"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   111 - assertIsFalse "ABC.DEF@."                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   112 - assertIsFalse "john..doe@example.com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   113 - assertIsFalse "john.doe@example..com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   114 - assertIsTrue  "\"john..doe\"@example.com"                                  =   1 =  OK 
     *   115 - assertIsFalse "..........@domain."                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   116 - assertIsFalse "test.@test.com"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   117 - assertIsFalse ".test.@test.com"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   118 - assertIsFalse "asdf@asdf@asdf.com"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   119 - assertIsFalse "email@provider..com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *   120 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                        =   0 =  OK 
     *   121 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                           =   0 =  OK 
     *   122 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                           =   0 =  OK 
     *   123 - assertIsTrue  "ABC.DEF@GHI.JK2"                                            =   0 =  OK 
     *   124 - assertIsTrue  "ABC.DEF@2HI.JKL"                                            =   0 =  OK 
     *   125 - assertIsFalse "ABC.DEF@GHI.2KL"                                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   126 - assertIsFalse "ABC.DEF@GHI.JK-"                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   127 - assertIsFalse "ABC.DEF@GHI.JK_"                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   128 - assertIsFalse "ABC.DEF@-HI.JKL"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   129 - assertIsFalse "ABC.DEF@_HI.JKL"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   130 - assertIsFalse "ABC DEF@GHI.DE"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   131 - assertIsFalse "ABC.DEF@GHI DE"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   132 - assertIsFalse "A . B & C . D"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   133 - assertIsFalse " A . B & C . D"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   134 - assertIsFalse "(?).[!]@{&}.<:>"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   135 - assertIsTrue  "&local&&name&with&$@amp.com"                                =   0 =  OK 
     *   136 - assertIsTrue  "*local**name*with*@asterisk.com"                            =   0 =  OK 
     *   137 - assertIsTrue  "$local$$name$with$@dollar.com"                              =   0 =  OK 
     *   138 - assertIsTrue  "=local==name=with=@equality.com"                            =   0 =  OK 
     *   139 - assertIsTrue  "!local!!name!with!@exclamation.com"                         =   0 =  OK 
     *   140 - assertIsTrue  "`local``name`with`@grave-accent.com"                        =   0 =  OK 
     *   141 - assertIsTrue  "#local##name#with#@hash.com"                                =   0 =  OK 
     *   142 - assertIsTrue  "-local--name-with-@hypen.com"                               =   0 =  OK 
     *   143 - assertIsTrue  "{local{name{{with{@leftbracket.com"                         =   0 =  OK 
     *   144 - assertIsTrue  "%local%%name%with%@percentage.com"                          =   0 =  OK 
     *   145 - assertIsTrue  "|local||name|with|@pipe.com"                                =   0 =  OK 
     *   146 - assertIsTrue  "+local++name+with+@plus.com"                                =   0 =  OK 
     *   147 - assertIsTrue  "?local??name?with?@question.com"                            =   0 =  OK 
     *   148 - assertIsTrue  "}local}name}}with}@rightbracket.com"                        =   0 =  OK 
     *   149 - assertIsTrue  "~local~~name~with~@tilde.com"                               =   0 =  OK 
     *   150 - assertIsTrue  "^local^^name^with^@xor.com"                                 =   0 =  OK 
     *   151 - assertIsTrue  "_local__name_with_@underscore.com"                          =   0 =  OK 
     *   152 - assertIsFalse ":local::name:with:@colon.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   153 - assertIsTrue  "&.local.name.starts.with.amp@domain.com"                    =   0 =  OK 
     *   154 - assertIsTrue  "local.name.ends.with.amp&@domain.com"                       =   0 =  OK 
     *   155 - assertIsTrue  "local.name.with.amp.before&.point@domain.com"               =   0 =  OK 
     *   156 - assertIsTrue  "local.name.with.amp.after.&point@domain.com"                =   0 =  OK 
     *   157 - assertIsTrue  "local.name.with.double.amp&&test@domain.com"                =   0 =  OK 
     *   158 - assertIsFalse "(comment &) local.name.with.comment.with.amp@domain.com"    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   159 - assertIsTrue  "\"quote&\".local.name.with.qoute.with.amp@domain.com"       =   1 =  OK 
     *   160 - assertIsTrue  "&@amp.domain.com"                                           =   0 =  OK 
     *   161 - assertIsTrue  "&&&&&&@amp.domain.com"                                      =   0 =  OK 
     *   162 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                 =   0 =  OK 
     *   163 - assertIsFalse "name & <pointy.brackets1.with.amp@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   164 - assertIsFalse "<pointy.brackets2.with.amp@domain.com> name &"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   165 - assertIsTrue  "*.local.name.starts.with.asterisk@domain.com"               =   0 =  OK 
     *   166 - assertIsTrue  "local.name.ends.with.asterisk*@domain.com"                  =   0 =  OK 
     *   167 - assertIsTrue  "local.name.with.asterisk.before*.point@domain.com"          =   0 =  OK 
     *   168 - assertIsTrue  "local.name.with.asterisk.after.*point@domain.com"           =   0 =  OK 
     *   169 - assertIsTrue  "local.name.with.double.asterisk**test@domain.com"           =   0 =  OK 
     *   170 - assertIsFalse "(comment *) local.name.with.comment.with.asterisk@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   171 - assertIsTrue  "\"quote*\".local.name.with.qoute.with.asterisk@domain.com"  =   1 =  OK 
     *   172 - assertIsTrue  "*@asterisk.domain.com"                                      =   0 =  OK 
     *   173 - assertIsTrue  "******@asterisk.domain.com"                                 =   0 =  OK 
     *   174 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                            =   0 =  OK 
     *   175 - assertIsFalse "name * <pointy.brackets1.with.asterisk@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   176 - assertIsFalse "<pointy.brackets2.with.asterisk@domain.com> name *"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   177 - assertIsTrue  "$.local.name.starts.with.dollar@domain.com"                 =   0 =  OK 
     *   178 - assertIsTrue  "local.name.ends.with.dollar$@domain.com"                    =   0 =  OK 
     *   179 - assertIsTrue  "local.name.with.dollar.before$.point@domain.com"            =   0 =  OK 
     *   180 - assertIsTrue  "local.name.with.dollar.after.$point@domain.com"             =   0 =  OK 
     *   181 - assertIsTrue  "local.name.with.double.dollar$$test@domain.com"             =   0 =  OK 
     *   182 - assertIsFalse "(comment $) local.name.with.comment.with.dollar@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   183 - assertIsTrue  "\"quote$\".local.name.with.qoute.with.dollar@domain.com"    =   1 =  OK 
     *   184 - assertIsTrue  "$@dollar.domain.com"                                        =   0 =  OK 
     *   185 - assertIsTrue  "$$$$$$@dollar.domain.com"                                   =   0 =  OK 
     *   186 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                              =   0 =  OK 
     *   187 - assertIsFalse "name $ <pointy.brackets1.with.dollar@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   188 - assertIsFalse "<pointy.brackets2.with.dollar@domain.com> name $"           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   189 - assertIsTrue  "=.local.name.starts.with.equality@domain.com"               =   0 =  OK 
     *   190 - assertIsTrue  "local.name.ends.with.equality=@domain.com"                  =   0 =  OK 
     *   191 - assertIsTrue  "local.name.with.equality.before=.point@domain.com"          =   0 =  OK 
     *   192 - assertIsTrue  "local.name.with.equality.after.=point@domain.com"           =   0 =  OK 
     *   193 - assertIsTrue  "local.name.with.double.equality==test@domain.com"           =   0 =  OK 
     *   194 - assertIsFalse "(comment =) local.name.with.comment.with.equality@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   195 - assertIsTrue  "\"quote=\".local.name.with.qoute.with.equality@domain.com"  =   1 =  OK 
     *   196 - assertIsTrue  "=@equality.domain.com"                                      =   0 =  OK 
     *   197 - assertIsTrue  "======@equality.domain.com"                                 =   0 =  OK 
     *   198 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                            =   0 =  OK 
     *   199 - assertIsFalse "name = <pointy.brackets1.with.equality@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   200 - assertIsFalse "<pointy.brackets2.with.equality@domain.com> name ="         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   201 - assertIsTrue  "!.local.name.starts.with.exclamation@domain.com"            =   0 =  OK 
     *   202 - assertIsTrue  "local.name.ends.with.exclamation!@domain.com"               =   0 =  OK 
     *   203 - assertIsTrue  "local.name.with.exclamation.before!.point@domain.com"       =   0 =  OK 
     *   204 - assertIsTrue  "local.name.with.exclamation.after.!point@domain.com"        =   0 =  OK 
     *   205 - assertIsTrue  "local.name.with.double.exclamation!!test@domain.com"        =   0 =  OK 
     *   206 - assertIsFalse "(comment !) local.name.with.comment.with.exclamation@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   207 - assertIsTrue  "\"quote!\".local.name.with.qoute.with.exclamation@domain.com" =   1 =  OK 
     *   208 - assertIsTrue  "!@exclamation.domain.com"                                   =   0 =  OK 
     *   209 - assertIsTrue  "!!!!!!@exclamation.domain.com"                              =   0 =  OK 
     *   210 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                         =   0 =  OK 
     *   211 - assertIsFalse "name ! <pointy.brackets1.with.exclamation@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   212 - assertIsFalse "<pointy.brackets2.with.exclamation@domain.com> name !"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   213 - assertIsTrue  "`.local.name.starts.with.grave-accent@domain.com"           =   0 =  OK 
     *   214 - assertIsTrue  "local.name.ends.with.grave-accent`@domain.com"              =   0 =  OK 
     *   215 - assertIsTrue  "local.name.with.grave-accent.before`.point@domain.com"      =   0 =  OK 
     *   216 - assertIsTrue  "local.name.with.grave-accent.after.`point@domain.com"       =   0 =  OK 
     *   217 - assertIsTrue  "local.name.with.double.grave-accent``test@domain.com"       =   0 =  OK 
     *   218 - assertIsFalse "(comment `) local.name.with.comment.with.grave-accent@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   219 - assertIsTrue  "\"quote`\".local.name.with.qoute.with.grave-accent@domain.com" =   1 =  OK 
     *   220 - assertIsTrue  "`@grave-accent.domain.com"                                  =   0 =  OK 
     *   221 - assertIsTrue  "``````@grave-accent.domain.com"                             =   0 =  OK 
     *   222 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                        =   0 =  OK 
     *   223 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   224 - assertIsFalse "<pointy.brackets2.with.grave-accent@domain.com> name `"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   225 - assertIsTrue  "#.local.name.starts.with.hash@domain.com"                   =   0 =  OK 
     *   226 - assertIsTrue  "local.name.ends.with.hash#@domain.com"                      =   0 =  OK 
     *   227 - assertIsTrue  "local.name.with.hash.before#.point@domain.com"              =   0 =  OK 
     *   228 - assertIsTrue  "local.name.with.hash.after.#point@domain.com"               =   0 =  OK 
     *   229 - assertIsTrue  "local.name.with.double.hash##test@domain.com"               =   0 =  OK 
     *   230 - assertIsFalse "(comment #) local.name.with.comment.with.hash@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   231 - assertIsTrue  "\"quote#\".local.name.with.qoute.with.hash@domain.com"      =   1 =  OK 
     *   232 - assertIsTrue  "#@hash.domain.com"                                          =   0 =  OK 
     *   233 - assertIsTrue  "######@hash.domain.com"                                     =   0 =  OK 
     *   234 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                =   0 =  OK 
     *   235 - assertIsFalse "name # <pointy.brackets1.with.hash@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   236 - assertIsFalse "<pointy.brackets2.with.hash@domain.com> name #"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   237 - assertIsTrue  "-.local.name.starts.with.hypen@domain.com"                  =   0 =  OK 
     *   238 - assertIsTrue  "local.name.ends.with.hypen-@domain.com"                     =   0 =  OK 
     *   239 - assertIsTrue  "local.name.with.hypen.before-.point@domain.com"             =   0 =  OK 
     *   240 - assertIsTrue  "local.name.with.hypen.after.-point@domain.com"              =   0 =  OK 
     *   241 - assertIsTrue  "local.name.with.double.hypen--test@domain.com"              =   0 =  OK 
     *   242 - assertIsFalse "(comment -) local.name.with.comment.with.hypen@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   243 - assertIsTrue  "\"quote-\".local.name.with.qoute.with.hypen@domain.com"     =   1 =  OK 
     *   244 - assertIsTrue  "-@hypen.domain.com"                                         =   0 =  OK 
     *   245 - assertIsTrue  "------@hypen.domain.com"                                    =   0 =  OK 
     *   246 - assertIsTrue  "-.-.-.-.-.-@hypen.domain.com"                               =   0 =  OK 
     *   247 - assertIsFalse "name - <pointy.brackets1.with.hypen@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   248 - assertIsFalse "<pointy.brackets2.with.hypen@domain.com> name -"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   249 - assertIsTrue  "{.local.name.starts.with.leftbracket@domain.com"            =   0 =  OK 
     *   250 - assertIsTrue  "local.name.ends.with.leftbracket{@domain.com"               =   0 =  OK 
     *   251 - assertIsTrue  "local.name.with.leftbracket.before{.point@domain.com"       =   0 =  OK 
     *   252 - assertIsTrue  "local.name.with.leftbracket.after.{point@domain.com"        =   0 =  OK 
     *   253 - assertIsTrue  "local.name.with.double.leftbracket{{test@domain.com"        =   0 =  OK 
     *   254 - assertIsFalse "(comment {) local.name.with.comment.with.leftbracket@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   255 - assertIsTrue  "\"quote{\".local.name.with.qoute.with.leftbracket@domain.com" =   1 =  OK 
     *   256 - assertIsTrue  "{@leftbracket.domain.com"                                   =   0 =  OK 
     *   257 - assertIsTrue  "{{{{{{@leftbracket.domain.com"                              =   0 =  OK 
     *   258 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                         =   0 =  OK 
     *   259 - assertIsFalse "name { <pointy.brackets1.with.leftbracket@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   260 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name {"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   261 - assertIsTrue  "%.local.name.starts.with.percentage@domain.com"             =   0 =  OK 
     *   262 - assertIsTrue  "local.name.ends.with.percentage%@domain.com"                =   0 =  OK 
     *   263 - assertIsTrue  "local.name.with.percentage.before%.point@domain.com"        =   0 =  OK 
     *   264 - assertIsTrue  "local.name.with.percentage.after.%point@domain.com"         =   0 =  OK 
     *   265 - assertIsTrue  "local.name.with.double.percentage%%test@domain.com"         =   0 =  OK 
     *   266 - assertIsFalse "(comment %) local.name.with.comment.with.percentage@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   267 - assertIsTrue  "\"quote%\".local.name.with.qoute.with.percentage@domain.com" =   1 =  OK 
     *   268 - assertIsTrue  "%@percentage.domain.com"                                    =   0 =  OK 
     *   269 - assertIsTrue  "%%%%%%@percentage.domain.com"                               =   0 =  OK 
     *   270 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                          =   0 =  OK 
     *   271 - assertIsFalse "name % <pointy.brackets1.with.percentage@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   272 - assertIsFalse "<pointy.brackets2.with.percentage@domain.com> name %"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   273 - assertIsTrue  "|.local.name.starts.with.pipe@domain.com"                   =   0 =  OK 
     *   274 - assertIsTrue  "local.name.ends.with.pipe|@domain.com"                      =   0 =  OK 
     *   275 - assertIsTrue  "local.name.with.pipe.before|.point@domain.com"              =   0 =  OK 
     *   276 - assertIsTrue  "local.name.with.pipe.after.|point@domain.com"               =   0 =  OK 
     *   277 - assertIsTrue  "local.name.with.double.pipe||test@domain.com"               =   0 =  OK 
     *   278 - assertIsFalse "(comment |) local.name.with.comment.with.pipe@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   279 - assertIsTrue  "\"quote|\".local.name.with.qoute.with.pipe@domain.com"      =   1 =  OK 
     *   280 - assertIsTrue  "|@pipe.domain.com"                                          =   0 =  OK 
     *   281 - assertIsTrue  "||||||@pipe.domain.com"                                     =   0 =  OK 
     *   282 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                =   0 =  OK 
     *   283 - assertIsFalse "name | <pointy.brackets1.with.pipe@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   284 - assertIsFalse "<pointy.brackets2.with.pipe@domain.com> name |"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   285 - assertIsTrue  "+.local.name.starts.with.plus@domain.com"                   =   0 =  OK 
     *   286 - assertIsTrue  "local.name.ends.with.plus+@domain.com"                      =   0 =  OK 
     *   287 - assertIsTrue  "local.name.with.plus.before+.point@domain.com"              =   0 =  OK 
     *   288 - assertIsTrue  "local.name.with.plus.after.+point@domain.com"               =   0 =  OK 
     *   289 - assertIsTrue  "local.name.with.double.plus++test@domain.com"               =   0 =  OK 
     *   290 - assertIsFalse "(comment +) local.name.with.comment.with.plus@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   291 - assertIsTrue  "\"quote+\".local.name.with.qoute.with.plus@domain.com"      =   1 =  OK 
     *   292 - assertIsTrue  "+@plus.domain.com"                                          =   0 =  OK 
     *   293 - assertIsTrue  "++++++@plus.domain.com"                                     =   0 =  OK 
     *   294 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                =   0 =  OK 
     *   295 - assertIsFalse "name + <pointy.brackets1.with.plus@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   296 - assertIsFalse "<pointy.brackets2.with.plus@domain.com> name +"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   297 - assertIsTrue  "?.local.name.starts.with.question@domain.com"               =   0 =  OK 
     *   298 - assertIsTrue  "local.name.ends.with.question?@domain.com"                  =   0 =  OK 
     *   299 - assertIsTrue  "local.name.with.question.before?.point@domain.com"          =   0 =  OK 
     *   300 - assertIsTrue  "local.name.with.question.after.?point@domain.com"           =   0 =  OK 
     *   301 - assertIsTrue  "local.name.with.double.question??test@domain.com"           =   0 =  OK 
     *   302 - assertIsFalse "(comment ?) local.name.with.comment.with.question@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   303 - assertIsTrue  "\"quote?\".local.name.with.qoute.with.question@domain.com"  =   1 =  OK 
     *   304 - assertIsTrue  "?@question.domain.com"                                      =   0 =  OK 
     *   305 - assertIsTrue  "??????@question.domain.com"                                 =   0 =  OK 
     *   306 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                            =   0 =  OK 
     *   307 - assertIsFalse "name ? <pointy.brackets1.with.question@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   308 - assertIsFalse "<pointy.brackets2.with.question@domain.com> name ?"         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   309 - assertIsTrue  "}.local.name.starts.with.rightbracket@domain.com"           =   0 =  OK 
     *   310 - assertIsTrue  "local.name.ends.with.rightbracket}@domain.com"              =   0 =  OK 
     *   311 - assertIsTrue  "local.name.with.rightbracket.before}.point@domain.com"      =   0 =  OK 
     *   312 - assertIsTrue  "local.name.with.rightbracket.after.}point@domain.com"       =   0 =  OK 
     *   313 - assertIsTrue  "local.name.with.double.rightbracket}}test@domain.com"       =   0 =  OK 
     *   314 - assertIsFalse "(comment }) local.name.with.comment.with.rightbracket@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   315 - assertIsTrue  "\"quote}\".local.name.with.qoute.with.rightbracket@domain.com" =   1 =  OK 
     *   316 - assertIsTrue  "}@rightbracket.domain.com"                                  =   0 =  OK 
     *   317 - assertIsTrue  "}}}}}}@rightbracket.domain.com"                             =   0 =  OK 
     *   318 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                        =   0 =  OK 
     *   319 - assertIsFalse "name } <pointy.brackets1.with.rightbracket@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   320 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name }"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   321 - assertIsTrue  "~.local.name.starts.with.tilde@domain.com"                  =   0 =  OK 
     *   322 - assertIsTrue  "local.name.ends.with.tilde~@domain.com"                     =   0 =  OK 
     *   323 - assertIsTrue  "local.name.with.tilde.before~.point@domain.com"             =   0 =  OK 
     *   324 - assertIsTrue  "local.name.with.tilde.after.~point@domain.com"              =   0 =  OK 
     *   325 - assertIsTrue  "local.name.with.double.tilde~~test@domain.com"              =   0 =  OK 
     *   326 - assertIsFalse "(comment ~) local.name.with.comment.with.tilde@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   327 - assertIsTrue  "\"quote~\".local.name.with.qoute.with.tilde@domain.com"     =   1 =  OK 
     *   328 - assertIsTrue  "~@tilde.domain.com"                                         =   0 =  OK 
     *   329 - assertIsTrue  "~~~~~~@tilde.domain.com"                                    =   0 =  OK 
     *   330 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                               =   0 =  OK 
     *   331 - assertIsFalse "name ~ <pointy.brackets1.with.tilde@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   332 - assertIsFalse "<pointy.brackets2.with.tilde@domain.com> name ~"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   333 - assertIsTrue  "^.local.name.starts.with.xor@domain.com"                    =   0 =  OK 
     *   334 - assertIsTrue  "local.name.ends.with.xor^@domain.com"                       =   0 =  OK 
     *   335 - assertIsTrue  "local.name.with.xor.before^.point@domain.com"               =   0 =  OK 
     *   336 - assertIsTrue  "local.name.with.xor.after.^point@domain.com"                =   0 =  OK 
     *   337 - assertIsTrue  "local.name.with.double.xor^^test@domain.com"                =   0 =  OK 
     *   338 - assertIsFalse "(comment ^) local.name.with.comment.with.xor@domain.com"    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   339 - assertIsTrue  "\"quote^\".local.name.with.qoute.with.xor@domain.com"       =   1 =  OK 
     *   340 - assertIsTrue  "^@xor.domain.com"                                           =   0 =  OK 
     *   341 - assertIsTrue  "^^^^^^@xor.domain.com"                                      =   0 =  OK 
     *   342 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                 =   0 =  OK 
     *   343 - assertIsFalse "name ^ <pointy.brackets1.with.xor@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   344 - assertIsFalse "<pointy.brackets2.with.xor@domain.com> name ^"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   345 - assertIsTrue  "_.local.name.starts.with.underscore@domain.com"             =   0 =  OK 
     *   346 - assertIsTrue  "local.name.ends.with.underscore_@domain.com"                =   0 =  OK 
     *   347 - assertIsTrue  "local.name.with.underscore.before_.point@domain.com"        =   0 =  OK 
     *   348 - assertIsTrue  "local.name.with.underscore.after._point@domain.com"         =   0 =  OK 
     *   349 - assertIsTrue  "local.name.with.double.underscore__test@domain.com"         =   0 =  OK 
     *   350 - assertIsFalse "(comment _) local.name.with.comment.with.underscore@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   351 - assertIsTrue  "\"quote_\".local.name.with.qoute.with.underscore@domain.com" =   1 =  OK 
     *   352 - assertIsTrue  "_@underscore.domain.com"                                    =   0 =  OK 
     *   353 - assertIsTrue  "______@underscore.domain.com"                               =   0 =  OK 
     *   354 - assertIsTrue  "_._._._._._@underscore.domain.com"                          =   0 =  OK 
     *   355 - assertIsFalse "name _ <pointy.brackets1.with.underscore@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   356 - assertIsFalse "<pointy.brackets2.with.underscore@domain.com> name _"       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   357 - assertIsFalse ":.local.name.starts.with.colon@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   358 - assertIsFalse "local.name.ends.with.colon:@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   359 - assertIsFalse "local.name.with.colon.before:.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   360 - assertIsFalse "local.name.with.colon.after.:point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   361 - assertIsFalse "local.name.with.double.colon::test@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   362 - assertIsFalse "(comment :) local.name.with.comment.with.colon@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   363 - assertIsTrue  "\"quote:\".local.name.with.qoute.with.colon@domain.com"     =   1 =  OK 
     *   364 - assertIsFalse ":@colon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   365 - assertIsFalse "::::::@colon.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   366 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   367 - assertIsFalse "name : <pointy.brackets1.with.colon@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   368 - assertIsFalse "<pointy.brackets2.with.colon@domain.com> name :"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   369 - assertIsFalse "(.local.name.starts.with.leftbracket@domain.com"            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   370 - assertIsFalse "local.name.ends.with.leftbracket(@domain.com"               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   371 - assertIsFalse "local.name.with.leftbracket.before(.point@domain.com"       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   372 - assertIsFalse "local.name.with.leftbracket.after.(point@domain.com"        = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   373 - assertIsFalse "local.name.with.double.leftbracket((test@domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   374 - assertIsFalse "(comment () local.name.with.comment.with.leftbracket@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   375 - assertIsTrue  "\"quote(\".local.name.with.qoute.with.leftbracket@domain.com" =   1 =  OK 
     *   376 - assertIsFalse "(@leftbracket.domain.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   377 - assertIsFalse "((((((@leftbracket.domain.com"                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   378 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   379 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket@domain.com>"      =   0 =  OK 
     *   380 - assertIsTrue  "<pointy.brackets2.with.leftbracket@domain.com> name ("      =   0 =  OK 
     *   381 - assertIsFalse ").local.name.starts.with.rightbracket@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   382 - assertIsFalse "local.name.ends.with.rightbracket)@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   383 - assertIsFalse "local.name.with.rightbracket.before).point@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   384 - assertIsFalse "local.name.with.rightbracket.after.)point@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   385 - assertIsFalse "local.name.with.double.rightbracket))test@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   386 - assertIsFalse "(comment )) local.name.with.comment.with.rightbracket@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   387 - assertIsTrue  "\"quote)\".local.name.with.qoute.with.rightbracket@domain.com" =   1 =  OK 
     *   388 - assertIsFalse ")@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   389 - assertIsFalse "))))))@rightbracket.domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   390 - assertIsFalse ").).).).).)@rightbracket.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   391 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket@domain.com>"     =   0 =  OK 
     *   392 - assertIsTrue  "<pointy.brackets2.with.rightbracket@domain.com> name )"     =   0 =  OK 
     *   393 - assertIsFalse "[.local.name.starts.with.leftbracket@domain.com"            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   394 - assertIsFalse "local.name.ends.with.leftbracket[@domain.com"               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   395 - assertIsFalse "local.name.with.leftbracket.before[.point@domain.com"       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   396 - assertIsFalse "local.name.with.leftbracket.after.[point@domain.com"        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   397 - assertIsFalse "local.name.with.double.leftbracket[[test@domain.com"        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   398 - assertIsFalse "(comment [) local.name.with.comment.with.leftbracket@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   399 - assertIsTrue  "\"quote[\".local.name.with.qoute.with.leftbracket@domain.com" =   1 =  OK 
     *   400 - assertIsFalse "[@leftbracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   401 - assertIsFalse "[[[[[[@leftbracket.domain.com"                              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   402 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   403 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   404 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name ["      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   405 - assertIsFalse "].local.name.starts.with.rightbracket@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   406 - assertIsFalse "local.name.ends.with.rightbracket]@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   407 - assertIsFalse "local.name.with.rightbracket.before].point@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   408 - assertIsFalse "local.name.with.rightbracket.after.]point@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   409 - assertIsFalse "local.name.with.double.rightbracket]]test@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   410 - assertIsFalse "(comment ]) local.name.with.comment.with.rightbracket@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   411 - assertIsTrue  "\"quote]\".local.name.with.qoute.with.rightbracket@domain.com" =   1 =  OK 
     *   412 - assertIsFalse "]@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   413 - assertIsFalse "]]]]]]@rightbracket.domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   414 - assertIsFalse "].].].].].]@rightbracket.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   415 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket@domain.com>"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   416 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name ]"     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   417 - assertIsFalse " .local.name.starts.with.space@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   418 - assertIsFalse "local.name.ends.with.space @domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   419 - assertIsFalse "local.name.with.space.before .point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   420 - assertIsFalse "local.name.with.space.after. point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   421 - assertIsFalse "local.name.with.double.space  test@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   422 - assertIsFalse "(comment  ) local.name.with.comment.with.space@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   423 - assertIsTrue  "\"quote \".local.name.with.qoute.with.space@domain.com"     =   1 =  OK 
     *   424 - assertIsFalse " @space.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   425 - assertIsFalse "      @space.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   426 - assertIsFalse " . . . . . @space.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   427 - assertIsTrue  "name   <pointy.brackets1.with.space@domain.com>"            =   0 =  OK 
     *   428 - assertIsTrue  "<pointy.brackets2.with.space@domain.com> name  "            =   0 =  OK 
     *   429 - assertIsFalse "..local.name.starts.with.dot@domain.com"                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   430 - assertIsFalse "local.name.ends.with.dot.@domain.com"                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   431 - assertIsFalse "local.name.with.dot.before..point@domain.com"               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   432 - assertIsFalse "local.name.with.dot.after..point@domain.com"                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   433 - assertIsFalse "local.name.with.double.dot..test@domain.com"                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   434 - assertIsFalse "(comment .) local.name.with.comment.with.dot@domain.com"    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   435 - assertIsTrue  "\"quote.\".local.name.with.qoute.with.dot@domain.com"       =   1 =  OK 
     *   436 - assertIsFalse ".@dot.domain.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   437 - assertIsFalse "......@dot.domain.com"                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   438 - assertIsFalse "...........@dot.domain.com"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   439 - assertIsFalse "name . <pointy.brackets1.with.dot@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   440 - assertIsFalse "<pointy.brackets2.with.dot@domain.com> name ."              =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   441 - assertIsFalse "().local.name.starts.with.empty.bracket@domain.com"         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   442 - assertIsTrue  "local.name.ends.with.empty.bracket()@domain.com"            =   6 =  OK 
     *   443 - assertIsFalse "local.name.with.empty.bracket.before().point@domain.com"    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   444 - assertIsFalse "local.name.with.empty.bracket.after.()point@domain.com"     = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   445 - assertIsFalse "local.name.with.double.empty.bracket()()test@domain.com"    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   446 - assertIsFalse "(comment ()) local.name.with.comment.with.empty.bracket@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   447 - assertIsTrue  "\"quote()\".local.name.with.qoute.with.empty.bracket@domain.com" =   1 =  OK 
     *   448 - assertIsFalse "()@empty.bracket.domain.com"                                =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   449 - assertIsFalse "()()()()()()@empty.bracket.domain.com"                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   450 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   451 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket@domain.com>"   =   0 =  OK 
     *   452 - assertIsTrue  "<pointy.brackets2.with.empty.bracket@domain.com> name ()"   =   0 =  OK 
     *   453 - assertIsTrue  "{}.local.name.starts.with.empty.bracket@domain.com"         =   0 =  OK 
     *   454 - assertIsTrue  "local.name.ends.with.empty.bracket{}@domain.com"            =   0 =  OK 
     *   455 - assertIsTrue  "local.name.with.empty.bracket.before{}.point@domain.com"    =   0 =  OK 
     *   456 - assertIsTrue  "local.name.with.empty.bracket.after.{}point@domain.com"     =   0 =  OK 
     *   457 - assertIsTrue  "local.name.with.double.empty.bracket{}{}test@domain.com"    =   0 =  OK 
     *   458 - assertIsFalse "(comment {}) local.name.with.comment.with.empty.bracket@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   459 - assertIsTrue  "\"quote{}\".local.name.with.qoute.with.empty.bracket@domain.com" =   1 =  OK 
     *   460 - assertIsTrue  "{}@empty.bracket.domain.com"                                =   0 =  OK 
     *   461 - assertIsTrue  "{}{}{}{}{}{}@empty.bracket.domain.com"                      =   0 =  OK 
     *   462 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                 =   0 =  OK 
     *   463 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   464 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name {}"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   465 - assertIsFalse "[].local.name.starts.with.empty.bracket@domain.com"         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   466 - assertIsFalse "local.name.ends.with.empty.bracket[]@domain.com"            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   467 - assertIsFalse "local.name.with.empty.bracket.before[].point@domain.com"    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   468 - assertIsFalse "local.name.with.empty.bracket.after.[]point@domain.com"     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   469 - assertIsFalse "local.name.with.double.empty.bracket[][]test@domain.com"    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   470 - assertIsFalse "(comment []) local.name.with.comment.with.empty.bracket@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   471 - assertIsTrue  "\"quote[]\".local.name.with.qoute.with.empty.bracket@domain.com" =   1 =  OK 
     *   472 - assertIsFalse "[]@empty.bracket.domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   473 - assertIsFalse "[][][][][][]@empty.bracket.domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   474 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   475 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket@domain.com>"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   476 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name []"   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   477 - assertIsTrue  "domain.part@with0number0.com"                               =   0 =  OK 
     *   478 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"              =   0 =  OK 
     *   479 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"               =   0 =  OK 
     *   480 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"               =   0 =  OK 
     *   481 - assertIsTrue  "domain.part@with.number0.before0.point.com"                 =   0 =  OK 
     *   482 - assertIsTrue  "domain.part@with.number0.after.0point.com"                  =   0 =  OK 
     *   483 - assertIsTrue  "domain.part@with9number9.com"                               =   0 =  OK 
     *   484 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"              =   0 =  OK 
     *   485 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"               =   0 =  OK 
     *   486 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"               =   0 =  OK 
     *   487 - assertIsTrue  "domain.part@with.number9.before9.point.com"                 =   0 =  OK 
     *   488 - assertIsTrue  "domain.part@with.number9.after.9point.com"                  =   0 =  OK 
     *   489 - assertIsTrue  "domain.part@with0123456789numbers.com"                      =   0 =  OK 
     *   490 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"     =   0 =  OK 
     *   491 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"      =   0 =  OK 
     *   492 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"      =   0 =  OK 
     *   493 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"        =   0 =  OK 
     *   494 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"         =   0 =  OK 
     *   495 - assertIsTrue  "domain.part@with-hyphen.com"                                =   0 =  OK 
     *   496 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   497 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   498 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   499 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   500 - assertIsFalse "domain.part@with.-hyphen.after.point.com"                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   501 - assertIsTrue  "domain.part@with_underscore.com"                            =   0 =  OK 
     *   502 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   503 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   504 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   505 - assertIsFalse "domain.part@with.underscore.before_.point.com"              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   506 - assertIsFalse "domain.part@with.underscore.after._point.com"               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   507 - assertIsFalse "domain.part@with&amp.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   508 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   509 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   510 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   511 - assertIsFalse "domain.part@with.amp.before&.point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   512 - assertIsFalse "domain.part@with.amp.after.&point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   513 - assertIsFalse "domain.part@with*asterisk.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   514 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   515 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   516 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   517 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   518 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   519 - assertIsFalse "domain.part@with$dollar.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   520 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   521 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   522 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   523 - assertIsFalse "domain.part@with.dollar.before$.point.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   524 - assertIsFalse "domain.part@with.dollar.after.$point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   525 - assertIsFalse "domain.part@with=equality.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   526 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   527 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   528 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   529 - assertIsFalse "domain.part@with.equality.before=.point.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   530 - assertIsFalse "domain.part@with.equality.after.=point.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   531 - assertIsFalse "domain.part@with!exclamation.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   532 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   533 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   534 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   535 - assertIsFalse "domain.part@with.exclamation.before!.point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   536 - assertIsFalse "domain.part@with.exclamation.after.!point.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   537 - assertIsFalse "domain.part@with?question.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   538 - assertIsFalse "domain.part@?with.question.at.domain.start.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   539 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   540 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   541 - assertIsFalse "domain.part@with.question.before?.point.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   542 - assertIsFalse "domain.part@with.question.after.?point.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   543 - assertIsFalse "domain.part@with`grave-accent.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   544 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   545 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   546 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   547 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   548 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   549 - assertIsFalse "domain.part@with#hash.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   550 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   551 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   552 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   553 - assertIsFalse "domain.part@with.hash.before#.point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   554 - assertIsFalse "domain.part@with.hash.after.#point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   555 - assertIsFalse "domain.part@with%percentage.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   556 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   557 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   558 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   559 - assertIsFalse "domain.part@with.percentage.before%.point.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   560 - assertIsFalse "domain.part@with.percentage.after.%point.com"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   561 - assertIsFalse "domain.part@with|pipe.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   562 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   563 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   564 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   565 - assertIsFalse "domain.part@with.pipe.before|.point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   566 - assertIsFalse "domain.part@with.pipe.after.|point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   567 - assertIsFalse "domain.part@with+plus.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   568 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   569 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   570 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   571 - assertIsFalse "domain.part@with.plus.before+.point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   572 - assertIsFalse "domain.part@with.plus.after.+point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   573 - assertIsFalse "domain.part@with{leftbracket.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   574 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   575 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   576 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   577 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   578 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   579 - assertIsFalse "domain.part@with}rightbracket.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   580 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   581 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   582 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   583 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   584 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   585 - assertIsFalse "domain.part@with(leftbracket.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   586 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   587 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   588 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   589 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   590 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   591 - assertIsFalse "domain.part@with)rightbracket.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   592 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   593 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   594 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   595 - assertIsFalse "domain.part@with.rightbracket.before).point.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   596 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   597 - assertIsFalse "domain.part@with[leftbracket.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   598 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   599 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   600 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   601 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   602 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   603 - assertIsFalse "domain.part@with]rightbracket.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   604 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   605 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   606 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   607 - assertIsFalse "domain.part@with.rightbracket.before].point.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   608 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   609 - assertIsFalse "domain.part@with~tilde.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   610 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   611 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   612 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   613 - assertIsFalse "domain.part@with.tilde.before~.point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   614 - assertIsFalse "domain.part@with.tilde.after.~point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   615 - assertIsFalse "domain.part@with^xor.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   616 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   617 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   618 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   619 - assertIsFalse "domain.part@with.xor.before^.point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   620 - assertIsFalse "domain.part@with.xor.after.^point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   621 - assertIsFalse "domain.part@with:colon.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   622 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   623 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   624 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   625 - assertIsFalse "domain.part@with.colon.before:.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   626 - assertIsFalse "domain.part@with.colon.after.:point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   627 - assertIsFalse "domain.part@with space.com"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   628 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   629 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   630 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   631 - assertIsFalse "domain.part@with.space.before .point.com"                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   632 - assertIsFalse "domain.part@with.space.after. point.com"                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   633 - assertIsFalse ",.local.name.starts.with.comma@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   634 - assertIsFalse "local.name.ends.with.comma,@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   635 - assertIsFalse "local.name.with.comma.before,.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   636 - assertIsFalse "local.name.with.comma.after.,point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   637 - assertIsFalse "local.name.with.double.comma,,test@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   638 - assertIsFalse "(comment ,) local.name.with.comment.with.comma@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   639 - assertIsTrue  "\"quote,\".local.name.with.qoute.with.comma@domain.com"     =   1 =  OK 
     *   640 - assertIsFalse ",@comma.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   641 - assertIsFalse ",,,,,,@comma.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   642 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   643 - assertIsFalse "name , <pointy.brackets1.with.comma@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   644 - assertIsFalse "<pointy.brackets2.with.comma@domain.com> name ,"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   645 - assertIsFalse "domain.part@with,comma.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   646 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   647 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   648 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   649 - assertIsFalse "domain.part@with.comma.before,.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   650 - assertIsFalse "domain.part@with.comma.after.,point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   651 - assertIsFalse "§.local.name.starts.with.paragraph@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   652 - assertIsFalse "local.name.ends.with.paragraph§@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   653 - assertIsFalse "local.name.with.paragraph.before§.point@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   654 - assertIsFalse "local.name.with.paragraph.after.§point@domain.com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   655 - assertIsFalse "local.name.with.double.paragraph§§test@domain.com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   656 - assertIsFalse "(comment §) local.name.with.comment.with.paragraph@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   657 - assertIsFalse "\"quote§\".local.name.with.qoute.with.paragraph@domain.com" =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   658 - assertIsFalse "§@paragraph.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   659 - assertIsFalse "§§§§§§@paragraph.domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   660 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   661 - assertIsFalse "name § <pointy.brackets1.with.paragraph@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   662 - assertIsFalse "<pointy.brackets2.with.paragraph@domain.com> name §"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   663 - assertIsFalse "domain.part@with§paragraph.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   664 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   665 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   666 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   667 - assertIsFalse "domain.part@with.paragraph.before§.point.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   668 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   669 - assertIsTrue  "'.local.name.starts.with.quote@domain.com"                  =   0 =  OK 
     *   670 - assertIsTrue  "local.name.ends.with.quote'@domain.com"                     =   0 =  OK 
     *   671 - assertIsTrue  "local.name.with.quote.before'.point@domain.com"             =   0 =  OK 
     *   672 - assertIsTrue  "local.name.with.quote.after.'point@domain.com"              =   0 =  OK 
     *   673 - assertIsTrue  "local.name.with.double.quote''test@domain.com"              =   0 =  OK 
     *   674 - assertIsFalse "(comment ') local.name.with.comment.with.quote@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   675 - assertIsTrue  "\"quote'\".local.name.with.qoute.with.quote@domain.com"     =   1 =  OK 
     *   676 - assertIsTrue  "'@quote.domain.com"                                         =   0 =  OK 
     *   677 - assertIsTrue  "''''''@quote.domain.com"                                    =   0 =  OK 
     *   678 - assertIsTrue  "'.'.'.'.'.'@quote.domain.com"                               =   0 =  OK 
     *   679 - assertIsFalse "name ' <pointy.brackets1.with.quote@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   680 - assertIsFalse "<pointy.brackets2.with.quote@domain.com> name '"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   681 - assertIsFalse "domain.part@with'quote.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   682 - assertIsFalse "domain.part@'with.quote.at.domain.start.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   683 - assertIsFalse "domain.part@with.quote.at.domain.end1'.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   684 - assertIsFalse "domain.part@with.quote.at.domain.end2.com'"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   685 - assertIsFalse "domain.part@with.quote.before'.point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   686 - assertIsFalse "domain.part@with.quote.after.'point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   687 - assertIsFalse "\".local.name.starts.with.double.quote@domain.com"          =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   688 - assertIsFalse "local.name.ends.with.double.quote\"@domain.com"             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   689 - assertIsFalse "local.name.with.double.quote.before\".point@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   690 - assertIsFalse "local.name.with.double.quote.after.\"point@domain.com"      =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   691 - assertIsFalse "local.name.with.double.double.quote\"\"test@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   692 - assertIsFalse "(comment \") local.name.with.comment.with.double.quote@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   693 - assertIsFalse "\"quote\"\".local.name.with.qoute.with.double.quote@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   694 - assertIsFalse "\"@double.quote.domain.com"                                 =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   695 - assertIsTrue  "\".\".\".\".\".\"@double.quote.domain.com"                  =   1 =  OK 
     *   696 - assertIsTrue  "name \" <pointy.brackets1.with.double.quote@domain.com>"    =   0 =  OK 
     *   697 - assertIsTrue  "<pointy.brackets2.with.double.quote@domain.com> name \""    =   0 =  OK 
     *   698 - assertIsFalse "domain.part@with\"double.quote.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   699 - assertIsFalse "domain.part@\"with.double.quote.at.domain.start.com"        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   700 - assertIsFalse "domain.part@with.double.quote.at.domain.end1\".com"         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   701 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com\""         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   702 - assertIsFalse "domain.part@with.double.quote.before\".point.com"           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   703 - assertIsFalse "domain.part@with.double.quote.after.\"point.com"            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   704 - assertIsFalse ")(.local.name.starts.with.false.bracket1@domain.com"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   705 - assertIsFalse "local.name.ends.with.false.bracket1)(@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   706 - assertIsFalse "local.name.with.false.bracket1.before)(.point@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   707 - assertIsFalse "local.name.with.false.bracket1.after.)(point@domain.com"    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   708 - assertIsFalse "local.name.with.double.false.bracket1)()(test@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   709 - assertIsFalse "(comment )() local.name.with.comment.with.false.bracket1@domain.com" =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   710 - assertIsTrue  "\"quote)(\".local.name.with.qoute.with.false.bracket1@domain.com" =   1 =  OK 
     *   711 - assertIsFalse ")(@false.bracket1.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   712 - assertIsFalse ")()()()()()(@false.bracket1.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   713 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   714 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1@domain.com>"  =   0 =  OK 
     *   715 - assertIsTrue  "<pointy.brackets2.with.false.bracket1@domain.com> name )("  =   0 =  OK 
     *   716 - assertIsFalse "domain.part@with)(false.bracket1.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   717 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   718 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   719 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   720 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   721 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   722 - assertIsTrue  "}{.local.name.starts.with.false.bracket2@domain.com"        =   0 =  OK 
     *   723 - assertIsTrue  "local.name.ends.with.false.bracket2}{@domain.com"           =   0 =  OK 
     *   724 - assertIsTrue  "local.name.with.false.bracket2.before}{.point@domain.com"   =   0 =  OK 
     *   725 - assertIsTrue  "local.name.with.false.bracket2.after.}{point@domain.com"    =   0 =  OK 
     *   726 - assertIsTrue  "local.name.with.double.false.bracket2}{}{test@domain.com"   =   0 =  OK 
     *   727 - assertIsFalse "(comment }{) local.name.with.comment.with.false.bracket2@domain.com" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   728 - assertIsTrue  "\"quote}{\".local.name.with.qoute.with.false.bracket2@domain.com" =   1 =  OK 
     *   729 - assertIsTrue  "}{@false.bracket2.domain.com"                               =   0 =  OK 
     *   730 - assertIsTrue  "}{}{}{}{}{}{@false.bracket2.domain.com"                     =   0 =  OK 
     *   731 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                =   0 =  OK 
     *   732 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   733 - assertIsFalse "<pointy.brackets2.with.false.bracket2@domain.com> name }{"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   734 - assertIsFalse "domain.part@with}{false.bracket2.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   735 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   736 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   737 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   738 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   739 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   740 - assertIsFalse "][.local.name.starts.with.false.bracket3@domain.com"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   741 - assertIsFalse "local.name.ends.with.false.bracket3][@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   742 - assertIsFalse "local.name.with.false.bracket3.before][.point@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   743 - assertIsFalse "local.name.with.false.bracket3.after.][point@domain.com"    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   744 - assertIsFalse "local.name.with.double.false.bracket3][][test@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   745 - assertIsFalse "(comment ][) local.name.with.comment.with.false.bracket3@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   746 - assertIsTrue  "\"quote][\".local.name.with.qoute.with.false.bracket3@domain.com" =   1 =  OK 
     *   747 - assertIsFalse "][@false.bracket3.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   748 - assertIsFalse "][][][][][][@false.bracket3.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   749 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   750 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   751 - assertIsFalse "<pointy.brackets2.with.false.bracket3@domain.com> name ]["  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   752 - assertIsFalse "domain.part@with][false.bracket3.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   753 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   754 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   755 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   756 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   757 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   758 - assertIsFalse "><.local.name.starts.with.false.bracket4@domain.com"        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   759 - assertIsFalse "local.name.ends.with.false.bracket4><@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   760 - assertIsFalse "local.name.with.false.bracket4.before><.point@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   761 - assertIsFalse "local.name.with.false.bracket4.after.><point@domain.com"    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   762 - assertIsFalse "local.name.with.double.false.bracket4><><test@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   763 - assertIsFalse "(comment ><) local.name.with.comment.with.false.bracket4@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   764 - assertIsTrue  "\"quote><\".local.name.with.qoute.with.false.bracket4@domain.com" =   1 =  OK 
     *   765 - assertIsFalse "><@false.bracket4.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   766 - assertIsFalse "><><><><><><@false.bracket4.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   767 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   768 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4@domain.com>"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   769 - assertIsFalse "<pointy.brackets2.with.false.bracket4@domain.com> name ><"  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   770 - assertIsFalse "domain.part@with><false.bracket4.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   771 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   772 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   773 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   774 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   775 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   776 - assertIsFalse "DomainHyphen@-atstart"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   777 - assertIsFalse "DomainHyphen@atend-.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   778 - assertIsFalse "DomainHyphen@bb.-cc"                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   779 - assertIsFalse "DomainHyphen@bb.-cc-"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   780 - assertIsFalse "DomainHyphen@bb.cc-"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   781 - assertIsFalse "DomainHyphen@bb.c-c"                                        =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   782 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   783 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                          =   0 =  OK 
     *   784 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   785 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   786 - assertIsFalse "DomainNotAllowedCharacter@example'"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   787 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   788 - assertIsTrue  "domain.starts.with.digit@2domain.com"                       =   0 =  OK 
     *   789 - assertIsTrue  "domain.ends.with.digit@domain2.com"                         =   0 =  OK 
     *   790 - assertIsFalse "tld.starts.with.digit@domain.2com"                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   791 - assertIsTrue  "tld.ends.with.digit@domain.com2"                            =   0 =  OK 
     *   792 - assertIsFalse "email@=qowaiv.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   793 - assertIsFalse "email@plus+.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   794 - assertIsFalse "email@domain.com>"                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   795 - assertIsFalse "email@mailto:domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   796 - assertIsFalse "mailto:mailto:email@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   797 - assertIsFalse "email@-domain.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   798 - assertIsFalse "email@domain-.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   799 - assertIsFalse "email@domain.com-"                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   800 - assertIsFalse "email@{leftbracket.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   801 - assertIsFalse "email@rightbracket}.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   802 - assertIsFalse "email@pp|e.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   803 - assertIsTrue  "email@domain.domain.domain.com.com"                         =   0 =  OK 
     *   804 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                  =   0 =  OK 
     *   805 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"           =   0 =  OK 
     *   806 - assertIsFalse "unescaped white space@fake$com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   807 - assertIsFalse "\"Joe Smith email@domain.com"                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   808 - assertIsFalse "\"Joe Smith' email@domain.com"                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   809 - assertIsFalse "\"Joe Smith\"email@domain.com"                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   810 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   811 - assertIsTrue  "{john'doe}@my.server"                                       =   0 =  OK 
     *   812 - assertIsTrue  "email@domain-one.com"                                       =   0 =  OK 
     *   813 - assertIsTrue  "_______@domain.com"                                         =   0 =  OK 
     *   814 - assertIsTrue  "?????@domain.com"                                           =   0 =  OK 
     *   815 - assertIsFalse "local@?????.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   816 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                              =   1 =  OK 
     *   817 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                     =   1 =  OK 
     *   818 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                  =   0 =  OK 
     *   819 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"        =   1 =  OK 
     *   820 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                           =   0 =  OK 
     *   821 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   822 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   823 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   824 - assertIsFalse "\"\"@[]"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   825 - assertIsFalse "\"\"@[1"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   826 - assertIsFalse "ABC.DEF@[]"                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   827 - assertIsTrue  "\"    \"@[1.2.3.4]"                                         =   3 =  OK 
     *   828 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                  =   2 =  OK 
     *   829 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                    =   3 =  OK 
     *   830 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                          =   2 =  OK 
     *   831 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   832 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   833 - assertIsFalse "ABC.DEF[1.2.3.4]"                                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   834 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   835 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   836 - assertIsFalse "ABC.DEF@[][][][]"                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   837 - assertIsFalse "ABC.DEF@[....]"                                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   838 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   839 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   840 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                    =   3 =  OK 
     *   841 - assertIsFalse "ABC.DEF@MyDomain[1.2.3.4]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   842 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                      =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   843 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   844 - assertIsFalse "ABC.DEF@[..]"                                               =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   845 - assertIsFalse "ABC.DEF@[.2.3.4]"                                           =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   846 - assertIsFalse "ABC.DEF@[1]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   847 - assertIsFalse "ABC.DEF@[1.2]"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   848 - assertIsFalse "ABC.DEF@[1.2.3]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   849 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                        =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   850 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                      =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   851 - assertIsFalse "ABC.DEF@[MyDomain.de]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   852 - assertIsFalse "ABC.DEF@[1.2.3.]"                                           =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   853 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   854 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   855 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   856 - assertIsFalse "ABC.DEF@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   857 - assertIsFalse "ABC.DEF@1.2.3.4]"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   858 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   859 - assertIsFalse "ABC.DEF@[12.34]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   860 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   861 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   862 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   863 - assertIsFalse "ip.v4.with.underscore@[123.14_5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   864 - assertIsFalse "ip.v4.with.underscore@[123.145_.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   865 - assertIsFalse "ip.v4.with.underscore@[123.145._178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   866 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90_]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   867 - assertIsFalse "ip.v4.with.underscore@[_123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   868 - assertIsFalse "ip.v4.with.amp@[123.14&5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   869 - assertIsFalse "ip.v4.with.amp@[123.145&.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   870 - assertIsFalse "ip.v4.with.amp@[123.145.&178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   871 - assertIsFalse "ip.v4.with.amp@[123.145.178.90&]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   872 - assertIsFalse "ip.v4.with.amp@[&123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   873 - assertIsFalse "ip.v4.with.asterisk@[123.14*5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   874 - assertIsFalse "ip.v4.with.asterisk@[123.145*.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   875 - assertIsFalse "ip.v4.with.asterisk@[123.145.*178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   876 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90*]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   877 - assertIsFalse "ip.v4.with.asterisk@[*123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   878 - assertIsFalse "ip.v4.with.dollar@[123.14$5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   879 - assertIsFalse "ip.v4.with.dollar@[123.145$.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   880 - assertIsFalse "ip.v4.with.dollar@[123.145.$178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   881 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90$]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   882 - assertIsFalse "ip.v4.with.dollar@[$123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   883 - assertIsFalse "ip.v4.with.equality@[123.14=5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   884 - assertIsFalse "ip.v4.with.equality@[123.145=.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   885 - assertIsFalse "ip.v4.with.equality@[123.145.=178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   886 - assertIsFalse "ip.v4.with.equality@[123.145.178.90=]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   887 - assertIsFalse "ip.v4.with.equality@[=123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   888 - assertIsFalse "ip.v4.with.exclamation@[123.14!5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   889 - assertIsFalse "ip.v4.with.exclamation@[123.145!.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   890 - assertIsFalse "ip.v4.with.exclamation@[123.145.!178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   891 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90!]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   892 - assertIsFalse "ip.v4.with.exclamation@[!123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   893 - assertIsFalse "ip.v4.with.question@[123.14?5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   894 - assertIsFalse "ip.v4.with.question@[123.145?.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   895 - assertIsFalse "ip.v4.with.question@[123.145.?178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   896 - assertIsFalse "ip.v4.with.question@[123.145.178.90?]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   897 - assertIsFalse "ip.v4.with.question@[?123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   898 - assertIsFalse "ip.v4.with.grave-accent@[123.14`5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   899 - assertIsFalse "ip.v4.with.grave-accent@[123.145`.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   900 - assertIsFalse "ip.v4.with.grave-accent@[123.145.`178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   901 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90`]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   902 - assertIsFalse "ip.v4.with.grave-accent@[`123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   903 - assertIsFalse "ip.v4.with.hash@[123.14#5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   904 - assertIsFalse "ip.v4.with.hash@[123.145#.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   905 - assertIsFalse "ip.v4.with.hash@[123.145.#178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   906 - assertIsFalse "ip.v4.with.hash@[123.145.178.90#]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   907 - assertIsFalse "ip.v4.with.hash@[#123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   908 - assertIsFalse "ip.v4.with.percentage@[123.14%5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   909 - assertIsFalse "ip.v4.with.percentage@[123.145%.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   910 - assertIsFalse "ip.v4.with.percentage@[123.145.%178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   911 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90%]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   912 - assertIsFalse "ip.v4.with.percentage@[%123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   913 - assertIsFalse "ip.v4.with.pipe@[123.14|5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   914 - assertIsFalse "ip.v4.with.pipe@[123.145|.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   915 - assertIsFalse "ip.v4.with.pipe@[123.145.|178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   916 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90|]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   917 - assertIsFalse "ip.v4.with.pipe@[|123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   918 - assertIsFalse "ip.v4.with.plus@[123.14+5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   919 - assertIsFalse "ip.v4.with.plus@[123.145+.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   920 - assertIsFalse "ip.v4.with.plus@[123.145.+178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   921 - assertIsFalse "ip.v4.with.plus@[123.145.178.90+]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   922 - assertIsFalse "ip.v4.with.plus@[+123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   923 - assertIsFalse "ip.v4.with.leftbracket@[123.14{5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   924 - assertIsFalse "ip.v4.with.leftbracket@[123.145{.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   925 - assertIsFalse "ip.v4.with.leftbracket@[123.145.{178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   926 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90{]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   927 - assertIsFalse "ip.v4.with.leftbracket@[{123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   928 - assertIsFalse "ip.v4.with.rightbracket@[123.14}5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   929 - assertIsFalse "ip.v4.with.rightbracket@[123.145}.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   930 - assertIsFalse "ip.v4.with.rightbracket@[123.145.}178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   931 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90}]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   932 - assertIsFalse "ip.v4.with.rightbracket@[}123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   933 - assertIsFalse "ip.v4.with.leftbracket@[123.14(5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   934 - assertIsFalse "ip.v4.with.leftbracket@[123.145(.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   935 - assertIsFalse "ip.v4.with.leftbracket@[123.145.(178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   936 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90(]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   937 - assertIsFalse "ip.v4.with.leftbracket@[(123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   938 - assertIsFalse "ip.v4.with.rightbracket@[123.14)5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   939 - assertIsFalse "ip.v4.with.rightbracket@[123.145).178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   940 - assertIsFalse "ip.v4.with.rightbracket@[123.145.)178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   941 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90)]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   942 - assertIsFalse "ip.v4.with.rightbracket@[)123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   943 - assertIsFalse "ip.v4.with.leftbracket@[123.14[5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   944 - assertIsFalse "ip.v4.with.leftbracket@[123.145[.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   945 - assertIsFalse "ip.v4.with.leftbracket@[123.145.[178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   946 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90[]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   947 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   948 - assertIsFalse "ip.v4.with.rightbracket@[123.14]5.178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   949 - assertIsFalse "ip.v4.with.rightbracket@[123.145].178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   950 - assertIsFalse "ip.v4.with.rightbracket@[123.145.]178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   951 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   952 - assertIsFalse "ip.v4.with.rightbracket@[]123.145.178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   953 - assertIsFalse "ip.v4.with.tilde@[123.14~5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   954 - assertIsFalse "ip.v4.with.tilde@[123.145~.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   955 - assertIsFalse "ip.v4.with.tilde@[123.145.~178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   956 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90~]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   957 - assertIsFalse "ip.v4.with.tilde@[~123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   958 - assertIsFalse "ip.v4.with.xor@[123.14^5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   959 - assertIsFalse "ip.v4.with.xor@[123.145^.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   960 - assertIsFalse "ip.v4.with.xor@[123.145.^178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   961 - assertIsFalse "ip.v4.with.xor@[123.145.178.90^]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   962 - assertIsFalse "ip.v4.with.xor@[^123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   963 - assertIsFalse "ip.v4.with.colon@[123.14:5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   964 - assertIsFalse "ip.v4.with.colon@[123.145:.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   965 - assertIsFalse "ip.v4.with.colon@[123.145.:178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   966 - assertIsFalse "ip.v4.with.colon@[123.145.178.90:]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   967 - assertIsFalse "ip.v4.with.colon@[:123.145.178.90]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   968 - assertIsFalse "ip.v4.with.space@[123.14 5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   969 - assertIsFalse "ip.v4.with.space@[123.145 .178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   970 - assertIsFalse "ip.v4.with.space@[123.145. 178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   971 - assertIsFalse "ip.v4.with.space@[123.145.178.90 ]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   972 - assertIsFalse "ip.v4.with.space@[ 123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   973 - assertIsTrue  "email@[123.123.123.123]"                                    =   2 =  OK 
     *   974 - assertIsFalse "email@111.222.333"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   975 - assertIsFalse "email@111.222.333.256"                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   976 - assertIsFalse "email@[123.123.123.123"                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   977 - assertIsFalse "email@[123.123.123].123"                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   978 - assertIsFalse "email@123.123.123.123]"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   979 - assertIsFalse "email@123.123.[123.123]"                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   980 - assertIsFalse "ab@988.120.150.10"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   981 - assertIsFalse "ab@120.256.256.120"                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   982 - assertIsFalse "ab@120.25.1111.120"                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   983 - assertIsFalse "ab@[188.120.150.10"                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   984 - assertIsFalse "ab@188.120.150.10]"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   985 - assertIsFalse "ab@[188.120.150.10].com"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   986 - assertIsTrue  "ab@188.120.150.10"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   987 - assertIsTrue  "ab@1.0.0.10"                                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   988 - assertIsTrue  "ab@120.25.254.120"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   989 - assertIsTrue  "ab@01.120.150.1"                                            =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   990 - assertIsTrue  "ab@88.120.150.021"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   991 - assertIsTrue  "ab@88.120.150.01"                                           =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   992 - assertIsTrue  "email@123.123.123.123"                                      =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   993 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                 =   4 =  OK 
     *   994 - assertIsFalse "ABC.DEF@[IP"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   995 - assertIsFalse "ABC.DEF@[IPv6]"                                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   996 - assertIsFalse "ABC.DEF@[IPv6:]"                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   997 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   998 - assertIsFalse "ABC.DEF@[IPv6:1]"                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   999 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                         =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1000 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                       =   4 =  OK 
     *  1001 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                     =   4 =  OK 
     *  1002 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                  =   4 =  OK 
     *  1003 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                 =   4 =  OK 
     *  1004 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                 =   4 =  OK 
     *  1005 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                               =   4 =  OK 
     *  1006 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1007 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                             =   4 =  OK 
     *  1008 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1009 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                     =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1010 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                   =   4 =  OK 
     *  1011 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1012 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1013 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1014 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1015 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1016 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                              =   4 =  OK 
     *  1017 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1018 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1019 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1020 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1021 - assertIsTrue  "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]"                             =   4 =  OK 
     *  1022 - assertIsFalse "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1023 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1024 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1025 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1026 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1027 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1028 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1029 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1030 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1031 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1032 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1033 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1034 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1035 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1036 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1037 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1038 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1039 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1040 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1041 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1042 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1043 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1044 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1045 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1046 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1047 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1048 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1049 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1050 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1051 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1052 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1053 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1054 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1055 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1056 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1057 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1058 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1059 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1060 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1061 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1062 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1063 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1064 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1065 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1066 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1067 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1068 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1069 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1070 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1071 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1072 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1073 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1074 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1075 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1076 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1077 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1078 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1079 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1080 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1081 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1082 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1083 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1084 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1085 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1086 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1087 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1088 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1089 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1090 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1091 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1092 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1093 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1094 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1095 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1096 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1097 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1098 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1099 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1100 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1101 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1102 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1103 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1104 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1105 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1106 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1107 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1108 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1109 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1110 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1111 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1112 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1113 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"              =   4 =  OK 
     *  1114 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =   4 =  OK 
     *  1115 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1116 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1117 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =   4 =  OK 
     *  1118 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1119 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"     =   4 =  OK 
     *  1120 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"     =   4 =  OK 
     *  1121 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"     =   4 =  OK 
     *  1122 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                    =   4 =  OK 
     *  1123 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                     =   4 =  OK 
     *  1124 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1125 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1126 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1127 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1128 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                             =   4 =  OK 
     *  1129 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                             =   4 =  OK 
     *  1130 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                 =   4 =  OK 
     *  1131 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                 =   4 =  OK 
     *  1132 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1133 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1134 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1135 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1136 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *  1137 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                         =   1 =  OK 
     *  1138 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                         =   1 =  OK 
     *  1139 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                         =   1 =  OK 
     *  1140 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                       =   1 =  OK 
     *  1141 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                        =   1 =  OK 
     *  1142 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                        =   1 =  OK 
     *  1143 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                        =   1 =  OK 
     *  1144 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1145 - assertIsFalse "\"\"@GHI.DE"                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1146 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1147 - assertIsFalse "A@G\"HI.DE"                                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1148 - assertIsFalse "\"@GHI.DE"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1149 - assertIsFalse "ABC.DEF.\""                                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1150 - assertIsFalse "ABC.DEF@\"\""                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1151 - assertIsFalse "ABC.DEF@G\"HI.DE"                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1152 - assertIsFalse "ABC.DEF@GHI.DE\""                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1153 - assertIsFalse "ABC.DEF@\"GHI.DE"                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1154 - assertIsFalse "\"Escape.Sequenz.Ende\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1155 - assertIsFalse "ABC.DEF\"GHI.DE"                                            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1156 - assertIsFalse "ABC.DEF\"@GHI.DE"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1157 - assertIsFalse "ABC.DE\"F@GHI.DE"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1158 - assertIsFalse "\"ABC.DEF@GHI.DE"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1159 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                         =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1160 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                        =   1 =  OK 
     *  1161 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                        =   1 =  OK 
     *  1162 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                 =   1 =  OK 
     *  1163 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1164 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1165 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1166 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1167 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1168 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                     =   1 =  OK 
     *  1169 - assertIsFalse "\"Ende.am.Eingabeende\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1170 - assertIsFalse "0\"00.000\"@GHI.JKL"                                        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1171 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                       =   1 =  OK 
     *  1172 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                             =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1173 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1174 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1175 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"      =   1 =  OK 
     *  1176 - assertIsFalse "\"Joe Smith\" email@domain.com"                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1177 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1178 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *  1179 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                           =   6 =  OK 
     *  1180 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1181 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                           =   6 =  OK 
     *  1182 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                       =   6 =  OK 
     *  1183 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                 =   6 =  OK 
     *  1184 - assertIsFalse "ABC.DEF@             (MNO)"                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1185 - assertIsFalse "ABC.DEF@   .         (MNO)"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1186 - assertIsFalse "ABC.DEF              (MNO)"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1187 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                 =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1188 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1189 - assertIsFalse "ABC.DEF@GHI.JKL          "                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1190 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1191 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                         =   6 =  OK 
     *  1192 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                          =   6 =  OK 
     *  1193 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                          =   6 =  OK 
     *  1194 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                          =   6 =  OK 
     *  1195 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                          =   6 =  OK 
     *  1196 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1197 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                       = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1198 - assertIsFalse "(ABC).DEF@GHI.JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1199 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                          = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1200 - assertIsFalse "ABC.DEF@(GHI).JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1201 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                      = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1202 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1203 - assertIsFalse "AB(CD)EF@GHI.JKL"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1204 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1205 - assertIsFalse "(ABCDEF)@GHI.JKL"                                           =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1206 - assertIsFalse "(ABCDEF).@GHI.JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1207 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1208 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                          =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1209 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                         =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1210 - assertIsFalse "ABC(DEF@GHI.JKL"                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1211 - assertIsFalse "ABC.DEF@GHI)JKL"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1212 - assertIsFalse ")ABC.DEF@GHI.JKL"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1213 - assertIsFalse "ABC(DEF@GHI).JKL"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1214 - assertIsFalse "ABC(DEF.GHI).JKL"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1215 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                          =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1216 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1217 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1218 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1219 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1220 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1221 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                 =   2 =  OK 
     *  1222 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  1223 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                 =   2 =  OK 
     *  1224 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                             =   2 =  OK 
     *  1225 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1226 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                     =   4 =  OK 
     *  1227 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                     =   4 =  OK 
     *  1228 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                 =   4 =  OK 
     *  1229 - assertIsTrue  "(comment)john.smith@example.com"                            =   6 =  OK 
     *  1230 - assertIsTrue  "john.smith(comment)@example.com"                            =   6 =  OK 
     *  1231 - assertIsTrue  "john.smith@(comment)example.com"                            =   6 =  OK 
     *  1232 - assertIsTrue  "john.smith@example.com(comment)"                            =   6 =  OK 
     *  1233 - assertIsFalse "john.smith@exampl(comment)e.com"                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1234 - assertIsFalse "john.s(comment)mith@example.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1235 - assertIsFalse "john.smith(comment)@(comment)example.com"                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1236 - assertIsFalse "john.smith(com@ment)example.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1237 - assertIsFalse "email( (nested) )@plus.com"                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1238 - assertIsFalse "email)mirror(@plus.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1239 - assertIsFalse "email@plus.com (not closed comment"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1240 - assertIsFalse "email(with @ in comment)plus.com"                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1241 - assertIsTrue  "email@domain.com (joe Smith)"                               =   6 =  OK 
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *  1242 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                  =   0 =  OK 
     *  1243 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                  =   0 =  OK 
     *  1244 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                   =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1245 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                   =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1246 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                              =   0 =  OK 
     *  1247 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                        =   1 =  OK 
     *  1248 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                =   1 =  OK 
     *  1249 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1250 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1251 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1252 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1253 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1254 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1255 - assertIsFalse "ABC DEF <A@A>"                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1256 - assertIsFalse "<A@A> ABC DEF"                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1257 - assertIsFalse "ABC DEF <>"                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1258 - assertIsFalse "<> ABC DEF"                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1259 - assertIsFalse ">"                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1260 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                          =   0 =  OK 
     *  1261 - assertIsTrue  "Joe Smith <email@domain.com>"                               =   0 =  OK 
     *  1262 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1263 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1264 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *  1265 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *  1266 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" =   9 =  OK 
     *  1267 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " =   9 =  OK 
     *  1268 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1269 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>" =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1270 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1271 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1272 - assertIsFalse "Test |<gaaf <email@domain.com>"                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1273 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1274 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1275 - assertIsFalse "<null>@mail.com"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *  1276 - assertIsTrue  "A@B.CD"                                                     =   0 =  OK 
     *  1277 - assertIsFalse "A@B.C"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1278 - assertIsFalse "A@COM"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1279 - assertIsTrue  "ABC.DEF@GHI.JKL"                                            =   0 =  OK 
     *  1280 - assertIsTrue  "ABC.DEF@GHI.J"                                              =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1281 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  1282 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  1283 - assertIsTrue  "A@B.CD"                                                     =   0 =  OK 
     *  1284 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *  1285 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  1286 - assertIsTrue  "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  1287 - assertIsFalse "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1288 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1289 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1290 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1291 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1292 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1293 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1294 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1295 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1296 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1297 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1298 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1299 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1300 - assertIsFalse "eMail Test XX4 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com>" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1301 - assertIsFalse "eMail Test XX5A <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1302 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1303 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  1304 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  1305 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1306 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1307 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  1308 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1309 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1310 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1311 - assertIsTrue  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1312 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1313 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=.?^`{|}~@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-0123456789.a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.e4.f5.g6.h7.i8.j9.K0.L1.M2.N3.O.domain.name" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  1314 - assertIsTrue  "email@domain.topleveldomain"                                =   0 =  OK 
     *  1315 - assertIsTrue  "email@email.email.mydomain"                                 =   0 =  OK 
     * 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1316 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                           =   6 =  OK 
     *  1317 - assertIsTrue  "\"MaxMustermann\"@example.com"                              =   1 =  OK 
     *  1318 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                 =   1 =  OK 
     *  1319 - assertIsTrue  "\".John.Doe\"@example.com"                                  =   1 =  OK 
     *  1320 - assertIsTrue  "\"John.Doe.\"@example.com"                                  =   1 =  OK 
     *  1321 - assertIsTrue  "\"John..Doe\"@example.com"                                  =   1 =  OK 
     *  1322 - assertIsTrue  "john.smith(comment)@example.com"                            =   6 =  OK 
     *  1323 - assertIsTrue  "(comment)john.smith@example.com"                            =   6 =  OK 
     *  1324 - assertIsTrue  "john.smith@(comment)example.com"                            =   6 =  OK 
     *  1325 - assertIsTrue  "john.smith@example.com(comment)"                            =   6 =  OK 
     *  1326 - assertIsTrue  "john.smith@example.com"                                     =   0 =  OK 
     *  1327 - assertIsTrue  "jsmith@[192.168.2.1]"                                       =   2 =  OK 
     *  1328 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                  =   4 =  OK 
     *  1329 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                          =   0 =  OK 
     *  1330 - assertIsTrue  "Marc Dupont <md118@example.com>"                            =   0 =  OK 
     *  1331 - assertIsTrue  "simple@example.com"                                         =   0 =  OK 
     *  1332 - assertIsTrue  "very.common@example.com"                                    =   0 =  OK 
     *  1333 - assertIsTrue  "disposable.style.email.with+symbol@example.com"             =   0 =  OK 
     *  1334 - assertIsTrue  "other.email-with-hyphen@example.com"                        =   0 =  OK 
     *  1335 - assertIsTrue  "fully-qualified-domain@example.com"                         =   0 =  OK 
     *  1336 - assertIsTrue  "user.name+tag+sorting@example.com"                          =   0 =  OK 
     *  1337 - assertIsTrue  "user+mailbox/department=shipping@example.com"               =   0 =  OK 
     *  1338 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                           =   0 =  OK 
     *  1339 - assertIsTrue  "x@example.com"                                              =   0 =  OK 
     *  1340 - assertIsTrue  "info@firma.org"                                             =   0 =  OK 
     *  1341 - assertIsTrue  "example-indeed@strange-example.com"                         =   0 =  OK 
     *  1342 - assertIsTrue  "admin@mailserver1"                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1343 - assertIsTrue  "example@s.example"                                          =   0 =  OK 
     *  1344 - assertIsTrue  "\" \"@example.org"                                          =   1 =  OK 
     *  1345 - assertIsTrue  "\"john..doe\"@example.org"                                  =   1 =  OK 
     *  1346 - assertIsTrue  "mailhost!username@example.org"                              =   0 =  OK 
     *  1347 - assertIsTrue  "user%example.com@example.org"                               =   0 =  OK 
     *  1348 - assertIsTrue  "joe25317@NOSPAMexample.com"                                 =   0 =  OK 
     *  1349 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                 =   0 =  OK 
     *  1350 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1351 - assertIsFalse "Abc..123@example.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1352 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1353 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1354 - assertIsFalse "just\"not\"right@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1355 - assertIsFalse "this is\"not\allowed@example.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1356 - assertIsFalse "this\ still\\"not\\allowed@example.com"                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1357 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1358 - assertIsFalse "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com" =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     * 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1359 - assertIsFalse "nolocalpart.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1360 - assertIsFalse "test@example.com test"                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1361 - assertIsFalse "user  name@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1362 - assertIsFalse "user   name@example.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1363 - assertIsFalse "example.@example.co.uk"                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1364 - assertIsFalse "example@example@example.co.uk"                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1365 - assertIsFalse "(test_exampel@example.fr}"                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1366 - assertIsFalse "example(example)example@example.co.uk"                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1367 - assertIsFalse ".example@localhost"                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1368 - assertIsFalse "ex\ample@localhost"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1369 - assertIsFalse "example@local\host"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1370 - assertIsFalse "example@localhost."                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1371 - assertIsFalse "user name@example.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1372 - assertIsFalse "username@ example . com"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1373 - assertIsFalse "example@(fake}.com"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1374 - assertIsFalse "example@(fake.com"                                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1375 - assertIsTrue  "username@example.com"                                       =   0 =  OK 
     *  1376 - assertIsTrue  "usern.ame@example.com"                                      =   0 =  OK 
     *  1377 - assertIsFalse "user[na]me@example.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1378 - assertIsFalse "\"\"\"@iana.org"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1379 - assertIsFalse "\"\\"@iana.org"                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1380 - assertIsFalse "\"test\"test@iana.org"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1381 - assertIsFalse "\"test\"\"test\"@iana.org"                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1382 - assertIsTrue  "\"test\".\"test\"@iana.org"                                 =   1 =  OK 
     *  1383 - assertIsTrue  "\"test\".test@iana.org"                                     =   1 =  OK 
     *  1384 - assertIsFalse "\"test\\"@iana.org"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1385 - assertIsFalse "\r\ntest@iana.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1386 - assertIsFalse "\r\n test@iana.org"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1387 - assertIsFalse "\r\n \r\ntest@iana.org"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1388 - assertIsFalse "\r\n \r\n test@iana.org"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1389 - assertIsFalse "test@iana.org \r\n"                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1390 - assertIsFalse "test@iana.org \r\n "                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1391 - assertIsFalse "test@iana.org \r\n \r\n"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1392 - assertIsFalse "test@iana.org \r\n\r\n"                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1393 - assertIsFalse "test@iana.org  \r\n\r\n "                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1394 - assertIsFalse "test@iana/icann.org"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1395 - assertIsFalse "test@foo;bar.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1396 - assertIsFalse "a@test.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1397 - assertIsFalse "comment)example@example.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1398 - assertIsFalse "comment(example))@example.com"                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1399 - assertIsFalse "example@example)comment.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1400 - assertIsFalse "example@example(comment)).com"                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1401 - assertIsFalse "example@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1402 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                              =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1403 - assertIsFalse "exam(ple@exam).ple"                                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1404 - assertIsFalse "example@(example))comment.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1405 - assertIsTrue  "example@example.com"                                        =   0 =  OK 
     *  1406 - assertIsTrue  "example@example.co.uk"                                      =   0 =  OK 
     *  1407 - assertIsTrue  "example_underscore@example.fr"                              =   0 =  OK 
     *  1408 - assertIsTrue  "exam'ple@example.com"                                       =   0 =  OK 
     *  1409 - assertIsTrue  "exam\ ple@example.com"                                      =   0 =  OK 
     *  1410 - assertIsFalse "example((example))@fakedfake.co.uk"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1411 - assertIsFalse "example@faked(fake).co.uk"                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1412 - assertIsTrue  "example+@example.com"                                       =   0 =  OK 
     *  1413 - assertIsTrue  "example@with-hyphen.example.com"                            =   0 =  OK 
     *  1414 - assertIsTrue  "with-hyphen@example.com"                                    =   0 =  OK 
     *  1415 - assertIsTrue  "example@1leadingnum.example.com"                            =   0 =  OK 
     *  1416 - assertIsTrue  "1leadingnum@example.com"                                    =   0 =  OK 
     *  1417 - assertIsTrue  "инфо@письмо.рф"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1418 - assertIsTrue  "\"username\"@example.com"                                   =   1 =  OK 
     *  1419 - assertIsTrue  "\"user.name\"@example.com"                                  =   1 =  OK 
     *  1420 - assertIsTrue  "\"user name\"@example.com"                                  =   1 =  OK 
     *  1421 - assertIsTrue  "\"user@name\"@example.com"                                  =   1 =  OK 
     *  1422 - assertIsFalse "\"\a\"@iana.org"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1423 - assertIsTrue  "\"test\ test\"@iana.org"                                    =   1 =  OK 
     *  1424 - assertIsFalse "\"\"@iana.org"                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1425 - assertIsFalse "\"\"@[]"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1426 - assertIsTrue  "\"\\"\"@iana.org"                                           =   1 =  OK 
     *  1427 - assertIsTrue  "example@localhost"                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1428 - assertIsFalse "..@test.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1429 - assertIsFalse ".a@test.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1430 - assertIsFalse "ab@sd@dd"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1431 - assertIsFalse ".@s.dd"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1432 - assertIsFalse "a@b.-de.cc"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1433 - assertIsFalse "a@bde-.cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1434 - assertIsFalse "a@b._de.cc"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1435 - assertIsFalse "a@bde_.cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1436 - assertIsFalse "a@bde.cc."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1437 - assertIsFalse "ab@b+de.cc"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1438 - assertIsFalse "a..b@bde.cc"                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1439 - assertIsFalse "_@bde.cc."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1440 - assertIsFalse "plainaddress"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1441 - assertIsFalse "plain.address"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1442 - assertIsFalse ".email@domain.com"                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1443 - assertIsFalse "email.@domain.com"                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1444 - assertIsFalse "email..email@domain.com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1445 - assertIsFalse "email@.domain.com"                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1446 - assertIsFalse "email@domain.com."                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1447 - assertIsFalse "email@domain..com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1448 - assertIsFalse "MailTo:casesensitve@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1449 - assertIsFalse "mailto:email@domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1450 - assertIsFalse "email@domain"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1451 - assertIsTrue  "someone@somewhere.com"                                      =   0 =  OK 
     *  1452 - assertIsTrue  "someone@somewhere.co.uk"                                    =   0 =  OK 
     *  1453 - assertIsTrue  "someone+tag@somewhere.net"                                  =   0 =  OK 
     *  1454 - assertIsTrue  "futureTLD@somewhere.fooo"                                   =   0 =  OK 
     *  1455 - assertIsFalse "myemailsample.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1456 - assertIsTrue  "myemail@sample"                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1457 - assertIsTrue  "myemail@sample.com"                                         =   0 =  OK 
     *  1458 - assertIsFalse "myemail@@sample.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1459 - assertIsFalse "myemail@sa@mple.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1460 - assertIsTrue  "\"myemail@sa\"@mple.com"                                    =   1 =  OK 
     *  1461 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1462 - assertIsFalse "foo~&(&)(@bar.com"                                          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1463 - assertIsTrue  "\"foo\@bar\"@Something.com"                                 =   1 =  OK 
     *  1464 - assertIsFalse "Foobar Some@thing.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1465 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1466 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                  =   0 =  OK 
     *  1467 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1468 - assertIsTrue  "cog@wheel.com"                                              =   0 =  OK 
     *  1469 - assertIsTrue  "\"cogwheel the orange\"@example.com"                        =   1 =  OK 
     *  1470 - assertIsFalse "123@$.xyz"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1471 - assertIsTrue  "david.jones@proseware.com"                                  =   0 =  OK 
     *  1472 - assertIsTrue  "d.j@server1.proseware.com"                                  =   0 =  OK 
     *  1473 - assertIsTrue  "jones@ms1.proseware.com"                                    =   0 =  OK 
     *  1474 - assertIsFalse "j.@server1.proseware.com"                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1475 - assertIsTrue  "j@proseware.com9"                                           =   0 =  OK 
     *  1476 - assertIsTrue  "js#internal@proseware.com"                                  =   0 =  OK 
     *  1477 - assertIsTrue  "j_9@[129.126.118.1]"                                        =   2 =  OK 
     *  1478 - assertIsFalse "j..s@proseware.com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1479 - assertIsTrue  "js*@proseware.com"                                          =   0 =  OK 
     *  1480 - assertIsFalse "js@proseware..com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1481 - assertIsTrue  "js@proseware.com9"                                          =   0 =  OK 
     *  1482 - assertIsTrue  "j.s@server1.proseware.com"                                  =   0 =  OK 
     *  1483 - assertIsTrue  "\"j\\"s\"@proseware.com"                                    =   1 =  OK 
     *  1484 - assertIsFalse "dasddas-@.com"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1485 - assertIsTrue  "-asd@das.com"                                               =   0 =  OK 
     *  1486 - assertIsFalse "as3d@dac.coas-"                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1487 - assertIsTrue  "dsq!a?@das.com"                                             =   0 =  OK 
     *  1488 - assertIsTrue  "_dasd@sd.com"                                               =   0 =  OK 
     *  1489 - assertIsFalse "dad@sds"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1490 - assertIsTrue  "asd-@asd.com"                                               =   0 =  OK 
     *  1491 - assertIsTrue  "dasd_-@jdas.com"                                            =   0 =  OK 
     *  1492 - assertIsFalse "asd@dasd@asd.cm"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1493 - assertIsFalse "da23@das..com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1494 - assertIsTrue  "_dasd_das_@9.com"                                           =   0 =  OK 
     *  1495 - assertIsTrue  "d23d@da9.co9"                                               =   0 =  OK 
     *  1496 - assertIsTrue  "dasd.dadas@dasd.com"                                        =   0 =  OK 
     *  1497 - assertIsTrue  "dda_das@das-dasd.com"                                       =   0 =  OK 
     *  1498 - assertIsTrue  "dasd-dasd@das.com.das"                                      =   0 =  OK 
     *  1499 - assertIsFalse "fdsa"                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1500 - assertIsFalse "fdsa@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1501 - assertIsFalse "fdsa@fdsa"                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1502 - assertIsFalse "fdsa@fdsa."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1503 - assertIsFalse "@b.com"                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1504 - assertIsFalse "a@.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1505 - assertIsTrue  "a@bcom"                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1506 - assertIsTrue  "a.b@com"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1507 - assertIsFalse "a@b."                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1508 - assertIsTrue  "ab@c.com"                                                   =   0 =  OK 
     *  1509 - assertIsTrue  "a@bc.com"                                                   =   0 =  OK 
     *  1510 - assertIsTrue  "a@b.com"                                                    =   0 =  OK 
     *  1511 - assertIsTrue  "a@b.c.com"                                                  =   0 =  OK 
     *  1512 - assertIsTrue  "a+b@c.com"                                                  =   0 =  OK 
     *  1513 - assertIsTrue  "a@123.45.67.89"                                             =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1514 - assertIsTrue  "%2@gmail.com"                                               =   0 =  OK 
     *  1515 - assertIsTrue  "\"%2\"@gmail.com"                                           =   1 =  OK 
     *  1516 - assertIsTrue  "\"a..b\"@gmail.com"                                         =   1 =  OK 
     *  1517 - assertIsTrue  "\"a_b\"@gmail.com"                                          =   1 =  OK 
     *  1518 - assertIsTrue  "_@gmail.com"                                                =   0 =  OK 
     *  1519 - assertIsTrue  "1@gmail.com"                                                =   0 =  OK 
     *  1520 - assertIsTrue  "1_example@something.gmail.com"                              =   0 =  OK 
     *  1521 - assertIsTrue  "d._.___d@gmail.com"                                         =   0 =  OK 
     *  1522 - assertIsTrue  "d.oy.smith@gmail.com"                                       =   0 =  OK 
     *  1523 - assertIsTrue  "d_oy_smith@gmail.com"                                       =   0 =  OK 
     *  1524 - assertIsTrue  "doysmith@gmail.com"                                         =   0 =  OK 
     *  1525 - assertIsTrue  "D.Oy'Smith@gmail.com"                                       =   0 =  OK 
     *  1526 - assertIsTrue  "%20f3v34g34@gvvre.com"                                      =   0 =  OK 
     *  1527 - assertIsTrue  "piskvor@example.lighting"                                   =   0 =  OK 
     *  1528 - assertIsTrue  "--@ooo.ooo"                                                 =   0 =  OK 
     *  1529 - assertIsFalse "check@thiscom"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1530 - assertIsFalse "check@this..com"                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1531 - assertIsFalse " check@this.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1532 - assertIsTrue  "check@this.com"                                             =   0 =  OK 
     *  1533 - assertIsTrue  "Abc@example.com"                                            =   0 =  OK 
     *  1534 - assertIsFalse "Abc@example.com."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1535 - assertIsTrue  "Abc@10.42.0.1"                                              =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1536 - assertIsTrue  "user@localserver"                                           =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1537 - assertIsTrue  "Abc.123@example.com"                                        =   0 =  OK 
     *  1538 - assertIsTrue  "user+mailbox/department=shipping@example.com"               =   0 =  OK 
     *  1539 - assertIsTrue  "\" \"@example.org"                                          =   1 =  OK 
     *  1540 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                    =   4 =  OK 
     *  1541 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1542 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1543 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1544 - assertIsFalse "this\ still\\"not\allowed@example.com"                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1545 - assertIsTrue  "email@example.com"                                          =   0 =  OK 
     *  1546 - assertIsTrue  "email@example.co.uk"                                        =   0 =  OK 
     *  1547 - assertIsTrue  "email@mail.gmail.com"                                       =   0 =  OK 
     *  1548 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com" =   0 =  OK 
     *  1549 - assertIsFalse "email@example.co.uk."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1550 - assertIsFalse "email@example"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1551 - assertIsFalse " email@example.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1552 - assertIsFalse "email@example.com "                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1553 - assertIsFalse "invalid.email.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1554 - assertIsFalse "invalid@email@domain.com"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1555 - assertIsFalse "email@example..com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1556 - assertIsTrue  "yoursite@ourearth.com"                                      =   0 =  OK 
     *  1557 - assertIsTrue  "my.ownsite@ourearth.org"                                    =   0 =  OK 
     *  1558 - assertIsTrue  "mysite@you.me.net"                                          =   0 =  OK 
     *  1559 - assertIsTrue  "xxxx@gmail.com"                                             =   0 =  OK 
     *  1560 - assertIsTrue  "xxxxxx@yahoo.com"                                           =   0 =  OK 
     *  1561 - assertIsFalse "xxxx.ourearth.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1562 - assertIsFalse "xxxx@.com.my"                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1563 - assertIsFalse "@you.me.net"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1564 - assertIsFalse "xxxx123@gmail.b"                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1565 - assertIsFalse "xxxx@.org.org"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1566 - assertIsFalse ".xxxx@mysite.org"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1567 - assertIsFalse "xxxxx()*@gmail.com"                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1568 - assertIsFalse "xxxx..1234@yahoo.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1569 - assertIsTrue  "alex@example.com"                                           =   0 =  OK 
     *  1570 - assertIsTrue  "alireza@test.co.uk"                                         =   0 =  OK 
     *  1571 - assertIsTrue  "peter.example@yahoo.com.au"                                 =   0 =  OK 
     *  1572 - assertIsTrue  "peter_123@news.com"                                         =   0 =  OK 
     *  1573 - assertIsTrue  "hello7___@ca.com.pt"                                        =   0 =  OK 
     *  1574 - assertIsTrue  "example@example.co"                                         =   0 =  OK 
     *  1575 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1576 - assertIsFalse "hallo2ww22@example....caaaao"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1577 - assertIsTrue  "abcxyz123@qwert.com"                                        =   0 =  OK 
     *  1578 - assertIsTrue  "abc123xyz@asdf.co.in"                                       =   0 =  OK 
     *  1579 - assertIsTrue  "abc1_xyz1@gmail1.com"                                       =   0 =  OK 
     *  1580 - assertIsTrue  "abc.xyz@gmail.com.in"                                       =   0 =  OK 
     *  1581 - assertIsTrue  "pio_pio@factory.com"                                        =   0 =  OK 
     *  1582 - assertIsTrue  "~pio_pio@factory.com"                                       =   0 =  OK 
     *  1583 - assertIsTrue  "pio_~pio@factory.com"                                       =   0 =  OK 
     *  1584 - assertIsTrue  "pio_#pio@factory.com"                                       =   0 =  OK 
     *  1585 - assertIsFalse "pio_pio@#factory.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1586 - assertIsFalse "pio_pio@factory.c#om"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1587 - assertIsFalse "pio_pio@factory.c*om"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1588 - assertIsTrue  "pio^_pio@factory.com"                                       =   0 =  OK 
     *  1589 - assertIsTrue  "\"Abc\@def\"@example.com"                                   =   1 =  OK 
     *  1590 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                =   1 =  OK 
     *  1591 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                               =   1 =  OK 
     *  1592 - assertIsTrue  "Fred\ Bloggs@example.com"                                   =   0 =  OK 
     *  1593 - assertIsFalse "\"Joe\Blow\"@example.com"                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1594 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                  =   1 =  OK 
     *  1595 - assertIsTrue  "\"Abc@def\"@example.com"                                    =   1 =  OK 
     *  1596 - assertIsTrue  "customer/department=shipping@example.com"                   =   0 =  OK 
     *  1597 - assertIsFalse "\$A12345@example.com"                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1598 - assertIsTrue  "!def!xyz%abc@example.com"                                   =   0 =  OK 
     *  1599 - assertIsTrue  "_somename@example.com"                                      =   0 =  OK 
     *  1600 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                             =   1 =  OK 
     *  1601 - assertIsTrue  "\"abcdefghixyz\"@example.com"                               =   1 =  OK 
     *  1602 - assertIsFalse "abc\"defghi\"xyz@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1603 - assertIsFalse "abc\\"def\\"ghi@example.com"                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1604 - assertIsTrue  "!sd@gh.com"                                                 =   0 =  OK 
     * 
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1605 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                 =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1606 - assertIsTrue  "\"back\slash\"@sld.com"                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1607 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                =   1 =  OK 
     *  1608 - assertIsTrue  "\"quoted\"@sld.com"                                         =   1 =  OK 
     *  1609 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                         =   1 =  OK 
     *  1610 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"         =   0 =  OK 
     *  1611 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                        =   0 =  OK 
     *  1612 - assertIsTrue  "01234567890@numbers-in-local.net"                           =   0 =  OK 
     *  1613 - assertIsTrue  "a@single-character-in-local.org"                            =   0 =  OK 
     *  1614 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *  1615 - assertIsTrue  "backticksarelegit@test.com"                                 =   0 =  OK 
     *  1616 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                 =   2 =  OK 
     *  1617 - assertIsTrue  "country-code-tld@sld.rw"                                    =   0 =  OK 
     *  1618 - assertIsTrue  "country-code-tld@sld.uk"                                    =   0 =  OK 
     *  1619 - assertIsTrue  "letters-in-sld@123.com"                                     =   0 =  OK 
     *  1620 - assertIsTrue  "local@dash-in-sld.com"                                      =   0 =  OK 
     *  1621 - assertIsTrue  "local@sld.newTLD"                                           =   0 =  OK 
     *  1622 - assertIsTrue  "local@sub.domains.com"                                      =   0 =  OK 
     *  1623 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                           =   0 =  OK 
     *  1624 - assertIsTrue  "one-character-third-level@a.example.com"                    =   0 =  OK 
     *  1625 - assertIsTrue  "one-letter-sld@x.org"                                       =   0 =  OK 
     *  1626 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                   =   0 =  OK 
     *  1627 - assertIsTrue  "single-character-in-sld@x.org"                              =   0 =  OK 
     *  1628 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  1629 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-length-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  1630 - assertIsTrue  "uncommon-tld@sld.mobi"                                      =   0 =  OK 
     *  1631 - assertIsTrue  "uncommon-tld@sld.museum"                                    =   0 =  OK 
     *  1632 - assertIsTrue  "uncommon-tld@sld.travel"                                    =   0 =  OK 
     *  1633 - assertIsFalse "invalid"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1634 - assertIsFalse "invalid@"                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  1635 - assertIsFalse "invalid @"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1636 - assertIsFalse "invalid@[555.666.777.888]"                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1637 - assertIsFalse "invalid@[IPv6:123456]"                                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1638 - assertIsFalse "invalid@[127.0.0.1.]"                                       =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1639 - assertIsFalse "invalid@[127.0.0.1]."                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1640 - assertIsFalse "invalid@[127.0.0.1]x"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1641 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1642 - assertIsFalse "@missing-local.org"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1643 - assertIsFalse "IP-and-port@127.0.0.1:25"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1644 - assertIsFalse "another-invalid-ip@127.0.0.256"                             =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1645 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1646 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1647 - assertIsFalse "invalid-ip@127.0.0.1.26"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1648 - assertIsFalse "local-ends-with-dot.@sld.com"                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1649 - assertIsFalse "missing-at-sign.net"                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1650 - assertIsFalse "missing-sld@.com"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1651 - assertIsFalse "missing-tld@sld."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1652 - assertIsFalse "sld-ends-with-dash@sld-.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1653 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1654 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1655 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1656 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-255-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bl.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1657 - assertIsFalse "two..consecutive-dots@sld.com"                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1658 - assertIsFalse "unbracketed-IP@127.0.0.1"                                   =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1659 - assertIsFalse "underscore.error@example.com_"                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1660 - assertIsTrue  "first.last@iana.org"                                        =   0 =  OK 
     *  1661 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *  1662 - assertIsTrue  "\"first\\"last\"@iana.org"                                  =   1 =  OK 
     *  1663 - assertIsTrue  "\"first@last\"@iana.org"                                    =   1 =  OK 
     *  1664 - assertIsTrue  "\"first\\last\"@iana.org"                                   =   1 =  OK 
     *  1665 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  1666 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  1667 - assertIsTrue  "first.last@[12.34.56.78]"                                   =   2 =  OK 
     *  1668 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"          =   4 =  OK 
     *  1669 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"           =   4 =  OK 
     *  1670 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"          =   4 =  OK 
     *  1671 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"          =   4 =  OK 
     *  1672 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"  =   4 =  OK 
     *  1673 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  1674 - assertIsTrue  "first.last@3com.com"                                        =   0 =  OK 
     *  1675 - assertIsTrue  "first.last@123.iana.org"                                    =   0 =  OK 
     *  1676 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"    =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1677 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"      =   4 =  OK 
     *  1678 - assertIsTrue  "\"Abc\@def\"@iana.org"                                      =   1 =  OK 
     *  1679 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                  =   1 =  OK 
     *  1680 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                    =   1 =  OK 
     *  1681 - assertIsTrue  "\"Abc@def\"@iana.org"                                       =   1 =  OK 
     *  1682 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                 =   1 =  OK 
     *  1683 - assertIsTrue  "user+mailbox@iana.org"                                      =   0 =  OK 
     *  1684 - assertIsTrue  "$A12345@iana.org"                                           =   0 =  OK 
     *  1685 - assertIsTrue  "!def!xyz%abc@iana.org"                                      =   0 =  OK 
     *  1686 - assertIsTrue  "_somename@iana.org"                                         =   0 =  OK 
     *  1687 - assertIsTrue  "dclo@us.ibm.com"                                            =   0 =  OK 
     *  1688 - assertIsTrue  "peter.piper@iana.org"                                       =   0 =  OK 
     *  1689 - assertIsTrue  "test@iana.org"                                              =   0 =  OK 
     *  1690 - assertIsTrue  "TEST@iana.org"                                              =   0 =  OK 
     *  1691 - assertIsTrue  "1234567890@iana.org"                                        =   0 =  OK 
     *  1692 - assertIsTrue  "test+test@iana.org"                                         =   0 =  OK 
     *  1693 - assertIsTrue  "test-test@iana.org"                                         =   0 =  OK 
     *  1694 - assertIsTrue  "t*est@iana.org"                                             =   0 =  OK 
     *  1695 - assertIsTrue  "+1~1+@iana.org"                                             =   0 =  OK 
     *  1696 - assertIsTrue  "{_test_}@iana.org"                                          =   0 =  OK 
     *  1697 - assertIsTrue  "test.test@iana.org"                                         =   0 =  OK 
     *  1698 - assertIsTrue  "\"test.test\"@iana.org"                                     =   1 =  OK 
     *  1699 - assertIsTrue  "test.\"test\"@iana.org"                                     =   1 =  OK 
     *  1700 - assertIsTrue  "\"test@test\"@iana.org"                                     =   1 =  OK 
     *  1701 - assertIsTrue  "test@123.123.123.x123"                                      =   0 =  OK 
     *  1702 - assertIsFalse "test@123.123.123.123"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1703 - assertIsTrue  "test@[123.123.123.123]"                                     =   2 =  OK 
     *  1704 - assertIsTrue  "test@example.iana.org"                                      =   0 =  OK 
     *  1705 - assertIsTrue  "test@example.example.iana.org"                              =   0 =  OK 
     *  1706 - assertIsTrue  "customer/department@iana.org"                               =   0 =  OK 
     *  1707 - assertIsTrue  "_Yosemite.Sam@iana.org"                                     =   0 =  OK 
     *  1708 - assertIsTrue  "~@iana.org"                                                 =   0 =  OK 
     *  1709 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                 =   1 =  OK 
     *  1710 - assertIsTrue  "Ima.Fool@iana.org"                                          =   0 =  OK 
     *  1711 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                      =   1 =  OK 
     *  1712 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                    =   1 =  OK 
     *  1713 - assertIsTrue  "\"first\".\"last\"@iana.org"                                =   1 =  OK 
     *  1714 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                         =   1 =  OK 
     *  1715 - assertIsTrue  "\"first\".last@iana.org"                                    =   1 =  OK 
     *  1716 - assertIsTrue  "first.\"last\"@iana.org"                                    =   1 =  OK 
     *  1717 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                     =   1 =  OK 
     *  1718 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                         =   1 =  OK 
     *  1719 - assertIsTrue  "\"first.middle.last\"@iana.org"                             =   1 =  OK 
     *  1720 - assertIsTrue  "\"first..last\"@iana.org"                                   =   1 =  OK 
     *  1721 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                         =   1 =  OK 
     *  1722 - assertIsFalse "first.last @iana.orgin"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1723 - assertIsTrue  "\"test blah\"@iana.orgin"                                   =   1 =  OK 
     *  1724 - assertIsTrue  "name.lastname@domain.com"                                   =   0 =  OK 
     *  1725 - assertIsTrue  "a@bar.com"                                                  =   0 =  OK 
     *  1726 - assertIsTrue  "aaa@[123.123.123.123]"                                      =   2 =  OK 
     *  1727 - assertIsTrue  "a-b@bar.com"                                                =   0 =  OK 
     *  1728 - assertIsFalse "+@b.c"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1729 - assertIsTrue  "+@b.com"                                                    =   0 =  OK 
     *  1730 - assertIsTrue  "a@b.co-foo.uk"                                              =   0 =  OK 
     *  1731 - assertIsTrue  "\"hello my name is\"@stutter.comin"                         =   1 =  OK 
     *  1732 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                         =   1 =  OK 
     *  1733 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                           =   0 =  OK 
     *  1734 - assertIsFalse "foobar@192.168.0.1"                                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1735 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"          =   6 =  OK 
     *  1736 - assertIsTrue  "user%uucp!path@berkeley.edu"                                =   0 =  OK 
     *  1737 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                  =   0 =  OK 
     *  1738 - assertIsTrue  "test@test.com"                                              =   0 =  OK 
     *  1739 - assertIsTrue  "test@xn--example.com"                                       =   0 =  OK 
     *  1740 - assertIsTrue  "test@example.com"                                           =   0 =  OK 
     *  1741 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                   =   0 =  OK 
     *  1742 - assertIsTrue  "first\@last@iana.org"                                       =   0 =  OK 
     *  1743 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                   =   0 =  OK 
     *  1744 - assertIsFalse "first.last@example.123"                                     =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1745 - assertIsFalse "first.last@comin"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1746 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                  =   1 =  OK 
     *  1747 - assertIsTrue  "Abc\@def@iana.org"                                          =   0 =  OK 
     *  1748 - assertIsTrue  "Fred\ Bloggs@iana.org"                                      =   0 =  OK 
     *  1749 - assertIsFalse "Joe.\Blow@iana.org"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1750 - assertIsFalse "first.last@sub.do.com"                                      =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1751 - assertIsFalse "first.last"                                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1752 - assertIsTrue  "wild.wezyr@best-server-ever.com"                            =   0 =  OK 
     *  1753 - assertIsTrue  "\"hello world\"@example.com"                                =   1 =  OK 
     *  1754 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1755 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"               =   1 =  OK 
     *  1756 - assertIsTrue  "example+tag@gmail.com"                                      =   0 =  OK 
     *  1757 - assertIsFalse ".ann..other.@example.com"                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1758 - assertIsTrue  "ann.other@example.com"                                      =   0 =  OK 
     *  1759 - assertIsTrue  "something@something.something"                              =   0 =  OK 
     *  1760 - assertIsTrue  "c@(Chris's host.)public.examplein"                          =   6 =  OK 
     *  1761 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                        =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1762 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1763 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1764 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1765 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1766 - assertIsFalse "first().last@iana.orgin"                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1767 - assertIsFalse "pete(his account)@silly.test(his host)"                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1768 - assertIsFalse "jdoe@machine(comment). examplein"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1769 - assertIsFalse "first(abc.def).last@iana.orgin"                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1770 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1771 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                       = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1772 - assertIsFalse "first(abc\(def)@iana.orgin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1773 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1774 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1775 - assertIsFalse "1234 @ local(blah) .machine .examplein"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1776 - assertIsFalse "a@bin"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1777 - assertIsFalse "a@barin"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1778 - assertIsFalse "@about.museum"                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1779 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1780 - assertIsFalse ".first.last@iana.org"                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1781 - assertIsFalse "first.last.@iana.org"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1782 - assertIsFalse "first..last@iana.org"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1783 - assertIsFalse "\"first\"last\"@iana.org"                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1784 - assertIsFalse "first.last@"                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  1785 - assertIsFalse "first.last@-xample.com"                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1786 - assertIsFalse "first.last@exampl-.com"                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1787 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1788 - assertIsFalse "abc\@iana.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1789 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1790 - assertIsFalse "abc@def@iana.org"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1791 - assertIsFalse "@iana.org"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1792 - assertIsFalse "doug@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1793 - assertIsFalse "\"qu@iana.org"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1794 - assertIsFalse "ote\"@iana.org"                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1795 - assertIsFalse ".dot@iana.org"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1796 - assertIsFalse "dot.@iana.org"                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1797 - assertIsFalse "two..dot@iana.org"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1798 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1799 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1800 - assertIsFalse "hello world@iana.org"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1801 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1802 - assertIsFalse "test.iana.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1803 - assertIsFalse "test.@iana.org"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1804 - assertIsFalse "test..test@iana.org"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1805 - assertIsFalse ".test@iana.org"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1806 - assertIsFalse "test@test@iana.org"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1807 - assertIsFalse "test@@iana.org"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1808 - assertIsFalse "-- test --@iana.org"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1809 - assertIsFalse "[test]@iana.org"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1810 - assertIsFalse "\"test\"test\"@iana.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1811 - assertIsFalse "()[]\;:.><@iana.org"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1812 - assertIsFalse "test@."                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1813 - assertIsFalse "test@example."                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1814 - assertIsFalse "test@.org"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1815 - assertIsFalse "test@[123.123.123.123"                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1816 - assertIsFalse "test@123.123.123.123]"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1817 - assertIsFalse "NotAnEmail"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1818 - assertIsFalse "@NotAnEmail"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1819 - assertIsFalse "\"test\"blah\"@iana.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1820 - assertIsFalse ".wooly@iana.org"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1821 - assertIsFalse "wo..oly@iana.org"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1822 - assertIsFalse "pootietang.@iana.org"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1823 - assertIsFalse ".@iana.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1824 - assertIsFalse "Ima Fool@iana.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1825 - assertIsFalse "foo@[\1.2.3.4]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1826 - assertIsFalse "first.\"\".last@iana.org"                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1827 - assertIsFalse "first\last@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1828 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1829 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1830 - assertIsFalse "cal(foo(bar)@iamcal.com"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1831 - assertIsFalse "cal(foo)bar)@iamcal.com"                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1832 - assertIsFalse "cal(foo\)@iamcal.com"                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1833 - assertIsFalse "first(middle)last@iana.org"                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1834 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1835 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1836 - assertIsFalse ".@"                                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1837 - assertIsFalse "@bar.com"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1838 - assertIsFalse "@@bar.com"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1839 - assertIsFalse "aaa.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1840 - assertIsFalse "aaa@.com"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1841 - assertIsFalse "aaa@.123"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1842 - assertIsFalse "aaa@[123.123.123.123]a"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1843 - assertIsFalse "aaa@[123.123.123.333]"                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1844 - assertIsFalse "a@bar.com."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1845 - assertIsFalse "a@-b.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1846 - assertIsFalse "a@b-.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1847 - assertIsFalse "-@..com"                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1848 - assertIsFalse "-@a..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1849 - assertIsFalse "@about.museum-"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1850 - assertIsFalse "test@...........com"                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1851 - assertIsFalse "first.last@[IPv6::]"                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1852 - assertIsFalse "first.last@[IPv6::::]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1853 - assertIsFalse "first.last@[IPv6::b4]"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1854 - assertIsFalse "first.last@[IPv6::::b4]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1855 - assertIsFalse "first.last@[IPv6::b3:b4]"                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1856 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1857 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1858 - assertIsFalse "first.last@[IPv6:a1:]"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1859 - assertIsFalse "first.last@[IPv6:a1:::]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1860 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1861 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1862 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1863 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1864 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1865 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1866 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1867 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1868 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"        =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1869 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1870 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1871 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1872 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                        =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  1873 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1874 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1875 - assertIsFalse "first.last@[IPv6::a2::b4]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1876 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1877 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1878 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1879 - assertIsFalse "first.last@[.12.34.56.78]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1880 - assertIsFalse "first.last@[12.34.56.789]"                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1881 - assertIsFalse "first.last@[::12.34.56.78]"                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1882 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                            =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1883 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                            =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1884 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1885 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1886 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]" =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1887 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1888 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1889 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1890 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1891 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1892 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1893 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1894 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1895 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1896 - assertIsTrue  "first.last@[IPv6:::]"                                       =   4 =  OK 
     *  1897 - assertIsTrue  "first.last@[IPv6:::b4]"                                     =   4 =  OK 
     *  1898 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                  =   4 =  OK 
     *  1899 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                   =   4 =  OK 
     *  1900 - assertIsTrue  "first.last@[IPv6:a1::]"                                     =   4 =  OK 
     *  1901 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                  =   4 =  OK 
     *  1902 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                    =   4 =  OK 
     *  1903 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                    =   4 =  OK 
     *  1904 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1905 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1906 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1907 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1908 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                          =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1909 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1910 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1911 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1912 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1913 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"       =   4 =  OK 
     * 
     * 
     * ---- https://www.rohannagar.com/jmail/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1914 - assertIsFalse "\"qu@test.org"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1915 - assertIsFalse "ote\"@test.org"                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1916 - assertIsFalse "\"().:;<>[\]@example.com"                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1917 - assertIsFalse "\"\"\"@iana.org"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1918 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1919 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1920 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1921 - assertIsFalse "this is\"not\allowed@example.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1922 - assertIsFalse "this\ still\"not\allowed@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1923 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1924 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1925 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1926 - assertIsFalse "plainaddress"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1927 - assertIsFalse "@example.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1928 - assertIsFalse ".email@example.com"                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1929 - assertIsFalse "email.@example.com"                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1930 - assertIsFalse "email..email@example.com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1931 - assertIsFalse "email@-example.com"                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1932 - assertIsFalse "email@111.222.333.44444"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1933 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1934 - assertIsFalse "email@[12.34.44.56"                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1935 - assertIsFalse "email@14.44.56.34]"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1936 - assertIsFalse "email@[1.1.23.5f]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1937 - assertIsFalse "email@[3.256.255.23]"                                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1938 - assertIsFalse "\"first\\"last\"@test.org"                                  =   1 =  #### FEHLER ####    eMail-Adresse korrekt (Local Part mit String)
     *  1939 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1940 - assertIsFalse "first\@last@iana.org"                                       =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1941 - assertIsFalse "test@example.com "                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1942 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1943 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1944 - assertIsFalse "invalid@about.museum-"                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1945 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1946 - assertIsFalse "abc@def@test.org"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1947 - assertIsFalse "abc\@def@test.org"                                          =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1948 - assertIsFalse "abc\@test.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1949 - assertIsFalse "@test.org"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1950 - assertIsFalse ".dot@test.org"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1951 - assertIsFalse "dot.@test.org"                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1952 - assertIsFalse "two..dot@test.org"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1953 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1954 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1955 - assertIsFalse "hello world@test.org"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1956 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1957 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1958 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1959 - assertIsFalse "test.test.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1960 - assertIsFalse "test.@test.org"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1961 - assertIsFalse "test..test@test.org"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1962 - assertIsFalse ".test@test.org"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1963 - assertIsFalse "test@test@test.org"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1964 - assertIsFalse "test@@test.org"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1965 - assertIsFalse "-- test --@test.org"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1966 - assertIsFalse "[test]@test.org"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1967 - assertIsFalse "\"test\"test\"@test.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1968 - assertIsFalse "()[]\;:.><@test.org"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1969 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1970 - assertIsFalse ".@test.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1971 - assertIsFalse "Ima Fool@test.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1972 - assertIsFalse "\"first\\"last\"@test.org"                                  =   1 =  #### FEHLER ####    eMail-Adresse korrekt (Local Part mit String)
     *  1973 - assertIsFalse "foo@[.2.3.4]"                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1974 - assertIsFalse "first\last@test.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1975 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1976 - assertIsFalse "first(middle)last@test.org"                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1977 - assertIsFalse "\"test\"test@test.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1978 - assertIsFalse "()@test.com"                                                =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1979 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  1980 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1981 - assertIsFalse "invalid@[1]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1982 - assertIsFalse "ä📧@-foo"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1983 - assertIsFalse "ä📧@foo-"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1984 - assertIsFalse "first(comment(inner@comment.com"                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1985 - assertIsFalse "Joe A Smith <email@example.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1986 - assertIsFalse "Joe A Smith email@example.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1987 - assertIsFalse "Joe A Smith <email@example.com->"                           =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1988 - assertIsFalse "Joe A Smith <email@-example.com->"                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1989 - assertIsFalse "Joe A Smith <email>"                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1990 - assertIsTrue  "\"email\"@example.com"                                      =   1 =  OK 
     *  1991 - assertIsTrue  "\"first@last\"@test.org"                                    =   1 =  OK 
     *  1992 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                 =   1 =  OK 
     *  1993 - assertIsTrue  "\"first\"last\"@test.org"                                   =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1994 - assertIsTrue  "much.\"more\ unusual\"@example.com"                         =   1 =  OK 
     *  1995 - assertIsTrue  "\"first\last\"@test.org"                                    =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1996 - assertIsTrue  "\"Abc\@def\"@test.org"                                      =   1 =  OK 
     *  1997 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                  =   1 =  OK 
     *  1998 - assertIsTrue  "\"Joe.\Blow\"@test.org"                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1999 - assertIsTrue  "\"Abc@def\"@test.org"                                       =   1 =  OK 
     *  2000 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                   =   1 =  OK 
     *  2001 - assertIsTrue  "\"Doug \"Ace\" L.\"@test.org"                               =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2002 - assertIsTrue  "\"[[ test ]]\"@test.org"                                    =   1 =  OK 
     *  2003 - assertIsTrue  "\"test.test\"@test.org"                                     =   1 =  OK 
     *  2004 - assertIsTrue  "test.\"test\"@test.org"                                     =   1 =  OK 
     *  2005 - assertIsTrue  "\"test@test\"@test.org"                                     =   1 =  OK 
     *  2006 - assertIsTrue  "\"test  est\"@test.org"                                      =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2007 - assertIsTrue  "\"first\".\"last\"@test.org"                                =   1 =  OK 
     *  2008 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                         =   1 =  OK 
     *  2009 - assertIsTrue  "\"first\".last@test.org"                                    =   1 =  OK 
     *  2010 - assertIsTrue  "first.\"last\"@test.org"                                    =   1 =  OK 
     *  2011 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                     =   1 =  OK 
     *  2012 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                         =   1 =  OK 
     *  2013 - assertIsTrue  "\"first.middle.last\"@test.org"                             =   1 =  OK 
     *  2014 - assertIsTrue  "\"first..last\"@test.org"                                   =   1 =  OK 
     *  2015 - assertIsTrue  "\"Unicode NULL \"@char.com"                                 =   1 =  OK 
     *  2016 - assertIsTrue  "\"test\blah\"@test.org"                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2017 - assertIsTrue  "\"testlah\"@test.org"                                      =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2018 - assertIsTrue  "\"test\"blah\"@test.org"                                    =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2019 - assertIsTrue  "\"first\\"last\"@test.org"                                  =   1 =  OK 
     *  2020 - assertIsTrue  "\"Test \"Fail\" Ing\"@test.org"                             =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2021 - assertIsTrue  "\"test blah\"@test.org"                                     =   1 =  OK 
     *  2022 - assertIsTrue  "first.last@test.org"                                        =   0 =  OK 
     *  2023 - assertIsTrue  "jdoe@machine(comment).example"                              = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2024 - assertIsTrue  "first.\"\".last@test.org"                                   =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2025 - assertIsTrue  "\"\"@test.org"                                              =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2026 - assertIsTrue  "very.common@example.org"                                    =   0 =  OK 
     *  2027 - assertIsTrue  "test/test@test.com"                                         =   0 =  OK 
     *  2028 - assertIsTrue  "user-@example.org"                                          =   0 =  OK 
     *  2029 - assertIsTrue  "firstname.lastname@example.com"                             =   0 =  OK 
     *  2030 - assertIsTrue  "email@subdomain.example.com"                                =   0 =  OK 
     *  2031 - assertIsTrue  "firstname+lastname@example.com"                             =   0 =  OK 
     *  2032 - assertIsTrue  "1234567890@example.com"                                     =   0 =  OK 
     *  2033 - assertIsTrue  "email@example-one.com"                                      =   0 =  OK 
     *  2034 - assertIsTrue  "_______@example.com"                                        =   0 =  OK 
     *  2035 - assertIsTrue  "email@example.name"                                         =   0 =  OK 
     *  2036 - assertIsTrue  "email@example.museum"                                       =   0 =  OK 
     *  2037 - assertIsTrue  "email@example.co.jp"                                        =   0 =  OK 
     *  2038 - assertIsTrue  "firstname-lastname@example.com"                             =   0 =  OK 
     *  2039 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  2040 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2041 - assertIsTrue  "first.last@123.test.org"                                    =   0 =  OK 
     *  2042 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2043 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org" =   0 =  OK 
     *  2044 - assertIsTrue  "user+mailbox@test.org"                                      =   0 =  OK 
     *  2045 - assertIsTrue  "customer/department=shipping@test.org"                      =   0 =  OK 
     *  2046 - assertIsTrue  "$A12345@test.org"                                           =   0 =  OK 
     *  2047 - assertIsTrue  "!def!xyz%abc@test.org"                                      =   0 =  OK 
     *  2048 - assertIsTrue  "_somename@test.org"                                         =   0 =  OK 
     *  2049 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                            =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2050 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2051 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2052 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2053 - assertIsTrue  "+@b.c"                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2054 - assertIsTrue  "TEST@test.org"                                              =   0 =  OK 
     *  2055 - assertIsTrue  "1234567890@test.org"                                        =   0 =  OK 
     *  2056 - assertIsTrue  "test-test@test.org"                                         =   0 =  OK 
     *  2057 - assertIsTrue  "t*est@test.org"                                             =   0 =  OK 
     *  2058 - assertIsTrue  "+1~1+@test.org"                                             =   0 =  OK 
     *  2059 - assertIsTrue  "{_test_}@test.org"                                          =   0 =  OK 
     *  2060 - assertIsTrue  "valid@about.museum"                                         =   0 =  OK 
     *  2061 - assertIsTrue  "a@bar"                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2062 - assertIsTrue  "cal(foo\@bar)@iamcal.com"                                   =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2063 - assertIsTrue  "(comment)test@test.org"                                     =   6 =  OK 
     *  2064 - assertIsTrue  "(foo)cal(bar)@(baz)iamcal.com(quux)"                        =  99 =  #### FEHLER ####    Kommentar: kein zweiter Kommentar gueltig
     *  2065 - assertIsTrue  "cal(foo\)bar)@iamcal.com"                                   =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2066 - assertIsTrue  "cal(woo(yay)hoopla)@iamcal.com"                             =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2067 - assertIsTrue  "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org" =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2068 - assertIsTrue  "pete(his account)@silly.test(his host)"                     =  99 =  #### FEHLER ####    Kommentar: kein zweiter Kommentar gueltig
     *  2069 - assertIsTrue  "first(abc\(def)@test.org"                                   =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2070 - assertIsTrue  "a(a(b(c)d(e(f))g)h(i)j)@test.org"                           =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2071 - assertIsTrue  "c@(Chris's host.)public.example"                            =   6 =  OK 
     *  2072 - assertIsTrue  "_Yosemite.Sam@test.org"                                     =   0 =  OK 
     *  2073 - assertIsTrue  "~@test.org"                                                 =   0 =  OK 
     *  2074 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"              =   6 =  OK 
     *  2075 - assertIsTrue  "test@Bücher.ch"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2076 - assertIsTrue  "あいうえお@example.com"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2077 - assertIsTrue  "Pelé@example.com"                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2078 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2079 - assertIsTrue  "我買@屋企.香港"                                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2080 - assertIsTrue  "二ノ宮@黒川.日本"                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2081 - assertIsTrue  "медведь@с-балалайкой.рф"                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2082 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2083 - assertIsTrue  "email@example.com (Joe Smith)"                              =   6 =  OK 
     *  2084 - assertIsTrue  "cal@iamcal(woo).(yay)com"                                   = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2085 - assertIsTrue  "first(abc.def).last@test.org"                               = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2086 - assertIsTrue  "first(a\"bc.def).last@test.org"                             =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2087 - assertIsTrue  "first.(\")middle.last(\")@test.org"                         = 101 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2088 - assertIsTrue  "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).com" = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2089 - assertIsTrue  "first().last@test.org"                                      = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2090 - assertIsTrue  "mymail\@hello@hotmail.com"                                  =   0 =  OK 
     *  2091 - assertIsTrue  "Abc\@def@test.org"                                          =   0 =  OK 
     *  2092 - assertIsTrue  "Fred\ Bloggs@test.org"                                      =   0 =  OK 
     *  2093 - assertIsTrue  "Joe.\\Blow@test.org"                                        =   0 =  OK 
     * 
     * 
     * ---- https://www.linuxjournal.com/article/9585 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2094 - assertIsTrue  "dclo@us.ibm.com"                                            =   0 =  OK 
     *  2095 - assertIsTrue  "abc\@def@example.com"                                       =   0 =  OK 
     *  2096 - assertIsTrue  "abc\\@example.com"                                          =   0 =  OK 
     *  2097 - assertIsTrue  "Fred\ Bloggs@example.com"                                   =   0 =  OK 
     *  2098 - assertIsTrue  "Joe.\\Blow@example.com"                                     =   0 =  OK 
     *  2099 - assertIsTrue  "\"Abc@def\"@example.com"                                    =   1 =  OK 
     *  2100 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                =   1 =  OK 
     *  2101 - assertIsTrue  "customer/department=shipping@example.com"                   =   0 =  OK 
     *  2102 - assertIsTrue  "$A12345@example.com"                                        =   0 =  OK 
     *  2103 - assertIsTrue  "!def!xyz%abc@example.com"                                   =   0 =  OK 
     *  2104 - assertIsTrue  "_somename@example.com"                                      =   0 =  OK 
     *  2105 - assertIsTrue  "user+mailbox@example.com"                                   =   0 =  OK 
     *  2106 - assertIsTrue  "peter.piper@example.com"                                    =   0 =  OK 
     *  2107 - assertIsTrue  "Doug\ \\"Ace\\"\ Lovell@example.com"                        =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2108 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                          =   1 =  OK 
     *  2109 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                   =   0 =  OK 
     *  2110 - assertIsFalse "abc@def@example.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2111 - assertIsFalse "abc\\@def@example.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2112 - assertIsFalse "abc\@example.com"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2113 - assertIsFalse "@example.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2114 - assertIsFalse "doug@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2115 - assertIsFalse "\"qu@example.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2116 - assertIsFalse "ote\"@example.com"                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2117 - assertIsFalse ".dot@example.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2118 - assertIsFalse "dot.@example.com"                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2119 - assertIsFalse "two..dot@example.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2120 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2121 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2122 - assertIsFalse "hello world@example.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2123 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     * 
     * 
     * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2124 - assertIsTrue  "jkt@gmail.com"                                              =   0 =  OK 
     *  2125 - assertIsFalse " jkt@gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2126 - assertIsFalse "jkt@ gmail.com"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2127 - assertIsFalse "jkt@g mail.com"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2128 - assertIsFalse "jkt @gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2129 - assertIsFalse "j kt@gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * 
     * ---- https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2130 - assertIsTrue  "jinujawad6s@gmail.com"                                      =   0 =  OK 
     *  2131 - assertIsTrue  "drp@drp.cz"                                                 =   0 =  OK 
     *  2132 - assertIsTrue  "tvf@tvf.cz"                                                 =   0 =  OK 
     *  2133 - assertIsTrue  "info@ermaelan.com"                                          =   0 =  OK 
     *  2134 - assertIsTrue  "begeddov@jfinity.com"                                       =   0 =  OK 
     *  2135 - assertIsTrue  "vdv@dyomedea.com"                                           =   0 =  OK 
     *  2136 - assertIsTrue  "me@aaronsw.com"                                             =   0 =  OK 
     *  2137 - assertIsTrue  "aaron@theinfo.org"                                          =   0 =  OK 
     *  2138 - assertIsTrue  "rss-dev@yahoogroups.com"                                    =   0 =  OK 
     * 
     * 
     * ---- https://www.journaldev.com/638/java-email-validation-regex ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2139 - assertIsTrue  "journaldev@yahoo.com"                                       =   0 =  OK 
     *  2140 - assertIsTrue  "journaldev-100@yahoo.com"                                   =   0 =  OK 
     *  2141 - assertIsTrue  "journaldev.100@yahoo.com"                                   =   0 =  OK 
     *  2142 - assertIsTrue  "journaldev111@journaldev.com"                               =   0 =  OK 
     *  2143 - assertIsTrue  "journaldev-100@journaldev.net"                              =   0 =  OK 
     *  2144 - assertIsTrue  "journaldev.100@journaldev.com.au"                           =   0 =  OK 
     *  2145 - assertIsTrue  "journaldev@1.com"                                           =   0 =  OK 
     *  2146 - assertIsTrue  "journaldev@gmail.com.com"                                   =   0 =  OK 
     *  2147 - assertIsTrue  "journaldev+100@gmail.com"                                   =   0 =  OK 
     *  2148 - assertIsTrue  "journaldev-100@yahoo-test.com"                              =   0 =  OK 
     *  2149 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                          =   0 =  OK 
     *  2150 - assertIsFalse "journaldev"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2151 - assertIsFalse "journaldev@.com.my"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2152 - assertIsFalse "journaldev123@gmail.a"                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2153 - assertIsFalse "journaldev123@.com"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2154 - assertIsFalse "journaldev123@.com.com"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2155 - assertIsFalse ".journaldev@journaldev.com"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2156 - assertIsFalse "journaldev()*@gmail.com"                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2157 - assertIsFalse "journaldev@%*.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2158 - assertIsFalse "journaldev..2002@gmail.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2159 - assertIsFalse "journaldev.@gmail.com"                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2160 - assertIsFalse "journaldev@journaldev@gmail.com"                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2161 - assertIsFalse "journaldev@gmail.com.1a"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2162 - assertIsTrue  "me@example.com"                                             =   0 =  OK 
     *  2163 - assertIsTrue  "a.nonymous@example.com"                                     =   0 =  OK 
     *  2164 - assertIsTrue  "name+tag@example.com"                                       =   0 =  OK 
     *  2165 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                             =   2 =  OK 
     *  2166 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]" =   4 =  OK 
     *  2167 - assertIsTrue  "me(this is a comment)@example.com"                          =   6 =  OK 
     *  2168 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                  =   1 =  OK 
     *  2169 - assertIsTrue  "me.example@com"                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2170 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"            =   0 =  OK 
     *  2171 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net" =   0 =  OK 
     *  2172 - assertIsFalse "NotAnEmail"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2173 - assertIsFalse "me@"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2174 - assertIsFalse "@example.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2175 - assertIsFalse ".me@example.com"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2176 - assertIsFalse "me@example..com"                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2177 - assertIsFalse "me\@example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2178 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2179 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2180 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * 
     * ---- https://stackoverflow.com/questions/22993545/ruby-email-validation-with-regex?noredirect=1&lq=1 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2181 - assertIsTrue  "hello.me_1@email.com"                                       =   0 =  OK 
     *  2182 - assertIsTrue  "something_valid@somewhere.tld"                              =   0 =  OK 
     *  2183 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                  =   1 =  OK 
     *  2184 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                      =   0 =  OK 
     *  2185 - assertIsFalse "foo.bar#gmail.co.u"                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2186 - assertIsFalse "f...bar@gmail.com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2187 - assertIsFalse "get_at_m.e@gmail"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2188 - assertIsFalse ".....@a...."                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2189 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2190 - assertIsFalse "a.b@example,com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2191 - assertIsFalse "a.b@example,co.de"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2192 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2193 - assertIsTrue  "Ärger.Öde@Übel.de"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2194 - assertIsTrue  "Smørrebrød@danmark.dk"                                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2195 - assertIsTrue  "ip.without.brackets@1.2.3.4"                                =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2196 - assertIsTrue  "ip.without.brackets@1:2:3:4:5:6:7:8"                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2197 - assertIsTrue  "(space after comment) john.smith@example.com"               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2198 - assertIsTrue  "email.address.without@topleveldomain"                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2199 - assertIsTrue  "EmailAddressWithout@PointSeperator"                         =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2200 - assertIsTrue  "valid.email.from.nr888@fillup.tofalse.com"                  =   0 =  OK 
     *           ...
     *  2201 - assertIsTrue  "valid.email.to.nr1312@fillup.tofalse.com"                   =   0 =  OK 
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  1312   KORREKT 1227 =   93.521 % | FALSCH ERKANNT   85 =    6.479 % = Error 0
     *   ASSERT_IS_FALSE 1312   KORREKT 1297 =   98.857 % | FALSCH ERKANNT   15 =    1.143 % = Error 0
     * 
     *   GESAMT          2624   KORREKT 2524 =   96.189 % | FALSCH ERKANNT  100 =    3.811 % = Error 0
     * 
     * 
     *   Millisekunden    163 = 0.06211890243902439
     * 
     * </pre> 
     */

    /*
     * Variable fuer die Startzeit der Funktion deklarieren und die aktuellen
     * System-Millisekunden speichern
     */
    long time_stamp_start = System.currentTimeMillis();

    //generateTestCases();

    try
    {
      wlHeadline( "General Correct" );

      assertIsTrue( "name1.name2@domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.domain1.tld" );

      assertIsTrue( "ip4.adress@[1.2.3.4]" );
      assertIsTrue( "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]" );
      
      assertIsTrue( "\"quote1\".name1@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"@domain1.tld" );
      
      assertIsTrue( "(comment1)name1@domain1.tld" );
      assertIsTrue( "name1(comment1)@domain1.tld" );
      
      assertIsTrue( "(comment1)\"quote1\".name1@domain1.tld" );
      assertIsTrue( "(comment1)name1.\"quote1\"@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"(comment1)@domain1.tld" );      
      assertIsTrue( "\"quote1\".name1(comment1)@domain1.tld" );

      assertIsTrue( "name3 <name1.name2@domain1.tld>" );
      assertIsTrue( "<name1.name2@domain1.tld> name3" );

      assertIsTrue( "A.B@C.DE" );
      assertIsTrue( "A.\"B\"@C.DE" );
      assertIsTrue( "A.B@[1.2.3.4]" );
      assertIsTrue( "A.\"B\"@[1.2.3.4]" );
      assertIsTrue( "A.B@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "(A)B@C.DE" );
      assertIsTrue( "A(B)@C.DE" );
      assertIsTrue( "(A)\"B\"@C.DE" );
      assertIsTrue( "\"A\"(B)@C.DE" );
      assertIsTrue( "(A)B@[1.2.3.4]" );
      assertIsTrue( "A(B)@[1.2.3.4]" );
      assertIsTrue( "(A)\"B\"@[1.2.3.4]" );
      assertIsTrue( "\"A\"(B)@[1.2.3.4]" );
      assertIsTrue( "(A)B@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "A(B)@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsTrue( "firstname.lastname@domain.com" );
      assertIsTrue( "firstname+lastname@domain.com" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
      assertIsTrue( "a.b.c.d@domain.com" );
      assertIsTrue( "1@domain.com" );
      assertIsTrue( "a@domain.com" );
      assertIsTrue( "email@domain.co.de" );
      assertIsTrue( "email@domain.com" );
      assertIsTrue( "email@subdomain.domain.com" );
      assertIsTrue( "2@bde.cc" );
      assertIsTrue( "-@bde.cc" );
      assertIsTrue( "a2@bde.cc" );
      assertIsTrue( "a-b@bde.cc" );
      assertIsTrue( "ab@b-de.cc" );
      assertIsTrue( "a+b@bde.cc" );
      assertIsTrue( "f.f.f@bde.cc" );
      assertIsTrue( "ab_c@bde.cc" );
      assertIsTrue( "_-_@bde.cc" );
      assertIsTrue( "w.b.f@test.com" );
      assertIsTrue( "w.b.f@test.museum" );
      assertIsTrue( "a.a@test.com" );
      assertIsTrue( "ab@288.120.150.10.com" );
      assertIsTrue( "ab@[120.254.254.120]" );
      assertIsTrue( "1234567890@domain.com" );
      assertIsTrue( "john.smith@example.com" );

      wlHeadline( "No Input" );

      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "        " );

      wlHeadline( "AT-Character" );

      assertIsFalse( "email.with.no.at.character" );
      assertIsFalse( "@.local.name.starts.with.at@domain.com" );
      assertIsFalse( "@no.local.email.part.domain.com" );
      assertIsFalse( "local.name.ends.with.at@@domain.com" );
      assertIsFalse( "local.name.with.at.before@.point@domain.com" );
      assertIsFalse( "local.name.with.at.after.@point@domain.com" );
      assertIsFalse( "local.name.with.double.at@@test@domain.com" );
      assertIsFalse( "(comment @) local.name.with.comment.with.at@domain.com" );
      assertIsTrue( "\"quote@\".local.name.with.qoute.with.at@domain.com" );
      assertIsTrue( "qouted.\\@.character@at.domain.com" );
      assertIsTrue( "\\@@at.domain.com" );      
      assertIsFalse( "@@at.domain.com" );
      assertIsFalse( "@@@@@@@at.domain.com" );
      assertIsTrue( "\\@.\\@.\\@.\\@.\\@.\\@@at.domain.com" );
      assertIsFalse( "@.@.@.@.@.@@at.domain.com" );
      assertIsFalse( "name @ <pointy.brackets1.with.at@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.at@domain.com> name @" );

      assertIsFalse( "ABCDEFGHIJKLMNOP" );
      assertIsFalse( "ABC.DEF.GHI.JKL" );
      assertIsFalse( "ABC.DEF@ GHI.JKL" );
      assertIsFalse( "ABC.DEF @GHI.JKL" );
      assertIsFalse( "ABC.DEF @ GHI.JKL" );
      assertIsFalse( "@" );
      assertIsFalse( "@.@.@." );
      assertIsFalse( "@.@.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@.@.@GHI.JKL" );
      assertIsFalse( "@@@GHI.JKL" );
      assertIsFalse( "@GHI.JKL" );
      assertIsFalse( "ABC.DEF@" );
      assertIsFalse( "ABC.DEF@@GHI.JKL" );
      assertIsFalse( "ABC@DEF@GHI.JKL" );
      assertIsFalse( "@%^%#$@#$@#.com" );
      assertIsFalse( "@domain.com" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@domain@domain.com" );
      assertIsFalse( "@@@@@@gmail.com" );
      assertIsFalse( "first@last@test.org" );
      assertIsFalse( "@test@a.com" );
      assertIsFalse( "@\"someStringThatMightBe@email.com" );
      assertIsFalse( "test@@test.com" );

      wlHeadline( "Seperator" );

      assertIsFalse( "ABCDEF@GHIJKL" );
      assertIsFalse( "ABC.DEF@GHIJKL" );
      assertIsFalse( ".ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL." );
      assertIsFalse( "ABC..DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI..JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL.." );
      assertIsFalse( "ABC.DEF.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@.GHI.JKL" );
      assertIsFalse( "ABC.DEF@." );
      assertIsFalse( "john..doe@example.com" );
      assertIsFalse( "john.doe@example..com" );
      assertIsTrue( "\"john..doe\"@example.com" );
      assertIsFalse( "..........@domain." );
      assertIsFalse( "test.@test.com" );
      assertIsFalse( ".test.@test.com" );
      assertIsFalse( "asdf@asdf@asdf.com" );
      assertIsFalse( "email@provider..com" );

      wlHeadline( "Characters" );

      assertIsTrue( "ABC1.DEF2@GHI3.JKL4" );
      assertIsTrue( "ABC.DEF_@GHI.JKL" );
      assertIsTrue( "#ABC.DEF@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.JK2" );
      assertIsTrue( "ABC.DEF@2HI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.2KL" );
      assertIsFalse( "ABC.DEF@GHI.JK-" );
      assertIsFalse( "ABC.DEF@GHI.JK_" );
      assertIsFalse( "ABC.DEF@-HI.JKL" );
      assertIsFalse( "ABC.DEF@_HI.JKL" );
      assertIsFalse( "ABC DEF@GHI.DE" );
      assertIsFalse( "ABC.DEF@GHI DE" );
      assertIsFalse( "A . B & C . D" );
      assertIsFalse( " A . B & C . D" );
      assertIsFalse( "(?).[!]@{&}.<:>" );

      assertIsTrue( "&local&&name&with&$@amp.com" );
      assertIsTrue( "*local**name*with*@asterisk.com" );
      assertIsTrue( "$local$$name$with$@dollar.com" );
      assertIsTrue( "=local==name=with=@equality.com" );
      assertIsTrue( "!local!!name!with!@exclamation.com" );
      assertIsTrue( "`local``name`with`@grave-accent.com" );
      assertIsTrue( "#local##name#with#@hash.com" );
      assertIsTrue( "-local--name-with-@hypen.com" );
      assertIsTrue( "{local{name{{with{@leftbracket.com" );
      assertIsTrue( "%local%%name%with%@percentage.com" );
      assertIsTrue( "|local||name|with|@pipe.com" );
      assertIsTrue( "+local++name+with+@plus.com" );
      assertIsTrue( "?local??name?with?@question.com" );
      assertIsTrue( "}local}name}}with}@rightbracket.com" );
      assertIsTrue( "~local~~name~with~@tilde.com" );
      assertIsTrue( "^local^^name^with^@xor.com" );
      assertIsTrue( "_local__name_with_@underscore.com" );
      assertIsFalse( ":local::name:with:@colon.com" );

      assertIsTrue( "&.local.name.starts.with.amp@domain.com" );
      assertIsTrue( "local.name.ends.with.amp&@domain.com" );
      assertIsTrue( "local.name.with.amp.before&.point@domain.com" );
      assertIsTrue( "local.name.with.amp.after.&point@domain.com" );
      assertIsTrue( "local.name.with.double.amp&&test@domain.com" );
      assertIsFalse( "(comment &) local.name.with.comment.with.amp@domain.com" ); // or ist it true?
      assertIsTrue( "\"quote&\".local.name.with.qoute.with.amp@domain.com" );
      assertIsTrue( "&@amp.domain.com" );
      assertIsTrue( "&&&&&&@amp.domain.com" );
      assertIsTrue( "&.&.&.&.&.&@amp.domain.com" );
      assertIsFalse( "name & <pointy.brackets1.with.amp@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.amp@domain.com> name &" ); // ?

      assertIsTrue( "*.local.name.starts.with.asterisk@domain.com" );
      assertIsTrue( "local.name.ends.with.asterisk*@domain.com" );
      assertIsTrue( "local.name.with.asterisk.before*.point@domain.com" );
      assertIsTrue( "local.name.with.asterisk.after.*point@domain.com" );
      assertIsTrue( "local.name.with.double.asterisk**test@domain.com" );
      assertIsFalse( "(comment *) local.name.with.comment.with.asterisk@domain.com" ); // ?
      assertIsTrue( "\"quote*\".local.name.with.qoute.with.asterisk@domain.com" );
      assertIsTrue( "*@asterisk.domain.com" );
      assertIsTrue( "******@asterisk.domain.com" );
      assertIsTrue( "*.*.*.*.*.*@asterisk.domain.com" );
      assertIsFalse( "name * <pointy.brackets1.with.asterisk@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.asterisk@domain.com> name *" ); // ?

      assertIsTrue( "$.local.name.starts.with.dollar@domain.com" );
      assertIsTrue( "local.name.ends.with.dollar$@domain.com" );
      assertIsTrue( "local.name.with.dollar.before$.point@domain.com" );
      assertIsTrue( "local.name.with.dollar.after.$point@domain.com" );
      assertIsTrue( "local.name.with.double.dollar$$test@domain.com" );
      assertIsFalse( "(comment $) local.name.with.comment.with.dollar@domain.com" ); // ?
      assertIsTrue( "\"quote$\".local.name.with.qoute.with.dollar@domain.com" );
      assertIsTrue( "$@dollar.domain.com" );
      assertIsTrue( "$$$$$$@dollar.domain.com" );
      assertIsTrue( "$.$.$.$.$.$@dollar.domain.com" );
      assertIsFalse( "name $ <pointy.brackets1.with.dollar@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.dollar@domain.com> name $" ); // ?

      assertIsTrue( "=.local.name.starts.with.equality@domain.com" );
      assertIsTrue( "local.name.ends.with.equality=@domain.com" );
      assertIsTrue( "local.name.with.equality.before=.point@domain.com" );
      assertIsTrue( "local.name.with.equality.after.=point@domain.com" );
      assertIsTrue( "local.name.with.double.equality==test@domain.com" );
      assertIsFalse( "(comment =) local.name.with.comment.with.equality@domain.com" ); // ?
      assertIsTrue( "\"quote=\".local.name.with.qoute.with.equality@domain.com" );
      assertIsTrue( "=@equality.domain.com" );
      assertIsTrue( "======@equality.domain.com" );
      assertIsTrue( "=.=.=.=.=.=@equality.domain.com" );
      assertIsFalse( "name = <pointy.brackets1.with.equality@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.equality@domain.com> name =" ); // ?

      assertIsTrue( "!.local.name.starts.with.exclamation@domain.com" );
      assertIsTrue( "local.name.ends.with.exclamation!@domain.com" );
      assertIsTrue( "local.name.with.exclamation.before!.point@domain.com" );
      assertIsTrue( "local.name.with.exclamation.after.!point@domain.com" );
      assertIsTrue( "local.name.with.double.exclamation!!test@domain.com" );
      assertIsFalse( "(comment !) local.name.with.comment.with.exclamation@domain.com" ); // ?
      assertIsTrue( "\"quote!\".local.name.with.qoute.with.exclamation@domain.com" );
      assertIsTrue( "!@exclamation.domain.com" );
      assertIsTrue( "!!!!!!@exclamation.domain.com" );
      assertIsTrue( "!.!.!.!.!.!@exclamation.domain.com" );
      assertIsFalse( "name ! <pointy.brackets1.with.exclamation@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.exclamation@domain.com> name !" ); // ?

      assertIsTrue( "`.local.name.starts.with.grave-accent@domain.com" );
      assertIsTrue( "local.name.ends.with.grave-accent`@domain.com" );
      assertIsTrue( "local.name.with.grave-accent.before`.point@domain.com" );
      assertIsTrue( "local.name.with.grave-accent.after.`point@domain.com" );
      assertIsTrue( "local.name.with.double.grave-accent``test@domain.com" );
      assertIsFalse( "(comment `) local.name.with.comment.with.grave-accent@domain.com" ); // ?
      assertIsTrue( "\"quote`\".local.name.with.qoute.with.grave-accent@domain.com" );
      assertIsTrue( "`@grave-accent.domain.com" );
      assertIsTrue( "``````@grave-accent.domain.com" );
      assertIsTrue( "`.`.`.`.`.`@grave-accent.domain.com" );
      assertIsFalse( "name ` <pointy.brackets1.with.grave-accent@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.grave-accent@domain.com> name `" ); // ?

      assertIsTrue( "#.local.name.starts.with.hash@domain.com" );
      assertIsTrue( "local.name.ends.with.hash#@domain.com" );
      assertIsTrue( "local.name.with.hash.before#.point@domain.com" );
      assertIsTrue( "local.name.with.hash.after.#point@domain.com" );
      assertIsTrue( "local.name.with.double.hash##test@domain.com" );
      assertIsFalse( "(comment #) local.name.with.comment.with.hash@domain.com" ); // ?
      assertIsTrue( "\"quote#\".local.name.with.qoute.with.hash@domain.com" );
      assertIsTrue( "#@hash.domain.com" );
      assertIsTrue( "######@hash.domain.com" );
      assertIsTrue( "#.#.#.#.#.#@hash.domain.com" );
      assertIsFalse( "name # <pointy.brackets1.with.hash@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.hash@domain.com> name #" ); // ?

      assertIsTrue( "-.local.name.starts.with.hypen@domain.com" );
      assertIsTrue( "local.name.ends.with.hypen-@domain.com" );
      assertIsTrue( "local.name.with.hypen.before-.point@domain.com" );
      assertIsTrue( "local.name.with.hypen.after.-point@domain.com" );
      assertIsTrue( "local.name.with.double.hypen--test@domain.com" );
      assertIsFalse( "(comment -) local.name.with.comment.with.hypen@domain.com" ); // ?
      assertIsTrue( "\"quote-\".local.name.with.qoute.with.hypen@domain.com" );
      assertIsTrue( "-@hypen.domain.com" );
      assertIsTrue( "------@hypen.domain.com" );
      assertIsTrue( "-.-.-.-.-.-@hypen.domain.com" );
      assertIsFalse( "name - <pointy.brackets1.with.hypen@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.hypen@domain.com> name -" ); // ?

      assertIsTrue( "{.local.name.starts.with.leftbracket@domain.com" );
      assertIsTrue( "local.name.ends.with.leftbracket{@domain.com" );
      assertIsTrue( "local.name.with.leftbracket.before{.point@domain.com" );
      assertIsTrue( "local.name.with.leftbracket.after.{point@domain.com" );
      assertIsTrue( "local.name.with.double.leftbracket{{test@domain.com" );
      assertIsFalse( "(comment {) local.name.with.comment.with.leftbracket@domain.com" ); // ?
      assertIsTrue( "\"quote{\".local.name.with.qoute.with.leftbracket@domain.com" );
      assertIsTrue( "{@leftbracket.domain.com" );
      assertIsTrue( "{{{{{{@leftbracket.domain.com" );
      assertIsTrue( "{.{.{.{.{.{@leftbracket.domain.com" );
      assertIsFalse( "name { <pointy.brackets1.with.leftbracket@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.leftbracket@domain.com> name {" ); // ?

      assertIsTrue( "%.local.name.starts.with.percentage@domain.com" );
      assertIsTrue( "local.name.ends.with.percentage%@domain.com" );
      assertIsTrue( "local.name.with.percentage.before%.point@domain.com" );
      assertIsTrue( "local.name.with.percentage.after.%point@domain.com" );
      assertIsTrue( "local.name.with.double.percentage%%test@domain.com" );
      assertIsFalse( "(comment %) local.name.with.comment.with.percentage@domain.com" ); // ?
      assertIsTrue( "\"quote%\".local.name.with.qoute.with.percentage@domain.com" );
      assertIsTrue( "%@percentage.domain.com" );
      assertIsTrue( "%%%%%%@percentage.domain.com" );
      assertIsTrue( "%.%.%.%.%.%@percentage.domain.com" );
      assertIsFalse( "name % <pointy.brackets1.with.percentage@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.percentage@domain.com> name %" ); // ?

      assertIsTrue( "|.local.name.starts.with.pipe@domain.com" );
      assertIsTrue( "local.name.ends.with.pipe|@domain.com" );
      assertIsTrue( "local.name.with.pipe.before|.point@domain.com" );
      assertIsTrue( "local.name.with.pipe.after.|point@domain.com" );
      assertIsTrue( "local.name.with.double.pipe||test@domain.com" );
      assertIsFalse( "(comment |) local.name.with.comment.with.pipe@domain.com" ); // ?
      assertIsTrue( "\"quote|\".local.name.with.qoute.with.pipe@domain.com" );
      assertIsTrue( "|@pipe.domain.com" );
      assertIsTrue( "||||||@pipe.domain.com" );
      assertIsTrue( "|.|.|.|.|.|@pipe.domain.com" );
      assertIsFalse( "name | <pointy.brackets1.with.pipe@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.pipe@domain.com> name |" ); // ?

      assertIsTrue( "+.local.name.starts.with.plus@domain.com" );
      assertIsTrue( "local.name.ends.with.plus+@domain.com" );
      assertIsTrue( "local.name.with.plus.before+.point@domain.com" );
      assertIsTrue( "local.name.with.plus.after.+point@domain.com" );
      assertIsTrue( "local.name.with.double.plus++test@domain.com" );
      assertIsFalse( "(comment +) local.name.with.comment.with.plus@domain.com" ); // ?
      assertIsTrue( "\"quote+\".local.name.with.qoute.with.plus@domain.com" );
      assertIsTrue( "+@plus.domain.com" );
      assertIsTrue( "++++++@plus.domain.com" );
      assertIsTrue( "+.+.+.+.+.+@plus.domain.com" );
      assertIsFalse( "name + <pointy.brackets1.with.plus@domain.com>" ); // ?
      assertIsFalse( "<pointy.brackets2.with.plus@domain.com> name +" ); // ?

      assertIsTrue( "?.local.name.starts.with.question@domain.com" );
      assertIsTrue( "local.name.ends.with.question?@domain.com" );
      assertIsTrue( "local.name.with.question.before?.point@domain.com" );
      assertIsTrue( "local.name.with.question.after.?point@domain.com" );
      assertIsTrue( "local.name.with.double.question??test@domain.com" );
      assertIsFalse( "(comment ?) local.name.with.comment.with.question@domain.com" ); // ?
      assertIsTrue( "\"quote?\".local.name.with.qoute.with.question@domain.com" );
      assertIsTrue( "?@question.domain.com" );
      assertIsTrue( "??????@question.domain.com" );
      assertIsTrue( "?.?.?.?.?.?@question.domain.com" );
      assertIsFalse( "name ? <pointy.brackets1.with.question@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.question@domain.com> name ?" );

      assertIsTrue( "}.local.name.starts.with.rightbracket@domain.com" );
      assertIsTrue( "local.name.ends.with.rightbracket}@domain.com" );
      assertIsTrue( "local.name.with.rightbracket.before}.point@domain.com" );
      assertIsTrue( "local.name.with.rightbracket.after.}point@domain.com" );
      assertIsTrue( "local.name.with.double.rightbracket}}test@domain.com" );
      assertIsFalse( "(comment }) local.name.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote}\".local.name.with.qoute.with.rightbracket@domain.com" );
      assertIsTrue( "}@rightbracket.domain.com" );
      assertIsTrue( "}}}}}}@rightbracket.domain.com" );
      assertIsTrue( "}.}.}.}.}.}@rightbracket.domain.com" );
      assertIsFalse( "name } <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket@domain.com> name }" );

      assertIsTrue( "~.local.name.starts.with.tilde@domain.com" );
      assertIsTrue( "local.name.ends.with.tilde~@domain.com" );
      assertIsTrue( "local.name.with.tilde.before~.point@domain.com" );
      assertIsTrue( "local.name.with.tilde.after.~point@domain.com" );
      assertIsTrue( "local.name.with.double.tilde~~test@domain.com" );
      assertIsFalse( "(comment ~) local.name.with.comment.with.tilde@domain.com" );
      assertIsTrue( "\"quote~\".local.name.with.qoute.with.tilde@domain.com" );
      assertIsTrue( "~@tilde.domain.com" );
      assertIsTrue( "~~~~~~@tilde.domain.com" );
      assertIsTrue( "~.~.~.~.~.~@tilde.domain.com" );
      assertIsFalse( "name ~ <pointy.brackets1.with.tilde@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.tilde@domain.com> name ~" );

      assertIsTrue( "^.local.name.starts.with.xor@domain.com" );
      assertIsTrue( "local.name.ends.with.xor^@domain.com" );
      assertIsTrue( "local.name.with.xor.before^.point@domain.com" );
      assertIsTrue( "local.name.with.xor.after.^point@domain.com" );
      assertIsTrue( "local.name.with.double.xor^^test@domain.com" );
      assertIsFalse( "(comment ^) local.name.with.comment.with.xor@domain.com" );
      assertIsTrue( "\"quote^\".local.name.with.qoute.with.xor@domain.com" );
      assertIsTrue( "^@xor.domain.com" );
      assertIsTrue( "^^^^^^@xor.domain.com" );
      assertIsTrue( "^.^.^.^.^.^@xor.domain.com" );
      assertIsFalse( "name ^ <pointy.brackets1.with.xor@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.xor@domain.com> name ^" );

      assertIsTrue( "_.local.name.starts.with.underscore@domain.com" );
      assertIsTrue( "local.name.ends.with.underscore_@domain.com" );
      assertIsTrue( "local.name.with.underscore.before_.point@domain.com" );
      assertIsTrue( "local.name.with.underscore.after._point@domain.com" );
      assertIsTrue( "local.name.with.double.underscore__test@domain.com" );
      assertIsFalse( "(comment _) local.name.with.comment.with.underscore@domain.com" );
      assertIsTrue( "\"quote_\".local.name.with.qoute.with.underscore@domain.com" );
      assertIsTrue( "_@underscore.domain.com" );
      assertIsTrue( "______@underscore.domain.com" );
      assertIsTrue( "_._._._._._@underscore.domain.com" );
      assertIsFalse( "name _ <pointy.brackets1.with.underscore@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.underscore@domain.com> name _" );

      assertIsFalse( ":.local.name.starts.with.colon@domain.com" );
      assertIsFalse( "local.name.ends.with.colon:@domain.com" );
      assertIsFalse( "local.name.with.colon.before:.point@domain.com" );
      assertIsFalse( "local.name.with.colon.after.:point@domain.com" );
      assertIsFalse( "local.name.with.double.colon::test@domain.com" );
      assertIsFalse( "(comment :) local.name.with.comment.with.colon@domain.com" );
      assertIsTrue( "\"quote:\".local.name.with.qoute.with.colon@domain.com" );
      assertIsFalse( ":@colon.domain.com" );
      assertIsFalse( "::::::@colon.domain.com" );
      assertIsFalse( ":.:.:.:.:.:@colon.domain.com" );
      assertIsFalse( "name : <pointy.brackets1.with.colon@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.colon@domain.com> name :" );
      
      
      assertIsFalse( "(.local.name.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.leftbracket(@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.before(.point@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.after.(point@domain.com" );
      assertIsFalse( "local.name.with.double.leftbracket((test@domain.com" );
      assertIsFalse( "(comment () local.name.with.comment.with.leftbracket@domain.com" );
      assertIsTrue( "\"quote(\".local.name.with.qoute.with.leftbracket@domain.com" );
      assertIsFalse( "(@leftbracket.domain.com" );
      assertIsFalse( "((((((@leftbracket.domain.com" );
      assertIsFalse( "(.(.(.(.(.(@leftbracket.domain.com" );
      assertIsTrue( "name ( <pointy.brackets1.with.leftbracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.leftbracket@domain.com> name (" );

      assertIsFalse( ").local.name.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.rightbracket)@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.before).point@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.after.)point@domain.com" );
      assertIsFalse( "local.name.with.double.rightbracket))test@domain.com" );
      assertIsFalse( "(comment )) local.name.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote)\".local.name.with.qoute.with.rightbracket@domain.com" );
      assertIsFalse( ")@rightbracket.domain.com" );
      assertIsFalse( "))))))@rightbracket.domain.com" );
      assertIsFalse( ").).).).).)@rightbracket.domain.com" );
      assertIsTrue( "name ) <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.rightbracket@domain.com> name )" );

      assertIsFalse( "[.local.name.starts.with.leftbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.leftbracket[@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.before[.point@domain.com" );
      assertIsFalse( "local.name.with.leftbracket.after.[point@domain.com" );
      assertIsFalse( "local.name.with.double.leftbracket[[test@domain.com" );
      assertIsFalse( "(comment [) local.name.with.comment.with.leftbracket@domain.com" );
      assertIsTrue( "\"quote[\".local.name.with.qoute.with.leftbracket@domain.com" );
      assertIsFalse( "[@leftbracket.domain.com" );
      assertIsFalse( "[[[[[[@leftbracket.domain.com" );
      assertIsFalse( "[.[.[.[.[.[@leftbracket.domain.com" );
      assertIsFalse( "name [ <pointy.brackets1.with.leftbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.leftbracket@domain.com> name [" );

      assertIsFalse( "].local.name.starts.with.rightbracket@domain.com" );
      assertIsFalse( "local.name.ends.with.rightbracket]@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.before].point@domain.com" );
      assertIsFalse( "local.name.with.rightbracket.after.]point@domain.com" );
      assertIsFalse( "local.name.with.double.rightbracket]]test@domain.com" );
      assertIsFalse( "(comment ]) local.name.with.comment.with.rightbracket@domain.com" );
      assertIsTrue( "\"quote]\".local.name.with.qoute.with.rightbracket@domain.com" );
      assertIsFalse( "]@rightbracket.domain.com" );
      assertIsFalse( "]]]]]]@rightbracket.domain.com" );
      assertIsFalse( "].].].].].]@rightbracket.domain.com" );
      assertIsFalse( "name ] <pointy.brackets1.with.rightbracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.rightbracket@domain.com> name ]" );

      assertIsFalse( " .local.name.starts.with.space@domain.com" );
      assertIsFalse( "local.name.ends.with.space @domain.com" );
      assertIsFalse( "local.name.with.space.before .point@domain.com" );
      assertIsFalse( "local.name.with.space.after. point@domain.com" );
      assertIsFalse( "local.name.with.double.space  test@domain.com" );
      assertIsFalse( "(comment  ) local.name.with.comment.with.space@domain.com" );
      assertIsTrue( "\"quote \".local.name.with.qoute.with.space@domain.com" );
      assertIsFalse( " @space.domain.com" );
      assertIsFalse( "      @space.domain.com" );
      assertIsFalse( " . . . . . @space.domain.com" );
      assertIsTrue( "name   <pointy.brackets1.with.space@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.space@domain.com> name  " );

      assertIsFalse( "..local.name.starts.with.dot@domain.com" );
      assertIsFalse( "local.name.ends.with.dot.@domain.com" );
      assertIsFalse( "local.name.with.dot.before..point@domain.com" );
      assertIsFalse( "local.name.with.dot.after..point@domain.com" );
      assertIsFalse( "local.name.with.double.dot..test@domain.com" );
      assertIsFalse( "(comment .) local.name.with.comment.with.dot@domain.com" );
      assertIsTrue( "\"quote.\".local.name.with.qoute.with.dot@domain.com" );
      assertIsFalse( ".@dot.domain.com" );
      assertIsFalse( "......@dot.domain.com" );
      assertIsFalse( "...........@dot.domain.com" );
      assertIsFalse( "name . <pointy.brackets1.with.dot@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.dot@domain.com> name ." );


      assertIsFalse( "().local.name.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.name.ends.with.empty.bracket()@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.before().point@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.after.()point@domain.com" );
      assertIsFalse( "local.name.with.double.empty.bracket()()test@domain.com" );
      assertIsFalse( "(comment ()) local.name.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote()\".local.name.with.qoute.with.empty.bracket@domain.com" );
      assertIsFalse( "()@empty.bracket.domain.com" );
      assertIsFalse( "()()()()()()@empty.bracket.domain.com" );
      assertIsFalse( "().().().().().()@empty.bracket.domain.com" );
      assertIsTrue( "name () <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.empty.bracket@domain.com> name ()" );

      assertIsTrue( "{}.local.name.starts.with.empty.bracket@domain.com" );
      assertIsTrue( "local.name.ends.with.empty.bracket{}@domain.com" );
      assertIsTrue( "local.name.with.empty.bracket.before{}.point@domain.com" );
      assertIsTrue( "local.name.with.empty.bracket.after.{}point@domain.com" );
      assertIsTrue( "local.name.with.double.empty.bracket{}{}test@domain.com" );
      assertIsFalse( "(comment {}) local.name.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote{}\".local.name.with.qoute.with.empty.bracket@domain.com" );
      assertIsTrue( "{}@empty.bracket.domain.com" );
      assertIsTrue( "{}{}{}{}{}{}@empty.bracket.domain.com" );
      assertIsTrue( "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com" );
      assertIsFalse( "name {} <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket@domain.com> name {}" );

      assertIsFalse( "[].local.name.starts.with.empty.bracket@domain.com" );
      assertIsFalse( "local.name.ends.with.empty.bracket[]@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.before[].point@domain.com" );
      assertIsFalse( "local.name.with.empty.bracket.after.[]point@domain.com" );
      assertIsFalse( "local.name.with.double.empty.bracket[][]test@domain.com" );
      assertIsFalse( "(comment []) local.name.with.comment.with.empty.bracket@domain.com" );
      assertIsTrue( "\"quote[]\".local.name.with.qoute.with.empty.bracket@domain.com" );
      assertIsFalse( "[]@empty.bracket.domain.com" );
      assertIsFalse( "[][][][][][]@empty.bracket.domain.com" );
      assertIsFalse( "[].[].[].[].[].[]@empty.bracket.domain.com" );
      assertIsFalse( "name [] <pointy.brackets1.with.empty.bracket@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.empty.bracket@domain.com> name []" );

      assertIsTrue( "domain.part@with0number0.com" );
      assertIsTrue( "domain.part@0with.number0.at.domain.start.com" );
      assertIsTrue( "domain.part@with.number0.at.domain.end10.com" );
      assertIsTrue( "domain.part@with.number0.at.domain.end2.com0" );
      assertIsTrue( "domain.part@with.number0.before0.point.com" );
      assertIsTrue( "domain.part@with.number0.after.0point.com" );

      assertIsTrue( "domain.part@with9number9.com" );
      assertIsTrue( "domain.part@9with.number9.at.domain.start.com" );
      assertIsTrue( "domain.part@with.number9.at.domain.end19.com" );
      assertIsTrue( "domain.part@with.number9.at.domain.end2.com9" );
      assertIsTrue( "domain.part@with.number9.before9.point.com" );
      assertIsTrue( "domain.part@with.number9.after.9point.com" );

      assertIsTrue( "domain.part@with0123456789numbers.com" );
      assertIsTrue( "domain.part@0123456789with.numbers.at.domain.start.com" );
      assertIsTrue( "domain.part@with.numbers.at.domain.end10123456789.com" );
      assertIsTrue( "domain.part@with.numbers.at.domain.end2.com0123456789" );
      assertIsTrue( "domain.part@with.numbers.before0123456789.point.com" );
      assertIsTrue( "domain.part@with.numbers.after.0123456789point.com" );

      assertIsTrue( "domain.part@with-hyphen.com" );
      assertIsFalse( "domain.part@-with.hyphen.at.domain.start.com" );
      assertIsFalse( "domain.part@with.hyphen.at.domain.end1-.com" );
      assertIsFalse( "domain.part@with.hyphen.at.domain.end2.com-" );
      assertIsFalse( "domain.part@with.hyphen.before-.point.com" );
      assertIsFalse( "domain.part@with.-hyphen.after.point.com" );

      assertIsTrue( "domain.part@with_underscore.com" );
      assertIsFalse( "domain.part@_with.underscore.at.domain.start.com" );
      assertIsFalse( "domain.part@with.underscore.at.domain.end1_.com" );
      assertIsFalse( "domain.part@with.underscore.at.domain.end2.com_" );
      assertIsFalse( "domain.part@with.underscore.before_.point.com" );
      assertIsFalse( "domain.part@with.underscore.after._point.com" );

      assertIsFalse( "domain.part@with&amp.com" );
      assertIsFalse( "domain.part@&with.amp.at.domain.start.com" );
      assertIsFalse( "domain.part@with.amp.at.domain.end1&.com" );
      assertIsFalse( "domain.part@with.amp.at.domain.end2.com&" );
      assertIsFalse( "domain.part@with.amp.before&.point.com" );
      assertIsFalse( "domain.part@with.amp.after.&point.com" );

      assertIsFalse( "domain.part@with*asterisk.com" );
      assertIsFalse( "domain.part@*with.asterisk.at.domain.start.com" );
      assertIsFalse( "domain.part@with.asterisk.at.domain.end1*.com" );
      assertIsFalse( "domain.part@with.asterisk.at.domain.end2.com*" );
      assertIsFalse( "domain.part@with.asterisk.before*.point.com" );
      assertIsFalse( "domain.part@with.asterisk.after.*point.com" );

      assertIsFalse( "domain.part@with$dollar.com" );
      assertIsFalse( "domain.part@$with.dollar.at.domain.start.com" );
      assertIsFalse( "domain.part@with.dollar.at.domain.end1$.com" );
      assertIsFalse( "domain.part@with.dollar.at.domain.end2.com$" );
      assertIsFalse( "domain.part@with.dollar.before$.point.com" );
      assertIsFalse( "domain.part@with.dollar.after.$point.com" );

      assertIsFalse( "domain.part@with=equality.com" );
      assertIsFalse( "domain.part@=with.equality.at.domain.start.com" );
      assertIsFalse( "domain.part@with.equality.at.domain.end1=.com" );
      assertIsFalse( "domain.part@with.equality.at.domain.end2.com=" );
      assertIsFalse( "domain.part@with.equality.before=.point.com" );
      assertIsFalse( "domain.part@with.equality.after.=point.com" );

      assertIsFalse( "domain.part@with!exclamation.com" );
      assertIsFalse( "domain.part@!with.exclamation.at.domain.start.com" );
      assertIsFalse( "domain.part@with.exclamation.at.domain.end1!.com" );
      assertIsFalse( "domain.part@with.exclamation.at.domain.end2.com!" );
      assertIsFalse( "domain.part@with.exclamation.before!.point.com" );
      assertIsFalse( "domain.part@with.exclamation.after.!point.com" );

      assertIsFalse( "domain.part@with?question.com" );
      assertIsFalse( "domain.part@?with.question.at.domain.start.com" );
      assertIsFalse( "domain.part@with.question.at.domain.end1?.com" );
      assertIsFalse( "domain.part@with.question.at.domain.end2.com?" );
      assertIsFalse( "domain.part@with.question.before?.point.com" );
      assertIsFalse( "domain.part@with.question.after.?point.com" );

      assertIsFalse( "domain.part@with`grave-accent.com" );
      assertIsFalse( "domain.part@`with.grave-accent.at.domain.start.com" );
      assertIsFalse( "domain.part@with.grave-accent.at.domain.end1`.com" );
      assertIsFalse( "domain.part@with.grave-accent.at.domain.end2.com`" );
      assertIsFalse( "domain.part@with.grave-accent.before`.point.com" );
      assertIsFalse( "domain.part@with.grave-accent.after.`point.com" );

      assertIsFalse( "domain.part@with#hash.com" );
      assertIsFalse( "domain.part@#with.hash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.hash.at.domain.end1#.com" );
      assertIsFalse( "domain.part@with.hash.at.domain.end2.com#" );
      assertIsFalse( "domain.part@with.hash.before#.point.com" );
      assertIsFalse( "domain.part@with.hash.after.#point.com" );

      assertIsFalse( "domain.part@with%percentage.com" );
      assertIsFalse( "domain.part@%with.percentage.at.domain.start.com" );
      assertIsFalse( "domain.part@with.percentage.at.domain.end1%.com" );
      assertIsFalse( "domain.part@with.percentage.at.domain.end2.com%" );
      assertIsFalse( "domain.part@with.percentage.before%.point.com" );
      assertIsFalse( "domain.part@with.percentage.after.%point.com" );

      assertIsFalse( "domain.part@with|pipe.com" );
      assertIsFalse( "domain.part@|with.pipe.at.domain.start.com" );
      assertIsFalse( "domain.part@with.pipe.at.domain.end1|.com" );
      assertIsFalse( "domain.part@with.pipe.at.domain.end2.com|" );
      assertIsFalse( "domain.part@with.pipe.before|.point.com" );
      assertIsFalse( "domain.part@with.pipe.after.|point.com" );

      assertIsFalse( "domain.part@with+plus.com" );
      assertIsFalse( "domain.part@+with.plus.at.domain.start.com" );
      assertIsFalse( "domain.part@with.plus.at.domain.end1+.com" );
      assertIsFalse( "domain.part@with.plus.at.domain.end2.com+" );
      assertIsFalse( "domain.part@with.plus.before+.point.com" );
      assertIsFalse( "domain.part@with.plus.after.+point.com" );

      assertIsFalse( "domain.part@with{leftbracket.com" );
      assertIsFalse( "domain.part@{with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1{.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com{" );
      assertIsFalse( "domain.part@with.leftbracket.before{.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.{point.com" );

      assertIsFalse( "domain.part@with}rightbracket.com" );
      assertIsFalse( "domain.part@}with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1}.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com}" );
      assertIsFalse( "domain.part@with.rightbracket.before}.point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.}point.com" );

      assertIsFalse( "domain.part@with(leftbracket.com" );
      assertIsFalse( "domain.part@(with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1(.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com(" );
      assertIsFalse( "domain.part@with.leftbracket.before(.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.(point.com" );

      assertIsFalse( "domain.part@with)rightbracket.com" );
      assertIsFalse( "domain.part@)with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1).com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com)" );
      assertIsFalse( "domain.part@with.rightbracket.before).point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.)point.com" );

      assertIsFalse( "domain.part@with[leftbracket.com" );
      assertIsFalse( "domain.part@[with.leftbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end1[.com" );
      assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com[" );
      assertIsFalse( "domain.part@with.leftbracket.before[.point.com" );
      assertIsFalse( "domain.part@with.leftbracket.after.[point.com" );

      assertIsFalse( "domain.part@with]rightbracket.com" );
      assertIsFalse( "domain.part@]with.rightbracket.at.domain.start.com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end1].com" );
      assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com]" );
      assertIsFalse( "domain.part@with.rightbracket.before].point.com" );
      assertIsFalse( "domain.part@with.rightbracket.after.]point.com" );

      assertIsFalse( "domain.part@with~tilde.com" );
      assertIsFalse( "domain.part@~with.tilde.at.domain.start.com" );
      assertIsFalse( "domain.part@with.tilde.at.domain.end1~.com" );
      assertIsFalse( "domain.part@with.tilde.at.domain.end2.com~" );
      assertIsFalse( "domain.part@with.tilde.before~.point.com" );
      assertIsFalse( "domain.part@with.tilde.after.~point.com" );

      assertIsFalse( "domain.part@with^xor.com" );
      assertIsFalse( "domain.part@^with.xor.at.domain.start.com" );
      assertIsFalse( "domain.part@with.xor.at.domain.end1^.com" );
      assertIsFalse( "domain.part@with.xor.at.domain.end2.com^" );
      assertIsFalse( "domain.part@with.xor.before^.point.com" );
      assertIsFalse( "domain.part@with.xor.after.^point.com" );

      assertIsFalse( "domain.part@with:colon.com" );
      assertIsFalse( "domain.part@:with.colon.at.domain.start.com" );
      assertIsFalse( "domain.part@with.colon.at.domain.end1:.com" );
      assertIsFalse( "domain.part@with.colon.at.domain.end2.com:" );
      assertIsFalse( "domain.part@with.colon.before:.point.com" );
      assertIsFalse( "domain.part@with.colon.after.:point.com" );

      assertIsFalse( "domain.part@with space.com" );
      assertIsFalse( "domain.part@ with.space.at.domain.start.com" );
      assertIsFalse( "domain.part@with.space.at.domain.end1 .com" );
      assertIsFalse( "domain.part@with.space.at.domain.end2.com " );
      assertIsFalse( "domain.part@with.space.before .point.com" );
      assertIsFalse( "domain.part@with.space.after. point.com" );
      
      assertIsFalse( ",.local.name.starts.with.comma@domain.com" );
      assertIsFalse( "local.name.ends.with.comma,@domain.com" );
      assertIsFalse( "local.name.with.comma.before,.point@domain.com" );
      assertIsFalse( "local.name.with.comma.after.,point@domain.com" );
      assertIsFalse( "local.name.with.double.comma,,test@domain.com" );
      assertIsFalse( "(comment ,) local.name.with.comment.with.comma@domain.com" );
      assertIsTrue( "\"quote,\".local.name.with.qoute.with.comma@domain.com" );
      assertIsFalse( ",@comma.domain.com" );
      assertIsFalse( ",,,,,,@comma.domain.com" );
      assertIsFalse( ",.,.,.,.,.,@comma.domain.com" );
      assertIsFalse( "name , <pointy.brackets1.with.comma@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.comma@domain.com> name ," );

      assertIsFalse( "domain.part@with,comma.com" );
      assertIsFalse( "domain.part@,with.comma.at.domain.start.com" );
      assertIsFalse( "domain.part@with.comma.at.domain.end1,.com" );
      assertIsFalse( "domain.part@with.comma.at.domain.end2.com," );
      assertIsFalse( "domain.part@with.comma.before,.point.com" );
      assertIsFalse( "domain.part@with.comma.after.,point.com" );

      assertIsFalse( "§.local.name.starts.with.paragraph@domain.com" );
      assertIsFalse( "local.name.ends.with.paragraph§@domain.com" );
      assertIsFalse( "local.name.with.paragraph.before§.point@domain.com" );
      assertIsFalse( "local.name.with.paragraph.after.§point@domain.com" );
      assertIsFalse( "local.name.with.double.paragraph§§test@domain.com" );
      assertIsFalse( "(comment §) local.name.with.comment.with.paragraph@domain.com" );
      assertIsFalse( "\"quote§\".local.name.with.qoute.with.paragraph@domain.com" );
      assertIsFalse( "§@paragraph.domain.com" );
      assertIsFalse( "§§§§§§@paragraph.domain.com" );
      assertIsFalse( "§.§.§.§.§.§@paragraph.domain.com" );
      assertIsFalse( "name § <pointy.brackets1.with.paragraph@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.paragraph@domain.com> name §" );

      assertIsFalse( "domain.part@with§paragraph.com" );
      assertIsFalse( "domain.part@§with.paragraph.at.domain.start.com" );
      assertIsFalse( "domain.part@with.paragraph.at.domain.end1§.com" );
      assertIsFalse( "domain.part@with.paragraph.at.domain.end2.com§" );
      assertIsFalse( "domain.part@with.paragraph.before§.point.com" );
      assertIsFalse( "domain.part@with.paragraph.after.§point.com" );

      assertIsTrue( "'.local.name.starts.with.quote@domain.com" );
      assertIsTrue( "local.name.ends.with.quote'@domain.com" );
      assertIsTrue( "local.name.with.quote.before'.point@domain.com" );
      assertIsTrue( "local.name.with.quote.after.'point@domain.com" );
      assertIsTrue( "local.name.with.double.quote''test@domain.com" );
      assertIsFalse( "(comment ') local.name.with.comment.with.quote@domain.com" );
      assertIsTrue( "\"quote'\".local.name.with.qoute.with.quote@domain.com" );
      assertIsTrue( "'@quote.domain.com" );
      assertIsTrue( "''''''@quote.domain.com" );
      assertIsTrue( "'.'.'.'.'.'@quote.domain.com" );
      assertIsFalse( "name ' <pointy.brackets1.with.quote@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.quote@domain.com> name '" );

      assertIsFalse( "domain.part@with'quote.com" );
      assertIsFalse( "domain.part@'with.quote.at.domain.start.com" );
      assertIsFalse( "domain.part@with.quote.at.domain.end1'.com" );
      assertIsFalse( "domain.part@with.quote.at.domain.end2.com'" );
      assertIsFalse( "domain.part@with.quote.before'.point.com" );
      assertIsFalse( "domain.part@with.quote.after.'point.com" );

      assertIsFalse( "\".local.name.starts.with.double.quote@domain.com" );
      assertIsFalse( "local.name.ends.with.double.quote\"@domain.com" );
      assertIsFalse( "local.name.with.double.quote.before\".point@domain.com" );
      assertIsFalse( "local.name.with.double.quote.after.\"point@domain.com" );
      assertIsFalse( "local.name.with.double.double.quote\"\"test@domain.com" );
      assertIsFalse( "(comment \") local.name.with.comment.with.double.quote@domain.com" );
      assertIsFalse( "\"quote\"\".local.name.with.qoute.with.double.quote@domain.com" );
      assertIsFalse( "\"@double.quote.domain.com" );
      assertIsTrue( "\".\".\".\".\".\"@double.quote.domain.com" );
      assertIsTrue( "name \" <pointy.brackets1.with.double.quote@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.double.quote@domain.com> name \"" );

      assertIsFalse( "domain.part@with\"double.quote.com" );
      assertIsFalse( "domain.part@\"with.double.quote.at.domain.start.com" );
      assertIsFalse( "domain.part@with.double.quote.at.domain.end1\".com" );
      assertIsFalse( "domain.part@with.double.quote.at.domain.end2.com\"" );
      assertIsFalse( "domain.part@with.double.quote.before\".point.com" );
      assertIsFalse( "domain.part@with.double.quote.after.\"point.com" );

      assertIsFalse( ")(.local.name.starts.with.false.bracket1@domain.com" );
      assertIsFalse( "local.name.ends.with.false.bracket1)(@domain.com" );
      assertIsFalse( "local.name.with.false.bracket1.before)(.point@domain.com" );
      assertIsFalse( "local.name.with.false.bracket1.after.)(point@domain.com" );
      assertIsFalse( "local.name.with.double.false.bracket1)()(test@domain.com" );
      assertIsFalse( "(comment )() local.name.with.comment.with.false.bracket1@domain.com" );
      assertIsTrue( "\"quote)(\".local.name.with.qoute.with.false.bracket1@domain.com" );
      assertIsFalse( ")(@false.bracket1.domain.com" );
      assertIsFalse( ")()()()()()(@false.bracket1.domain.com" );
      assertIsFalse( ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com" );
      assertIsTrue( "name )( <pointy.brackets1.with.false.bracket1@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.false.bracket1@domain.com> name )(" );

      assertIsFalse( "domain.part@with)(false.bracket1.com" );
      assertIsFalse( "domain.part@)(with.false.bracket1.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket1.at.domain.end1)(.com" );
      assertIsFalse( "domain.part@with.false.bracket1.at.domain.end2.com)(" );
      assertIsFalse( "domain.part@with.false.bracket1.before)(.point.com" );
      assertIsFalse( "domain.part@with.false.bracket1.after.)(point.com" );

      assertIsTrue( "}{.local.name.starts.with.false.bracket2@domain.com" );
      assertIsTrue( "local.name.ends.with.false.bracket2}{@domain.com" );
      assertIsTrue( "local.name.with.false.bracket2.before}{.point@domain.com" );
      assertIsTrue( "local.name.with.false.bracket2.after.}{point@domain.com" );
      assertIsTrue( "local.name.with.double.false.bracket2}{}{test@domain.com" );
      assertIsFalse( "(comment }{) local.name.with.comment.with.false.bracket2@domain.com" );
      assertIsTrue( "\"quote}{\".local.name.with.qoute.with.false.bracket2@domain.com" );
      assertIsTrue( "}{@false.bracket2.domain.com" );
      assertIsTrue( "}{}{}{}{}{}{@false.bracket2.domain.com" );
      assertIsTrue( "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com" );
      assertIsFalse( "name }{ <pointy.brackets1.with.false.bracket2@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket2@domain.com> name }{" );

      assertIsFalse( "domain.part@with}{false.bracket2.com" );
      assertIsFalse( "domain.part@}{with.false.bracket2.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket2.at.domain.end1}{.com" );
      assertIsFalse( "domain.part@with.false.bracket2.at.domain.end2.com}{" );
      assertIsFalse( "domain.part@with.false.bracket2.before}{.point.com" );
      assertIsFalse( "domain.part@with.false.bracket2.after.}{point.com" );

      assertIsFalse( "][.local.name.starts.with.false.bracket3@domain.com" );
      assertIsFalse( "local.name.ends.with.false.bracket3][@domain.com" );
      assertIsFalse( "local.name.with.false.bracket3.before][.point@domain.com" );
      assertIsFalse( "local.name.with.false.bracket3.after.][point@domain.com" );
      assertIsFalse( "local.name.with.double.false.bracket3][][test@domain.com" );
      assertIsFalse( "(comment ][) local.name.with.comment.with.false.bracket3@domain.com" );
      assertIsTrue( "\"quote][\".local.name.with.qoute.with.false.bracket3@domain.com" );
      assertIsFalse( "][@false.bracket3.domain.com" );
      assertIsFalse( "][][][][][][@false.bracket3.domain.com" );
      assertIsFalse( "][.][.][.][.][.][@false.bracket3.domain.com" );
      assertIsFalse( "name ][ <pointy.brackets1.with.false.bracket3@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket3@domain.com> name ][" );

      assertIsFalse( "domain.part@with][false.bracket3.com" );
      assertIsFalse( "domain.part@][with.false.bracket3.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket3.at.domain.end1][.com" );
      assertIsFalse( "domain.part@with.false.bracket3.at.domain.end2.com][" );
      assertIsFalse( "domain.part@with.false.bracket3.before][.point.com" );
      assertIsFalse( "domain.part@with.false.bracket3.after.][point.com" );

      assertIsFalse( "><.local.name.starts.with.false.bracket4@domain.com" );
      assertIsFalse( "local.name.ends.with.false.bracket4><@domain.com" );
      assertIsFalse( "local.name.with.false.bracket4.before><.point@domain.com" );
      assertIsFalse( "local.name.with.false.bracket4.after.><point@domain.com" );
      assertIsFalse( "local.name.with.double.false.bracket4><><test@domain.com" );
      assertIsFalse( "(comment ><) local.name.with.comment.with.false.bracket4@domain.com" );
      assertIsTrue( "\"quote><\".local.name.with.qoute.with.false.bracket4@domain.com" );
      assertIsFalse( "><@false.bracket4.domain.com" );
      assertIsFalse( "><><><><><><@false.bracket4.domain.com" );
      assertIsFalse( "><.><.><.><.><.><@false.bracket4.domain.com" );
      assertIsFalse( "name >< <pointy.brackets1.with.false.bracket4@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.false.bracket4@domain.com> name ><" );

      assertIsFalse( "domain.part@with><false.bracket4.com" );
      assertIsFalse( "domain.part@><with.false.bracket4.at.domain.start.com" );
      assertIsFalse( "domain.part@with.false.bracket4.at.domain.end1><.com" );
      assertIsFalse( "domain.part@with.false.bracket4.at.domain.end2.com><" );
      assertIsFalse( "domain.part@with.false.bracket4.before><.point.com" );
      assertIsFalse( "domain.part@with.false.bracket4.after.><point.com" );

      assertIsFalse( "DomainHyphen@-atstart" );
      assertIsFalse( "DomainHyphen@atend-.com" );
      assertIsFalse( "DomainHyphen@bb.-cc" );
      assertIsFalse( "DomainHyphen@bb.-cc-" );
      assertIsFalse( "DomainHyphen@bb.cc-" );
      assertIsFalse( "DomainHyphen@bb.c-c" ); // https://tools.ietf.org/id/draft-liman-tld-names-01.html
      assertIsFalse( "DomainNotAllowedCharacter@/atstart" );
      assertIsTrue( "DomainNotAllowedCharacter@a.start" );
      assertIsFalse( "DomainNotAllowedCharacter@atst\\art.com" );
      assertIsFalse( "DomainNotAllowedCharacter@exa\\mple" );
      assertIsFalse( "DomainNotAllowedCharacter@example'" );
      assertIsFalse( "DomainNotAllowedCharacter@100%.de'" );
      assertIsTrue( "domain.starts.with.digit@2domain.com" );
      assertIsTrue( "domain.ends.with.digit@domain2.com" );
      assertIsFalse( "tld.starts.with.digit@domain.2com" );
      assertIsTrue( "tld.ends.with.digit@domain.com2" );

      assertIsFalse( "email@=qowaiv.com" );
      assertIsFalse( "email@plus+.com" );
      assertIsFalse( "email@domain.com>" );
      assertIsFalse( "email@mailto:domain.com" );
      assertIsFalse( "mailto:mailto:email@domain.com" );
      assertIsFalse( "email@-domain.com" );
      assertIsFalse( "email@domain-.com" );
      assertIsFalse( "email@domain.com-" );
      assertIsFalse( "email@{leftbracket.com" );
      assertIsFalse( "email@rightbracket}.com" );
      assertIsFalse( "email@pp|e.com" );

      assertIsTrue( "email@domain.domain.domain.com.com" );
      assertIsTrue( "email@domain.domain.domain.domain.com.com" );
      assertIsTrue( "email@domain.domain.domain.domain.domain.com.com" );

      assertIsFalse( "unescaped white space@fake$com" );
      assertIsFalse( "\"Joe Smith email@domain.com" );
      assertIsFalse( "\"Joe Smith' email@domain.com" );
      assertIsFalse( "\"Joe Smith\"email@domain.com" );
      assertIsFalse( "Joe Smith &lt;email@domain.com&gt;" );
      assertIsTrue( "{john'doe}@my.server" );
      assertIsTrue( "email@domain-one.com" );
      assertIsTrue( "_______@domain.com" );
      assertIsTrue( "?????@domain.com" );
      assertIsFalse( "local@?????.com" );
      assertIsTrue( "\"B3V3RLY H1LL$\"@example.com" );
      assertIsTrue( "\"-- --- .. -.\"@sh.de" );
      assertIsTrue( "{{-^-}{-=-}{-^-}}@GHI.JKL" );
      assertIsTrue( "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de" );
      assertIsTrue( "#!$%&'*+-/=?^_`{}|~@eksample.org" );
      assertIsFalse( "eksample@#!$%&'*+-/=?^_`{}|~.org" );
      assertIsFalse( "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2.4}" );
      assertIsFalse( "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com" );

      wlHeadline( "IP V4" );

      assertIsFalse( "\"\"@[]" );
      assertIsFalse( "\"\"@[1" );
      assertIsFalse( "ABC.DEF@[]" );
      assertIsTrue( "\"    \"@[1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[001.002.003.004]" );
      assertIsTrue( "\"ABC.DEF\"@[127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsFalse( "ABC.DE[F@1.2.3.4]" );
      assertIsFalse( "ABC.DEF@{1.2.3.4}" );
      assertIsFalse( "ABC.DEF[1.2.3.4]" );
      assertIsFalse( "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4][5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[][][][]" );
      assertIsFalse( "ABC.DEF@[....]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF[@1.2.3.4]" );
      assertIsTrue( "\"[1.2.3.4]\"@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF@MyDomain[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.00002.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.456]" );
      assertIsFalse( "ABC.DEF@[..]" );
      assertIsFalse( "ABC.DEF@[.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1]" );
      assertIsFalse( "ABC.DEF@[1.2]" );
      assertIsFalse( "ABC.DEF@[1.2.3]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5.6]" );
      assertIsFalse( "ABC.DEF@[MyDomain.de]" );
      assertIsFalse( "ABC.DEF@[1.2.3.]" );
      assertIsFalse( "ABC.DEF@[1.2.3. ]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4].de" );
      assertIsFalse( "ABC.DE@[1.2.3.4][5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4" );
      assertIsFalse( "ABC.DEF@1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.Z]" );
      assertIsFalse( "ABC.DEF@[12.34]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4]ABC" );
      assertIsFalse( "ABC.DEF@[1234.5.6.7]" );
      assertIsFalse( "ABC.DEF@[1.2...3.4]" );

      assertIsFalse( "ip.v4.with.underscore@[123.14_5.178.90]" );
      assertIsFalse( "ip.v4.with.underscore@[123.145_.178.90]" );
      assertIsFalse( "ip.v4.with.underscore@[123.145._178.90]" );
      assertIsFalse( "ip.v4.with.underscore@[123.145.178.90_]" );
      assertIsFalse( "ip.v4.with.underscore@[_123.145.178.90]" );

      assertIsFalse( "ip.v4.with.amp@[123.14&5.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145&.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.&178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.178.90&]" );
      assertIsFalse( "ip.v4.with.amp@[&123.145.178.90]" );

      assertIsFalse( "ip.v4.with.asterisk@[123.14*5.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145*.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.*178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.178.90*]" );
      assertIsFalse( "ip.v4.with.asterisk@[*123.145.178.90]" );

      assertIsFalse( "ip.v4.with.dollar@[123.14$5.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145$.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.$178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.178.90$]" );
      assertIsFalse( "ip.v4.with.dollar@[$123.145.178.90]" );

      assertIsFalse( "ip.v4.with.equality@[123.14=5.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145=.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.=178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.178.90=]" );
      assertIsFalse( "ip.v4.with.equality@[=123.145.178.90]" );

      assertIsFalse( "ip.v4.with.exclamation@[123.14!5.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145!.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.!178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.178.90!]" );
      assertIsFalse( "ip.v4.with.exclamation@[!123.145.178.90]" );

      assertIsFalse( "ip.v4.with.question@[123.14?5.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145?.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.?178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.178.90?]" );
      assertIsFalse( "ip.v4.with.question@[?123.145.178.90]" );

      assertIsFalse( "ip.v4.with.grave-accent@[123.14`5.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145`.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.`178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.178.90`]" );
      assertIsFalse( "ip.v4.with.grave-accent@[`123.145.178.90]" );

      assertIsFalse( "ip.v4.with.hash@[123.14#5.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145#.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.#178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.178.90#]" );
      assertIsFalse( "ip.v4.with.hash@[#123.145.178.90]" );

      assertIsFalse( "ip.v4.with.percentage@[123.14%5.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145%.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.%178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.178.90%]" );
      assertIsFalse( "ip.v4.with.percentage@[%123.145.178.90]" );

      assertIsFalse( "ip.v4.with.pipe@[123.14|5.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145|.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.|178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.178.90|]" );
      assertIsFalse( "ip.v4.with.pipe@[|123.145.178.90]" );

      assertIsFalse( "ip.v4.with.plus@[123.14+5.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145+.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.+178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.178.90+]" );
      assertIsFalse( "ip.v4.with.plus@[+123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14{5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145{.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.{178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90{]" );
      assertIsFalse( "ip.v4.with.leftbracket@[{123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14}5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145}.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.}178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90}]" );
      assertIsFalse( "ip.v4.with.rightbracket@[}123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14(5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145(.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.(178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90(]" );
      assertIsFalse( "ip.v4.with.leftbracket@[(123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14)5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145).178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.)178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90)]" );
      assertIsFalse( "ip.v4.with.rightbracket@[)123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14[5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145[.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.[178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90[]" );
      assertIsFalse( "ip.v4.with.leftbracket@[[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14]5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145].178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.]178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90]]" );
      assertIsFalse( "ip.v4.with.rightbracket@[]123.145.178.90]" );

      assertIsFalse( "ip.v4.with.tilde@[123.14~5.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145~.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.~178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.178.90~]" );
      assertIsFalse( "ip.v4.with.tilde@[~123.145.178.90]" );

      assertIsFalse( "ip.v4.with.xor@[123.14^5.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145^.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.^178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.178.90^]" );
      assertIsFalse( "ip.v4.with.xor@[^123.145.178.90]" );

      assertIsFalse( "ip.v4.with.colon@[123.14:5.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145:.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.:178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.178.90:]" );
      assertIsFalse( "ip.v4.with.colon@[:123.145.178.90]" );

      assertIsFalse( "ip.v4.with.space@[123.14 5.178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145 .178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145. 178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145.178.90 ]" );
      assertIsFalse( "ip.v4.with.space@[ 123.145.178.90]" );

      assertIsTrue( "email@[123.123.123.123]" );
      assertIsFalse( "email@111.222.333" );
      assertIsFalse( "email@111.222.333.256" );
      assertIsFalse( "email@[123.123.123.123" );
      assertIsFalse( "email@[123.123.123].123" );
      assertIsFalse( "email@123.123.123.123]" );
      assertIsFalse( "email@123.123.[123.123]" );

      assertIsFalse( "ab@988.120.150.10" );
      assertIsFalse( "ab@120.256.256.120" );
      assertIsFalse( "ab@120.25.1111.120" );
      assertIsFalse( "ab@[188.120.150.10" );
      assertIsFalse( "ab@188.120.150.10]" );
      assertIsFalse( "ab@[188.120.150.10].com" );
      assertIsTrue( "ab@188.120.150.10" );
      assertIsTrue( "ab@1.0.0.10" );
      assertIsTrue( "ab@120.25.254.120" );
      assertIsTrue( "ab@01.120.150.1" );
      assertIsTrue( "ab@88.120.150.021" );
      assertIsTrue( "ab@88.120.150.01" );
      assertIsTrue( "email@123.123.123.123" );

      wlHeadline( "IP V6" );

      assertIsTrue( "ABC.DEF@[IPv6:2001:db8::1]" );
      assertIsFalse( "ABC.DEF@[IP" );
      assertIsFalse( "ABC.DEF@[IPv6]" );
      assertIsFalse( "ABC.DEF@[IPv6:]" );
      assertIsFalse( "ABC.DEF@[IPv6::::::]" );
      assertIsFalse( "ABC.DEF@[IPv6:1]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5::]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6:7" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv4:1:2:3:4]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4::]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:::]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2::4:5::]" );
      assertIsFalse( "ABC.DEF@[I127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[D127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[iPv6:2001:db8::1]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3::5::7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:Z]" );
      assertIsFalse( "ABC.DEF@[IPv6:12:34]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC" );
      assertIsTrue( "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6" );
      assertIsFalse( "ABC.DEF@[IPv6:12345:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:::6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])" );

      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]" );

      assertIsFalse( "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]" );

      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]" );

      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]" );

      assertIsFalse( "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]" );

      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]" );

      assertIsFalse( "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]" );

      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]" );

      assertIsFalse( "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]" );

      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]" );

      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]" );

      assertIsFalse( "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );

      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]" );

      assertIsFalse( "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]" );

      assertIsFalse( "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]" );

      assertIsTrue( "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334" );

      /*
       * https://formvalidation.io/guide/validators/ip/
       */

      assertIsTrue( "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]" );
      assertIsTrue( "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]" );
      assertIsTrue( "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]" );
      assertIsTrue( "ABC.DEF@[IPv6:fe00::1]" );
      assertIsFalse( "ABC.DEF@[IPv6:10.168.0001.100]" );
      assertIsFalse( "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]" );
      assertIsFalse( "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]" );
      assertIsFalse( "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]" );

      wlHeadline( "IP V4 embedded in IP V6" );

      assertIsTrue( "ABC.DEF@[IPv6::FFFF:127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[IPv6::ffff:127.0.0.1]" );

      assertIsTrue( "ABC.DEF@[::FFFF:127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[::ffff:127.0.0.1]" );

      assertIsFalse( "ABC.DEF@[IPv6::ffff:.127.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::fff:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::1234:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6:::127.0.0.1]" );

      wlHeadline( "Strings" );

      assertIsTrue( "\"ABC.DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC@DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\\"DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\@DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\'DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC\\\\DEF\"@GHI.DE" );
      assertIsFalse( "\"ABC DEF@G\"HI.DE" );
      assertIsFalse( "\"\"@GHI.DE" );
      assertIsFalse( "\"ABC.DEF@G\"HI.DE" );
      assertIsFalse( "A@G\"HI.DE" );
      assertIsFalse( "\"@GHI.DE" );
      assertIsFalse( "ABC.DEF.\"" );
      assertIsFalse( "ABC.DEF@\"\"" );
      assertIsFalse( "ABC.DEF@G\"HI.DE" );
      assertIsFalse( "ABC.DEF@GHI.DE\"" );
      assertIsFalse( "ABC.DEF@\"GHI.DE" );
      assertIsFalse( "\"Escape.Sequenz.Ende\"" );
      assertIsFalse( "ABC.DEF\"GHI.DE" );
      assertIsFalse( "ABC.DEF\"@GHI.DE" );
      assertIsFalse( "ABC.DE\"F@GHI.DE" );
      assertIsFalse( "\"ABC.DEF@GHI.DE" );
      assertIsFalse( "\"ABC.DEF@GHI.DE\"" );
      assertIsTrue( "\".ABC.DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC.DEF.\"@GHI.DE" );
      assertIsTrue( "\"ABC\".DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "A\"BC\".DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "\"ABC\".DEF.G\"HI\"@JKL.de" );
      assertIsFalse( "\"AB\"C.DEF.\"GHI\"@JKL.de" );
      assertIsFalse( "\"ABC\".DEF.\"GHI\"J@KL.de" );
      assertIsFalse( "\"AB\"C.D\"EF\"@GHI.DE" );
      assertIsTrue( "AB.\"(CD)\".EF@GHI.JKL" );
      assertIsFalse( "\"Ende.am.Eingabeende\"" );
      assertIsFalse( "0\"00.000\"@GHI.JKL" );

      assertIsTrue( "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de" );
      assertIsFalse( "\"A[B]C\".D(E)F.\"GHI\"@JKL.de" );
      assertIsFalse( "\"A[B]C\".D[E]F.\"GHI\"@JKL.de" );
      assertIsFalse( "\"A[B]C\".D<E>F.\"GHI\"@JKL.de" );
      assertIsTrue( "\"()<>[]:.;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org" );

      assertIsFalse( "\"Joe Smith\" email@domain.com" );
      assertIsFalse( "\"Joe\\tSmith\".email@domain.com" );
      assertIsFalse( "\"Joe\"Smith\" email@domain.com" );

      wlHeadline( "Comments" );
      assertIsTrue( "(ABC)DEF@GHI.JKL" );
      assertIsTrue( "(ABC) DEF@GHI.JKL" );
      assertIsTrue( "ABC(DEF)@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.JKL(MNO)" );
      assertIsTrue( "ABC.DEF@GHI.JKL      (MNO)" );
      assertIsFalse( "ABC.DEF@             (MNO)" );
      assertIsFalse( "ABC.DEF@   .         (MNO)" );
      assertIsFalse( "ABC.DEF              (MNO)" );
      assertIsFalse( "ABC.DEF@GHI.         (MNO)" );
      assertIsFalse( "ABC.DEF@GHI.JKL       MNO" );
      assertIsFalse( "ABC.DEF@GHI.JKL          " );
      assertIsFalse( "ABC.DEF@GHI.JKL       .  " );

      assertIsTrue( "ABC.DEF@GHI.JKL ()" );
      assertIsTrue( "ABC.DEF@GHI.JKL()" );
      assertIsTrue( "ABC.DEF@()GHI.JKL" );
      assertIsTrue( "ABC.DEF()@GHI.JKL" );
      assertIsTrue( "()ABC.DEF@GHI.JKL" );
      assertIsFalse( "()()()ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL ()()" );
      assertIsFalse( "(ABC).DEF@GHI.JKL" );
      assertIsFalse( "ABC.(DEF)@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GHI).JKL" );
      assertIsFalse( "ABC.DEF@GHI.(JKL).MNO" );
      assertIsFalse( "ABC.DEF@GHI.JK(L.M)NO" );
      assertIsFalse( "AB(CD)EF@GHI.JKL" );
      assertIsFalse( "AB.(CD).EF@GHI.JKL" );
      assertIsFalse( "(ABCDEF)@GHI.JKL" );
      assertIsFalse( "(ABCDEF).@GHI.JKL" );
      assertIsFalse( "(AB\"C)DEF@GHI.JKL" );
      assertIsFalse( "(AB\\C)DEF@GHI.JKL" );
      assertIsFalse( "(AB\\@C)DEF@GHI.JKL" );
      assertIsFalse( "ABC(DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI)JKL" );
      assertIsFalse( ")ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC(DEF@GHI).JKL" );
      assertIsFalse( "ABC(DEF.GHI).JKL" );
      assertIsFalse( "(ABC.DEF@GHI.JKL)" );
      assertIsFalse( "(A(B(C)DEF@GHI.JKL" );
      assertIsFalse( "(A)B)C)DEF@GHI.JKL" );
      assertIsFalse( "(A)BCDE(F)@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GH)I.JK(LM)" );
      assertIsFalse( "ABC.DEF@(GH(I.JK)L)M" );
      assertIsTrue( "ABC.DEF@(comment)[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@(comment) [1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4](comment)" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]    (comment)" );
      assertIsFalse( "ABC.DEF@[1.2.3(comment).4]" );
      assertIsTrue( "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)" );
      assertIsTrue( "(comment)john.smith@example.com" );
      assertIsTrue( "john.smith(comment)@example.com" );
      assertIsTrue( "john.smith@(comment)example.com" );
      assertIsTrue( "john.smith@example.com(comment)" );
      assertIsFalse( "john.smith@exampl(comment)e.com" );
      assertIsFalse( "john.s(comment)mith@example.com" );
      assertIsFalse( "john.smith(comment)@(comment)example.com" );
      assertIsFalse( "john.smith(com@ment)example.com" );
      assertIsFalse( "email( (nested) )@plus.com" );
      assertIsFalse( "email)mirror(@plus.com" );
      assertIsFalse( "email@plus.com (not closed comment" );
      assertIsFalse( "email(with @ in comment)plus.com" );
      assertIsTrue( "email@domain.com (joe Smith)" );

      wlHeadline( "Pointy Brackets" );

      assertIsTrue( "ABC DEF <ABC.DEF@GHI.JKL>" );
      assertIsTrue( "<ABC.DEF@GHI.JKL> ABC DEF" );
      assertIsFalse( "ABC DEF ABC.DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL ABC DEF" );
      assertIsTrue( "\"ABC DEF \"<ABC.DEF@GHI.JKL>" );
      assertIsTrue( "\"ABC<DEF>\"@JKL.DE" );
      assertIsTrue( "\"ABC<DEF@GHI.COM>\"@JKL.DE" );
      assertIsFalse( "ABC DEF <ABC.<DEF@GHI.JKL>" );
      assertIsFalse( "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>" );
      assertIsFalse( "ABC DEF <ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL> ABC DEF" );
      assertIsFalse( "ABC DEF >ABC.DEF@GHI.JKL<" );
      assertIsFalse( ">ABC.DEF@GHI.JKL< ABC DEF" );
      assertIsFalse( "ABC DEF <A@A>" );
      assertIsFalse( "<A@A> ABC DEF" );
      assertIsFalse( "ABC DEF <>" );
      assertIsFalse( "<> ABC DEF" );
      assertIsFalse( ">" );
      assertIsTrue( "<ABC.DEF@GHI.JKL>" ); // correct ?
      assertIsTrue( "Joe Smith <email@domain.com>" );
      assertIsFalse( "Joe Smith <mailto:email@domain.com>" );
      assertIsFalse( "Joe Smith <mailto:email(with comment)@domain.com>" );
      assertIsTrue( "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>" );
      assertIsTrue( "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsTrue( "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " );
      assertIsFalse( "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>" );
      assertIsFalse( "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" );
      assertIsFalse( "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " );
      assertIsFalse( "Test |<gaaf <email@domain.com>" );
      assertIsFalse( "Display Name <email@plus.com> (Comment after name with display)" );
      assertIsFalse( "\"With extra < within quotes\" Display Name<email@domain.com>" );
      assertIsFalse( "<null>@mail.com" );

      wlHeadline( "Length" );

      String str_50 = "12345678901234567890123456789012345678901234567890";
      String str_63 = "A23456789012345678901234567890123456789012345678901234567890123";

      assertIsTrue( "A@B.CD" );
      assertIsFalse( "A@B.C" );
      assertIsFalse( "A@COM" );

      assertIsTrue( "ABC.DEF@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.J" );
      assertIsTrue( "ABC.DEF@GHI." + str_63 );
      assertIsFalse( "ABC.DEF@GHI." + str_63 + "A" );
      assertIsTrue( "A@B.CD" );

      assertIsTrue( "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" );
      assertIsTrue( "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" );
      assertIsTrue( "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" );
      assertIsFalse( "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );

      assertIsTrue( "domain.label.with.63.characters@" + str_63 + ".com" );
      assertIsFalse( "domain.label.with.64.characters@" + str_63 + "A.com" );
      assertIsTrue( "two.domain.labels.with.63.characters@" + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "domain.label.with.63.and.64.characters@" + str_63 + "." + str_63 + "A.com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + ".com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsTrue( "" + str_50 + ".1234567@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "" + str_50 + ".12345678@" + str_63 + "." + str_63 + "." + str_63 + ".com" );

      assertIsTrue( "eMail Test XX1 <63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com>" );
      assertIsTrue( "eMail Test XX2 <" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com>" );
      assertIsFalse( "eMail Test XX3 AAA<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com>" );
      assertIsFalse( "eMail Test XX4 <" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + "A.com>" );
      assertIsFalse( "eMail Test XX5A <" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com>" );

      assertIsTrue( "" + str_50 + " " + str_50 + " " + str_50 + " " + str_50 + " " + str_50.substring( 0, 38 ) + "<A@B.de.com>" );

      assertIsTrue( "<63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test OK3" );
      assertIsTrue( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test OK4" );
      assertIsFalse( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test FALSE3" );
      assertIsFalse( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + "A.com> eMail Test FALSE4" );

      assertIsTrue( "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com" );
      assertIsFalse( "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" );
      assertIsFalse( "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" );
      assertIsFalse( "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" );

      assertIsTrue( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
      assertIsFalse( "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );

      assertIsTrue( "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=.?^`{|}~@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-0123456789.a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.e4.f5.g6.h7.i8.j9.K0.L1.M2.N3.O.domain.name" );

      assertIsTrue( "email@domain.topleveldomain" );
      assertIsTrue( "email@email.email.mydomain" );

      wl( "" );
      wlHeadline( "https://en.wikipedia.org/wiki/Email_address/" );
      wl( "" );

      assertIsTrue( "MaxMuster(Kommentar)@example.com" );
      assertIsTrue( "\"MaxMustermann\"@example.com" );
      assertIsTrue( "Max.\"Musterjunge\".Mustermann@example.com" );

      assertIsTrue( "\".John.Doe\"@example.com" );
      assertIsTrue( "\"John.Doe.\"@example.com" );
      assertIsTrue( "\"John..Doe\"@example.com" );
      assertIsTrue( "john.smith(comment)@example.com" );
      assertIsTrue( "(comment)john.smith@example.com" );
      assertIsTrue( "john.smith@(comment)example.com" );
      assertIsTrue( "john.smith@example.com(comment)" );
      assertIsTrue( "john.smith@example.com" );
      assertIsTrue( "jsmith@[192.168.2.1]" );
      assertIsTrue( "jsmith@[IPv6:2001:db8::1]" );
      assertIsTrue( "surelsaya@surabaya.vibriel.net.id" );
      assertIsTrue( "Marc Dupont <md118@example.com>" );
      assertIsTrue( "simple@example.com" );
      assertIsTrue( "very.common@example.com" );
      assertIsTrue( "disposable.style.email.with+symbol@example.com" );
      assertIsTrue( "other.email-with-hyphen@example.com" );
      assertIsTrue( "fully-qualified-domain@example.com" );
      assertIsTrue( "user.name+tag+sorting@example.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "!#$%&'*+-/=?^_`.{|}~@example.com" );
      assertIsTrue( "x@example.com" );
      assertIsTrue( "info@firma.org" );
      assertIsTrue( "example-indeed@strange-example.com" );
      assertIsTrue( "admin@mailserver1" );
      assertIsTrue( "example@s.example" );
      assertIsTrue( "\" \"@example.org" );
      assertIsTrue( "\"john..doe\"@example.org" );
      assertIsTrue( "mailhost!username@example.org" );
      assertIsTrue( "user%example.com@example.org" );
      assertIsTrue( "joe25317@NOSPAMexample.com" );
      assertIsTrue( "Peter.Zapfl@Telekom.DBP.De" );

      assertIsFalse( "Abc.example.com" );
      assertIsFalse( "Abc..123@example.com" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsFalse( "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "just\"not\"right@example.com" );
      assertIsFalse( "this is\"not\\allowed@example.com" );
      assertIsFalse( "this\\ still\\\"not\\\\allowed@example.com" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );
      assertIsFalse( "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com" );

      wl( "" );
      wlHeadline( "https://github.com/egulias/EmailValidator4J" );
      wl( "" );

      assertIsFalse( "nolocalpart.com" );
      assertIsFalse( "test@example.com test" );
      assertIsFalse( "user  name@example.com" );
      assertIsFalse( "user   name@example.com" );
      assertIsFalse( "example.@example.co.uk" );
      assertIsFalse( "example@example@example.co.uk" );
      assertIsFalse( "(test_exampel@example.fr}" );
      assertIsFalse( "example(example)example@example.co.uk" );
      assertIsFalse( ".example@localhost" );
      assertIsFalse( "ex\\ample@localhost" );
      assertIsFalse( "example@local\\host" );
      assertIsFalse( "example@localhost." );
      assertIsFalse( "user name@example.com" );
      assertIsFalse( "username@ example . com" );
      assertIsFalse( "example@(fake}.com" );
      assertIsFalse( "example@(fake.com" );
      assertIsTrue( "username@example.com" );
      assertIsTrue( "usern.ame@example.com" );
      assertIsFalse( "user[na]me@example.com" );
      assertIsFalse( "\"\"\"@iana.org" );
      assertIsFalse( "\"\\\"@iana.org" );
      assertIsFalse( "\"test\"test@iana.org" );
      assertIsFalse( "\"test\"\"test\"@iana.org" );
      assertIsTrue( "\"test\".\"test\"@iana.org" );
      assertIsTrue( "\"test\".test@iana.org" );
      assertIsFalse( String.format( "\"test\\%s@iana.org", "\"" ) );
      assertIsFalse( "\r\ntest@iana.org" );
      assertIsFalse( "\r\n test@iana.org" );
      assertIsFalse( "\r\n \r\ntest@iana.org" );
      assertIsFalse( "\r\n \r\n test@iana.org" );
      assertIsFalse( "test@iana.org \r\n" );
      assertIsFalse( "test@iana.org \r\n " );
      assertIsFalse( "test@iana.org \r\n \r\n" );
      assertIsFalse( "test@iana.org \r\n\r\n" );
      assertIsFalse( "test@iana.org  \r\n\r\n " );
      assertIsFalse( "test@iana/icann.org" );
      assertIsFalse( "test@foo;bar.com" );
      assertIsFalse( (char) 1 + "a@test.com" );
      assertIsFalse( "comment)example@example.com" );
      assertIsFalse( "comment(example))@example.com" );
      assertIsFalse( "example@example)comment.com" );
      assertIsFalse( "example@example(comment)).com" );
      assertIsFalse( "example@[1.2.3.4" );
      assertIsFalse( "example@[IPv6:1:2:3:4:5:6:7:8" );
      assertIsFalse( "exam(ple@exam).ple" );
      assertIsFalse( "example@(example))comment.com" );
      assertIsTrue( "example@example.com" );
      assertIsTrue( "example@example.co.uk" );
      assertIsTrue( "example_underscore@example.fr" );
      assertIsTrue( "exam'ple@example.com" );
      assertIsTrue( String.format( "exam\\%sple@example.com", " " ) );
      assertIsFalse( "example((example))@fakedfake.co.uk" );
      assertIsFalse( "example@faked(fake).co.uk" );
      assertIsTrue( "example+@example.com" );
      assertIsTrue( "example@with-hyphen.example.com" );
      assertIsTrue( "with-hyphen@example.com" );
      assertIsTrue( "example@1leadingnum.example.com" );
      assertIsTrue( "1leadingnum@example.com" );
      assertIsTrue( "инфо@письмо.рф" );
      assertIsTrue( "\"username\"@example.com" );
      assertIsTrue( "\"user.name\"@example.com" );
      assertIsTrue( "\"user name\"@example.com" );
      assertIsTrue( "\"user@name\"@example.com" );
      assertIsFalse( "\"\\a\"@iana.org" );
      assertIsTrue( "\"test\\ test\"@iana.org" );
      assertIsFalse( "\"\"@iana.org" );
      assertIsFalse( "\"\"@[]" );
      assertIsTrue( String.format( "\"\\%s\"@iana.org", "\"" ) );
      assertIsTrue( "example@localhost" );

      wl( "" );
      wlHeadline( "unsorted from the WEB" );
      wl( "" );

      /*
       * <pre>
       * 
       * Various examples. Scraped from the Internet-Forums.
       * 
       * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
       * https://stackoverflow.com/questions/25471114/how-to-validate-an-e-mail-address-in-swift?noredirect=1&lq=1
       * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
       * https://stackoverflow.com/questions/6850894/regex-split-email-address?noredirect=1&lq=1
       * https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript?page=3&tab=votes#tab-top
       * https://docs.microsoft.com/en-us/dotnet/api/system.net.mail.mailaddress.address?redirectedfrom=MSDN&view=netframework-4.8#System_Net_Mail_MailAddress_Address
       * 
       * </pre>
       */
      assertIsFalse( "..@test.com" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( "ab@sd@dd" );
      assertIsFalse( ".@s.dd" );
      assertIsFalse( "a@b.-de.cc" );
      assertIsFalse( "a@bde-.cc" );
      assertIsFalse( "a@b._de.cc" );
      assertIsFalse( "a@bde_.cc" );
      assertIsFalse( "a@bde.cc." );
      assertIsFalse( "ab@b+de.cc" );
      assertIsFalse( "a..b@bde.cc" );
      assertIsFalse( "_@bde.cc." );
      assertIsFalse( "plainaddress" );
      assertIsFalse( "plain.address" );
      assertIsFalse( ".email@domain.com" );
      assertIsFalse( "email.@domain.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email@.domain.com" );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@domain..com" );
      assertIsFalse( "MailTo:casesensitve@domain.com" );
      assertIsFalse( "mailto:email@domain.com" );
      assertIsFalse( "email@domain" );
      assertIsTrue( "someone@somewhere.com" );
      assertIsTrue( "someone@somewhere.co.uk" );
      assertIsTrue( "someone+tag@somewhere.net" );
      assertIsTrue( "futureTLD@somewhere.fooo" );
      assertIsFalse( "myemailsample.com" );
      assertIsTrue( "myemail@sample" );
      assertIsTrue( "myemail@sample.com" );
      assertIsFalse( "myemail@@sample.com" );
      assertIsFalse( "myemail@sa@mple.com" );
      assertIsTrue( "\"myemail@sa\"@mple.com" );
      assertIsFalse( "a.\"b@c\".x.\"@\".d.e@f.g@" );
      assertIsFalse( "foo~&(&)(@bar.com" );
      assertIsTrue( "\"foo\\@bar\"@Something.com" );
      assertIsFalse( "Foobar Some@thing.com" );
      assertIsFalse( "foo@bar@machine.subdomain.example.museum" );
      assertIsTrue( "foo\\@bar@machine.subdomain.example.museum" );
      assertIsFalse( "foo.bar@machine.sub\\@domain.example.museum" );
      assertIsTrue( "cog@wheel.com" );
      assertIsTrue( "\"cogwheel the orange\"@example.com" );
      assertIsFalse( "123@$.xyz" );
      assertIsTrue( "david.jones@proseware.com" );
      assertIsTrue( "d.j@server1.proseware.com" );
      assertIsTrue( "jones@ms1.proseware.com" );
      assertIsFalse( "j.@server1.proseware.com" );
      assertIsTrue( "j@proseware.com9" );
      assertIsTrue( "js#internal@proseware.com" );
      assertIsTrue( "j_9@[129.126.118.1]" );
      assertIsFalse( "j..s@proseware.com" );
      assertIsTrue( "js*@proseware.com" );
      assertIsFalse( "js@proseware..com" );
      assertIsTrue( "js@proseware.com9" );
      assertIsTrue( "j.s@server1.proseware.com" );
      assertIsTrue( "\"j\\\"s\"@proseware.com" );
      assertIsFalse( "dasddas-@.com" );
      assertIsTrue( "-asd@das.com" );
      assertIsFalse( "as3d@dac.coas-" );
      assertIsTrue( "dsq!a?@das.com" );
      assertIsTrue( "_dasd@sd.com" );
      assertIsFalse( "dad@sds" );
      assertIsTrue( "asd-@asd.com" );
      assertIsTrue( "dasd_-@jdas.com" );
      assertIsFalse( "asd@dasd@asd.cm" );
      assertIsFalse( "da23@das..com" );
      assertIsTrue( "_dasd_das_@9.com" );
      assertIsTrue( "d23d@da9.co9" );
      assertIsTrue( "dasd.dadas@dasd.com" );
      assertIsTrue( "dda_das@das-dasd.com" );
      assertIsTrue( "dasd-dasd@das.com.das" );
      assertIsFalse( "fdsa" );
      assertIsFalse( "fdsa@" );
      assertIsFalse( "fdsa@fdsa" );
      assertIsFalse( "fdsa@fdsa." );
      assertIsFalse( "@b.com" );
      assertIsFalse( "a@.com" );
      assertIsTrue( "a@bcom" );
      assertIsTrue( "a.b@com" );
      assertIsFalse( "a@b." );
      assertIsTrue( "ab@c.com" );
      assertIsTrue( "a@bc.com" );
      assertIsTrue( "a@b.com" );
      assertIsTrue( "a@b.c.com" );
      assertIsTrue( "a+b@c.com" );
      assertIsTrue( "a@123.45.67.89" );
      assertIsTrue( "%2@gmail.com" );
      assertIsTrue( "\"%2\"@gmail.com" );
      assertIsTrue( "\"a..b\"@gmail.com" );
      assertIsTrue( "\"a_b\"@gmail.com" );
      assertIsTrue( "_@gmail.com" );
      assertIsTrue( "1@gmail.com" );
      assertIsTrue( "1_example@something.gmail.com" );
      assertIsTrue( "d._.___d@gmail.com" );
      assertIsTrue( "d.oy.smith@gmail.com" );
      assertIsTrue( "d_oy_smith@gmail.com" );
      assertIsTrue( "doysmith@gmail.com" );
      assertIsTrue( "D.Oy'Smith@gmail.com" );
      assertIsTrue( "%20f3v34g34@gvvre.com" );
      assertIsTrue( "piskvor@example.lighting" );
      assertIsTrue( "--@ooo.ooo" );
      assertIsFalse( "check@thiscom" );
      assertIsFalse( "check@this..com" );
      assertIsFalse( " check@this.com" );
      assertIsTrue( "check@this.com" );
      assertIsTrue( "Abc@example.com" );
      assertIsFalse( "Abc@example.com." );
      assertIsTrue( "Abc@10.42.0.1" );
      assertIsTrue( "user@localserver" );
      assertIsTrue( "Abc.123@example.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "\" \"@example.org" );
      assertIsTrue( "user@[IPv6:2001:DB8::1]" );
      assertIsFalse( "Abc.example.com" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "this\\ still\\\"not\\allowed@example.com" );
      assertIsTrue( "email@example.com" );
      assertIsTrue( "email@example.co.uk" );
      assertIsTrue( "email@mail.gmail.com" );
      assertIsTrue( "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com" );
      assertIsFalse( "email@example.co.uk." );
      assertIsFalse( "email@example" );
      assertIsFalse( " email@example.com" );
      assertIsFalse( "email@example.com " );
      assertIsFalse( "invalid.email.com" );
      assertIsFalse( "invalid@email@domain.com" );
      assertIsFalse( "email@example..com" );
      assertIsTrue( "yoursite@ourearth.com" );
      assertIsTrue( "my.ownsite@ourearth.org" );
      assertIsTrue( "mysite@you.me.net" );
      assertIsTrue( "xxxx@gmail.com" );
      assertIsTrue( "xxxxxx@yahoo.com" );
      assertIsFalse( "xxxx.ourearth.com" );
      assertIsFalse( "xxxx@.com.my" );
      assertIsFalse( "@you.me.net" );
      assertIsFalse( "xxxx123@gmail.b" );
      assertIsFalse( "xxxx@.org.org" );
      assertIsFalse( ".xxxx@mysite.org" );
      assertIsFalse( "xxxxx()*@gmail.com" );
      assertIsFalse( "xxxx..1234@yahoo.com" );
      assertIsTrue( "alex@example.com" );
      assertIsTrue( "alireza@test.co.uk" );
      assertIsTrue( "peter.example@yahoo.com.au" );
      assertIsTrue( "peter_123@news.com" );
      assertIsTrue( "hello7___@ca.com.pt" );
      assertIsTrue( "example@example.co" );
      assertIsFalse( "hallo@example.coassjj#sswzazaaaa" );
      assertIsFalse( "hallo2ww22@example....caaaao" );
      assertIsTrue( "abcxyz123@qwert.com" );
      assertIsTrue( "abc123xyz@asdf.co.in" );
      assertIsTrue( "abc1_xyz1@gmail1.com" );
      assertIsTrue( "abc.xyz@gmail.com.in" );
      assertIsTrue( "pio_pio@factory.com" );
      assertIsTrue( "~pio_pio@factory.com" );
      assertIsTrue( "pio_~pio@factory.com" );
      assertIsTrue( "pio_#pio@factory.com" );
      assertIsFalse( "pio_pio@#factory.com" );
      assertIsFalse( "pio_pio@factory.c#om" );
      assertIsFalse( "pio_pio@factory.c*om" );
      assertIsTrue( "pio^_pio@factory.com" );
      assertIsTrue( "\"Abc\\@def\"@example.com" );
      assertIsTrue( "\"Fred Bloggs\"@example.com" );
      assertIsTrue( "\"Fred\\ Bloggs\"@example.com" );
      assertIsTrue( "Fred\\ Bloggs@example.com" );
      assertIsFalse( "\"Joe\\Blow\"@example.com" );
      assertIsTrue( "\"Joe\\\\Blow\"@example.com" );
      assertIsTrue( "\"Abc@def\"@example.com" );
      assertIsTrue( "customer/department=shipping@example.com" );
      assertIsFalse( "\\$A12345@example.com" );
      assertIsTrue( "!def!xyz%abc@example.com" );
      assertIsTrue( "_somename@example.com" );
      assertIsTrue( "abc.\"defghi\".xyz@example.com" );
      assertIsTrue( "\"abcdefghixyz\"@example.com" );
      assertIsFalse( "abc\"defghi\"xyz@example.com" );
      assertIsFalse( "abc\\\"def\\\"ghi@example.com" );
      assertIsTrue( "!sd@gh.com" );

      wl( "" );
      wlHeadline( "https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs" );
      wl( "" );

      assertIsTrue( "\"\\e\\s\\c\\a\\p\\e\\d\"@sld.com" );
      assertIsTrue( "\"back\\slash\"@sld.com" );
      assertIsTrue( "\"escaped\\\"quote\"@sld.com" );
      assertIsTrue( "\"quoted\"@sld.com" );
      assertIsTrue( "\"quoted-at-sign@sld.org\"@sld.com" );
      assertIsTrue( "&'*+-./=?^_{}~@other-valid-characters-in-local.net" );
      assertIsTrue( "_.-+~^*'`{GEO}`'*^~+-._@example.com" );
      assertIsTrue( "01234567890@numbers-in-local.net" );
      assertIsTrue( "a@single-character-in-local.org" );
      assertIsTrue( "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" );
      assertIsTrue( "backticksarelegit@test.com" );
      assertIsTrue( "bracketed-IP-instead-of-domain@[127.0.0.1]" );
      assertIsTrue( "country-code-tld@sld.rw" );
      assertIsTrue( "country-code-tld@sld.uk" );
      assertIsTrue( "letters-in-sld@123.com" );
      assertIsTrue( "local@dash-in-sld.com" );
      assertIsTrue( "local@sld.newTLD" );
      assertIsTrue( "local@sub.domains.com" );
      assertIsTrue( "mixed-1234-in-{+^}-local@sld.net" );
      assertIsTrue( "one-character-third-level@a.example.com" );
      assertIsTrue( "one-letter-sld@x.org" );
      assertIsTrue( "punycode-numbers-in-tld@sld.xn--3e0b707e" );
      assertIsTrue( "single-character-in-sld@x.org" );
      assertIsTrue( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" );
      assertIsTrue( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-length-blah-blah-blah-blah-bla.org" );
      assertIsTrue( "uncommon-tld@sld.mobi" );
      assertIsTrue( "uncommon-tld@sld.museum" );
      assertIsTrue( "uncommon-tld@sld.travel" );
      assertIsFalse( "invalid" );
      assertIsFalse( "invalid@" );
      assertIsFalse( "invalid @" );
      assertIsFalse( "invalid@[555.666.777.888]" );
      assertIsFalse( "invalid@[IPv6:123456]" );
      assertIsFalse( "invalid@[127.0.0.1.]" );
      assertIsFalse( "invalid@[127.0.0.1]." );
      assertIsFalse( "invalid@[127.0.0.1]x" );
      assertIsFalse( "<>@[]`|@even-more-invalid-characters-in-local.org" );
      assertIsFalse( "@missing-local.org" );
      assertIsFalse( "IP-and-port@127.0.0.1:25" );
      assertIsFalse( "another-invalid-ip@127.0.0.256" );
      assertIsFalse( "ip.range.overflow@[127.0.0.256]" );
      assertIsFalse( "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org" );
      assertIsFalse( "invalid-ip@127.0.0.1.26" );
      assertIsFalse( "local-ends-with-dot.@sld.com" );
      assertIsFalse( "missing-at-sign.net" );
      assertIsFalse( "missing-sld@.com" );
      assertIsFalse( "missing-tld@sld." );
      assertIsFalse( "sld-ends-with-dash@sld-.com" );
      assertIsFalse( "sld-starts-with-dashsh@-sld.com" );
      assertIsFalse( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" );
      assertIsFalse( "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" );
      assertIsFalse( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-255-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bl.org" );
      assertIsFalse( "two..consecutive-dots@sld.com" );
      assertIsFalse( "unbracketed-IP@127.0.0.1" );
      assertIsFalse( "underscore.error@example.com_" );

      wl( "" );
      wlHeadline( "https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php" );
      wl( "" );

      assertIsTrue( "first.last@iana.org" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678901234@iana.org" );
      assertIsTrue( "\"first\\\"last\"@iana.org" );
      assertIsTrue( "\"first@last\"@iana.org" );
      assertIsTrue( "\"first\\\\last\"@iana.org" );
      assertIsTrue( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" );
      assertIsTrue( "first.last@[12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:6666]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]" );
      assertIsTrue( "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" );
      assertIsTrue( "first.last@3com.com" );
      assertIsTrue( "first.last@123.iana.org" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]" );
      assertIsTrue( "\"Abc\\@def\"@iana.org" );
      assertIsTrue( "\"Fred\\ Bloggs\"@iana.org" );
      assertIsTrue( "\"Joe.\\\\Blow\"@iana.org" );
      assertIsTrue( "\"Abc@def\"@iana.org" );
      assertIsTrue( "\"Fred Bloggs\"@iana.orgin" );
      assertIsTrue( "user+mailbox@iana.org" );
      assertIsTrue( "$A12345@iana.org" );
      assertIsTrue( "!def!xyz%abc@iana.org" );
      assertIsTrue( "_somename@iana.org" );
      assertIsTrue( "dclo@us.ibm.com" );
      assertIsTrue( "peter.piper@iana.org" );
      assertIsTrue( "test@iana.org" );
      assertIsTrue( "TEST@iana.org" );
      assertIsTrue( "1234567890@iana.org" );
      assertIsTrue( "test+test@iana.org" );
      assertIsTrue( "test-test@iana.org" );
      assertIsTrue( "t*est@iana.org" );
      assertIsTrue( "+1~1+@iana.org" );
      assertIsTrue( "{_test_}@iana.org" );
      assertIsTrue( "test.test@iana.org" );
      assertIsTrue( "\"test.test\"@iana.org" );
      assertIsTrue( "test.\"test\"@iana.org" );
      assertIsTrue( "\"test@test\"@iana.org" );
      assertIsTrue( "test@123.123.123.x123" );
      assertIsFalse( "test@123.123.123.123" );
      assertIsTrue( "test@[123.123.123.123]" );
      assertIsTrue( "test@example.iana.org" );
      assertIsTrue( "test@example.example.iana.org" );
      assertIsTrue( "customer/department@iana.org" );
      assertIsTrue( "_Yosemite.Sam@iana.org" );
      assertIsTrue( "~@iana.org" );
      assertIsTrue( "\"Austin@Powers\"@iana.org" );
      assertIsTrue( "Ima.Fool@iana.org" );
      assertIsTrue( "\"Ima.Fool\"@iana.org" );
      assertIsTrue( "\"Ima Fool\"@iana.orgin" );
      assertIsTrue( "\"first\".\"last\"@iana.org" );
      assertIsTrue( "\"first\".middle.\"last\"@iana.org" );
      assertIsTrue( "\"first\".last@iana.org" );
      assertIsTrue( "first.\"last\"@iana.org" );
      assertIsTrue( "\"first\".\"middle\".\"last\"@iana.org" );
      assertIsTrue( "\"first.middle\".\"last\"@iana.org" );
      assertIsTrue( "\"first.middle.last\"@iana.org" );
      assertIsTrue( "\"first..last\"@iana.org" );
      assertIsTrue( "first.\"middle\".\"last\"@iana.org" );
      assertIsFalse( "first.last @iana.orgin" );
      assertIsTrue( "\"test blah\"@iana.orgin" );
      assertIsTrue( "name.lastname@domain.com" );
      assertIsTrue( "a@bar.com" );
      assertIsTrue( "aaa@[123.123.123.123]" );
      assertIsTrue( "a-b@bar.com" );
      assertIsFalse( "+@b.c" );
      assertIsTrue( "+@b.com" );
      assertIsTrue( "a@b.co-foo.uk" );
      assertIsTrue( "\"hello my name is\"@stutter.comin" );
      assertIsTrue( "\"Test \\\"Fail\\\" Ing\"@iana.orgin" );
      assertIsTrue( "shaitan@my-domain.thisisminekthx" );
      assertIsFalse( "foobar@192.168.0.1" );
      assertIsTrue( "HM2Kinsists@(that comments are allowed)this.is.ok" );
      assertIsTrue( "user%uucp!path@berkeley.edu" );
      assertIsTrue( "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com" );
      assertIsTrue( "test@test.com" );
      assertIsTrue( "test@xn--example.com" );
      assertIsTrue( "test@example.com" );
      assertIsTrue( "{^c\\@**Dog^}@cartoon.com" );
      assertIsTrue( "first\\@last@iana.org" );
      assertIsTrue( "phil.h\\@\\@ck@haacked.com" );
      assertIsFalse( "first.last@example.123" );
      assertIsFalse( "first.last@comin" );
      assertIsTrue( "\"[[ test ]]\"@iana.orgin" );
      assertIsTrue( "Abc\\@def@iana.org" );
      assertIsTrue( "Fred\\ Bloggs@iana.org" );
      assertIsFalse( "Joe.\\Blow@iana.org" );
      assertIsFalse( "first.last@sub.do.com" );
      assertIsFalse( "first.last" );
      assertIsTrue( "wild.wezyr@best-server-ever.com" );
      assertIsTrue( "\"hello world\"@example.com" );
      assertIsFalse( "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com" );
      assertIsTrue( "John.\"The*$hizzle*Bizzle\".Doe@whatever.com" );
      assertIsTrue( "example+tag@gmail.com" );
      assertIsFalse( ".ann..other.@example.com" );
      assertIsTrue( "ann.other@example.com" );
      assertIsTrue( "something@something.something" );
      assertIsTrue( "c@(Chris's host.)public.examplein" );
      assertIsFalse( "(foo)cal(bar)@(baz)iamcal.com(quux)" );
      assertIsFalse( "cal@iamcal(woo).(yay)comin" );
      assertIsFalse( "cal(woo(yay)hoopla)@iamcal.comin" );
      assertIsFalse( "cal(foo\\@bar)@iamcal.comin" );
      assertIsFalse( "cal(foo\\)bar)@iamcal.comin" );
      assertIsFalse( "first().last@iana.orgin" );
      assertIsFalse( "pete(his account)@silly.test(his host)" );
      assertIsFalse( "jdoe@machine(comment). examplein" );
      assertIsFalse( "first(abc.def).last@iana.orgin" );
      assertIsFalse( "first(a\"bc.def).last@iana.orgin" );
      assertIsFalse( "first.(\")middle.last(\")@iana.orgin" );
      assertIsFalse( "first(abc\\(def)@iana.orgin" );
      assertIsFalse( "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" );
      assertIsFalse( "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin" );
      assertIsFalse( "1234 @ local(blah) .machine .examplein" );
      assertIsFalse( "a@bin" );
      assertIsFalse( "a@barin" );
      assertIsFalse( "@about.museum" );
      assertIsFalse( "12345678901234567890123456789012345678901234567890123456789012345@iana.org" );
      assertIsFalse( ".first.last@iana.org" );
      assertIsFalse( "first.last.@iana.org" );
      assertIsFalse( "first..last@iana.org" );
      assertIsFalse( "\"first\"last\"@iana.org" );
      assertIsFalse( "first.last@" );
      assertIsFalse( "first.last@-xample.com" );
      assertIsFalse( "first.last@exampl-.com" );
      assertIsFalse( "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" );
      assertIsFalse( "abc\\@iana.org" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@iana.org" );
      assertIsFalse( "abc@def@iana.org" );
      assertIsFalse( "@iana.org" );
      assertIsFalse( "doug@" );
      assertIsFalse( "\"qu@iana.org" );
      assertIsFalse( "ote\"@iana.org" );
      assertIsFalse( ".dot@iana.org" );
      assertIsFalse( "dot.@iana.org" );
      assertIsFalse( "two..dot@iana.org" );
      assertIsFalse( "\"Doug \"Ace\" L.\"@iana.org" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ L\\.@iana.org" );
      assertIsFalse( "hello world@iana.org" );
      assertIsFalse( "gatsby@f.sc.ot.t.f.i.tzg.era.l.d." );
      assertIsFalse( "test.iana.org" );
      assertIsFalse( "test.@iana.org" );
      assertIsFalse( "test..test@iana.org" );
      assertIsFalse( ".test@iana.org" );
      assertIsFalse( "test@test@iana.org" );
      assertIsFalse( "test@@iana.org" );
      assertIsFalse( "-- test --@iana.org" );
      assertIsFalse( "[test]@iana.org" );
      assertIsFalse( "\"test\"test\"@iana.org" );
      assertIsFalse( "()[]\\;:.><@iana.org" );
      assertIsFalse( "test@." );
      assertIsFalse( "test@example." );
      assertIsFalse( "test@.org" );
      assertIsFalse( "test@[123.123.123.123" );
      assertIsFalse( "test@123.123.123.123]" );
      assertIsFalse( "NotAnEmail" );
      assertIsFalse( "@NotAnEmail" );
      assertIsFalse( "\"test\"blah\"@iana.org" );
      assertIsFalse( ".wooly@iana.org" );
      assertIsFalse( "wo..oly@iana.org" );
      assertIsFalse( "pootietang.@iana.org" );
      assertIsFalse( ".@iana.org" );
      assertIsFalse( "Ima Fool@iana.org" );
      assertIsFalse( "foo@[\\1.2.3.4]" );
      assertIsFalse( "first.\"\".last@iana.org" );
      assertIsFalse( "first\\last@iana.org" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]" );
      assertIsFalse( "\"foo\"(yay)@(hoopla)[1.2.3.4]" );
      assertIsFalse( "cal(foo(bar)@iamcal.com" );
      assertIsFalse( "cal(foo)bar)@iamcal.com" );
      assertIsFalse( "cal(foo\\)@iamcal.com" );
      assertIsFalse( "first(middle)last@iana.org" );
      assertIsFalse( "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" );
      assertIsFalse( "a(a(b(c)d(e(f))g)(h(i)j)@iana.org" );
      assertIsFalse( ".@" );
      assertIsFalse( "@bar.com" );
      assertIsFalse( "@@bar.com" );
      assertIsFalse( "aaa.com" );
      assertIsFalse( "aaa@.com" );
      assertIsFalse( "aaa@.123" );
      assertIsFalse( "aaa@[123.123.123.123]a" );
      assertIsFalse( "aaa@[123.123.123.333]" );
      assertIsFalse( "a@bar.com." );
      assertIsFalse( "a@-b.com" );
      assertIsFalse( "a@b-.com" );
      assertIsFalse( "-@..com" );
      assertIsFalse( "-@a..com" );
      assertIsFalse( "@about.museum-" );
      assertIsFalse( "test@...........com" );
      assertIsFalse( "first.last@[IPv6::]" );
      assertIsFalse( "first.last@[IPv6::::]" );
      assertIsFalse( "first.last@[IPv6::b4]" );
      assertIsFalse( "first.last@[IPv6::::b4]" );
      assertIsFalse( "first.last@[IPv6::b3:b4]" );
      assertIsFalse( "first.last@[IPv6::::b3:b4]" );
      assertIsFalse( "first.last@[IPv6:a1:::b4]" );
      assertIsFalse( "first.last@[IPv6:a1:]" );
      assertIsFalse( "first.last@[IPv6:a1:::]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:::]" );
      assertIsFalse( "first.last@[IPv6::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6::::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1:11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1:::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]" );
      assertIsFalse( "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::11.22.33]" );
      assertIsFalse( "first.last@[IPv6:a1::11.22.33.44.55]" );
      assertIsFalse( "first.last@[IPv6:a1::b211.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::b2::11.22.33.44]" );
      assertIsFalse( "first.last@[IPv6:a1::b3:]" );
      assertIsFalse( "first.last@[IPv6::a2::b4]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]" );
      assertIsFalse( "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]" );
      assertIsFalse( "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]" );
      assertIsFalse( "first.last@[.12.34.56.78]" );
      assertIsFalse( "first.last@[12.34.56.789]" );
      assertIsFalse( "first.last@[::12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:::12.34.56.78]" );
      assertIsFalse( "first.last@[IPv5:::12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]" );
      assertIsFalse( "first.last@[IPv6:1111:2222::3333::4444:5555:6666]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:333x::4444:5555]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:33333::4444:5555]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:::]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333::5555:6666::]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]" );
      assertIsTrue( "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]" );
      assertIsTrue( "first.last@[IPv6:::]" );
      assertIsTrue( "first.last@[IPv6:::b4]" );
      assertIsTrue( "first.last@[IPv6:::b3:b4]" );
      assertIsTrue( "first.last@[IPv6:a1::b4]" );
      assertIsTrue( "first.last@[IPv6:a1::]" );
      assertIsTrue( "first.last@[IPv6:a1:a2::]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:cdef::]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:CDEF::]" );
      assertIsTrue( "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1:a2::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:a1::b2:11.22.33.44]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]" );

      wl( "" );
      wlHeadline( "https://www.rohannagar.com/jmail/" );
      wl( "" );

      assertIsFalse( "\"qu@test.org" ); // Opening quote must have a closing quote
      assertIsFalse( "ote\"@test.org" ); // Closing quote must have an opening quote
      assertIsFalse( "\"().:;<>[\\]@example.com" ); // Opening quote must have a closing quote
      assertIsFalse( "\"\"\"@iana.org" ); // Each quote must be in a pair
      assertIsFalse( "Abc.example.com" ); // The @ character must be present
      assertIsFalse( "A@b@c@example.com" ); // There can only be a single @ character
      assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" ); 
      assertIsFalse( "this is\"not\\allowed@example.com" ); // Whitespace should be quoted or dot-separated
      assertIsFalse( "this\\ still\"not\\allowed@example.com" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" ); 
      assertIsFalse( "QA[icon]CHOCOLATE[icon]@test.com" ); // Unquoted [ and ] characters are not allowed
      assertIsFalse( "QA\\[icon\\]CHOCOLATE\\[icon\\]@test.com" ); // Unquoted [ and ] characters are not allowed
      assertIsFalse( "plainaddress" ); // The @ character must be present
      assertIsFalse( "@example.com" );
      assertIsFalse( ".email@example.com" );
      assertIsFalse( "email.@example.com" );
      assertIsFalse( "email..email@example.com" );
      assertIsFalse( "email@-example.com" );
      assertIsFalse( "email@111.222.333.44444" );
      assertIsFalse( "this\\ is\"really\"not\\allowed@example.com" );
      assertIsFalse( "email@[12.34.44.56" );
      assertIsFalse( "email@14.44.56.34]" );
      assertIsFalse( "email@[1.1.23.5f]" );
      assertIsFalse( "email@[3.256.255.23]" );
      assertIsFalse( "\"first\\\"last\"@test.org" );
      assertIsFalse( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" );
      assertIsFalse( "first\\@last@iana.org" );
      assertIsFalse( "test@example.com " );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]" );
      assertIsFalse( "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]" );
      assertIsFalse( "invalid@about.museum-" );
      assertIsFalse( "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" );
      assertIsFalse( "abc@def@test.org" );
      assertIsFalse( "abc\\@def@test.org" );
      assertIsFalse( "abc\\@test.org" );
      assertIsFalse( "@test.org" );
      assertIsFalse( ".dot@test.org" );
      assertIsFalse( "dot.@test.org" );
      assertIsFalse( "two..dot@test.org" );
      assertIsFalse( "\"Doug \"Ace\" L.\"@test.org" );
      assertIsFalse( "Doug\\ \"Ace\"\\ L\\.@test.org" );
      assertIsFalse( "hello world@test.org" );
      assertIsFalse( "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" );
      assertIsFalse( "a(a(b(c)d(e(f))g)(h(i)j)@test.org" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@test.org" );
      assertIsFalse( "test.test.org" );
      assertIsFalse( "test.@test.org" );
      assertIsFalse( "test..test@test.org" );
      assertIsFalse( ".test@test.org" );
      assertIsFalse( "test@test@test.org" );
      assertIsFalse( "test@@test.org" );
      assertIsFalse( "-- test --@test.org" );
      assertIsFalse( "[test]@test.org" );
      assertIsFalse( "\"test\"test\"@test.org" );
      assertIsFalse( "()[]\\;:.><@test.org" );
      assertIsFalse( "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" );
      assertIsFalse( ".@test.org" );
      assertIsFalse( "Ima Fool@test.org" );
      assertIsFalse( "\"first\\\"last\"@test.org" );
      assertIsFalse( "foo@[\1.2.3.4]" );
      assertIsFalse( "first\\last@test.org" );
      assertIsFalse( "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" );
      assertIsFalse( "first(middle)last@test.org" );
      assertIsFalse( "\"test\"test@test.com" );
      assertIsFalse( "()@test.com" );
      assertIsFalse( "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" );
      assertIsFalse( "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" );
      assertIsFalse( "invalid@[1]" );
      assertIsFalse( "ä📧@-foo" );
      assertIsFalse( "ä📧@foo-" );
      assertIsFalse( "first(comment(inner@comment.com" );
      assertIsFalse( "Joe A Smith <email@example.com" );
      assertIsFalse( "Joe A Smith email@example.com" );
      assertIsFalse( "Joe A Smith <email@example.com->" );
      assertIsFalse( "Joe A Smith <email@-example.com->" );
      assertIsFalse( "Joe A Smith <email>" );
      assertIsTrue( "\"email\"@example.com" );
      assertIsTrue( "\"first@last\"@test.org" );
      assertIsTrue( "very.unusual.\"@\".unusual.com@example.com" );
      assertIsTrue( "\"first\"last\"@test.org" );
      assertIsTrue( "much.\"more\\ unusual\"@example.com" );
      assertIsTrue( "\"first\\last\"@test.org" );
      assertIsTrue( "\"Abc\\@def\"@test.org" );
      assertIsTrue( "\"Fred\\ Bloggs\"@test.org" );
      assertIsTrue( "\"Joe.\\Blow\"@test.org" );
      assertIsTrue( "\"Abc@def\"@test.org" );
      assertIsTrue( "\"Fred Bloggs\"@test.org" );
      assertIsTrue( "\"Doug \"Ace\" L.\"@test.org" );
      assertIsTrue( "\"[[ test ]]\"@test.org" );
      assertIsTrue( "\"test.test\"@test.org" );
      assertIsTrue( "test.\"test\"@test.org" );
      assertIsTrue( "\"test@test\"@test.org" );
      assertIsTrue( "\"test\test\"@test.org" );
      assertIsTrue( "\"first\".\"last\"@test.org" );
      assertIsTrue( "\"first\".middle.\"last\"@test.org" );
      assertIsTrue( "\"first\".last@test.org" );
      assertIsTrue( "first.\"last\"@test.org" );
      assertIsTrue( "\"first\".\"middle\".\"last\"@test.org" );
      assertIsTrue( "\"first.middle\".\"last\"@test.org" );
      assertIsTrue( "\"first.middle.last\"@test.org" );
      assertIsTrue( "\"first..last\"@test.org" );
      assertIsTrue( "\"Unicode NULL \"@char.com" );
      assertIsTrue( "\"test\\blah\"@test.org" );
      assertIsTrue( "\"test\blah\"@test.org" );
      assertIsTrue( "\"test\"blah\"@test.org" );
      assertIsTrue( "\"first\\\"last\"@test.org" );
      assertIsTrue( "\"Test \"Fail\" Ing\"@test.org" );
      assertIsTrue( "\"test blah\"@test.org" );
      assertIsTrue( "first.last@test.org" );
      assertIsTrue( "jdoe@machine(comment).example" );
      assertIsTrue( "first.\"\".last@test.org" );
      assertIsTrue( "\"\"@test.org" );
      assertIsTrue( "very.common@example.org" );
      assertIsTrue( "test/test@test.com" );
      assertIsTrue( "user-@example.org" );
      assertIsTrue( "firstname.lastname@example.com" );
      assertIsTrue( "email@subdomain.example.com" );
      assertIsTrue( "firstname+lastname@example.com" );
      assertIsTrue( "1234567890@example.com" );
      assertIsTrue( "email@example-one.com" );
      assertIsTrue( "_______@example.com" );
      assertIsTrue( "email@example.name" );
      assertIsTrue( "email@example.museum" );
      assertIsTrue( "email@example.co.jp" );
      assertIsTrue( "firstname-lastname@example.com" );
      assertIsTrue( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" );
      assertIsTrue( "first.last@123.test.org" );
      assertIsTrue( "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" );
      assertIsTrue( "1234567890123456789012345678901234567890123456789012345678901234@test.org" );
      assertIsTrue( "user+mailbox@test.org" );
      assertIsTrue( "customer/department=shipping@test.org" );
      assertIsTrue( "$A12345@test.org" );
      assertIsTrue( "!def!xyz%abc@test.org" );
      assertIsTrue( "_somename@test.org" );
      assertIsTrue( "first.last@[IPv6:::12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]" );
      assertIsTrue( "+@b.c" );
      assertIsTrue( "TEST@test.org" );
      assertIsTrue( "1234567890@test.org" );
      assertIsTrue( "test-test@test.org" );
      assertIsTrue( "t*est@test.org" );
      assertIsTrue( "+1~1+@test.org" );
      assertIsTrue( "{_test_}@test.org" );
      assertIsTrue( "valid@about.museum" );
      assertIsTrue( "a@bar" );
      assertIsTrue( "cal(foo\\@bar)@iamcal.com" );
      assertIsTrue( "(comment)test@test.org" );
      assertIsTrue( "(foo)cal(bar)@(baz)iamcal.com(quux)" );
      assertIsTrue( "cal(foo\\)bar)@iamcal.com" );
      assertIsTrue( "cal(woo(yay)hoopla)@iamcal.com" );
      assertIsTrue( "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org" );
      assertIsTrue( "pete(his account)@silly.test(his host)" );
      assertIsTrue( "first(abc\\(def)@test.org" );
      assertIsTrue( "a(a(b(c)d(e(f))g)h(i)j)@test.org" );
      assertIsTrue( "c@(Chris's host.)public.example" );
      assertIsTrue( "_Yosemite.Sam@test.org" );
      assertIsTrue( "~@test.org" );
      assertIsTrue( "Iinsist@(that comments are allowed)this.is.ok" );
      assertIsTrue( "test@Bücher.ch" );
      assertIsTrue( "あいうえお@example.com" );
      assertIsTrue( "Pelé@example.com" );
      assertIsTrue( "δοκιμή@παράδειγμα.δοκιμή" );
      assertIsTrue( "我買@屋企.香港" );
      assertIsTrue( "二ノ宮@黒川.日本" );
      assertIsTrue( "медведь@с-балалайкой.рф" );
      assertIsTrue( "संपर्क@डाटामेल.भारत" );
      assertIsTrue( "email@example.com (Joe Smith)" );
      assertIsTrue( "cal@iamcal(woo).(yay)com" );
      assertIsTrue( "first(abc.def).last@test.org" );
      assertIsTrue( "first(a\"bc.def).last@test.org" );
      assertIsTrue( "first.(\")middle.last(\")@test.org" );
      assertIsTrue( "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).com" );
      assertIsTrue( "first().last@test.org" );
      assertIsTrue( "mymail\\@hello@hotmail.com" );
      assertIsTrue( "Abc\\@def@test.org" );
      assertIsTrue( "Fred\\ Bloggs@test.org" );
      assertIsTrue( "Joe.\\\\Blow@test.org" );

      wl( "" );
      wlHeadline( "https://www.linuxjournal.com/article/9585" );
      wl( "" );

      assertIsTrue( "dclo@us.ibm.com" );
      assertIsTrue( "abc\\@def@example.com" );
      assertIsTrue( "abc\\\\@example.com" );
      assertIsTrue( "Fred\\ Bloggs@example.com" );
      assertIsTrue( "Joe.\\\\Blow@example.com" );
      assertIsTrue( "\"Abc@def\"@example.com" );
      assertIsTrue( "\"Fred Bloggs\"@example.com" );
      assertIsTrue( "customer/department=shipping@example.com" );
      assertIsTrue( "$A12345@example.com" );
      assertIsTrue( "!def!xyz%abc@example.com" );
      assertIsTrue( "_somename@example.com" );
      assertIsTrue( "user+mailbox@example.com" );
      assertIsTrue( "peter.piper@example.com" );
      assertIsTrue( "Doug\\ \\\"Ace\\\"\\ Lovell@example.com" );
      assertIsTrue( "\"Doug \\\"Ace\\\" L.\"@example.com" );
      assertIsTrue( "{^c\\@**Dog^}@cartoon.com" );

      assertIsFalse( "abc@def@example.com" );
      assertIsFalse( "abc\\\\@def@example.com" );
      assertIsFalse( "abc\\@example.com" );
      assertIsFalse( "@example.com" );
      assertIsFalse( "doug@" );
      assertIsFalse( "\"qu@example.com" );
      assertIsFalse( "ote\"@example.com" );
      assertIsFalse( ".dot@example.com" );
      assertIsFalse( "dot.@example.com" );
      assertIsFalse( "two..dot@example.com" );
      assertIsFalse( "\"Doug \"Ace\" L.\"@example.com" );
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ L\\.@example.com" );
      assertIsFalse( "hello world@example.com" );
      assertIsFalse( "gatsby@f.sc.ot.t.f.i.tzg.era.l.d." );

      wl( "" );
      wlHeadline( "https://github.com/dotnet/docs/issues/6620" );
      wl( "" );

      assertIsTrue( "jkt@gmail.com" );

      assertIsFalse( " jkt@gmail.com" );
      assertIsFalse( "jkt@ gmail.com" );
      assertIsFalse( "jkt@g mail.com" );
      assertIsFalse( "jkt @gmail.com" );
      assertIsFalse( "j kt@gmail.com" );

      wl( "" );
      wlHeadline( "https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/" );
      wl( "" );

      assertIsTrue( "jinujawad6s@gmail.com" );
      assertIsTrue( "drp@drp.cz" );
      assertIsTrue( "tvf@tvf.cz" );
      assertIsTrue( "info@ermaelan.com" );
      assertIsTrue( "begeddov@jfinity.com" );
      assertIsTrue( "vdv@dyomedea.com" );
      assertIsTrue( "me@aaronsw.com" );
      assertIsTrue( "aaron@theinfo.org" );
      assertIsTrue( "rss-dev@yahoogroups.com" );

      wl( "" );
      wlHeadline( "https://www.journaldev.com/638/java-email-validation-regex" );
      wl( "" );

      assertIsTrue( "journaldev@yahoo.com" );
      assertIsTrue( "journaldev-100@yahoo.com" );
      assertIsTrue( "journaldev.100@yahoo.com" );
      assertIsTrue( "journaldev111@journaldev.com" );
      assertIsTrue( "journaldev-100@journaldev.net" );
      assertIsTrue( "journaldev.100@journaldev.com.au" );
      assertIsTrue( "journaldev@1.com" );
      assertIsTrue( "journaldev@gmail.com.com" );
      assertIsTrue( "journaldev+100@gmail.com" );
      assertIsTrue( "journaldev-100@yahoo-test.com" );
      assertIsTrue( "journaldev_100@yahoo-test.ABC.CoM" );

      assertIsFalse( "journaldev" );
      assertIsFalse( "journaldev@.com.my" );
      assertIsFalse( "journaldev123@gmail.a" );
      assertIsFalse( "journaldev123@.com" );
      assertIsFalse( "journaldev123@.com.com" );
      assertIsFalse( ".journaldev@journaldev.com" );
      assertIsFalse( "journaldev()*@gmail.com" );
      assertIsFalse( "journaldev@%*.com" );
      assertIsFalse( "journaldev..2002@gmail.com" );
      assertIsFalse( "journaldev.@gmail.com" );
      assertIsFalse( "journaldev@journaldev@gmail.com" );
      assertIsFalse( "journaldev@gmail.com.1a" );

      wl( "" );
      wlHeadline( "https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java" );
      wl( "" );

      assertIsTrue( "me@example.com" );
      assertIsTrue( "a.nonymous@example.com" );
      assertIsTrue( "name+tag@example.com" );
      assertIsTrue( "!#$%&'+-/=.?^`{|}~@[1.0.0.127]" );
      assertIsTrue( "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]" );
      assertIsTrue( "me(this is a comment)@example.com" );
      assertIsTrue( "\"bob(hi)smith\"@test.com" );
      assertIsTrue( "me.example@com" );
      assertIsTrue( "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com" );
      assertIsTrue( "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net" );

      assertIsFalse( "NotAnEmail" );
      assertIsFalse( "me@" );
      assertIsFalse( "@example.com" );
      assertIsFalse( ".me@example.com" );
      assertIsFalse( "me@example..com" );
      assertIsFalse( "me\\@example.com" );
      assertIsFalse( "\"ßoµ\" <notifications@example.com>" );
      assertIsFalse( "[Kayaks] <kayaks@kayaks.org>" );
      assertIsFalse( "Kayaks.org <kayaks@kayaks.org>" );

      wl( "" );
      wlHeadline( "https://stackoverflow.com/questions/22993545/ruby-email-validation-with-regex?noredirect=1&lq=1" );
      wl( "" );
      
      assertIsTrue( "hello.me_1@email.com" );
      assertIsTrue( "something_valid@somewhere.tld" );
      assertIsTrue( "\"Look at all these spaces!\"@example.com" );
      assertIsTrue( "f.o.o.b.a.r@gmail.com" );
      assertIsFalse( "foo.bar#gmail.co.u" );
      assertIsFalse( "f...bar@gmail.com" );
      assertIsFalse( "get_at_m.e@gmail" );
      assertIsFalse( ".....@a...." );
      assertIsFalse( "david.gilbertson@SOME+THING-ODD!!.com" );
      assertIsFalse( "a.b@example,com" );
      assertIsFalse( "a.b@example,co.de" );

      wl( "" );
      wlHeadline( "unsupported" );
      wl( "" );

      assertIsTrue( "Loïc.Accentué@voilà.fr" );
      assertIsTrue( "Ärger.Öde@Übel.de" );
      assertIsTrue( "Smørrebrød@danmark.dk" );

      assertIsTrue( "ip.without.brackets@1.2.3.4" );
      assertIsTrue( "ip.without.brackets@1:2:3:4:5:6:7:8" );

      assertIsTrue( "(space after comment) john.smith@example.com" );

      assertIsTrue( "email.address.without@topleveldomain" );

      assertIsTrue( "EmailAddressWithout@PointSeperator" );

      wl( "" );
      wlHeadline( "Fillup" );
      wl( "" );

      while ( COUNT_ASSERT_IS_TRUE < COUNT_ASSERT_IS_FALSE )
      {
        assertIsTrue( "valid.email.from.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.tofalse.com" );

        KNZ_LOG_AUSGABE = false;

        if ( COUNT_ASSERT_IS_TRUE + 1 == COUNT_ASSERT_IS_FALSE )
        {
          wl( "          ..." );

          KNZ_LOG_AUSGABE = true;

          assertIsTrue( "valid.email.to.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.tofalse.com" );
        }
      }

      KNZ_LOG_AUSGABE = true;

      while ( COUNT_ASSERT_IS_FALSE < COUNT_ASSERT_IS_TRUE )
      {
        assertIsFalse( "false.email.from.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.totrue.[()]" );

        KNZ_LOG_AUSGABE = false;

        if ( COUNT_ASSERT_IS_FALSE + 1 == COUNT_ASSERT_IS_TRUE )
        {
          wl( "          ..." );

          KNZ_LOG_AUSGABE = true;

          assertIsFalse( "false.email.to.nr" + ( COUNT_ASSERT_IS_TRUE + 1 ) + "@fillup.totrue.[()]" );
        }
      }

      KNZ_LOG_AUSGABE = true;
    }
    catch ( Exception err_inst )
    {
      System.out.println( err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    DecimalFormatSymbols m_other_symbols = null;

    DecimalFormat m_number_format = null;

    m_other_symbols = new DecimalFormatSymbols( Locale.getDefault() );

    m_other_symbols.setDecimalSeparator( '.' );
    m_other_symbols.setGroupingSeparator( '.' );

    m_number_format = new DecimalFormat( "###.##", m_other_symbols );

    m_number_format.setMaximumFractionDigits( 3 );
    m_number_format.setMinimumFractionDigits( 3 );

    double email_ok_proz_true_korrekt_erkannt = ( 100.0 * T_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_TRUE;

    double email_ok_proz_false_korrekt_erkannt = ( 100.0 * F_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_FALSE;

    double email_ok_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    double email_false_proz_true_korrekt_erkannt = ( 100.0 * T_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_TRUE;

    double email_false_proz_false_korrekt_erkannt = ( 100.0 * F_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_FALSE;

    double email_false_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( T_RESULT_COUNT_EMAIL_IS_FALSE + F_RESULT_COUNT_EMAIL_IS_TRUE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    wl( "" );

    wlHeadline( "Statistik" );

    wl( "  ASSERT_IS_TRUE  " + getStringZahl( COUNT_ASSERT_IS_TRUE ) + "   KORREKT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_ok_proz_true_korrekt_erkannt ) + m_number_format.format( email_ok_proz_true_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_true_korrekt_erkannt ) + m_number_format.format( email_false_proz_true_korrekt_erkannt ) + " % = Error " + T_RESULT_COUNT_ERROR );
    wl( "  ASSERT_IS_FALSE " + getStringZahl( COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_ok_proz_false_korrekt_erkannt ) + m_number_format.format( email_ok_proz_false_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_false_proz_false_korrekt_erkannt ) + m_number_format.format( email_false_proz_false_korrekt_erkannt ) + " % = Error " + F_RESULT_COUNT_ERROR );
    wl( "" );
    wl( "  GESAMT          " + getStringZahl( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) + " = " + getEinzug( email_ok_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_ok_proz_korrekt_erkannt_insgesamt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE + T_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_false_proz_korrekt_erkannt_insgesamt ) + " % = Error " + ( T_RESULT_COUNT_ERROR + F_RESULT_COUNT_ERROR ) );
    wl( "" );

    /*
     * Variable fuer die Zeitdifferenz deklarieren
     */
    long zeit_differenz_millisekunden = 0;

    /*
     * Variable fuer die Endzeit der Funktion deklarieren und die aktuellen
     * System-Millisekunden speichern
     */
    long time_stamp_ende = System.currentTimeMillis();

    /*
     * Funktionslaufzeit in Millisekunden berechnen
     */
    zeit_differenz_millisekunden = time_stamp_ende - time_stamp_start;

    /*
     * Informationen ueber die Laufzeit in das Log schreiben
     */
    wl( "" );
    wl( "  Millisekunden " + FkString.right( "          " + zeit_differenz_millisekunden, 6 ) + " = " + ( (double) zeit_differenz_millisekunden / (double) ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) ) );
    wl( "" );

    if ( m_str_buffer != null )
    {
      String home_dir = "/home/ea234";

      home_dir = "c:/Daten/";

      schreibeDatei( home_dir + "/log_test_email_assert_true_false.txt", m_str_buffer.toString() );
    }

    m_str_buffer = null;

    System.exit( 0 );
  }

  private static String getStringZahl( int pZahl )
  {
    return ( pZahl < 10 ? "   " : ( pZahl < 100 ? "  " : ( pZahl < 1000 ? " " : "" ) ) ) + pZahl;
  }

  private static String getEinzug( double pZahl )
  {
    return ( pZahl < 10.0 ? "   " : ( pZahl < 100.0 ? "  " : ( pZahl < 1000.0 ? " " : "" ) ) );
  }

  private static void wlHeadline( String pString )
  {
    wl( "" );
    wl( "---- " + pString + " ----------------------------------------------------------------------------------------------------" );
    wl( "" );
  }

  /**
   * Ausgabe auf System.out
   * 
   * @param pString der auszugebende String
   */
  private static void wl( String pString )
  {
    if ( m_str_buffer == null )
    {
      m_str_buffer = new StringBuffer();
    }

    m_str_buffer.append( "\n" + "     * " + pString );

    System.out.println( pString );
  }

  private static String getID()
  {
    LAUFENDE_ZAHL++;

    if ( LAUFENDE_ZAHL == Integer.MAX_VALUE )
    {
      LAUFENDE_ZAHL = 0;
    }

    return ( LAUFENDE_ZAHL < 10 ? "    " : ( LAUFENDE_ZAHL < 100 ? "   " : ( LAUFENDE_ZAHL < 1000 ? "  " : ( LAUFENDE_ZAHL < 10000 ? " " : "" ) ) ) ) + LAUFENDE_ZAHL;
  }

  private static void assertIsTrue( String pString )
  {
    boolean knz_is_valid_email_adress = false;

    int return_code = 0;

    try
    {
      if ( TEST_B_KNZ_AKTIV )
      {
        //knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );

        //knz_is_valid_email_adress = EmailAddressValidator.isValid( pString, RFC_COMPLIANT );

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + FkString.getFeldLinksMin( "" + knz_is_valid_email_adress, 7 ) + " = " + ( knz_is_valid_email_adress ? " OK " : " #### FEHLER #### " ) );
        }
      }
      else
      {
        return_code = FkEMail.checkEMailAdresse( pString );

        knz_is_valid_email_adress = return_code < 10;

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress ? " OK " : " #### FEHLER ####    " + FkEMail.getFehlerText( return_code ) ) );
        }
      }

      if ( knz_is_valid_email_adress )
      {
        T_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        T_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      T_RESULT_COUNT_ERROR++;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsTrue " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = Fehler = " + err_inst.getMessage() );
      }
    }

    COUNT_ASSERT_IS_TRUE++;
  }

  private static void assertIsFalse( String pString )
  {
    boolean knz_is_valid_email_adress = false;

    boolean knz_soll_wert = false;

    int return_code = 0;

    try
    {
      if ( TEST_B_KNZ_AKTIV )
      {
        //knz_is_valid_email_adress = JMail.isValid( pString );

        //knz_is_valid_email_adress = EmailAddressValidator.isValid( pString, RFC_COMPLIANT );

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + FkString.getFeldLinksMin( "" + knz_is_valid_email_adress, 7 ) + " = " + ( knz_is_valid_email_adress == knz_soll_wert ? " OK " : " #### FEHLER #### " ) );
        }
      }
      else
      {
        return_code = FkEMail.checkEMailAdresse( pString );

        knz_is_valid_email_adress = return_code < 10;

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress == knz_soll_wert ? " OK " : " #### FEHLER #### " ) + "   " + FkEMail.getFehlerText( return_code ) );
        }
      }

      if ( knz_is_valid_email_adress )
      {
        F_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        F_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      F_RESULT_COUNT_ERROR++;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = Fehler = " + err_inst.getMessage() );
      }
    }

    COUNT_ASSERT_IS_FALSE++;
  }

  private static void generateTestCases()
  {
    generateTest( "_", "underscore" );
    generateTest( "&", "amp" );
    generateTest( "*", "asterisk" );
    generateTest( "$", "dollar" );
    generateTest( "=", "equality" );
    generateTest( "!", "exclamation" );
    generateTest( "?", "question" );
    generateTest( "`", "grave-accent" );
    generateTest( "#", "hash" );
    generateTest( "%", "percentage" );
    generateTest( "|", "pipe" );
    generateTest( "+", "plus" );
    generateTest( "{", "leftbracket" );
    generateTest( "}", "rightbracket" );
    generateTest( "(", "leftbracket" );
    generateTest( ")", "rightbracket" );
    generateTest( "[", "leftbracket" );
    generateTest( "]", "rightbracket" );
    generateTest( "<", "lower.than" );
    generateTest( ">", "greater.than" );
    generateTest( "~", "tilde" );
    generateTest( "^", "xor" );
    generateTest( ":", "colon" );
    generateTest( " ", "space" );
    generateTest( ".", "dot" );
    generateTest( ",", "comma" );
    generateTest( "@", "at" );
    generateTest( "§", "paragraph" );
    generateTest( "'", "double.quote" );
    generateTest( "\"", "double.quote" );
    
    generateTest( "()", "empty.bracket" );
    generateTest( "{}", "empty.bracket" );
    generateTest( "[]", "empty.bracket" );
    generateTest( "<>", "empty.bracket" );

    generateTest( ")(", "false.bracket1" );
    generateTest( "}{", "false.bracket2" );
    generateTest( "][", "false.bracket3" );
    generateTest( "><", "false.bracket4" );

    generateTest( "0", "number0" );
    generateTest( "9", "number9" );

    generateTest( "0123456789", "numbers" );
  }

  private static void generateTest( String pCharacter, String pName )
  {
    System.out.println( "\n      assertIsTrue( \"" + pCharacter + ".local.name.starts.with." + pName + "@domain.com\" );" );
    System.out.println( "      assertIsTrue( \"local.name.ends.with." + pName + pCharacter + "@domain.com\" );" );
    System.out.println( "      assertIsTrue( \"local.name.with." + pName + ".before" + pCharacter + ".point@domain.com\" );" );
    System.out.println( "      assertIsTrue( \"local.name.with." + pName + ".after." + pCharacter + "point@domain.com\" );" );
    System.out.println( "      assertIsTrue( \"local.name.with.double." + pName + "" + pCharacter + "" + pCharacter + "test@domain.com\" );" );
    System.out.println( "      assertIsTrue( \"(comment " + pCharacter + ") local.name.with.comment.with." + pName + "@domain.com\" );" );
    System.out.println( "      assertIsTrue( \"\\\"quote" + pCharacter + "\\\".local.name.with.qoute.with." + pName + "@domain.com\" );" );
    System.out.println( "      assertIsTrue( \"" + pCharacter + "@" + pName + ".domain.com\" );" );
    System.out.println( "      assertIsTrue( \"" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "@" + pName + ".domain.com\" );" );
    System.out.println( "      assertIsTrue( \"" + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "@" + pName + ".domain.com\" );" );
    System.out.println( "      assertIsTrue( \"name " + pCharacter + " <pointy.brackets1.with." + pName + "@domain.com>\" );" );
    System.out.println( "      assertIsTrue( \"<pointy.brackets2.with." + pName + "@domain.com> name " + pCharacter + "\" );" );
    
    //System.out.println( "\n      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
    //System.out.println( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
    //System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
    //System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
    //System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
    //System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );

    //System.out.println( "\n      assertIsFalse( \"ip.v4.with." + pName + "@[123.14" + pCharacter + "5.178.90]\" );" );
    //System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145" + pCharacter + ".178.90]\" );" );
    //System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145." + pCharacter + "178.90]\" );" );
    //System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90" + pCharacter + "]\" );" );
    //System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[" + pCharacter + "123.145.178.90]\" );" );

    //System.out.println( "\n      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:2" + pCharacter + "2:3:4:5:6:7]\" );" );
    //System.out.println( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22" + pCharacter + ":3:4:5:6:7]\" );" );
    //System.out.println( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:" + pCharacter + "3:4:5:6:7]\" );" );
    //System.out.println( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7" + pCharacter + "]\" );" );
  }

  /**
   * <pre>
   * Erstellt die Datei und schreibt dort den "pInhalt" rein.
   * 
   * Ist kein "pInhalt" null wird eine leere Datei erstellt.
   * </pre>
   * 
   * @param pDateiName der Dateiname
   * @param pInhalt    der zu schreibende Inhalt
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
}
