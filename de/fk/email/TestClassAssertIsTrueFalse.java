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
   * Datum 31.10.2023 09:23:59:546
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
   *    14 - assertIsTrue  "escaped.at\@.sign@domain.tld"                                                   =   0 =  OK 
   *    15 - assertIsTrue  "\"at.sign.@\".in.string@domain.tld"                                             =   1 =  OK 
   *    16 - assertIsTrue  "ip4.adress@[1.2.3.4]"                                                           =   2 =  OK 
   *    17 - assertIsTrue  "ip6.adress@[IPv6:1:2:3:4:5:6:7:8]"                                              =   4 =  OK 
   *    18 - assertIsTrue  "ip4.embedded.in.ip6@[IPv6::ffff:127.0.0.1]"                                     =   4 =  OK 
   *    19 - assertIsTrue  "ip4.without.brackets@1.2.3.4"                                                   =   2 =  OK 
   *    20 - assertIsTrue  "\"string1\".name1@domain.tld"                                                   =   1 =  OK 
   *    21 - assertIsTrue  "name1.\"string1\"@domain.tld"                                                   =   1 =  OK 
   *    22 - assertIsTrue  "name1.\"string1\".name2@domain.tld"                                             =   1 =  OK 
   *    23 - assertIsTrue  "name1.\"string1\".name2@subdomain1.domain.tld"                                  =   1 =  OK 
   *    24 - assertIsTrue  "\"string1\".\"quote2\".name1@domain.tld"                                        =   1 =  OK 
   *    25 - assertIsTrue  "\"string1\"@domain.tld"                                                         =   1 =  OK 
   *    26 - assertIsTrue  "\"special characters in string - % & * + - / = ? _\"@domain.tld"                =   1 =  OK 
   *    27 - assertIsTrue  "\"string1\\"embedded string\\"\"@domain.tld"                                    =   1 =  OK 
   *    28 - assertIsTrue  "\"string1(embedded comment)\"@domain.tld"                                       =   1 =  OK 
   *    29 - assertIsTrue  "(comment1)name1@domain.tld"                                                     =   6 =  OK 
   *    30 - assertIsTrue  "(comment1)-name1@domain.tld"                                                    =   6 =  OK 
   *    31 - assertIsTrue  "name1(comment1)@domain.tld"                                                     =   6 =  OK 
   *    32 - assertIsTrue  "name1@(comment1)domain.tld"                                                     =   6 =  OK 
   *    33 - assertIsTrue  "name1@domain.tld(comment1)"                                                     =   6 =  OK 
   *    34 - assertIsTrue  "(spaces after comment)     name1.name2@domain.tld"                              =   6 =  OK 
   *    35 - assertIsTrue  "name1.name2@domain.tld   (spaces before comment)"                               =   6 =  OK 
   *    36 - assertIsTrue  "(comment1.\\"comment2)name1@domain.tld"                                         =   6 =  OK 
   *    37 - assertIsTrue  "(comment1.\\"String\\")name1@domain.tld"                                        =   6 =  OK 
   *    38 - assertIsTrue  "(comment1.\\"String\\".@domain.tld)name1@domain.tld"                            =   6 =  OK 
   *    39 - assertIsTrue  "(comment1)name1.ip4.adress@[1.2.3.4]"                                           =   2 =  OK 
   *    40 - assertIsTrue  "name1.ip4.adress(comment1)@[1.2.3.4]"                                           =   2 =  OK 
   *    41 - assertIsTrue  "name1.ip4.adress@(comment1)[1.2.3.4]"                                           =   2 =  OK 
   *    42 - assertIsTrue  "name1.ip4.adress@[1.2.3.4](comment1)"                                           =   2 =  OK 
   *    43 - assertIsTrue  "(comment1)\"string1\".name1@domain.tld"                                         =   7 =  OK 
   *    44 - assertIsTrue  "(comment1)name1.\"string1\"@domain.tld"                                         =   7 =  OK 
   *    45 - assertIsTrue  "name1.\"string1\"(comment1)@domain.tld"                                         =   7 =  OK 
   *    46 - assertIsTrue  "\"string1\".name1(comment1)@domain.tld"                                         =   7 =  OK 
   *    47 - assertIsTrue  "name1.\"string1\"@(comment1)domain.tld"                                         =   7 =  OK 
   *    48 - assertIsTrue  "\"string1\".name1@domain.tld(comment1)"                                         =   7 =  OK 
   *    49 - assertIsTrue  "<name1.name2@domain.tld>"                                                       =   0 =  OK 
   *    50 - assertIsTrue  "name3 <name1.name2@domain.tld>"                                                 =   0 =  OK 
   *    51 - assertIsTrue  "<name1.name2@domain.tld> name3"                                                 =   0 =  OK 
   *    52 - assertIsTrue  "\"name3 name4\" <name1.name2@domain.tld>"                                       =   0 =  OK 
   *    53 - assertIsTrue  "name1 <ip4.adress@[1.2.3.4]>"                                                   =   2 =  OK 
   *    54 - assertIsTrue  "name1 <ip6.adress@[IPv6:1:2:3:4:5:6:7:8]>"                                      =   4 =  OK 
   *    55 - assertIsTrue  "<ip4.adress@[1.2.3.4]> name1"                                                   =   2 =  OK 
   *    56 - assertIsTrue  "<ip6.adress@[IPv6:1:2:3:4:5:6:7:8]> name 1"                                     =   4 =  OK 
   *    57 - assertIsTrue  "\"display name\" <\"string1\".local.part@domain-name.tld (comment)>"            =   7 =  OK 
   *    58 - assertIsTrue  "\"display name\" <(comment)local.part@domain-name.top_level_domain>"            =   6 =  OK 
   *    59 - assertIsTrue  "\"display name\" <local.part@(comment)domain-name.top_level_domain>"            =   6 =  OK 
   *    60 - assertIsTrue  "\"display name\" <(comment) local.part.\"string1\"@domain-name.top_level_domain>" =   7 =  OK 
   *    61 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part@domain-name.top_level_domain>" =   6 =  OK 
   *    62 - assertIsTrue  "\"display name \\"string\\" \" <(comment)local.part.wiht.escaped.at\@.sign@domain-name.top_level_domain>" =   6 =  OK 
   *    63 - assertIsTrue  "name1\@domain.tld.name1@domain.tld"                                             =   0 =  OK 
   *    64 - assertIsTrue  "\"name1\@domain.tld\".name1@domain.tld"                                         =   1 =  OK 
   *    65 - assertIsTrue  "\"name1\@domain.tld \\"name1\@domain.tld\\"\".name1@domain.tld"                 =   1 =  OK 
   *    66 - assertIsTrue  "\"name1\@domain.tld \\"name1\@domain.tld\\"\".name1@domain.tld (name1@domain.tld)" =   7 =  OK 
   *    67 - assertIsTrue  "(name1@domain.tld) name1@domain.tld"                                            =   6 =  OK 
   *    68 - assertIsTrue  "(name1@domain.tld) \"name1\@domain.tld\".name1@domain.tld"                      =   7 =  OK 
   *    69 - assertIsTrue  "(name1@domain.tld) name1.\"name1\@domain.tld\"@domain.tld"                      =   7 =  OK 
   * 
   * ---- No Input --------------------------------------------------------------------------------------------------------------------
   * 
   *    70 - assertIsFalse null                                                                             =  10 =  OK    Laenge: Eingabe ist null
   *    71 - assertIsFalse ""                                                                               =  11 =  OK    Laenge: Eingabe ist Leerstring
   *    72 - assertIsFalse "        "                                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * ---- AT-Sign ---------------------------------------------------------------------------------------------------------------------
   * 
   *    73 - assertIsFalse "1234567890"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    74 - assertIsFalse "OnlyTextNoDotNoAt"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *    75 - assertIsFalse "email.with.no.at.sign"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    76 - assertIsFalse "email.with.no.domain@"                                                          =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *    77 - assertIsFalse "@@domain.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *    78 - assertIsFalse "name1.@domain.com"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *    79 - assertIsFalse "name1@.domain.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *    80 - assertIsFalse "@name1.at.domain.com"                                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *    81 - assertIsFalse "name1.at.domain.com@"                                                           =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *    82 - assertIsFalse "name1@name2@domain.com"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *    83 - assertIsFalse "email.with.no.domain\@domain.com"                                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    84 - assertIsFalse "email.with.no.domain\@.domain.com"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    85 - assertIsFalse "email.with.no.domain\@123domain.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    86 - assertIsFalse "email.with.no.domain\@_domain.com"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    87 - assertIsFalse "email.with.no.domain\@-domain.com"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    88 - assertIsFalse "email.with.double\@no.domain\@domain.com"                                       =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *    89 - assertIsTrue  "\"wrong.at.sign.combination.in.string1@.\"@domain.com"                          =   1 =  OK 
   *    90 - assertIsTrue  "\"wrong.at.sign.combination.in.string2.@\"@domain.com"                          =   1 =  OK 
   *    91 - assertIsTrue  "email.with.escaped.at\@.sign.version1@domain.com"                               =   0 =  OK 
   *    92 - assertIsTrue  "email.with.escaped.\@.sign.version2@domain.com"                                 =   0 =  OK 
   *    93 - assertIsTrue  "email.with.escaped.at\@123.sign.version3@domain.com"                            =   0 =  OK 
   *    94 - assertIsTrue  "email.with.escaped.\@123.sign.version4@domain.com"                              =   0 =  OK 
   *    95 - assertIsTrue  "email.with.escaped.at\@-.sign.version5@domain.com"                              =   0 =  OK 
   *    96 - assertIsTrue  "email.with.escaped.\@-.sign.version6@domain.com"                                =   0 =  OK 
   *    97 - assertIsTrue  "email.with.escaped.at.sign.\@@domain.com"                                       =   0 =  OK 
   *    98 - assertIsTrue  "(@) email.with.at.sign.in.commet1@domain.com"                                   =   6 =  OK 
   *    99 - assertIsTrue  "email.with.at.sign.in.commet2@domain.com (@)"                                   =   6 =  OK 
   *   100 - assertIsTrue  "email.with.at.sign.in.commet3@domain.com (.@)"                                  =   6 =  OK 
   *   101 - assertIsFalse "@@email.with.unescaped.at.sign.as.local.part"                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   102 - assertIsTrue  "\@@email.with.escaped.at.sign.as.local.part"                                    =   0 =  OK 
   *   103 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   104 - assertIsFalse "@no.local.part.com"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   105 - assertIsFalse "@@@@@@only.multiple.at.signs.in.local.part.com"                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   106 - assertIsFalse "local.part.with.two.@at.signs@domain.com"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *   107 - assertIsFalse "local.part.ends.with.at.sign@@domain.com"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   108 - assertIsFalse "local.part.with.at.sign.before@.point@domain.com"                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   109 - assertIsFalse "local.part.with.at.sign.after.@point@domain.com"                                =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *   110 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *   111 - assertIsTrue  "(comment @) local.part.with.at.sign.in.comment@domain.com"                      =   6 =  OK 
   *   112 - assertIsTrue  "domain.part.with.comment.with.at@(comment with @)domain.com"                    =   6 =  OK 
   *   113 - assertIsFalse "domain.part.with.comment.with.qouted.at@(comment with \@)domain.com"            =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *   114 - assertIsTrue  "\"String@\".local.part.with.at.sign.in.string@domain.com"                       =   1 =  OK 
   *   115 - assertIsTrue  "\@.\@.\@.\@.\@.\@@domain.com"                                                   =   0 =  OK 
   *   116 - assertIsFalse "\@.\@.\@.\@.\@.\@@at.sub\@domain.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   117 - assertIsFalse "@.@.@.@.@.@@domain.com"                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   118 - assertIsFalse "@.@.@."                                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *   119 - assertIsFalse "\@.\@@\@.\@"                                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   120 - assertIsFalse "@"                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *   121 - assertIsFalse "name @ <pointy.brackets1.with.at.sign.in.display.name@domain.com>"              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   122 - assertIsFalse "<pointy.brackets2.with.at.sign.in.display.name@domain.com> name @"              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   123 - assertIsTrue  "<pointy.brackets3.with.escaped.at.sign.in.display.name@domain.com> name \@"     =   0 =  OK 
   * 
   * ---- Seperator -------------------------------------------------------------------------------------------------------------------
   * 
   *   124 - assertIsFalse "EmailAdressWith@NoDots"                                                         =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *   125 - assertIsFalse "..local.part.starts.with.dot@domain.com"                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   126 - assertIsFalse "local.part.ends.with.dot.@domain.com"                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *   127 - assertIsTrue  "local.part.with.dot.character@domain.com"                                       =   0 =  OK 
   *   128 - assertIsFalse "local.part.with.dot.before..point@domain.com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   129 - assertIsFalse "local.part.with.dot.after..point@domain.com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   130 - assertIsFalse "local.part.with.double.dot..test@domain.com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   131 - assertIsTrue  "(comment .) local.part.with.dot.in.comment@domain.com"                          =   6 =  OK 
   *   132 - assertIsTrue  "\"string.\".local.part.with.dot.in.String@domain.com"                           =   1 =  OK 
   *   133 - assertIsFalse "\"string\.\".local.part.with.escaped.dot.in.String@domain.com"                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   134 - assertIsFalse ".@local.part.only.dot.domain.com"                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   135 - assertIsFalse "......@local.part.only.consecutive.dot.domain.com"                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   136 - assertIsFalse "...........@dot.domain.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   137 - assertIsFalse "name . <pointy.brackets1.with.dot.in.display.name@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   138 - assertIsFalse "<pointy.brackets2.with.dot.in.display.name@domain.com> name ."                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   139 - assertIsTrue  "domain.part@with.dot.com"                                                       =   0 =  OK 
   *   140 - assertIsFalse "domain.part@.with.dot.at.domain.start.com"                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   141 - assertIsFalse "domain.part@with.dot.at.domain.end1..com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   142 - assertIsFalse "domain.part@with.dot.at.domain.end2.com."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *   143 - assertIsFalse "domain.part@with.dot.before..point.com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   144 - assertIsFalse "domain.part@with.dot.after..point.com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   145 - assertIsFalse "domain.part@with.consecutive.dot..test.com"                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   146 - assertIsTrue  "domain.part.with.dot.in.comment@(comment .)domain.com"                          =   6 =  OK 
   *   147 - assertIsFalse "domain.part.only.dot@..com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   148 - assertIsFalse "top.level.domain.only@dot.."                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   149 - assertIsFalse "...local.part.starts.with.double.dot@domain.com"                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   150 - assertIsFalse "local.part.ends.with.double.dot..@domain.com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   151 - assertIsFalse "local.part.with.double.dot..character@domain.com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   152 - assertIsFalse "local.part.with.double.dot.before...point@domain.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   153 - assertIsFalse "local.part.with.double.dot.after...point@domain.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   154 - assertIsFalse "local.part.with.double.double.dot....test@domain.com"                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   155 - assertIsTrue  "(comment ..) local.part.with.double.dot.in.comment@domain.com"                  =   6 =  OK 
   *   156 - assertIsTrue  "\"string..\".local.part.with.double.dot.in.String@domain.com"                   =   1 =  OK 
   *   157 - assertIsFalse "\"string\..\".local.part.with.escaped.double.dot.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   158 - assertIsFalse "..@local.part.only.double.dot.domain.com"                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   159 - assertIsFalse "............@local.part.only.consecutive.double.dot.domain.com"                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   160 - assertIsFalse ".................@double.dot.domain.com"                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *   161 - assertIsFalse "name .. <pointy.brackets1.with.double.dot.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   162 - assertIsFalse "<pointy.brackets2.with.double.dot.in.display.name@domain.com> name .."          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   163 - assertIsFalse "domain.part@with..double.dot.com"                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   164 - assertIsFalse "domain.part@..with.double.dot.at.domain.start.com"                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   165 - assertIsFalse "domain.part@with.double.dot.at.domain.end1...com"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   166 - assertIsFalse "domain.part@with.double.dot.at.domain.end2.com.."                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   167 - assertIsFalse "domain.part@with.double.dot.before...point.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   168 - assertIsFalse "domain.part@with.double.dot.after...point.com"                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   169 - assertIsFalse "domain.part@with.consecutive.double.dot....test.com"                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *   170 - assertIsTrue  "domain.part.with.comment.with.double.dot@(comment ..)domain.com"                =   6 =  OK 
   *   171 - assertIsFalse "domain.part.only.double.dot@...com"                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *   172 - assertIsFalse "top.level.domain.only@double.dot..."                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   * 
   * ---- Characters ------------------------------------------------------------------------------------------------------------------
   * 
   *   173 - assertIsTrue  "&local&&part&with&$@amp.com"                                                    =   0 =  OK 
   *   174 - assertIsTrue  "*local**part*with*@asterisk.com"                                                =   0 =  OK 
   *   175 - assertIsTrue  "$local$$part$with$@dollar.com"                                                  =   0 =  OK 
   *   176 - assertIsTrue  "=local==part=with=@equality.com"                                                =   0 =  OK 
   *   177 - assertIsTrue  "!local!!part!with!@exclamation.com"                                             =   0 =  OK 
   *   178 - assertIsTrue  "`local``part`with`@grave-accent.com"                                            =   0 =  OK 
   *   179 - assertIsTrue  "#local##part#with#@hash.com"                                                    =   0 =  OK 
   *   180 - assertIsTrue  "-local--part-with-@hypen.com"                                                   =   0 =  OK 
   *   181 - assertIsTrue  "{local{part{{with{@leftbracket.com"                                             =   0 =  OK 
   *   182 - assertIsTrue  "%local%%part%with%@percentage.com"                                              =   0 =  OK 
   *   183 - assertIsTrue  "|local||part|with|@pipe.com"                                                    =   0 =  OK 
   *   184 - assertIsTrue  "+local++part+with+@plus.com"                                                    =   0 =  OK 
   *   185 - assertIsTrue  "?local??part?with?@question.com"                                                =   0 =  OK 
   *   186 - assertIsTrue  "}local}part}}with}@rightbracket.com"                                            =   0 =  OK 
   *   187 - assertIsTrue  "~local~~part~with~@tilde.com"                                                   =   0 =  OK 
   *   188 - assertIsTrue  "^local^^part^with^@xor.com"                                                     =   0 =  OK 
   *   189 - assertIsTrue  "_local__part_with_@underscore.com"                                              =   0 =  OK 
   *   190 - assertIsFalse ":local::part:with:@colon.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   191 - assertIsFalse "local.part@&domain&&part&with&.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   192 - assertIsFalse "local.part@*domain**part*with*.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   193 - assertIsFalse "local.part@$domain$$part$with$.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   194 - assertIsFalse "local.part@=domain==part=with=.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   195 - assertIsFalse "local.part@!domain!!part!with!.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   196 - assertIsFalse "local.part@`domain``part`with`.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   197 - assertIsFalse "local.part@#domain##part#with#.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   198 - assertIsFalse "local.part@-domain--part-with-.com"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   199 - assertIsFalse "local.part@{domain{part{{with{.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   200 - assertIsFalse "local.part@%domain%%part%with%.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   201 - assertIsFalse "local.part@|domain||part|with|.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   202 - assertIsFalse "local.part@+domain++part+with+.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   203 - assertIsFalse "local.part@?domain??part?with?.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   204 - assertIsFalse "local.part@}domain}part}}with}.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   205 - assertIsFalse "local.part@~domain~~part~with~.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   206 - assertIsFalse "local.part@^domain^^part^with^.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   207 - assertIsFalse "local.part@_domain__part_with_.com"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   208 - assertIsFalse "local.part@domain--part.double.dash.com"                                        =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *   209 - assertIsFalse ";.local.part.starts.with.semicolon@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   210 - assertIsFalse "local.part.ends.with.semicolon;@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   211 - assertIsFalse "local.part.with.semicolon;character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   212 - assertIsFalse "local.part.with.semicolon.before;.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   213 - assertIsFalse "local.part.with.semicolon.after.;point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   214 - assertIsFalse "local.part.with.double.semicolon;;test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   215 - assertIsFalse "(comment ;) local.part.with.semicolon.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   216 - assertIsTrue  "\"string;\".local.part.with.semicolon.in.String@domain.com"                     =   1 =  OK 
   *   217 - assertIsFalse "\"string\;\".local.part.with.escaped.semicolon.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   218 - assertIsFalse ";@local.part.only.semicolon.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   219 - assertIsFalse ";;;;;;@local.part.only.consecutive.semicolon.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   220 - assertIsFalse ";.;.;.;.;.;@semicolon.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   221 - assertIsFalse "name ; <pointy.brackets1.with.semicolon.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   222 - assertIsFalse "<pointy.brackets2.with.semicolon.in.display.name@domain.com> name ;"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   223 - assertIsFalse "domain.part@with;semicolon.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   224 - assertIsFalse "domain.part@;with.semicolon.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   225 - assertIsFalse "domain.part@with.semicolon.at.domain.end1;.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   226 - assertIsFalse "domain.part@with.semicolon.at.domain.end2.com;"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   227 - assertIsFalse "domain.part@with.semicolon.before;.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   228 - assertIsFalse "domain.part@with.semicolon.after.;point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   229 - assertIsFalse "domain.part@with.consecutive.semicolon;;test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   230 - assertIsFalse "domain.part.with.semicolon.in.comment@(comment ;)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   231 - assertIsFalse "domain.part.only.semicolon@;.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   232 - assertIsFalse "top.level.domain.only@semicolon.;"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   233 - assertIsTrue  "&.local.part.starts.with.amp@domain.com"                                        =   0 =  OK 
   *   234 - assertIsTrue  "local.part.ends.with.amp&@domain.com"                                           =   0 =  OK 
   *   235 - assertIsTrue  "local.part.with.amp&character@domain.com"                                       =   0 =  OK 
   *   236 - assertIsTrue  "local.part.with.amp.before&.point@domain.com"                                   =   0 =  OK 
   *   237 - assertIsTrue  "local.part.with.amp.after.&point@domain.com"                                    =   0 =  OK 
   *   238 - assertIsTrue  "local.part.with.double.amp&&test@domain.com"                                    =   0 =  OK 
   *   239 - assertIsTrue  "(comment &) local.part.with.amp.in.comment@domain.com"                          =   6 =  OK 
   *   240 - assertIsTrue  "\"string&\".local.part.with.amp.in.String@domain.com"                           =   1 =  OK 
   *   241 - assertIsFalse "\"string\&\".local.part.with.escaped.amp.in.String@domain.com"                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   242 - assertIsTrue  "&@local.part.only.amp.domain.com"                                               =   0 =  OK 
   *   243 - assertIsTrue  "&&&&&&@local.part.only.consecutive.amp.domain.com"                              =   0 =  OK 
   *   244 - assertIsTrue  "&.&.&.&.&.&@amp.domain.com"                                                     =   0 =  OK 
   *   245 - assertIsFalse "name & <pointy.brackets1.with.amp.in.display.name@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   246 - assertIsFalse "<pointy.brackets2.with.amp.in.display.name@domain.com> name &"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   247 - assertIsFalse "domain.part@with&amp.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   248 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   249 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   250 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   251 - assertIsFalse "domain.part@with.amp.before&.point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   252 - assertIsFalse "domain.part@with.amp.after.&point.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   253 - assertIsFalse "domain.part@with.consecutive.amp&&test.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   254 - assertIsTrue  "domain.part.with.amp.in.comment@(comment &)domain.com"                          =   6 =  OK 
   *   255 - assertIsFalse "domain.part.only.amp@&.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   256 - assertIsFalse "top.level.domain.only@amp.&"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   257 - assertIsTrue  "*.local.part.starts.with.asterisk@domain.com"                                   =   0 =  OK 
   *   258 - assertIsTrue  "local.part.ends.with.asterisk*@domain.com"                                      =   0 =  OK 
   *   259 - assertIsTrue  "local.part.with.asterisk*character@domain.com"                                  =   0 =  OK 
   *   260 - assertIsTrue  "local.part.with.asterisk.before*.point@domain.com"                              =   0 =  OK 
   *   261 - assertIsTrue  "local.part.with.asterisk.after.*point@domain.com"                               =   0 =  OK 
   *   262 - assertIsTrue  "local.part.with.double.asterisk**test@domain.com"                               =   0 =  OK 
   *   263 - assertIsTrue  "(comment *) local.part.with.asterisk.in.comment@domain.com"                     =   6 =  OK 
   *   264 - assertIsTrue  "\"string*\".local.part.with.asterisk.in.String@domain.com"                      =   1 =  OK 
   *   265 - assertIsFalse "\"string\*\".local.part.with.escaped.asterisk.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   266 - assertIsTrue  "*@local.part.only.asterisk.domain.com"                                          =   0 =  OK 
   *   267 - assertIsTrue  "******@local.part.only.consecutive.asterisk.domain.com"                         =   0 =  OK 
   *   268 - assertIsTrue  "*.*.*.*.*.*@asterisk.domain.com"                                                =   0 =  OK 
   *   269 - assertIsFalse "name * <pointy.brackets1.with.asterisk.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   270 - assertIsFalse "<pointy.brackets2.with.asterisk.in.display.name@domain.com> name *"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   271 - assertIsFalse "domain.part@with*asterisk.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   272 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   273 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   274 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   275 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   276 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   277 - assertIsFalse "domain.part@with.consecutive.asterisk**test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   278 - assertIsTrue  "domain.part.with.asterisk.in.comment@(comment *)domain.com"                     =   6 =  OK 
   *   279 - assertIsFalse "domain.part.only.asterisk@*.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   280 - assertIsFalse "top.level.domain.only@asterisk.*"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   281 - assertIsTrue  "_.local.part.starts.with.underscore@domain.com"                                 =   0 =  OK 
   *   282 - assertIsTrue  "local.part.ends.with.underscore_@domain.com"                                    =   0 =  OK 
   *   283 - assertIsTrue  "local.part.with.underscore_character@domain.com"                                =   0 =  OK 
   *   284 - assertIsTrue  "local.part.with.underscore.before_.point@domain.com"                            =   0 =  OK 
   *   285 - assertIsTrue  "local.part.with.underscore.after._point@domain.com"                             =   0 =  OK 
   *   286 - assertIsTrue  "local.part.with.double.underscore__test@domain.com"                             =   0 =  OK 
   *   287 - assertIsTrue  "(comment _) local.part.with.underscore.in.comment@domain.com"                   =   6 =  OK 
   *   288 - assertIsTrue  "\"string_\".local.part.with.underscore.in.String@domain.com"                    =   1 =  OK 
   *   289 - assertIsFalse "\"string\_\".local.part.with.escaped.underscore.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   290 - assertIsTrue  "_@local.part.only.underscore.domain.com"                                        =   0 =  OK 
   *   291 - assertIsTrue  "______@local.part.only.consecutive.underscore.domain.com"                       =   0 =  OK 
   *   292 - assertIsTrue  "_._._._._._@underscore.domain.com"                                              =   0 =  OK 
   *   293 - assertIsFalse "name _ <pointy.brackets1.with.underscore.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   294 - assertIsFalse "<pointy.brackets2.with.underscore.in.display.name@domain.com> name _"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   295 - assertIsTrue  "domain.part@with_underscore.com"                                                =   0 =  OK 
   *   296 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   297 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   298 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *   299 - assertIsFalse "domain.part@with.underscore.before_.point.com"                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   300 - assertIsFalse "domain.part@with.underscore.after._point.com"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   301 - assertIsTrue  "domain.part@with.consecutive.underscore__test.com"                              =   0 =  OK 
   *   302 - assertIsTrue  "domain.part.with.underscore.in.comment@(comment _)domain.com"                   =   6 =  OK 
   *   303 - assertIsFalse "domain.part.only.underscore@_.com"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   304 - assertIsFalse "top.level.domain.only@underscore._"                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *   305 - assertIsTrue  "$.local.part.starts.with.dollar@domain.com"                                     =   0 =  OK 
   *   306 - assertIsTrue  "local.part.ends.with.dollar$@domain.com"                                        =   0 =  OK 
   *   307 - assertIsTrue  "local.part.with.dollar$character@domain.com"                                    =   0 =  OK 
   *   308 - assertIsTrue  "local.part.with.dollar.before$.point@domain.com"                                =   0 =  OK 
   *   309 - assertIsTrue  "local.part.with.dollar.after.$point@domain.com"                                 =   0 =  OK 
   *   310 - assertIsTrue  "local.part.with.double.dollar$$test@domain.com"                                 =   0 =  OK 
   *   311 - assertIsTrue  "(comment $) local.part.with.dollar.in.comment@domain.com"                       =   6 =  OK 
   *   312 - assertIsTrue  "\"string$\".local.part.with.dollar.in.String@domain.com"                        =   1 =  OK 
   *   313 - assertIsFalse "\"string\$\".local.part.with.escaped.dollar.in.String@domain.com"               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   314 - assertIsTrue  "$@local.part.only.dollar.domain.com"                                            =   0 =  OK 
   *   315 - assertIsTrue  "$$$$$$@local.part.only.consecutive.dollar.domain.com"                           =   0 =  OK 
   *   316 - assertIsTrue  "$.$.$.$.$.$@dollar.domain.com"                                                  =   0 =  OK 
   *   317 - assertIsFalse "name $ <pointy.brackets1.with.dollar.in.display.name@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   318 - assertIsFalse "<pointy.brackets2.with.dollar.in.display.name@domain.com> name $"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   319 - assertIsFalse "domain.part@with$dollar.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   320 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   321 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   322 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   323 - assertIsFalse "domain.part@with.dollar.before$.point.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   324 - assertIsFalse "domain.part@with.dollar.after.$point.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   325 - assertIsFalse "domain.part@with.consecutive.dollar$$test.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   326 - assertIsTrue  "domain.part.with.dollar.in.comment@(comment $)domain.com"                       =   6 =  OK 
   *   327 - assertIsFalse "domain.part.only.dollar@$.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   328 - assertIsFalse "top.level.domain.only@dollar.$"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   329 - assertIsTrue  "=.local.part.starts.with.equality@domain.com"                                   =   0 =  OK 
   *   330 - assertIsTrue  "local.part.ends.with.equality=@domain.com"                                      =   0 =  OK 
   *   331 - assertIsTrue  "local.part.with.equality=character@domain.com"                                  =   0 =  OK 
   *   332 - assertIsTrue  "local.part.with.equality.before=.point@domain.com"                              =   0 =  OK 
   *   333 - assertIsTrue  "local.part.with.equality.after.=point@domain.com"                               =   0 =  OK 
   *   334 - assertIsTrue  "local.part.with.double.equality==test@domain.com"                               =   0 =  OK 
   *   335 - assertIsTrue  "(comment =) local.part.with.equality.in.comment@domain.com"                     =   6 =  OK 
   *   336 - assertIsTrue  "\"string=\".local.part.with.equality.in.String@domain.com"                      =   1 =  OK 
   *   337 - assertIsFalse "\"string\=\".local.part.with.escaped.equality.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   338 - assertIsTrue  "=@local.part.only.equality.domain.com"                                          =   0 =  OK 
   *   339 - assertIsTrue  "======@local.part.only.consecutive.equality.domain.com"                         =   0 =  OK 
   *   340 - assertIsTrue  "=.=.=.=.=.=@equality.domain.com"                                                =   0 =  OK 
   *   341 - assertIsFalse "name = <pointy.brackets1.with.equality.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   342 - assertIsFalse "<pointy.brackets2.with.equality.in.display.name@domain.com> name ="             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   343 - assertIsFalse "domain.part@with=equality.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   344 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   345 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   346 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   347 - assertIsFalse "domain.part@with.equality.before=.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   348 - assertIsFalse "domain.part@with.equality.after.=point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   349 - assertIsFalse "domain.part@with.consecutive.equality==test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   350 - assertIsTrue  "domain.part.with.equality.in.comment@(comment =)domain.com"                     =   6 =  OK 
   *   351 - assertIsFalse "domain.part.only.equality@=.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   352 - assertIsFalse "top.level.domain.only@equality.="                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   353 - assertIsTrue  "!.local.part.starts.with.exclamation@domain.com"                                =   0 =  OK 
   *   354 - assertIsTrue  "local.part.ends.with.exclamation!@domain.com"                                   =   0 =  OK 
   *   355 - assertIsTrue  "local.part.with.exclamation!character@domain.com"                               =   0 =  OK 
   *   356 - assertIsTrue  "local.part.with.exclamation.before!.point@domain.com"                           =   0 =  OK 
   *   357 - assertIsTrue  "local.part.with.exclamation.after.!point@domain.com"                            =   0 =  OK 
   *   358 - assertIsTrue  "local.part.with.double.exclamation!!test@domain.com"                            =   0 =  OK 
   *   359 - assertIsTrue  "(comment !) local.part.with.exclamation.in.comment@domain.com"                  =   6 =  OK 
   *   360 - assertIsTrue  "\"string!\".local.part.with.exclamation.in.String@domain.com"                   =   1 =  OK 
   *   361 - assertIsFalse "\"string\!\".local.part.with.escaped.exclamation.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   362 - assertIsTrue  "!@local.part.only.exclamation.domain.com"                                       =   0 =  OK 
   *   363 - assertIsTrue  "!!!!!!@local.part.only.consecutive.exclamation.domain.com"                      =   0 =  OK 
   *   364 - assertIsTrue  "!.!.!.!.!.!@exclamation.domain.com"                                             =   0 =  OK 
   *   365 - assertIsFalse "name ! <pointy.brackets1.with.exclamation.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   366 - assertIsFalse "<pointy.brackets2.with.exclamation.in.display.name@domain.com> name !"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   367 - assertIsFalse "domain.part@with!exclamation.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   368 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   369 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   370 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   371 - assertIsFalse "domain.part@with.exclamation.before!.point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   372 - assertIsFalse "domain.part@with.exclamation.after.!point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   373 - assertIsFalse "domain.part@with.consecutive.exclamation!!test.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   374 - assertIsTrue  "domain.part.with.exclamation.in.comment@(comment !)domain.com"                  =   6 =  OK 
   *   375 - assertIsFalse "domain.part.only.exclamation@!.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   376 - assertIsFalse "top.level.domain.only@exclamation.!"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   377 - assertIsTrue  "?.local.part.starts.with.question@domain.com"                                   =   0 =  OK 
   *   378 - assertIsTrue  "local.part.ends.with.question?@domain.com"                                      =   0 =  OK 
   *   379 - assertIsTrue  "local.part.with.question?character@domain.com"                                  =   0 =  OK 
   *   380 - assertIsTrue  "local.part.with.question.before?.point@domain.com"                              =   0 =  OK 
   *   381 - assertIsTrue  "local.part.with.question.after.?point@domain.com"                               =   0 =  OK 
   *   382 - assertIsTrue  "local.part.with.double.question??test@domain.com"                               =   0 =  OK 
   *   383 - assertIsTrue  "(comment ?) local.part.with.question.in.comment@domain.com"                     =   6 =  OK 
   *   384 - assertIsTrue  "\"string?\".local.part.with.question.in.String@domain.com"                      =   1 =  OK 
   *   385 - assertIsFalse "\"string\?\".local.part.with.escaped.question.in.String@domain.com"             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   386 - assertIsTrue  "?@local.part.only.question.domain.com"                                          =   0 =  OK 
   *   387 - assertIsTrue  "??????@local.part.only.consecutive.question.domain.com"                         =   0 =  OK 
   *   388 - assertIsTrue  "?.?.?.?.?.?@question.domain.com"                                                =   0 =  OK 
   *   389 - assertIsFalse "name ? <pointy.brackets1.with.question.in.display.name@domain.com>"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   390 - assertIsFalse "<pointy.brackets2.with.question.in.display.name@domain.com> name ?"             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   391 - assertIsFalse "domain.part@with?question.com"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   392 - assertIsFalse "domain.part@?with.question.at.domain.start.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   393 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   394 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   395 - assertIsFalse "domain.part@with.question.before?.point.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   396 - assertIsFalse "domain.part@with.question.after.?point.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   397 - assertIsFalse "domain.part@with.consecutive.question??test.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   398 - assertIsTrue  "domain.part.with.question.in.comment@(comment ?)domain.com"                     =   6 =  OK 
   *   399 - assertIsFalse "domain.part.only.question@?.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   400 - assertIsFalse "top.level.domain.only@question.?"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   401 - assertIsTrue  "`.local.part.starts.with.grave-accent@domain.com"                               =   0 =  OK 
   *   402 - assertIsTrue  "local.part.ends.with.grave-accent`@domain.com"                                  =   0 =  OK 
   *   403 - assertIsTrue  "local.part.with.grave-accent`character@domain.com"                              =   0 =  OK 
   *   404 - assertIsTrue  "local.part.with.grave-accent.before`.point@domain.com"                          =   0 =  OK 
   *   405 - assertIsTrue  "local.part.with.grave-accent.after.`point@domain.com"                           =   0 =  OK 
   *   406 - assertIsTrue  "local.part.with.double.grave-accent``test@domain.com"                           =   0 =  OK 
   *   407 - assertIsTrue  "(comment `) local.part.with.grave-accent.in.comment@domain.com"                 =   6 =  OK 
   *   408 - assertIsTrue  "\"string`\".local.part.with.grave-accent.in.String@domain.com"                  =   1 =  OK 
   *   409 - assertIsFalse "\"string\`\".local.part.with.escaped.grave-accent.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   410 - assertIsTrue  "`@local.part.only.grave-accent.domain.com"                                      =   0 =  OK 
   *   411 - assertIsTrue  "``````@local.part.only.consecutive.grave-accent.domain.com"                     =   0 =  OK 
   *   412 - assertIsTrue  "`.`.`.`.`.`@grave-accent.domain.com"                                            =   0 =  OK 
   *   413 - assertIsFalse "name ` <pointy.brackets1.with.grave-accent.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   414 - assertIsFalse "<pointy.brackets2.with.grave-accent.in.display.name@domain.com> name `"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   415 - assertIsFalse "domain.part@with`grave-accent.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   416 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   417 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   418 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   419 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   420 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   421 - assertIsFalse "domain.part@with.consecutive.grave-accent``test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   422 - assertIsTrue  "domain.part.with.grave-accent.in.comment@(comment `)domain.com"                 =   6 =  OK 
   *   423 - assertIsFalse "domain.part.only.grave-accent@`.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   424 - assertIsFalse "top.level.domain.only@grave-accent.`"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   425 - assertIsTrue  "#.local.part.starts.with.hash@domain.com"                                       =   0 =  OK 
   *   426 - assertIsTrue  "local.part.ends.with.hash#@domain.com"                                          =   0 =  OK 
   *   427 - assertIsTrue  "local.part.with.hash#character@domain.com"                                      =   0 =  OK 
   *   428 - assertIsTrue  "local.part.with.hash.before#.point@domain.com"                                  =   0 =  OK 
   *   429 - assertIsTrue  "local.part.with.hash.after.#point@domain.com"                                   =   0 =  OK 
   *   430 - assertIsTrue  "local.part.with.double.hash##test@domain.com"                                   =   0 =  OK 
   *   431 - assertIsTrue  "(comment #) local.part.with.hash.in.comment@domain.com"                         =   6 =  OK 
   *   432 - assertIsTrue  "\"string#\".local.part.with.hash.in.String@domain.com"                          =   1 =  OK 
   *   433 - assertIsFalse "\"string\#\".local.part.with.escaped.hash.in.String@domain.com"                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   434 - assertIsTrue  "#@local.part.only.hash.domain.com"                                              =   0 =  OK 
   *   435 - assertIsTrue  "######@local.part.only.consecutive.hash.domain.com"                             =   0 =  OK 
   *   436 - assertIsTrue  "#.#.#.#.#.#@hash.domain.com"                                                    =   0 =  OK 
   *   437 - assertIsFalse "name # <pointy.brackets1.with.hash.in.display.name@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   438 - assertIsFalse "<pointy.brackets2.with.hash.in.display.name@domain.com> name #"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   439 - assertIsFalse "domain.part@with#hash.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   440 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   441 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   442 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   443 - assertIsFalse "domain.part@with.hash.before#.point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   444 - assertIsFalse "domain.part@with.hash.after.#point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   445 - assertIsFalse "domain.part@with.consecutive.hash##test.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   446 - assertIsTrue  "domain.part.with.hash.in.comment@(comment #)domain.com"                         =   6 =  OK 
   *   447 - assertIsFalse "domain.part.only.hash@#.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   448 - assertIsFalse "top.level.domain.only@hash.#"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   449 - assertIsTrue  "%.local.part.starts.with.percentage@domain.com"                                 =   0 =  OK 
   *   450 - assertIsTrue  "local.part.ends.with.percentage%@domain.com"                                    =   0 =  OK 
   *   451 - assertIsTrue  "local.part.with.percentage%character@domain.com"                                =   0 =  OK 
   *   452 - assertIsTrue  "local.part.with.percentage.before%.point@domain.com"                            =   0 =  OK 
   *   453 - assertIsTrue  "local.part.with.percentage.after.%point@domain.com"                             =   0 =  OK 
   *   454 - assertIsTrue  "local.part.with.double.percentage%%test@domain.com"                             =   0 =  OK 
   *   455 - assertIsTrue  "(comment %) local.part.with.percentage.in.comment@domain.com"                   =   6 =  OK 
   *   456 - assertIsTrue  "\"string%\".local.part.with.percentage.in.String@domain.com"                    =   1 =  OK 
   *   457 - assertIsFalse "\"string\%\".local.part.with.escaped.percentage.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   458 - assertIsTrue  "%@local.part.only.percentage.domain.com"                                        =   0 =  OK 
   *   459 - assertIsTrue  "%%%%%%@local.part.only.consecutive.percentage.domain.com"                       =   0 =  OK 
   *   460 - assertIsTrue  "%.%.%.%.%.%@percentage.domain.com"                                              =   0 =  OK 
   *   461 - assertIsFalse "name % <pointy.brackets1.with.percentage.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   462 - assertIsFalse "<pointy.brackets2.with.percentage.in.display.name@domain.com> name %"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   463 - assertIsFalse "domain.part@with%percentage.com"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   464 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   465 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   466 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   467 - assertIsFalse "domain.part@with.percentage.before%.point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   468 - assertIsFalse "domain.part@with.percentage.after.%point.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   469 - assertIsFalse "domain.part@with.consecutive.percentage%%test.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   470 - assertIsTrue  "domain.part.with.percentage.in.comment@(comment %)domain.com"                   =   6 =  OK 
   *   471 - assertIsFalse "domain.part.only.percentage@%.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   472 - assertIsFalse "top.level.domain.only@percentage.%"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   473 - assertIsTrue  "|.local.part.starts.with.pipe@domain.com"                                       =   0 =  OK 
   *   474 - assertIsTrue  "local.part.ends.with.pipe|@domain.com"                                          =   0 =  OK 
   *   475 - assertIsTrue  "local.part.with.pipe|character@domain.com"                                      =   0 =  OK 
   *   476 - assertIsTrue  "local.part.with.pipe.before|.point@domain.com"                                  =   0 =  OK 
   *   477 - assertIsTrue  "local.part.with.pipe.after.|point@domain.com"                                   =   0 =  OK 
   *   478 - assertIsTrue  "local.part.with.double.pipe||test@domain.com"                                   =   0 =  OK 
   *   479 - assertIsTrue  "(comment |) local.part.with.pipe.in.comment@domain.com"                         =   6 =  OK 
   *   480 - assertIsTrue  "\"string|\".local.part.with.pipe.in.String@domain.com"                          =   1 =  OK 
   *   481 - assertIsFalse "\"string\|\".local.part.with.escaped.pipe.in.String@domain.com"                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   482 - assertIsTrue  "|@local.part.only.pipe.domain.com"                                              =   0 =  OK 
   *   483 - assertIsTrue  "||||||@local.part.only.consecutive.pipe.domain.com"                             =   0 =  OK 
   *   484 - assertIsTrue  "|.|.|.|.|.|@pipe.domain.com"                                                    =   0 =  OK 
   *   485 - assertIsFalse "name | <pointy.brackets1.with.pipe.in.display.name@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   486 - assertIsFalse "<pointy.brackets2.with.pipe.in.display.name@domain.com> name |"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   487 - assertIsFalse "domain.part@with|pipe.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   488 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   489 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   490 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   491 - assertIsFalse "domain.part@with.pipe.before|.point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   492 - assertIsFalse "domain.part@with.pipe.after.|point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   493 - assertIsFalse "domain.part@with.consecutive.pipe||test.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   494 - assertIsTrue  "domain.part.with.pipe.in.comment@(comment |)domain.com"                         =   6 =  OK 
   *   495 - assertIsFalse "domain.part.only.pipe@|.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   496 - assertIsFalse "top.level.domain.only@pipe.|"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   497 - assertIsTrue  "+.local.part.starts.with.plus@domain.com"                                       =   0 =  OK 
   *   498 - assertIsTrue  "local.part.ends.with.plus+@domain.com"                                          =   0 =  OK 
   *   499 - assertIsTrue  "local.part.with.plus+character@domain.com"                                      =   0 =  OK 
   *   500 - assertIsTrue  "local.part.with.plus.before+.point@domain.com"                                  =   0 =  OK 
   *   501 - assertIsTrue  "local.part.with.plus.after.+point@domain.com"                                   =   0 =  OK 
   *   502 - assertIsTrue  "local.part.with.double.plus++test@domain.com"                                   =   0 =  OK 
   *   503 - assertIsTrue  "(comment +) local.part.with.plus.in.comment@domain.com"                         =   6 =  OK 
   *   504 - assertIsTrue  "\"string+\".local.part.with.plus.in.String@domain.com"                          =   1 =  OK 
   *   505 - assertIsFalse "\"string\+\".local.part.with.escaped.plus.in.String@domain.com"                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   506 - assertIsTrue  "+@local.part.only.plus.domain.com"                                              =   0 =  OK 
   *   507 - assertIsTrue  "++++++@local.part.only.consecutive.plus.domain.com"                             =   0 =  OK 
   *   508 - assertIsTrue  "+.+.+.+.+.+@plus.domain.com"                                                    =   0 =  OK 
   *   509 - assertIsFalse "name + <pointy.brackets1.with.plus.in.display.name@domain.com>"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   510 - assertIsFalse "<pointy.brackets2.with.plus.in.display.name@domain.com> name +"                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   511 - assertIsFalse "domain.part@with+plus.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   512 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   513 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   514 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   515 - assertIsFalse "domain.part@with.plus.before+.point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   516 - assertIsFalse "domain.part@with.plus.after.+point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   517 - assertIsFalse "domain.part@with.consecutive.plus++test.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   518 - assertIsTrue  "domain.part.with.plus.in.comment@(comment +)domain.com"                         =   6 =  OK 
   *   519 - assertIsFalse "domain.part.only.plus@+.com"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   520 - assertIsFalse "top.level.domain.only@plus.+"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   521 - assertIsTrue  "{.local.part.starts.with.leftbracket@domain.com"                                =   0 =  OK 
   *   522 - assertIsTrue  "local.part.ends.with.leftbracket{@domain.com"                                   =   0 =  OK 
   *   523 - assertIsTrue  "local.part.with.leftbracket{character@domain.com"                               =   0 =  OK 
   *   524 - assertIsTrue  "local.part.with.leftbracket.before{.point@domain.com"                           =   0 =  OK 
   *   525 - assertIsTrue  "local.part.with.leftbracket.after.{point@domain.com"                            =   0 =  OK 
   *   526 - assertIsTrue  "local.part.with.double.leftbracket{{test@domain.com"                            =   0 =  OK 
   *   527 - assertIsTrue  "(comment {) local.part.with.leftbracket.in.comment@domain.com"                  =   6 =  OK 
   *   528 - assertIsTrue  "\"string{\".local.part.with.leftbracket.in.String@domain.com"                   =   1 =  OK 
   *   529 - assertIsFalse "\"string\{\".local.part.with.escaped.leftbracket.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   530 - assertIsTrue  "{@local.part.only.leftbracket.domain.com"                                       =   0 =  OK 
   *   531 - assertIsTrue  "{{{{{{@local.part.only.consecutive.leftbracket.domain.com"                      =   0 =  OK 
   *   532 - assertIsTrue  "{.{.{.{.{.{@leftbracket.domain.com"                                             =   0 =  OK 
   *   533 - assertIsFalse "name { <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   534 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name {"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   535 - assertIsFalse "domain.part@with{leftbracket.com"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   536 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   537 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   538 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   539 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   540 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   541 - assertIsFalse "domain.part@with.consecutive.leftbracket{{test.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   542 - assertIsTrue  "domain.part.with.leftbracket.in.comment@(comment {)domain.com"                  =   6 =  OK 
   *   543 - assertIsFalse "domain.part.only.leftbracket@{.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   544 - assertIsFalse "top.level.domain.only@leftbracket.{"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   545 - assertIsTrue  "}.local.part.starts.with.rightbracket@domain.com"                               =   0 =  OK 
   *   546 - assertIsTrue  "local.part.ends.with.rightbracket}@domain.com"                                  =   0 =  OK 
   *   547 - assertIsTrue  "local.part.with.rightbracket}character@domain.com"                              =   0 =  OK 
   *   548 - assertIsTrue  "local.part.with.rightbracket.before}.point@domain.com"                          =   0 =  OK 
   *   549 - assertIsTrue  "local.part.with.rightbracket.after.}point@domain.com"                           =   0 =  OK 
   *   550 - assertIsTrue  "local.part.with.double.rightbracket}}test@domain.com"                           =   0 =  OK 
   *   551 - assertIsTrue  "(comment }) local.part.with.rightbracket.in.comment@domain.com"                 =   6 =  OK 
   *   552 - assertIsTrue  "\"string}\".local.part.with.rightbracket.in.String@domain.com"                  =   1 =  OK 
   *   553 - assertIsFalse "\"string\}\".local.part.with.escaped.rightbracket.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   554 - assertIsTrue  "}@local.part.only.rightbracket.domain.com"                                      =   0 =  OK 
   *   555 - assertIsTrue  "}}}}}}@local.part.only.consecutive.rightbracket.domain.com"                     =   0 =  OK 
   *   556 - assertIsTrue  "}.}.}.}.}.}@rightbracket.domain.com"                                            =   0 =  OK 
   *   557 - assertIsFalse "name } <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   558 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name }"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   559 - assertIsFalse "domain.part@with}rightbracket.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   560 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   561 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   562 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   563 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   564 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   565 - assertIsFalse "domain.part@with.consecutive.rightbracket}}test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   566 - assertIsTrue  "domain.part.with.rightbracket.in.comment@(comment })domain.com"                 =   6 =  OK 
   *   567 - assertIsFalse "domain.part.only.rightbracket@}.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   568 - assertIsFalse "top.level.domain.only@rightbracket.}"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   569 - assertIsFalse "(.local.part.starts.with.leftbracket@domain.com"                                =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   570 - assertIsFalse "local.part.ends.with.leftbracket(@domain.com"                                   =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   571 - assertIsFalse "local.part.with.leftbracket(character@domain.com"                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   572 - assertIsFalse "local.part.with.leftbracket.before(.point@domain.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   573 - assertIsFalse "local.part.with.leftbracket.after.(point@domain.com"                            = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   574 - assertIsFalse "local.part.with.double.leftbracket((test@domain.com"                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   575 - assertIsFalse "(comment () local.part.with.leftbracket.in.comment@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   576 - assertIsTrue  "\"string(\".local.part.with.leftbracket.in.String@domain.com"                   =   1 =  OK 
   *   577 - assertIsFalse "\"string\(\".local.part.with.escaped.leftbracket.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   578 - assertIsFalse "(@local.part.only.leftbracket.domain.com"                                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   579 - assertIsFalse "((((((@local.part.only.consecutive.leftbracket.domain.com"                      =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   580 - assertIsFalse "(.(.(.(.(.(@leftbracket.domain.com"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   581 - assertIsTrue  "name ( <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"          =   0 =  OK 
   *   582 - assertIsTrue  "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ("          =   0 =  OK 
   *   583 - assertIsFalse "domain.part@with(leftbracket.com"                                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   584 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"                              =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   585 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   586 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("                               =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   587 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"                                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   588 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"                                  = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   589 - assertIsFalse "domain.part@with.consecutive.leftbracket((test.com"                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   590 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment ()domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   591 - assertIsFalse "domain.part.only.leftbracket@(.com"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *   592 - assertIsFalse "top.level.domain.only@leftbracket.("                                            = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   593 - assertIsFalse ").local.part.starts.with.rightbracket@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   594 - assertIsFalse "local.part.ends.with.rightbracket)@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   595 - assertIsFalse "local.part.with.rightbracket)character@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   596 - assertIsFalse "local.part.with.rightbracket.before).point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   597 - assertIsFalse "local.part.with.rightbracket.after.)point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   598 - assertIsFalse "local.part.with.double.rightbracket))test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   599 - assertIsFalse "(comment )) local.part.with.rightbracket.in.comment@domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   600 - assertIsTrue  "\"string)\".local.part.with.rightbracket.in.String@domain.com"                  =   1 =  OK 
   *   601 - assertIsFalse "\"string\)\".local.part.with.escaped.rightbracket.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   602 - assertIsFalse ")@local.part.only.rightbracket.domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   603 - assertIsFalse "))))))@local.part.only.consecutive.rightbracket.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   604 - assertIsFalse ").).).).).)@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   605 - assertIsTrue  "name ) <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"         =   0 =  OK 
   *   606 - assertIsTrue  "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name )"         =   0 =  OK 
   *   607 - assertIsFalse "domain.part@with)rightbracket.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   608 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   609 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   610 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   611 - assertIsFalse "domain.part@with.rightbracket.before).point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   612 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   613 - assertIsFalse "domain.part@with.consecutive.rightbracket))test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   614 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ))domain.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   615 - assertIsFalse "domain.part.only.rightbracket@).com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   616 - assertIsFalse "top.level.domain.only@rightbracket.)"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   617 - assertIsFalse "[.local.part.starts.with.leftbracket@domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   618 - assertIsFalse "local.part.ends.with.leftbracket[@domain.com"                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   619 - assertIsFalse "local.part.with.leftbracket[character@domain.com"                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   620 - assertIsFalse "local.part.with.leftbracket.before[.point@domain.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   621 - assertIsFalse "local.part.with.leftbracket.after.[point@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   622 - assertIsFalse "local.part.with.double.leftbracket[[test@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   623 - assertIsFalse "(comment [) local.part.with.leftbracket.in.comment@domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   624 - assertIsTrue  "\"string[\".local.part.with.leftbracket.in.String@domain.com"                   =   1 =  OK 
   *   625 - assertIsFalse "\"string\[\".local.part.with.escaped.leftbracket.in.String@domain.com"          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   626 - assertIsFalse "[@local.part.only.leftbracket.domain.com"                                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   627 - assertIsFalse "[[[[[[@local.part.only.consecutive.leftbracket.domain.com"                      =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   628 - assertIsFalse "[.[.[.[.[.[@leftbracket.domain.com"                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   629 - assertIsFalse "name [ <pointy.brackets1.with.leftbracket.in.display.name@domain.com>"          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   630 - assertIsFalse "<pointy.brackets2.with.leftbracket.in.display.name@domain.com> name ["          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   631 - assertIsFalse "domain.part@with[leftbracket.com"                                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   632 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *   633 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   634 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   635 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   636 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   637 - assertIsFalse "domain.part@with.consecutive.leftbracket[[test.com"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   638 - assertIsFalse "domain.part.with.leftbracket.in.comment@(comment [)domain.com"                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   639 - assertIsFalse "domain.part.only.leftbracket@[.com"                                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *   640 - assertIsFalse "top.level.domain.only@leftbracket.["                                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   641 - assertIsFalse "].local.part.starts.with.rightbracket@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   642 - assertIsFalse "local.part.ends.with.rightbracket]@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   643 - assertIsFalse "local.part.with.rightbracket]character@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   644 - assertIsFalse "local.part.with.rightbracket.before].point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   645 - assertIsFalse "local.part.with.rightbracket.after.]point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   646 - assertIsFalse "local.part.with.double.rightbracket]]test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   647 - assertIsFalse "(comment ]) local.part.with.rightbracket.in.comment@domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   648 - assertIsTrue  "\"string]\".local.part.with.rightbracket.in.String@domain.com"                  =   1 =  OK 
   *   649 - assertIsFalse "\"string\]\".local.part.with.escaped.rightbracket.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   650 - assertIsFalse "]@local.part.only.rightbracket.domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   651 - assertIsFalse "]]]]]]@local.part.only.consecutive.rightbracket.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   652 - assertIsFalse "].].].].].]@rightbracket.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   653 - assertIsFalse "name ] <pointy.brackets1.with.rightbracket.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   654 - assertIsFalse "<pointy.brackets2.with.rightbracket.in.display.name@domain.com> name ]"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   655 - assertIsFalse "domain.part@with]rightbracket.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   656 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   657 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   658 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   659 - assertIsFalse "domain.part@with.rightbracket.before].point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   660 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   661 - assertIsFalse "domain.part@with.consecutive.rightbracket]]test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   662 - assertIsFalse "domain.part.with.rightbracket.in.comment@(comment ])domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   663 - assertIsFalse "domain.part.only.rightbracket@].com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   664 - assertIsFalse "top.level.domain.only@rightbracket.]"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   665 - assertIsFalse "().local.part.starts.with.empty.bracket@domain.com"                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   666 - assertIsTrue  "local.part.ends.with.empty.bracket()@domain.com"                                =   6 =  OK 
   *   667 - assertIsFalse "local.part.with.empty.bracket()character@domain.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   668 - assertIsFalse "local.part.with.empty.bracket.before().point@domain.com"                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   669 - assertIsFalse "local.part.with.empty.bracket.after.()point@domain.com"                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *   670 - assertIsFalse "local.part.with.double.empty.bracket()()test@domain.com"                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *   671 - assertIsFalse "(comment ()) local.part.with.empty.bracket.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   672 - assertIsTrue  "\"string()\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   673 - assertIsFalse "\"string\()\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   674 - assertIsFalse "()@local.part.only.empty.bracket.domain.com"                                    =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *   675 - assertIsFalse "()()()()()()@local.part.only.consecutive.empty.bracket.domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *   676 - assertIsFalse "().().().().().()@empty.bracket.domain.com"                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   677 - assertIsTrue  "name () <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =   0 =  OK 
   *   678 - assertIsTrue  "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name ()"       =   0 =  OK 
   *   679 - assertIsFalse "domain.part@with()empty.bracket.com"                                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *   680 - assertIsTrue  "domain.part@()with.empty.bracket.at.domain.start.com"                           =   6 =  OK 
   *   681 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1().com"                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   682 - assertIsTrue  "domain.part@with.empty.bracket.at.domain.end2.com()"                            =   6 =  OK 
   *   683 - assertIsFalse "domain.part@with.empty.bracket.before().point.com"                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   684 - assertIsFalse "domain.part@with.empty.bracket.after.()point.com"                               = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   685 - assertIsFalse "domain.part@with.consecutive.empty.bracket()()test.com"                         = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *   686 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment ())domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   687 - assertIsFalse "domain.part.only.empty.bracket@().com"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *   688 - assertIsFalse "top.level.domain.only@empty.bracket.()"                                         = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *   689 - assertIsTrue  "{}.local.part.starts.with.empty.bracket@domain.com"                             =   0 =  OK 
   *   690 - assertIsTrue  "local.part.ends.with.empty.bracket{}@domain.com"                                =   0 =  OK 
   *   691 - assertIsTrue  "local.part.with.empty.bracket{}character@domain.com"                            =   0 =  OK 
   *   692 - assertIsTrue  "local.part.with.empty.bracket.before{}.point@domain.com"                        =   0 =  OK 
   *   693 - assertIsTrue  "local.part.with.empty.bracket.after.{}point@domain.com"                         =   0 =  OK 
   *   694 - assertIsTrue  "local.part.with.double.empty.bracket{}{}test@domain.com"                        =   0 =  OK 
   *   695 - assertIsTrue  "(comment {}) local.part.with.empty.bracket.in.comment@domain.com"               =   6 =  OK 
   *   696 - assertIsTrue  "\"string{}\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   697 - assertIsFalse "\"string\{}\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   698 - assertIsTrue  "{}@local.part.only.empty.bracket.domain.com"                                    =   0 =  OK 
   *   699 - assertIsTrue  "{}{}{}{}{}{}@local.part.only.consecutive.empty.bracket.domain.com"              =   0 =  OK 
   *   700 - assertIsTrue  "{}.{}.{}.{}.{}.{}@empty.bracket.domain.com"                                     =   0 =  OK 
   *   701 - assertIsFalse "name {} <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   702 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name {}"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   703 - assertIsFalse "domain.part@with{}empty.bracket.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   704 - assertIsFalse "domain.part@{}with.empty.bracket.at.domain.start.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   705 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1{}.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   706 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com{}"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   707 - assertIsFalse "domain.part@with.empty.bracket.before{}.point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   708 - assertIsFalse "domain.part@with.empty.bracket.after.{}point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   709 - assertIsFalse "domain.part@with.consecutive.empty.bracket{}{}test.com"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   710 - assertIsTrue  "domain.part.with.empty.bracket.in.comment@(comment {})domain.com"               =   6 =  OK 
   *   711 - assertIsFalse "domain.part.only.empty.bracket@{}.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   712 - assertIsFalse "top.level.domain.only@empty.bracket.{}"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   713 - assertIsFalse "[].local.part.starts.with.empty.bracket@domain.com"                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   714 - assertIsFalse "local.part.ends.with.empty.bracket[]@domain.com"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   715 - assertIsFalse "local.part.with.empty.bracket[]character@domain.com"                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   716 - assertIsFalse "local.part.with.empty.bracket.before[].point@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   717 - assertIsFalse "local.part.with.empty.bracket.after.[]point@domain.com"                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   718 - assertIsFalse "local.part.with.double.empty.bracket[][]test@domain.com"                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   719 - assertIsFalse "(comment []) local.part.with.empty.bracket.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   720 - assertIsTrue  "\"string[]\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   721 - assertIsFalse "\"string\[]\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   722 - assertIsFalse "[]@local.part.only.empty.bracket.domain.com"                                    =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   723 - assertIsFalse "[][][][][][]@local.part.only.consecutive.empty.bracket.domain.com"              =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   724 - assertIsFalse "[].[].[].[].[].[]@empty.bracket.domain.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *   725 - assertIsFalse "name [] <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   726 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name []"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   727 - assertIsFalse "domain.part@with[]empty.bracket.com"                                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   728 - assertIsFalse "domain.part@[]with.empty.bracket.at.domain.start.com"                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   729 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1[].com"                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   730 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com[]"                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   731 - assertIsFalse "domain.part@with.empty.bracket.before[].point.com"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   732 - assertIsFalse "domain.part@with.empty.bracket.after.[]point.com"                               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   733 - assertIsFalse "domain.part@with.consecutive.empty.bracket[][]test.com"                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   734 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment [])domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   735 - assertIsFalse "domain.part.only.empty.bracket@[].com"                                          =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *   736 - assertIsFalse "top.level.domain.only@empty.bracket.[]"                                         =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *   737 - assertIsFalse "<>.local.part.starts.with.empty.bracket@domain.com"                             =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   738 - assertIsFalse "local.part.ends.with.empty.bracket<>@domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   739 - assertIsFalse "local.part.with.empty.bracket<>character@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   740 - assertIsFalse "local.part.with.empty.bracket.before<>.point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   741 - assertIsFalse "local.part.with.empty.bracket.after.<>point@domain.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   742 - assertIsFalse "local.part.with.double.empty.bracket<><>test@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   743 - assertIsFalse "(comment <>) local.part.with.empty.bracket.in.comment@domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   744 - assertIsTrue  "\"string<>\".local.part.with.empty.bracket.in.String@domain.com"                =   1 =  OK 
   *   745 - assertIsFalse "\"string\<>\".local.part.with.escaped.empty.bracket.in.String@domain.com"       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   746 - assertIsFalse "<>@local.part.only.empty.bracket.domain.com"                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   747 - assertIsFalse "<><><><><><>@local.part.only.consecutive.empty.bracket.domain.com"              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   748 - assertIsFalse "<>.<>.<>.<>.<>.<>@empty.bracket.domain.com"                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   749 - assertIsFalse "name <> <pointy.brackets1.with.empty.bracket.in.display.name@domain.com>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   750 - assertIsFalse "<pointy.brackets2.with.empty.bracket.in.display.name@domain.com> name <>"       =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   751 - assertIsFalse "domain.part@with<>empty.bracket.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   752 - assertIsFalse "domain.part@<>with.empty.bracket.at.domain.start.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   753 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end1<>.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   754 - assertIsFalse "domain.part@with.empty.bracket.at.domain.end2.com<>"                            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   755 - assertIsFalse "domain.part@with.empty.bracket.before<>.point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   756 - assertIsFalse "domain.part@with.empty.bracket.after.<>point.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   757 - assertIsFalse "domain.part@with.consecutive.empty.bracket<><>test.com"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   758 - assertIsFalse "domain.part.with.empty.bracket.in.comment@(comment <>)domain.com"               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   759 - assertIsFalse "domain.part.only.empty.bracket@<>.com"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   760 - assertIsFalse "top.level.domain.only@empty.bracket.<>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   761 - assertIsFalse ")(.local.part.starts.with.false.bracket1@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   762 - assertIsFalse "local.part.ends.with.false.bracket1)(@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   763 - assertIsFalse "local.part.with.false.bracket1)(character@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   764 - assertIsFalse "local.part.with.false.bracket1.before)(.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   765 - assertIsFalse "local.part.with.false.bracket1.after.)(point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   766 - assertIsFalse "local.part.with.double.false.bracket1)()(test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   767 - assertIsFalse "(comment )() local.part.with.false.bracket1.in.comment@domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *   768 - assertIsTrue  "\"string)(\".local.part.with.false.bracket1.in.String@domain.com"               =   1 =  OK 
   *   769 - assertIsFalse "\"string\)(\".local.part.with.escaped.false.bracket1.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   770 - assertIsFalse ")(@local.part.only.false.bracket1.domain.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   771 - assertIsFalse ")()()()()()(@local.part.only.consecutive.false.bracket1.domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   772 - assertIsFalse ")(.)(.)(.)(.)(.)(@false.bracket1.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   773 - assertIsTrue  "name )( <pointy.brackets1.with.false.bracket1.in.display.name@domain.com>"      =   0 =  OK 
   *   774 - assertIsTrue  "<pointy.brackets2.with.false.bracket1.in.display.name@domain.com> name )("      =   0 =  OK 
   *   775 - assertIsFalse "domain.part@with)(false.bracket1.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   776 - assertIsFalse "domain.part@)(with.false.bracket1.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   777 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end1)(.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   778 - assertIsFalse "domain.part@with.false.bracket1.at.domain.end2.com)("                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   779 - assertIsFalse "domain.part@with.false.bracket1.before)(.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   780 - assertIsFalse "domain.part@with.false.bracket1.after.)(point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   781 - assertIsFalse "domain.part@with.consecutive.false.bracket1)()(test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   782 - assertIsFalse "domain.part.with.false.bracket1.in.comment@(comment )()domain.com"              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *   783 - assertIsFalse "domain.part.only.false.bracket1@)(.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   784 - assertIsFalse "top.level.domain.only@false.bracket1.)("                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   785 - assertIsTrue  "}{.local.part.starts.with.false.bracket2@domain.com"                            =   0 =  OK 
   *   786 - assertIsTrue  "local.part.ends.with.false.bracket2}{@domain.com"                               =   0 =  OK 
   *   787 - assertIsTrue  "local.part.with.false.bracket2}{character@domain.com"                           =   0 =  OK 
   *   788 - assertIsTrue  "local.part.with.false.bracket2.before}{.point@domain.com"                       =   0 =  OK 
   *   789 - assertIsTrue  "local.part.with.false.bracket2.after.}{point@domain.com"                        =   0 =  OK 
   *   790 - assertIsTrue  "local.part.with.double.false.bracket2}{}{test@domain.com"                       =   0 =  OK 
   *   791 - assertIsTrue  "(comment }{) local.part.with.false.bracket2.in.comment@domain.com"              =   6 =  OK 
   *   792 - assertIsTrue  "\"string}{\".local.part.with.false.bracket2.in.String@domain.com"               =   1 =  OK 
   *   793 - assertIsFalse "\"string\}{\".local.part.with.escaped.false.bracket2.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   794 - assertIsTrue  "}{@local.part.only.false.bracket2.domain.com"                                   =   0 =  OK 
   *   795 - assertIsTrue  "}{}{}{}{}{}{@local.part.only.consecutive.false.bracket2.domain.com"             =   0 =  OK 
   *   796 - assertIsTrue  "}{.}{.}{.}{.}{.}{@false.bracket2.domain.com"                                    =   0 =  OK 
   *   797 - assertIsFalse "name }{ <pointy.brackets1.with.false.bracket2.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   798 - assertIsFalse "<pointy.brackets2.with.false.bracket2.in.display.name@domain.com> name }{"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   799 - assertIsFalse "domain.part@with}{false.bracket2.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   800 - assertIsFalse "domain.part@}{with.false.bracket2.at.domain.start.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   801 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end1}{.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   802 - assertIsFalse "domain.part@with.false.bracket2.at.domain.end2.com}{"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   803 - assertIsFalse "domain.part@with.false.bracket2.before}{.point.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   804 - assertIsFalse "domain.part@with.false.bracket2.after.}{point.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   805 - assertIsFalse "domain.part@with.consecutive.false.bracket2}{}{test.com"                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   806 - assertIsTrue  "domain.part.with.false.bracket2.in.comment@(comment }{)domain.com"              =   6 =  OK 
   *   807 - assertIsFalse "domain.part.only.false.bracket2@}{.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   808 - assertIsFalse "top.level.domain.only@false.bracket2.}{"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   809 - assertIsFalse "][.local.part.starts.with.false.bracket3@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   810 - assertIsFalse "local.part.ends.with.false.bracket3][@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   811 - assertIsFalse "local.part.with.false.bracket3][character@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   812 - assertIsFalse "local.part.with.false.bracket3.before][.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   813 - assertIsFalse "local.part.with.false.bracket3.after.][point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   814 - assertIsFalse "local.part.with.double.false.bracket3][][test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   815 - assertIsFalse "(comment ][) local.part.with.false.bracket3.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   816 - assertIsTrue  "\"string][\".local.part.with.false.bracket3.in.String@domain.com"               =   1 =  OK 
   *   817 - assertIsFalse "\"string\][\".local.part.with.escaped.false.bracket3.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   818 - assertIsFalse "][@local.part.only.false.bracket3.domain.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   819 - assertIsFalse "][][][][][][@local.part.only.consecutive.false.bracket3.domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   820 - assertIsFalse "][.][.][.][.][.][@false.bracket3.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   821 - assertIsFalse "name ][ <pointy.brackets1.with.false.bracket3.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   822 - assertIsFalse "<pointy.brackets2.with.false.bracket3.in.display.name@domain.com> name ]["      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   823 - assertIsFalse "domain.part@with][false.bracket3.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   824 - assertIsFalse "domain.part@][with.false.bracket3.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   825 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end1][.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   826 - assertIsFalse "domain.part@with.false.bracket3.at.domain.end2.com]["                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   827 - assertIsFalse "domain.part@with.false.bracket3.before][.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   828 - assertIsFalse "domain.part@with.false.bracket3.after.][point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   829 - assertIsFalse "domain.part@with.consecutive.false.bracket3][][test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   830 - assertIsFalse "domain.part.with.false.bracket3.in.comment@(comment ][)domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   831 - assertIsFalse "domain.part.only.false.bracket3@][.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   832 - assertIsFalse "top.level.domain.only@false.bracket3.]["                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   833 - assertIsFalse "><.local.part.starts.with.false.bracket4@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   834 - assertIsFalse "local.part.ends.with.false.bracket4><@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   835 - assertIsFalse "local.part.with.false.bracket4><character@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   836 - assertIsFalse "local.part.with.false.bracket4.before><.point@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   837 - assertIsFalse "local.part.with.false.bracket4.after.><point@domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   838 - assertIsFalse "local.part.with.double.false.bracket4><><test@domain.com"                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   839 - assertIsFalse "(comment ><) local.part.with.false.bracket4.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   840 - assertIsTrue  "\"string><\".local.part.with.false.bracket4.in.String@domain.com"               =   1 =  OK 
   *   841 - assertIsFalse "\"string\><\".local.part.with.escaped.false.bracket4.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   842 - assertIsFalse "><@local.part.only.false.bracket4.domain.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   843 - assertIsFalse "><><><><><><@local.part.only.consecutive.false.bracket4.domain.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   844 - assertIsFalse "><.><.><.><.><.><@false.bracket4.domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   845 - assertIsFalse "name >< <pointy.brackets1.with.false.bracket4.in.display.name@domain.com>"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   846 - assertIsFalse "<pointy.brackets2.with.false.bracket4.in.display.name@domain.com> name ><"      =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   847 - assertIsFalse "domain.part@with><false.bracket4.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   848 - assertIsFalse "domain.part@><with.false.bracket4.at.domain.start.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   849 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end1><.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   850 - assertIsFalse "domain.part@with.false.bracket4.at.domain.end2.com><"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   851 - assertIsFalse "domain.part@with.false.bracket4.before><.point.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   852 - assertIsFalse "domain.part@with.false.bracket4.after.><point.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   853 - assertIsFalse "domain.part@with.consecutive.false.bracket4><><test.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   854 - assertIsFalse "domain.part.with.false.bracket4.in.comment@(comment ><)domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   855 - assertIsFalse "domain.part.only.false.bracket4@><.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   856 - assertIsFalse "top.level.domain.only@false.bracket4.><"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   857 - assertIsFalse "<.local.part.starts.with.lower.than@domain.com"                                 =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   858 - assertIsFalse "local.part.ends.with.lower.than<@domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   859 - assertIsFalse "local.part.with.lower.than<character@domain.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   860 - assertIsFalse "local.part.with.lower.than.before<.point@domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   861 - assertIsFalse "local.part.with.lower.than.after.<point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   862 - assertIsFalse "local.part.with.double.lower.than<<test@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   863 - assertIsFalse "(comment <) local.part.with.lower.than.in.comment@domain.com"                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   864 - assertIsTrue  "\"string<\".local.part.with.lower.than.in.String@domain.com"                    =   1 =  OK 
   *   865 - assertIsFalse "\"string\<\".local.part.with.escaped.lower.than.in.String@domain.com"           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   866 - assertIsFalse "<@local.part.only.lower.than.domain.com"                                        =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   867 - assertIsFalse "<<<<<<@local.part.only.consecutive.lower.than.domain.com"                       =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   868 - assertIsFalse "<.<.<.<.<.<@lower.than.domain.com"                                              =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *   869 - assertIsFalse "name < <pointy.brackets1.with.lower.than.in.display.name@domain.com>"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   870 - assertIsFalse "<pointy.brackets2.with.lower.than.in.display.name@domain.com> name <"           =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   871 - assertIsFalse "domain.part@with<lower.than.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   872 - assertIsFalse "domain.part@<with.lower.than.at.domain.start.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   873 - assertIsFalse "domain.part@with.lower.than.at.domain.end1<.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   874 - assertIsFalse "domain.part@with.lower.than.at.domain.end2.com<"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   875 - assertIsFalse "domain.part@with.lower.than.before<.point.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   876 - assertIsFalse "domain.part@with.lower.than.after.<point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   877 - assertIsFalse "domain.part@with.consecutive.lower.than<<test.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   878 - assertIsFalse "domain.part.with.lower.than.in.comment@(comment <)domain.com"                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   879 - assertIsFalse "domain.part.only.lower.than@<.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   880 - assertIsFalse "top.level.domain.only@lower.than.<"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   881 - assertIsFalse ">.local.part.starts.with.greater.than@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   882 - assertIsFalse "local.part.ends.with.greater.than>@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   883 - assertIsFalse "local.part.with.greater.than>character@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   884 - assertIsFalse "local.part.with.greater.than.before>.point@domain.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   885 - assertIsFalse "local.part.with.greater.than.after.>point@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   886 - assertIsFalse "local.part.with.double.greater.than>>test@domain.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   887 - assertIsFalse "(comment >) local.part.with.greater.than.in.comment@domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   888 - assertIsTrue  "\"string>\".local.part.with.greater.than.in.String@domain.com"                  =   1 =  OK 
   *   889 - assertIsFalse "\"string\>\".local.part.with.escaped.greater.than.in.String@domain.com"         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   890 - assertIsFalse ">@local.part.only.greater.than.domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   891 - assertIsFalse ">>>>>>@local.part.only.consecutive.greater.than.domain.com"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   892 - assertIsFalse ">.>.>.>.>.>@greater.than.domain.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   893 - assertIsFalse "name > <pointy.brackets1.with.greater.than.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   894 - assertIsFalse "<pointy.brackets2.with.greater.than.in.display.name@domain.com> name >"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   895 - assertIsFalse "domain.part@with>greater.than.com"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   896 - assertIsFalse "domain.part@>with.greater.than.at.domain.start.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   897 - assertIsFalse "domain.part@with.greater.than.at.domain.end1>.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   898 - assertIsFalse "domain.part@with.greater.than.at.domain.end2.com>"                              =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *   899 - assertIsFalse "domain.part@with.greater.than.before>.point.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   900 - assertIsFalse "domain.part@with.greater.than.after.>point.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   901 - assertIsFalse "domain.part@with.consecutive.greater.than>>test.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   902 - assertIsFalse "domain.part.with.greater.than.in.comment@(comment >)domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   903 - assertIsFalse "domain.part.only.greater.than@>.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   904 - assertIsFalse "top.level.domain.only@greater.than.>"                                           =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *   905 - assertIsTrue  "~.local.part.starts.with.tilde@domain.com"                                      =   0 =  OK 
   *   906 - assertIsTrue  "local.part.ends.with.tilde~@domain.com"                                         =   0 =  OK 
   *   907 - assertIsTrue  "local.part.with.tilde~character@domain.com"                                     =   0 =  OK 
   *   908 - assertIsTrue  "local.part.with.tilde.before~.point@domain.com"                                 =   0 =  OK 
   *   909 - assertIsTrue  "local.part.with.tilde.after.~point@domain.com"                                  =   0 =  OK 
   *   910 - assertIsTrue  "local.part.with.double.tilde~~test@domain.com"                                  =   0 =  OK 
   *   911 - assertIsTrue  "(comment ~) local.part.with.tilde.in.comment@domain.com"                        =   6 =  OK 
   *   912 - assertIsTrue  "\"string~\".local.part.with.tilde.in.String@domain.com"                         =   1 =  OK 
   *   913 - assertIsFalse "\"string\~\".local.part.with.escaped.tilde.in.String@domain.com"                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   914 - assertIsTrue  "~@local.part.only.tilde.domain.com"                                             =   0 =  OK 
   *   915 - assertIsTrue  "~~~~~~@local.part.only.consecutive.tilde.domain.com"                            =   0 =  OK 
   *   916 - assertIsTrue  "~.~.~.~.~.~@tilde.domain.com"                                                   =   0 =  OK 
   *   917 - assertIsFalse "name ~ <pointy.brackets1.with.tilde.in.display.name@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   918 - assertIsFalse "<pointy.brackets2.with.tilde.in.display.name@domain.com> name ~"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   919 - assertIsFalse "domain.part@with~tilde.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   920 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   921 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   922 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   923 - assertIsFalse "domain.part@with.tilde.before~.point.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   924 - assertIsFalse "domain.part@with.tilde.after.~point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   925 - assertIsFalse "domain.part@with.consecutive.tilde~~test.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   926 - assertIsTrue  "domain.part.with.tilde.in.comment@(comment ~)domain.com"                        =   6 =  OK 
   *   927 - assertIsFalse "domain.part.only.tilde@~.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   928 - assertIsFalse "top.level.domain.only@tilde.~"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   929 - assertIsTrue  "^.local.part.starts.with.xor@domain.com"                                        =   0 =  OK 
   *   930 - assertIsTrue  "local.part.ends.with.xor^@domain.com"                                           =   0 =  OK 
   *   931 - assertIsTrue  "local.part.with.xor^character@domain.com"                                       =   0 =  OK 
   *   932 - assertIsTrue  "local.part.with.xor.before^.point@domain.com"                                   =   0 =  OK 
   *   933 - assertIsTrue  "local.part.with.xor.after.^point@domain.com"                                    =   0 =  OK 
   *   934 - assertIsTrue  "local.part.with.double.xor^^test@domain.com"                                    =   0 =  OK 
   *   935 - assertIsTrue  "(comment ^) local.part.with.xor.in.comment@domain.com"                          =   6 =  OK 
   *   936 - assertIsTrue  "\"string^\".local.part.with.xor.in.String@domain.com"                           =   1 =  OK 
   *   937 - assertIsFalse "\"string\^\".local.part.with.escaped.xor.in.String@domain.com"                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   938 - assertIsTrue  "^@local.part.only.xor.domain.com"                                               =   0 =  OK 
   *   939 - assertIsTrue  "^^^^^^@local.part.only.consecutive.xor.domain.com"                              =   0 =  OK 
   *   940 - assertIsTrue  "^.^.^.^.^.^@xor.domain.com"                                                     =   0 =  OK 
   *   941 - assertIsFalse "name ^ <pointy.brackets1.with.xor.in.display.name@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   942 - assertIsFalse "<pointy.brackets2.with.xor.in.display.name@domain.com> name ^"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   943 - assertIsFalse "domain.part@with^xor.com"                                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   944 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   945 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   946 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   947 - assertIsFalse "domain.part@with.xor.before^.point.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   948 - assertIsFalse "domain.part@with.xor.after.^point.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   949 - assertIsFalse "domain.part@with.consecutive.xor^^test.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   950 - assertIsTrue  "domain.part.with.xor.in.comment@(comment ^)domain.com"                          =   6 =  OK 
   *   951 - assertIsFalse "domain.part.only.xor@^.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   952 - assertIsFalse "top.level.domain.only@xor.^"                                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *   953 - assertIsFalse ":.local.part.starts.with.colon@domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   954 - assertIsFalse "local.part.ends.with.colon:@domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   955 - assertIsFalse "local.part.with.colon:character@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   956 - assertIsFalse "local.part.with.colon.before:.point@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   957 - assertIsFalse "local.part.with.colon.after.:point@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   958 - assertIsFalse "local.part.with.double.colon::test@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   959 - assertIsFalse "(comment :) local.part.with.colon.in.comment@domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   960 - assertIsTrue  "\"string:\".local.part.with.colon.in.String@domain.com"                         =   1 =  OK 
   *   961 - assertIsFalse "\"string\:\".local.part.with.escaped.colon.in.String@domain.com"                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *   962 - assertIsFalse ":@local.part.only.colon.domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   963 - assertIsFalse "::::::@local.part.only.consecutive.colon.domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   964 - assertIsFalse ":.:.:.:.:.:@colon.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   965 - assertIsFalse "name : <pointy.brackets1.with.colon.in.display.name@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   966 - assertIsFalse "<pointy.brackets2.with.colon.in.display.name@domain.com> name :"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *   967 - assertIsFalse "domain.part@with:colon.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   968 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   969 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   970 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   971 - assertIsFalse "domain.part@with.colon.before:.point.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   972 - assertIsFalse "domain.part@with.colon.after.:point.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   973 - assertIsFalse "domain.part@with.consecutive.colon::test.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   974 - assertIsFalse "domain.part.with.colon.in.comment@(comment :)domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *   975 - assertIsFalse "domain.part.only.colon@:.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   976 - assertIsFalse "top.level.domain.only@colon.:"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   977 - assertIsFalse " .local.part.starts.with.space@domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   978 - assertIsFalse "local.part.ends.with.space @domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   979 - assertIsFalse "local.part.with.space character@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   980 - assertIsFalse "local.part.with.space.before .point@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   981 - assertIsFalse "local.part.with.space.after. point@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   982 - assertIsFalse "local.part.with.double.space  test@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   983 - assertIsTrue  "(comment  ) local.part.with.space.in.comment@domain.com"                        =   6 =  OK 
   *   984 - assertIsTrue  "\"string \".local.part.with.space.in.String@domain.com"                         =   1 =  OK 
   *   985 - assertIsTrue  "\"string\ \".local.part.with.escaped.space.in.String@domain.com"                =   1 =  OK 
   *   986 - assertIsFalse " @local.part.only.space.domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   987 - assertIsFalse "      @local.part.only.consecutive.space.domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   988 - assertIsFalse " . . . . . @space.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   989 - assertIsTrue  "name   <pointy.brackets1.with.space.in.display.name@domain.com>"                =   0 =  OK 
   *   990 - assertIsTrue  "<pointy.brackets2.with.space.in.display.name@domain.com> name  "                =   0 =  OK 
   *   991 - assertIsFalse "domain.part@with space.com"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   992 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   993 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   994 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *   995 - assertIsFalse "domain.part@with.space.before .point.com"                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   996 - assertIsFalse "domain.part@with.space.after. point.com"                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   997 - assertIsFalse "domain.part@with.consecutive.space  test.com"                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *   998 - assertIsTrue  "domain.part.with.space.in.comment@(comment  )domain.com"                        =   6 =  OK 
   *   999 - assertIsFalse "domain.part.only.space@ .com"                                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  1000 - assertIsFalse "top.level.domain.only@space. "                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1001 - assertIsFalse ",.local.part.starts.with.comma@domain.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1002 - assertIsFalse "local.part.ends.with.comma,@domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1003 - assertIsFalse "local.part.with.comma,character@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1004 - assertIsFalse "local.part.with.comma.before,.point@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1005 - assertIsFalse "local.part.with.comma.after.,point@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1006 - assertIsFalse "local.part.with.double.comma,,test@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1007 - assertIsFalse "(comment ,) local.part.with.comma.in.comment@domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1008 - assertIsTrue  "\"string,\".local.part.with.comma.in.String@domain.com"                         =   1 =  OK 
   *  1009 - assertIsFalse "\"string\,\".local.part.with.escaped.comma.in.String@domain.com"                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1010 - assertIsFalse ",@local.part.only.comma.domain.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1011 - assertIsFalse ",,,,,,@local.part.only.consecutive.comma.domain.com"                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1012 - assertIsFalse ",.,.,.,.,.,@comma.domain.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1013 - assertIsFalse "name , <pointy.brackets1.with.comma.in.display.name@domain.com>"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1014 - assertIsFalse "<pointy.brackets2.with.comma.in.display.name@domain.com> name ,"                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1015 - assertIsFalse "domain.part@with,comma.com"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1016 - assertIsFalse "domain.part@,with.comma.at.domain.start.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1017 - assertIsFalse "domain.part@with.comma.at.domain.end1,.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1018 - assertIsFalse "domain.part@with.comma.at.domain.end2.com,"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1019 - assertIsFalse "domain.part@with.comma.before,.point.com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1020 - assertIsFalse "domain.part@with.comma.after.,point.com"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1021 - assertIsFalse "domain.part@with.consecutive.comma,,test.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1022 - assertIsFalse "domain.part.with.comma.in.comment@(comment ,)domain.com"                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1023 - assertIsFalse "domain.part.only.comma@,.com"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1024 - assertIsFalse "top.level.domain.only@comma.,"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1025 - assertIsFalse "@.local.part.starts.with.at@domain.com"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1026 - assertIsFalse "local.part.ends.with.at@@domain.com"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1027 - assertIsFalse "local.part.with.at@character@domain.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1028 - assertIsFalse "local.part.with.at.before@.point@domain.com"                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1029 - assertIsFalse "local.part.with.at.after.@point@domain.com"                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  1030 - assertIsFalse "local.part.with.double.at@@test@domain.com"                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1031 - assertIsTrue  "(comment @) local.part.with.at.in.comment@domain.com"                           =   6 =  OK 
   *  1032 - assertIsTrue  "\"string@\".local.part.with.at.in.String@domain.com"                            =   1 =  OK 
   *  1033 - assertIsTrue  "\"string\@\".local.part.with.escaped.at.in.String@domain.com"                   =   1 =  OK 
   *  1034 - assertIsFalse "@@local.part.only.at.domain.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1035 - assertIsFalse "@@@@@@@local.part.only.consecutive.at.domain.com"                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1036 - assertIsFalse "@.@.@.@.@.@@at.domain.com"                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  1037 - assertIsFalse "name @ <pointy.brackets1.with.at.in.display.name@domain.com>"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1038 - assertIsFalse "<pointy.brackets2.with.at.in.display.name@domain.com> name @"                   =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1039 - assertIsFalse "domain.part@with@at.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1040 - assertIsFalse "domain.part@@with.at.at.domain.start.com"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1041 - assertIsFalse "domain.part@with.at.at.domain.end1@.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1042 - assertIsFalse "domain.part@with.at.at.domain.end2.com@"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1043 - assertIsFalse "domain.part@with.at.before@.point.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1044 - assertIsFalse "domain.part@with.at.after.@point.com"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1045 - assertIsFalse "domain.part@with.consecutive.at@@test.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1046 - assertIsTrue  "domain.part.with.at.in.comment@(comment @)domain.com"                           =   6 =  OK 
   *  1047 - assertIsFalse "domain.part.only.at@@.com"                                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1048 - assertIsFalse "top.level.domain.only@at.@"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1049 - assertIsFalse "§.local.part.starts.with.paragraph@domain.com"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1050 - assertIsFalse "local.part.ends.with.paragraph§@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1051 - assertIsFalse "local.part.with.paragraph§character@domain.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1052 - assertIsFalse "local.part.with.paragraph.before§.point@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1053 - assertIsFalse "local.part.with.paragraph.after.§point@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1054 - assertIsFalse "local.part.with.double.paragraph§§test@domain.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1055 - assertIsFalse "(comment §) local.part.with.paragraph.in.comment@domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1056 - assertIsFalse "\"string§\".local.part.with.paragraph.in.String@domain.com"                     =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  1057 - assertIsFalse "\"string\§\".local.part.with.escaped.paragraph.in.String@domain.com"            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1058 - assertIsFalse "§@local.part.only.paragraph.domain.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1059 - assertIsFalse "§§§§§§@local.part.only.consecutive.paragraph.domain.com"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1060 - assertIsFalse "§.§.§.§.§.§@paragraph.domain.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1061 - assertIsFalse "name § <pointy.brackets1.with.paragraph.in.display.name@domain.com>"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1062 - assertIsFalse "<pointy.brackets2.with.paragraph.in.display.name@domain.com> name §"            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1063 - assertIsFalse "domain.part@with§paragraph.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1064 - assertIsFalse "domain.part@§with.paragraph.at.domain.start.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1065 - assertIsFalse "domain.part@with.paragraph.at.domain.end1§.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1066 - assertIsFalse "domain.part@with.paragraph.at.domain.end2.com§"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1067 - assertIsFalse "domain.part@with.paragraph.before§.point.com"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1068 - assertIsFalse "domain.part@with.paragraph.after.§point.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1069 - assertIsFalse "domain.part@with.consecutive.paragraph§§test.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1070 - assertIsFalse "domain.part.with.paragraph.in.comment@(comment §)domain.com"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1071 - assertIsFalse "domain.part.only.paragraph@§.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1072 - assertIsFalse "top.level.domain.only@paragraph.§"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1073 - assertIsTrue  "'.local.part.starts.with.double.quote@domain.com"                               =   0 =  OK 
   *  1074 - assertIsTrue  "local.part.ends.with.double.quote'@domain.com"                                  =   0 =  OK 
   *  1075 - assertIsTrue  "local.part.with.double.quote'character@domain.com"                              =   0 =  OK 
   *  1076 - assertIsTrue  "local.part.with.double.quote.before'.point@domain.com"                          =   0 =  OK 
   *  1077 - assertIsTrue  "local.part.with.double.quote.after.'point@domain.com"                           =   0 =  OK 
   *  1078 - assertIsTrue  "local.part.with.double.double.quote''test@domain.com"                           =   0 =  OK 
   *  1079 - assertIsTrue  "(comment ') local.part.with.double.quote.in.comment@domain.com"                 =   6 =  OK 
   *  1080 - assertIsTrue  "\"string'\".local.part.with.double.quote.in.String@domain.com"                  =   1 =  OK 
   *  1081 - assertIsTrue  "\"string\'\".local.part.with.escaped.double.quote.in.String@domain.com"         =   1 =  OK 
   *  1082 - assertIsTrue  "'@local.part.only.double.quote.domain.com"                                      =   0 =  OK 
   *  1083 - assertIsTrue  "''''''@local.part.only.consecutive.double.quote.domain.com"                     =   0 =  OK 
   *  1084 - assertIsTrue  "'.'.'.'.'.'@double.quote.domain.com"                                            =   0 =  OK 
   *  1085 - assertIsFalse "name ' <pointy.brackets1.with.double.quote.in.display.name@domain.com>"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1086 - assertIsFalse "<pointy.brackets2.with.double.quote.in.display.name@domain.com> name '"         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1087 - assertIsFalse "domain.part@with'double.quote.com"                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1088 - assertIsFalse "domain.part@'with.double.quote.at.domain.start.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1089 - assertIsFalse "domain.part@with.double.quote.at.domain.end1'.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1090 - assertIsFalse "domain.part@with.double.quote.at.domain.end2.com'"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1091 - assertIsFalse "domain.part@with.double.quote.before'.point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1092 - assertIsFalse "domain.part@with.double.quote.after.'point.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1093 - assertIsFalse "domain.part@with.consecutive.double.quote''test.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1094 - assertIsTrue  "domain.part.with.double.quote.in.comment@(comment ')domain.com"                 =   6 =  OK 
   *  1095 - assertIsFalse "domain.part.only.double.quote@'.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1096 - assertIsFalse "top.level.domain.only@double.quote.'"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1097 - assertIsTrue  "/.local.part.starts.with.forward.slash@domain.com"                              =   0 =  OK 
   *  1098 - assertIsTrue  "local.part.ends.with.forward.slash/@domain.com"                                 =   0 =  OK 
   *  1099 - assertIsTrue  "local.part.with.forward.slash/character@domain.com"                             =   0 =  OK 
   *  1100 - assertIsTrue  "local.part.with.forward.slash.before/.point@domain.com"                         =   0 =  OK 
   *  1101 - assertIsTrue  "local.part.with.forward.slash.after./point@domain.com"                          =   0 =  OK 
   *  1102 - assertIsTrue  "local.part.with.double.forward.slash//test@domain.com"                          =   0 =  OK 
   *  1103 - assertIsTrue  "(comment /) local.part.with.forward.slash.in.comment@domain.com"                =   6 =  OK 
   *  1104 - assertIsTrue  "\"string/\".local.part.with.forward.slash.in.String@domain.com"                 =   1 =  OK 
   *  1105 - assertIsFalse "\"string\/\".local.part.with.escaped.forward.slash.in.String@domain.com"        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1106 - assertIsTrue  "/@local.part.only.forward.slash.domain.com"                                     =   0 =  OK 
   *  1107 - assertIsTrue  "//////@local.part.only.consecutive.forward.slash.domain.com"                    =   0 =  OK 
   *  1108 - assertIsTrue  "/./././././@forward.slash.domain.com"                                           =   0 =  OK 
   *  1109 - assertIsFalse "name / <pointy.brackets1.with.forward.slash.in.display.name@domain.com>"        =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1110 - assertIsFalse "<pointy.brackets2.with.forward.slash.in.display.name@domain.com> name /"        =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1111 - assertIsFalse "domain.part@with/forward.slash.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1112 - assertIsFalse "domain.part@/with.forward.slash.at.domain.start.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1113 - assertIsFalse "domain.part@with.forward.slash.at.domain.end1/.com"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1114 - assertIsFalse "domain.part@with.forward.slash.at.domain.end2.com/"                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1115 - assertIsFalse "domain.part@with.forward.slash.before/.point.com"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1116 - assertIsFalse "domain.part@with.forward.slash.after./point.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1117 - assertIsFalse "domain.part@with.consecutive.forward.slash//test.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1118 - assertIsTrue  "domain.part.with.forward.slash.in.comment@(comment /)domain.com"                =   6 =  OK 
   *  1119 - assertIsFalse "domain.part.only.forward.slash@/.com"                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1120 - assertIsFalse "top.level.domain.only@forward.slash./"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1121 - assertIsTrue  "-.local.part.starts.with.hyphen@domain.com"                                     =   0 =  OK 
   *  1122 - assertIsTrue  "local.part.ends.with.hyphen-@domain.com"                                        =   0 =  OK 
   *  1123 - assertIsTrue  "local.part.with.hyphen-character@domain.com"                                    =   0 =  OK 
   *  1124 - assertIsTrue  "local.part.with.hyphen.before-.point@domain.com"                                =   0 =  OK 
   *  1125 - assertIsTrue  "local.part.with.hyphen.after.-point@domain.com"                                 =   0 =  OK 
   *  1126 - assertIsTrue  "local.part.with.double.hyphen--test@domain.com"                                 =   0 =  OK 
   *  1127 - assertIsTrue  "(comment -) local.part.with.hyphen.in.comment@domain.com"                       =   6 =  OK 
   *  1128 - assertIsTrue  "\"string-\".local.part.with.hyphen.in.String@domain.com"                        =   1 =  OK 
   *  1129 - assertIsFalse "\"string\-\".local.part.with.escaped.hyphen.in.String@domain.com"               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1130 - assertIsTrue  "-@local.part.only.hyphen.domain.com"                                            =   0 =  OK 
   *  1131 - assertIsTrue  "------@local.part.only.consecutive.hyphen.domain.com"                           =   0 =  OK 
   *  1132 - assertIsTrue  "-.-.-.-.-.-@hyphen.domain.com"                                                  =   0 =  OK 
   *  1133 - assertIsFalse "name - <pointy.brackets1.with.hyphen.in.display.name@domain.com>"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1134 - assertIsFalse "<pointy.brackets2.with.hyphen.in.display.name@domain.com> name -"               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1135 - assertIsTrue  "domain.part@with-hyphen.com"                                                    =   0 =  OK 
   *  1136 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1137 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1138 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  1139 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1140 - assertIsFalse "domain.part@with.hyphen.after.-point.com"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1141 - assertIsTrue  "domain.part@with.consecutive.hyphen--test.com"                                  =   0 =  OK 
   *  1142 - assertIsTrue  "domain.part.with.hyphen.in.comment@(comment -)domain.com"                       =   6 =  OK 
   *  1143 - assertIsFalse "domain.part.only.hyphen@-.com"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1144 - assertIsFalse "top.level.domain.only@hyphen.-"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1145 - assertIsFalse "\"\".local.part.starts.with.empty.string1@domain.com"                           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1146 - assertIsFalse "local.part.ends.with.empty.string1\"\"@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1147 - assertIsFalse "local.part.with.empty.string1\"\"character@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1148 - assertIsFalse "local.part.with.empty.string1.before\"\".point@domain.com"                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1149 - assertIsFalse "local.part.with.empty.string1.after.\"\"point@domain.com"                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1150 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"test@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1151 - assertIsFalse "(comment \"\") local.part.with.empty.string1.in.comment@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1152 - assertIsFalse "\"string\"\"\".local.part.with.empty.string1.in.String@domain.com"              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1153 - assertIsFalse "\"string\\"\"\".local.part.with.escaped.empty.string1.in.String@domain.com"     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1154 - assertIsFalse "\"\"@local.part.only.empty.string1.domain.com"                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1155 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.empty.string1.domain.com"  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1156 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\"@empty.string1.domain.com"                         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1157 - assertIsTrue  "name \"\" <pointy.brackets1.with.empty.string1.in.display.name@domain.com>"     =   0 =  OK 
   *  1158 - assertIsTrue  "<pointy.brackets2.with.empty.string1.in.display.name@domain.com> name \"\""     =   0 =  OK 
   *  1159 - assertIsFalse "domain.part@with\"\"empty.string1.com"                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1160 - assertIsFalse "domain.part@\"\"with.empty.string1.at.domain.start.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1161 - assertIsFalse "domain.part@with.empty.string1.at.domain.end1\"\".com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1162 - assertIsFalse "domain.part@with.empty.string1.at.domain.end2.com\"\""                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1163 - assertIsFalse "domain.part@with.empty.string1.before\"\".point.com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1164 - assertIsFalse "domain.part@with.empty.string1.after.\"\"point.com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1165 - assertIsFalse "domain.part@with.consecutive.empty.string1\"\"\"\"test.com"                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1166 - assertIsFalse "domain.part.with.empty.string1.in.comment@(comment \"\")domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1167 - assertIsFalse "domain.part.only.empty.string1@\"\".com"                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1168 - assertIsFalse "top.level.domain.only@empty.string1.\"\""                                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1169 - assertIsFalse "a\"\"b.local.part.starts.with.empty.string2@domain.com"                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1170 - assertIsFalse "local.part.ends.with.empty.string2a\"\"b@domain.com"                            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1171 - assertIsFalse "local.part.with.empty.string2a\"\"bcharacter@domain.com"                        =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1172 - assertIsFalse "local.part.with.empty.string2.beforea\"\"b.point@domain.com"                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1173 - assertIsFalse "local.part.with.empty.string2.after.a\"\"bpoint@domain.com"                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1174 - assertIsFalse "local.part.with.double.empty.string2a\"\"ba\"\"btest@domain.com"                =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1175 - assertIsFalse "(comment a\"\"b) local.part.with.empty.string2.in.comment@domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1176 - assertIsFalse "\"stringa\"\"b\".local.part.with.empty.string2.in.String@domain.com"            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1177 - assertIsFalse "\"string\a\"\"b\".local.part.with.escaped.empty.string2.in.String@domain.com"   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1178 - assertIsFalse "a\"\"b@local.part.only.empty.string2.domain.com"                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1179 - assertIsFalse "a\"\"ba\"\"ba\"\"ba\"\"ba\"\"ba\"\"b@local.part.only.consecutive.empty.string2.domain.com" =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1180 - assertIsFalse "a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b.a\"\"b@empty.string2.domain.com"             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  1181 - assertIsTrue  "name a\"\"b <pointy.brackets1.with.empty.string2.in.display.name@domain.com>"   =   0 =  OK 
   *  1182 - assertIsTrue  "<pointy.brackets2.with.empty.string2.in.display.name@domain.com> name a\"\"b"   =   0 =  OK 
   *  1183 - assertIsFalse "domain.part@witha\"\"bempty.string2.com"                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1184 - assertIsFalse "domain.part@a\"\"bwith.empty.string2.at.domain.start.com"                       =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1185 - assertIsFalse "domain.part@with.empty.string2.at.domain.end1a\"\"b.com"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1186 - assertIsFalse "domain.part@with.empty.string2.at.domain.end2.coma\"\"b"                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1187 - assertIsFalse "domain.part@with.empty.string2.beforea\"\"b.point.com"                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1188 - assertIsFalse "domain.part@with.empty.string2.after.a\"\"bpoint.com"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1189 - assertIsFalse "domain.part@with.consecutive.empty.string2a\"\"ba\"\"btest.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1190 - assertIsFalse "domain.part.with.empty.string2.in.comment@(comment a\"\"b)domain.com"           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1191 - assertIsFalse "domain.part.only.empty.string2@a\"\"b.com"                                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1192 - assertIsFalse "top.level.domain.only@empty.string2.a\"\"b"                                     =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1193 - assertIsFalse "\"\"\"\".local.part.starts.with.double.empty.string1@domain.com"                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1194 - assertIsFalse "local.part.ends.with.double.empty.string1\"\"\"\"@domain.com"                   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1195 - assertIsFalse "local.part.with.double.empty.string1\"\"\"\"character@domain.com"               =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1196 - assertIsFalse "local.part.with.double.empty.string1.before\"\"\"\".point@domain.com"           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1197 - assertIsFalse "local.part.with.double.empty.string1.after.\"\"\"\"point@domain.com"            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1198 - assertIsFalse "local.part.with.double.double.empty.string1\"\"\"\"\"\"\"\"test@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1199 - assertIsFalse "(comment \"\"\"\") local.part.with.double.empty.string1.in.comment@domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1200 - assertIsFalse "\"string\"\"\"\"\".local.part.with.double.empty.string1.in.String@domain.com"   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1201 - assertIsFalse "\"string\\"\"\"\"\".local.part.with.escaped.double.empty.string1.in.String@domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1202 - assertIsFalse "\"\"\"\"@local.part.only.double.empty.string1.domain.com"                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1203 - assertIsFalse "\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"\"@local.part.only.consecutive.double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1204 - assertIsFalse "\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\".\"\"\"\"@double.empty.string1.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1205 - assertIsTrue  "name \"\"\"\" <pointy.brackets1.with.double.empty.string1.in.display.name@domain.com>" =   0 =  OK 
   *  1206 - assertIsTrue  "<pointy.brackets2.with.double.empty.string1.in.display.name@domain.com> name \"\"\"\"" =   0 =  OK 
   *  1207 - assertIsFalse "domain.part@with\"\"\"\"double.empty.string1.com"                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1208 - assertIsFalse "domain.part@\"\"\"\"with.double.empty.string1.at.domain.start.com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1209 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end1\"\"\"\".com"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1210 - assertIsFalse "domain.part@with.double.empty.string1.at.domain.end2.com\"\"\"\""               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1211 - assertIsFalse "domain.part@with.double.empty.string1.before\"\"\"\".point.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1212 - assertIsFalse "domain.part@with.double.empty.string1.after.\"\"\"\"point.com"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1213 - assertIsFalse "domain.part@with.consecutive.double.empty.string1\"\"\"\"\"\"\"\"test.com"      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1214 - assertIsFalse "domain.part.with.double.empty.string1.in.comment@(comment \"\"\"\")domain.com"  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1215 - assertIsFalse "domain.part.only.double.empty.string1@\"\"\"\".com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1216 - assertIsFalse "top.level.domain.only@double.empty.string1.\"\"\"\""                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1217 - assertIsFalse "\"\".\"\".local.part.starts.with.double.empty.string2@domain.com"               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1218 - assertIsFalse "local.part.ends.with.double.empty.string2\"\".\"\"@domain.com"                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1219 - assertIsFalse "local.part.with.double.empty.string2\"\".\"\"character@domain.com"              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1220 - assertIsFalse "local.part.with.double.empty.string2.before\"\".\"\".point@domain.com"          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1221 - assertIsFalse "local.part.with.double.empty.string2.after.\"\".\"\"point@domain.com"           =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1222 - assertIsFalse "local.part.with.double.double.empty.string2\"\".\"\"\"\".\"\"test@domain.com"   =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1223 - assertIsFalse "(comment \"\".\"\") local.part.with.double.empty.string2.in.comment@domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1224 - assertIsFalse "\"string\"\".\"\"\".local.part.with.double.empty.string2.in.String@domain.com"  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1225 - assertIsFalse "\"string\\"\".\"\"\".local.part.with.escaped.double.empty.string2.in.String@domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1226 - assertIsFalse "\"\".\"\"@local.part.only.double.empty.string2.domain.com"                      =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1227 - assertIsFalse "\"\".\"\"\"\".\"\"\"\".\"\"\"\"@local.part.only.consecutive.double.empty.string2.domain.com" =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1228 - assertIsFalse "\"\".\"\".\"\".\"\".\"\".\"\".\"\"@double.empty.string2.domain.com"             =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  1229 - assertIsFalse "name \"\".\"\" <pointy.brackets1.with.double.empty.string2.in.display.name@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1230 - assertIsFalse "<pointy.brackets2.with.double.empty.string2.in.display.name@domain.com> name \"\".\"\"" =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1231 - assertIsFalse "domain.part@with\"\".\"\"double.empty.string2.com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1232 - assertIsFalse "domain.part@\"\".\"\"with.double.empty.string2.at.domain.start.com"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1233 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end1\"\".\"\".com"              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1234 - assertIsFalse "domain.part@with.double.empty.string2.at.domain.end2.com\"\".\"\""              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1235 - assertIsFalse "domain.part@with.double.empty.string2.before\"\".\"\".point.com"                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1236 - assertIsFalse "domain.part@with.double.empty.string2.after.\"\".\"\"point.com"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1237 - assertIsFalse "domain.part@with.consecutive.double.empty.string2\"\".\"\"\"\".\"\"test.com"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1238 - assertIsFalse "domain.part.with.double.empty.string2.in.comment@(comment \"\".\"\")domain.com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1239 - assertIsFalse "domain.part.only.double.empty.string2@\"\".\"\".com"                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1240 - assertIsFalse "top.level.domain.only@double.empty.string2.\"\".\"\""                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1241 - assertIsTrue  "0.local.part.starts.with.number0@domain.com"                                    =   0 =  OK 
   *  1242 - assertIsTrue  "local.part.ends.with.number00@domain.com"                                       =   0 =  OK 
   *  1243 - assertIsTrue  "local.part.with.number00character@domain.com"                                   =   0 =  OK 
   *  1244 - assertIsTrue  "local.part.with.number0.before0.point@domain.com"                               =   0 =  OK 
   *  1245 - assertIsTrue  "local.part.with.number0.after.0point@domain.com"                                =   0 =  OK 
   *  1246 - assertIsTrue  "local.part.with.double.number000test@domain.com"                                =   0 =  OK 
   *  1247 - assertIsTrue  "(comment 0) local.part.with.number0.in.comment@domain.com"                      =   6 =  OK 
   *  1248 - assertIsTrue  "\"string0\".local.part.with.number0.in.String@domain.com"                       =   1 =  OK 
   *  1249 - assertIsFalse "\"string\0\".local.part.with.escaped.number0.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1250 - assertIsTrue  "0@local.part.only.number0.domain.com"                                           =   0 =  OK 
   *  1251 - assertIsTrue  "000000@local.part.only.consecutive.number0.domain.com"                          =   0 =  OK 
   *  1252 - assertIsTrue  "0.0.0.0.0.0@number0.domain.com"                                                 =   0 =  OK 
   *  1253 - assertIsTrue  "name 0 <pointy.brackets1.with.number0.in.display.name@domain.com>"              =   0 =  OK 
   *  1254 - assertIsTrue  "<pointy.brackets2.with.number0.in.display.name@domain.com> name 0"              =   0 =  OK 
   *  1255 - assertIsTrue  "domain.part@with0number0.com"                                                   =   0 =  OK 
   *  1256 - assertIsTrue  "domain.part@0with.number0.at.domain.start.com"                                  =   0 =  OK 
   *  1257 - assertIsTrue  "domain.part@with.number0.at.domain.end10.com"                                   =   0 =  OK 
   *  1258 - assertIsTrue  "domain.part@with.number0.at.domain.end2.com0"                                   =   0 =  OK 
   *  1259 - assertIsTrue  "domain.part@with.number0.before0.point.com"                                     =   0 =  OK 
   *  1260 - assertIsTrue  "domain.part@with.number0.after.0point.com"                                      =   0 =  OK 
   *  1261 - assertIsTrue  "domain.part@with.consecutive.number000test.com"                                 =   0 =  OK 
   *  1262 - assertIsTrue  "domain.part.with.number0.in.comment@(comment 0)domain.com"                      =   6 =  OK 
   *  1263 - assertIsTrue  "domain.part.only.number0@0.com"                                                 =   0 =  OK 
   *  1264 - assertIsFalse "top.level.domain.only@number0.0"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1265 - assertIsTrue  "9.local.part.starts.with.number9@domain.com"                                    =   0 =  OK 
   *  1266 - assertIsTrue  "local.part.ends.with.number99@domain.com"                                       =   0 =  OK 
   *  1267 - assertIsTrue  "local.part.with.number99character@domain.com"                                   =   0 =  OK 
   *  1268 - assertIsTrue  "local.part.with.number9.before9.point@domain.com"                               =   0 =  OK 
   *  1269 - assertIsTrue  "local.part.with.number9.after.9point@domain.com"                                =   0 =  OK 
   *  1270 - assertIsTrue  "local.part.with.double.number999test@domain.com"                                =   0 =  OK 
   *  1271 - assertIsTrue  "(comment 9) local.part.with.number9.in.comment@domain.com"                      =   6 =  OK 
   *  1272 - assertIsTrue  "\"string9\".local.part.with.number9.in.String@domain.com"                       =   1 =  OK 
   *  1273 - assertIsFalse "\"string\9\".local.part.with.escaped.number9.in.String@domain.com"              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1274 - assertIsTrue  "9@local.part.only.number9.domain.com"                                           =   0 =  OK 
   *  1275 - assertIsTrue  "999999@local.part.only.consecutive.number9.domain.com"                          =   0 =  OK 
   *  1276 - assertIsTrue  "9.9.9.9.9.9@number9.domain.com"                                                 =   0 =  OK 
   *  1277 - assertIsTrue  "name 9 <pointy.brackets1.with.number9.in.display.name@domain.com>"              =   0 =  OK 
   *  1278 - assertIsTrue  "<pointy.brackets2.with.number9.in.display.name@domain.com> name 9"              =   0 =  OK 
   *  1279 - assertIsTrue  "domain.part@with9number9.com"                                                   =   0 =  OK 
   *  1280 - assertIsTrue  "domain.part@9with.number9.at.domain.start.com"                                  =   0 =  OK 
   *  1281 - assertIsTrue  "domain.part@with.number9.at.domain.end19.com"                                   =   0 =  OK 
   *  1282 - assertIsTrue  "domain.part@with.number9.at.domain.end2.com9"                                   =   0 =  OK 
   *  1283 - assertIsTrue  "domain.part@with.number9.before9.point.com"                                     =   0 =  OK 
   *  1284 - assertIsTrue  "domain.part@with.number9.after.9point.com"                                      =   0 =  OK 
   *  1285 - assertIsTrue  "domain.part@with.consecutive.number999test.com"                                 =   0 =  OK 
   *  1286 - assertIsTrue  "domain.part.with.number9.in.comment@(comment 9)domain.com"                      =   6 =  OK 
   *  1287 - assertIsTrue  "domain.part.only.number9@9.com"                                                 =   0 =  OK 
   *  1288 - assertIsFalse "top.level.domain.only@number9.9"                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1289 - assertIsTrue  "0123456789.local.part.starts.with.numbers@domain.com"                           =   0 =  OK 
   *  1290 - assertIsTrue  "local.part.ends.with.numbers0123456789@domain.com"                              =   0 =  OK 
   *  1291 - assertIsTrue  "local.part.with.numbers0123456789character@domain.com"                          =   0 =  OK 
   *  1292 - assertIsTrue  "local.part.with.numbers.before0123456789.point@domain.com"                      =   0 =  OK 
   *  1293 - assertIsTrue  "local.part.with.numbers.after.0123456789point@domain.com"                       =   0 =  OK 
   *  1294 - assertIsTrue  "local.part.with.double.numbers01234567890123456789test@domain.com"              =   0 =  OK 
   *  1295 - assertIsTrue  "(comment 0123456789) local.part.with.numbers.in.comment@domain.com"             =   6 =  OK 
   *  1296 - assertIsTrue  "\"string0123456789\".local.part.with.numbers.in.String@domain.com"              =   1 =  OK 
   *  1297 - assertIsFalse "\"string\0123456789\".local.part.with.escaped.numbers.in.String@domain.com"     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1298 - assertIsTrue  "0123456789@local.part.only.numbers.domain.com"                                  =   0 =  OK 
   *  1299 - assertIsTrue  "01234567890123@local.part.only.consecutive.numbers.domain.com"                  =   0 =  OK 
   *  1300 - assertIsTrue  "0123456789.0123456789.0123456789@numbers.domain.com"                            =   0 =  OK 
   *  1301 - assertIsTrue  "name 0123456789 <pointy.brackets1.with.numbers.in.display.name@domain.com>"     =   0 =  OK 
   *  1302 - assertIsTrue  "<pointy.brackets2.with.numbers.in.display.name@domain.com> name 0123456789"     =   0 =  OK 
   *  1303 - assertIsTrue  "domain.part@with0123456789numbers.com"                                          =   0 =  OK 
   *  1304 - assertIsTrue  "domain.part@0123456789with.numbers.at.domain.start.com"                         =   0 =  OK 
   *  1305 - assertIsTrue  "domain.part@with.numbers.at.domain.end10123456789.com"                          =   0 =  OK 
   *  1306 - assertIsTrue  "domain.part@with.numbers.at.domain.end2.com0123456789"                          =   0 =  OK 
   *  1307 - assertIsTrue  "domain.part@with.numbers.before0123456789.point.com"                            =   0 =  OK 
   *  1308 - assertIsTrue  "domain.part@with.numbers.after.0123456789point.com"                             =   0 =  OK 
   *  1309 - assertIsTrue  "domain.part@with.consecutive.numbers01234567890123456789test.com"               =   0 =  OK 
   *  1310 - assertIsTrue  "domain.part.with.numbers.in.comment@(comment 0123456789)domain.com"             =   6 =  OK 
   *  1311 - assertIsTrue  "domain.part.only.numbers@0123456789.com"                                        =   0 =  OK 
   *  1312 - assertIsFalse "top.level.domain.only@numbers.0123456789"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  1313 - assertIsFalse "\.local.part.starts.with.slash@domain.com"                                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1314 - assertIsFalse "local.part.ends.with.slash\@domain.com"                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  1315 - assertIsFalse "local.part.with.slash\character@domain.com"                                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1316 - assertIsFalse "local.part.with.slash.before\.point@domain.com"                                 =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1317 - assertIsFalse "local.part.with.slash.after.\point@domain.com"                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1318 - assertIsTrue  "local.part.with.double.slash\\test@domain.com"                                  =   0 =  OK 
   *  1319 - assertIsFalse "(comment \) local.part.with.slash.in.comment@domain.com"                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  1320 - assertIsFalse "\"string\\".local.part.with.slash.in.String@domain.com"                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  1321 - assertIsTrue  "\"string\\\".local.part.with.escaped.slash.in.String@domain.com"                =   1 =  OK 
   *  1322 - assertIsFalse "\@local.part.only.slash.domain.com"                                             =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  1323 - assertIsTrue  "\\\\\\@local.part.only.consecutive.slash.domain.com"                            =   0 =  OK 
   *  1324 - assertIsFalse "\.\.\.\.\.\@slash.domain.com"                                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1325 - assertIsTrue  "escaped character is space \ <pointy.brackets1.with.slash.in.display.name@domain.com>" =   0 =  OK 
   *  1326 - assertIsFalse "no escaped character \<pointy.brackets1.with.slash.in.display.name@domain.com>" =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
   *  1327 - assertIsFalse "<pointy.brackets2.with.slash.in.display.name@domain.com> name \"                =  83 =  OK    String: Escape-Zeichen nicht am Ende der Eingabe
   *  1328 - assertIsFalse "domain.part@with\slash.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1329 - assertIsFalse "domain.part@\with.slash.at.domain.start.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1330 - assertIsFalse "domain.part@with.slash.at.domain.end1\.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1331 - assertIsFalse "domain.part@with.slash.at.domain.end2.com\"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1332 - assertIsFalse "domain.part@with.slash.before\.point.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1333 - assertIsFalse "domain.part@with.slash.after.\point.com"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1334 - assertIsFalse "domain.part@with.consecutive.slash\\test.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1335 - assertIsFalse "domain.part.with.slash.in.comment@(comment \)domain.com"                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  1336 - assertIsFalse "domain.part.only.slash@\.com"                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1337 - assertIsFalse "top.level.domain.only@slash.\"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1338 - assertIsTrue  "\"str\".local.part.starts.with.string@domain.com"                               =   1 =  OK 
   *  1339 - assertIsFalse "local.part.ends.with.string\"str\"@domain.com"                                  =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1340 - assertIsFalse "local.part.with.string\"str\"character@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1341 - assertIsFalse "local.part.with.string.before\"str\".point@domain.com"                          =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1342 - assertIsFalse "local.part.with.string.after.\"str\"point@domain.com"                           =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1343 - assertIsFalse "local.part.with.double.string\"str\"\"str\"test@domain.com"                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  1344 - assertIsFalse "(comment \"str\") local.part.with.string.in.comment@domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1345 - assertIsFalse "\"string\"str\"\".local.part.with.string.in.String@domain.com"                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1346 - assertIsFalse "\"string\\"str\"\".local.part.with.escaped.string.in.String@domain.com"         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1347 - assertIsTrue  "\"str\"@local.part.only.string.domain.com"                                      =   1 =  OK 
   *  1348 - assertIsFalse "\"str\"\"str\"\"str\"\"str\"\"str\"\"str\"@local.part.only.consecutive.string.domain.com" =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  1349 - assertIsTrue  "\"str\".\"str\".\"str\".\"str\".\"str\".\"str\"@string.domain.com"              =   1 =  OK 
   *  1350 - assertIsTrue  "name \"str\" <pointy.brackets1.with.string.in.display.name@domain.com>"         =   0 =  OK 
   *  1351 - assertIsTrue  "<pointy.brackets2.with.string.in.display.name@domain.com> name \"str\""         =   0 =  OK 
   *  1352 - assertIsFalse "domain.part@with\"str\"string.com"                                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1353 - assertIsFalse "domain.part@\"str\"with.string.at.domain.start.com"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1354 - assertIsFalse "domain.part@with.string.at.domain.end1\"str\".com"                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1355 - assertIsFalse "domain.part@with.string.at.domain.end2.com\"str\""                              =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1356 - assertIsFalse "domain.part@with.string.before\"str\".point.com"                                =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1357 - assertIsFalse "domain.part@with.string.after.\"str\"point.com"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1358 - assertIsFalse "domain.part@with.consecutive.string\"str\"\"str\"test.com"                      =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1359 - assertIsFalse "domain.part.with.string.in.comment@(comment \"str\")domain.com"                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1360 - assertIsFalse "domain.part.only.string@\"str\".com"                                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1361 - assertIsFalse "top.level.domain.only@string.\"str\""                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1362 - assertIsFalse "(comment).local.part.starts.with.comment@domain.com"                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1363 - assertIsTrue  "local.part.ends.with.comment(comment)@domain.com"                               =   6 =  OK 
   *  1364 - assertIsFalse "local.part.with.comment(comment)character@domain.com"                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  1365 - assertIsFalse "local.part.with.comment.before(comment).point@domain.com"                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1366 - assertIsFalse "local.part.with.comment.after.(comment)point@domain.com"                        = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  1367 - assertIsFalse "local.part.with.double.comment(comment)(comment)test@domain.com"                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  1368 - assertIsFalse "(comment (comment)) local.part.with.comment.in.comment@domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1369 - assertIsTrue  "\"string(comment)\".local.part.with.comment.in.String@domain.com"               =   1 =  OK 
   *  1370 - assertIsFalse "\"string\(comment)\".local.part.with.escaped.comment.in.String@domain.com"      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  1371 - assertIsFalse "(comment)@local.part.only.comment.domain.com"                                   =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *  1372 - assertIsFalse "(comment)(comment)(comment)@local.part.only.consecutive.comment.domain.com"     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  1373 - assertIsFalse "(comment).(comment).(comment).(comment)@comment.domain.com"                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1374 - assertIsTrue  "name (comment) <pointy.brackets1.with.comment.in.display.name@domain.com>"      =   0 =  OK 
   *  1375 - assertIsTrue  "<pointy.brackets2.with.comment.in.display.name@domain.com> name (comment)"      =   0 =  OK 
   *  1376 - assertIsFalse "domain.part@with(comment)comment.com"                                           = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  1377 - assertIsTrue  "domain.part@(comment)with.comment.at.domain.start.com"                          =   6 =  OK 
   *  1378 - assertIsFalse "domain.part@with.comment.at.domain.end1(comment).com"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1379 - assertIsTrue  "domain.part@with.comment.at.domain.end2.com(comment)"                           =   6 =  OK 
   *  1380 - assertIsFalse "domain.part@with.comment.before(comment).point.com"                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1381 - assertIsFalse "domain.part@with.comment.after.(comment)point.com"                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *  1382 - assertIsFalse "domain.part@with.consecutive.comment(comment)(comment)test.com"                 = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  1383 - assertIsFalse "domain.part.with.comment.in.comment@(comment (comment))domain.com"              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1384 - assertIsFalse "domain.part.only.comment@(comment).com"                                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  1385 - assertIsFalse "top.level.domain.only@comment.(comment)"                                        = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   * 
   * ---- IP V4 -----------------------------------------------------------------------------------------------------------------------
   * 
   *  1386 - assertIsFalse "\"\"@[]"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  1387 - assertIsFalse "\"\"@[1"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  1388 - assertIsFalse "A+B@[1[2[3[4[5[6(1(2(3(4(5(6(7(8)(9)]{break{that{reg{ex[state(ment}[({})"       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1389 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1390 - assertIsFalse "1.2.3.4]@[5.6.7.8]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1391 - assertIsFalse "[1.2.3.4@[5.6.7.8]"                                                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1392 - assertIsFalse "[1.2.3.4][5.6.7.8]@[9.10.11.12]"                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1393 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12]"                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1394 - assertIsFalse "[1.2.3.4]@[5.6.7.8]9.10.11.12]"                                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1395 - assertIsFalse "[1.2.3.4]@[5.6.7.8][9.10.11.12["                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1396 - assertIsTrue  "ip4.in.local.part.as.string1.\"[1.2.3.4]\"@[5.6.7.8]"                           =   3 =  OK 
   *  1397 - assertIsTrue  "ip4.in.local.part.as.string2.\"@[1.2.3.4]\"@[5.6.7.8]"                          =   3 =  OK 
   *  1398 - assertIsFalse "ip4.ends.with.alpha.character1@[1.2.3.Z]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1399 - assertIsFalse "ip4.ends.with.alpha.character2@[1.2.3.]Z"                                       =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
   *  1400 - assertIsFalse "ip4.ends.with.top.level.domain@[1.2.3.].de"                                     =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
   *  1401 - assertIsFalse "ip4.with.double.ip4@[1.2.3.4][5.6.7.8]"                                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1402 - assertIsFalse "ip4.with.ip4.in.comment1@([1.2.3.4])"                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1403 - assertIsFalse "ip4.with.ip4.in.comment2@([1.2.3.4])[5.6.7.8]"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1404 - assertIsFalse "ip4.with.ip4.in.comment3@[1.2.3.4]([5.6.7.8])"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1405 - assertIsTrue  "ip4.with.ip4.in.comment4@[1.2.3.4] (@)"                                         =   2 =  OK 
   *  1406 - assertIsTrue  "ip4.with.ip4.in.comment5@[1.2.3.4] (@.)"                                        =   2 =  OK 
   *  1407 - assertIsFalse "ip4.with.hex.numbers@[AB.CD.EF.EA]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1408 - assertIsFalse "ip4.with.hex.number.overflow@[AB.CD.EF.FF1]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1409 - assertIsFalse "ip4.with.double.brackets@[1.2.3.4][5.6.7.8]"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1410 - assertIsFalse "ip4.missing.at.sign[1.2.3.4]"                                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1411 - assertIsFalse "ip4.missing.the.start.bracket@]"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1412 - assertIsFalse "ip4.missing.the.end.bracket@["                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1413 - assertIsFalse "ip4.missing.the.start.bracket@1.2.3.4]"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1414 - assertIsFalse "ip4.missing.the.end.bracket@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1415 - assertIsFalse "ip4.missing.numbers.and.the.start.bracket@...]"                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1416 - assertIsFalse "ip4.missing.numbers.and.the.end.bracket@[..."                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1417 - assertIsFalse "ip4.missplaced.start.bracket1[@1.2.3.4]"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1418 - assertIsFalse "ip4.missing.the.first.number@[.2.3.4]"                                          =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1419 - assertIsFalse "ip4.missing.the.last.number@[1.2.3.]"                                           =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
   *  1420 - assertIsFalse "ip4.last.number.is.space@[1.2.3. ]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1421 - assertIsFalse "ip4.with.only.one.numberABC.DEF@[1]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1422 - assertIsFalse "ip4.with.only.two.numbers@[1.2]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1423 - assertIsFalse "ip4.with.only.three.numbers@[1.2.3]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1424 - assertIsFalse "ip4.with.five.numbers@[1.2.3.4.5]"                                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1425 - assertIsFalse "ip4.with.six.numbers@[1.2.3.4.5.6]"                                             =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1426 - assertIsFalse "ip4.with.byte.overflow1@[1.2.3.256]"                                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1427 - assertIsFalse "ip4.with.byte.overflow2@[1.2.3.1000]"                                           =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1428 - assertIsFalse "ip4.with.to.many.leading.zeros@[0001.000002.000003.00000004]"                   =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1429 - assertIsTrue  "ip4.with.two.leading.zeros@[001.002.003.004]"                                   =   2 =  OK 
   *  1430 - assertIsTrue  "ip4.zero@[0.0.0.0]"                                                             =   2 =  OK 
   *  1431 - assertIsTrue  "ip4.correct1@[1.2.3.4]"                                                         =   2 =  OK 
   *  1432 - assertIsTrue  "ip4.correct2@[255.255.255.255]"                                                 =   2 =  OK 
   *  1433 - assertIsTrue  "\"ip4.local.part.as.string\"@[127.0.0.1]"                                       =   3 =  OK 
   *  1434 - assertIsTrue  "\"    \"@[1.2.3.4]"                                                             =   3 =  OK 
   *  1435 - assertIsFalse "ip4.no.email.adress[1.2.3.4]  but.with.space[1.2.3.4]"                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  1436 - assertIsFalse "ip4.with.negative.number1@[-1.2.3.4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1437 - assertIsFalse "ip4.with.negative.number2@[1.-2.3.4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1438 - assertIsFalse "ip4.with.negative.number3@[1.2.-3.4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1439 - assertIsFalse "ip4.with.negative.number4@[1.2.3.-4]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1440 - assertIsFalse "ip4.with.only.empty.brackets@[]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1441 - assertIsFalse "ip4.with.three.empty.brackets@[][][]"                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1442 - assertIsFalse "ip4.with.wrong.characters.in.brackets@[{][})][}][}\\"]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1443 - assertIsFalse "ip4.in.false.brackets@{1.2.3.4}"                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1444 - assertIsFalse "ip4.with.semicolon.between.numbers@[123.14;5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1445 - assertIsFalse "ip4.with.semicolon.before.point@[123.145;.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1446 - assertIsFalse "ip4.with.semicolon.after.point@[123.145.;178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1447 - assertIsFalse "ip4.with.semicolon.before.start.bracket@;[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1448 - assertIsFalse "ip4.with.semicolon.after.start.bracket@[;123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1449 - assertIsFalse "ip4.with.semicolon.before.end.bracket@[123.145.178.90;]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1450 - assertIsFalse "ip4.with.semicolon.after.end.bracket@[123.145.178.90];"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1451 - assertIsFalse "ip4.with.only.one.dot.in.brackets@[.]"                                          =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1452 - assertIsFalse "ip4.with.only.double.dot.in.brackets@[..]"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1453 - assertIsFalse "ip4.with.only.triple.dot.in.brackets@[...]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1454 - assertIsFalse "ip4.with.only.four.dots.in.brackets@[....]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1455 - assertIsFalse "ip4.with.false.consecutive.points@[1.2...3.4]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1456 - assertIsFalse "ip4.with.dot.between.numbers@[123.14.5.178.90]"                                 =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1457 - assertIsFalse "ip4.with.dot.before.point@[123.145..178.90]"                                    =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1458 - assertIsFalse "ip4.with.dot.after.point@[123.145..178.90]"                                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1459 - assertIsFalse "ip4.with.dot.before.start.bracket@.[123.145.178.90]"                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1460 - assertIsFalse "ip4.with.dot.after.start.bracket@[.123.145.178.90]"                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1461 - assertIsFalse "ip4.with.dot.before.end.bracket@[123.145.178.90.]"                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1462 - assertIsFalse "ip4.with.dot.after.end.bracket@[123.145.178.90]."                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1463 - assertIsFalse "ip4.with.double.dot.between.numbers@[123.14..5.178.90]"                         =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1464 - assertIsFalse "ip4.with.double.dot.before.point@[123.145...178.90]"                            =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1465 - assertIsFalse "ip4.with.double.dot.after.point@[123.145...178.90]"                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1466 - assertIsFalse "ip4.with.double.dot.before.start.bracket@..[123.145.178.90]"                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1467 - assertIsFalse "ip4.with.double.dot.after.start.bracket@[..123.145.178.90]"                     =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1468 - assertIsFalse "ip4.with.double.dot.before.end.bracket@[123.145.178.90..]"                      =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  1469 - assertIsFalse "ip4.with.double.dot.after.end.bracket@[123.145.178.90].."                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1470 - assertIsFalse "ip4.with.amp.between.numbers@[123.14&5.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1471 - assertIsFalse "ip4.with.amp.before.point@[123.145&.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1472 - assertIsFalse "ip4.with.amp.after.point@[123.145.&178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1473 - assertIsFalse "ip4.with.amp.before.start.bracket@&[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1474 - assertIsFalse "ip4.with.amp.after.start.bracket@[&123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1475 - assertIsFalse "ip4.with.amp.before.end.bracket@[123.145.178.90&]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1476 - assertIsFalse "ip4.with.amp.after.end.bracket@[123.145.178.90]&"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1477 - assertIsFalse "ip4.with.asterisk.between.numbers@[123.14*5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1478 - assertIsFalse "ip4.with.asterisk.before.point@[123.145*.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1479 - assertIsFalse "ip4.with.asterisk.after.point@[123.145.*178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1480 - assertIsFalse "ip4.with.asterisk.before.start.bracket@*[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1481 - assertIsFalse "ip4.with.asterisk.after.start.bracket@[*123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1482 - assertIsFalse "ip4.with.asterisk.before.end.bracket@[123.145.178.90*]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1483 - assertIsFalse "ip4.with.asterisk.after.end.bracket@[123.145.178.90]*"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1484 - assertIsFalse "ip4.with.underscore.between.numbers@[123.14_5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1485 - assertIsFalse "ip4.with.underscore.before.point@[123.145_.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1486 - assertIsFalse "ip4.with.underscore.after.point@[123.145._178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1487 - assertIsFalse "ip4.with.underscore.before.start.bracket@_[123.145.178.90]"                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1488 - assertIsFalse "ip4.with.underscore.after.start.bracket@[_123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1489 - assertIsFalse "ip4.with.underscore.before.end.bracket@[123.145.178.90_]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1490 - assertIsFalse "ip4.with.underscore.after.end.bracket@[123.145.178.90]_"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1491 - assertIsFalse "ip4.with.dollar.between.numbers@[123.14$5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1492 - assertIsFalse "ip4.with.dollar.before.point@[123.145$.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1493 - assertIsFalse "ip4.with.dollar.after.point@[123.145.$178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1494 - assertIsFalse "ip4.with.dollar.before.start.bracket@$[123.145.178.90]"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1495 - assertIsFalse "ip4.with.dollar.after.start.bracket@[$123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1496 - assertIsFalse "ip4.with.dollar.before.end.bracket@[123.145.178.90$]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1497 - assertIsFalse "ip4.with.dollar.after.end.bracket@[123.145.178.90]$"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1498 - assertIsFalse "ip4.with.equality.between.numbers@[123.14=5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1499 - assertIsFalse "ip4.with.equality.before.point@[123.145=.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1500 - assertIsFalse "ip4.with.equality.after.point@[123.145.=178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1501 - assertIsFalse "ip4.with.equality.before.start.bracket@=[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1502 - assertIsFalse "ip4.with.equality.after.start.bracket@[=123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1503 - assertIsFalse "ip4.with.equality.before.end.bracket@[123.145.178.90=]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1504 - assertIsFalse "ip4.with.equality.after.end.bracket@[123.145.178.90]="                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1505 - assertIsFalse "ip4.with.exclamation.between.numbers@[123.14!5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1506 - assertIsFalse "ip4.with.exclamation.before.point@[123.145!.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1507 - assertIsFalse "ip4.with.exclamation.after.point@[123.145.!178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1508 - assertIsFalse "ip4.with.exclamation.before.start.bracket@![123.145.178.90]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1509 - assertIsFalse "ip4.with.exclamation.after.start.bracket@[!123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1510 - assertIsFalse "ip4.with.exclamation.before.end.bracket@[123.145.178.90!]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1511 - assertIsFalse "ip4.with.exclamation.after.end.bracket@[123.145.178.90]!"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1512 - assertIsFalse "ip4.with.question.between.numbers@[123.14?5.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1513 - assertIsFalse "ip4.with.question.before.point@[123.145?.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1514 - assertIsFalse "ip4.with.question.after.point@[123.145.?178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1515 - assertIsFalse "ip4.with.question.before.start.bracket@?[123.145.178.90]"                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1516 - assertIsFalse "ip4.with.question.after.start.bracket@[?123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1517 - assertIsFalse "ip4.with.question.before.end.bracket@[123.145.178.90?]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1518 - assertIsFalse "ip4.with.question.after.end.bracket@[123.145.178.90]?"                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1519 - assertIsFalse "ip4.with.grave-accent.between.numbers@[123.14`5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1520 - assertIsFalse "ip4.with.grave-accent.before.point@[123.145`.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1521 - assertIsFalse "ip4.with.grave-accent.after.point@[123.145.`178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1522 - assertIsFalse "ip4.with.grave-accent.before.start.bracket@`[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1523 - assertIsFalse "ip4.with.grave-accent.after.start.bracket@[`123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1524 - assertIsFalse "ip4.with.grave-accent.before.end.bracket@[123.145.178.90`]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1525 - assertIsFalse "ip4.with.grave-accent.after.end.bracket@[123.145.178.90]`"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1526 - assertIsFalse "ip4.with.hash.between.numbers@[123.14#5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1527 - assertIsFalse "ip4.with.hash.before.point@[123.145#.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1528 - assertIsFalse "ip4.with.hash.after.point@[123.145.#178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1529 - assertIsFalse "ip4.with.hash.before.start.bracket@#[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1530 - assertIsFalse "ip4.with.hash.after.start.bracket@[#123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1531 - assertIsFalse "ip4.with.hash.before.end.bracket@[123.145.178.90#]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1532 - assertIsFalse "ip4.with.hash.after.end.bracket@[123.145.178.90]#"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1533 - assertIsFalse "ip4.with.percentage.between.numbers@[123.14%5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1534 - assertIsFalse "ip4.with.percentage.before.point@[123.145%.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1535 - assertIsFalse "ip4.with.percentage.after.point@[123.145.%178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1536 - assertIsFalse "ip4.with.percentage.before.start.bracket@%[123.145.178.90]"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1537 - assertIsFalse "ip4.with.percentage.after.start.bracket@[%123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1538 - assertIsFalse "ip4.with.percentage.before.end.bracket@[123.145.178.90%]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1539 - assertIsFalse "ip4.with.percentage.after.end.bracket@[123.145.178.90]%"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1540 - assertIsFalse "ip4.with.pipe.between.numbers@[123.14|5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1541 - assertIsFalse "ip4.with.pipe.before.point@[123.145|.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1542 - assertIsFalse "ip4.with.pipe.after.point@[123.145.|178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1543 - assertIsFalse "ip4.with.pipe.before.start.bracket@|[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1544 - assertIsFalse "ip4.with.pipe.after.start.bracket@[|123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1545 - assertIsFalse "ip4.with.pipe.before.end.bracket@[123.145.178.90|]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1546 - assertIsFalse "ip4.with.pipe.after.end.bracket@[123.145.178.90]|"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1547 - assertIsFalse "ip4.with.plus.between.numbers@[123.14+5.178.90]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1548 - assertIsFalse "ip4.with.plus.before.point@[123.145+.178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1549 - assertIsFalse "ip4.with.plus.after.point@[123.145.+178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1550 - assertIsFalse "ip4.with.plus.before.start.bracket@+[123.145.178.90]"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1551 - assertIsFalse "ip4.with.plus.after.start.bracket@[+123.145.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1552 - assertIsFalse "ip4.with.plus.before.end.bracket@[123.145.178.90+]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1553 - assertIsFalse "ip4.with.plus.after.end.bracket@[123.145.178.90]+"                              =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1554 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14{5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1555 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145{.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1556 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.{178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1557 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@{[123.145.178.90]"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1558 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[{123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1559 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90{]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1560 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]{"                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1561 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14}5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1562 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145}.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1563 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.}178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1564 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@}[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1565 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[}123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1566 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90}]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1567 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]}"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1568 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14(5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1569 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145(.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1570 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.(178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1571 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@([123.145.178.90]"                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1572 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[(123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1573 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90(]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1574 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]("                       =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  1575 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14)5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1576 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145).178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1577 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.)178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1578 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@)[123.145.178.90]"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1579 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[)123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1580 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90)]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1581 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90])"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1582 - assertIsFalse "ip4.with.leftbracket.between.numbers@[123.14[5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1583 - assertIsFalse "ip4.with.leftbracket.before.point@[123.145[.178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1584 - assertIsFalse "ip4.with.leftbracket.after.point@[123.145.[178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1585 - assertIsFalse "ip4.with.leftbracket.before.start.bracket@[[123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1586 - assertIsFalse "ip4.with.leftbracket.after.start.bracket@[[123.145.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1587 - assertIsFalse "ip4.with.leftbracket.before.end.bracket@[123.145.178.90[]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1588 - assertIsFalse "ip4.with.leftbracket.after.end.bracket@[123.145.178.90]["                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1589 - assertIsFalse "ip4.with.rightbracket.between.numbers@[123.14]5.178.90]"                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1590 - assertIsFalse "ip4.with.rightbracket.before.point@[123.145].178.90]"                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1591 - assertIsFalse "ip4.with.rightbracket.after.point@[123.145.]178.90]"                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1592 - assertIsFalse "ip4.with.rightbracket.before.start.bracket@][123.145.178.90]"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1593 - assertIsFalse "ip4.with.rightbracket.after.start.bracket@[]123.145.178.90]"                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1594 - assertIsFalse "ip4.with.rightbracket.before.end.bracket@[123.145.178.90]]"                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1595 - assertIsFalse "ip4.with.rightbracket.after.end.bracket@[123.145.178.90]]"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1596 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14()5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1597 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145().178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1598 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.()178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1599 - assertIsTrue  "ip4.with.empty.bracket.before.start.bracket@()[123.145.178.90]"                 =   2 =  OK 
   *  1600 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[()123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1601 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90()]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1602 - assertIsTrue  "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]()"                    =   2 =  OK 
   *  1603 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14{}5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1604 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145{}.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1605 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.{}178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1606 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@{}[123.145.178.90]"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1607 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[{}123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1608 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90{}]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1609 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]{}"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1610 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14[]5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1611 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145[].178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1612 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.[]178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1613 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@[][123.145.178.90]"                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1614 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[[]123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1615 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90[]]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1616 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90][]"                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1617 - assertIsFalse "ip4.with.empty.bracket.between.numbers@[123.14<>5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1618 - assertIsFalse "ip4.with.empty.bracket.before.point@[123.145<>.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1619 - assertIsFalse "ip4.with.empty.bracket.after.point@[123.145.<>178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1620 - assertIsFalse "ip4.with.empty.bracket.before.start.bracket@<>[123.145.178.90]"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1621 - assertIsFalse "ip4.with.empty.bracket.after.start.bracket@[<>123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1622 - assertIsFalse "ip4.with.empty.bracket.before.end.bracket@[123.145.178.90<>]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1623 - assertIsFalse "ip4.with.empty.bracket.after.end.bracket@[123.145.178.90]<>"                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  1624 - assertIsFalse "ip4.with.false.bracket1.between.numbers@[123.14)(5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1625 - assertIsFalse "ip4.with.false.bracket1.before.point@[123.145)(.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1626 - assertIsFalse "ip4.with.false.bracket1.after.point@[123.145.)(178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1627 - assertIsFalse "ip4.with.false.bracket1.before.start.bracket@)([123.145.178.90]"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1628 - assertIsFalse "ip4.with.false.bracket1.after.start.bracket@[)(123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1629 - assertIsFalse "ip4.with.false.bracket1.before.end.bracket@[123.145.178.90)(]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1630 - assertIsFalse "ip4.with.false.bracket1.after.end.bracket@[123.145.178.90])("                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1631 - assertIsFalse "ip4.with.false.bracket2.between.numbers@[123.14}{5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1632 - assertIsFalse "ip4.with.false.bracket2.before.point@[123.145}{.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1633 - assertIsFalse "ip4.with.false.bracket2.after.point@[123.145.}{178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1634 - assertIsFalse "ip4.with.false.bracket2.before.start.bracket@}{[123.145.178.90]"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1635 - assertIsFalse "ip4.with.false.bracket2.after.start.bracket@[}{123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1636 - assertIsFalse "ip4.with.false.bracket2.before.end.bracket@[123.145.178.90}{]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1637 - assertIsFalse "ip4.with.false.bracket2.after.end.bracket@[123.145.178.90]}{"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1638 - assertIsFalse "ip4.with.false.bracket4.between.numbers@[123.14><5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1639 - assertIsFalse "ip4.with.false.bracket4.before.point@[123.145><.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1640 - assertIsFalse "ip4.with.false.bracket4.after.point@[123.145.><178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1641 - assertIsFalse "ip4.with.false.bracket4.before.start.bracket@><[123.145.178.90]"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1642 - assertIsFalse "ip4.with.false.bracket4.after.start.bracket@[><123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1643 - assertIsFalse "ip4.with.false.bracket4.before.end.bracket@[123.145.178.90><]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1644 - assertIsFalse "ip4.with.false.bracket4.after.end.bracket@[123.145.178.90]><"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1645 - assertIsFalse "ip4.with.lower.than.between.numbers@[123.14<5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1646 - assertIsFalse "ip4.with.lower.than.before.point@[123.145<.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1647 - assertIsFalse "ip4.with.lower.than.after.point@[123.145.<178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1648 - assertIsFalse "ip4.with.lower.than.before.start.bracket@<[123.145.178.90]"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1649 - assertIsFalse "ip4.with.lower.than.after.start.bracket@[<123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1650 - assertIsFalse "ip4.with.lower.than.before.end.bracket@[123.145.178.90<]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1651 - assertIsFalse "ip4.with.lower.than.after.end.bracket@[123.145.178.90]<"                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1652 - assertIsFalse "ip4.with.greater.than.between.numbers@[123.14>5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1653 - assertIsFalse "ip4.with.greater.than.before.point@[123.145>.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1654 - assertIsFalse "ip4.with.greater.than.after.point@[123.145.>178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1655 - assertIsFalse "ip4.with.greater.than.before.start.bracket@>[123.145.178.90]"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1656 - assertIsFalse "ip4.with.greater.than.after.start.bracket@[>123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1657 - assertIsFalse "ip4.with.greater.than.before.end.bracket@[123.145.178.90>]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1658 - assertIsFalse "ip4.with.greater.than.after.end.bracket@[123.145.178.90]>"                      =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  1659 - assertIsFalse "ip4.with.tilde.between.numbers@[123.14~5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1660 - assertIsFalse "ip4.with.tilde.before.point@[123.145~.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1661 - assertIsFalse "ip4.with.tilde.after.point@[123.145.~178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1662 - assertIsFalse "ip4.with.tilde.before.start.bracket@~[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1663 - assertIsFalse "ip4.with.tilde.after.start.bracket@[~123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1664 - assertIsFalse "ip4.with.tilde.before.end.bracket@[123.145.178.90~]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1665 - assertIsFalse "ip4.with.tilde.after.end.bracket@[123.145.178.90]~"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1666 - assertIsFalse "ip4.with.xor.between.numbers@[123.14^5.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1667 - assertIsFalse "ip4.with.xor.before.point@[123.145^.178.90]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1668 - assertIsFalse "ip4.with.xor.after.point@[123.145.^178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1669 - assertIsFalse "ip4.with.xor.before.start.bracket@^[123.145.178.90]"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1670 - assertIsFalse "ip4.with.xor.after.start.bracket@[^123.145.178.90]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1671 - assertIsFalse "ip4.with.xor.before.end.bracket@[123.145.178.90^]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1672 - assertIsFalse "ip4.with.xor.after.end.bracket@[123.145.178.90]^"                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1673 - assertIsFalse "ip4.with.colon.between.numbers@[123.14:5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1674 - assertIsFalse "ip4.with.colon.before.point@[123.145:.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1675 - assertIsFalse "ip4.with.colon.after.point@[123.145.:178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1676 - assertIsFalse "ip4.with.colon.before.start.bracket@:[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1677 - assertIsFalse "ip4.with.colon.after.start.bracket@[:123.145.178.90]"                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1678 - assertIsFalse "ip4.with.colon.before.end.bracket@[123.145.178.90:]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1679 - assertIsFalse "ip4.with.colon.after.end.bracket@[123.145.178.90]:"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1680 - assertIsFalse "ip4.with.space.between.numbers@[123.14 5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1681 - assertIsFalse "ip4.with.space.before.point@[123.145 .178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1682 - assertIsFalse "ip4.with.space.after.point@[123.145. 178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1683 - assertIsFalse "ip4.with.space.before.start.bracket@ [123.145.178.90]"                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1684 - assertIsFalse "ip4.with.space.after.start.bracket@[ 123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1685 - assertIsFalse "ip4.with.space.before.end.bracket@[123.145.178.90 ]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1686 - assertIsFalse "ip4.with.space.after.end.bracket@[123.145.178.90] "                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1687 - assertIsFalse "ip4.with.comma.between.numbers@[123.14,5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1688 - assertIsFalse "ip4.with.comma.before.point@[123.145,.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1689 - assertIsFalse "ip4.with.comma.after.point@[123.145.,178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1690 - assertIsFalse "ip4.with.comma.before.start.bracket@,[123.145.178.90]"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1691 - assertIsFalse "ip4.with.comma.after.start.bracket@[,123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1692 - assertIsFalse "ip4.with.comma.before.end.bracket@[123.145.178.90,]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1693 - assertIsFalse "ip4.with.comma.after.end.bracket@[123.145.178.90],"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1694 - assertIsFalse "ip4.with.at.between.numbers@[123.14@5.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1695 - assertIsFalse "ip4.with.at.before.point@[123.145@.178.90]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1696 - assertIsFalse "ip4.with.at.after.point@[123.145.@178.90]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1697 - assertIsFalse "ip4.with.at.before.start.bracket@@[123.145.178.90]"                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  1698 - assertIsFalse "ip4.with.at.after.start.bracket@[@123.145.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1699 - assertIsFalse "ip4.with.at.before.end.bracket@[123.145.178.90@]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1700 - assertIsFalse "ip4.with.at.after.end.bracket@[123.145.178.90]@"                                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1701 - assertIsFalse "ip4.with.paragraph.between.numbers@[123.14§5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1702 - assertIsFalse "ip4.with.paragraph.before.point@[123.145§.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1703 - assertIsFalse "ip4.with.paragraph.after.point@[123.145.§178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1704 - assertIsFalse "ip4.with.paragraph.before.start.bracket@§[123.145.178.90]"                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1705 - assertIsFalse "ip4.with.paragraph.after.start.bracket@[§123.145.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1706 - assertIsFalse "ip4.with.paragraph.before.end.bracket@[123.145.178.90§]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1707 - assertIsFalse "ip4.with.paragraph.after.end.bracket@[123.145.178.90]§"                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1708 - assertIsFalse "ip4.with.double.quote.between.numbers@[123.14'5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1709 - assertIsFalse "ip4.with.double.quote.before.point@[123.145'.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1710 - assertIsFalse "ip4.with.double.quote.after.point@[123.145.'178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1711 - assertIsFalse "ip4.with.double.quote.before.start.bracket@'[123.145.178.90]"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1712 - assertIsFalse "ip4.with.double.quote.after.start.bracket@['123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1713 - assertIsFalse "ip4.with.double.quote.before.end.bracket@[123.145.178.90']"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1714 - assertIsFalse "ip4.with.double.quote.after.end.bracket@[123.145.178.90]'"                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1715 - assertIsFalse "ip4.with.forward.slash.between.numbers@[123.14/5.178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1716 - assertIsFalse "ip4.with.forward.slash.before.point@[123.145/.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1717 - assertIsFalse "ip4.with.forward.slash.after.point@[123.145./178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1718 - assertIsFalse "ip4.with.forward.slash.before.start.bracket@/[123.145.178.90]"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1719 - assertIsFalse "ip4.with.forward.slash.after.start.bracket@[/123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1720 - assertIsFalse "ip4.with.forward.slash.before.end.bracket@[123.145.178.90/]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1721 - assertIsFalse "ip4.with.forward.slash.after.end.bracket@[123.145.178.90]/"                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1722 - assertIsFalse "ip4.with.hyphen.between.numbers@[123.14-5.178.90]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1723 - assertIsFalse "ip4.with.hyphen.before.point@[123.145-.178.90]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1724 - assertIsFalse "ip4.with.hyphen.after.point@[123.145.-178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1725 - assertIsFalse "ip4.with.hyphen.before.start.bracket@-[123.145.178.90]"                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1726 - assertIsFalse "ip4.with.hyphen.after.start.bracket@[-123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1727 - assertIsFalse "ip4.with.hyphen.before.end.bracket@[123.145.178.90-]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1728 - assertIsFalse "ip4.with.hyphen.after.end.bracket@[123.145.178.90]-"                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1729 - assertIsFalse "ip4.with.empty.string1.between.numbers@[123.14\"\"5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1730 - assertIsFalse "ip4.with.empty.string1.before.point@[123.145\"\".178.90]"                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1731 - assertIsFalse "ip4.with.empty.string1.after.point@[123.145.\"\"178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1732 - assertIsFalse "ip4.with.empty.string1.before.start.bracket@\"\"[123.145.178.90]"               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1733 - assertIsFalse "ip4.with.empty.string1.after.start.bracket@[\"\"123.145.178.90]"                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1734 - assertIsFalse "ip4.with.empty.string1.before.end.bracket@[123.145.178.90\"\"]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1735 - assertIsFalse "ip4.with.empty.string1.after.end.bracket@[123.145.178.90]\"\""                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1736 - assertIsFalse "ip4.with.empty.string2.between.numbers@[123.14a\"\"b5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1737 - assertIsFalse "ip4.with.empty.string2.before.point@[123.145a\"\"b.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1738 - assertIsFalse "ip4.with.empty.string2.after.point@[123.145.a\"\"b178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1739 - assertIsFalse "ip4.with.empty.string2.before.start.bracket@a\"\"b[123.145.178.90]"             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1740 - assertIsFalse "ip4.with.empty.string2.after.start.bracket@[a\"\"b123.145.178.90]"              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1741 - assertIsFalse "ip4.with.empty.string2.before.end.bracket@[123.145.178.90a\"\"b]"               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1742 - assertIsFalse "ip4.with.empty.string2.after.end.bracket@[123.145.178.90]a\"\"b"                =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1743 - assertIsFalse "ip4.with.double.empty.string1.between.numbers@[123.14\"\"\"\"5.178.90]"         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1744 - assertIsFalse "ip4.with.double.empty.string1.before.point@[123.145\"\"\"\".178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1745 - assertIsFalse "ip4.with.double.empty.string1.after.point@[123.145.\"\"\"\"178.90]"             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1746 - assertIsFalse "ip4.with.double.empty.string1.before.start.bracket@\"\"\"\"[123.145.178.90]"    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1747 - assertIsFalse "ip4.with.double.empty.string1.after.start.bracket@[\"\"\"\"123.145.178.90]"     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1748 - assertIsFalse "ip4.with.double.empty.string1.before.end.bracket@[123.145.178.90\"\"\"\"]"      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1749 - assertIsFalse "ip4.with.double.empty.string1.after.end.bracket@[123.145.178.90]\"\"\"\""       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1750 - assertIsFalse "ip4.with.double.empty.string2.between.numbers@[123.14\"\".\"\"5.178.90]"        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1751 - assertIsFalse "ip4.with.double.empty.string2.before.point@[123.145\"\".\"\".178.90]"           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1752 - assertIsFalse "ip4.with.double.empty.string2.after.point@[123.145.\"\".\"\"178.90]"            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1753 - assertIsFalse "ip4.with.double.empty.string2.before.start.bracket@\"\".\"\"[123.145.178.90]"   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1754 - assertIsFalse "ip4.with.double.empty.string2.after.start.bracket@[\"\".\"\"123.145.178.90]"    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1755 - assertIsFalse "ip4.with.double.empty.string2.before.end.bracket@[123.145.178.90\"\".\"\"]"     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1756 - assertIsFalse "ip4.with.double.empty.string2.after.end.bracket@[123.145.178.90]\"\".\"\""      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1757 - assertIsFalse "ip4.with.number0.between.numbers@[123.1405.178.90]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1758 - assertIsFalse "ip4.with.number0.before.point@[123.1450.178.90]"                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1759 - assertIsFalse "ip4.with.number0.after.point@[123.145.0178.90]"                                 =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1760 - assertIsFalse "ip4.with.number0.before.start.bracket@0[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1761 - assertIsFalse "ip4.with.number0.after.start.bracket@[0123.145.178.90]"                         =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1762 - assertIsFalse "ip4.with.number0.before.end.bracket@[123.145.178.900]"                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1763 - assertIsFalse "ip4.with.number0.after.end.bracket@[123.145.178.90]0"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1764 - assertIsFalse "ip4.with.number9.between.numbers@[123.1495.178.90]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1765 - assertIsFalse "ip4.with.number9.before.point@[123.1459.178.90]"                                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1766 - assertIsFalse "ip4.with.number9.after.point@[123.145.9178.90]"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1767 - assertIsFalse "ip4.with.number9.before.start.bracket@9[123.145.178.90]"                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1768 - assertIsFalse "ip4.with.number9.after.start.bracket@[9123.145.178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1769 - assertIsFalse "ip4.with.number9.before.end.bracket@[123.145.178.909]"                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1770 - assertIsFalse "ip4.with.number9.after.end.bracket@[123.145.178.90]9"                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1771 - assertIsFalse "ip4.with.numbers.between.numbers@[123.1401234567895.178.90]"                    =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1772 - assertIsFalse "ip4.with.numbers.before.point@[123.1450123456789.178.90]"                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1773 - assertIsFalse "ip4.with.numbers.after.point@[123.145.0123456789178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1774 - assertIsFalse "ip4.with.numbers.before.start.bracket@0123456789[123.145.178.90]"               =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1775 - assertIsFalse "ip4.with.numbers.after.start.bracket@[0123456789123.145.178.90]"                =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1776 - assertIsFalse "ip4.with.numbers.before.end.bracket@[123.145.178.900123456789]"                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1777 - assertIsFalse "ip4.with.numbers.after.end.bracket@[123.145.178.90]0123456789"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1778 - assertIsFalse "ip4.with.byte.overflow.between.numbers@[123.149995.178.90]"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1779 - assertIsFalse "ip4.with.byte.overflow.before.point@[123.145999.178.90]"                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1780 - assertIsFalse "ip4.with.byte.overflow.after.point@[123.145.999178.90]"                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1781 - assertIsFalse "ip4.with.byte.overflow.before.start.bracket@999[123.145.178.90]"                =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1782 - assertIsFalse "ip4.with.byte.overflow.after.start.bracket@[999123.145.178.90]"                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1783 - assertIsFalse "ip4.with.byte.overflow.before.end.bracket@[123.145.178.90999]"                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1784 - assertIsFalse "ip4.with.byte.overflow.after.end.bracket@[123.145.178.90]999"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1785 - assertIsFalse "ip4.with.no.hex.number.between.numbers@[123.14xyz5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1786 - assertIsFalse "ip4.with.no.hex.number.before.point@[123.145xyz.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1787 - assertIsFalse "ip4.with.no.hex.number.after.point@[123.145.xyz178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1788 - assertIsFalse "ip4.with.no.hex.number.before.start.bracket@xyz[123.145.178.90]"                =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1789 - assertIsFalse "ip4.with.no.hex.number.after.start.bracket@[xyz123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1790 - assertIsFalse "ip4.with.no.hex.number.before.end.bracket@[123.145.178.90xyz]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1791 - assertIsFalse "ip4.with.no.hex.number.after.end.bracket@[123.145.178.90]xyz"                   =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1792 - assertIsFalse "ip4.with.slash.between.numbers@[123.14\5.178.90]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1793 - assertIsFalse "ip4.with.slash.before.point@[123.145\.178.90]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1794 - assertIsFalse "ip4.with.slash.after.point@[123.145.\178.90]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1795 - assertIsFalse "ip4.with.slash.before.start.bracket@\[123.145.178.90]"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1796 - assertIsFalse "ip4.with.slash.after.start.bracket@[\123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1797 - assertIsFalse "ip4.with.slash.before.end.bracket@[123.145.178.90\]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1798 - assertIsFalse "ip4.with.slash.after.end.bracket@[123.145.178.90]\"                             =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1799 - assertIsFalse "ip4.with.string.between.numbers@[123.14\"str\"5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1800 - assertIsFalse "ip4.with.string.before.point@[123.145\"str\".178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1801 - assertIsFalse "ip4.with.string.after.point@[123.145.\"str\"178.90]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1802 - assertIsFalse "ip4.with.string.before.start.bracket@\"str\"[123.145.178.90]"                   =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  1803 - assertIsFalse "ip4.with.string.after.start.bracket@[\"str\"123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1804 - assertIsFalse "ip4.with.string.before.end.bracket@[123.145.178.90\"str\"]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1805 - assertIsFalse "ip4.with.string.after.end.bracket@[123.145.178.90]\"str\""                      =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1806 - assertIsFalse "ip4.with.comment.between.numbers@[123.14(comment)5.178.90]"                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1807 - assertIsFalse "ip4.with.comment.before.point@[123.145(comment).178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1808 - assertIsFalse "ip4.with.comment.after.point@[123.145.(comment)178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1809 - assertIsTrue  "ip4.with.comment.before.start.bracket@(comment)[123.145.178.90]"                =   2 =  OK 
   *  1810 - assertIsFalse "ip4.with.comment.after.start.bracket@[(comment)123.145.178.90]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1811 - assertIsFalse "ip4.with.comment.before.end.bracket@[123.145.178.90(comment)]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1812 - assertIsTrue  "ip4.with.comment.after.end.bracket@[123.145.178.90](comment)"                   =   2 =  OK 
   *  1813 - assertIsTrue  "email@[123.123.123.123]"                                                        =   2 =  OK 
   *  1814 - assertIsTrue  "email@123.123.123.123"                                                          =   2 =  OK 
   *  1815 - assertIsTrue  "ab@88.120.150.021"                                                              =   2 =  OK 
   *  1816 - assertIsTrue  "ab@88.120.150.01"                                                               =   2 =  OK 
   *  1817 - assertIsTrue  "ab@188.120.150.10"                                                              =   2 =  OK 
   *  1818 - assertIsTrue  "ab@120.25.254.120"                                                              =   2 =  OK 
   *  1819 - assertIsTrue  "ab@1.0.0.10"                                                                    =   2 =  OK 
   *  1820 - assertIsTrue  "ab@01.120.150.1"                                                                =   2 =  OK 
   *  1821 - assertIsFalse "email@[3.256.255.23]"                                                           =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1822 - assertIsFalse "email@[123.123.123].123"                                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  1823 - assertIsFalse "email@[123.123.123.123"                                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1824 - assertIsFalse "email@[12.34.44.56"                                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1825 - assertIsFalse "email@[1.1.23.5f]"                                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1826 - assertIsFalse "email@14.44.56.34]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1827 - assertIsFalse "email@123.123.[123.123]"                                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1828 - assertIsFalse "email@123.123.123.123]"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1829 - assertIsFalse "email@111.222.333.44444"                                                        =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1830 - assertIsFalse "email@111.222.333.256"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1831 - assertIsFalse "email@111.222.333"                                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  1832 - assertIsFalse "email@-example.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1833 - assertIsFalse "email.@example.com"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  1834 - assertIsFalse "email..email@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  1835 - assertIsFalse ".email@example.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   * 
   * ---- ip4 without brackets --------------------------------------------------------------------------------------------------------
   * 
   *  1836 - assertIsTrue  "ip4.without.brackets.ok1@127.0.0.1"                                             =   2 =  OK 
   *  1837 - assertIsTrue  "ip4.without.brackets.ok2@0.0.0.0"                                               =   2 =  OK 
   *  1838 - assertIsFalse "ip4.without.brackets.but.with.space.at.end@127.0.0.1 "                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1839 - assertIsFalse "ip4.without.brackets.byte.overflow@127.0.999.1"                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  1840 - assertIsFalse "ip4.without.brackets.more.than.three.numbers1@127.0.0001.1"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1841 - assertIsFalse "ip4.without.brackets.more.than.three.numbers2@127.0.1234.1"                     =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  1842 - assertIsFalse "ip4.without.brackets.negative.number@127.0.-1.1"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1843 - assertIsFalse "ip4.without.brackets.point.error1@127.0..0.1"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  1844 - assertIsFalse "ip4.without.brackets.point.error1@127...1"                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1845 - assertIsFalse "ip4.without.brackets.error.bracket@127.0.1.1[]"                                 =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  1846 - assertIsFalse "ip4.without.brackets.point.error2@127001"                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  1847 - assertIsFalse "ip4.without.brackets.point.error3@127.0.0."                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  1848 - assertIsFalse "ip4.without.brackets.character.error@127.0.A.1"                                 =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1849 - assertIsFalse "ip4.without.brackets.error.double.ip4@127.0.0.1.127.0.0.1"                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  1850 - assertIsTrue  "ip4.without.brackets.normal.tld1@127.0.0.1.com"                                 =   0 =  OK 
   *  1851 - assertIsTrue  "ip4.without.brackets.normal.tld2@127.0.99.1.com"                                =   0 =  OK 
   *  1852 - assertIsTrue  "ip4.without.brackets.normal.tld3@127.0.A.1.com"                                 =   0 =  OK 
   * 
   * ---- IP V6 -----------------------------------------------------------------------------------------------------------------------
   * 
   *  1853 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                                     =   4 =  OK 
   *  1854 - assertIsFalse "ABC.DEF@[IP"                                                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1855 - assertIsFalse "ABC.DEF@[IPv6]"                                                                 =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  1856 - assertIsFalse "ABC.DEF@[IPv6:]"                                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1857 - assertIsFalse "ABC.DEF@[IPv6:"                                                                 =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1858 - assertIsFalse "ABC.DEF@[IPv6::]"                                                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1859 - assertIsFalse "ABC.DEF@[IPv6::"                                                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1860 - assertIsFalse "ABC.DEF@[IPv6:::::...]"                                                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1861 - assertIsFalse "ABC.DEF@[IPv6:::::..."                                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1862 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1863 - assertIsFalse "ABC.DEF@[IPv6:1]"                                                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1864 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                                             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1865 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                                           =   4 =  OK 
   *  1866 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                                         =   4 =  OK 
   *  1867 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                                      =   4 =  OK 
   *  1868 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                                     =   4 =  OK 
   *  1869 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                                     =   4 =  OK 
   *  1870 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                                                   =   4 =  OK 
   *  1871 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                                    =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1872 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                                                 =   4 =  OK 
   *  1873 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                                               =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  1874 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                                         =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  1875 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                                       =   4 =  OK 
   *  1876 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1877 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1878 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                                           =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  1879 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1880 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1881 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                                                  =   4 =  OK 
   *  1882 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1883 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1884 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1885 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  1886 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                                                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  1887 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  1888 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1889 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                                                =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  1890 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                                                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1891 - assertIsFalse "ABC.DEF@([IPv6:1:2:3:4:5:6])"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  1892 - assertIsFalse "ABC.DEF@[IPv6:1:-2:3:4:5:]"                                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1893 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:2;2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1894 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22;:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1895 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22:;3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1896 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22:3:4:5:6:7;]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1897 - assertIsFalse "ip.v6.with.semicolon@[IPv6:1:22:3:4:5:6:7];"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1898 - assertIsFalse "ip.v6.with.semicolon@;[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  1899 - assertIsFalse "ip.v6.with.semicolon@[;IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1900 - assertIsFalse "ip.v6.with.dot@[IPv6:1:2.2:3:4:5:6:7]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1901 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22.:3:4:5:6:7]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1902 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:.3:4:5:6:7]"                                          =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  1903 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7.]"                                          =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1904 - assertIsFalse "ip.v6.with.dot@[IPv6:1:22:3:4:5:6:7]."                                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1905 - assertIsFalse "ip.v6.with.dot@.[IPv6:1:22:3:4:5:6:7]"                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1906 - assertIsFalse "ip.v6.with.dot@[.IPv6:1:22:3:4:5:6:7]"                                          =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1907 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:2..2:3:4:5:6:7]"                                  =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1908 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22..:3:4:5:6:7]"                                  =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1909 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:..3:4:5:6:7]"                                  =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  1910 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7..]"                                  =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  1911 - assertIsFalse "ip.v6.with.double.dot@[IPv6:1:22:3:4:5:6:7].."                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1912 - assertIsFalse "ip.v6.with.double.dot@..[IPv6:1:22:3:4:5:6:7]"                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  1913 - assertIsFalse "ip.v6.with.double.dot@[..IPv6:1:22:3:4:5:6:7]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  1914 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1915 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1916 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1917 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1918 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7]&"                                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1919 - assertIsFalse "ip.v6.with.amp@&[IPv6:1:22:3:4:5:6:7]"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1920 - assertIsFalse "ip.v6.with.amp@[&IPv6:1:22:3:4:5:6:7]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1921 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1922 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1923 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1924 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1925 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7]*"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1926 - assertIsFalse "ip.v6.with.asterisk@*[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1927 - assertIsFalse "ip.v6.with.asterisk@[*IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1928 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1929 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1930 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1931 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1932 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7]_"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1933 - assertIsFalse "ip.v6.with.underscore@_[IPv6:1:22:3:4:5:6:7]"                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  1934 - assertIsFalse "ip.v6.with.underscore@[_IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1935 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1936 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1937 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1938 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1939 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7]$"                                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1940 - assertIsFalse "ip.v6.with.dollar@$[IPv6:1:22:3:4:5:6:7]"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1941 - assertIsFalse "ip.v6.with.dollar@[$IPv6:1:22:3:4:5:6:7]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1942 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1943 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1944 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1945 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1946 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7]="                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1947 - assertIsFalse "ip.v6.with.equality@=[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1948 - assertIsFalse "ip.v6.with.equality@[=IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1949 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1950 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1951 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1952 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1953 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7]!"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1954 - assertIsFalse "ip.v6.with.exclamation@![IPv6:1:22:3:4:5:6:7]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1955 - assertIsFalse "ip.v6.with.exclamation@[!IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1956 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1957 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1958 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1959 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1960 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7]?"                                     =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1961 - assertIsFalse "ip.v6.with.question@?[IPv6:1:22:3:4:5:6:7]"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1962 - assertIsFalse "ip.v6.with.question@[?IPv6:1:22:3:4:5:6:7]"                                     =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1963 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1964 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1965 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1966 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1967 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7]`"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1968 - assertIsFalse "ip.v6.with.grave-accent@`[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1969 - assertIsFalse "ip.v6.with.grave-accent@[`IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1970 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1971 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1972 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1973 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1974 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7]#"                                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1975 - assertIsFalse "ip.v6.with.hash@#[IPv6:1:22:3:4:5:6:7]"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1976 - assertIsFalse "ip.v6.with.hash@[#IPv6:1:22:3:4:5:6:7]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1977 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1978 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1979 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1980 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1981 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7]%"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1982 - assertIsFalse "ip.v6.with.percentage@%[IPv6:1:22:3:4:5:6:7]"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1983 - assertIsFalse "ip.v6.with.percentage@[%IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1984 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1985 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1986 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1987 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1988 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7]|"                                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1989 - assertIsFalse "ip.v6.with.pipe@|[IPv6:1:22:3:4:5:6:7]"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1990 - assertIsFalse "ip.v6.with.pipe@[|IPv6:1:22:3:4:5:6:7]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1991 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1992 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1993 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1994 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1995 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7]+"                                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  1996 - assertIsFalse "ip.v6.with.plus@+[IPv6:1:22:3:4:5:6:7]"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  1997 - assertIsFalse "ip.v6.with.plus@[+IPv6:1:22:3:4:5:6:7]"                                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1998 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  1999 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2000 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2001 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2002 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]{"                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2003 - assertIsFalse "ip.v6.with.leftbracket@{[IPv6:1:22:3:4:5:6:7]"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2004 - assertIsFalse "ip.v6.with.leftbracket@[{IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2005 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2006 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2007 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2008 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2009 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]}"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2010 - assertIsFalse "ip.v6.with.rightbracket@}[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2011 - assertIsFalse "ip.v6.with.rightbracket@[}IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2012 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2013 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2014 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2015 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2016 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]("                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2017 - assertIsFalse "ip.v6.with.leftbracket@([IPv6:1:22:3:4:5:6:7]"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2018 - assertIsFalse "ip.v6.with.leftbracket@[(IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2019 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2020 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2021 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2022 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2023 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7])"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2024 - assertIsFalse "ip.v6.with.rightbracket@)[IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2025 - assertIsFalse "ip.v6.with.rightbracket@[)IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2026 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2027 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2028 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2029 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2030 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7]["                                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2031 - assertIsFalse "ip.v6.with.leftbracket@[[IPv6:1:22:3:4:5:6:7]"                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2032 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  2033 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"                                 =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  2034 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2035 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2036 - assertIsFalse "ip.v6.with.rightbracket@][IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2037 - assertIsFalse "ip.v6.with.rightbracket@[]IPv6:1:22:3:4:5:6:7]"                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  2038 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2()2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2039 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22():3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2040 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:()3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2041 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7()]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2042 - assertIsTrue  "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]()"                               =   4 =  OK 
   *  2043 - assertIsTrue  "ip.v6.with.empty.bracket@()[IPv6:1:22:3:4:5:6:7]"                               =   4 =  OK 
   *  2044 - assertIsFalse "ip.v6.with.empty.bracket@[()IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2045 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2{}2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2046 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22{}:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2047 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:{}3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2048 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7{}]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2049 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]{}"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2050 - assertIsFalse "ip.v6.with.empty.bracket@{}[IPv6:1:22:3:4:5:6:7]"                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2051 - assertIsFalse "ip.v6.with.empty.bracket@[{}IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2052 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2[]2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2053 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22[]:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2054 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:[]3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2055 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7[]]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2056 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7][]"                               =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2057 - assertIsFalse "ip.v6.with.empty.bracket@[][IPv6:1:22:3:4:5:6:7]"                               =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  2058 - assertIsFalse "ip.v6.with.empty.bracket@[[]IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2059 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:2<>2:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2060 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22<>:3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2061 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:<>3:4:5:6:7]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2062 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7<>]"                               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2063 - assertIsFalse "ip.v6.with.empty.bracket@[IPv6:1:22:3:4:5:6:7]<>"                               =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2064 - assertIsFalse "ip.v6.with.empty.bracket@<>[IPv6:1:22:3:4:5:6:7]"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2065 - assertIsFalse "ip.v6.with.empty.bracket@[<>IPv6:1:22:3:4:5:6:7]"                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2066 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:2)(2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2067 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22)(:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2068 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:)(3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2069 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7)(]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2070 - assertIsFalse "ip.v6.with.false.bracket1@[IPv6:1:22:3:4:5:6:7])("                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2071 - assertIsFalse "ip.v6.with.false.bracket1@)([IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2072 - assertIsFalse "ip.v6.with.false.bracket1@[)(IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2073 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:2}{2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2074 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22}{:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2075 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:}{3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2076 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7}{]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2077 - assertIsFalse "ip.v6.with.false.bracket2@[IPv6:1:22:3:4:5:6:7]}{"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2078 - assertIsFalse "ip.v6.with.false.bracket2@}{[IPv6:1:22:3:4:5:6:7]"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2079 - assertIsFalse "ip.v6.with.false.bracket2@[}{IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2080 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:2><2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2081 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22><:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2082 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:><3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2083 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7><]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2084 - assertIsFalse "ip.v6.with.false.bracket4@[IPv6:1:22:3:4:5:6:7]><"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2085 - assertIsFalse "ip.v6.with.false.bracket4@><[IPv6:1:22:3:4:5:6:7]"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2086 - assertIsFalse "ip.v6.with.false.bracket4@[><IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2087 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:2<2:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2088 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22<:3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2089 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:<3:4:5:6:7]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2090 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7<]"                                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2091 - assertIsFalse "ip.v6.with.lower.than@[IPv6:1:22:3:4:5:6:7]<"                                   =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2092 - assertIsFalse "ip.v6.with.lower.than@<[IPv6:1:22:3:4:5:6:7]"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2093 - assertIsFalse "ip.v6.with.lower.than@[<IPv6:1:22:3:4:5:6:7]"                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2094 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:2>2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2095 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22>:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2096 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:>3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2097 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7>]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2098 - assertIsFalse "ip.v6.with.greater.than@[IPv6:1:22:3:4:5:6:7]>"                                 =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  2099 - assertIsFalse "ip.v6.with.greater.than@>[IPv6:1:22:3:4:5:6:7]"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2100 - assertIsFalse "ip.v6.with.greater.than@[>IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2101 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2102 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2103 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2104 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2105 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7]~"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2106 - assertIsFalse "ip.v6.with.tilde@~[IPv6:1:22:3:4:5:6:7]"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2107 - assertIsFalse "ip.v6.with.tilde@[~IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2108 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2109 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2110 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2111 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2112 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7]^"                                          =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2113 - assertIsFalse "ip.v6.with.xor@^[IPv6:1:22:3:4:5:6:7]"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2114 - assertIsFalse "ip.v6.with.xor@[^IPv6:1:22:3:4:5:6:7]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2115 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:2:2:3:4:5:6:7]"                                        =   4 =  OK 
   *  2116 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                        =   4 =  OK 
   *  2117 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22::3:4:5:6:7]"                                        =   4 =  OK 
   *  2118 - assertIsTrue  "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7:]"                                        =   4 =  OK 
   *  2119 - assertIsFalse "ip.v6.with.colon@[IPv6:1:22:3:4:5:6:7]:"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2120 - assertIsFalse "ip.v6.with.colon@:[IPv6:1:22:3:4:5:6:7]"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2121 - assertIsFalse "ip.v6.with.colon@[:IPv6:1:22:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2122 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2123 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2124 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2125 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2126 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7] "                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2127 - assertIsFalse "ip.v6.with.space@ [IPv6:1:22:3:4:5:6:7]"                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2128 - assertIsFalse "ip.v6.with.space@[ IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2129 - assertIsFalse "ip.v6.with.comma@[IPv6:1:2,2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2130 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22,:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2131 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:,3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2132 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7,]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2133 - assertIsFalse "ip.v6.with.comma@[IPv6:1:22:3:4:5:6:7],"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2134 - assertIsFalse "ip.v6.with.comma@,[IPv6:1:22:3:4:5:6:7]"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2135 - assertIsFalse "ip.v6.with.comma@[,IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2136 - assertIsFalse "ip.v6.with.at@[IPv6:1:2@2:3:4:5:6:7]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2137 - assertIsFalse "ip.v6.with.at@[IPv6:1:22@:3:4:5:6:7]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2138 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:@3:4:5:6:7]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2139 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7@]"                                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2140 - assertIsFalse "ip.v6.with.at@[IPv6:1:22:3:4:5:6:7]@"                                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2141 - assertIsFalse "ip.v6.with.at@@[IPv6:1:22:3:4:5:6:7]"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2142 - assertIsFalse "ip.v6.with.at@[@IPv6:1:22:3:4:5:6:7]"                                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2143 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:2§2:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2144 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22§:3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2145 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:§3:4:5:6:7]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2146 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7§]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2147 - assertIsFalse "ip.v6.with.paragraph@[IPv6:1:22:3:4:5:6:7]§"                                    =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2148 - assertIsFalse "ip.v6.with.paragraph@§[IPv6:1:22:3:4:5:6:7]"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2149 - assertIsFalse "ip.v6.with.paragraph@[§IPv6:1:22:3:4:5:6:7]"                                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2150 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:2'2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2151 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22':3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2152 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:'3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2153 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7']"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2154 - assertIsFalse "ip.v6.with.double.quote@[IPv6:1:22:3:4:5:6:7]'"                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2155 - assertIsFalse "ip.v6.with.double.quote@'[IPv6:1:22:3:4:5:6:7]"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2156 - assertIsFalse "ip.v6.with.double.quote@['IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2157 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:2/2:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2158 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22/:3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2159 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:/3:4:5:6:7]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2160 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7/]"                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2161 - assertIsFalse "ip.v6.with.forward.slash@[IPv6:1:22:3:4:5:6:7]/"                                =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2162 - assertIsFalse "ip.v6.with.forward.slash@/[IPv6:1:22:3:4:5:6:7]"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2163 - assertIsFalse "ip.v6.with.forward.slash@[/IPv6:1:22:3:4:5:6:7]"                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2164 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:2-2:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2165 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22-:3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2166 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:-3:4:5:6:7]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2167 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7-]"                                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2168 - assertIsFalse "ip.v6.with.hyphen@[IPv6:1:22:3:4:5:6:7]-"                                       =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2169 - assertIsFalse "ip.v6.with.hyphen@-[IPv6:1:22:3:4:5:6:7]"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2170 - assertIsFalse "ip.v6.with.hyphen@[-IPv6:1:22:3:4:5:6:7]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2171 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:2\"\"2:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2172 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22\"\":3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2173 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:\"\"3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2174 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2175 - assertIsFalse "ip.v6.with.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\""                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2176 - assertIsFalse "ip.v6.with.empty.string1@\"\"[IPv6:1:22:3:4:5:6:7]"                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2177 - assertIsFalse "ip.v6.with.empty.string1@[\"\"IPv6:1:22:3:4:5:6:7]"                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2178 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:2a\"\"b2:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2179 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22a\"\"b:3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2180 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:a\"\"b3:4:5:6:7]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2181 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7a\"\"b]"                           =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2182 - assertIsFalse "ip.v6.with.empty.string2@[IPv6:1:22:3:4:5:6:7]a\"\"b"                           =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2183 - assertIsFalse "ip.v6.with.empty.string2@a\"\"b[IPv6:1:22:3:4:5:6:7]"                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2184 - assertIsFalse "ip.v6.with.empty.string2@[a\"\"bIPv6:1:22:3:4:5:6:7]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2185 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:2\"\"\"\"2:3:4:5:6:7]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2186 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22\"\"\"\":3:4:5:6:7]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2187 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:\"\"\"\"3:4:5:6:7]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2188 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7\"\"\"\"]"                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2189 - assertIsFalse "ip.v6.with.double.empty.string1@[IPv6:1:22:3:4:5:6:7]\"\"\"\""                  =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2190 - assertIsFalse "ip.v6.with.double.empty.string1@\"\"\"\"[IPv6:1:22:3:4:5:6:7]"                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2191 - assertIsFalse "ip.v6.with.double.empty.string1@[\"\"\"\"IPv6:1:22:3:4:5:6:7]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2192 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:2\"\".\"\"2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2193 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22\"\".\"\":3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2194 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:\"\".\"\"3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2195 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7\"\".\"\"]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2196 - assertIsFalse "ip.v6.with.double.empty.string2@[IPv6:1:22:3:4:5:6:7]\"\".\"\""                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2197 - assertIsFalse "ip.v6.with.double.empty.string2@\"\".\"\"[IPv6:1:22:3:4:5:6:7]"                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2198 - assertIsFalse "ip.v6.with.double.empty.string2@[\"\".\"\"IPv6:1:22:3:4:5:6:7]"                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2199 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:202:3:4:5:6:7]"                                      =   4 =  OK 
   *  2200 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:220:3:4:5:6:7]"                                      =   4 =  OK 
   *  2201 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:03:4:5:6:7]"                                      =   4 =  OK 
   *  2202 - assertIsTrue  "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:70]"                                      =   4 =  OK 
   *  2203 - assertIsFalse "ip.v6.with.number0@[IPv6:1:22:3:4:5:6:7]0"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2204 - assertIsFalse "ip.v6.with.number0@0[IPv6:1:22:3:4:5:6:7]"                                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2205 - assertIsFalse "ip.v6.with.number0@[0IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2206 - assertIsFalse "ip.v6.with.number9@[IPv6:1:292:3:4:5:6:7]"                                      =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  2207 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:229:3:4:5:6:7]"                                      =   4 =  OK 
   *  2208 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:93:4:5:6:7]"                                      =   4 =  OK 
   *  2209 - assertIsTrue  "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:79]"                                      =   4 =  OK 
   *  2210 - assertIsFalse "ip.v6.with.number9@[IPv6:1:22:3:4:5:6:7]9"                                      =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2211 - assertIsFalse "ip.v6.with.number9@9[IPv6:1:22:3:4:5:6:7]"                                      =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2212 - assertIsFalse "ip.v6.with.number9@[9IPv6:1:22:3:4:5:6:7]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2213 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:201234567892:3:4:5:6:7]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2214 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:220123456789:3:4:5:6:7]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2215 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:01234567893:4:5:6:7]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2216 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:70123456789]"                             =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2217 - assertIsFalse "ip.v6.with.numbers@[IPv6:1:22:3:4:5:6:7]0123456789"                             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2218 - assertIsFalse "ip.v6.with.numbers@0123456789[IPv6:1:22:3:4:5:6:7]"                             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2219 - assertIsFalse "ip.v6.with.numbers@[0123456789IPv6:1:22:3:4:5:6:7]"                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  2220 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:29992:3:4:5:6:7]"                              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2221 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22999:3:4:5:6:7]"                              =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2222 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:9993:4:5:6:7]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  2223 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7999]"                              =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  2224 - assertIsFalse "ip.v6.with.byte.overflow@[IPv6:1:22:3:4:5:6:7]999"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2225 - assertIsFalse "ip.v6.with.byte.overflow@999[IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2226 - assertIsFalse "ip.v6.with.byte.overflow@[999IPv6:1:22:3:4:5:6:7]"                              =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2227 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:2xyz2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2228 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22xyz:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2229 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:xyz3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2230 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7xyz]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2231 - assertIsFalse "ip.v6.with.no.hex.number@[IPv6:1:22:3:4:5:6:7]xyz"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2232 - assertIsFalse "ip.v6.with.no.hex.number@xyz[IPv6:1:22:3:4:5:6:7]"                              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2233 - assertIsFalse "ip.v6.with.no.hex.number@[xyzIPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2234 - assertIsFalse "ip.v6.with.slash@[IPv6:1:2\2:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2235 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22\:3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2236 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:\3:4:5:6:7]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2237 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7\]"                                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2238 - assertIsFalse "ip.v6.with.slash@[IPv6:1:22:3:4:5:6:7]\"                                        =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2239 - assertIsFalse "ip.v6.with.slash@\[IPv6:1:22:3:4:5:6:7]"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2240 - assertIsFalse "ip.v6.with.slash@[\IPv6:1:22:3:4:5:6:7]"                                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2241 - assertIsFalse "ip.v6.with.string@[IPv6:1:2\"str\"2:3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2242 - assertIsFalse "ip.v6.with.string@[IPv6:1:22\"str\":3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2243 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:\"str\"3:4:5:6:7]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2244 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7\"str\"]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2245 - assertIsFalse "ip.v6.with.string@[IPv6:1:22:3:4:5:6:7]\"str\""                                 =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  2246 - assertIsFalse "ip.v6.with.string@\"str\"[IPv6:1:22:3:4:5:6:7]"                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2247 - assertIsFalse "ip.v6.with.string@[\"str\"IPv6:1:22:3:4:5:6:7]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2248 - assertIsFalse "ip.v6.with.comment@[IPv6:1:2(comment)2:3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2249 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22(comment):3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2250 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:(comment)3:4:5:6:7]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2251 - assertIsFalse "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7(comment)]"                              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2252 - assertIsTrue  "ip.v6.with.comment@[IPv6:1:22:3:4:5:6:7](comment)"                              =   4 =  OK 
   *  2253 - assertIsTrue  "ip.v6.with.comment@(comment)[IPv6:1:22:3:4:5:6:7]"                              =   4 =  OK 
   *  2254 - assertIsFalse "ip.v6.with.comment@[(comment)IPv6:1:22:3:4:5:6:7]"                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2255 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"                         =   4 =  OK 
   *  2256 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"                         =   4 =  OK 
   *  2257 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"                         =   4 =  OK 
   *  2258 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                                        =   4 =  OK 
   *  2259 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                                         =   4 =  OK 
   *  2260 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2261 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"                        =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  2262 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"                        =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2263 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"                       =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   * ---- IP V4 embedded in IP V6 -----------------------------------------------------------------------------------------------------
   * 
   *  2264 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                                                 =   4 =  OK 
   *  2265 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                                                 =   4 =  OK 
   *  2266 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                                     =   4 =  OK 
   *  2267 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                                     =   4 =  OK 
   *  2268 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  2269 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                                                  =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  2270 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  2271 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                                       =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2272 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                                     =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  2273 - assertIsFalse "ABC.DEF@[IPv6::FFFF:-127.0.0.1]"                                                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2274 - assertIsFalse "ABC.DEF@[IPv6::FFFF:127.0.-0.1]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2275 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.999]"                                               =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2276 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.0.0001]"                                              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  2277 - assertIsFalse "ABC.DEF@[IPv6::ffff:127.0.XYZ.1]"                                               =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   * 
   * ---- Strings ---------------------------------------------------------------------------------------------------------------------
   * 
   *  2278 - assertIsTrue  "\"local.part.only.string\"@domain.com"                                          =   1 =  OK 
   *  2279 - assertIsTrue  "\"&lt; &clubs; &diams; &hearts; &spades; experiment &gt;\"@domain.com"          =   1 =  OK 
   *  2280 - assertIsTrue  "\"local.part\".\"two.strings\"@domain.com"                                      =   1 =  OK 
   *  2281 - assertIsFalse "-\"hyphen.before.string\"@domain.com"                                           = 140 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge -"
   *  2282 - assertIsFalse "\"hyphen.after.string\"-.\"string2\"@domain.com"                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2283 - assertIsFalse "\"hyphen.before.string2\".-\"string2\"@domain.com"                              =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2284 - assertIsFalse ".\"point.before.string\".\"string2\"@domain.com"                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2285 - assertIsTrue  "\"space in string\"@domain.com"                                                 =   1 =  OK 
   *  2286 - assertIsTrue  "\"at.sign@in.string\"@domain.com"                                               =   1 =  OK 
   *  2287 - assertIsTrue  "\"escaped.qoute.in\\"string\"@domain.com"                                       =   1 =  OK 
   *  2288 - assertIsTrue  "\"escaped.at.sign\@in.string\"@domain.com"                                      =   1 =  OK 
   *  2289 - assertIsTrue  "\"escaped.sign.'in.string\"@domain.com"                                         =   1 =  OK 
   *  2290 - assertIsTrue  "\"escaped.back.slash\\in.string\"@domain.com"                                   =   1 =  OK 
   *  2291 - assertIsFalse "\"\"@empty.string.domain.com"                                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2292 - assertIsFalse "\"missplaced.end.of.string@do\"main.com"                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2293 - assertIsFalse "domain.part.is.string@\"domain.com\""                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2294 - assertIsFalse "not.closed.string.in.domain.part.version1@\"domain.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2295 - assertIsFalse "not.closed.string.in.domain.part.version2@do\"main.com"                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2296 - assertIsFalse "not.closed.string.in.domain.part.version3@domain.com\""                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2297 - assertIsFalse "string.in.domain.part4@do\"main.com\""                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2298 - assertIsFalse "string.in.domain.part5@do\"main.com"                                            =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2299 - assertIsFalse "embedded.string.in.domain.part@do\"ma\"in.com"                                  =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2300 - assertIsFalse "\"@missplaced.start.of.string"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2301 - assertIsFalse "no.at.sign.and.no.domain.part.\""                                               =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2302 - assertIsFalse "domain.part.is.empty.string@\"\""                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2303 - assertIsFalse "\"no.email.adress.only.string\""                                                =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2304 - assertIsFalse "no.email.adress\"with.string.start"                                             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2305 - assertIsFalse "string.starts.before.at.sign\"@domain.com"                                      =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2306 - assertIsFalse "string.starts.before.at.sign\"but.with.caracters.before.at.sign@domain.com"     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2307 - assertIsFalse "ABC.DEF.\"@GHI.DE"                                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2308 - assertIsFalse "\"\".email.starts.with.empty.string@domain.com"                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2309 - assertIsTrue  "\"string.with.double..point\"@domain.com"                                       =   1 =  OK 
   *  2310 - assertIsTrue  "string.with.\"(comment)\".in.string@domain.com"                                 =   1 =  OK 
   *  2311 - assertIsTrue  "\"string.with.\\".\\".point\"@domain.com"                                       =   1 =  OK 
   *  2312 - assertIsTrue  "\"string.with.embedded.empty.\\"\\".string\"@domain.com"                        =   1 =  OK 
   *  2313 - assertIsTrue  "\"embedded.string.with.space.and.escaped.\\" \@ \\".at.sign\"@domain.com"       =   1 =  OK 
   *  2314 - assertIsFalse "\"string.is.not.closed@domain.com"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2315 - assertIsFalse "\"whole.email.adress.is.string@domain.com\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2316 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                                            =   1 =  OK 
   *  2317 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                                            =   1 =  OK 
   *  2318 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                                     =   1 =  OK 
   *  2319 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                                     =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2320 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
   *  2321 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2322 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2323 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2324 - assertIsFalse "A\"B\"C.D\"E\"F@GHI.DE"                                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2325 - assertIsFalse "ABC.DEF@G\"H\"I.DE"                                                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2326 - assertIsFalse "\"\".\"\".ABC.DEF@GHI.DE"                                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2327 - assertIsFalse "\"\"\"\"ABC.DEF@GHI.DE"                                                         =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2328 - assertIsFalse "AB\"\"\"\"C.DEF@GHI.DE"                                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2329 - assertIsFalse "ABC.DEF@G\"\"\"\"HI.DE"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2330 - assertIsFalse "ABC.DEF@GHI.D\"\"\"\"E"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2331 - assertIsFalse "ABC.DEF@GHI.D\"\".\"\"E"                                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2332 - assertIsFalse "ABC.DEF@GHI.\"\"\"\"DE"                                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2333 - assertIsFalse "ABC.DEF@GHI.\"\".\"\"DE"                                                        =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2334 - assertIsFalse "ABC.DEF@GHI.D\"\"E"                                                             =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
   *  2335 - assertIsFalse "0\"00.000\"@domain.com"                                                         =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2336 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                                           =   1 =  OK 
   *  2337 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2338 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                                                 =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2339 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2340 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"                          =   1 =  OK 
   *  2341 - assertIsTrue  "\"ABC \\"\\\\" !\".DEF@GHI.DE"                                                  =   1 =  OK 
   * 
   * ---- Comments --------------------------------------------------------------------------------------------------------------------
   * 
   *  2342 - assertIsFalse "escape.character.at.input.end@domain.com (comment \"                            =  96 =  OK    Kommentar: Escape-Zeichen nicht am Ende der Eingabe
   *  2343 - assertIsTrue  "(comment)local.part.with.comment.at.start@domain.com"                           =   6 =  OK 
   *  2344 - assertIsTrue  "(comment \\"string1\\" \\"string2) is.not.closed@domain.com"                    =   6 =  OK 
   *  2345 - assertIsTrue  "(comment) local.part.with.space.after.comment.at.start@domain.com"              =   6 =  OK 
   *  2346 - assertIsFalse "(comment)at.start.and.end@domain.com(comment end)"                              =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2347 - assertIsFalse "(two.comments)in.the(local.part)@domain.com"                                    =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2348 - assertIsFalse "(nested(comment))in.the.local.part@domain.com"                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2349 - assertIsTrue  "local.part.with.comment.before(at.sign)@domain.com"                             =   6 =  OK 
   *  2350 - assertIsFalse "local.part.with.comment.before(at.sign.and.spaces.after.comment)    @domain.com" =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2351 - assertIsFalse "(local.part.with) (two.comments.with.space.after)  comment@domain.com"          =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2352 - assertIsFalse "(local.part.with) (two.comments.with.space.after.first).comment@domain.com"     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2353 - assertIsTrue  "domain.part.with.comment.at.the.end@domain.com(comment)"                        =   6 =  OK 
   *  2354 - assertIsFalse "comment.not(closed@domain.com"                                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2355 - assertIsFalse "comment.not.startet@do)main.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2356 - assertIsFalse ")comment.close.bracket.at.start@domain.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2357 - assertIsFalse "comment.close.bracket.before.at.sign)@domain.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2358 - assertIsTrue  "ip4.with.comment.after.at.sign.at.input.start@(without.space)[1.2.3.4]"         =   2 =  OK 
   *  2359 - assertIsTrue  "ip4.with.comment.after.at.sign.at.input.end@[1.2.3.4](without.space)"           =   2 =  OK 
   *  2360 - assertIsTrue  "ip4.with.comment.after.at.sign.at.input.end@[1.2.3.4]  (with.space.before.comment)" =   2 =  OK 
   *  2361 - assertIsFalse "ip4.with.comment.after.at.sign@(with.space) [1.2.3.4]"                          = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
   *  2362 - assertIsFalse "ip4.with.embedded.comment.in.ip4@[1.2.3(comment).4]"                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2363 - assertIsFalse "()()()three.consecutive.empty.comments.at.email.start@domain.com"               =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2364 - assertIsTrue  "morse.code.in.comment(... .... .. -)@storm.de"                                  =   6 =  OK 
   *  2365 - assertIsTrue  "(comment)          \"string\".name1@domain.tld"                                 =   7 =  OK 
   *  2366 - assertIsFalse "(comment) Error )  \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2367 - assertIsFalse "(comment(nested Comment)) nested.comments.not.supported@domain.com"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2368 - assertIsFalse ")                  \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2369 - assertIsFalse ")))))              \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2370 - assertIsFalse "())                \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2371 - assertIsFalse "   ())             \"string\".name1@domain.tld"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2372 - assertIsFalse "(input.is.only.one.comment)"                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2373 - assertIsFalse "  (input.is.only.one.comment.with.leading.spaces)"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2374 - assertIsFalse "(input.is.only.one.comment.with.trailing.spaces)    "                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2375 - assertIsFalse "(comment)  ."                                                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2376 - assertIsFalse "(comment.space.point.space) . "                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2377 - assertIsFalse "domain.part.with.comment.but.spaces.after.comment@domain.com(comment)    "      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2378 - assertIsTrue  "domain.part.with.comment@(comment)domain.com"                                   =   6 =  OK 
   *  2379 - assertIsTrue  "domain.part.with.comment@(and.at.sgin.in.comment.@.)domain.com"                 =   6 =  OK 
   *  2380 - assertIsTrue  "domain.part.with.comment@(and.escaped.at.sgin.in.comment.\@.)domain.com"        =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2381 - assertIsFalse "ABC.DEF@(GHI)   JKL.MNO"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2382 - assertIsFalse "ABC.DEF@(GHI.)   JKL.MNO"                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2383 - assertIsFalse "ABC.DEF@(GHI.) (ABC)JKL.MNO"                                                    =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2384 - assertIsFalse "ABC.DEF@(GHI().()ABC)JKL.MNO"                                                   =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2385 - assertIsFalse "ABC.DEF@(GHI.)   JKL(MNO)"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2386 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                                     =   6 =  OK 
   *  2387 - assertIsFalse "ABC.DEF@             (MNO)"                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2388 - assertIsFalse "ABC.DEF@   .         (MNO)"                                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2389 - assertIsFalse "ABC.DEF              (MNO)"                                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2390 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                                     =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  2391 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2392 - assertIsFalse "ABC.DEF@GHI.JKL          "                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2393 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2394 - assertIsFalse "("                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2395 - assertIsFalse "(         )"                                                                    =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2396 - assertIsFalse ")"                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2397 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                                             =   6 =  OK 
   *  2398 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                                              =   6 =  OK 
   *  2399 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                                              =   6 =  OK 
   *  2400 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                                              =   6 =  OK 
   *  2401 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                                              =   6 =  OK 
   *  2402 - assertIsFalse "ABC.DEF@(GHI.JKL)"                                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2403 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                                           = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2404 - assertIsFalse "(ABC)(DEF)@GHI.JKL"                                                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2405 - assertIsFalse "(ABC()DEF)@GHI.JKL"                                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2406 - assertIsFalse "(ABC(Z)DEF)@GHI.JKL"                                                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2407 - assertIsFalse "(ABC).(DEF)@GHI.JKL"                                                            = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2408 - assertIsFalse "(ABC).DEF@GHI.JKL"                                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2409 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                                              = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  2410 - assertIsFalse "ABC.DEF@(GHI).JKL"                                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2411 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                                          = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
   *  2412 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                                          = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2413 - assertIsFalse "AB(CD)EF@GHI.JKL"                                                               =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2414 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                                             = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  2415 - assertIsFalse "(ABCDEF)@GHI.JKL"                                                               =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *  2416 - assertIsFalse "(ABCDEF).@GHI.JKL"                                                              = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2417 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2418 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                                              =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2419 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                                             =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2420 - assertIsFalse "ABC(DEF@GHI).JKL"                                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2421 - assertIsFalse "ABC(DEF.GHI).JKL"                                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2422 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                                              =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
   *  2423 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2424 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2425 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2426 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                                           =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2427 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2428 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                                         =   4 =  OK 
   *  2429 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                                         =   4 =  OK 
   *  2430 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                                     =   4 =  OK 
   *  2431 - assertIsFalse "(Comment).ABC.DEF@GHI.JKL"                                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2432 - assertIsTrue  "(Comment)-ABC.DEF@GHI.JKL"                                                      =   6 =  OK 
   *  2433 - assertIsTrue  "(Comment)_ABC.DEF@GHI.JKL"                                                      =   6 =  OK 
   *  2434 - assertIsFalse "-(Comment)ABC.DEF@GHI.JKL"                                                      = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
   *  2435 - assertIsFalse ".(Comment)ABC.DEF@GHI.JKL"                                                      =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2436 - assertIsFalse "a@abc(bananas)def.com"                                                          = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2437 - assertIsTrue  "\"address(comment\"@example.com"                                                =   1 =  OK 
   *  2438 - assertIsFalse "address(co\"mm\"ent)@example.com"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2439 - assertIsFalse "address(co\"mment)@example.com"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2440 - assertIsFalse "test@test.com(comment"                                                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   * 
   * ---- Display Name ----------------------------------------------------------------------------------------------------------------
   * 
   *  2441 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                                      =   0 =  OK 
   *  2442 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                                      =   0 =  OK 
   *  2443 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                                       =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  2444 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                                       =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *  2445 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                                                  =   0 =  OK 
   *  2446 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                                            =   1 =  OK 
   *  2447 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                                    =   1 =  OK 
   *  2448 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                                     =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2449 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2450 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2451 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2452 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2453 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2454 - assertIsFalse "ABC DEF <A@A>"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2455 - assertIsFalse "<A@A> ABC DEF"                                                                  =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2456 - assertIsFalse "ABC DEF <>"                                                                     =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2457 - assertIsFalse "<> ABC DEF"                                                                     =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2458 - assertIsFalse "<"                                                                              =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
   *  2459 - assertIsFalse ">"                                                                              =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  2460 - assertIsFalse "<         >"                                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2461 - assertIsFalse "< <     > >"                                                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2462 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                                              =   0 =  OK 
   *  2463 - assertIsFalse "<.ABC.DEF@GHI.JKL>"                                                             = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2464 - assertIsFalse "<ABC.DEF@GHI.JKL.>"                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2465 - assertIsTrue  "<-ABC.DEF@GHI.JKL>"                                                             =   0 =  OK 
   *  2466 - assertIsFalse "<ABC.DEF@GHI.JKL->"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2467 - assertIsTrue  "<_ABC.DEF@GHI.JKL>"                                                             =   0 =  OK 
   *  2468 - assertIsFalse "<ABC.DEF@GHI.JKL_>"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2469 - assertIsTrue  "<(Comment)ABC.DEF@GHI.JKL>"                                                     =   6 =  OK 
   *  2470 - assertIsFalse "<(Comment).ABC.DEF@GHI.JKL>"                                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2471 - assertIsFalse "<.(Comment)ABC.DEF@GHI.JKL>"                                                    = 142 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2472 - assertIsTrue  "<(Comment)-ABC.DEF@GHI.JKL>"                                                    =   6 =  OK 
   *  2473 - assertIsFalse "<-(Comment)ABC.DEF@GHI.JKL>"                                                    = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
   *  2474 - assertIsTrue  "<(Comment)_ABC.DEF@GHI.JKL>"                                                    =   6 =  OK 
   *  2475 - assertIsFalse "<_(Comment)ABC.DEF@GHI.JKL>"                                                    = 141 =  OK    Trennzeichen: Kein Start mit der Zeichenfolge "-("
   *  2476 - assertIsTrue  "Joe Smith <email@domain.com>"                                                   =   0 =  OK 
   *  2477 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2478 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2479 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"                =   9 =  OK 
   *  2480 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>"                =   9 =  OK 
   *  2481 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"                =   9 =  OK 
   *  2482 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part "               =   9 =  OK 
   *  2483 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2484 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>"               =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2485 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part"               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2486 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part "              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2487 - assertIsFalse "Test |<gaaf <email@domain.com>"                                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2488 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2489 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>"                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2490 - assertIsFalse "<null>@mail.com"                                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2491 - assertIsFalse "email.adress@domain.com <display name>"                                         =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2492 - assertIsFalse "email.adress@domain.com <email.adress@domain.com>"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2493 - assertIsFalse "display.name@false.com <email.adress@correct.com>"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2494 - assertIsFalse "<email>.<adress>@domain.com"                                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  2495 - assertIsFalse "<email>.<adress> email.adress@domain.com"                                       =  18 =  OK    Struktur: Fehler in Adress-String-X
   * 
   * ---- Length ----------------------------------------------------------------------------------------------------------------------
   * 
   *  2496 - assertIsTrue  "A@B.CD"                                                                         =   0 =  OK 
   *  2497 - assertIsFalse "A@B.C"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2498 - assertIsFalse "A@COM"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2499 - assertIsTrue  "ABC.DEF@GHI.JKL"                                                                =   0 =  OK 
   *  2500 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123"    =   0 =  OK 
   *  2501 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A"   =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
   *  2502 - assertIsTrue  "A@B.CD"                                                                         =   0 =  OK 
   *  2503 - assertIsTrue  "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@ZZZZZZZZZX.ZL" =   0 =  OK 
   *  2504 - assertIsFalse "zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2505 - assertIsTrue  "True64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain.tld>" =   0 =  OK 
   *  2506 - assertIsFalse "False64 <zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2507 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain.tld> True64 " =   0 =  OK 
   *  2508 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain.tld> False64 " =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2509 - assertIsTrue  "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzz@domain.tld>"  =   0 =  OK 
   *  2510 - assertIsFalse "<zzzzzzzzz1zzzzzzzzz2zzzzzzzzz3zzzzzzzzz4zzzzzzzzz5zzzzzzzzz6zzzzT@domain.tld>" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2511 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com"          =   0 =  OK 
   *  2512 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
   *  2513 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@example.com"   =   0 =  OK 
   *  2514 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2515 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2516 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2517 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2518 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2519 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2520 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2521 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
   *  2522 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2523 - assertIsTrue  "eMail Test XX1 <63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
   *  2524 - assertIsTrue  "eMail Test XX2 <1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =   0 =  OK 
   *  2525 - assertIsFalse "eMail Test XX3 AAA<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com>" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2526 - assertIsTrue  "12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678901234567890 12345678901234567890123456789012345678<A@B.de.com>" =   0 =  OK 
   *  2527 - assertIsTrue  "<63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK3" =   0 =  OK 
   *  2528 - assertIsTrue  "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test OK4" =   0 =  OK 
   *  2529 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com> eMail Test FALSE3" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2530 - assertIsFalse "<1234567890123456789012345678901234567890@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com> eMail Test FALSE4" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2531 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com"  =   1 =  OK 
   *  2532 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2533 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2534 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2535 - assertIsTrue  "abcdefghijklmnopqrstuvwxyz.ABCDEFGHIJKLMNOPQRSTUVWXYZ.!#$%&'+-/=@a.b.c.d.e.f.g.h.i.j.k.l.m.n.o.p.q.r.s.t.u.v.w.x.y.z.A.B.C.D.E.F.G.H.I.J.K.L.M.N.O.P.Q.R.S.T.U.V.W.X.Y.Z.0.1.2.3.4.5.6.7.8.9.a-z.A-Z.0-9.a0.b1.c2.d3.domain.name" =   0 =  OK 
   *  2536 - assertIsTrue  "email@domain.topleveldomain"                                                    =   0 =  OK 
   *  2537 - assertIsTrue  "email@email.email.mydomain"                                                     =   0 =  OK 
   * 
   * ---- https://en.wikipedia.org/wiki/Email_address/ --------------------------------------------------------------------------------
   * 
   *  2538 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                                               =   6 =  OK 
   *  2539 - assertIsTrue  "\"MaxMustermann\"@example.com"                                                  =   1 =  OK 
   *  2540 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                                     =   1 =  OK 
   *  2541 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                                              =   0 =  OK 
   *  2542 - assertIsTrue  "Marc Dupont <md118@example.com>"                                                =   0 =  OK 
   *  2543 - assertIsTrue  "simple@example.com"                                                             =   0 =  OK 
   *  2544 - assertIsTrue  "very.common@example.com"                                                        =   0 =  OK 
   *  2545 - assertIsTrue  "disposable.style.email.with+symbol@example.com"                                 =   0 =  OK 
   *  2546 - assertIsTrue  "other.email-with-hyphen@example.com"                                            =   0 =  OK 
   *  2547 - assertIsTrue  "fully-qualified-domain@example.com"                                             =   0 =  OK 
   *  2548 - assertIsTrue  "user.name+tag+sorting@example.com"                                              =   0 =  OK 
   *  2549 - assertIsTrue  "user+mailbox/department=shipping@example.com"                                   =   0 =  OK 
   *  2550 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                                               =   0 =  OK 
   *  2551 - assertIsTrue  "x@example.com"                                                                  =   0 =  OK 
   *  2552 - assertIsTrue  "info@firma.org"                                                                 =   0 =  OK 
   *  2553 - assertIsTrue  "example-indeed@strange-example.com"                                             =   0 =  OK 
   *  2554 - assertIsTrue  "admin@mailserver1"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2555 - assertIsTrue  "example@s.example"                                                              =   0 =  OK 
   *  2556 - assertIsTrue  "\" \"@example.org"                                                              =   1 =  OK 
   *  2557 - assertIsTrue  "mailhost!username@example.org"                                                  =   0 =  OK 
   *  2558 - assertIsTrue  "user%example.com@example.org"                                                   =   0 =  OK 
   *  2559 - assertIsTrue  "joe25317@NOSPAMexample.com"                                                     =   0 =  OK 
   *  2560 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                                     =   0 =  OK 
   *  2561 - assertIsTrue  "nama@contoh.com"                                                                =   0 =  OK 
   *  2562 - assertIsFalse "\"John Smith\" (johnsmith@example.com)"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2563 - assertIsFalse "just\"not\"right@example.com"                                                   =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2564 - assertIsFalse "this is\"not\allowed@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2565 - assertIsFalse "this\ still\\"not\\allowed@example.com"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2566 - assertIsTrue  "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com"             =   0 =  OK 
   *  2567 - assertIsTrue  "(buero)office@example.com"                                                      =   6 =  OK 
   *  2568 - assertIsTrue  "office(etage-3)@example.com"                                                    =   6 =  OK 
   *  2569 - assertIsFalse "off(kommentar)ice@example.com"                                                  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2570 - assertIsTrue  "\"(buero)office\"@example.com"                                                  =   1 =  OK 
   *  2571 - assertIsTrue  "\"office(etage-3)\"@example.com"                                                =   1 =  OK 
   *  2572 - assertIsTrue  "\"off(kommentar)ice\"@example.com"                                              =   1 =  OK 
   *  2573 - assertIsTrue  "\"address(comment)\"@example.com"                                               =   1 =  OK 
   *  2574 - assertIsTrue  "Buero <office@example.com>"                                                     =   0 =  OK 
   *  2575 - assertIsTrue  "\"vorname(Kommentar).nachname\"@provider.de"                                    =   1 =  OK 
   *  2576 - assertIsTrue  "\"Herr \\"Kaiser\\" Franz Beckenbauer\" <local-part@domain-part.com>"           =   0 =  OK 
   *  2577 - assertIsTrue  "Erwin Empfaenger <erwin@example.com>"                                           =   0 =  OK 
   * 
   * ---- https://github.com/egulias/EmailValidator4J ---------------------------------------------------------------------------------
   * 
   *  2578 - assertIsFalse "nolocalpart.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2579 - assertIsFalse "test@example.com test"                                                          = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2580 - assertIsFalse "user  name@example.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2581 - assertIsFalse "user   name@example.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2582 - assertIsFalse "example.@example.co.uk"                                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2583 - assertIsFalse "example@example@example.co.uk"                                                  =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2584 - assertIsFalse "(test_exampel@example.fr}"                                                      =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2585 - assertIsFalse "example(example)example@example.co.uk"                                          =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2586 - assertIsFalse ".example@localhost"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2587 - assertIsFalse "ex\ample@localhost"                                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2588 - assertIsFalse "example@local\host"                                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2589 - assertIsFalse "example@localhost."                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2590 - assertIsFalse "user name@example.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2591 - assertIsFalse "username@ example . com"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2592 - assertIsFalse "example@(fake}.com"                                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2593 - assertIsFalse "example@(fake.com"                                                              =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2594 - assertIsTrue  "username@example.com"                                                           =   0 =  OK 
   *  2595 - assertIsTrue  "usern.ame@example.com"                                                          =   0 =  OK 
   *  2596 - assertIsFalse "user[na]me@example.com"                                                         =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2597 - assertIsFalse "\"\"\"@iana.org"                                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2598 - assertIsFalse "\"\\"@iana.org"                                                                 =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2599 - assertIsFalse "\"test\"test@iana.org"                                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2600 - assertIsFalse "\"test\"\"test\"@iana.org"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2601 - assertIsTrue  "\"test\".\"test\"@iana.org"                                                     =   1 =  OK 
   *  2602 - assertIsTrue  "\"test\".test@iana.org"                                                         =   1 =  OK 
   *  2603 - assertIsFalse "\"test\\"@iana.org"                                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2604 - assertIsFalse "\r\ntest@iana.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2605 - assertIsFalse "\r\n test@iana.org"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2606 - assertIsFalse "\r\n \r\ntest@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2607 - assertIsFalse "\r\n \r\n test@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2608 - assertIsFalse "test@iana.org \r\n"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2609 - assertIsFalse "test@iana.org \r\n "                                                            = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2610 - assertIsFalse "test@iana.org \r\n \r\n"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2611 - assertIsFalse "test@iana.org \r\n\r\n"                                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2612 - assertIsFalse "test@iana.org  \r\n\r\n "                                                       = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2613 - assertIsFalse "test@iana/icann.org"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2614 - assertIsFalse "test@foo;bar.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2615 - assertIsFalse "a@test.com"                                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2616 - assertIsFalse "comment)example@example.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2617 - assertIsFalse "comment(example))@example.com"                                                  =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2618 - assertIsFalse "example@example)comment.com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2619 - assertIsFalse "example@example(comment)).com"                                                  = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  2620 - assertIsFalse "example@[1.2.3.4"                                                               =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2621 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2622 - assertIsFalse "exam(ple@exam).ple"                                                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2623 - assertIsFalse "example@(example))comment.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2624 - assertIsTrue  "example@example.com"                                                            =   0 =  OK 
   *  2625 - assertIsTrue  "example@example.co.uk"                                                          =   0 =  OK 
   *  2626 - assertIsTrue  "example_underscore@example.fr"                                                  =   0 =  OK 
   *  2627 - assertIsTrue  "exam'ple@example.com"                                                           =   0 =  OK 
   *  2628 - assertIsTrue  "exam\ ple@example.com"                                                          =   0 =  OK 
   *  2629 - assertIsFalse "example((example))@fakedfake.co.uk"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2630 - assertIsFalse "example@faked(fake).co.uk"                                                      = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2631 - assertIsTrue  "example+@example.com"                                                           =   0 =  OK 
   *  2632 - assertIsTrue  "example@with-hyphen.example.com"                                                =   0 =  OK 
   *  2633 - assertIsTrue  "with-hyphen@example.com"                                                        =   0 =  OK 
   *  2634 - assertIsTrue  "example@1leadingnum.example.com"                                                =   0 =  OK 
   *  2635 - assertIsTrue  "1leadingnum@example.com"                                                        =   0 =  OK 
   *  2636 - assertIsTrue  "инфо@письмо.рф"                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2637 - assertIsTrue  "\"username\"@example.com"                                                       =   1 =  OK 
   *  2638 - assertIsTrue  "\"user.name\"@example.com"                                                      =   1 =  OK 
   *  2639 - assertIsTrue  "\"user name\"@example.com"                                                      =   1 =  OK 
   *  2640 - assertIsTrue  "\"user@name\"@example.com"                                                      =   1 =  OK 
   *  2641 - assertIsFalse "\"\a\"@iana.org"                                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2642 - assertIsTrue  "\"test\ test\"@iana.org"                                                        =   1 =  OK 
   *  2643 - assertIsFalse "\"\"@iana.org"                                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2644 - assertIsFalse "\"\"@[]"                                                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2645 - assertIsTrue  "\"\\"\"@iana.org"                                                               =   1 =  OK 
   *  2646 - assertIsTrue  "example@localhost"                                                              =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   * 
   * ---- https://github.com/JoshData/python-email-validator/blob/main/tests/test_syntax.py -------------------------------------------
   * 
   *  2647 - assertIsTrue  "\"unnecessarily.quoted.local.part\"@example.com"                                =   1 =  OK 
   *  2648 - assertIsTrue  "\"quoted..local.part\"@example.com"                                             =   1 =  OK 
   *  2649 - assertIsTrue  "\"quoted.with.at@\"@example.com"                                                =   1 =  OK 
   *  2650 - assertIsTrue  "\"quoted with space\"@example.com"                                              =   1 =  OK 
   *  2651 - assertIsTrue  "\"quoted.with.dquote\\"\"@example.com"                                          =   1 =  OK 
   *  2652 - assertIsTrue  "\"unnecessarily.quoted.with.unicode.?\"@example.com"                            =   1 =  OK 
   *  2653 - assertIsTrue  "\"quoted.with..unicode.?\"@example.com"                                         =   1 =  OK 
   *  2654 - assertIsTrue  "\"quoted.with.extraneous.\\escape\"@example.com"                                =   1 =  OK 
   *  2655 - assertIsFalse "my@localhost"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2656 - assertIsFalse "my@.leadingdot.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2657 - assertIsFalse "my@.leadingfwdot.com"                                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2658 - assertIsFalse "my@twodots..com"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2659 - assertIsFalse "my@twofwdots...com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2660 - assertIsFalse "my@trailingdot.com."                                                            =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2661 - assertIsFalse "my@trailingfwdot.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2662 - assertIsFalse "me@-leadingdash"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2663 - assertIsFalse "me@-leadingdashfw"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2664 - assertIsFalse "me@trailingdash-"                                                               =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2665 - assertIsFalse "me@trailingdashfw-"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2666 - assertIsFalse "my@baddash.-.com"                                                               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2667 - assertIsFalse "my@baddash.-a.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2668 - assertIsFalse "my@baddash.b-.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2669 - assertIsFalse "my@baddashfw.-.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2670 - assertIsFalse "my@baddashfw.-a.com"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2671 - assertIsFalse "my@baddashfw.b-.com"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2672 - assertIsFalse "me@x!"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2673 - assertIsFalse "me@x "                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2674 - assertIsFalse ".leadingdot@domain.com"                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2675 - assertIsFalse "twodots..here@domain.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2676 - assertIsFalse "trailingdot.@domain.email"                                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2677 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2678 - assertIsFalse "white space@test"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2679 - assertIsFalse "test@white space"                                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2680 - assertIsFalse "\nmy@example.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2681 - assertIsFalse "m\ny@example.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2682 - assertIsFalse "my\n@example.com"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2683 - assertIsFalse "test@\n"                                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2684 - assertIsFalse "bad\"quotes\"@example.com"                                                      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2685 - assertIsTrue  "obsolete.\"quoted\".atom@example.com"                                           =   1 =  OK 
   *  2686 - assertIsFalse "11111111112222222222333333333344444444445555555555666666666677777@example.com"  =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2687 - assertIsFalse "111111111122222222223333333333444444444455555555556666666666777777@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2688 - assertIsFalse "me@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.111111111122222222223333333333444444444455555555556.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2689 - assertIsFalse "me@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555566.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2690 - assertIsFalse "me@?1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555566.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2691 - assertIsFalse "my.long.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.11111111112222222222333333333344444.info" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2692 - assertIsFalse "my.long.address@?111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.11111111112222222222333333.info" =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2693 - assertIsFalse "my.long.address@?111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444.info" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2694 - assertIsFalse "my.?ong.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.111111111122222222223333333333444.info" =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  2695 - assertIsFalse "my.?ong.address@1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444444444555555555.6666666666777777777788888888889999999999000000000.1111111111222222222233333333334444.info" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2696 - assertIsFalse "me@bad-tld-1"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2697 - assertIsFalse "me@bad.tld-2"                                                                   =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  2698 - assertIsTrue  "me@xn--0.tld"                                                                   =   0 =  OK 
   *  2699 - assertIsTrue  "me@[127.0.0.1]"                                                                 =   2 =  OK 
   *  2700 - assertIsFalse "me@[127.0.0.999]"                                                               =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2701 - assertIsTrue  "me@[IPv6:::1]"                                                                  =   4 =  OK 
   *  2702 - assertIsFalse "me@[IPv6:::G]"                                                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2703 - assertIsFalse "me@[tag:text]"                                                                  =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2704 - assertIsFalse "me@[untaggedtext]"                                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2705 - assertIsFalse "me@[tag:invalid space]"                                                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2706 - assertIsFalse "test"                                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2707 - assertIsFalse "@"                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2708 - assertIsFalse "test@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2709 - assertIsFalse "@io"                                                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2710 - assertIsFalse "@iana.org"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2711 - assertIsTrue  "a@iana.org"                                                                     =   0 =  OK 
   *  2712 - assertIsFalse "test_exa-mple.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2713 - assertIsTrue  "!#$%&`*+/=?^`{|}~@iana.org"                                                     =   0 =  OK 
   *  2714 - assertIsTrue  "test\@test@iana.org"                                                            =   0 =  OK 
   *  2715 - assertIsTrue  "123@iana.org"                                                                   =   0 =  OK 
   *  2716 - assertIsTrue  "test@123.com"                                                                   =   0 =  OK 
   *  2717 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org"      =   0 =  OK 
   *  2718 - assertIsFalse "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklmn@iana.org"     =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2719 - assertIsFalse "test@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm.com"      =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2720 - assertIsTrue  "test@mason-dixon.com"                                                           =   0 =  OK 
   *  2721 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2722 - assertIsTrue  "a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hij" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2723 - assertIsTrue  "a@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefg.hijk" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2724 - assertIsTrue  "\"test\"@iana.org"                                                              =   1 =  OK 
   *  2725 - assertIsTrue  " test @iana.org"                                                                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2726 - assertIsTrue  " test@iana.org"                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2727 - assertIsFalse "((comment)test@iana.org"                                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2728 - assertIsTrue  "(comment(comment))test@iana.org"                                                =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2729 - assertIsTrue  "(comment)test@iana.org"                                                         =   6 =  OK 
   *  2730 - assertIsFalse "(comment\)test@iana.org"                                                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2731 - assertIsFalse "(test@iana.org"                                                                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2732 - assertIsFalse ".test@iana.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2733 - assertIsFalse "\"\"@iana.org"                                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  2734 - assertIsFalse "\"\\"@iana.org"                                                                 =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2735 - assertIsTrue  "\"\\"\"@iana.org"                                                               =   1 =  OK 
   *  2736 - assertIsTrue  "\"\\\"@iana.org"                                                                =   1 =  OK 
   *  2737 - assertIsFalse "\"test@iana.org"                                                                =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2738 - assertIsTrue  "\"test\".\"test\"@iana.org"                                                     =   1 =  OK 
   *  2739 - assertIsTrue  "\"test\".test@iana.org"                                                         =   1 =  OK 
   *  2740 - assertIsFalse "\"test\"\"test\"@iana.org"                                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2741 - assertIsFalse "\"test\"test@iana.org"                                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2742 - assertIsTrue  "\"test\ test\"@iana.org"                                                        =   1 =  OK 
   *  2743 - assertIsFalse "\"test\\"@iana.org"                                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2744 - assertIsFalse "\"test\x00\"@iana.org"                                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2745 - assertIsFalse "\"test\©\"@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2746 - assertIsTrue  "\r\n \r\n test@iana.org"                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2747 - assertIsTrue  "\r\n test@iana.org"                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2748 - assertIsTrue  "test . test@iana.org"                                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2749 - assertIsFalse "test(comment)test@iana.org"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  2750 - assertIsFalse "test..iana.org"                                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2751 - assertIsFalse "test.@iana.org"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2752 - assertIsTrue  "test.test@iana.org"                                                             =   0 =  OK 
   *  2753 - assertIsTrue  "test@ iana .com"                                                                = 105 =  #### FEHLER ####    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2754 - assertIsTrue  "test@(comment)iana.org"                                                         =   6 =  OK 
   *  2755 - assertIsFalse "test@(iana.org"                                                                 =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  2756 - assertIsFalse "test@-iana.org"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2757 - assertIsFalse "test@.iana.org"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2758 - assertIsFalse "test@[1.2.3.4"                                                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2759 - assertIsTrue  "test@[IPv6:1::2:]"                                                              =   4 =  OK 
   *  2760 - assertIsTrue  "test@about.museum"                                                              =   0 =  OK 
   *  2761 - assertIsTrue  "test@g--a.com"                                                                  =   0 =  OK 
   *  2762 - assertIsFalse "test@iana-.com"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2763 - assertIsFalse "test@iana..com"                                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2764 - assertIsTrue  "test@iana.org "                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2765 - assertIsTrue  "test@iana.org \r\n "                                                            = 105 =  #### FEHLER ####    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2766 - assertIsFalse "test@iana.org \r\n \r\n"                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2767 - assertIsFalse "test@iana.org \r\n"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2768 - assertIsFalse "test@iana.org \r\n\r\n "                                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2769 - assertIsFalse "test@iana.org \r\n\r\n"                                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  2770 - assertIsTrue  "test@iana.org"                                                                  =   0 =  OK 
   *  2771 - assertIsFalse "test@iana.org(comment\"                                                         =  96 =  OK    Kommentar: Escape-Zeichen nicht am Ende der Eingabe
   *  2772 - assertIsFalse "test@iana.org(comment\)"                                                        =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2773 - assertIsFalse "test@iana.org-"                                                                 =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  2774 - assertIsFalse "test@iana.org."                                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2775 - assertIsTrue  "test@iana.org\r\n "                                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2776 - assertIsTrue  "test@iana.org\r\n \r\n "                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2777 - assertIsFalse "test@iana.org\r\n \r\n"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2778 - assertIsFalse "test@iana.org\r\n"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2779 - assertIsTrue  "test@iana/icann.org"                                                            =  21 =  #### FEHLER ####    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  2780 - assertIsTrue  "test@nominet.org.uk"                                                            =   0 =  OK 
   *  2781 - assertIsFalse "test\"@iana.org"                                                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2782 - assertIsFalse "test\"text\"@iana.org"                                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2783 - assertIsTrue  "\"abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefghj\"@iana.org"   =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2784 - assertIsTrue  "\"abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz abcdefg\h\"@iana.org"   =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  2785 - assertIsTrue  "test@[255.255.255.255]"                                                         =   2 =  OK 
   *  2786 - assertIsFalse "test@a[255.255.255.255]"                                                        =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
   *  2787 - assertIsFalse "test@[255.255.255]"                                                             =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  2788 - assertIsFalse "test@[255.255.255.255.255]"                                                     =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  2789 - assertIsFalse "test@[255.255.255.256]"                                                         =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  2790 - assertIsTrue  "test@[1111:2222:3333:4444:5555:6666:7777:8888]"                                 =   4 =  OK 
   *  2791 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                                 =   4 =  OK 
   *  2792 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"                            =   4 =  OK 
   *  2793 - assertIsFalse "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"                       =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  2794 - assertIsFalse "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:888G]"                            =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2795 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666::8888]"                                =   4 =  OK 
   *  2796 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555::8888]"                                     =   4 =  OK 
   *  2797 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666::7777:8888]"                           =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  2798 - assertIsTrue  "test@[IPv6::3333:4444:5555:6666:7777:8888]"                                     =   4 =  OK 
   *  2799 - assertIsTrue  "test@[IPv6:::3333:4444:5555:6666:7777:8888]"                                    =   4 =  OK 
   *  2800 - assertIsTrue  "test@[IPv6:1111::4444:5555::8888]"                                              =  50 =  #### FEHLER ####    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  2801 - assertIsTrue  "test@[IPv6:::]"                                                                 =   4 =  OK 
   *  2802 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:255.255.255.255]"                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2803 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:255.255.255.255]"                      =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2804 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666:7777:255.255.255.255]"                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2805 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444::255.255.255.255]"                               =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2806 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:5555:6666::255.255.255.255]"                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2807 - assertIsTrue  "test@[IPv6:1111:2222:3333:4444:::255.255.255.255]"                              =  50 =  #### FEHLER ####    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  2808 - assertIsTrue  "test@[IPv6::255.255.255.255]"                                                   =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2809 - assertIsTrue  "test@(comment)[255.255.255.255]"                                                =   2 =  OK 
   *  2810 - assertIsTrue  "(comment)abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghiklm@iana.org" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2811 - assertIsTrue  "test@(comment)abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghikl.com" =  63 =  #### FEHLER ####    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2812 - assertIsTrue  "(comment)test@abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghik.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghik.abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijk.abcdefghijklmnopqrstuvwxyzabcdefghijk.abcdefghijklmnopqrstu" =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  2813 - assertIsFalse "test@iana.org\n"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2814 - assertIsTrue  "test@xn--hxajbheg2az3al.xn--jxalpdlp"                                           =   0 =  OK 
   *  2815 - assertIsTrue  "xn--test@iana.org"                                                              =   0 =  OK 
   *  2816 - assertIsTrue  "test@[RFC-5322-domain-literal]"                                                 =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2817 - assertIsFalse "test@[RFC-5322]-domain-literal]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2818 - assertIsFalse "test@[RFC-5322-[domain-literal]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2819 - assertIsTrue  "test@[RFC-5322-\  -domain-literal]"                                              =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2820 - assertIsTrue  "test@[RFC-5322-\]-domain-literal]"                                              =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2821 - assertIsFalse "test@[RFC-5322-domain-literal\]"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2822 - assertIsFalse "test@[RFC-5322-domain-literal\"                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2823 - assertIsTrue  "test@[RFC 5322 domain literal]"                                                 =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2824 - assertIsTrue  "test@[RFC-5322-domain-literal] (comment)"                                       =  59 =  #### FEHLER ####    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  2825 - assertIsFalse "test@iana.org\r"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2826 - assertIsFalse "\rtest@iana.org"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2827 - assertIsFalse "\"\rtest\"@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2828 - assertIsFalse "(\r)test@iana.org"                                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2829 - assertIsFalse "test@iana.org(\r)"                                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2830 - assertIsFalse "\ntest@iana.org"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2831 - assertIsFalse "\"\n\"@iana.org"                                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2832 - assertIsTrue  "\"\\n\"@iana.org"                                                               =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  2833 - assertIsFalse "(\n)test@iana.org"                                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2834 - assertIsTrue  "\"\x07\"@iana.org"                                                              =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  2835 - assertIsFalse "\r\ntest@iana.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2836 - assertIsFalse "\r\n \r\ntest@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2837 - assertIsFalse " \r\ntest@iana.org"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2838 - assertIsTrue  " \r\n test@iana.org"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2839 - assertIsFalse " \r\n \r\ntest@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2840 - assertIsFalse " \r\n\r\ntest@iana.org"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2841 - assertIsFalse " \r\n\r\n test@iana.org"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2842 - assertIsFalse "test.(comment)test@iana.org"                                                    = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   * 
   * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php -------------------------------------------------
   * 
   *  2843 - assertIsTrue  "first.last@iana.org"                                                            =   0 =  OK 
   *  2844 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org"      =   0 =  OK 
   *  2845 - assertIsTrue  "\"first\\"last\"@iana.org"                                                      =   1 =  OK 
   *  2846 - assertIsTrue  "\"first@last\"@iana.org"                                                        =   1 =  OK 
   *  2847 - assertIsTrue  "\"first\\last\"@iana.org"                                                       =   1 =  OK 
   *  2848 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
   *  2849 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
   *  2850 - assertIsTrue  "first.last@[12.34.56.78]"                                                       =   2 =  OK 
   *  2851 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"                              =   4 =  OK 
   *  2852 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"                               =   4 =  OK 
   *  2853 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"                              =   4 =  OK 
   *  2854 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"                              =   4 =  OK 
   *  2855 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"                      =   4 =  OK 
   *  2856 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
   *  2857 - assertIsTrue  "first.last@3com.com"                                                            =   0 =  OK 
   *  2858 - assertIsTrue  "first.last@123.iana.org"                                                        =   0 =  OK 
   *  2859 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                        =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  2860 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"                          =   4 =  OK 
   *  2861 - assertIsTrue  "\"Abc\@def\"@iana.org"                                                          =   1 =  OK 
   *  2862 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                                      =   1 =  OK 
   *  2863 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                                        =   1 =  OK 
   *  2864 - assertIsTrue  "\"Abc@def\"@iana.org"                                                           =   1 =  OK 
   *  2865 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                                     =   1 =  OK 
   *  2866 - assertIsTrue  "user+mailbox@iana.org"                                                          =   0 =  OK 
   *  2867 - assertIsTrue  "$A12345@iana.org"                                                               =   0 =  OK 
   *  2868 - assertIsTrue  "!def!xyz%abc@iana.org"                                                          =   0 =  OK 
   *  2869 - assertIsTrue  "_somename@iana.org"                                                             =   0 =  OK 
   *  2870 - assertIsTrue  "dclo@us.ibm.com"                                                                =   0 =  OK 
   *  2871 - assertIsTrue  "peter.piper@iana.org"                                                           =   0 =  OK 
   *  2872 - assertIsTrue  "test@iana.org"                                                                  =   0 =  OK 
   *  2873 - assertIsTrue  "TEST@iana.org"                                                                  =   0 =  OK 
   *  2874 - assertIsTrue  "1234567890@iana.org"                                                            =   0 =  OK 
   *  2875 - assertIsTrue  "test+test@iana.org"                                                             =   0 =  OK 
   *  2876 - assertIsTrue  "test-test@iana.org"                                                             =   0 =  OK 
   *  2877 - assertIsTrue  "t*est@iana.org"                                                                 =   0 =  OK 
   *  2878 - assertIsTrue  "+1~1+@iana.org"                                                                 =   0 =  OK 
   *  2879 - assertIsTrue  "{_test_}@iana.org"                                                              =   0 =  OK 
   *  2880 - assertIsTrue  "test.test@iana.org"                                                             =   0 =  OK 
   *  2881 - assertIsTrue  "\"test.test\"@iana.org"                                                         =   1 =  OK 
   *  2882 - assertIsTrue  "test.\"test\"@iana.org"                                                         =   1 =  OK 
   *  2883 - assertIsTrue  "\"test@test\"@iana.org"                                                         =   1 =  OK 
   *  2884 - assertIsTrue  "test@123.123.123.x123"                                                          =   0 =  OK 
   *  2885 - assertIsTrue  "test@123.123.123.123"                                                           =   2 =  OK 
   *  2886 - assertIsTrue  "test@[123.123.123.123]"                                                         =   2 =  OK 
   *  2887 - assertIsTrue  "test@example.iana.org"                                                          =   0 =  OK 
   *  2888 - assertIsTrue  "test@example.example.iana.org"                                                  =   0 =  OK 
   *  2889 - assertIsTrue  "customer/department@iana.org"                                                   =   0 =  OK 
   *  2890 - assertIsTrue  "_Yosemite.Sam@iana.org"                                                         =   0 =  OK 
   *  2891 - assertIsTrue  "~@iana.org"                                                                     =   0 =  OK 
   *  2892 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                                     =   1 =  OK 
   *  2893 - assertIsTrue  "Ima.Fool@iana.org"                                                              =   0 =  OK 
   *  2894 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                                          =   1 =  OK 
   *  2895 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                                        =   1 =  OK 
   *  2896 - assertIsTrue  "\"first\".\"last\"@iana.org"                                                    =   1 =  OK 
   *  2897 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                                             =   1 =  OK 
   *  2898 - assertIsTrue  "\"first\".last@iana.org"                                                        =   1 =  OK 
   *  2899 - assertIsTrue  "first.\"last\"@iana.org"                                                        =   1 =  OK 
   *  2900 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                                         =   1 =  OK 
   *  2901 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                                             =   1 =  OK 
   *  2902 - assertIsTrue  "\"first.middle.last\"@iana.org"                                                 =   1 =  OK 
   *  2903 - assertIsTrue  "\"first..last\"@iana.org"                                                       =   1 =  OK 
   *  2904 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                                             =   1 =  OK 
   *  2905 - assertIsFalse "first.last @iana.orgin"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2906 - assertIsTrue  "\"test blah\"@iana.orgin"                                                       =   1 =  OK 
   *  2907 - assertIsTrue  "name.lastname@domain.com"                                                       =   0 =  OK 
   *  2908 - assertIsTrue  "a@bar.com"                                                                      =   0 =  OK 
   *  2909 - assertIsTrue  "aaa@[123.123.123.123]"                                                          =   2 =  OK 
   *  2910 - assertIsTrue  "a-b@bar.com"                                                                    =   0 =  OK 
   *  2911 - assertIsFalse "+@b.c"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2912 - assertIsTrue  "+@b.com"                                                                        =   0 =  OK 
   *  2913 - assertIsTrue  "a@b.co-foo.uk"                                                                  =   0 =  OK 
   *  2914 - assertIsTrue  "\"hello my name is\"@stutter.comin"                                             =   1 =  OK 
   *  2915 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                                             =   1 =  OK 
   *  2916 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                                               =   0 =  OK 
   *  2917 - assertIsTrue  "foobar@192.168.0.1"                                                             =   2 =  OK 
   *  2918 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"                              =   6 =  OK 
   *  2919 - assertIsTrue  "user%uucp!path@berkeley.edu"                                                    =   0 =  OK 
   *  2920 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                                      =   0 =  OK 
   *  2921 - assertIsTrue  "test@test.com"                                                                  =   0 =  OK 
   *  2922 - assertIsTrue  "test@xn--example.com"                                                           =   0 =  OK 
   *  2923 - assertIsTrue  "test@example.com"                                                               =   0 =  OK 
   *  2924 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                       =   0 =  OK 
   *  2925 - assertIsTrue  "first\@last@iana.org"                                                           =   0 =  OK 
   *  2926 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                                       =   0 =  OK 
   *  2927 - assertIsFalse "first.last@example.123"                                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  2928 - assertIsFalse "first.last@comin"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2929 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                                      =   1 =  OK 
   *  2930 - assertIsTrue  "Abc\@def@iana.org"                                                              =   0 =  OK 
   *  2931 - assertIsTrue  "Fred\ Bloggs@iana.org"                                                          =   0 =  OK 
   *  2932 - assertIsFalse "Joe.\Blow@iana.org"                                                             =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2933 - assertIsTrue  "first.last@sub.do.com"                                                          =   0 =  OK 
   *  2934 - assertIsFalse "first.last"                                                                     =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2935 - assertIsTrue  "wild.wezyr@best-server-ever.com"                                                =   0 =  OK 
   *  2936 - assertIsTrue  "\"hello world\"@example.com"                                                    =   1 =  OK 
   *  2937 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2938 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"                                   =   1 =  OK 
   *  2939 - assertIsTrue  "example+tag@gmail.com"                                                          =   0 =  OK 
   *  2940 - assertIsFalse ".ann..other.@example.com"                                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2941 - assertIsTrue  "ann.other@example.com"                                                          =   0 =  OK 
   *  2942 - assertIsTrue  "something@something.something"                                                  =   0 =  OK 
   *  2943 - assertIsTrue  "c@(Chris's host.)public.examplein"                                              =   6 =  OK 
   *  2944 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                                            =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2945 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                                     = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2946 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2947 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                                     =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2948 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                                     =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  2949 - assertIsFalse "first().last@iana.orgin"                                                        = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2950 - assertIsFalse "pete(his account)@silly.test(his host)"                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  2951 - assertIsFalse "jdoe@machine(comment). examplein"                                               = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2952 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  2953 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                                             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  2954 - assertIsFalse "1234 @ local(blah) .machine .examplein"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2955 - assertIsFalse "a@bin"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2956 - assertIsFalse "a@barin"                                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2957 - assertIsFalse "@about.museum"                                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2958 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org"     =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  2959 - assertIsFalse ".first.last@iana.org"                                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2960 - assertIsFalse "first.last.@iana.org"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2961 - assertIsFalse "first..last@iana.org"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2962 - assertIsFalse "\"first\"last\"@iana.org"                                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2963 - assertIsFalse "first.last@"                                                                    =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *  2964 - assertIsFalse "first.last@-xample.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2965 - assertIsFalse "first.last@exampl-.com"                                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  2966 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  2967 - assertIsFalse "abc\@iana.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2968 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2969 - assertIsFalse "abc@def@iana.org"                                                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2970 - assertIsFalse "@iana.org"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2971 - assertIsFalse "doug@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  2972 - assertIsFalse "\"qu@iana.org"                                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  2973 - assertIsFalse "ote\"@iana.org"                                                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  2974 - assertIsFalse ".dot@iana.org"                                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2975 - assertIsFalse "dot.@iana.org"                                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2976 - assertIsFalse "two..dot@iana.org"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2977 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2978 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                                                  =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  2979 - assertIsFalse "hello world@iana.org"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2980 - assertIsFalse "test.iana.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  2981 - assertIsFalse "test.@iana.org"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  2982 - assertIsFalse "test..test@iana.org"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  2983 - assertIsFalse ".test@iana.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2984 - assertIsFalse "test@test@iana.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2985 - assertIsFalse "test@@iana.org"                                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  2986 - assertIsFalse "-- test --@iana.org"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2987 - assertIsFalse "[test]@iana.org"                                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2988 - assertIsFalse "\"test\"test\"@iana.org"                                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2989 - assertIsFalse "()[]\;:.><@iana.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  2990 - assertIsFalse "test@."                                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2991 - assertIsFalse "test@example."                                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  2992 - assertIsFalse "test@.org"                                                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  2993 - assertIsFalse "test@[123.123.123.123"                                                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  2994 - assertIsFalse "test@123.123.123.123]"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  2995 - assertIsFalse "NotAnEmail"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  2996 - assertIsFalse "@NotAnEmail"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  2997 - assertIsFalse "\"test\"blah\"@iana.org"                                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  2998 - assertIsFalse ".wooly@iana.org"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  2999 - assertIsFalse "wo..oly@iana.org"                                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3000 - assertIsFalse "pootietang.@iana.org"                                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3001 - assertIsFalse ".@iana.org"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3002 - assertIsFalse "Ima Fool@iana.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3003 - assertIsFalse "foo@[\1.2.3.4]"                                                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3004 - assertIsFalse "first.\"\".last@iana.org"                                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3005 - assertIsFalse "first\last@iana.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3006 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3007 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  3008 - assertIsFalse "cal(foo(bar)@iamcal.com"                                                        =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3009 - assertIsFalse "cal(foo)bar)@iamcal.com"                                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3010 - assertIsFalse "cal(foo\)@iamcal.com"                                                           =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3011 - assertIsFalse "first(middle)last@iana.org"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3012 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3013 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3014 - assertIsFalse ".@"                                                                             =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3015 - assertIsFalse "@bar.com"                                                                       =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3016 - assertIsFalse "@@bar.com"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3017 - assertIsFalse "aaa.com"                                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3018 - assertIsFalse "aaa@.com"                                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3019 - assertIsFalse "aaa@.123"                                                                       =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3020 - assertIsFalse "aaa@[123.123.123.123]a"                                                         =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3021 - assertIsFalse "aaa@[123.123.123.333]"                                                          =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3022 - assertIsFalse "a@bar.com."                                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3023 - assertIsFalse "a@-b.com"                                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3024 - assertIsFalse "a@b-.com"                                                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3025 - assertIsFalse "-@..com"                                                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3026 - assertIsFalse "-@a..com"                                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3027 - assertIsFalse "@about.museum-"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3028 - assertIsFalse "test@...........com"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3029 - assertIsFalse "first.last@[IPv6::]"                                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3030 - assertIsFalse "first.last@[IPv6::::]"                                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3031 - assertIsFalse "first.last@[IPv6::b4]"                                                          =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3032 - assertIsFalse "first.last@[IPv6::::b4]"                                                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3033 - assertIsFalse "first.last@[IPv6::b3:b4]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3034 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3035 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3036 - assertIsFalse "first.last@[IPv6:a1:]"                                                          =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3037 - assertIsFalse "first.last@[IPv6:a1:::]"                                                        =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3038 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                                       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3039 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                                     =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3040 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3041 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3042 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                                               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3043 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                                             =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3044 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3045 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3046 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"                            =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  3047 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3048 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3049 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                                           =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3050 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                                            =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
   *  3051 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                                          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3052 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                                      =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3053 - assertIsFalse "first.last@[IPv6::a2::b4]"                                                      =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3054 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                                        =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3055 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                                        =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3056 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                                     =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3057 - assertIsFalse "first.last@[.12.34.56.78]"                                                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
   *  3058 - assertIsFalse "first.last@[12.34.56.789]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3059 - assertIsFalse "first.last@[::12.34.56.78]"                                                     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3060 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                                                =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3061 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                                                =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
   *  3062 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3063 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]"               =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3064 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]"                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3065 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"                              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3066 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3067 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"                                   =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  3068 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3069 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3070 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3071 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                    =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3072 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                                       =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3073 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                                       =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
   *  3074 - assertIsTrue  "first.last@[IPv6:::]"                                                           =   4 =  OK 
   *  3075 - assertIsTrue  "first.last@[IPv6:::b4]"                                                         =   4 =  OK 
   *  3076 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                                      =   4 =  OK 
   *  3077 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                                       =   4 =  OK 
   *  3078 - assertIsTrue  "first.last@[IPv6:a1::]"                                                         =   4 =  OK 
   *  3079 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                                      =   4 =  OK 
   *  3080 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                                        =   4 =  OK 
   *  3081 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                                        =   4 =  OK 
   *  3082 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"                                  =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3083 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"                               =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3084 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                                     =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3085 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"                                  =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3086 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                                              =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3087 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3088 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"                             =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3089 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"                             =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3090 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                                           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3091 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                           =   4 =  OK 
   * 
   * ---- https://www.rohannagar.com/jmail/ -------------------------------------------------------------------------------------------
   * 
   *  3092 - assertIsFalse "\"qu@test.org"                                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3093 - assertIsFalse "ote\"@test.org"                                                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3094 - assertIsFalse "\"().:;<>[\]@example.com"                                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3095 - assertIsFalse "\"\"\"@iana.org"                                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3096 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3097 - assertIsFalse "this is\"not\allowed@example.com"                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3098 - assertIsFalse "this\ still\"not\allowed@example.com"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3099 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                                               =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3100 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3101 - assertIsFalse "plainaddress"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3102 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3103 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                                      =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3104 - assertIsTrue  "\"first\\"last\"@test.org"                                                      =   1 =  OK 
   *  3105 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3106 - assertIsTrue  "first\@last@iana.org"                                                           =   0 =  OK 
   *  3107 - assertIsFalse "test@example.com "                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3108 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"                           =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3109 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"                                   =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3110 - assertIsFalse "invalid@about.museum-"                                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3111 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  3112 - assertIsFalse "abc@def@test.org"                                                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3113 - assertIsTrue  "abc\@def@test.org"                                                              =   0 =  OK 
   *  3114 - assertIsFalse "abc\@test.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3115 - assertIsFalse "@test.org"                                                                      =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3116 - assertIsFalse ".dot@test.org"                                                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3117 - assertIsFalse "dot.@test.org"                                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3118 - assertIsFalse "two..dot@test.org"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3119 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3120 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                                    =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3121 - assertIsFalse "hello world@test.org"                                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3122 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3123 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                                              =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3124 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3125 - assertIsFalse "test.test.org"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3126 - assertIsFalse "test.@test.org"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3127 - assertIsFalse "test..test@test.org"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3128 - assertIsFalse ".test@test.org"                                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3129 - assertIsFalse "test@test@test.org"                                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3130 - assertIsFalse "test@@test.org"                                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3131 - assertIsFalse "-- test --@test.org"                                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3132 - assertIsFalse "[test]@test.org"                                                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3133 - assertIsFalse "\"test\"test\"@test.org"                                                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3134 - assertIsFalse "()[]\;:.><@test.org"                                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3135 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3136 - assertIsFalse ".@test.org"                                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3137 - assertIsFalse "Ima Fool@test.org"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3138 - assertIsTrue  "\"first\\"last\"@test.org"                                                      =   1 =  OK 
   *  3139 - assertIsFalse "foo@[.2.3.4]"                                                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3140 - assertIsFalse "first\last@test.org"                                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3141 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3142 - assertIsFalse "first(middle)last@test.org"                                                     =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3143 - assertIsFalse "\"test\"test@test.com"                                                          =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3144 - assertIsFalse "()@test.com"                                                                    =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
   *  3145 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
   *  3146 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  3147 - assertIsFalse "invalid@[1]"                                                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3148 - assertIsFalse "ä📧@-foo"                                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3149 - assertIsFalse "ä📧@foo-"                                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3150 - assertIsFalse "first(comment(inner@comment.com"                                                =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3151 - assertIsFalse "Joe A Smith <email@example.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3152 - assertIsFalse "Joe A Smith email@example.com"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3153 - assertIsFalse "Joe A Smith <email@example.com->"                                               =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3154 - assertIsFalse "Joe A Smith <email@-example.com->"                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3155 - assertIsFalse "Joe A Smith <email>"                                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3156 - assertIsTrue  "\"email\"@example.com"                                                          =   1 =  OK 
   *  3157 - assertIsTrue  "\"first@last\"@test.org"                                                        =   1 =  OK 
   *  3158 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                                     =   1 =  OK 
   *  3159 - assertIsFalse "\"first\"last\"@test.org"                                                       =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3160 - assertIsTrue  "much.\"more\ unusual\"@example.com"                                             =   1 =  OK 
   *  3161 - assertIsFalse "\"first\last\"@test.org"                                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3162 - assertIsTrue  "\"Abc\@def\"@test.org"                                                          =   1 =  OK 
   *  3163 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                                      =   1 =  OK 
   *  3164 - assertIsFalse "\"Joe.\Blow\"@test.org"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3165 - assertIsTrue  "\"Abc@def\"@test.org"                                                           =   1 =  OK 
   *  3166 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                                       =   1 =  OK 
   *  3167 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@test.org"                                                 =   1 =  OK 
   *  3168 - assertIsTrue  "\"[[ test ]]\"@test.org"                                                        =   1 =  OK 
   *  3169 - assertIsTrue  "\"test.test\"@test.org"                                                         =   1 =  OK 
   *  3170 - assertIsTrue  "test.\"test\"@test.org"                                                         =   1 =  OK 
   *  3171 - assertIsTrue  "\"test@test\"@test.org"                                                         =   1 =  OK 
   *  3172 - assertIsFalse "\"test tabulator  est\"@test.org"                                               =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  3173 - assertIsTrue  "\"first\".\"last\"@test.org"                                                    =   1 =  OK 
   *  3174 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                                             =   1 =  OK 
   *  3175 - assertIsTrue  "\"first\".last@test.org"                                                        =   1 =  OK 
   *  3176 - assertIsTrue  "first.\"last\"@test.org"                                                        =   1 =  OK 
   *  3177 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                                         =   1 =  OK 
   *  3178 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                                             =   1 =  OK 
   *  3179 - assertIsTrue  "\"first.middle.last\"@test.org"                                                 =   1 =  OK 
   *  3180 - assertIsTrue  "\"first..last\"@test.org"                                                       =   1 =  OK 
   *  3181 - assertIsTrue  "\"Unicode NULL \"@char.com"                                                     =   1 =  OK 
   *  3182 - assertIsFalse "\"test\blah\"@test.org"                                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3183 - assertIsFalse "\"testlah\"@test.org"                                                          =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  3184 - assertIsTrue  "\"test\\"blah\"@test.org"                                                       =   1 =  OK 
   *  3185 - assertIsTrue  "\"first\\"last\"@test.org"                                                      =   1 =  OK 
   *  3186 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@test.org"                                               =   1 =  OK 
   *  3187 - assertIsFalse "\"Test \"Fail\" Ing\"@test.org"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3188 - assertIsTrue  "\"test blah\"@test.org"                                                         =   1 =  OK 
   *  3189 - assertIsTrue  "first.last@test.org"                                                            =   0 =  OK 
   *  3190 - assertIsFalse "jdoe@machine(comment).example"                                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3191 - assertIsFalse "first.\"\".last@test.org"                                                       =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3192 - assertIsFalse "\"\"@test.org"                                                                  =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3193 - assertIsTrue  "very.common@example.org"                                                        =   0 =  OK 
   *  3194 - assertIsTrue  "test/test@test.com"                                                             =   0 =  OK 
   *  3195 - assertIsTrue  "user-@example.org"                                                              =   0 =  OK 
   *  3196 - assertIsTrue  "firstname.lastname@example.com"                                                 =   0 =  OK 
   *  3197 - assertIsTrue  "email@subdomain.example.com"                                                    =   0 =  OK 
   *  3198 - assertIsTrue  "firstname+lastname@example.com"                                                 =   0 =  OK 
   *  3199 - assertIsTrue  "1234567890@example.com"                                                         =   0 =  OK 
   *  3200 - assertIsTrue  "email@example-one.com"                                                          =   0 =  OK 
   *  3201 - assertIsTrue  "_______@example.com"                                                            =   0 =  OK 
   *  3202 - assertIsTrue  "email@example.name"                                                             =   0 =  OK 
   *  3203 - assertIsTrue  "email@example.museum"                                                           =   0 =  OK 
   *  3204 - assertIsTrue  "email@example.co.jp"                                                            =   0 =  OK 
   *  3205 - assertIsTrue  "firstname-lastname@example.com"                                                 =   0 =  OK 
   *  3206 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
   *  3207 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
   *  3208 - assertIsTrue  "first.last@123.test.org"                                                        =   0 =  OK 
   *  3209 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
   *  3210 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org"      =   0 =  OK 
   *  3211 - assertIsTrue  "user+mailbox@test.org"                                                          =   0 =  OK 
   *  3212 - assertIsTrue  "customer/department=shipping@test.org"                                          =   0 =  OK 
   *  3213 - assertIsTrue  "$A12345@test.org"                                                               =   0 =  OK 
   *  3214 - assertIsTrue  "!def!xyz%abc@test.org"                                                          =   0 =  OK 
   *  3215 - assertIsTrue  "_somename@test.org"                                                             =   0 =  OK 
   *  3216 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                                                =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3217 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"                             =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3218 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]"                    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3219 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"                        =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3220 - assertIsTrue  "+@b.c"                                                                          =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  3221 - assertIsTrue  "TEST@test.org"                                                                  =   0 =  OK 
   *  3222 - assertIsTrue  "1234567890@test.org"                                                            =   0 =  OK 
   *  3223 - assertIsTrue  "test-test@test.org"                                                             =   0 =  OK 
   *  3224 - assertIsTrue  "t*est@test.org"                                                                 =   0 =  OK 
   *  3225 - assertIsTrue  "+1~1+@test.org"                                                                 =   0 =  OK 
   *  3226 - assertIsTrue  "{_test_}@test.org"                                                              =   0 =  OK 
   *  3227 - assertIsTrue  "valid@about.museum"                                                             =   0 =  OK 
   *  3228 - assertIsTrue  "a@bar"                                                                          =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
   *  3229 - assertIsFalse "cal(foo\@bar)@iamcal.com"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3230 - assertIsTrue  "(comment)test@test.org"                                                         =   6 =  OK 
   *  3231 - assertIsFalse "cal(foo\)bar)@iamcal.com"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3232 - assertIsFalse "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org"             =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3233 - assertIsFalse "first(abc\(def)@test.org"                                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
   *  3234 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@test.org"                                               =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3235 - assertIsTrue  "c@(Chris's host.)public.example"                                                =   6 =  OK 
   *  3236 - assertIsTrue  "_Yosemite.Sam@test.org"                                                         =   0 =  OK 
   *  3237 - assertIsTrue  "~@test.org"                                                                     =   0 =  OK 
   *  3238 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"                                  =   6 =  OK 
   *  3239 - assertIsTrue  "test@Bücher.ch"                                                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3240 - assertIsTrue  "あいうえお@example.com"                                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3241 - assertIsTrue  "Pelé@example.com"                                                               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3242 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3243 - assertIsTrue  "我買@屋企.香港"                                                                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3244 - assertIsTrue  "二ノ宮@黒川.日本"                                                                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3245 - assertIsTrue  "медведь@с-балалайкой.рф"                                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3246 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3247 - assertIsTrue  "email@example.com (Joe Smith)"                                                  =   6 =  OK 
   *  3248 - assertIsFalse "cal@iamcal(woo).(yay)com"                                                       = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3249 - assertIsFalse "first(abc.def).last@test.org"                                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3250 - assertIsFalse "first(a\"bc.def).last@test.org"                                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3251 - assertIsFalse "first.(\")middle.last(\")@test.org"                                             = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
   *  3252 - assertIsFalse "first().last@test.org"                                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
   *  3253 - assertIsTrue  "Abc\@def@test.org"                                                              =   0 =  OK 
   *  3254 - assertIsTrue  "Fred\ Bloggs@test.org"                                                          =   0 =  OK 
   *  3255 - assertIsTrue  "Joe.\\Blow@test.org"                                                            =   0 =  OK 
   * 
   * ---- unsorted from the WEB -------------------------------------------------------------------------------------------------------
   * 
   *  3256 - assertIsFalse "testm ail@mail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3257 - assertIsFalse "testmail@mail.com."                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3258 - assertIsFalse ".testmail@mail.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3259 - assertIsFalse " testmail@mail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3260 - assertIsTrue  "username@company.domain"                                                        =   0 =  OK 
   *  3261 - assertIsTrue  "support@whoisxmlapi.com"                                                        =   0 =  OK 
   *  3262 - assertIsTrue  "popular_website15@comPany.com"                                                  =   0 =  OK 
   *  3263 - assertIsTrue  "domain.starts.with.numbers1@1234domain.com"                                     =   0 =  OK 
   *  3264 - assertIsTrue  "domain.starts.with.numbers2@123.123domain.com"                                  =   0 =  OK 
   *  3265 - assertIsTrue  "my+address@example.org"                                                         =   0 =  OK 
   *  3266 - assertIsFalse "me@Ｄｏｍａｉｎ.com"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3267 - assertIsFalse "me@D o m a i n.com"                                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3268 - assertIsFalse "invalid@domain,com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3269 - assertIsTrue  "hello@00.pe"                                                                    =   0 =  OK 
   *  3270 - assertIsTrue  "\"@\"@domain.com"                                                               =   1 =  OK 
   *  3271 - assertIsTrue  "\@@domain.com"                                                                  =   0 =  OK 
   *  3272 - assertIsTrue  "to1@domain.com"                                                                 =   0 =  OK 
   *  3273 - assertIsTrue  "Full Name <full@example.com>"                                                   =   0 =  OK 
   *  3274 - assertIsFalse "ツ-test@joshdata.me"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3275 - assertIsFalse "user@@host"                                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3276 - assertIsTrue  "t#@d.com"                                                                       =   0 =  OK 
   *  3277 - assertIsFalse "u\"evil@domain.com"                                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3278 - assertIsTrue  "nonexistinglogin@valid-domain.com"                                              =   0 =  OK 
   *  3279 - assertIsTrue  "user@department.company.com"                                                    =   0 =  OK 
   *  3280 - assertIsTrue  "john@example.com"                                                               =   0 =  OK 
   *  3281 - assertIsTrue  "python-list@python.org"                                                         =   0 =  OK 
   *  3282 - assertIsTrue  "wha.t.`1an?ug{}ly@email.com"                                                    =   0 =  OK 
   *  3283 - assertIsFalse "Hello ABCD, here is my mail id example@me.com "                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3284 - assertIsTrue  "\"djt jr\"@wh.gov"                                                              =   1 =  OK 
   *  3285 - assertIsFalse "a..@..............a"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3286 - assertIsTrue  "me+valid@mydomain.example.net"                                                  =   0 =  OK 
   *  3287 - assertIsTrue  "revo@74.125.228.53"                                                             =   2 =  OK 
   *  3288 - assertIsFalse "revo@test&^%$#|.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3289 - assertIsTrue  "\"<script>alert('XSS')</script>\"@example.com "                                 =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3290 - assertIsTrue  "ansel@adams.photography"                                                        =   0 =  OK 
   *  3291 - assertIsFalse "<')))><@fish.left.com"                                                          =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3292 - assertIsFalse "><(((*>@fish.right.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3293 - assertIsFalse " check@this.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3294 - assertIsFalse " email@example.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3295 - assertIsFalse ".....@a...."                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3296 - assertIsFalse "..@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3297 - assertIsFalse "..@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3298 - assertIsTrue  "\"test....\"@gmail.com"                                                         =   1 =  OK 
   *  3299 - assertIsFalse "test....@gmail.com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3300 - assertIsTrue  "name@xn--4ca9at.at"                                                             =   0 =  OK 
   *  3301 - assertIsTrue  "simon-@hotmail.com"                                                             =   0 =  OK 
   *  3302 - assertIsTrue  "!@mydomain.net"                                                                 =   0 =  OK 
   *  3303 - assertIsTrue  "sean.o'leary@cobbcounty.org"                                                    =   0 =  OK 
   *  3304 - assertIsTrue  "xjhgjg876896@domain.com"                                                        =   0 =  OK 
   *  3305 - assertIsTrue  "Tony Snow <tony@example.com>"                                                   =   0 =  OK 
   *  3306 - assertIsTrue  "(tony snow) tony@example.com"                                                   =   6 =  OK 
   *  3307 - assertIsTrue  "tony%example.com@example.org"                                                   =   0 =  OK 
   *  3308 - assertIsFalse "a@[a-a:::::aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa:aa]"                          =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
   *  3309 - assertIsFalse "a-b'c_d.e@f-g.h"                                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3310 - assertIsFalse "a-b'c_d.@f-g.h"                                                                 =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3311 - assertIsFalse "a-b'c_d.e@f-.h"                                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3312 - assertIsTrue  "\"root\"@example.com"                                                           =   1 =  OK 
   *  3313 - assertIsTrue  "root@example.com"                                                               =   0 =  OK 
   *  3314 - assertIsFalse ".@s.dd"                                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3315 - assertIsFalse ".@s.dd"                                                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3316 - assertIsFalse ".a@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3317 - assertIsFalse ".a@test.com"                                                                    =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3318 - assertIsFalse ".abc@somedomain.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3319 - assertIsFalse ".dot@example.com"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3320 - assertIsFalse ".email@domain.com"                                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3321 - assertIsFalse ".journaldev@journaldev.com"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3322 - assertIsFalse ".username@yahoo.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3323 - assertIsFalse ".username@yahoo.com"                                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3324 - assertIsFalse ".xxxx@mysite.org"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3325 - assertIsFalse "stuff.@stuff.com"                                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3326 - assertIsFalse "asdf@asdf"                                                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3327 - assertIsFalse "123@$.xyz"                                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3328 - assertIsFalse "<1234   @   local(blah)  .machine .example>"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3329 - assertIsFalse "@%^%#$@#$@#.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3330 - assertIsFalse "@b.com"                                                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3331 - assertIsFalse "@domain.com"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3332 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3333 - assertIsFalse "@mail.example.com:joe@sixpack.com"                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3334 - assertIsFalse "@yahoo.com"                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3335 - assertIsFalse "@you.me.net"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3336 - assertIsFalse "A@b@c@example.com"                                                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3337 - assertIsFalse "Abc.example.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3338 - assertIsFalse "Abc@example.com."                                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3339 - assertIsFalse "Abc..123@example.com"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3340 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                                           =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3341 - assertIsFalse "Display Name <email@plus.com> (after name with display)"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3342 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                                               =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3343 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@example.com"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3344 - assertIsFalse "Foobar Some@thing.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3345 - assertIsFalse "MailTo:casesensitve@domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3346 - assertIsFalse "No -foo@bar.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3347 - assertIsFalse "No asd@-bar.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3348 - assertIsFalse "DomainHyphen@-atstart"                                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3349 - assertIsFalse "DomainHyphen@atend-.com"                                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3350 - assertIsFalse "DomainHyphen@bb.-cc"                                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3351 - assertIsFalse "DomainHyphen@bb.-cc-"                                                           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3352 - assertIsFalse "DomainHyphen@bb.cc-"                                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3353 - assertIsFalse "DomainHyphen@bb.c-c"                                                            =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  3354 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3355 - assertIsTrue  "DomainNotAllowedCharacter@a.start"                                              =   0 =  OK 
   *  3356 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3357 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3358 - assertIsFalse "DomainNotAllowedCharacter@example'"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3359 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3360 - assertIsTrue  "domain.starts.with.digit@2domain.com"                                           =   0 =  OK 
   *  3361 - assertIsTrue  "domain.ends.with.digit@domain2.com"                                             =   0 =  OK 
   *  3362 - assertIsFalse "tld.starts.with.digit@domain.2com"                                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3363 - assertIsTrue  "tld.ends.with.digit@domain.com2"                                                =   0 =  OK 
   *  3364 - assertIsTrue  "enrst.den.baecker@web.de"                                                       =   0 =  OK 
   *  3365 - assertIsTrue  "neserdna.trebkce@web.de"                                                        =   0 =  OK 
   *  3366 - assertIsTrue  "rpxoreg.naqerfra@web.de"                                                        =   0 =  OK 
   *  3367 - assertIsFalse "unescaped white space@fake$com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3368 - assertIsTrue  "\"SL-EA-234\"@kfz-kennzeichen.de"                                               =   1 =  OK 
   *  3369 - assertIsTrue  "\"Name.\"@test.de"                                                              =   1 =  OK 
   *  3370 - assertIsTrue  "\"Name..\"@test.de"                                                             =   1 =  OK 
   *  3371 - assertIsTrue  "\"Name.\\"\"@test.de"                                                           =   1 =  OK 
   *  3372 - assertIsTrue  "\".Name.\"@test.de"                                                             =   1 =  OK 
   *  3373 - assertIsTrue  "\"..Name..\"@test.de"                                                           =   1 =  OK 
   *  3374 - assertIsTrue  "\"...Name...\"@test.de"                                                         =   1 =  OK 
   *  3375 - assertIsTrue  "\" ...Name... \"@test.de"                                                       =   1 =  OK 
   *  3376 - assertIsTrue  "\"..Name\"@test.de"                                                             =   1 =  OK 
   *  3377 - assertIsTrue  "\"Name\"@test.de"                                                               =   1 =  OK 
   *  3378 - assertIsTrue  "\"\\"Name.\\"\"@test.de"                                                        =   1 =  OK 
   *  3379 - assertIsTrue  "\\\"Name.\\"\"@test.de"                                                         =   1 =  OK 
   *  3380 - assertIsTrue  "\"Vorname.Nachname\"@test.de"                                                   =   1 =  OK 
   *  3381 - assertIsTrue  "\"Vorname+Nachname\"@test.de"                                                   =   1 =  OK 
   *  3382 - assertIsTrue  "\"Vorname+Nachname.\"@test.de"                                                  =   1 =  OK 
   *  3383 - assertIsTrue  "\" 123,45 * 678,910 = 83.811,4395 \"@calc-test.de"                              =   1 =  OK 
   *  3384 - assertIsTrue  "\" 50.000,00 / 106,00 = 471.69811320754716981132075471698113 \"@calc-test.de"   =   1 =  OK 
   *  3385 - assertIsTrue  "\" 2 + 2 = 3,999999999999999999999999991 \"@erste-hochrechnung.de"              =   1 =  OK 
   *  3386 - assertIsTrue  "\".John.Doe\"@example.com"                                                      =   1 =  OK 
   *  3387 - assertIsTrue  "\"John.Doe.\"@example.com"                                                      =   1 =  OK 
   *  3388 - assertIsTrue  "\"John..Doe\"@example.com"                                                      =   1 =  OK 
   *  3389 - assertIsTrue  "john.smith(comment)@example.com"                                                =   6 =  OK 
   *  3390 - assertIsTrue  "(comment)john.smith@example.com"                                                =   6 =  OK 
   *  3391 - assertIsTrue  "john.smith@(comment)example.com"                                                =   6 =  OK 
   *  3392 - assertIsTrue  "john.smith@example.com(comment)"                                                =   6 =  OK 
   *  3393 - assertIsTrue  "john.smith@example.com"                                                         =   0 =  OK 
   *  3394 - assertIsTrue  "joeuser+tag@example.com"                                                        =   0 =  OK 
   *  3395 - assertIsTrue  "jsmith@[192.168.2.1]"                                                           =   2 =  OK 
   *  3396 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                                      =   4 =  OK 
   *  3397 - assertIsFalse "john.smith@exampl(comment)e.com"                                                = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
   *  3398 - assertIsFalse "john.s(comment)mith@example.com"                                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3399 - assertIsFalse "john.smith(comment)@(comment)example.com"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
   *  3400 - assertIsFalse "john.smith(com@ment)example.com"                                                =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3401 - assertIsFalse "\"\"Joe Smith email@domain.com"                                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3402 - assertIsFalse "\"\"Joe Smith' email@domain.com"                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3403 - assertIsFalse "\"\"Joe Smith\"\"email@domain.com"                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
   *  3404 - assertIsFalse "\"Joe Smith\" email@domain.com"                                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3405 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3406 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3407 - assertIsFalse "\"Joe Smith email@domain.com"                                                   =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3408 - assertIsFalse "\"Joe Smith' email@domain.com"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3409 - assertIsFalse "\"Joe Smith\"email@domain.com"                                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3410 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3411 - assertIsTrue  "{john'doe}@my.server"                                                           =   0 =  OK 
   *  3412 - assertIsTrue  "email@domain-one.com"                                                           =   0 =  OK 
   *  3413 - assertIsTrue  "_______@domain.com"                                                             =   0 =  OK 
   *  3414 - assertIsTrue  "?????@domain.com"                                                               =   0 =  OK 
   *  3415 - assertIsFalse "local@?????.com"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3416 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                                                  =   1 =  OK 
   *  3417 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                                         =   1 =  OK 
   *  3418 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                                      =   0 =  OK 
   *  3419 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                                               =   0 =  OK 
   *  3420 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3421 - assertIsTrue  "$!{${$!{${!{$$$!$!${$!{.${!{$!{$$!${$!$!$$$!$!{$@WeB.dE"                        =   0 =  OK 
   *  3422 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
   *  3423 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"                            =   1 =  OK 
   *  3424 - assertIsFalse "${jndi:ldap://CheckThat.HowLovely.com/a}@log4j.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3425 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3426 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                                                =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
   *  3427 - assertIsFalse "\"Joe Q. Public\" <john.q.public@example.com>"                                  =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3428 - assertIsFalse "\"Joe\Blow\"@example.com"                                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3429 - assertIsFalse "\"qu@example.com"                                                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3430 - assertIsFalse "\$A12345@example.com"                                                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3431 - assertIsFalse "_@bde.cc,"                                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3432 - assertIsFalse "a..b@bde.cc"                                                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3433 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                                     =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3434 - assertIsFalse "a.b@example,co.de"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3435 - assertIsFalse "a.b@example,com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3436 - assertIsFalse "a>b@somedomain.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3437 - assertIsFalse "a@.com"                                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3438 - assertIsFalse "a@b."                                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3439 - assertIsFalse "a@b.-de.cc"                                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3440 - assertIsFalse "a@b._de.cc"                                                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3441 - assertIsFalse "a@bde-.cc"                                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3442 - assertIsFalse "a@bde.c-c"                                                                      =   0 =  #### FEHLER ####    eMail-Adresse korrekt
   *  3443 - assertIsFalse "a@bde.cc."                                                                      =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3444 - assertIsFalse "a@bde_.cc"                                                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3445 - assertIsFalse "ab@120.25.1111.120"                                                             =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
   *  3446 - assertIsFalse "ab@120.256.256.120"                                                             =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3447 - assertIsFalse "ab@188.120.150.10]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3448 - assertIsFalse "ab@988.120.150.10"                                                              =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3449 - assertIsFalse "ab@[188.120.150.10"                                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  3450 - assertIsFalse "ab@[188.120.150.10].com"                                                        =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3451 - assertIsFalse "ab@188.120.150.10]"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3452 - assertIsFalse "ab@b+de.cc"                                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3453 - assertIsFalse "ab@sd@dd"                                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3454 - assertIsFalse "abc.@somedomain.com"                                                            =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3455 - assertIsFalse "abc@def@example.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3456 - assertIsFalse "abc@gmail..com"                                                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3457 - assertIsFalse "abc@gmail.com.."                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3458 - assertIsFalse "abc\"defghi\"xyz@example.com"                                                   =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3459 - assertIsFalse "abc\@example.com"                                                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3460 - assertIsFalse "abc\\"def\\"ghi@example.com"                                                    =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3461 - assertIsFalse "abc\\@def@example.com"                                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3462 - assertIsFalse "as3d@dac.coas-"                                                                 =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3463 - assertIsFalse "asd@dasd@asd.cm"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3464 - assertIsFalse "check@this..com"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3465 - assertIsFalse "check@thiscom"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3466 - assertIsFalse "da23@das..com"                                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3467 - assertIsFalse "dad@sds"                                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3468 - assertIsFalse "dasddas-@.com"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3469 - assertIsFalse "david.gilbertson@SOME+THING-ODD!!.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3470 - assertIsFalse "dot.@example.com"                                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3471 - assertIsFalse "doug@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3472 - assertIsFalse "email( (nested) )@plus.com"                                                     =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
   *  3473 - assertIsFalse "email(with @ in comment)plus.com"                                               =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3474 - assertIsFalse "email)mirror(@plus.com"                                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3475 - assertIsFalse "email@plus.com (not closed comment"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  3476 - assertIsTrue  "email@domain.com (joe Smith)"                                                   =   6 =  OK 
   *  3477 - assertIsTrue  "email@domain.domain.domain.com.com"                                             =   0 =  OK 
   *  3478 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                                      =   0 =  OK 
   *  3479 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"                               =   0 =  OK 
   *  3480 - assertIsFalse "email..email@domain.com"                                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3481 - assertIsFalse "email.@domain.com"                                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3482 - assertIsFalse "email.domain.com"                                                               =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3483 - assertIsFalse "email@#hash.com"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3484 - assertIsFalse "email@-domain.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3485 - assertIsFalse "email@.domain.com"                                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3486 - assertIsFalse "email@=qowaiv.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3487 - assertIsFalse "email@caret^xor.com"                                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3488 - assertIsFalse "email@colon:colon.com"                                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3489 - assertIsFalse "email@dollar$.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3490 - assertIsFalse "email@domain"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3491 - assertIsFalse "email@domain-.com"                                                              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3492 - assertIsFalse "email@domain..com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3493 - assertIsFalse "email@domain.com-"                                                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3494 - assertIsFalse "email@domain.com."                                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3495 - assertIsFalse "email@domain.com>"                                                              =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
   *  3496 - assertIsFalse "email@domain@domain.com"                                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3497 - assertIsFalse "email@example"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3498 - assertIsFalse "email@example..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3499 - assertIsFalse "email@example.co.uk."                                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3500 - assertIsFalse "email@example.com "                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3501 - assertIsFalse "email@exclamation!mark.com"                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3502 - assertIsFalse "email@grave`accent.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3503 - assertIsFalse "email@mailto:domain.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3504 - assertIsFalse "email@obelix*asterisk.com"                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3505 - assertIsFalse "email@plus+.com"                                                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3506 - assertIsFalse "email@plus.com (not closed comment"                                             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
   *  3507 - assertIsFalse "email@pp|e.com"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3508 - assertIsFalse "email@p|pe.com"                                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3509 - assertIsFalse "email@question?mark.com"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3510 - assertIsFalse "email@r&amp;d.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3511 - assertIsFalse "email@rightbracket}.com"                                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3512 - assertIsFalse "email@wave~tilde.com"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3513 - assertIsFalse "email@{leftbracket.com"                                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3514 - assertIsFalse "mailto:mailto:email@domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3515 - assertIsFalse "f...bar@gmail.com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3516 - assertIsFalse "fa ke@false.com"                                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3517 - assertIsFalse "fake@-false.com"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3518 - assertIsFalse "fake@fal se.com"                                                                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3519 - assertIsFalse "fdsa"                                                                           =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3520 - assertIsFalse "fdsa@"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3521 - assertIsFalse "fdsa@fdsa"                                                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3522 - assertIsFalse "fdsa@fdsa."                                                                     =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3523 - assertIsFalse "foo.bar#gmail.co.u"                                                             =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3524 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3525 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3526 - assertIsFalse "foo~&(&)(@bar.com"                                                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3527 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3528 - assertIsFalse "get_at_m.e@gmail"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3529 - assertIsFalse "hallo2ww22@example....caaaao"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3530 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3531 - assertIsFalse "hello world@example.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3532 - assertIsFalse "invalid.email.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3533 - assertIsFalse "invalid@email@domain.com"                                                       =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3534 - assertIsFalse "isis@100%.nl"                                                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3535 - assertIsFalse "j..s@proseware.com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3536 - assertIsFalse "j.@server1.proseware.com"                                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3537 - assertIsFalse "jane@jungle.com: | /usr/bin/vacation"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3538 - assertIsFalse "journaldev"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3539 - assertIsFalse "journaldev()*@gmail.com"                                                        =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3540 - assertIsFalse "journaldev..2002@gmail.com"                                                     =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3541 - assertIsFalse "journaldev.@gmail.com"                                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3542 - assertIsFalse "journaldev123@.com"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3543 - assertIsFalse "journaldev123@.com.com"                                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3544 - assertIsFalse "journaldev123@gmail.a"                                                          =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3545 - assertIsFalse "journaldev@%*.com"                                                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3546 - assertIsFalse "journaldev@.com.my"                                                             =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3547 - assertIsFalse "journaldev@gmail.com.1a"                                                        =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3548 - assertIsFalse "journaldev@journaldev@gmail.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3549 - assertIsFalse "js@proseware..com"                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3550 - assertIsFalse "mailto:email@domain.com"                                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3551 - assertIsFalse "mailto:mailto:email@domain.com"                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3552 - assertIsFalse "me..2002@gmail.com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3553 - assertIsFalse "me.@gmail.com"                                                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3554 - assertIsFalse "me123@.com"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3555 - assertIsFalse "me123@.com.com"                                                                 =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3556 - assertIsFalse "me@%*.com"                                                                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3557 - assertIsFalse "me@.com.my"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3558 - assertIsFalse "me@gmail.com.1a"                                                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3559 - assertIsFalse "me@me@gmail.com"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3560 - assertIsFalse "myemail@@sample.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3561 - assertIsFalse "myemail@sa@mple.com"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3562 - assertIsFalse "myemailsample.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3563 - assertIsFalse "ote\"@example.com"                                                              =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
   *  3564 - assertIsFalse "pio_pio@#factory.com"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3565 - assertIsFalse "pio_pio@factory.c#om"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3566 - assertIsFalse "pio_pio@factory.c*om"                                                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3567 - assertIsFalse "plain.address"                                                                  =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3568 - assertIsFalse "plainaddress"                                                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3569 - assertIsFalse "tarzan@jungle.org,jane@jungle.org"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3570 - assertIsFalse "this is not valid@email$com"                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3571 - assertIsFalse "this\ still\\"not\allowed@example.com"                                          =  84 =  OK    String: Ungueltige Escape-Sequenz im String
   *  3572 - assertIsFalse "two..dot@example.com"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3573 - assertIsFalse "user#domain.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3574 - assertIsFalse "username@yahoo..com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3575 - assertIsFalse "username@yahoo.c"                                                               =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3576 - assertIsTrue  "username@domain.com"                                                            =   0 =  OK 
   *  3577 - assertIsTrue  "_username@domain.com"                                                           =   0 =  OK 
   *  3578 - assertIsTrue  "username_@domain.com"                                                           =   0 =  OK 
   *  3579 - assertIsFalse "xxx@u*.com"                                                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3580 - assertIsFalse "xxxx..1234@yahoo.com"                                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3581 - assertIsFalse "xxxx.ourearth.com"                                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3582 - assertIsFalse "xxxx123@gmail.b"                                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3583 - assertIsFalse "xxxx@.com.my"                                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3584 - assertIsFalse "xxxx@.org.org"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3585 - assertIsFalse "xxxxx()*@gmail.com"                                                             =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
   *  3586 - assertIsFalse "{something}@{something}.{something}"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3587 - assertIsTrue  "!def!xyz%abc@example.com"                                                       =   0 =  OK 
   *  3588 - assertIsTrue  "!sd@gh.com"                                                                     =   0 =  OK 
   *  3589 - assertIsTrue  "$A12345@example.com"                                                            =   0 =  OK 
   *  3590 - assertIsTrue  "%20f3v34g34@gvvre.com"                                                          =   0 =  OK 
   *  3591 - assertIsTrue  "%2@gmail.com"                                                                   =   0 =  OK 
   *  3592 - assertIsTrue  "--@ooo.ooo"                                                                     =   0 =  OK 
   *  3593 - assertIsTrue  "-@bde.cc"                                                                       =   0 =  OK 
   *  3594 - assertIsTrue  "-asd@das.com"                                                                   =   0 =  OK 
   *  3595 - assertIsTrue  "1234567890@domain.com"                                                          =   0 =  OK 
   *  3596 - assertIsTrue  "1@domain.com"                                                                   =   0 =  OK 
   *  3597 - assertIsTrue  "1@gmail.com"                                                                    =   0 =  OK 
   *  3598 - assertIsTrue  "1_example@something.gmail.com"                                                  =   0 =  OK 
   *  3599 - assertIsTrue  "2@bde.cc"                                                                       =   0 =  OK 
   *  3600 - assertIsTrue  "3c296rD3HNEE@d139c.a51"                                                         =   0 =  OK 
   *  3601 - assertIsTrue  "<boss@nil.test>"                                                                =   0 =  OK 
   *  3602 - assertIsTrue  "<john@doe.com>"                                                                 =   0 =  OK 
   *  3603 - assertIsTrue  "A__z/J0hn.sm{it!}h_comment@example.com.co"                                      =   0 =  OK 
   *  3604 - assertIsTrue  "Abc.123@example.com"                                                            =   0 =  OK 
   *  3605 - assertIsTrue  "Abc@10.42.0.1"                                                                  =   2 =  OK 
   *  3606 - assertIsTrue  "Abc@example.com"                                                                =   0 =  OK 
   *  3607 - assertIsTrue  "D.Oy'Smith@gmail.com"                                                           =   0 =  OK 
   *  3608 - assertIsTrue  "Fred\ Bloggs@example.com"                                                       =   0 =  OK 
   *  3609 - assertIsTrue  "Joe.\\Blow@example.com"                                                         =   0 =  OK 
   *  3610 - assertIsTrue  "John <john@doe.com>"                                                            =   0 =  OK 
   *  3611 - assertIsTrue  "mymail\@hello@hotmail.com"                                                      =   0 =  OK 
   *  3612 - assertIsTrue  "PN=Joe/OU=X400/@gateway.com"                                                    =   0 =  OK 
   *  3613 - assertIsTrue  "This is <john@127.0.0.1>"                                                       =   2 =  OK 
   *  3614 - assertIsTrue  "This is <john@[127.0.0.1]>"                                                     =   2 =  OK 
   *  3615 - assertIsTrue  "Who? <one@y.test>"                                                              =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
   *  3616 - assertIsTrue  "\" \"@example.org"                                                              =   1 =  OK 
   *  3617 - assertIsTrue  "\"%2\"@gmail.com"                                                               =   1 =  OK 
   *  3618 - assertIsTrue  "\"Abc@def\"@example.com"                                                        =   1 =  OK 
   *  3619 - assertIsTrue  "\"Abc\@def\"@example.com"                                                       =   1 =  OK 
   *  3620 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                                              =   1 =  OK 
   *  3621 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                                    =   1 =  OK 
   *  3622 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                                                   =   1 =  OK 
   *  3623 - assertIsTrue  "\"Giant; \\"Big\\" Box\" <sysservices@example.net>"                             =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
   *  3624 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                                      =   1 =  OK 
   *  3625 - assertIsTrue  "\"Look at all these spaces!\"@example.com"                                      =   1 =  OK 
   *  3626 - assertIsTrue  "\"a..b\"@gmail.com"                                                             =   1 =  OK 
   *  3627 - assertIsTrue  "\"a@b\"@example.com"                                                            =   1 =  OK 
   *  3628 - assertIsTrue  "\"a_b\"@gmail.com"                                                              =   1 =  OK 
   *  3629 - assertIsTrue  "\"abcdefghixyz\"@example.com"                                                   =   1 =  OK 
   *  3630 - assertIsTrue  "\"cogwheel the orange\"@example.com"                                            =   1 =  OK 
   *  3631 - assertIsTrue  "\"foo\@bar\"@Something.com"                                                     =   1 =  OK 
   *  3632 - assertIsTrue  "\"j\\"s\"@proseware.com"                                                        =   1 =  OK 
   *  3633 - assertIsTrue  "\"myemail@sa\"@mple.com"                                                        =   1 =  OK 
   *  3634 - assertIsTrue  "_-_@bde.cc"                                                                     =   0 =  OK 
   *  3635 - assertIsTrue  "_@gmail.com"                                                                    =   0 =  OK 
   *  3636 - assertIsTrue  "_dasd@sd.com"                                                                   =   0 =  OK 
   *  3637 - assertIsTrue  "_dasd_das_@9.com"                                                               =   0 =  OK 
   *  3638 - assertIsTrue  "_somename@example.com"                                                          =   0 =  OK 
   *  3639 - assertIsTrue  "a&d@somedomain.com"                                                             =   0 =  OK 
   *  3640 - assertIsTrue  "a*d@somedomain.com"                                                             =   0 =  OK 
   *  3641 - assertIsTrue  "a+b@bde.cc"                                                                     =   0 =  OK 
   *  3642 - assertIsTrue  "a+b@c.com"                                                                      =   0 =  OK 
   *  3643 - assertIsTrue  "a-b@bde.cc"                                                                     =   0 =  OK 
   *  3644 - assertIsTrue  "a.a@test.com"                                                                   =   0 =  OK 
   *  3645 - assertIsTrue  "a.b@com"                                                                        =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3646 - assertIsTrue  "a/d@somedomain.com"                                                             =   0 =  OK 
   *  3647 - assertIsTrue  "a2@bde.cc"                                                                      =   0 =  OK 
   *  3648 - assertIsTrue  "a@123.45.67.89"                                                                 =   2 =  OK 
   *  3649 - assertIsTrue  "a@b.c.com"                                                                      =   0 =  OK 
   *  3650 - assertIsTrue  "a@b.com"                                                                        =   0 =  OK 
   *  3651 - assertIsTrue  "a@bc.com"                                                                       =   0 =  OK 
   *  3652 - assertIsTrue  "a@bcom"                                                                         =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3653 - assertIsTrue  "a@domain.com"                                                                   =   0 =  OK 
   *  3654 - assertIsTrue  "a__z@provider.com"                                                              =   0 =  OK 
   *  3655 - assertIsTrue  "a_z%@gmail.com"                                                                 =   0 =  OK 
   *  3656 - assertIsTrue  "aaron@theinfo.org"                                                              =   0 =  OK 
   *  3657 - assertIsTrue  "ab@288.120.150.10.com"                                                          =   0 =  OK 
   *  3658 - assertIsTrue  "ab@[120.254.254.120]"                                                           =   2 =  OK 
   *  3659 - assertIsTrue  "ab@b-de.cc"                                                                     =   0 =  OK 
   *  3660 - assertIsTrue  "ab@c.com"                                                                       =   0 =  OK 
   *  3661 - assertIsTrue  "ab_c@bde.cc"                                                                    =   0 =  OK 
   *  3662 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                                                 =   1 =  OK 
   *  3663 - assertIsTrue  "abc.efg@gmail.com"                                                              =   0 =  OK 
   *  3664 - assertIsTrue  "abc.xyz@gmail.com.in"                                                           =   0 =  OK 
   *  3665 - assertIsTrue  "abc123xyz@asdf.co.in"                                                           =   0 =  OK 
   *  3666 - assertIsTrue  "abc1_xyz1@gmail1.com"                                                           =   0 =  OK 
   *  3667 - assertIsTrue  "abc@abc.abc"                                                                    =   0 =  OK 
   *  3668 - assertIsTrue  "abc@abc.abcd"                                                                   =   0 =  OK 
   *  3669 - assertIsTrue  "abc@abc.abcde"                                                                  =   0 =  OK 
   *  3670 - assertIsTrue  "abc@abc.co.in"                                                                  =   0 =  OK 
   *  3671 - assertIsTrue  "abc@abc.com.com.com.com"                                                        =   0 =  OK 
   *  3672 - assertIsTrue  "abc@gmail.com.my"                                                               =   0 =  OK 
   *  3673 - assertIsTrue  "abc\@def@example.com"                                                           =   0 =  OK 
   *  3674 - assertIsTrue  "abc\\@example.com"                                                              =   0 =  OK 
   *  3675 - assertIsTrue  "abcxyz123@qwert.com"                                                            =   0 =  OK 
   *  3676 - assertIsTrue  "alex@example.com"                                                               =   0 =  OK 
   *  3677 - assertIsTrue  "alireza@test.co.uk"                                                             =   0 =  OK 
   *  3678 - assertIsTrue  "asd-@asd.com"                                                                   =   0 =  OK 
   *  3679 - assertIsTrue  "begeddov@jfinity.com"                                                           =   0 =  OK 
   *  3680 - assertIsTrue  "check@this.com"                                                                 =   0 =  OK 
   *  3681 - assertIsTrue  "cog@wheel.com"                                                                  =   0 =  OK 
   *  3682 - assertIsTrue  "customer/department=shipping@example.com"                                       =   0 =  OK 
   *  3683 - assertIsTrue  "d._.___d@gmail.com"                                                             =   0 =  OK 
   *  3684 - assertIsTrue  "d.j@server1.proseware.com"                                                      =   0 =  OK 
   *  3685 - assertIsTrue  "d.oy.smith@gmail.com"                                                           =   0 =  OK 
   *  3686 - assertIsTrue  "d23d@da9.co9"                                                                   =   0 =  OK 
   *  3687 - assertIsTrue  "d_oy_smith@gmail.com"                                                           =   0 =  OK 
   *  3688 - assertIsTrue  "dasd-dasd@das.com.das"                                                          =   0 =  OK 
   *  3689 - assertIsTrue  "dasd.dadas@dasd.com"                                                            =   0 =  OK 
   *  3690 - assertIsTrue  "dasd_-@jdas.com"                                                                =   0 =  OK 
   *  3691 - assertIsTrue  "david.jones@proseware.com"                                                      =   0 =  OK 
   *  3692 - assertIsTrue  "dda_das@das-dasd.com"                                                           =   0 =  OK 
   *  3693 - assertIsTrue  "digit-only-domain-with-subdomain@sub.123.com"                                   =   0 =  OK 
   *  3694 - assertIsTrue  "digit-only-domain@123.com"                                                      =   0 =  OK 
   *  3695 - assertIsTrue  "doysmith@gmail.com"                                                             =   0 =  OK 
   *  3696 - assertIsTrue  "drp@drp.cz"                                                                     =   0 =  OK 
   *  3697 - assertIsTrue  "dsq!a?@das.com"                                                                 =   0 =  OK 
   *  3698 - assertIsTrue  "email@domain.co.de"                                                             =   0 =  OK 
   *  3699 - assertIsTrue  "email@domain.com"                                                               =   0 =  OK 
   *  3700 - assertIsTrue  "email@example.co.uk"                                                            =   0 =  OK 
   *  3701 - assertIsTrue  "email@example.com"                                                              =   0 =  OK 
   *  3702 - assertIsTrue  "email@mail.gmail.com"                                                           =   0 =  OK 
   *  3703 - assertIsTrue  "email@subdomain.domain.com"                                                     =   0 =  OK 
   *  3704 - assertIsTrue  "example@example.co"                                                             =   0 =  OK 
   *  3705 - assertIsTrue  "f.f.f@bde.cc"                                                                   =   0 =  OK 
   *  3706 - assertIsTrue  "f.o.o.b.a.r@gmail.com"                                                          =   0 =  OK 
   *  3707 - assertIsTrue  "first-name-last-name@d-a-n.com"                                                 =   0 =  OK 
   *  3708 - assertIsTrue  "firstname+lastname@domain.com"                                                  =   0 =  OK 
   *  3709 - assertIsTrue  "firstname-lastname@domain.com"                                                  =   0 =  OK 
   *  3710 - assertIsTrue  "firstname.lastname@domain.com"                                                  =   0 =  OK 
   *  3711 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                                      =   0 =  OK 
   *  3712 - assertIsTrue  "futureTLD@somewhere.fooo"                                                       =   0 =  OK 
   *  3713 - assertIsTrue  "hello.me_1@email.com"                                                           =   0 =  OK 
   *  3714 - assertIsTrue  "hello7___@ca.com.pt"                                                            =   0 =  OK 
   *  3715 - assertIsTrue  "info@ermaelan.com"                                                              =   0 =  OK 
   *  3716 - assertIsTrue  "j.s@server1.proseware.com"                                                      =   0 =  OK 
   *  3717 - assertIsTrue  "j@proseware.com9"                                                               =   0 =  OK 
   *  3718 - assertIsTrue  "j_9@[129.126.118.1]"                                                            =   2 =  OK 
   *  3719 - assertIsTrue  "jinujawad6s@gmail.com"                                                          =   0 =  OK 
   *  3720 - assertIsTrue  "john.doe@example.com"                                                           =   0 =  OK 
   *  3721 - assertIsTrue  "john.o'doe@example.com"                                                         =   0 =  OK 
   *  3722 - assertIsTrue  "jones@ms1.proseware.com"                                                        =   0 =  OK 
   *  3723 - assertIsTrue  "journaldev+100@gmail.com"                                                       =   0 =  OK 
   *  3724 - assertIsTrue  "journaldev-100@journaldev.net"                                                  =   0 =  OK 
   *  3725 - assertIsTrue  "journaldev-100@yahoo-test.com"                                                  =   0 =  OK 
   *  3726 - assertIsTrue  "journaldev-100@yahoo.com"                                                       =   0 =  OK 
   *  3727 - assertIsTrue  "journaldev.100@journaldev.com.au"                                               =   0 =  OK 
   *  3728 - assertIsTrue  "journaldev.100@yahoo.com"                                                       =   0 =  OK 
   *  3729 - assertIsTrue  "journaldev111@journaldev.com"                                                   =   0 =  OK 
   *  3730 - assertIsTrue  "journaldev@1.com"                                                               =   0 =  OK 
   *  3731 - assertIsTrue  "journaldev@gmail.com.com"                                                       =   0 =  OK 
   *  3732 - assertIsTrue  "journaldev@yahoo.com"                                                           =   0 =  OK 
   *  3733 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                                              =   0 =  OK 
   *  3734 - assertIsTrue  "js#internal@proseware.com"                                                      =   0 =  OK 
   *  3735 - assertIsTrue  "js*@proseware.com"                                                              =   0 =  OK 
   *  3736 - assertIsTrue  "js@proseware.com9"                                                              =   0 =  OK 
   *  3737 - assertIsTrue  "me+100@me.com"                                                                  =   0 =  OK 
   *  3738 - assertIsTrue  "me+alpha@example.com"                                                           =   0 =  OK 
   *  3739 - assertIsTrue  "me-100@me.com"                                                                  =   0 =  OK 
   *  3740 - assertIsTrue  "me-100@me.com.au"                                                               =   0 =  OK 
   *  3741 - assertIsTrue  "me-100@yahoo-test.com"                                                          =   0 =  OK 
   *  3742 - assertIsTrue  "me.100@me.com"                                                                  =   0 =  OK 
   *  3743 - assertIsTrue  "me@aaronsw.com"                                                                 =   0 =  OK 
   *  3744 - assertIsTrue  "me@company.co.uk"                                                               =   0 =  OK 
   *  3745 - assertIsTrue  "me@gmail.com"                                                                   =   0 =  OK 
   *  3746 - assertIsTrue  "me@gmail.com"                                                                   =   0 =  OK 
   *  3747 - assertIsTrue  "me@mail.s2.example.com"                                                         =   0 =  OK 
   *  3748 - assertIsTrue  "me@me.cu.uk"                                                                    =   0 =  OK 
   *  3749 - assertIsTrue  "my.ownsite@ourearth.org"                                                        =   0 =  OK 
   *  3750 - assertIsFalse "myemail@sample"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3751 - assertIsTrue  "myemail@sample.com"                                                             =   0 =  OK 
   *  3752 - assertIsTrue  "mysite@you.me.net"                                                              =   0 =  OK 
   *  3753 - assertIsTrue  "o'hare@example.com"                                                             =   0 =  OK 
   *  3754 - assertIsTrue  "peter.example@domain.comau"                                                     =   0 =  OK 
   *  3755 - assertIsTrue  "peter.piper@example.com"                                                        =   0 =  OK 
   *  3756 - assertIsTrue  "peter_123@news.com"                                                             =   0 =  OK 
   *  3757 - assertIsTrue  "pio^_pio@factory.com"                                                           =   0 =  OK 
   *  3758 - assertIsTrue  "pio_#pio@factory.com"                                                           =   0 =  OK 
   *  3759 - assertIsTrue  "pio_pio@factory.com"                                                            =   0 =  OK 
   *  3760 - assertIsTrue  "pio_~pio@factory.com"                                                           =   0 =  OK 
   *  3761 - assertIsTrue  "piskvor@example.lighting"                                                       =   0 =  OK 
   *  3762 - assertIsTrue  "rss-dev@yahoogroups.com"                                                        =   0 =  OK 
   *  3763 - assertIsTrue  "someone+tag@somewhere.net"                                                      =   0 =  OK 
   *  3764 - assertIsTrue  "someone@somewhere.co.uk"                                                        =   0 =  OK 
   *  3765 - assertIsTrue  "someone@somewhere.com"                                                          =   0 =  OK 
   *  3766 - assertIsTrue  "something_valid@somewhere.tld"                                                  =   0 =  OK 
   *  3767 - assertIsTrue  "tvf@tvf.cz"                                                                     =   0 =  OK 
   *  3768 - assertIsTrue  "user#@domain.co.in"                                                             =   0 =  OK 
   *  3769 - assertIsTrue  "user'name@domain.co.in"                                                         =   0 =  OK 
   *  3770 - assertIsTrue  "user+mailbox@example.com"                                                       =   0 =  OK 
   *  3771 - assertIsTrue  "user-name@domain.co.in"                                                         =   0 =  OK 
   *  3772 - assertIsTrue  "user.name@domain.com"                                                           =   0 =  OK 
   *  3773 - assertIsFalse ".user.name@domain.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3774 - assertIsFalse "user-name@domain.com."                                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3775 - assertIsFalse "username@.com"                                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3776 - assertIsTrue  "user1@domain.com"                                                               =   0 =  OK 
   *  3777 - assertIsTrue  "user?name@domain.co.in"                                                         =   0 =  OK 
   *  3778 - assertIsTrue  "user@domain.co.in"                                                              =   0 =  OK 
   *  3779 - assertIsTrue  "user@domain.com"                                                                =   0 =  OK 
   *  3780 - assertIsFalse "user@domaincom"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3781 - assertIsTrue  "user_name@domain.co.in"                                                         =   0 =  OK 
   *  3782 - assertIsTrue  "user_name@domain.com"                                                           =   0 =  OK 
   *  3783 - assertIsTrue  "username@yahoo.corporate"                                                       =   0 =  OK 
   *  3784 - assertIsTrue  "username@yahoo.corporate.in"                                                    =   0 =  OK 
   *  3785 - assertIsTrue  "username+something@domain.com"                                                  =   0 =  OK 
   *  3786 - assertIsTrue  "vdv@dyomedea.com"                                                               =   0 =  OK 
   *  3787 - assertIsTrue  "xxxx@gmail.com"                                                                 =   0 =  OK 
   *  3788 - assertIsTrue  "xxxxxx@yahoo.com"                                                               =   0 =  OK 
   *  3789 - assertIsFalse "first;name)lastname@domain.com(blah"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3790 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                                        =   4 =  OK 
   *  3791 - assertIsFalse "user@localserver"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3792 - assertIsTrue  "w.b.f@test.com"                                                                 =   0 =  OK 
   *  3793 - assertIsTrue  "w.b.f@test.museum"                                                              =   0 =  OK 
   *  3794 - assertIsTrue  "yoursite@ourearth.com"                                                          =   0 =  OK 
   *  3795 - assertIsTrue  "~pio_pio@factory.com"                                                           =   0 =  OK 
   *  3796 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                                       =   0 =  OK 
   *  3797 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
   *  3798 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-six-characters.and-this-address-is-257-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-blah-.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3799 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
   *  3800 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bla.org" =   0 =  OK 
   *  3801 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net"   =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  3802 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com"                 =   0 =  OK 
   *  3803 - assertIsTrue  "valid@[1.1.1.1]"                                                                =   2 =  OK 
   *  3804 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:127.0.0.1]"               =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3805 - assertIsTrue  "valid.ipv6v4.addr@[IPv6:::12.34.56.78]"                                         =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3806 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80::230:48ff:fe33:bc33]"                                =   4 =  OK 
   *  3807 - assertIsTrue  "valid.ipv6.addr@[IPv6:fe80:0000:0000:0000:0202:b3ff:fe1e:8329]"                 =   4 =  OK 
   *  3808 - assertIsTrue  "valid.ipv6.addr@[IPv6:::]"                                                      =   4 =  OK 
   *  3809 - assertIsTrue  "valid.ipv6.addr@[IPv6:::3333:4444:5555:6666:7777:8888]"                         =   4 =  OK 
   *  3810 - assertIsTrue  "valid.ipv6.addr@[IPv6:::12.34.56.78]"                                           =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
   *  3811 - assertIsTrue  "valid.ipv6.addr@[IPv6:2607:f0d0:1002:51::4]"                                    =   4 =  OK 
   *  3812 - assertIsTrue  "valid.ipv6.addr@[IPv6:0::1]"                                                    =   4 =  OK 
   *  3813 - assertIsTrue  "valid.ipv4.addr@[255.255.255.255]"                                              =   2 =  OK 
   *  3814 - assertIsTrue  "valid.ipv4.addr@[123.1.72.10]"                                                  =   2 =  OK 
   *  3815 - assertIsFalse "\"invalid-qstring@example.com"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
   *  3816 - assertIsFalse "\"locál-part\"@example.com"                                                     =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
   *  3817 - assertIsFalse "invalid @"                                                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3818 - assertIsFalse "invalid"                                                                        =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3819 - assertIsFalse "invalid@"                                                                       =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *  3820 - assertIsFalse "invalid@[10.1.52]"                                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3821 - assertIsFalse "invalid@[10.1]"                                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3822 - assertIsFalse "invalid@[10]"                                                                   =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3823 - assertIsFalse "invalid@[111.111.111.111"                                                       =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  3824 - assertIsFalse "invalid@[127.0.0.1.]"                                                           =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
   *  3825 - assertIsFalse "invalid@[127.0.0.1]."                                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3826 - assertIsFalse "invalid@[127.0.0.1]x"                                                           =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
   *  3827 - assertIsFalse "invalid@[256.256.256.256]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3828 - assertIsFalse "invalid@[555.666.777.888]"                                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3829 - assertIsFalse "invalid@[IPv6:1111:1111]"                                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
   *  3830 - assertIsFalse "invalid@[IPv6:1111::1111::1111]"                                                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3831 - assertIsFalse "invalid@[IPv6:1111:::1111::1111]"                                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
   *  3832 - assertIsFalse "invalid@[IPv6:123456]"                                                          =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
   *  3833 - assertIsFalse "invalid@[IPv6:1::2:]"                                                           =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3834 - assertIsFalse "invalid@[IPv6:2607:f0d0:1002:51::4"                                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
   *  3835 - assertIsFalse "invalid@[IPv6::1::1]"                                                           =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
   *  3836 - assertIsFalse "invalid@[IPv6:aaaa:aaaa:aaaa:aaaa:aaaa:aaaa:555.666.777.888]"                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
   *  3837 - assertIsFalse "invalid@[]"                                                                     =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
   *  3838 - assertIsFalse "invalid@domain1.com@domain2.com"                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   * 
   * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs --------------------------------------------------
   * 
   *  3839 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  3840 - assertIsTrue  "\"back\slash\"@sld.com"                                                         =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
   *  3841 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                                    =   1 =  OK 
   *  3842 - assertIsTrue  "\"quoted\"@sld.com"                                                             =   1 =  OK 
   *  3843 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                                             =   1 =  OK 
   *  3844 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"                             =   0 =  OK 
   *  3845 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                                            =   0 =  OK 
   *  3846 - assertIsTrue  "01234567890@numbers-in-local.net"                                               =   0 =  OK 
   *  3847 - assertIsTrue  "a@single-character-in-local.org"                                                =   0 =  OK 
   *  3848 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org"      =   0 =  OK 
   *  3849 - assertIsTrue  "backticksarelegit@test.com"                                                     =   0 =  OK 
   *  3850 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                                     =   2 =  OK 
   *  3851 - assertIsTrue  "country-code-tld@sld.rw"                                                        =   0 =  OK 
   *  3852 - assertIsTrue  "country-code-tld@sld.uk"                                                        =   0 =  OK 
   *  3853 - assertIsTrue  "letters-in-sld@123.com"                                                         =   0 =  OK 
   *  3854 - assertIsTrue  "local@dash-in-sld.com"                                                          =   0 =  OK 
   *  3855 - assertIsTrue  "local@sld.newTLD"                                                               =   0 =  OK 
   *  3856 - assertIsTrue  "local@sub.domains.com"                                                          =   0 =  OK 
   *  3857 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                                               =   0 =  OK 
   *  3858 - assertIsTrue  "one-character-third-level@a.example.com"                                        =   0 =  OK 
   *  3859 - assertIsTrue  "one-letter-sld@x.org"                                                           =   0 =  OK 
   *  3860 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                                       =   0 =  OK 
   *  3861 - assertIsTrue  "single-character-in-sld@x.org"                                                  =   0 =  OK 
   *  3862 - assertIsTrue  "uncommon-tld@sld.mobi"                                                          =   0 =  OK 
   *  3863 - assertIsTrue  "uncommon-tld@sld.museum"                                                        =   0 =  OK 
   *  3864 - assertIsTrue  "uncommon-tld@sld.travel"                                                        =   0 =  OK 
   *  3865 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"                              =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3866 - assertIsFalse "@missing-local.org"                                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3867 - assertIsFalse "IP-and-port@127.0.0.1:25"                                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3868 - assertIsFalse "another-invalid-ip@127.0.0.256"                                                 =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3869 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
   *  3870 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
   *  3871 - assertIsFalse "invalid-ip@127.0.0.1.26"                                                        =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  3872 - assertIsFalse "local-ends-with-dot.@sld.com"                                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3873 - assertIsFalse "missing-at-sign.net"                                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3874 - assertIsFalse "missing-sld@.com"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3875 - assertIsFalse "missing-tld@sld."                                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3876 - assertIsFalse "sld-ends-with-dash@sld-.com"                                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3877 - assertIsFalse "sld-starts-with-dash@-sld.com"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3878 - assertIsFalse "two..consecutive-dots@sld.com"                                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3879 - assertIsTrue  "unbracketed-IP@127.0.0.1"                                                       =   2 =  OK 
   *  3880 - assertIsFalse "underscore.error@example.com_"                                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   * 
   * ---- https://mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/ -------------------------------
   * 
   *  3881 - assertIsTrue  "hello@example.com"                                                              =   0 =  OK 
   *  3882 - assertIsTrue  "hello@example.co.uk"                                                            =   0 =  OK 
   *  3883 - assertIsTrue  "hello-2020@example.com"                                                         =   0 =  OK 
   *  3884 - assertIsTrue  "hello.2020@example.com"                                                         =   0 =  OK 
   *  3885 - assertIsTrue  "hello_2020@example.com"                                                         =   0 =  OK 
   *  3886 - assertIsTrue  "h@example.com"                                                                  =   0 =  OK 
   *  3887 - assertIsTrue  "h@example-example.com"                                                          =   0 =  OK 
   *  3888 - assertIsTrue  "h@example-example-example.com"                                                  =   0 =  OK 
   *  3889 - assertIsTrue  "h@example.example-example.com"                                                  =   0 =  OK 
   *  3890 - assertIsTrue  "hello.world-2020@example.com"                                                   =   0 =  OK 
   *  3891 - assertIsTrue  "hello@example_example.com"                                                      =   0 =  OK 
   *  3892 - assertIsTrue  "hello!+2020@example.com"                                                        =   0 =  OK 
   *  3893 - assertIsFalse "hello"                                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3894 - assertIsFalse "hello@2020@example.com"                                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  3895 - assertIsTrue  "hello\@2020@example.com"                                                        =   0 =  OK 
   *  3896 - assertIsTrue  "(comment @) hello\@2020@example.com"                                            =   6 =  OK 
   *  3897 - assertIsFalse ".hello@example.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3898 - assertIsFalse "hello.@example.com"                                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  3899 - assertIsFalse "hello..world@example.com"                                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3900 - assertIsTrue  "hello.world.\"string ..\"@example.com"                                          =   1 =  OK 
   *  3901 - assertIsTrue  "(comment ..) hello.world.\"string ..\"@example.com"                             =   7 =  OK 
   *  3902 - assertIsTrue  "hello.world.\"string ..\"@example.com (comment ..)"                             =   7 =  OK 
   *  3903 - assertIsFalse "hello@example.a"                                                                =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3904 - assertIsFalse "hello@example..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3905 - assertIsFalse "hello@.com"                                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3906 - assertIsFalse "hello@.com."                                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  3907 - assertIsFalse "hello@-example.com"                                                             =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  3908 - assertIsFalse "hello@example.com-"                                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  3909 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234xx@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
   *  3910 - assertIsTrue  "email@goes_here.com"                                                            =   0 =  OK 
   *  3911 - assertIsTrue  "double--dash@example.com"                                                       =   0 =  OK 
   * 
   * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------
   * 
   *  3912 - assertIsFalse ""                                                                               =  11 =  OK    Laenge: Eingabe ist Leerstring
   *  3913 - assertIsFalse " "                                                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3914 - assertIsFalse " jkt@gmail.com"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3915 - assertIsFalse "jkt@gmail.com "                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3916 - assertIsFalse "jkt@ gmail.com"                                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3917 - assertIsFalse "jkt@g mail.com"                                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  3918 - assertIsFalse "jkt @gmail.com"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  3919 - assertIsFalse "j kt@gmail.com"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * ---- https://www.abstractapi.com/guides/java-email-validation --------------------------------------------------------------------
   * 
   *  3920 - assertIsTrue  "\"test123\"@gmail.com"                                                          =   1 =  OK 
   *  3921 - assertIsTrue  "test123@gmail.comcomco"                                                         =   0 =  OK 
   *  3922 - assertIsTrue  "test123@gmail.c"                                                                =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
   *  3923 - assertIsTrue  "test1&23@gmail.com"                                                             =   0 =  OK 
   *  3924 - assertIsTrue  "test123@gmail.com"                                                              =   0 =  OK 
   *  3925 - assertIsFalse "test123@gmail..com"                                                             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3926 - assertIsFalse ".test123@gmail.com"                                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3927 - assertIsFalse "test123@gmail.com."                                                             =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3928 - assertIsFalse "test1Ὠ23@gmail.com"                                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   * 
   * ---- https://www.javatpoint.com/java-email-validation ----------------------------------------------------------------------------
   * 
   *  3929 - assertIsTrue  "javaTpoint@domain.com"                                                          =   0 =  OK 
   *  3930 - assertIsTrue  "javaTpoint@domain.co.in"                                                        =   0 =  OK 
   *  3931 - assertIsTrue  "javaTpoint.name@domain.com"                                                     =   0 =  OK 
   *  3932 - assertIsTrue  "javaTpoint#@domain.co.in"                                                       =   0 =  OK 
   *  3933 - assertIsTrue  "java'Tpoint@domain.com"                                                         =   0 =  OK 
   *  3934 - assertIsTrue  "1avaTpoint#@domain.co.in"                                                       =   0 =  OK 
   *  3935 - assertIsTrue  "12453@domain.com"                                                               =   0 =  OK 
   *  3936 - assertIsFalse "javaTpoint@domaincom"                                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3937 - assertIsFalse "javaTpoint@domain.com."                                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  3938 - assertIsFalse "javaTpoint@domain..com"                                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3939 - assertIsFalse "javaTpoint#domain.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3940 - assertIsFalse ".javaTpoint@yahoo.com"                                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3941 - assertIsFalse "@yahoo.com"                                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3942 - assertIsFalse "javaTpoint#domain.com"                                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3943 - assertIsFalse "12javaTpoint#domain.com"                                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   * 
   * ---- https://java2blog.com/validate-email-address-in-java/ -----------------------------------------------------------------------
   * 
   *  3944 - assertIsTrue  "admin@java2blog.com"                                                            =   0 =  OK 
   *  3945 - assertIsFalse "@java2blog.com"                                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3946 - assertIsTrue  "arpit.mandliya@java2blog.com"                                                   =   0 =  OK 
   * 
   * ---- https://www.tutorialspoint.com/javaexamples/regular_email.htm ---------------------------------------------------------------
   * 
   *  3947 - assertIsTrue  "sairamkrishna@tutorialspoint.com"                                               =   0 =  OK 
   *  3948 - assertIsTrue  "kittuprasad700@gmail.com"                                                       =   0 =  OK 
   *  3949 - assertIsFalse "sairamkrishna_mammahe%google-india.com"                                         =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3950 - assertIsTrue  "sairam.krishna@gmail-indai.com"                                                 =   0 =  OK 
   *  3951 - assertIsTrue  "sai#@youtube.co.in"                                                             =   0 =  OK 
   *  3952 - assertIsFalse "kittu@domaincom"                                                                =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3953 - assertIsFalse "kittu#gmail.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3954 - assertIsFalse "@pindom.com"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   * 
   * ---- https://github.com/bbottema/email-rfc2822-validator/blob/master/src/test/java/demo/TestClass.java ---------------------------
   * 
   *  3955 - assertIsTrue  "me@example.com"                                                                 =   0 =  OK 
   *  3956 - assertIsTrue  "a.nonymous@example.com"                                                         =   0 =  OK 
   *  3957 - assertIsTrue  "name+tag@example.com"                                                           =   0 =  OK 
   *  3958 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[1.0.0.127]"                                                 =   2 =  OK 
   *  3959 - assertIsTrue  "!#$%&'+-/=.?^`{|}~@[IPv6:0123:4567:89AB:CDEF:0123:4567:89AB:CDEF]"              =   4 =  OK 
   *  3960 - assertIsTrue  "me(this is a comment)@example.com"                                              =   6 =  OK 
   *  3961 - assertIsTrue  "\"bob(hi)smith\"@test.com"                                                      =   1 =  OK 
   *  3962 - assertIsTrue  "\"Bob\" <bob@hi.com>"                                                           =   0 =  OK 
   *  3963 - assertIsTrue  "me.example@com"                                                                 =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3964 - assertIsTrue  "309d4696df38ff12c023600e3bc2bd4b@fakedomain.com"                                =   0 =  OK 
   *  3965 - assertIsTrue  "ewiuhdghiufduhdvjhbajbkerwukhgjhvxbhvbsejskuadukfhgskjebf@gmail.net"            =   0 =  OK 
   *  3966 - assertIsTrue  "iitakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                               =   0 =  OK 
   *  3967 - assertIsTrue  "i-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                              =   0 =  OK 
   *  3968 - assertIsTrue  "ki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                             =   0 =  OK 
   *  3969 - assertIsTrue  "hki-itakeskus-kauppakeskus-rav.paallikko@fi.xyz.dom"                            =   0 =  OK 
   *  3970 - assertIsTrue  "rekry@fi.xyz.dom"                                                               =   0 =  OK 
   *  3971 - assertIsFalse "NotAnEmail"                                                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  3972 - assertIsFalse "me@"                                                                            =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
   *  3973 - assertIsFalse "@example.com"                                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  3974 - assertIsFalse ".me@example.com"                                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  3975 - assertIsFalse "me@example..com"                                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  3976 - assertIsFalse "me\@example.com"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  3977 - assertIsFalse "\"ßoµ\" <notifications@example.com>"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3978 - assertIsFalse "[Kayaks] <kayaks@kayaks.org>"                                                   =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3979 - assertIsFalse "Kayaks.org <kayaks@kayaks.org>"                                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  3980 - assertIsFalse "semico...@gmail.com"                                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   * 
   * ---- my old tests ----------------------------------------------------------------------------------------------------------------
   * 
   *  3981 - assertIsTrue  "A.\"B\"@C.DE"                                                                   =   1 =  OK 
   *  3982 - assertIsTrue  "A.B@[1.2.3.4]"                                                                  =   2 =  OK 
   *  3983 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                                              =   3 =  OK 
   *  3984 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                                     =   4 =  OK 
   *  3985 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                 =   5 =  OK 
   *  3986 - assertIsTrue  "(A)B@C.DE"                                                                      =   6 =  OK 
   *  3987 - assertIsTrue  "A(B)@C.DE"                                                                      =   6 =  OK 
   *  3988 - assertIsTrue  "(A)\"B\"@C.DE"                                                                  =   7 =  OK 
   *  3989 - assertIsTrue  "\"A\"(B)@C.DE"                                                                  =   7 =  OK 
   *  3990 - assertIsTrue  "(A)B@[1.2.3.4]"                                                                 =   2 =  OK 
   *  3991 - assertIsTrue  "A(B)@[1.2.3.4]"                                                                 =   2 =  OK 
   *  3992 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                                             =   8 =  OK 
   *  3993 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                                             =   8 =  OK 
   *  3994 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                                    =   4 =  OK 
   *  3995 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                    =   4 =  OK 
   *  3996 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                                                =   9 =  OK 
   *  3997 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                                                =   9 =  OK 
   *  3998 - assertIsTrue  "a.b.c.d@domain.com"                                                             =   0 =  OK 
   *  3999 - assertIsFalse "ABCDEFGHIJKLMNOP"                                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4000 - assertIsFalse "ABC.DEF.GHI.JKL"                                                                =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
   *  4001 - assertIsFalse "ABC.DEF@ GHI.JKL"                                                               = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  4002 - assertIsFalse "ABC.DEF @GHI.JKL"                                                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4003 - assertIsFalse "ABC.DEF @ GHI.JKL"                                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4004 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                                            =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  4005 - assertIsFalse "ABC.DEF@"                                                                       =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
   *  4006 - assertIsFalse "ABC.DEF@@GHI.JKL"                                                               =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4007 - assertIsFalse "ABC@DEF@GHI.JKL"                                                                =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4008 - assertIsFalse "@%^%#$@#$@#.com"                                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4009 - assertIsFalse "first@last@test.org"                                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4010 - assertIsFalse "@test@a.com"                                                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4011 - assertIsFalse "@\"someStringThatMightBe@email.com"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   *  4012 - assertIsFalse "test@@test.com"                                                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
   *  4013 - assertIsFalse "ABCDEF@GHIJKL"                                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4014 - assertIsFalse "ABC.DEF@GHIJKL"                                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4015 - assertIsFalse ".ABC.DEF@GHI.JKL"                                                               =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
   *  4016 - assertIsFalse "ABC.DEF@GHI.JKL."                                                               =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
   *  4017 - assertIsFalse "ABC..DEF@GHI.JKL"                                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  4018 - assertIsFalse "ABC.DEF@GHI..JKL"                                                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  4019 - assertIsFalse "ABC.DEF@GHI.JKL.."                                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
   *  4020 - assertIsFalse "ABC.DEF.@GHI.JKL"                                                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
   *  4021 - assertIsFalse "ABC.DEF@.GHI.JKL"                                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  4022 - assertIsFalse "ABC.DEF@."                                                                      =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
   *  4023 - assertIsTrue  "\"ABC..DEF\"@GHI.JKL"                                                           =   1 =  OK 
   *  4024 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                                            =   0 =  OK 
   *  4025 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                                               =   0 =  OK 
   *  4026 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                                               =   0 =  OK 
   *  4027 - assertIsTrue  "ABC.DEF@GHI.JK2"                                                                =   0 =  OK 
   *  4028 - assertIsTrue  "ABC.DEF@2HI.JKL"                                                                =   0 =  OK 
   *  4029 - assertIsFalse "ABC.DEF@GHI.2KL"                                                                =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
   *  4030 - assertIsFalse "ABC.DEF@GHI.JK-"                                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  4031 - assertIsFalse "ABC.DEF@GHI.JK_"                                                                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
   *  4032 - assertIsFalse "ABC.DEF@-HI.JKL"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  4033 - assertIsFalse "ABC.DEF@_HI.JKL"                                                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
   *  4034 - assertIsFalse "ABC DEF@GHI.DE"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4035 - assertIsFalse "ABC.DEF@GHI DE"                                                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
   *  4036 - assertIsFalse "A . B & C . D"                                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4037 - assertIsFalse " A . B & C . D"                                                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4038 - assertIsFalse "(?).[!]@{&}.<:>"                                                                =  18 =  OK    Struktur: Fehler in Adress-String-X
   *  4039 - assertIsFalse "   my.correct.email.adress.com@but.we.dont.trim.the.input.so.the.emailadress.is.false.de   " =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4040 - assertIsTrue  "my.correct.email.adress.com@but.without.spaces.so.the.emailadress.is.correct.de" =   0 =  OK 
   * 
   * ---- unsupported -----------------------------------------------------------------------------------------------------------------
   * 
   *  4041 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                                         =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4042 - assertIsTrue  "Ärger.Öde@Übel.de"                                                              =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4043 - assertIsTrue  "Smørrebrød@danmark.dk"                                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4044 - assertIsTrue  "ip6.without.brackets@1:2:3:4:5:6:7:8"                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
   *  4045 - assertIsTrue  "email.address.without@topleveldomain"                                           =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4046 - assertIsTrue  "EmailAddressWithout@PointSeperator"                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
   *  4047 - assertIsFalse "@1st.relay,@2nd.relay:user@final.domain"                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
   * 
   * ---- Fillup ----------------------------------------------------------------------------------------------------------------------
   * 
   * Fillup ist nicht aktiv
   * 
   * 
   * ---- Statistik -------------------------------------------------------------------------------------------------------------------
   * 
   *   ASSERT_IS_TRUE  1387   KORREKT 1301 =   93.800 % | FALSCH ERKANNT   86 =    6.200 % = Error 0
   *   ASSERT_IS_FALSE 2660   KORREKT 2642 =   99.323 % | FALSCH ERKANNT   18 =    0.677 % = Error 0
   * 
   *   GESAMT          4047   KORREKT 3943 =   97.430 % | FALSCH ERKANNT  104 =    2.570 % = Error 0
   * 
   * 
   *   Millisekunden    124 = 0.030639980232270818
   * 
   *   Datum 31.10.2023 09:23:59:654
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
    assertIsTrue( "\"Vorname+Nachname.\"@test.de" );
    assertIsTrue( "\" 123,45 * 678,910 = 83.811,4395 \"@calc-test.de" );
    assertIsTrue( "\" 50.000,00 / 106,00 = 471.69811320754716981132075471698113 \"@calc-test.de" );
    assertIsTrue( "\" 2 + 2 = 3,999999999999999999999999991 \"@erste-hochrechnung.de" );
    assertIsTrue( "\".John.Doe\"@example.com" );
    assertIsTrue( "\"John.Doe.\"@example.com" );
    assertIsTrue( "\"John..Doe\"@example.com" );
    assertIsTrue( "john.smith(comment)@example.com" );
    assertIsTrue( "(comment)john.smith@example.com" );
    assertIsTrue( "john.smith@(comment)example.com" );
    assertIsTrue( "john.smith@example.com(comment)" );
    assertIsTrue( "john.smith@example.com" );
    assertIsTrue( "joeuser+tag@example.com" );
    assertIsTrue( "jsmith@[192.168.2.1]" );
    assertIsTrue( "jsmith@[IPv6:2001:db8::1]" );
    assertIsFalse( "john.smith@exampl(comment)e.com" );
    assertIsFalse( "john.s(comment)mith@example.com" );
    assertIsFalse( "john.smith(comment)@(comment)example.com" );
    assertIsFalse( "john.smith(com@ment)example.com" );
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
    assertIsFalse( "\"Doug \"Ace\" L.\"@example.com" );
    assertIsFalse( "\"Joe Q. Public\" <john.q.public@example.com>" );
    assertIsFalse( "\"Joe\\Blow\"@example.com" );
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
    assertIsFalse( "email@plus.com (not closed comment" );
    assertIsTrue( "email@domain.com (joe Smith)" );
    assertIsTrue( "email@domain.domain.domain.com.com" );
    assertIsTrue( "email@domain.domain.domain.domain.com.com" );
    assertIsTrue( "email@domain.domain.domain.domain.domain.com.com" );
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
    assertIsFalse( "email@domain.com-" );
    assertIsFalse( "email@domain.com." );
    assertIsFalse( "email@domain.com>" );
    assertIsFalse( "email@domain@domain.com" );
    assertIsFalse( "email@example" );
    assertIsFalse( "email@example..com" );
    assertIsFalse( "email@example.co.uk." );
    assertIsFalse( "email@example.com " );
    assertIsFalse( "email@exclamation!mark.com" );
    assertIsFalse( "email@grave`accent.com" );
    assertIsFalse( "email@mailto:domain.com" );
    assertIsFalse( "email@obelix*asterisk.com" );
    assertIsFalse( "email@plus+.com" );
    assertIsFalse( "email@plus.com (not closed comment" );
    assertIsFalse( "email@pp|e.com" );
    assertIsFalse( "email@p|pe.com" );
    assertIsFalse( "email@question?mark.com" );
    assertIsFalse( "email@r&amp;d.com" );
    assertIsFalse( "email@rightbracket}.com" );
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
    assertIsTrue( "<john@doe.com>" );
    assertIsTrue( "A__z/J0hn.sm{it!}h_comment@example.com.co" );
    assertIsTrue( "Abc.123@example.com" );
    assertIsTrue( "Abc@10.42.0.1" );
    assertIsTrue( "Abc@example.com" );
    assertIsTrue( "D.Oy'Smith@gmail.com" );
    assertIsTrue( "Fred\\ Bloggs@example.com" );
    assertIsTrue( "Joe.\\\\Blow@example.com" );
    assertIsTrue( "John <john@doe.com>" );
    assertIsTrue( "mymail\\@hello@hotmail.com" );
    assertIsTrue( "PN=Joe/OU=X400/@gateway.com" );
    assertIsTrue( "This is <john@127.0.0.1>" );
    assertIsTrue( "This is <john@[127.0.0.1]>" );
    assertIsTrue( "Who? <one@y.test>" );
    assertIsTrue( "\" \"@example.org" );
    assertIsTrue( "\"%2\"@gmail.com" );
    assertIsTrue( "\"Abc@def\"@example.com" );
    assertIsTrue( "\"Abc\\@def\"@example.com" );
    assertIsTrue( "\"Doug \\\"Ace\\\" L.\"@example.com" );
    assertIsTrue( "\"Fred Bloggs\"@example.com" );
    assertIsTrue( "\"Fred\\ Bloggs\"@example.com" );
    assertIsTrue( "\"Giant; \\\"Big\\\" Box\" <sysservices@example.net>" );
    assertIsTrue( "\"Joe\\\\Blow\"@example.com" );
    assertIsTrue( "\"Look at all these spaces!\"@example.com" );
    assertIsTrue( "\"a..b\"@gmail.com" );
    assertIsTrue( "\"a@b\"@example.com" );
    assertIsTrue( "\"a_b\"@gmail.com" );
    assertIsTrue( "\"abcdefghixyz\"@example.com" );
    assertIsTrue( "\"cogwheel the orange\"@example.com" );
    assertIsTrue( "\"foo\\@bar\"@Something.com" );
    assertIsTrue( "\"j\\\"s\"@proseware.com" );
    assertIsTrue( "\"myemail@sa\"@mple.com" );
    assertIsTrue( "_-_@bde.cc" );
    assertIsTrue( "_@gmail.com" );
    assertIsTrue( "_dasd@sd.com" );
    assertIsTrue( "_dasd_das_@9.com" );
    assertIsTrue( "_somename@example.com" );
    assertIsTrue( "a&d@somedomain.com" );
    assertIsTrue( "a*d@somedomain.com" );
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
    assertIsTrue( "email@domain.co.de" );
    assertIsTrue( "email@domain.com" );
    assertIsTrue( "email@example.co.uk" );
    assertIsTrue( "email@example.com" );
    assertIsTrue( "email@mail.gmail.com" );
    assertIsTrue( "email@subdomain.domain.com" );
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
    assertIsTrue( "john.doe@example.com" );
    assertIsTrue( "john.o'doe@example.com" );
    assertIsTrue( "jones@ms1.proseware.com" );
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

    assertIsFalse( "no.email.adress\"with.string.start" );
    assertIsFalse( "string.starts.before.at.sign\"@domain.com" );
    assertIsFalse( "string.starts.before.at.sign\"but.with.caracters.before.at.sign@domain.com" );
    assertIsFalse( "ABC.DEF.\"@GHI.DE" );

    assertIsFalse( "\"\".email.starts.with.empty.string@domain.com" );
    assertIsTrue( "\"string.with.double..point\"@domain.com" );
    assertIsTrue( "string.with.\"(comment)\".in.string@domain.com" );
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
    assertIsTrue( "\"special characters in string - % & * + - / = ? _\"@domain.tld" );
    assertIsTrue( "\"string1\\\"embedded string\\\"\"@domain.tld" );
    assertIsTrue( "\"string1(embedded comment)\"@domain.tld" );

    assertIsTrue( "(comment1)name1@domain.tld" );
    assertIsTrue( "(comment1)-name1@domain.tld" );
    assertIsTrue( "name1(comment1)@domain.tld" );
    assertIsTrue( "name1@(comment1)domain.tld" );
    assertIsTrue( "name1@domain.tld(comment1)" );
    assertIsTrue( "(spaces after comment)     name1.name2@domain.tld" );
    assertIsTrue( "name1.name2@domain.tld   (spaces before comment)" );

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
