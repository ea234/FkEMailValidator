package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

class TestClassAssertTrueFalse
{
  private static final int TEST_B_TEST_NR = 5;

  private static int          LAUFENDE_ZAHL                 = 0;

  private static int          COUNT_ASSERT_IS_TRUE          = 0;

  private static int          COUNT_ASSERT_IS_FALSE         = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          T_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          F_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          F_RESULT_COUNT_ERROR          = 0;

  private static int          T_RESULT_COUNT_ERROR          = 0;

  private static int          BREITE_SPALTE_EMAIL_AUSGABE   = 70;

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
     *     1 - assertIsTrue  "name1.name2@domain1.tld"                                              =   0 =  OK 
     *     2 - assertIsTrue  "name1+name2@domain1.tld"                                              =   0 =  OK 
     *     3 - assertIsTrue  "name1-name2@domain1.tld"                                              =   0 =  OK 
     *     4 - assertIsTrue  "name1.name2@subdomain1.domain1.tld"                                   =   0 =  OK 
     *     5 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                 =   2 =  OK 
     *     6 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                    =   4 =  OK 
     *     7 - assertIsTrue  "\"quote1\".name1@domain1.tld"                                         =   1 =  OK 
     *     8 - assertIsTrue  "name1.\"quote1\"@domain1.tld"                                         =   1 =  OK 
     *     9 - assertIsTrue  "(comment1)name1@domain1.tld"                                          =   6 =  OK 
     *    10 - assertIsTrue  "name1(comment1)@domain1.tld"                                          =   6 =  OK 
     *    11 - assertIsTrue  "name1@(comment1)domain1.tld"                                          =   6 =  OK 
     *    12 - assertIsTrue  "name1@domain1.tld(comment1)"                                          =   6 =  OK 
     *    13 - assertIsTrue  "(comment1)\"quote1\".name1@domain1.tld"                               =   7 =  OK 
     *    14 - assertIsTrue  "(comment1)name1.\"quote1\"@domain1.tld"                               =   7 =  OK 
     *    15 - assertIsTrue  "name1.\"quote1\"(comment1)@domain1.tld"                               =   7 =  OK 
     *    16 - assertIsTrue  "\"quote1\".name1(comment1)@domain1.tld"                               =   7 =  OK 
     *    17 - assertIsTrue  "name1.\"quote1\"@(comment1)domain1.tld"                               =   7 =  OK 
     *    18 - assertIsTrue  "\"quote1\".name1@domain1.tld(comment1)"                               =   7 =  OK 
     *    19 - assertIsTrue  "<name1.name2@domain1.tld>"                                            =   0 =  OK 
     *    20 - assertIsTrue  "name3 <name1.name2@domain1.tld>"                                      =   0 =  OK 
     *    21 - assertIsTrue  "<name1.name2@domain1.tld> name3"                                      =   0 =  OK 
     *    22 - assertIsTrue  "\"name3 name4\" <name1.name2@domain1.tld>"                            =   0 =  OK 
     *    23 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"  =   6 =  OK 
     *    24 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *    25 - assertIsTrue  "A.\"B\"@C.DE"                                                         =   1 =  OK 
     *    26 - assertIsTrue  "A.B@[1.2.3.4]"                                                        =   2 =  OK 
     *    27 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                    =   3 =  OK 
     *    28 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                           =   4 =  OK 
     *    29 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                       =   5 =  OK 
     *    30 - assertIsTrue  "(A)B@C.DE"                                                            =   6 =  OK 
     *    31 - assertIsTrue  "A(B)@C.DE"                                                            =   6 =  OK 
     *    32 - assertIsTrue  "(A)\"B\"@C.DE"                                                        =   7 =  OK 
     *    33 - assertIsTrue  "\"A\"(B)@C.DE"                                                        =   7 =  OK 
     *    34 - assertIsTrue  "(A)B@[1.2.3.4]"                                                       =   2 =  OK 
     *    35 - assertIsTrue  "A(B)@[1.2.3.4]"                                                       =   2 =  OK 
     *    36 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                   =   8 =  OK 
     *    37 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                   =   8 =  OK 
     *    38 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *    39 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                          =   4 =  OK 
     *    40 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *    41 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                      =   9 =  OK 
     *    42 - assertIsTrue  "a.b.c.d@domain.com"                                                   =   0 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    43 - assertIsFalse null                                                                   =  10 =  OK    Laenge: Eingabe ist null
     *    44 - assertIsFalse ""                                                                     =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    45 - assertIsFalse "        "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    46 - assertIsFalse "1234567890"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    47 - assertIsFalse "onlytextnodotsnoat"                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    48 - assertIsFalse "email.with.no.at.character"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    49 - assertIsFalse "email.with.no.domain@"                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    50 - assertIsFalse "@.local.name.starts.with.at@domain.com"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    51 - assertIsFalse "@no.local.email.part.domain.com"                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    52 - assertIsFalse "local.name.with@at@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    53 - assertIsFalse "local.name.ends.with.at@@domain.com"                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    54 - assertIsFalse "local.name.with.at.before@.point@domain.com"                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    55 - assertIsFalse "local.name.with.at.after.@point@domain.com"                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    56 - assertIsFalse "local.name.with.double.at@@test@domain.com"                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    57 - assertIsFalse "(comment @) local.name.with.comment.with.at@domain.com"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    58 - assertIsTrue  "\"quote@\".local.name.with.qoute.with.at@domain.com"                  =   1 =  OK 
     *    59 - assertIsTrue  "qouted.\@.character@at.domain.com"                                    =   0 =  OK 
     *    60 - assertIsTrue  "\@@at.domain.com"                                                     =   0 =  OK 
     *    61 - assertIsFalse "@@at.domain.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    62 - assertIsFalse "@at.domain.com"                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    63 - assertIsFalse "@@@@@@@at.domain.com"                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    64 - assertIsTrue  "\@.\@.\@.\@.\@.\@@at.domain.com"                                      =   0 =  OK 
     *    65 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    66 - assertIsFalse "@.@.@.@.@.@@at.domain.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    67 - assertIsFalse "@.@.@."                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    68 - assertIsFalse "\@.\@@\@.\@"                                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    69 - assertIsFalse "@"                                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    70 - assertIsFalse "name @ <pointy.brackets1.with.at@domain.com>"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    71 - assertIsFalse "<pointy.brackets2.with.at@domain.com> name @"                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    72 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    73 - assertIsFalse "ABC.DEF.GHI.JKL"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    74 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *    75 - assertIsFalse "ABC.DEF @GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    76 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    77 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    78 - assertIsFalse "ABC.DEF@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    79 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    80 - assertIsFalse "ABC@DEF@GHI.JKL"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    81 - assertIsFalse "@%^%#$@#$@#.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    82 - assertIsFalse "email.domain.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    83 - assertIsFalse "email@domain@domain.com"                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    84 - assertIsFalse "first@last@test.org"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    85 - assertIsFalse "@test@a.com"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    86 - assertIsFalse "@\"someStringThatMightBe@email.com"                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    87 - assertIsFalse "test@@test.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    88 - assertIsFalse "ABCDEF@GHIJKL"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    89 - assertIsFalse "ABC.DEF@GHIJKL"                                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    90 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    91 - assertIsFalse "ABC.DEF@GHI.JKL."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    92 - assertIsFalse "ABC..DEF@GHI.JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    93 - assertIsFalse "ABC.DEF@GHI..JKL"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    94 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    95 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    96 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    97 - assertIsFalse "ABC.DEF@."                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    98 - assertIsFalse "john..doe@example.com"                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    99 - assertIsFalse "john.doe@example..com"                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   100 - assertIsTrue  "\"john..doe\"@example.com"                                            =   1 =  OK 
     *   101 - assertIsFalse "..........@domain."                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   102 - assertIsFalse "test.@test.com"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   103 - assertIsFalse ".test.@test.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   104 - assertIsFalse "asdf@asdf@asdf.com"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   105 - assertIsFalse "email@provider..com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *   106 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                  =   0 =  OK 
     *   107 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                     =   0 =  OK 
     *   108 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                     =   0 =  OK 
     *   109 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                      =   0 =  OK 
     *   110 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                      =   0 =  OK 
     *   111 - assertIsFalse "ABC.DEF@GHI.2KL"                                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   112 - assertIsFalse "ABC.DEF@GHI.JK-"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   113 - assertIsFalse "ABC.DEF@GHI.JK_"                                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   114 - assertIsFalse "ABC.DEF@-HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   115 - assertIsFalse "ABC.DEF@_HI.JKL"                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   116 - assertIsFalse "ABC DEF@GHI.DE"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   117 - assertIsFalse "ABC.DEF@GHI DE"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   118 - assertIsFalse "A . B & C . D"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   119 - assertIsFalse " A . B & C . D"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   120 - assertIsFalse "(?).[!]@{&}.<:>"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   121 - assertIsTrue  "&local&&name&with&$@amp.com"                                          =   0 =  OK 
     *   122 - assertIsTrue  "*local**name*with*@asterisk.com"                                      =   0 =  OK 
     *   123 - assertIsTrue  "$local$$name$with$@dollar.com"                                        =   0 =  OK 
     *   124 - assertIsTrue  "=local==name=with=@equality.com"                                      =   0 =  OK 
     *   125 - assertIsTrue  "!local!!name!with!@exclamation.com"                                   =   0 =  OK 
     *   126 - assertIsTrue  "`local``name`with`@grave-accent.com"                                  =   0 =  OK 
     *   127 - assertIsTrue  "#local##name#with#@hash.com"                                          =   0 =  OK 
     *   128 - assertIsTrue  "-local--name-with-@hypen.com"                                         =   0 =  OK 
     *   129 - assertIsTrue  "{local{name{{with{@leftbracket.com"                                   =   0 =  OK 
     *   130 - assertIsTrue  "%local%%name%with%@percentage.com"                                    =   0 =  OK 
     *   131 - assertIsTrue  "|local||name|with|@pipe.com"                                          =   0 =  OK 
     *   132 - assertIsTrue  "+local++name+with+@plus.com"                                          =   0 =  OK 
     *   133 - assertIsTrue  "?local??name?with?@question.com"                                      =   0 =  OK 
     *   134 - assertIsTrue  "}local}name}}with}@rightbracket.com"                                  =   0 =  OK 
     *   135 - assertIsTrue  "~local~~name~with~@tilde.com"                                         =   0 =  OK 
     *   136 - assertIsTrue  "^local^^name^with^@xor.com"                                           =   0 =  OK 
     *   137 - assertIsTrue  "_local__name_with_@underscore.com"                                    =   0 =  OK 
     *   138 - assertIsFalse ":local::name:with:@colon.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   139 - assertIsTrue  "&.local.name.starts.with.amp@domain.com"                              =   0 =  OK 
     *   140 - assertIsTrue  "local.name.ends.with.amp&@domain.com"                                 =   0 =  OK 
     *   141 - assertIsTrue  "local.name.with.amp.before&.point@domain.com"                         =   0 =  OK 
     *   142 - assertIsTrue  "local.name.with.amp.after.&point@domain.com"                          =   0 =  OK 
     *   143 - assertIsTrue  "local.name.with.double.amp&&test@domain.com"                          =   0 =  OK 
     *   144 - assertIsFalse "(comment &) local.name.with.comment.with.amp@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   145 - assertIsTrue  "\"quote&\".local.name.with.qoute.with.amp@domain.com"                 =   1 =  OK 
     *   146 - assertIsTrue  "&@amp.domain.com"                                                     =   0 =  OK 
     *   147 - assertIsTrue  "&&&&&&@amp.domain.com"                                                =   0 =  OK 
     *   148 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                           =   0 =  OK 
     *   149 - assertIsFalse "name & <pointy.brackets1.with.amp@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   150 - assertIsFalse "<pointy.brackets2.with.amp@domain.com> name &"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   151 - assertIsTrue  "*.local.name.starts.with.asterisk@domain.com"                         =   0 =  OK 
     *   152 - assertIsTrue  "local.name.ends.with.asterisk*@domain.com"                            =   0 =  OK 
     *   153 - assertIsTrue  "local.name.with.asterisk.before*.point@domain.com"                    =   0 =  OK 
     *   154 - assertIsTrue  "local.name.with.asterisk.after.*point@domain.com"                     =   0 =  OK 
     *   155 - assertIsTrue  "local.name.with.double.asterisk**test@domain.com"                     =   0 =  OK 
     *   156 - assertIsFalse "(comment *) local.name.with.comment.with.asterisk@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   157 - assertIsTrue  "\"quote*\".local.name.with.qoute.with.asterisk@domain.com"            =   1 =  OK 
     *   158 - assertIsTrue  "*@asterisk.domain.com"                                                =   0 =  OK 
     *   159 - assertIsTrue  "******@asterisk.domain.com"                                           =   0 =  OK 
     *   160 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                      =   0 =  OK 
     *   161 - assertIsFalse "name * <pointy.brackets1.with.asterisk@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   162 - assertIsFalse "<pointy.brackets2.with.asterisk@domain.com> name *"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   163 - assertIsTrue  "$.local.name.starts.with.dollar@domain.com"                           =   0 =  OK 
     *   164 - assertIsTrue  "local.name.ends.with.dollar$@domain.com"                              =   0 =  OK 
     *   165 - assertIsTrue  "local.name.with.dollar.before$.point@domain.com"                      =   0 =  OK 
     *   166 - assertIsTrue  "local.name.with.dollar.after.$point@domain.com"                       =   0 =  OK 
     *   167 - assertIsTrue  "local.name.with.double.dollar$$test@domain.com"                       =   0 =  OK 
     *   168 - assertIsFalse "(comment $) local.name.with.comment.with.dollar@domain.com"           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   169 - assertIsTrue  "\"quote$\".local.name.with.qoute.with.dollar@domain.com"              =   1 =  OK 
     *   170 - assertIsTrue  "$@dollar.domain.com"                                                  =   0 =  OK 
     *   171 - assertIsTrue  "$$$$$$@dollar.domain.com"                                             =   0 =  OK 
     *   172 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                        =   0 =  OK 
     *   173 - assertIsFalse "name $ <pointy.brackets1.with.dollar@domain.com>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   174 - assertIsFalse "<pointy.brackets2.with.dollar@domain.com> name $"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   175 - assertIsTrue  "=.local.name.starts.with.equality@domain.com"                         =   0 =  OK 
     *   176 - assertIsTrue  "local.name.ends.with.equality=@domain.com"                            =   0 =  OK 
     *   177 - assertIsTrue  "local.name.with.equality.before=.point@domain.com"                    =   0 =  OK 
     *   178 - assertIsTrue  "local.name.with.equality.after.=point@domain.com"                     =   0 =  OK 
     *   179 - assertIsTrue  "local.name.with.double.equality==test@domain.com"                     =   0 =  OK 
     *   180 - assertIsFalse "(comment =) local.name.with.comment.with.equality@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   181 - assertIsTrue  "\"quote=\".local.name.with.qoute.with.equality@domain.com"            =   1 =  OK 
     *   182 - assertIsTrue  "=@equality.domain.com"                                                =   0 =  OK 
     *   183 - assertIsTrue  "======@equality.domain.com"                                           =   0 =  OK 
     *   184 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                      =   0 =  OK 
     *   185 - assertIsFalse "name = <pointy.brackets1.with.equality@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   186 - assertIsFalse "<pointy.brackets2.with.equality@domain.com> name ="                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   187 - assertIsTrue  "!.local.name.starts.with.exclamation@domain.com"                      =   0 =  OK 
     *   188 - assertIsTrue  "local.name.ends.with.exclamation!@domain.com"                         =   0 =  OK 
     *   189 - assertIsTrue  "local.name.with.exclamation.before!.point@domain.com"                 =   0 =  OK 
     *   190 - assertIsTrue  "local.name.with.exclamation.after.!point@domain.com"                  =   0 =  OK 
     *   191 - assertIsTrue  "local.name.with.double.exclamation!!test@domain.com"                  =   0 =  OK 
     *   192 - assertIsFalse "(comment !) local.name.with.comment.with.exclamation@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   193 - assertIsTrue  "\"quote!\".local.name.with.qoute.with.exclamation@domain.com"         =   1 =  OK 
     *   194 - assertIsTrue  "!@exclamation.domain.com"                                             =   0 =  OK 
     *   195 - assertIsTrue  "!!!!!!@exclamation.domain.com"                                        =   0 =  OK 
     *   196 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                   =   0 =  OK 
     *   197 - assertIsFalse "name ! <pointy.brackets1.with.exclamation@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   198 - assertIsFalse "<pointy.brackets2.with.exclamation@domain.com> name !"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   199 - assertIsTrue  "`.local.name.starts.with.grave-accent@domain.com"                     =   0 =  OK 
     *   200 - assertIsTrue  "local.name.ends.with.grave-accent`@domain.com"                        =   0 =  OK 
     *   201 - assertIsTrue  "local.name.with.grave-accent.before`.point@domain.com"                =   0 =  OK 
     *   202 - assertIsTrue  "local.name.with.grave-accent.after.`point@domain.com"                 =   0 =  OK 
     *   203 - assertIsTrue  "local.name.with.double.grave-accent``test@domain.com"                 =   0 =  OK 
     *   204 - assertIsFalse "(comment `) local.name.with.comment.with.grave-accent@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   205 - assertIsTrue  "\"quote`\".local.name.with.qoute.with.grave-accent@domain.com"        =   1 =  OK 
     *   206 - assertIsTrue  "`@grave-accent.domain.com"                                            =   0 =  OK 
     *   207 - assertIsTrue  "``````@grave-accent.domain.com"                                       =   0 =  OK 
     *   208 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                  =   0 =  OK 
     *   209 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   210 - assertIsFalse "<pointy.brackets2.with.grave-accent@domain.com> name `"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   211 - assertIsTrue  "#.local.name.starts.with.hash@domain.com"                             =   0 =  OK 
     *   212 - assertIsTrue  "local.name.ends.with.hash#@domain.com"                                =   0 =  OK 
     *   213 - assertIsTrue  "local.name.with.hash.before#.point@domain.com"                        =   0 =  OK 
     *   214 - assertIsTrue  "local.name.with.hash.after.#point@domain.com"                         =   0 =  OK 
     *   215 - assertIsTrue  "local.name.with.double.hash##test@domain.com"                         =   0 =  OK 
     *   216 - assertIsFalse "(comment #) local.name.with.comment.with.hash@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   217 - assertIsTrue  "\"quote#\".local.name.with.qoute.with.hash@domain.com"                =   1 =  OK 
     *   218 - assertIsTrue  "#@hash.domain.com"                                                    =   0 =  OK 
     *   219 - assertIsTrue  "######@hash.domain.com"                                               =   0 =  OK 
     *   220 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                          =   0 =  OK 
     *   221 - assertIsFalse "name # <pointy.brackets1.with.hash@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   222 - assertIsFalse "<pointy.brackets2.with.hash@domain.com> name #"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   223 - assertIsTrue  "-.local.name.starts.with.hypen@domain.com"                            =   0 =  OK 
     *   224 - assertIsTrue  "local.name.ends.with.hypen-@domain.com"                               =   0 =  OK 
     *   225 - assertIsTrue  "local.name.with.hypen.before-.point@domain.com"                       =   0 =  OK 
     *   226 - assertIsTrue  "local.name.with.hypen.after.-point@domain.com"                        =   0 =  OK 
     *   227 - assertIsTrue  "local.name.with.double.hypen--test@domain.com"                        =   0 =  OK 
     *   228 - assertIsFalse "(comment -) local.name.with.comment.with.hypen@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   229 - assertIsTrue  "\"quote-\".local.name.with.qoute.with.hypen@domain.com"               =   1 =  OK 
     *   230 - assertIsTrue  "-@hypen.domain.com"                                                   =   0 =  OK 
     *   231 - assertIsTrue  "------@hypen.domain.com"                                              =   0 =  OK 
     *   232 - assertIsTrue  "-.-.-.-.-.-@hypen.domain.com"                                         =   0 =  OK 
     *   233 - assertIsFalse "name - <pointy.brackets1.with.hypen@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   234 - assertIsFalse "<pointy.brackets2.with.hypen@domain.com> name -"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   235 - assertIsTrue  "{.local.name.starts.with.leftbracket@domain.com"                      =   0 =  OK 
     *   236 - assertIsTrue  "local.name.ends.with.leftbracket{@domain.com"                         =   0 =  OK 
     *   237 - assertIsTrue  "local.name.with.leftbracket.before{.point@domain.com"                 =   0 =  OK 
     *   238 - assertIsTrue  "local.name.with.leftbracket.after.{point@domain.com"                  =   0 =  OK 
     *   239 - assertIsTrue  "local.name.with.double.leftbracket{{test@domain.com"                  =   0 =  OK 
     *   240 - assertIsFalse "(comment {) local.name.with.comment.with.leftbracket@domain.com"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   241 - assertIsTrue  "\"quote{\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   242 - assertIsTrue  "{@leftbracket.domain.com"                                             =   0 =  OK 
     *   243 - assertIsTrue  "{{{{{{@leftbracket.domain.com"                                        =   0 =  OK 
     *   244 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                   =   0 =  OK 
     *   245 - assertIsFalse "name { <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   246 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name {"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   247 - assertIsTrue  "%.local.name.starts.with.percentage@domain.com"                       =   0 =  OK 
     *   248 - assertIsTrue  "local.name.ends.with.percentage%@domain.com"                          =   0 =  OK 
     *   249 - assertIsTrue  "local.name.with.percentage.before%.point@domain.com"                  =   0 =  OK 
     *   250 - assertIsTrue  "local.name.with.percentage.after.%point@domain.com"                   =   0 =  OK 
     *   251 - assertIsTrue  "local.name.with.double.percentage%%test@domain.com"                   =   0 =  OK 
     *   252 - assertIsFalse "(comment %) local.name.with.comment.with.percentage@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   253 - assertIsTrue  "\"quote%\".local.name.with.qoute.with.percentage@domain.com"          =   1 =  OK 
     *   254 - assertIsTrue  "%@percentage.domain.com"                                              =   0 =  OK 
     *   255 - assertIsTrue  "%%%%%%@percentage.domain.com"                                         =   0 =  OK 
     *   256 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                    =   0 =  OK 
     *   257 - assertIsFalse "name % <pointy.brackets1.with.percentage@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   258 - assertIsFalse "<pointy.brackets2.with.percentage@domain.com> name %"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   259 - assertIsTrue  "|.local.name.starts.with.pipe@domain.com"                             =   0 =  OK 
     *   260 - assertIsTrue  "local.name.ends.with.pipe|@domain.com"                                =   0 =  OK 
     *   261 - assertIsTrue  "local.name.with.pipe.before|.point@domain.com"                        =   0 =  OK 
     *   262 - assertIsTrue  "local.name.with.pipe.after.|point@domain.com"                         =   0 =  OK 
     *   263 - assertIsTrue  "local.name.with.double.pipe||test@domain.com"                         =   0 =  OK 
     *   264 - assertIsFalse "(comment |) local.name.with.comment.with.pipe@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   265 - assertIsTrue  "\"quote|\".local.name.with.qoute.with.pipe@domain.com"                =   1 =  OK 
     *   266 - assertIsTrue  "|@pipe.domain.com"                                                    =   0 =  OK 
     *   267 - assertIsTrue  "||||||@pipe.domain.com"                                               =   0 =  OK 
     *   268 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                          =   0 =  OK 
     *   269 - assertIsFalse "name | <pointy.brackets1.with.pipe@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   270 - assertIsFalse "<pointy.brackets2.with.pipe@domain.com> name |"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   271 - assertIsTrue  "+.local.name.starts.with.plus@domain.com"                             =   0 =  OK 
     *   272 - assertIsTrue  "local.name.ends.with.plus+@domain.com"                                =   0 =  OK 
     *   273 - assertIsTrue  "local.name.with.plus.before+.point@domain.com"                        =   0 =  OK 
     *   274 - assertIsTrue  "local.name.with.plus.after.+point@domain.com"                         =   0 =  OK 
     *   275 - assertIsTrue  "local.name.with.double.plus++test@domain.com"                         =   0 =  OK 
     *   276 - assertIsFalse "(comment +) local.name.with.comment.with.plus@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   277 - assertIsTrue  "\"quote+\".local.name.with.qoute.with.plus@domain.com"                =   1 =  OK 
     *   278 - assertIsTrue  "+@plus.domain.com"                                                    =   0 =  OK 
     *   279 - assertIsTrue  "++++++@plus.domain.com"                                               =   0 =  OK 
     *   280 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                          =   0 =  OK 
     *   281 - assertIsFalse "name + <pointy.brackets1.with.plus@domain.com>"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   282 - assertIsFalse "<pointy.brackets2.with.plus@domain.com> name +"                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   283 - assertIsTrue  "?.local.name.starts.with.question@domain.com"                         =   0 =  OK 
     *   284 - assertIsTrue  "local.name.ends.with.question?@domain.com"                            =   0 =  OK 
     *   285 - assertIsTrue  "local.name.with.question.before?.point@domain.com"                    =   0 =  OK 
     *   286 - assertIsTrue  "local.name.with.question.after.?point@domain.com"                     =   0 =  OK 
     *   287 - assertIsTrue  "local.name.with.double.question??test@domain.com"                     =   0 =  OK 
     *   288 - assertIsFalse "(comment ?) local.name.with.comment.with.question@domain.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   289 - assertIsTrue  "\"quote?\".local.name.with.qoute.with.question@domain.com"            =   1 =  OK 
     *   290 - assertIsTrue  "?@question.domain.com"                                                =   0 =  OK 
     *   291 - assertIsTrue  "??????@question.domain.com"                                           =   0 =  OK 
     *   292 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                      =   0 =  OK 
     *   293 - assertIsFalse "name ? <pointy.brackets1.with.question@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   294 - assertIsFalse "<pointy.brackets2.with.question@domain.com> name ?"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   295 - assertIsTrue  "}.local.name.starts.with.rightbracket@domain.com"                     =   0 =  OK 
     *   296 - assertIsTrue  "local.name.ends.with.rightbracket}@domain.com"                        =   0 =  OK 
     *   297 - assertIsTrue  "local.name.with.rightbracket.before}.point@domain.com"                =   0 =  OK 
     *   298 - assertIsTrue  "local.name.with.rightbracket.after.}point@domain.com"                 =   0 =  OK 
     *   299 - assertIsTrue  "local.name.with.double.rightbracket}}test@domain.com"                 =   0 =  OK 
     *   300 - assertIsFalse "(comment }) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   301 - assertIsTrue  "\"quote}\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   302 - assertIsTrue  "}@rightbracket.domain.com"                                            =   0 =  OK 
     *   303 - assertIsTrue  "}}}}}}@rightbracket.domain.com"                                       =   0 =  OK 
     *   304 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                  =   0 =  OK 
     *   305 - assertIsFalse "name } <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   306 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name }"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   307 - assertIsTrue  "~.local.name.starts.with.tilde@domain.com"                            =   0 =  OK 
     *   308 - assertIsTrue  "local.name.ends.with.tilde~@domain.com"                               =   0 =  OK 
     *   309 - assertIsTrue  "local.name.with.tilde.before~.point@domain.com"                       =   0 =  OK 
     *   310 - assertIsTrue  "local.name.with.tilde.after.~point@domain.com"                        =   0 =  OK 
     *   311 - assertIsTrue  "local.name.with.double.tilde~~test@domain.com"                        =   0 =  OK 
     *   312 - assertIsFalse "(comment ~) local.name.with.comment.with.tilde@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   313 - assertIsTrue  "\"quote~\".local.name.with.qoute.with.tilde@domain.com"               =   1 =  OK 
     *   314 - assertIsTrue  "~@tilde.domain.com"                                                   =   0 =  OK 
     *   315 - assertIsTrue  "~~~~~~@tilde.domain.com"                                              =   0 =  OK 
     *   316 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                         =   0 =  OK 
     *   317 - assertIsFalse "name ~ <pointy.brackets1.with.tilde@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   318 - assertIsFalse "<pointy.brackets2.with.tilde@domain.com> name ~"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   319 - assertIsTrue  "^.local.name.starts.with.xor@domain.com"                              =   0 =  OK 
     *   320 - assertIsTrue  "local.name.ends.with.xor^@domain.com"                                 =   0 =  OK 
     *   321 - assertIsTrue  "local.name.with.xor.before^.point@domain.com"                         =   0 =  OK 
     *   322 - assertIsTrue  "local.name.with.xor.after.^point@domain.com"                          =   0 =  OK 
     *   323 - assertIsTrue  "local.name.with.double.xor^^test@domain.com"                          =   0 =  OK 
     *   324 - assertIsFalse "(comment ^) local.name.with.comment.with.xor@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   325 - assertIsTrue  "\"quote^\".local.name.with.qoute.with.xor@domain.com"                 =   1 =  OK 
     *   326 - assertIsTrue  "^@xor.domain.com"                                                     =   0 =  OK 
     *   327 - assertIsTrue  "^^^^^^@xor.domain.com"                                                =   0 =  OK 
     *   328 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                           =   0 =  OK 
     *   329 - assertIsFalse "name ^ <pointy.brackets1.with.xor@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   330 - assertIsFalse "<pointy.brackets2.with.xor@domain.com> name ^"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   331 - assertIsTrue  "_.local.name.starts.with.underscore@domain.com"                       =   0 =  OK 
     *   332 - assertIsTrue  "local.name.ends.with.underscore_@domain.com"                          =   0 =  OK 
     *   333 - assertIsTrue  "local.name.with.underscore.before_.point@domain.com"                  =   0 =  OK 
     *   334 - assertIsTrue  "local.name.with.underscore.after._point@domain.com"                   =   0 =  OK 
     *   335 - assertIsTrue  "local.name.with.double.underscore__test@domain.com"                   =   0 =  OK 
     *   336 - assertIsFalse "(comment _) local.name.with.comment.with.underscore@domain.com"       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   337 - assertIsTrue  "\"quote_\".local.name.with.qoute.with.underscore@domain.com"          =   1 =  OK 
     *   338 - assertIsTrue  "_@underscore.domain.com"                                              =   0 =  OK 
     *   339 - assertIsTrue  "______@underscore.domain.com"                                         =   0 =  OK 
     *   340 - assertIsTrue  "_._._._._._@underscore.domain.com"                                    =   0 =  OK 
     *   341 - assertIsFalse "name _ <pointy.brackets1.with.underscore@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   342 - assertIsFalse "<pointy.brackets2.with.underscore@domain.com> name _"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   343 - assertIsFalse ":.local.name.starts.with.colon@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   344 - assertIsFalse "local.name.ends.with.colon:@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   345 - assertIsFalse "local.name.with.colon.before:.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   346 - assertIsFalse "local.name.with.colon.after.:point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   347 - assertIsFalse "local.name.with.double.colon::test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   348 - assertIsFalse "(comment :) local.name.with.comment.with.colon@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   349 - assertIsTrue  "\"quote:\".local.name.with.qoute.with.colon@domain.com"               =   1 =  OK 
     *   350 - assertIsFalse ":@colon.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   351 - assertIsFalse "::::::@colon.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   352 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   353 - assertIsFalse "name : <pointy.brackets1.with.colon@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   354 - assertIsFalse "<pointy.brackets2.with.colon@domain.com> name :"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   355 - assertIsFalse "(.local.name.starts.with.leftbracket@domain.com"                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   356 - assertIsFalse "local.name.ends.with.leftbracket(@domain.com"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   357 - assertIsFalse "local.name.with.leftbracket.before(.point@domain.com"                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   358 - assertIsFalse "local.name.with.leftbracket.after.(point@domain.com"                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   359 - assertIsFalse "local.name.with.double.leftbracket((test@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   360 - assertIsFalse "(comment () local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   361 - assertIsTrue  "\"quote(\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   362 - assertIsFalse "(@leftbracket.domain.com"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   363 - assertIsFalse "((((((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   364 - assertIsFalse "(()(((@leftbracket.domain.com"                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   365 - assertIsFalse "((<)>(((@leftbracket.domain.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   366 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   367 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket@domain.com>"                =   0 =  OK 
     *   368 - assertIsTrue  "<pointy.brackets2.with.leftbracket@domain.com> name ("                =   0 =  OK 
     *   369 - assertIsFalse "\.local.name.starts.with.slash@domain.com"                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   370 - assertIsFalse "local.name.ends.with.slash\@domain.com"                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   371 - assertIsFalse "local.name.with.slash.before\.point@domain.com"                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   372 - assertIsFalse "local.name.with.slash.after.\point@domain.com"                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   373 - assertIsTrue  "local.name.with.double.slash\\test@domain.com"                        =   0 =  OK 
     *   374 - assertIsFalse "(comment \) local.name.with.comment.with.slash@domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   375 - assertIsFalse "\"quote\\".local.name.with.qoute.with.slash@domain.com"               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   376 - assertIsFalse "\@slash.domain.com"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   377 - assertIsTrue  "\\\\\\@slash.domain.com"                                              =   0 =  OK 
     *   378 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   379 - assertIsFalse "name \ <pointy.brackets1.with.slash@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   380 - assertIsFalse "<pointy.brackets2.with.slash@domain.com> name \"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   381 - assertIsFalse ").local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   382 - assertIsFalse "local.name.ends.with.rightbracket)@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   383 - assertIsFalse "local.name.with.rightbracket.before).point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   384 - assertIsFalse "local.name.with.rightbracket.after.)point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   385 - assertIsFalse "local.name.with.double.rightbracket))test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   386 - assertIsFalse "(comment )) local.name.with.comment.with.rightbracket@domain.com"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   387 - assertIsTrue  "\"quote)\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   388 - assertIsFalse ")@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   389 - assertIsFalse "))))))@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   390 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   391 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket@domain.com>"               =   0 =  OK 
     *   392 - assertIsTrue  "<pointy.brackets2.with.rightbracket@domain.com> name )"               =   0 =  OK 
     *   393 - assertIsFalse "[.local.name.starts.with.leftbracket@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   394 - assertIsFalse "local.name.ends.with.leftbracket[@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   395 - assertIsFalse "local.name.with.leftbracket.before[.point@domain.com"                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   396 - assertIsFalse "local.name.with.leftbracket.after.[point@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   397 - assertIsFalse "local.name.with.double.leftbracket[[test@domain.com"                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   398 - assertIsFalse "(comment [) local.name.with.comment.with.leftbracket@domain.com"      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   399 - assertIsTrue  "\"quote[\".local.name.with.qoute.with.leftbracket@domain.com"         =   1 =  OK 
     *   400 - assertIsFalse "[@leftbracket.domain.com"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   401 - assertIsFalse "[[[[[[@leftbracket.domain.com"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   402 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   403 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   404 - assertIsFalse "<pointy.brackets2.with.leftbracket@domain.com> name ["                =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   405 - assertIsFalse "].local.name.starts.with.rightbracket@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   406 - assertIsFalse "local.name.ends.with.rightbracket]@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   407 - assertIsFalse "local.name.with.rightbracket.before].point@domain.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   408 - assertIsFalse "local.name.with.rightbracket.after.]point@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   409 - assertIsFalse "local.name.with.double.rightbracket]]test@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   410 - assertIsFalse "(comment ]) local.name.with.comment.with.rightbracket@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   411 - assertIsTrue  "\"quote]\".local.name.with.qoute.with.rightbracket@domain.com"        =   1 =  OK 
     *   412 - assertIsFalse "]@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   413 - assertIsFalse "]]]]]]@rightbracket.domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   414 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   415 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   416 - assertIsFalse "<pointy.brackets2.with.rightbracket@domain.com> name ]"               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   417 - assertIsFalse " .local.name.starts.with.space@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   418 - assertIsFalse "local.name.ends.with.space @domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   419 - assertIsFalse "local.name.with.space.before .point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   420 - assertIsFalse "local.name.with.space.after. point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   421 - assertIsFalse "local.name.with.double.space  test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   422 - assertIsFalse "(comment  ) local.name.with.comment.with.space@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   423 - assertIsTrue  "\"quote \".local.name.with.qoute.with.space@domain.com"               =   1 =  OK 
     *   424 - assertIsFalse " @space.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   425 - assertIsFalse "      @space.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   426 - assertIsFalse " . . . . . @space.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   427 - assertIsTrue  "name   <pointy.brackets1.with.space@domain.com>"                      =   0 =  OK 
     *   428 - assertIsTrue  "<pointy.brackets2.with.space@domain.com> name  "                      =   0 =  OK 
     *   429 - assertIsFalse "..local.name.starts.with.dot@domain.com"                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   430 - assertIsFalse "local.name.ends.with.dot.@domain.com"                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   431 - assertIsFalse "local.name.with.dot.before..point@domain.com"                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   432 - assertIsFalse "local.name.with.dot.after..point@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   433 - assertIsFalse "local.name.with.double.dot..test@domain.com"                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   434 - assertIsFalse "(comment .) local.name.with.comment.with.dot@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   435 - assertIsTrue  "\"quote.\".local.name.with.qoute.with.dot@domain.com"                 =   1 =  OK 
     *   436 - assertIsFalse ".@dot.domain.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   437 - assertIsFalse "......@dot.domain.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   438 - assertIsFalse "...........@dot.domain.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   439 - assertIsFalse "name . <pointy.brackets1.with.dot@domain.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   440 - assertIsFalse "<pointy.brackets2.with.dot@domain.com> name ."                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   441 - assertIsFalse "().local.name.starts.with.empty.bracket@domain.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   442 - assertIsTrue  "local.name.ends.with.empty.bracket()@domain.com"                      =   6 =  OK 
     *   443 - assertIsFalse "local.name.with.empty.bracket.before().point@domain.com"              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   444 - assertIsFalse "local.name.with.empty.bracket.after.()point@domain.com"               = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   445 - assertIsFalse "local.name.with.double.empty.bracket()()test@domain.com"              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   446 - assertIsFalse "(comment ()) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   447 - assertIsTrue  "\"quote()\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   448 - assertIsFalse "()@empty.bracket.domain.com"                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   449 - assertIsFalse "()()()()()()@empty.bracket.domain.com"                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   450 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   451 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket@domain.com>"             =   0 =  OK 
     *   452 - assertIsTrue  "<pointy.brackets2.with.empty.bracket@domain.com> name ()"             =   0 =  OK 
     *   453 - assertIsTrue  "{}.local.name.starts.with.empty.bracket@domain.com"                   =   0 =  OK 
     *   454 - assertIsTrue  "local.name.ends.with.empty.bracket{}@domain.com"                      =   0 =  OK 
     *   455 - assertIsTrue  "local.name.with.empty.bracket.before{}.point@domain.com"              =   0 =  OK 
     *   456 - assertIsTrue  "local.name.with.empty.bracket.after.{}point@domain.com"               =   0 =  OK 
     *   457 - assertIsTrue  "local.name.with.double.empty.bracket{}{}test@domain.com"              =   0 =  OK 
     *   458 - assertIsFalse "(comment {}) local.name.with.comment.with.empty.bracket@domain.com"   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   459 - assertIsTrue  "\"quote{}\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   460 - assertIsTrue  "{}@empty.bracket.domain.com"                                          =   0 =  OK 
     *   461 - assertIsTrue  "{}{}{}{}{}{}@empty.bracket.domain.com"                                =   0 =  OK 
     *   462 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                           =   0 =  OK 
     *   463 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   464 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name {}"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   465 - assertIsFalse "[].local.name.starts.with.empty.bracket@domain.com"                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   466 - assertIsFalse "local.name.ends.with.empty.bracket[]@domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   467 - assertIsFalse "local.name.with.empty.bracket.before[].point@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   468 - assertIsFalse "local.name.with.empty.bracket.after.[]point@domain.com"               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   469 - assertIsFalse "local.name.with.double.empty.bracket[][]test@domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   470 - assertIsFalse "(comment []) local.name.with.comment.with.empty.bracket@domain.com"   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   471 - assertIsTrue  "\"quote[]\".local.name.with.qoute.with.empty.bracket@domain.com"      =   1 =  OK 
     *   472 - assertIsFalse "[]@empty.bracket.domain.com"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   473 - assertIsFalse "[][][][][][]@empty.bracket.domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   474 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   475 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   476 - assertIsFalse "<pointy.brackets2.with.empty.bracket@domain.com> name []"             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   477 - assertIsTrue  "999.local.name.starts.with.byte.overflow@domain.com"                  =   0 =  OK 
     *   478 - assertIsTrue  "local.name.ends.with.byte.overflow999@domain.com"                     =   0 =  OK 
     *   479 - assertIsTrue  "local.name.with.byte.overflow.before999.point@domain.com"             =   0 =  OK 
     *   480 - assertIsTrue  "local.name.with.byte.overflow.after.999point@domain.com"              =   0 =  OK 
     *   481 - assertIsTrue  "local.name.with.double.byte.overflow999999test@domain.com"            =   0 =  OK 
     *   482 - assertIsTrue  "(comment 999) local.name.with.comment.with.byte.overflow@domain.com"  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   483 - assertIsTrue  "\"quote999\".local.name.with.qoute.with.byte.overflow@domain.com"     =   1 =  OK 
     *   484 - assertIsTrue  "999@byte.overflow.domain.com"                                         =   0 =  OK 
     *   485 - assertIsTrue  "999999999999999999@byte.overflow.domain.com"                          =   0 =  OK 
     *   486 - assertIsTrue  "999.999.999.999.999.999@byte.overflow.domain.com"                     =   0 =  OK 
     *   487 - assertIsTrue  "name 999 <pointy.brackets1.with.byte.overflow@domain.com>"            =   0 =  OK 
     *   488 - assertIsTrue  "<pointy.brackets2.with.byte.overflow@domain.com> name 999"            =   0 =  OK 
     *   489 - assertIsTrue  "\"str\".local.name.starts.with.string@domain.com"                     =   1 =  OK 
     *   490 - assertIsFalse "local.name.ends.with.string\"str\"@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   491 - assertIsFalse "local.name.with.string.before\"str\".point@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   492 - assertIsFalse "local.name.with.string.after.\"str\"point@domain.com"                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   493 - assertIsFalse "local.name.with.double.string\"str\"\"str\"test@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   494 - assertIsFalse "(comment \"str\") local.name.with.comment.with.string@domain.com"     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   495 - assertIsFalse "\"quote\"str\"\".local.name.with.qoute.with.string@domain.com"        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   496 - assertIsTrue  "\"str\"@string.domain.com"                                            =   1 =  OK 
     *   497 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com"         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   498 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"    =   1 =  OK 
     *   499 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string@domain.com>"               =   0 =  OK 
     *   500 - assertIsTrue  "<pointy.brackets2.with.string@domain.com> name \"str\""               =   0 =  OK 
     *   501 - assertIsFalse "(comment).local.name.starts.with.comment@domain.com"                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   502 - assertIsTrue  "local.name.ends.with.comment(comment)@domain.com"                     =   6 =  OK 
     *   503 - assertIsFalse "local.name.with.comment.before(comment).point@domain.com"             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   504 - assertIsFalse "local.name.with.comment.after.(comment)point@domain.com"              = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   505 - assertIsFalse "local.name.with.double.comment(comment)(comment)test@domain.com"      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   506 - assertIsFalse "(comment (comment)) local.name.with.comment.with.comment@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   507 - assertIsTrue  "\"quote(comment)\".local.name.with.qoute.with.comment@domain.com"     =   1 =  OK 
     *   508 - assertIsFalse "(comment)@comment.domain.com"                                         =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   509 - assertIsFalse "(comment)(comment)(comment)(comment)(comment)(comment)@comment.domain.com" =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   510 - assertIsFalse "(comment).(comment).(comment).(comment).(comment).(comment)@comment.domain.com" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   511 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment@domain.com>"            =   0 =  OK 
     *   512 - assertIsTrue  "<pointy.brackets2.with.comment@domain.com> name (comment)"            =   0 =  OK 
     *   513 - assertIsTrue  "domain.part@with0number0.com"                                         =   0 =  OK 
     *   514 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                        =   0 =  OK 
     *   515 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                         =   0 =  OK 
     *   516 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                         =   0 =  OK 
     *   517 - assertIsTrue  "domain.part@with.number0.before0.point.com"                           =   0 =  OK 
     *   518 - assertIsTrue  "domain.part@with.number0.after.0point.com"                            =   0 =  OK 
     *   519 - assertIsTrue  "domain.part@with9number9.com"                                         =   0 =  OK 
     *   520 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                        =   0 =  OK 
     *   521 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                         =   0 =  OK 
     *   522 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                         =   0 =  OK 
     *   523 - assertIsTrue  "domain.part@with.number9.before9.point.com"                           =   0 =  OK 
     *   524 - assertIsTrue  "domain.part@with.number9.after.9point.com"                            =   0 =  OK 
     *   525 - assertIsTrue  "domain.part@with0123456789numbers.com"                                =   0 =  OK 
     *   526 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"               =   0 =  OK 
     *   527 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                =   0 =  OK 
     *   528 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                =   0 =  OK 
     *   529 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                  =   0 =  OK 
     *   530 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                   =   0 =  OK 
     *   531 - assertIsTrue  "domain.part@with-hyphen.com"                                          =   0 =  OK 
     *   532 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   533 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   534 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   535 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   536 - assertIsFalse "domain.part@with.-hyphen.after.point.com"                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   537 - assertIsTrue  "domain.part@with_underscore.com"                                      =   0 =  OK 
     *   538 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   539 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   540 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   541 - assertIsFalse "domain.part@with.underscore.before_.point.com"                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   542 - assertIsFalse "domain.part@with.underscore.after._point.com"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   543 - assertIsFalse "domain.part@with&amp.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   544 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   545 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   546 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   547 - assertIsFalse "domain.part@with.amp.before&.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   548 - assertIsFalse "domain.part@with.amp.after.&point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   549 - assertIsFalse "domain.part@with*asterisk.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   550 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   551 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   552 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   553 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   554 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   555 - assertIsFalse "domain.part@with$dollar.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   556 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   557 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   558 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   559 - assertIsFalse "domain.part@with.dollar.before$.point.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   560 - assertIsFalse "domain.part@with.dollar.after.$point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   561 - assertIsFalse "domain.part@with=equality.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   562 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   563 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   564 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   565 - assertIsFalse "domain.part@with.equality.before=.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   566 - assertIsFalse "domain.part@with.equality.after.=point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   567 - assertIsFalse "domain.part@with!exclamation.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   568 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   569 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   570 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   571 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   572 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   573 - assertIsFalse "domain.part@with?question.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   574 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   575 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   576 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   577 - assertIsFalse "domain.part@with.question.before?.point.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   578 - assertIsFalse "domain.part@with.question.after.?point.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   579 - assertIsFalse "domain.part@with`grave-accent.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   580 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   581 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   582 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   583 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   584 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   585 - assertIsFalse "domain.part@with#hash.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   586 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   587 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   588 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   589 - assertIsFalse "domain.part@with.hash.before#.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   590 - assertIsFalse "domain.part@with.hash.after.#point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   591 - assertIsFalse "domain.part@with%percentage.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   592 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   593 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   594 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   595 - assertIsFalse "domain.part@with.percentage.before%.point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   596 - assertIsFalse "domain.part@with.percentage.after.%point.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   597 - assertIsFalse "domain.part@with|pipe.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   598 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   599 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   600 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   601 - assertIsFalse "domain.part@with.pipe.before|.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   602 - assertIsFalse "domain.part@with.pipe.after.|point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   603 - assertIsFalse "domain.part@with+plus.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   604 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   605 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   606 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   607 - assertIsFalse "domain.part@with.plus.before+.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   608 - assertIsFalse "domain.part@with.plus.after.+point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   609 - assertIsFalse "domain.part@with{leftbracket.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   610 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   611 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   612 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   613 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   614 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   615 - assertIsFalse "domain.part@with}rightbracket.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   616 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   617 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   618 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   619 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   620 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   621 - assertIsFalse "domain.part@with(leftbracket.com"                                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   622 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   623 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   624 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                     =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   625 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   626 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   627 - assertIsFalse "domain.part@with)rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   628 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   629 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   630 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   631 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   632 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   633 - assertIsFalse "domain.part@with[leftbracket.com"                                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   634 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   635 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   636 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                     =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   637 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                       =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   638 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   639 - assertIsFalse "domain.part@with]rightbracket.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   640 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   641 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   642 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   643 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   644 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   645 - assertIsFalse "domain.part@with~tilde.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   646 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   647 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   648 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   649 - assertIsFalse "domain.part@with.tilde.before~.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   650 - assertIsFalse "domain.part@with.tilde.after.~point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   651 - assertIsFalse "domain.part@with^xor.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   652 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   653 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   654 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   655 - assertIsFalse "domain.part@with.xor.before^.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   656 - assertIsFalse "domain.part@with.xor.after.^point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   657 - assertIsFalse "domain.part@with:colon.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   658 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   659 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   660 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   661 - assertIsFalse "domain.part@with.colon.before:.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   662 - assertIsFalse "domain.part@with.colon.after.:point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   663 - assertIsFalse "domain.part@with space.com"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   664 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   665 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   666 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   667 - assertIsFalse "domain.part@with.space.before .point.com"                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   668 - assertIsFalse "domain.part@with.space.after. point.com"                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   669 - assertIsTrue  "domain.part@with999byte.overflow.com"                                 =   0 =  OK 
     *   670 - assertIsTrue  "domain.part@999with.byte.overflow.at.domain.start.com"                =   0 =  OK 
     *   671 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end1999.com"                 =   0 =  OK 
     *   672 - assertIsTrue  "domain.part@with.byte.overflow.at.domain.end2.com999"                 =   0 =  OK 
     *   673 - assertIsTrue  "domain.part@with.byte.overflow.before999.point.com"                   =   0 =  OK 
     *   674 - assertIsTrue  "domain.part@with.byte.overflow.after.999point.com"                    =   0 =  OK 
     *   675 - assertIsTrue  "domain.part@withxyzno.hex.number.com"                                 =   0 =  OK 
     *   676 - assertIsTrue  "domain.part@xyzwith.no.hex.number.at.domain.start.com"                =   0 =  OK 
     *   677 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end1xyz.com"                 =   0 =  OK 
     *   678 - assertIsTrue  "domain.part@with.no.hex.number.at.domain.end2.comxyz"                 =   0 =  OK 
     *   679 - assertIsTrue  "domain.part@with.no.hex.number.beforexyz.point.com"                   =   0 =  OK 
     *   680 - assertIsTrue  "domain.part@with.no.hex.number.after.xyzpoint.com"                    =   0 =  OK 
     *   681 - assertIsFalse "domain.part@with\"str\"string.com"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   682 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   683 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   684 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   685 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   686 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   687 - assertIsFalse "domain.part@with(comment)comment.com"                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   688 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                =   6 =  OK 
     *   689 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   690 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                 =   6 =  OK 
     *   691 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   692 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                    = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   693 - assertIsFalse ",.local.name.starts.with.comma@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   694 - assertIsFalse "local.name.ends.with.comma,@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   695 - assertIsFalse "local.name.with.comma.before,.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   696 - assertIsFalse "local.name.with.comma.after.,point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   697 - assertIsFalse "local.name.with.double.comma,,test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   698 - assertIsFalse "(comment ,) local.name.with.comment.with.comma@domain.com"            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   699 - assertIsTrue  "\"quote,\".local.name.with.qoute.with.comma@domain.com"               =   1 =  OK 
     *   700 - assertIsFalse ",@comma.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   701 - assertIsFalse ",,,,,,@comma.domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   702 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   703 - assertIsFalse "name , <pointy.brackets1.with.comma@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   704 - assertIsFalse "<pointy.brackets2.with.comma@domain.com> name ,"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   705 - assertIsFalse "domain.part@with,comma.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   706 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   707 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   708 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   709 - assertIsFalse "domain.part@with.comma.before,.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   710 - assertIsFalse "domain.part@with.comma.after.,point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   711 - assertIsFalse "§.local.name.starts.with.paragraph@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   712 - assertIsFalse "local.name.ends.with.paragraph§@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   713 - assertIsFalse "local.name.with.paragraph.before§.point@domain.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   714 - assertIsFalse "local.name.with.paragraph.after.§point@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   715 - assertIsFalse "local.name.with.double.paragraph§§test@domain.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   716 - assertIsFalse "(comment §) local.name.with.comment.with.paragraph@domain.com"        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   717 - assertIsFalse "\"quote§\".local.name.with.qoute.with.paragraph@domain.com"           =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   718 - assertIsFalse "§@paragraph.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   719 - assertIsFalse "§§§§§§@paragraph.domain.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   720 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   721 - assertIsFalse "name § <pointy.brackets1.with.paragraph@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   722 - assertIsFalse "<pointy.brackets2.with.paragraph@domain.com> name §"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   723 - assertIsFalse "domain.part@with§paragraph.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   724 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   725 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   726 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   727 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   728 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   729 - assertIsTrue  "'.local.name.starts.with.quote@domain.com"                            =   0 =  OK 
     *   730 - assertIsTrue  "local.name.ends.with.quote'@domain.com"                               =   0 =  OK 
     *   731 - assertIsTrue  "local.name.with.quote.before'.point@domain.com"                       =   0 =  OK 
     *   732 - assertIsTrue  "local.name.with.quote.after.'point@domain.com"                        =   0 =  OK 
     *   733 - assertIsTrue  "local.name.with.double.quote''test@domain.com"                        =   0 =  OK 
     *   734 - assertIsFalse "(comment ') local.name.with.comment.with.quote@domain.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   735 - assertIsTrue  "\"quote'\".local.name.with.qoute.with.quote@domain.com"               =   1 =  OK 
     *   736 - assertIsTrue  "'@quote.domain.com"                                                   =   0 =  OK 
     *   737 - assertIsTrue  "''''''@quote.domain.com"                                              =   0 =  OK 
     *   738 - assertIsTrue  "'.'.'.'.'.'@quote.domain.com"                                         =   0 =  OK 
     *   739 - assertIsFalse "name ' <pointy.brackets1.with.quote@domain.com>"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   740 - assertIsFalse "<pointy.brackets2.with.quote@domain.com> name '"                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   741 - assertIsFalse "domain.part@with'quote.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   742 - assertIsFalse "domain.part@'with.quote.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   743 - assertIsFalse "domain.part@with.quote.at.domain.end1'.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   744 - assertIsFalse "domain.part@with.quote.at.domain.end2.com'"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   745 - assertIsFalse "domain.part@with.quote.before'.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   746 - assertIsFalse "domain.part@with.quote.after.'point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   747 - assertIsFalse "\".local.name.starts.with.double.quote@domain.com"                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   748 - assertIsFalse "local.name.ends.with.double.quote\"@domain.com"                       =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   749 - assertIsFalse "local.name.with.double.quote.before\".point@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   750 - assertIsFalse "local.name.with.double.quote.after.\"point@domain.com"                =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   751 - assertIsFalse "local.name.with.double.double.quote\"\"test@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   752 - assertIsFalse "(comment \") local.name.with.comment.with.double.quote@domain.com"    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   753 - assertIsFalse "\"quote\"\".local.name.with.qoute.with.double.quote@domain.com"       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   754 - assertIsFalse "\"@double.quote.domain.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   755 - assertIsTrue  "\".\".\".\".\".\"@double.quote.domain.com"                            =   1 =  OK 
     *   756 - assertIsTrue  "name \" <pointy.brackets1.with.double.quote@domain.com>"              =   0 =  OK 
     *   757 - assertIsTrue  "<pointy.brackets2.with.double.quote@domain.com> name \""              =   0 =  OK 
     *   758 - assertIsFalse "domain.part@with\"double.quote.com"                                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   759 - assertIsFalse "domain.part@\"with.double.quote.at.domain.start.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   760 - assertIsFalse "domain.part@with.double.quote.at.domain.end1\".com"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   761 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com\""                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   762 - assertIsFalse "domain.part@with.double.quote.before\".point.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   763 - assertIsFalse "domain.part@with.double.quote.after.\"point.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   764 - assertIsFalse ")(.local.name.starts.with.false.bracket1@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   765 - assertIsFalse "local.name.ends.with.false.bracket1)(@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   766 - assertIsFalse "local.name.with.false.bracket1.before)(.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   767 - assertIsFalse "local.name.with.false.bracket1.after.)(point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   768 - assertIsFalse "local.name.with.double.false.bracket1)()(test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   769 - assertIsFalse "(comment )() local.name.with.comment.with.false.bracket1@domain.com"  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   770 - assertIsTrue  "\"quote)(\".local.name.with.qoute.with.false.bracket1@domain.com"     =   1 =  OK 
     *   771 - assertIsFalse ")(@false.bracket1.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   772 - assertIsFalse ")()()()()()(@false.bracket1.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   773 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   774 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1@domain.com>"            =   0 =  OK 
     *   775 - assertIsTrue  "<pointy.brackets2.with.false.bracket1@domain.com> name )("            =   0 =  OK 
     *   776 - assertIsFalse "domain.part@with)(false.bracket1.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   777 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   778 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   779 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   780 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   781 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   782 - assertIsTrue  "}{.local.name.starts.with.false.bracket2@domain.com"                  =   0 =  OK 
     *   783 - assertIsTrue  "local.name.ends.with.false.bracket2}{@domain.com"                     =   0 =  OK 
     *   784 - assertIsTrue  "local.name.with.false.bracket2.before}{.point@domain.com"             =   0 =  OK 
     *   785 - assertIsTrue  "local.name.with.false.bracket2.after.}{point@domain.com"              =   0 =  OK 
     *   786 - assertIsTrue  "local.name.with.double.false.bracket2}{}{test@domain.com"             =   0 =  OK 
     *   787 - assertIsFalse "(comment }{) local.name.with.comment.with.false.bracket2@domain.com"  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   788 - assertIsTrue  "\"quote}{\".local.name.with.qoute.with.false.bracket2@domain.com"     =   1 =  OK 
     *   789 - assertIsTrue  "}{@false.bracket2.domain.com"                                         =   0 =  OK 
     *   790 - assertIsTrue  "}{}{}{}{}{}{@false.bracket2.domain.com"                               =   0 =  OK 
     *   791 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                          =   0 =  OK 
     *   792 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   793 - assertIsFalse "<pointy.brackets2.with.false.bracket2@domain.com> name }{"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   794 - assertIsFalse "domain.part@with}{false.bracket2.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   795 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   796 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   797 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   798 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   799 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   800 - assertIsFalse "][.local.name.starts.with.false.bracket3@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   801 - assertIsFalse "local.name.ends.with.false.bracket3][@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   802 - assertIsFalse "local.name.with.false.bracket3.before][.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   803 - assertIsFalse "local.name.with.false.bracket3.after.][point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   804 - assertIsFalse "local.name.with.double.false.bracket3][][test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   805 - assertIsFalse "(comment ][) local.name.with.comment.with.false.bracket3@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   806 - assertIsTrue  "\"quote][\".local.name.with.qoute.with.false.bracket3@domain.com"     =   1 =  OK 
     *   807 - assertIsFalse "][@false.bracket3.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   808 - assertIsFalse "][][][][][][@false.bracket3.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   809 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   810 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   811 - assertIsFalse "<pointy.brackets2.with.false.bracket3@domain.com> name ]["            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   812 - assertIsFalse "domain.part@with][false.bracket3.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   813 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   814 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   815 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   816 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   817 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   818 - assertIsFalse "><.local.name.starts.with.false.bracket4@domain.com"                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   819 - assertIsFalse "local.name.ends.with.false.bracket4><@domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   820 - assertIsFalse "local.name.with.false.bracket4.before><.point@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   821 - assertIsFalse "local.name.with.false.bracket4.after.><point@domain.com"              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   822 - assertIsFalse "local.name.with.double.false.bracket4><><test@domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   823 - assertIsFalse "(comment ><) local.name.with.comment.with.false.bracket4@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   824 - assertIsTrue  "\"quote><\".local.name.with.qoute.with.false.bracket4@domain.com"     =   1 =  OK 
     *   825 - assertIsFalse "><@false.bracket4.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   826 - assertIsFalse "><><><><><><@false.bracket4.domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   827 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   828 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   829 - assertIsFalse "<pointy.brackets2.with.false.bracket4@domain.com> name ><"            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   830 - assertIsFalse "domain.part@with\slash.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   831 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   832 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   833 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   834 - assertIsFalse "domain.part@with.slash.before\.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   835 - assertIsFalse "domain.part@with.slash.after.\point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   836 - assertIsFalse "domain.part@with><false.bracket4.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   837 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   838 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   839 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   840 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   841 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   842 - assertIsFalse "DomainHyphen@-atstart"                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   843 - assertIsFalse "DomainHyphen@atend-.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   844 - assertIsFalse "DomainHyphen@bb.-cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   845 - assertIsFalse "DomainHyphen@bb.-cc-"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   846 - assertIsFalse "DomainHyphen@bb.cc-"                                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   847 - assertIsFalse "DomainHyphen@bb.c-c"                                                  =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   848 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   849 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                    =   0 =  OK 
     *   850 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   851 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   852 - assertIsFalse "DomainNotAllowedCharacter@example'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   853 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   854 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                 =   0 =  OK 
     *   855 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                   =   0 =  OK 
     *   856 - assertIsFalse "tld.starts.with.digit@domain.2com"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   857 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                      =   0 =  OK 
     *   858 - assertIsFalse "email@=qowaiv.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   859 - assertIsFalse "email@plus+.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   860 - assertIsFalse "email@domain.com>"                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   861 - assertIsFalse "email@mailto:domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   862 - assertIsFalse "mailto:mailto:email@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   863 - assertIsFalse "email@-domain.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   864 - assertIsFalse "email@domain-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   865 - assertIsFalse "email@domain.com-"                                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   866 - assertIsFalse "email@{leftbracket.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   867 - assertIsFalse "email@rightbracket}.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   868 - assertIsFalse "email@pp|e.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   869 - assertIsTrue  "email@domain.domain.domain.com.com"                                   =   0 =  OK 
     *   870 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                            =   0 =  OK 
     *   871 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                     =   0 =  OK 
     *   872 - assertIsFalse "unescaped white space@fake$com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   873 - assertIsFalse "\"Joe Smith email@domain.com"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   874 - assertIsFalse "\"Joe Smith' email@domain.com"                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   875 - assertIsFalse "\"Joe Smith\"email@domain.com"                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   876 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   877 - assertIsTrue  "{john'doe}@my.server"                                                 =   0 =  OK 
     *   878 - assertIsTrue  "email@domain-one.com"                                                 =   0 =  OK 
     *   879 - assertIsTrue  "_______@domain.com"                                                   =   0 =  OK 
     *   880 - assertIsTrue  "?????@domain.com"                                                     =   0 =  OK 
     *   881 - assertIsFalse "local@?????.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   882 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                        =   1 =  OK 
     *   883 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                               =   1 =  OK 
     *   884 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                            =   0 =  OK 
     *   885 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                     =   0 =  OK 
     *   886 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   887 - assertIsFalse "(ohne !, passt das) $!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   888 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   889 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                  =   1 =  OK 
     *   890 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   891 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   892 - assertIsFalse "\"\"@[1"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   893 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   894 - assertIsFalse "ABC.DEF@[]"                                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   895 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                   =   3 =  OK 
     *   896 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                            =   2 =  OK 
     *   897 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                              =   3 =  OK 
     *   898 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                                    =   2 =  OK 
     *   899 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   900 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   901 - assertIsFalse "ABC.DEF[1.2.3.4]"                                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   902 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   903 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   904 - assertIsFalse "ABC.DEF@[][][][]"                                                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   905 - assertIsFalse "ABC.DEF@[{][})][}][}\\"]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   906 - assertIsFalse "ABC.DEF@[....]"                                                       =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   907 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   908 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   909 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                              =   3 =  OK 
     *   910 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   911 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   912 - assertIsFalse "ABC.DEF@[..]"                                                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   913 - assertIsFalse "ABC.DEF@[.2.3.4]"                                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   914 - assertIsFalse "ABC.DEF@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   915 - assertIsFalse "ABC.DEF@[1.2]"                                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   916 - assertIsFalse "ABC.DEF@[1.2.3]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   917 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                                  =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   918 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                                =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   919 - assertIsFalse "ABC.DEF@[1.2.3.]"                                                     =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   920 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   921 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   922 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   923 - assertIsFalse "ABC.DEF@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   924 - assertIsFalse "ABC.DEF@1.2.3.4]"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   925 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   926 - assertIsFalse "ABC.DEF@[12.34]"                                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   927 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   928 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   929 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   930 - assertIsFalse "ip.v4.with.underscore@[123.14_5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   931 - assertIsFalse "ip.v4.with.underscore@[123.145_.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   932 - assertIsFalse "ip.v4.with.underscore@[123.145._178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   933 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90_]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   934 - assertIsFalse "ip.v4.with.underscore@[_123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   935 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90]_"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   936 - assertIsFalse "ip.v4.with.underscore@_[123.145.178.90]"                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   937 - assertIsFalse "ip.v4.with.amp@[123.14&5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   938 - assertIsFalse "ip.v4.with.amp@[123.145&.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   939 - assertIsFalse "ip.v4.with.amp@[123.145.&178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   940 - assertIsFalse "ip.v4.with.amp@[123.145.178.90&]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   941 - assertIsFalse "ip.v4.with.amp@[&123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   942 - assertIsFalse "ip.v4.with.amp@[123.145.178.90]&"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   943 - assertIsFalse "ip.v4.with.amp@&[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   944 - assertIsFalse "ip.v4.with.asterisk@[123.14*5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   945 - assertIsFalse "ip.v4.with.asterisk@[123.145*.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   946 - assertIsFalse "ip.v4.with.asterisk@[123.145.*178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   947 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90*]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   948 - assertIsFalse "ip.v4.with.asterisk@[*123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   949 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90]*"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   950 - assertIsFalse "ip.v4.with.asterisk@*[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   951 - assertIsFalse "ip.v4.with.dollar@[123.14$5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   952 - assertIsFalse "ip.v4.with.dollar@[123.145$.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   953 - assertIsFalse "ip.v4.with.dollar@[123.145.$178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   954 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90$]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   955 - assertIsFalse "ip.v4.with.dollar@[$123.145.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   956 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90]$"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   957 - assertIsFalse "ip.v4.with.dollar@$[123.145.178.90]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   958 - assertIsFalse "ip.v4.with.equality@[123.14=5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   959 - assertIsFalse "ip.v4.with.equality@[123.145=.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   960 - assertIsFalse "ip.v4.with.equality@[123.145.=178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   961 - assertIsFalse "ip.v4.with.equality@[123.145.178.90=]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   962 - assertIsFalse "ip.v4.with.equality@[=123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   963 - assertIsFalse "ip.v4.with.equality@[123.145.178.90]="                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   964 - assertIsFalse "ip.v4.with.equality@=[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   965 - assertIsFalse "ip.v4.with.exclamation@[123.14!5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   966 - assertIsFalse "ip.v4.with.exclamation@[123.145!.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   967 - assertIsFalse "ip.v4.with.exclamation@[123.145.!178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   968 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90!]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   969 - assertIsFalse "ip.v4.with.exclamation@[!123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   970 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90]!"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   971 - assertIsFalse "ip.v4.with.exclamation@![123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   972 - assertIsFalse "ip.v4.with.question@[123.14?5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   973 - assertIsFalse "ip.v4.with.question@[123.145?.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   974 - assertIsFalse "ip.v4.with.question@[123.145.?178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   975 - assertIsFalse "ip.v4.with.question@[123.145.178.90?]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   976 - assertIsFalse "ip.v4.with.question@[?123.145.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   977 - assertIsFalse "ip.v4.with.question@[123.145.178.90]?"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   978 - assertIsFalse "ip.v4.with.question@?[123.145.178.90]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   979 - assertIsFalse "ip.v4.with.grave-accent@[123.14`5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   980 - assertIsFalse "ip.v4.with.grave-accent@[123.145`.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   981 - assertIsFalse "ip.v4.with.grave-accent@[123.145.`178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   982 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90`]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   983 - assertIsFalse "ip.v4.with.grave-accent@[`123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   984 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90]`"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   985 - assertIsFalse "ip.v4.with.grave-accent@`[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   986 - assertIsFalse "ip.v4.with.hash@[123.14#5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   987 - assertIsFalse "ip.v4.with.hash@[123.145#.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   988 - assertIsFalse "ip.v4.with.hash@[123.145.#178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   989 - assertIsFalse "ip.v4.with.hash@[123.145.178.90#]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   990 - assertIsFalse "ip.v4.with.hash@[#123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   991 - assertIsFalse "ip.v4.with.hash@[123.145.178.90]#"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   992 - assertIsFalse "ip.v4.with.hash@#[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   993 - assertIsFalse "ip.v4.with.percentage@[123.14%5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   994 - assertIsFalse "ip.v4.with.percentage@[123.145%.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   995 - assertIsFalse "ip.v4.with.percentage@[123.145.%178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   996 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90%]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   997 - assertIsFalse "ip.v4.with.percentage@[%123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   998 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90]%"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   999 - assertIsFalse "ip.v4.with.percentage@%[123.145.178.90]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1000 - assertIsFalse "ip.v4.with.pipe@[123.14|5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1001 - assertIsFalse "ip.v4.with.pipe@[123.145|.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1002 - assertIsFalse "ip.v4.with.pipe@[123.145.|178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1003 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90|]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1004 - assertIsFalse "ip.v4.with.pipe@[|123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1005 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90]|"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1006 - assertIsFalse "ip.v4.with.pipe@|[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1007 - assertIsFalse "ip.v4.with.plus@[123.14+5.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1008 - assertIsFalse "ip.v4.with.plus@[123.145+.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1009 - assertIsFalse "ip.v4.with.plus@[123.145.+178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1010 - assertIsFalse "ip.v4.with.plus@[123.145.178.90+]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1011 - assertIsFalse "ip.v4.with.plus@[+123.145.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1012 - assertIsFalse "ip.v4.with.plus@[123.145.178.90]+"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1013 - assertIsFalse "ip.v4.with.plus@+[123.145.178.90]"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1014 - assertIsFalse "ip.v4.with.leftbracket@[123.14{5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1015 - assertIsFalse "ip.v4.with.leftbracket@[123.145{.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1016 - assertIsFalse "ip.v4.with.leftbracket@[123.145.{178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1017 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90{]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1018 - assertIsFalse "ip.v4.with.leftbracket@[{123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1019 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]{"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1020 - assertIsFalse "ip.v4.with.leftbracket@{[123.145.178.90]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1021 - assertIsFalse "ip.v4.with.rightbracket@[123.14}5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1022 - assertIsFalse "ip.v4.with.rightbracket@[123.145}.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1023 - assertIsFalse "ip.v4.with.rightbracket@[123.145.}178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1024 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90}]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1025 - assertIsFalse "ip.v4.with.rightbracket@[}123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1026 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]}"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1027 - assertIsFalse "ip.v4.with.rightbracket@}[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1028 - assertIsFalse "ip.v4.with.leftbracket@[123.14(5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1029 - assertIsFalse "ip.v4.with.leftbracket@[123.145(.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1030 - assertIsFalse "ip.v4.with.leftbracket@[123.145.(178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1031 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90(]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1032 - assertIsFalse "ip.v4.with.leftbracket@[(123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1033 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]("                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1034 - assertIsFalse "ip.v4.with.leftbracket@([123.145.178.90]"                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1035 - assertIsFalse "ip.v4.with.rightbracket@[123.14)5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1036 - assertIsFalse "ip.v4.with.rightbracket@[123.145).178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1037 - assertIsFalse "ip.v4.with.rightbracket@[123.145.)178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1038 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90)]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1039 - assertIsFalse "ip.v4.with.rightbracket@[)123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1040 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90])"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1041 - assertIsFalse "ip.v4.with.rightbracket@)[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1042 - assertIsFalse "ip.v4.with.leftbracket@[123.14[5.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1043 - assertIsFalse "ip.v4.with.leftbracket@[123.145[.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1044 - assertIsFalse "ip.v4.with.leftbracket@[123.145.[178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1045 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90[]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1046 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1047 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90]["                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1048 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1049 - assertIsFalse "ip.v4.with.rightbracket@[123.14]5.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1050 - assertIsFalse "ip.v4.with.rightbracket@[123.145].178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1051 - assertIsFalse "ip.v4.with.rightbracket@[123.145.]178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1052 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1053 - assertIsFalse "ip.v4.with.rightbracket@[]123.145.178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1054 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1055 - assertIsFalse "ip.v4.with.rightbracket@][123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1056 - assertIsFalse "ip.v4.with.lower.than@[123.14<5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1057 - assertIsFalse "ip.v4.with.lower.than@[123.145<.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1058 - assertIsFalse "ip.v4.with.lower.than@[123.145.<178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1059 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90<]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1060 - assertIsFalse "ip.v4.with.lower.than@[<123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1061 - assertIsFalse "ip.v4.with.lower.than@[123.145.178.90]<"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1062 - assertIsFalse "ip.v4.with.lower.than@<[123.145.178.90]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1063 - assertIsFalse "ip.v4.with.greater.than@[123.14>5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1064 - assertIsFalse "ip.v4.with.greater.than@[123.145>.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1065 - assertIsFalse "ip.v4.with.greater.than@[123.145.>178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1066 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90>]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1067 - assertIsFalse "ip.v4.with.greater.than@[>123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1068 - assertIsFalse "ip.v4.with.greater.than@[123.145.178.90]>"                            =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1069 - assertIsFalse "ip.v4.with.greater.than@>[123.145.178.90]"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1070 - assertIsFalse "ip.v4.with.tilde@[123.14~5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1071 - assertIsFalse "ip.v4.with.tilde@[123.145~.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1072 - assertIsFalse "ip.v4.with.tilde@[123.145.~178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1073 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90~]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1074 - assertIsFalse "ip.v4.with.tilde@[~123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1075 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90]~"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1076 - assertIsFalse "ip.v4.with.tilde@~[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1077 - assertIsFalse "ip.v4.with.xor@[123.14^5.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1078 - assertIsFalse "ip.v4.with.xor@[123.145^.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1079 - assertIsFalse "ip.v4.with.xor@[123.145.^178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1080 - assertIsFalse "ip.v4.with.xor@[123.145.178.90^]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1081 - assertIsFalse "ip.v4.with.xor@[^123.145.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1082 - assertIsFalse "ip.v4.with.xor@[123.145.178.90]^"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1083 - assertIsFalse "ip.v4.with.xor@^[123.145.178.90]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1084 - assertIsFalse "ip.v4.with.colon@[123.14:5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1085 - assertIsFalse "ip.v4.with.colon@[123.145:.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1086 - assertIsFalse "ip.v4.with.colon@[123.145.:178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1087 - assertIsFalse "ip.v4.with.colon@[123.145.178.90:]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1088 - assertIsFalse "ip.v4.with.colon@[:123.145.178.90]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1089 - assertIsFalse "ip.v4.with.colon@[123.145.178.90]:"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1090 - assertIsFalse "ip.v4.with.colon@:[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1091 - assertIsFalse "ip.v4.with.space@[123.14 5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1092 - assertIsFalse "ip.v4.with.space@[123.145 .178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1093 - assertIsFalse "ip.v4.with.space@[123.145. 178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1094 - assertIsFalse "ip.v4.with.space@[123.145.178.90 ]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1095 - assertIsFalse "ip.v4.with.space@[ 123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1096 - assertIsFalse "ip.v4.with.space@[123.145.178.90] "                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1097 - assertIsFalse "ip.v4.with.space@ [123.145.178.90]"                                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1098 - assertIsFalse "ip.v4.with.dot@[123.14.5.178.90]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1099 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1100 - assertIsFalse "ip.v4.with.dot@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1101 - assertIsFalse "ip.v4.with.dot@[123.145.178.90.]"                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1102 - assertIsFalse "ip.v4.with.dot@[.123.145.178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1103 - assertIsFalse "ip.v4.with.dot@[123.145.178.90]."                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1104 - assertIsFalse "ip.v4.with.dot@.[123.145.178.90]"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1105 - assertIsFalse "ip.v4.with.comma@[123.14,5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1106 - assertIsFalse "ip.v4.with.comma@[123.145,.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1107 - assertIsFalse "ip.v4.with.comma@[123.145.,178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1108 - assertIsFalse "ip.v4.with.comma@[123.145.178.90,]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1109 - assertIsFalse "ip.v4.with.comma@[,123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1110 - assertIsFalse "ip.v4.with.comma@[123.145.178.90],"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1111 - assertIsFalse "ip.v4.with.comma@,[123.145.178.90]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1112 - assertIsFalse "ip.v4.with.at@[123.14@5.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1113 - assertIsFalse "ip.v4.with.at@[123.145@.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1114 - assertIsFalse "ip.v4.with.at@[123.145.@178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1115 - assertIsFalse "ip.v4.with.at@[123.145.178.90@]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1116 - assertIsFalse "ip.v4.with.at@[@123.145.178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1117 - assertIsFalse "ip.v4.with.at@[123.145.178.90]@"                                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1118 - assertIsFalse "ip.v4.with.at@@[123.145.178.90]"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1119 - assertIsFalse "ip.v4.with.paragraph@[123.14§5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1120 - assertIsFalse "ip.v4.with.paragraph@[123.145§.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1121 - assertIsFalse "ip.v4.with.paragraph@[123.145.§178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1122 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90§]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1123 - assertIsFalse "ip.v4.with.paragraph@[§123.145.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1124 - assertIsFalse "ip.v4.with.paragraph@[123.145.178.90]§"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1125 - assertIsFalse "ip.v4.with.paragraph@§[123.145.178.90]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1126 - assertIsFalse "ip.v4.with.double.quote@[123.14'5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1127 - assertIsFalse "ip.v4.with.double.quote@[123.145'.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1128 - assertIsFalse "ip.v4.with.double.quote@[123.145.'178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1129 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90']"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1130 - assertIsFalse "ip.v4.with.double.quote@['123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1131 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]'"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1132 - assertIsFalse "ip.v4.with.double.quote@'[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1133 - assertIsFalse "ip.v4.with.double.quote@[123.14\"5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1134 - assertIsFalse "ip.v4.with.double.quote@[123.145\".178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1135 - assertIsFalse "ip.v4.with.double.quote@[123.145.\"178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1136 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90\"]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1137 - assertIsFalse "ip.v4.with.double.quote@[\"123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1138 - assertIsFalse "ip.v4.with.double.quote@[123.145.178.90]\""                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1139 - assertIsFalse "ip.v4.with.double.quote@\"[123.145.178.90]"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1140 - assertIsFalse "ip.v4.with.empty.bracket@[123.14()5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1141 - assertIsFalse "ip.v4.with.empty.bracket@[123.145().178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1142 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.()178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1143 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90()]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1144 - assertIsFalse "ip.v4.with.empty.bracket@[()123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1145 - assertIsTrue  "ip.v4.with.empty.bracket@[123.145.178.90]()"                          =   2 =  OK 
     *  1146 - assertIsTrue  "ip.v4.with.empty.bracket@()[123.145.178.90]"                          =   2 =  OK 
     *  1147 - assertIsFalse "ip.v4.with.empty.bracket@[123.14{}5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1148 - assertIsFalse "ip.v4.with.empty.bracket@[123.145{}.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1149 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.{}178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1150 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90{}]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1151 - assertIsFalse "ip.v4.with.empty.bracket@[{}123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1152 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]{}"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1153 - assertIsFalse "ip.v4.with.empty.bracket@{}[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1154 - assertIsFalse "ip.v4.with.empty.bracket@[123.14[]5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1155 - assertIsFalse "ip.v4.with.empty.bracket@[123.145[].178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1156 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.[]178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1157 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90[]]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1158 - assertIsFalse "ip.v4.with.empty.bracket@[[]123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1159 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90][]"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1160 - assertIsFalse "ip.v4.with.empty.bracket@[][123.145.178.90]"                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1161 - assertIsFalse "ip.v4.with.empty.bracket@[123.14<>5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1162 - assertIsFalse "ip.v4.with.empty.bracket@[123.145<>.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1163 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.<>178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1164 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90<>]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1165 - assertIsFalse "ip.v4.with.empty.bracket@[<>123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1166 - assertIsFalse "ip.v4.with.empty.bracket@[123.145.178.90]<>"                          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1167 - assertIsFalse "ip.v4.with.empty.bracket@<>[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1168 - assertIsFalse "ip.v4.with.false.bracket1@[123.14)(5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1169 - assertIsFalse "ip.v4.with.false.bracket1@[123.145)(.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1170 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.)(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1171 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90)(]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1172 - assertIsFalse "ip.v4.with.false.bracket1@[)(123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1173 - assertIsFalse "ip.v4.with.false.bracket1@[123.145.178.90])("                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1174 - assertIsFalse "ip.v4.with.false.bracket1@)([123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1175 - assertIsFalse "ip.v4.with.false.bracket2@[123.14}{5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1176 - assertIsFalse "ip.v4.with.false.bracket2@[123.145}{.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1177 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.}{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1178 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90}{]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1179 - assertIsFalse "ip.v4.with.false.bracket2@[}{123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1180 - assertIsFalse "ip.v4.with.false.bracket2@[123.145.178.90]}{"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1181 - assertIsFalse "ip.v4.with.false.bracket2@}{[123.145.178.90]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1182 - assertIsFalse "ip.v4.with.false.bracket3@[123.14][5.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1183 - assertIsFalse "ip.v4.with.false.bracket3@[123.145][.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1184 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.][178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1185 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90][]"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1186 - assertIsFalse "ip.v4.with.false.bracket3@[][123.145.178.90]"                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1187 - assertIsFalse "ip.v4.with.false.bracket3@[123.145.178.90]]["                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1188 - assertIsFalse "ip.v4.with.false.bracket3@][[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1189 - assertIsFalse "ip.v4.with.false.bracket4@[123.14><5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1190 - assertIsFalse "ip.v4.with.false.bracket4@[123.145><.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1191 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.><178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1192 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90><]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1193 - assertIsFalse "ip.v4.with.false.bracket4@[><123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1194 - assertIsFalse "ip.v4.with.false.bracket4@[123.145.178.90]><"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1195 - assertIsFalse "ip.v4.with.false.bracket4@><[123.145.178.90]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1196 - assertIsFalse "ip.v4.with.number0@[123.1405.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1197 - assertIsFalse "ip.v4.with.number0@[123.1450.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1198 - assertIsFalse "ip.v4.with.number0@[123.145.0178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1199 - assertIsFalse "ip.v4.with.number0@[123.145.178.900]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1200 - assertIsFalse "ip.v4.with.number0@[0123.145.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1201 - assertIsFalse "ip.v4.with.number0@[123.145.178.90]0"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1202 - assertIsFalse "ip.v4.with.number0@0[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1203 - assertIsFalse "ip.v4.with.number9@[123.1495.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1204 - assertIsFalse "ip.v4.with.number9@[123.1459.178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1205 - assertIsFalse "ip.v4.with.number9@[123.145.9178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1206 - assertIsFalse "ip.v4.with.number9@[123.145.178.909]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1207 - assertIsFalse "ip.v4.with.number9@[9123.145.178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1208 - assertIsFalse "ip.v4.with.number9@[123.145.178.90]9"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1209 - assertIsFalse "ip.v4.with.number9@9[123.145.178.90]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1210 - assertIsFalse "ip.v4.with.numbers@[123.1401234567895.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1211 - assertIsFalse "ip.v4.with.numbers@[123.1450123456789.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1212 - assertIsFalse "ip.v4.with.numbers@[123.145.0123456789178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1213 - assertIsFalse "ip.v4.with.numbers@[123.145.178.900123456789]"                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1214 - assertIsFalse "ip.v4.with.numbers@[0123456789123.145.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1215 - assertIsFalse "ip.v4.with.numbers@[123.145.178.90]0123456789"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1216 - assertIsFalse "ip.v4.with.numbers@0123456789[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1217 - assertIsFalse "ip.v4.with.slash@[123.14\5.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1218 - assertIsFalse "ip.v4.with.slash@[123.145\.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1219 - assertIsFalse "ip.v4.with.slash@[123.145.\178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1220 - assertIsFalse "ip.v4.with.slash@[123.145.178.90\]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1221 - assertIsFalse "ip.v4.with.slash@[\123.145.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1222 - assertIsFalse "ip.v4.with.slash@[123.145.178.90]\"                                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1223 - assertIsFalse "ip.v4.with.slash@\[123.145.178.90]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1224 - assertIsFalse "ip.v4.with.byte.overflow@[123.149995.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1225 - assertIsFalse "ip.v4.with.byte.overflow@[123.145999.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *  1226 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.999178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1227 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90999]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1228 - assertIsFalse "ip.v4.with.byte.overflow@[123.145.178.90]999"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1229 - assertIsFalse "ip.v4.with.byte.overflow@[999123.145.178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1230 - assertIsFalse "ip.v4.with.byte.overflow@999[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1231 - assertIsFalse "ip.v4.with.no.hex.number@[123.14xyz5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1232 - assertIsFalse "ip.v4.with.no.hex.number@[123.145xyz.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1233 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.xyz178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1234 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90xyz]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1235 - assertIsFalse "ip.v4.with.no.hex.number@[123.145.178.90]xyz"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1236 - assertIsFalse "ip.v4.with.no.hex.number@[xyz123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1237 - assertIsFalse "ip.v4.with.no.hex.number@xyz[123.145.178.90]"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1238 - assertIsFalse "ip.v4.with.string@[123.14\"str\"5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1239 - assertIsFalse "ip.v4.with.string@[123.145\"str\".178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1240 - assertIsFalse "ip.v4.with.string@[123.145.\"str\"178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1241 - assertIsFalse "ip.v4.with.string@[123.145.178.90\"str\"]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1242 - assertIsFalse "ip.v4.with.string@[123.145.178.90]\"str\""                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1243 - assertIsFalse "ip.v4.with.string@[\"str\"123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1244 - assertIsFalse "ip.v4.with.string@\"str\"[123.145.178.90]"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1245 - assertIsFalse "ip.v4.with.comment@[123.14(comment)5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1246 - assertIsFalse "ip.v4.with.comment@[123.145(comment).178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1247 - assertIsFalse "ip.v4.with.comment@[123.145.(comment)178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1248 - assertIsFalse "ip.v4.with.comment@[123.145.178.90(comment)]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1249 - assertIsTrue  "ip.v4.with.comment@[123.145.178.90](comment)"                         =   2 =  OK 
     *  1250 - assertIsTrue  "ip.v4.with.comment@[(comment)123.145.178.90]"                         =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1251 - assertIsFalse "ip.v4.with.comment@(comment)[123.145.178.90]"                         =   2 =  #### FEHLER ####    eMail-Adresse korrekt (IP4-Adresse)
     *  1252 - assertIsTrue  "email@[123.123.123.123]"                                              =   2 =  OK 
     *  1253 - assertIsFalse "email@111.222.333"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1254 - assertIsFalse "email@111.222.333.256"                                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1255 - assertIsFalse "email@[123.123.123.123"                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1256 - assertIsFalse "email@[123.123.123].123"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1257 - assertIsFalse "email@123.123.123.123]"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1258 - assertIsFalse "email@123.123.[123.123]"                                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1259 - assertIsFalse "ab@988.120.150.10"                                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1260 - assertIsFalse "ab@120.256.256.120"                                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1261 - assertIsFalse "ab@120.25.1111.120"                                                   =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1262 - assertIsFalse "ab@[188.120.150.10"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1263 - assertIsFalse "ab@188.120.150.10]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1264 - assertIsFalse "ab@[188.120.150.10].com"                                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1265 - assertIsTrue  "ab@188.120.150.10"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1266 - assertIsTrue  "ab@1.0.0.10"                                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1267 - assertIsTrue  "ab@120.25.254.120"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1268 - assertIsTrue  "ab@01.120.150.1"                                                      =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1269 - assertIsTrue  "ab@88.120.150.021"                                                    =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1270 - assertIsTrue  "ab@88.120.150.01"                                                     =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1271 - assertIsTrue  "email@123.123.123.123"                                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1272 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                           =   4 =  OK 
     *  1273 - assertIsFalse "ABC.DEF@[IP"                                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1274 - assertIsFalse "ABC.DEF@[IPv6]"                                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1275 - assertIsFalse "ABC.DEF@[IPv6:]"                                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1276 - assertIsFalse "ABC.DEF@[IPv6::]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1277 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1278 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1279 - assertIsFalse "ABC.DEF@[IPv6:"                                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1280 - assertIsFalse "ABC.DEF@[IPv6::"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1281 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1282 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1283 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1284 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                   =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1285 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                 =   4 =  OK 
     *  1286 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                               =   4 =  OK 
     *  1287 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                            =   4 =  OK 
     *  1288 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                           =   4 =  OK 
     *  1289 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                           =   4 =  OK 
     *  1290 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                         =   4 =  OK 
     *  1291 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1292 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                       =   4 =  OK 
     *  1293 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                     =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1294 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                               =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1295 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                             =   4 =  OK 
     *  1296 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1297 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1298 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                 =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1299 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1300 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1301 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                        =   4 =  OK 
     *  1302 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1303 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1304 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1305 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                            =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1306 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1307 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1308 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1309 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1310 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1311 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1312 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1313 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1314 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1315 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1316 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1317 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1318 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1319 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1320 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1321 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1322 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1323 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1324 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1325 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1326 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1327 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1328 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1329 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1330 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1331 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1332 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1333 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1334 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1335 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1336 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1337 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1338 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1339 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1340 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1341 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1342 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1343 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1344 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1345 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1346 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1347 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1348 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1349 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1350 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1351 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1352 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1353 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1354 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1355 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1356 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1357 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1358 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1359 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1360 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1361 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1362 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1363 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1364 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1365 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1366 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1367 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1368 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1369 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1370 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1371 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1372 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1373 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1374 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1375 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1376 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1377 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1378 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1379 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1380 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1381 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1382 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1383 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1384 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1385 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1386 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1387 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1388 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1389 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1390 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1391 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1392 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1393 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1394 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1395 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1396 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1397 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1398 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1399 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1400 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                        =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1401 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1402 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1403 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1404 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1405 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1406 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1407 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1408 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1409 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1410 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1411 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1412 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1413 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1414 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1415 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1416 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1417 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1418 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1419 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1420 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1421 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1422 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1423 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1424 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1425 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1426 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1427 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1428 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1429 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1430 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1431 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1432 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1433 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1434 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1435 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1436 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1437 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1438 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1439 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1440 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1441 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1442 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1443 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                              =   4 =  OK 
     *  1444 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1445 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                              =   4 =  OK 
     *  1446 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                              =   4 =  OK 
     *  1447 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1448 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1449 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1450 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1451 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1452 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1453 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1454 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1455 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1456 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1457 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1458 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1459 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1460 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1461 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1462 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1463 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1464 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1465 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1466 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1467 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1468 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1469 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1470 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1471 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1472 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1473 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1474 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1475 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1476 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1477 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1478 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1479 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1480 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1481 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1482 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1483 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1484 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1485 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2\"2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1486 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22\":3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1487 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:\"3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1488 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7\"]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1489 - assertIsFalse "ip.v6.with.double.quote@\"[IPv6:1:22:3:4:5:6:7]"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1490 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]\""                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1491 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1492 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1493 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1494 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1495 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                     =   4 =  OK 
     *  1496 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                     =   4 =  OK 
     *  1497 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1498 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1499 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1500 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1501 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1502 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1503 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1504 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1505 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1506 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1507 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1508 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1509 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1510 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1511 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1512 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1513 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1514 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1515 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1516 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1517 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1518 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1519 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1520 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1521 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1522 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1523 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1524 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1525 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1526 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1527 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:2][2:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1528 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22][:3:4:5:6:7]"                    =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1529 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:][3:4:5:6:7]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1530 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7][]"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1531 - assertIsFalse "ip.v6.with.false.bracket3@][[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1532 - assertIsFalse "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7]]["                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1533 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1534 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1535 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1536 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1537 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1538 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1539 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1540 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1541 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1542 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1543 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                   =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1544 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1545 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1546 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1547 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1548 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1549 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1550 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1551 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1552 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1553 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1554 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:27999]"                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1555 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]2999"                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1556 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1557 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                    =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1558 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1559 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1560 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1561 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1562 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1563 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *  1564 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1565 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1566 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1567 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1568 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1569 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1570 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1571 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1572 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1573 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1574 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1575 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1576 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                    =   4 =  OK 
     *  1577 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                    =   4 =  OK 
     *  1578 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1579 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"                        =   4 =  OK 
     *  1580 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1581 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1582 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1583 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"                   =   4 =  OK 
     *  1584 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1585 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"               =   4 =  OK 
     *  1586 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"               =   4 =  OK 
     *  1587 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"               =   4 =  OK 
     *  1588 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                              =   4 =  OK 
     *  1589 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                               =   4 =  OK 
     *  1590 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1591 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1592 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1593 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *  1594 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                       =   4 =  OK 
     *  1595 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                       =   4 =  OK 
     *  1596 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                           =   4 =  OK 
     *  1597 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                           =   4 =  OK 
     *  1598 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1599 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                        =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1600 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1601 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1602 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                           =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1603 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                     =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1604 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *  1605 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1606 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1607 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                                   =   1 =  OK 
     *  1608 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                                 =   1 =  OK 
     *  1609 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1610 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1611 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1612 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1613 - assertIsFalse "\"\"@GHI.DE"                                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1614 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1615 - assertIsFalse "A@G\"HI.DE"                                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1616 - assertIsFalse "\"@GHI.DE"                                                            =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1617 - assertIsFalse "ABC.DEF.\""                                                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1618 - assertIsFalse "ABC.DEF@\"\""                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1619 - assertIsFalse "ABC.DEF@G\"HI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1620 - assertIsFalse "ABC.DEF@GHI.DE\""                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1621 - assertIsFalse "ABC.DEF@\"GHI.DE"                                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *  1622 - assertIsFalse "\"Escape.Sequenz.Ende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1623 - assertIsFalse "ABC.DEF\"GHI.DE"                                                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1624 - assertIsFalse "ABC.DEF\"@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1625 - assertIsFalse "ABC.DE\"F@GHI.DE"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1626 - assertIsFalse "\"ABC.DEF@GHI.DE"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1627 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                                   =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1628 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                  =   1 =  OK 
     *  1629 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                  =   1 =  OK 
     *  1630 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                           =   1 =  OK 
     *  1631 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1632 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *  1633 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1634 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1635 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1636 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                               =   1 =  OK 
     *  1637 - assertIsFalse "\"Ende.am.Eingabeende\""                                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1638 - assertIsFalse "0\"00.000\"@GHI.JKL"                                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1639 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                 =   1 =  OK 
     *  1640 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                       =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1641 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1642 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1643 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                =   1 =  OK 
     *  1644 - assertIsFalse "\"Joe Smith\" email@domain.com"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1645 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1646 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *  1647 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                                     =   6 =  OK 
     *  1648 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1649 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                                     =   6 =  OK 
     *  1650 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                                 =   6 =  OK 
     *  1651 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                           =   6 =  OK 
     *  1652 - assertIsFalse "ABC.DEF@             (MNO)"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1653 - assertIsFalse "ABC.DEF@   .         (MNO)"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1654 - assertIsFalse "ABC.DEF              (MNO)"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1655 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                           =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1656 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1657 - assertIsFalse "ABC.DEF@GHI.JKL          "                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1658 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1659 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                   =   6 =  OK 
     *  1660 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                    =   6 =  OK 
     *  1661 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                    =   6 =  OK 
     *  1662 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                    =   6 =  OK 
     *  1663 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                    =   6 =  OK 
     *  1664 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                                =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1665 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1666 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1667 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1668 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1669 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *  1670 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1671 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1672 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                   = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1673 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                     =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1674 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1675 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1676 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                    =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1677 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                   =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1678 - assertIsFalse "ABC(DEF@GHI.JKL"                                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1679 - assertIsFalse "ABC.DEF@GHI)JKL"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1680 - assertIsFalse ")ABC.DEF@GHI.JKL"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1681 - assertIsFalse "ABC(DEF@GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1682 - assertIsFalse "ABC(DEF.GHI).JKL"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1683 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *  1684 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1685 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1686 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1687 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1688 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1689 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                           =   2 =  OK 
     *  1690 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                          = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *  1691 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                           =   2 =  OK 
     *  1692 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                                       =   2 =  OK 
     *  1693 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1694 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                               =   4 =  OK 
     *  1695 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                               =   4 =  OK 
     *  1696 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                           =   4 =  OK 
     *  1697 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  1698 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  1699 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  1700 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  1701 - assertIsFalse "john.smith@exampl(comment)e.com"                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1702 - assertIsFalse "john.s(comment)mith@example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1703 - assertIsFalse "john.smith(comment)@(comment)example.com"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1704 - assertIsFalse "john.smith(com@ment)example.com"                                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1705 - assertIsFalse "email( (nested) )@plus.com"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1706 - assertIsFalse "email)mirror(@plus.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1707 - assertIsFalse "email@plus.com (not closed comment"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1708 - assertIsFalse "email(with @ in comment)plus.com"                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1709 - assertIsTrue  "email@domain.com (joe Smith)"                                         =   6 =  OK 
     *  1710 - assertIsFalse "a@abc(bananas)def.com"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *  1711 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                            =   0 =  OK 
     *  1712 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                            =   0 =  OK 
     *  1713 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                             =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1714 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                             =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *  1715 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                        =   0 =  OK 
     *  1716 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                  =   1 =  OK 
     *  1717 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                          =   1 =  OK 
     *  1718 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                           =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1719 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1720 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1721 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1722 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1723 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1724 - assertIsFalse "ABC DEF <A@A>"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1725 - assertIsFalse "<A@A> ABC DEF"                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1726 - assertIsFalse "ABC DEF <>"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1727 - assertIsFalse "<> ABC DEF"                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1728 - assertIsFalse ">"                                                                    =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *  1729 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                    =   0 =  OK 
     *  1730 - assertIsTrue  "Joe Smith <email@domain.com>"                                         =   0 =  OK 
     *  1731 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1732 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1733 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1734 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"      =   9 =  OK 
     *  1735 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"      =   9 =  OK 
     *  1736 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "     =   9 =  OK 
     *  1737 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1738 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1739 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1740 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1741 - assertIsFalse "Test |<gaaf <email@domain.com>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1742 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1743 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1744 - assertIsFalse "<null>@mail.com"                                                      =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *  1745 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1746 - assertIsFalse "A@B.C"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1747 - assertIsFalse "A@COM"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1748 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                      =   0 =  OK 
     *  1749 - assertIsTrue  "ABC.DEF@GHI.J"                                                        =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1750 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *  1751 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  1752 - assertIsTrue  "A@B.CD"                                                               =   0 =  OK 
     *  1753 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *  1754 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *  1755 - assertIsTrue  "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" =   0 =  OK 
     *  1756 - assertIsFalse "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1757 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1758 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1759 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1760 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1761 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1762 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1763 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1764 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *  1765 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1766 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1767 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
     *  1768 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1769 - assertIsFalse "eMail Test XX4 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com>" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1770 - assertIsFalse "eMail Test XX5A <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1771 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1772 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
     *  1773 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
     *  1774 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1775 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1776 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *  1777 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1778 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1779 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1780 - assertIsTrue  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1781 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1782 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=.?^`{|}~@ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-0123456789.a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.e4.f5.g6.h7.i8.j9.K0.L1.M2.N3.O.domain.name" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  1783 - assertIsTrue  "email@domain.topleveldomain"                                          =   0 =  OK 
     *  1784 - assertIsTrue  "email@email.email.mydomain"                                           =   0 =  OK 
     * 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1785 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                     =   6 =  OK 
     *  1786 - assertIsTrue  "\"MaxMustermann\"@example.com"                                        =   1 =  OK 
     *  1787 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                           =   1 =  OK 
     *  1788 - assertIsTrue  "\".John.Doe\"@example.com"                                            =   1 =  OK 
     *  1789 - assertIsTrue  "\"John.Doe.\"@example.com"                                            =   1 =  OK 
     *  1790 - assertIsTrue  "\"John..Doe\"@example.com"                                            =   1 =  OK 
     *  1791 - assertIsTrue  "john.smith(comment)@example.com"                                      =   6 =  OK 
     *  1792 - assertIsTrue  "(comment)john.smith@example.com"                                      =   6 =  OK 
     *  1793 - assertIsTrue  "john.smith@(comment)example.com"                                      =   6 =  OK 
     *  1794 - assertIsTrue  "john.smith@example.com(comment)"                                      =   6 =  OK 
     *  1795 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  1796 - assertIsTrue  "jsmith@[192.168.2.1]"                                                 =   2 =  OK 
     *  1797 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                            =   4 =  OK 
     *  1798 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                    =   0 =  OK 
     *  1799 - assertIsTrue  "Marc Dupont <md118@example.com>"                                      =   0 =  OK 
     *  1800 - assertIsTrue  "simple@example.com"                                                   =   0 =  OK 
     *  1801 - assertIsTrue  "very.common@example.com"                                              =   0 =  OK 
     *  1802 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                       =   0 =  OK 
     *  1803 - assertIsTrue  "other.email-with-hyphen@example.com"                                  =   0 =  OK 
     *  1804 - assertIsTrue  "fully-qualified-domain@example.com"                                   =   0 =  OK 
     *  1805 - assertIsTrue  "user.name+tag+sorting@example.com"                                    =   0 =  OK 
     *  1806 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  1807 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                     =   0 =  OK 
     *  1808 - assertIsTrue  "x@example.com"                                                        =   0 =  OK 
     *  1809 - assertIsTrue  "info@firma.org"                                                       =   0 =  OK 
     *  1810 - assertIsTrue  "example-indeed@strange-example.com"                                   =   0 =  OK 
     *  1811 - assertIsTrue  "admin@mailserver1"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1812 - assertIsTrue  "example@s.example"                                                    =   0 =  OK 
     *  1813 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  1814 - assertIsTrue  "\"john..doe\"@example.org"                                            =   1 =  OK 
     *  1815 - assertIsTrue  "mailhost!username@example.org"                                        =   0 =  OK 
     *  1816 - assertIsTrue  "user%example.com@example.org"                                         =   0 =  OK 
     *  1817 - assertIsTrue  "joe25317@NOSPAMexample.com"                                           =   0 =  OK 
     *  1818 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  1819 - assertIsTrue  "nama@contoh.com"                                                      =   0 =  OK 
     *  1820 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                           =   0 =  OK 
     *  1821 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1822 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1823 - assertIsFalse "Abc..123@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1824 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1825 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1826 - assertIsFalse "just\"not\"right@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1827 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1828 - assertIsFalse "this\ still\\"not\\allowed@example.com"                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1829 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1830 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"   =   0 =  OK 
     * 
     * 
     * ---- https://github.com/egulias/EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1831 - assertIsFalse "nolocalpart.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1832 - assertIsFalse "test@example.com test"                                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1833 - assertIsFalse "user  name@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1834 - assertIsFalse "user   name@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1835 - assertIsFalse "example.@example.co.uk"                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1836 - assertIsFalse "example@example@example.co.uk"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1837 - assertIsFalse "(test_exampel@example.fr}"                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1838 - assertIsFalse "example(example)example@example.co.uk"                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1839 - assertIsFalse ".example@localhost"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1840 - assertIsFalse "ex\ample@localhost"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1841 - assertIsFalse "example@local\host"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1842 - assertIsFalse "example@localhost."                                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1843 - assertIsFalse "user name@example.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1844 - assertIsFalse "username@ example . com"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1845 - assertIsFalse "example@(fake}.com"                                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1846 - assertIsFalse "example@(fake.com"                                                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *  1847 - assertIsTrue  "username@example.com"                                                 =   0 =  OK 
     *  1848 - assertIsTrue  "usern.ame@example.com"                                                =   0 =  OK 
     *  1849 - assertIsFalse "user[na]me@example.com"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1850 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1851 - assertIsFalse "\"\\"@iana.org"                                                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1852 - assertIsFalse "\"test\"test@iana.org"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1853 - assertIsFalse "\"test\"\"test\"@iana.org"                                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1854 - assertIsTrue  "\"test\".\"test\"@iana.org"                                           =   1 =  OK 
     *  1855 - assertIsTrue  "\"test\".test@iana.org"                                               =   1 =  OK 
     *  1856 - assertIsFalse "\"test\\"@iana.org"                                                   =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1857 - assertIsFalse "\r\ntest@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1858 - assertIsFalse "\r\n test@iana.org"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1859 - assertIsFalse "\r\n \r\ntest@iana.org"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1860 - assertIsFalse "\r\n \r\n test@iana.org"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1861 - assertIsFalse "test@iana.org \r\n"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1862 - assertIsFalse "test@iana.org \r\n "                                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1863 - assertIsFalse "test@iana.org \r\n \r\n"                                              = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1864 - assertIsFalse "test@iana.org \r\n\r\n"                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1865 - assertIsFalse "test@iana.org  \r\n\r\n "                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1866 - assertIsFalse "test@iana/icann.org"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1867 - assertIsFalse "test@foo;bar.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1868 - assertIsFalse "a@test.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1869 - assertIsFalse "comment)example@example.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1870 - assertIsFalse "comment(example))@example.com"                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1871 - assertIsFalse "example@example)comment.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1872 - assertIsFalse "example@example(comment)).com"                                        = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *  1873 - assertIsFalse "example@[1.2.3.4"                                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1874 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                        =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1875 - assertIsFalse "exam(ple@exam).ple"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1876 - assertIsFalse "example@(example))comment.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1877 - assertIsTrue  "example@example.com"                                                  =   0 =  OK 
     *  1878 - assertIsTrue  "example@example.co.uk"                                                =   0 =  OK 
     *  1879 - assertIsTrue  "example_underscore@example.fr"                                        =   0 =  OK 
     *  1880 - assertIsTrue  "exam'ple@example.com"                                                 =   0 =  OK 
     *  1881 - assertIsTrue  "exam\ ple@example.com"                                                =   0 =  OK 
     *  1882 - assertIsFalse "example((example))@fakedfake.co.uk"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1883 - assertIsFalse "example@faked(fake).co.uk"                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1884 - assertIsTrue  "example+@example.com"                                                 =   0 =  OK 
     *  1885 - assertIsTrue  "example@with-hyphen.example.com"                                      =   0 =  OK 
     *  1886 - assertIsTrue  "with-hyphen@example.com"                                              =   0 =  OK 
     *  1887 - assertIsTrue  "example@1leadingnum.example.com"                                      =   0 =  OK 
     *  1888 - assertIsTrue  "1leadingnum@example.com"                                              =   0 =  OK 
     *  1889 - assertIsTrue  "инфо@письмо.рф"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1890 - assertIsTrue  "\"username\"@example.com"                                             =   1 =  OK 
     *  1891 - assertIsTrue  "\"user.name\"@example.com"                                            =   1 =  OK 
     *  1892 - assertIsTrue  "\"user name\"@example.com"                                            =   1 =  OK 
     *  1893 - assertIsTrue  "\"user@name\"@example.com"                                            =   1 =  OK 
     *  1894 - assertIsFalse "\"\a\"@iana.org"                                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1895 - assertIsTrue  "\"test\ test\"@iana.org"                                              =   1 =  OK 
     *  1896 - assertIsFalse "\"\"@iana.org"                                                        =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1897 - assertIsFalse "\"\"@[]"                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1898 - assertIsTrue  "\"\\"\"@iana.org"                                                     =   1 =  OK 
     *  1899 - assertIsTrue  "example@localhost"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1900 - assertIsFalse " check@this.com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1901 - assertIsFalse " email@example.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1902 - assertIsTrue  "!def!xyz%abc@example.com"                                             =   0 =  OK 
     *  1903 - assertIsTrue  "!sd@gh.com"                                                           =   0 =  OK 
     *  1904 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                =   0 =  OK 
     *  1905 - assertIsTrue  "%2@gmail.com"                                                         =   0 =  OK 
     *  1906 - assertIsTrue  "--@ooo.ooo"                                                           =   0 =  OK 
     *  1907 - assertIsTrue  "-@bde.cc"                                                             =   0 =  OK 
     *  1908 - assertIsTrue  "-asd@das.com"                                                         =   0 =  OK 
     *  1909 - assertIsFalse "..@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1910 - assertIsFalse ".@s.dd"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1911 - assertIsFalse ".a@test.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1912 - assertIsFalse ".email@domain.com"                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1913 - assertIsFalse ".xxxx@mysite.org"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1914 - assertIsTrue  "1234567890@domain.com"                                                =   0 =  OK 
     *  1915 - assertIsFalse "123@$.xyz"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1916 - assertIsTrue  "1@domain.com"                                                         =   0 =  OK 
     *  1917 - assertIsTrue  "1@gmail.com"                                                          =   0 =  OK 
     *  1918 - assertIsTrue  "1_example@something.gmail.com"                                        =   0 =  OK 
     *  1919 - assertIsTrue  "2@bde.cc"                                                             =   0 =  OK 
     *  1920 - assertIsFalse "@b.com"                                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1921 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1922 - assertIsFalse "@you.me.net"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1923 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1924 - assertIsTrue  "Abc.123@example.com"                                                  =   0 =  OK 
     *  1925 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1926 - assertIsTrue  "Abc@10.42.0.1"                                                        =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1927 - assertIsTrue  "Abc@example.com"                                                      =   0 =  OK 
     *  1928 - assertIsFalse "Abc@example.com."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1929 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                 =   0 =  OK 
     *  1930 - assertIsFalse "Foobar Some@thing.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1931 - assertIsTrue  "Fred\ Bloggs@example.com"                                             =   0 =  OK 
     *  1932 - assertIsFalse "MailTo:casesensitve@domain.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1933 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                          =   0 =  OK 
     *  1934 - assertIsTrue  "\" \"@example.org"                                                    =   1 =  OK 
     *  1935 - assertIsTrue  "\"%2\"@gmail.com"                                                     =   1 =  OK 
     *  1936 - assertIsTrue  "\"Abc@def\"@example.com"                                              =   1 =  OK 
     *  1937 - assertIsTrue  "\"Abc\@def\"@example.com"                                             =   1 =  OK 
     *  1938 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                          =   1 =  OK 
     *  1939 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                         =   1 =  OK 
     *  1940 - assertIsFalse "\"Joe\Blow\"@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1941 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                            =   1 =  OK 
     *  1942 - assertIsTrue  "\"a..b\"@gmail.com"                                                   =   1 =  OK 
     *  1943 - assertIsTrue  "\"a_b\"@gmail.com"                                                    =   1 =  OK 
     *  1944 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                         =   1 =  OK 
     *  1945 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                  =   1 =  OK 
     *  1946 - assertIsTrue  "\"foo\@bar\"@Something.com"                                           =   1 =  OK 
     *  1947 - assertIsTrue  "\"j\\"s\"@proseware.com"                                              =   1 =  OK 
     *  1948 - assertIsTrue  "\"myemail@sa\"@mple.com"                                              =   1 =  OK 
     *  1949 - assertIsFalse "\$A12345@example.com"                                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1950 - assertIsTrue  "_-_@bde.cc"                                                           =   0 =  OK 
     *  1951 - assertIsFalse "_@bde.cc."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1952 - assertIsTrue  "_@gmail.com"                                                          =   0 =  OK 
     *  1953 - assertIsTrue  "_dasd@sd.com"                                                         =   0 =  OK 
     *  1954 - assertIsTrue  "_dasd_das_@9.com"                                                     =   0 =  OK 
     *  1955 - assertIsTrue  "_somename@example.com"                                                =   0 =  OK 
     *  1956 - assertIsTrue  "a+b@bde.cc"                                                           =   0 =  OK 
     *  1957 - assertIsTrue  "a+b@c.com"                                                            =   0 =  OK 
     *  1958 - assertIsTrue  "a-b@bde.cc"                                                           =   0 =  OK 
     *  1959 - assertIsFalse "a..b@bde.cc"                                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1960 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1961 - assertIsTrue  "a.a@test.com"                                                         =   0 =  OK 
     *  1962 - assertIsTrue  "a.b@com"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1963 - assertIsTrue  "a2@bde.cc"                                                            =   0 =  OK 
     *  1964 - assertIsFalse "a@.com"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1965 - assertIsTrue  "a@123.45.67.89"                                                       =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1966 - assertIsFalse "a@b."                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1967 - assertIsFalse "a@b.-de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1968 - assertIsFalse "a@b._de.cc"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1969 - assertIsTrue  "a@b.c.com"                                                            =   0 =  OK 
     *  1970 - assertIsTrue  "a@b.com"                                                              =   0 =  OK 
     *  1971 - assertIsTrue  "a@bc.com"                                                             =   0 =  OK 
     *  1972 - assertIsTrue  "a@bcom"                                                               =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1973 - assertIsFalse "a@bde-.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1974 - assertIsFalse "a@bde.cc."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1975 - assertIsFalse "a@bde_.cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1976 - assertIsTrue  "a@domain.com"                                                         =   0 =  OK 
     *  1977 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1978 - assertIsTrue  "ab@288.120.150.10.com"                                                =   0 =  OK 
     *  1979 - assertIsTrue  "ab@[120.254.254.120]"                                                 =   2 =  OK 
     *  1980 - assertIsFalse "ab@b+de.cc"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1981 - assertIsTrue  "ab@b-de.cc"                                                           =   0 =  OK 
     *  1982 - assertIsTrue  "ab@c.com"                                                             =   0 =  OK 
     *  1983 - assertIsFalse "ab@sd@dd"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1984 - assertIsTrue  "ab_c@bde.cc"                                                          =   0 =  OK 
     *  1985 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                       =   1 =  OK 
     *  1986 - assertIsTrue  "abc.xyz@gmail.com.in"                                                 =   0 =  OK 
     *  1987 - assertIsTrue  "abc123xyz@asdf.co.in"                                                 =   0 =  OK 
     *  1988 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                 =   0 =  OK 
     *  1989 - assertIsFalse "abc\"defghi\"xyz@example.com"                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1990 - assertIsFalse "abc\\"def\\"ghi@example.com"                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1991 - assertIsTrue  "abcxyz123@qwert.com"                                                  =   0 =  OK 
     *  1992 - assertIsTrue  "alex@example.com"                                                     =   0 =  OK 
     *  1993 - assertIsTrue  "alireza@test.co.uk"                                                   =   0 =  OK 
     *  1994 - assertIsFalse "as3d@dac.coas-"                                                       =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1995 - assertIsTrue  "asd-@asd.com"                                                         =   0 =  OK 
     *  1996 - assertIsFalse "asd@dasd@asd.cm"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1997 - assertIsFalse "check@this..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1998 - assertIsTrue  "check@this.com"                                                       =   0 =  OK 
     *  1999 - assertIsFalse "check@thiscom"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2000 - assertIsTrue  "cog@wheel.com"                                                        =   0 =  OK 
     *  2001 - assertIsTrue  "customer/department=shipping@example.com"                             =   0 =  OK 
     *  2002 - assertIsTrue  "d._.___d@gmail.com"                                                   =   0 =  OK 
     *  2003 - assertIsTrue  "d.j@server1.proseware.com"                                            =   0 =  OK 
     *  2004 - assertIsTrue  "d.oy.smith@gmail.com"                                                 =   0 =  OK 
     *  2005 - assertIsTrue  "d23d@da9.co9"                                                         =   0 =  OK 
     *  2006 - assertIsTrue  "d_oy_smith@gmail.com"                                                 =   0 =  OK 
     *  2007 - assertIsFalse "da23@das..com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2008 - assertIsFalse "dad@sds"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2009 - assertIsTrue  "dasd-dasd@das.com.das"                                                =   0 =  OK 
     *  2010 - assertIsTrue  "dasd.dadas@dasd.com"                                                  =   0 =  OK 
     *  2011 - assertIsTrue  "dasd_-@jdas.com"                                                      =   0 =  OK 
     *  2012 - assertIsFalse "dasddas-@.com"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2013 - assertIsTrue  "david.jones@proseware.com"                                            =   0 =  OK 
     *  2014 - assertIsTrue  "dda_das@das-dasd.com"                                                 =   0 =  OK 
     *  2015 - assertIsTrue  "doysmith@gmail.com"                                                   =   0 =  OK 
     *  2016 - assertIsTrue  "dsq!a?@das.com"                                                       =   0 =  OK 
     *  2017 - assertIsFalse "email..email@domain.com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2018 - assertIsFalse "email.@domain.com"                                                    =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2019 - assertIsFalse "email@.domain.com"                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2020 - assertIsFalse "email@domain"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2021 - assertIsFalse "email@domain..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2022 - assertIsTrue  "email@domain.co.de"                                                   =   0 =  OK 
     *  2023 - assertIsTrue  "email@domain.com"                                                     =   0 =  OK 
     *  2024 - assertIsFalse "email@domain.com."                                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2025 - assertIsFalse "email@example"                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2026 - assertIsFalse "email@example..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2027 - assertIsTrue  "email@example.co.uk"                                                  =   0 =  OK 
     *  2028 - assertIsFalse "email@example.co.uk."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2029 - assertIsFalse "email@example.com "                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2030 - assertIsTrue  "email@example.com"                                                    =   0 =  OK 
     *  2031 - assertIsTrue  "email@mail.gmail.com"                                                 =   0 =  OK 
     *  2032 - assertIsTrue  "email@subdomain.domain.com"                                           =   0 =  OK 
     *  2033 - assertIsTrue  "example@example.co"                                                   =   0 =  OK 
     *  2034 - assertIsTrue  "f.f.f@bde.cc"                                                         =   0 =  OK 
     *  2035 - assertIsFalse "fdsa"                                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2036 - assertIsFalse "fdsa@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2037 - assertIsFalse "fdsa@fdsa"                                                            =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2038 - assertIsFalse "fdsa@fdsa."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2039 - assertIsTrue  "first-name-last-name@d-a-n.com"                                       =   0 =  OK 
     *  2040 - assertIsTrue  "firstname+lastname@domain.com"                                        =   0 =  OK 
     *  2041 - assertIsTrue  "firstname-lastname@domain.com"                                        =   0 =  OK 
     *  2042 - assertIsTrue  "firstname.lastname@domain.com"                                        =   0 =  OK 
     *  2043 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2044 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2045 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                            =   0 =  OK 
     *  2046 - assertIsFalse "foo~&(&)(@bar.com"                                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2047 - assertIsTrue  "futureTLD@somewhere.fooo"                                             =   0 =  OK 
     *  2048 - assertIsFalse "hallo2ww22@example....caaaao"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2049 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2050 - assertIsTrue  "hello7___@ca.com.pt"                                                  =   0 =  OK 
     *  2051 - assertIsFalse "invalid.email.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2052 - assertIsFalse "invalid@email@domain.com"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2053 - assertIsFalse "j..s@proseware.com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2054 - assertIsFalse "j.@server1.proseware.com"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2055 - assertIsTrue  "j.s@server1.proseware.com"                                            =   0 =  OK 
     *  2056 - assertIsTrue  "j@proseware.com9"                                                     =   0 =  OK 
     *  2057 - assertIsTrue  "j_9@[129.126.118.1]"                                                  =   2 =  OK 
     *  2058 - assertIsTrue  "john.smith@example.com"                                               =   0 =  OK 
     *  2059 - assertIsTrue  "jones@ms1.proseware.com"                                              =   0 =  OK 
     *  2060 - assertIsTrue  "js#internal@proseware.com"                                            =   0 =  OK 
     *  2061 - assertIsTrue  "js*@proseware.com"                                                    =   0 =  OK 
     *  2062 - assertIsFalse "js@proseware..com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2063 - assertIsTrue  "js@proseware.com9"                                                    =   0 =  OK 
     *  2064 - assertIsFalse "mailto:email@domain.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2065 - assertIsTrue  "my.ownsite@ourearth.org"                                              =   0 =  OK 
     *  2066 - assertIsFalse "myemail@@sample.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2067 - assertIsFalse "myemail@sa@mple.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2068 - assertIsTrue  "myemail@sample"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2069 - assertIsTrue  "myemail@sample.com"                                                   =   0 =  OK 
     *  2070 - assertIsFalse "myemailsample.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2071 - assertIsTrue  "mysite@you.me.net"                                                    =   0 =  OK 
     *  2072 - assertIsTrue  "peter.example@yahoo.com.au"                                           =   0 =  OK 
     *  2073 - assertIsTrue  "peter_123@news.com"                                                   =   0 =  OK 
     *  2074 - assertIsTrue  "pio^_pio@factory.com"                                                 =   0 =  OK 
     *  2075 - assertIsTrue  "pio_#pio@factory.com"                                                 =   0 =  OK 
     *  2076 - assertIsFalse "pio_pio@#factory.com"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2077 - assertIsFalse "pio_pio@factory.c#om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2078 - assertIsFalse "pio_pio@factory.c*om"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2079 - assertIsTrue  "pio_pio@factory.com"                                                  =   0 =  OK 
     *  2080 - assertIsTrue  "pio_~pio@factory.com"                                                 =   0 =  OK 
     *  2081 - assertIsTrue  "piskvor@example.lighting"                                             =   0 =  OK 
     *  2082 - assertIsFalse "plain.address"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2083 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2084 - assertIsTrue  "someone+tag@somewhere.net"                                            =   0 =  OK 
     *  2085 - assertIsTrue  "someone@somewhere.co.uk"                                              =   0 =  OK 
     *  2086 - assertIsTrue  "someone@somewhere.com"                                                =   0 =  OK 
     *  2087 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2088 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2089 - assertIsFalse "this\ still\\"not\allowed@example.com"                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2090 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"       =   0 =  OK 
     *  2091 - assertIsTrue  "user+mailbox/department=shipping@example.com"                         =   0 =  OK 
     *  2092 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                              =   4 =  OK 
     *  2093 - assertIsTrue  "user@localserver"                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2094 - assertIsTrue  "w.b.f@test.com"                                                       =   0 =  OK 
     *  2095 - assertIsTrue  "w.b.f@test.museum"                                                    =   0 =  OK 
     *  2096 - assertIsFalse "xxxx..1234@yahoo.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2097 - assertIsFalse "xxxx.ourearth.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2098 - assertIsFalse "xxxx123@gmail.b"                                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2099 - assertIsFalse "xxxx@.com.my"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2100 - assertIsFalse "xxxx@.org.org"                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2101 - assertIsTrue  "xxxx@gmail.com"                                                       =   0 =  OK 
     *  2102 - assertIsFalse "xxxxx()*@gmail.com"                                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2103 - assertIsTrue  "xxxxxx@yahoo.com"                                                     =   0 =  OK 
     *  2104 - assertIsTrue  "yoursite@ourearth.com"                                                =   0 =  OK 
     *  2105 - assertIsTrue  "~pio_pio@factory.com"                                                 =   0 =  OK 
     *  2106 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                        =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2107 - assertIsTrue  "Who? <one@y.test>"                                                    =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2108 - assertIsTrue  "<boss@nil.test>"                                                      =   0 =  OK 
     *  2109 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                   =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *  2110 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2111 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                           =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2112 - assertIsTrue  "\"back\slash\"@sld.com"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2113 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                          =   1 =  OK 
     *  2114 - assertIsTrue  "\"quoted\"@sld.com"                                                   =   1 =  OK 
     *  2115 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                   =   1 =  OK 
     *  2116 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                   =   0 =  OK 
     *  2117 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                  =   0 =  OK 
     *  2118 - assertIsTrue  "01234567890@numbers-in-local.net"                                     =   0 =  OK 
     *  2119 - assertIsTrue  "a@single-character-in-local.org"                                      =   0 =  OK 
     *  2120 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *  2121 - assertIsTrue  "backticksarelegit@test.com"                                           =   0 =  OK 
     *  2122 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                           =   2 =  OK 
     *  2123 - assertIsTrue  "country-code-tld@sld.rw"                                              =   0 =  OK 
     *  2124 - assertIsTrue  "country-code-tld@sld.uk"                                              =   0 =  OK 
     *  2125 - assertIsTrue  "letters-in-sld@123.com"                                               =   0 =  OK 
     *  2126 - assertIsTrue  "local@dash-in-sld.com"                                                =   0 =  OK 
     *  2127 - assertIsTrue  "local@sld.newTLD"                                                     =   0 =  OK 
     *  2128 - assertIsTrue  "local@sub.domains.com"                                                =   0 =  OK 
     *  2129 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                     =   0 =  OK 
     *  2130 - assertIsTrue  "one-character-third-level@a.example.com"                              =   0 =  OK 
     *  2131 - assertIsTrue  "one-letter-sld@x.org"                                                 =   0 =  OK 
     *  2132 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                             =   0 =  OK 
     *  2133 - assertIsTrue  "single-character-in-sld@x.org"                                        =   0 =  OK 
     *  2134 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  2135 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-length-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  2136 - assertIsTrue  "uncommon-tld@sld.mobi"                                                =   0 =  OK 
     *  2137 - assertIsTrue  "uncommon-tld@sld.museum"                                              =   0 =  OK 
     *  2138 - assertIsTrue  "uncommon-tld@sld.travel"                                              =   0 =  OK 
     *  2139 - assertIsFalse "invalid"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2140 - assertIsFalse "invalid@"                                                             =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2141 - assertIsFalse "invalid @"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2142 - assertIsFalse "invalid@[555.666.777.888]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2143 - assertIsFalse "invalid@[IPv6:123456]"                                                =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2144 - assertIsFalse "invalid@[127.0.0.1.]"                                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  2145 - assertIsFalse "invalid@[127.0.0.1]."                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2146 - assertIsFalse "invalid@[127.0.0.1]x"                                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2147 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2148 - assertIsFalse "@missing-local.org"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2149 - assertIsFalse "IP-and-port@127.0.0.1:25"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2150 - assertIsFalse "another-invalid-ip@127.0.0.256"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2151 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2152 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2153 - assertIsFalse "invalid-ip@127.0.0.1.26"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2154 - assertIsFalse "local-ends-with-dot.@sld.com"                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2155 - assertIsFalse "missing-at-sign.net"                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2156 - assertIsFalse "missing-sld@.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2157 - assertIsFalse "missing-tld@sld."                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2158 - assertIsFalse "sld-ends-with-dash@sld-.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2159 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2160 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2161 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2162 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-255-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bl.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2163 - assertIsFalse "two..consecutive-dots@sld.com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2164 - assertIsFalse "unbracketed-IP@127.0.0.1"                                             =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2165 - assertIsFalse "underscore.error@example.com_"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2166 - assertIsTrue  "first.last@iana.org"                                                  =   0 =  OK 
     *  2167 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *  2168 - assertIsTrue  "\"first\\"last\"@iana.org"                                            =   1 =  OK 
     *  2169 - assertIsTrue  "\"first@last\"@iana.org"                                              =   1 =  OK 
     *  2170 - assertIsTrue  "\"first\\last\"@iana.org"                                             =   1 =  OK 
     *  2171 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  2172 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  2173 - assertIsTrue  "first.last@[12.34.56.78]"                                             =   2 =  OK 
     *  2174 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                    =   4 =  OK 
     *  2175 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                     =   4 =  OK 
     *  2176 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                    =   4 =  OK 
     *  2177 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                    =   4 =  OK 
     *  2178 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"            =   4 =  OK 
     *  2179 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  2180 - assertIsTrue  "first.last@3com.com"                                                  =   0 =  OK 
     *  2181 - assertIsTrue  "first.last@123.iana.org"                                              =   0 =  OK 
     *  2182 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2183 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                =   4 =  OK 
     *  2184 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                =   1 =  OK 
     *  2185 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                            =   1 =  OK 
     *  2186 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                              =   1 =  OK 
     *  2187 - assertIsTrue  "\"Abc@def\"@iana.org"                                                 =   1 =  OK 
     *  2188 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                           =   1 =  OK 
     *  2189 - assertIsTrue  "user+mailbox@iana.org"                                                =   0 =  OK 
     *  2190 - assertIsTrue  "$A12345@iana.org"                                                     =   0 =  OK 
     *  2191 - assertIsTrue  "!def!xyz%abc@iana.org"                                                =   0 =  OK 
     *  2192 - assertIsTrue  "_somename@iana.org"                                                   =   0 =  OK 
     *  2193 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2194 - assertIsTrue  "peter.piper@iana.org"                                                 =   0 =  OK 
     *  2195 - assertIsTrue  "test@iana.org"                                                        =   0 =  OK 
     *  2196 - assertIsTrue  "TEST@iana.org"                                                        =   0 =  OK 
     *  2197 - assertIsTrue  "1234567890@iana.org"                                                  =   0 =  OK 
     *  2198 - assertIsTrue  "test+test@iana.org"                                                   =   0 =  OK 
     *  2199 - assertIsTrue  "test-test@iana.org"                                                   =   0 =  OK 
     *  2200 - assertIsTrue  "t*est@iana.org"                                                       =   0 =  OK 
     *  2201 - assertIsTrue  "+1~1+@iana.org"                                                       =   0 =  OK 
     *  2202 - assertIsTrue  "{_test_}@iana.org"                                                    =   0 =  OK 
     *  2203 - assertIsTrue  "test.test@iana.org"                                                   =   0 =  OK 
     *  2204 - assertIsTrue  "\"test.test\"@iana.org"                                               =   1 =  OK 
     *  2205 - assertIsTrue  "test.\"test\"@iana.org"                                               =   1 =  OK 
     *  2206 - assertIsTrue  "\"test@test\"@iana.org"                                               =   1 =  OK 
     *  2207 - assertIsTrue  "test@123.123.123.x123"                                                =   0 =  OK 
     *  2208 - assertIsFalse "test@123.123.123.123"                                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2209 - assertIsTrue  "test@[123.123.123.123]"                                               =   2 =  OK 
     *  2210 - assertIsTrue  "test@example.iana.org"                                                =   0 =  OK 
     *  2211 - assertIsTrue  "test@example.example.iana.org"                                        =   0 =  OK 
     *  2212 - assertIsTrue  "customer/department@iana.org"                                         =   0 =  OK 
     *  2213 - assertIsTrue  "_Yosemite.Sam@iana.org"                                               =   0 =  OK 
     *  2214 - assertIsTrue  "~@iana.org"                                                           =   0 =  OK 
     *  2215 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                           =   1 =  OK 
     *  2216 - assertIsTrue  "Ima.Fool@iana.org"                                                    =   0 =  OK 
     *  2217 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                =   1 =  OK 
     *  2218 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                              =   1 =  OK 
     *  2219 - assertIsTrue  "\"first\".\"last\"@iana.org"                                          =   1 =  OK 
     *  2220 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                   =   1 =  OK 
     *  2221 - assertIsTrue  "\"first\".last@iana.org"                                              =   1 =  OK 
     *  2222 - assertIsTrue  "first.\"last\"@iana.org"                                              =   1 =  OK 
     *  2223 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                               =   1 =  OK 
     *  2224 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2225 - assertIsTrue  "\"first.middle.last\"@iana.org"                                       =   1 =  OK 
     *  2226 - assertIsTrue  "\"first..last\"@iana.org"                                             =   1 =  OK 
     *  2227 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                   =   1 =  OK 
     *  2228 - assertIsFalse "first.last @iana.orgin"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2229 - assertIsTrue  "\"test blah\"@iana.orgin"                                             =   1 =  OK 
     *  2230 - assertIsTrue  "name.lastname@domain.com"                                             =   0 =  OK 
     *  2231 - assertIsTrue  "a@bar.com"                                                            =   0 =  OK 
     *  2232 - assertIsTrue  "aaa@[123.123.123.123]"                                                =   2 =  OK 
     *  2233 - assertIsTrue  "a-b@bar.com"                                                          =   0 =  OK 
     *  2234 - assertIsFalse "+@b.c"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2235 - assertIsTrue  "+@b.com"                                                              =   0 =  OK 
     *  2236 - assertIsTrue  "a@b.co-foo.uk"                                                        =   0 =  OK 
     *  2237 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                   =   1 =  OK 
     *  2238 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                   =   1 =  OK 
     *  2239 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                     =   0 =  OK 
     *  2240 - assertIsFalse "foobar@192.168.0.1"                                                   =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2241 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                    =   6 =  OK 
     *  2242 - assertIsTrue  "user%uucp!path@berkeley.edu"                                          =   0 =  OK 
     *  2243 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                            =   0 =  OK 
     *  2244 - assertIsTrue  "test@test.com"                                                        =   0 =  OK 
     *  2245 - assertIsTrue  "test@xn--example.com"                                                 =   0 =  OK 
     *  2246 - assertIsTrue  "test@example.com"                                                     =   0 =  OK 
     *  2247 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2248 - assertIsTrue  "first\@last@iana.org"                                                 =   0 =  OK 
     *  2249 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                             =   0 =  OK 
     *  2250 - assertIsFalse "first.last@example.123"                                               =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2251 - assertIsFalse "first.last@comin"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2252 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                            =   1 =  OK 
     *  2253 - assertIsTrue  "Abc\@def@iana.org"                                                    =   0 =  OK 
     *  2254 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                =   0 =  OK 
     *  2255 - assertIsFalse "Joe.\Blow@iana.org"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2256 - assertIsTrue  "first.last@sub.do.com"                                                =   0 =  OK 
     *  2257 - assertIsFalse "first.last"                                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2258 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                      =   0 =  OK 
     *  2259 - assertIsTrue  "\"hello world\"@example.com"                                          =   1 =  OK 
     *  2260 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2261 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                         =   1 =  OK 
     *  2262 - assertIsTrue  "example+tag@gmail.com"                                                =   0 =  OK 
     *  2263 - assertIsFalse ".ann..other.@example.com"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2264 - assertIsTrue  "ann.other@example.com"                                                =   0 =  OK 
     *  2265 - assertIsTrue  "something@something.something"                                        =   0 =  OK 
     *  2266 - assertIsTrue  "c@(Chris's host.)public.examplein"                                    =   6 =  OK 
     *  2267 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2268 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2269 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2270 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2271 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2272 - assertIsFalse "first().last@iana.orgin"                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2273 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2274 - assertIsFalse "jdoe@machine(comment). examplein"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2275 - assertIsFalse "first(abc.def).last@iana.orgin"                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2276 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2277 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2278 - assertIsFalse "first(abc\(def)@iana.orgin"                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2279 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2280 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2281 - assertIsFalse "1234 @ local(blah) .machine .examplein"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2282 - assertIsFalse "a@bin"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2283 - assertIsFalse "a@barin"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2284 - assertIsFalse "@about.museum"                                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2285 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2286 - assertIsFalse ".first.last@iana.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2287 - assertIsFalse "first.last.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2288 - assertIsFalse "first..last@iana.org"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2289 - assertIsFalse "\"first\"last\"@iana.org"                                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2290 - assertIsFalse "first.last@"                                                          =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  2291 - assertIsFalse "first.last@-xample.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2292 - assertIsFalse "first.last@exampl-.com"                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2293 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2294 - assertIsFalse "abc\@iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2295 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2296 - assertIsFalse "abc@def@iana.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2297 - assertIsFalse "@iana.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2298 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2299 - assertIsFalse "\"qu@iana.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2300 - assertIsFalse "ote\"@iana.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2301 - assertIsFalse ".dot@iana.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2302 - assertIsFalse "dot.@iana.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2303 - assertIsFalse "two..dot@iana.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2304 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2305 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2306 - assertIsFalse "hello world@iana.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2307 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2308 - assertIsFalse "test.iana.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2309 - assertIsFalse "test.@iana.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2310 - assertIsFalse "test..test@iana.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2311 - assertIsFalse ".test@iana.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2312 - assertIsFalse "test@test@iana.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2313 - assertIsFalse "test@@iana.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2314 - assertIsFalse "-- test --@iana.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2315 - assertIsFalse "[test]@iana.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2316 - assertIsFalse "\"test\"test\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2317 - assertIsFalse "()[]\;:.><@iana.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2318 - assertIsFalse "test@."                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2319 - assertIsFalse "test@example."                                                        =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2320 - assertIsFalse "test@.org"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2321 - assertIsFalse "test@[123.123.123.123"                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2322 - assertIsFalse "test@123.123.123.123]"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2323 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2324 - assertIsFalse "@NotAnEmail"                                                          =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2325 - assertIsFalse "\"test\"blah\"@iana.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2326 - assertIsFalse ".wooly@iana.org"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2327 - assertIsFalse "wo..oly@iana.org"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2328 - assertIsFalse "pootietang.@iana.org"                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2329 - assertIsFalse ".@iana.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2330 - assertIsFalse "Ima Fool@iana.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2331 - assertIsFalse "foo@[\1.2.3.4]"                                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2332 - assertIsFalse "first.\"\".last@iana.org"                                             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2333 - assertIsFalse "first\last@iana.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2334 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2335 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2336 - assertIsFalse "cal(foo(bar)@iamcal.com"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2337 - assertIsFalse "cal(foo)bar)@iamcal.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2338 - assertIsFalse "cal(foo\)@iamcal.com"                                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2339 - assertIsFalse "first(middle)last@iana.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2340 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2341 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2342 - assertIsFalse ".@"                                                                   =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2343 - assertIsFalse "@bar.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2344 - assertIsFalse "@@bar.com"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2345 - assertIsFalse "aaa.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2346 - assertIsFalse "aaa@.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2347 - assertIsFalse "aaa@.123"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2348 - assertIsFalse "aaa@[123.123.123.123]a"                                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  2349 - assertIsFalse "aaa@[123.123.123.333]"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2350 - assertIsFalse "a@bar.com."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  2351 - assertIsFalse "a@-b.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2352 - assertIsFalse "a@b-.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2353 - assertIsFalse "-@..com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2354 - assertIsFalse "-@a..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2355 - assertIsFalse "@about.museum-"                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2356 - assertIsFalse "test@...........com"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2357 - assertIsFalse "first.last@[IPv6::]"                                                  =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2358 - assertIsFalse "first.last@[IPv6::::]"                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2359 - assertIsFalse "first.last@[IPv6::b4]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2360 - assertIsFalse "first.last@[IPv6::::b4]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2361 - assertIsFalse "first.last@[IPv6::b3:b4]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2362 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2363 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                            =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2364 - assertIsFalse "first.last@[IPv6:a1:]"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  2365 - assertIsFalse "first.last@[IPv6:a1:::]"                                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2366 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                             =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2367 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2368 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2369 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2370 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2371 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2372 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2373 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2374 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                  =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2375 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2376 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2377 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2378 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                  =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  2379 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2380 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2381 - assertIsFalse "first.last@[IPv6::a2::b4]"                                            =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2382 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2383 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2384 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2385 - assertIsFalse "first.last@[.12.34.56.78]"                                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  2386 - assertIsFalse "first.last@[12.34.56.789]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2387 - assertIsFalse "first.last@[::12.34.56.78]"                                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2388 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2389 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                      =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  2390 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2391 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2392 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"       =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2393 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2394 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2395 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                         =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  2396 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2397 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2398 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2399 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2400 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2401 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                             =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  2402 - assertIsTrue  "first.last@[IPv6:::]"                                                 =   4 =  OK 
     *  2403 - assertIsTrue  "first.last@[IPv6:::b4]"                                               =   4 =  OK 
     *  2404 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                            =   4 =  OK 
     *  2405 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                             =   4 =  OK 
     *  2406 - assertIsTrue  "first.last@[IPv6:a1::]"                                               =   4 =  OK 
     *  2407 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                            =   4 =  OK 
     *  2408 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                              =   4 =  OK 
     *  2409 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                              =   4 =  OK 
     *  2410 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2411 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2412 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2413 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2414 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                    =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2415 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2416 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2417 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2418 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2419 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  OK 
     * 
     * 
     * ---- https://www.rohannagar.com/jmail/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2420 - assertIsFalse "\"qu@test.org"                                                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2421 - assertIsFalse "ote\"@test.org"                                                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2422 - assertIsFalse "\"().:;<>[\]@example.com"                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2423 - assertIsFalse "\"\"\"@iana.org"                                                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  2424 - assertIsFalse "Abc.example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2425 - assertIsFalse "A@b@c@example.com"                                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2426 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2427 - assertIsFalse "this is\"not\allowed@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2428 - assertIsFalse "this\ still\"not\allowed@example.com"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2429 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  2430 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2431 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2432 - assertIsFalse "plainaddress"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2433 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2434 - assertIsFalse ".email@example.com"                                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2435 - assertIsFalse "email.@example.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2436 - assertIsFalse "email..email@example.com"                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2437 - assertIsFalse "email@-example.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2438 - assertIsFalse "email@111.222.333.44444"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  2439 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                            =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2440 - assertIsFalse "email@[12.34.44.56"                                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  2441 - assertIsFalse "email@14.44.56.34]"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2442 - assertIsFalse "email@[1.1.23.5f]"                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2443 - assertIsFalse "email@[3.256.255.23]"                                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  2444 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2445 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2446 - assertIsFalse "first\@last@iana.org"                                                 =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  2447 - assertIsFalse "test@example.com "                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2448 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                 =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  2449 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  2450 - assertIsFalse "invalid@about.museum-"                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2451 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2452 - assertIsFalse "abc@def@test.org"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2453 - assertIsTrue  "abc\@def@test.org"                                                    =   0 =  OK 
     *  2454 - assertIsFalse "abc\@test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2455 - assertIsFalse "@test.org"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2456 - assertIsFalse ".dot@test.org"                                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2457 - assertIsFalse "dot.@test.org"                                                        =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2458 - assertIsFalse "two..dot@test.org"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2459 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2460 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2461 - assertIsFalse "hello world@test.org"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2462 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2463 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2464 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2465 - assertIsFalse "test.test.org"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2466 - assertIsFalse "test.@test.org"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2467 - assertIsFalse "test..test@test.org"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2468 - assertIsFalse ".test@test.org"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2469 - assertIsFalse "test@test@test.org"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2470 - assertIsFalse "test@@test.org"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2471 - assertIsFalse "-- test --@test.org"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2472 - assertIsFalse "[test]@test.org"                                                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2473 - assertIsFalse "\"test\"test\"@test.org"                                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2474 - assertIsFalse "()[]\;:.><@test.org"                                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  2475 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2476 - assertIsFalse ".@test.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2477 - assertIsFalse "Ima Fool@test.org"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2478 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2479 - assertIsFalse "foo@[.2.3.4]"                                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  2480 - assertIsFalse "first\last@test.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2481 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2482 - assertIsFalse "first(middle)last@test.org"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2483 - assertIsFalse "\"test\"test@test.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2484 - assertIsFalse "()@test.com"                                                          =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  2485 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  2486 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  2487 - assertIsFalse "invalid@[1]"                                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  2488 - assertIsFalse "ä📧@-foo"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2489 - assertIsFalse "ä📧@foo-"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2490 - assertIsFalse "first(comment(inner@comment.com"                                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2491 - assertIsFalse "Joe A Smith <email@example.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2492 - assertIsFalse "Joe A Smith email@example.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2493 - assertIsFalse "Joe A Smith <email@example.com->"                                     =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  2494 - assertIsFalse "Joe A Smith <email@-example.com->"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  2495 - assertIsFalse "Joe A Smith <email>"                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2496 - assertIsTrue  "\"email\"@example.com"                                                =   1 =  OK 
     *  2497 - assertIsTrue  "\"first@last\"@test.org"                                              =   1 =  OK 
     *  2498 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                           =   1 =  OK 
     *  2499 - assertIsTrue  "\"first\"last\"@test.org"                                             =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2500 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                   =   1 =  OK 
     *  2501 - assertIsTrue  "\"first\last\"@test.org"                                              =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2502 - assertIsTrue  "\"Abc\@def\"@test.org"                                                =   1 =  OK 
     *  2503 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                            =   1 =  OK 
     *  2504 - assertIsTrue  "\"Joe.\Blow\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2505 - assertIsTrue  "\"Abc@def\"@test.org"                                                 =   1 =  OK 
     *  2506 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                             =   1 =  OK 
     *  2507 - assertIsTrue  "\"Doug \"Ace\" L.\"@test.org"                                         =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2508 - assertIsTrue  "\"[[ test ]]\"@test.org"                                              =   1 =  OK 
     *  2509 - assertIsTrue  "\"test.test\"@test.org"                                               =   1 =  OK 
     *  2510 - assertIsTrue  "test.\"test\"@test.org"                                               =   1 =  OK 
     *  2511 - assertIsTrue  "\"test@test\"@test.org"                                               =   1 =  OK 
     *  2512 - assertIsTrue  "\"test  est\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2513 - assertIsTrue  "\"first\".\"last\"@test.org"                                          =   1 =  OK 
     *  2514 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                   =   1 =  OK 
     *  2515 - assertIsTrue  "\"first\".last@test.org"                                              =   1 =  OK 
     *  2516 - assertIsTrue  "first.\"last\"@test.org"                                              =   1 =  OK 
     *  2517 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                               =   1 =  OK 
     *  2518 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                   =   1 =  OK 
     *  2519 - assertIsTrue  "\"first.middle.last\"@test.org"                                       =   1 =  OK 
     *  2520 - assertIsTrue  "\"first..last\"@test.org"                                             =   1 =  OK 
     *  2521 - assertIsTrue  "\"Unicode NULL \"@char.com"                                           =   1 =  OK 
     *  2522 - assertIsTrue  "\"test\blah\"@test.org"                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  2523 - assertIsTrue  "\"testlah\"@test.org"                                                =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  2524 - assertIsTrue  "\"test\"blah\"@test.org"                                              =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2525 - assertIsTrue  "\"first\\"last\"@test.org"                                            =   1 =  OK 
     *  2526 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                     =   1 =  OK 
     *  2527 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2528 - assertIsTrue  "\"test blah\"@test.org"                                               =   1 =  OK 
     *  2529 - assertIsTrue  "first.last@test.org"                                                  =   0 =  OK 
     *  2530 - assertIsFalse "jdoe@machine(comment).example"                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  2531 - assertIsTrue  "first.\"\".last@test.org"                                             =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2532 - assertIsTrue  "\"\"@test.org"                                                        =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  2533 - assertIsTrue  "very.common@example.org"                                              =   0 =  OK 
     *  2534 - assertIsTrue  "test/test@test.com"                                                   =   0 =  OK 
     *  2535 - assertIsTrue  "user-@example.org"                                                    =   0 =  OK 
     *  2536 - assertIsTrue  "firstname.lastname@example.com"                                       =   0 =  OK 
     *  2537 - assertIsTrue  "email@subdomain.example.com"                                          =   0 =  OK 
     *  2538 - assertIsTrue  "firstname+lastname@example.com"                                       =   0 =  OK 
     *  2539 - assertIsTrue  "1234567890@example.com"                                               =   0 =  OK 
     *  2540 - assertIsTrue  "email@example-one.com"                                                =   0 =  OK 
     *  2541 - assertIsTrue  "_______@example.com"                                                  =   0 =  OK 
     *  2542 - assertIsTrue  "email@example.name"                                                   =   0 =  OK 
     *  2543 - assertIsTrue  "email@example.museum"                                                 =   0 =  OK 
     *  2544 - assertIsTrue  "email@example.co.jp"                                                  =   0 =  OK 
     *  2545 - assertIsTrue  "firstname-lastname@example.com"                                       =   0 =  OK 
     *  2546 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  2547 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2548 - assertIsTrue  "first.last@123.test.org"                                              =   0 =  OK 
     *  2549 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  2550 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org" =   0 =  OK 
     *  2551 - assertIsTrue  "user+mailbox@test.org"                                                =   0 =  OK 
     *  2552 - assertIsTrue  "customer/department=shipping@test.org"                                =   0 =  OK 
     *  2553 - assertIsTrue  "$A12345@test.org"                                                     =   0 =  OK 
     *  2554 - assertIsTrue  "!def!xyz%abc@test.org"                                                =   0 =  OK 
     *  2555 - assertIsTrue  "_somename@test.org"                                                   =   0 =  OK 
     *  2556 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                      =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  2557 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2558 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"          =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2559 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  2560 - assertIsTrue  "+@b.c"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2561 - assertIsTrue  "TEST@test.org"                                                        =   0 =  OK 
     *  2562 - assertIsTrue  "1234567890@test.org"                                                  =   0 =  OK 
     *  2563 - assertIsTrue  "test-test@test.org"                                                   =   0 =  OK 
     *  2564 - assertIsTrue  "t*est@test.org"                                                       =   0 =  OK 
     *  2565 - assertIsTrue  "+1~1+@test.org"                                                       =   0 =  OK 
     *  2566 - assertIsTrue  "{_test_}@test.org"                                                    =   0 =  OK 
     *  2567 - assertIsTrue  "valid@about.museum"                                                   =   0 =  OK 
     *  2568 - assertIsTrue  "a@bar"                                                                =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  2569 - assertIsTrue  "cal(foo\@bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2570 - assertIsTrue  "(comment)test@test.org"                                               =   6 =  OK 
     *  2571 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                  =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2572 - assertIsTrue  "cal(foo\)bar)@iamcal.com"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2573 - assertIsTrue  "cal(woo(yay)hoopla)@iamcal.com"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2574 - assertIsTrue  "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"   =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2575 - assertIsFalse "pete(his account)@silly.test(his host)"                               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  2576 - assertIsTrue  "first(abc\(def)@test.org"                                             =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  2577 - assertIsTrue  "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                     =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2578 - assertIsTrue  "c@(Chris's host.)public.example"                                      =   6 =  OK 
     *  2579 - assertIsTrue  "_Yosemite.Sam@test.org"                                               =   0 =  OK 
     *  2580 - assertIsTrue  "~@test.org"                                                           =   0 =  OK 
     *  2581 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                        =   6 =  OK 
     *  2582 - assertIsTrue  "test@Bücher.ch"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2583 - assertIsTrue  "あいうえお@example.com"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2584 - assertIsTrue  "Pelé@example.com"                                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2585 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2586 - assertIsTrue  "我買@屋企.香港"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2587 - assertIsTrue  "二ノ宮@黒川.日本"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2588 - assertIsTrue  "медведь@с-балалайкой.рф"                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2589 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2590 - assertIsTrue  "email@example.com (Joe Smith)"                                        =   6 =  OK 
     *  2591 - assertIsTrue  "cal@iamcal(woo).(yay)com"                                             = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2592 - assertIsTrue  "first(abc.def).last@test.org"                                         = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2593 - assertIsTrue  "first(a\"bc.def).last@test.org"                                       =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  2594 - assertIsTrue  "first.(\")middle.last(\")@test.org"                                   = 101 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  2595 - assertIsTrue  "first().last@test.org"                                                = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  2596 - assertIsTrue  "mymail\@hello@hotmail.com"                                            =   0 =  OK 
     *  2597 - assertIsTrue  "Abc\@def@test.org"                                                    =   0 =  OK 
     *  2598 - assertIsTrue  "Fred\ Bloggs@test.org"                                                =   0 =  OK 
     *  2599 - assertIsTrue  "Joe.\\Blow@test.org"                                                  =   0 =  OK 
     * 
     * 
     * ---- https://www.linuxjournal.com/article/9585 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2600 - assertIsTrue  "dclo@us.ibm.com"                                                      =   0 =  OK 
     *  2601 - assertIsTrue  "abc\@def@example.com"                                                 =   0 =  OK 
     *  2602 - assertIsTrue  "abc\\@example.com"                                                    =   0 =  OK 
     *  2603 - assertIsTrue  "Fred\ Bloggs@example.com"                                             =   0 =  OK 
     *  2604 - assertIsTrue  "Joe.\\Blow@example.com"                                               =   0 =  OK 
     *  2605 - assertIsTrue  "\"Abc@def\"@example.com"                                              =   1 =  OK 
     *  2606 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                          =   1 =  OK 
     *  2607 - assertIsTrue  "customer/department=shipping@example.com"                             =   0 =  OK 
     *  2608 - assertIsTrue  "$A12345@example.com"                                                  =   0 =  OK 
     *  2609 - assertIsTrue  "!def!xyz%abc@example.com"                                             =   0 =  OK 
     *  2610 - assertIsTrue  "_somename@example.com"                                                =   0 =  OK 
     *  2611 - assertIsTrue  "user+mailbox@example.com"                                             =   0 =  OK 
     *  2612 - assertIsTrue  "peter.piper@example.com"                                              =   0 =  OK 
     *  2613 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2614 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                    =   1 =  OK 
     *  2615 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                             =   0 =  OK 
     *  2616 - assertIsFalse "abc@def@example.com"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2617 - assertIsFalse "abc\\@def@example.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2618 - assertIsFalse "abc\@example.com"                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2619 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2620 - assertIsFalse "doug@"                                                                =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2621 - assertIsFalse "\"qu@example.com"                                                     =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  2622 - assertIsFalse "ote\"@example.com"                                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  2623 - assertIsFalse ".dot@example.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2624 - assertIsFalse "dot.@example.com"                                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2625 - assertIsFalse "two..dot@example.com"                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2626 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  2627 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  2628 - assertIsFalse "hello world@example.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2629 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                    =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     * 
     * 
     * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2630 - assertIsTrue  "jkt@gmail.com"                                                        =   0 =  OK 
     *  2631 - assertIsFalse " jkt@gmail.com"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2632 - assertIsFalse "jkt@ gmail.com"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2633 - assertIsFalse "jkt@g mail.com"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  2634 - assertIsFalse "jkt @gmail.com"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2635 - assertIsFalse "j kt@gmail.com"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * 
     * ---- https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2636 - assertIsTrue  "jinujawad6s@gmail.com"                                                =   0 =  OK 
     *  2637 - assertIsTrue  "drp@drp.cz"                                                           =   0 =  OK 
     *  2638 - assertIsTrue  "tvf@tvf.cz"                                                           =   0 =  OK 
     *  2639 - assertIsTrue  "info@ermaelan.com"                                                    =   0 =  OK 
     *  2640 - assertIsTrue  "begeddov@jfinity.com"                                                 =   0 =  OK 
     *  2641 - assertIsTrue  "vdv@dyomedea.com"                                                     =   0 =  OK 
     *  2642 - assertIsTrue  "me@aaronsw.com"                                                       =   0 =  OK 
     *  2643 - assertIsTrue  "aaron@theinfo.org"                                                    =   0 =  OK 
     *  2644 - assertIsTrue  "rss-dev@yahoogroups.com"                                              =   0 =  OK 
     * 
     * 
     * ---- https://www.journaldev.com/638/java-email-validation-regex ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2645 - assertIsTrue  "journaldev@yahoo.com"                                                 =   0 =  OK 
     *  2646 - assertIsTrue  "journaldev-100@yahoo.com"                                             =   0 =  OK 
     *  2647 - assertIsTrue  "journaldev.100@yahoo.com"                                             =   0 =  OK 
     *  2648 - assertIsTrue  "journaldev111@journaldev.com"                                         =   0 =  OK 
     *  2649 - assertIsTrue  "journaldev-100@journaldev.net"                                        =   0 =  OK 
     *  2650 - assertIsTrue  "journaldev.100@journaldev.com.au"                                     =   0 =  OK 
     *  2651 - assertIsTrue  "journaldev@1.com"                                                     =   0 =  OK 
     *  2652 - assertIsTrue  "journaldev@gmail.com.com"                                             =   0 =  OK 
     *  2653 - assertIsTrue  "journaldev+100@gmail.com"                                             =   0 =  OK 
     *  2654 - assertIsTrue  "journaldev-100@yahoo-test.com"                                        =   0 =  OK 
     *  2655 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                    =   0 =  OK 
     *  2656 - assertIsFalse "journaldev"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2657 - assertIsFalse "journaldev@.com.my"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2658 - assertIsFalse "journaldev123@gmail.a"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2659 - assertIsFalse "journaldev123@.com"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2660 - assertIsFalse "journaldev123@.com.com"                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  2661 - assertIsFalse ".journaldev@journaldev.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2662 - assertIsFalse "journaldev()*@gmail.com"                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  2663 - assertIsFalse "journaldev@%*.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2664 - assertIsFalse "journaldev..2002@gmail.com"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2665 - assertIsFalse "journaldev.@gmail.com"                                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  2666 - assertIsFalse "journaldev@journaldev@gmail.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  2667 - assertIsFalse "journaldev@gmail.com.1a"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * 
     * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2668 - assertIsTrue  "me@example.com"                                                       =   0 =  OK 
     *  2669 - assertIsTrue  "a.nonymous@example.com"                                               =   0 =  OK 
     *  2670 - assertIsTrue  "name+tag@example.com"                                                 =   0 =  OK 
     *  2671 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                       =   2 =  OK 
     *  2672 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"    =   4 =  OK 
     *  2673 - assertIsTrue  "me(this is a comment)@example.com"                                    =   6 =  OK 
     *  2674 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                            =   1 =  OK 
     *  2675 - assertIsTrue  "me.example@com"                                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2676 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                      =   0 =  OK 
     *  2677 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"  =   0 =  OK 
     *  2678 - assertIsFalse "NotAnEmail"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2679 - assertIsFalse "me@"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  2680 - assertIsFalse "@example.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  2681 - assertIsFalse ".me@example.com"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2682 - assertIsFalse "me@example..com"                                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2683 - assertIsFalse "me\@example.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2684 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2685 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  2686 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * 
     * ---- https://stackoverflow.com/questions/22993545/ruby-email-validation-with-regex?noredirect=1&lq=1 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2687 - assertIsTrue  "hello.me_1@email.com"                                                 =   0 =  OK 
     *  2688 - assertIsTrue  "something_valid@somewhere.tld"                                        =   0 =  OK 
     *  2689 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                            =   1 =  OK 
     *  2690 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                =   0 =  OK 
     *  2691 - assertIsFalse "foo.bar#gmail.co.u"                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  2692 - assertIsFalse "f...bar@gmail.com"                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  2693 - assertIsFalse "get_at_m.e@gmail"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2694 - assertIsFalse ".....@a...."                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  2695 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  2696 - assertIsFalse "a.b@example,com"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2697 - assertIsFalse "a.b@example,co.de"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2698 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2699 - assertIsTrue  "Ärger.Öde@Übel.de"                                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2700 - assertIsTrue  "Smørrebrød@danmark.dk"                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2701 - assertIsTrue  "ip.without.brackets@1.2.3.4"                                          =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  2702 - assertIsTrue  "ip.without.brackets@1:2:3:4:5:6:7:8"                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2703 - assertIsTrue  "(space after comment) john.smith@example.com"                         =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  2704 - assertIsTrue  "email.address.without@topleveldomain"                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  2705 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                   =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  2706 - assertIsTrue  "valid.email.from.nr951@fillup.tofalse.com"                            =   0 =  OK 
     *           ...
     *  2707 - assertIsTrue  "valid.email.to.nr1755@fillup.tofalse.com"                             =   0 =  OK 
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  1755   KORREKT 1672 =   95.271 % | FALSCH ERKANNT   83 =    4.729 % = Error 0
     *   ASSERT_IS_FALSE 1755   KORREKT 1743 =   99.316 % | FALSCH ERKANNT   12 =    0.684 % = Error 0
     * 
     *   GESAMT          3510   KORREKT 3415 =   97.293 % | FALSCH ERKANNT   95 =    2.707 % = Error 0
     * 
     * 
     *   Millisekunden    144 = 0.041025641025641026
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
      assertIsTrue( "name1+name2@domain1.tld" );
      assertIsTrue( "name1-name2@domain1.tld" );
      assertIsTrue( "name1.name2@subdomain1.domain1.tld" );

      assertIsTrue( "ip4.adress@[1.2.3.4]" );
      assertIsTrue( "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]" );

      assertIsTrue( "\"quote1\".name1@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"@domain1.tld" );

      assertIsTrue( "(comment1)name1@domain1.tld" );
      assertIsTrue( "name1(comment1)@domain1.tld" );
      assertIsTrue( "name1@(comment1)domain1.tld" );
      assertIsTrue( "name1@domain1.tld(comment1)" );

      assertIsTrue( "(comment1)\"quote1\".name1@domain1.tld" );
      assertIsTrue( "(comment1)name1.\"quote1\"@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"(comment1)@domain1.tld" );
      assertIsTrue( "\"quote1\".name1(comment1)@domain1.tld" );
      assertIsTrue( "name1.\"quote1\"@(comment1)domain1.tld" );
      assertIsTrue( "\"quote1\".name1@domain1.tld(comment1)" );

      assertIsTrue( "<name1.name2@domain1.tld>" );
      assertIsTrue( "name3 <name1.name2@domain1.tld>" );
      assertIsTrue( "<name1.name2@domain1.tld> name3" );
      assertIsTrue( "\"name3 name4\" <name1.name2@domain1.tld>" );
      assertIsTrue( "\"display name\" <(comment)local.part@domain-name.top_level_domain>" );

      assertIsTrue( "A@B.CD" );
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
      assertIsTrue( "a.b.c.d@domain.com" );

      wlHeadline( "No Input" );

      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "        " );

      wlHeadline( "AT-Character" );

      assertIsFalse( "1234567890" );
      assertIsFalse( "onlytextnodotsnoat" );
      assertIsFalse( "email.with.no.at.character" );
      assertIsFalse( "email.with.no.domain@" );
      assertIsFalse( "@.local.name.starts.with.at@domain.com" );
      assertIsFalse( "@no.local.email.part.domain.com" );
      assertIsFalse( "local.name.with@at@domain.com" );
      assertIsFalse( "local.name.ends.with.at@@domain.com" );
      assertIsFalse( "local.name.with.at.before@.point@domain.com" );
      assertIsFalse( "local.name.with.at.after.@point@domain.com" );
      assertIsFalse( "local.name.with.double.at@@test@domain.com" );
      assertIsFalse( "(comment @) local.name.with.comment.with.at@domain.com" );
      assertIsTrue( "\"quote@\".local.name.with.qoute.with.at@domain.com" );
      assertIsTrue( "qouted.\\@.character@at.domain.com" );
      assertIsTrue( "\\@@at.domain.com" );
      assertIsFalse( "@@at.domain.com" );
      assertIsFalse( "@at.domain.com" );
      assertIsFalse( "@@@@@@@at.domain.com" );
      assertIsTrue( "\\@.\\@.\\@.\\@.\\@.\\@@at.domain.com" );
      assertIsFalse( "\\@.\\@.\\@.\\@.\\@.\\@@at.sub\\@domain.com" );
      assertIsFalse( "@.@.@.@.@.@@at.domain.com" );
      assertIsFalse( "@.@.@." );
      assertIsFalse( "\\@.\\@@\\@.\\@" );
      assertIsFalse( "@" );
      assertIsFalse( "name @ <pointy.brackets1.with.at@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.at@domain.com> name @" );

      assertIsFalse( "ABCDEFGHIJKLMNOP" );
      assertIsFalse( "ABC.DEF.GHI.JKL" );
      assertIsFalse( "ABC.DEF@ GHI.JKL" );
      assertIsFalse( "ABC.DEF @GHI.JKL" );
      assertIsFalse( "ABC.DEF @ GHI.JKL" );
      assertIsFalse( "ABC.DEF@.@.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@" );
      assertIsFalse( "ABC.DEF@@GHI.JKL" );
      assertIsFalse( "ABC@DEF@GHI.JKL" );
      assertIsFalse( "@%^%#$@#$@#.com" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@domain@domain.com" );
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
      assertIsFalse( "(()(((@leftbracket.domain.com" );
      assertIsFalse( "((<)>(((@leftbracket.domain.com" );
      assertIsFalse( "(.(.(.(.(.(@leftbracket.domain.com" );
      assertIsTrue( "name ( <pointy.brackets1.with.leftbracket@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.leftbracket@domain.com> name (" );

      assertIsFalse( "\\.local.name.starts.with.slash@domain.com" );
      assertIsFalse( "local.name.ends.with.slash\\@domain.com" );
      assertIsFalse( "local.name.with.slash.before\\.point@domain.com" );
      assertIsFalse( "local.name.with.slash.after.\\point@domain.com" );
      assertIsTrue( "local.name.with.double.slash\\\\test@domain.com" );
      assertIsFalse( "(comment \\) local.name.with.comment.with.slash@domain.com" );
      assertIsFalse( "\"quote\\\".local.name.with.qoute.with.slash@domain.com" );
      assertIsFalse( "\\@slash.domain.com" );
      assertIsTrue( "\\\\\\\\\\\\@slash.domain.com" );
      assertIsFalse( "\\.\\.\\.\\.\\.\\@slash.domain.com" );
      assertIsFalse( "name \\ <pointy.brackets1.with.slash@domain.com>" );
      assertIsFalse( "<pointy.brackets2.with.slash@domain.com> name \\" );

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
      
      assertIsTrue( "999.local.name.starts.with.byte.overflow@domain.com" );
      assertIsTrue( "local.name.ends.with.byte.overflow999@domain.com" );
      assertIsTrue( "local.name.with.byte.overflow.before999.point@domain.com" );
      assertIsTrue( "local.name.with.byte.overflow.after.999point@domain.com" );
      assertIsTrue( "local.name.with.double.byte.overflow999999test@domain.com" );
      assertIsTrue( "(comment 999) local.name.with.comment.with.byte.overflow@domain.com" );
      assertIsTrue( "\"quote999\".local.name.with.qoute.with.byte.overflow@domain.com" );
      assertIsTrue( "999@byte.overflow.domain.com" );
      assertIsTrue( "999999999999999999@byte.overflow.domain.com" );
      assertIsTrue( "999.999.999.999.999.999@byte.overflow.domain.com" );
      assertIsTrue( "name 999 <pointy.brackets1.with.byte.overflow@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.byte.overflow@domain.com> name 999" );

      assertIsTrue( "\"str\".local.name.starts.with.string@domain.com" );
      assertIsFalse( "local.name.ends.with.string\"str\"@domain.com" );
      assertIsFalse( "local.name.with.string.before\"str\".point@domain.com" );
      assertIsFalse( "local.name.with.string.after.\"str\"point@domain.com" );
      assertIsFalse( "local.name.with.double.string\"str\"\"str\"test@domain.com" );
      assertIsFalse( "(comment \"str\") local.name.with.comment.with.string@domain.com" );
      assertIsFalse( "\"quote\"str\"\".local.name.with.qoute.with.string@domain.com" );
      assertIsTrue( "\"str\"@string.domain.com" );
      assertIsFalse( "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@string.domain.com" );
      assertIsTrue( "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com" );
      assertIsTrue( "name \"str\" <pointy.brackets1.with.string@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.string@domain.com> name \"str\"" );

      assertIsFalse( "(comment).local.name.starts.with.comment@domain.com" );
      assertIsTrue( "local.name.ends.with.comment(comment)@domain.com" );
      assertIsFalse( "local.name.with.comment.before(comment).point@domain.com" );
      assertIsFalse( "local.name.with.comment.after.(comment)point@domain.com" );
      assertIsFalse( "local.name.with.double.comment(comment)(comment)test@domain.com" );
      assertIsFalse( "(comment (comment)) local.name.with.comment.with.comment@domain.com" );
      assertIsTrue( "\"quote(comment)\".local.name.with.qoute.with.comment@domain.com" );
      assertIsFalse( "(comment)@comment.domain.com" );
      assertIsFalse( "(comment)(comment)(comment)(comment)(comment)(comment)@comment.domain.com" );
      assertIsFalse( "(comment).(comment).(comment).(comment).(comment).(comment)@comment.domain.com" );
      assertIsTrue( "name (comment) <pointy.brackets1.with.comment@domain.com>" );
      assertIsTrue( "<pointy.brackets2.with.comment@domain.com> name (comment)" );

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
            
      assertIsTrue( "domain.part@with999byte.overflow.com" );
      assertIsTrue( "domain.part@999with.byte.overflow.at.domain.start.com" );
      assertIsTrue( "domain.part@with.byte.overflow.at.domain.end1999.com" );
      assertIsTrue( "domain.part@with.byte.overflow.at.domain.end2.com999" );
      assertIsTrue( "domain.part@with.byte.overflow.before999.point.com" );
      assertIsTrue( "domain.part@with.byte.overflow.after.999point.com" );

      assertIsTrue( "domain.part@withxyzno.hex.number.com" );
      assertIsTrue( "domain.part@xyzwith.no.hex.number.at.domain.start.com" );
      assertIsTrue( "domain.part@with.no.hex.number.at.domain.end1xyz.com" );
      assertIsTrue( "domain.part@with.no.hex.number.at.domain.end2.comxyz" );
      assertIsTrue( "domain.part@with.no.hex.number.beforexyz.point.com" );
      assertIsTrue( "domain.part@with.no.hex.number.after.xyzpoint.com" );

      assertIsFalse( "domain.part@with\"str\"string.com" );
      assertIsFalse( "domain.part@\"str\"with.string.at.domain.start.com" );
      assertIsFalse( "domain.part@with.string.at.domain.end1\"str\".com" );
      assertIsFalse( "domain.part@with.string.at.domain.end2.com\"str\"" );
      assertIsFalse( "domain.part@with.string.before\"str\".point.com" );
      assertIsFalse( "domain.part@with.string.after.\"str\"point.com" );

      assertIsFalse( "domain.part@with(comment)comment.com" );
      assertIsTrue( "domain.part@(comment)with.comment.at.domain.start.com" );
      assertIsFalse( "domain.part@with.comment.at.domain.end1(comment).com" );
      assertIsTrue( "domain.part@with.comment.at.domain.end2.com(comment)" );
      assertIsFalse( "domain.part@with.comment.before(comment).point.com" );
      assertIsFalse( "domain.part@with.comment.after.(comment)point.com" );

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

      assertIsFalse( "domain.part@with\\slash.com" );
      assertIsFalse( "domain.part@\\with.slash.at.domain.start.com" );
      assertIsFalse( "domain.part@with.slash.at.domain.end1\\.com" );
      assertIsFalse( "domain.part@with.slash.at.domain.end2.com\\" );
      assertIsFalse( "domain.part@with.slash.before\\.point.com" );
      assertIsFalse( "domain.part@with.slash.after.\\point.com" );

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
      assertIsTrue( "#!$%&'*+-/=?^_`{}|~@eksample.org" );
      assertIsFalse( "eksample@#!$%&'*+-/=?^_`{}|~.org" );
      assertIsFalse( "(ohne !, passt das) $!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE" );
      assertIsFalse( "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2.4}" );
      assertIsTrue( "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de" );
      assertIsFalse( "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com" );

      wlHeadline( "IP V4" );

      assertIsFalse( "\"\"@[]" );
      assertIsFalse( "\"\"@[1" );
      assertIsFalse( "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" );
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
      assertIsFalse( "ABC.DEF@[{][})][}][}\\\"]" );
      assertIsFalse( "ABC.DEF@[....]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF[@1.2.3.4]" );
      assertIsTrue( "\"[1.2.3.4]\"@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[1.00002.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.456]" );
      assertIsFalse( "ABC.DEF@[..]" );
      assertIsFalse( "ABC.DEF@[.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1]" );
      assertIsFalse( "ABC.DEF@[1.2]" );
      assertIsFalse( "ABC.DEF@[1.2.3]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5.6]" );
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
      assertIsFalse( "ip.v4.with.underscore@[123.145.178.90]_" );
      assertIsFalse( "ip.v4.with.underscore@_[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.amp@[123.14&5.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145&.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.&178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.178.90&]" );
      assertIsFalse( "ip.v4.with.amp@[&123.145.178.90]" );
      assertIsFalse( "ip.v4.with.amp@[123.145.178.90]&" );
      assertIsFalse( "ip.v4.with.amp@&[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.asterisk@[123.14*5.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145*.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.*178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.178.90*]" );
      assertIsFalse( "ip.v4.with.asterisk@[*123.145.178.90]" );
      assertIsFalse( "ip.v4.with.asterisk@[123.145.178.90]*" );
      assertIsFalse( "ip.v4.with.asterisk@*[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.dollar@[123.14$5.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145$.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.$178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.178.90$]" );
      assertIsFalse( "ip.v4.with.dollar@[$123.145.178.90]" );
      assertIsFalse( "ip.v4.with.dollar@[123.145.178.90]$" );
      assertIsFalse( "ip.v4.with.dollar@$[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.equality@[123.14=5.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145=.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.=178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.178.90=]" );
      assertIsFalse( "ip.v4.with.equality@[=123.145.178.90]" );
      assertIsFalse( "ip.v4.with.equality@[123.145.178.90]=" );
      assertIsFalse( "ip.v4.with.equality@=[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.exclamation@[123.14!5.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145!.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.!178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.178.90!]" );
      assertIsFalse( "ip.v4.with.exclamation@[!123.145.178.90]" );
      assertIsFalse( "ip.v4.with.exclamation@[123.145.178.90]!" );
      assertIsFalse( "ip.v4.with.exclamation@![123.145.178.90]" );

      assertIsFalse( "ip.v4.with.question@[123.14?5.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145?.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.?178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.178.90?]" );
      assertIsFalse( "ip.v4.with.question@[?123.145.178.90]" );
      assertIsFalse( "ip.v4.with.question@[123.145.178.90]?" );
      assertIsFalse( "ip.v4.with.question@?[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.grave-accent@[123.14`5.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145`.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.`178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.178.90`]" );
      assertIsFalse( "ip.v4.with.grave-accent@[`123.145.178.90]" );
      assertIsFalse( "ip.v4.with.grave-accent@[123.145.178.90]`" );
      assertIsFalse( "ip.v4.with.grave-accent@`[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.hash@[123.14#5.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145#.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.#178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.178.90#]" );
      assertIsFalse( "ip.v4.with.hash@[#123.145.178.90]" );
      assertIsFalse( "ip.v4.with.hash@[123.145.178.90]#" );
      assertIsFalse( "ip.v4.with.hash@#[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.percentage@[123.14%5.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145%.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.%178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.178.90%]" );
      assertIsFalse( "ip.v4.with.percentage@[%123.145.178.90]" );
      assertIsFalse( "ip.v4.with.percentage@[123.145.178.90]%" );
      assertIsFalse( "ip.v4.with.percentage@%[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.pipe@[123.14|5.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145|.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.|178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.178.90|]" );
      assertIsFalse( "ip.v4.with.pipe@[|123.145.178.90]" );
      assertIsFalse( "ip.v4.with.pipe@[123.145.178.90]|" );
      assertIsFalse( "ip.v4.with.pipe@|[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.plus@[123.14+5.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145+.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.+178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.178.90+]" );
      assertIsFalse( "ip.v4.with.plus@[+123.145.178.90]" );
      assertIsFalse( "ip.v4.with.plus@[123.145.178.90]+" );
      assertIsFalse( "ip.v4.with.plus@+[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14{5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145{.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.{178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90{]" );
      assertIsFalse( "ip.v4.with.leftbracket@[{123.145.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90]{" );
      assertIsFalse( "ip.v4.with.leftbracket@{[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14}5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145}.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.}178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90}]" );
      assertIsFalse( "ip.v4.with.rightbracket@[}123.145.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90]}" );
      assertIsFalse( "ip.v4.with.rightbracket@}[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14(5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145(.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.(178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90(]" );
      assertIsFalse( "ip.v4.with.leftbracket@[(123.145.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90](" );
      assertIsFalse( "ip.v4.with.leftbracket@([123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14)5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145).178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.)178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90)]" );
      assertIsFalse( "ip.v4.with.rightbracket@[)123.145.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90])" );
      assertIsFalse( "ip.v4.with.rightbracket@)[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.leftbracket@[123.14[5.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145[.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.[178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90[]" );
      assertIsFalse( "ip.v4.with.leftbracket@[[123.145.178.90]" );
      assertIsFalse( "ip.v4.with.leftbracket@[123.145.178.90][" );
      assertIsFalse( "ip.v4.with.leftbracket@[[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.rightbracket@[123.14]5.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145].178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.]178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90]]" );
      assertIsFalse( "ip.v4.with.rightbracket@[]123.145.178.90]" );
      assertIsFalse( "ip.v4.with.rightbracket@[123.145.178.90]]" );
      assertIsFalse( "ip.v4.with.rightbracket@][123.145.178.90]" );

      assertIsFalse( "ip.v4.with.lower.than@[123.14<5.178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145<.178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145.<178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145.178.90<]" );
      assertIsFalse( "ip.v4.with.lower.than@[<123.145.178.90]" );
      assertIsFalse( "ip.v4.with.lower.than@[123.145.178.90]<" );
      assertIsFalse( "ip.v4.with.lower.than@<[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.greater.than@[123.14>5.178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145>.178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145.>178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145.178.90>]" );
      assertIsFalse( "ip.v4.with.greater.than@[>123.145.178.90]" );
      assertIsFalse( "ip.v4.with.greater.than@[123.145.178.90]>" );
      assertIsFalse( "ip.v4.with.greater.than@>[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.tilde@[123.14~5.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145~.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.~178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.178.90~]" );
      assertIsFalse( "ip.v4.with.tilde@[~123.145.178.90]" );
      assertIsFalse( "ip.v4.with.tilde@[123.145.178.90]~" );
      assertIsFalse( "ip.v4.with.tilde@~[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.xor@[123.14^5.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145^.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.^178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.178.90^]" );
      assertIsFalse( "ip.v4.with.xor@[^123.145.178.90]" );
      assertIsFalse( "ip.v4.with.xor@[123.145.178.90]^" );
      assertIsFalse( "ip.v4.with.xor@^[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.colon@[123.14:5.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145:.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.:178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.178.90:]" );
      assertIsFalse( "ip.v4.with.colon@[:123.145.178.90]" );
      assertIsFalse( "ip.v4.with.colon@[123.145.178.90]:" );
      assertIsFalse( "ip.v4.with.colon@:[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.space@[123.14 5.178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145 .178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145. 178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145.178.90 ]" );
      assertIsFalse( "ip.v4.with.space@[ 123.145.178.90]" );
      assertIsFalse( "ip.v4.with.space@[123.145.178.90] " );
      assertIsFalse( "ip.v4.with.space@ [123.145.178.90]" );

      assertIsFalse( "ip.v4.with.dot@[123.14.5.178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145..178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145..178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145.178.90.]" );
      assertIsFalse( "ip.v4.with.dot@[.123.145.178.90]" );
      assertIsFalse( "ip.v4.with.dot@[123.145.178.90]." );
      assertIsFalse( "ip.v4.with.dot@.[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.comma@[123.14,5.178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145,.178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145.,178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145.178.90,]" );
      assertIsFalse( "ip.v4.with.comma@[,123.145.178.90]" );
      assertIsFalse( "ip.v4.with.comma@[123.145.178.90]," );
      assertIsFalse( "ip.v4.with.comma@,[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.at@[123.14@5.178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145@.178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145.@178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145.178.90@]" );
      assertIsFalse( "ip.v4.with.at@[@123.145.178.90]" );
      assertIsFalse( "ip.v4.with.at@[123.145.178.90]@" );
      assertIsFalse( "ip.v4.with.at@@[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.paragraph@[123.14§5.178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145§.178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145.§178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145.178.90§]" );
      assertIsFalse( "ip.v4.with.paragraph@[§123.145.178.90]" );
      assertIsFalse( "ip.v4.with.paragraph@[123.145.178.90]§" );
      assertIsFalse( "ip.v4.with.paragraph@§[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.double.quote@[123.14'5.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145'.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.'178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90']" );
      assertIsFalse( "ip.v4.with.double.quote@['123.145.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90]'" );
      assertIsFalse( "ip.v4.with.double.quote@'[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.double.quote@[123.14\"5.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145\".178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.\"178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90\"]" );
      assertIsFalse( "ip.v4.with.double.quote@[\"123.145.178.90]" );
      assertIsFalse( "ip.v4.with.double.quote@[123.145.178.90]\"" );
      assertIsFalse( "ip.v4.with.double.quote@\"[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14()5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145().178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.()178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90()]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[()123.145.178.90]" );
      assertIsTrue( "ip.v4.with.empty.bracket@[123.145.178.90]()" );
      assertIsTrue( "ip.v4.with.empty.bracket@()[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14{}5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145{}.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.{}178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90{}]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[{}123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90]{}" );
      assertIsFalse( "ip.v4.with.empty.bracket@{}[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14[]5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145[].178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.[]178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90[]]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[[]123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90][]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[][123.145.178.90]" );

      assertIsFalse( "ip.v4.with.empty.bracket@[123.14<>5.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145<>.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.<>178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90<>]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[<>123.145.178.90]" );
      assertIsFalse( "ip.v4.with.empty.bracket@[123.145.178.90]<>" );
      assertIsFalse( "ip.v4.with.empty.bracket@<>[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket1@[123.14)(5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145)(.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145.)(178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145.178.90)(]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[)(123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket1@[123.145.178.90])(" );
      assertIsFalse( "ip.v4.with.false.bracket1@)([123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket2@[123.14}{5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145}{.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145.}{178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145.178.90}{]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[}{123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket2@[123.145.178.90]}{" );
      assertIsFalse( "ip.v4.with.false.bracket2@}{[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket3@[123.14][5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145][.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145.][178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145.178.90][]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[][123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket3@[123.145.178.90]][" );
      assertIsFalse( "ip.v4.with.false.bracket3@][[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.false.bracket4@[123.14><5.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145><.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145.><178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145.178.90><]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[><123.145.178.90]" );
      assertIsFalse( "ip.v4.with.false.bracket4@[123.145.178.90]><" );
      assertIsFalse( "ip.v4.with.false.bracket4@><[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.number0@[123.1405.178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.1450.178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.145.0178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.145.178.900]" );
      assertIsFalse( "ip.v4.with.number0@[0123.145.178.90]" );
      assertIsFalse( "ip.v4.with.number0@[123.145.178.90]0" );
      assertIsFalse( "ip.v4.with.number0@0[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.number9@[123.1495.178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.1459.178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.145.9178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.145.178.909]" );
      assertIsFalse( "ip.v4.with.number9@[9123.145.178.90]" );
      assertIsFalse( "ip.v4.with.number9@[123.145.178.90]9" );
      assertIsFalse( "ip.v4.with.number9@9[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.numbers@[123.1401234567895.178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.1450123456789.178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.145.0123456789178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.145.178.900123456789]" );
      assertIsFalse( "ip.v4.with.numbers@[0123456789123.145.178.90]" );
      assertIsFalse( "ip.v4.with.numbers@[123.145.178.90]0123456789" );
      assertIsFalse( "ip.v4.with.numbers@0123456789[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.slash@[123.14\\5.178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145\\.178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145.\\178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145.178.90\\]" );
      assertIsFalse( "ip.v4.with.slash@[\\123.145.178.90]" );
      assertIsFalse( "ip.v4.with.slash@[123.145.178.90]\\" );
      assertIsFalse( "ip.v4.with.slash@\\[123.145.178.90]" );
      
      assertIsFalse( "ip.v4.with.byte.overflow@[123.149995.178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145999.178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145.999178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145.178.90999]" );
      assertIsFalse( "ip.v4.with.byte.overflow@[123.145.178.90]999" );
      assertIsFalse( "ip.v4.with.byte.overflow@[999123.145.178.90]" );
      assertIsFalse( "ip.v4.with.byte.overflow@999[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.no.hex.number@[123.14xyz5.178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145xyz.178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145.xyz178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145.178.90xyz]" );
      assertIsFalse( "ip.v4.with.no.hex.number@[123.145.178.90]xyz" );
      assertIsFalse( "ip.v4.with.no.hex.number@[xyz123.145.178.90]" );
      assertIsFalse( "ip.v4.with.no.hex.number@xyz[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.string@[123.14\"str\"5.178.90]" );
      assertIsFalse( "ip.v4.with.string@[123.145\"str\".178.90]" );
      assertIsFalse( "ip.v4.with.string@[123.145.\"str\"178.90]" );
      assertIsFalse( "ip.v4.with.string@[123.145.178.90\"str\"]" );
      assertIsFalse( "ip.v4.with.string@[123.145.178.90]\"str\"" );
      assertIsFalse( "ip.v4.with.string@[\"str\"123.145.178.90]" );
      assertIsFalse( "ip.v4.with.string@\"str\"[123.145.178.90]" );

      assertIsFalse( "ip.v4.with.comment@[123.14(comment)5.178.90]" );
      assertIsFalse( "ip.v4.with.comment@[123.145(comment).178.90]" );
      assertIsFalse( "ip.v4.with.comment@[123.145.(comment)178.90]" );
      assertIsFalse( "ip.v4.with.comment@[123.145.178.90(comment)]" );
      assertIsTrue( "ip.v4.with.comment@[123.145.178.90](comment)" );
      assertIsTrue( "ip.v4.with.comment@[(comment)123.145.178.90]" );
      assertIsFalse( "ip.v4.with.comment@(comment)[123.145.178.90]" );
      

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
      assertIsFalse( "ABC.DEF@[IPv6::]" );
      assertIsFalse( "ABC.DEF@[IPv6:::::...]" );
      assertIsFalse( "ABC.DEF@[IPv6::::::]" );
      assertIsFalse( "ABC.DEF@[IPv6:" );
      assertIsFalse( "ABC.DEF@[IPv6::" );
      assertIsFalse( "ABC.DEF@[IPv6:::::..." );
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
      assertIsFalse( "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_" );

      assertIsFalse( "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]" );
      assertIsFalse( "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&" );

      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]" );
      assertIsFalse( "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*" );

      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]" );
      assertIsFalse( "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$" );

      assertIsFalse( "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]" );
      assertIsFalse( "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]=" );

      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]" );
      assertIsFalse( "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!" );

      assertIsFalse( "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]" );
      assertIsFalse( "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?" );

      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]" );
      assertIsFalse( "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`" );

      assertIsFalse( "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]" );
      assertIsFalse( "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#" );

      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]" );
      assertIsFalse( "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%" );

      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]" );
      assertIsFalse( "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|" );

      assertIsFalse( "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]" );
      assertIsFalse( "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]" );
      assertIsFalse( "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]" );
      assertIsFalse( "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]" );
      assertIsFalse( "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7](" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]" );
      assertIsFalse( "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])" );

      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]" );
      assertIsFalse( "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7][" );

      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );
      assertIsFalse( "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );

      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]" );
      assertIsFalse( "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<" );

      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]" );
      assertIsFalse( "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>" );

      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]" );
      assertIsFalse( "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~" );

      assertIsFalse( "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]" );
      assertIsFalse( "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^" );

      assertIsTrue( "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]" );
      assertIsFalse( "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:" );

      assertIsFalse( "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]" );
      assertIsFalse( "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] " );

      assertIsFalse( "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]" );
      assertIsFalse( "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]." );

      assertIsFalse( "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]" );
      assertIsFalse( "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7]," );

      assertIsFalse( "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]" );
      assertIsFalse( "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@" );

      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]" );
      assertIsFalse( "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§" );

      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']" );
      assertIsFalse( "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'" );

      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:2\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7\"]" );
      assertIsFalse( "ip.v6.with.double.quote@\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]\"" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]" );
      assertIsTrue( "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]" );
      assertIsTrue( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]" );
      assertIsFalse( "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]" );

      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]" );
      assertIsFalse( "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>" );

      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]" );
      assertIsFalse( "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])(" );

      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]" );
      assertIsFalse( "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{" );

      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:2][2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22][:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22:][3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7][]" );
      assertIsFalse( "ip.v6.with.false.bracket3@][[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket3@[IPv6:1:22:3:4:5:6:7]][" );

      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]" );
      assertIsFalse( "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><" );

      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]" );
      assertIsFalse( "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789" );

      assertIsFalse( "ip.v6.with.slash@[IPv6:1:2\\2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22\\:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:\\3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\\]" );
      assertIsFalse( "ip.v6.with.slash@\\[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\\" );

      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:27999]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]2999" );
      assertIsFalse( "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz" );
      assertIsFalse( "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]" );
      assertIsFalse( "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\"" );
      assertIsFalse( "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]" );

      assertIsFalse( "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]" );
      assertIsTrue( "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)" );
      assertIsTrue( "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]" );
      assertIsFalse( "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]" );
      
      
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

      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.999]" );
      assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]" );

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
      assertIsFalse( "a@abc(bananas)def.com" );

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
      assertIsTrue( "nama@contoh.com" );
      assertIsTrue( "Peter.Zapfl@Telekom.DBP.De" );
      assertIsFalse( "\"John Smith\" (johnsmith@example.com)" ); // ?
      assertIsFalse( "Abc.example.com" );
      assertIsFalse( "Abc..123@example.com" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsFalse( "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "just\"not\"right@example.com" );
      assertIsFalse( "this is\"not\\allowed@example.com" );
      assertIsFalse( "this\\ still\\\"not\\\\allowed@example.com" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );
      assertIsTrue( "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com" );

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

      assertIsFalse( " check@this.com" );
      assertIsFalse( " email@example.com" );
      assertIsTrue( "!def!xyz%abc@example.com" );
      assertIsTrue( "!sd@gh.com" );
      assertIsTrue( "%20f3v34g34@gvvre.com" );
      assertIsTrue( "%2@gmail.com" );
      assertIsTrue( "--@ooo.ooo" );
      assertIsTrue( "-@bde.cc" );
      assertIsTrue( "-asd@das.com" );
      assertIsFalse( "..@test.com" );
      assertIsFalse( ".@s.dd" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( ".email@domain.com" );
      assertIsFalse( ".xxxx@mysite.org" );
      assertIsTrue( "1234567890@domain.com" );
      assertIsFalse( "123@$.xyz" );
      assertIsTrue( "1@domain.com" );
      assertIsTrue( "1@gmail.com" );
      assertIsTrue( "1_example@something.gmail.com" );
      assertIsTrue( "2@bde.cc" );
      assertIsFalse( "@b.com" );
      assertIsFalse( "@mail.example.com:joe@sixpack.com" );
      assertIsFalse( "@you.me.net" );
      assertIsFalse( "A@b@c@example.com" );
      assertIsTrue( "Abc.123@example.com" );
      assertIsFalse( "Abc.example.com" );
      assertIsTrue( "Abc@10.42.0.1" );
      assertIsTrue( "Abc@example.com" );
      assertIsFalse( "Abc@example.com." );
      assertIsTrue( "D.Oy'Smith@gmail.com" );
      assertIsFalse( "Foobar Some@thing.com" );
      assertIsTrue( "Fred\\ Bloggs@example.com" );
      assertIsFalse( "MailTo:casesensitve@domain.com" );
      assertIsTrue( "PN=Joe/OU=X400/@gateway.com" );
      assertIsTrue( "\" \"@example.org" );
      assertIsTrue( "\"%2\"@gmail.com" );
      assertIsTrue( "\"Abc@def\"@example.com" );
      assertIsTrue( "\"Abc\\@def\"@example.com" );
      assertIsTrue( "\"Fred Bloggs\"@example.com" );
      assertIsTrue( "\"Fred\\ Bloggs\"@example.com" );
      assertIsFalse( "\"Joe\\Blow\"@example.com" );
      assertIsTrue( "\"Joe\\\\Blow\"@example.com" );
      assertIsTrue( "\"a..b\"@gmail.com" );
      assertIsTrue( "\"a_b\"@gmail.com" );
      assertIsTrue( "\"abcdefghixyz\"@example.com" );
      assertIsTrue( "\"cogwheel the orange\"@example.com" );
      assertIsTrue( "\"foo\\@bar\"@Something.com" );
      assertIsTrue( "\"j\\\"s\"@proseware.com" );
      assertIsTrue( "\"myemail@sa\"@mple.com" );
      assertIsFalse( "\\$A12345@example.com" );
      assertIsTrue( "_-_@bde.cc" );
      assertIsFalse( "_@bde.cc." );
      assertIsTrue( "_@gmail.com" );
      assertIsTrue( "_dasd@sd.com" );
      assertIsTrue( "_dasd_das_@9.com" );
      assertIsTrue( "_somename@example.com" );
      assertIsTrue( "a+b@bde.cc" );
      assertIsTrue( "a+b@c.com" );
      assertIsTrue( "a-b@bde.cc" );
      assertIsFalse( "a..b@bde.cc" );
      assertIsFalse( "a.\"b@c\".x.\"@\".d.e@f.g@" );
      assertIsTrue( "a.a@test.com" );
      assertIsTrue( "a.b@com" );
      assertIsTrue( "a2@bde.cc" );
      assertIsFalse( "a@.com" );
      assertIsTrue( "a@123.45.67.89" );
      assertIsFalse( "a@b." );
      assertIsFalse( "a@b.-de.cc" );
      assertIsFalse( "a@b._de.cc" );
      assertIsTrue( "a@b.c.com" );
      assertIsTrue( "a@b.com" );
      assertIsTrue( "a@bc.com" );
      assertIsTrue( "a@bcom" );
      assertIsFalse( "a@bde-.cc" );
      assertIsFalse( "a@bde.cc." );
      assertIsFalse( "a@bde_.cc" );
      assertIsTrue( "a@domain.com" );
      assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" );
      assertIsTrue( "ab@288.120.150.10.com" );
      assertIsTrue( "ab@[120.254.254.120]" );
      assertIsFalse( "ab@b+de.cc" );
      assertIsTrue( "ab@b-de.cc" );
      assertIsTrue( "ab@c.com" );
      assertIsFalse( "ab@sd@dd" );
      assertIsTrue( "ab_c@bde.cc" );
      assertIsTrue( "abc.\"defghi\".xyz@example.com" );
      assertIsTrue( "abc.xyz@gmail.com.in" );
      assertIsTrue( "abc123xyz@asdf.co.in" );
      assertIsTrue( "abc1_xyz1@gmail1.com" );
      assertIsFalse( "abc\"defghi\"xyz@example.com" );
      assertIsFalse( "abc\\\"def\\\"ghi@example.com" );
      assertIsTrue( "abcxyz123@qwert.com" );
      assertIsTrue( "alex@example.com" );
      assertIsTrue( "alireza@test.co.uk" );
      assertIsFalse( "as3d@dac.coas-" );
      assertIsTrue( "asd-@asd.com" );
      assertIsFalse( "asd@dasd@asd.cm" );
      assertIsFalse( "check@this..com" );
      assertIsTrue( "check@this.com" );
      assertIsFalse( "check@thiscom" );
      assertIsTrue( "cog@wheel.com" );
      assertIsTrue( "customer/department=shipping@example.com" );
      assertIsTrue( "d._.___d@gmail.com" );
      assertIsTrue( "d.j@server1.proseware.com" );
      assertIsTrue( "d.oy.smith@gmail.com" );
      assertIsTrue( "d23d@da9.co9" );
      assertIsTrue( "d_oy_smith@gmail.com" );
      assertIsFalse( "da23@das..com" );
      assertIsFalse( "dad@sds" );
      assertIsTrue( "dasd-dasd@das.com.das" );
      assertIsTrue( "dasd.dadas@dasd.com" );
      assertIsTrue( "dasd_-@jdas.com" );
      assertIsFalse( "dasddas-@.com" );
      assertIsTrue( "david.jones@proseware.com" );
      assertIsTrue( "dda_das@das-dasd.com" );
      assertIsTrue( "doysmith@gmail.com" );
      assertIsTrue( "dsq!a?@das.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email.@domain.com" );
      assertIsFalse( "email@.domain.com" );
      assertIsFalse( "email@domain" );
      assertIsFalse( "email@domain..com" );
      assertIsTrue( "email@domain.co.de" );
      assertIsTrue( "email@domain.com" );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@example" );
      assertIsFalse( "email@example..com" );
      assertIsTrue( "email@example.co.uk" );
      assertIsFalse( "email@example.co.uk." );
      assertIsFalse( "email@example.com " );
      assertIsTrue( "email@example.com" );
      assertIsTrue( "email@mail.gmail.com" );
      assertIsTrue( "email@subdomain.domain.com" );
      assertIsTrue( "example@example.co" );
      assertIsTrue( "f.f.f@bde.cc" );
      assertIsFalse( "fdsa" );
      assertIsFalse( "fdsa@" );
      assertIsFalse( "fdsa@fdsa" );
      assertIsFalse( "fdsa@fdsa." );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
      assertIsTrue( "firstname+lastname@domain.com" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "firstname.lastname@domain.com" );
      assertIsFalse( "foo.bar@machine.sub\\@domain.example.museum" );
      assertIsFalse( "foo@bar@machine.subdomain.example.museum" );
      assertIsTrue( "foo\\@bar@machine.subdomain.example.museum" );
      assertIsFalse( "foo~&(&)(@bar.com" );
      assertIsTrue( "futureTLD@somewhere.fooo" );
      assertIsFalse( "hallo2ww22@example....caaaao" );
      assertIsFalse( "hallo@example.coassjj#sswzazaaaa" );
      assertIsTrue( "hello7___@ca.com.pt" );
      assertIsFalse( "invalid.email.com" );
      assertIsFalse( "invalid@email@domain.com" );
      assertIsFalse( "j..s@proseware.com" );
      assertIsFalse( "j.@server1.proseware.com" );
      assertIsTrue( "j.s@server1.proseware.com" );
      assertIsTrue( "j@proseware.com9" );
      assertIsTrue( "j_9@[129.126.118.1]" );
      assertIsTrue( "john.smith@example.com" );
      assertIsTrue( "jones@ms1.proseware.com" );
      assertIsTrue( "js#internal@proseware.com" );
      assertIsTrue( "js*@proseware.com" );
      assertIsFalse( "js@proseware..com" );
      assertIsTrue( "js@proseware.com9" );
      assertIsFalse( "mailto:email@domain.com" );
      assertIsTrue( "my.ownsite@ourearth.org" );
      assertIsFalse( "myemail@@sample.com" );
      assertIsFalse( "myemail@sa@mple.com" );
      assertIsTrue( "myemail@sample" );
      assertIsTrue( "myemail@sample.com" );
      assertIsFalse( "myemailsample.com" );
      assertIsTrue( "mysite@you.me.net" );
      assertIsTrue( "peter.example@yahoo.com.au" );
      assertIsTrue( "peter_123@news.com" );
      assertIsTrue( "pio^_pio@factory.com" );
      assertIsTrue( "pio_#pio@factory.com" );
      assertIsFalse( "pio_pio@#factory.com" );
      assertIsFalse( "pio_pio@factory.c#om" );
      assertIsFalse( "pio_pio@factory.c*om" );
      assertIsTrue( "pio_pio@factory.com" );
      assertIsTrue( "pio_~pio@factory.com" );
      assertIsTrue( "piskvor@example.lighting" );
      assertIsFalse( "plain.address" );
      assertIsFalse( "plainaddress" );
      assertIsTrue( "someone+tag@somewhere.net" );
      assertIsTrue( "someone@somewhere.co.uk" );
      assertIsTrue( "someone@somewhere.com" );
      assertIsFalse( "tarzan@jungle.org,jane@jungle.org" );
      assertIsFalse( "jane@jungle.com: | /usr/bin/vacation" );
      assertIsFalse( "this\\ still\\\"not\\allowed@example.com" );
      assertIsTrue( "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com" );
      assertIsTrue( "user+mailbox/department=shipping@example.com" );
      assertIsTrue( "user@[IPv6:2001:DB8::1]" );
      assertIsTrue( "user@localserver" );
      assertIsTrue( "w.b.f@test.com" );
      assertIsTrue( "w.b.f@test.museum" );
      assertIsFalse( "xxxx..1234@yahoo.com" );
      assertIsFalse( "xxxx.ourearth.com" );
      assertIsFalse( "xxxx123@gmail.b" );
      assertIsFalse( "xxxx@.com.my" );
      assertIsFalse( "xxxx@.org.org" );
      assertIsTrue( "xxxx@gmail.com" );
      assertIsFalse( "xxxxx()*@gmail.com" );
      assertIsTrue( "xxxxxx@yahoo.com" );
      assertIsTrue( "yoursite@ourearth.com" );
      assertIsTrue( "~pio_pio@factory.com" );
      assertIsFalse( "\"Joe Q. Public\" <john.q.public@example.com>" );
      assertIsTrue( "Who? <one@y.test>" );
      assertIsTrue( "<boss@nil.test>" );
      assertIsTrue( "\"Giant; \\\"Big\\\" Box\" <sysservices@example.net>" );
      assertIsFalse( "<1234   @   local(blah)  .machine .example>" );

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
      assertIsTrue( "first.last@sub.do.com" );
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
      assertIsTrue( "\"first\\\"last\"@test.org" );
      assertIsFalse( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" );
      assertIsFalse( "first\\@last@iana.org" );
      assertIsFalse( "test@example.com " );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]" );
      assertIsFalse( "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]" );
      assertIsFalse( "invalid@about.museum-" );
      assertIsFalse( "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" );
      assertIsFalse( "abc@def@test.org" );
      assertIsTrue( "abc\\@def@test.org" );
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
      assertIsTrue( "\"first\\\"last\"@test.org" );
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
      assertIsTrue( "\"Test \\\"Fail\\\" Ing\"@test.org" );
      assertIsFalse( "\"Test \"Fail\" Ing\"@test.org" );
      assertIsTrue( "\"test blah\"@test.org" );
      assertIsTrue( "first.last@test.org" );
      assertIsFalse( "jdoe@machine(comment).example" );
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
      assertIsFalse( "(foo)cal(bar)@(baz)iamcal.com(quux)" );
      assertIsTrue( "cal(foo\\)bar)@iamcal.com" );
      assertIsTrue( "cal(woo(yay)hoopla)@iamcal.com" );
      assertIsTrue( "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org" );
      assertIsFalse( "pete(his account)@silly.test(his host)" );
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
      assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@example.com" );
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
        knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );

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
        
        knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );
        

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
    
    generateTest( "\\\\", "slash" );

    generateTest( "999", "byte.overflow" );
    generateTest( "xyz", "no.hex.number" );

    generateTest( "\\\"str\\\"", "string" );
    generateTest( "(comment)", "comment" );
  }

  private static void generateTest( String pCharacter, String pName )
  {
    //wl( "" );
    //wl( "      assertIsTrue( \"" + pCharacter + ".local.name.starts.with." + pName + "@domain.com\" );" );
    //wl( "      assertIsTrue( \"local.name.ends.with." + pName + pCharacter + "@domain.com\" );" );
    //wl( "      assertIsTrue( \"local.name.with." + pName + pCharacter + "character@domain.com\" );" );
    //wl( "      assertIsTrue( \"local.name.with." + pName + ".before" + pCharacter + ".point@domain.com\" );" );
    //wl( "      assertIsTrue( \"local.name.with." + pName + ".after." + pCharacter + "point@domain.com\" );" );
    //wl( "      assertIsTrue( \"local.name.with.double." + pName + "" + pCharacter + "" + pCharacter + "test@domain.com\" );" );
    //wl( "      assertIsTrue( \"(comment " + pCharacter + ") local.name.with.comment.with." + pName + "@domain.com\" );" );
    //wl( "      assertIsTrue( \"\\\"quote" + pCharacter + "\\\".local.name.with.qoute.with." + pName + "@domain.com\" );" );
    //wl( "      assertIsTrue( \"" + pCharacter + "@" + pName + ".domain.com\" );" );
    //wl( "      assertIsTrue( \"" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "@" + pName + ".domain.com\" );" );
    //wl( "      assertIsTrue( \"" + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "@" + pName + ".domain.com\" );" );
    //wl( "      assertIsTrue( \"name " + pCharacter + " <pointy.brackets1.with." + pName + "@domain.com>\" );" );
    //wl( "      assertIsTrue( \"<pointy.brackets2.with." + pName + "@domain.com> name " + pCharacter + "\" );" );
    //wl( "" );
    //wl( "      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
    //wl( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
    //wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
    //wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
    //wl( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
    //wl( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );
    //wl( "" );
    //wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.14" + pCharacter + "5.178.90]\" );" );
    //wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145" + pCharacter + ".178.90]\" );" );
    //wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145." + pCharacter + "178.90]\" );" );
    //wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90" + pCharacter + "]\" );" );
    //wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90]" + pCharacter + "\" );" );
    //wl( "      assertIsFalse( \"ip.v4.with." + pName + "@[" + pCharacter + "123.145.178.90]\" );" );
    //wl( "      assertIsFalse( \"ip.v4.with." + pName + "@" + pCharacter + "[123.145.178.90]\" );" );
    wl( "" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:2" + pCharacter + "2:3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22" + pCharacter + ":3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:" + pCharacter + "3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7" + pCharacter + "]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7]" + pCharacter + "\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@" + pCharacter + "[IPv6:1:22:3:4:5:6:7]\" );" );
    wl( "      assertIsFalse( \"ip.v6.with." + pName + "@[" + pCharacter + "IPv6:1:22:3:4:5:6:7]\" );" );
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
