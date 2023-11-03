package de.fk.email;

import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

class TestClassAssertIsTrueFalse
{
  private static int          TEST_B_TEST_NR                    = 2;

  private static int          LAUFENDE_ZAHL                     = 0;

  private static int          COUNT_ASSERT_IS_TRUE              = 0;

  private static int          COUNT_ASSERT_IS_FALSE             = 0;

  private static int          TRUE_RESULT_COUNT_EMAIL_IS_TRUE   = 0;

  private static int          TRUE_RESULT_COUNT_EMAIL_IS_FALSE  = 0;

  private static int          FALSE_RESULT_COUNT_EMAIL_IS_TRUE  = 0;

  private static int          FALSE_RESULT_COUNT_EMAIL_IS_FALSE = 0;

  private static int          FALSE_RESULT_COUNT_ERROR          = 0;

  private static int          TRUE_RESULT_COUNT_ERROR           = 0;

  private static int          BREITE_SPALTE_EMAIL_AUSGABE       = 80;

  private static boolean      KNZ_FILLUP_AKTIV                  = false;

  private static boolean      KNZ_LOG_AUSGABE                   = true;

  private static boolean      TEST_B_KNZ_AKTIV                  = false;

  private static StringBuffer m_str_buffer                      = null;

  /*
   * <pre>
   * 
   * Unit Tests eMail Validator
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

  /*
   * <pre>
   * Testcases for eMail Validation
   * 
   * Datum 03.11.2023 12:39:37:511
   * 
   * 
   * ---- To be Fixed -----------------------------------------------------------------------------------------------------------------
   * 
   *     1 - assertIsTrue  "\"With extra < within quotes\" Display Name<email@domain.com>"                  =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
   *     2 - assertIsTrue  "\"<script>alert('XSS')</script>\"@example.com "                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *     3 - assertIsFalse "\"Moose Brains !!!\" @ (yes, this is my address) spam.la <MooseBrains>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *     4 - assertIsFalse "double.dash.in.domain@double--dash.com"                                         =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   * 
   * ---- Correct ---------------------------------------------------------------------------------------------------------------------
   * 
   *     5 - assertIsTrue  "n@d.td"                                                                         =   0 =  OK 
   *     6 - assertIsTrue  "1@2.td"                                                                         =   0 =  OK 
   *     7 - assertIsTrue  "12.345@678.90.tld"                                                              =   0 =  OK 
   *     8 - assertIsTrue  "name1.name2@domain.tld"                                                         =   0 =  OK 
   *     9 - assertIsTrue  "name1+name2@domain.tld"                                                         =   0 =  OK 
   *    10 - assertIsTrue  "name1-name2@domain.tld"                                                         =   0 =  OK 
   *    11 - assertIsTrue  "name1.name2@subdomain1.domain.tld"                                              =   0 =  OK 
   *    12 - assertIsTrue  "name1.name2@subdomain1.tu-domain.tld"                                           =   0 =  OK 
   *    13 - assertIsTrue  "name1.name2@subdomain1.tu_domain.tld"                                           =   0 =  OK 
   *    14 - assertIsTrue  "name1.name2@subdomain1.subdomain2.domain.tld"                                   =   0 =  OK 
   *    15 - assertIsTrue  "escaped.at\@.sign@domain.tld"                                                   =   0 =  OK 
   *    16 - assertIsTrue  "\"at.sign.@\".in.string@domain.tld"                                             =   1 =  OK 
   *    17 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                           =   2 =  OK 
   *    18 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                              =   4 =  OK 
   *    19 - assertIsTrue  "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]"                                     =   4 =  OK 
   *    20 - assertIsTrue  "ip4.without.brackets@1.2.3.4"                                                   =   2 =  OK 
   *    21 - assertIsTrue  "\"string1\".name1@domain.tld"                                                   =   1 =  OK 
   *    22 - assertIsTrue  "name1.\"string1\"@domain.tld"                                                   =   1 =  OK 
   *    23 - assertIsTrue  "name1.\"string1\".name2@domain.tld"                                             =   1 =  OK 
   *    24 - assertIsTrue  "name1.\"string1\".name2@subdomain1.domain.tld"                                  =   1 =  OK 
   *    25 - assertIsTrue  "\"string1\".\"quote2\".name1@domain.tld"                                        =   1 =  OK 
   *    26 - assertIsTrue  "\"string1\"@domain.tld"                                                         =   1 =  OK 
   *    27 - assertIsTrue  "\"special characters in string ( - % & * + - / = ? _ )\"@domain.tld"            =   1 =  OK 
   *    28 - assertIsTrue  "\"string1\\"embedded string\\"\"@domain.tld"                                    =   1 =  OK 
   *    29 - assertIsTrue  "\"string1(embedded comment)\"@domain.tld"                                       =   1 =  OK 
   *    30 - assertIsTrue  "(comment1)name1@domain.tld"                                                     =   6 =  OK 
   *    31 - assertIsTrue  "(comment1)-name1@domain.tld"                                                    =   6 =  OK 
   *    32 - assertIsTrue  "name1(comment1)@domain.tld"                                                     =   6 =  OK 
   *    33 - assertIsTrue  "name1@(comment1)domain.tld"                                                     =   6 =  OK 
   *    34 - assertIsTrue  "name1@domain.tld(comment1)"                                                     =   6 =  OK 
   *    35 - assertIsTrue  "(spaces after comment)     name1.name2@domain.tld"                              =   6 =  OK 
   *    36 - assertIsTrue  "name1.name2@domain.tld    (spaces before comment)"                              =   6 =  OK 
   *    37 - assertIsTrue  "(comment1.\\"comment2)name1@domain.tld"                                         =   6 =  OK 
   *    38 - assertIsTrue  "(comment1.\\"String\\")name1@domain.tld"                                        =   6 =  OK 
   *    39 - assertIsTrue  "(comment1.\\"String\\".@domain.tld)name1@domain.tld"                            =   6 =  OK 
   *    40 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                           =   2 =  OK 
   *    41 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                           =   2 =  OK 
   *    42 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                           =   2 =  OK 
   *    43 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                           =   2 =  OK 
   *    44 - assertIsTrue  "(comment1)\"string1\".name1@domain.tld"                                         =   7 =  OK 
   *    45 - assertIsTrue  "(comment1)name1.\"string1\"@domain.tld"                                         =   7 =  OK 
   *    46 - assertIsTrue  "name1.\"string1\"(comment1)@domain.tld"                                         =   7 =  OK 
   *    47 - assertIsTrue  "\"string1\".name1(comment1)@domain.tld"                                         =   7 =  OK 
   *    48 - assertIsTrue  "name1.\"string1\"@(comment1)domain.tld"                                         =   7 =  OK 
   *    49 - assertIsTrue  "\"string1\".name1@domain.tld(comment1)"                                         =   7 =  OK 
   *    50 - assertIsTrue  "\"string1(comment1)\".name1@domain.tld"                                         =   1 =  OK 
   *    51 - assertIsTrue  "<name1.name2@domain.tld>"                                                       =   0 =  OK 
   *    52 - assertIsTrue  "name3 <name1.name2@domain.tld>"                                                 =   0 =  OK 
   *    53 - assertIsTrue  "<name1.name2@domain.tld> name3"                                                 =   0 =  OK 
   *    54 - assertIsTrue  "\"name3 name4\" <name1.name2@domain.tld>"                                       =   0 =  OK 
   *    55 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                                   =   2 =  OK 
   *    56 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                                      =   4 =  OK 
   *    57 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                                   =   2 =  OK 
   *    58 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                                     =   4 =  OK 
   *    59 - assertIsTrue  "\"display name\" <\"string1\".local.part@domain-name.tld (comment)>"            =   7 =  OK 
   *    60 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"            =   6 =  OK 
   *    61 - assertIsTrue  "\"display name\" <local.part@(comment)domain-name.top_level_domain>"            =   6 =  OK 
   *    62 - assertIsTrue  "\"display name\" <(comment) local.part.\"string1\"@domain-name.top_level_domain>" =   7 =  OK 
   *    63 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part@domain-name.top_level_domain>" =   6 =  OK 
   *    64 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part.wiht.escaped.at\@.sign@domain-name.top_level_domain>" =   6 =  OK 
   *    65 - assertIsTrue  "name1\@domain.tld.name1@domain.tld"                                             =   0 =  OK 
   *    66 - assertIsTrue  "\"name1\@domain.tld\".name1@domain.tld"                                         =   1 =  OK 
   *    67 - assertIsTrue  "\"name1\@domain.tld \\"name1\@domain.tld\\"\".name1@domain.tld"                 =   1 =  OK 
   *    68 - assertIsTrue  "\"name1\@domain.tld \\"name1\@domain.tld\\"\".name1@domain.tld (name1@domain.tld)" =   7 =  OK 
   *    69 - assertIsTrue  "(name1@domain.tld) name1@domain.tld"                                            =   6 =  OK 
   *    70 - assertIsTrue  "(name1@domain.tld) \"name1\@domain.tld\".name1@domain.tld"                      =   7 =  OK 
   *    71 - assertIsTrue  "(name1@domain.tld) name1.\"name1\@domain.tld\"@domain.tld"                      =   7 =  OK 
   * 
   * ---- No Input --------------------------------------------------------------------------------------------------------------------
   * 
   *    72 - assertIsFalse null                                                                             =  10 =  OK    Laenge: Eingabe ist null
   *    73 - assertIsFalse ""                                                                               =  11 =  OK    Laenge: Eingabe ist Leerstring
   *    74 - assertIsFalse "        "                                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * ---- AT-Sign ---------------------------------------------------------------------------------------------------------------------
   * 
   *    75 - assertIsFalse "1234567890"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    76 - assertIsFalse "OnlyTextNoDotNoAt"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    77 - assertIsFalse "email.with.no.at.sign"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    78 - assertIsFalse "email.with.no.domain@"                                                          =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *    79 - assertIsFalse "@@domain.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *    80 - assertIsFalse "name1.@domain.com"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *    81 - assertIsFalse "name1@.domain.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *    82 - assertIsFalse "@name1.at.domain.com"                                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *    83 - assertIsFalse "name1.at.domain.com@"                                                           =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *    84 - assertIsFalse "name1@name2@domain.com"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    85 - assertIsFalse "email.with.no.domain\@domain.com"                                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    86 - assertIsFalse "email.with.no.domain\@.domain.com"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    87 - assertIsFalse "email.with.no.domain\@123domain.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    88 - assertIsFalse "email.with.no.domain\@_domain.com"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    89 - assertIsFalse "email.with.no.domain\@-domain.com"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    90 - assertIsFalse "email.with.double\@no.domain\@domain.com"                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    91 - assertIsTrue  "\"wrong.at.sign.combination.in.string1@.\"@domain.com"                          =   1 =  OK 
   *    92 - assertIsTrue  "\"wrong.at.sign.combination.in.string2.@\"@domain.com"                          =   1 =  OK 
   *    93 - assertIsTrue  "email.with.escaped.at\@.sign.version1@domain.com"                               =   0 =  OK 
   *    94 - assertIsTrue  "email.with.escaped.\@.sign.version2@domain.com"                                 =   0 =  OK 
   *    95 - assertIsTrue  "email.with.escaped.at\@123.sign.version3@domain.com"                            =   0 =  OK 
   *    96 - assertIsTrue  "email.with.escaped.\@123.sign.version4@domain.com"                              =   0 =  OK 
   *    97 - assertIsTrue  "email.with.escaped.at\@-.sign.version5@domain.com"                              =   0 =  OK 
   *    98 - assertIsTrue  "email.with.escaped.\@-.sign.version6@domain.com"                                =   0 =  OK 
   *    99 - assertIsTrue  "email.with.escaped.at.sign.\@@domain.com"                                       =   0 =  OK 
   *   100 - assertIsTrue  "(@) email.with.at.sign.in.commet1@domain.com"                                   =   6 =  OK 
   *   101 - assertIsTrue  "email.with.at.sign.in.commet2@domain.com (@)"                                   =   6 =  OK 
   *   102 - assertIsTrue  "email.with.at.sign.in.commet3@domain.com (.@)"                                  =   6 =  OK 
   *   103 - assertIsFalse "@@email.with.unescaped.at.sign.as.local.part"                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   104 - assertIsTrue  "\@@email.with.escaped.at.sign.as.local.part"                                    =   0 =  OK 
   *   105 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   106 - assertIsFalse "@no.local.part.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   107 - assertIsFalse "@@@@@@only.multiple.at.signs.in.local.part.com"                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   108 - assertIsFalse "local.part.with.two.@at.signs@domain.com"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *   109 - assertIsFalse "local.part.ends.with.at.sign@@domain.com"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   110 - assertIsFalse "local.part.with.at.sign.before@.point@domain.com"                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   111 - assertIsFalse "local.part.with.at.sign.after.@point@domain.com"                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *   112 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   113 - assertIsTrue  "(comment @) local.part.with.at.sign.in.comment@domain.com"                      =   6 =  OK 
   *   114 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"                    =   6 =  OK 
   *   115 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   116 - assertIsTrue  "\"String@\".local.part.with.at.sign.in.string@domain.com"                       =   1 =  OK 
   *   117 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                                   =   0 =  OK 
   *   118 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   119 - assertIsFalse "@.@.@.@.@.@@domain.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   120 - assertIsFalse "@.@.@."                                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   121 - assertIsFalse "\@.\@@\@.\@"                                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   122 - assertIsFalse "@"                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *   123 - assertIsFalse "name @ <pointy.brackets1.with.at.sign.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   124 - assertIsFalse "<pointy.brackets2.with.at.sign.in.display.name@domain.com> name @"              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   125 - assertIsTrue  "<pointy.brackets3.with.escaped.at.sign.in.display.name@domain.com> name \@"     =   0 =  OK 
   * 
   * ---- Seperator -------------------------------------------------------------------------------------------------------------------
   * 
   *   126 - assertIsFalse "EmailAdressWith@NoDots"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   127 - assertIsFalse "..local.part.starts.with.dot@domain.com"                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   128 - assertIsFalse "local.part.ends.with.dot.@domain.com"                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *   129 - assertIsTrue  "local.part.with.dot.character@domain.com"                                       =   0 =  OK 
   *   130 - assertIsFalse "local.part.with.dot.before..point@domain.com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   131 - assertIsFalse "local.part.with.dot.after..point@domain.com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   132 - assertIsFalse "local.part.with.double.dot..test@domain.com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   133 - assertIsTrue  "(comment .) local.part.with.dot.in.comment@domain.com"                          =   6 =  OK 
   *   134 - assertIsTrue  "\"string.\".local.part.with.dot.in.String@domain.com"                           =   1 =  OK 
   *   135 - assertIsFalse "\"string\.\".local.part.with.escaped.dot.in.String@domain.com"                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   136 - assertIsFalse ".@local.part.only.dot.domain.com"                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   137 - assertIsFalse "......@local.part.only.consecutive.dot.domain.com"                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   138 - assertIsFalse "...........@dot.domain.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   139 - assertIsFalse "name . <pointy.brackets1.with.dot.in.display.name@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   140 - assertIsFalse "<pointy.brackets2.with.dot.in.display.name@domain.com> name ."                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   141 - assertIsTrue  "domain.part@with.dot.com"                                                       =   0 =  OK 
   *   142 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   143 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   144 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   145 - assertIsFalse "domain.part@with.dot.before..point.com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   146 - assertIsFalse "domain.part@with.dot.after..point.com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   147 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   148 - assertIsTrue  "domain.part.with.dot.in.comment@(comment .)domain.com"                          =   6 =  OK 
   *   149 - assertIsFalse "domain.part.only.dot@..com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   150 - assertIsFalse "top.level.domain.only@dot.."                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   151 - assertIsFalse "...local.part.starts.with.double.dot@domain.com"                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   152 - assertIsFalse "local.part.ends.with.double.dot..@domain.com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   153 - assertIsFalse "local.part.with.double.dot..character@domain.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   154 - assertIsFalse "local.part.with.double.dot.before...point@domain.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   155 - assertIsFalse "local.part.with.double.dot.after...point@domain.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   156 - assertIsFalse "local.part.with.double.double.dot....test@domain.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   157 - assertIsTrue  "(comment ..) local.part.with.double.dot.in.comment@domain.com"                  =   6 =  OK 
   *   158 - assertIsTrue  "\"string..\".local.part.with.double.dot.in.String@domain.com"                   =   1 =  OK 
   *   159 - assertIsFalse "\"string\..\".local.part.with.escaped.double.dot.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   160 - assertIsFalse "..@local.part.only.double.dot.domain.com"                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   161 - assertIsFalse "............@local.part.only.consecutive.double.dot.domain.com"                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   162 - assertIsFalse ".................@double.dot.domain.com"                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   163 - assertIsFalse "name .. <pointy.brackets1.with.double.dot.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   164 - assertIsFalse "<pointy.brackets2.with.double.dot.in.display.name@domain.com> name .."          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   165 - assertIsFalse "domain.part@with..double.dot.com"                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   166 - assertIsFalse "domain.part@..with.double.dot.at.domain.start.com"                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   167 - assertIsFalse "domain.part@with.double.dot.at.domain.end1...com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   168 - assertIsFalse "domain.part@with.double.dot.at.domain.end2.com.."                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   169 - assertIsFalse "domain.part@with.double.dot.before...point.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   170 - assertIsFalse "domain.part@with.double.dot.after...point.com"                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   171 - assertIsFalse "domain.part@with.consecutive.double.dot....test.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   172 - assertIsTrue  "domain.part.with.comment.with.double.dot@(comment ..)domain.com"                =   6 =  OK 
   *   173 - assertIsFalse "domain.part.only.double.dot@...com"                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   174 - assertIsFalse "top.level.domain.only@double.dot..."                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   * 
   * ---- Characters ------------------------------------------------------------------------------------------------------------------
   * 
   *   175 - assertIsTrue  "&local&&part&with&$@amp.com"                                                    =   0 =  OK 
   *   176 - assertIsTrue  "*local**part*with*@asterisk.com"                                                =   0 =  OK 
   *   177 - assertIsTrue  "$local$$part$with$@dollar.com"                                                  =   0 =  OK 
   *   178 - assertIsTrue  "=local==part=with=@equality.com"                                                =   0 =  OK 
   *   179 - assertIsTrue  "!local!!part!with!@exclamation.com"                                             =   0 =  OK 
   *   180 - assertIsTrue  "`local``part`with`@grave-accent.com"                                            =   0 =  OK 
   *   181 - assertIsTrue  "#local##part#with#@hash.com"                                                    =   0 =  OK 
   *   182 - assertIsTrue  "-local--part-with-@hypen.com"                                                   =   0 =  OK 
   *   183 - assertIsTrue  "{local{part{{with{@leftbracket.com"                                             =   0 =  OK 
   *   184 - assertIsTrue  "%local%%part%with%@percentage.com"                                              =   0 =  OK 
   *   185 - assertIsTrue  "|local||part|with|@pipe.com"                                                    =   0 =  OK 
   *   186 - assertIsTrue  "+local++part+with+@plus.com"                                                    =   0 =  OK 
   *   187 - assertIsTrue  "?local??part?with?@question.com"                                                =   0 =  OK 
   *   188 - assertIsTrue  "}local}part}}with}@rightbracket.com"                                            =   0 =  OK 
   *   189 - assertIsTrue  "~local~~part~with~@tilde.com"                                                   =   0 =  OK 
   *   190 - assertIsTrue  "^local^^part^with^@xor.com"                                                     =   0 =  OK 
   *   191 - assertIsTrue  "_local__part_with_@underscore.com"                                              =   0 =  OK 
   *   192 - assertIsFalse ":local::part:with:@colon.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   193 - assertIsFalse "local.part@&domain&&part&with&.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   194 - assertIsFalse "local.part@*domain**part*with*.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   195 - assertIsFalse "local.part@$domain$$part$with$.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   196 - assertIsFalse "local.part@=domain==part=with=.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   197 - assertIsFalse "local.part@!domain!!part!with!.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   198 - assertIsFalse "local.part@`domain``part`with`.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   199 - assertIsFalse "local.part@#domain##part#with#.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   200 - assertIsFalse "local.part@-domain--part-with-.com"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   201 - assertIsFalse "local.part@{domain{part{{with{.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   202 - assertIsFalse "local.part@%domain%%part%with%.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   203 - assertIsFalse "local.part@|domain||part|with|.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   204 - assertIsFalse "local.part@+domain++part+with+.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   205 - assertIsFalse "local.part@?domain??part?with?.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   206 - assertIsFalse "local.part@}domain}part}}with}.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   207 - assertIsFalse "local.part@~domain~~part~with~.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   208 - assertIsFalse "local.part@^domain^^part^with^.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   209 - assertIsFalse "local.part@_domain__part_with_.com"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   210 - assertIsFalse "local.part@domain--part.double.dash.com"                                        =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *   211 - assertIsFalse ";.local.part.starts.with.semicolon@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   212 - assertIsFalse "local.part.ends.with.semicolon;@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   213 - assertIsFalse "local.part.with.semicolon;character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   214 - assertIsFalse "local.part.with.semicolon.before;.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   215 - assertIsFalse "local.part.with.semicolon.after.;point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   216 - assertIsFalse "local.part.with.double.semicolon;;test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   217 - assertIsFalse "(comment ;) local.part.with.semicolon.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   218 - assertIsTrue  "\"string;\".local.part.with.semicolon.in.String@domain.com"                     =   1 =  OK 
   *   219 - assertIsFalse "\"string\;\".local.part.with.escaped.semicolon.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   220 - assertIsFalse ";@local.part.only.semicolon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   221 - assertIsFalse ";;;;;;@local.part.only.consecutive.semicolon.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   222 - assertIsFalse ";.;.;.;.;.;@semicolon.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   223 - assertIsFalse "name ; <pointy.brackets1.with.semicolon.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   224 - assertIsFalse "<pointy.brackets2.with.semicolon.in.display.name@domain.com> name ;"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   225 - assertIsFalse "domain.part@with;semicolon.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   226 - assertIsFalse "domain.part@;with.semicolon.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   227 - assertIsFalse "domain.part@with.semicolon.at.domain.end1;.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   228 - assertIsFalse "domain.part@with.semicolon.at.domain.end2.com;"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   229 - assertIsFalse "domain.part@with.semicolon.before;.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   230 - assertIsFalse "domain.part@with.semicolon.after.;point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   231 - assertIsFalse "domain.part@with.consecutive.semicolon;;test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   232 - assertIsFalse "domain.part.with.semicolon.in.comment@(comment ;)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   233 - assertIsFalse "domain.part.only.semicolon@;.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   234 - assertIsFalse "top.level.domain.only@semicolon.;"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   235 - assertIsTrue  "&.local.part.starts.with.amp@domain.com"                                        =   0 =  OK 
   *   236 - assertIsTrue  "local.part.ends.with.amp&@domain.com"                                           =   0 =  OK 
   *   237 - assertIsTrue  "local.part.with.amp&character@domain.com"                                       =   0 =  OK 
   *   238 - assertIsTrue  "local.part.with.amp.before&.point@domain.com"                                   =   0 =  OK 
   *   239 - assertIsTrue  "local.part.with.amp.after.&point@domain.com"                                    =   0 =  OK 
   *   240 - assertIsTrue  "local.part.with.double.amp&&test@domain.com"                                    =   0 =  OK 
   *   241 - assertIsTrue  "(comment &) local.part.with.amp.in.comment@domain.com"                          =   6 =  OK 
   *   242 - assertIsTrue  "\"string&\".local.part.with.amp.in.String@domain.com"                           =   1 =  OK 
   *   243 - assertIsFalse "\"string\&\".local.part.with.escaped.amp.in.String@domain.com"                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   244 - assertIsTrue  "&@local.part.only.amp.domain.com"                                               =   0 =  OK 
   *   245 - assertIsTrue  "&&&&&&@local.part.only.consecutive.amp.domain.com"                              =   0 =  OK 
   *   246 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                                     =   0 =  OK 
   *   247 - assertIsFalse "name & <pointy.brackets1.with.amp.in.display.name@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   248 - assertIsFalse "<pointy.brackets2.with.amp.in.display.name@domain.com> name &"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   249 - assertIsFalse "domain.part@with&amp.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   250 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   251 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   252 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   253 - assertIsFalse "domain.part@with.amp.before&.point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   254 - assertIsFalse "domain.part@with.amp.after.&point.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   255 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   256 - assertIsTrue  "domain.part.with.amp.in.comment@(comment &)domain.com"                          =   6 =  OK 
   *   257 - assertIsFalse "domain.part.only.amp@&.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   258 - assertIsFalse "top.level.domain.only@amp.&"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   259 - assertIsTrue  "*.local.part.starts.with.asterisk@domain.com"                                   =   0 =  OK 
   *   260 - assertIsTrue  "local.part.ends.with.asterisk*@domain.com"                                      =   0 =  OK 
   *   261 - assertIsTrue  "local.part.with.asterisk*character@domain.com"                                  =   0 =  OK 
   *   262 - assertIsTrue  "local.part.with.asterisk.before*.point@domain.com"                              =   0 =  OK 
   *   263 - assertIsTrue  "local.part.with.asterisk.after.*point@domain.com"                               =   0 =  OK 
   *   264 - assertIsTrue  "local.part.with.double.asterisk**test@domain.com"                               =   0 =  OK 
   *   265 - assertIsTrue  "(comment *) local.part.with.asterisk.in.comment@domain.com"                     =   6 =  OK 
   *   266 - assertIsTrue  "\"string*\".local.part.with.asterisk.in.String@domain.com"                      =   1 =  OK 
   *   267 - assertIsFalse "\"string\*\".local.part.with.escaped.asterisk.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   268 - assertIsTrue  "*@local.part.only.asterisk.domain.com"                                          =   0 =  OK 
   *   269 - assertIsTrue  "******@local.part.only.consecutive.asterisk.domain.com"                         =   0 =  OK 
   *   270 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                                =   0 =  OK 
   *   271 - assertIsFalse "name * <pointy.brackets1.with.asterisk.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   272 - assertIsFalse "<pointy.brackets2.with.asterisk.in.display.name@domain.com> name *"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   273 - assertIsFalse "domain.part@with*asterisk.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   274 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   275 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   276 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   277 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   278 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   279 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   280 - assertIsTrue  "domain.part.with.asterisk.in.comment@(comment *)domain.com"                     =   6 =  OK 
   *   281 - assertIsFalse "domain.part.only.asterisk@*.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   282 - assertIsFalse "top.level.domain.only@asterisk.*"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   283 - assertIsTrue  "_.local.part.starts.with.underscore@domain.com"                                 =   0 =  OK 
   *   284 - assertIsTrue  "local.part.ends.with.underscore_@domain.com"                                    =   0 =  OK 
   *   285 - assertIsTrue  "local.part.with.underscore_character@domain.com"                                =   0 =  OK 
   *   286 - assertIsTrue  "local.part.with.underscore.before_.point@domain.com"                            =   0 =  OK 
   *   287 - assertIsTrue  "local.part.with.underscore.after._point@domain.com"                             =   0 =  OK 
   *   288 - assertIsTrue  "local.part.with.double.underscore__test@domain.com"                             =   0 =  OK 
   *   289 - assertIsTrue  "(comment _) local.part.with.underscore.in.comment@domain.com"                   =   6 =  OK 
   *   290 - assertIsTrue  "\"string_\".local.part.with.underscore.in.String@domain.com"                    =   1 =  OK 
   *   291 - assertIsFalse "\"string\_\".local.part.with.escaped.underscore.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   292 - assertIsTrue  "_@local.part.only.underscore.domain.com"                                        =   0 =  OK 
   *   293 - assertIsTrue  "______@local.part.only.consecutive.underscore.domain.com"                       =   0 =  OK 
   *   294 - assertIsTrue  "_._._._._._@underscore.domain.com"                                              =   0 =  OK 
   *   295 - assertIsFalse "name _ <pointy.brackets1.with.underscore.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   296 - assertIsFalse "<pointy.brackets2.with.underscore.in.display.name@domain.com> name _"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   297 - assertIsTrue  "domain.part@with_underscore.com"                                                =   0 =  OK 
   *   298 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   299 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   300 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   301 - assertIsFalse "domain.part@with.underscore.before_.point.com"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   302 - assertIsFalse "domain.part@with.underscore.after._point.com"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   303 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                              =   0 =  OK 
   *   304 - assertIsTrue  "domain.part.with.underscore.in.comment@(comment _)domain.com"                   =   6 =  OK 
   *   305 - assertIsFalse "domain.part.only.underscore@_.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   306 - assertIsFalse "top.level.domain.only@underscore._"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   307 - assertIsTrue  "$.local.part.starts.with.dollar@domain.com"                                     =   0 =  OK 
   *   308 - assertIsTrue  "local.part.ends.with.dollar$@domain.com"                                        =   0 =  OK 
   *   309 - assertIsTrue  "local.part.with.dollar$character@domain.com"                                    =   0 =  OK 
   *   310 - assertIsTrue  "local.part.with.dollar.before$.point@domain.com"                                =   0 =  OK 
   *   311 - assertIsTrue  "local.part.with.dollar.after.$point@domain.com"                                 =   0 =  OK 
   *   312 - assertIsTrue  "local.part.with.double.dollar$$test@domain.com"                                 =   0 =  OK 
   *   313 - assertIsTrue  "(comment $) local.part.with.dollar.in.comment@domain.com"                       =   6 =  OK 
   *   314 - assertIsTrue  "\"string$\".local.part.with.dollar.in.String@domain.com"                        =   1 =  OK 
   *   315 - assertIsFalse "\"string\$\".local.part.with.escaped.dollar.in.String@domain.com"               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   316 - assertIsTrue  "$@local.part.only.dollar.domain.com"                                            =   0 =  OK 
   *   317 - assertIsTrue  "$$$$$$@local.part.only.consecutive.dollar.domain.com"                           =   0 =  OK 
   *   318 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                                  =   0 =  OK 
   *   319 - assertIsFalse "name $ <pointy.brackets1.with.dollar.in.display.name@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   320 - assertIsFalse "<pointy.brackets2.with.dollar.in.display.name@domain.com> name $"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   321 - assertIsFalse "domain.part@with$dollar.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   322 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   323 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   324 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   325 - assertIsFalse "domain.part@with.dollar.before$.point.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   326 - assertIsFalse "domain.part@with.dollar.after.$point.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   327 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   328 - assertIsTrue  "domain.part.with.dollar.in.comment@(comment $)domain.com"                       =   6 =  OK 
   *   329 - assertIsFalse "domain.part.only.dollar@$.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   330 - assertIsFalse "top.level.domain.only@dollar.$"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   331 - assertIsTrue  "=.local.part.starts.with.equality@domain.com"                                   =   0 =  OK 
   *   332 - assertIsTrue  "local.part.ends.with.equality=@domain.com"                                      =   0 =  OK 
   *   333 - assertIsTrue  "local.part.with.equality=character@domain.com"                                  =   0 =  OK 
   *   334 - assertIsTrue  "local.part.with.equality.before=.point@domain.com"                              =   0 =  OK 
   *   335 - assertIsTrue  "local.part.with.equality.after.=point@domain.com"                               =   0 =  OK 
   *   336 - assertIsTrue  "local.part.with.double.equality==test@domain.com"                               =   0 =  OK 
   *   337 - assertIsTrue  "(comment =) local.part.with.equality.in.comment@domain.com"                     =   6 =  OK 
   *   338 - assertIsTrue  "\"string=\".local.part.with.equality.in.String@domain.com"                      =   1 =  OK 
   *   339 - assertIsFalse "\"string\=\".local.part.with.escaped.equality.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   340 - assertIsTrue  "=@local.part.only.equality.domain.com"                                          =   0 =  OK 
   *   341 - assertIsTrue  "======@local.part.only.consecutive.equality.domain.com"                         =   0 =  OK 
   *   342 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                                =   0 =  OK 
   *   343 - assertIsFalse "name = <pointy.brackets1.with.equality.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   344 - assertIsFalse "<pointy.brackets2.with.equality.in.display.name@domain.com> name ="             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   345 - assertIsFalse "domain.part@with=equality.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   346 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   347 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   348 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   349 - assertIsFalse "domain.part@with.equality.before=.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   350 - assertIsFalse "domain.part@with.equality.after.=point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   351 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   352 - assertIsTrue  "domain.part.with.equality.in.comment@(comment =)domain.com"                     =   6 =  OK 
   *   353 - assertIsFalse "domain.part.only.equality@=.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   354 - assertIsFalse "top.level.domain.only@equality.="                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   355 - assertIsTrue  "!.local.part.starts.with.exclamation@domain.com"                                =   0 =  OK 
   *   356 - assertIsTrue  "local.part.ends.with.exclamation!@domain.com"                                   =   0 =  OK 
   *   357 - assertIsTrue  "local.part.with.exclamation!character@domain.com"                               =   0 =  OK 
   *   358 - assertIsTrue  "local.part.with.exclamation.before!.point@domain.com"                           =   0 =  OK 
   *   359 - assertIsTrue  "local.part.with.exclamation.after.!point@domain.com"                            =   0 =  OK 
   *   360 - assertIsTrue  "local.part.with.double.exclamation!!test@domain.com"                            =   0 =  OK 
   *   361 - assertIsTrue  "(comment !) local.part.with.exclamation.in.comment@domain.com"                  =   6 =  OK 
   *   362 - assertIsTrue  "\"string!\".local.part.with.exclamation.in.String@domain.com"                   =   1 =  OK 
   *   363 - assertIsFalse "\"string\!\".local.part.with.escaped.exclamation.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   364 - assertIsTrue  "!@local.part.only.exclamation.domain.com"                                       =   0 =  OK 
   *   365 - assertIsTrue  "!!!!!!@local.part.only.consecutive.exclamation.domain.com"                      =   0 =  OK 
   *   366 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                             =   0 =  OK 
   *   367 - assertIsFalse "name ! <pointy.brackets1.with.exclamation.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   368 - assertIsFalse "<pointy.brackets2.with.exclamation.in.display.name@domain.com> name !"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   369 - assertIsFalse "domain.part@with!exclamation.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   370 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   371 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   372 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   373 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   374 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   375 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   376 - assertIsTrue  "domain.part.with.exclamation.in.comment@(comment !)domain.com"                  =   6 =  OK 
   *   377 - assertIsFalse "domain.part.only.exclamation@!.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   378 - assertIsFalse "top.level.domain.only@exclamation.!"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   379 - assertIsTrue  "?.local.part.starts.with.question@domain.com"                                   =   0 =  OK 
   *   380 - assertIsTrue  "local.part.ends.with.question?@domain.com"                                      =   0 =  OK 
   *   381 - assertIsTrue  "local.part.with.question?character@domain.com"                                  =   0 =  OK 
   *   382 - assertIsTrue  "local.part.with.question.before?.point@domain.com"                              =   0 =  OK 
   *   383 - assertIsTrue  "local.part.with.question.after.?point@domain.com"                               =   0 =  OK 
   *   384 - assertIsTrue  "local.part.with.double.question??test@domain.com"                               =   0 =  OK 
   *   385 - assertIsTrue  "(comment ?) local.part.with.question.in.comment@domain.com"                     =   6 =  OK 
   *   386 - assertIsTrue  "\"string?\".local.part.with.question.in.String@domain.com"                      =   1 =  OK 
   *   387 - assertIsFalse "\"string\?\".local.part.with.escaped.question.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   388 - assertIsTrue  "?@local.part.only.question.domain.com"                                          =   0 =  OK 
   *   389 - assertIsTrue  "??????@local.part.only.consecutive.question.domain.com"                         =   0 =  OK 
   *   390 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                                =   0 =  OK 
   *   391 - assertIsFalse "name ? <pointy.brackets1.with.question.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   392 - assertIsFalse "<pointy.brackets2.with.question.in.display.name@domain.com> name ?"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   393 - assertIsFalse "domain.part@with?question.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   394 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   395 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   396 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   397 - assertIsFalse "domain.part@with.question.before?.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   398 - assertIsFalse "domain.part@with.question.after.?point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   399 - assertIsFalse "domain.part@with.consecutive.question??test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   400 - assertIsTrue  "domain.part.with.question.in.comment@(comment ?)domain.com"                     =   6 =  OK 
   *   401 - assertIsFalse "domain.part.only.question@?.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   402 - assertIsFalse "top.level.domain.only@question.?"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   403 - assertIsTrue  "`.local.part.starts.with.grave-accent@domain.com"                               =   0 =  OK 
   *   404 - assertIsTrue  "local.part.ends.with.grave-accent`@domain.com"                                  =   0 =  OK 
   *   405 - assertIsTrue  "local.part.with.grave-accent`character@domain.com"                              =   0 =  OK 
   *   406 - assertIsTrue  "local.part.with.grave-accent.before`.point@domain.com"                          =   0 =  OK 
   *   407 - assertIsTrue  "local.part.with.grave-accent.after.`point@domain.com"                           =   0 =  OK 
   *   408 - assertIsTrue  "local.part.with.double.grave-accent``test@domain.com"                           =   0 =  OK 
   *   409 - assertIsTrue  "(comment `) local.part.with.grave-accent.in.comment@domain.com"                 =   6 =  OK 
   *   410 - assertIsTrue  "\"string`\".local.part.with.grave-accent.in.String@domain.com"                  =   1 =  OK 
   *   411 - assertIsFalse "\"string\`\".local.part.with.escaped.grave-accent.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   412 - assertIsTrue  "`@local.part.only.grave-accent.domain.com"                                      =   0 =  OK 
   *   413 - assertIsTrue  "``````@local.part.only.consecutive.grave-accent.domain.com"                     =   0 =  OK 
   *   414 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                            =   0 =  OK 
   *   415 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   416 - assertIsFalse "<pointy.brackets2.with.grave-accent.in.display.name@domain.com> name `"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   417 - assertIsFalse "domain.part@with`grave-accent.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   418 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   419 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   420 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   421 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   422 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   423 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   424 - assertIsTrue  "domain.part.with.grave-accent.in.comment@(comment `)domain.com"                 =   6 =  OK 
   *   425 - assertIsFalse "domain.part.only.grave-accent@`.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   426 - assertIsFalse "top.level.domain.only@grave-accent.`"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   427 - assertIsTrue  "#.local.part.starts.with.hash@domain.com"                                       =   0 =  OK 
   *   428 - assertIsTrue  "local.part.ends.with.hash#@domain.com"                                          =   0 =  OK 
   *   429 - assertIsTrue  "local.part.with.hash#character@domain.com"                                      =   0 =  OK 
   *   430 - assertIsTrue  "local.part.with.hash.before#.point@domain.com"                                  =   0 =  OK 
   *   431 - assertIsTrue  "local.part.with.hash.after.#point@domain.com"                                   =   0 =  OK 
   *   432 - assertIsTrue  "local.part.with.double.hash##test@domain.com"                                   =   0 =  OK 
   *   433 - assertIsTrue  "(comment #) local.part.with.hash.in.comment@domain.com"                         =   6 =  OK 
   *   434 - assertIsTrue  "\"string#\".local.part.with.hash.in.String@domain.com"                          =   1 =  OK 
   *   435 - assertIsFalse "\"string\#\".local.part.with.escaped.hash.in.String@domain.com"                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   436 - assertIsTrue  "#@local.part.only.hash.domain.com"                                              =   0 =  OK 
   *   437 - assertIsTrue  "######@local.part.only.consecutive.hash.domain.com"                             =   0 =  OK 
   *   438 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                                    =   0 =  OK 
   *   439 - assertIsFalse "name # <pointy.brackets1.with.hash.in.display.name@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   440 - assertIsFalse "<pointy.brackets2.with.hash.in.display.name@domain.com> name #"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   441 - assertIsFalse "domain.part@with#hash.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   442 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   443 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   444 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   445 - assertIsFalse "domain.part@with.hash.before#.point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   446 - assertIsFalse "domain.part@with.hash.after.#point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   447 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   448 - assertIsTrue  "domain.part.with.hash.in.comment@(comment #)domain.com"                         =   6 =  OK 
   *   449 - assertIsFalse "domain.part.only.hash@#.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   450 - assertIsFalse "top.level.domain.only@hash.#"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   451 - assertIsTrue  "%.local.part.starts.with.percentage@domain.com"                                 =   0 =  OK 
   *   452 - assertIsTrue  "local.part.ends.with.percentage%@domain.com"                                    =   0 =  OK 
   *   453 - assertIsTrue  "local.part.with.percentage%character@domain.com"                                =   0 =  OK 
   *   454 - assertIsTrue  "local.part.with.percentage.before%.point@domain.com"                            =   0 =  OK 
   *   455 - assertIsTrue  "local.part.with.percentage.after.%point@domain.com"                             =   0 =  OK 
   *   456 - assertIsTrue  "local.part.with.double.percentage%%test@domain.com"                             =   0 =  OK 
   *   457 - assertIsTrue  "(comment %) local.part.with.percentage.in.comment@domain.com"                   =   6 =  OK 
   *   458 - assertIsTrue  "\"string%\".local.part.with.percentage.in.String@domain.com"                    =   1 =  OK 
   *   459 - assertIsFalse "\"string\%\".local.part.with.escaped.percentage.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   460 - assertIsTrue  "%@local.part.only.percentage.domain.com"                                        =   0 =  OK 
   *   461 - assertIsTrue  "%%%%%%@local.part.only.consecutive.percentage.domain.com"                       =   0 =  OK 
   *   462 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                              =   0 =  OK 
   *   463 - assertIsFalse "name % <pointy.brackets1.with.percentage.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   464 - assertIsFalse "<pointy.brackets2.with.percentage.in.display.name@domain.com> name %"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   465 - assertIsFalse "domain.part@with%percentage.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   466 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   467 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   468 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   469 - assertIsFalse "domain.part@with.percentage.before%.point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   470 - assertIsFalse "domain.part@with.percentage.after.%point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   471 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   472 - assertIsTrue  "domain.part.with.percentage.in.comment@(comment %)domain.com"                   =   6 =  OK 
   *   473 - assertIsFalse "domain.part.only.percentage@%.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   474 - assertIsFalse "top.level.domain.only@percentage.%"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   475 - assertIsTrue  "|.local.part.starts.with.pipe@domain.com"                                       =   0 =  OK 
   *   476 - assertIsTrue  "local.part.ends.with.pipe|@domain.com"                                          =   0 =  OK 
   *   477 - assertIsTrue  "local.part.with.pipe|character@domain.com"                                      =   0 =  OK 
   *   478 - assertIsTrue  "local.part.with.pipe.before|.point@domain.com"                                  =   0 =  OK 
   *   479 - assertIsTrue  "local.part.with.pipe.after.|point@domain.com"                                   =   0 =  OK 
   *   480 - assertIsTrue  "local.part.with.double.pipe||test@domain.com"                                   =   0 =  OK 
   *   481 - assertIsTrue  "(comment |) local.part.with.pipe.in.comment@domain.com"                         =   6 =  OK 
   *   482 - assertIsTrue  "\"string|\".local.part.with.pipe.in.String@domain.com"                          =   1 =  OK 
   *   483 - assertIsFalse "\"string\|\".local.part.with.escaped.pipe.in.String@domain.com"                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   484 - assertIsTrue  "|@local.part.only.pipe.domain.com"                                              =   0 =  OK 
   *   485 - assertIsTrue  "||||||@local.part.only.consecutive.pipe.domain.com"                             =   0 =  OK 
   *   486 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                                    =   0 =  OK 
   *   487 - assertIsFalse "name | <pointy.brackets1.with.pipe.in.display.name@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   488 - assertIsFalse "<pointy.brackets2.with.pipe.in.display.name@domain.com> name |"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   489 - assertIsFalse "domain.part@with|pipe.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   490 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   491 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   492 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   493 - assertIsFalse "domain.part@with.pipe.before|.point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   494 - assertIsFalse "domain.part@with.pipe.after.|point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   495 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   496 - assertIsTrue  "domain.part.with.pipe.in.comment@(comment |)domain.com"                         =   6 =  OK 
   *   497 - assertIsFalse "domain.part.only.pipe@|.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   498 - assertIsFalse "top.level.domain.only@pipe.|"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   499 - assertIsTrue  "+.local.part.starts.with.plus@domain.com"                                       =   0 =  OK 
   *   500 - assertIsTrue  "local.part.ends.with.plus+@domain.com"                                          =   0 =  OK 
   *   501 - assertIsTrue  "local.part.with.plus+character@domain.com"                                      =   0 =  OK 
   *   502 - assertIsTrue  "local.part.with.plus.before+.point@domain.com"                                  =   0 =  OK 
   *   503 - assertIsTrue  "local.part.with.plus.after.+point@domain.com"                                   =   0 =  OK 
   *   504 - assertIsTrue  "local.part.with.double.plus++test@domain.com"                                   =   0 =  OK 
   *   505 - assertIsTrue  "(comment +) local.part.with.plus.in.comment@domain.com"                         =   6 =  OK 
   *   506 - assertIsTrue  "\"string+\".local.part.with.plus.in.String@domain.com"                          =   1 =  OK 
   *   507 - assertIsFalse "\"string\+\".local.part.with.escaped.plus.in.String@domain.com"                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   508 - assertIsTrue  "+@local.part.only.plus.domain.com"                                              =   0 =  OK 
   *   509 - assertIsTrue  "++++++@local.part.only.consecutive.plus.domain.com"                             =   0 =  OK 
   *   510 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                                    =   0 =  OK 
   *   511 - assertIsFalse "name + <pointy.brackets1.with.plus.in.display.name@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   512 - assertIsFalse "<pointy.brackets2.with.plus.in.display.name@domain.com> name +"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   513 - assertIsFalse "domain.part@with+plus.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   514 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   515 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   516 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   517 - assertIsFalse "domain.part@with.plus.before+.point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   518 - assertIsFalse "domain.part@with.plus.after.+point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   519 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   520 - assertIsTrue  "domain.part.with.plus.in.comment@(comment +)domain.com"                         =   6 =  OK 
   *   521 - assertIsFalse "domain.part.only.plus@+.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   522 - assertIsFalse "top.level.domain.only@plus.+"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   523 - assertIsTrue  "{.local.part.starts.with.leftbracket@domain.com"                                =   0 =  OK 
   *   524 - assertIsTrue  "local.part.ends.with.leftbracket{@domain.com"                                   =   0 =  OK 
   *   525 - assertIsTrue  "local.part.with.leftbracket{character@domain.com"                               =   0 =  OK 
   *   526 - assertIsTrue  "local.part.with.leftbracket.before{.point@domain.com"                           =   0 =  OK 
   *   527 - assertIsTrue  "local.part.with.leftbracket.after.{point@domain.com"                            =   0 =  OK 
   *   528 - assertIsTrue  "local.part.with.double.leftbracket{{test@domain.com"                            =   0 =  OK 
   *   529 - assertIsTrue  "(comment {) local.part.with.leftbracket.in.comment@domain.com"                  =   6 =  OK 
   *   530 - assertIsTrue  "\"string{\".local.part.with.leftbracket.in.String@domain.com"                   =   1 =  OK 
   *   531 - assertIsFalse "\"string\{\".local.part.with.escaped.leftbracket.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   532 - assertIsTrue  "{@local.part.only.leftbracket.domain.com"                                       =   0 =  OK 
   *   533 - assertIsTrue  "{{{{{{@local.part.only.consecutive.leftbracket.domain.com"                      =   0 =  OK 
   *   534 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                             =   0 =  OK 
   *   535 - assertIsFalse "name { <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   536 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name {"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   537 - assertIsFalse "domain.part@with{leftbracket.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   538 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   539 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   540 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   541 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   542 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   543 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   544 - assertIsTrue  "domain.part.with.leftbracket.in.comment@(comment {)domain.com"                  =   6 =  OK 
   *   545 - assertIsFalse "domain.part.only.leftbracket@{.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   546 - assertIsFalse "top.level.domain.only@leftbracket.{"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   547 - assertIsTrue  "}.local.part.starts.with.rightbracket@domain.com"                               =   0 =  OK 
   *   548 - assertIsTrue  "local.part.ends.with.rightbracket}@domain.com"                                  =   0 =  OK 
   *   549 - assertIsTrue  "local.part.with.rightbracket}character@domain.com"                              =   0 =  OK 
   *   550 - assertIsTrue  "local.part.with.rightbracket.before}.point@domain.com"                          =   0 =  OK 
   *   551 - assertIsTrue  "local.part.with.rightbracket.after.}point@domain.com"                           =   0 =  OK 
   *   552 - assertIsTrue  "local.part.with.double.rightbracket}}test@domain.com"                           =   0 =  OK 
   *   553 - assertIsTrue  "(comment }) local.part.with.rightbracket.in.comment@domain.com"                 =   6 =  OK 
   *   554 - assertIsTrue  "\"string}\".local.part.with.rightbracket.in.String@domain.com"                  =   1 =  OK 
   *   555 - assertIsFalse "\"string\}\".local.part.with.escaped.rightbracket.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   556 - assertIsTrue  "}@local.part.only.rightbracket.domain.com"                                      =   0 =  OK 
   *   557 - assertIsTrue  "}}}}}}@local.part.only.consecutive.rightbracket.domain.com"                     =   0 =  OK 
   *   558 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                            =   0 =  OK 
   *   559 - assertIsFalse "name } <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   560 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name }"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   561 - assertIsFalse "domain.part@with}rightbracket.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   562 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   563 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   564 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   565 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   566 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   567 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   568 - assertIsTrue  "domain.part.with.rightbracket.in.comment@(comment })domain.com"                 =   6 =  OK 
   *   569 - assertIsFalse "domain.part.only.rightbracket@}.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   570 - assertIsFalse "top.level.domain.only@rightbracket.}"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   571 - assertIsFalse "(.local.part.starts.with.leftbracket@domain.com"                                =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   572 - assertIsFalse "local.part.ends.with.leftbracket(@domain.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   573 - assertIsFalse "local.part.with.leftbracket(character@domain.com"                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   574 - assertIsFalse "local.part.with.leftbracket.before(.point@domain.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   575 - assertIsFalse "local.part.with.leftbracket.after.(point@domain.com"                            = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   576 - assertIsFalse "local.part.with.double.leftbracket((test@domain.com"                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   577 - assertIsFalse "(comment () local.part.with.leftbracket.in.comment@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   578 - assertIsTrue  "\"string(\".local.part.with.leftbracket.in.String@domain.com"                   =   1 =  OK 
   *   579 - assertIsFalse "\"string\(\".local.part.with.escaped.leftbracket.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   580 - assertIsFalse "(@local.part.only.leftbracket.domain.com"                                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   581 - assertIsFalse "((((((@local.part.only.consecutive.leftbracket.domain.com"                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   582 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   583 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"          =   0 =  OK 
   *   584 - assertIsTrue  "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ("          =   0 =  OK 
   *   585 - assertIsFalse "domain.part@with(leftbracket.com"                                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   586 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                              =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   587 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   588 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   589 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   590 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                                  = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   591 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   592 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment ()domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   593 - assertIsFalse "domain.part.only.leftbracket@(.com"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   594 - assertIsFalse "top.level.domain.only@leftbracket.("                                            = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   595 - assertIsFalse ").local.part.starts.with.rightbracket@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   596 - assertIsFalse "local.part.ends.with.rightbracket)@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   597 - assertIsFalse "local.part.with.rightbracket)character@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   598 - assertIsFalse "local.part.with.rightbracket.before).point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   599 - assertIsFalse "local.part.with.rightbracket.after.)point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   600 - assertIsFalse "local.part.with.double.rightbracket))test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   601 - assertIsFalse "(comment )) local.part.with.rightbracket.in.comment@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   602 - assertIsTrue  "\"string)\".local.part.with.rightbracket.in.String@domain.com"                  =   1 =  OK 
   *   603 - assertIsFalse "\"string\)\".local.part.with.escaped.rightbracket.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   604 - assertIsFalse ")@local.part.only.rightbracket.domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   605 - assertIsFalse "))))))@local.part.only.consecutive.rightbracket.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   606 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   607 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"         =   0 =  OK 
   *   608 - assertIsTrue  "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name )"         =   0 =  OK 
   *   609 - assertIsFalse "domain.part@with)rightbracket.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   610 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   611 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   612 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   613 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   614 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   615 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   616 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ))domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   617 - assertIsFalse "domain.part.only.rightbracket@).com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   618 - assertIsFalse "top.level.domain.only@rightbracket.)"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   619 - assertIsFalse "[.local.part.starts.with.leftbracket@domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   620 - assertIsFalse "local.part.ends.with.leftbracket[@domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   621 - assertIsFalse "local.part.with.leftbracket[character@domain.com"                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   622 - assertIsFalse "local.part.with.leftbracket.before[.point@domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   623 - assertIsFalse "local.part.with.leftbracket.after.[point@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   624 - assertIsFalse "local.part.with.double.leftbracket[[test@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   625 - assertIsFalse "(comment [) local.part.with.leftbracket.in.comment@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   626 - assertIsTrue  "\"string[\".local.part.with.leftbracket.in.String@domain.com"                   =   1 =  OK 
   *   627 - assertIsFalse "\"string\[\".local.part.with.escaped.leftbracket.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   628 - assertIsFalse "[@local.part.only.leftbracket.domain.com"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   629 - assertIsFalse "[[[[[[@local.part.only.consecutive.leftbracket.domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   630 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   631 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   632 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ["          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   633 - assertIsFalse "domain.part@with[leftbracket.com"                                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   634 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   635 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   636 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   637 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   638 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   639 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   640 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment [)domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   641 - assertIsFalse "domain.part.only.leftbracket@[.com"                                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *   642 - assertIsFalse "top.level.domain.only@leftbracket.["                                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   643 - assertIsFalse "].local.part.starts.with.rightbracket@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   644 - assertIsFalse "local.part.ends.with.rightbracket]@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   645 - assertIsFalse "local.part.with.rightbracket]character@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   646 - assertIsFalse "local.part.with.rightbracket.before].point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   647 - assertIsFalse "local.part.with.rightbracket.after.]point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   648 - assertIsFalse "local.part.with.double.rightbracket]]test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   649 - assertIsFalse "(comment ]) local.part.with.rightbracket.in.comment@domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   650 - assertIsTrue  "\"string]\".local.part.with.rightbracket.in.String@domain.com"                  =   1 =  OK 
   *   651 - assertIsFalse "\"string\]\".local.part.with.escaped.rightbracket.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   652 - assertIsFalse "]@local.part.only.rightbracket.domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   653 - assertIsFalse "]]]]]]@local.part.only.consecutive.rightbracket.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   654 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   655 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   656 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name ]"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   657 - assertIsFalse "domain.part@with]rightbracket.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   658 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   659 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   660 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   661 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   662 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   663 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   664 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ])domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   665 - assertIsFalse "domain.part.only.rightbracket@].com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   666 - assertIsFalse "top.level.domain.only@rightbracket.]"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   667 - assertIsFalse "().local.part.starts.with.empty.bracket@domain.com"                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   668 - assertIsTrue  "local.part.ends.with.empty.bracket()@domain.com"                                =   6 =  OK 
   *   669 - assertIsFalse "local.part.with.empty.bracket()character@domain.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   670 - assertIsFalse "local.part.with.empty.bracket.before().point@domain.com"                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   671 - assertIsFalse "local.part.with.empty.bracket.after.()point@domain.com"                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   672 - assertIsFalse "local.part.with.double.empty.bracket()()test@domain.com"                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   673 - assertIsFalse "(comment ()) local.part.with.empty.bracket.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   674 - assertIsTrue  "\"string()\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   675 - assertIsFalse "\"string\()\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   676 - assertIsFalse "()@local.part.only.empty.bracket.domain.com"                                    =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *   677 - assertIsFalse "()()()()()()@local.part.only.consecutive.empty.bracket.domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *   678 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   679 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =   0 =  OK 
   *   680 - assertIsTrue  "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name ()"       =   0 =  OK 
   *   681 - assertIsFalse "domain.part@with()empty.bracket.com"                                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *   682 - assertIsTrue  "domain.part@()with.empty.bracket.at.domain.start.com"                           =   6 =  OK 
   *   683 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1().com"                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   684 - assertIsTrue  "domain.part@with.empty.bracket.at.domain.end2.com()"                            =   6 =  OK 
   *   685 - assertIsFalse "domain.part@with.empty.bracket.before().point.com"                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   686 - assertIsFalse "domain.part@with.empty.bracket.after.()point.com"                               = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   687 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"                         = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *   688 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment ())domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   689 - assertIsFalse "domain.part.only.empty.bracket@().com"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   690 - assertIsFalse "top.level.domain.only@empty.bracket.()"                                         = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   691 - assertIsTrue  "{}.local.part.starts.with.empty.bracket@domain.com"                             =   0 =  OK 
   *   692 - assertIsTrue  "local.part.ends.with.empty.bracket{}@domain.com"                                =   0 =  OK 
   *   693 - assertIsTrue  "local.part.with.empty.bracket{}character@domain.com"                            =   0 =  OK 
   *   694 - assertIsTrue  "local.part.with.empty.bracket.before{}.point@domain.com"                        =   0 =  OK 
   *   695 - assertIsTrue  "local.part.with.empty.bracket.after.{}point@domain.com"                         =   0 =  OK 
   *   696 - assertIsTrue  "local.part.with.double.empty.bracket{}{}test@domain.com"                        =   0 =  OK 
   *   697 - assertIsTrue  "(comment {}) local.part.with.empty.bracket.in.comment@domain.com"               =   6 =  OK 
   *   698 - assertIsTrue  "\"string{}\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   699 - assertIsFalse "\"string\{}\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   700 - assertIsTrue  "{}@local.part.only.empty.bracket.domain.com"                                    =   0 =  OK 
   *   701 - assertIsTrue  "{}{}{}{}{}{}@local.part.only.consecutive.empty.bracket.domain.com"              =   0 =  OK 
   *   702 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                                     =   0 =  OK 
   *   703 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   704 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name {}"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   705 - assertIsFalse "domain.part@with{}empty.bracket.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   706 - assertIsFalse "domain.part@{}with.empty.bracket.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   707 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1{}.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   708 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com{}"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   709 - assertIsFalse "domain.part@with.empty.bracket.before{}.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   710 - assertIsFalse "domain.part@with.empty.bracket.after.{}point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   711 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   712 - assertIsTrue  "domain.part.with.empty.bracket.in.comment@(comment {})domain.com"               =   6 =  OK 
   *   713 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   714 - assertIsFalse "top.level.domain.only@empty.bracket.{}"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   715 - assertIsFalse "[].local.part.starts.with.empty.bracket@domain.com"                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   716 - assertIsFalse "local.part.ends.with.empty.bracket[]@domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   717 - assertIsFalse "local.part.with.empty.bracket[]character@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   718 - assertIsFalse "local.part.with.empty.bracket.before[].point@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   719 - assertIsFalse "local.part.with.empty.bracket.after.[]point@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   720 - assertIsFalse "local.part.with.double.empty.bracket[][]test@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   721 - assertIsFalse "(comment []) local.part.with.empty.bracket.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   722 - assertIsTrue  "\"string[]\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   723 - assertIsFalse "\"string\[]\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   724 - assertIsFalse "[]@local.part.only.empty.bracket.domain.com"                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   725 - assertIsFalse "[][][][][][]@local.part.only.consecutive.empty.bracket.domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   726 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   727 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   728 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name []"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   729 - assertIsFalse "domain.part@with[]empty.bracket.com"                                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   730 - assertIsFalse "domain.part@[]with.empty.bracket.at.domain.start.com"                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   731 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1[].com"                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   732 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com[]"                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   733 - assertIsFalse "domain.part@with.empty.bracket.before[].point.com"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   734 - assertIsFalse "domain.part@with.empty.bracket.after.[]point.com"                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   735 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   736 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment [])domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   737 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   738 - assertIsFalse "top.level.domain.only@empty.bracket.[]"                                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   739 - assertIsFalse "<>.local.part.starts.with.empty.bracket@domain.com"                             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   740 - assertIsFalse "local.part.ends.with.empty.bracket<>@domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   741 - assertIsFalse "local.part.with.empty.bracket<>character@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   742 - assertIsFalse "local.part.with.empty.bracket.before<>.point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   743 - assertIsFalse "local.part.with.empty.bracket.after.<>point@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   744 - assertIsFalse "local.part.with.double.empty.bracket<><>test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   745 - assertIsFalse "(comment <>) local.part.with.empty.bracket.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   746 - assertIsTrue  "\"string<>\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   747 - assertIsFalse "\"string\<>\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   748 - assertIsFalse "<>@local.part.only.empty.bracket.domain.com"                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   749 - assertIsFalse "<><><><><><>@local.part.only.consecutive.empty.bracket.domain.com"              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   750 - assertIsFalse "<>.<>.<>.<>.<>.<>@empty.bracket.domain.com"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   751 - assertIsFalse "name <> <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   752 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name <>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   753 - assertIsFalse "domain.part@with<>empty.bracket.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   754 - assertIsFalse "domain.part@<>with.empty.bracket.at.domain.start.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   755 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1<>.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   756 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com<>"                            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   757 - assertIsFalse "domain.part@with.empty.bracket.before<>.point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   758 - assertIsFalse "domain.part@with.empty.bracket.after.<>point.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   759 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   760 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment <>)domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   761 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   762 - assertIsFalse "top.level.domain.only@empty.bracket.<>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   763 - assertIsFalse ")(.local.part.starts.with.false.bracket1@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   764 - assertIsFalse "local.part.ends.with.false.bracket1)(@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   765 - assertIsFalse "local.part.with.false.bracket1)(character@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   766 - assertIsFalse "local.part.with.false.bracket1.before)(.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   767 - assertIsFalse "local.part.with.false.bracket1.after.)(point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   768 - assertIsFalse "local.part.with.double.false.bracket1)()(test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   769 - assertIsFalse "(comment )() local.part.with.false.bracket1.in.comment@domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *   770 - assertIsTrue  "\"string)(\".local.part.with.false.bracket1.in.String@domain.com"               =   1 =  OK 
   *   771 - assertIsFalse "\"string\)(\".local.part.with.escaped.false.bracket1.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   772 - assertIsFalse ")(@local.part.only.false.bracket1.domain.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   773 - assertIsFalse ")()()()()()(@local.part.only.consecutive.false.bracket1.domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   774 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   775 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1.in.display.name@domain.com>"      =   0 =  OK 
   *   776 - assertIsTrue  "<pointy.brackets2.with.false.bracket1.in.display.name@domain.com> name )("      =   0 =  OK 
   *   777 - assertIsFalse "domain.part@with)(false.bracket1.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   778 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   779 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   780 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   781 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   782 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   783 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   784 - assertIsFalse "domain.part.with.false.bracket1.in.comment@(comment )()domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *   785 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   786 - assertIsFalse "top.level.domain.only@false.bracket1.)("                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   787 - assertIsTrue  "}{.local.part.starts.with.false.bracket2@domain.com"                            =   0 =  OK 
   *   788 - assertIsTrue  "local.part.ends.with.false.bracket2}{@domain.com"                               =   0 =  OK 
   *   789 - assertIsTrue  "local.part.with.false.bracket2}{character@domain.com"                           =   0 =  OK 
   *   790 - assertIsTrue  "local.part.with.false.bracket2.before}{.point@domain.com"                       =   0 =  OK 
   *   791 - assertIsTrue  "local.part.with.false.bracket2.after.}{point@domain.com"                        =   0 =  OK 
   *   792 - assertIsTrue  "local.part.with.double.false.bracket2}{}{test@domain.com"                       =   0 =  OK 
   *   793 - assertIsTrue  "(comment }{) local.part.with.false.bracket2.in.comment@domain.com"              =   6 =  OK 
   *   794 - assertIsTrue  "\"string}{\".local.part.with.false.bracket2.in.String@domain.com"               =   1 =  OK 
   *   795 - assertIsFalse "\"string\}{\".local.part.with.escaped.false.bracket2.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   796 - assertIsTrue  "}{@local.part.only.false.bracket2.domain.com"                                   =   0 =  OK 
   *   797 - assertIsTrue  "}{}{}{}{}{}{@local.part.only.consecutive.false.bracket2.domain.com"             =   0 =  OK 
   *   798 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                                    =   0 =  OK 
   *   799 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   800 - assertIsFalse "<pointy.brackets2.with.false.bracket2.in.display.name@domain.com> name }{"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   801 - assertIsFalse "domain.part@with}{false.bracket2.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   802 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   803 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   804 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   805 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   806 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   807 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   808 - assertIsTrue  "domain.part.with.false.bracket2.in.comment@(comment }{)domain.com"              =   6 =  OK 
   *   809 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   810 - assertIsFalse "top.level.domain.only@false.bracket2.}{"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   811 - assertIsFalse "][.local.part.starts.with.false.bracket3@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   812 - assertIsFalse "local.part.ends.with.false.bracket3][@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   813 - assertIsFalse "local.part.with.false.bracket3][character@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   814 - assertIsFalse "local.part.with.false.bracket3.before][.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   815 - assertIsFalse "local.part.with.false.bracket3.after.][point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   816 - assertIsFalse "local.part.with.double.false.bracket3][][test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   817 - assertIsFalse "(comment ][) local.part.with.false.bracket3.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   818 - assertIsTrue  "\"string][\".local.part.with.false.bracket3.in.String@domain.com"               =   1 =  OK 
   *   819 - assertIsFalse "\"string\][\".local.part.with.escaped.false.bracket3.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   820 - assertIsFalse "][@local.part.only.false.bracket3.domain.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   821 - assertIsFalse "][][][][][][@local.part.only.consecutive.false.bracket3.domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   822 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   823 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   824 - assertIsFalse "<pointy.brackets2.with.false.bracket3.in.display.name@domain.com> name ]["      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   825 - assertIsFalse "domain.part@with][false.bracket3.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   826 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   827 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   828 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   829 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   830 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   831 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   832 - assertIsFalse "domain.part.with.false.bracket3.in.comment@(comment ][)domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   833 - assertIsFalse "domain.part.only.false.bracket3@][.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   834 - assertIsFalse "top.level.domain.only@false.bracket3.]["                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   835 - assertIsFalse "><.local.part.starts.with.false.bracket4@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   836 - assertIsFalse "local.part.ends.with.false.bracket4><@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   837 - assertIsFalse "local.part.with.false.bracket4><character@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   838 - assertIsFalse "local.part.with.false.bracket4.before><.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   839 - assertIsFalse "local.part.with.false.bracket4.after.><point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   840 - assertIsFalse "local.part.with.double.false.bracket4><><test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   841 - assertIsFalse "(comment ><) local.part.with.false.bracket4.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   842 - assertIsTrue  "\"string><\".local.part.with.false.bracket4.in.String@domain.com"               =   1 =  OK 
   *   843 - assertIsFalse "\"string\><\".local.part.with.escaped.false.bracket4.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   844 - assertIsFalse "><@local.part.only.false.bracket4.domain.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   845 - assertIsFalse "><><><><><><@local.part.only.consecutive.false.bracket4.domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   846 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   847 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   848 - assertIsFalse "<pointy.brackets2.with.false.bracket4.in.display.name@domain.com> name ><"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   849 - assertIsFalse "domain.part@with><false.bracket4.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   850 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   851 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   852 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   853 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   854 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   855 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   856 - assertIsFalse "domain.part.with.false.bracket4.in.comment@(comment ><)domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   857 - assertIsFalse "domain.part.only.false.bracket4@><.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   858 - assertIsFalse "top.level.domain.only@false.bracket4.><"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   859 - assertIsFalse "<.local.part.starts.with.lower.than@domain.com"                                 =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   860 - assertIsFalse "local.part.ends.with.lower.than<@domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   861 - assertIsFalse "local.part.with.lower.than<character@domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   862 - assertIsFalse "local.part.with.lower.than.before<.point@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   863 - assertIsFalse "local.part.with.lower.than.after.<point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   864 - assertIsFalse "local.part.with.double.lower.than<<test@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   865 - assertIsFalse "(comment <) local.part.with.lower.than.in.comment@domain.com"                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   866 - assertIsTrue  "\"string<\".local.part.with.lower.than.in.String@domain.com"                    =   1 =  OK 
   *   867 - assertIsFalse "\"string\<\".local.part.with.escaped.lower.than.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   868 - assertIsFalse "<@local.part.only.lower.than.domain.com"                                        =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   869 - assertIsFalse "<<<<<<@local.part.only.consecutive.lower.than.domain.com"                       =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   870 - assertIsFalse "<.<.<.<.<.<@lower.than.domain.com"                                              =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   871 - assertIsFalse "name < <pointy.brackets1.with.lower.than.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   872 - assertIsFalse "<pointy.brackets2.with.lower.than.in.display.name@domain.com> name <"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   873 - assertIsFalse "domain.part@with<lower.than.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   874 - assertIsFalse "domain.part@<with.lower.than.at.domain.start.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   875 - assertIsFalse "domain.part@with.lower.than.at.domain.end1<.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   876 - assertIsFalse "domain.part@with.lower.than.at.domain.end2.com<"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   877 - assertIsFalse "domain.part@with.lower.than.before<.point.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   878 - assertIsFalse "domain.part@with.lower.than.after.<point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   879 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   880 - assertIsFalse "domain.part.with.lower.than.in.comment@(comment <)domain.com"                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   881 - assertIsFalse "domain.part.only.lower.than@<.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   882 - assertIsFalse "top.level.domain.only@lower.than.<"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   883 - assertIsFalse ">.local.part.starts.with.greater.than@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   884 - assertIsFalse "local.part.ends.with.greater.than>@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   885 - assertIsFalse "local.part.with.greater.than>character@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   886 - assertIsFalse "local.part.with.greater.than.before>.point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   887 - assertIsFalse "local.part.with.greater.than.after.>point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   888 - assertIsFalse "local.part.with.double.greater.than>>test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   889 - assertIsFalse "(comment >) local.part.with.greater.than.in.comment@domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   890 - assertIsTrue  "\"string>\".local.part.with.greater.than.in.String@domain.com"                  =   1 =  OK 
   *   891 - assertIsFalse "\"string\>\".local.part.with.escaped.greater.than.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   892 - assertIsFalse ">@local.part.only.greater.than.domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   893 - assertIsFalse ">>>>>>@local.part.only.consecutive.greater.than.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   894 - assertIsFalse ">.>.>.>.>.>@greater.than.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   895 - assertIsFalse "name > <pointy.brackets1.with.greater.than.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   896 - assertIsFalse "<pointy.brackets2.with.greater.than.in.display.name@domain.com> name >"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   897 - assertIsFalse "domain.part@with>greater.than.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   898 - assertIsFalse "domain.part@>with.greater.than.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   899 - assertIsFalse "domain.part@with.greater.than.at.domain.end1>.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   900 - assertIsFalse "domain.part@with.greater.than.at.domain.end2.com>"                              =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *   901 - assertIsFalse "domain.part@with.greater.than.before>.point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   902 - assertIsFalse "domain.part@with.greater.than.after.>point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   903 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   904 - assertIsFalse "domain.part.with.greater.than.in.comment@(comment >)domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   905 - assertIsFalse "domain.part.only.greater.than@>.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   906 - assertIsFalse "top.level.domain.only@greater.than.>"                                           =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *   907 - assertIsTrue  "~.local.part.starts.with.tilde@domain.com"                                      =   0 =  OK 
   *   908 - assertIsTrue  "local.part.ends.with.tilde~@domain.com"                                         =   0 =  OK 
   *   909 - assertIsTrue  "local.part.with.tilde~character@domain.com"                                     =   0 =  OK 
   *   910 - assertIsTrue  "local.part.with.tilde.before~.point@domain.com"                                 =   0 =  OK 
   *   911 - assertIsTrue  "local.part.with.tilde.after.~point@domain.com"                                  =   0 =  OK 
   *   912 - assertIsTrue  "local.part.with.double.tilde~~test@domain.com"                                  =   0 =  OK 
   *   913 - assertIsTrue  "(comment ~) local.part.with.tilde.in.comment@domain.com"                        =   6 =  OK 
   *   914 - assertIsTrue  "\"string~\".local.part.with.tilde.in.String@domain.com"                         =   1 =  OK 
   *   915 - assertIsFalse "\"string\~\".local.part.with.escaped.tilde.in.String@domain.com"                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   916 - assertIsTrue  "~@local.part.only.tilde.domain.com"                                             =   0 =  OK 
   *   917 - assertIsTrue  "~~~~~~@local.part.only.consecutive.tilde.domain.com"                            =   0 =  OK 
   *   918 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                                   =   0 =  OK 
   *   919 - assertIsFalse "name ~ <pointy.brackets1.with.tilde.in.display.name@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   920 - assertIsFalse "<pointy.brackets2.with.tilde.in.display.name@domain.com> name ~"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   921 - assertIsFalse "domain.part@with~tilde.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   922 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   923 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   924 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   925 - assertIsFalse "domain.part@with.tilde.before~.point.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   926 - assertIsFalse "domain.part@with.tilde.after.~point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   927 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   928 - assertIsTrue  "domain.part.with.tilde.in.comment@(comment ~)domain.com"                        =   6 =  OK 
   *   929 - assertIsFalse "domain.part.only.tilde@~.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   930 - assertIsFalse "top.level.domain.only@tilde.~"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   931 - assertIsTrue  "^.local.part.starts.with.xor@domain.com"                                        =   0 =  OK 
   *   932 - assertIsTrue  "local.part.ends.with.xor^@domain.com"                                           =   0 =  OK 
   *   933 - assertIsTrue  "local.part.with.xor^character@domain.com"                                       =   0 =  OK 
   *   934 - assertIsTrue  "local.part.with.xor.before^.point@domain.com"                                   =   0 =  OK 
   *   935 - assertIsTrue  "local.part.with.xor.after.^point@domain.com"                                    =   0 =  OK 
   *   936 - assertIsTrue  "local.part.with.double.xor^^test@domain.com"                                    =   0 =  OK 
   *   937 - assertIsTrue  "(comment ^) local.part.with.xor.in.comment@domain.com"                          =   6 =  OK 
   *   938 - assertIsTrue  "\"string^\".local.part.with.xor.in.String@domain.com"                           =   1 =  OK 
   *   939 - assertIsFalse "\"string\^\".local.part.with.escaped.xor.in.String@domain.com"                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   940 - assertIsTrue  "^@local.part.only.xor.domain.com"                                               =   0 =  OK 
   *   941 - assertIsTrue  "^^^^^^@local.part.only.consecutive.xor.domain.com"                              =   0 =  OK 
   *   942 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                                     =   0 =  OK 
   *   943 - assertIsFalse "name ^ <pointy.brackets1.with.xor.in.display.name@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   944 - assertIsFalse "<pointy.brackets2.with.xor.in.display.name@domain.com> name ^"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   945 - assertIsFalse "domain.part@with^xor.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   946 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   947 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   948 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   949 - assertIsFalse "domain.part@with.xor.before^.point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   950 - assertIsFalse "domain.part@with.xor.after.^point.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   951 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   952 - assertIsTrue  "domain.part.with.xor.in.comment@(comment ^)domain.com"                          =   6 =  OK 
   *   953 - assertIsFalse "domain.part.only.xor@^.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   954 - assertIsFalse "top.level.domain.only@xor.^"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   955 - assertIsFalse ":.local.part.starts.with.colon@domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   956 - assertIsFalse "local.part.ends.with.colon:@domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   957 - assertIsFalse "local.part.with.colon:character@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   958 - assertIsFalse "local.part.with.colon.before:.point@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   959 - assertIsFalse "local.part.with.colon.after.:point@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   960 - assertIsFalse "local.part.with.double.colon::test@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   961 - assertIsFalse "(comment :) local.part.with.colon.in.comment@domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   962 - assertIsTrue  "\"string:\".local.part.with.colon.in.String@domain.com"                         =   1 =  OK 
   *   963 - assertIsFalse "\"string\:\".local.part.with.escaped.colon.in.String@domain.com"                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   964 - assertIsFalse ":@local.part.only.colon.domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   965 - assertIsFalse "::::::@local.part.only.consecutive.colon.domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   966 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   967 - assertIsFalse "name : <pointy.brackets1.with.colon.in.display.name@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   968 - assertIsFalse "<pointy.brackets2.with.colon.in.display.name@domain.com> name :"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   969 - assertIsFalse "domain.part@with:colon.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   970 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   971 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   972 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   973 - assertIsFalse "domain.part@with.colon.before:.point.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   974 - assertIsFalse "domain.part@with.colon.after.:point.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   975 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   976 - assertIsFalse "domain.part.with.colon.in.comment@(comment :)domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   977 - assertIsFalse "domain.part.only.colon@:.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   978 - assertIsFalse "top.level.domain.only@colon.:"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   979 - assertIsFalse " .local.part.starts.with.space@domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   980 - assertIsFalse "local.part.ends.with.space @domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   981 - assertIsFalse "local.part.with.space character@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   982 - assertIsFalse "local.part.with.space.before .point@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   983 - assertIsFalse "local.part.with.space.after. point@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   984 - assertIsFalse "local.part.with.double.space  test@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   985 - assertIsTrue  "(comment  ) local.part.with.space.in.comment@domain.com"                        =   6 =  OK 
   *   986 - assertIsTrue  "\"string \".local.part.with.space.in.String@domain.com"                         =   1 =  OK 
   *   987 - assertIsTrue  "\"string\ \".local.part.with.escaped.space.in.String@domain.com"                =   1 =  OK 
   *   988 - assertIsFalse " @local.part.only.space.domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   989 - assertIsFalse "      @local.part.only.consecutive.space.domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   990 - assertIsFalse " . . . . . @space.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   991 - assertIsTrue  "name   <pointy.brackets1.with.space.in.display.name@domain.com>"                =   0 =  OK 
   *   992 - assertIsTrue  "<pointy.brackets2.with.space.in.display.name@domain.com> name  "                =   0 =  OK 
   *   993 - assertIsFalse "domain.part@with space.com"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   994 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   995 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   996 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   997 - assertIsFalse "domain.part@with.space.before .point.com"                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   998 - assertIsFalse "domain.part@with.space.after. point.com"                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   999 - assertIsFalse "domain.part@with.consecutive.space  test.com"                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  1000 - assertIsTrue  "domain.part.with.space.in.comment@(comment  )domain.com"                        =   6 =  OK 
   *  1001 - assertIsFalse "domain.part.only.space@ .com"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  1002 - assertIsFalse "top.level.domain.only@space. "                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1003 - assertIsFalse ",.local.part.starts.with.comma@domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1004 - assertIsFalse "local.part.ends.with.comma,@domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1005 - assertIsFalse "local.part.with.comma,character@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1006 - assertIsFalse "local.part.with.comma.before,.point@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1007 - assertIsFalse "local.part.with.comma.after.,point@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1008 - assertIsFalse "local.part.with.double.comma,,test@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1009 - assertIsFalse "(comment ,) local.part.with.comma.in.comment@domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1010 - assertIsTrue  "\"string,\".local.part.with.comma.in.String@domain.com"                         =   1 =  OK 
   *  1011 - assertIsFalse "\"string\,\".local.part.with.escaped.comma.in.String@domain.com"                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1012 - assertIsFalse ",@local.part.only.comma.domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1013 - assertIsFalse ",,,,,,@local.part.only.consecutive.comma.domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1014 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1015 - assertIsFalse "name , <pointy.brackets1.with.comma.in.display.name@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1016 - assertIsFalse "<pointy.brackets2.with.comma.in.display.name@domain.com> name ,"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1017 - assertIsFalse "domain.part@with,comma.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1018 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1019 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1020 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1021 - assertIsFalse "domain.part@with.comma.before,.point.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1022 - assertIsFalse "domain.part@with.comma.after.,point.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1023 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1024 - assertIsFalse "domain.part.with.comma.in.comment@(comment ,)domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1025 - assertIsFalse "domain.part.only.comma@,.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1026 - assertIsFalse "top.level.domain.only@comma.,"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1027 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1028 - assertIsFalse "local.part.ends.with.at@@domain.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1029 - assertIsFalse "local.part.with.at@character@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1030 - assertIsFalse "local.part.with.at.before@.point@domain.com"                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1031 - assertIsFalse "local.part.with.at.after.@point@domain.com"                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  1032 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1033 - assertIsTrue  "(comment @) local.part.with.at.in.comment@domain.com"                           =   6 =  OK 
   *  1034 - assertIsTrue  "\"string@\".local.part.with.at.in.String@domain.com"                            =   1 =  OK 
   *  1035 - assertIsTrue  "\"string\@\".local.part.with.escaped.at.in.String@domain.com"                   =   1 =  OK 
   *  1036 - assertIsFalse "@@local.part.only.at.domain.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1037 - assertIsFalse "@@@@@@@local.part.only.consecutive.at.domain.com"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1038 - assertIsFalse "@.@.@.@.@.@@at.domain.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1039 - assertIsFalse "name @ <pointy.brackets1.with.at.in.display.name@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1040 - assertIsFalse "<pointy.brackets2.with.at.in.display.name@domain.com> name @"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1041 - assertIsFalse "domain.part@with@at.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1042 - assertIsFalse "domain.part@@with.at.at.domain.start.com"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1043 - assertIsFalse "domain.part@with.at.at.domain.end1@.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1044 - assertIsFalse "domain.part@with.at.at.domain.end2.com@"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1045 - assertIsFalse "domain.part@with.at.before@.point.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1046 - assertIsFalse "domain.part@with.at.after.@point.com"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1047 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1048 - assertIsTrue  "domain.part.with.at.in.comment@(comment @)domain.com"                           =   6 =  OK 
   *  1049 - assertIsFalse "domain.part.only.at@@.com"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1050 - assertIsFalse "top.level.domain.only@at.@"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1051 - assertIsFalse "§.local.part.starts.with.paragraph@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1052 - assertIsFalse "local.part.ends.with.paragraph§@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1053 - assertIsFalse "local.part.with.paragraph§character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1054 - assertIsFalse "local.part.with.paragraph.before§.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1055 - assertIsFalse "local.part.with.paragraph.after.§point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1056 - assertIsFalse "local.part.with.double.paragraph§§test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1057 - assertIsFalse "(comment §) local.part.with.paragraph.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1058 - assertIsFalse "\"string§\".local.part.with.paragraph.in.String@domain.com"                     =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  1059 - assertIsFalse "\"string\§\".local.part.with.escaped.paragraph.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1060 - assertIsFalse "§@local.part.only.paragraph.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1061 - assertIsFalse "§§§§§§@local.part.only.consecutive.paragraph.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1062 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1063 - assertIsFalse "name § <pointy.brackets1.with.paragraph.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1064 - assertIsFalse "<pointy.brackets2.with.paragraph.in.display.name@domain.com> name §"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1065 - assertIsFalse "domain.part@with§paragraph.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1066 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1067 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1068 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1069 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1070 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1071 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1072 - assertIsFalse "domain.part.with.paragraph.in.comment@(comment §)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1073 - assertIsFalse "domain.part.only.paragraph@§.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1074 - assertIsFalse "top.level.domain.only@paragraph.§"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1075 - assertIsTrue  "'.local.part.starts.with.double.quote@domain.com"                               =   0 =  OK 
   *  1076 - assertIsTrue  "local.part.ends.with.double.quote'@domain.com"                                  =   0 =  OK 
   *  1077 - assertIsTrue  "local.part.with.double.quote'character@domain.com"                              =   0 =  OK 
   *  1078 - assertIsTrue  "local.part.with.double.quote.before'.point@domain.com"                          =   0 =  OK 
   *  1079 - assertIsTrue  "local.part.with.double.quote.after.'point@domain.com"                           =   0 =  OK 
   *  1080 - assertIsTrue  "local.part.with.double.double.quote''test@domain.com"                           =   0 =  OK 
   *  1081 - assertIsTrue  "(comment ') local.part.with.double.quote.in.comment@domain.com"                 =   6 =  OK 
   *  1082 - assertIsTrue  "\"string'\".local.part.with.double.quote.in.String@domain.com"                  =   1 =  OK 
   *  1083 - assertIsTrue  "\"string\'\".local.part.with.escaped.double.quote.in.String@domain.com"         =   1 =  OK 
   *  1084 - assertIsTrue  "'@local.part.only.double.quote.domain.com"                                      =   0 =  OK 
   *  1085 - assertIsTrue  "''''''@local.part.only.consecutive.double.quote.domain.com"                     =   0 =  OK 
   *  1086 - assertIsTrue  "'.'.'.'.'.'@double.quote.domain.com"                                            =   0 =  OK 
   *  1087 - assertIsFalse "name ' <pointy.brackets1.with.double.quote.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1088 - assertIsFalse "<pointy.brackets2.with.double.quote.in.display.name@domain.com> name '"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1089 - assertIsFalse "domain.part@with'double.quote.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1090 - assertIsFalse "domain.part@'with.double.quote.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1091 - assertIsFalse "domain.part@with.double.quote.at.domain.end1'.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1092 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com'"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1093 - assertIsFalse "domain.part@with.double.quote.before'.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1094 - assertIsFalse "domain.part@with.double.quote.after.'point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1095 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1096 - assertIsTrue  "domain.part.with.double.quote.in.comment@(comment ')domain.com"                 =   6 =  OK 
   *  1097 - assertIsFalse "domain.part.only.double.quote@'.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1098 - assertIsFalse "top.level.domain.only@double.quote.'"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1099 - assertIsTrue  "/.local.part.starts.with.forward.slash@domain.com"                              =   0 =  OK 
   *  1100 - assertIsTrue  "local.part.ends.with.forward.slash/@domain.com"                                 =   0 =  OK 
   *  1101 - assertIsTrue  "local.part.with.forward.slash/character@domain.com"                             =   0 =  OK 
   *  1102 - assertIsTrue  "local.part.with.forward.slash.before/.point@domain.com"                         =   0 =  OK 
   *  1103 - assertIsTrue  "local.part.with.forward.slash.after./point@domain.com"                          =   0 =  OK 
   *  1104 - assertIsTrue  "local.part.with.double.forward.slash//test@domain.com"                          =   0 =  OK 
   *  1105 - assertIsTrue  "(comment /) local.part.with.forward.slash.in.comment@domain.com"                =   6 =  OK 
   *  1106 - assertIsTrue  "\"string/\".local.part.with.forward.slash.in.String@domain.com"                 =   1 =  OK 
   *  1107 - assertIsFalse "\"string\/\".local.part.with.escaped.forward.slash.in.String@domain.com"        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1108 - assertIsTrue  "/@local.part.only.forward.slash.domain.com"                                     =   0 =  OK 
   *  1109 - assertIsTrue  "//////@local.part.only.consecutive.forward.slash.domain.com"                    =   0 =  OK 
   *  1110 - assertIsTrue  "/./././././@forward.slash.domain.com"                                           =   0 =  OK 
   *  1111 - assertIsFalse "name / <pointy.brackets1.with.forward.slash.in.display.name@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1112 - assertIsFalse "<pointy.brackets2.with.forward.slash.in.display.name@domain.com> name /"        =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1113 - assertIsFalse "domain.part@with/forward.slash.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1114 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1115 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1116 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1117 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1118 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1119 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1120 - assertIsTrue  "domain.part.with.forward.slash.in.comment@(comment /)domain.com"                =   6 =  OK 
   *  1121 - assertIsFalse "domain.part.only.forward.slash@/.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1122 - assertIsFalse "top.level.domain.only@forward.slash./"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1123 - assertIsTrue  "-.local.part.starts.with.hyphen@domain.com"                                     =   0 =  OK 
   *  1124 - assertIsTrue  "local.part.ends.with.hyphen-@domain.com"                                        =   0 =  OK 
   *  1125 - assertIsTrue  "local.part.with.hyphen-character@domain.com"                                    =   0 =  OK 
   *  1126 - assertIsTrue  "local.part.with.hyphen.before-.point@domain.com"                                =   0 =  OK 
   *  1127 - assertIsTrue  "local.part.with.hyphen.after.-point@domain.com"                                 =   0 =  OK 
   *  1128 - assertIsTrue  "local.part.with.double.hyphen--test@domain.com"                                 =   0 =  OK 
   *  1129 - assertIsTrue  "(comment -) local.part.with.hyphen.in.comment@domain.com"                       =   6 =  OK 
   *  1130 - assertIsTrue  "\"string-\".local.part.with.hyphen.in.String@domain.com"                        =   1 =  OK 
   *  1131 - assertIsFalse "\"string\-\".local.part.with.escaped.hyphen.in.String@domain.com"               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1132 - assertIsTrue  "-@local.part.only.hyphen.domain.com"                                            =   0 =  OK 
   *  1133 - assertIsTrue  "------@local.part.only.consecutive.hyphen.domain.com"                           =   0 =  OK 
   *  1134 - assertIsTrue  "-.-.-.-.-.-@hyphen.domain.com"                                                  =   0 =  OK 
   *  1135 - assertIsFalse "name - <pointy.brackets1.with.hyphen.in.display.name@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1136 - assertIsFalse "<pointy.brackets2.with.hyphen.in.display.name@domain.com> name -"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1137 - assertIsTrue  "domain.part@with-hyphen.com"                                                    =   0 =  OK 
   *  1138 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1139 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1140 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  1141 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1142 - assertIsFalse "domain.part@with.hyphen.after.-point.com"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1143 - assertIsTrue  "domain.part@with.consecutive.hyphen--test.com"                                  =   0 =  OK 
   *  1144 - assertIsTrue  "domain.part.with.hyphen.in.comment@(comment -)domain.com"                       =   6 =  OK 
   *  1145 - assertIsFalse "domain.part.only.hyphen@-.com"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1146 - assertIsFalse "top.level.domain.only@hyphen.-"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1147 - assertIsFalse "\"\".local.part.starts.with.empty.string1@domain.com"                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1148 - assertIsFalse "local.part.ends.with.empty.string1\"\"@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1149 - assertIsFalse "local.part.with.empty.string1\"\"character@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1150 - assertIsFalse "local.part.with.empty.string1.before\"\".point@domain.com"                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1151 - assertIsFalse "local.part.with.empty.string1.after.\"\"point@domain.com"                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1152 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"test@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1153 - assertIsFalse "(comment \"\") local.part.with.empty.string1.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1154 - assertIsFalse "\"string\"\"\".local.part.with.empty.string1.in.String@domain.com"              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1155 - assertIsFalse "\"string\\"\"\".local.part.with.escaped.empty.string1.in.String@domain.com"     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1156 - assertIsFalse "\"\"@local.part.only.empty.string1.domain.com"                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1157 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.empty.string1.domain.com"  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1158 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"                         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1159 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1.in.display.name@domain.com>"     =   0 =  OK 
   *  1160 - assertIsTrue  "<pointy.brackets2.with.empty.string1.in.display.name@domain.com> name \"\""     =   0 =  OK 
   *  1161 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1162 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1163 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1164 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1165 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1166 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1167 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1168 - assertIsFalse "domain.part.with.empty.string1.in.comment@(comment \"\")domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1169 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1170 - assertIsFalse "top.level.domain.only@empty.string1.\"\""                                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1171 - assertIsFalse "a\"\"b.local.part.starts.with.empty.string2@domain.com"                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1172 - assertIsFalse "local.part.ends.with.empty.string2a\"\"b@domain.com"                            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1173 - assertIsFalse "local.part.with.empty.string2a\"\"bcharacter@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1174 - assertIsFalse "local.part.with.empty.string2.beforea\"\"b.point@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1175 - assertIsFalse "local.part.with.empty.string2.after.a\"\"bpoint@domain.com"                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1176 - assertIsFalse "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1177 - assertIsFalse "(comment a\"\"b) local.part.with.empty.string2.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1178 - assertIsFalse "\"stringa\"\"b\".local.part.with.empty.string2.in.String@domain.com"            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1179 - assertIsFalse "\"string\a\"\"b\".local.part.with.escaped.empty.string2.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1180 - assertIsFalse "a\"\"b@local.part.only.empty.string2.domain.com"                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1181 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@local.part.only.consecutive.empty.string2.domain.com" =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1182 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1183 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2.in.display.name@domain.com>"   =   0 =  OK 
   *  1184 - assertIsTrue  "<pointy.brackets2.with.empty.string2.in.display.name@domain.com> name a\"\"b"   =   0 =  OK 
   *  1185 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1186 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1187 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1188 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1189 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1190 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1191 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1192 - assertIsFalse "domain.part.with.empty.string2.in.comment@(comment a\"\"b)domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1193 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1194 - assertIsFalse "top.level.domain.only@empty.string2.a\"\"b"                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1195 - assertIsFalse "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com"                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1196 - assertIsFalse "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com"                   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1197 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"character@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1198 - assertIsFalse "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1199 - assertIsFalse "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com"            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1200 - assertIsFalse "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1201 - assertIsFalse "(comment \"\"\"\") local.part.with.double.empty.string1.in.comment@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1202 - assertIsFalse "\"string\"\"\"\"\".local.part.with.double.empty.string1.in.String@domain.com"   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1203 - assertIsFalse "\"string\\"\"\"\"\".local.part.with.escaped.double.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1204 - assertIsFalse "\"\"\"\"@local.part.only.double.empty.string1.domain.com"                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1205 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1206 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1207 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1.in.display.name@domain.com>" =   0 =  OK 
   *  1208 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1.in.display.name@domain.com> name \"\"\"\"" =   0 =  OK 
   *  1209 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1210 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1211 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1212 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1213 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1214 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1215 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com"      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1216 - assertIsFalse "domain.part.with.double.empty.string1.in.comment@(comment \"\"\"\")domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1217 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1218 - assertIsFalse "top.level.domain.only@double.empty.string1.\"\"\"\""                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1219 - assertIsFalse "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com"               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1220 - assertIsFalse "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1221 - assertIsFalse "local.part.with.double.empty.string2\"\".\"\"character@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1222 - assertIsFalse "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1223 - assertIsFalse "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com"           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1224 - assertIsFalse "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com"   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1225 - assertIsFalse "(comment \"\".\"\") local.part.with.double.empty.string2.in.comment@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1226 - assertIsFalse "\"string\"\".\"\"\".local.part.with.double.empty.string2.in.String@domain.com"  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1227 - assertIsFalse "\"string\\"\".\"\"\".local.part.with.escaped.double.empty.string2.in.String@domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1228 - assertIsFalse "\"\".\"\"@local.part.only.double.empty.string2.domain.com"                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1229 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\"@local.part.only.consecutive.double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1230 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com"             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1231 - assertIsFalse "name \"\".\"\" <pointy.brackets1.with.double.empty.string2.in.display.name@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1232 - assertIsFalse "<pointy.brackets2.with.double.empty.string2.in.display.name@domain.com> name \"\".\"\"" =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1233 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1234 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1235 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1236 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1237 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1238 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1239 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1240 - assertIsFalse "domain.part.with.double.empty.string2.in.comment@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1241 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1242 - assertIsFalse "top.level.domain.only@double.empty.string2.\"\".\"\""                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1243 - assertIsTrue  "0.local.part.starts.with.number0@domain.com"                                    =   0 =  OK 
   *  1244 - assertIsTrue  "local.part.ends.with.number00@domain.com"                                       =   0 =  OK 
   *  1245 - assertIsTrue  "local.part.with.number00character@domain.com"                                   =   0 =  OK 
   *  1246 - assertIsTrue  "local.part.with.number0.before0.point@domain.com"                               =   0 =  OK 
   *  1247 - assertIsTrue  "local.part.with.number0.after.0point@domain.com"                                =   0 =  OK 
   *  1248 - assertIsTrue  "local.part.with.double.number000test@domain.com"                                =   0 =  OK 
   *  1249 - assertIsTrue  "(comment 0) local.part.with.number0.in.comment@domain.com"                      =   6 =  OK 
   *  1250 - assertIsTrue  "\"string0\".local.part.with.number0.in.String@domain.com"                       =   1 =  OK 
   *  1251 - assertIsFalse "\"string\0\".local.part.with.escaped.number0.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1252 - assertIsTrue  "0@local.part.only.number0.domain.com"                                           =   0 =  OK 
   *  1253 - assertIsTrue  "000000@local.part.only.consecutive.number0.domain.com"                          =   0 =  OK 
   *  1254 - assertIsTrue  "0.0.0.0.0.0@number0.domain.com"                                                 =   0 =  OK 
   *  1255 - assertIsTrue  "name 0 <pointy.brackets1.with.number0.in.display.name@domain.com>"              =   0 =  OK 
   *  1256 - assertIsTrue  "<pointy.brackets2.with.number0.in.display.name@domain.com> name 0"              =   0 =  OK 
   *  1257 - assertIsTrue  "domain.part@with0number0.com"                                                   =   0 =  OK 
   *  1258 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                                  =   0 =  OK 
   *  1259 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                                   =   0 =  OK 
   *  1260 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                                   =   0 =  OK 
   *  1261 - assertIsTrue  "domain.part@with.number0.before0.point.com"                                     =   0 =  OK 
   *  1262 - assertIsTrue  "domain.part@with.number0.after.0point.com"                                      =   0 =  OK 
   *  1263 - assertIsTrue  "domain.part@with.consecutive.number000test.com"                                 =   0 =  OK 
   *  1264 - assertIsTrue  "domain.part.with.number0.in.comment@(comment 0)domain.com"                      =   6 =  OK 
   *  1265 - assertIsTrue  "domain.part.only.number0@0.com"                                                 =   0 =  OK 
   *  1266 - assertIsFalse "top.level.domain.only@number0.0"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1267 - assertIsTrue  "9.local.part.starts.with.number9@domain.com"                                    =   0 =  OK 
   *  1268 - assertIsTrue  "local.part.ends.with.number99@domain.com"                                       =   0 =  OK 
   *  1269 - assertIsTrue  "local.part.with.number99character@domain.com"                                   =   0 =  OK 
   *  1270 - assertIsTrue  "local.part.with.number9.before9.point@domain.com"                               =   0 =  OK 
   *  1271 - assertIsTrue  "local.part.with.number9.after.9point@domain.com"                                =   0 =  OK 
   *  1272 - assertIsTrue  "local.part.with.double.number999test@domain.com"                                =   0 =  OK 
   *  1273 - assertIsTrue  "(comment 9) local.part.with.number9.in.comment@domain.com"                      =   6 =  OK 
   *  1274 - assertIsTrue  "\"string9\".local.part.with.number9.in.String@domain.com"                       =   1 =  OK 
   *  1275 - assertIsFalse "\"string\9\".local.part.with.escaped.number9.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1276 - assertIsTrue  "9@local.part.only.number9.domain.com"                                           =   0 =  OK 
   *  1277 - assertIsTrue  "999999@local.part.only.consecutive.number9.domain.com"                          =   0 =  OK 
   *  1278 - assertIsTrue  "9.9.9.9.9.9@number9.domain.com"                                                 =   0 =  OK 
   *  1279 - assertIsTrue  "name 9 <pointy.brackets1.with.number9.in.display.name@domain.com>"              =   0 =  OK 
   *  1280 - assertIsTrue  "<pointy.brackets2.with.number9.in.display.name@domain.com> name 9"              =   0 =  OK 
   *  1281 - assertIsTrue  "domain.part@with9number9.com"                                                   =   0 =  OK 
   *  1282 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                                  =   0 =  OK 
   *  1283 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                                   =   0 =  OK 
   *  1284 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                                   =   0 =  OK 
   *  1285 - assertIsTrue  "domain.part@with.number9.before9.point.com"                                     =   0 =  OK 
   *  1286 - assertIsTrue  "domain.part@with.number9.after.9point.com"                                      =   0 =  OK 
   *  1287 - assertIsTrue  "domain.part@with.consecutive.number999test.com"                                 =   0 =  OK 
   *  1288 - assertIsTrue  "domain.part.with.number9.in.comment@(comment 9)domain.com"                      =   6 =  OK 
   *  1289 - assertIsTrue  "domain.part.only.number9@9.com"                                                 =   0 =  OK 
   *  1290 - assertIsFalse "top.level.domain.only@number9.9"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1291 - assertIsTrue  "0123456789.local.part.starts.with.numbers@domain.com"                           =   0 =  OK 
   *  1292 - assertIsTrue  "local.part.ends.with.numbers0123456789@domain.com"                              =   0 =  OK 
   *  1293 - assertIsTrue  "local.part.with.numbers0123456789character@domain.com"                          =   0 =  OK 
   *  1294 - assertIsTrue  "local.part.with.numbers.before0123456789.point@domain.com"                      =   0 =  OK 
   *  1295 - assertIsTrue  "local.part.with.numbers.after.0123456789point@domain.com"                       =   0 =  OK 
   *  1296 - assertIsTrue  "local.part.with.double.numbers01234567890123456789test@domain.com"              =   0 =  OK 
   *  1297 - assertIsTrue  "(comment 0123456789) local.part.with.numbers.in.comment@domain.com"             =   6 =  OK 
   *  1298 - assertIsTrue  "\"string0123456789\".local.part.with.numbers.in.String@domain.com"              =   1 =  OK 
   *  1299 - assertIsFalse "\"string\0123456789\".local.part.with.escaped.numbers.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1300 - assertIsTrue  "0123456789@local.part.only.numbers.domain.com"                                  =   0 =  OK 
   *  1301 - assertIsTrue  "01234567890123@local.part.only.consecutive.numbers.domain.com"                  =   0 =  OK 
   *  1302 - assertIsTrue  "0123456789.0123456789.0123456789@numbers.domain.com"                            =   0 =  OK 
   *  1303 - assertIsTrue  "name 0123456789 <pointy.brackets1.with.numbers.in.display.name@domain.com>"     =   0 =  OK 
   *  1304 - assertIsTrue  "<pointy.brackets2.with.numbers.in.display.name@domain.com> name 0123456789"     =   0 =  OK 
   *  1305 - assertIsTrue  "domain.part@with0123456789numbers.com"                                          =   0 =  OK 
   *  1306 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"                         =   0 =  OK 
   *  1307 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                          =   0 =  OK 
   *  1308 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                          =   0 =  OK 
   *  1309 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                            =   0 =  OK 
   *  1310 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                             =   0 =  OK 
   *  1311 - assertIsTrue  "domain.part@with.consecutive.numbers01234567890123456789test.com"               =   0 =  OK 
   *  1312 - assertIsTrue  "domain.part.with.numbers.in.comment@(comment 0123456789)domain.com"             =   6 =  OK 
   *  1313 - assertIsTrue  "domain.part.only.numbers@0123456789.com"                                        =   0 =  OK 
   *  1314 - assertIsFalse "top.level.domain.only@numbers.0123456789"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  1315 - assertIsFalse "\.local.part.starts.with.slash@domain.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1316 - assertIsFalse "local.part.ends.with.slash\@domain.com"                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  1317 - assertIsFalse "local.part.with.slash\character@domain.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1318 - assertIsFalse "local.part.with.slash.before\.point@domain.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1319 - assertIsFalse "local.part.with.slash.after.\point@domain.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1320 - assertIsTrue  "local.part.with.double.slash\\test@domain.com"                                  =   0 =  OK 
   *  1321 - assertIsFalse "(comment \) local.part.with.slash.in.comment@domain.com"                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  1322 - assertIsFalse "\"string\\".local.part.with.slash.in.String@domain.com"                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  1323 - assertIsTrue  "\"string\\\".local.part.with.escaped.slash.in.String@domain.com"                =   1 =  OK 
   *  1324 - assertIsFalse "\@local.part.only.slash.domain.com"                                             =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  1325 - assertIsTrue  "\\\\\\@local.part.only.consecutive.slash.domain.com"                            =   0 =  OK 
   *  1326 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1327 - assertIsTrue  "escaped character is space \ <pointy.brackets1.with.slash.in.display.name@domain.com>" =   0 =  OK 
   *  1328 - assertIsFalse "no escaped character \<pointy.brackets1.with.slash.in.display.name@domain.com>" =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
   *  1329 - assertIsFalse "<pointy.brackets2.with.slash.in.display.name@domain.com> name \"                =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
   *  1330 - assertIsFalse "domain.part@with\slash.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1331 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1332 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1333 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1334 - assertIsFalse "domain.part@with.slash.before\.point.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1335 - assertIsFalse "domain.part@with.slash.after.\point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1336 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1337 - assertIsFalse "domain.part.with.slash.in.comment@(comment \)domain.com"                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  1338 - assertIsFalse "domain.part.only.slash@\.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1339 - assertIsFalse "top.level.domain.only@slash.\"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1340 - assertIsTrue  "\"str\".local.part.starts.with.string@domain.com"                               =   1 =  OK 
   *  1341 - assertIsFalse "local.part.ends.with.string\"str\"@domain.com"                                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1342 - assertIsFalse "local.part.with.string\"str\"character@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1343 - assertIsFalse "local.part.with.string.before\"str\".point@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1344 - assertIsFalse "local.part.with.string.after.\"str\"point@domain.com"                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1345 - assertIsFalse "local.part.with.double.string\"str\"\"str\"test@domain.com"                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1346 - assertIsFalse "(comment \"str\") local.part.with.string.in.comment@domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1347 - assertIsFalse "\"string\"str\"\".local.part.with.string.in.String@domain.com"                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1348 - assertIsFalse "\"string\\"str\"\".local.part.with.escaped.string.in.String@domain.com"         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1349 - assertIsTrue  "\"str\"@local.part.only.string.domain.com"                                      =   1 =  OK 
   *  1350 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@local.part.only.consecutive.string.domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1351 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"              =   1 =  OK 
   *  1352 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string.in.display.name@domain.com>"         =   0 =  OK 
   *  1353 - assertIsTrue  "<pointy.brackets2.with.string.in.display.name@domain.com> name \"str\""         =   0 =  OK 
   *  1354 - assertIsFalse "domain.part@with\"str\"string.com"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1355 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1356 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1357 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1358 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1359 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1360 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1361 - assertIsFalse "domain.part.with.string.in.comment@(comment \"str\")domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1362 - assertIsFalse "domain.part.only.string@\"str\".com"                                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1363 - assertIsFalse "top.level.domain.only@string.\"str\""                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1364 - assertIsFalse "(comment).local.part.starts.with.comment@domain.com"                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1365 - assertIsTrue  "local.part.ends.with.comment(comment)@domain.com"                               =   6 =  OK 
   *  1366 - assertIsFalse "local.part.with.comment(comment)character@domain.com"                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  1367 - assertIsFalse "local.part.with.comment.before(comment).point@domain.com"                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1368 - assertIsFalse "local.part.with.comment.after.(comment)point@domain.com"                        = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  1369 - assertIsFalse "local.part.with.double.comment(comment)(comment)test@domain.com"                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  1370 - assertIsFalse "(comment (comment)) local.part.with.comment.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1371 - assertIsTrue  "\"string(comment)\".local.part.with.comment.in.String@domain.com"               =   1 =  OK 
   *  1372 - assertIsFalse "\"string\(comment)\".local.part.with.escaped.comment.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1373 - assertIsFalse "(comment)@local.part.only.comment.domain.com"                                   =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *  1374 - assertIsFalse "(comment)(comment)(comment)@local.part.only.consecutive.comment.domain.com"     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  1375 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1376 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment.in.display.name@domain.com>"      =   0 =  OK 
   *  1377 - assertIsTrue  "<pointy.brackets2.with.comment.in.display.name@domain.com> name (comment)"      =   0 =  OK 
   *  1378 - assertIsFalse "domain.part@with(comment)comment.com"                                           = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  1379 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                          =   6 =  OK 
   *  1380 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1381 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                           =   6 =  OK 
   *  1382 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1383 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *  1384 - assertIsFalse "domain.part@with.consecutive.comment(comment)(comment)test.com"                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  1385 - assertIsFalse "domain.part.with.comment.in.comment@(comment (comment))domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1386 - assertIsFalse "domain.part.only.comment@(comment).com"                                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1387 - assertIsFalse "top.level.domain.only@comment.(comment)"                                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   * 
   * ---- IP V4 -----------------------------------------------------------------------------------------------------------------------
   * 
   *  1388 - assertIsFalse "\"\"@[]"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  1389 - assertIsFalse "\"\"@[1"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  1390 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})"       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1391 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1392 - assertIsFalse "1.2.3.4]@[5.6.7.8]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1393 - assertIsFalse "[1.2.3.4@[5.6.7.8]"                                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1394 - assertIsFalse "[1.2.3.4][5.6.7.8]@[9.10.11.12]"                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1395 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12]"                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1396 - assertIsFalse "[1.2.3.4]@[5.6.7.8]9.10.11.12]"                                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1397 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12["                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1398 - assertIsTrue  "ip4.in.local.part.as.string1.\"[1.2.3.4]\"@[5.6.7.8]"                           =   3 =  OK 
   *  1399 - assertIsTrue  "ip4.in.local.part.as.string2.\"@[1.2.3.4]\"@[5.6.7.8]"                          =   3 =  OK 
   *  1400 - assertIsFalse "ip4.ends.with.alpha.character1@[1.2.3.Z]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1401 - assertIsFalse "ip4.ends.with.alpha.character2@[1.2.3.]Z"                                       =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
   *  1402 - assertIsFalse "ip4.ends.with.top.level.domain@[1.2.3.].de"                                     =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
   *  1403 - assertIsFalse "ip4.with.double.ip4@[1.2.3.4][5.6.7.8]"                                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1404 - assertIsFalse "ip4.with.ip4.in.comment1@([1.2.3.4])"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1405 - assertIsFalse "ip4.with.ip4.in.comment2@([1.2.3.4])[5.6.7.8]"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1406 - assertIsFalse "ip4.with.ip4.in.comment3@[1.2.3.4]([5.6.7.8])"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1407 - assertIsTrue  "ip4.with.ip4.in.comment4@[1.2.3.4] (@)"                                         =   2 =  OK 
   *  1408 - assertIsTrue  "ip4.with.ip4.in.comment5@[1.2.3.4] (@.)"                                        =   2 =  OK 
   *  1409 - assertIsFalse "ip4.with.hex.numbers@[AB.CD.EF.EA]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1410 - assertIsFalse "ip4.with.hex.number.overflow@[AB.CD.EF.FF1]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1411 - assertIsFalse "ip4.with.double.brackets@[1.2.3.4][5.6.7.8]"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1412 - assertIsFalse "ip4.missing.at.sign[1.2.3.4]"                                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1413 - assertIsFalse "ip4.missing.the.start.bracket@]"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1414 - assertIsFalse "ip4.missing.the.end.bracket@["                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1415 - assertIsFalse "ip4.missing.the.start.bracket@1.2.3.4]"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1416 - assertIsFalse "ip4.missing.the.end.bracket@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1417 - assertIsFalse "ip4.missing.numbers.and.the.start.bracket@...]"                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1418 - assertIsFalse "ip4.missing.numbers.and.the.end.bracket@[..."                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1419 - assertIsFalse "ip4.missplaced.start.bracket1[@1.2.3.4]"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1420 - assertIsFalse "ip4.missing.the.first.number@[.2.3.4]"                                          =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1421 - assertIsFalse "ip4.missing.the.last.number@[1.2.3.]"                                           =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
   *  1422 - assertIsFalse "ip4.last.number.is.space@[1.2.3. ]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1423 - assertIsFalse "ip4.with.only.one.numberABC.DEF@[1]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1424 - assertIsFalse "ip4.with.only.two.numbers@[1.2]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1425 - assertIsFalse "ip4.with.only.three.numbers@[1.2.3]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1426 - assertIsFalse "ip4.with.five.numbers@[1.2.3.4.5]"                                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1427 - assertIsFalse "ip4.with.six.numbers@[1.2.3.4.5.6]"                                             =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1428 - assertIsFalse "ip4.with.byte.overflow1@[1.2.3.256]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1429 - assertIsFalse "ip4.with.byte.overflow2@[1.2.3.1000]"                                           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1430 - assertIsFalse "ip4.with.to.many.leading.zeros@[0001.000002.000003.00000004]"                   =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1431 - assertIsTrue  "ip4.with.two.leading.zeros@[001.002.003.004]"                                   =   2 =  OK 
   *  1432 - assertIsTrue  "ip4.zero@[0.0.0.0]"                                                             =   2 =  OK 
   *  1433 - assertIsTrue  "ip4.correct1@[1.2.3.4]"                                                         =   2 =  OK 
   *  1434 - assertIsTrue  "ip4.correct2@[255.255.255.255]"                                                 =   2 =  OK 
   *  1435 - assertIsTrue  "\"ip4.local.part.as.string\"@[127.0.0.1]"                                       =   3 =  OK 
   *  1436 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                             =   3 =  OK 
   *  1437 - assertIsFalse "ip4.no.email.adress[1.2.3.4]  but.with.space[1.2.3.4]"                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1438 - assertIsFalse "ip4.with.negative.number1@[-1.2.3.4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1439 - assertIsFalse "ip4.with.negative.number2@[1.-2.3.4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1440 - assertIsFalse "ip4.with.negative.number3@[1.2.-3.4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1441 - assertIsFalse "ip4.with.negative.number4@[1.2.3.-4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1442 - assertIsFalse "ip4.with.only.empty.brackets@[]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1443 - assertIsFalse "ip4.with.three.empty.brackets@[][][]"                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1444 - assertIsFalse "ip4.with.wrong.characters.in.brackets@[{][})][}][}\\"]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1445 - assertIsFalse "ip4.in.false.brackets@{1.2.3.4}"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1446 - assertIsFalse "ip4.with.semicolon.between.numbers@[123.14;5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1447 - assertIsFalse "ip4.with.semicolon.before.point@[123.145;.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1448 - assertIsFalse "ip4.with.semicolon.after.point@[123.145.;178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1449 - assertIsFalse "ip4.with.semicolon.before.start.bracket@;[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1450 - assertIsFalse "ip4.with.semicolon.after.start.bracket@[;123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1451 - assertIsFalse "ip4.with.semicolon.before.end.bracket@[123.145.178.90;]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1452 - assertIsFalse "ip4.with.semicolon.after.end.bracket@[123.145.178.90];"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1453 - assertIsFalse "ip4.with.only.one.dot.in.brackets@[.]"                                          =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1454 - assertIsFalse "ip4.with.only.double.dot.in.brackets@[..]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1455 - assertIsFalse "ip4.with.only.triple.dot.in.brackets@[...]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1456 - assertIsFalse "ip4.with.only.four.dots.in.brackets@[....]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1457 - assertIsFalse "ip4.with.false.consecutive.points@[1.2...3.4]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1458 - assertIsFalse "ip4.with.dot.between.numbers@[123.14.5.178.90]"                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1459 - assertIsFalse "ip4.with.dot.before.point@[123.145..178.90]"                                    =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1460 - assertIsFalse "ip4.with.dot.after.point@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1461 - assertIsFalse "ip4.with.dot.before.start.bracket@.[123.145.178.90]"                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1462 - assertIsFalse "ip4.with.dot.after.start.bracket@[.123.145.178.90]"                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1463 - assertIsFalse "ip4.with.dot.before.end.bracket@[123.145.178.90.]"                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1464 - assertIsFalse "ip4.with.dot.after.end.bracket@[123.145.178.90]."                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1465 - assertIsFalse "ip4.with.double.dot.between.numbers@[123.14..5.178.90]"                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1466 - assertIsFalse "ip4.with.double.dot.before.point@[123.145...178.90]"                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1467 - assertIsFalse "ip4.with.double.dot.after.point@[123.145...178.90]"                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1468 - assertIsFalse "ip4.with.double.dot.before.start.bracket@..[123.145.178.90]"                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1469 - assertIsFalse "ip4.with.double.dot.after.start.bracket@[..123.145.178.90]"                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1470 - assertIsFalse "ip4.with.double.dot.before.end.bracket@[123.145.178.90..]"                      =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1471 - assertIsFalse "ip4.with.double.dot.after.end.bracket@[123.145.178.90].."                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1472 - assertIsFalse "ip4.with.amp.between.numbers@[123.14&5.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1473 - assertIsFalse "ip4.with.amp.before.point@[123.145&.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1474 - assertIsFalse "ip4.with.amp.after.point@[123.145.&178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1475 - assertIsFalse "ip4.with.amp.before.start.bracket@&[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1476 - assertIsFalse "ip4.with.amp.after.start.bracket@[&123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1477 - assertIsFalse "ip4.with.amp.before.end.bracket@[123.145.178.90&]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1478 - assertIsFalse "ip4.with.amp.after.end.bracket@[123.145.178.90]&"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1479 - assertIsFalse "ip4.with.asterisk.between.numbers@[123.14*5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1480 - assertIsFalse "ip4.with.asterisk.before.point@[123.145*.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1481 - assertIsFalse "ip4.with.asterisk.after.point@[123.145.*178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1482 - assertIsFalse "ip4.with.asterisk.before.start.bracket@*[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1483 - assertIsFalse "ip4.with.asterisk.after.start.bracket@[*123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1484 - assertIsFalse "ip4.with.asterisk.before.end.bracket@[123.145.178.90*]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1485 - assertIsFalse "ip4.with.asterisk.after.end.bracket@[123.145.178.90]*"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1486 - assertIsFalse "ip4.with.underscore.between.numbers@[123.14_5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1487 - assertIsFalse "ip4.with.underscore.before.point@[123.145_.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1488 - assertIsFalse "ip4.with.underscore.after.point@[123.145._178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1489 - assertIsFalse "ip4.with.underscore.before.start.bracket@_[123.145.178.90]"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1490 - assertIsFalse "ip4.with.underscore.after.start.bracket@[_123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1491 - assertIsFalse "ip4.with.underscore.before.end.bracket@[123.145.178.90_]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1492 - assertIsFalse "ip4.with.underscore.after.end.bracket@[123.145.178.90]_"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1493 - assertIsFalse "ip4.with.dollar.between.numbers@[123.14$5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1494 - assertIsFalse "ip4.with.dollar.before.point@[123.145$.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1495 - assertIsFalse "ip4.with.dollar.after.point@[123.145.$178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1496 - assertIsFalse "ip4.with.dollar.before.start.bracket@$[123.145.178.90]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1497 - assertIsFalse "ip4.with.dollar.after.start.bracket@[$123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1498 - assertIsFalse "ip4.with.dollar.before.end.bracket@[123.145.178.90$]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1499 - assertIsFalse "ip4.with.dollar.after.end.bracket@[123.145.178.90]$"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1500 - assertIsFalse "ip4.with.equality.between.numbers@[123.14=5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1501 - assertIsFalse "ip4.with.equality.before.point@[123.145=.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1502 - assertIsFalse "ip4.with.equality.after.point@[123.145.=178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1503 - assertIsFalse "ip4.with.equality.before.start.bracket@=[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1504 - assertIsFalse "ip4.with.equality.after.start.bracket@[=123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1505 - assertIsFalse "ip4.with.equality.before.end.bracket@[123.145.178.90=]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1506 - assertIsFalse "ip4.with.equality.after.end.bracket@[123.145.178.90]="                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1507 - assertIsFalse "ip4.with.exclamation.between.numbers@[123.14!5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1508 - assertIsFalse "ip4.with.exclamation.before.point@[123.145!.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1509 - assertIsFalse "ip4.with.exclamation.after.point@[123.145.!178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1510 - assertIsFalse "ip4.with.exclamation.before.start.bracket@![123.145.178.90]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1511 - assertIsFalse "ip4.with.exclamation.after.start.bracket@[!123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1512 - assertIsFalse "ip4.with.exclamation.before.end.bracket@[123.145.178.90!]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1513 - assertIsFalse "ip4.with.exclamation.after.end.bracket@[123.145.178.90]!"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1514 - assertIsFalse "ip4.with.question.between.numbers@[123.14?5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1515 - assertIsFalse "ip4.with.question.before.point@[123.145?.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1516 - assertIsFalse "ip4.with.question.after.point@[123.145.?178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1517 - assertIsFalse "ip4.with.question.before.start.bracket@?[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1518 - assertIsFalse "ip4.with.question.after.start.bracket@[?123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1519 - assertIsFalse "ip4.with.question.before.end.bracket@[123.145.178.90?]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1520 - assertIsFalse "ip4.with.question.after.end.bracket@[123.145.178.90]?"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1521 - assertIsFalse "ip4.with.grave-accent.between.numbers@[123.14`5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1522 - assertIsFalse "ip4.with.grave-accent.before.point@[123.145`.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1523 - assertIsFalse "ip4.with.grave-accent.after.point@[123.145.`178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1524 - assertIsFalse "ip4.with.grave-accent.before.start.bracket@`[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1525 - assertIsFalse "ip4.with.grave-accent.after.start.bracket@[`123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1526 - assertIsFalse "ip4.with.grave-accent.before.end.bracket@[123.145.178.90`]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1527 - assertIsFalse "ip4.with.grave-accent.after.end.bracket@[123.145.178.90]`"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1528 - assertIsFalse "ip4.with.hash.between.numbers@[123.14#5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1529 - assertIsFalse "ip4.with.hash.before.point@[123.145#.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1530 - assertIsFalse "ip4.with.hash.after.point@[123.145.#178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1531 - assertIsFalse "ip4.with.hash.before.start.bracket@#[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1532 - assertIsFalse "ip4.with.hash.after.start.bracket@[#123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1533 - assertIsFalse "ip4.with.hash.before.end.bracket@[123.145.178.90#]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1534 - assertIsFalse "ip4.with.hash.after.end.bracket@[123.145.178.90]#"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1535 - assertIsFalse "ip4.with.percentage.between.numbers@[123.14%5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1536 - assertIsFalse "ip4.with.percentage.before.point@[123.145%.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1537 - assertIsFalse "ip4.with.percentage.after.point@[123.145.%178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1538 - assertIsFalse "ip4.with.percentage.before.start.bracket@%[123.145.178.90]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1539 - assertIsFalse "ip4.with.percentage.after.start.bracket@[%123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1540 - assertIsFalse "ip4.with.percentage.before.end.bracket@[123.145.178.90%]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1541 - assertIsFalse "ip4.with.percentage.after.end.bracket@[123.145.178.90]%"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1542 - assertIsFalse "ip4.with.pipe.between.numbers@[123.14|5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1543 - assertIsFalse "ip4.with.pipe.before.point@[123.145|.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1544 - assertIsFalse "ip4.with.pipe.after.point@[123.145.|178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1545 - assertIsFalse "ip4.with.pipe.before.start.bracket@|[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1546 - assertIsFalse "ip4.with.pipe.after.start.bracket@[|123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1547 - assertIsFalse "ip4.with.pipe.before.end.bracket@[123.145.178.90|]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1548 - assertIsFalse "ip4.with.pipe.after.end.bracket@[123.145.178.90]|"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1549 - assertIsFalse "ip4.with.plus.between.numbers@[123.14+5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1550 - assertIsFalse "ip4.with.plus.before.point@[123.145+.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1551 - assertIsFalse "ip4.with.plus.after.point@[123.145.+178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1552 - assertIsFalse "ip4.with.plus.before.start.bracket@+[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1553 - assertIsFalse "ip4.with.plus.after.start.bracket@[+123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1554 - assertIsFalse "ip4.with.plus.before.end.bracket@[123.145.178.90+]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1555 - assertIsFalse "ip4.with.plus.after.end.bracket@[123.145.178.90]+"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1556 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14{5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1557 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145{.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1558 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.{178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1559 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@{[123.145.178.90]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1560 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[{123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1561 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90{]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1562 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]{"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1563 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14}5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1564 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145}.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1565 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.}178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1566 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@}[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1567 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[}123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1568 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90}]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1569 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]}"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1570 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14(5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1571 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145(.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1572 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.(178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1573 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@([123.145.178.90]"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1574 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[(123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1575 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90(]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1576 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]("                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  1577 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14)5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1578 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145).178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1579 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.)178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1580 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@)[123.145.178.90]"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1581 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[)123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1582 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90)]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1583 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90])"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1584 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14[5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1585 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145[.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1586 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.[178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1587 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@[[123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1588 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[[123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1589 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90[]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1590 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]["                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1591 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14]5.178.90]"                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1592 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145].178.90]"                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1593 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.]178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1594 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@][123.145.178.90]"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1595 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[]123.145.178.90]"                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1596 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90]]"                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1597 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]]"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1598 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14()5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1599 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145().178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1600 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.()178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1601 - assertIsTrue  "ip4.with.empty.bracket.before.start.bracket@()[123.145.178.90]"                 =   2 =  OK 
   *  1602 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[()123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1603 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90()]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1604 - assertIsTrue  "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]()"                    =   2 =  OK 
   *  1605 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14{}5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1606 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145{}.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1607 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.{}178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1608 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@{}[123.145.178.90]"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1609 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[{}123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1610 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90{}]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1611 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]{}"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1612 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14[]5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1613 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145[].178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1614 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.[]178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1615 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@[][123.145.178.90]"                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1616 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[[]123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1617 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90[]]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1618 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90][]"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1619 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14<>5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1620 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145<>.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1621 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.<>178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1622 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@<>[123.145.178.90]"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1623 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[<>123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1624 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90<>]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1625 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]<>"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1626 - assertIsFalse "ip4.with.false.bracket1.between.numbers@[123.14)(5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1627 - assertIsFalse "ip4.with.false.bracket1.before.point@[123.145)(.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1628 - assertIsFalse "ip4.with.false.bracket1.after.point@[123.145.)(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1629 - assertIsFalse "ip4.with.false.bracket1.before.start.bracket@)([123.145.178.90]"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1630 - assertIsFalse "ip4.with.false.bracket1.after.start.bracket@[)(123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1631 - assertIsFalse "ip4.with.false.bracket1.before.end.bracket@[123.145.178.90)(]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1632 - assertIsFalse "ip4.with.false.bracket1.after.end.bracket@[123.145.178.90])("                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1633 - assertIsFalse "ip4.with.false.bracket2.between.numbers@[123.14}{5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1634 - assertIsFalse "ip4.with.false.bracket2.before.point@[123.145}{.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1635 - assertIsFalse "ip4.with.false.bracket2.after.point@[123.145.}{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1636 - assertIsFalse "ip4.with.false.bracket2.before.start.bracket@}{[123.145.178.90]"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1637 - assertIsFalse "ip4.with.false.bracket2.after.start.bracket@[}{123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1638 - assertIsFalse "ip4.with.false.bracket2.before.end.bracket@[123.145.178.90}{]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1639 - assertIsFalse "ip4.with.false.bracket2.after.end.bracket@[123.145.178.90]}{"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1640 - assertIsFalse "ip4.with.false.bracket4.between.numbers@[123.14><5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1641 - assertIsFalse "ip4.with.false.bracket4.before.point@[123.145><.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1642 - assertIsFalse "ip4.with.false.bracket4.after.point@[123.145.><178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1643 - assertIsFalse "ip4.with.false.bracket4.before.start.bracket@><[123.145.178.90]"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1644 - assertIsFalse "ip4.with.false.bracket4.after.start.bracket@[><123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1645 - assertIsFalse "ip4.with.false.bracket4.before.end.bracket@[123.145.178.90><]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1646 - assertIsFalse "ip4.with.false.bracket4.after.end.bracket@[123.145.178.90]><"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1647 - assertIsFalse "ip4.with.lower.than.between.numbers@[123.14<5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1648 - assertIsFalse "ip4.with.lower.than.before.point@[123.145<.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1649 - assertIsFalse "ip4.with.lower.than.after.point@[123.145.<178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1650 - assertIsFalse "ip4.with.lower.than.before.start.bracket@<[123.145.178.90]"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1651 - assertIsFalse "ip4.with.lower.than.after.start.bracket@[<123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1652 - assertIsFalse "ip4.with.lower.than.before.end.bracket@[123.145.178.90<]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1653 - assertIsFalse "ip4.with.lower.than.after.end.bracket@[123.145.178.90]<"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1654 - assertIsFalse "ip4.with.greater.than.between.numbers@[123.14>5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1655 - assertIsFalse "ip4.with.greater.than.before.point@[123.145>.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1656 - assertIsFalse "ip4.with.greater.than.after.point@[123.145.>178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1657 - assertIsFalse "ip4.with.greater.than.before.start.bracket@>[123.145.178.90]"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1658 - assertIsFalse "ip4.with.greater.than.after.start.bracket@[>123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1659 - assertIsFalse "ip4.with.greater.than.before.end.bracket@[123.145.178.90>]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1660 - assertIsFalse "ip4.with.greater.than.after.end.bracket@[123.145.178.90]>"                      =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  1661 - assertIsFalse "ip4.with.tilde.between.numbers@[123.14~5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1662 - assertIsFalse "ip4.with.tilde.before.point@[123.145~.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1663 - assertIsFalse "ip4.with.tilde.after.point@[123.145.~178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1664 - assertIsFalse "ip4.with.tilde.before.start.bracket@~[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1665 - assertIsFalse "ip4.with.tilde.after.start.bracket@[~123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1666 - assertIsFalse "ip4.with.tilde.before.end.bracket@[123.145.178.90~]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1667 - assertIsFalse "ip4.with.tilde.after.end.bracket@[123.145.178.90]~"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1668 - assertIsFalse "ip4.with.xor.between.numbers@[123.14^5.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1669 - assertIsFalse "ip4.with.xor.before.point@[123.145^.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1670 - assertIsFalse "ip4.with.xor.after.point@[123.145.^178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1671 - assertIsFalse "ip4.with.xor.before.start.bracket@^[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1672 - assertIsFalse "ip4.with.xor.after.start.bracket@[^123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1673 - assertIsFalse "ip4.with.xor.before.end.bracket@[123.145.178.90^]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1674 - assertIsFalse "ip4.with.xor.after.end.bracket@[123.145.178.90]^"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1675 - assertIsFalse "ip4.with.colon.between.numbers@[123.14:5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1676 - assertIsFalse "ip4.with.colon.before.point@[123.145:.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1677 - assertIsFalse "ip4.with.colon.after.point@[123.145.:178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1678 - assertIsFalse "ip4.with.colon.before.start.bracket@:[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1679 - assertIsFalse "ip4.with.colon.after.start.bracket@[:123.145.178.90]"                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1680 - assertIsFalse "ip4.with.colon.before.end.bracket@[123.145.178.90:]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1681 - assertIsFalse "ip4.with.colon.after.end.bracket@[123.145.178.90]:"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1682 - assertIsFalse "ip4.with.space.between.numbers@[123.14 5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1683 - assertIsFalse "ip4.with.space.before.point@[123.145 .178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1684 - assertIsFalse "ip4.with.space.after.point@[123.145. 178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1685 - assertIsFalse "ip4.with.space.before.start.bracket@ [123.145.178.90]"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1686 - assertIsFalse "ip4.with.space.after.start.bracket@[ 123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1687 - assertIsFalse "ip4.with.space.before.end.bracket@[123.145.178.90 ]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1688 - assertIsFalse "ip4.with.space.after.end.bracket@[123.145.178.90] "                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1689 - assertIsFalse "ip4.with.comma.between.numbers@[123.14,5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1690 - assertIsFalse "ip4.with.comma.before.point@[123.145,.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1691 - assertIsFalse "ip4.with.comma.after.point@[123.145.,178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1692 - assertIsFalse "ip4.with.comma.before.start.bracket@,[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1693 - assertIsFalse "ip4.with.comma.after.start.bracket@[,123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1694 - assertIsFalse "ip4.with.comma.before.end.bracket@[123.145.178.90,]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1695 - assertIsFalse "ip4.with.comma.after.end.bracket@[123.145.178.90],"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1696 - assertIsFalse "ip4.with.at.between.numbers@[123.14@5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1697 - assertIsFalse "ip4.with.at.before.point@[123.145@.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1698 - assertIsFalse "ip4.with.at.after.point@[123.145.@178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1699 - assertIsFalse "ip4.with.at.before.start.bracket@@[123.145.178.90]"                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1700 - assertIsFalse "ip4.with.at.after.start.bracket@[@123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1701 - assertIsFalse "ip4.with.at.before.end.bracket@[123.145.178.90@]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1702 - assertIsFalse "ip4.with.at.after.end.bracket@[123.145.178.90]@"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1703 - assertIsFalse "ip4.with.paragraph.between.numbers@[123.14§5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1704 - assertIsFalse "ip4.with.paragraph.before.point@[123.145§.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1705 - assertIsFalse "ip4.with.paragraph.after.point@[123.145.§178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1706 - assertIsFalse "ip4.with.paragraph.before.start.bracket@§[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1707 - assertIsFalse "ip4.with.paragraph.after.start.bracket@[§123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1708 - assertIsFalse "ip4.with.paragraph.before.end.bracket@[123.145.178.90§]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1709 - assertIsFalse "ip4.with.paragraph.after.end.bracket@[123.145.178.90]§"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1710 - assertIsFalse "ip4.with.double.quote.between.numbers@[123.14'5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1711 - assertIsFalse "ip4.with.double.quote.before.point@[123.145'.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1712 - assertIsFalse "ip4.with.double.quote.after.point@[123.145.'178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1713 - assertIsFalse "ip4.with.double.quote.before.start.bracket@'[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1714 - assertIsFalse "ip4.with.double.quote.after.start.bracket@['123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1715 - assertIsFalse "ip4.with.double.quote.before.end.bracket@[123.145.178.90']"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1716 - assertIsFalse "ip4.with.double.quote.after.end.bracket@[123.145.178.90]'"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1717 - assertIsFalse "ip4.with.forward.slash.between.numbers@[123.14/5.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1718 - assertIsFalse "ip4.with.forward.slash.before.point@[123.145/.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1719 - assertIsFalse "ip4.with.forward.slash.after.point@[123.145./178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1720 - assertIsFalse "ip4.with.forward.slash.before.start.bracket@/[123.145.178.90]"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1721 - assertIsFalse "ip4.with.forward.slash.after.start.bracket@[/123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1722 - assertIsFalse "ip4.with.forward.slash.before.end.bracket@[123.145.178.90/]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1723 - assertIsFalse "ip4.with.forward.slash.after.end.bracket@[123.145.178.90]/"                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1724 - assertIsFalse "ip4.with.hyphen.between.numbers@[123.14-5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1725 - assertIsFalse "ip4.with.hyphen.before.point@[123.145-.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1726 - assertIsFalse "ip4.with.hyphen.after.point@[123.145.-178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1727 - assertIsFalse "ip4.with.hyphen.before.start.bracket@-[123.145.178.90]"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1728 - assertIsFalse "ip4.with.hyphen.after.start.bracket@[-123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1729 - assertIsFalse "ip4.with.hyphen.before.end.bracket@[123.145.178.90-]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1730 - assertIsFalse "ip4.with.hyphen.after.end.bracket@[123.145.178.90]-"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1731 - assertIsFalse "ip4.with.empty.string1.between.numbers@[123.14\"\"5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1732 - assertIsFalse "ip4.with.empty.string1.before.point@[123.145\"\".178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1733 - assertIsFalse "ip4.with.empty.string1.after.point@[123.145.\"\"178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1734 - assertIsFalse "ip4.with.empty.string1.before.start.bracket@\"\"[123.145.178.90]"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1735 - assertIsFalse "ip4.with.empty.string1.after.start.bracket@[\"\"123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1736 - assertIsFalse "ip4.with.empty.string1.before.end.bracket@[123.145.178.90\"\"]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1737 - assertIsFalse "ip4.with.empty.string1.after.end.bracket@[123.145.178.90]\"\""                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1738 - assertIsFalse "ip4.with.empty.string2.between.numbers@[123.14a\"\"b5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1739 - assertIsFalse "ip4.with.empty.string2.before.point@[123.145a\"\"b.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1740 - assertIsFalse "ip4.with.empty.string2.after.point@[123.145.a\"\"b178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1741 - assertIsFalse "ip4.with.empty.string2.before.start.bracket@a\"\"b[123.145.178.90]"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1742 - assertIsFalse "ip4.with.empty.string2.after.start.bracket@[a\"\"b123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1743 - assertIsFalse "ip4.with.empty.string2.before.end.bracket@[123.145.178.90a\"\"b]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1744 - assertIsFalse "ip4.with.empty.string2.after.end.bracket@[123.145.178.90]a\"\"b"                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1745 - assertIsFalse "ip4.with.double.empty.string1.between.numbers@[123.14\"\"\"\"5.178.90]"         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1746 - assertIsFalse "ip4.with.double.empty.string1.before.point@[123.145\"\"\"\".178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1747 - assertIsFalse "ip4.with.double.empty.string1.after.point@[123.145.\"\"\"\"178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1748 - assertIsFalse "ip4.with.double.empty.string1.before.start.bracket@\"\"\"\"[123.145.178.90]"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1749 - assertIsFalse "ip4.with.double.empty.string1.after.start.bracket@[\"\"\"\"123.145.178.90]"     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1750 - assertIsFalse "ip4.with.double.empty.string1.before.end.bracket@[123.145.178.90\"\"\"\"]"      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1751 - assertIsFalse "ip4.with.double.empty.string1.after.end.bracket@[123.145.178.90]\"\"\"\""       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1752 - assertIsFalse "ip4.with.double.empty.string2.between.numbers@[123.14\"\".\"\"5.178.90]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1753 - assertIsFalse "ip4.with.double.empty.string2.before.point@[123.145\"\".\"\".178.90]"           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1754 - assertIsFalse "ip4.with.double.empty.string2.after.point@[123.145.\"\".\"\"178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1755 - assertIsFalse "ip4.with.double.empty.string2.before.start.bracket@\"\".\"\"[123.145.178.90]"   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1756 - assertIsFalse "ip4.with.double.empty.string2.after.start.bracket@[\"\".\"\"123.145.178.90]"    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1757 - assertIsFalse "ip4.with.double.empty.string2.before.end.bracket@[123.145.178.90\"\".\"\"]"     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1758 - assertIsFalse "ip4.with.double.empty.string2.after.end.bracket@[123.145.178.90]\"\".\"\""      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1759 - assertIsFalse "ip4.with.number0.between.numbers@[123.1405.178.90]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1760 - assertIsFalse "ip4.with.number0.before.point@[123.1450.178.90]"                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1761 - assertIsFalse "ip4.with.number0.after.point@[123.145.0178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1762 - assertIsFalse "ip4.with.number0.before.start.bracket@0[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1763 - assertIsFalse "ip4.with.number0.after.start.bracket@[0123.145.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1764 - assertIsFalse "ip4.with.number0.before.end.bracket@[123.145.178.900]"                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1765 - assertIsFalse "ip4.with.number0.after.end.bracket@[123.145.178.90]0"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1766 - assertIsFalse "ip4.with.number9.between.numbers@[123.1495.178.90]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1767 - assertIsFalse "ip4.with.number9.before.point@[123.1459.178.90]"                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1768 - assertIsFalse "ip4.with.number9.after.point@[123.145.9178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1769 - assertIsFalse "ip4.with.number9.before.start.bracket@9[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1770 - assertIsFalse "ip4.with.number9.after.start.bracket@[9123.145.178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1771 - assertIsFalse "ip4.with.number9.before.end.bracket@[123.145.178.909]"                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1772 - assertIsFalse "ip4.with.number9.after.end.bracket@[123.145.178.90]9"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1773 - assertIsFalse "ip4.with.numbers.between.numbers@[123.1401234567895.178.90]"                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1774 - assertIsFalse "ip4.with.numbers.before.point@[123.1450123456789.178.90]"                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1775 - assertIsFalse "ip4.with.numbers.after.point@[123.145.0123456789178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1776 - assertIsFalse "ip4.with.numbers.before.start.bracket@0123456789[123.145.178.90]"               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1777 - assertIsFalse "ip4.with.numbers.after.start.bracket@[0123456789123.145.178.90]"                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1778 - assertIsFalse "ip4.with.numbers.before.end.bracket@[123.145.178.900123456789]"                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1779 - assertIsFalse "ip4.with.numbers.after.end.bracket@[123.145.178.90]0123456789"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1780 - assertIsFalse "ip4.with.byte.overflow.between.numbers@[123.149995.178.90]"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1781 - assertIsFalse "ip4.with.byte.overflow.before.point@[123.145999.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1782 - assertIsFalse "ip4.with.byte.overflow.after.point@[123.145.999178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1783 - assertIsFalse "ip4.with.byte.overflow.before.start.bracket@999[123.145.178.90]"                =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1784 - assertIsFalse "ip4.with.byte.overflow.after.start.bracket@[999123.145.178.90]"                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1785 - assertIsFalse "ip4.with.byte.overflow.before.end.bracket@[123.145.178.90999]"                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1786 - assertIsFalse "ip4.with.byte.overflow.after.end.bracket@[123.145.178.90]999"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1787 - assertIsFalse "ip4.with.no.hex.number.between.numbers@[123.14xyz5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1788 - assertIsFalse "ip4.with.no.hex.number.before.point@[123.145xyz.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1789 - assertIsFalse "ip4.with.no.hex.number.after.point@[123.145.xyz178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1790 - assertIsFalse "ip4.with.no.hex.number.before.start.bracket@xyz[123.145.178.90]"                =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1791 - assertIsFalse "ip4.with.no.hex.number.after.start.bracket@[xyz123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1792 - assertIsFalse "ip4.with.no.hex.number.before.end.bracket@[123.145.178.90xyz]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1793 - assertIsFalse "ip4.with.no.hex.number.after.end.bracket@[123.145.178.90]xyz"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1794 - assertIsFalse "ip4.with.slash.between.numbers@[123.14\5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1795 - assertIsFalse "ip4.with.slash.before.point@[123.145\.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1796 - assertIsFalse "ip4.with.slash.after.point@[123.145.\178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1797 - assertIsFalse "ip4.with.slash.before.start.bracket@\[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1798 - assertIsFalse "ip4.with.slash.after.start.bracket@[\123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1799 - assertIsFalse "ip4.with.slash.before.end.bracket@[123.145.178.90\]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1800 - assertIsFalse "ip4.with.slash.after.end.bracket@[123.145.178.90]\"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1801 - assertIsFalse "ip4.with.string.between.numbers@[123.14\"str\"5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1802 - assertIsFalse "ip4.with.string.before.point@[123.145\"str\".178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1803 - assertIsFalse "ip4.with.string.after.point@[123.145.\"str\"178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1804 - assertIsFalse "ip4.with.string.before.start.bracket@\"str\"[123.145.178.90]"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1805 - assertIsFalse "ip4.with.string.after.start.bracket@[\"str\"123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1806 - assertIsFalse "ip4.with.string.before.end.bracket@[123.145.178.90\"str\"]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1807 - assertIsFalse "ip4.with.string.after.end.bracket@[123.145.178.90]\"str\""                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1808 - assertIsFalse "ip4.with.comment.between.numbers@[123.14(comment)5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1809 - assertIsFalse "ip4.with.comment.before.point@[123.145(comment).178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1810 - assertIsFalse "ip4.with.comment.after.point@[123.145.(comment)178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1811 - assertIsTrue  "ip4.with.comment.before.start.bracket@(comment)[123.145.178.90]"                =   2 =  OK 
   *  1812 - assertIsFalse "ip4.with.comment.after.start.bracket@[(comment)123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1813 - assertIsFalse "ip4.with.comment.before.end.bracket@[123.145.178.90(comment)]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1814 - assertIsTrue  "ip4.with.comment.after.end.bracket@[123.145.178.90](comment)"                   =   2 =  OK 
   *  1815 - assertIsTrue  "email@[123.123.123.123]"                                                        =   2 =  OK 
   *  1816 - assertIsTrue  "email@123.123.123.123"                                                          =   2 =  OK 
   *  1817 - assertIsTrue  "ab@88.120.150.021"                                                              =   2 =  OK 
   *  1818 - assertIsTrue  "ab@88.120.150.01"                                                               =   2 =  OK 
   *  1819 - assertIsTrue  "ab@188.120.150.10"                                                              =   2 =  OK 
   *  1820 - assertIsTrue  "ab@120.25.254.120"                                                              =   2 =  OK 
   *  1821 - assertIsTrue  "ab@1.0.0.10"                                                                    =   2 =  OK 
   *  1822 - assertIsTrue  "ab@01.120.150.1"                                                                =   2 =  OK 
   *  1823 - assertIsFalse "email@[3.256.255.23]"                                                           =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1824 - assertIsFalse "email@[123.123.123].123"                                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1825 - assertIsFalse "email@[123.123.123.123"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1826 - assertIsFalse "email@[12.34.44.56"                                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1827 - assertIsFalse "email@[1.1.23.5f]"                                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1828 - assertIsFalse "email@14.44.56.34]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1829 - assertIsFalse "email@123.123.[123.123]"                                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1830 - assertIsFalse "email@123.123.123.123]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1831 - assertIsFalse "email@111.222.333.44444"                                                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1832 - assertIsFalse "email@111.222.333.256"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1833 - assertIsFalse "email@111.222.333"                                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  1834 - assertIsFalse "email@-example.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1835 - assertIsFalse "email.@example.com"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  1836 - assertIsFalse "email..email@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  1837 - assertIsFalse ".email@example.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   * 
   * ---- ip4 without brackets --------------------------------------------------------------------------------------------------------
   * 
   *  1838 - assertIsTrue  "ip4.without.brackets.ok1@127.0.0.1"                                             =   2 =  OK 
   *  1839 - assertIsTrue  "ip4.without.brackets.ok2@0.0.0.0"                                               =   2 =  OK 
   *  1840 - assertIsFalse "ip4.without.brackets.but.with.space.at.end@127.0.0.1 "                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1841 - assertIsFalse "ip4.without.brackets.byte.overflow@127.0.999.1"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1842 - assertIsFalse "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1843 - assertIsFalse "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1844 - assertIsFalse "ip4.without.brackets.negative.number@127.0.-1.1"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1845 - assertIsFalse "ip4.without.brackets.point.error1@127.0..0.1"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  1846 - assertIsFalse "ip4.without.brackets.point.error1@127...1"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1847 - assertIsFalse "ip4.without.brackets.error.bracket@127.0.1.1[]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1848 - assertIsFalse "ip4.without.brackets.point.error2@127001"                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  1849 - assertIsFalse "ip4.without.brackets.point.error3@127.0.0."                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  1850 - assertIsFalse "ip4.without.brackets.character.error@127.0.A.1"                                 =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1851 - assertIsFalse "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1"                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1852 - assertIsTrue  "ip4.without.brackets.normal.tld1@127.0.0.1.com"                                 =   0 =  OK 
   *  1853 - assertIsTrue  "ip4.without.brackets.normal.tld2@127.0.99.1.com"                                =   0 =  OK 
   *  1854 - assertIsTrue  "ip4.without.brackets.normal.tld3@127.0.A.1.com"                                 =   0 =  OK 
   * 
   * ---- IP V6 -----------------------------------------------------------------------------------------------------------------------
   * 
   *  1855 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                                     =   4 =  OK 
   *  1856 - assertIsFalse "ABC.DEF@[IP"                                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1857 - assertIsFalse "ABC.DEF@[IPv6]"                                                                 =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  1858 - assertIsFalse "ABC.DEF@[IPv6:]"                                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1859 - assertIsFalse "ABC.DEF@[IPv6:"                                                                 =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1860 - assertIsFalse "ABC.DEF@[IPv6::]"                                                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1861 - assertIsFalse "ABC.DEF@[IPv6::"                                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1862 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1863 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1864 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1865 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1866 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1867 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                           =   4 =  OK 
   *  1868 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                                         =   4 =  OK 
   *  1869 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                                      =   4 =  OK 
   *  1870 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                                     =   4 =  OK 
   *  1871 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                                     =   4 =  OK 
   *  1872 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                                   =   4 =  OK 
   *  1873 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                                    =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1874 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                                 =   4 =  OK 
   *  1875 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                               =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  1876 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                                         =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  1877 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                                       =   4 =  OK 
   *  1878 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1879 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1880 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                           =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  1881 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1882 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1883 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                                  =   4 =  OK 
   *  1884 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1885 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1886 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1887 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1888 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  1889 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1890 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1891 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1892 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1893 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1894 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1895 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:2;2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1896 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22;:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1897 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22:;3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1898 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22:3:4:5:6:7;]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1899 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22:3:4:5:6:7];"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1900 - assertIsFalse "ip.v6.with.semicolon@;[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1901 - assertIsFalse "ip.v6.with.semicolon@[;IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1902 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1903 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1904 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                          =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  1905 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1906 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1907 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1908 - assertIsFalse "ip.v6.with.dot@[.IPv6:1:22:3:4:5:6:7]"                                          =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1909 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:2..2:3:4:5:6:7]"                                  =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1910 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22..:3:4:5:6:7]"                                  =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1911 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:..3:4:5:6:7]"                                  =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  1912 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7..]"                                  =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1913 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7].."                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1914 - assertIsFalse "ip.v6.with.double.dot@..[IPv6:1:22:3:4:5:6:7]"                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1915 - assertIsFalse "ip.v6.with.double.dot@[..IPv6:1:22:3:4:5:6:7]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1916 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1917 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1918 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1919 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1920 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1921 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1922 - assertIsFalse "ip.v6.with.amp@[&IPv6:1:22:3:4:5:6:7]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1923 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1924 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1925 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1926 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1927 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1928 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1929 - assertIsFalse "ip.v6.with.asterisk@[*IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1930 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1931 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1932 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1933 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1934 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1935 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1936 - assertIsFalse "ip.v6.with.underscore@[_IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1937 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1938 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1939 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1940 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1941 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1942 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1943 - assertIsFalse "ip.v6.with.dollar@[$IPv6:1:22:3:4:5:6:7]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1944 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1945 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1946 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1947 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1948 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1949 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1950 - assertIsFalse "ip.v6.with.equality@[=IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1951 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1952 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1953 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1954 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1955 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1956 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1957 - assertIsFalse "ip.v6.with.exclamation@[!IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1958 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1959 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1960 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1961 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1962 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1963 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1964 - assertIsFalse "ip.v6.with.question@[?IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1965 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1966 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1967 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1968 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1969 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1970 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1971 - assertIsFalse "ip.v6.with.grave-accent@[`IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1972 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1973 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1974 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1975 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1976 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1977 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1978 - assertIsFalse "ip.v6.with.hash@[#IPv6:1:22:3:4:5:6:7]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1979 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1980 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1981 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1982 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1983 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1984 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1985 - assertIsFalse "ip.v6.with.percentage@[%IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1986 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1987 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1988 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1989 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1990 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1991 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1992 - assertIsFalse "ip.v6.with.pipe@[|IPv6:1:22:3:4:5:6:7]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1993 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1994 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1995 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1996 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1997 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1998 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1999 - assertIsFalse "ip.v6.with.plus@[+IPv6:1:22:3:4:5:6:7]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2000 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2001 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2002 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2003 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2004 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2005 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2006 - assertIsFalse "ip.v6.with.leftbracket@[{IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2007 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2008 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2009 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2010 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2011 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2012 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2013 - assertIsFalse "ip.v6.with.rightbracket@[}IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2014 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2015 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2016 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2017 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2018 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2019 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2020 - assertIsFalse "ip.v6.with.leftbracket@[(IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2021 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2022 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2023 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2024 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2025 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2026 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2027 - assertIsFalse "ip.v6.with.rightbracket@[)IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2028 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2029 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2030 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2031 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2032 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2033 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2034 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  2035 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  2036 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2037 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2038 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2039 - assertIsFalse "ip.v6.with.rightbracket@[]IPv6:1:22:3:4:5:6:7]"                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  2040 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2041 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2042 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2043 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2044 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                               =   4 =  OK 
   *  2045 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                               =   4 =  OK 
   *  2046 - assertIsFalse "ip.v6.with.empty.bracket@[()IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2047 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2048 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2049 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2050 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2051 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2052 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2053 - assertIsFalse "ip.v6.with.empty.bracket@[{}IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2054 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2055 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2056 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2057 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2058 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2059 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  2060 - assertIsFalse "ip.v6.with.empty.bracket@[[]IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2061 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2062 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2063 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2064 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2065 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2066 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2067 - assertIsFalse "ip.v6.with.empty.bracket@[<>IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2068 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2069 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2070 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2071 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2072 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2073 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2074 - assertIsFalse "ip.v6.with.false.bracket1@[)(IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2075 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2076 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2077 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2078 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2079 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2080 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2081 - assertIsFalse "ip.v6.with.false.bracket2@[}{IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2082 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2083 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2084 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2085 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2086 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2087 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2088 - assertIsFalse "ip.v6.with.false.bracket4@[><IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2089 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2090 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2091 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2092 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2093 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2094 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2095 - assertIsFalse "ip.v6.with.lower.than@[<IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2096 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2097 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2098 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2099 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2100 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                                 =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  2101 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2102 - assertIsFalse "ip.v6.with.greater.than@[>IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2103 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2104 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2105 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2106 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2107 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2108 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2109 - assertIsFalse "ip.v6.with.tilde@[~IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2110 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2111 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2112 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2113 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2114 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2115 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2116 - assertIsFalse "ip.v6.with.xor@[^IPv6:1:22:3:4:5:6:7]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2117 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                                        =   4 =  OK 
   *  2118 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                        =   4 =  OK 
   *  2119 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                        =   4 =  OK 
   *  2120 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                                        =   4 =  OK 
   *  2121 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2122 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2123 - assertIsFalse "ip.v6.with.colon@[:IPv6:1:22:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2124 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2125 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2126 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2127 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2128 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2129 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2130 - assertIsFalse "ip.v6.with.space@[ IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2131 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2132 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2133 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2134 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2135 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2136 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2137 - assertIsFalse "ip.v6.with.comma@[,IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2138 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2139 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2140 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2141 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2142 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2143 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2144 - assertIsFalse "ip.v6.with.at@[@IPv6:1:22:3:4:5:6:7]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2145 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2146 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2147 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2148 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2149 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2150 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2151 - assertIsFalse "ip.v6.with.paragraph@[§IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2152 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2153 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2154 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2155 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2156 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2157 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2158 - assertIsFalse "ip.v6.with.double.quote@['IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2159 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2160 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2161 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2162 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2163 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2164 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2165 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2166 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2167 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2168 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2169 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2170 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2171 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2172 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2173 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2174 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2175 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2176 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2177 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2178 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2179 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2180 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2181 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2182 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2183 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2184 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2185 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2186 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2187 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2188 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2189 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2190 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2191 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2192 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2193 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2194 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2195 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2196 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2197 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2198 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2199 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2200 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2201 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:202:3:4:5:6:7]"                                      =   4 =  OK 
   *  2202 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:220:3:4:5:6:7]"                                      =   4 =  OK 
   *  2203 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:03:4:5:6:7]"                                      =   4 =  OK 
   *  2204 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:70]"                                      =   4 =  OK 
   *  2205 - assertIsFalse "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:7]0"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2206 - assertIsFalse "ip.v6.with.number0@0[IPv6:1:22:3:4:5:6:7]"                                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2207 - assertIsFalse "ip.v6.with.number0@[0IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2208 - assertIsFalse "ip.v6.with.number9@[IPv6:1:292:3:4:5:6:7]"                                      =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  2209 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:229:3:4:5:6:7]"                                      =   4 =  OK 
   *  2210 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:93:4:5:6:7]"                                      =   4 =  OK 
   *  2211 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:79]"                                      =   4 =  OK 
   *  2212 - assertIsFalse "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:7]9"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2213 - assertIsFalse "ip.v6.with.number9@9[IPv6:1:22:3:4:5:6:7]"                                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2214 - assertIsFalse "ip.v6.with.number9@[9IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2215 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2216 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2217 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2218 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2219 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2220 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2221 - assertIsFalse "ip.v6.with.numbers@[0123456789IPv6:1:22:3:4:5:6:7]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  2222 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2223 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2224 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  2225 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7999]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  2226 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]999"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2227 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2228 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                              =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2229 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2230 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2231 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2232 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2233 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2234 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2235 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2236 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2237 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2238 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2239 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2240 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2241 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2242 - assertIsFalse "ip.v6.with.slash@[\IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2243 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2244 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2245 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2246 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2247 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2248 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2249 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2250 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2251 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2252 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2253 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2254 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                              =   4 =  OK 
   *  2255 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                              =   4 =  OK 
   *  2256 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2257 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"                         =   4 =  OK 
   *  2258 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"                         =   4 =  OK 
   *  2259 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"                         =   4 =  OK 
   *  2260 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                                        =   4 =  OK 
   *  2261 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                                         =   4 =  OK 
   *  2262 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2263 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"                        =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2264 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2265 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   * ---- IP V4 embedded in IP V6 -----------------------------------------------------------------------------------------------------
   * 
   *  2266 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                                 =   4 =  OK 
   *  2267 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                                 =   4 =  OK 
   *  2268 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                                     =   4 =  OK 
   *  2269 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                                     =   4 =  OK 
   *  2270 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  2271 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                                  =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  2272 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  2273 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2274 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                                     =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  2275 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2276 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2277 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                               =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2278 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.0001]"                                              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  2279 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   * ---- Strings ---------------------------------------------------------------------------------------------------------------------
   * 
   *  2280 - assertIsTrue  "\"local.part.only.string\"@domain.com"                                          =   1 =  OK 
   *  2281 - assertIsTrue  "\"&lt; &clubs; &diams; &hearts; &spades; experiment &gt;\"@domain.com"          =   1 =  OK 
   *  2282 - assertIsTrue  "\"local.part\".\"two.strings\"@domain.com"                                      =   1 =  OK 
   *  2283 - assertIsFalse "-\"hyphen.before.string\"@domain.com"                                           = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
   *  2284 - assertIsFalse "\"hyphen.after.string\"-.\"string2\"@domain.com"                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2285 - assertIsFalse "\"hyphen.before.string2\".-\"string2\"@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2286 - assertIsFalse ".\"point.before.string\".\"string2\"@domain.com"                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2287 - assertIsTrue  "\"space in string\"@domain.com"                                                 =   1 =  OK 
   *  2288 - assertIsTrue  "\"at.sign@in.string\"@domain.com"                                               =   1 =  OK 
   *  2289 - assertIsTrue  "\"escaped.qoute.in\\"string\"@domain.com"                                       =   1 =  OK 
   *  2290 - assertIsTrue  "\"escaped.at.sign\@in.string\"@domain.com"                                      =   1 =  OK 
   *  2291 - assertIsTrue  "\"escaped.sign.'in.string\"@domain.com"                                         =   1 =  OK 
   *  2292 - assertIsTrue  "\"escaped.back.slash\\in.string\"@domain.com"                                   =   1 =  OK 
   *  2293 - assertIsFalse "\"\"@empty.string.domain.com"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2294 - assertIsFalse "\"missplaced.end.of.string@do\"main.com"                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2295 - assertIsFalse "domain.part.is.string@\"domain.com\""                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2296 - assertIsFalse "not.closed.string.in.domain.part.version1@\"domain.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2297 - assertIsFalse "not.closed.string.in.domain.part.version2@do\"main.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2298 - assertIsFalse "not.closed.string.in.domain.part.version3@domain.com\""                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2299 - assertIsFalse "string.in.domain.part4@do\"main.com\""                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2300 - assertIsFalse "string.in.domain.part5@do\"main.com"                                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2301 - assertIsFalse "embedded.string.in.domain.part@do\"ma\"in.com"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2302 - assertIsFalse "\"@missplaced.start.of.string"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2303 - assertIsFalse "no.at.sign.and.no.domain.part.\""                                               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2304 - assertIsFalse "domain.part.is.empty.string@\"\""                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2305 - assertIsFalse "\"no.email.adress.only.string\""                                                =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2306 - assertIsFalse "\"no.email.adress.only.string.and.missplaced.backslash\"                        =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
   *  2307 - assertIsFalse "no.email.adress\"with.string.start"                                             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2308 - assertIsFalse "string.starts.before.at.sign\"@domain.com"                                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2309 - assertIsFalse "string.starts.before.at.sign\"but.with.caracters.before.at.sign@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2310 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2311 - assertIsFalse "\"\".email.starts.with.empty.string@domain.com"                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2312 - assertIsTrue  "\"string.with.double..point\"@domain.com"                                       =   1 =  OK 
   *  2313 - assertIsTrue  "string.with.\"(comment)\".in.string@domain.com"                                 =   1 =  OK 
   *  2314 - assertIsTrue  "string.with.brackets.\")()(\".in.string@domain.com"                             =   1 =  OK 
   *  2315 - assertIsTrue  "\"string.with.\\".\\".point\"@domain.com"                                       =   1 =  OK 
   *  2316 - assertIsTrue  "\"string.with.embedded.empty.\\"\\".string\"@domain.com"                        =   1 =  OK 
   *  2317 - assertIsTrue  "\"embedded.string.with.space.and.escaped.\\" \@ \\".at.sign\"@domain.com"       =   1 =  OK 
   *  2318 - assertIsFalse "\"string.is.not.closed@domain.com"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2319 - assertIsFalse "\"whole.email.adress.is.string@domain.com\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2320 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                            =   1 =  OK 
   *  2321 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                            =   1 =  OK 
   *  2322 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                                     =   1 =  OK 
   *  2323 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2324 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2325 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2326 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2327 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2328 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2329 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2330 - assertIsFalse "\"\".\"\".ABC.DEF@GHI.DE"                                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2331 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                                         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2332 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2333 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2334 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2335 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2336 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2337 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2338 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2339 - assertIsFalse "0\"00.000\"@domain.com"                                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2340 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                           =   1 =  OK 
   *  2341 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2342 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2343 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2344 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                          =   1 =  OK 
   *  2345 - assertIsTrue  "\"ABC \\"\\\\" !\".DEF@GHI.DE"                                                  =   1 =  OK 
   * 
   * ---- Comments --------------------------------------------------------------------------------------------------------------------
   * 
   *  2346 - assertIsFalse "escape.character.at.input.end@domain.com (comment \"                            =  96 =  OK    Kommentar: Escape-Zeichen nicht am Ende der Eingabe
   *  2347 - assertIsTrue  "(comment)local.part.with.comment.at.start@domain.com"                           =   6 =  OK 
   *  2348 - assertIsTrue  "(comment \\"string1\\" \\"string2) is.not.closed@domain.com"                    =   6 =  OK 
   *  2349 - assertIsTrue  "(comment) local.part.with.space.after.comment.at.start@domain.com"              =   6 =  OK 
   *  2350 - assertIsFalse "(comment)at.start.and.end@domain.com(comment end)"                              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2351 - assertIsFalse "(two.comments)in.the(local.part)@domain.com"                                    =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2352 - assertIsFalse "(nested(comment))in.the.local.part@domain.com"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2353 - assertIsTrue  "local.part.with.comment.before(at.sign)@domain.com"                             =   6 =  OK 
   *  2354 - assertIsFalse "local.part.with.comment.before(at.sign.and.spaces.after.comment)    @domain.com" =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2355 - assertIsFalse "(local.part.with) (two.comments.with.space.after)  comment@domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2356 - assertIsFalse "(local.part.with) (two.comments.with.space.after.first).comment@domain.com"     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2357 - assertIsTrue  "domain.part.with.comment.at.the.end@domain.com(comment)"                        =   6 =  OK 
   *  2358 - assertIsFalse "comment.not(closed@domain.com"                                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2359 - assertIsFalse "comment.not.startet@do)main.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2360 - assertIsFalse ")comment.close.bracket.at.start@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2361 - assertIsFalse "comment.close.bracket.before.at.sign)@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2362 - assertIsTrue  "ip4.with.comment.after.at.sign.at.input.start@(without.space)[1.2.3.4]"         =   2 =  OK 
   *  2363 - assertIsTrue  "ip4.with.comment.after.at.sign.at.input.end@[1.2.3.4](without.space)"           =   2 =  OK 
   *  2364 - assertIsTrue  "ip4.with.comment.after.at.sign.at.input.end@[1.2.3.4]  (with.space.before.comment)" =   2 =  OK 
   *  2365 - assertIsFalse "ip4.with.comment.after.at.sign@(with.space) [1.2.3.4]"                          = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
   *  2366 - assertIsFalse "ip4.with.embedded.comment.in.ip4@[1.2.3(comment).4]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2367 - assertIsFalse "()()()three.consecutive.empty.comments.at.email.start@domain.com"               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2368 - assertIsTrue  "morse.code.in.comment(... .... .. -)@storm.de"                                  =   6 =  OK 
   *  2369 - assertIsTrue  "(comment)          \"string\".name1@domain.tld"                                 =   7 =  OK 
   *  2370 - assertIsFalse "(comment) Error )  \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2371 - assertIsFalse "(comment(nested Comment)) nested.comments.not.supported@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2372 - assertIsFalse ")                  \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2373 - assertIsFalse ")))))              \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2374 - assertIsFalse "())                \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2375 - assertIsFalse "   ())             \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2376 - assertIsFalse "(input.is.only.one.comment)"                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2377 - assertIsFalse "  (input.is.only.one.comment.with.leading.spaces)"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2378 - assertIsFalse "(input.is.only.one.comment.with.trailing.spaces)    "                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2379 - assertIsFalse "(comment)  ."                                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2380 - assertIsFalse "(comment.space.point.space) . "                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2381 - assertIsFalse "domain.part.with.comment.but.spaces.after.comment@domain.com(comment)    "      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2382 - assertIsTrue  "domain.part.with.comment@(comment)domain.com"                                   =   6 =  OK 
   *  2383 - assertIsTrue  "domain.part.with.comment@(and.at.sgin.in.comment.@.)domain.com"                 =   6 =  OK 
   *  2384 - assertIsTrue  "domain.part.with.comment@(and.escaped.at.sgin.in.comment.\@.)domain.com"        =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2385 - assertIsFalse "ABC.DEF@(GHI)   JKL.MNO"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2386 - assertIsFalse "ABC.DEF@(GHI.)   JKL.MNO"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2387 - assertIsFalse "ABC.DEF@(GHI.) (ABC)JKL.MNO"                                                    =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2388 - assertIsFalse "ABC.DEF@(GHI().()ABC)JKL.MNO"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2389 - assertIsFalse "ABC.DEF@(GHI.)   JKL(MNO)"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2390 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                                     =   6 =  OK 
   *  2391 - assertIsFalse "ABC.DEF@             (MNO)"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2392 - assertIsFalse "ABC.DEF@   .         (MNO)"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2393 - assertIsFalse "ABC.DEF              (MNO)"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2394 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                                     =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  2395 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2396 - assertIsFalse "ABC.DEF@GHI.JKL          "                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2397 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2398 - assertIsFalse "("                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2399 - assertIsFalse "(         )"                                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2400 - assertIsFalse ")"                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2401 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                             =   6 =  OK 
   *  2402 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                              =   6 =  OK 
   *  2403 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                              =   6 =  OK 
   *  2404 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                              =   6 =  OK 
   *  2405 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                              =   6 =  OK 
   *  2406 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2407 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                           = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2408 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2409 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2410 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2411 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2412 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2413 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                              = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  2414 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2415 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                          = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *  2416 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                          = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2417 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                               =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2418 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                             = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  2419 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                               =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *  2420 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2421 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2422 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                              =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2423 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                             =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2424 - assertIsFalse "ABC(DEF@GHI).JKL"                                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2425 - assertIsFalse "ABC(DEF.GHI).JKL"                                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2426 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                              =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2427 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2428 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2429 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2430 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                           =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2431 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2432 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                         =   4 =  OK 
   *  2433 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                         =   4 =  OK 
   *  2434 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                                     =   4 =  OK 
   *  2435 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2436 - assertIsTrue  "(Comment)-ABC.DEF@GHI.JKL"                                                      =   6 =  OK 
   *  2437 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                                      =   6 =  OK 
   *  2438 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                                      = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
   *  2439 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2440 - assertIsFalse "a@abc(bananas)def.com"                                                          = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2441 - assertIsTrue  "\"address(comment\"@example.com"                                                =   1 =  OK 
   *  2442 - assertIsFalse "address(co\"mm\"ent)@example.com"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2443 - assertIsFalse "address(co\"mment)@example.com"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2444 - assertIsFalse "test@test.com(comment"                                                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   * 
   * ---- Display Name ----------------------------------------------------------------------------------------------------------------
   * 
   *  2445 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                                      =   0 =  OK 
   *  2446 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                                      =   0 =  OK 
   *  2447 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  2448 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                                       =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *  2449 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                                  =   0 =  OK 
   *  2450 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                            =   1 =  OK 
   *  2451 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                                    =   1 =  OK 
   *  2452 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2453 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2454 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2455 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2456 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2457 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2458 - assertIsFalse "ABC DEF <A@A>"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2459 - assertIsFalse "<A@A> ABC DEF"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2460 - assertIsFalse "ABC DEF <>"                                                                     =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2461 - assertIsFalse "<> ABC DEF"                                                                     =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2462 - assertIsFalse "<"                                                                              =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *  2463 - assertIsFalse ">"                                                                              =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  2464 - assertIsFalse "<         >"                                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2465 - assertIsFalse "< <     > >"                                                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2466 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                              =   0 =  OK 
   *  2467 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                             = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2468 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2469 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                             =   0 =  OK 
   *  2470 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2471 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                             =   0 =  OK 
   *  2472 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2473 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                                     =   6 =  OK 
   *  2474 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2475 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                                    = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2476 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                                    =   6 =  OK 
   *  2477 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                                    = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
   *  2478 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                                    =   6 =  OK 
   *  2479 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                                    = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
   *  2480 - assertIsTrue  "Joe Smith <email@domain.com>"                                                   =   0 =  OK 
   *  2481 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2482 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2483 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                =   9 =  OK 
   *  2484 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"                =   9 =  OK 
   *  2485 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                =   9 =  OK 
   *  2486 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "               =   9 =  OK 
   *  2487 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2488 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"               =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2489 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2490 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2491 - assertIsFalse "Test |<gaaf <email@domain.com>"                                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2492 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2493 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2494 - assertIsFalse "<null>@mail.com"                                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2495 - assertIsFalse "email.adress@domain.com <display name>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2496 - assertIsFalse "email.adress@domain.com <email.adress@domain.com>"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2497 - assertIsFalse "display.name@false.com <email.adress@correct.com>"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2498 - assertIsFalse "<email>.<adress>@domain.com"                                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2499 - assertIsFalse "<email>.<adress> email.adress@domain.com"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
   * 
   * ---- Length ----------------------------------------------------------------------------------------------------------------------
   * 
   *  2500 - assertIsTrue  "A@B.CD"                                                                         =   0 =  OK 
   *  2501 - assertIsFalse "A@B.C"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2502 - assertIsFalse "A@COM"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2503 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                                =   0 =  OK 
   *  2504 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123"    =   0 =  OK 
   *  2505 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A"   =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
   *  2506 - assertIsTrue  "A@B.CD"                                                                         =   0 =  OK 
   *  2507 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
   *  2508 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2509 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain.tld>" =   0 =  OK 
   *  2510 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2511 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain.tld> True64 " =   0 =  OK 
   *  2512 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2513 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain.tld>"  =   0 =  OK 
   *  2514 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2515 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"          =   0 =  OK 
   *  2516 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
   *  2517 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@example.com"   =   0 =  OK 
   *  2518 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2519 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2520 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2521 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2522 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2523 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2524 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2525 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2526 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2527 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
   *  2528 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
   *  2529 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2530 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
   *  2531 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
   *  2532 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
   *  2533 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2534 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2535 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com"  =   1 =  OK 
   *  2536 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2537 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2538 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2539 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
   *  2540 - assertIsTrue  "email@domain.topleveldomain"                                                    =   0 =  OK 
   *  2541 - assertIsTrue  "email@email.email.mydomain"                                                     =   0 =  OK 
   * 
   * ---- https://en.wikipedia.org/wiki/Email_address/ --------------------------------------------------------------------------------
   * 
   *  2542 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                               =   6 =  OK 
   *  2543 - assertIsTrue  "\"MaxMustermann\"@example.com"                                                  =   1 =  OK 
   *  2544 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                                     =   1 =  OK 
   *  2545 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                              =   0 =  OK 
   *  2546 - assertIsTrue  "Marc Dupont <md118@example.com>"                                                =   0 =  OK 
   *  2547 - assertIsTrue  "simple@example.com"                                                             =   0 =  OK 
   *  2548 - assertIsTrue  "very.common@example.com"                                                        =   0 =  OK 
   *  2549 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                                 =   0 =  OK 
   *  2550 - assertIsTrue  "other.email-with-hyphen@example.com"                                            =   0 =  OK 
   *  2551 - assertIsTrue  "fully-qualified-domain@example.com"                                             =   0 =  OK 
   *  2552 - assertIsTrue  "user.name+tag+sorting@example.com"                                              =   0 =  OK 
   *  2553 - assertIsTrue  "user+mailbox/department=shipping@example.com"                                   =   0 =  OK 
   *  2554 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                               =   0 =  OK 
   *  2555 - assertIsTrue  "x@example.com"                                                                  =   0 =  OK 
   *  2556 - assertIsTrue  "info@firma.org"                                                                 =   0 =  OK 
   *  2557 - assertIsTrue  "example-indeed@strange-example.com"                                             =   0 =  OK 
   *  2558 - assertIsTrue  "admin@mailserver1"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2559 - assertIsTrue  "example@s.example"                                                              =   0 =  OK 
   *  2560 - assertIsTrue  "\" \"@example.org"                                                              =   1 =  OK 
   *  2561 - assertIsTrue  "mailhost!username@example.org"                                                  =   0 =  OK 
   *  2562 - assertIsTrue  "user%example.com@example.org"                                                   =   0 =  OK 
   *  2563 - assertIsTrue  "joe25317@NOSPAMexample.com"                                                     =   0 =  OK 
   *  2564 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                                     =   0 =  OK 
   *  2565 - assertIsTrue  "nama@contoh.com"                                                                =   0 =  OK 
   *  2566 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2567 - assertIsFalse "just\"not\"right@example.com"                                                   =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2568 - assertIsFalse "this is\"not\allowed@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2569 - assertIsFalse "this\ still\\"not\\allowed@example.com"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2570 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"             =   0 =  OK 
   *  2571 - assertIsTrue  "(buero)office@example.com"                                                      =   6 =  OK 
   *  2572 - assertIsTrue  "office(etage-3)@example.com"                                                    =   6 =  OK 
   *  2573 - assertIsFalse "off(kommentar)ice@example.com"                                                  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2574 - assertIsTrue  "\"(buero)office\"@example.com"                                                  =   1 =  OK 
   *  2575 - assertIsTrue  "\"office(etage-3)\"@example.com"                                                =   1 =  OK 
   *  2576 - assertIsTrue  "\"off(kommentar)ice\"@example.com"                                              =   1 =  OK 
   *  2577 - assertIsTrue  "\"address(comment)\"@example.com"                                               =   1 =  OK 
   *  2578 - assertIsTrue  "Buero <office@example.com>"                                                     =   0 =  OK 
   *  2579 - assertIsTrue  "\"vorname(Kommentar).nachname\"@provider.de"                                    =   1 =  OK 
   *  2580 - assertIsTrue  "\"Herr \\"Kaiser\\" Franz Beckenbauer\" <local-part@domain-part.com>"           =   0 =  OK 
   *  2581 - assertIsTrue  "Erwin Empfaenger <erwin@example.com>"                                           =   0 =  OK 
   * 
   * ---- https://github.com/egulias/EmailValidator4J ---------------------------------------------------------------------------------
   * 
   *  2582 - assertIsFalse "nolocalpart.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2583 - assertIsFalse "test@example.com test"                                                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2584 - assertIsFalse "user  name@example.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2585 - assertIsFalse "user   name@example.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2586 - assertIsFalse "example.@example.co.uk"                                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2587 - assertIsFalse "example@example@example.co.uk"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2588 - assertIsFalse "(test_exampel@example.fr}"                                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2589 - assertIsFalse "example(example)example@example.co.uk"                                          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2590 - assertIsFalse ".example@localhost"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2591 - assertIsFalse "ex\ample@localhost"                                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2592 - assertIsFalse "example@local\host"                                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2593 - assertIsFalse "example@localhost."                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2594 - assertIsFalse "user name@example.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2595 - assertIsFalse "username@ example . com"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2596 - assertIsFalse "example@(fake}.com"                                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2597 - assertIsFalse "example@(fake.com"                                                              =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2598 - assertIsTrue  "username@example.com"                                                           =   0 =  OK 
   *  2599 - assertIsTrue  "usern.ame@example.com"                                                          =   0 =  OK 
   *  2600 - assertIsFalse "user[na]me@example.com"                                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2601 - assertIsFalse "\"\"\"@iana.org"                                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2602 - assertIsFalse "\"\\"@iana.org"                                                                 =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2603 - assertIsFalse "\"test\"test@iana.org"                                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2604 - assertIsFalse "\"test\"\"test\"@iana.org"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2605 - assertIsTrue  "\"test\".\"test\"@iana.org"                                                     =   1 =  OK 
   *  2606 - assertIsTrue  "\"test\".test@iana.org"                                                         =   1 =  OK 
   *  2607 - assertIsFalse "\"test\\"@iana.org"                                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2608 - assertIsFalse "\r\ntest@iana.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2609 - assertIsFalse "\r\n test@iana.org"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2610 - assertIsFalse "\r\n \r\ntest@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2611 - assertIsFalse "\r\n \r\n test@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2612 - assertIsFalse "test@iana.org \r\n"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2613 - assertIsFalse "test@iana.org \r\n "                                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2614 - assertIsFalse "test@iana.org \r\n \r\n"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2615 - assertIsFalse "test@iana.org \r\n\r\n"                                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2616 - assertIsFalse "test@iana.org  \r\n\r\n "                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2617 - assertIsFalse "test@iana/icann.org"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2618 - assertIsFalse "test@foo;bar.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2619 - assertIsFalse "a@test.com"                                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2620 - assertIsFalse "comment)example@example.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2621 - assertIsFalse "comment(example))@example.com"                                                  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2622 - assertIsFalse "example@example)comment.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2623 - assertIsFalse "example@example(comment)).com"                                                  = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2624 - assertIsFalse "example@[1.2.3.4"                                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2625 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2626 - assertIsFalse "exam(ple@exam).ple"                                                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2627 - assertIsFalse "example@(example))comment.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2628 - assertIsTrue  "example@example.com"                                                            =   0 =  OK 
   *  2629 - assertIsTrue  "example@example.co.uk"                                                          =   0 =  OK 
   *  2630 - assertIsTrue  "example_underscore@example.fr"                                                  =   0 =  OK 
   *  2631 - assertIsTrue  "exam'ple@example.com"                                                           =   0 =  OK 
   *  2632 - assertIsTrue  "exam\ ple@example.com"                                                          =   0 =  OK 
   *  2633 - assertIsFalse "example((example))@fakedfake.co.uk"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2634 - assertIsFalse "example@faked(fake).co.uk"                                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2635 - assertIsTrue  "example+@example.com"                                                           =   0 =  OK 
   *  2636 - assertIsTrue  "example@with-hyphen.example.com"                                                =   0 =  OK 
   *  2637 - assertIsTrue  "with-hyphen@example.com"                                                        =   0 =  OK 
   *  2638 - assertIsTrue  "example@1leadingnum.example.com"                                                =   0 =  OK 
   *  2639 - assertIsTrue  "1leadingnum@example.com"                                                        =   0 =  OK 
   *  2640 - assertIsTrue  "инфо@письмо.рф"                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2641 - assertIsTrue  "\"username\"@example.com"                                                       =   1 =  OK 
   *  2642 - assertIsTrue  "\"user.name\"@example.com"                                                      =   1 =  OK 
   *  2643 - assertIsTrue  "\"user name\"@example.com"                                                      =   1 =  OK 
   *  2644 - assertIsTrue  "\"user@name\"@example.com"                                                      =   1 =  OK 
   *  2645 - assertIsFalse "\"\a\"@iana.org"                                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2646 - assertIsTrue  "\"test\ test\"@iana.org"                                                        =   1 =  OK 
   *  2647 - assertIsFalse "\"\"@iana.org"                                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2648 - assertIsFalse "\"\"@[]"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2649 - assertIsTrue  "\"\\"\"@iana.org"                                                               =   1 =  OK 
   *  2650 - assertIsTrue  "example@localhost"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * 
   * ---- https://github.com/JoshData/python-email-validator/blob/main/tests/test_syntax.py -------------------------------------------
   * 
   *  2651 - assertIsTrue  "\"unnecessarily.quoted.local.part\"@example.com"                                =   1 =  OK 
   *  2652 - assertIsTrue  "\"quoted..local.part\"@example.com"                                             =   1 =  OK 
   *  2653 - assertIsTrue  "\"quoted.with.at@\"@example.com"                                                =   1 =  OK 
   *  2654 - assertIsTrue  "\"quoted with space\"@example.com"                                              =   1 =  OK 
   *  2655 - assertIsTrue  "\"quoted.with.dquote\\"\"@example.com"                                          =   1 =  OK 
   *  2656 - assertIsTrue  "\"unnecessarily.quoted.with.unicode.?\"@example.com"                            =   1 =  OK 
   *  2657 - assertIsTrue  "\"quoted.with..unicode.?\"@example.com"                                         =   1 =  OK 
   *  2658 - assertIsTrue  "\"quoted.with.extraneous.\\escape\"@example.com"                                =   1 =  OK 
   *  2659 - assertIsFalse "my@localhost"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2660 - assertIsFalse "my@.leadingdot.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2661 - assertIsFalse "my@.leadingfwdot.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2662 - assertIsFalse "my@twodots..com"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2663 - assertIsFalse "my@twofwdots...com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2664 - assertIsFalse "my@trailingdot.com."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2665 - assertIsFalse "my@trailingfwdot.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2666 - assertIsFalse "me@-leadingdash"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2667 - assertIsFalse "me@-leadingdashfw"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2668 - assertIsFalse "me@trailingdash-"                                                               =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2669 - assertIsFalse "me@trailingdashfw-"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2670 - assertIsFalse "my@baddash.-.com"                                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2671 - assertIsFalse "my@baddash.-a.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2672 - assertIsFalse "my@baddash.b-.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2673 - assertIsFalse "my@baddashfw.-.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2674 - assertIsFalse "my@baddashfw.-a.com"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2675 - assertIsFalse "my@baddashfw.b-.com"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2676 - assertIsFalse "me@x!"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2677 - assertIsFalse "me@x "                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2678 - assertIsFalse ".leadingdot@domain.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2679 - assertIsFalse "twodots..here@domain.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2680 - assertIsFalse "trailingdot.@domain.email"                                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2681 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2682 - assertIsFalse "white space@test"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2683 - assertIsFalse "test@white space"                                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2684 - assertIsFalse "\nmy@example.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2685 - assertIsFalse "m\ny@example.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2686 - assertIsFalse "my\n@example.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2687 - assertIsFalse "test@\n"                                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2688 - assertIsFalse "bad\"quotes\"@example.com"                                                      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2689 - assertIsTrue  "obsolete.\"quoted\".atom@example.com"                                           =   1 =  OK 
   *  2690 - assertIsFalse "11111111112222222222333333333344444444445555555555666666666677777@example.com"  =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2691 - assertIsFalse "111111111122222222223333333333444444444455555555556666666666777777@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2692 - assertIsFalse "me@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.111111111122222222223333333333444444444455555555556.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2693 - assertIsFalse "me@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555566.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2694 - assertIsFalse "me@?1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555566.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2695 - assertIsFalse "my.long.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.11111111112222222222333333333344444.info" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2696 - assertIsFalse "my.long.address@?111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.11111111112222222222333333.info" =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2697 - assertIsFalse "my.long.address@?111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444.info" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2698 - assertIsFalse "my.?ong.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.111111111122222222223333333333444.info" =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  2699 - assertIsFalse "my.?ong.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444.info" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2700 - assertIsFalse "me@bad-tld-1"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2701 - assertIsFalse "me@bad.tld-2"                                                                   =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  2702 - assertIsTrue  "me@xn--0.tld"                                                                   =   0 =  OK 
   *  2703 - assertIsTrue  "me@[127.0.0.1]"                                                                 =   2 =  OK 
   *  2704 - assertIsFalse "me@[127.0.0.999]"                                                               =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2705 - assertIsTrue  "me@[IPv6:::1]"                                                                  =   4 =  OK 
   *  2706 - assertIsFalse "me@[IPv6:::G]"                                                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2707 - assertIsFalse "me@[tag:text]"                                                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2708 - assertIsFalse "me@[untaggedtext]"                                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2709 - assertIsFalse "me@[tag:invalid space]"                                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2710 - assertIsFalse "test"                                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2711 - assertIsFalse "@"                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2712 - assertIsFalse "test@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2713 - assertIsFalse "@io"                                                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2714 - assertIsFalse "@iana.org"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2715 - assertIsTrue  "a@iana.org"                                                                     =   0 =  OK 
   *  2716 - assertIsFalse "test_exa-mple.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2717 - assertIsTrue  "!#$%&`*+/=?^`{|}~@iana.org"                                                     =   0 =  OK 
   *  2718 - assertIsTrue  "test\@test@iana.org"                                                            =   0 =  OK 
   *  2719 - assertIsTrue  "123@iana.org"                                                                   =   0 =  OK 
   *  2720 - assertIsTrue  "test@123.com"                                                                   =   0 =  OK 
   *  2721 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org"      =   0 =  OK 
   *  2722 - assertIsFalse "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklmn@iana.org"     =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2723 - assertIsFalse "test@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm.com"      =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2724 - assertIsTrue  "test@mason-dixon.com"                                                           =   0 =  OK 
   *  2725 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2726 - assertIsTrue  "a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hij" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2727 - assertIsTrue  "a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hijk" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2728 - assertIsTrue  "\"test\"@iana.org"                                                              =   1 =  OK 
   *  2729 - assertIsTrue  " test @iana.org"                                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2730 - assertIsTrue  " test@iana.org"                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2731 - assertIsFalse "((comment)test@iana.org"                                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2732 - assertIsTrue  "(comment(comment))test@iana.org"                                                =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2733 - assertIsTrue  "(comment)test@iana.org"                                                         =   6 =  OK 
   *  2734 - assertIsFalse "(comment\)test@iana.org"                                                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2735 - assertIsFalse "(test@iana.org"                                                                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2736 - assertIsFalse ".test@iana.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2737 - assertIsFalse "\"\"@iana.org"                                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2738 - assertIsFalse "\"\\"@iana.org"                                                                 =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2739 - assertIsTrue  "\"\\"\"@iana.org"                                                               =   1 =  OK 
   *  2740 - assertIsTrue  "\"\\\"@iana.org"                                                                =   1 =  OK 
   *  2741 - assertIsFalse "\"test@iana.org"                                                                =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2742 - assertIsTrue  "\"test\".\"test\"@iana.org"                                                     =   1 =  OK 
   *  2743 - assertIsTrue  "\"test\".test@iana.org"                                                         =   1 =  OK 
   *  2744 - assertIsFalse "\"test\"\"test\"@iana.org"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2745 - assertIsFalse "\"test\"test@iana.org"                                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2746 - assertIsTrue  "\"test\ test\"@iana.org"                                                        =   1 =  OK 
   *  2747 - assertIsFalse "\"test\\"@iana.org"                                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2748 - assertIsFalse "\"test\x00\"@iana.org"                                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2749 - assertIsFalse "\"test\©\"@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2750 - assertIsTrue  "\r\n \r\n test@iana.org"                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2751 - assertIsTrue  "\r\n test@iana.org"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2752 - assertIsTrue  "test . test@iana.org"                                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2753 - assertIsFalse "test(comment)test@iana.org"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2754 - assertIsFalse "test..iana.org"                                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2755 - assertIsFalse "test.@iana.org"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2756 - assertIsTrue  "test.test@iana.org"                                                             =   0 =  OK 
   *  2757 - assertIsTrue  "test@ iana .com"                                                                = 105 =  #### FEHLER ####    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2758 - assertIsTrue  "test@(comment)iana.org"                                                         =   6 =  OK 
   *  2759 - assertIsFalse "test@(iana.org"                                                                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2760 - assertIsFalse "test@-iana.org"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2761 - assertIsFalse "test@.iana.org"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2762 - assertIsFalse "test@[1.2.3.4"                                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2763 - assertIsTrue  "test@[IPv6:1::2:]"                                                              =   4 =  OK 
   *  2764 - assertIsTrue  "test@about.museum"                                                              =   0 =  OK 
   *  2765 - assertIsTrue  "test@g--a.com"                                                                  =   0 =  OK 
   *  2766 - assertIsFalse "test@iana-.com"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2767 - assertIsFalse "test@iana..com"                                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2768 - assertIsTrue  "test@iana.org "                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2769 - assertIsTrue  "test@iana.org \r\n "                                                            = 105 =  #### FEHLER ####    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2770 - assertIsFalse "test@iana.org \r\n \r\n"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2771 - assertIsFalse "test@iana.org \r\n"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2772 - assertIsFalse "test@iana.org \r\n\r\n "                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2773 - assertIsFalse "test@iana.org \r\n\r\n"                                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2774 - assertIsTrue  "test@iana.org"                                                                  =   0 =  OK 
   *  2775 - assertIsFalse "test@iana.org(comment\"                                                         =  96 =  OK    Kommentar: Escape-Zeichen nicht am Ende der Eingabe
   *  2776 - assertIsFalse "test@iana.org(comment\)"                                                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2777 - assertIsFalse "test@iana.org-"                                                                 =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2778 - assertIsFalse "test@iana.org."                                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2779 - assertIsTrue  "test@iana.org\r\n "                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2780 - assertIsTrue  "test@iana.org\r\n \r\n "                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2781 - assertIsFalse "test@iana.org\r\n \r\n"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2782 - assertIsFalse "test@iana.org\r\n"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2783 - assertIsTrue  "test@iana/icann.org"                                                            =  21 =  #### FEHLER ####    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2784 - assertIsTrue  "test@nominet.org.uk"                                                            =   0 =  OK 
   *  2785 - assertIsFalse "test\"@iana.org"                                                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2786 - assertIsFalse "test\"text\"@iana.org"                                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2787 - assertIsTrue  "\"abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghj\"@iana.org"   =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2788 - assertIsTrue  "\"abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefg\h\"@iana.org"   =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  2789 - assertIsTrue  "test@[255.255.255.255]"                                                         =   2 =  OK 
   *  2790 - assertIsFalse "test@a[255.255.255.255]"                                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2791 - assertIsFalse "test@[255.255.255]"                                                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  2792 - assertIsFalse "test@[255.255.255.255.255]"                                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  2793 - assertIsFalse "test@[255.255.255.256]"                                                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2794 - assertIsTrue  "test@[1111:2222:3333:4444:5555:6666:7777:8888]"                                 =   4 =  OK 
   *  2795 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 =   4 =  OK 
   *  2796 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"                            =   4 =  OK 
   *  2797 - assertIsFalse "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"                       =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  2798 - assertIsFalse "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:888G]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2799 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666::8888]"                                =   4 =  OK 
   *  2800 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555::8888]"                                     =   4 =  OK 
   *  2801 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666::7777:8888]"                           =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  2802 - assertIsTrue  "test@[IPv6::3333:4444:5555:6666:7777:8888]"                                     =   4 =  OK 
   *  2803 - assertIsTrue  "test@[IPv6:::3333:4444:5555:6666:7777:8888]"                                    =   4 =  OK 
   *  2804 - assertIsTrue  "test@[IPv6:1111::4444:5555::8888]"                                              =  50 =  #### FEHLER ####    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  2805 - assertIsTrue  "test@[IPv6:::]"                                                                 =   4 =  OK 
   *  2806 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:255.255.255.255]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2807 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:255.255.255.255]"                      =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2808 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:255.255.255.255]"                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2809 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444::255.255.255.255]"                               =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2810 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666::255.255.255.255]"                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2811 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:::255.255.255.255]"                              =  50 =  #### FEHLER ####    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  2812 - assertIsTrue  "test@[IPv6::255.255.255.255]"                                                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2813 - assertIsTrue  "test@(comment)[255.255.255.255]"                                                =   2 =  OK 
   *  2814 - assertIsTrue  "(comment)abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2815 - assertIsTrue  "test@(comment)abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.com" =  63 =  #### FEHLER ####    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2816 - assertIsTrue  "(comment)test@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghik.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghik.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijk.abcdefghijklmnopqrstuvwxyzabcdefghijk.abcdefghijklmnopqrstu" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2817 - assertIsFalse "test@iana.org\n"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2818 - assertIsTrue  "test@xn--hxajbheg2az3al.xn--jxalpdlp"                                           =   0 =  OK 
   *  2819 - assertIsTrue  "xn--test@iana.org"                                                              =   0 =  OK 
   *  2820 - assertIsTrue  "test@[RFC-5322-domain-literal]"                                                 =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2821 - assertIsFalse "test@[RFC-5322]-domain-literal]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2822 - assertIsFalse "test@[RFC-5322-[domain-literal]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2823 - assertIsTrue  "test@[RFC-5322-\  -domain-literal]"                                              =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2824 - assertIsTrue  "test@[RFC-5322-\]-domain-literal]"                                              =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2825 - assertIsFalse "test@[RFC-5322-domain-literal\]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2826 - assertIsFalse "test@[RFC-5322-domain-literal\"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2827 - assertIsTrue  "test@[RFC 5322 domain literal]"                                                 =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2828 - assertIsTrue  "test@[RFC-5322-domain-literal] (comment)"                                       =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2829 - assertIsFalse "test@iana.org\r"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2830 - assertIsFalse "\rtest@iana.org"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2831 - assertIsFalse "\"\rtest\"@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2832 - assertIsFalse "(\r)test@iana.org"                                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2833 - assertIsFalse "test@iana.org(\r)"                                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2834 - assertIsFalse "\ntest@iana.org"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2835 - assertIsFalse "\"\n\"@iana.org"                                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2836 - assertIsTrue  "\"\\n\"@iana.org"                                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  2837 - assertIsFalse "(\n)test@iana.org"                                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2838 - assertIsTrue  "\"\x07\"@iana.org"                                                              =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  2839 - assertIsFalse "\r\ntest@iana.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2840 - assertIsFalse "\r\n \r\ntest@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2841 - assertIsFalse " \r\ntest@iana.org"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2842 - assertIsTrue  " \r\n test@iana.org"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2843 - assertIsFalse " \r\n \r\ntest@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2844 - assertIsFalse " \r\n\r\ntest@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2845 - assertIsFalse " \r\n\r\n test@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2846 - assertIsFalse "test.(comment)test@iana.org"                                                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   * 
   * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php -------------------------------------------------
   * 
   *  2847 - assertIsTrue  "first.last@iana.org"                                                            =   0 =  OK 
   *  2848 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org"      =   0 =  OK 
   *  2849 - assertIsTrue  "\"first\\"last\"@iana.org"                                                      =   1 =  OK 
   *  2850 - assertIsTrue  "\"first@last\"@iana.org"                                                        =   1 =  OK 
   *  2851 - assertIsTrue  "\"first\\last\"@iana.org"                                                       =   1 =  OK 
   *  2852 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
   *  2853 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
   *  2854 - assertIsTrue  "first.last@[12.34.56.78]"                                                       =   2 =  OK 
   *  2855 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                              =   4 =  OK 
   *  2856 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                               =   4 =  OK 
   *  2857 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                              =   4 =  OK 
   *  2858 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                              =   4 =  OK 
   *  2859 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"                      =   4 =  OK 
   *  2860 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
   *  2861 - assertIsTrue  "first.last@3com.com"                                                            =   0 =  OK 
   *  2862 - assertIsTrue  "first.last@123.iana.org"                                                        =   0 =  OK 
   *  2863 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                        =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2864 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                          =   4 =  OK 
   *  2865 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                          =   1 =  OK 
   *  2866 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                                      =   1 =  OK 
   *  2867 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                                        =   1 =  OK 
   *  2868 - assertIsTrue  "\"Abc@def\"@iana.org"                                                           =   1 =  OK 
   *  2869 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                                     =   1 =  OK 
   *  2870 - assertIsTrue  "user+mailbox@iana.org"                                                          =   0 =  OK 
   *  2871 - assertIsTrue  "$A12345@iana.org"                                                               =   0 =  OK 
   *  2872 - assertIsTrue  "!def!xyz%abc@iana.org"                                                          =   0 =  OK 
   *  2873 - assertIsTrue  "_somename@iana.org"                                                             =   0 =  OK 
   *  2874 - assertIsTrue  "dclo@us.ibm.com"                                                                =   0 =  OK 
   *  2875 - assertIsTrue  "peter.piper@iana.org"                                                           =   0 =  OK 
   *  2876 - assertIsTrue  "test@iana.org"                                                                  =   0 =  OK 
   *  2877 - assertIsTrue  "TEST@iana.org"                                                                  =   0 =  OK 
   *  2878 - assertIsTrue  "1234567890@iana.org"                                                            =   0 =  OK 
   *  2879 - assertIsTrue  "test+test@iana.org"                                                             =   0 =  OK 
   *  2880 - assertIsTrue  "test-test@iana.org"                                                             =   0 =  OK 
   *  2881 - assertIsTrue  "t*est@iana.org"                                                                 =   0 =  OK 
   *  2882 - assertIsTrue  "+1~1+@iana.org"                                                                 =   0 =  OK 
   *  2883 - assertIsTrue  "{_test_}@iana.org"                                                              =   0 =  OK 
   *  2884 - assertIsTrue  "test.test@iana.org"                                                             =   0 =  OK 
   *  2885 - assertIsTrue  "\"test.test\"@iana.org"                                                         =   1 =  OK 
   *  2886 - assertIsTrue  "test.\"test\"@iana.org"                                                         =   1 =  OK 
   *  2887 - assertIsTrue  "\"test@test\"@iana.org"                                                         =   1 =  OK 
   *  2888 - assertIsTrue  "test@123.123.123.x123"                                                          =   0 =  OK 
   *  2889 - assertIsTrue  "test@123.123.123.123"                                                           =   2 =  OK 
   *  2890 - assertIsTrue  "test@[123.123.123.123]"                                                         =   2 =  OK 
   *  2891 - assertIsTrue  "test@example.iana.org"                                                          =   0 =  OK 
   *  2892 - assertIsTrue  "test@example.example.iana.org"                                                  =   0 =  OK 
   *  2893 - assertIsTrue  "customer/department@iana.org"                                                   =   0 =  OK 
   *  2894 - assertIsTrue  "_Yosemite.Sam@iana.org"                                                         =   0 =  OK 
   *  2895 - assertIsTrue  "~@iana.org"                                                                     =   0 =  OK 
   *  2896 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                                     =   1 =  OK 
   *  2897 - assertIsTrue  "Ima.Fool@iana.org"                                                              =   0 =  OK 
   *  2898 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                          =   1 =  OK 
   *  2899 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                                        =   1 =  OK 
   *  2900 - assertIsTrue  "\"first\".\"last\"@iana.org"                                                    =   1 =  OK 
   *  2901 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                             =   1 =  OK 
   *  2902 - assertIsTrue  "\"first\".last@iana.org"                                                        =   1 =  OK 
   *  2903 - assertIsTrue  "first.\"last\"@iana.org"                                                        =   1 =  OK 
   *  2904 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                                         =   1 =  OK 
   *  2905 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                             =   1 =  OK 
   *  2906 - assertIsTrue  "\"first.middle.last\"@iana.org"                                                 =   1 =  OK 
   *  2907 - assertIsTrue  "\"first..last\"@iana.org"                                                       =   1 =  OK 
   *  2908 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                             =   1 =  OK 
   *  2909 - assertIsFalse "first.last @iana.orgin"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2910 - assertIsTrue  "\"test blah\"@iana.orgin"                                                       =   1 =  OK 
   *  2911 - assertIsTrue  "name.lastname@domain.com"                                                       =   0 =  OK 
   *  2912 - assertIsTrue  "a@bar.com"                                                                      =   0 =  OK 
   *  2913 - assertIsTrue  "aaa@[123.123.123.123]"                                                          =   2 =  OK 
   *  2914 - assertIsTrue  "a-b@bar.com"                                                                    =   0 =  OK 
   *  2915 - assertIsFalse "+@b.c"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2916 - assertIsTrue  "+@b.com"                                                                        =   0 =  OK 
   *  2917 - assertIsTrue  "a@b.co-foo.uk"                                                                  =   0 =  OK 
   *  2918 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                             =   1 =  OK 
   *  2919 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                             =   1 =  OK 
   *  2920 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                               =   0 =  OK 
   *  2921 - assertIsTrue  "foobar@192.168.0.1"                                                             =   2 =  OK 
   *  2922 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                              =   6 =  OK 
   *  2923 - assertIsTrue  "user%uucp!path@berkeley.edu"                                                    =   0 =  OK 
   *  2924 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                                      =   0 =  OK 
   *  2925 - assertIsTrue  "test@test.com"                                                                  =   0 =  OK 
   *  2926 - assertIsTrue  "test@xn--example.com"                                                           =   0 =  OK 
   *  2927 - assertIsTrue  "test@example.com"                                                               =   0 =  OK 
   *  2928 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                       =   0 =  OK 
   *  2929 - assertIsTrue  "first\@last@iana.org"                                                           =   0 =  OK 
   *  2930 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                                       =   0 =  OK 
   *  2931 - assertIsFalse "first.last@example.123"                                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  2932 - assertIsFalse "first.last@comin"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2933 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                                      =   1 =  OK 
   *  2934 - assertIsTrue  "Abc\@def@iana.org"                                                              =   0 =  OK 
   *  2935 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                          =   0 =  OK 
   *  2936 - assertIsFalse "Joe.\Blow@iana.org"                                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2937 - assertIsTrue  "first.last@sub.do.com"                                                          =   0 =  OK 
   *  2938 - assertIsFalse "first.last"                                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2939 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                                =   0 =  OK 
   *  2940 - assertIsTrue  "\"hello world\"@example.com"                                                    =   1 =  OK 
   *  2941 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2942 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                                   =   1 =  OK 
   *  2943 - assertIsTrue  "example+tag@gmail.com"                                                          =   0 =  OK 
   *  2944 - assertIsFalse ".ann..other.@example.com"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2945 - assertIsTrue  "ann.other@example.com"                                                          =   0 =  OK 
   *  2946 - assertIsTrue  "something@something.something"                                                  =   0 =  OK 
   *  2947 - assertIsTrue  "c@(Chris's host.)public.examplein"                                              =   6 =  OK 
   *  2948 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                            =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2949 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2950 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2951 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                                     =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2952 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                                     =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2953 - assertIsFalse "first().last@iana.orgin"                                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2954 - assertIsFalse "pete(his account)@silly.test(his host)"                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2955 - assertIsFalse "jdoe@machine(comment). examplein"                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2956 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2957 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2958 - assertIsFalse "1234 @ local(blah) .machine .examplein"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2959 - assertIsFalse "a@bin"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2960 - assertIsFalse "a@barin"                                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2961 - assertIsFalse "@about.museum"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2962 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org"     =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2963 - assertIsFalse ".first.last@iana.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2964 - assertIsFalse "first.last.@iana.org"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2965 - assertIsFalse "first..last@iana.org"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2966 - assertIsFalse "\"first\"last\"@iana.org"                                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2967 - assertIsFalse "first.last@"                                                                    =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *  2968 - assertIsFalse "first.last@-xample.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2969 - assertIsFalse "first.last@exampl-.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2970 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2971 - assertIsFalse "abc\@iana.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2972 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2973 - assertIsFalse "abc@def@iana.org"                                                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2974 - assertIsFalse "@iana.org"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2975 - assertIsFalse "doug@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2976 - assertIsFalse "\"qu@iana.org"                                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2977 - assertIsFalse "ote\"@iana.org"                                                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2978 - assertIsFalse ".dot@iana.org"                                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2979 - assertIsFalse "dot.@iana.org"                                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2980 - assertIsFalse "two..dot@iana.org"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2981 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2982 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2983 - assertIsFalse "hello world@iana.org"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2984 - assertIsFalse "test.iana.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2985 - assertIsFalse "test.@iana.org"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2986 - assertIsFalse "test..test@iana.org"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2987 - assertIsFalse ".test@iana.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2988 - assertIsFalse "test@test@iana.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2989 - assertIsFalse "test@@iana.org"                                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2990 - assertIsFalse "-- test --@iana.org"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2991 - assertIsFalse "[test]@iana.org"                                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2992 - assertIsFalse "\"test\"test\"@iana.org"                                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2993 - assertIsFalse "()[]\;:.><@iana.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2994 - assertIsFalse "test@."                                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2995 - assertIsFalse "test@example."                                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2996 - assertIsFalse "test@.org"                                                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2997 - assertIsFalse "test@[123.123.123.123"                                                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2998 - assertIsFalse "test@123.123.123.123]"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2999 - assertIsFalse "NotAnEmail"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3000 - assertIsFalse "@NotAnEmail"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3001 - assertIsFalse "\"test\"blah\"@iana.org"                                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3002 - assertIsFalse ".wooly@iana.org"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3003 - assertIsFalse "wo..oly@iana.org"                                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3004 - assertIsFalse "pootietang.@iana.org"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3005 - assertIsFalse ".@iana.org"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3006 - assertIsFalse "Ima Fool@iana.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3007 - assertIsFalse "foo@[\1.2.3.4]"                                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3008 - assertIsFalse "first.\"\".last@iana.org"                                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3009 - assertIsFalse "first\last@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3010 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3011 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  3012 - assertIsFalse "cal(foo(bar)@iamcal.com"                                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3013 - assertIsFalse "cal(foo)bar)@iamcal.com"                                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3014 - assertIsFalse "cal(foo\)@iamcal.com"                                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3015 - assertIsFalse "first(middle)last@iana.org"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3016 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3017 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3018 - assertIsFalse ".@"                                                                             =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3019 - assertIsFalse "@bar.com"                                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3020 - assertIsFalse "@@bar.com"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3021 - assertIsFalse "aaa.com"                                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3022 - assertIsFalse "aaa@.com"                                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3023 - assertIsFalse "aaa@.123"                                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3024 - assertIsFalse "aaa@[123.123.123.123]a"                                                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3025 - assertIsFalse "aaa@[123.123.123.333]"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3026 - assertIsFalse "a@bar.com."                                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3027 - assertIsFalse "a@-b.com"                                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3028 - assertIsFalse "a@b-.com"                                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3029 - assertIsFalse "-@..com"                                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3030 - assertIsFalse "-@a..com"                                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3031 - assertIsFalse "@about.museum-"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3032 - assertIsFalse "test@...........com"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3033 - assertIsFalse "first.last@[IPv6::]"                                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3034 - assertIsFalse "first.last@[IPv6::::]"                                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3035 - assertIsFalse "first.last@[IPv6::b4]"                                                          =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3036 - assertIsFalse "first.last@[IPv6::::b4]"                                                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3037 - assertIsFalse "first.last@[IPv6::b3:b4]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3038 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3039 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3040 - assertIsFalse "first.last@[IPv6:a1:]"                                                          =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3041 - assertIsFalse "first.last@[IPv6:a1:::]"                                                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3042 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3043 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3044 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3045 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3046 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3047 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                             =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3048 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3049 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3050 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                            =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  3051 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3052 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3053 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                           =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3054 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                            =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *  3055 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3056 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                                      =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3057 - assertIsFalse "first.last@[IPv6::a2::b4]"                                                      =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3058 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                                        =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3059 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                                        =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3060 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                                     =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3061 - assertIsFalse "first.last@[.12.34.56.78]"                                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  3062 - assertIsFalse "first.last@[12.34.56.789]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3063 - assertIsFalse "first.last@[::12.34.56.78]"                                                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3064 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                                =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3065 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                                =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  3066 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3067 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3068 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3069 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3070 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3071 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  3072 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3073 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3074 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3075 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                    =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3076 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                                       =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3077 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                                       =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3078 - assertIsTrue  "first.last@[IPv6:::]"                                                           =   4 =  OK 
   *  3079 - assertIsTrue  "first.last@[IPv6:::b4]"                                                         =   4 =  OK 
   *  3080 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                                      =   4 =  OK 
   *  3081 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                                       =   4 =  OK 
   *  3082 - assertIsTrue  "first.last@[IPv6:a1::]"                                                         =   4 =  OK 
   *  3083 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                                      =   4 =  OK 
   *  3084 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                                        =   4 =  OK 
   *  3085 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                                        =   4 =  OK 
   *  3086 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                                  =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3087 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                               =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3088 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3089 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                                  =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3090 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                              =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3091 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3092 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                             =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3093 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                             =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3094 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3095 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                           =   4 =  OK 
   * 
   * ---- https://www.rohannagar.com/jmail/ -------------------------------------------------------------------------------------------
   * 
   *  3096 - assertIsFalse "\"qu@test.org"                                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3097 - assertIsFalse "ote\"@test.org"                                                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3098 - assertIsFalse "\"().:;<>[\]@example.com"                                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3099 - assertIsFalse "\"\"\"@iana.org"                                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3100 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3101 - assertIsFalse "this is\"not\allowed@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3102 - assertIsFalse "this\ still\"not\allowed@example.com"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3103 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3104 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3105 - assertIsFalse "plainaddress"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3106 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3107 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                                      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3108 - assertIsTrue  "\"first\\"last\"@test.org"                                                      =   1 =  OK 
   *  3109 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3110 - assertIsTrue  "first\@last@iana.org"                                                           =   0 =  OK 
   *  3111 - assertIsFalse "test@example.com "                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3112 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                           =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3113 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3114 - assertIsFalse "invalid@about.museum-"                                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3115 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  3116 - assertIsFalse "abc@def@test.org"                                                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3117 - assertIsTrue  "abc\@def@test.org"                                                              =   0 =  OK 
   *  3118 - assertIsFalse "abc\@test.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3119 - assertIsFalse "@test.org"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3120 - assertIsFalse ".dot@test.org"                                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3121 - assertIsFalse "dot.@test.org"                                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3122 - assertIsFalse "two..dot@test.org"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3123 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3124 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3125 - assertIsFalse "hello world@test.org"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3126 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3127 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3128 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3129 - assertIsFalse "test.test.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3130 - assertIsFalse "test.@test.org"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3131 - assertIsFalse "test..test@test.org"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3132 - assertIsFalse ".test@test.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3133 - assertIsFalse "test@test@test.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3134 - assertIsFalse "test@@test.org"                                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3135 - assertIsFalse "-- test --@test.org"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3136 - assertIsFalse "[test]@test.org"                                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3137 - assertIsFalse "\"test\"test\"@test.org"                                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3138 - assertIsFalse "()[]\;:.><@test.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3139 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3140 - assertIsFalse ".@test.org"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3141 - assertIsFalse "Ima Fool@test.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3142 - assertIsTrue  "\"first\\"last\"@test.org"                                                      =   1 =  OK 
   *  3143 - assertIsFalse "foo@[.2.3.4]"                                                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3144 - assertIsFalse "first\last@test.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3145 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3146 - assertIsFalse "first(middle)last@test.org"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3147 - assertIsFalse "\"test\"test@test.com"                                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3148 - assertIsFalse "()@test.com"                                                                    =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *  3149 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
   *  3150 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  3151 - assertIsFalse "invalid@[1]"                                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3152 - assertIsFalse "ä📧@-foo"                                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3153 - assertIsFalse "ä📧@foo-"                                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3154 - assertIsFalse "first(comment(inner@comment.com"                                                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3155 - assertIsFalse "Joe A Smith <email@example.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3156 - assertIsFalse "Joe A Smith email@example.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3157 - assertIsFalse "Joe A Smith <email@example.com->"                                               =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3158 - assertIsFalse "Joe A Smith <email@-example.com->"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3159 - assertIsFalse "Joe A Smith <email>"                                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3160 - assertIsTrue  "\"email\"@example.com"                                                          =   1 =  OK 
   *  3161 - assertIsTrue  "\"first@last\"@test.org"                                                        =   1 =  OK 
   *  3162 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                                     =   1 =  OK 
   *  3163 - assertIsFalse "\"first\"last\"@test.org"                                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3164 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                             =   1 =  OK 
   *  3165 - assertIsFalse "\"first\last\"@test.org"                                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3166 - assertIsTrue  "\"Abc\@def\"@test.org"                                                          =   1 =  OK 
   *  3167 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                                      =   1 =  OK 
   *  3168 - assertIsFalse "\"Joe.\Blow\"@test.org"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3169 - assertIsTrue  "\"Abc@def\"@test.org"                                                           =   1 =  OK 
   *  3170 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                                       =   1 =  OK 
   *  3171 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@test.org"                                                 =   1 =  OK 
   *  3172 - assertIsTrue  "\"[[ test ]]\"@test.org"                                                        =   1 =  OK 
   *  3173 - assertIsTrue  "\"test.test\"@test.org"                                                         =   1 =  OK 
   *  3174 - assertIsTrue  "test.\"test\"@test.org"                                                         =   1 =  OK 
   *  3175 - assertIsTrue  "\"test@test\"@test.org"                                                         =   1 =  OK 
   *  3176 - assertIsFalse "\"test tabulator  est\"@test.org"                                               =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  3177 - assertIsTrue  "\"first\".\"last\"@test.org"                                                    =   1 =  OK 
   *  3178 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                             =   1 =  OK 
   *  3179 - assertIsTrue  "\"first\".last@test.org"                                                        =   1 =  OK 
   *  3180 - assertIsTrue  "first.\"last\"@test.org"                                                        =   1 =  OK 
   *  3181 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                                         =   1 =  OK 
   *  3182 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                             =   1 =  OK 
   *  3183 - assertIsTrue  "\"first.middle.last\"@test.org"                                                 =   1 =  OK 
   *  3184 - assertIsTrue  "\"first..last\"@test.org"                                                       =   1 =  OK 
   *  3185 - assertIsTrue  "\"Unicode NULL \"@char.com"                                                     =   1 =  OK 
   *  3186 - assertIsFalse "\"test\blah\"@test.org"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3187 - assertIsFalse "\"testlah\"@test.org"                                                          =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  3188 - assertIsTrue  "\"test\\"blah\"@test.org"                                                       =   1 =  OK 
   *  3189 - assertIsTrue  "\"first\\"last\"@test.org"                                                      =   1 =  OK 
   *  3190 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                               =   1 =  OK 
   *  3191 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3192 - assertIsTrue  "\"test blah\"@test.org"                                                         =   1 =  OK 
   *  3193 - assertIsTrue  "first.last@test.org"                                                            =   0 =  OK 
   *  3194 - assertIsFalse "jdoe@machine(comment).example"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3195 - assertIsFalse "first.\"\".last@test.org"                                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3196 - assertIsFalse "\"\"@test.org"                                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3197 - assertIsTrue  "very.common@example.org"                                                        =   0 =  OK 
   *  3198 - assertIsTrue  "test/test@test.com"                                                             =   0 =  OK 
   *  3199 - assertIsTrue  "user-@example.org"                                                              =   0 =  OK 
   *  3200 - assertIsTrue  "firstname.lastname@example.com"                                                 =   0 =  OK 
   *  3201 - assertIsTrue  "email@subdomain.example.com"                                                    =   0 =  OK 
   *  3202 - assertIsTrue  "firstname+lastname@example.com"                                                 =   0 =  OK 
   *  3203 - assertIsTrue  "1234567890@example.com"                                                         =   0 =  OK 
   *  3204 - assertIsTrue  "email@example-one.com"                                                          =   0 =  OK 
   *  3205 - assertIsTrue  "_______@example.com"                                                            =   0 =  OK 
   *  3206 - assertIsTrue  "email@example.name"                                                             =   0 =  OK 
   *  3207 - assertIsTrue  "email@example.museum"                                                           =   0 =  OK 
   *  3208 - assertIsTrue  "email@example.co.jp"                                                            =   0 =  OK 
   *  3209 - assertIsTrue  "firstname-lastname@example.com"                                                 =   0 =  OK 
   *  3210 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
   *  3211 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
   *  3212 - assertIsTrue  "first.last@123.test.org"                                                        =   0 =  OK 
   *  3213 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
   *  3214 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org"      =   0 =  OK 
   *  3215 - assertIsTrue  "user+mailbox@test.org"                                                          =   0 =  OK 
   *  3216 - assertIsTrue  "customer/department=shipping@test.org"                                          =   0 =  OK 
   *  3217 - assertIsTrue  "$A12345@test.org"                                                               =   0 =  OK 
   *  3218 - assertIsTrue  "!def!xyz%abc@test.org"                                                          =   0 =  OK 
   *  3219 - assertIsTrue  "_somename@test.org"                                                             =   0 =  OK 
   *  3220 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                                =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3221 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                             =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3222 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3223 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3224 - assertIsTrue  "+@b.c"                                                                          =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  3225 - assertIsTrue  "TEST@test.org"                                                                  =   0 =  OK 
   *  3226 - assertIsTrue  "1234567890@test.org"                                                            =   0 =  OK 
   *  3227 - assertIsTrue  "test-test@test.org"                                                             =   0 =  OK 
   *  3228 - assertIsTrue  "t*est@test.org"                                                                 =   0 =  OK 
   *  3229 - assertIsTrue  "+1~1+@test.org"                                                                 =   0 =  OK 
   *  3230 - assertIsTrue  "{_test_}@test.org"                                                              =   0 =  OK 
   *  3231 - assertIsTrue  "valid@about.museum"                                                             =   0 =  OK 
   *  3232 - assertIsTrue  "a@bar"                                                                          =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  3233 - assertIsFalse "cal(foo\@bar)@iamcal.com"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3234 - assertIsTrue  "(comment)test@test.org"                                                         =   6 =  OK 
   *  3235 - assertIsFalse "cal(foo\)bar)@iamcal.com"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3236 - assertIsFalse "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3237 - assertIsFalse "first(abc\(def)@test.org"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3238 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3239 - assertIsTrue  "c@(Chris's host.)public.example"                                                =   6 =  OK 
   *  3240 - assertIsTrue  "_Yosemite.Sam@test.org"                                                         =   0 =  OK 
   *  3241 - assertIsTrue  "~@test.org"                                                                     =   0 =  OK 
   *  3242 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                                  =   6 =  OK 
   *  3243 - assertIsTrue  "test@Bücher.ch"                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3244 - assertIsTrue  "あいうえお@example.com"                                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3245 - assertIsTrue  "Pelé@example.com"                                                               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3246 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3247 - assertIsTrue  "我買@屋企.香港"                                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3248 - assertIsTrue  "二ノ宮@黒川.日本"                                                                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3249 - assertIsTrue  "медведь@с-балалайкой.рф"                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3250 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3251 - assertIsTrue  "email@example.com (Joe Smith)"                                                  =   6 =  OK 
   *  3252 - assertIsFalse "cal@iamcal(woo).(yay)com"                                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3253 - assertIsFalse "first(abc.def).last@test.org"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3254 - assertIsFalse "first(a\"bc.def).last@test.org"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3255 - assertIsFalse "first.(\")middle.last(\")@test.org"                                             = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  3256 - assertIsFalse "first().last@test.org"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3257 - assertIsTrue  "Abc\@def@test.org"                                                              =   0 =  OK 
   *  3258 - assertIsTrue  "Fred\ Bloggs@test.org"                                                          =   0 =  OK 
   *  3259 - assertIsTrue  "Joe.\\Blow@test.org"                                                            =   0 =  OK 
   * 
   * ---- unsorted from the WEB -------------------------------------------------------------------------------------------------------
   * 
   *  3260 - assertIsFalse "testm ail@mail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3261 - assertIsFalse "testmail@mail.com."                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3262 - assertIsFalse ".testmail@mail.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3263 - assertIsFalse " testmail@mail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3264 - assertIsTrue  "username@company.domain"                                                        =   0 =  OK 
   *  3265 - assertIsTrue  "support@whoisxmlapi.com"                                                        =   0 =  OK 
   *  3266 - assertIsTrue  "popular_website15@comPany.com"                                                  =   0 =  OK 
   *  3267 - assertIsTrue  "domain.starts.with.numbers1@1234domain.com"                                     =   0 =  OK 
   *  3268 - assertIsTrue  "domain.starts.with.numbers2@123.123domain.com"                                  =   0 =  OK 
   *  3269 - assertIsTrue  "^local.part.with.caret@domain.com"                                              =   0 =  OK 
   *  3270 - assertIsTrue  "answers@educative.io"                                                           =   0 =  OK 
   *  3271 - assertIsTrue  "test_123@gmail.com"                                                             =   0 =  OK 
   *  3272 - assertIsTrue  "student@mit.edu"                                                                =   0 =  OK 
   *  3273 - assertIsTrue  "admission@ox.ac.uk"                                                             =   0 =  OK 
   *  3274 - assertIsTrue  "-9i{@ssss.ss"                                                                   =   0 =  OK 
   *  3275 - assertIsFalse "-9i{@-ssss.ss"                                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3276 - assertIsFalse "answers@educative"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3277 - assertIsFalse "test 123@gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3278 - assertIsFalse "student@mitedu"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3279 - assertIsFalse "admission@@ox.ac.uk"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3280 - assertIsFalse "my@as@sd.com"                                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3281 - assertIsFalse "{a}!%?@@bar.com"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3282 - assertIsFalse "foo#!@@bar.com"                                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3283 - assertIsFalse "Younourmetd345@abcd.b"                                                          =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3284 - assertIsFalse "@youmenandwe.we.net"                                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3285 - assertIsFalse "yourminewebsite@.com.you "                                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3286 - assertIsFalse "Ourwebsiteismne.azbyz.com"                                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3287 - assertIsFalse "Inownzsite()&@abcd.com"                                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3288 - assertIsFalse "Owner.me..7080@abcd.com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3289 - assertIsTrue  "Own.minesite@myuniverse.org"                                                    =   0 =  OK 
   *  3290 - assertIsTrue  "my+address@example.org"                                                         =   0 =  OK 
   *  3291 - assertIsFalse "me@Ｄｏｍａｉｎ.com"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3292 - assertIsFalse "me@D o m a i n.com"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3293 - assertIsFalse "invalid@domain,com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3294 - assertIsTrue  "hello@00.pe"                                                                    =   0 =  OK 
   *  3295 - assertIsTrue  "\"@\"@domain.com"                                                               =   1 =  OK 
   *  3296 - assertIsTrue  "\@@domain.com"                                                                  =   0 =  OK 
   *  3297 - assertIsTrue  "to1@domain.com"                                                                 =   0 =  OK 
   *  3298 - assertIsTrue  "Full Name <full@example.com>"                                                   =   0 =  OK 
   *  3299 - assertIsFalse "ツ-test@joshdata.me"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3300 - assertIsFalse "user@@host"                                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3301 - assertIsTrue  "t#@d.com"                                                                       =   0 =  OK 
   *  3302 - assertIsFalse "u\"evil@domain.com"                                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3303 - assertIsTrue  "nonexistinglogin@valid-domain.com"                                              =   0 =  OK 
   *  3304 - assertIsTrue  "user@department.company.com"                                                    =   0 =  OK 
   *  3305 - assertIsTrue  "john@example.com"                                                               =   0 =  OK 
   *  3306 - assertIsTrue  "python-list@python.org"                                                         =   0 =  OK 
   *  3307 - assertIsTrue  "wha.t.`1an?ug{}ly@email.com"                                                    =   0 =  OK 
   *  3308 - assertIsFalse "Hello ABCD, here is my mail id example@me.com "                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3309 - assertIsTrue  "\"djt jr\"@wh.gov"                                                              =   1 =  OK 
   *  3310 - assertIsFalse "a..@..............a"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3311 - assertIsTrue  "me+valid@mydomain.example.net"                                                  =   0 =  OK 
   *  3312 - assertIsTrue  "revo@74.125.228.53"                                                             =   2 =  OK 
   *  3313 - assertIsFalse "revo@test&^%$#|.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3314 - assertIsTrue  "\"<script>alert('XSS')</script>\"@example.com "                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3315 - assertIsTrue  "ansel@adams.photography"                                                        =   0 =  OK 
   *  3316 - assertIsFalse "<')))><@fish.left.com"                                                          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3317 - assertIsFalse "><(((*>@fish.right.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3318 - assertIsFalse " check@this.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3319 - assertIsFalse " email@example.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3320 - assertIsFalse ".....@a...."                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3321 - assertIsFalse "..@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3322 - assertIsFalse "..@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3323 - assertIsTrue  "\"test....\"@gmail.com"                                                         =   1 =  OK 
   *  3324 - assertIsFalse "test....@gmail.com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3325 - assertIsTrue  "name@xn--4ca9at.at"                                                             =   0 =  OK 
   *  3326 - assertIsTrue  "simon-@hotmail.com"                                                             =   0 =  OK 
   *  3327 - assertIsTrue  "!@mydomain.net"                                                                 =   0 =  OK 
   *  3328 - assertIsTrue  "sean.o'leary@cobbcounty.org"                                                    =   0 =  OK 
   *  3329 - assertIsTrue  "xjhgjg876896@domain.com"                                                        =   0 =  OK 
   *  3330 - assertIsTrue  "Tony Snow <tony@example.com>"                                                   =   0 =  OK 
   *  3331 - assertIsTrue  "(tony snow) tony@example.com"                                                   =   6 =  OK 
   *  3332 - assertIsTrue  "tony%example.com@example.org"                                                   =   0 =  OK 
   *  3333 - assertIsFalse "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3334 - assertIsFalse "a-b'c_d.e@f-g.h"                                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3335 - assertIsFalse "a-b'c_d.@f-g.h"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3336 - assertIsFalse "a-b'c_d.e@f-.h"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3337 - assertIsTrue  "\"root\"@example.com"                                                           =   1 =  OK 
   *  3338 - assertIsTrue  "root@example.com"                                                               =   0 =  OK 
   *  3339 - assertIsFalse ".@s.dd"                                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3340 - assertIsFalse ".@s.dd"                                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3341 - assertIsFalse ".a@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3342 - assertIsFalse ".a@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3343 - assertIsFalse ".abc@somedomain.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3344 - assertIsFalse ".dot@example.com"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3345 - assertIsFalse ".email@domain.com"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3346 - assertIsFalse ".journaldev@journaldev.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3347 - assertIsFalse ".username@yahoo.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3348 - assertIsFalse ".username@yahoo.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3349 - assertIsFalse ".xxxx@mysite.org"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3350 - assertIsFalse "stuff.@stuff.com"                                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3351 - assertIsFalse "asdf@asdf"                                                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3352 - assertIsFalse "123@$.xyz"                                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3353 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3354 - assertIsFalse "@%^%#$@#$@#.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3355 - assertIsFalse "@b.com"                                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3356 - assertIsFalse "@domain.com"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3357 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3358 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3359 - assertIsFalse "@yahoo.com"                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3360 - assertIsFalse "@you.me.net"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3361 - assertIsFalse "A@b@c@example.com"                                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3362 - assertIsFalse "Abc.example.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3363 - assertIsFalse "Abc@example.com."                                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3364 - assertIsFalse "Abc..123@example.com"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3365 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3366 - assertIsFalse "Display Name <email@plus.com> (after name with display)"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3367 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3368 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3369 - assertIsFalse "Foobar Some@thing.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3370 - assertIsFalse "MailTo:casesensitve@domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3371 - assertIsFalse "No -foo@bar.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3372 - assertIsFalse "No asd@-bar.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3373 - assertIsFalse "DomainHyphen@-atstart"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3374 - assertIsFalse "DomainHyphen@atend-.com"                                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3375 - assertIsFalse "DomainHyphen@bb.-cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3376 - assertIsFalse "DomainHyphen@bb.-cc-"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3377 - assertIsFalse "DomainHyphen@bb.cc-"                                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3378 - assertIsFalse "DomainHyphen@bb.c-c"                                                            =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  3379 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3380 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                              =   0 =  OK 
   *  3381 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3382 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3383 - assertIsFalse "DomainNotAllowedCharacter@example'"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3384 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3385 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                           =   0 =  OK 
   *  3386 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                             =   0 =  OK 
   *  3387 - assertIsFalse "tld.starts.with.digit@domain.2com"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3388 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                                =   0 =  OK 
   *  3389 - assertIsTrue  "enrst.den.baecker@web.de"                                                       =   0 =  OK 
   *  3390 - assertIsTrue  "neserdna.trebkce@web.de"                                                        =   0 =  OK 
   *  3391 - assertIsTrue  "rpxoreg.naqerfra@web.de"                                                        =   0 =  OK 
   *  3392 - assertIsFalse "unescaped white space@fake$com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3393 - assertIsTrue  "\"SL-EA-234\"@kfz-kennzeichen.de"                                               =   1 =  OK 
   *  3394 - assertIsTrue  "\"Name\"@test.de"                                                               =   1 =  OK 
   *  3395 - assertIsTrue  "\"Name.\"@test.de"                                                              =   1 =  OK 
   *  3396 - assertIsTrue  "\"Name..\"@test.de"                                                             =   1 =  OK 
   *  3397 - assertIsTrue  "\"Name.\\"\"@test.de"                                                           =   1 =  OK 
   *  3398 - assertIsTrue  "\".Name.\"@test.de"                                                             =   1 =  OK 
   *  3399 - assertIsTrue  "\"..Name..\"@test.de"                                                           =   1 =  OK 
   *  3400 - assertIsTrue  "\"...Name...\"@test.de"                                                         =   1 =  OK 
   *  3401 - assertIsTrue  "\" ...Name... \"@test.de"                                                       =   1 =  OK 
   *  3402 - assertIsTrue  "\"..Name\"@test.de"                                                             =   1 =  OK 
   *  3403 - assertIsTrue  "\"Name\"@test.de"                                                               =   1 =  OK 
   *  3404 - assertIsTrue  "\"\\"Name.\\"\"@test.de"                                                        =   1 =  OK 
   *  3405 - assertIsTrue  "\\\"Name.\\"\"@test.de"                                                         =   1 =  OK 
   *  3406 - assertIsTrue  "\"Vorname.Nachname\"@test.de"                                                   =   1 =  OK 
   *  3407 - assertIsTrue  "\"Vorname+Nachname\"@test.de"                                                   =   1 =  OK 
   *  3408 - assertIsTrue  "\"Vorname.Nachname.\"@test.de"                                                  =   1 =  OK 
   *  3409 - assertIsTrue  "\"Vorname+Nachname.\"@test.de"                                                  =   1 =  OK 
   *  3410 - assertIsTrue  "\" 123,45 * 678,910 = 83.811,4395 \"@calc-test.de"                              =   1 =  OK 
   *  3411 - assertIsTrue  "\" 50.000,00 / 106,00 = 471.69811320754716981132075471698113 \"@calc-test.de"   =   1 =  OK 
   *  3412 - assertIsTrue  "\" 2 + 2 = 3,999999999999999999999999991 \"@erste-hochrechnung.de"              =   1 =  OK 
   *  3413 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                                      =   1 =  OK 
   *  3414 - assertIsTrue  "\".John.Doe\"@example.com"                                                      =   1 =  OK 
   *  3415 - assertIsTrue  "\"John.Doe.\"@example.com"                                                      =   1 =  OK 
   *  3416 - assertIsTrue  "\"John..Doe\"@example.com"                                                      =   1 =  OK 
   *  3417 - assertIsTrue  "john@doe.com77"                                                                 =   0 =  OK 
   *  3418 - assertIsTrue  "john.smith(comment)@example.com"                                                =   6 =  OK 
   *  3419 - assertIsTrue  "(comment)john.smith@example.com"                                                =   6 =  OK 
   *  3420 - assertIsTrue  "john.smith@(comment)example.com"                                                =   6 =  OK 
   *  3421 - assertIsTrue  "john.smith@example.com(comment)"                                                =   6 =  OK 
   *  3422 - assertIsTrue  "john.smith@example.com"                                                         =   0 =  OK 
   *  3423 - assertIsTrue  "joeuser+tag@example.com"                                                        =   0 =  OK 
   *  3424 - assertIsTrue  "jsmith@[192.168.2.1]"                                                           =   2 =  OK 
   *  3425 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                                      =   4 =  OK 
   *  3426 - assertIsTrue  "Fred\ Bloggs@example.com"                                                       =   0 =  OK 
   *  3427 - assertIsTrue  "Joe.\\Blow@example.com"                                                         =   0 =  OK 
   *  3428 - assertIsTrue  "<john@doe.com>"                                                                 =   0 =  OK 
   *  3429 - assertIsTrue  "John <john@doe.com>"                                                            =   0 =  OK 
   *  3430 - assertIsFalse "john.smith@exampl(comment)e.com"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  3431 - assertIsFalse "john.s(comment)mith@example.com"                                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3432 - assertIsFalse "john.smith(comment)@(comment)example.com"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  3433 - assertIsFalse "john.smith(com@ment)example.com"                                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3434 - assertIsTrue  "john.doe@example.com"                                                           =   0 =  OK 
   *  3435 - assertIsTrue  "john.o'doe@example.com"                                                         =   0 =  OK 
   *  3436 - assertIsTrue  "jones@ms1.proseware.com"                                                        =   0 =  OK 
   *  3437 - assertIsFalse "\"\"Joe Smith email@domain.com"                                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3438 - assertIsFalse "\"\"Joe Smith' email@domain.com"                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3439 - assertIsFalse "\"\"Joe Smith\"\"email@domain.com"                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3440 - assertIsFalse "\"Joe Smith\" email@domain.com"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3441 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3442 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3443 - assertIsFalse "\"Joe Smith email@domain.com"                                                   =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3444 - assertIsFalse "\"Joe Smith' email@domain.com"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3445 - assertIsFalse "\"Joe Smith\"email@domain.com"                                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3446 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3447 - assertIsTrue  "{john'doe}@my.server"                                                           =   0 =  OK 
   *  3448 - assertIsTrue  "email@domain-one.com"                                                           =   0 =  OK 
   *  3449 - assertIsTrue  "_______@domain.com"                                                             =   0 =  OK 
   *  3450 - assertIsTrue  "?????@domain.com"                                                               =   0 =  OK 
   *  3451 - assertIsFalse "local@?????.com"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3452 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                                  =   1 =  OK 
   *  3453 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                                         =   1 =  OK 
   *  3454 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                      =   0 =  OK 
   *  3455 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                               =   0 =  OK 
   *  3456 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3457 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"                        =   0 =  OK 
   *  3458 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3459 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                            =   1 =  OK 
   *  3460 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3461 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3462 - assertIsFalse "\"qu@example.com"                                                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3463 - assertIsFalse "\$A12345@example.com"                                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3464 - assertIsFalse "_@bde.cc,"                                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3465 - assertIsFalse "a..b@bde.cc"                                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3466 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3467 - assertIsFalse "a.b@example,co.de"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3468 - assertIsFalse "a.b@example,com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3469 - assertIsFalse "a>b@somedomain.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3470 - assertIsFalse "a@.com"                                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3471 - assertIsFalse "a@b."                                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3472 - assertIsFalse "a@b.-de.cc"                                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3473 - assertIsFalse "a@b._de.cc"                                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3474 - assertIsFalse "a@bde-.cc"                                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3475 - assertIsFalse "a@bde.c-c"                                                                      =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  3476 - assertIsFalse "a@bde.cc."                                                                      =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3477 - assertIsFalse "a@bde_.cc"                                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3478 - assertIsTrue  "a+b@bde.cc"                                                                     =   0 =  OK 
   *  3479 - assertIsTrue  "a+b@c.com"                                                                      =   0 =  OK 
   *  3480 - assertIsTrue  "a-b@bde.cc"                                                                     =   0 =  OK 
   *  3481 - assertIsTrue  "a.a@test.com"                                                                   =   0 =  OK 
   *  3482 - assertIsTrue  "a.b@com"                                                                        =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3483 - assertIsTrue  "a/d@somedomain.com"                                                             =   0 =  OK 
   *  3484 - assertIsTrue  "a2@bde.cc"                                                                      =   0 =  OK 
   *  3485 - assertIsTrue  "a@123.45.67.89"                                                                 =   2 =  OK 
   *  3486 - assertIsTrue  "a@b.c.com"                                                                      =   0 =  OK 
   *  3487 - assertIsTrue  "a@b.com"                                                                        =   0 =  OK 
   *  3488 - assertIsTrue  "a@bc.com"                                                                       =   0 =  OK 
   *  3489 - assertIsTrue  "a@bcom"                                                                         =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3490 - assertIsTrue  "a@domain.com"                                                                   =   0 =  OK 
   *  3491 - assertIsTrue  "a__z@provider.com"                                                              =   0 =  OK 
   *  3492 - assertIsTrue  "a_z%@gmail.com"                                                                 =   0 =  OK 
   *  3493 - assertIsTrue  "aaron@theinfo.org"                                                              =   0 =  OK 
   *  3494 - assertIsTrue  "ab@288.120.150.10.com"                                                          =   0 =  OK 
   *  3495 - assertIsTrue  "ab@[120.254.254.120]"                                                           =   2 =  OK 
   *  3496 - assertIsTrue  "ab@b-de.cc"                                                                     =   0 =  OK 
   *  3497 - assertIsTrue  "ab@c.com"                                                                       =   0 =  OK 
   *  3498 - assertIsTrue  "ab_c@bde.cc"                                                                    =   0 =  OK 
   *  3499 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                                 =   1 =  OK 
   *  3500 - assertIsTrue  "abc.efg@gmail.com"                                                              =   0 =  OK 
   *  3501 - assertIsTrue  "abc.xyz@gmail.com.in"                                                           =   0 =  OK 
   *  3502 - assertIsTrue  "abc123xyz@asdf.co.in"                                                           =   0 =  OK 
   *  3503 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                           =   0 =  OK 
   *  3504 - assertIsTrue  "abc@abc.abc"                                                                    =   0 =  OK 
   *  3505 - assertIsTrue  "abc@abc.abcd"                                                                   =   0 =  OK 
   *  3506 - assertIsTrue  "abc@abc.abcde"                                                                  =   0 =  OK 
   *  3507 - assertIsTrue  "abc@abc.co.in"                                                                  =   0 =  OK 
   *  3508 - assertIsTrue  "abc@abc.com.com.com.com"                                                        =   0 =  OK 
   *  3509 - assertIsTrue  "abc@gmail.com.my"                                                               =   0 =  OK 
   *  3510 - assertIsTrue  "abc\@def@example.com"                                                           =   0 =  OK 
   *  3511 - assertIsTrue  "abc\\@example.com"                                                              =   0 =  OK 
   *  3512 - assertIsTrue  "abcxyz123@qwert.com"                                                            =   0 =  OK 
   *  3513 - assertIsFalse "ab@120.25.1111.120"                                                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  3514 - assertIsFalse "ab@120.256.256.120"                                                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3515 - assertIsFalse "ab@188.120.150.10]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3516 - assertIsFalse "ab@988.120.150.10"                                                              =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3517 - assertIsFalse "ab@[188.120.150.10"                                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  3518 - assertIsFalse "ab@[188.120.150.10].com"                                                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3519 - assertIsFalse "ab@188.120.150.10]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3520 - assertIsFalse "ab@b+de.cc"                                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3521 - assertIsFalse "ab@sd@dd"                                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3522 - assertIsFalse "abc.@somedomain.com"                                                            =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3523 - assertIsFalse "abc@def@example.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3524 - assertIsFalse "abc@gmail..com"                                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3525 - assertIsFalse "abc@gmail.com.."                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3526 - assertIsFalse "abc\"defghi\"xyz@example.com"                                                   =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3527 - assertIsFalse "abc\@example.com"                                                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3528 - assertIsFalse "abc\\"def\\"ghi@example.com"                                                    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3529 - assertIsFalse "abc\\@def@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3530 - assertIsFalse "as3d@dac.coas-"                                                                 =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3531 - assertIsFalse "asd@dasd@asd.cm"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3532 - assertIsFalse "check@this..com"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3533 - assertIsFalse "check@thiscom"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3534 - assertIsFalse "da23@das..com"                                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3535 - assertIsFalse "dad@sds"                                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3536 - assertIsFalse "dasddas-@.com"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3537 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3538 - assertIsFalse "dot.@example.com"                                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3539 - assertIsFalse "doug@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3540 - assertIsFalse "email( (nested) )@plus.com"                                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3541 - assertIsFalse "email(with @ in comment)plus.com"                                               =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3542 - assertIsFalse "email)mirror(@plus.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3543 - assertIsFalse "email..email@domain.com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3544 - assertIsFalse "email.@domain.com"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3545 - assertIsFalse "email.domain.com"                                                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3546 - assertIsFalse "email@#hash.com"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3547 - assertIsFalse "email@-domain.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3548 - assertIsFalse "email@.domain.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3549 - assertIsFalse "email@=qowaiv.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3550 - assertIsFalse "email@caret^xor.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3551 - assertIsFalse "email@colon:colon.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3552 - assertIsFalse "email@dollar$.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3553 - assertIsFalse "email@domain"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3554 - assertIsFalse "email@domain-.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3555 - assertIsFalse "email@domain..com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3556 - assertIsTrue  "email@domain.co.de"                                                             =   0 =  OK 
   *  3557 - assertIsTrue  "email@domain.com (joe Smith)"                                                   =   6 =  OK 
   *  3558 - assertIsTrue  "email@domain.com"                                                               =   0 =  OK 
   *  3559 - assertIsFalse "email@domain.com-"                                                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3560 - assertIsFalse "email@domain.com."                                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3561 - assertIsFalse "email@domain.com>"                                                              =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  3562 - assertIsTrue  "email@domain.domain.domain.com.com"                                             =   0 =  OK 
   *  3563 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                                      =   0 =  OK 
   *  3564 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                               =   0 =  OK 
   *  3565 - assertIsFalse "email@domain@domain.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3566 - assertIsFalse "email@example"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3567 - assertIsFalse "email@example..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3568 - assertIsTrue  "email@example.co.uk"                                                            =   0 =  OK 
   *  3569 - assertIsFalse "email@example.co.uk."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3570 - assertIsFalse "email@example.com "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3571 - assertIsTrue  "email@example.com"                                                              =   0 =  OK 
   *  3572 - assertIsFalse "email@exclamation!mark.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3573 - assertIsFalse "email@grave`accent.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3574 - assertIsTrue  "email@mail.gmail.com"                                                           =   0 =  OK 
   *  3575 - assertIsFalse "email@mailto:domain.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3576 - assertIsFalse "email@obelix*asterisk.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3577 - assertIsFalse "email@plus+.com"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3578 - assertIsFalse "email@plus.com (not closed comment"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  3579 - assertIsFalse "email@plus.com (not closed comment"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  3580 - assertIsFalse "email@pp|e.com"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3581 - assertIsFalse "email@p|pe.com"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3582 - assertIsFalse "email@question?mark.com"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3583 - assertIsFalse "email@r&amp;d.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3584 - assertIsFalse "email@rightbracket}.com"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3585 - assertIsTrue  "email@subdomain.domain.com"                                                     =   0 =  OK 
   *  3586 - assertIsFalse "email@wave~tilde.com"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3587 - assertIsFalse "email@{leftbracket.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3588 - assertIsFalse "mailto:mailto:email@domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3589 - assertIsFalse "f...bar@gmail.com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3590 - assertIsFalse "fa ke@false.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3591 - assertIsFalse "fake@-false.com"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3592 - assertIsFalse "fake@fal se.com"                                                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3593 - assertIsFalse "fdsa"                                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3594 - assertIsFalse "fdsa@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3595 - assertIsFalse "fdsa@fdsa"                                                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3596 - assertIsFalse "fdsa@fdsa."                                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3597 - assertIsFalse "foo.bar#gmail.co.u"                                                             =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3598 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3599 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3600 - assertIsFalse "foo~&(&)(@bar.com"                                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3601 - assertIsTrue  "foo\@bar@example.com"                                                           =   0 =  OK 
   *  3602 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3603 - assertIsFalse "get_at_m.e@gmail"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3604 - assertIsFalse "hallo2ww22@example....caaaao"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3605 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3606 - assertIsFalse "hello world@example.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3607 - assertIsFalse "invalid.email.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3608 - assertIsFalse "invalid@email@domain.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3609 - assertIsFalse "isis@100%.nl"                                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3610 - assertIsFalse "j..s@proseware.com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3611 - assertIsFalse "j.@server1.proseware.com"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3612 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3613 - assertIsFalse "journaldev"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3614 - assertIsFalse "journaldev()*@gmail.com"                                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3615 - assertIsFalse "journaldev..2002@gmail.com"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3616 - assertIsFalse "journaldev.@gmail.com"                                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3617 - assertIsFalse "journaldev123@.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3618 - assertIsFalse "journaldev123@.com.com"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3619 - assertIsFalse "journaldev123@gmail.a"                                                          =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3620 - assertIsFalse "journaldev@%*.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3621 - assertIsFalse "journaldev@.com.my"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3622 - assertIsFalse "journaldev@gmail.com.1a"                                                        =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3623 - assertIsFalse "journaldev@journaldev@gmail.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3624 - assertIsFalse "js@proseware..com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3625 - assertIsFalse "mailto:email@domain.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3626 - assertIsFalse "mailto:mailto:email@domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3627 - assertIsFalse "me..2002@gmail.com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3628 - assertIsFalse "me.@gmail.com"                                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3629 - assertIsFalse "me123@.com"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3630 - assertIsFalse "me123@.com.com"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3631 - assertIsFalse "me@%*.com"                                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3632 - assertIsFalse "me@.com.my"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3633 - assertIsFalse "me@gmail.com.1a"                                                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3634 - assertIsFalse "me@me@gmail.com"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3635 - assertIsFalse "myemail@@sample.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3636 - assertIsFalse "myemail@sa@mple.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3637 - assertIsFalse "myemailsample.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3638 - assertIsFalse "myname@mail*.com"                                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3639 - assertIsFalse "ote\"@example.com"                                                              =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3640 - assertIsFalse "pio_pio@#factory.com"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3641 - assertIsFalse "pio_pio@factory.c#om"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3642 - assertIsFalse "pio_pio@factory.c*om"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3643 - assertIsFalse "plain.address"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3644 - assertIsFalse "plainaddress"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3645 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3646 - assertIsFalse "this is not valid@email$com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3647 - assertIsFalse "this\ still\\"not\allowed@example.com"                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3648 - assertIsFalse "two..dot@example.com"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3649 - assertIsFalse "user#domain.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3650 - assertIsFalse "username@yahoo..com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3651 - assertIsFalse "username@yahoo.c"                                                               =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3652 - assertIsTrue  "username@domain.com"                                                            =   0 =  OK 
   *  3653 - assertIsTrue  "_username@domain.com"                                                           =   0 =  OK 
   *  3654 - assertIsTrue  "username_@domain.com"                                                           =   0 =  OK 
   *  3655 - assertIsFalse "xxx@u*.com"                                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3656 - assertIsFalse "xxxx..1234@yahoo.com"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3657 - assertIsFalse "xxxx.ourearth.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3658 - assertIsFalse "xxxx123@gmail.b"                                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3659 - assertIsFalse "xxxx@.com.my"                                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3660 - assertIsFalse "xxxx@.org.org"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3661 - assertIsFalse "xxxxx()*@gmail.com"                                                             =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3662 - assertIsFalse "{something}@{something}.{something}"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3663 - assertIsTrue  "!def!xyz%abc@example.com"                                                       =   0 =  OK 
   *  3664 - assertIsTrue  "!sd@gh.com"                                                                     =   0 =  OK 
   *  3665 - assertIsTrue  "$A12345@example.com"                                                            =   0 =  OK 
   *  3666 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                          =   0 =  OK 
   *  3667 - assertIsTrue  "%2@gmail.com"                                                                   =   0 =  OK 
   *  3668 - assertIsTrue  "--@ooo.ooo"                                                                     =   0 =  OK 
   *  3669 - assertIsTrue  "-@bde.cc"                                                                       =   0 =  OK 
   *  3670 - assertIsTrue  "-asd@das.com"                                                                   =   0 =  OK 
   *  3671 - assertIsTrue  "1234567890@domain.com"                                                          =   0 =  OK 
   *  3672 - assertIsTrue  "1@domain.com"                                                                   =   0 =  OK 
   *  3673 - assertIsTrue  "1@gmail.com"                                                                    =   0 =  OK 
   *  3674 - assertIsTrue  "1_example@something.gmail.com"                                                  =   0 =  OK 
   *  3675 - assertIsTrue  "2@bde.cc"                                                                       =   0 =  OK 
   *  3676 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                                         =   0 =  OK 
   *  3677 - assertIsTrue  "<boss@nil.test>"                                                                =   0 =  OK 
   *  3678 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                                      =   0 =  OK 
   *  3679 - assertIsTrue  "Abc.123@example.com"                                                            =   0 =  OK 
   *  3680 - assertIsTrue  "Abc@10.42.0.1"                                                                  =   2 =  OK 
   *  3681 - assertIsTrue  "Abc@example.com"                                                                =   0 =  OK 
   *  3682 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                           =   0 =  OK 
   *  3683 - assertIsTrue  "mymail\@hello@hotmail.com"                                                      =   0 =  OK 
   *  3684 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                                    =   0 =  OK 
   *  3685 - assertIsTrue  "This is <john@127.0.0.1>"                                                       =   2 =  OK 
   *  3686 - assertIsTrue  "This is <john@[127.0.0.1]>"                                                     =   2 =  OK 
   *  3687 - assertIsTrue  "Who? <one@y.test>"                                                              =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
   *  3688 - assertIsTrue  "\"%2\"@gmail.com"                                                               =   1 =  OK 
   *  3689 - assertIsTrue  "\"Abc@def\"@example.com"                                                        =   1 =  OK 
   *  3690 - assertIsTrue  "\"Abc\@def\"@example.com"                                                       =   1 =  OK 
   *  3691 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                              =   1 =  OK 
   *  3692 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                                    =   1 =  OK 
   *  3693 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                                   =   1 =  OK 
   *  3694 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                             =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
   *  3695 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                                      =   1 =  OK 
   *  3696 - assertIsTrue  "\"a..b\"@gmail.com"                                                             =   1 =  OK 
   *  3697 - assertIsTrue  "\"a@b\"@example.com"                                                            =   1 =  OK 
   *  3698 - assertIsTrue  "\"a_b\"@gmail.com"                                                              =   1 =  OK 
   *  3699 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                                   =   1 =  OK 
   *  3700 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                            =   1 =  OK 
   *  3701 - assertIsTrue  "\"foo\@bar\"@Something.com"                                                     =   1 =  OK 
   *  3702 - assertIsTrue  "\"j\\"s\"@proseware.com"                                                        =   1 =  OK 
   *  3703 - assertIsTrue  "\"myemail@sa\"@mple.com"                                                        =   1 =  OK 
   *  3704 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3705 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3706 - assertIsFalse "\"Joe\Blow\"@example.com"                                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3707 - assertIsTrue  "\"this is v@lid!\"@example.com"                                                 =   1 =  OK 
   *  3708 - assertIsTrue  "_-_@bde.cc"                                                                     =   0 =  OK 
   *  3709 - assertIsTrue  "_@gmail.com"                                                                    =   0 =  OK 
   *  3710 - assertIsTrue  "_dasd@sd.com"                                                                   =   0 =  OK 
   *  3711 - assertIsTrue  "_dasd_das_@9.com"                                                               =   0 =  OK 
   *  3712 - assertIsTrue  "_somename@example.com"                                                          =   0 =  OK 
   *  3713 - assertIsTrue  "a&d@somedomain.com"                                                             =   0 =  OK 
   *  3714 - assertIsTrue  "a*d@somedomain.com"                                                             =   0 =  OK 
   *  3715 - assertIsTrue  "alex@example.com"                                                               =   0 =  OK 
   *  3716 - assertIsTrue  "alireza@test.co.uk"                                                             =   0 =  OK 
   *  3717 - assertIsTrue  "asd-@asd.com"                                                                   =   0 =  OK 
   *  3718 - assertIsTrue  "begeddov@jfinity.com"                                                           =   0 =  OK 
   *  3719 - assertIsTrue  "check@this.com"                                                                 =   0 =  OK 
   *  3720 - assertIsTrue  "cog@wheel.com"                                                                  =   0 =  OK 
   *  3721 - assertIsTrue  "customer/department=shipping@example.com"                                       =   0 =  OK 
   *  3722 - assertIsTrue  "d._.___d@gmail.com"                                                             =   0 =  OK 
   *  3723 - assertIsTrue  "d.j@server1.proseware.com"                                                      =   0 =  OK 
   *  3724 - assertIsTrue  "d.oy.smith@gmail.com"                                                           =   0 =  OK 
   *  3725 - assertIsTrue  "d23d@da9.co9"                                                                   =   0 =  OK 
   *  3726 - assertIsTrue  "d_oy_smith@gmail.com"                                                           =   0 =  OK 
   *  3727 - assertIsTrue  "dasd-dasd@das.com.das"                                                          =   0 =  OK 
   *  3728 - assertIsTrue  "dasd.dadas@dasd.com"                                                            =   0 =  OK 
   *  3729 - assertIsTrue  "dasd_-@jdas.com"                                                                =   0 =  OK 
   *  3730 - assertIsTrue  "david.jones@proseware.com"                                                      =   0 =  OK 
   *  3731 - assertIsTrue  "dda_das@das-dasd.com"                                                           =   0 =  OK 
   *  3732 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                                   =   0 =  OK 
   *  3733 - assertIsTrue  "digit-only-domain@123.com"                                                      =   0 =  OK 
   *  3734 - assertIsTrue  "doysmith@gmail.com"                                                             =   0 =  OK 
   *  3735 - assertIsTrue  "drp@drp.cz"                                                                     =   0 =  OK 
   *  3736 - assertIsTrue  "dsq!a?@das.com"                                                                 =   0 =  OK 
   *  3737 - assertIsTrue  "example@example.co"                                                             =   0 =  OK 
   *  3738 - assertIsTrue  "f.f.f@bde.cc"                                                                   =   0 =  OK 
   *  3739 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                          =   0 =  OK 
   *  3740 - assertIsTrue  "first-name-last-name@d-a-n.com"                                                 =   0 =  OK 
   *  3741 - assertIsTrue  "firstname+lastname@domain.com"                                                  =   0 =  OK 
   *  3742 - assertIsTrue  "firstname-lastname@domain.com"                                                  =   0 =  OK 
   *  3743 - assertIsTrue  "firstname.lastname@domain.com"                                                  =   0 =  OK 
   *  3744 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                                      =   0 =  OK 
   *  3745 - assertIsTrue  "futureTLD@somewhere.fooo"                                                       =   0 =  OK 
   *  3746 - assertIsTrue  "hello.me_1@email.com"                                                           =   0 =  OK 
   *  3747 - assertIsTrue  "hello7___@ca.com.pt"                                                            =   0 =  OK 
   *  3748 - assertIsTrue  "info@ermaelan.com"                                                              =   0 =  OK 
   *  3749 - assertIsTrue  "j.s@server1.proseware.com"                                                      =   0 =  OK 
   *  3750 - assertIsTrue  "j@proseware.com9"                                                               =   0 =  OK 
   *  3751 - assertIsTrue  "j_9@[129.126.118.1]"                                                            =   2 =  OK 
   *  3752 - assertIsTrue  "jinujawad6s@gmail.com"                                                          =   0 =  OK 
   *  3753 - assertIsTrue  "journaldev+100@gmail.com"                                                       =   0 =  OK 
   *  3754 - assertIsTrue  "journaldev-100@journaldev.net"                                                  =   0 =  OK 
   *  3755 - assertIsTrue  "journaldev-100@yahoo-test.com"                                                  =   0 =  OK 
   *  3756 - assertIsTrue  "journaldev-100@yahoo.com"                                                       =   0 =  OK 
   *  3757 - assertIsTrue  "journaldev.100@journaldev.com.au"                                               =   0 =  OK 
   *  3758 - assertIsTrue  "journaldev.100@yahoo.com"                                                       =   0 =  OK 
   *  3759 - assertIsTrue  "journaldev111@journaldev.com"                                                   =   0 =  OK 
   *  3760 - assertIsTrue  "journaldev@1.com"                                                               =   0 =  OK 
   *  3761 - assertIsTrue  "journaldev@gmail.com.com"                                                       =   0 =  OK 
   *  3762 - assertIsTrue  "journaldev@yahoo.com"                                                           =   0 =  OK 
   *  3763 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                              =   0 =  OK 
   *  3764 - assertIsTrue  "js#internal@proseware.com"                                                      =   0 =  OK 
   *  3765 - assertIsTrue  "js*@proseware.com"                                                              =   0 =  OK 
   *  3766 - assertIsTrue  "js@proseware.com9"                                                              =   0 =  OK 
   *  3767 - assertIsTrue  "me+100@me.com"                                                                  =   0 =  OK 
   *  3768 - assertIsTrue  "me+alpha@example.com"                                                           =   0 =  OK 
   *  3769 - assertIsTrue  "me-100@me.com"                                                                  =   0 =  OK 
   *  3770 - assertIsTrue  "me-100@me.com.au"                                                               =   0 =  OK 
   *  3771 - assertIsTrue  "me-100@yahoo-test.com"                                                          =   0 =  OK 
   *  3772 - assertIsTrue  "me.100@me.com"                                                                  =   0 =  OK 
   *  3773 - assertIsTrue  "me@aaronsw.com"                                                                 =   0 =  OK 
   *  3774 - assertIsTrue  "me@company.co.uk"                                                               =   0 =  OK 
   *  3775 - assertIsTrue  "me@gmail.com"                                                                   =   0 =  OK 
   *  3776 - assertIsTrue  "me@gmail.com"                                                                   =   0 =  OK 
   *  3777 - assertIsTrue  "me@mail.s2.example.com"                                                         =   0 =  OK 
   *  3778 - assertIsTrue  "me@me.cu.uk"                                                                    =   0 =  OK 
   *  3779 - assertIsTrue  "my.ownsite@ourearth.org"                                                        =   0 =  OK 
   *  3780 - assertIsFalse "myemail@sample"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3781 - assertIsTrue  "myemail@sample.com"                                                             =   0 =  OK 
   *  3782 - assertIsTrue  "mysite@you.me.net"                                                              =   0 =  OK 
   *  3783 - assertIsTrue  "o'hare@example.com"                                                             =   0 =  OK 
   *  3784 - assertIsTrue  "peter.example@domain.comau"                                                     =   0 =  OK 
   *  3785 - assertIsTrue  "peter.piper@example.com"                                                        =   0 =  OK 
   *  3786 - assertIsTrue  "peter_123@news.com"                                                             =   0 =  OK 
   *  3787 - assertIsTrue  "pio^_pio@factory.com"                                                           =   0 =  OK 
   *  3788 - assertIsTrue  "pio_#pio@factory.com"                                                           =   0 =  OK 
   *  3789 - assertIsTrue  "pio_pio@factory.com"                                                            =   0 =  OK 
   *  3790 - assertIsTrue  "pio_~pio@factory.com"                                                           =   0 =  OK 
   *  3791 - assertIsTrue  "piskvor@example.lighting"                                                       =   0 =  OK 
   *  3792 - assertIsTrue  "rss-dev@yahoogroups.com"                                                        =   0 =  OK 
   *  3793 - assertIsTrue  "someone+tag@somewhere.net"                                                      =   0 =  OK 
   *  3794 - assertIsTrue  "someone@somewhere.co.uk"                                                        =   0 =  OK 
   *  3795 - assertIsTrue  "someone@somewhere.com"                                                          =   0 =  OK 
   *  3796 - assertIsTrue  "something_valid@somewhere.tld"                                                  =   0 =  OK 
   *  3797 - assertIsTrue  "tvf@tvf.cz"                                                                     =   0 =  OK 
   *  3798 - assertIsTrue  "user#@domain.co.in"                                                             =   0 =  OK 
   *  3799 - assertIsTrue  "user'name@domain.co.in"                                                         =   0 =  OK 
   *  3800 - assertIsTrue  "user+mailbox@example.com"                                                       =   0 =  OK 
   *  3801 - assertIsTrue  "user-name@domain.co.in"                                                         =   0 =  OK 
   *  3802 - assertIsTrue  "user.name@domain.com"                                                           =   0 =  OK 
   *  3803 - assertIsFalse ".user.name@domain.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3804 - assertIsFalse "user-name@domain.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3805 - assertIsFalse "username@.com"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3806 - assertIsTrue  "user1@domain.com"                                                               =   0 =  OK 
   *  3807 - assertIsTrue  "user?name@domain.co.in"                                                         =   0 =  OK 
   *  3808 - assertIsTrue  "user@domain.co.in"                                                              =   0 =  OK 
   *  3809 - assertIsTrue  "user@domain.com"                                                                =   0 =  OK 
   *  3810 - assertIsFalse "user@domaincom"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3811 - assertIsTrue  "user_name@domain.co.in"                                                         =   0 =  OK 
   *  3812 - assertIsTrue  "user_name@domain.com"                                                           =   0 =  OK 
   *  3813 - assertIsTrue  "username@yahoo.corporate"                                                       =   0 =  OK 
   *  3814 - assertIsTrue  "username@yahoo.corporate.in"                                                    =   0 =  OK 
   *  3815 - assertIsTrue  "username+something@domain.com"                                                  =   0 =  OK 
   *  3816 - assertIsTrue  "vdv@dyomedea.com"                                                               =   0 =  OK 
   *  3817 - assertIsTrue  "xxxx@gmail.com"                                                                 =   0 =  OK 
   *  3818 - assertIsTrue  "xxxxxx@yahoo.com"                                                               =   0 =  OK 
   *  3819 - assertIsFalse "first;name)lastname@domain.com(blah"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3820 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                                        =   4 =  OK 
   *  3821 - assertIsFalse "user@localserver"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3822 - assertIsTrue  "w.b.f@test.com"                                                                 =   0 =  OK 
   *  3823 - assertIsTrue  "w.b.f@test.museum"                                                              =   0 =  OK 
   *  3824 - assertIsTrue  "yoursite@ourearth.com"                                                          =   0 =  OK 
   *  3825 - assertIsTrue  "~pio_pio@factory.com"                                                           =   0 =  OK 
   *  3826 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                       =   0 =  OK 
   *  3827 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  3828 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3829 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
   *  3830 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
   *  3831 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net"   =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  3832 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"                 =   0 =  OK 
   *  3833 - assertIsTrue  "valid@[1.1.1.1]"                                                                =   2 =  OK 
   *  3834 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"               =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3835 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                                         =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3836 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                                =   4 =  OK 
   *  3837 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"                 =   4 =  OK 
   *  3838 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                                      =   4 =  OK 
   *  3839 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"                         =   4 =  OK 
   *  3840 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                           =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3841 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                                    =   4 =  OK 
   *  3842 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                                    =   4 =  OK 
   *  3843 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                              =   2 =  OK 
   *  3844 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                                  =   2 =  OK 
   *  3845 - assertIsFalse "\"invalid-qstring@example.com"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3846 - assertIsFalse "\"locál-part\"@example.com"                                                     =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  3847 - assertIsFalse "invalid @"                                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3848 - assertIsFalse "invalid"                                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3849 - assertIsFalse "invalid@"                                                                       =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *  3850 - assertIsFalse "invalid@[10.1.52]"                                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3851 - assertIsFalse "invalid@[10.1]"                                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3852 - assertIsFalse "invalid@[10]"                                                                   =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3853 - assertIsFalse "invalid@[111.111.111.111"                                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  3854 - assertIsFalse "invalid@[127.0.0.1.]"                                                           =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  3855 - assertIsFalse "invalid@[127.0.0.1]."                                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3856 - assertIsFalse "invalid@[127.0.0.1]x"                                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3857 - assertIsFalse "invalid@[256.256.256.256]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3858 - assertIsFalse "invalid@[555.666.777.888]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3859 - assertIsFalse "invalid@[IPv6:1111:1111]"                                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3860 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3861 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3862 - assertIsFalse "invalid@[IPv6:123456]"                                                          =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  3863 - assertIsFalse "invalid@[IPv6:1::2:]"                                                           =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3864 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  3865 - assertIsFalse "invalid@[IPv6::1::1]"                                                           =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3866 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3867 - assertIsFalse "invalid@[]"                                                                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3868 - assertIsFalse "invalid@domain1.com@domain2.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   * 
   * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs --------------------------------------------------
   * 
   *  3869 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  3870 - assertIsTrue  "\"back\slash\"@sld.com"                                                         =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  3871 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                                    =   1 =  OK 
   *  3872 - assertIsTrue  "\"quoted\"@sld.com"                                                             =   1 =  OK 
   *  3873 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                             =   1 =  OK 
   *  3874 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                             =   0 =  OK 
   *  3875 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                            =   0 =  OK 
   *  3876 - assertIsTrue  "01234567890@numbers-in-local.net"                                               =   0 =  OK 
   *  3877 - assertIsTrue  "a@single-character-in-local.org"                                                =   0 =  OK 
   *  3878 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org"      =   0 =  OK 
   *  3879 - assertIsTrue  "backticksarelegit@test.com"                                                     =   0 =  OK 
   *  3880 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                                     =   2 =  OK 
   *  3881 - assertIsTrue  "country-code-tld@sld.rw"                                                        =   0 =  OK 
   *  3882 - assertIsTrue  "country-code-tld@sld.uk"                                                        =   0 =  OK 
   *  3883 - assertIsTrue  "letters-in-sld@123.com"                                                         =   0 =  OK 
   *  3884 - assertIsTrue  "local@dash-in-sld.com"                                                          =   0 =  OK 
   *  3885 - assertIsTrue  "local@sld.newTLD"                                                               =   0 =  OK 
   *  3886 - assertIsTrue  "local@sub.domains.com"                                                          =   0 =  OK 
   *  3887 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                               =   0 =  OK 
   *  3888 - assertIsTrue  "one-character-third-level@a.example.com"                                        =   0 =  OK 
   *  3889 - assertIsTrue  "one-letter-sld@x.org"                                                           =   0 =  OK 
   *  3890 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                                       =   0 =  OK 
   *  3891 - assertIsTrue  "single-character-in-sld@x.org"                                                  =   0 =  OK 
   *  3892 - assertIsTrue  "uncommon-tld@sld.mobi"                                                          =   0 =  OK 
   *  3893 - assertIsTrue  "uncommon-tld@sld.museum"                                                        =   0 =  OK 
   *  3894 - assertIsTrue  "uncommon-tld@sld.travel"                                                        =   0 =  OK 
   *  3895 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3896 - assertIsFalse "@missing-local.org"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3897 - assertIsFalse "IP-and-port@127.0.0.1:25"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3898 - assertIsFalse "another-invalid-ip@127.0.0.256"                                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3899 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3900 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3901 - assertIsFalse "invalid-ip@127.0.0.1.26"                                                        =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3902 - assertIsFalse "local-ends-with-dot.@sld.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3903 - assertIsFalse "missing-at-sign.net"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3904 - assertIsFalse "missing-sld@.com"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3905 - assertIsFalse "missing-tld@sld."                                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3906 - assertIsFalse "sld-ends-with-dash@sld-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3907 - assertIsFalse "sld-starts-with-dash@-sld.com"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3908 - assertIsFalse "two..consecutive-dots@sld.com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3909 - assertIsTrue  "unbracketed-IP@127.0.0.1"                                                       =   2 =  OK 
   *  3910 - assertIsFalse "underscore.error@example.com_"                                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   * 
   * ---- https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/ -------------------------------
   * 
   *  3911 - assertIsTrue  "hello@example.com"                                                              =   0 =  OK 
   *  3912 - assertIsTrue  "hello@example.co.uk"                                                            =   0 =  OK 
   *  3913 - assertIsTrue  "hello-2020@example.com"                                                         =   0 =  OK 
   *  3914 - assertIsTrue  "hello.2020@example.com"                                                         =   0 =  OK 
   *  3915 - assertIsTrue  "hello_2020@example.com"                                                         =   0 =  OK 
   *  3916 - assertIsTrue  "h@example.com"                                                                  =   0 =  OK 
   *  3917 - assertIsTrue  "h@example-example.com"                                                          =   0 =  OK 
   *  3918 - assertIsTrue  "h@example-example-example.com"                                                  =   0 =  OK 
   *  3919 - assertIsTrue  "h@example.example-example.com"                                                  =   0 =  OK 
   *  3920 - assertIsTrue  "hello.world-2020@example.com"                                                   =   0 =  OK 
   *  3921 - assertIsTrue  "hello@example_example.com"                                                      =   0 =  OK 
   *  3922 - assertIsTrue  "hello!+2020@example.com"                                                        =   0 =  OK 
   *  3923 - assertIsFalse "hello"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3924 - assertIsFalse "hello@2020@example.com"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3925 - assertIsTrue  "hello\@2020@example.com"                                                        =   0 =  OK 
   *  3926 - assertIsTrue  "(comment @) hello\@2020@example.com"                                            =   6 =  OK 
   *  3927 - assertIsFalse ".hello@example.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3928 - assertIsFalse "hello.@example.com"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3929 - assertIsFalse "hello..world@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3930 - assertIsTrue  "hello.world.\"string ..\"@example.com"                                          =   1 =  OK 
   *  3931 - assertIsTrue  "(comment ..) hello.world.\"string ..\"@example.com"                             =   7 =  OK 
   *  3932 - assertIsTrue  "hello.world.\"string ..\"@example.com (comment ..)"                             =   7 =  OK 
   *  3933 - assertIsFalse "hello@example.a"                                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3934 - assertIsFalse "hello@example..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3935 - assertIsFalse "hello@.com"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3936 - assertIsFalse "hello@.com."                                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3937 - assertIsFalse "hello@-example.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3938 - assertIsFalse "hello@example.com-"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3939 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234xx@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  3940 - assertIsTrue  "email@goes_here.com"                                                            =   0 =  OK 
   *  3941 - assertIsTrue  "double--dash@example.com"                                                       =   0 =  OK 
   * 
   * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------
   * 
   *  3942 - assertIsFalse ""                                                                               =  11 =  OK    Laenge: Eingabe ist Leerstring
   *  3943 - assertIsFalse " "                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3944 - assertIsFalse " jkt@gmail.com"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3945 - assertIsFalse "jkt@gmail.com "                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3946 - assertIsFalse "jkt@ gmail.com"                                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3947 - assertIsFalse "jkt@g mail.com"                                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3948 - assertIsFalse "jkt @gmail.com"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3949 - assertIsFalse "j kt@gmail.com"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * ---- https://www.abstractapi.com/guides/java-email-validation --------------------------------------------------------------------
   * 
   *  3950 - assertIsTrue  "\"test123\"@gmail.com"                                                          =   1 =  OK 
   *  3951 - assertIsTrue  "test123@gmail.comcomco"                                                         =   0 =  OK 
   *  3952 - assertIsTrue  "test123@gmail.c"                                                                =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3953 - assertIsTrue  "test1&23@gmail.com"                                                             =   0 =  OK 
   *  3954 - assertIsTrue  "test123@gmail.com"                                                              =   0 =  OK 
   *  3955 - assertIsFalse "test123@gmail..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3956 - assertIsFalse ".test123@gmail.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3957 - assertIsFalse "test123@gmail.com."                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3958 - assertIsFalse "test1Ὠ23@gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * ---- https://www.javatpoint.com/java-email-validation ----------------------------------------------------------------------------
   * 
   *  3959 - assertIsTrue  "javaTpoint@domain.com"                                                          =   0 =  OK 
   *  3960 - assertIsTrue  "javaTpoint@domain.co.in"                                                        =   0 =  OK 
   *  3961 - assertIsTrue  "javaTpoint.name@domain.com"                                                     =   0 =  OK 
   *  3962 - assertIsTrue  "javaTpoint#@domain.co.in"                                                       =   0 =  OK 
   *  3963 - assertIsTrue  "java'Tpoint@domain.com"                                                         =   0 =  OK 
   *  3964 - assertIsTrue  "1avaTpoint#@domain.co.in"                                                       =   0 =  OK 
   *  3965 - assertIsTrue  "12453@domain.com"                                                               =   0 =  OK 
   *  3966 - assertIsFalse "javaTpoint@domaincom"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3967 - assertIsFalse "javaTpoint@domain.com."                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3968 - assertIsFalse "javaTpoint@domain..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3969 - assertIsFalse "javaTpoint#domain.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3970 - assertIsFalse ".javaTpoint@yahoo.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3971 - assertIsFalse "@yahoo.com"                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3972 - assertIsFalse "javaTpoint#domain.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3973 - assertIsFalse "12javaTpoint#domain.com"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   * 
   * ---- https://stackoverflow.com/questions/2035783/email-validation-converting-a-regular-expression-written-in-php-preg-to-net -----
   * 
   *  3974 - assertIsTrue  "l3tt3rsAndNumb3rs@domain.com"                                                   =   0 =  OK 
   *  3975 - assertIsTrue  "has-dash@domain.com"                                                            =   0 =  OK 
   *  3976 - assertIsTrue  "hasApostrophe.o'leary@domain.org"                                               =   0 =  OK 
   *  3977 - assertIsTrue  "uncommonTLD@domain.museum"                                                      =   0 =  OK 
   *  3978 - assertIsTrue  "uncommonTLD@domain.travel"                                                      =   0 =  OK 
   *  3979 - assertIsTrue  "uncommonTLD@domain.mobi"                                                        =   0 =  OK 
   *  3980 - assertIsTrue  "countryCodeTLD@domain.uk"                                                       =   0 =  OK 
   *  3981 - assertIsTrue  "countryCodeTLD@domain.rw"                                                       =   0 =  OK 
   *  3982 - assertIsTrue  "lettersInDomain@911.com"                                                        =   0 =  OK 
   *  3983 - assertIsTrue  "underscore_inLocal@domain.net"                                                  =   0 =  OK 
   *  3984 - assertIsTrue  "IPInsteadOfDomain@127.0.0.1"                                                    =   2 =  OK 
   *  3985 - assertIsFalse "IPAndPort@127.0.0.1:25"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3986 - assertIsTrue  "subdomain@sub.domain.com"                                                       =   0 =  OK 
   *  3987 - assertIsTrue  "local@dash-inDomain.com"                                                        =   0 =  OK 
   *  3988 - assertIsTrue  "dot.inLocal@foo.com"                                                            =   0 =  OK 
   *  3989 - assertIsTrue  "a@singleLetterLocal.org"                                                        =   0 =  OK 
   *  3990 - assertIsTrue  "singleLetterDomain@x.org"                                                       =   0 =  OK 
   *  3991 - assertIsTrue  "&*=?^+{}'~@validCharsInLocal.net"                                               =   0 =  OK 
   *  3992 - assertIsFalse "missingDomain@.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3993 - assertIsFalse "@missingLocal.org"                                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3994 - assertIsFalse "missingatSign.net"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3995 - assertIsFalse "local@SecondLevelDomainNamesAreInvalidIfTheyAreLongerThan64Charactersss.org"    =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  3996 - assertIsTrue  "TLDDoesntExist@domain.moc"                                                      =   0 =  OK 
   *  3997 - assertIsTrue  "numbersInTLD@domain.c0m"                                                        =   0 =  OK 
   * 
   * ---- https://java2blog.com/validate-email-address-in-java/ -----------------------------------------------------------------------
   * 
   *  3998 - assertIsTrue  "admin@java2blog.com"                                                            =   0 =  OK 
   *  3999 - assertIsFalse "@java2blog.com"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4000 - assertIsTrue  "arpit.mandliya@java2blog.com"                                                   =   0 =  OK 
   * 
   * ---- https://www.tutorialspoint.com/javaexamples/regular_email.htm ---------------------------------------------------------------
   * 
   *  4001 - assertIsTrue  "sairamkrishna@tutorialspoint.com"                                               =   0 =  OK 
   *  4002 - assertIsTrue  "kittuprasad700@gmail.com"                                                       =   0 =  OK 
   *  4003 - assertIsFalse "sairamkrishna_mammahe%google-india.com"                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  4004 - assertIsTrue  "sairam.krishna@gmail-indai.com"                                                 =   0 =  OK 
   *  4005 - assertIsTrue  "sai#@youtube.co.in"                                                             =   0 =  OK 
   *  4006 - assertIsFalse "kittu@domaincom"                                                                =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4007 - assertIsFalse "kittu#gmail.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  4008 - assertIsFalse "@pindom.com"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   * 
   * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ---------------------------
   * 
   *  4009 - assertIsTrue  "me@example.com"                                                                 =   0 =  OK 
   *  4010 - assertIsTrue  "a.nonymous@example.com"                                                         =   0 =  OK 
   *  4011 - assertIsTrue  "name+tag@example.com"                                                           =   0 =  OK 
   *  4012 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                                 =   2 =  OK 
   *  4013 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"              =   4 =  OK 
   *  4014 - assertIsTrue  "me(this is a comment)@example.com"                                              =   6 =  OK 
   *  4015 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                                      =   1 =  OK 
   *  4016 - assertIsTrue  "\"Bob\" <bob@hi.com>"                                                           =   0 =  OK 
   *  4017 - assertIsTrue  "me.example@com"                                                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4018 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                                =   0 =  OK 
   *  4019 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"            =   0 =  OK 
   *  4020 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                               =   0 =  OK 
   *  4021 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                              =   0 =  OK 
   *  4022 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                             =   0 =  OK 
   *  4023 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                            =   0 =  OK 
   *  4024 - assertIsTrue  "rekry@fi.xyz.dom"                                                               =   0 =  OK 
   *  4025 - assertIsFalse "NotAnEmail"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4026 - assertIsFalse "me@"                                                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  4027 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4028 - assertIsFalse ".me@example.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  4029 - assertIsFalse "me@example..com"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  4030 - assertIsFalse "me\@example.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  4031 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  4032 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                                   =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  4033 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  4034 - assertIsFalse "semico...@gmail.com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   * 
   * ---- my old tests ----------------------------------------------------------------------------------------------------------------
   * 
   *  4035 - assertIsTrue  "A.\"B\"@C.DE"                                                                   =   1 =  OK 
   *  4036 - assertIsTrue  "A.B@[1.2.3.4]"                                                                  =   2 =  OK 
   *  4037 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                              =   3 =  OK 
   *  4038 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                     =   4 =  OK 
   *  4039 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                 =   5 =  OK 
   *  4040 - assertIsTrue  "(A)B@C.DE"                                                                      =   6 =  OK 
   *  4041 - assertIsTrue  "A(B)@C.DE"                                                                      =   6 =  OK 
   *  4042 - assertIsTrue  "(A)\"B\"@C.DE"                                                                  =   7 =  OK 
   *  4043 - assertIsTrue  "\"A\"(B)@C.DE"                                                                  =   7 =  OK 
   *  4044 - assertIsTrue  "(A)B@[1.2.3.4]"                                                                 =   2 =  OK 
   *  4045 - assertIsTrue  "A(B)@[1.2.3.4]"                                                                 =   2 =  OK 
   *  4046 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                             =   8 =  OK 
   *  4047 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                             =   8 =  OK 
   *  4048 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                    =   4 =  OK 
   *  4049 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                    =   4 =  OK 
   *  4050 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                =   9 =  OK 
   *  4051 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                =   9 =  OK 
   *  4052 - assertIsTrue  "a.b.c.d@domain.com"                                                             =   0 =  OK 
   *  4053 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4054 - assertIsFalse "ABC.DEF.GHI.JKL"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  4055 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  4056 - assertIsFalse "ABC.DEF @GHI.JKL"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4057 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4058 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  4059 - assertIsFalse "ABC.DEF@"                                                                       =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *  4060 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4061 - assertIsFalse "ABC@DEF@GHI.JKL"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4062 - assertIsFalse "@%^%#$@#$@#.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4063 - assertIsFalse "first@last@test.org"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4064 - assertIsFalse "@test@a.com"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4065 - assertIsFalse "@\"someStringThatMightBe@email.com"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4066 - assertIsFalse "test@@test.com"                                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4067 - assertIsFalse "ABCDEF@GHIJKL"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4068 - assertIsFalse "ABC.DEF@GHIJKL"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4069 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  4070 - assertIsFalse "ABC.DEF@GHI.JKL."                                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  4071 - assertIsFalse "ABC..DEF@GHI.JKL"                                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  4072 - assertIsFalse "ABC.DEF@GHI..JKL"                                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  4073 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  4074 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  4075 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  4076 - assertIsFalse "ABC.DEF@."                                                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  4077 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                           =   1 =  OK 
   *  4078 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                            =   0 =  OK 
   *  4079 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                               =   0 =  OK 
   *  4080 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                               =   0 =  OK 
   *  4081 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                                =   0 =  OK 
   *  4082 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                                =   0 =  OK 
   *  4083 - assertIsFalse "ABC.DEF@GHI.2KL"                                                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  4084 - assertIsFalse "ABC.DEF@GHI.JK-"                                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  4085 - assertIsFalse "ABC.DEF@GHI.JK_"                                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  4086 - assertIsFalse "ABC.DEF@-HI.JKL"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  4087 - assertIsFalse "ABC.DEF@_HI.JKL"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  4088 - assertIsFalse "ABC DEF@GHI.DE"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4089 - assertIsFalse "ABC.DEF@GHI DE"                                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  4090 - assertIsFalse "A . B & C . D"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4091 - assertIsFalse " A . B & C . D"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4092 - assertIsFalse "(?).[!]@{&}.<:>"                                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  4093 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4094 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
   * 
   * ---- unsupported -----------------------------------------------------------------------------------------------------------------
   * 
   *  4095 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                                         =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4096 - assertIsTrue  "Ärger.Öde@Übel.de"                                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4097 - assertIsTrue  "Smørrebrød@danmark.dk"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4098 - assertIsTrue  "ip6.without.brackets@1:2:3:4:5:6:7:8"                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4099 - assertIsTrue  "email.address.without@topleveldomain"                                           =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4100 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4101 - assertIsFalse "@1st.relay,@2nd.relay:user@final.domain"                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   * 
   * ---- Fillup ----------------------------------------------------------------------------------------------------------------------
   * 
   * Fillup ist nicht aktiv
   * 
   * 
   * ---- Statistik -------------------------------------------------------------------------------------------------------------------
   * 
   *   ASSERT_IS_TRUE  1420   KORREKT 1334 =   93.944 % | FALSCH ERKANNT   86 =    6.056 % = Error 0
   *   ASSERT_IS_FALSE 2681   KORREKT 2663 =   99.329 % | FALSCH ERKANNT   18 =    0.671 % = Error 0
   * 
   *   GESAMT          4101   KORREKT 3997 =   97.464 % | FALSCH ERKANNT  104 =    2.536 % = Error 0
   * 
   * 
   *   Millisekunden    227 = 0.05535235308461351
   * 
   *   Datum 03.11.2023 12:39:37:720
   *  
   * </pre> 
   */

  public static void main( String[] args )
  {
    /*
     * Variable fuer die Startzeit der Funktion deklarieren und die aktuellen
     * System-Millisekunden speichern
     */
    long time_stamp_start = System.currentTimeMillis();

    // generateTestCases();

    try
    {
      wl( "" );
      wl( "Datum " + getAktDatumUndUhrzeitMs() );
      wl( "" );

      wlHeadline( "To be Fixed" );

      assertIsTrue( "\"With extra < within quotes\" Display Name<email@domain.com>" );
      assertIsTrue( "\"<script>alert('XSS')</script>\"@example.com " );

      /*
       * https://thedailywtf.com/articles/comments/Validating_Email_Addresses
       */
      assertIsFalse( "\"Moose Brains !!!\" @ (yes, this is my address) spam.la <MooseBrains>" );

      assertIsFalse( "double.dash.in.domain@double--dash.com" );

      runTestCorrect();

      wlHeadline( "No Input" );

      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "        " );

      runTestAtSign();

      runTestSeperator();

      runTestCharacters();

      runTestIP4();

      runTestIP6();

      runTestStrings();

      runTestComments();

      runTestDisplayName();

      runTestLength();

      runTestWikipedia();

      runTestEmailValidator4J();

      runTestsJoshData();

      runTestfighFingForaLostCause();

      runTestRohanNagar();

      runTestVariousInternet();

      runTestInternet2();

      wlHeadline( "my old tests" );

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
      assertIsFalse( "first@last@test.org" );
      assertIsFalse( "@test@a.com" );
      assertIsFalse( "@\"someStringThatMightBe@email.com" );
      assertIsFalse( "test@@test.com" );

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
      assertIsTrue( "\"ABC..DEF\"@GHI.JKL" );

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

      assertIsFalse( "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " );
      assertIsTrue( "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" );

      wlHeadline( "unsupported" );

      assertIsTrue( "Loïc.Accentué@voilà.fr" );
      assertIsTrue( "Ärger.Öde@Übel.de" );
      assertIsTrue( "Smørrebrød@danmark.dk" );

      assertIsTrue( "ip6.without.brackets@1:2:3:4:5:6:7:8" );

      assertIsTrue( "email.address.without@topleveldomain" );

      assertIsTrue( "EmailAddressWithout@PointSeperator" );

      /*
       * https://github.com/RohanNagar/jmail/issues/6
       */
      assertIsFalse( "@1st.relay,@2nd.relay:user@final.domain" );

      wlHeadline( "Fillup" );

      if ( KNZ_FILLUP_AKTIV )
      {
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
      }
      else
      {
        wl( "Fillup ist nicht aktiv" );
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

    double email_ok_proz_true_korrekt_erkannt = ( 100.0 * TRUE_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_TRUE;

    double email_ok_proz_false_korrekt_erkannt = ( 100.0 * FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_FALSE;

    double email_ok_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( TRUE_RESULT_COUNT_EMAIL_IS_TRUE + FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    double email_false_proz_true_korrekt_erkannt = ( 100.0 * TRUE_RESULT_COUNT_EMAIL_IS_FALSE ) / COUNT_ASSERT_IS_TRUE;

    double email_false_proz_false_korrekt_erkannt = ( 100.0 * FALSE_RESULT_COUNT_EMAIL_IS_TRUE ) / COUNT_ASSERT_IS_FALSE;

    double email_false_proz_korrekt_erkannt_insgesamt = ( 100.0 * ( TRUE_RESULT_COUNT_EMAIL_IS_FALSE + FALSE_RESULT_COUNT_EMAIL_IS_TRUE ) ) / ( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE );

    wl( "" );

    wlHeadline( "Statistik" );

    wl( "  ASSERT_IS_TRUE  " + getStringZahl( COUNT_ASSERT_IS_TRUE ) + "   KORREKT " + getStringZahl( TRUE_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_ok_proz_true_korrekt_erkannt ) + m_number_format.format( email_ok_proz_true_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( TRUE_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_true_korrekt_erkannt ) + m_number_format.format( email_false_proz_true_korrekt_erkannt ) + " % = Error " + TRUE_RESULT_COUNT_ERROR );
    wl( "  ASSERT_IS_FALSE " + getStringZahl( COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_ok_proz_false_korrekt_erkannt ) + m_number_format.format( email_ok_proz_false_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( FALSE_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_false_proz_false_korrekt_erkannt ) + m_number_format.format( email_false_proz_false_korrekt_erkannt ) + " % = Error " + FALSE_RESULT_COUNT_ERROR );
    wl( "" );
    wl( "  GESAMT          " + getStringZahl( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( ( TRUE_RESULT_COUNT_EMAIL_IS_TRUE + FALSE_RESULT_COUNT_EMAIL_IS_FALSE ) ) + " = " + getEinzug( email_ok_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_ok_proz_korrekt_erkannt_insgesamt ) + " % | FALSCH ERKANNT " + getStringZahl( FALSE_RESULT_COUNT_EMAIL_IS_TRUE + TRUE_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_false_proz_korrekt_erkannt_insgesamt ) + " % = Error " + ( TRUE_RESULT_COUNT_ERROR + FALSE_RESULT_COUNT_ERROR ) );
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
    wl( "  Datum " + getAktDatumUndUhrzeitMs() );
    wl( "" );

    if ( m_str_buffer != null )
    {
      String home_dir = "/home/ea234";

      //home_dir = "c:/Daten/";

      schreibeDatei( home_dir + "/log_test_email_assert_true_false.txt", m_str_buffer.toString() );
    }

    m_str_buffer = null;

    System.exit( 0 );
  }

  private static void runTestInternet2()
  {
    wlHeadline( "https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs" );

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
    assertIsTrue( "uncommon-tld@sld.mobi" );
    assertIsTrue( "uncommon-tld@sld.museum" );
    assertIsTrue( "uncommon-tld@sld.travel" );
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
    assertIsFalse( "sld-starts-with-dash@-sld.com" );
    assertIsFalse( "two..consecutive-dots@sld.com" );
    assertIsTrue( "unbracketed-IP@127.0.0.1" );
    assertIsFalse( "underscore.error@example.com_" );

    wlHeadline( "https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/" );

    assertIsTrue( "hello@example.com" );
    assertIsTrue( "hello@example.co.uk" ); // .co.uk, 2 tld
    assertIsTrue( "hello-2020@example.com" ); // -
    assertIsTrue( "hello.2020@example.com" ); // .
    assertIsTrue( "hello_2020@example.com" ); // _
    assertIsTrue( "h@example.com" ); // local-part one letter
    assertIsTrue( "h@example-example.com" ); // domain contains a hyphen -
    assertIsTrue( "h@example-example-example.com" ); // domain contains two hyphens - -
    assertIsTrue( "h@example.example-example.com" ); // domain contains . -
    assertIsTrue( "hello.world-2020@example.com" ); // local-part contains . -

    assertIsTrue( "hello@example_example.com" ); // domain doesn't allow underscore
    assertIsTrue( "hello!+2020@example.com" ); // local-part don't allow special characters like !+

    assertIsFalse( "hello" ); // email need at least one @
    assertIsFalse( "hello@2020@example.com" ); // email doesn't allow more than one @
    assertIsTrue( "hello\\@2020@example.com" );
    assertIsTrue( "(comment @) hello\\@2020@example.com" );

    assertIsFalse( ".hello@example.com" ); // local-part can't start with a dot .
    assertIsFalse( "hello.@example.com" ); // local-part can't end with a dot .
    assertIsFalse( "hello..world@example.com" ); // local part don't allow dot . appear consecutively
    assertIsTrue( "hello.world.\"string ..\"@example.com" );
    assertIsTrue( "(comment ..) hello.world.\"string ..\"@example.com" );
    assertIsTrue( "hello.world.\"string ..\"@example.com (comment ..)" );

    assertIsFalse( "hello@example.a" ); // domain tld min 2 chars
    assertIsFalse( "hello@example..com" ); // domain doesn't allow dot . appear consecutively
    assertIsFalse( "hello@.com" ); // domain doesn't start with a dot .
    assertIsFalse( "hello@.com." ); // domain doesn't end with a dot .
    assertIsFalse( "hello@-example.com" ); // domain doesn't allow to start with a hyphen -
    assertIsFalse( "hello@example.com-" ); // domain doesn't allow to end with a hyphen -
    assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234xx@example.com" ); // local part is longer than 64 characters

    assertIsTrue( "email@goes_here.com" );
    assertIsTrue( "double--dash@example.com" );

    wlHeadline( "https://github.com/dotnet/docs/issues/6620" );

    assertIsFalse( "" ); //(empty string) results in ArgumentException
    assertIsFalse( " " ); //(string that contains only white spaces) results in FormatException
    assertIsFalse( " jkt@gmail.com" ); //(white spaces at the start or at the end) results in "jkt@gmail.com" (no white spaces)
    assertIsFalse( "jkt@gmail.com " ); //(white spaces at the start or at the end) results in "jkt@gmail.com" (no white spaces)
    assertIsFalse( "jkt@ gmail.com" ); //(white space is directly after @) results in "jkt@gmail.com" (no white spaces)
    assertIsFalse( "jkt@g mail.com" ); //(any not trailing white space after @ except the above case) resuls in FormatException
    assertIsFalse( "jkt @gmail.com" ); //(white space is in front of @) results in "jkt@gmail.com" (no white spaces)
    assertIsFalse( "j kt@gmail.com" ); //(white space is before @, inside the username) results in "kt@gmail.com" (the part after a white space is taken as the mail address)

    wlHeadline( "https://www.abstractapi.com/guides/java-email-validation" );

    assertIsTrue( "\"test123\"@gmail.com" );
    assertIsTrue( "test123@gmail.comcomco" );
    assertIsTrue( "test123@gmail.c" );
    assertIsTrue( "test1&23@gmail.com" );
    assertIsTrue( "test123@gmail.com" );
    assertIsFalse( "test123@gmail..com" );
    assertIsFalse( ".test123@gmail.com" );
    assertIsFalse( "test123@gmail.com." );
    assertIsFalse( "test1Ὠ23@gmail.com" );

    wlHeadline( "https://www.javatpoint.com/java-email-validation" );

    assertIsTrue( "javaTpoint@domain.com" );
    assertIsTrue( "javaTpoint@domain.co.in" );
    assertIsTrue( "javaTpoint.name@domain.com" );
    assertIsTrue( "javaTpoint#@domain.co.in" );
    assertIsTrue( "java'Tpoint@domain.com" );
    assertIsTrue( "1avaTpoint#@domain.co.in" );
    assertIsTrue( "12453@domain.com" );
    assertIsFalse( "javaTpoint@domaincom" );
    assertIsFalse( "javaTpoint@domain.com." );
    assertIsFalse( "javaTpoint@domain..com" );
    assertIsFalse( "javaTpoint#domain.com" );
    assertIsFalse( ".javaTpoint@yahoo.com" );
    assertIsFalse( "@yahoo.com" );
    assertIsFalse( "javaTpoint#domain.com" );
    assertIsFalse( "12javaTpoint#domain.com" );

    wlHeadline( "https://stackoverflow.com/questions/2035783/email-validation-converting-a-regular-expression-written-in-php-preg-to-net" );

    assertIsTrue( "l3tt3rsAndNumb3rs@domain.com" );
    assertIsTrue( "has-dash@domain.com" );
    assertIsTrue( "hasApostrophe.o'leary@domain.org" );
    assertIsTrue( "uncommonTLD@domain.museum" );
    assertIsTrue( "uncommonTLD@domain.travel" );
    assertIsTrue( "uncommonTLD@domain.mobi" );
    assertIsTrue( "countryCodeTLD@domain.uk" );
    assertIsTrue( "countryCodeTLD@domain.rw" );
    assertIsTrue( "lettersInDomain@911.com" );
    assertIsTrue( "underscore_inLocal@domain.net" );
    assertIsTrue( "IPInsteadOfDomain@127.0.0.1" );
    assertIsFalse( "IPAndPort@127.0.0.1:25" );
    assertIsTrue( "subdomain@sub.domain.com" );
    assertIsTrue( "local@dash-inDomain.com" );
    assertIsTrue( "dot.inLocal@foo.com" );
    assertIsTrue( "a@singleLetterLocal.org" );
    assertIsTrue( "singleLetterDomain@x.org" );
    assertIsTrue( "&*=?^+{}'~@validCharsInLocal.net" );
    assertIsFalse( "missingDomain@.com" );
    assertIsFalse( "@missingLocal.org" );
    assertIsFalse( "missingatSign.net" );
    assertIsFalse( "local@SecondLevelDomainNamesAreInvalidIfTheyAreLongerThan64Charactersss.org" );
    assertIsTrue( "TLDDoesntExist@domain.moc" );
    assertIsTrue( "numbersInTLD@domain.c0m" );

    wlHeadline( "https://java2blog.com/validate-email-address-in-java/" );

    assertIsTrue( "admin@java2blog.com" );
    assertIsFalse( "@java2blog.com" );
    assertIsTrue( "arpit.mandliya@java2blog.com" );

    wlHeadline( "https://www.tutorialspoint.com/javaexamples/regular_email.htm" );

    assertIsTrue( "sairamkrishna@tutorialspoint.com" );
    assertIsTrue( "kittuprasad700@gmail.com" );
    assertIsFalse( "sairamkrishna_mammahe%google-india.com" );
    assertIsTrue( "sairam.krishna@gmail-indai.com" );
    assertIsTrue( "sai#@youtube.co.in" );
    assertIsFalse( "kittu@domaincom" );
    assertIsFalse( "kittu#gmail.com" );
    assertIsFalse( "@pindom.com" );

    wlHeadline( "https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java" );

    assertIsTrue( "me@example.com" );
    assertIsTrue( "a.nonymous@example.com" );
    assertIsTrue( "name+tag@example.com" );
    assertIsTrue( "!#$%&'+-/=.?^`{|}~@[1.0.0.127]" );
    assertIsTrue( "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]" );
    assertIsTrue( "me(this is a comment)@example.com" );
    assertIsTrue( "\"bob(hi)smith\"@test.com" );
    assertIsTrue( "\"Bob\" <bob@hi.com>" );
    assertIsTrue( "me.example@com" );
    assertIsTrue( "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com" );
    assertIsTrue( "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net" );

    /*
     * https://github.com/bbottema/simple-java-mail/issues/16
     */
    assertIsTrue( "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );
    assertIsTrue( "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );
    assertIsTrue( "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );
    assertIsTrue( "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom" );

    assertIsTrue( "rekry@fi.xyz.dom" );
    assertIsFalse( "NotAnEmail" );
    assertIsFalse( "me@" );
    assertIsFalse( "@example.com" );
    assertIsFalse( ".me@example.com" );
    assertIsFalse( "me@example..com" );
    assertIsFalse( "me\\@example.com" );
    assertIsFalse( "\"ßoµ\" <notifications@example.com>" );
    assertIsFalse( "[Kayaks] <kayaks@kayaks.org>" );
    assertIsFalse( "Kayaks.org <kayaks@kayaks.org>" );
    assertIsFalse( "semico...@gmail.com" );
  }

  private static void runTestfighFingForaLostCause()
  {
    wlHeadline( "https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php" );

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
    assertIsTrue( "test@123.123.123.123" );
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
    assertIsTrue( "foobar@192.168.0.1" );
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
  }

  private static void runTestRohanNagar()
  {
    wlHeadline( "https://www.rohannagar.com/jmail/" );

    assertIsFalse( "\"qu@test.org" ); // Opening quote must have a closing quote
    assertIsFalse( "ote\"@test.org" ); // Closing quote must have an opening quote
    assertIsFalse( "\"().:;<>[\\]@example.com" ); // Opening quote must have a closing quote
    assertIsFalse( "\"\"\"@iana.org" ); // Each quote must be in a pair
    assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" );
    assertIsFalse( "this is\"not\\allowed@example.com" ); // Whitespace should be quoted or dot-separated
    assertIsFalse( "this\\ still\"not\\allowed@example.com" );
    assertIsFalse( "QA[icon]CHOCOLATE[icon]@test.com" ); // Unquoted [ and ] characters are not allowed
    assertIsFalse( "QA\\[icon\\]CHOCOLATE\\[icon\\]@test.com" ); // Unquoted [ and ] characters are not allowed
    assertIsFalse( "plainaddress" ); // The @ character must be present
    assertIsFalse( "@example.com" );
    assertIsFalse( "this\\ is\"really\"not\\allowed@example.com" );
    assertIsTrue( "\"first\\\"last\"@test.org" ); // TODO Auto-generated method stub

    assertIsFalse( "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" );
    assertIsTrue( "first\\@last@iana.org" );
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
    assertIsFalse( "\"first\"last\"@test.org" );
    assertIsTrue( "much.\"more\\ unusual\"@example.com" );
    assertIsFalse( "\"first\\last\"@test.org" );
    assertIsTrue( "\"Abc\\@def\"@test.org" );
    assertIsTrue( "\"Fred\\ Bloggs\"@test.org" );
    assertIsFalse( "\"Joe.\\Blow\"@test.org" );
    assertIsTrue( "\"Abc@def\"@test.org" );
    assertIsTrue( "\"Fred Bloggs\"@test.org" );
    assertIsTrue( "\"Doug \\\"Ace\\\" L.\"@test.org" );
    assertIsTrue( "\"[[ test ]]\"@test.org" );
    assertIsTrue( "\"test.test\"@test.org" );
    assertIsTrue( "test.\"test\"@test.org" );
    assertIsTrue( "\"test@test\"@test.org" );
    assertIsFalse( "\"test tabulator \test\"@test.org" );
    assertIsTrue( "\"first\".\"last\"@test.org" );
    assertIsTrue( "\"first\".middle.\"last\"@test.org" );
    assertIsTrue( "\"first\".last@test.org" );
    assertIsTrue( "first.\"last\"@test.org" );
    assertIsTrue( "\"first\".\"middle\".\"last\"@test.org" );
    assertIsTrue( "\"first.middle\".\"last\"@test.org" );
    assertIsTrue( "\"first.middle.last\"@test.org" );
    assertIsTrue( "\"first..last\"@test.org" );
    assertIsTrue( "\"Unicode NULL \"@char.com" );
    assertIsFalse( "\"test\\blah\"@test.org" );
    assertIsFalse( "\"test\blah\"@test.org" );
    assertIsTrue( "\"test\\\"blah\"@test.org" );
    assertIsTrue( "\"first\\\"last\"@test.org" );
    assertIsTrue( "\"Test \\\"Fail\\\" Ing\"@test.org" );
    assertIsFalse( "\"Test \"Fail\" Ing\"@test.org" );
    assertIsTrue( "\"test blah\"@test.org" );
    assertIsTrue( "first.last@test.org" );
    assertIsFalse( "jdoe@machine(comment).example" );
    assertIsFalse( "first.\"\".last@test.org" );
    assertIsFalse( "\"\"@test.org" );
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
    assertIsFalse( "cal(foo\\@bar)@iamcal.com" );
    assertIsTrue( "(comment)test@test.org" );
    assertIsFalse( "cal(foo\\)bar)@iamcal.com" );
    assertIsFalse( "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org" );
    assertIsFalse( "first(abc\\(def)@test.org" );
    assertIsFalse( "a(a(b(c)d(e(f))g)h(i)j)@test.org" );
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
    assertIsFalse( "cal@iamcal(woo).(yay)com" );
    assertIsFalse( "first(abc.def).last@test.org" );
    assertIsFalse( "first(a\"bc.def).last@test.org" );
    assertIsFalse( "first.(\")middle.last(\")@test.org" );
    assertIsFalse( "first().last@test.org" );
    assertIsTrue( "Abc\\@def@test.org" );
    assertIsTrue( "Fred\\ Bloggs@test.org" );
    assertIsTrue( "Joe.\\\\Blow@test.org" );
  }

  private static void runTestVariousInternet()
  {
    wlHeadline( "unsorted from the WEB" );

    /*
     * <pre>
     * 
     * Various examples from the Internet.
     * 
     * https://stackoverflow.com/questions/8022530/how-to-check-for-valid-email-address
     * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
     * https://stackoverflow.com/questions/25471114/how-to-validate-an-e-mail-address-in-swift?noredirect=1&lq=1
     * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
     * https://stackoverflow.com/questions/6850894/regex-split-email-address?noredirect=1&lq=1
     * https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript?page=3&tab=votes#tab-top
     * https://stackoverflow.com/questions/22993545/ruby-email-validation-with-regex?noredirect=1&lq=1
     * https://docs.microsoft.com/en-us/dotnet/api/system.net.mail.mailaddress.address?redirectedfrom=MSDN&view=netframework-4.8#System_Net_Mail_MailAddress_Address
     * https://github.com/manishsaraan/email-validator/blob/master/test.js
     * https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/
     * https://www.journaldev.com/638/java-email-validation-regex
     * https://www.linuxjournal.com/article/9585
     * https://github.com/JoshData/python-email-validator/blob/main/tests/test_syntax.py
     * https://www.educative.io/answers/how-to-validate-an-email-address-in-javascript
     * https://www.upgrad.com/blog/email-validation-in-javascript/
     * 
     * </pre>
     */

    assertIsFalse( "testm ail@mail.com" ); // spaces into the string
    assertIsFalse( "testmail@mail.com." ); // finish with points
    assertIsFalse( ".testmail@mail.com" ); // start with points
    assertIsFalse( " testmail@mail.com" ); // spaces at start
    assertIsTrue( "username@company.domain" );
    assertIsTrue( "support@whoisxmlapi.com" );
    assertIsTrue( "popular_website15@comPany.com" );

    assertIsTrue( "domain.starts.with.numbers1@1234domain.com" );
    assertIsTrue( "domain.starts.with.numbers2@123.123domain.com" );

    assertIsTrue( "^local.part.with.caret@domain.com" );

    assertIsTrue( "answers@educative.io" );
    assertIsTrue( "test_123@gmail.com" );
    assertIsTrue( "student@mit.edu" );
    assertIsTrue( "admission@ox.ac.uk" );
    assertIsTrue( "-9i{@ssss.ss" );
    assertIsFalse( "-9i{@-ssss.ss" );
    assertIsFalse( "answers@educative" );
    assertIsFalse( "test 123@gmail.com" );
    assertIsFalse( "student@mitedu" );
    assertIsFalse( "admission@@ox.ac.uk" );
    assertIsFalse( "my@as@sd.com" );
    assertIsFalse( "{a}!%?@@bar.com" );
    assertIsFalse( "foo#!@@bar.com" );
    assertIsFalse( "Younourmetd345@abcd.b" );
    assertIsFalse( "@youmenandwe.we.net" );
    assertIsFalse( "yourminewebsite@.com.you " );
    assertIsFalse( "Ourwebsiteismne.azbyz.com" );
    assertIsFalse( "Inownzsite()&@abcd.com" );
    assertIsFalse( "Owner.me..7080@abcd.com" );
    assertIsTrue( "Own.minesite@myuniverse.org" );
    assertIsTrue( "my+address@example.org" );
    assertIsFalse( "me@Ｄｏｍａｉｎ.com" );
    assertIsFalse( "me@D o m a i n.com" );
    assertIsFalse( "invalid@domain,com" );
    assertIsTrue( "hello@00.pe" );
    assertIsTrue( "\"@\"@domain.com" );
    assertIsTrue( "\\@@domain.com" );
    assertIsTrue( "to1@domain.com" );
    assertIsTrue( "Full Name <full@example.com>" );
    assertIsFalse( "ツ-test@joshdata.me" );
    assertIsFalse( "user@@host" );
    assertIsTrue( "t#@d.com" );
    assertIsFalse( "u\"evil@domain.com" );
    assertIsTrue( "nonexistinglogin@valid-domain.com" );
    assertIsTrue( "user@department.company.com" );
    assertIsTrue( "john@example.com" );
    assertIsTrue( "python-list@python.org" );
    assertIsTrue( "wha.t.`1an?ug{}ly@email.com" );
    assertIsFalse( "Hello ABCD, here is my mail id example@me.com " );
    assertIsTrue( "\"djt jr\"@wh.gov" );
    assertIsFalse( "a..@..............a" );
    assertIsTrue( "me+valid@mydomain.example.net" );
    assertIsTrue( "revo@74.125.228.53" );
    assertIsFalse( "revo@test&^%$#|.com" );
    assertIsTrue( "\"<script>alert('XSS')</script>\"@example.com " );
    assertIsTrue( "ansel@adams.photography" );
    assertIsFalse( "<')))><@fish.left.com" );
    assertIsFalse( "><(((*>@fish.right.com" );
    assertIsFalse( " check@this.com" );
    assertIsFalse( " email@example.com" );
    assertIsFalse( ".....@a...." );
    assertIsFalse( "..@test.com" );
    assertIsFalse( "..@test.com" );
    assertIsTrue( "\"test....\"@gmail.com" ); // https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression?noredirect=1&lq=1
    assertIsFalse( "test....@gmail.com" );
    assertIsTrue( "name@xn--4ca9at.at" );
    assertIsTrue( "simon-@hotmail.com" );
    assertIsTrue( "!@mydomain.net" );
    assertIsTrue( "sean.o'leary@cobbcounty.org" );
    assertIsTrue( "xjhgjg876896@domain.com" );
    assertIsTrue( "Tony Snow <tony@example.com>" );
    assertIsTrue( "(tony snow) tony@example.com" );
    assertIsTrue( "tony%example.com@example.org" );
    assertIsFalse( "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]" );
    assertIsFalse( "a-b'c_d.e@f-g.h" );
    assertIsFalse( "a-b'c_d.@f-g.h" );
    assertIsFalse( "a-b'c_d.e@f-.h" );
    assertIsTrue( "\"root\"@example.com" );
    assertIsTrue( "root@example.com" );
    assertIsFalse( ".@s.dd" );
    assertIsFalse( ".@s.dd" );
    assertIsFalse( ".a@test.com" );
    assertIsFalse( ".a@test.com" );
    assertIsFalse( ".abc@somedomain.com" );
    assertIsFalse( ".dot@example.com" );
    assertIsFalse( ".email@domain.com" );
    assertIsFalse( ".journaldev@journaldev.com" );
    assertIsFalse( ".username@yahoo.com" );
    assertIsFalse( ".username@yahoo.com" );
    assertIsFalse( ".xxxx@mysite.org" );
    assertIsFalse( "stuff.@stuff.com" );
    assertIsFalse( "asdf@asdf" );
    assertIsFalse( "123@$.xyz" );
    assertIsFalse( "<1234   @   local(blah)  .machine .example>" );
    assertIsFalse( "@%^%#$@#$@#.com" );
    assertIsFalse( "@b.com" );
    assertIsFalse( "@domain.com" );
    assertIsFalse( "@example.com" );
    assertIsFalse( "@mail.example.com:joe@sixpack.com" );
    assertIsFalse( "@yahoo.com" );
    assertIsFalse( "@you.me.net" );
    assertIsFalse( "A@b@c@example.com" );
    assertIsFalse( "Abc.example.com" );
    assertIsFalse( "Abc@example.com." );
    assertIsFalse( "Abc..123@example.com" );
    assertIsFalse( "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com" );
    assertIsFalse( "Display Name <email@plus.com> (after name with display)" );
    assertIsFalse( "Doug\\ \\\"Ace\\\"\\ L\\.@example.com" );
    assertIsFalse( "Doug\\ \\\"Ace\\\"\\ Lovell@example.com" );
    assertIsFalse( "Foobar Some@thing.com" );
    assertIsFalse( "MailTo:casesensitve@domain.com" );
    assertIsFalse( "No -foo@bar.com" );
    assertIsFalse( "No asd@-bar.com" );
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
    assertIsTrue( "enrst.den.baecker@web.de" );
    assertIsTrue( "neserdna.trebkce@web.de" );
    assertIsTrue( "rpxoreg.naqerfra@web.de" );
    assertIsFalse( "unescaped white space@fake$com" );
    assertIsTrue( "\"SL-EA-234\"@kfz-kennzeichen.de" );
    assertIsTrue( "\"Name\"@test.de" );
    assertIsTrue( "\"Name.\"@test.de" );
    assertIsTrue( "\"Name..\"@test.de" );
    assertIsTrue( "\"Name.\\\"\"@test.de" );
    assertIsTrue( "\".Name.\"@test.de" );
    assertIsTrue( "\"..Name..\"@test.de" );
    assertIsTrue( "\"...Name...\"@test.de" );
    assertIsTrue( "\" ...Name... \"@test.de" );
    assertIsTrue( "\"..Name\"@test.de" );
    assertIsTrue( "\"Name\"@test.de" );
    assertIsTrue( "\"\\\"Name.\\\"\"@test.de" );
    assertIsTrue( "\\\\\"Name.\\\"\"@test.de" );
    assertIsTrue( "\"Vorname.Nachname\"@test.de" );
    assertIsTrue( "\"Vorname+Nachname\"@test.de" );
    assertIsTrue( "\"Vorname.Nachname.\"@test.de" );
    assertIsTrue( "\"Vorname+Nachname.\"@test.de" );
    assertIsTrue( "\" 123,45 * 678,910 = 83.811,4395 \"@calc-test.de" );
    assertIsTrue( "\" 50.000,00 / 106,00 = 471.69811320754716981132075471698113 \"@calc-test.de" );
    assertIsTrue( "\" 2 + 2 = 3,999999999999999999999999991 \"@erste-hochrechnung.de" );
    assertIsTrue( "\"Joe\\\\Blow\"@example.com" );
    assertIsTrue( "\".John.Doe\"@example.com" );
    assertIsTrue( "\"John.Doe.\"@example.com" );
    assertIsTrue( "\"John..Doe\"@example.com" );
    assertIsTrue( "john@doe.com77" );
    assertIsTrue( "john.smith(comment)@example.com" );
    assertIsTrue( "(comment)john.smith@example.com" );
    assertIsTrue( "john.smith@(comment)example.com" );
    assertIsTrue( "john.smith@example.com(comment)" );
    assertIsTrue( "john.smith@example.com" );
    assertIsTrue( "joeuser+tag@example.com" );
    assertIsTrue( "jsmith@[192.168.2.1]" );
    assertIsTrue( "jsmith@[IPv6:2001:db8::1]" );
    assertIsTrue( "Fred\\ Bloggs@example.com" );
    assertIsTrue( "Joe.\\\\Blow@example.com" );
    assertIsTrue( "<john@doe.com>" );
    assertIsTrue( "John <john@doe.com>" );
    assertIsFalse( "john.smith@exampl(comment)e.com" );
    assertIsFalse( "john.s(comment)mith@example.com" );
    assertIsFalse( "john.smith(comment)@(comment)example.com" );
    assertIsFalse( "john.smith(com@ment)example.com" );
    assertIsTrue( "john.doe@example.com" );
    assertIsTrue( "john.o'doe@example.com" );
    assertIsTrue( "jones@ms1.proseware.com" );
    assertIsFalse( "\"\"Joe Smith email@domain.com" );
    assertIsFalse( "\"\"Joe Smith' email@domain.com" );
    assertIsFalse( "\"\"Joe Smith\"\"email@domain.com" );
    assertIsFalse( "\"Joe Smith\" email@domain.com" );
    assertIsFalse( "\"Joe\\tSmith\".email@domain.com" );
    assertIsFalse( "\"Joe\"Smith\" email@domain.com" );
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
    assertIsTrue( "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE" );
    assertIsFalse( "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2.4}" );
    assertIsTrue( "\"\\\" + \\\"select * from user\\\" + \\\"\"@example.de" );
    assertIsFalse( "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com" );
    assertIsFalse( "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
    assertIsFalse( "\"qu@example.com" );
    assertIsFalse( "\\$A12345@example.com" );
    assertIsFalse( "_@bde.cc," );
    assertIsFalse( "a..b@bde.cc" );
    assertIsFalse( "a.\"b@c\".x.\"@\".d.e@f.g@" );
    assertIsFalse( "a.b@example,co.de" );
    assertIsFalse( "a.b@example,com" );
    assertIsFalse( "a>b@somedomain.com" );
    assertIsFalse( "a@.com" );
    assertIsFalse( "a@b." );
    assertIsFalse( "a@b.-de.cc" );
    assertIsFalse( "a@b._de.cc" );
    assertIsFalse( "a@bde-.cc" );
    assertIsFalse( "a@bde.c-c" );
    assertIsFalse( "a@bde.cc." );
    assertIsFalse( "a@bde_.cc" );
    assertIsTrue( "a+b@bde.cc" );
    assertIsTrue( "a+b@c.com" );
    assertIsTrue( "a-b@bde.cc" );
    assertIsTrue( "a.a@test.com" );
    assertIsTrue( "a.b@com" );
    assertIsTrue( "a/d@somedomain.com" );
    assertIsTrue( "a2@bde.cc" );
    assertIsTrue( "a@123.45.67.89" );
    assertIsTrue( "a@b.c.com" );
    assertIsTrue( "a@b.com" );
    assertIsTrue( "a@bc.com" );
    assertIsTrue( "a@bcom" );
    assertIsTrue( "a@domain.com" );
    assertIsTrue( "a__z@provider.com" );
    assertIsTrue( "a_z%@gmail.com" );
    assertIsTrue( "aaron@theinfo.org" );
    assertIsTrue( "ab@288.120.150.10.com" );
    assertIsTrue( "ab@[120.254.254.120]" );
    assertIsTrue( "ab@b-de.cc" );
    assertIsTrue( "ab@c.com" );
    assertIsTrue( "ab_c@bde.cc" );
    assertIsTrue( "abc.\"defghi\".xyz@example.com" );
    assertIsTrue( "abc.efg@gmail.com" );
    assertIsTrue( "abc.xyz@gmail.com.in" );
    assertIsTrue( "abc123xyz@asdf.co.in" );
    assertIsTrue( "abc1_xyz1@gmail1.com" );
    assertIsTrue( "abc@abc.abc" );
    assertIsTrue( "abc@abc.abcd" );
    assertIsTrue( "abc@abc.abcde" );
    assertIsTrue( "abc@abc.co.in" );
    assertIsTrue( "abc@abc.com.com.com.com" );
    assertIsTrue( "abc@gmail.com.my" );
    assertIsTrue( "abc\\@def@example.com" );
    assertIsTrue( "abc\\\\@example.com" );
    assertIsTrue( "abcxyz123@qwert.com" );
    assertIsFalse( "ab@120.25.1111.120" );
    assertIsFalse( "ab@120.256.256.120" );
    assertIsFalse( "ab@188.120.150.10]" );
    assertIsFalse( "ab@988.120.150.10" );
    assertIsFalse( "ab@[188.120.150.10" );
    assertIsFalse( "ab@[188.120.150.10].com" );
    assertIsFalse( "ab@188.120.150.10]" );
    assertIsFalse( "ab@b+de.cc" );
    assertIsFalse( "ab@sd@dd" );
    assertIsFalse( "abc.@somedomain.com" );
    assertIsFalse( "abc@def@example.com" );
    assertIsFalse( "abc@gmail..com" );
    assertIsFalse( "abc@gmail.com.." );
    assertIsFalse( "abc\"defghi\"xyz@example.com" );
    assertIsFalse( "abc\\@example.com" );
    assertIsFalse( "abc\\\"def\\\"ghi@example.com" );
    assertIsFalse( "abc\\\\@def@example.com" );
    assertIsFalse( "as3d@dac.coas-" );
    assertIsFalse( "asd@dasd@asd.cm" );
    assertIsFalse( "check@this..com" );
    assertIsFalse( "check@thiscom" );
    assertIsFalse( "da23@das..com" );
    assertIsFalse( "dad@sds" );
    assertIsFalse( "dasddas-@.com" );
    assertIsFalse( "david.gilbertson@SOME+THING-ODD!!.com" );
    assertIsFalse( "dot.@example.com" );
    assertIsFalse( "doug@" );
    assertIsFalse( "email( (nested) )@plus.com" );
    assertIsFalse( "email(with @ in comment)plus.com" );
    assertIsFalse( "email)mirror(@plus.com" );
    assertIsFalse( "email..email@domain.com" );
    assertIsFalse( "email.@domain.com" );
    assertIsFalse( "email.domain.com" );
    assertIsFalse( "email@#hash.com" );
    assertIsFalse( "email@-domain.com" );
    assertIsFalse( "email@.domain.com" );
    assertIsFalse( "email@=qowaiv.com" );
    assertIsFalse( "email@caret^xor.com" );
    assertIsFalse( "email@colon:colon.com" );
    assertIsFalse( "email@dollar$.com" );
    assertIsFalse( "email@domain" );
    assertIsFalse( "email@domain-.com" );
    assertIsFalse( "email@domain..com" );
    assertIsTrue( "email@domain.co.de" );
    assertIsTrue( "email@domain.com (joe Smith)" );
    assertIsTrue( "email@domain.com" );
    assertIsFalse( "email@domain.com-" );
    assertIsFalse( "email@domain.com." );
    assertIsFalse( "email@domain.com>" );
    assertIsTrue( "email@domain.domain.domain.com.com" );
    assertIsTrue( "email@domain.domain.domain.domain.com.com" );
    assertIsTrue( "email@domain.domain.domain.domain.domain.com.com" );
    assertIsFalse( "email@domain@domain.com" );
    assertIsFalse( "email@example" );
    assertIsFalse( "email@example..com" );
    assertIsTrue( "email@example.co.uk" );
    assertIsFalse( "email@example.co.uk." );
    assertIsFalse( "email@example.com " );
    assertIsTrue( "email@example.com" );
    assertIsFalse( "email@exclamation!mark.com" );
    assertIsFalse( "email@grave`accent.com" );
    assertIsTrue( "email@mail.gmail.com" );
    assertIsFalse( "email@mailto:domain.com" );
    assertIsFalse( "email@obelix*asterisk.com" );
    assertIsFalse( "email@plus+.com" );
    assertIsFalse( "email@plus.com (not closed comment" );
    assertIsFalse( "email@plus.com (not closed comment" );
    assertIsFalse( "email@pp|e.com" );
    assertIsFalse( "email@p|pe.com" );
    assertIsFalse( "email@question?mark.com" );
    assertIsFalse( "email@r&amp;d.com" );
    assertIsFalse( "email@rightbracket}.com" );
    assertIsTrue( "email@subdomain.domain.com" );
    assertIsFalse( "email@wave~tilde.com" );
    assertIsFalse( "email@{leftbracket.com" );
    assertIsFalse( "mailto:mailto:email@domain.com" );
    assertIsFalse( "f...bar@gmail.com" );
    assertIsFalse( "fa ke@false.com" );
    assertIsFalse( "fake@-false.com" );
    assertIsFalse( "fake@fal se.com" );
    assertIsFalse( "fdsa" );
    assertIsFalse( "fdsa@" );
    assertIsFalse( "fdsa@fdsa" );
    assertIsFalse( "fdsa@fdsa." );
    assertIsFalse( "foo.bar#gmail.co.u" );
    assertIsFalse( "foo.bar@machine.sub\\@domain.example.museum" );
    assertIsFalse( "foo@bar@machine.subdomain.example.museum" );
    assertIsFalse( "foo~&(&)(@bar.com" );
    assertIsTrue( "foo\\@bar@example.com" );
    assertIsFalse( "gatsby@f.sc.ot.t.f.i.tzg.era.l.d." );
    assertIsFalse( "get_at_m.e@gmail" );
    assertIsFalse( "hallo2ww22@example....caaaao" );
    assertIsFalse( "hallo@example.coassjj#sswzazaaaa" );
    assertIsFalse( "hello world@example.com" );
    assertIsFalse( "invalid.email.com" );
    assertIsFalse( "invalid@email@domain.com" );
    assertIsFalse( "isis@100%.nl" );
    assertIsFalse( "j..s@proseware.com" );
    assertIsFalse( "j.@server1.proseware.com" );
    assertIsFalse( "jane@jungle.com: | /usr/bin/vacation" );
    assertIsFalse( "journaldev" );
    assertIsFalse( "journaldev()*@gmail.com" );
    assertIsFalse( "journaldev..2002@gmail.com" );
    assertIsFalse( "journaldev.@gmail.com" );
    assertIsFalse( "journaldev123@.com" );
    assertIsFalse( "journaldev123@.com.com" );
    assertIsFalse( "journaldev123@gmail.a" );
    assertIsFalse( "journaldev@%*.com" );
    assertIsFalse( "journaldev@.com.my" );
    assertIsFalse( "journaldev@gmail.com.1a" );
    assertIsFalse( "journaldev@journaldev@gmail.com" );
    assertIsFalse( "js@proseware..com" );
    assertIsFalse( "mailto:email@domain.com" );
    assertIsFalse( "mailto:mailto:email@domain.com" );
    assertIsFalse( "me..2002@gmail.com" );
    assertIsFalse( "me.@gmail.com" );
    assertIsFalse( "me123@.com" );
    assertIsFalse( "me123@.com.com" );
    assertIsFalse( "me@%*.com" );
    assertIsFalse( "me@.com.my" );
    assertIsFalse( "me@gmail.com.1a" );
    assertIsFalse( "me@me@gmail.com" );
    assertIsFalse( "myemail@@sample.com" );
    assertIsFalse( "myemail@sa@mple.com" );
    assertIsFalse( "myemailsample.com" );
    assertIsFalse( "myname@mail*.com" );
    assertIsFalse( "ote\"@example.com" );
    assertIsFalse( "pio_pio@#factory.com" );
    assertIsFalse( "pio_pio@factory.c#om" );
    assertIsFalse( "pio_pio@factory.c*om" );
    assertIsFalse( "plain.address" );
    assertIsFalse( "plainaddress" );
    assertIsFalse( "tarzan@jungle.org,jane@jungle.org" );
    assertIsFalse( "this is not valid@email$com" );
    assertIsFalse( "this\\ still\\\"not\\allowed@example.com" );
    assertIsFalse( "two..dot@example.com" );
    assertIsFalse( "user#domain.com" );
    assertIsFalse( "username@yahoo..com" );
    assertIsFalse( "username@yahoo.c" );
    assertIsTrue( "username@domain.com" );
    assertIsTrue( "_username@domain.com" );
    assertIsTrue( "username_@domain.com" );
    assertIsFalse( "xxx@u*.com" );
    assertIsFalse( "xxxx..1234@yahoo.com" );
    assertIsFalse( "xxxx.ourearth.com" );
    assertIsFalse( "xxxx123@gmail.b" );
    assertIsFalse( "xxxx@.com.my" );
    assertIsFalse( "xxxx@.org.org" );
    assertIsFalse( "xxxxx()*@gmail.com" );
    assertIsFalse( "{something}@{something}.{something}" );
    assertIsTrue( "!def!xyz%abc@example.com" );
    assertIsTrue( "!sd@gh.com" );
    assertIsTrue( "$A12345@example.com" );
    assertIsTrue( "%20f3v34g34@gvvre.com" );
    assertIsTrue( "%2@gmail.com" );
    assertIsTrue( "--@ooo.ooo" );
    assertIsTrue( "-@bde.cc" );
    assertIsTrue( "-asd@das.com" );
    assertIsTrue( "1234567890@domain.com" );
    assertIsTrue( "1@domain.com" );
    assertIsTrue( "1@gmail.com" );
    assertIsTrue( "1_example@something.gmail.com" );
    assertIsTrue( "2@bde.cc" );
    assertIsTrue( "3c296rD3HNEE@d139c.a51" );
    assertIsTrue( "<boss@nil.test>" );
    assertIsTrue( "A__z/J0hn.sm{it!}h_comment@example.com.co" );
    assertIsTrue( "Abc.123@example.com" );
    assertIsTrue( "Abc@10.42.0.1" );
    assertIsTrue( "Abc@example.com" );
    assertIsTrue( "D.Oy'Smith@gmail.com" );
    assertIsTrue( "mymail\\@hello@hotmail.com" );
    assertIsTrue( "PN=Joe/OU=X400/@gateway.com" );
    assertIsTrue( "This is <john@127.0.0.1>" );
    assertIsTrue( "This is <john@[127.0.0.1]>" );
    assertIsTrue( "Who? <one@y.test>" );
    assertIsTrue( "\"%2\"@gmail.com" );
    assertIsTrue( "\"Abc@def\"@example.com" );
    assertIsTrue( "\"Abc\\@def\"@example.com" );
    assertIsTrue( "\"Doug \\\"Ace\\\" L.\"@example.com" );
    assertIsTrue( "\"Fred Bloggs\"@example.com" );
    assertIsTrue( "\"Fred\\ Bloggs\"@example.com" );
    assertIsTrue( "\"Giant; \\\"Big\\\" Box\" <sysservices@example.net>" );
    assertIsTrue( "\"Look at all these spaces!\"@example.com" );
    assertIsTrue( "\"a..b\"@gmail.com" );
    assertIsTrue( "\"a@b\"@example.com" );
    assertIsTrue( "\"a_b\"@gmail.com" );
    assertIsTrue( "\"abcdefghixyz\"@example.com" );
    assertIsTrue( "\"cogwheel the orange\"@example.com" );
    assertIsTrue( "\"foo\\@bar\"@Something.com" );
    assertIsTrue( "\"j\\\"s\"@proseware.com" );
    assertIsTrue( "\"myemail@sa\"@mple.com" );
    assertIsFalse( "\"Doug \"Ace\" L.\"@example.com" );
    assertIsFalse( "\"Joe Q. Public\" <john.q.public@example.com>" );
    assertIsFalse( "\"Joe\\Blow\"@example.com" );
    assertIsTrue( "\"this is v@lid!\"@example.com" );
    assertIsTrue( "_-_@bde.cc" );
    assertIsTrue( "_@gmail.com" );
    assertIsTrue( "_dasd@sd.com" );
    assertIsTrue( "_dasd_das_@9.com" );
    assertIsTrue( "_somename@example.com" );
    assertIsTrue( "a&d@somedomain.com" );
    assertIsTrue( "a*d@somedomain.com" );
    assertIsTrue( "alex@example.com" );
    assertIsTrue( "alireza@test.co.uk" );
    assertIsTrue( "asd-@asd.com" );
    assertIsTrue( "begeddov@jfinity.com" );
    assertIsTrue( "check@this.com" );
    assertIsTrue( "cog@wheel.com" );
    assertIsTrue( "customer/department=shipping@example.com" );
    assertIsTrue( "d._.___d@gmail.com" );
    assertIsTrue( "d.j@server1.proseware.com" );
    assertIsTrue( "d.oy.smith@gmail.com" );
    assertIsTrue( "d23d@da9.co9" );
    assertIsTrue( "d_oy_smith@gmail.com" );
    assertIsTrue( "dasd-dasd@das.com.das" );
    assertIsTrue( "dasd.dadas@dasd.com" );
    assertIsTrue( "dasd_-@jdas.com" );
    assertIsTrue( "david.jones@proseware.com" );
    assertIsTrue( "dda_das@das-dasd.com" );
    assertIsTrue( "digit-only-domain-with-subdomain@sub.123.com" );
    assertIsTrue( "digit-only-domain@123.com" );
    assertIsTrue( "doysmith@gmail.com" );
    assertIsTrue( "drp@drp.cz" );
    assertIsTrue( "dsq!a?@das.com" );
    assertIsTrue( "example@example.co" );
    assertIsTrue( "f.f.f@bde.cc" );
    assertIsTrue( "f.o.o.b.a.r@gmail.com" );
    assertIsTrue( "first-name-last-name@d-a-n.com" );
    assertIsTrue( "firstname+lastname@domain.com" );
    assertIsTrue( "firstname-lastname@domain.com" );
    assertIsTrue( "firstname.lastname@domain.com" );
    assertIsTrue( "foo\\@bar@machine.subdomain.example.museum" );
    assertIsTrue( "futureTLD@somewhere.fooo" );
    assertIsTrue( "hello.me_1@email.com" );
    assertIsTrue( "hello7___@ca.com.pt" );
    assertIsTrue( "info@ermaelan.com" );
    assertIsTrue( "j.s@server1.proseware.com" );
    assertIsTrue( "j@proseware.com9" );
    assertIsTrue( "j_9@[129.126.118.1]" );
    assertIsTrue( "jinujawad6s@gmail.com" );
    assertIsTrue( "journaldev+100@gmail.com" );
    assertIsTrue( "journaldev-100@journaldev.net" );
    assertIsTrue( "journaldev-100@yahoo-test.com" );
    assertIsTrue( "journaldev-100@yahoo.com" );
    assertIsTrue( "journaldev.100@journaldev.com.au" );
    assertIsTrue( "journaldev.100@yahoo.com" );
    assertIsTrue( "journaldev111@journaldev.com" );
    assertIsTrue( "journaldev@1.com" );
    assertIsTrue( "journaldev@gmail.com.com" );
    assertIsTrue( "journaldev@yahoo.com" );
    assertIsTrue( "journaldev_100@yahoo-test.ABC.CoM" );
    assertIsTrue( "js#internal@proseware.com" );
    assertIsTrue( "js*@proseware.com" );
    assertIsTrue( "js@proseware.com9" );
    assertIsTrue( "me+100@me.com" );
    assertIsTrue( "me+alpha@example.com" );
    assertIsTrue( "me-100@me.com" );
    assertIsTrue( "me-100@me.com.au" );
    assertIsTrue( "me-100@yahoo-test.com" );
    assertIsTrue( "me.100@me.com" );
    assertIsTrue( "me@aaronsw.com" );
    assertIsTrue( "me@company.co.uk" );
    assertIsTrue( "me@gmail.com" );
    assertIsTrue( "me@gmail.com" );
    assertIsTrue( "me@mail.s2.example.com" );
    assertIsTrue( "me@me.cu.uk" );
    assertIsTrue( "my.ownsite@ourearth.org" );
    assertIsFalse( "myemail@sample" );
    assertIsTrue( "myemail@sample.com" );
    assertIsTrue( "mysite@you.me.net" );
    assertIsTrue( "o'hare@example.com" );
    assertIsTrue( "peter.example@domain.comau" );
    assertIsTrue( "peter.piper@example.com" );
    assertIsTrue( "peter_123@news.com" );
    assertIsTrue( "pio^_pio@factory.com" );
    assertIsTrue( "pio_#pio@factory.com" );
    assertIsTrue( "pio_pio@factory.com" );
    assertIsTrue( "pio_~pio@factory.com" );
    assertIsTrue( "piskvor@example.lighting" );
    assertIsTrue( "rss-dev@yahoogroups.com" );
    assertIsTrue( "someone+tag@somewhere.net" );
    assertIsTrue( "someone@somewhere.co.uk" );
    assertIsTrue( "someone@somewhere.com" );
    assertIsTrue( "something_valid@somewhere.tld" );
    assertIsTrue( "tvf@tvf.cz" );
    assertIsTrue( "user#@domain.co.in" );
    assertIsTrue( "user'name@domain.co.in" );
    assertIsTrue( "user+mailbox@example.com" );
    assertIsTrue( "user-name@domain.co.in" );
    assertIsTrue( "user.name@domain.com" );
    assertIsFalse( ".user.name@domain.com" );
    assertIsFalse( "user-name@domain.com." );
    assertIsFalse( "username@.com" );
    assertIsTrue( "user1@domain.com" );
    assertIsTrue( "user?name@domain.co.in" );
    assertIsTrue( "user@domain.co.in" );
    assertIsTrue( "user@domain.com" );
    assertIsFalse( "user@domaincom" );
    assertIsTrue( "user_name@domain.co.in" );
    assertIsTrue( "user_name@domain.com" );
    assertIsTrue( "username@yahoo.corporate" );
    assertIsTrue( "username@yahoo.corporate.in" );
    assertIsTrue( "username+something@domain.com" );
    assertIsTrue( "vdv@dyomedea.com" );
    assertIsTrue( "xxxx@gmail.com" );
    assertIsTrue( "xxxxxx@yahoo.com" );
    assertIsFalse( "first;name)lastname@domain.com(blah" );
    assertIsTrue( "user@[IPv6:2001:DB8::1]" );
    assertIsFalse( "user@localserver" );
    assertIsTrue( "w.b.f@test.com" );
    assertIsTrue( "w.b.f@test.museum" );
    assertIsTrue( "yoursite@ourearth.com" );
    assertIsTrue( "~pio_pio@factory.com" );

    assertIsTrue( "{^c\\@**Dog^}@cartoon.com" );
    assertIsFalse( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" );
    assertIsFalse( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" );
    assertIsTrue( "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" );
    assertIsTrue( "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" );
    assertIsFalse( "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" );
    assertIsTrue( "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com" );

    assertIsTrue( "valid@[1.1.1.1]" );
    assertIsTrue( "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]" );
    assertIsTrue( "valid.ipv6v4.addr@[IPv6:::12.34.56.78]" );
    assertIsTrue( "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]" );
    assertIsTrue( "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]" );
    assertIsTrue( "valid.ipv6.addr@[IPv6:::]" );
    assertIsTrue( "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]" );
    assertIsTrue( "valid.ipv6.addr@[IPv6:::12.34.56.78]" );
    assertIsTrue( "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]" );
    assertIsTrue( "valid.ipv6.addr@[IPv6:0::1]" );
    assertIsTrue( "valid.ipv4.addr@[255.255.255.255]" );
    assertIsTrue( "valid.ipv4.addr@[123.1.72.10]" );

    assertIsFalse( "\"invalid-qstring@example.com" ); // unterminated q-string in local-part of the addr-spec);  
    assertIsFalse( "\"locál-part\"@example.com" ); // international local-part when allowInternational=false should fail);
    assertIsFalse( "invalid @" );
    assertIsFalse( "invalid" );
    assertIsFalse( "invalid@" );
    assertIsFalse( "invalid@[10.1.52]" );
    assertIsFalse( "invalid@[10.1]" );
    assertIsFalse( "invalid@[10]" );
    assertIsFalse( "invalid@[111.111.111.111" ); // unenclosed IPv4 literal);
    assertIsFalse( "invalid@[127.0.0.1.]" );
    assertIsFalse( "invalid@[127.0.0.1]." );
    assertIsFalse( "invalid@[127.0.0.1]x" );
    assertIsFalse( "invalid@[256.256.256.256]" );
    assertIsFalse( "invalid@[555.666.777.888]" );
    assertIsFalse( "invalid@[IPv6:1111:1111]" ); // incomplete IPv6);
    assertIsFalse( "invalid@[IPv6:1111::1111::1111]" ); // invalid IPv6-comp);
    assertIsFalse( "invalid@[IPv6:1111:::1111::1111]" ); // more than 2 consecutive :'s in IPv6);
    assertIsFalse( "invalid@[IPv6:123456]" );
    assertIsFalse( "invalid@[IPv6:1::2:]" ); // incomplete IPv6);
    assertIsFalse( "invalid@[IPv6:2607:f0d0:1002:51::4" ); // unenclosed IPv6 literal);
    assertIsFalse( "invalid@[IPv6::1::1]" );
    assertIsFalse( "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]" ); // invalid IPv4 address in IPv6v4);
    assertIsFalse( "invalid@[]" ); // empty IP literal);
    assertIsFalse( "invalid@domain1.com@domain2.com" );
  }

  private static void runTestEmailValidator4J()
  {
    wlHeadline( "https://github.com/egulias/EmailValidator4J" );

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
    assertIsFalse( "\"test\\\"@iana.org" );
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
    assertIsTrue( "exam\\ ple@example.com" );
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
    assertIsTrue( "\"\\\"\"@iana.org" );
    assertIsTrue( "example@localhost" );
  }

  private static void runTestWikipedia()
  {
    wlHeadline( "https://en.wikipedia.org/wiki/Email_address/" );

    assertIsTrue( "MaxMuster(Kommentar)@example.com" );
    assertIsTrue( "\"MaxMustermann\"@example.com" );
    assertIsTrue( "Max.\"Musterjunge\".Mustermann@example.com" );
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
    assertIsTrue( "mailhost!username@example.org" );
    assertIsTrue( "user%example.com@example.org" );
    assertIsTrue( "joe25317@NOSPAMexample.com" );
    assertIsTrue( "Peter.Zapfl@Telekom.DBP.De" );
    assertIsTrue( "nama@contoh.com" );
    assertIsFalse( "\"John Smith\" (johnsmith@example.com)" ); // ?
    assertIsFalse( "just\"not\"right@example.com" );
    assertIsFalse( "this is\"not\\allowed@example.com" );
    assertIsFalse( "this\\ still\\\"not\\\\allowed@example.com" );
    assertIsTrue( "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com" );
    assertIsTrue( "(buero)office@example.com" );
    assertIsTrue( "office(etage-3)@example.com" );
    assertIsFalse( "off(kommentar)ice@example.com" );
    assertIsTrue( "\"(buero)office\"@example.com" );
    assertIsTrue( "\"office(etage-3)\"@example.com" );
    assertIsTrue( "\"off(kommentar)ice\"@example.com" );
    assertIsTrue( "\"address(comment)\"@example.com" );
    assertIsTrue( "Buero <office@example.com>" );
    assertIsTrue( "\"vorname(Kommentar).nachname\"@provider.de" );
    assertIsTrue( "\"Herr \\\"Kaiser\\\" Franz Beckenbauer\" <local-part@domain-part.com>" );
    assertIsTrue( "Erwin Empfaenger <erwin@example.com>" );
  }

  private static void runTestLength()
  {
    wlHeadline( "Length" );

    String str_50 = "12345678901234567890123456789012345678901234567890";
    String str_63 = "A23456789012345678901234567890123456789012345678901234567890123";
    //               1234567890          1234567890          1234567890          1234567890          
    String str_64 = "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz";

    assertIsTrue( "A@B.CD" );
    assertIsFalse( "A@B.C" );
    assertIsFalse( "A@COM" );

    assertIsTrue( "ABC.DEF@GHI.JKL" );
    assertIsTrue( "ABC.DEF@GHI." + str_63 );
    assertIsFalse( "ABC.DEF@GHI." + str_63 + "A" );

    assertIsTrue( "A@B.CD" );

    assertIsTrue( str_64 + "@ZZZZZZZZZX.ZL" );
    assertIsFalse( str_64 + "T@ZZZZZZZZZX.ZL" );

    assertIsTrue( "True64 <" + str_64 + "@domain.tld>" );
    assertIsFalse( "False64 <" + str_64 + "T@domain.tld>" );

    assertIsTrue( "<" + str_64 + "@domain.tld> True64 " );
    assertIsFalse( "<" + str_64 + "T@domain.tld> False64 " );

    assertIsTrue( "<" + str_64 + "@domain.tld>" );
    assertIsFalse( "<" + str_64 + "T@domain.tld>" );

    assertIsTrue( "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" );
    assertIsTrue( "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" );
    assertIsTrue( "1234567890123456789012345678901234567890123456789012345678901234@example.com" );
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

    assertIsTrue( "" + str_50 + " " + str_50 + " " + str_50 + " " + str_50 + " " + str_50.substring( 0, 38 ) + "<A@B.de.com>" );

    assertIsTrue( "<63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test OK3" );
    assertIsTrue( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test OK4" );
    assertIsFalse( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + ".com> eMail Test FALSE3" );
    assertIsFalse( "<" + str_50.substring( 0, 40 ) + "@" + str_63 + "." + str_63 + "." + str_63 + "A.com> eMail Test FALSE4" );

    assertIsTrue( "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com" );
    assertIsFalse( "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" );
    assertIsFalse( "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" );
    assertIsFalse( "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" );
    assertIsTrue( "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" );

    assertIsTrue( "email@domain.topleveldomain" );
    assertIsTrue( "email@email.email.mydomain" );
  }

  private static void runTestDisplayName()
  {
    wlHeadline( "Display Name" );

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
    assertIsFalse( "<" );
    assertIsFalse( ">" );
    assertIsFalse( "<         >" );
    assertIsFalse( "< <     > >" );
    assertIsTrue( "<ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<.ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<ABC.DEF@GHI.JKL.>" );

    assertIsTrue( "<-ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<ABC.DEF@GHI.JKL->" );

    assertIsTrue( "<_ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<ABC.DEF@GHI.JKL_>" );

    assertIsTrue( "<(Comment)ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<(Comment).ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<.(Comment)ABC.DEF@GHI.JKL>" );
    assertIsTrue( "<(Comment)-ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<-(Comment)ABC.DEF@GHI.JKL>" );
    assertIsTrue( "<(Comment)_ABC.DEF@GHI.JKL>" );
    assertIsFalse( "<_(Comment)ABC.DEF@GHI.JKL>" );

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

    assertIsFalse( "email.adress@domain.com <display name>" );
    assertIsFalse( "email.adress@domain.com <email.adress@domain.com>" );
    assertIsFalse( "display.name@false.com <email.adress@correct.com>" );
    assertIsFalse( "<email>.<adress>@domain.com" );
    assertIsFalse( "<email>.<adress> email.adress@domain.com" );
  }

  private static void runTestComments()
  {
    wlHeadline( "Comments" );

    assertIsFalse( "escape.character.at.input.end@domain.com (comment \\" );

    assertIsTrue( "(comment)local.part.with.comment.at.start@domain.com" );
    assertIsTrue( "(comment \\\"string1\\\" \\\"string2) is.not.closed@domain.com" );
    assertIsTrue( "(comment) local.part.with.space.after.comment.at.start@domain.com" );
    assertIsFalse( "(comment)at.start.and.end@domain.com(comment end)" );
    assertIsFalse( "(two.comments)in.the(local.part)@domain.com" );
    assertIsFalse( "(nested(comment))in.the.local.part@domain.com" );
    assertIsTrue( "local.part.with.comment.before(at.sign)@domain.com" );
    assertIsFalse( "local.part.with.comment.before(at.sign.and.spaces.after.comment)    @domain.com" );
    assertIsFalse( "(local.part.with) (two.comments.with.space.after)  comment@domain.com" );
    assertIsFalse( "(local.part.with) (two.comments.with.space.after.first).comment@domain.com" );
    assertIsTrue( "domain.part.with.comment.at.the.end@domain.com(comment)" );
    assertIsFalse( "comment.not(closed@domain.com" );
    assertIsFalse( "comment.not.startet@do)main.com" );
    assertIsFalse( ")comment.close.bracket.at.start@domain.com" );
    assertIsFalse( "comment.close.bracket.before.at.sign)@domain.com" );
    assertIsTrue( "ip4.with.comment.after.at.sign.at.input.start@(without.space)[1.2.3.4]" );
    assertIsTrue( "ip4.with.comment.after.at.sign.at.input.end@[1.2.3.4](without.space)" );
    assertIsTrue( "ip4.with.comment.after.at.sign.at.input.end@[1.2.3.4]  (with.space.before.comment)" );
    assertIsFalse( "ip4.with.comment.after.at.sign@(with.space) [1.2.3.4]" );
    assertIsFalse( "ip4.with.embedded.comment.in.ip4@[1.2.3(comment).4]" );
    assertIsFalse( "()()()three.consecutive.empty.comments.at.email.start@domain.com" );
    assertIsTrue( "morse.code.in.comment(... .... .. -)@storm.de" );
    assertIsTrue( "(comment)          \"string\".name1@domain.tld" );
    assertIsFalse( "(comment) Error )  \"string\".name1@domain.tld" );
    assertIsFalse( "(comment(nested Comment)) nested.comments.not.supported@domain.com" );

    assertIsFalse( ")                  \"string\".name1@domain.tld" );
    assertIsFalse( ")))))              \"string\".name1@domain.tld" );
    assertIsFalse( "())                \"string\".name1@domain.tld" );
    assertIsFalse( "   ())             \"string\".name1@domain.tld" );
    assertIsFalse( "(input.is.only.one.comment)" );
    assertIsFalse( "  (input.is.only.one.comment.with.leading.spaces)" );
    assertIsFalse( "(input.is.only.one.comment.with.trailing.spaces)    " );
    assertIsFalse( "(comment)  ." );
    assertIsFalse( "(comment.space.point.space) . " );
    assertIsFalse( "domain.part.with.comment.but.spaces.after.comment@domain.com(comment)    " );
    assertIsTrue( "domain.part.with.comment@(comment)domain.com" );
    assertIsTrue( "domain.part.with.comment@(and.at.sgin.in.comment.@.)domain.com" );
    assertIsTrue( "domain.part.with.comment@(and.escaped.at.sgin.in.comment.\\@.)domain.com" );
    assertIsFalse( "ABC.DEF@(GHI)   JKL.MNO" );
    assertIsFalse( "ABC.DEF@(GHI.)   JKL.MNO" );
    assertIsFalse( "ABC.DEF@(GHI.) (ABC)JKL.MNO" );
    assertIsFalse( "ABC.DEF@(GHI().()ABC)JKL.MNO" );
    assertIsFalse( "ABC.DEF@(GHI.)   JKL(MNO)" );
    assertIsTrue( "ABC.DEF@GHI.JKL      (MNO)" );
    assertIsFalse( "ABC.DEF@             (MNO)" );
    assertIsFalse( "ABC.DEF@   .         (MNO)" );
    assertIsFalse( "ABC.DEF              (MNO)" );
    assertIsFalse( "ABC.DEF@GHI.         (MNO)" );
    assertIsFalse( "ABC.DEF@GHI.JKL       MNO" );
    assertIsFalse( "ABC.DEF@GHI.JKL          " );
    assertIsFalse( "ABC.DEF@GHI.JKL       .  " );

    assertIsFalse( "(" );
    assertIsFalse( "(         )" );
    assertIsFalse( ")" );
    assertIsTrue( "ABC.DEF@GHI.JKL ()" );
    assertIsTrue( "ABC.DEF@GHI.JKL()" );
    assertIsTrue( "ABC.DEF@()GHI.JKL" );
    assertIsTrue( "ABC.DEF()@GHI.JKL" );
    assertIsTrue( "()ABC.DEF@GHI.JKL" );
    assertIsFalse( "ABC.DEF@(GHI.JKL)" );
    assertIsFalse( "ABC.DEF@GHI.JKL ()()" );
    assertIsFalse( "(ABC)(DEF)@GHI.JKL" );
    assertIsFalse( "(ABC()DEF)@GHI.JKL" );
    assertIsFalse( "(ABC(Z)DEF)@GHI.JKL" );
    assertIsFalse( "(ABC).(DEF)@GHI.JKL" );
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
    assertIsFalse( "ABC(DEF@GHI).JKL" );
    assertIsFalse( "ABC(DEF.GHI).JKL" );
    assertIsFalse( "(ABC.DEF@GHI.JKL)" );
    assertIsFalse( "(A(B(C)DEF@GHI.JKL" );
    assertIsFalse( "(A)B)C)DEF@GHI.JKL" );
    assertIsFalse( "(A)BCDE(F)@GHI.JKL" );
    assertIsFalse( "ABC.DEF@(GH)I.JK(LM)" );
    assertIsFalse( "ABC.DEF@(GH(I.JK)L)M" );

    assertIsTrue( "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]" );
    assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)" );
    assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)" );
    assertIsFalse( "(Comment).ABC.DEF@GHI.JKL" );
    assertIsTrue( "(Comment)-ABC.DEF@GHI.JKL" );
    assertIsTrue( "(Comment)_ABC.DEF@GHI.JKL" );
    assertIsFalse( "-(Comment)ABC.DEF@GHI.JKL" );
    assertIsFalse( ".(Comment)ABC.DEF@GHI.JKL" );

    assertIsFalse( "a@abc(bananas)def.com" );

    assertIsTrue( "\"address(comment\"@example.com" );
    assertIsFalse( "address(co\"mm\"ent)@example.com" );
    assertIsFalse( "address(co\"mment)@example.com" );

    assertIsFalse( "test@test.com(comment" );
  }

  private static void runTestStrings()
  {
    wlHeadline( "Strings" );

    assertIsTrue( "\"local.part.only.string\"@domain.com" );
    assertIsTrue( "\"&lt; &clubs; &diams; &hearts; &spades; experiment &gt;\"@domain.com" );
    assertIsTrue( "\"local.part\".\"two.strings\"@domain.com" );
    assertIsFalse( "-\"hyphen.before.string\"@domain.com" );
    assertIsFalse( "\"hyphen.after.string\"-.\"string2\"@domain.com" );
    assertIsFalse( "\"hyphen.before.string2\".-\"string2\"@domain.com" );
    assertIsFalse( ".\"point.before.string\".\"string2\"@domain.com" );
    assertIsTrue( "\"space in string\"@domain.com" );
    assertIsTrue( "\"at.sign@in.string\"@domain.com" );
    assertIsTrue( "\"escaped.qoute.in\\\"string\"@domain.com" );
    assertIsTrue( "\"escaped.at.sign\\@in.string\"@domain.com" );
    assertIsTrue( "\"escaped.sign.\'in.string\"@domain.com" );
    assertIsTrue( "\"escaped.back.slash\\\\in.string\"@domain.com" );
    assertIsFalse( "\"\"@empty.string.domain.com" );
    assertIsFalse( "\"missplaced.end.of.string@do\"main.com" );
    assertIsFalse( "domain.part.is.string@\"domain.com\"" );
    assertIsFalse( "not.closed.string.in.domain.part.version1@\"domain.com" );
    assertIsFalse( "not.closed.string.in.domain.part.version2@do\"main.com" );
    assertIsFalse( "not.closed.string.in.domain.part.version3@domain.com\"" );
    assertIsFalse( "string.in.domain.part4@do\"main.com\"" );
    assertIsFalse( "string.in.domain.part5@do\"main.com" );
    assertIsFalse( "embedded.string.in.domain.part@do\"ma\"in.com" );
    assertIsFalse( "\"@missplaced.start.of.string" );
    assertIsFalse( "no.at.sign.and.no.domain.part.\"" );
    assertIsFalse( "domain.part.is.empty.string@\"\"" );
    assertIsFalse( "\"no.email.adress.only.string\"" );
    assertIsFalse( "\"no.email.adress.only.string.and.missplaced.backslash\\" );

    assertIsFalse( "no.email.adress\"with.string.start" );
    assertIsFalse( "string.starts.before.at.sign\"@domain.com" );
    assertIsFalse( "string.starts.before.at.sign\"but.with.caracters.before.at.sign@domain.com" );
    assertIsFalse( "ABC.DEF.\"@GHI.DE" );

    assertIsFalse( "\"\".email.starts.with.empty.string@domain.com" );
    assertIsTrue( "\"string.with.double..point\"@domain.com" );
    assertIsTrue( "string.with.\"(comment)\".in.string@domain.com" );
    assertIsTrue( "string.with.brackets.\")()(\".in.string@domain.com" );
    assertIsTrue( "\"string.with.\\\".\\\".point\"@domain.com" );
    assertIsTrue( "\"string.with.embedded.empty.\\\"\\\".string\"@domain.com" );
    assertIsTrue( "\"embedded.string.with.space.and.escaped.\\\" \\@ \\\".at.sign\"@domain.com" );

    assertIsFalse( "\"string.is.not.closed@domain.com" );
    assertIsFalse( "\"whole.email.adress.is.string@domain.com\"" );
    assertIsTrue( "\".ABC.DEF\"@GHI.DE" );
    assertIsTrue( "\"ABC.DEF.\"@GHI.DE" );
    assertIsTrue( "\"ABC\".DEF.\"GHI\"@JKL.de" );
    assertIsFalse( "A\"BC\".DEF.\"GHI\"@JKL.de" );
    assertIsFalse( "\"ABC\".DEF.G\"HI\"@JKL.de" );
    assertIsFalse( "\"AB\"C.DEF.\"GHI\"@JKL.de" );
    assertIsFalse( "\"ABC\".DEF.\"GHI\"J@KL.de" );
    assertIsFalse( "\"AB\"C.D\"EF\"@GHI.DE" );
    assertIsFalse( "A\"B\"C.D\"E\"F@GHI.DE" );
    assertIsFalse( "ABC.DEF@G\"H\"I.DE" );

    assertIsFalse( "\"\".\"\".ABC.DEF@GHI.DE" );
    assertIsFalse( "\"\"\"\"ABC.DEF@GHI.DE" );
    assertIsFalse( "AB\"\"\"\"C.DEF@GHI.DE" );
    assertIsFalse( "ABC.DEF@G\"\"\"\"HI.DE" );
    assertIsFalse( "ABC.DEF@GHI.D\"\"\"\"E" );
    assertIsFalse( "ABC.DEF@GHI.D\"\".\"\"E" );
    assertIsFalse( "ABC.DEF@GHI.\"\"\"\"DE" );
    assertIsFalse( "ABC.DEF@GHI.\"\".\"\"DE" );
    assertIsFalse( "ABC.DEF@GHI.D\"\"E" );

    assertIsFalse( "0\"00.000\"@domain.com" );

    assertIsTrue( "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de" );
    assertIsFalse( "\"A[B]C\".D(E)F.\"GHI\"@JKL.de" );
    assertIsFalse( "\"A[B]C\".D[E]F.\"GHI\"@JKL.de" );
    assertIsFalse( "\"A[B]C\".D<E>F.\"GHI\"@JKL.de" );
    assertIsTrue( "\"()<>[]:.;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org" );

    assertIsTrue( "\"ABC \\\"\\\\\\\" !\".DEF@GHI.DE" );
  }

  private static void runTestIP6()
  {
    wlHeadline( "IP V6" );

    assertIsTrue( "ABC.DEF@[IPv6:2001:db8::1]" );
    assertIsFalse( "ABC.DEF@[IP" );
    assertIsFalse( "ABC.DEF@[IPv6]" );

    assertIsFalse( "ABC.DEF@[IPv6:]" );
    assertIsFalse( "ABC.DEF@[IPv6:" );
    assertIsFalse( "ABC.DEF@[IPv6::]" );
    assertIsFalse( "ABC.DEF@[IPv6::" );
    assertIsFalse( "ABC.DEF@[IPv6:::::...]" );
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
    assertIsFalse( "ABC.DEF@([IPv6:1:2:3:4:5:6])" );

    assertIsFalse( "ABC.DEF@[IPv6:1:-2:3:4:5:]" );

    assertIsFalse( "ip.v6.with.semicolon@[IPv6:1:2;2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.semicolon@[IPv6:1:22;:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.semicolon@[IPv6:1:22:;3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.semicolon@[IPv6:1:22:3:4:5:6:7;]" );
    assertIsFalse( "ip.v6.with.semicolon@[IPv6:1:22:3:4:5:6:7];" );
    assertIsFalse( "ip.v6.with.semicolon@;[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.semicolon@[;IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]" );
    assertIsFalse( "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]." );
    assertIsFalse( "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dot@[.IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:2..2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22..:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22:..3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7..]" );
    assertIsFalse( "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7].." );
    assertIsFalse( "ip.v6.with.double.dot@..[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.dot@[..IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]" );
    assertIsFalse( "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&" );
    assertIsFalse( "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.amp@[&IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]" );
    assertIsFalse( "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*" );
    assertIsFalse( "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.asterisk@[*IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]" );
    assertIsFalse( "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_" );
    assertIsFalse( "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.underscore@[_IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]" );
    assertIsFalse( "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$" );
    assertIsFalse( "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.dollar@[$IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]" );
    assertIsFalse( "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]=" );
    assertIsFalse( "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.equality@[=IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]" );
    assertIsFalse( "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!" );
    assertIsFalse( "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.exclamation@[!IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]" );
    assertIsFalse( "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?" );
    assertIsFalse( "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.question@[?IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]" );
    assertIsFalse( "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`" );
    assertIsFalse( "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.grave-accent@[`IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]" );
    assertIsFalse( "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#" );
    assertIsFalse( "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hash@[#IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]" );
    assertIsFalse( "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%" );
    assertIsFalse( "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.percentage@[%IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]" );
    assertIsFalse( "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|" );
    assertIsFalse( "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.pipe@[|IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]" );
    assertIsFalse( "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+" );
    assertIsFalse( "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.plus@[+IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{" );
    assertIsFalse( "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[{IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}" );
    assertIsFalse( "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[}IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7](" );
    assertIsFalse( "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[(IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])" );
    assertIsFalse( "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[)IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]" );
    assertIsFalse( "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7][" );
    assertIsFalse( "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]" );
    assertIsFalse( "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.rightbracket@[]IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]" );
    assertIsTrue( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()" );
    assertIsTrue( "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[()IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}" );
    assertIsFalse( "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[{}IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[[]IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>" );
    assertIsFalse( "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.bracket@[<>IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]" );
    assertIsFalse( "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])(" );
    assertIsFalse( "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket1@[)(IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]" );
    assertIsFalse( "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{" );
    assertIsFalse( "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket2@[}{IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]" );
    assertIsFalse( "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><" );
    assertIsFalse( "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.false.bracket4@[><IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]" );
    assertIsFalse( "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<" );
    assertIsFalse( "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.lower.than@[<IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]" );
    assertIsFalse( "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>" );
    assertIsFalse( "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.greater.than@[>IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]" );
    assertIsFalse( "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~" );
    assertIsFalse( "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.tilde@[~IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]" );
    assertIsFalse( "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^" );
    assertIsFalse( "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.xor@[^IPv6:1:22:3:4:5:6:7]" );

    assertIsTrue( "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]" );
    assertIsFalse( "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:" );
    assertIsFalse( "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.colon@[:IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]" );
    assertIsFalse( "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] " );
    assertIsFalse( "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.space@[ IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]" );
    assertIsFalse( "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7]," );
    assertIsFalse( "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.comma@[,IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]" );
    assertIsFalse( "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@" );
    assertIsFalse( "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.at@[@IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]" );
    assertIsFalse( "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§" );
    assertIsFalse( "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.paragraph@[§IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']" );
    assertIsFalse( "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'" );
    assertIsFalse( "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.quote@['IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]" );
    assertIsFalse( "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/" );
    assertIsFalse( "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]" );
    assertIsFalse( "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-" );
    assertIsFalse( "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]" );
    assertIsFalse( "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"" );
    assertIsFalse( "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]" );
    assertIsFalse( "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b" );
    assertIsFalse( "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]" );
    assertIsFalse( "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\"" );
    assertIsFalse( "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]" );
    assertIsFalse( "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\"" );
    assertIsFalse( "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]" );

    assertIsTrue( "ip.v6.with.number0@[IPv6:1:202:3:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.number0@[IPv6:1:220:3:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.number0@[IPv6:1:22:03:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:70]" );
    assertIsFalse( "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:7]0" );
    assertIsFalse( "ip.v6.with.number0@0[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.number0@[0IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.number9@[IPv6:1:292:3:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.number9@[IPv6:1:229:3:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.number9@[IPv6:1:22:93:4:5:6:7]" );
    assertIsTrue( "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:79]" );
    assertIsFalse( "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:7]9" );
    assertIsFalse( "ip.v6.with.number9@9[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.number9@[9IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]" );
    assertIsFalse( "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789" );
    assertIsFalse( "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.numbers@[0123456789IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7999]" );
    assertIsFalse( "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]999" );
    assertIsFalse( "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]" );
    assertIsFalse( "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz" );
    assertIsFalse( "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]" );

    assertIsFalse( "ip.v6.with.slash@[IPv6:1:2\\2:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.slash@[IPv6:1:22\\:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:\\3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\\]" );
    assertIsFalse( "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\\" );
    assertIsFalse( "ip.v6.with.slash@\\[IPv6:1:22:3:4:5:6:7]" );
    assertIsFalse( "ip.v6.with.slash@[\\IPv6:1:22:3:4:5:6:7]" );

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
    assertIsFalse( "ABC.DEF@[IPv6::FFFF:-127.0.0.1]" );
    assertIsFalse( "ABC.DEF@[IPv6::FFFF:127.0.-0.1]" );
    assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.999]" );
    assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.0.0001]" );
    assertIsFalse( "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]" );
  }

  private static void runTestIP4()
  {
    wlHeadline( "IP V4" );

    assertIsFalse( "\"\"@[]" );
    assertIsFalse( "\"\"@[1" );
    assertIsFalse( "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})" );
    assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
    assertIsFalse( "1.2.3.4]@[5.6.7.8]" );
    assertIsFalse( "[1.2.3.4@[5.6.7.8]" );
    assertIsFalse( "[1.2.3.4][5.6.7.8]@[9.10.11.12]" );
    assertIsFalse( "[1.2.3.4]@[5.6.7.8][9.10.11.12]" );
    assertIsFalse( "[1.2.3.4]@[5.6.7.8]9.10.11.12]" );
    assertIsFalse( "[1.2.3.4]@[5.6.7.8][9.10.11.12[" );

    assertIsTrue( "ip4.in.local.part.as.string1.\"[1.2.3.4]\"@[5.6.7.8]" );
    assertIsTrue( "ip4.in.local.part.as.string2.\"@[1.2.3.4]\"@[5.6.7.8]" );
    assertIsFalse( "ip4.ends.with.alpha.character1@[1.2.3.Z]" );
    assertIsFalse( "ip4.ends.with.alpha.character2@[1.2.3.]Z" );
    assertIsFalse( "ip4.ends.with.top.level.domain@[1.2.3.].de" );

    assertIsFalse( "ip4.with.double.ip4@[1.2.3.4][5.6.7.8]" );

    assertIsFalse( "ip4.with.ip4.in.comment1@([1.2.3.4])" );
    assertIsFalse( "ip4.with.ip4.in.comment2@([1.2.3.4])[5.6.7.8]" );
    assertIsFalse( "ip4.with.ip4.in.comment3@[1.2.3.4]([5.6.7.8])" );

    assertIsTrue( "ip4.with.ip4.in.comment4@[1.2.3.4] (@)" );
    assertIsTrue( "ip4.with.ip4.in.comment5@[1.2.3.4] (@.)" );

    assertIsFalse( "ip4.with.hex.numbers@[AB.CD.EF.EA]" );
    assertIsFalse( "ip4.with.hex.number.overflow@[AB.CD.EF.FF1]" );

    assertIsFalse( "ip4.with.double.brackets@[1.2.3.4][5.6.7.8]" );
    assertIsFalse( "ip4.missing.at.sign[1.2.3.4]" );
    assertIsFalse( "ip4.missing.the.start.bracket@]" );
    assertIsFalse( "ip4.missing.the.end.bracket@[" );
    assertIsFalse( "ip4.missing.the.start.bracket@1.2.3.4]" );
    assertIsFalse( "ip4.missing.the.end.bracket@[1.2.3.4" );

    assertIsFalse( "ip4.missing.numbers.and.the.start.bracket@...]" );
    assertIsFalse( "ip4.missing.numbers.and.the.end.bracket@[..." );
    assertIsFalse( "ip4.missplaced.start.bracket1[@1.2.3.4]" );

    assertIsFalse( "ip4.missing.the.first.number@[.2.3.4]" );
    assertIsFalse( "ip4.missing.the.last.number@[1.2.3.]" );
    assertIsFalse( "ip4.last.number.is.space@[1.2.3. ]" );

    assertIsFalse( "ip4.with.only.one.numberABC.DEF@[1]" );
    assertIsFalse( "ip4.with.only.two.numbers@[1.2]" );
    assertIsFalse( "ip4.with.only.three.numbers@[1.2.3]" );
    assertIsFalse( "ip4.with.five.numbers@[1.2.3.4.5]" );
    assertIsFalse( "ip4.with.six.numbers@[1.2.3.4.5.6]" );
    assertIsFalse( "ip4.with.byte.overflow1@[1.2.3.256]" );
    assertIsFalse( "ip4.with.byte.overflow2@[1.2.3.1000]" );
    assertIsFalse( "ip4.with.to.many.leading.zeros@[0001.000002.000003.00000004]" );
    assertIsTrue( "ip4.with.two.leading.zeros@[001.002.003.004]" );
    assertIsTrue( "ip4.zero@[0.0.0.0]" );
    assertIsTrue( "ip4.correct1@[1.2.3.4]" );
    assertIsTrue( "ip4.correct2@[255.255.255.255]" );
    assertIsTrue( "\"ip4.local.part.as.string\"@[127.0.0.1]" );
    assertIsTrue( "\"    \"@[1.2.3.4]" );
    assertIsFalse( "ip4.no.email.adress[1.2.3.4]  but.with.space[1.2.3.4]" );

    assertIsFalse( "ip4.with.negative.number1@[-1.2.3.4]" );
    assertIsFalse( "ip4.with.negative.number2@[1.-2.3.4]" );
    assertIsFalse( "ip4.with.negative.number3@[1.2.-3.4]" );
    assertIsFalse( "ip4.with.negative.number4@[1.2.3.-4]" );

    assertIsFalse( "ip4.with.only.empty.brackets@[]" );
    assertIsFalse( "ip4.with.three.empty.brackets@[][][]" );
    assertIsFalse( "ip4.with.wrong.characters.in.brackets@[{][})][}][}\\\"]" );
    assertIsFalse( "ip4.in.false.brackets@{1.2.3.4}" );

    assertIsFalse( "ip4.with.semicolon.between.numbers@[123.14;5.178.90]" );
    assertIsFalse( "ip4.with.semicolon.before.point@[123.145;.178.90]" );
    assertIsFalse( "ip4.with.semicolon.after.point@[123.145.;178.90]" );
    assertIsFalse( "ip4.with.semicolon.before.start.bracket@;[123.145.178.90]" );
    assertIsFalse( "ip4.with.semicolon.after.start.bracket@[;123.145.178.90]" );
    assertIsFalse( "ip4.with.semicolon.before.end.bracket@[123.145.178.90;]" );
    assertIsFalse( "ip4.with.semicolon.after.end.bracket@[123.145.178.90];" );

    assertIsFalse( "ip4.with.only.one.dot.in.brackets@[.]" );
    assertIsFalse( "ip4.with.only.double.dot.in.brackets@[..]" );
    assertIsFalse( "ip4.with.only.triple.dot.in.brackets@[...]" );
    assertIsFalse( "ip4.with.only.four.dots.in.brackets@[....]" );
    assertIsFalse( "ip4.with.false.consecutive.points@[1.2...3.4]" );

    assertIsFalse( "ip4.with.dot.between.numbers@[123.14.5.178.90]" );
    assertIsFalse( "ip4.with.dot.before.point@[123.145..178.90]" );
    assertIsFalse( "ip4.with.dot.after.point@[123.145..178.90]" );
    assertIsFalse( "ip4.with.dot.before.start.bracket@.[123.145.178.90]" );
    assertIsFalse( "ip4.with.dot.after.start.bracket@[.123.145.178.90]" );
    assertIsFalse( "ip4.with.dot.before.end.bracket@[123.145.178.90.]" );
    assertIsFalse( "ip4.with.dot.after.end.bracket@[123.145.178.90]." );

    assertIsFalse( "ip4.with.double.dot.between.numbers@[123.14..5.178.90]" );
    assertIsFalse( "ip4.with.double.dot.before.point@[123.145...178.90]" );
    assertIsFalse( "ip4.with.double.dot.after.point@[123.145...178.90]" );
    assertIsFalse( "ip4.with.double.dot.before.start.bracket@..[123.145.178.90]" );
    assertIsFalse( "ip4.with.double.dot.after.start.bracket@[..123.145.178.90]" );
    assertIsFalse( "ip4.with.double.dot.before.end.bracket@[123.145.178.90..]" );
    assertIsFalse( "ip4.with.double.dot.after.end.bracket@[123.145.178.90].." );

    assertIsFalse( "ip4.with.amp.between.numbers@[123.14&5.178.90]" );
    assertIsFalse( "ip4.with.amp.before.point@[123.145&.178.90]" );
    assertIsFalse( "ip4.with.amp.after.point@[123.145.&178.90]" );
    assertIsFalse( "ip4.with.amp.before.start.bracket@&[123.145.178.90]" );
    assertIsFalse( "ip4.with.amp.after.start.bracket@[&123.145.178.90]" );
    assertIsFalse( "ip4.with.amp.before.end.bracket@[123.145.178.90&]" );
    assertIsFalse( "ip4.with.amp.after.end.bracket@[123.145.178.90]&" );

    assertIsFalse( "ip4.with.asterisk.between.numbers@[123.14*5.178.90]" );
    assertIsFalse( "ip4.with.asterisk.before.point@[123.145*.178.90]" );
    assertIsFalse( "ip4.with.asterisk.after.point@[123.145.*178.90]" );
    assertIsFalse( "ip4.with.asterisk.before.start.bracket@*[123.145.178.90]" );
    assertIsFalse( "ip4.with.asterisk.after.start.bracket@[*123.145.178.90]" );
    assertIsFalse( "ip4.with.asterisk.before.end.bracket@[123.145.178.90*]" );
    assertIsFalse( "ip4.with.asterisk.after.end.bracket@[123.145.178.90]*" );

    assertIsFalse( "ip4.with.underscore.between.numbers@[123.14_5.178.90]" );
    assertIsFalse( "ip4.with.underscore.before.point@[123.145_.178.90]" );
    assertIsFalse( "ip4.with.underscore.after.point@[123.145._178.90]" );
    assertIsFalse( "ip4.with.underscore.before.start.bracket@_[123.145.178.90]" );
    assertIsFalse( "ip4.with.underscore.after.start.bracket@[_123.145.178.90]" );
    assertIsFalse( "ip4.with.underscore.before.end.bracket@[123.145.178.90_]" );
    assertIsFalse( "ip4.with.underscore.after.end.bracket@[123.145.178.90]_" );

    assertIsFalse( "ip4.with.dollar.between.numbers@[123.14$5.178.90]" );
    assertIsFalse( "ip4.with.dollar.before.point@[123.145$.178.90]" );
    assertIsFalse( "ip4.with.dollar.after.point@[123.145.$178.90]" );
    assertIsFalse( "ip4.with.dollar.before.start.bracket@$[123.145.178.90]" );
    assertIsFalse( "ip4.with.dollar.after.start.bracket@[$123.145.178.90]" );
    assertIsFalse( "ip4.with.dollar.before.end.bracket@[123.145.178.90$]" );
    assertIsFalse( "ip4.with.dollar.after.end.bracket@[123.145.178.90]$" );

    assertIsFalse( "ip4.with.equality.between.numbers@[123.14=5.178.90]" );
    assertIsFalse( "ip4.with.equality.before.point@[123.145=.178.90]" );
    assertIsFalse( "ip4.with.equality.after.point@[123.145.=178.90]" );
    assertIsFalse( "ip4.with.equality.before.start.bracket@=[123.145.178.90]" );
    assertIsFalse( "ip4.with.equality.after.start.bracket@[=123.145.178.90]" );
    assertIsFalse( "ip4.with.equality.before.end.bracket@[123.145.178.90=]" );
    assertIsFalse( "ip4.with.equality.after.end.bracket@[123.145.178.90]=" );

    assertIsFalse( "ip4.with.exclamation.between.numbers@[123.14!5.178.90]" );
    assertIsFalse( "ip4.with.exclamation.before.point@[123.145!.178.90]" );
    assertIsFalse( "ip4.with.exclamation.after.point@[123.145.!178.90]" );
    assertIsFalse( "ip4.with.exclamation.before.start.bracket@![123.145.178.90]" );
    assertIsFalse( "ip4.with.exclamation.after.start.bracket@[!123.145.178.90]" );
    assertIsFalse( "ip4.with.exclamation.before.end.bracket@[123.145.178.90!]" );
    assertIsFalse( "ip4.with.exclamation.after.end.bracket@[123.145.178.90]!" );

    assertIsFalse( "ip4.with.question.between.numbers@[123.14?5.178.90]" );
    assertIsFalse( "ip4.with.question.before.point@[123.145?.178.90]" );
    assertIsFalse( "ip4.with.question.after.point@[123.145.?178.90]" );
    assertIsFalse( "ip4.with.question.before.start.bracket@?[123.145.178.90]" );
    assertIsFalse( "ip4.with.question.after.start.bracket@[?123.145.178.90]" );
    assertIsFalse( "ip4.with.question.before.end.bracket@[123.145.178.90?]" );
    assertIsFalse( "ip4.with.question.after.end.bracket@[123.145.178.90]?" );

    assertIsFalse( "ip4.with.grave-accent.between.numbers@[123.14`5.178.90]" );
    assertIsFalse( "ip4.with.grave-accent.before.point@[123.145`.178.90]" );
    assertIsFalse( "ip4.with.grave-accent.after.point@[123.145.`178.90]" );
    assertIsFalse( "ip4.with.grave-accent.before.start.bracket@`[123.145.178.90]" );
    assertIsFalse( "ip4.with.grave-accent.after.start.bracket@[`123.145.178.90]" );
    assertIsFalse( "ip4.with.grave-accent.before.end.bracket@[123.145.178.90`]" );
    assertIsFalse( "ip4.with.grave-accent.after.end.bracket@[123.145.178.90]`" );

    assertIsFalse( "ip4.with.hash.between.numbers@[123.14#5.178.90]" );
    assertIsFalse( "ip4.with.hash.before.point@[123.145#.178.90]" );
    assertIsFalse( "ip4.with.hash.after.point@[123.145.#178.90]" );
    assertIsFalse( "ip4.with.hash.before.start.bracket@#[123.145.178.90]" );
    assertIsFalse( "ip4.with.hash.after.start.bracket@[#123.145.178.90]" );
    assertIsFalse( "ip4.with.hash.before.end.bracket@[123.145.178.90#]" );
    assertIsFalse( "ip4.with.hash.after.end.bracket@[123.145.178.90]#" );

    assertIsFalse( "ip4.with.percentage.between.numbers@[123.14%5.178.90]" );
    assertIsFalse( "ip4.with.percentage.before.point@[123.145%.178.90]" );
    assertIsFalse( "ip4.with.percentage.after.point@[123.145.%178.90]" );
    assertIsFalse( "ip4.with.percentage.before.start.bracket@%[123.145.178.90]" );
    assertIsFalse( "ip4.with.percentage.after.start.bracket@[%123.145.178.90]" );
    assertIsFalse( "ip4.with.percentage.before.end.bracket@[123.145.178.90%]" );
    assertIsFalse( "ip4.with.percentage.after.end.bracket@[123.145.178.90]%" );

    assertIsFalse( "ip4.with.pipe.between.numbers@[123.14|5.178.90]" );
    assertIsFalse( "ip4.with.pipe.before.point@[123.145|.178.90]" );
    assertIsFalse( "ip4.with.pipe.after.point@[123.145.|178.90]" );
    assertIsFalse( "ip4.with.pipe.before.start.bracket@|[123.145.178.90]" );
    assertIsFalse( "ip4.with.pipe.after.start.bracket@[|123.145.178.90]" );
    assertIsFalse( "ip4.with.pipe.before.end.bracket@[123.145.178.90|]" );
    assertIsFalse( "ip4.with.pipe.after.end.bracket@[123.145.178.90]|" );

    assertIsFalse( "ip4.with.plus.between.numbers@[123.14+5.178.90]" );
    assertIsFalse( "ip4.with.plus.before.point@[123.145+.178.90]" );
    assertIsFalse( "ip4.with.plus.after.point@[123.145.+178.90]" );
    assertIsFalse( "ip4.with.plus.before.start.bracket@+[123.145.178.90]" );
    assertIsFalse( "ip4.with.plus.after.start.bracket@[+123.145.178.90]" );
    assertIsFalse( "ip4.with.plus.before.end.bracket@[123.145.178.90+]" );
    assertIsFalse( "ip4.with.plus.after.end.bracket@[123.145.178.90]+" );

    assertIsFalse( "ip4.with.leftbracket.between.numbers@[123.14{5.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.point@[123.145{.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.after.point@[123.145.{178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.start.bracket@{[123.145.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.after.start.bracket@[{123.145.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.end.bracket@[123.145.178.90{]" );
    assertIsFalse( "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]{" );

    assertIsFalse( "ip4.with.rightbracket.between.numbers@[123.14}5.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.point@[123.145}.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.after.point@[123.145.}178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.start.bracket@}[123.145.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.after.start.bracket@[}123.145.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.end.bracket@[123.145.178.90}]" );
    assertIsFalse( "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]}" );

    assertIsFalse( "ip4.with.leftbracket.between.numbers@[123.14(5.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.point@[123.145(.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.after.point@[123.145.(178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.start.bracket@([123.145.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.after.start.bracket@[(123.145.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.end.bracket@[123.145.178.90(]" );
    assertIsFalse( "ip4.with.leftbracket.after.end.bracket@[123.145.178.90](" );

    assertIsFalse( "ip4.with.rightbracket.between.numbers@[123.14)5.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.point@[123.145).178.90]" );
    assertIsFalse( "ip4.with.rightbracket.after.point@[123.145.)178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.start.bracket@)[123.145.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.after.start.bracket@[)123.145.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.end.bracket@[123.145.178.90)]" );
    assertIsFalse( "ip4.with.rightbracket.after.end.bracket@[123.145.178.90])" );

    assertIsFalse( "ip4.with.leftbracket.between.numbers@[123.14[5.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.point@[123.145[.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.after.point@[123.145.[178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.start.bracket@[[123.145.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.after.start.bracket@[[123.145.178.90]" );
    assertIsFalse( "ip4.with.leftbracket.before.end.bracket@[123.145.178.90[]" );
    assertIsFalse( "ip4.with.leftbracket.after.end.bracket@[123.145.178.90][" );

    assertIsFalse( "ip4.with.rightbracket.between.numbers@[123.14]5.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.point@[123.145].178.90]" );
    assertIsFalse( "ip4.with.rightbracket.after.point@[123.145.]178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.start.bracket@][123.145.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.after.start.bracket@[]123.145.178.90]" );
    assertIsFalse( "ip4.with.rightbracket.before.end.bracket@[123.145.178.90]]" );
    assertIsFalse( "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]]" );

    assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14()5.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145().178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.()178.90]" );
    assertIsTrue( "ip4.with.empty.bracket.before.start.bracket@()[123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[()123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90()]" );
    assertIsTrue( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]()" );

    assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14{}5.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145{}.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.{}178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.start.bracket@{}[123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[{}123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90{}]" );
    assertIsFalse( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]{}" );

    assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14[]5.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145[].178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.[]178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.start.bracket@[][123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[[]123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90[]]" );
    assertIsFalse( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90][]" );

    assertIsFalse( "ip4.with.empty.bracket.between.numbers@[123.14<>5.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.point@[123.145<>.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.point@[123.145.<>178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.start.bracket@<>[123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.after.start.bracket@[<>123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90<>]" );
    assertIsFalse( "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]<>" );

    assertIsFalse( "ip4.with.false.bracket1.between.numbers@[123.14)(5.178.90]" );
    assertIsFalse( "ip4.with.false.bracket1.before.point@[123.145)(.178.90]" );
    assertIsFalse( "ip4.with.false.bracket1.after.point@[123.145.)(178.90]" );
    assertIsFalse( "ip4.with.false.bracket1.before.start.bracket@)([123.145.178.90]" );
    assertIsFalse( "ip4.with.false.bracket1.after.start.bracket@[)(123.145.178.90]" );
    assertIsFalse( "ip4.with.false.bracket1.before.end.bracket@[123.145.178.90)(]" );
    assertIsFalse( "ip4.with.false.bracket1.after.end.bracket@[123.145.178.90])(" );

    assertIsFalse( "ip4.with.false.bracket2.between.numbers@[123.14}{5.178.90]" );
    assertIsFalse( "ip4.with.false.bracket2.before.point@[123.145}{.178.90]" );
    assertIsFalse( "ip4.with.false.bracket2.after.point@[123.145.}{178.90]" );
    assertIsFalse( "ip4.with.false.bracket2.before.start.bracket@}{[123.145.178.90]" );
    assertIsFalse( "ip4.with.false.bracket2.after.start.bracket@[}{123.145.178.90]" );
    assertIsFalse( "ip4.with.false.bracket2.before.end.bracket@[123.145.178.90}{]" );
    assertIsFalse( "ip4.with.false.bracket2.after.end.bracket@[123.145.178.90]}{" );

    assertIsFalse( "ip4.with.false.bracket4.between.numbers@[123.14><5.178.90]" );
    assertIsFalse( "ip4.with.false.bracket4.before.point@[123.145><.178.90]" );
    assertIsFalse( "ip4.with.false.bracket4.after.point@[123.145.><178.90]" );
    assertIsFalse( "ip4.with.false.bracket4.before.start.bracket@><[123.145.178.90]" );
    assertIsFalse( "ip4.with.false.bracket4.after.start.bracket@[><123.145.178.90]" );
    assertIsFalse( "ip4.with.false.bracket4.before.end.bracket@[123.145.178.90><]" );
    assertIsFalse( "ip4.with.false.bracket4.after.end.bracket@[123.145.178.90]><" );

    assertIsFalse( "ip4.with.lower.than.between.numbers@[123.14<5.178.90]" );
    assertIsFalse( "ip4.with.lower.than.before.point@[123.145<.178.90]" );
    assertIsFalse( "ip4.with.lower.than.after.point@[123.145.<178.90]" );
    assertIsFalse( "ip4.with.lower.than.before.start.bracket@<[123.145.178.90]" );
    assertIsFalse( "ip4.with.lower.than.after.start.bracket@[<123.145.178.90]" );
    assertIsFalse( "ip4.with.lower.than.before.end.bracket@[123.145.178.90<]" );
    assertIsFalse( "ip4.with.lower.than.after.end.bracket@[123.145.178.90]<" );

    assertIsFalse( "ip4.with.greater.than.between.numbers@[123.14>5.178.90]" );
    assertIsFalse( "ip4.with.greater.than.before.point@[123.145>.178.90]" );
    assertIsFalse( "ip4.with.greater.than.after.point@[123.145.>178.90]" );
    assertIsFalse( "ip4.with.greater.than.before.start.bracket@>[123.145.178.90]" );
    assertIsFalse( "ip4.with.greater.than.after.start.bracket@[>123.145.178.90]" );
    assertIsFalse( "ip4.with.greater.than.before.end.bracket@[123.145.178.90>]" );
    assertIsFalse( "ip4.with.greater.than.after.end.bracket@[123.145.178.90]>" );

    assertIsFalse( "ip4.with.tilde.between.numbers@[123.14~5.178.90]" );
    assertIsFalse( "ip4.with.tilde.before.point@[123.145~.178.90]" );
    assertIsFalse( "ip4.with.tilde.after.point@[123.145.~178.90]" );
    assertIsFalse( "ip4.with.tilde.before.start.bracket@~[123.145.178.90]" );
    assertIsFalse( "ip4.with.tilde.after.start.bracket@[~123.145.178.90]" );
    assertIsFalse( "ip4.with.tilde.before.end.bracket@[123.145.178.90~]" );
    assertIsFalse( "ip4.with.tilde.after.end.bracket@[123.145.178.90]~" );

    assertIsFalse( "ip4.with.xor.between.numbers@[123.14^5.178.90]" );
    assertIsFalse( "ip4.with.xor.before.point@[123.145^.178.90]" );
    assertIsFalse( "ip4.with.xor.after.point@[123.145.^178.90]" );
    assertIsFalse( "ip4.with.xor.before.start.bracket@^[123.145.178.90]" );
    assertIsFalse( "ip4.with.xor.after.start.bracket@[^123.145.178.90]" );
    assertIsFalse( "ip4.with.xor.before.end.bracket@[123.145.178.90^]" );
    assertIsFalse( "ip4.with.xor.after.end.bracket@[123.145.178.90]^" );

    assertIsFalse( "ip4.with.colon.between.numbers@[123.14:5.178.90]" );
    assertIsFalse( "ip4.with.colon.before.point@[123.145:.178.90]" );
    assertIsFalse( "ip4.with.colon.after.point@[123.145.:178.90]" );
    assertIsFalse( "ip4.with.colon.before.start.bracket@:[123.145.178.90]" );
    assertIsFalse( "ip4.with.colon.after.start.bracket@[:123.145.178.90]" );
    assertIsFalse( "ip4.with.colon.before.end.bracket@[123.145.178.90:]" );
    assertIsFalse( "ip4.with.colon.after.end.bracket@[123.145.178.90]:" );

    assertIsFalse( "ip4.with.space.between.numbers@[123.14 5.178.90]" );
    assertIsFalse( "ip4.with.space.before.point@[123.145 .178.90]" );
    assertIsFalse( "ip4.with.space.after.point@[123.145. 178.90]" );
    assertIsFalse( "ip4.with.space.before.start.bracket@ [123.145.178.90]" );
    assertIsFalse( "ip4.with.space.after.start.bracket@[ 123.145.178.90]" );
    assertIsFalse( "ip4.with.space.before.end.bracket@[123.145.178.90 ]" );
    assertIsFalse( "ip4.with.space.after.end.bracket@[123.145.178.90] " );

    assertIsFalse( "ip4.with.comma.between.numbers@[123.14,5.178.90]" );
    assertIsFalse( "ip4.with.comma.before.point@[123.145,.178.90]" );
    assertIsFalse( "ip4.with.comma.after.point@[123.145.,178.90]" );
    assertIsFalse( "ip4.with.comma.before.start.bracket@,[123.145.178.90]" );
    assertIsFalse( "ip4.with.comma.after.start.bracket@[,123.145.178.90]" );
    assertIsFalse( "ip4.with.comma.before.end.bracket@[123.145.178.90,]" );
    assertIsFalse( "ip4.with.comma.after.end.bracket@[123.145.178.90]," );

    assertIsFalse( "ip4.with.at.between.numbers@[123.14@5.178.90]" );
    assertIsFalse( "ip4.with.at.before.point@[123.145@.178.90]" );
    assertIsFalse( "ip4.with.at.after.point@[123.145.@178.90]" );
    assertIsFalse( "ip4.with.at.before.start.bracket@@[123.145.178.90]" );
    assertIsFalse( "ip4.with.at.after.start.bracket@[@123.145.178.90]" );
    assertIsFalse( "ip4.with.at.before.end.bracket@[123.145.178.90@]" );
    assertIsFalse( "ip4.with.at.after.end.bracket@[123.145.178.90]@" );

    assertIsFalse( "ip4.with.paragraph.between.numbers@[123.14§5.178.90]" );
    assertIsFalse( "ip4.with.paragraph.before.point@[123.145§.178.90]" );
    assertIsFalse( "ip4.with.paragraph.after.point@[123.145.§178.90]" );
    assertIsFalse( "ip4.with.paragraph.before.start.bracket@§[123.145.178.90]" );
    assertIsFalse( "ip4.with.paragraph.after.start.bracket@[§123.145.178.90]" );
    assertIsFalse( "ip4.with.paragraph.before.end.bracket@[123.145.178.90§]" );
    assertIsFalse( "ip4.with.paragraph.after.end.bracket@[123.145.178.90]§" );

    assertIsFalse( "ip4.with.double.quote.between.numbers@[123.14'5.178.90]" );
    assertIsFalse( "ip4.with.double.quote.before.point@[123.145'.178.90]" );
    assertIsFalse( "ip4.with.double.quote.after.point@[123.145.'178.90]" );
    assertIsFalse( "ip4.with.double.quote.before.start.bracket@'[123.145.178.90]" );
    assertIsFalse( "ip4.with.double.quote.after.start.bracket@['123.145.178.90]" );
    assertIsFalse( "ip4.with.double.quote.before.end.bracket@[123.145.178.90']" );
    assertIsFalse( "ip4.with.double.quote.after.end.bracket@[123.145.178.90]'" );

    assertIsFalse( "ip4.with.forward.slash.between.numbers@[123.14/5.178.90]" );
    assertIsFalse( "ip4.with.forward.slash.before.point@[123.145/.178.90]" );
    assertIsFalse( "ip4.with.forward.slash.after.point@[123.145./178.90]" );
    assertIsFalse( "ip4.with.forward.slash.before.start.bracket@/[123.145.178.90]" );
    assertIsFalse( "ip4.with.forward.slash.after.start.bracket@[/123.145.178.90]" );
    assertIsFalse( "ip4.with.forward.slash.before.end.bracket@[123.145.178.90/]" );
    assertIsFalse( "ip4.with.forward.slash.after.end.bracket@[123.145.178.90]/" );

    assertIsFalse( "ip4.with.hyphen.between.numbers@[123.14-5.178.90]" );
    assertIsFalse( "ip4.with.hyphen.before.point@[123.145-.178.90]" );
    assertIsFalse( "ip4.with.hyphen.after.point@[123.145.-178.90]" );
    assertIsFalse( "ip4.with.hyphen.before.start.bracket@-[123.145.178.90]" );
    assertIsFalse( "ip4.with.hyphen.after.start.bracket@[-123.145.178.90]" );
    assertIsFalse( "ip4.with.hyphen.before.end.bracket@[123.145.178.90-]" );
    assertIsFalse( "ip4.with.hyphen.after.end.bracket@[123.145.178.90]-" );

    assertIsFalse( "ip4.with.empty.string1.between.numbers@[123.14\"\"5.178.90]" );
    assertIsFalse( "ip4.with.empty.string1.before.point@[123.145\"\".178.90]" );
    assertIsFalse( "ip4.with.empty.string1.after.point@[123.145.\"\"178.90]" );
    assertIsFalse( "ip4.with.empty.string1.before.start.bracket@\"\"[123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.string1.after.start.bracket@[\"\"123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.string1.before.end.bracket@[123.145.178.90\"\"]" );
    assertIsFalse( "ip4.with.empty.string1.after.end.bracket@[123.145.178.90]\"\"" );

    assertIsFalse( "ip4.with.empty.string2.between.numbers@[123.14a\"\"b5.178.90]" );
    assertIsFalse( "ip4.with.empty.string2.before.point@[123.145a\"\"b.178.90]" );
    assertIsFalse( "ip4.with.empty.string2.after.point@[123.145.a\"\"b178.90]" );
    assertIsFalse( "ip4.with.empty.string2.before.start.bracket@a\"\"b[123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.string2.after.start.bracket@[a\"\"b123.145.178.90]" );
    assertIsFalse( "ip4.with.empty.string2.before.end.bracket@[123.145.178.90a\"\"b]" );
    assertIsFalse( "ip4.with.empty.string2.after.end.bracket@[123.145.178.90]a\"\"b" );

    assertIsFalse( "ip4.with.double.empty.string1.between.numbers@[123.14\"\"\"\"5.178.90]" );
    assertIsFalse( "ip4.with.double.empty.string1.before.point@[123.145\"\"\"\".178.90]" );
    assertIsFalse( "ip4.with.double.empty.string1.after.point@[123.145.\"\"\"\"178.90]" );
    assertIsFalse( "ip4.with.double.empty.string1.before.start.bracket@\"\"\"\"[123.145.178.90]" );
    assertIsFalse( "ip4.with.double.empty.string1.after.start.bracket@[\"\"\"\"123.145.178.90]" );
    assertIsFalse( "ip4.with.double.empty.string1.before.end.bracket@[123.145.178.90\"\"\"\"]" );
    assertIsFalse( "ip4.with.double.empty.string1.after.end.bracket@[123.145.178.90]\"\"\"\"" );

    assertIsFalse( "ip4.with.double.empty.string2.between.numbers@[123.14\"\".\"\"5.178.90]" );
    assertIsFalse( "ip4.with.double.empty.string2.before.point@[123.145\"\".\"\".178.90]" );
    assertIsFalse( "ip4.with.double.empty.string2.after.point@[123.145.\"\".\"\"178.90]" );
    assertIsFalse( "ip4.with.double.empty.string2.before.start.bracket@\"\".\"\"[123.145.178.90]" );
    assertIsFalse( "ip4.with.double.empty.string2.after.start.bracket@[\"\".\"\"123.145.178.90]" );
    assertIsFalse( "ip4.with.double.empty.string2.before.end.bracket@[123.145.178.90\"\".\"\"]" );
    assertIsFalse( "ip4.with.double.empty.string2.after.end.bracket@[123.145.178.90]\"\".\"\"" );

    assertIsFalse( "ip4.with.number0.between.numbers@[123.1405.178.90]" );
    assertIsFalse( "ip4.with.number0.before.point@[123.1450.178.90]" );
    assertIsFalse( "ip4.with.number0.after.point@[123.145.0178.90]" );
    assertIsFalse( "ip4.with.number0.before.start.bracket@0[123.145.178.90]" );
    assertIsFalse( "ip4.with.number0.after.start.bracket@[0123.145.178.90]" );
    assertIsFalse( "ip4.with.number0.before.end.bracket@[123.145.178.900]" );
    assertIsFalse( "ip4.with.number0.after.end.bracket@[123.145.178.90]0" );

    assertIsFalse( "ip4.with.number9.between.numbers@[123.1495.178.90]" );
    assertIsFalse( "ip4.with.number9.before.point@[123.1459.178.90]" );
    assertIsFalse( "ip4.with.number9.after.point@[123.145.9178.90]" );
    assertIsFalse( "ip4.with.number9.before.start.bracket@9[123.145.178.90]" );
    assertIsFalse( "ip4.with.number9.after.start.bracket@[9123.145.178.90]" );
    assertIsFalse( "ip4.with.number9.before.end.bracket@[123.145.178.909]" );
    assertIsFalse( "ip4.with.number9.after.end.bracket@[123.145.178.90]9" );

    assertIsFalse( "ip4.with.numbers.between.numbers@[123.1401234567895.178.90]" );
    assertIsFalse( "ip4.with.numbers.before.point@[123.1450123456789.178.90]" );
    assertIsFalse( "ip4.with.numbers.after.point@[123.145.0123456789178.90]" );
    assertIsFalse( "ip4.with.numbers.before.start.bracket@0123456789[123.145.178.90]" );
    assertIsFalse( "ip4.with.numbers.after.start.bracket@[0123456789123.145.178.90]" );
    assertIsFalse( "ip4.with.numbers.before.end.bracket@[123.145.178.900123456789]" );
    assertIsFalse( "ip4.with.numbers.after.end.bracket@[123.145.178.90]0123456789" );

    assertIsFalse( "ip4.with.byte.overflow.between.numbers@[123.149995.178.90]" );
    assertIsFalse( "ip4.with.byte.overflow.before.point@[123.145999.178.90]" );
    assertIsFalse( "ip4.with.byte.overflow.after.point@[123.145.999178.90]" );
    assertIsFalse( "ip4.with.byte.overflow.before.start.bracket@999[123.145.178.90]" );
    assertIsFalse( "ip4.with.byte.overflow.after.start.bracket@[999123.145.178.90]" );
    assertIsFalse( "ip4.with.byte.overflow.before.end.bracket@[123.145.178.90999]" );
    assertIsFalse( "ip4.with.byte.overflow.after.end.bracket@[123.145.178.90]999" );

    assertIsFalse( "ip4.with.no.hex.number.between.numbers@[123.14xyz5.178.90]" );
    assertIsFalse( "ip4.with.no.hex.number.before.point@[123.145xyz.178.90]" );
    assertIsFalse( "ip4.with.no.hex.number.after.point@[123.145.xyz178.90]" );
    assertIsFalse( "ip4.with.no.hex.number.before.start.bracket@xyz[123.145.178.90]" );
    assertIsFalse( "ip4.with.no.hex.number.after.start.bracket@[xyz123.145.178.90]" );
    assertIsFalse( "ip4.with.no.hex.number.before.end.bracket@[123.145.178.90xyz]" );
    assertIsFalse( "ip4.with.no.hex.number.after.end.bracket@[123.145.178.90]xyz" );

    assertIsFalse( "ip4.with.slash.between.numbers@[123.14\\5.178.90]" );
    assertIsFalse( "ip4.with.slash.before.point@[123.145\\.178.90]" );
    assertIsFalse( "ip4.with.slash.after.point@[123.145.\\178.90]" );
    assertIsFalse( "ip4.with.slash.before.start.bracket@\\[123.145.178.90]" );
    assertIsFalse( "ip4.with.slash.after.start.bracket@[\\123.145.178.90]" );
    assertIsFalse( "ip4.with.slash.before.end.bracket@[123.145.178.90\\]" );
    assertIsFalse( "ip4.with.slash.after.end.bracket@[123.145.178.90]\\" );

    assertIsFalse( "ip4.with.string.between.numbers@[123.14\"str\"5.178.90]" );
    assertIsFalse( "ip4.with.string.before.point@[123.145\"str\".178.90]" );
    assertIsFalse( "ip4.with.string.after.point@[123.145.\"str\"178.90]" );
    assertIsFalse( "ip4.with.string.before.start.bracket@\"str\"[123.145.178.90]" );
    assertIsFalse( "ip4.with.string.after.start.bracket@[\"str\"123.145.178.90]" );
    assertIsFalse( "ip4.with.string.before.end.bracket@[123.145.178.90\"str\"]" );
    assertIsFalse( "ip4.with.string.after.end.bracket@[123.145.178.90]\"str\"" );

    assertIsFalse( "ip4.with.comment.between.numbers@[123.14(comment)5.178.90]" );
    assertIsFalse( "ip4.with.comment.before.point@[123.145(comment).178.90]" );
    assertIsFalse( "ip4.with.comment.after.point@[123.145.(comment)178.90]" );
    assertIsTrue( "ip4.with.comment.before.start.bracket@(comment)[123.145.178.90]" );
    assertIsFalse( "ip4.with.comment.after.start.bracket@[(comment)123.145.178.90]" );
    assertIsFalse( "ip4.with.comment.before.end.bracket@[123.145.178.90(comment)]" );
    assertIsTrue( "ip4.with.comment.after.end.bracket@[123.145.178.90](comment)" );

    assertIsTrue( "email@[123.123.123.123]" );
    assertIsTrue( "email@123.123.123.123" );
    assertIsTrue( "ab@88.120.150.021" );
    assertIsTrue( "ab@88.120.150.01" );
    assertIsTrue( "ab@188.120.150.10" );
    assertIsTrue( "ab@120.25.254.120" );
    assertIsTrue( "ab@1.0.0.10" );
    assertIsTrue( "ab@01.120.150.1" );
    assertIsFalse( "email@[3.256.255.23]" );
    assertIsFalse( "email@[123.123.123].123" );
    assertIsFalse( "email@[123.123.123.123" );
    assertIsFalse( "email@[12.34.44.56" );
    assertIsFalse( "email@[1.1.23.5f]" );
    assertIsFalse( "email@14.44.56.34]" );
    assertIsFalse( "email@123.123.[123.123]" );
    assertIsFalse( "email@123.123.123.123]" );
    assertIsFalse( "email@111.222.333.44444" );
    assertIsFalse( "email@111.222.333.256" );
    assertIsFalse( "email@111.222.333" );
    assertIsFalse( "email@-example.com" );
    assertIsFalse( "email.@example.com" );
    assertIsFalse( "email..email@example.com" );
    assertIsFalse( ".email@example.com" );

    wlHeadline( "ip4 without brackets" );

    assertIsTrue( "ip4.without.brackets.ok1@127.0.0.1" );
    assertIsTrue( "ip4.without.brackets.ok2@0.0.0.0" );
    assertIsFalse( "ip4.without.brackets.but.with.space.at.end@127.0.0.1 " );
    assertIsFalse( "ip4.without.brackets.byte.overflow@127.0.999.1" );
    assertIsFalse( "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1" );
    assertIsFalse( "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1" );
    assertIsFalse( "ip4.without.brackets.negative.number@127.0.-1.1" );
    assertIsFalse( "ip4.without.brackets.point.error1@127.0..0.1" );
    assertIsFalse( "ip4.without.brackets.point.error1@127...1" );
    assertIsFalse( "ip4.without.brackets.error.bracket@127.0.1.1[]" );

    assertIsFalse( "ip4.without.brackets.point.error2@127001" );
    assertIsFalse( "ip4.without.brackets.point.error3@127.0.0." );
    assertIsFalse( "ip4.without.brackets.character.error@127.0.A.1" );
    assertIsFalse( "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1" );
    assertIsTrue( "ip4.without.brackets.normal.tld1@127.0.0.1.com" );
    assertIsTrue( "ip4.without.brackets.normal.tld2@127.0.99.1.com" );
    assertIsTrue( "ip4.without.brackets.normal.tld3@127.0.A.1.com" );
  }

  private static void runTestCharacters()
  {
    wlHeadline( "Characters" );

    assertIsTrue( "&local&&part&with&$@amp.com" );
    assertIsTrue( "*local**part*with*@asterisk.com" );
    assertIsTrue( "$local$$part$with$@dollar.com" );
    assertIsTrue( "=local==part=with=@equality.com" );
    assertIsTrue( "!local!!part!with!@exclamation.com" );
    assertIsTrue( "`local``part`with`@grave-accent.com" );
    assertIsTrue( "#local##part#with#@hash.com" );
    assertIsTrue( "-local--part-with-@hypen.com" );
    assertIsTrue( "{local{part{{with{@leftbracket.com" );
    assertIsTrue( "%local%%part%with%@percentage.com" );
    assertIsTrue( "|local||part|with|@pipe.com" );
    assertIsTrue( "+local++part+with+@plus.com" );
    assertIsTrue( "?local??part?with?@question.com" );
    assertIsTrue( "}local}part}}with}@rightbracket.com" );
    assertIsTrue( "~local~~part~with~@tilde.com" );
    assertIsTrue( "^local^^part^with^@xor.com" );
    assertIsTrue( "_local__part_with_@underscore.com" );
    assertIsFalse( ":local::part:with:@colon.com" );

    assertIsFalse( "local.part@&domain&&part&with&.com" );
    assertIsFalse( "local.part@*domain**part*with*.com" );
    assertIsFalse( "local.part@$domain$$part$with$.com" );
    assertIsFalse( "local.part@=domain==part=with=.com" );
    assertIsFalse( "local.part@!domain!!part!with!.com" );
    assertIsFalse( "local.part@`domain``part`with`.com" );
    assertIsFalse( "local.part@#domain##part#with#.com" );
    assertIsFalse( "local.part@-domain--part-with-.com" );
    assertIsFalse( "local.part@{domain{part{{with{.com" );
    assertIsFalse( "local.part@%domain%%part%with%.com" );
    assertIsFalse( "local.part@|domain||part|with|.com" );
    assertIsFalse( "local.part@+domain++part+with+.com" );
    assertIsFalse( "local.part@?domain??part?with?.com" );
    assertIsFalse( "local.part@}domain}part}}with}.com" );
    assertIsFalse( "local.part@~domain~~part~with~.com" );
    assertIsFalse( "local.part@^domain^^part^with^.com" );
    assertIsFalse( "local.part@_domain__part_with_.com" );

    assertIsFalse( "local.part@domain--part.double.dash.com" ); // ?

    assertIsFalse( ";.local.part.starts.with.semicolon@domain.com" );
    assertIsFalse( "local.part.ends.with.semicolon;@domain.com" );
    assertIsFalse( "local.part.with.semicolon;character@domain.com" );
    assertIsFalse( "local.part.with.semicolon.before;.point@domain.com" );
    assertIsFalse( "local.part.with.semicolon.after.;point@domain.com" );
    assertIsFalse( "local.part.with.double.semicolon;;test@domain.com" );
    assertIsFalse( "(comment ;) local.part.with.semicolon.in.comment@domain.com" );
    assertIsTrue( "\"string;\".local.part.with.semicolon.in.String@domain.com" );
    assertIsFalse( "\"string\\;\".local.part.with.escaped.semicolon.in.String@domain.com" );
    assertIsFalse( ";@local.part.only.semicolon.domain.com" );
    assertIsFalse( ";;;;;;@local.part.only.consecutive.semicolon.domain.com" );
    assertIsFalse( ";.;.;.;.;.;@semicolon.domain.com" );
    assertIsFalse( "name ; <pointy.brackets1.with.semicolon.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.semicolon.in.display.name@domain.com> name ;" );

    assertIsFalse( "domain.part@with;semicolon.com" );
    assertIsFalse( "domain.part@;with.semicolon.at.domain.start.com" );
    assertIsFalse( "domain.part@with.semicolon.at.domain.end1;.com" );
    assertIsFalse( "domain.part@with.semicolon.at.domain.end2.com;" );
    assertIsFalse( "domain.part@with.semicolon.before;.point.com" );
    assertIsFalse( "domain.part@with.semicolon.after.;point.com" );
    assertIsFalse( "domain.part@with.consecutive.semicolon;;test.com" );
    assertIsFalse( "domain.part.with.semicolon.in.comment@(comment ;)domain.com" );
    assertIsFalse( "domain.part.only.semicolon@;.com" );
    assertIsFalse( "top.level.domain.only@semicolon.;" );

    assertIsTrue( "&.local.part.starts.with.amp@domain.com" );
    assertIsTrue( "local.part.ends.with.amp&@domain.com" );
    assertIsTrue( "local.part.with.amp&character@domain.com" );
    assertIsTrue( "local.part.with.amp.before&.point@domain.com" );
    assertIsTrue( "local.part.with.amp.after.&point@domain.com" );
    assertIsTrue( "local.part.with.double.amp&&test@domain.com" );
    assertIsTrue( "(comment &) local.part.with.amp.in.comment@domain.com" );
    assertIsTrue( "\"string&\".local.part.with.amp.in.String@domain.com" );
    assertIsFalse( "\"string\\&\".local.part.with.escaped.amp.in.String@domain.com" );
    assertIsTrue( "&@local.part.only.amp.domain.com" );
    assertIsTrue( "&&&&&&@local.part.only.consecutive.amp.domain.com" );
    assertIsTrue( "&.&.&.&.&.&@amp.domain.com" );
    assertIsFalse( "name & <pointy.brackets1.with.amp.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.amp.in.display.name@domain.com> name &" );

    assertIsFalse( "domain.part@with&amp.com" );
    assertIsFalse( "domain.part@&with.amp.at.domain.start.com" );
    assertIsFalse( "domain.part@with.amp.at.domain.end1&.com" );
    assertIsFalse( "domain.part@with.amp.at.domain.end2.com&" );
    assertIsFalse( "domain.part@with.amp.before&.point.com" );
    assertIsFalse( "domain.part@with.amp.after.&point.com" );
    assertIsFalse( "domain.part@with.consecutive.amp&&test.com" );
    assertIsTrue( "domain.part.with.amp.in.comment@(comment &)domain.com" );
    assertIsFalse( "domain.part.only.amp@&.com" );
    assertIsFalse( "top.level.domain.only@amp.&" );

    assertIsTrue( "*.local.part.starts.with.asterisk@domain.com" );
    assertIsTrue( "local.part.ends.with.asterisk*@domain.com" );
    assertIsTrue( "local.part.with.asterisk*character@domain.com" );
    assertIsTrue( "local.part.with.asterisk.before*.point@domain.com" );
    assertIsTrue( "local.part.with.asterisk.after.*point@domain.com" );
    assertIsTrue( "local.part.with.double.asterisk**test@domain.com" );
    assertIsTrue( "(comment *) local.part.with.asterisk.in.comment@domain.com" );
    assertIsTrue( "\"string*\".local.part.with.asterisk.in.String@domain.com" );
    assertIsFalse( "\"string\\*\".local.part.with.escaped.asterisk.in.String@domain.com" );
    assertIsTrue( "*@local.part.only.asterisk.domain.com" );
    assertIsTrue( "******@local.part.only.consecutive.asterisk.domain.com" );
    assertIsTrue( "*.*.*.*.*.*@asterisk.domain.com" );
    assertIsFalse( "name * <pointy.brackets1.with.asterisk.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.asterisk.in.display.name@domain.com> name *" );

    assertIsFalse( "domain.part@with*asterisk.com" );
    assertIsFalse( "domain.part@*with.asterisk.at.domain.start.com" );
    assertIsFalse( "domain.part@with.asterisk.at.domain.end1*.com" );
    assertIsFalse( "domain.part@with.asterisk.at.domain.end2.com*" );
    assertIsFalse( "domain.part@with.asterisk.before*.point.com" );
    assertIsFalse( "domain.part@with.asterisk.after.*point.com" );
    assertIsFalse( "domain.part@with.consecutive.asterisk**test.com" );
    assertIsTrue( "domain.part.with.asterisk.in.comment@(comment *)domain.com" );
    assertIsFalse( "domain.part.only.asterisk@*.com" );
    assertIsFalse( "top.level.domain.only@asterisk.*" );

    assertIsTrue( "_.local.part.starts.with.underscore@domain.com" );
    assertIsTrue( "local.part.ends.with.underscore_@domain.com" );
    assertIsTrue( "local.part.with.underscore_character@domain.com" );
    assertIsTrue( "local.part.with.underscore.before_.point@domain.com" );
    assertIsTrue( "local.part.with.underscore.after._point@domain.com" );
    assertIsTrue( "local.part.with.double.underscore__test@domain.com" );
    assertIsTrue( "(comment _) local.part.with.underscore.in.comment@domain.com" );
    assertIsTrue( "\"string_\".local.part.with.underscore.in.String@domain.com" );
    assertIsFalse( "\"string\\_\".local.part.with.escaped.underscore.in.String@domain.com" );
    assertIsTrue( "_@local.part.only.underscore.domain.com" );
    assertIsTrue( "______@local.part.only.consecutive.underscore.domain.com" );
    assertIsTrue( "_._._._._._@underscore.domain.com" );
    assertIsFalse( "name _ <pointy.brackets1.with.underscore.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.underscore.in.display.name@domain.com> name _" );

    assertIsTrue( "domain.part@with_underscore.com" );
    assertIsFalse( "domain.part@_with.underscore.at.domain.start.com" );
    assertIsFalse( "domain.part@with.underscore.at.domain.end1_.com" );
    assertIsFalse( "domain.part@with.underscore.at.domain.end2.com_" );
    assertIsFalse( "domain.part@with.underscore.before_.point.com" );
    assertIsFalse( "domain.part@with.underscore.after._point.com" );
    assertIsTrue( "domain.part@with.consecutive.underscore__test.com" );
    assertIsTrue( "domain.part.with.underscore.in.comment@(comment _)domain.com" );
    assertIsFalse( "domain.part.only.underscore@_.com" );
    assertIsFalse( "top.level.domain.only@underscore._" );

    assertIsTrue( "$.local.part.starts.with.dollar@domain.com" );
    assertIsTrue( "local.part.ends.with.dollar$@domain.com" );
    assertIsTrue( "local.part.with.dollar$character@domain.com" );
    assertIsTrue( "local.part.with.dollar.before$.point@domain.com" );
    assertIsTrue( "local.part.with.dollar.after.$point@domain.com" );
    assertIsTrue( "local.part.with.double.dollar$$test@domain.com" );
    assertIsTrue( "(comment $) local.part.with.dollar.in.comment@domain.com" );
    assertIsTrue( "\"string$\".local.part.with.dollar.in.String@domain.com" );
    assertIsFalse( "\"string\\$\".local.part.with.escaped.dollar.in.String@domain.com" );
    assertIsTrue( "$@local.part.only.dollar.domain.com" );
    assertIsTrue( "$$$$$$@local.part.only.consecutive.dollar.domain.com" );
    assertIsTrue( "$.$.$.$.$.$@dollar.domain.com" );
    assertIsFalse( "name $ <pointy.brackets1.with.dollar.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.dollar.in.display.name@domain.com> name $" );

    assertIsFalse( "domain.part@with$dollar.com" );
    assertIsFalse( "domain.part@$with.dollar.at.domain.start.com" );
    assertIsFalse( "domain.part@with.dollar.at.domain.end1$.com" );
    assertIsFalse( "domain.part@with.dollar.at.domain.end2.com$" );
    assertIsFalse( "domain.part@with.dollar.before$.point.com" );
    assertIsFalse( "domain.part@with.dollar.after.$point.com" );
    assertIsFalse( "domain.part@with.consecutive.dollar$$test.com" );
    assertIsTrue( "domain.part.with.dollar.in.comment@(comment $)domain.com" );
    assertIsFalse( "domain.part.only.dollar@$.com" );
    assertIsFalse( "top.level.domain.only@dollar.$" );

    assertIsTrue( "=.local.part.starts.with.equality@domain.com" );
    assertIsTrue( "local.part.ends.with.equality=@domain.com" );
    assertIsTrue( "local.part.with.equality=character@domain.com" );
    assertIsTrue( "local.part.with.equality.before=.point@domain.com" );
    assertIsTrue( "local.part.with.equality.after.=point@domain.com" );
    assertIsTrue( "local.part.with.double.equality==test@domain.com" );
    assertIsTrue( "(comment =) local.part.with.equality.in.comment@domain.com" );
    assertIsTrue( "\"string=\".local.part.with.equality.in.String@domain.com" );
    assertIsFalse( "\"string\\=\".local.part.with.escaped.equality.in.String@domain.com" );
    assertIsTrue( "=@local.part.only.equality.domain.com" );
    assertIsTrue( "======@local.part.only.consecutive.equality.domain.com" );
    assertIsTrue( "=.=.=.=.=.=@equality.domain.com" );
    assertIsFalse( "name = <pointy.brackets1.with.equality.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.equality.in.display.name@domain.com> name =" );

    assertIsFalse( "domain.part@with=equality.com" );
    assertIsFalse( "domain.part@=with.equality.at.domain.start.com" );
    assertIsFalse( "domain.part@with.equality.at.domain.end1=.com" );
    assertIsFalse( "domain.part@with.equality.at.domain.end2.com=" );
    assertIsFalse( "domain.part@with.equality.before=.point.com" );
    assertIsFalse( "domain.part@with.equality.after.=point.com" );
    assertIsFalse( "domain.part@with.consecutive.equality==test.com" );
    assertIsTrue( "domain.part.with.equality.in.comment@(comment =)domain.com" );
    assertIsFalse( "domain.part.only.equality@=.com" );
    assertIsFalse( "top.level.domain.only@equality.=" );

    assertIsTrue( "!.local.part.starts.with.exclamation@domain.com" );
    assertIsTrue( "local.part.ends.with.exclamation!@domain.com" );
    assertIsTrue( "local.part.with.exclamation!character@domain.com" );
    assertIsTrue( "local.part.with.exclamation.before!.point@domain.com" );
    assertIsTrue( "local.part.with.exclamation.after.!point@domain.com" );
    assertIsTrue( "local.part.with.double.exclamation!!test@domain.com" );
    assertIsTrue( "(comment !) local.part.with.exclamation.in.comment@domain.com" );
    assertIsTrue( "\"string!\".local.part.with.exclamation.in.String@domain.com" );
    assertIsFalse( "\"string\\!\".local.part.with.escaped.exclamation.in.String@domain.com" );
    assertIsTrue( "!@local.part.only.exclamation.domain.com" );
    assertIsTrue( "!!!!!!@local.part.only.consecutive.exclamation.domain.com" );
    assertIsTrue( "!.!.!.!.!.!@exclamation.domain.com" );
    assertIsFalse( "name ! <pointy.brackets1.with.exclamation.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.exclamation.in.display.name@domain.com> name !" );

    assertIsFalse( "domain.part@with!exclamation.com" );
    assertIsFalse( "domain.part@!with.exclamation.at.domain.start.com" );
    assertIsFalse( "domain.part@with.exclamation.at.domain.end1!.com" );
    assertIsFalse( "domain.part@with.exclamation.at.domain.end2.com!" );
    assertIsFalse( "domain.part@with.exclamation.before!.point.com" );
    assertIsFalse( "domain.part@with.exclamation.after.!point.com" );
    assertIsFalse( "domain.part@with.consecutive.exclamation!!test.com" );
    assertIsTrue( "domain.part.with.exclamation.in.comment@(comment !)domain.com" );
    assertIsFalse( "domain.part.only.exclamation@!.com" );
    assertIsFalse( "top.level.domain.only@exclamation.!" );

    assertIsTrue( "?.local.part.starts.with.question@domain.com" );
    assertIsTrue( "local.part.ends.with.question?@domain.com" );
    assertIsTrue( "local.part.with.question?character@domain.com" );
    assertIsTrue( "local.part.with.question.before?.point@domain.com" );
    assertIsTrue( "local.part.with.question.after.?point@domain.com" );
    assertIsTrue( "local.part.with.double.question??test@domain.com" );
    assertIsTrue( "(comment ?) local.part.with.question.in.comment@domain.com" );
    assertIsTrue( "\"string?\".local.part.with.question.in.String@domain.com" );
    assertIsFalse( "\"string\\?\".local.part.with.escaped.question.in.String@domain.com" );
    assertIsTrue( "?@local.part.only.question.domain.com" );
    assertIsTrue( "??????@local.part.only.consecutive.question.domain.com" );
    assertIsTrue( "?.?.?.?.?.?@question.domain.com" );
    assertIsFalse( "name ? <pointy.brackets1.with.question.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.question.in.display.name@domain.com> name ?" );

    assertIsFalse( "domain.part@with?question.com" );
    assertIsFalse( "domain.part@?with.question.at.domain.start.com" );
    assertIsFalse( "domain.part@with.question.at.domain.end1?.com" );
    assertIsFalse( "domain.part@with.question.at.domain.end2.com?" );
    assertIsFalse( "domain.part@with.question.before?.point.com" );
    assertIsFalse( "domain.part@with.question.after.?point.com" );
    assertIsFalse( "domain.part@with.consecutive.question??test.com" );
    assertIsTrue( "domain.part.with.question.in.comment@(comment ?)domain.com" );
    assertIsFalse( "domain.part.only.question@?.com" );
    assertIsFalse( "top.level.domain.only@question.?" );

    assertIsTrue( "`.local.part.starts.with.grave-accent@domain.com" );
    assertIsTrue( "local.part.ends.with.grave-accent`@domain.com" );
    assertIsTrue( "local.part.with.grave-accent`character@domain.com" );
    assertIsTrue( "local.part.with.grave-accent.before`.point@domain.com" );
    assertIsTrue( "local.part.with.grave-accent.after.`point@domain.com" );
    assertIsTrue( "local.part.with.double.grave-accent``test@domain.com" );
    assertIsTrue( "(comment `) local.part.with.grave-accent.in.comment@domain.com" );
    assertIsTrue( "\"string`\".local.part.with.grave-accent.in.String@domain.com" );
    assertIsFalse( "\"string\\`\".local.part.with.escaped.grave-accent.in.String@domain.com" );
    assertIsTrue( "`@local.part.only.grave-accent.domain.com" );
    assertIsTrue( "``````@local.part.only.consecutive.grave-accent.domain.com" );
    assertIsTrue( "`.`.`.`.`.`@grave-accent.domain.com" );
    assertIsFalse( "name ` <pointy.brackets1.with.grave-accent.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.grave-accent.in.display.name@domain.com> name `" );

    assertIsFalse( "domain.part@with`grave-accent.com" );
    assertIsFalse( "domain.part@`with.grave-accent.at.domain.start.com" );
    assertIsFalse( "domain.part@with.grave-accent.at.domain.end1`.com" );
    assertIsFalse( "domain.part@with.grave-accent.at.domain.end2.com`" );
    assertIsFalse( "domain.part@with.grave-accent.before`.point.com" );
    assertIsFalse( "domain.part@with.grave-accent.after.`point.com" );
    assertIsFalse( "domain.part@with.consecutive.grave-accent``test.com" );
    assertIsTrue( "domain.part.with.grave-accent.in.comment@(comment `)domain.com" );
    assertIsFalse( "domain.part.only.grave-accent@`.com" );
    assertIsFalse( "top.level.domain.only@grave-accent.`" );

    assertIsTrue( "#.local.part.starts.with.hash@domain.com" );
    assertIsTrue( "local.part.ends.with.hash#@domain.com" );
    assertIsTrue( "local.part.with.hash#character@domain.com" );
    assertIsTrue( "local.part.with.hash.before#.point@domain.com" );
    assertIsTrue( "local.part.with.hash.after.#point@domain.com" );
    assertIsTrue( "local.part.with.double.hash##test@domain.com" );
    assertIsTrue( "(comment #) local.part.with.hash.in.comment@domain.com" );
    assertIsTrue( "\"string#\".local.part.with.hash.in.String@domain.com" );
    assertIsFalse( "\"string\\#\".local.part.with.escaped.hash.in.String@domain.com" );
    assertIsTrue( "#@local.part.only.hash.domain.com" );
    assertIsTrue( "######@local.part.only.consecutive.hash.domain.com" );
    assertIsTrue( "#.#.#.#.#.#@hash.domain.com" );
    assertIsFalse( "name # <pointy.brackets1.with.hash.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.hash.in.display.name@domain.com> name #" );

    assertIsFalse( "domain.part@with#hash.com" );
    assertIsFalse( "domain.part@#with.hash.at.domain.start.com" );
    assertIsFalse( "domain.part@with.hash.at.domain.end1#.com" );
    assertIsFalse( "domain.part@with.hash.at.domain.end2.com#" );
    assertIsFalse( "domain.part@with.hash.before#.point.com" );
    assertIsFalse( "domain.part@with.hash.after.#point.com" );
    assertIsFalse( "domain.part@with.consecutive.hash##test.com" );
    assertIsTrue( "domain.part.with.hash.in.comment@(comment #)domain.com" );
    assertIsFalse( "domain.part.only.hash@#.com" );
    assertIsFalse( "top.level.domain.only@hash.#" );

    assertIsTrue( "%.local.part.starts.with.percentage@domain.com" );
    assertIsTrue( "local.part.ends.with.percentage%@domain.com" );
    assertIsTrue( "local.part.with.percentage%character@domain.com" );
    assertIsTrue( "local.part.with.percentage.before%.point@domain.com" );
    assertIsTrue( "local.part.with.percentage.after.%point@domain.com" );
    assertIsTrue( "local.part.with.double.percentage%%test@domain.com" );
    assertIsTrue( "(comment %) local.part.with.percentage.in.comment@domain.com" );
    assertIsTrue( "\"string%\".local.part.with.percentage.in.String@domain.com" );
    assertIsFalse( "\"string\\%\".local.part.with.escaped.percentage.in.String@domain.com" );
    assertIsTrue( "%@local.part.only.percentage.domain.com" );
    assertIsTrue( "%%%%%%@local.part.only.consecutive.percentage.domain.com" );
    assertIsTrue( "%.%.%.%.%.%@percentage.domain.com" );
    assertIsFalse( "name % <pointy.brackets1.with.percentage.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.percentage.in.display.name@domain.com> name %" );

    assertIsFalse( "domain.part@with%percentage.com" );
    assertIsFalse( "domain.part@%with.percentage.at.domain.start.com" );
    assertIsFalse( "domain.part@with.percentage.at.domain.end1%.com" );
    assertIsFalse( "domain.part@with.percentage.at.domain.end2.com%" );
    assertIsFalse( "domain.part@with.percentage.before%.point.com" );
    assertIsFalse( "domain.part@with.percentage.after.%point.com" );
    assertIsFalse( "domain.part@with.consecutive.percentage%%test.com" );
    assertIsTrue( "domain.part.with.percentage.in.comment@(comment %)domain.com" );
    assertIsFalse( "domain.part.only.percentage@%.com" );
    assertIsFalse( "top.level.domain.only@percentage.%" );

    assertIsTrue( "|.local.part.starts.with.pipe@domain.com" );
    assertIsTrue( "local.part.ends.with.pipe|@domain.com" );
    assertIsTrue( "local.part.with.pipe|character@domain.com" );
    assertIsTrue( "local.part.with.pipe.before|.point@domain.com" );
    assertIsTrue( "local.part.with.pipe.after.|point@domain.com" );
    assertIsTrue( "local.part.with.double.pipe||test@domain.com" );
    assertIsTrue( "(comment |) local.part.with.pipe.in.comment@domain.com" );
    assertIsTrue( "\"string|\".local.part.with.pipe.in.String@domain.com" );
    assertIsFalse( "\"string\\|\".local.part.with.escaped.pipe.in.String@domain.com" );
    assertIsTrue( "|@local.part.only.pipe.domain.com" );
    assertIsTrue( "||||||@local.part.only.consecutive.pipe.domain.com" );
    assertIsTrue( "|.|.|.|.|.|@pipe.domain.com" );
    assertIsFalse( "name | <pointy.brackets1.with.pipe.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.pipe.in.display.name@domain.com> name |" );

    assertIsFalse( "domain.part@with|pipe.com" );
    assertIsFalse( "domain.part@|with.pipe.at.domain.start.com" );
    assertIsFalse( "domain.part@with.pipe.at.domain.end1|.com" );
    assertIsFalse( "domain.part@with.pipe.at.domain.end2.com|" );
    assertIsFalse( "domain.part@with.pipe.before|.point.com" );
    assertIsFalse( "domain.part@with.pipe.after.|point.com" );
    assertIsFalse( "domain.part@with.consecutive.pipe||test.com" );
    assertIsTrue( "domain.part.with.pipe.in.comment@(comment |)domain.com" );
    assertIsFalse( "domain.part.only.pipe@|.com" );
    assertIsFalse( "top.level.domain.only@pipe.|" );

    assertIsTrue( "+.local.part.starts.with.plus@domain.com" );
    assertIsTrue( "local.part.ends.with.plus+@domain.com" );
    assertIsTrue( "local.part.with.plus+character@domain.com" );
    assertIsTrue( "local.part.with.plus.before+.point@domain.com" );
    assertIsTrue( "local.part.with.plus.after.+point@domain.com" );
    assertIsTrue( "local.part.with.double.plus++test@domain.com" );
    assertIsTrue( "(comment +) local.part.with.plus.in.comment@domain.com" );
    assertIsTrue( "\"string+\".local.part.with.plus.in.String@domain.com" );
    assertIsFalse( "\"string\\+\".local.part.with.escaped.plus.in.String@domain.com" );
    assertIsTrue( "+@local.part.only.plus.domain.com" );
    assertIsTrue( "++++++@local.part.only.consecutive.plus.domain.com" );
    assertIsTrue( "+.+.+.+.+.+@plus.domain.com" );
    assertIsFalse( "name + <pointy.brackets1.with.plus.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.plus.in.display.name@domain.com> name +" );

    assertIsFalse( "domain.part@with+plus.com" );
    assertIsFalse( "domain.part@+with.plus.at.domain.start.com" );
    assertIsFalse( "domain.part@with.plus.at.domain.end1+.com" );
    assertIsFalse( "domain.part@with.plus.at.domain.end2.com+" );
    assertIsFalse( "domain.part@with.plus.before+.point.com" );
    assertIsFalse( "domain.part@with.plus.after.+point.com" );
    assertIsFalse( "domain.part@with.consecutive.plus++test.com" );
    assertIsTrue( "domain.part.with.plus.in.comment@(comment +)domain.com" );
    assertIsFalse( "domain.part.only.plus@+.com" );
    assertIsFalse( "top.level.domain.only@plus.+" );

    assertIsTrue( "{.local.part.starts.with.leftbracket@domain.com" );
    assertIsTrue( "local.part.ends.with.leftbracket{@domain.com" );
    assertIsTrue( "local.part.with.leftbracket{character@domain.com" );
    assertIsTrue( "local.part.with.leftbracket.before{.point@domain.com" );
    assertIsTrue( "local.part.with.leftbracket.after.{point@domain.com" );
    assertIsTrue( "local.part.with.double.leftbracket{{test@domain.com" );
    assertIsTrue( "(comment {) local.part.with.leftbracket.in.comment@domain.com" );
    assertIsTrue( "\"string{\".local.part.with.leftbracket.in.String@domain.com" );
    assertIsFalse( "\"string\\{\".local.part.with.escaped.leftbracket.in.String@domain.com" );
    assertIsTrue( "{@local.part.only.leftbracket.domain.com" );
    assertIsTrue( "{{{{{{@local.part.only.consecutive.leftbracket.domain.com" );
    assertIsTrue( "{.{.{.{.{.{@leftbracket.domain.com" );
    assertIsFalse( "name { <pointy.brackets1.with.leftbracket.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name {" );

    assertIsFalse( "domain.part@with{leftbracket.com" );
    assertIsFalse( "domain.part@{with.leftbracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.leftbracket.at.domain.end1{.com" );
    assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com{" );
    assertIsFalse( "domain.part@with.leftbracket.before{.point.com" );
    assertIsFalse( "domain.part@with.leftbracket.after.{point.com" );
    assertIsFalse( "domain.part@with.consecutive.leftbracket{{test.com" );
    assertIsTrue( "domain.part.with.leftbracket.in.comment@(comment {)domain.com" );
    assertIsFalse( "domain.part.only.leftbracket@{.com" );
    assertIsFalse( "top.level.domain.only@leftbracket.{" );

    assertIsTrue( "}.local.part.starts.with.rightbracket@domain.com" );
    assertIsTrue( "local.part.ends.with.rightbracket}@domain.com" );
    assertIsTrue( "local.part.with.rightbracket}character@domain.com" );
    assertIsTrue( "local.part.with.rightbracket.before}.point@domain.com" );
    assertIsTrue( "local.part.with.rightbracket.after.}point@domain.com" );
    assertIsTrue( "local.part.with.double.rightbracket}}test@domain.com" );
    assertIsTrue( "(comment }) local.part.with.rightbracket.in.comment@domain.com" );
    assertIsTrue( "\"string}\".local.part.with.rightbracket.in.String@domain.com" );
    assertIsFalse( "\"string\\}\".local.part.with.escaped.rightbracket.in.String@domain.com" );
    assertIsTrue( "}@local.part.only.rightbracket.domain.com" );
    assertIsTrue( "}}}}}}@local.part.only.consecutive.rightbracket.domain.com" );
    assertIsTrue( "}.}.}.}.}.}@rightbracket.domain.com" );
    assertIsFalse( "name } <pointy.brackets1.with.rightbracket.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name }" );

    assertIsFalse( "domain.part@with}rightbracket.com" );
    assertIsFalse( "domain.part@}with.rightbracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.rightbracket.at.domain.end1}.com" );
    assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com}" );
    assertIsFalse( "domain.part@with.rightbracket.before}.point.com" );
    assertIsFalse( "domain.part@with.rightbracket.after.}point.com" );
    assertIsFalse( "domain.part@with.consecutive.rightbracket}}test.com" );
    assertIsTrue( "domain.part.with.rightbracket.in.comment@(comment })domain.com" );
    assertIsFalse( "domain.part.only.rightbracket@}.com" );
    assertIsFalse( "top.level.domain.only@rightbracket.}" );

    assertIsFalse( "(.local.part.starts.with.leftbracket@domain.com" );
    assertIsFalse( "local.part.ends.with.leftbracket(@domain.com" );
    assertIsFalse( "local.part.with.leftbracket(character@domain.com" );
    assertIsFalse( "local.part.with.leftbracket.before(.point@domain.com" );
    assertIsFalse( "local.part.with.leftbracket.after.(point@domain.com" );
    assertIsFalse( "local.part.with.double.leftbracket((test@domain.com" );
    assertIsFalse( "(comment () local.part.with.leftbracket.in.comment@domain.com" );
    assertIsTrue( "\"string(\".local.part.with.leftbracket.in.String@domain.com" );
    assertIsFalse( "\"string\\(\".local.part.with.escaped.leftbracket.in.String@domain.com" );
    assertIsFalse( "(@local.part.only.leftbracket.domain.com" );
    assertIsFalse( "((((((@local.part.only.consecutive.leftbracket.domain.com" );
    assertIsFalse( "(.(.(.(.(.(@leftbracket.domain.com" );
    assertIsTrue( "name ( <pointy.brackets1.with.leftbracket.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name (" );

    assertIsFalse( "domain.part@with(leftbracket.com" );
    assertIsFalse( "domain.part@(with.leftbracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.leftbracket.at.domain.end1(.com" );
    assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com(" );
    assertIsFalse( "domain.part@with.leftbracket.before(.point.com" );
    assertIsFalse( "domain.part@with.leftbracket.after.(point.com" );
    assertIsFalse( "domain.part@with.consecutive.leftbracket((test.com" );
    assertIsFalse( "domain.part.with.leftbracket.in.comment@(comment ()domain.com" );
    assertIsFalse( "domain.part.only.leftbracket@(.com" );
    assertIsFalse( "top.level.domain.only@leftbracket.(" );

    assertIsFalse( ").local.part.starts.with.rightbracket@domain.com" );
    assertIsFalse( "local.part.ends.with.rightbracket)@domain.com" );
    assertIsFalse( "local.part.with.rightbracket)character@domain.com" );
    assertIsFalse( "local.part.with.rightbracket.before).point@domain.com" );
    assertIsFalse( "local.part.with.rightbracket.after.)point@domain.com" );
    assertIsFalse( "local.part.with.double.rightbracket))test@domain.com" );
    assertIsFalse( "(comment )) local.part.with.rightbracket.in.comment@domain.com" );
    assertIsTrue( "\"string)\".local.part.with.rightbracket.in.String@domain.com" );
    assertIsFalse( "\"string\\)\".local.part.with.escaped.rightbracket.in.String@domain.com" );
    assertIsFalse( ")@local.part.only.rightbracket.domain.com" );
    assertIsFalse( "))))))@local.part.only.consecutive.rightbracket.domain.com" );
    assertIsFalse( ").).).).).)@rightbracket.domain.com" );
    assertIsTrue( "name ) <pointy.brackets1.with.rightbracket.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name )" );

    assertIsFalse( "domain.part@with)rightbracket.com" );
    assertIsFalse( "domain.part@)with.rightbracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.rightbracket.at.domain.end1).com" );
    assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com)" );
    assertIsFalse( "domain.part@with.rightbracket.before).point.com" );
    assertIsFalse( "domain.part@with.rightbracket.after.)point.com" );
    assertIsFalse( "domain.part@with.consecutive.rightbracket))test.com" );
    assertIsFalse( "domain.part.with.rightbracket.in.comment@(comment ))domain.com" );
    assertIsFalse( "domain.part.only.rightbracket@).com" );
    assertIsFalse( "top.level.domain.only@rightbracket.)" );

    assertIsFalse( "[.local.part.starts.with.leftbracket@domain.com" );
    assertIsFalse( "local.part.ends.with.leftbracket[@domain.com" );
    assertIsFalse( "local.part.with.leftbracket[character@domain.com" );
    assertIsFalse( "local.part.with.leftbracket.before[.point@domain.com" );
    assertIsFalse( "local.part.with.leftbracket.after.[point@domain.com" );
    assertIsFalse( "local.part.with.double.leftbracket[[test@domain.com" );
    assertIsFalse( "(comment [) local.part.with.leftbracket.in.comment@domain.com" );
    assertIsTrue( "\"string[\".local.part.with.leftbracket.in.String@domain.com" );
    assertIsFalse( "\"string\\[\".local.part.with.escaped.leftbracket.in.String@domain.com" );
    assertIsFalse( "[@local.part.only.leftbracket.domain.com" );
    assertIsFalse( "[[[[[[@local.part.only.consecutive.leftbracket.domain.com" );
    assertIsFalse( "[.[.[.[.[.[@leftbracket.domain.com" );
    assertIsFalse( "name [ <pointy.brackets1.with.leftbracket.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name [" );

    assertIsFalse( "domain.part@with[leftbracket.com" );
    assertIsFalse( "domain.part@[with.leftbracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.leftbracket.at.domain.end1[.com" );
    assertIsFalse( "domain.part@with.leftbracket.at.domain.end2.com[" );
    assertIsFalse( "domain.part@with.leftbracket.before[.point.com" );
    assertIsFalse( "domain.part@with.leftbracket.after.[point.com" );
    assertIsFalse( "domain.part@with.consecutive.leftbracket[[test.com" );
    assertIsFalse( "domain.part.with.leftbracket.in.comment@(comment [)domain.com" );
    assertIsFalse( "domain.part.only.leftbracket@[.com" );
    assertIsFalse( "top.level.domain.only@leftbracket.[" );

    assertIsFalse( "].local.part.starts.with.rightbracket@domain.com" );
    assertIsFalse( "local.part.ends.with.rightbracket]@domain.com" );
    assertIsFalse( "local.part.with.rightbracket]character@domain.com" );
    assertIsFalse( "local.part.with.rightbracket.before].point@domain.com" );
    assertIsFalse( "local.part.with.rightbracket.after.]point@domain.com" );
    assertIsFalse( "local.part.with.double.rightbracket]]test@domain.com" );
    assertIsFalse( "(comment ]) local.part.with.rightbracket.in.comment@domain.com" );
    assertIsTrue( "\"string]\".local.part.with.rightbracket.in.String@domain.com" );
    assertIsFalse( "\"string\\]\".local.part.with.escaped.rightbracket.in.String@domain.com" );
    assertIsFalse( "]@local.part.only.rightbracket.domain.com" );
    assertIsFalse( "]]]]]]@local.part.only.consecutive.rightbracket.domain.com" );
    assertIsFalse( "].].].].].]@rightbracket.domain.com" );
    assertIsFalse( "name ] <pointy.brackets1.with.rightbracket.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name ]" );

    assertIsFalse( "domain.part@with]rightbracket.com" );
    assertIsFalse( "domain.part@]with.rightbracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.rightbracket.at.domain.end1].com" );
    assertIsFalse( "domain.part@with.rightbracket.at.domain.end2.com]" );
    assertIsFalse( "domain.part@with.rightbracket.before].point.com" );
    assertIsFalse( "domain.part@with.rightbracket.after.]point.com" );
    assertIsFalse( "domain.part@with.consecutive.rightbracket]]test.com" );
    assertIsFalse( "domain.part.with.rightbracket.in.comment@(comment ])domain.com" );
    assertIsFalse( "domain.part.only.rightbracket@].com" );
    assertIsFalse( "top.level.domain.only@rightbracket.]" );

    assertIsFalse( "().local.part.starts.with.empty.bracket@domain.com" );
    assertIsTrue( "local.part.ends.with.empty.bracket()@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket()character@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket.before().point@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket.after.()point@domain.com" );
    assertIsFalse( "local.part.with.double.empty.bracket()()test@domain.com" );
    assertIsFalse( "(comment ()) local.part.with.empty.bracket.in.comment@domain.com" );
    assertIsTrue( "\"string()\".local.part.with.empty.bracket.in.String@domain.com" );
    assertIsFalse( "\"string\\()\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
    assertIsFalse( "()@local.part.only.empty.bracket.domain.com" );
    assertIsFalse( "()()()()()()@local.part.only.consecutive.empty.bracket.domain.com" );
    assertIsFalse( "().().().().().()@empty.bracket.domain.com" );
    assertIsTrue( "name () <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name ()" );

    assertIsFalse( "domain.part@with()empty.bracket.com" );
    assertIsTrue( "domain.part@()with.empty.bracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1().com" );
    assertIsTrue( "domain.part@with.empty.bracket.at.domain.end2.com()" );
    assertIsFalse( "domain.part@with.empty.bracket.before().point.com" );
    assertIsFalse( "domain.part@with.empty.bracket.after.()point.com" );
    assertIsFalse( "domain.part@with.consecutive.empty.bracket()()test.com" );
    assertIsFalse( "domain.part.with.empty.bracket.in.comment@(comment ())domain.com" );
    assertIsFalse( "domain.part.only.empty.bracket@().com" );
    assertIsFalse( "top.level.domain.only@empty.bracket.()" );

    assertIsTrue( "{}.local.part.starts.with.empty.bracket@domain.com" );
    assertIsTrue( "local.part.ends.with.empty.bracket{}@domain.com" );
    assertIsTrue( "local.part.with.empty.bracket{}character@domain.com" );
    assertIsTrue( "local.part.with.empty.bracket.before{}.point@domain.com" );
    assertIsTrue( "local.part.with.empty.bracket.after.{}point@domain.com" );
    assertIsTrue( "local.part.with.double.empty.bracket{}{}test@domain.com" );
    assertIsTrue( "(comment {}) local.part.with.empty.bracket.in.comment@domain.com" );
    assertIsTrue( "\"string{}\".local.part.with.empty.bracket.in.String@domain.com" );
    assertIsFalse( "\"string\\{}\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
    assertIsTrue( "{}@local.part.only.empty.bracket.domain.com" );
    assertIsTrue( "{}{}{}{}{}{}@local.part.only.consecutive.empty.bracket.domain.com" );
    assertIsTrue( "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com" );
    assertIsFalse( "name {} <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name {}" );

    assertIsFalse( "domain.part@with{}empty.bracket.com" );
    assertIsFalse( "domain.part@{}with.empty.bracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1{}.com" );
    assertIsFalse( "domain.part@with.empty.bracket.at.domain.end2.com{}" );
    assertIsFalse( "domain.part@with.empty.bracket.before{}.point.com" );
    assertIsFalse( "domain.part@with.empty.bracket.after.{}point.com" );
    assertIsFalse( "domain.part@with.consecutive.empty.bracket{}{}test.com" );
    assertIsTrue( "domain.part.with.empty.bracket.in.comment@(comment {})domain.com" );
    assertIsFalse( "domain.part.only.empty.bracket@{}.com" );
    assertIsFalse( "top.level.domain.only@empty.bracket.{}" );

    assertIsFalse( "[].local.part.starts.with.empty.bracket@domain.com" );
    assertIsFalse( "local.part.ends.with.empty.bracket[]@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket[]character@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket.before[].point@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket.after.[]point@domain.com" );
    assertIsFalse( "local.part.with.double.empty.bracket[][]test@domain.com" );
    assertIsFalse( "(comment []) local.part.with.empty.bracket.in.comment@domain.com" );
    assertIsTrue( "\"string[]\".local.part.with.empty.bracket.in.String@domain.com" );
    assertIsFalse( "\"string\\[]\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
    assertIsFalse( "[]@local.part.only.empty.bracket.domain.com" );
    assertIsFalse( "[][][][][][]@local.part.only.consecutive.empty.bracket.domain.com" );
    assertIsFalse( "[].[].[].[].[].[]@empty.bracket.domain.com" );
    assertIsFalse( "name [] <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name []" );

    assertIsFalse( "domain.part@with[]empty.bracket.com" );
    assertIsFalse( "domain.part@[]with.empty.bracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1[].com" );
    assertIsFalse( "domain.part@with.empty.bracket.at.domain.end2.com[]" );
    assertIsFalse( "domain.part@with.empty.bracket.before[].point.com" );
    assertIsFalse( "domain.part@with.empty.bracket.after.[]point.com" );
    assertIsFalse( "domain.part@with.consecutive.empty.bracket[][]test.com" );
    assertIsFalse( "domain.part.with.empty.bracket.in.comment@(comment [])domain.com" );
    assertIsFalse( "domain.part.only.empty.bracket@[].com" );
    assertIsFalse( "top.level.domain.only@empty.bracket.[]" );

    assertIsFalse( "<>.local.part.starts.with.empty.bracket@domain.com" );
    assertIsFalse( "local.part.ends.with.empty.bracket<>@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket<>character@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket.before<>.point@domain.com" );
    assertIsFalse( "local.part.with.empty.bracket.after.<>point@domain.com" );
    assertIsFalse( "local.part.with.double.empty.bracket<><>test@domain.com" );
    assertIsFalse( "(comment <>) local.part.with.empty.bracket.in.comment@domain.com" );
    assertIsTrue( "\"string<>\".local.part.with.empty.bracket.in.String@domain.com" );
    assertIsFalse( "\"string\\<>\".local.part.with.escaped.empty.bracket.in.String@domain.com" );
    assertIsFalse( "<>@local.part.only.empty.bracket.domain.com" );
    assertIsFalse( "<><><><><><>@local.part.only.consecutive.empty.bracket.domain.com" );
    assertIsFalse( "<>.<>.<>.<>.<>.<>@empty.bracket.domain.com" );
    assertIsFalse( "name <> <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name <>" );

    assertIsFalse( "domain.part@with<>empty.bracket.com" );
    assertIsFalse( "domain.part@<>with.empty.bracket.at.domain.start.com" );
    assertIsFalse( "domain.part@with.empty.bracket.at.domain.end1<>.com" );
    assertIsFalse( "domain.part@with.empty.bracket.at.domain.end2.com<>" );
    assertIsFalse( "domain.part@with.empty.bracket.before<>.point.com" );
    assertIsFalse( "domain.part@with.empty.bracket.after.<>point.com" );
    assertIsFalse( "domain.part@with.consecutive.empty.bracket<><>test.com" );
    assertIsFalse( "domain.part.with.empty.bracket.in.comment@(comment <>)domain.com" );
    assertIsFalse( "domain.part.only.empty.bracket@<>.com" );
    assertIsFalse( "top.level.domain.only@empty.bracket.<>" );

    assertIsFalse( ")(.local.part.starts.with.false.bracket1@domain.com" );
    assertIsFalse( "local.part.ends.with.false.bracket1)(@domain.com" );
    assertIsFalse( "local.part.with.false.bracket1)(character@domain.com" );
    assertIsFalse( "local.part.with.false.bracket1.before)(.point@domain.com" );
    assertIsFalse( "local.part.with.false.bracket1.after.)(point@domain.com" );
    assertIsFalse( "local.part.with.double.false.bracket1)()(test@domain.com" );
    assertIsFalse( "(comment )() local.part.with.false.bracket1.in.comment@domain.com" );
    assertIsTrue( "\"string)(\".local.part.with.false.bracket1.in.String@domain.com" );
    assertIsFalse( "\"string\\)(\".local.part.with.escaped.false.bracket1.in.String@domain.com" );
    assertIsFalse( ")(@local.part.only.false.bracket1.domain.com" );
    assertIsFalse( ")()()()()()(@local.part.only.consecutive.false.bracket1.domain.com" );
    assertIsFalse( ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com" );
    assertIsTrue( "name )( <pointy.brackets1.with.false.bracket1.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.false.bracket1.in.display.name@domain.com> name )(" );

    assertIsFalse( "domain.part@with)(false.bracket1.com" );
    assertIsFalse( "domain.part@)(with.false.bracket1.at.domain.start.com" );
    assertIsFalse( "domain.part@with.false.bracket1.at.domain.end1)(.com" );
    assertIsFalse( "domain.part@with.false.bracket1.at.domain.end2.com)(" );
    assertIsFalse( "domain.part@with.false.bracket1.before)(.point.com" );
    assertIsFalse( "domain.part@with.false.bracket1.after.)(point.com" );
    assertIsFalse( "domain.part@with.consecutive.false.bracket1)()(test.com" );
    assertIsFalse( "domain.part.with.false.bracket1.in.comment@(comment )()domain.com" );
    assertIsFalse( "domain.part.only.false.bracket1@)(.com" );
    assertIsFalse( "top.level.domain.only@false.bracket1.)(" );

    assertIsTrue( "}{.local.part.starts.with.false.bracket2@domain.com" );
    assertIsTrue( "local.part.ends.with.false.bracket2}{@domain.com" );
    assertIsTrue( "local.part.with.false.bracket2}{character@domain.com" );
    assertIsTrue( "local.part.with.false.bracket2.before}{.point@domain.com" );
    assertIsTrue( "local.part.with.false.bracket2.after.}{point@domain.com" );
    assertIsTrue( "local.part.with.double.false.bracket2}{}{test@domain.com" );
    assertIsTrue( "(comment }{) local.part.with.false.bracket2.in.comment@domain.com" );
    assertIsTrue( "\"string}{\".local.part.with.false.bracket2.in.String@domain.com" );
    assertIsFalse( "\"string\\}{\".local.part.with.escaped.false.bracket2.in.String@domain.com" );
    assertIsTrue( "}{@local.part.only.false.bracket2.domain.com" );
    assertIsTrue( "}{}{}{}{}{}{@local.part.only.consecutive.false.bracket2.domain.com" );
    assertIsTrue( "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com" );
    assertIsFalse( "name }{ <pointy.brackets1.with.false.bracket2.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.false.bracket2.in.display.name@domain.com> name }{" );

    assertIsFalse( "domain.part@with}{false.bracket2.com" );
    assertIsFalse( "domain.part@}{with.false.bracket2.at.domain.start.com" );
    assertIsFalse( "domain.part@with.false.bracket2.at.domain.end1}{.com" );
    assertIsFalse( "domain.part@with.false.bracket2.at.domain.end2.com}{" );
    assertIsFalse( "domain.part@with.false.bracket2.before}{.point.com" );
    assertIsFalse( "domain.part@with.false.bracket2.after.}{point.com" );
    assertIsFalse( "domain.part@with.consecutive.false.bracket2}{}{test.com" );
    assertIsTrue( "domain.part.with.false.bracket2.in.comment@(comment }{)domain.com" );
    assertIsFalse( "domain.part.only.false.bracket2@}{.com" );
    assertIsFalse( "top.level.domain.only@false.bracket2.}{" );

    assertIsFalse( "][.local.part.starts.with.false.bracket3@domain.com" );
    assertIsFalse( "local.part.ends.with.false.bracket3][@domain.com" );
    assertIsFalse( "local.part.with.false.bracket3][character@domain.com" );
    assertIsFalse( "local.part.with.false.bracket3.before][.point@domain.com" );
    assertIsFalse( "local.part.with.false.bracket3.after.][point@domain.com" );
    assertIsFalse( "local.part.with.double.false.bracket3][][test@domain.com" );
    assertIsFalse( "(comment ][) local.part.with.false.bracket3.in.comment@domain.com" );
    assertIsTrue( "\"string][\".local.part.with.false.bracket3.in.String@domain.com" );
    assertIsFalse( "\"string\\][\".local.part.with.escaped.false.bracket3.in.String@domain.com" );
    assertIsFalse( "][@local.part.only.false.bracket3.domain.com" );
    assertIsFalse( "][][][][][][@local.part.only.consecutive.false.bracket3.domain.com" );
    assertIsFalse( "][.][.][.][.][.][@false.bracket3.domain.com" );
    assertIsFalse( "name ][ <pointy.brackets1.with.false.bracket3.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.false.bracket3.in.display.name@domain.com> name ][" );

    assertIsFalse( "domain.part@with][false.bracket3.com" );
    assertIsFalse( "domain.part@][with.false.bracket3.at.domain.start.com" );
    assertIsFalse( "domain.part@with.false.bracket3.at.domain.end1][.com" );
    assertIsFalse( "domain.part@with.false.bracket3.at.domain.end2.com][" );
    assertIsFalse( "domain.part@with.false.bracket3.before][.point.com" );
    assertIsFalse( "domain.part@with.false.bracket3.after.][point.com" );
    assertIsFalse( "domain.part@with.consecutive.false.bracket3][][test.com" );
    assertIsFalse( "domain.part.with.false.bracket3.in.comment@(comment ][)domain.com" );
    assertIsFalse( "domain.part.only.false.bracket3@][.com" );
    assertIsFalse( "top.level.domain.only@false.bracket3.][" );

    assertIsFalse( "><.local.part.starts.with.false.bracket4@domain.com" );
    assertIsFalse( "local.part.ends.with.false.bracket4><@domain.com" );
    assertIsFalse( "local.part.with.false.bracket4><character@domain.com" );
    assertIsFalse( "local.part.with.false.bracket4.before><.point@domain.com" );
    assertIsFalse( "local.part.with.false.bracket4.after.><point@domain.com" );
    assertIsFalse( "local.part.with.double.false.bracket4><><test@domain.com" );
    assertIsFalse( "(comment ><) local.part.with.false.bracket4.in.comment@domain.com" );
    assertIsTrue( "\"string><\".local.part.with.false.bracket4.in.String@domain.com" );
    assertIsFalse( "\"string\\><\".local.part.with.escaped.false.bracket4.in.String@domain.com" );
    assertIsFalse( "><@local.part.only.false.bracket4.domain.com" );
    assertIsFalse( "><><><><><><@local.part.only.consecutive.false.bracket4.domain.com" );
    assertIsFalse( "><.><.><.><.><.><@false.bracket4.domain.com" );
    assertIsFalse( "name >< <pointy.brackets1.with.false.bracket4.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.false.bracket4.in.display.name@domain.com> name ><" );

    assertIsFalse( "domain.part@with><false.bracket4.com" );
    assertIsFalse( "domain.part@><with.false.bracket4.at.domain.start.com" );
    assertIsFalse( "domain.part@with.false.bracket4.at.domain.end1><.com" );
    assertIsFalse( "domain.part@with.false.bracket4.at.domain.end2.com><" );
    assertIsFalse( "domain.part@with.false.bracket4.before><.point.com" );
    assertIsFalse( "domain.part@with.false.bracket4.after.><point.com" );
    assertIsFalse( "domain.part@with.consecutive.false.bracket4><><test.com" );
    assertIsFalse( "domain.part.with.false.bracket4.in.comment@(comment ><)domain.com" );
    assertIsFalse( "domain.part.only.false.bracket4@><.com" );
    assertIsFalse( "top.level.domain.only@false.bracket4.><" );

    assertIsFalse( "<.local.part.starts.with.lower.than@domain.com" );
    assertIsFalse( "local.part.ends.with.lower.than<@domain.com" );
    assertIsFalse( "local.part.with.lower.than<character@domain.com" );
    assertIsFalse( "local.part.with.lower.than.before<.point@domain.com" );
    assertIsFalse( "local.part.with.lower.than.after.<point@domain.com" );
    assertIsFalse( "local.part.with.double.lower.than<<test@domain.com" );
    assertIsFalse( "(comment <) local.part.with.lower.than.in.comment@domain.com" );
    assertIsTrue( "\"string<\".local.part.with.lower.than.in.String@domain.com" );
    assertIsFalse( "\"string\\<\".local.part.with.escaped.lower.than.in.String@domain.com" );
    assertIsFalse( "<@local.part.only.lower.than.domain.com" );
    assertIsFalse( "<<<<<<@local.part.only.consecutive.lower.than.domain.com" );
    assertIsFalse( "<.<.<.<.<.<@lower.than.domain.com" );
    assertIsFalse( "name < <pointy.brackets1.with.lower.than.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.lower.than.in.display.name@domain.com> name <" );

    assertIsFalse( "domain.part@with<lower.than.com" );
    assertIsFalse( "domain.part@<with.lower.than.at.domain.start.com" );
    assertIsFalse( "domain.part@with.lower.than.at.domain.end1<.com" );
    assertIsFalse( "domain.part@with.lower.than.at.domain.end2.com<" );
    assertIsFalse( "domain.part@with.lower.than.before<.point.com" );
    assertIsFalse( "domain.part@with.lower.than.after.<point.com" );
    assertIsFalse( "domain.part@with.consecutive.lower.than<<test.com" );
    assertIsFalse( "domain.part.with.lower.than.in.comment@(comment <)domain.com" );
    assertIsFalse( "domain.part.only.lower.than@<.com" );
    assertIsFalse( "top.level.domain.only@lower.than.<" );

    assertIsFalse( ">.local.part.starts.with.greater.than@domain.com" );
    assertIsFalse( "local.part.ends.with.greater.than>@domain.com" );
    assertIsFalse( "local.part.with.greater.than>character@domain.com" );
    assertIsFalse( "local.part.with.greater.than.before>.point@domain.com" );
    assertIsFalse( "local.part.with.greater.than.after.>point@domain.com" );
    assertIsFalse( "local.part.with.double.greater.than>>test@domain.com" );
    assertIsFalse( "(comment >) local.part.with.greater.than.in.comment@domain.com" );
    assertIsTrue( "\"string>\".local.part.with.greater.than.in.String@domain.com" );
    assertIsFalse( "\"string\\>\".local.part.with.escaped.greater.than.in.String@domain.com" );
    assertIsFalse( ">@local.part.only.greater.than.domain.com" );
    assertIsFalse( ">>>>>>@local.part.only.consecutive.greater.than.domain.com" );
    assertIsFalse( ">.>.>.>.>.>@greater.than.domain.com" );
    assertIsFalse( "name > <pointy.brackets1.with.greater.than.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.greater.than.in.display.name@domain.com> name >" );

    assertIsFalse( "domain.part@with>greater.than.com" );
    assertIsFalse( "domain.part@>with.greater.than.at.domain.start.com" );
    assertIsFalse( "domain.part@with.greater.than.at.domain.end1>.com" );
    assertIsFalse( "domain.part@with.greater.than.at.domain.end2.com>" );
    assertIsFalse( "domain.part@with.greater.than.before>.point.com" );
    assertIsFalse( "domain.part@with.greater.than.after.>point.com" );
    assertIsFalse( "domain.part@with.consecutive.greater.than>>test.com" );
    assertIsFalse( "domain.part.with.greater.than.in.comment@(comment >)domain.com" );
    assertIsFalse( "domain.part.only.greater.than@>.com" );
    assertIsFalse( "top.level.domain.only@greater.than.>" );

    assertIsTrue( "~.local.part.starts.with.tilde@domain.com" );
    assertIsTrue( "local.part.ends.with.tilde~@domain.com" );
    assertIsTrue( "local.part.with.tilde~character@domain.com" );
    assertIsTrue( "local.part.with.tilde.before~.point@domain.com" );
    assertIsTrue( "local.part.with.tilde.after.~point@domain.com" );
    assertIsTrue( "local.part.with.double.tilde~~test@domain.com" );
    assertIsTrue( "(comment ~) local.part.with.tilde.in.comment@domain.com" );
    assertIsTrue( "\"string~\".local.part.with.tilde.in.String@domain.com" );
    assertIsFalse( "\"string\\~\".local.part.with.escaped.tilde.in.String@domain.com" );
    assertIsTrue( "~@local.part.only.tilde.domain.com" );
    assertIsTrue( "~~~~~~@local.part.only.consecutive.tilde.domain.com" );
    assertIsTrue( "~.~.~.~.~.~@tilde.domain.com" );
    assertIsFalse( "name ~ <pointy.brackets1.with.tilde.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.tilde.in.display.name@domain.com> name ~" );

    assertIsFalse( "domain.part@with~tilde.com" );
    assertIsFalse( "domain.part@~with.tilde.at.domain.start.com" );
    assertIsFalse( "domain.part@with.tilde.at.domain.end1~.com" );
    assertIsFalse( "domain.part@with.tilde.at.domain.end2.com~" );
    assertIsFalse( "domain.part@with.tilde.before~.point.com" );
    assertIsFalse( "domain.part@with.tilde.after.~point.com" );
    assertIsFalse( "domain.part@with.consecutive.tilde~~test.com" );
    assertIsTrue( "domain.part.with.tilde.in.comment@(comment ~)domain.com" );
    assertIsFalse( "domain.part.only.tilde@~.com" );
    assertIsFalse( "top.level.domain.only@tilde.~" );

    assertIsTrue( "^.local.part.starts.with.xor@domain.com" );
    assertIsTrue( "local.part.ends.with.xor^@domain.com" );
    assertIsTrue( "local.part.with.xor^character@domain.com" );
    assertIsTrue( "local.part.with.xor.before^.point@domain.com" );
    assertIsTrue( "local.part.with.xor.after.^point@domain.com" );
    assertIsTrue( "local.part.with.double.xor^^test@domain.com" );
    assertIsTrue( "(comment ^) local.part.with.xor.in.comment@domain.com" );
    assertIsTrue( "\"string^\".local.part.with.xor.in.String@domain.com" );
    assertIsFalse( "\"string\\^\".local.part.with.escaped.xor.in.String@domain.com" );
    assertIsTrue( "^@local.part.only.xor.domain.com" );
    assertIsTrue( "^^^^^^@local.part.only.consecutive.xor.domain.com" );
    assertIsTrue( "^.^.^.^.^.^@xor.domain.com" );
    assertIsFalse( "name ^ <pointy.brackets1.with.xor.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.xor.in.display.name@domain.com> name ^" );

    assertIsFalse( "domain.part@with^xor.com" );
    assertIsFalse( "domain.part@^with.xor.at.domain.start.com" );
    assertIsFalse( "domain.part@with.xor.at.domain.end1^.com" );
    assertIsFalse( "domain.part@with.xor.at.domain.end2.com^" );
    assertIsFalse( "domain.part@with.xor.before^.point.com" );
    assertIsFalse( "domain.part@with.xor.after.^point.com" );
    assertIsFalse( "domain.part@with.consecutive.xor^^test.com" );
    assertIsTrue( "domain.part.with.xor.in.comment@(comment ^)domain.com" );
    assertIsFalse( "domain.part.only.xor@^.com" );
    assertIsFalse( "top.level.domain.only@xor.^" );

    assertIsFalse( ":.local.part.starts.with.colon@domain.com" );
    assertIsFalse( "local.part.ends.with.colon:@domain.com" );
    assertIsFalse( "local.part.with.colon:character@domain.com" );
    assertIsFalse( "local.part.with.colon.before:.point@domain.com" );
    assertIsFalse( "local.part.with.colon.after.:point@domain.com" );
    assertIsFalse( "local.part.with.double.colon::test@domain.com" );
    assertIsFalse( "(comment :) local.part.with.colon.in.comment@domain.com" );
    assertIsTrue( "\"string:\".local.part.with.colon.in.String@domain.com" );
    assertIsFalse( "\"string\\:\".local.part.with.escaped.colon.in.String@domain.com" );
    assertIsFalse( ":@local.part.only.colon.domain.com" );
    assertIsFalse( "::::::@local.part.only.consecutive.colon.domain.com" );
    assertIsFalse( ":.:.:.:.:.:@colon.domain.com" );
    assertIsFalse( "name : <pointy.brackets1.with.colon.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.colon.in.display.name@domain.com> name :" );

    assertIsFalse( "domain.part@with:colon.com" );
    assertIsFalse( "domain.part@:with.colon.at.domain.start.com" );
    assertIsFalse( "domain.part@with.colon.at.domain.end1:.com" );
    assertIsFalse( "domain.part@with.colon.at.domain.end2.com:" );
    assertIsFalse( "domain.part@with.colon.before:.point.com" );
    assertIsFalse( "domain.part@with.colon.after.:point.com" );
    assertIsFalse( "domain.part@with.consecutive.colon::test.com" );
    assertIsFalse( "domain.part.with.colon.in.comment@(comment :)domain.com" );
    assertIsFalse( "domain.part.only.colon@:.com" );
    assertIsFalse( "top.level.domain.only@colon.:" );

    assertIsFalse( " .local.part.starts.with.space@domain.com" );
    assertIsFalse( "local.part.ends.with.space @domain.com" );
    assertIsFalse( "local.part.with.space character@domain.com" );
    assertIsFalse( "local.part.with.space.before .point@domain.com" );
    assertIsFalse( "local.part.with.space.after. point@domain.com" );
    assertIsFalse( "local.part.with.double.space  test@domain.com" );
    assertIsTrue( "(comment  ) local.part.with.space.in.comment@domain.com" );
    assertIsTrue( "\"string \".local.part.with.space.in.String@domain.com" );
    assertIsTrue( "\"string\\ \".local.part.with.escaped.space.in.String@domain.com" );
    assertIsFalse( " @local.part.only.space.domain.com" );
    assertIsFalse( "      @local.part.only.consecutive.space.domain.com" );
    assertIsFalse( " . . . . . @space.domain.com" );
    assertIsTrue( "name   <pointy.brackets1.with.space.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.space.in.display.name@domain.com> name  " );

    assertIsFalse( "domain.part@with space.com" );
    assertIsFalse( "domain.part@ with.space.at.domain.start.com" );
    assertIsFalse( "domain.part@with.space.at.domain.end1 .com" );
    assertIsFalse( "domain.part@with.space.at.domain.end2.com " );
    assertIsFalse( "domain.part@with.space.before .point.com" );
    assertIsFalse( "domain.part@with.space.after. point.com" );
    assertIsFalse( "domain.part@with.consecutive.space  test.com" );
    assertIsTrue( "domain.part.with.space.in.comment@(comment  )domain.com" );
    assertIsFalse( "domain.part.only.space@ .com" );
    assertIsFalse( "top.level.domain.only@space. " );

    assertIsFalse( ",.local.part.starts.with.comma@domain.com" );
    assertIsFalse( "local.part.ends.with.comma,@domain.com" );
    assertIsFalse( "local.part.with.comma,character@domain.com" );
    assertIsFalse( "local.part.with.comma.before,.point@domain.com" );
    assertIsFalse( "local.part.with.comma.after.,point@domain.com" );
    assertIsFalse( "local.part.with.double.comma,,test@domain.com" );
    assertIsFalse( "(comment ,) local.part.with.comma.in.comment@domain.com" );
    assertIsTrue( "\"string,\".local.part.with.comma.in.String@domain.com" );
    assertIsFalse( "\"string\\,\".local.part.with.escaped.comma.in.String@domain.com" );
    assertIsFalse( ",@local.part.only.comma.domain.com" );
    assertIsFalse( ",,,,,,@local.part.only.consecutive.comma.domain.com" );
    assertIsFalse( ",.,.,.,.,.,@comma.domain.com" );
    assertIsFalse( "name , <pointy.brackets1.with.comma.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.comma.in.display.name@domain.com> name ," );

    assertIsFalse( "domain.part@with,comma.com" );
    assertIsFalse( "domain.part@,with.comma.at.domain.start.com" );
    assertIsFalse( "domain.part@with.comma.at.domain.end1,.com" );
    assertIsFalse( "domain.part@with.comma.at.domain.end2.com," );
    assertIsFalse( "domain.part@with.comma.before,.point.com" );
    assertIsFalse( "domain.part@with.comma.after.,point.com" );
    assertIsFalse( "domain.part@with.consecutive.comma,,test.com" );
    assertIsFalse( "domain.part.with.comma.in.comment@(comment ,)domain.com" );
    assertIsFalse( "domain.part.only.comma@,.com" );
    assertIsFalse( "top.level.domain.only@comma.," );

    assertIsFalse( "@.local.part.starts.with.at@domain.com" );
    assertIsFalse( "local.part.ends.with.at@@domain.com" );
    assertIsFalse( "local.part.with.at@character@domain.com" );
    assertIsFalse( "local.part.with.at.before@.point@domain.com" );
    assertIsFalse( "local.part.with.at.after.@point@domain.com" );
    assertIsFalse( "local.part.with.double.at@@test@domain.com" );
    assertIsTrue( "(comment @) local.part.with.at.in.comment@domain.com" );
    assertIsTrue( "\"string@\".local.part.with.at.in.String@domain.com" );
    assertIsTrue( "\"string\\@\".local.part.with.escaped.at.in.String@domain.com" );
    assertIsFalse( "@@local.part.only.at.domain.com" );
    assertIsFalse( "@@@@@@@local.part.only.consecutive.at.domain.com" );
    assertIsFalse( "@.@.@.@.@.@@at.domain.com" );
    assertIsFalse( "name @ <pointy.brackets1.with.at.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.at.in.display.name@domain.com> name @" );

    assertIsFalse( "domain.part@with@at.com" );
    assertIsFalse( "domain.part@@with.at.at.domain.start.com" );
    assertIsFalse( "domain.part@with.at.at.domain.end1@.com" );
    assertIsFalse( "domain.part@with.at.at.domain.end2.com@" );
    assertIsFalse( "domain.part@with.at.before@.point.com" );
    assertIsFalse( "domain.part@with.at.after.@point.com" );
    assertIsFalse( "domain.part@with.consecutive.at@@test.com" );
    assertIsTrue( "domain.part.with.at.in.comment@(comment @)domain.com" );
    assertIsFalse( "domain.part.only.at@@.com" );
    assertIsFalse( "top.level.domain.only@at.@" );

    assertIsFalse( "§.local.part.starts.with.paragraph@domain.com" );
    assertIsFalse( "local.part.ends.with.paragraph§@domain.com" );
    assertIsFalse( "local.part.with.paragraph§character@domain.com" );
    assertIsFalse( "local.part.with.paragraph.before§.point@domain.com" );
    assertIsFalse( "local.part.with.paragraph.after.§point@domain.com" );
    assertIsFalse( "local.part.with.double.paragraph§§test@domain.com" );
    assertIsFalse( "(comment §) local.part.with.paragraph.in.comment@domain.com" );
    assertIsFalse( "\"string§\".local.part.with.paragraph.in.String@domain.com" );
    assertIsFalse( "\"string\\§\".local.part.with.escaped.paragraph.in.String@domain.com" );
    assertIsFalse( "§@local.part.only.paragraph.domain.com" );
    assertIsFalse( "§§§§§§@local.part.only.consecutive.paragraph.domain.com" );
    assertIsFalse( "§.§.§.§.§.§@paragraph.domain.com" );
    assertIsFalse( "name § <pointy.brackets1.with.paragraph.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.paragraph.in.display.name@domain.com> name §" );

    assertIsFalse( "domain.part@with§paragraph.com" );
    assertIsFalse( "domain.part@§with.paragraph.at.domain.start.com" );
    assertIsFalse( "domain.part@with.paragraph.at.domain.end1§.com" );
    assertIsFalse( "domain.part@with.paragraph.at.domain.end2.com§" );
    assertIsFalse( "domain.part@with.paragraph.before§.point.com" );
    assertIsFalse( "domain.part@with.paragraph.after.§point.com" );
    assertIsFalse( "domain.part@with.consecutive.paragraph§§test.com" );
    assertIsFalse( "domain.part.with.paragraph.in.comment@(comment §)domain.com" );
    assertIsFalse( "domain.part.only.paragraph@§.com" );
    assertIsFalse( "top.level.domain.only@paragraph.§" );

    assertIsTrue( "'.local.part.starts.with.double.quote@domain.com" );
    assertIsTrue( "local.part.ends.with.double.quote'@domain.com" );
    assertIsTrue( "local.part.with.double.quote'character@domain.com" );
    assertIsTrue( "local.part.with.double.quote.before'.point@domain.com" );
    assertIsTrue( "local.part.with.double.quote.after.'point@domain.com" );
    assertIsTrue( "local.part.with.double.double.quote''test@domain.com" );
    assertIsTrue( "(comment ') local.part.with.double.quote.in.comment@domain.com" );
    assertIsTrue( "\"string'\".local.part.with.double.quote.in.String@domain.com" );
    assertIsTrue( "\"string\\'\".local.part.with.escaped.double.quote.in.String@domain.com" );
    assertIsTrue( "'@local.part.only.double.quote.domain.com" );
    assertIsTrue( "''''''@local.part.only.consecutive.double.quote.domain.com" );
    assertIsTrue( "'.'.'.'.'.'@double.quote.domain.com" );
    assertIsFalse( "name ' <pointy.brackets1.with.double.quote.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.double.quote.in.display.name@domain.com> name '" );

    assertIsFalse( "domain.part@with'double.quote.com" );
    assertIsFalse( "domain.part@'with.double.quote.at.domain.start.com" );
    assertIsFalse( "domain.part@with.double.quote.at.domain.end1'.com" );
    assertIsFalse( "domain.part@with.double.quote.at.domain.end2.com'" );
    assertIsFalse( "domain.part@with.double.quote.before'.point.com" );
    assertIsFalse( "domain.part@with.double.quote.after.'point.com" );
    assertIsFalse( "domain.part@with.consecutive.double.quote''test.com" );
    assertIsTrue( "domain.part.with.double.quote.in.comment@(comment ')domain.com" );
    assertIsFalse( "domain.part.only.double.quote@'.com" );
    assertIsFalse( "top.level.domain.only@double.quote.'" );

    assertIsTrue( "/.local.part.starts.with.forward.slash@domain.com" );
    assertIsTrue( "local.part.ends.with.forward.slash/@domain.com" );
    assertIsTrue( "local.part.with.forward.slash/character@domain.com" );
    assertIsTrue( "local.part.with.forward.slash.before/.point@domain.com" );
    assertIsTrue( "local.part.with.forward.slash.after./point@domain.com" );
    assertIsTrue( "local.part.with.double.forward.slash//test@domain.com" );
    assertIsTrue( "(comment /) local.part.with.forward.slash.in.comment@domain.com" );
    assertIsTrue( "\"string/\".local.part.with.forward.slash.in.String@domain.com" );
    assertIsFalse( "\"string\\/\".local.part.with.escaped.forward.slash.in.String@domain.com" );
    assertIsTrue( "/@local.part.only.forward.slash.domain.com" );
    assertIsTrue( "//////@local.part.only.consecutive.forward.slash.domain.com" );
    assertIsTrue( "/./././././@forward.slash.domain.com" );
    assertIsFalse( "name / <pointy.brackets1.with.forward.slash.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.forward.slash.in.display.name@domain.com> name /" );

    assertIsFalse( "domain.part@with/forward.slash.com" );
    assertIsFalse( "domain.part@/with.forward.slash.at.domain.start.com" );
    assertIsFalse( "domain.part@with.forward.slash.at.domain.end1/.com" );
    assertIsFalse( "domain.part@with.forward.slash.at.domain.end2.com/" );
    assertIsFalse( "domain.part@with.forward.slash.before/.point.com" );
    assertIsFalse( "domain.part@with.forward.slash.after./point.com" );
    assertIsFalse( "domain.part@with.consecutive.forward.slash//test.com" );
    assertIsTrue( "domain.part.with.forward.slash.in.comment@(comment /)domain.com" );
    assertIsFalse( "domain.part.only.forward.slash@/.com" );
    assertIsFalse( "top.level.domain.only@forward.slash./" );

    assertIsTrue( "-.local.part.starts.with.hyphen@domain.com" );
    assertIsTrue( "local.part.ends.with.hyphen-@domain.com" );
    assertIsTrue( "local.part.with.hyphen-character@domain.com" );
    assertIsTrue( "local.part.with.hyphen.before-.point@domain.com" );
    assertIsTrue( "local.part.with.hyphen.after.-point@domain.com" );
    assertIsTrue( "local.part.with.double.hyphen--test@domain.com" );
    assertIsTrue( "(comment -) local.part.with.hyphen.in.comment@domain.com" );
    assertIsTrue( "\"string-\".local.part.with.hyphen.in.String@domain.com" );
    assertIsFalse( "\"string\\-\".local.part.with.escaped.hyphen.in.String@domain.com" );
    assertIsTrue( "-@local.part.only.hyphen.domain.com" );
    assertIsTrue( "------@local.part.only.consecutive.hyphen.domain.com" );
    assertIsTrue( "-.-.-.-.-.-@hyphen.domain.com" );
    assertIsFalse( "name - <pointy.brackets1.with.hyphen.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.hyphen.in.display.name@domain.com> name -" );

    assertIsTrue( "domain.part@with-hyphen.com" );
    assertIsFalse( "domain.part@-with.hyphen.at.domain.start.com" );
    assertIsFalse( "domain.part@with.hyphen.at.domain.end1-.com" );
    assertIsFalse( "domain.part@with.hyphen.at.domain.end2.com-" );
    assertIsFalse( "domain.part@with.hyphen.before-.point.com" );
    assertIsFalse( "domain.part@with.hyphen.after.-point.com" );
    assertIsTrue( "domain.part@with.consecutive.hyphen--test.com" );
    assertIsTrue( "domain.part.with.hyphen.in.comment@(comment -)domain.com" );
    assertIsFalse( "domain.part.only.hyphen@-.com" );
    assertIsFalse( "top.level.domain.only@hyphen.-" );

    assertIsFalse( "\"\".local.part.starts.with.empty.string1@domain.com" );
    assertIsFalse( "local.part.ends.with.empty.string1\"\"@domain.com" );
    assertIsFalse( "local.part.with.empty.string1\"\"character@domain.com" );
    assertIsFalse( "local.part.with.empty.string1.before\"\".point@domain.com" );
    assertIsFalse( "local.part.with.empty.string1.after.\"\"point@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string1\"\"\"\"test@domain.com" );
    assertIsFalse( "(comment \"\") local.part.with.empty.string1.in.comment@domain.com" );
    assertIsFalse( "\"string\"\"\".local.part.with.empty.string1.in.String@domain.com" );
    assertIsFalse( "\"string\\\"\"\".local.part.with.escaped.empty.string1.in.String@domain.com" );
    assertIsFalse( "\"\"@local.part.only.empty.string1.domain.com" );
    assertIsFalse( "\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.empty.string1.domain.com" );
    assertIsFalse( "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com" );
    assertIsTrue( "name \"\" <pointy.brackets1.with.empty.string1.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.empty.string1.in.display.name@domain.com> name \"\"" );

    assertIsFalse( "domain.part@with\"\"empty.string1.com" );
    assertIsFalse( "domain.part@\"\"with.empty.string1.at.domain.start.com" );
    assertIsFalse( "domain.part@with.empty.string1.at.domain.end1\"\".com" );
    assertIsFalse( "domain.part@with.empty.string1.at.domain.end2.com\"\"" );
    assertIsFalse( "domain.part@with.empty.string1.before\"\".point.com" );
    assertIsFalse( "domain.part@with.empty.string1.after.\"\"point.com" );
    assertIsFalse( "domain.part@with.consecutive.empty.string1\"\"\"\"test.com" );
    assertIsFalse( "domain.part.with.empty.string1.in.comment@(comment \"\")domain.com" );
    assertIsFalse( "domain.part.only.empty.string1@\"\".com" );
    assertIsFalse( "top.level.domain.only@empty.string1.\"\"" );

    assertIsFalse( "a\"\"b.local.part.starts.with.empty.string2@domain.com" );
    assertIsFalse( "local.part.ends.with.empty.string2a\"\"b@domain.com" );
    assertIsFalse( "local.part.with.empty.string2a\"\"bcharacter@domain.com" );
    assertIsFalse( "local.part.with.empty.string2.beforea\"\"b.point@domain.com" );
    assertIsFalse( "local.part.with.empty.string2.after.a\"\"bpoint@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com" );
    assertIsFalse( "(comment a\"\"b) local.part.with.empty.string2.in.comment@domain.com" );
    assertIsFalse( "\"stringa\"\"b\".local.part.with.empty.string2.in.String@domain.com" );
    assertIsFalse( "\"string\\a\"\"b\".local.part.with.escaped.empty.string2.in.String@domain.com" );
    assertIsFalse( "a\"\"b@local.part.only.empty.string2.domain.com" );
    assertIsFalse( "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@local.part.only.consecutive.empty.string2.domain.com" );
    assertIsFalse( "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com" );
    assertIsTrue( "name a\"\"b <pointy.brackets1.with.empty.string2.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.empty.string2.in.display.name@domain.com> name a\"\"b" );

    assertIsFalse( "domain.part@witha\"\"bempty.string2.com" );
    assertIsFalse( "domain.part@a\"\"bwith.empty.string2.at.domain.start.com" );
    assertIsFalse( "domain.part@with.empty.string2.at.domain.end1a\"\"b.com" );
    assertIsFalse( "domain.part@with.empty.string2.at.domain.end2.coma\"\"b" );
    assertIsFalse( "domain.part@with.empty.string2.beforea\"\"b.point.com" );
    assertIsFalse( "domain.part@with.empty.string2.after.a\"\"bpoint.com" );
    assertIsFalse( "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com" );
    assertIsFalse( "domain.part.with.empty.string2.in.comment@(comment a\"\"b)domain.com" );
    assertIsFalse( "domain.part.only.empty.string2@a\"\"b.com" );
    assertIsFalse( "top.level.domain.only@empty.string2.a\"\"b" );

    assertIsFalse( "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com" );
    assertIsFalse( "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string1\"\"\"\"character@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com" );
    assertIsFalse( "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com" );
    assertIsFalse( "(comment \"\"\"\") local.part.with.double.empty.string1.in.comment@domain.com" );
    assertIsFalse( "\"string\"\"\"\"\".local.part.with.double.empty.string1.in.String@domain.com" );
    assertIsFalse( "\"string\\\"\"\"\"\".local.part.with.escaped.double.empty.string1.in.String@domain.com" );
    assertIsFalse( "\"\"\"\"@local.part.only.double.empty.string1.domain.com" );
    assertIsFalse( "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.double.empty.string1.domain.com" );
    assertIsFalse( "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" );
    assertIsTrue( "name \"\"\"\" <pointy.brackets1.with.double.empty.string1.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.double.empty.string1.in.display.name@domain.com> name \"\"\"\"" );

    assertIsFalse( "domain.part@with\"\"\"\"double.empty.string1.com" );
    assertIsFalse( "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com" );
    assertIsFalse( "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com" );
    assertIsFalse( "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\"" );
    assertIsFalse( "domain.part@with.double.empty.string1.before\"\"\"\".point.com" );
    assertIsFalse( "domain.part@with.double.empty.string1.after.\"\"\"\"point.com" );
    assertIsFalse( "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com" );
    assertIsFalse( "domain.part.with.double.empty.string1.in.comment@(comment \"\"\"\")domain.com" );
    assertIsFalse( "domain.part.only.double.empty.string1@\"\"\"\".com" );
    assertIsFalse( "top.level.domain.only@double.empty.string1.\"\"\"\"" );

    assertIsFalse( "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com" );
    assertIsFalse( "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string2\"\".\"\"character@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com" );
    assertIsFalse( "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com" );
    assertIsFalse( "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com" );
    assertIsFalse( "(comment \"\".\"\") local.part.with.double.empty.string2.in.comment@domain.com" );
    assertIsFalse( "\"string\"\".\"\"\".local.part.with.double.empty.string2.in.String@domain.com" );
    assertIsFalse( "\"string\\\"\".\"\"\".local.part.with.escaped.double.empty.string2.in.String@domain.com" );
    assertIsFalse( "\"\".\"\"@local.part.only.double.empty.string2.domain.com" );
    assertIsFalse( "\"\".\"\"\"\".\"\"\"\".\"\"\"\"@local.part.only.consecutive.double.empty.string2.domain.com" );
    assertIsFalse( "\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com" );
    assertIsFalse( "name \"\".\"\" <pointy.brackets1.with.double.empty.string2.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.double.empty.string2.in.display.name@domain.com> name \"\".\"\"" );

    assertIsFalse( "domain.part@with\"\".\"\"double.empty.string2.com" );
    assertIsFalse( "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com" );
    assertIsFalse( "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com" );
    assertIsFalse( "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\"" );
    assertIsFalse( "domain.part@with.double.empty.string2.before\"\".\"\".point.com" );
    assertIsFalse( "domain.part@with.double.empty.string2.after.\"\".\"\"point.com" );
    assertIsFalse( "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com" );
    assertIsFalse( "domain.part.with.double.empty.string2.in.comment@(comment \"\".\"\")domain.com" );
    assertIsFalse( "domain.part.only.double.empty.string2@\"\".\"\".com" );
    assertIsFalse( "top.level.domain.only@double.empty.string2.\"\".\"\"" );

    assertIsTrue( "0.local.part.starts.with.number0@domain.com" );
    assertIsTrue( "local.part.ends.with.number00@domain.com" );
    assertIsTrue( "local.part.with.number00character@domain.com" );
    assertIsTrue( "local.part.with.number0.before0.point@domain.com" );
    assertIsTrue( "local.part.with.number0.after.0point@domain.com" );
    assertIsTrue( "local.part.with.double.number000test@domain.com" );
    assertIsTrue( "(comment 0) local.part.with.number0.in.comment@domain.com" );
    assertIsTrue( "\"string0\".local.part.with.number0.in.String@domain.com" );
    assertIsFalse( "\"string\\0\".local.part.with.escaped.number0.in.String@domain.com" );
    assertIsTrue( "0@local.part.only.number0.domain.com" );
    assertIsTrue( "000000@local.part.only.consecutive.number0.domain.com" );
    assertIsTrue( "0.0.0.0.0.0@number0.domain.com" );
    assertIsTrue( "name 0 <pointy.brackets1.with.number0.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.number0.in.display.name@domain.com> name 0" );

    assertIsTrue( "domain.part@with0number0.com" );
    assertIsTrue( "domain.part@0with.number0.at.domain.start.com" );
    assertIsTrue( "domain.part@with.number0.at.domain.end10.com" );
    assertIsTrue( "domain.part@with.number0.at.domain.end2.com0" );
    assertIsTrue( "domain.part@with.number0.before0.point.com" );
    assertIsTrue( "domain.part@with.number0.after.0point.com" );
    assertIsTrue( "domain.part@with.consecutive.number000test.com" );
    assertIsTrue( "domain.part.with.number0.in.comment@(comment 0)domain.com" );
    assertIsTrue( "domain.part.only.number0@0.com" );
    assertIsFalse( "top.level.domain.only@number0.0" );

    assertIsTrue( "9.local.part.starts.with.number9@domain.com" );
    assertIsTrue( "local.part.ends.with.number99@domain.com" );
    assertIsTrue( "local.part.with.number99character@domain.com" );
    assertIsTrue( "local.part.with.number9.before9.point@domain.com" );
    assertIsTrue( "local.part.with.number9.after.9point@domain.com" );
    assertIsTrue( "local.part.with.double.number999test@domain.com" );
    assertIsTrue( "(comment 9) local.part.with.number9.in.comment@domain.com" );
    assertIsTrue( "\"string9\".local.part.with.number9.in.String@domain.com" );
    assertIsFalse( "\"string\\9\".local.part.with.escaped.number9.in.String@domain.com" );
    assertIsTrue( "9@local.part.only.number9.domain.com" );
    assertIsTrue( "999999@local.part.only.consecutive.number9.domain.com" );
    assertIsTrue( "9.9.9.9.9.9@number9.domain.com" );
    assertIsTrue( "name 9 <pointy.brackets1.with.number9.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.number9.in.display.name@domain.com> name 9" );

    assertIsTrue( "domain.part@with9number9.com" );
    assertIsTrue( "domain.part@9with.number9.at.domain.start.com" );
    assertIsTrue( "domain.part@with.number9.at.domain.end19.com" );
    assertIsTrue( "domain.part@with.number9.at.domain.end2.com9" );
    assertIsTrue( "domain.part@with.number9.before9.point.com" );
    assertIsTrue( "domain.part@with.number9.after.9point.com" );
    assertIsTrue( "domain.part@with.consecutive.number999test.com" );
    assertIsTrue( "domain.part.with.number9.in.comment@(comment 9)domain.com" );
    assertIsTrue( "domain.part.only.number9@9.com" );
    assertIsFalse( "top.level.domain.only@number9.9" );

    assertIsTrue( "0123456789.local.part.starts.with.numbers@domain.com" );
    assertIsTrue( "local.part.ends.with.numbers0123456789@domain.com" );
    assertIsTrue( "local.part.with.numbers0123456789character@domain.com" );
    assertIsTrue( "local.part.with.numbers.before0123456789.point@domain.com" );
    assertIsTrue( "local.part.with.numbers.after.0123456789point@domain.com" );
    assertIsTrue( "local.part.with.double.numbers01234567890123456789test@domain.com" );
    assertIsTrue( "(comment 0123456789) local.part.with.numbers.in.comment@domain.com" );
    assertIsTrue( "\"string0123456789\".local.part.with.numbers.in.String@domain.com" );
    assertIsFalse( "\"string\\0123456789\".local.part.with.escaped.numbers.in.String@domain.com" );
    assertIsTrue( "0123456789@local.part.only.numbers.domain.com" );
    assertIsTrue( "01234567890123@local.part.only.consecutive.numbers.domain.com" );
    assertIsTrue( "0123456789.0123456789.0123456789@numbers.domain.com" );
    assertIsTrue( "name 0123456789 <pointy.brackets1.with.numbers.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.numbers.in.display.name@domain.com> name 0123456789" );

    assertIsTrue( "domain.part@with0123456789numbers.com" );
    assertIsTrue( "domain.part@0123456789with.numbers.at.domain.start.com" );
    assertIsTrue( "domain.part@with.numbers.at.domain.end10123456789.com" );
    assertIsTrue( "domain.part@with.numbers.at.domain.end2.com0123456789" );
    assertIsTrue( "domain.part@with.numbers.before0123456789.point.com" );
    assertIsTrue( "domain.part@with.numbers.after.0123456789point.com" );
    assertIsTrue( "domain.part@with.consecutive.numbers01234567890123456789test.com" );
    assertIsTrue( "domain.part.with.numbers.in.comment@(comment 0123456789)domain.com" );
    assertIsTrue( "domain.part.only.numbers@0123456789.com" );
    assertIsFalse( "top.level.domain.only@numbers.0123456789" );

    assertIsFalse( "\\.local.part.starts.with.slash@domain.com" );
    assertIsFalse( "local.part.ends.with.slash\\@domain.com" );
    assertIsFalse( "local.part.with.slash\\character@domain.com" );
    assertIsFalse( "local.part.with.slash.before\\.point@domain.com" );
    assertIsFalse( "local.part.with.slash.after.\\point@domain.com" );
    assertIsTrue( "local.part.with.double.slash\\\\test@domain.com" );
    assertIsFalse( "(comment \\) local.part.with.slash.in.comment@domain.com" );
    assertIsFalse( "\"string\\\".local.part.with.slash.in.String@domain.com" );
    assertIsTrue( "\"string\\\\\".local.part.with.escaped.slash.in.String@domain.com" );
    assertIsFalse( "\\@local.part.only.slash.domain.com" );
    assertIsTrue( "\\\\\\\\\\\\@local.part.only.consecutive.slash.domain.com" );
    assertIsFalse( "\\.\\.\\.\\.\\.\\@slash.domain.com" );
    assertIsTrue( "escaped character is space \\ <pointy.brackets1.with.slash.in.display.name@domain.com>" );
    assertIsFalse( "no escaped character \\<pointy.brackets1.with.slash.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.slash.in.display.name@domain.com> name \\" );

    assertIsFalse( "domain.part@with\\slash.com" );
    assertIsFalse( "domain.part@\\with.slash.at.domain.start.com" );
    assertIsFalse( "domain.part@with.slash.at.domain.end1\\.com" );
    assertIsFalse( "domain.part@with.slash.at.domain.end2.com\\" );
    assertIsFalse( "domain.part@with.slash.before\\.point.com" );
    assertIsFalse( "domain.part@with.slash.after.\\point.com" );
    assertIsFalse( "domain.part@with.consecutive.slash\\\\test.com" );
    assertIsFalse( "domain.part.with.slash.in.comment@(comment \\)domain.com" );
    assertIsFalse( "domain.part.only.slash@\\.com" );
    assertIsFalse( "top.level.domain.only@slash.\\" );

    assertIsTrue( "\"str\".local.part.starts.with.string@domain.com" );
    assertIsFalse( "local.part.ends.with.string\"str\"@domain.com" );
    assertIsFalse( "local.part.with.string\"str\"character@domain.com" );
    assertIsFalse( "local.part.with.string.before\"str\".point@domain.com" );
    assertIsFalse( "local.part.with.string.after.\"str\"point@domain.com" );
    assertIsFalse( "local.part.with.double.string\"str\"\"str\"test@domain.com" );
    assertIsFalse( "(comment \"str\") local.part.with.string.in.comment@domain.com" );
    assertIsFalse( "\"string\"str\"\".local.part.with.string.in.String@domain.com" );
    assertIsFalse( "\"string\\\"str\"\".local.part.with.escaped.string.in.String@domain.com" );
    assertIsTrue( "\"str\"@local.part.only.string.domain.com" );
    assertIsFalse( "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@local.part.only.consecutive.string.domain.com" );
    assertIsTrue( "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com" );
    assertIsTrue( "name \"str\" <pointy.brackets1.with.string.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.string.in.display.name@domain.com> name \"str\"" );

    assertIsFalse( "domain.part@with\"str\"string.com" );
    assertIsFalse( "domain.part@\"str\"with.string.at.domain.start.com" );
    assertIsFalse( "domain.part@with.string.at.domain.end1\"str\".com" );
    assertIsFalse( "domain.part@with.string.at.domain.end2.com\"str\"" );
    assertIsFalse( "domain.part@with.string.before\"str\".point.com" );
    assertIsFalse( "domain.part@with.string.after.\"str\"point.com" );
    assertIsFalse( "domain.part@with.consecutive.string\"str\"\"str\"test.com" );
    assertIsFalse( "domain.part.with.string.in.comment@(comment \"str\")domain.com" );
    assertIsFalse( "domain.part.only.string@\"str\".com" );
    assertIsFalse( "top.level.domain.only@string.\"str\"" );

    assertIsFalse( "(comment).local.part.starts.with.comment@domain.com" );
    assertIsTrue( "local.part.ends.with.comment(comment)@domain.com" );
    assertIsFalse( "local.part.with.comment(comment)character@domain.com" );
    assertIsFalse( "local.part.with.comment.before(comment).point@domain.com" );
    assertIsFalse( "local.part.with.comment.after.(comment)point@domain.com" );
    assertIsFalse( "local.part.with.double.comment(comment)(comment)test@domain.com" );
    assertIsFalse( "(comment (comment)) local.part.with.comment.in.comment@domain.com" );
    assertIsTrue( "\"string(comment)\".local.part.with.comment.in.String@domain.com" );
    assertIsFalse( "\"string\\(comment)\".local.part.with.escaped.comment.in.String@domain.com" );
    assertIsFalse( "(comment)@local.part.only.comment.domain.com" );
    assertIsFalse( "(comment)(comment)(comment)@local.part.only.consecutive.comment.domain.com" );
    assertIsFalse( "(comment).(comment).(comment).(comment)@comment.domain.com" );
    assertIsTrue( "name (comment) <pointy.brackets1.with.comment.in.display.name@domain.com>" );
    assertIsTrue( "<pointy.brackets2.with.comment.in.display.name@domain.com> name (comment)" );

    assertIsFalse( "domain.part@with(comment)comment.com" );
    assertIsTrue( "domain.part@(comment)with.comment.at.domain.start.com" );
    assertIsFalse( "domain.part@with.comment.at.domain.end1(comment).com" );
    assertIsTrue( "domain.part@with.comment.at.domain.end2.com(comment)" );
    assertIsFalse( "domain.part@with.comment.before(comment).point.com" );
    assertIsFalse( "domain.part@with.comment.after.(comment)point.com" );
    assertIsFalse( "domain.part@with.consecutive.comment(comment)(comment)test.com" );
    assertIsFalse( "domain.part.with.comment.in.comment@(comment (comment))domain.com" );
    assertIsFalse( "domain.part.only.comment@(comment).com" );
    assertIsFalse( "top.level.domain.only@comment.(comment)" );
  }

  private static void runTestSeperator()
  {
    wlHeadline( "Seperator" );

    assertIsFalse( "EmailAdressWith@NoDots" );

    assertIsFalse( "..local.part.starts.with.dot@domain.com" );
    assertIsFalse( "local.part.ends.with.dot.@domain.com" );
    assertIsTrue( "local.part.with.dot.character@domain.com" );
    assertIsFalse( "local.part.with.dot.before..point@domain.com" );
    assertIsFalse( "local.part.with.dot.after..point@domain.com" );
    assertIsFalse( "local.part.with.double.dot..test@domain.com" );
    assertIsTrue( "(comment .) local.part.with.dot.in.comment@domain.com" );
    assertIsTrue( "\"string.\".local.part.with.dot.in.String@domain.com" );
    assertIsFalse( "\"string\\.\".local.part.with.escaped.dot.in.String@domain.com" );
    assertIsFalse( ".@local.part.only.dot.domain.com" );
    assertIsFalse( "......@local.part.only.consecutive.dot.domain.com" );
    assertIsFalse( "...........@dot.domain.com" );
    assertIsFalse( "name . <pointy.brackets1.with.dot.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.dot.in.display.name@domain.com> name ." );

    assertIsTrue( "domain.part@with.dot.com" );
    assertIsFalse( "domain.part@.with.dot.at.domain.start.com" );
    assertIsFalse( "domain.part@with.dot.at.domain.end1..com" );
    assertIsFalse( "domain.part@with.dot.at.domain.end2.com." );
    assertIsFalse( "domain.part@with.dot.before..point.com" );
    assertIsFalse( "domain.part@with.dot.after..point.com" );
    assertIsFalse( "domain.part@with.consecutive.dot..test.com" );
    assertIsTrue( "domain.part.with.dot.in.comment@(comment .)domain.com" );
    assertIsFalse( "domain.part.only.dot@..com" );
    assertIsFalse( "top.level.domain.only@dot.." );

    assertIsFalse( "...local.part.starts.with.double.dot@domain.com" );
    assertIsFalse( "local.part.ends.with.double.dot..@domain.com" );
    assertIsFalse( "local.part.with.double.dot..character@domain.com" );
    assertIsFalse( "local.part.with.double.dot.before...point@domain.com" );
    assertIsFalse( "local.part.with.double.dot.after...point@domain.com" );
    assertIsFalse( "local.part.with.double.double.dot....test@domain.com" );
    assertIsTrue( "(comment ..) local.part.with.double.dot.in.comment@domain.com" );
    assertIsTrue( "\"string..\".local.part.with.double.dot.in.String@domain.com" );
    assertIsFalse( "\"string\\..\".local.part.with.escaped.double.dot.in.String@domain.com" );
    assertIsFalse( "..@local.part.only.double.dot.domain.com" );
    assertIsFalse( "............@local.part.only.consecutive.double.dot.domain.com" );
    assertIsFalse( ".................@double.dot.domain.com" );
    assertIsFalse( "name .. <pointy.brackets1.with.double.dot.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.double.dot.in.display.name@domain.com> name .." );

    assertIsFalse( "domain.part@with..double.dot.com" );
    assertIsFalse( "domain.part@..with.double.dot.at.domain.start.com" );
    assertIsFalse( "domain.part@with.double.dot.at.domain.end1...com" );
    assertIsFalse( "domain.part@with.double.dot.at.domain.end2.com.." );
    assertIsFalse( "domain.part@with.double.dot.before...point.com" );
    assertIsFalse( "domain.part@with.double.dot.after...point.com" );
    assertIsFalse( "domain.part@with.consecutive.double.dot....test.com" );
    assertIsTrue( "domain.part.with.comment.with.double.dot@(comment ..)domain.com" );
    assertIsFalse( "domain.part.only.double.dot@...com" );
    assertIsFalse( "top.level.domain.only@double.dot..." );
  }

  private static void runTestAtSign()
  {
    wlHeadline( "AT-Sign" );

    assertIsFalse( "1234567890" );
    assertIsFalse( "OnlyTextNoDotNoAt" );
    assertIsFalse( "email.with.no.at.sign" );
    assertIsFalse( "email.with.no.domain@" );
    assertIsFalse( "@@domain.com" );

    assertIsFalse( "name1.@domain.com" );
    assertIsFalse( "name1@.domain.com" );
    assertIsFalse( "@name1.at.domain.com" );
    assertIsFalse( "name1.at.domain.com@" );
    assertIsFalse( "name1@name2@domain.com" );

    /*
     * Eat this, you folks, that you are saying, only test for the occurance 
     * of one AT-Sign. 
     */
    assertIsFalse( "email.with.no.domain\\@domain.com" );
    assertIsFalse( "email.with.no.domain\\@.domain.com" );
    assertIsFalse( "email.with.no.domain\\@123domain.com" );
    assertIsFalse( "email.with.no.domain\\@_domain.com" );
    assertIsFalse( "email.with.no.domain\\@-domain.com" );
    assertIsFalse( "email.with.double\\@no.domain\\@domain.com" );
    assertIsTrue( "\"wrong.at.sign.combination.in.string1@.\"@domain.com" );
    assertIsTrue( "\"wrong.at.sign.combination.in.string2.@\"@domain.com" );

    assertIsTrue( "email.with.escaped.at\\@.sign.version1@domain.com" );
    assertIsTrue( "email.with.escaped.\\@.sign.version2@domain.com" );
    assertIsTrue( "email.with.escaped.at\\@123.sign.version3@domain.com" );
    assertIsTrue( "email.with.escaped.\\@123.sign.version4@domain.com" );
    assertIsTrue( "email.with.escaped.at\\@-.sign.version5@domain.com" );
    assertIsTrue( "email.with.escaped.\\@-.sign.version6@domain.com" );
    assertIsTrue( "email.with.escaped.at.sign.\\@@domain.com" );

    assertIsTrue( "(@) email.with.at.sign.in.commet1@domain.com" );
    assertIsTrue( "email.with.at.sign.in.commet2@domain.com (@)" );
    assertIsTrue( "email.with.at.sign.in.commet3@domain.com (.@)" );

    assertIsFalse( "@@email.with.unescaped.at.sign.as.local.part" );
    assertIsTrue( "\\@@email.with.escaped.at.sign.as.local.part" );
    assertIsFalse( "@.local.part.starts.with.at@domain.com" );
    assertIsFalse( "@no.local.part.com" );
    assertIsFalse( "@@@@@@only.multiple.at.signs.in.local.part.com" );

    assertIsFalse( "local.part.with.two.@at.signs@domain.com" );
    assertIsFalse( "local.part.ends.with.at.sign@@domain.com" );
    assertIsFalse( "local.part.with.at.sign.before@.point@domain.com" );
    assertIsFalse( "local.part.with.at.sign.after.@point@domain.com" );
    assertIsFalse( "local.part.with.double.at@@test@domain.com" );
    assertIsTrue( "(comment @) local.part.with.at.sign.in.comment@domain.com" );
    assertIsTrue( "domain.part.with.comment.with.at@(comment with @)domain.com" );
    assertIsFalse( "domain.part.with.comment.with.qouted.at@(comment with \\@)domain.com" );
    assertIsTrue( "\"String@\".local.part.with.at.sign.in.string@domain.com" );
    assertIsTrue( "\\@.\\@.\\@.\\@.\\@.\\@@domain.com" );
    assertIsFalse( "\\@.\\@.\\@.\\@.\\@.\\@@at.sub\\@domain.com" );
    assertIsFalse( "@.@.@.@.@.@@domain.com" );
    assertIsFalse( "@.@.@." );
    assertIsFalse( "\\@.\\@@\\@.\\@" );
    assertIsFalse( "@" );
    assertIsFalse( "name @ <pointy.brackets1.with.at.sign.in.display.name@domain.com>" );
    assertIsFalse( "<pointy.brackets2.with.at.sign.in.display.name@domain.com> name @" );
    assertIsTrue( "<pointy.brackets3.with.escaped.at.sign.in.display.name@domain.com> name \\@" );
  }

  private static void runTestCorrect()
  {
    wlHeadline( "Correct" );

    assertIsTrue( "n@d.td" );
    assertIsTrue( "1@2.td" );
    assertIsTrue( "12.345@678.90.tld" );

    assertIsTrue( "name1.name2@domain.tld" );
    assertIsTrue( "name1+name2@domain.tld" );
    assertIsTrue( "name1-name2@domain.tld" );
    assertIsTrue( "name1.name2@subdomain1.domain.tld" );
    assertIsTrue( "name1.name2@subdomain1.tu-domain.tld" );
    assertIsTrue( "name1.name2@subdomain1.tu_domain.tld" );
    assertIsTrue( "name1.name2@subdomain1.subdomain2.domain.tld" );

    assertIsTrue( "escaped.at\\@.sign@domain.tld" );
    assertIsTrue( "\"at.sign.@\".in.string@domain.tld" );

    assertIsTrue( "ip4.adress@[1.2.3.4]" );
    assertIsTrue( "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]" );
    assertIsTrue( "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]" );
    assertIsTrue( "ip4.without.brackets@1.2.3.4" );

    assertIsTrue( "\"string1\".name1@domain.tld" );
    assertIsTrue( "name1.\"string1\"@domain.tld" );
    assertIsTrue( "name1.\"string1\".name2@domain.tld" );
    assertIsTrue( "name1.\"string1\".name2@subdomain1.domain.tld" );
    assertIsTrue( "\"string1\".\"quote2\".name1@domain.tld" );
    assertIsTrue( "\"string1\"@domain.tld" );
    assertIsTrue( "\"special characters in string ( - % & * + - / = ? _ )\"@domain.tld" );
    assertIsTrue( "\"string1\\\"embedded string\\\"\"@domain.tld" );
    assertIsTrue( "\"string1(embedded comment)\"@domain.tld" );

    assertIsTrue( "(comment1)name1@domain.tld" );
    assertIsTrue( "(comment1)-name1@domain.tld" );
    assertIsTrue( "name1(comment1)@domain.tld" );
    assertIsTrue( "name1@(comment1)domain.tld" );
    assertIsTrue( "name1@domain.tld(comment1)" );
    assertIsTrue( "(spaces after comment)     name1.name2@domain.tld" );
    assertIsTrue( "name1.name2@domain.tld    (spaces before comment)" );

    assertIsTrue( "(comment1.\\\"comment2)name1@domain.tld" );

    assertIsTrue( "(comment1.\\\"String\\\")name1@domain.tld" );
    assertIsTrue( "(comment1.\\\"String\\\".@domain.tld)name1@domain.tld" );

    assertIsTrue( "(comment1)name1.ip4.adress@[1.2.3.4]" );
    assertIsTrue( "name1.ip4.adress(comment1)@[1.2.3.4]" );
    assertIsTrue( "name1.ip4.adress@(comment1)[1.2.3.4]" );
    assertIsTrue( "name1.ip4.adress@[1.2.3.4](comment1)" );

    assertIsTrue( "(comment1)\"string1\".name1@domain.tld" );
    assertIsTrue( "(comment1)name1.\"string1\"@domain.tld" );
    assertIsTrue( "name1.\"string1\"(comment1)@domain.tld" );
    assertIsTrue( "\"string1\".name1(comment1)@domain.tld" );
    assertIsTrue( "name1.\"string1\"@(comment1)domain.tld" );
    assertIsTrue( "\"string1\".name1@domain.tld(comment1)" );
    assertIsTrue( "\"string1(comment1)\".name1@domain.tld" );

    assertIsTrue( "<name1.name2@domain.tld>" );
    assertIsTrue( "name3 <name1.name2@domain.tld>" );
    assertIsTrue( "<name1.name2@domain.tld> name3" );
    assertIsTrue( "\"name3 name4\" <name1.name2@domain.tld>" );

    assertIsTrue( "name1 <ip4.adress@[1.2.3.4]>" );
    assertIsTrue( "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>" );
    assertIsTrue( "<ip4.adress@[1.2.3.4]> name1" );
    assertIsTrue( "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1" );

    assertIsTrue( "\"display name\" <\"string1\".local.part@domain-name.tld (comment)>" );

    assertIsTrue( "\"display name\" <(comment)local.part@domain-name.top_level_domain>" );
    assertIsTrue( "\"display name\" <local.part@(comment)domain-name.top_level_domain>" );
    assertIsTrue( "\"display name\" <(comment) local.part.\"string1\"@domain-name.top_level_domain>" );

    assertIsTrue( "\"display name \\\"string\\\" \" <(comment)local.part@domain-name.top_level_domain>" );
    assertIsTrue( "\"display name \\\"string\\\" \" <(comment)local.part.wiht.escaped.at\\@.sign@domain-name.top_level_domain>" );

    assertIsTrue( "name1\\@domain.tld.name1@domain.tld" );
    assertIsTrue( "\"name1\\@domain.tld\".name1@domain.tld" );
    assertIsTrue( "\"name1\\@domain.tld \\\"name1\\@domain.tld\\\"\".name1@domain.tld" );
    assertIsTrue( "\"name1\\@domain.tld \\\"name1\\@domain.tld\\\"\".name1@domain.tld (name1@domain.tld)" );
    assertIsTrue( "(name1@domain.tld) name1@domain.tld" );
    assertIsTrue( "(name1@domain.tld) \"name1\\@domain.tld\".name1@domain.tld" ); // needs To Be Fixed
    assertIsTrue( "(name1@domain.tld) name1.\"name1\\@domain.tld\"@domain.tld" );
  }

  private static void runTestsJoshData()
  {
    wlHeadline( "https://github.com/JoshData/python-email-validator/blob/main/tests/test_syntax.py" );

    assertIsTrue( "\"unnecessarily.quoted.local.part\"@example.com" ); // unnecessarily.quoted.local.part
    assertIsTrue( "\"quoted..local.part\"@example.com" ); // "quoted..local.part"
    assertIsTrue( "\"quoted.with.at@\"@example.com" ); // "quoted.with.at@"
    assertIsTrue( "\"quoted with space\"@example.com" ); // "quoted with space"
    assertIsTrue( "\"quoted.with.dquote\\\"\"@example.com" ); // "quoted.with.dquote\\""
    assertIsTrue( "\"unnecessarily.quoted.with.unicode.?\"@example.com" ); // unnecessarily.quoted.with.unicode.?
    assertIsTrue( "\"quoted.with..unicode.?\"@example.com" ); // "quoted.with..unicode.?"
    assertIsTrue( "\"quoted.with.extraneous.\\\\escape\"@example.com" ); // quoted.with.extraneous.escape

    assertIsFalse( "my@localhost" ); // The part after the @-sign is not valid. It should have a period.
    assertIsFalse( "my@.leadingdot.com" ); // An email address cannot have a period immediately after the @-sign.
    assertIsFalse( "my@.leadingfwdot.com" ); // An email address cannot have a period immediately after the @-sign.
    assertIsFalse( "my@twodots..com" ); // An email address cannot have two periods in a row.
    assertIsFalse( "my@twofwdots...com" ); // An email address cannot have two periods in a row.
    assertIsFalse( "my@trailingdot.com." ); // An email address cannot end with a period.
    assertIsFalse( "my@trailingfwdot.com." ); // An email address cannot end with a period.
    assertIsFalse( "me@-leadingdash" ); // An email address cannot have a hyphen immediately after the @-sign.
    assertIsFalse( "me@-leadingdashfw" ); // An email address cannot have a hyphen immediately after the @-sign.
    assertIsFalse( "me@trailingdash-" ); // An email address cannot end with a hyphen.
    assertIsFalse( "me@trailingdashfw-" ); // An email address cannot end with a hyphen.
    assertIsFalse( "my@baddash.-.com" ); // An email address cannot have a period and a hyphen next to each other.
    assertIsFalse( "my@baddash.-a.com" ); // An email address cannot have a period and a hyphen next to each other.
    assertIsFalse( "my@baddash.b-.com" ); // An email address cannot have a period and a hyphen next to each other.
    assertIsFalse( "my@baddashfw.-.com" ); // An email address cannot have a period and a hyphen next to each other.
    assertIsFalse( "my@baddashfw.-a.com" ); // An email address cannot have a period and a hyphen next to each other.
    assertIsFalse( "my@baddashfw.b-.com" ); // An email address cannot have a period and a hyphen next to each other.
    assertIsFalse( "me@x!" ); // The part after the @-sign contains invalid characters: \'!\'.
    assertIsFalse( "me@x " ); // The part after the @-sign contains invalid characters: SPACE.
    assertIsFalse( ".leadingdot@domain.com" ); // An email address cannot start with a period.
    assertIsFalse( "twodots..here@domain.com" ); // An email address cannot have two periods in a row.
    assertIsFalse( "trailingdot.@domain.email" ); // An email address cannot have a period immediately before the @-sign.

    assertIsFalse( "@example.com" ); // There must be something before the @-sign.
    assertIsFalse( "white space@test" ); // The email address contains invalid characters before the @-sign: SPACE.
    assertIsFalse( "test@white space" ); // The part after the @-sign contains invalid characters: SPACE.
    assertIsFalse( "\nmy@example.com" ); // The email address contains invalid characters before the @-sign: U+000A.
    assertIsFalse( "m\ny@example.com" ); // The email address contains invalid characters before the @-sign: U+000A.
    assertIsFalse( "my\n@example.com" ); // The email address contains invalid characters before the @-sign: U+000A.
    assertIsFalse( "test@\n" ); // The part after the @-sign contains invalid characters: U+000A.
    assertIsFalse( "bad\"quotes\"@example.com" ); // The email address contains invalid characters before the @-sign: \'"\'.
    assertIsTrue( "obsolete.\"quoted\".atom@example.com" );
    assertIsFalse( "11111111112222222222333333333344444444445555555555666666666677777@example.com" ); // The email address is too long before the @-sign (1 character too many).
    assertIsFalse( "111111111122222222223333333333444444444455555555556666666666777777@example.com" ); // The email address is too long before the @-sign (2 characters too many).
    assertIsFalse( "me@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.111111111122222222223333333333444444444455555555556.com" ); // The email address is too long (4 characters too many).
    assertIsFalse( "me@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555566.com" ); // The email address is too long after the @-sign (1 character too many).
    assertIsFalse( "me@?1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555566.com" ); // The email address is too long after the @-sign.
    assertIsFalse( "my.long.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.11111111112222222222333333333344444.info" ); // The email address is too long (2 characters too many).
    assertIsFalse( "my.long.address@?111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.11111111112222222222333333.info" ); // The email address is too long (when converted to IDNA ASCII).
    assertIsFalse( "my.long.address@?111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444.info" ); // The email address is too long (at least 1 character too many).
    assertIsFalse( "my.?ong.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.111111111122222222223333333333444.info" ); // The email address is too long (when encoded in bytes).
    assertIsFalse( "my.?ong.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444.info" ); // The email address is too long (at least 1 character too many).
    assertIsFalse( "me@bad-tld-1" ); // The part after the @-sign is not valid. It should have a period.
    assertIsFalse( "me@bad.tld-2" ); // The part after the @-sign is not valid. It is not within a valid top-level domain.
    assertIsTrue( "me@xn--0.tld" );
    assertIsTrue( "me@[127.0.0.1]" );
    assertIsFalse( "me@[127.0.0.999]" ); // The address in brackets after the @-sign is not valid: It is not an IPv4 address (Octet 999 (> 255) not permitted in \'127.0.0.999\') or is missing an address literal tag.
    assertIsTrue( "me@[IPv6:::1]" );
    assertIsFalse( "me@[IPv6:::G]" ); // The IPv6 address in brackets after the @-sign is not valid (Only hex digits permitted in \'G\' in \'::G\').
    assertIsFalse( "me@[tag:text]" ); // The part after the @-sign contains an invalid address literal tag in brackets.
    assertIsFalse( "me@[untaggedtext]" ); // The part after the @-sign in brackets is not an IPv4 address and has no address literal tag.
    assertIsFalse( "me@[tag:invalid space]" ); // The part after the @-sign contains invalid characters in brackets: SPACE.

    assertIsFalse( "test" ); // ISEMAIL_ERR_NODOMAIN
    assertIsFalse( "@" ); // ISEMAIL_ERR_NOLOCALPART
    assertIsFalse( "test@" ); // ISEMAIL_ERR_NODOMAIN
    assertIsFalse( "@io" ); // ISEMAIL_ERR_NOLOCALPART
    assertIsFalse( "@iana.org" ); // ISEMAIL_ERR_NOLOCALPART
    assertIsTrue( "a@iana.org" ); // ISEMAIL_VALID
    assertIsFalse( "test_exa-mple.com" ); // ISEMAIL_ERR_NODOMAIN
    assertIsTrue( "!#$%&`*+/=?^`{|}~@iana.org" ); // ISEMAIL_VALID
    assertIsTrue( "test\\@test@iana.org" );
    assertIsTrue( "123@iana.org" ); // ISEMAIL_VALID
    assertIsTrue( "test@123.com" ); // ISEMAIL_VALID
    assertIsTrue( "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org" ); // ISEMAIL_VALID
    assertIsFalse( "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklmn@iana.org" ); // ISEMAIL_RFC5322_LOCAL_TOOLONG
    assertIsFalse( "test@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm.com" ); // ISEMAIL_RFC5322_LABEL_TOOLONG
    assertIsTrue( "test@mason-dixon.com" ); // ISEMAIL_VALID
    assertIsTrue( "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij" ); // ISEMAIL_RFC5322_TOOLONG
    assertIsTrue( "a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hij" ); // ISEMAIL_RFC5322_TOOLONG
    assertIsTrue( "a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hijk" ); // ISEMAIL_RFC5322_DOMAIN_TOOLONG
    assertIsTrue( "\"test\"@iana.org" ); // ISEMAIL_RFC5321_QUOTEDSTRING

    assertIsTrue( " test @iana.org" ); // ISEMAIL_DEPREC_CFWS_NEAR_AT
    assertIsTrue( " test@iana.org" ); // ISEMAIL_CFWS_FWS
    assertIsFalse( "((comment)test@iana.org" ); // ISEMAIL_ERR_UNCLOSEDCOMMENT
    assertIsTrue( "(comment(comment))test@iana.org" ); // ISEMAIL_CFWS_COMMENT
    assertIsTrue( "(comment)test@iana.org" ); // ISEMAIL_CFWS_COMMENT
    assertIsFalse( "(comment\\)test@iana.org" ); // ISEMAIL_ERR_UNCLOSEDCOMMENT
    assertIsFalse( "(test@iana.org" ); // ISEMAIL_ERR_UNCLOSEDCOMMENT
    assertIsFalse( ".test@iana.org" ); // ISEMAIL_ERR_DOT_START
    assertIsFalse( "\"\"@iana.org" ); // ISEMAIL_ERR_EXPECTING_ATEXT
    assertIsFalse( "\"\\\"@iana.org" ); // ISEMAIL_ERR_UNCLOSEDQUOTEDSTR
    assertIsTrue( "\"\\\"\"@iana.org" ); // ISEMAIL_RFC5321_QUOTEDSTRING
    assertIsTrue( "\"\\\\\"@iana.org" ); // ISEMAIL_RFC5321_QUOTEDSTRING
    assertIsFalse( "\"test@iana.org" ); // ISEMAIL_ERR_UNCLOSEDQUOTEDSTR
    assertIsTrue( "\"test\".\"test\"@iana.org" ); // ISEMAIL_DEPREC_LOCALPART
    assertIsTrue( "\"test\".test@iana.org" ); // ISEMAIL_DEPREC_LOCALPART
    assertIsFalse( "\"test\"\"test\"@iana.org" ); // ISEMAIL_ERR_EXPECTING_ATEXT
    assertIsFalse( "\"test\"test@iana.org" ); // ISEMAIL_ERR_ATEXT_AFTER_QS
    assertIsTrue( "\"test\\ test\"@iana.org" ); // ISEMAIL_RFC5321_QUOTEDSTRING
    assertIsFalse( "\"test\\\"@iana.org" ); // ISEMAIL_ERR_UNCLOSEDQUOTEDSTR
    assertIsFalse( "\"test\\x00\"@iana.org" ); // ISEMAIL_ERR_EXPECTING_QTEXT
    assertIsFalse( "\"test\\©\"@iana.org" ); // ISEMAIL_ERR_EXPECTING_QPAIR
    assertIsTrue( "\r\n \r\n test@iana.org" ); // ISEMAIL_DEPREC_FWS
    assertIsTrue( "\r\n test@iana.org" ); // ISEMAIL_CFWS_FWS
    assertIsTrue( "test . test@iana.org" ); // ISEMAIL_DEPREC_FWS
    assertIsFalse( "test(comment)test@iana.org" ); // ISEMAIL_ERR_ATEXT_AFTER_CFWS
    assertIsFalse( "test..iana.org" ); // ISEMAIL_ERR_CONSECUTIVEDOTS
    assertIsFalse( "test.@iana.org" ); // ISEMAIL_ERR_DOT_END
    assertIsTrue( "test.test@iana.org" ); // ISEMAIL_VALID
    assertIsTrue( "test@ iana .com" ); // ISEMAIL_DEPREC_CFWS_NEAR_AT
    assertIsTrue( "test@(comment)iana.org" ); // ISEMAIL_DEPREC_CFWS_NEAR_AT
    assertIsFalse( "test@(iana.org" ); // ISEMAIL_ERR_UNCLOSEDCOMMENT
    assertIsFalse( "test@-iana.org" ); // ISEMAIL_ERR_DOMAINHYPHENSTART
    assertIsFalse( "test@.iana.org" ); // ISEMAIL_ERR_DOT_START
    assertIsFalse( "test@[1.2.3.4" ); // ISEMAIL_ERR_UNCLOSEDDOMLIT
    assertIsTrue( "test@[IPv6:1::2:]" ); // ISEMAIL_RFC5322_IPV6_COLONEND
    assertIsTrue( "test@about.museum" ); // ISEMAIL_VALID
    assertIsTrue( "test@g--a.com" ); // ISEMAIL_VALID
    assertIsFalse( "test@iana-.com" ); // ISEMAIL_ERR_DOMAINHYPHENEND
    assertIsFalse( "test@iana..com" ); // ISEMAIL_ERR_CONSECUTIVEDOTS
    assertIsTrue( "test@iana.org " ); // ISEMAIL_CFWS_FWS
    assertIsTrue( "test@iana.org \r\n " ); // ISEMAIL_CFWS_FWS
    assertIsFalse( "test@iana.org \r\n \r\n" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsFalse( "test@iana.org \r\n" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsFalse( "test@iana.org \r\n\r\n " ); // ISEMAIL_ERR_FWS_CRLF_X2
    assertIsFalse( "test@iana.org \r\n\r\n" ); // ISEMAIL_ERR_FWS_CRLF_X2
    assertIsTrue( "test@iana.org" ); // ISEMAIL_VALID
    assertIsFalse( "test@iana.org(comment\\" ); // ISEMAIL_ERR_BACKSLASHEND
    assertIsFalse( "test@iana.org(comment\\)" ); // ISEMAIL_ERR_UNCLOSEDCOMMENT
    assertIsFalse( "test@iana.org-" ); // ISEMAIL_ERR_DOMAINHYPHENEND
    assertIsFalse( "test@iana.org." ); // ISEMAIL_ERR_DOT_END
    assertIsTrue( "test@iana.org\r\n " ); // ISEMAIL_CFWS_FWS
    assertIsTrue( "test@iana.org\r\n \r\n " ); // ISEMAIL_DEPREC_FWS
    assertIsFalse( "test@iana.org\r\n \r\n" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsFalse( "test@iana.org\r\n" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsTrue( "test@iana/icann.org" ); // ISEMAIL_RFC5322_DOMAIN
    assertIsTrue( "test@nominet.org.uk" ); // ISEMAIL_VALID
    assertIsFalse( "test\"@iana.org" ); // ISEMAIL_ERR_EXPECTING_ATEXT
    assertIsFalse( "test\"text\"@iana.org" ); // ISEMAIL_ERR_EXPECTING_ATEXT

    assertIsTrue( "\"abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghj\"@iana.org" ); // ISEMAIL_RFC5322_LOCAL_TOOLONG
    assertIsTrue( "\"abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefg\\h\"@iana.org" ); // ISEMAIL_RFC5322_LOCAL_TOOLONG
    assertIsTrue( "test@[255.255.255.255]" ); // ISEMAIL_RFC5321_ADDRESSLITERAL
    assertIsFalse( "test@a[255.255.255.255]" ); // ISEMAIL_ERR_EXPECTING_ATEXT
    assertIsFalse( "test@[255.255.255]" ); // ISEMAIL_RFC5322_DOMAINLITERAL
    assertIsFalse( "test@[255.255.255.255.255]" ); // ISEMAIL_RFC5322_DOMAINLITERAL
    assertIsFalse( "test@[255.255.255.256]" ); // ISEMAIL_RFC5322_DOMAINLITERAL
    assertIsTrue( "test@[1111:2222:3333:4444:5555:6666:7777:8888]" ); // ISEMAIL_RFC5322_DOMAINLITERAL
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:6666:7777]" ); // ISEMAIL_RFC5322_IPV6_GRPCOUNT
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]" ); // ISEMAIL_RFC5321_ADDRESSLITERAL
    assertIsFalse( "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]" ); // ISEMAIL_RFC5322_IPV6_GRPCOUNT
    assertIsFalse( "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:888G]" ); // ISEMAIL_RFC5322_IPV6_BADCHAR
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:6666::8888]" ); // ISEMAIL_RFC5321_IPV6DEPRECATED
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555::8888]" ); // ISEMAIL_RFC5321_ADDRESSLITERAL
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:6666::7777:8888]" ); // ISEMAIL_RFC5322_IPV6_MAXGRPS
    assertIsTrue( "test@[IPv6::3333:4444:5555:6666:7777:8888]" ); // ISEMAIL_RFC5322_IPV6_COLONSTRT
    assertIsTrue( "test@[IPv6:::3333:4444:5555:6666:7777:8888]" ); // ISEMAIL_RFC5321_ADDRESSLITERAL
    assertIsTrue( "test@[IPv6:1111::4444:5555::8888]" ); // ISEMAIL_RFC5322_IPV6_2X2XCOLON
    assertIsTrue( "test@[IPv6:::]" ); // ISEMAIL_RFC5321_ADDRESSLITERAL
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:255.255.255.255]" ); // ISEMAIL_RFC5322_IPV6_GRPCOUNT
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:6666:255.255.255.255]" ); // ISEMAIL_RFC5321_ADDRESSLITERAL
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:255.255.255.255]" ); // ISEMAIL_RFC5322_IPV6_GRPCOUNT
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444::255.255.255.255]" ); // ISEMAIL_RFC5321_ADDRESSLITERAL
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:5555:6666::255.255.255.255]" ); // ISEMAIL_RFC5322_IPV6_MAXGRPS
    assertIsTrue( "test@[IPv6:1111:2222:3333:4444:::255.255.255.255]" ); // ISEMAIL_RFC5322_IPV6_2X2XCOLON
    assertIsTrue( "test@[IPv6::255.255.255.255]" ); // ISEMAIL_RFC5322_IPV6_COLONSTRT
    assertIsTrue( "test@(comment)[255.255.255.255]" ); // ISEMAIL_DEPREC_CFWS_NEAR_AT
    assertIsTrue( "(comment)abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org" ); // ISEMAIL_CFWS_COMMENT
    assertIsTrue( "test@(comment)abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.com" ); // ISEMAIL_DEPREC_CFWS_NEAR_AT
    assertIsTrue( "(comment)test@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghik.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghik.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijk.abcdefghijklmnopqrstuvwxyzabcdefghijk.abcdefghijklmnopqrstu" ); // ISEMAIL_CFWS_COMMENT
    assertIsFalse( "test@iana.org\n" ); // ISEMAIL_ERR_EXPECTING_ATEXT
    assertIsTrue( "test@xn--hxajbheg2az3al.xn--jxalpdlp" ); // ISEMAIL_VALID
    assertIsTrue( "xn--test@iana.org" ); // ISEMAIL_VALID
    assertIsTrue( "test@[RFC-5322-domain-literal]" ); // ISEMAIL_RFC5322_DOMAINLITERAL
    assertIsFalse( "test@[RFC-5322]-domain-literal]" ); // ISEMAIL_ERR_ATEXT_AFTER_DOMLIT
    assertIsFalse( "test@[RFC-5322-[domain-literal]" ); // ISEMAIL_ERR_EXPECTING_DTEXT

    assertIsTrue( "test@[RFC-5322-\\\t-domain-literal]" ); // ISEMAIL_RFC5322_DOMLIT_OBSDTEXT
    assertIsTrue( "test@[RFC-5322-\\]-domain-literal]" ); // ISEMAIL_RFC5322_DOMLIT_OBSDTEXT
    assertIsFalse( "test@[RFC-5322-domain-literal\\]" ); // ISEMAIL_ERR_UNCLOSEDDOMLIT
    assertIsFalse( "test@[RFC-5322-domain-literal\\" ); // ISEMAIL_ERR_BACKSLASHEND
    assertIsTrue( "test@[RFC 5322 domain literal]" ); // ISEMAIL_RFC5322_DOMAINLITERAL
    assertIsTrue( "test@[RFC-5322-domain-literal] (comment)" ); // ISEMAIL_RassertIsTrueFC5322_DOMAINLITERAL
    assertIsFalse( "test@iana.org\r" ); // ISEMAIL_ERR_CR_NO_LF
    assertIsFalse( "\rtest@iana.org" ); // ISEMAIL_ERR_CR_NO_LF
    assertIsFalse( "\"\\rtest\"@iana.org" ); // ISEMAIL_ERR_CR_NO_LF
    assertIsFalse( "(\r)test@iana.org" ); // ISEMAIL_ERR_CR_NO_LF
    assertIsFalse( "test@iana.org(\r)" ); // ISEMAIL_ERR_CR_NO_LF
    assertIsFalse( "\ntest@iana.org" ); // ISEMAIL_ERR_EXPECTING_ATEXT
    assertIsFalse( "\"\\n\"@iana.org" ); // ISEMAIL_ERR_EXPECTING_QTEXT
    assertIsTrue( "\"\\\n\"@iana.org" ); // ISEMAIL_DEPREC_QP
    assertIsFalse( "(\n)test@iana.org" ); // ISEMAIL_ERR_EXPECTING_CTEXT
    assertIsTrue( "\"\\x07\"@iana.org" ); // ISEMAIL_DEPREC_QTEXT

    assertIsFalse( "\r\ntest@iana.org" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsFalse( "\r\n \r\ntest@iana.org" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsFalse( " \r\ntest@iana.org" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsTrue( " \r\n test@iana.org" ); // ISEMAIL_CFWS_FWS
    assertIsFalse( " \r\n \r\ntest@iana.org" ); // ISEMAIL_ERR_FWS_CRLF_END
    assertIsFalse( " \r\n\r\ntest@iana.org" ); // ISEMAIL_ERR_FWS_CRLF_X2
    assertIsFalse( " \r\n\r\n test@iana.org" ); // ISEMAIL_ERR_FWS_CRLF_X2
    assertIsFalse( "test.(comment)test@iana.org" );
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
    wl( left( "---- " + pString + " ------------------------------------------------------------------------------------------------------------------------------", 130 ) );
    wl( "" );
  }

  /**
   * <pre>
   * Schneidet Anzahl-Stellen von dem uebergebenen String ab und gibt diesen zurueck.
   * 
   * Ist der Parameter "pString" gleich \"null\", wird ein Leerstring zurueckgegeben.
   * 
   * Uebersteigt die Anazhl der abzuschneidenden Stellen die Stringlaenge, wird der
   * Quellstring insgesamt zurueckgegeben.
   * 
   * Ist die Anzahl der abzuschneidenden Stellen negativ oder 0, wird ein Leerstring zurueckgegeben.
   * 
   * FkString.left( "ABC.DEF.GHI.JKL",  3 ) = "ABC"
   * FkString.left( "ABC.DEF.GHI.JKL",  4 ) = "ABC."
   * FkString.left( "ABC.DEF.GHI.JKL", 20 ) = "ABC.DEF.GHI.JKL"
   * 
   * FkString.left( "ABC.DEF.GHI.JKL", -3 ) = "" = negative Anzahl von Stellen = Leerstring
   * FkString.left(                "", 10 ) = "" = pString ist Leerstring      = Leerstring
   * FkString.left(              null, 10 ) = "" = pString ist null            = Leerstring
   * </pre>
   * 
   * @param pString der Quellstring
   * @param pAnzahlStellen die Anzahl der von links abzuschneidenden Stellen
   * @return den sich ergebenden String, Leerstring wenn die Anzahl der Stellen negativ ist oder pString null ist
   */
  private static String left( String pString, int pAnzahlStellen )
  {
    /*
     * Pruefung: "pString" gleich "null" ?
     * 
     * Ist der Parameter "pString" gleich "null" gibt es keinen String. 
     * Der Aufrufder bekommt einen Leerstring zurueck
     */
    if ( pString == null )
    {
      return "";
    }

    /*
     * Pruefung: Anzahl der Stellen negativ?
     * Ist die Anzahl der abzuschneidenden Stellen negativ, bleibt 
     * kein Teil von pString uebrig. Dieser Fall wird analog einer 
     * Uebergabe von 0 Zeichen abschneiden behandelt.  
     * 
     * Der Aufrufer bekommt einen Leerstring zurueck.
     */
    if ( pAnzahlStellen <= 0 )
    {
      return "";
    }

    /*
     * Pruefung: Teilstring zurueckgeben?
     * Ist die Anzahl der Stellen kleiner als die Laenge von "pString", 
     * wird ein Teilstring zurueckgegeben.
     *
     * Der Aufrufer bekommt den Teilstring ab der Position 0 bis zur
     * Anzahl der abzuschneidenden Stellen zuruek. 
     */
    if ( pAnzahlStellen < pString.length() )
    {
      return pString.substring( 0, pAnzahlStellen );
    }

    /*
     * Ueberschreitet die Anzahl der abzuschneidenden Stellen die 
     * Laenge des Eingabestrings, muss kein Zeichen vom Eingabestring
     * abgeschnitten werden. 
     * 
     * Der Aufrufer bekommt die Eingabe zuruek.
     */
    return pString;
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

    m_str_buffer.append( "\n" + "   * " + pString );

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
        TRUE_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        TRUE_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      TRUE_RESULT_COUNT_ERROR++;

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
        FALSE_RESULT_COUNT_EMAIL_IS_TRUE++;
      }
      else
      {
        FALSE_RESULT_COUNT_EMAIL_IS_FALSE++;
      }
    }
    catch ( Exception err_inst )
    {
      FALSE_RESULT_COUNT_ERROR++;

      if ( KNZ_LOG_AUSGABE )
      {
        wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = Fehler = " + err_inst.getMessage() );
      }
    }

    COUNT_ASSERT_IS_FALSE++;
  }

  private static void generateTestCases()
  {
    //generateTest( ";", "semicolon" );
    //generateTest( ".", "dot" );
    //generateTest( "..", "double.dot" );
    //generateTest( "&", "amp" );
    //generateTest( "*", "asterisk" );
    //generateTest( "_", "underscore" );
    //generateTest( "$", "dollar" );
    //generateTest( "=", "equality" );
    //generateTest( "!", "exclamation" );
    //generateTest( "?", "question" );
    //generateTest( "`", "grave-accent" );
    //generateTest( "#", "hash" );
    //generateTest( "%", "percentage" );
    //generateTest( "|", "pipe" );
    //generateTest( "+", "plus" );
    //generateTest( "{", "leftbracket" );
    //generateTest( "}", "rightbracket" );
    //generateTest( "(", "leftbracket" );
    //generateTest( ")", "rightbracket" );
    //generateTest( "[", "leftbracket" );
    //generateTest( "]", "rightbracket" );
    //generateTest( "()", "empty.bracket" );
    //generateTest( "{}", "empty.bracket" );
    //generateTest( "[]", "empty.bracket" );
    //generateTest( "<>", "empty.bracket" );
    //generateTest( ")(", "false.bracket1" );
    //generateTest( "}{", "false.bracket2" );
    //generateTest( "][", "false.bracket3" );--------------------------
    //generateTest( "><", "false.bracket4" );
    //generateTest( "<", "lower.than" );
    //generateTest( ">", "greater.than" );
    //generateTest( "~", "tilde" );
    //generateTest( "^", "xor" );
    //generateTest( ":", "colon" );
    //generateTest( " ", "space" );
    //generateTest( ",", "comma" );
    //generateTest( "@", "at" );
    //generateTest( "§", "paragraph" );
    //generateTest( "'", "double.quote" );
    //xxgenerateTest( "\"", "double.quote" );
    //generateTest( "/", "forward.slash" );
    //generateTest( "-", "hyphen" );
    //generateTest( "\\\"\\\"", "empty.string1" );
    //generateTest( "a\\\"\\\"b", "empty.string2" );
    //generateTest( "\\\"\\\"\\\"\\\"", "double.empty.string1" );
    //generateTest( "\\\"\\\".\\\"\\\"", "double.empty.string2" );
    //generateTest( "0", "number0" );
    //generateTest( "9", "number9" );
    //generateTest( "0123456789", "numbers" );
    //generateTest( "999", "byte.overflow" );
    //generateTest( "xyz", "no.hex.number" );
    //generateTest( "\\\\", "slash" );
    //generateTest( "\\\"str\\\"", "string" );
    //generateTest( "(comment)", "comment" );
  }

  private static void generateTest( String pCharacter, String pName )
  {
    wl( "" );
    wl( "      assertIsTrue( \"" + pCharacter + ".local.part.starts.with." + pName + "@domain.com\" );" );
    wl( "      assertIsTrue( \"local.part.ends.with." + pName + pCharacter + "@domain.com\" );" );
    wl( "      assertIsTrue( \"local.part.with." + pName + pCharacter + "character@domain.com\" );" );
    wl( "      assertIsTrue( \"local.part.with." + pName + ".before" + pCharacter + ".point@domain.com\" );" );
    wl( "      assertIsTrue( \"local.part.with." + pName + ".after." + pCharacter + "point@domain.com\" );" );
    wl( "      assertIsTrue( \"local.part.with.double." + pName + "" + pCharacter + "" + pCharacter + "test@domain.com\" );" );
    wl( "      assertIsFalse( \"(comment " + pCharacter + ") local.part.with." + pName + ".in.comment@domain.com\" );" );
    wl( "      assertIsTrue( \"\\\"string" + pCharacter + "\\\".local.part.with." + pName + ".in.String@domain.com\" );" );
    wl( "      assertIsFalse( \"\\\"string\\\\" + pCharacter + "\\\".local.part.with.escaped." + pName + ".in.String@domain.com\" );" );
    wl( "      assertIsTrue( \"" + pCharacter + "@local.part.only." + pName + ".domain.com\" );" );
    wl( "      assertIsTrue( \"" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "" + pCharacter + "@local.part.only.consecutive." + pName + ".domain.com\" );" );
    wl( "      assertIsTrue( \"" + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "." + pCharacter + "@" + pName + ".domain.com\" );" );
    wl( "      assertIsFalse( \"name " + pCharacter + " <pointy.brackets1.with." + pName + ".in.display.name@domain.com>\" );" );
    wl( "      assertIsFalse( \"<pointy.brackets2.with." + pName + ".in.display.name@domain.com> name " + pCharacter + "\" );" );
    wl( "" );
    wl( "      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
    wl( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );
    wl( "      assertIsFalse( \"domain.part@with.consecutive." + pName + "" + pCharacter + "" + pCharacter + "test.com\" );" );
    wl( "      assertIsTrue( \"domain.part.with." + pName + ".in.comment@(comment " + pCharacter + ")domain.com\" );" );
    wl( "      assertIsFalse( \"domain.part.only." + pName + "@" + pCharacter + ".com\" );" );
    wl( "      assertIsFalse( \"top.level.domain.only@" + pName + "." + pCharacter + "\" );" );
    wl( "" );
    wl( "      assertIsFalse( \"ip4.with." + pName + ".between.numbers@[123.14" + pCharacter + "5.178.90]\" );" );
    wl( "      assertIsFalse( \"ip4.with." + pName + ".before.point@[123.145" + pCharacter + ".178.90]\" );" );
    wl( "      assertIsFalse( \"ip4.with." + pName + ".after.point@[123.145." + pCharacter + "178.90]\" );" );
    wl( "      assertIsFalse( \"ip4.with." + pName + ".before.start.bracket@" + pCharacter + "[123.145.178.90]\" );" );
    wl( "      assertIsFalse( \"ip4.with." + pName + ".after.start.bracket@[" + pCharacter + "123.145.178.90]\" );" );
    wl( "      assertIsFalse( \"ip4.with." + pName + ".before.end.bracket@[123.145.178.90" + pCharacter + "]\" );" );
    wl( "      assertIsFalse( \"ip4.with." + pName + ".after.end.bracket@[123.145.178.90]" + pCharacter + "\" );" );
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

  private static String getAktDatumUndUhrzeitMs()
  {
    Calendar inst_java_calendar = Calendar.getInstance();

    int akt_jahr = inst_java_calendar.get( Calendar.YEAR );
    int akt_monat = inst_java_calendar.get( Calendar.MONTH ) + 1;
    int akt_tag = inst_java_calendar.get( Calendar.DATE );

    int akt_stunde = inst_java_calendar.get( Calendar.HOUR_OF_DAY );
    int akt_minute = inst_java_calendar.get( Calendar.MINUTE );
    int akt_sekunde = inst_java_calendar.get( Calendar.SECOND );
    int akt_milli_sekunde = inst_java_calendar.get( Calendar.MILLISECOND );

    return ( akt_tag < 10 ? "0" : "" ) + akt_tag + ( akt_monat < 10 ? ".0" : "." ) + akt_monat + "." + akt_jahr + ( akt_stunde < 10 ? " 0" : " " ) + akt_stunde + ( akt_minute < 10 ? ":0" : ":" ) + akt_minute + ( akt_sekunde < 10 ? ":0" : ":" ) + akt_sekunde + ":" + ( akt_milli_sekunde < 10 ? "00" : ( akt_milli_sekunde < 100 ? "0" : "" ) ) + akt_milli_sekunde;
  }
}
