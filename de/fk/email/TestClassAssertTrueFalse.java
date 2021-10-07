package de.fk.email;

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

  private static int          TEST_B_TEST_NR                = 8;

  private static boolean      TEST_B_KNZ_AKTIV              = false;

  private static StringBuffer m_str_buffer                  = null;

  /*
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
   */

  public static void main( String[] args )
  {
    /*
     *  NR     Fkt.          Input                                                        Result
     * ---- General Correct ----------------------------------------------------------------------------------------------------
     * 
     *     1 - assertIsTrue  "A.B@C.DE"                                                   =   0 =  OK 
     *     2 - assertIsTrue  "A.\"B\"@C.DE"                                               =   1 =  OK 
     *     3 - assertIsTrue  "A.B@[1.2.3.4]"                                              =   2 =  OK 
     *     4 - assertIsTrue  "A.\"B\"@[1.2.3.4]"                                          =   3 =  OK 
     *     5 - assertIsTrue  "A.B@[IPv6:1:2:3:4:5:6:7:8]"                                 =   4 =  OK 
     *     6 - assertIsTrue  "A.\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                             =   5 =  OK 
     *     7 - assertIsTrue  "(A)B@C.DE"                                                  =   6 =  OK 
     *     8 - assertIsTrue  "A(B)@C.DE"                                                  =   6 =  OK 
     *     9 - assertIsTrue  "(A)\"B\"@C.DE"                                              =   7 =  OK 
     *    10 - assertIsTrue  "\"A\"(B)@C.DE"                                              =   7 =  OK 
     *    11 - assertIsTrue  "(A)B@[1.2.3.4]"                                             =   2 =  OK 
     *    12 - assertIsTrue  "A(B)@[1.2.3.4]"                                             =   2 =  OK 
     *    13 - assertIsTrue  "(A)\"B\"@[1.2.3.4]"                                         =   8 =  OK 
     *    14 - assertIsTrue  "\"A\"(B)@[1.2.3.4]"                                         =   8 =  OK 
     *    15 - assertIsTrue  "(A)B@[IPv6:1:2:3:4:5:6:7:8]"                                =   4 =  OK 
     *    16 - assertIsTrue  "A(B)@[IPv6:1:2:3:4:5:6:7:8]"                                =   4 =  OK 
     *    17 - assertIsTrue  "(A)\"B\"@[IPv6:1:2:3:4:5:6:7:8]"                            =   9 =  OK 
     *    18 - assertIsTrue  "\"A\"(B)@[IPv6:1:2:3:4:5:6:7:8]"                            =   9 =  OK 
     *    19 - assertIsTrue  "firstname.lastname@domain.com"                              =   0 =  OK 
     *    20 - assertIsTrue  "firstname+lastname@domain.com"                              =   0 =  OK 
     *    21 - assertIsTrue  "firstname-lastname@domain.com"                              =   0 =  OK 
     *    22 - assertIsTrue  "first-name-last-name@d-a-n.com"                             =   0 =  OK 
     *    23 - assertIsTrue  "a.b.c.d@domain.com"                                         =   0 =  OK 
     *    24 - assertIsTrue  "1@domain.com"                                               =   0 =  OK 
     *    25 - assertIsTrue  "a@domain.com"                                               =   0 =  OK 
     *    26 - assertIsTrue  "email@domain.co.de"                                         =   0 =  OK 
     *    27 - assertIsTrue  "email@domain.com"                                           =   0 =  OK 
     *    28 - assertIsTrue  "email@subdomain.domain.com"                                 =   0 =  OK 
     *    29 - assertIsTrue  "2@bde.cc"                                                   =   0 =  OK 
     *    30 - assertIsTrue  "-@bde.cc"                                                   =   0 =  OK 
     *    31 - assertIsTrue  "a2@bde.cc"                                                  =   0 =  OK 
     *    32 - assertIsTrue  "a-b@bde.cc"                                                 =   0 =  OK 
     *    33 - assertIsTrue  "ab@b-de.cc"                                                 =   0 =  OK 
     *    34 - assertIsTrue  "a+b@bde.cc"                                                 =   0 =  OK 
     *    35 - assertIsTrue  "f.f.f@bde.cc"                                               =   0 =  OK 
     *    36 - assertIsTrue  "ab_c@bde.cc"                                                =   0 =  OK 
     *    37 - assertIsTrue  "_-_@bde.cc"                                                 =   0 =  OK 
     *    38 - assertIsTrue  "w.b.f@test.com"                                             =   0 =  OK 
     *    39 - assertIsTrue  "w.b.f@test.museum"                                          =   0 =  OK 
     *    40 - assertIsTrue  "a.a@test.com"                                               =   0 =  OK 
     *    41 - assertIsTrue  "ab@288.120.150.10.com"                                      =   0 =  OK 
     *    42 - assertIsTrue  "ab@[120.254.254.120]"                                       =   2 =  OK 
     *    43 - assertIsTrue  "1234567890@domain.com"                                      =   0 =  OK 
     *    44 - assertIsTrue  "john.smith@example.com"                                     =   0 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    45 - assertIsFalse null                                                         =  10 =  OK    Laenge: Eingabe ist null
     *    46 - assertIsFalse ""                                                           =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    47 - assertIsFalse "        "                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    48 - assertIsFalse "ABCDEFGHIJKLMNOP"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    49 - assertIsFalse "ABC.DEF.GHI.JKL"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    50 - assertIsFalse "ABC.DEF@ GHI.JKL"                                           = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *    51 - assertIsFalse "ABC.DEF @GHI.JKL"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    52 - assertIsFalse "ABC.DEF @ GHI.JKL"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    53 - assertIsFalse "@"                                                          =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    54 - assertIsFalse "@.@.@."                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    55 - assertIsFalse "@.@.@GHI.JKL"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    56 - assertIsFalse "ABC.DEF@.@.@GHI.JKL"                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    57 - assertIsFalse "@@@GHI.JKL"                                                 =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    58 - assertIsFalse "@GHI.JKL"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    59 - assertIsFalse "ABC.DEF@"                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    60 - assertIsFalse "ABC.DEF@@GHI.JKL"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    61 - assertIsFalse "ABC@DEF@GHI.JKL"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    62 - assertIsFalse "@%^%#$@#$@#.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    63 - assertIsFalse "@domain.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    64 - assertIsFalse "email.domain.com"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    65 - assertIsFalse "email@domain@domain.com"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    66 - assertIsFalse "@@@@@@gmail.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    67 - assertIsFalse "first@last@test.org"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    68 - assertIsFalse "@test@a.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    69 - assertIsFalse "@\"someStringThatMightBe@email.com"                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    70 - assertIsFalse "test@@test.com"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    71 - assertIsFalse "ABCDEF@GHIJKL"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    72 - assertIsFalse "ABC.DEF@GHIJKL"                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    73 - assertIsFalse ".ABC.DEF@GHI.JKL"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    74 - assertIsFalse "ABC.DEF@GHI.JKL."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    75 - assertIsFalse "ABC..DEF@GHI.JKL"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    76 - assertIsFalse "ABC.DEF@GHI..JKL"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    77 - assertIsFalse "ABC.DEF@GHI.JKL.."                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    78 - assertIsFalse "ABC.DEF.@GHI.JKL"                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    79 - assertIsFalse "ABC.DEF@.GHI.JKL"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    80 - assertIsFalse "ABC.DEF@."                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    81 - assertIsFalse "john..doe@example.com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    82 - assertIsFalse "john.doe@example..com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    83 - assertIsTrue  "\"john..doe\"@example.com"                                  =   1 =  OK 
     *    84 - assertIsFalse "..........@domain."                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    85 - assertIsFalse "test.@test.com"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    86 - assertIsFalse ".test.@test.com"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    87 - assertIsFalse "asdf@asdf@asdf.com"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    88 - assertIsFalse "email@provider..com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    89 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                        =   0 =  OK 
     *    90 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                           =   0 =  OK 
     *    91 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                           =   0 =  OK 
     *    92 - assertIsTrue  "ABC.DEF@GHI.JK2"                                            =   0 =  OK 
     *    93 - assertIsTrue  "ABC.DEF@2HI.JKL"                                            =   0 =  OK 
     *    94 - assertIsFalse "ABC.DEF@GHI.2KL"                                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    95 - assertIsFalse "ABC.DEF@GHI.JK-"                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    96 - assertIsFalse "ABC.DEF@GHI.JK_"                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    97 - assertIsFalse "ABC.DEF@-HI.JKL"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    98 - assertIsFalse "ABC.DEF@_HI.JKL"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    99 - assertIsFalse "ABC DEF@GHI.DE"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   100 - assertIsFalse "ABC.DEF@GHI DE"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   101 - assertIsFalse "A . B & C . D"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   102 - assertIsFalse " A . B & C . D"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   103 - assertIsFalse "(?).[!]@{&}.<:>"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   104 - assertIsTrue  "&local&&name&with&$@amp.com"                                =   0 =  OK 
     *   105 - assertIsTrue  "*local**name*with*@asterisk.com"                            =   0 =  OK 
     *   106 - assertIsTrue  "$local$$name$with$@dollar.com"                              =   0 =  OK 
     *   107 - assertIsTrue  "=local==name=with=@equality.com"                            =   0 =  OK 
     *   108 - assertIsTrue  "!local!!name!with!@exclamation.com"                         =   0 =  OK 
     *   109 - assertIsTrue  "`local``name`with`@grave-accent.com"                        =   0 =  OK 
     *   110 - assertIsTrue  "#local##name#with#@hash.com"                                =   0 =  OK 
     *   111 - assertIsTrue  "-local--name-with-@hypen.com"                               =   0 =  OK 
     *   112 - assertIsTrue  "{local{name{{with{@leftbracket.com"                         =   0 =  OK 
     *   113 - assertIsTrue  "%local%%name%with%@percentage.com"                          =   0 =  OK 
     *   114 - assertIsTrue  "|local||name|with|@pipe.com"                                =   0 =  OK 
     *   115 - assertIsTrue  "+local++name+with+@plus.com"                                =   0 =  OK 
     *   116 - assertIsTrue  "?local??name?with?@question.com"                            =   0 =  OK 
     *   117 - assertIsTrue  "}local}name}}with}@rightbracket.com"                        =   0 =  OK 
     *   118 - assertIsTrue  "~local~~name~with~@tilde.com"                               =   0 =  OK 
     *   119 - assertIsTrue  "^local^^name^with^@xor.com"                                 =   0 =  OK 
     *   120 - assertIsTrue  "_local__name_with_@underscore.com"                          =   0 =  OK 
     *   121 - assertIsFalse ":local::name:with:@colon.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   122 - assertIsTrue  "domain.part@with-hyphen.com"                                =   0 =  OK 
     *   123 - assertIsFalse "domain.part@-with.hyphen.at.domain.start.com"               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   124 - assertIsFalse "domain.part@with.hyphen.at.domain.end1-.com"                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   125 - assertIsFalse "domain.part@with.hyphen.at.domain.end2.com-"                =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   126 - assertIsFalse "domain.part@with.hyphen.before-.point.com"                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   127 - assertIsFalse "domain.part@with.-hyphen.after.point.com"                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   128 - assertIsTrue  "domain.part@with_underscore.com"                            =   0 =  OK 
     *   129 - assertIsFalse "domain.part@_with.underscore.at.domain.start.com"           =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   130 - assertIsFalse "domain.part@with.underscore.at.domain.end1_.com"            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   131 - assertIsFalse "domain.part@with.underscore.at.domain.end2.com_"            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   132 - assertIsFalse "domain.part@with.underscore.before_.point.com"              =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   133 - assertIsFalse "domain.part@with.underscore.after._point.com"               =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   134 - assertIsFalse "domain.part@with&amp.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   135 - assertIsFalse "domain.part@&with.amp.at.domain.start.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   136 - assertIsFalse "domain.part@with.amp.at.domain.end1&.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   137 - assertIsFalse "domain.part@with.amp.at.domain.end2.com&"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   138 - assertIsFalse "domain.part@with.amp.before&.point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   139 - assertIsFalse "domain.part@with.amp.after.&point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   140 - assertIsFalse "domain.part@with*asterisk.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   141 - assertIsFalse "domain.part@*with.asterisk.at.domain.start.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   142 - assertIsFalse "domain.part@with.asterisk.at.domain.end1*.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   143 - assertIsFalse "domain.part@with.asterisk.at.domain.end2.com*"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   144 - assertIsFalse "domain.part@with.asterisk.before*.point.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   145 - assertIsFalse "domain.part@with.asterisk.after.*point.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   146 - assertIsFalse "domain.part@with$dollar.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   147 - assertIsFalse "domain.part@$with.dollar.at.domain.start.com"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   148 - assertIsFalse "domain.part@with.dollar.at.domain.end1$.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   149 - assertIsFalse "domain.part@with.dollar.at.domain.end2.com$"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   150 - assertIsFalse "domain.part@with.dollar.before$.point.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   151 - assertIsFalse "domain.part@with.dollar.after.$point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   152 - assertIsFalse "domain.part@with=equality.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   153 - assertIsFalse "domain.part@=with.equality.at.domain.start.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   154 - assertIsFalse "domain.part@with.equality.at.domain.end1=.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   155 - assertIsFalse "domain.part@with.equality.at.domain.end2.com="              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   156 - assertIsFalse "domain.part@with.equality.before=.point.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   157 - assertIsFalse "domain.part@with.equality.after.=point.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   158 - assertIsFalse "domain.part@with!exclamation.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   159 - assertIsFalse "domain.part@!with.exclamation.at.domain.start.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   160 - assertIsFalse "domain.part@with.exclamation.at.domain.end1!.com"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   161 - assertIsFalse "domain.part@with.exclamation.at.domain.end2.com!"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   162 - assertIsFalse "domain.part@with.exclamation.before!.point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   163 - assertIsFalse "domain.part@with.exclamation.after.!point.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   164 - assertIsFalse "domain.part@with?question.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   165 - assertIsFalse "domain.part@?with.question.at.domain.start.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   166 - assertIsFalse "domain.part@with.question.at.domain.end1?.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   167 - assertIsFalse "domain.part@with.question.at.domain.end2.com?"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   168 - assertIsFalse "domain.part@with.question.before?.point.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   169 - assertIsFalse "domain.part@with.question.after.?point.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   170 - assertIsFalse "domain.part@with`grave-accent.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   171 - assertIsFalse "domain.part@`with.grave-accent.at.domain.start.com"         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   172 - assertIsFalse "domain.part@with.grave-accent.at.domain.end1`.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   173 - assertIsFalse "domain.part@with.grave-accent.at.domain.end2.com`"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   174 - assertIsFalse "domain.part@with.grave-accent.before`.point.com"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   175 - assertIsFalse "domain.part@with.grave-accent.after.`point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   176 - assertIsFalse "domain.part@with#hash.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   177 - assertIsFalse "domain.part@#with.hash.at.domain.start.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   178 - assertIsFalse "domain.part@with.hash.at.domain.end1#.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   179 - assertIsFalse "domain.part@with.hash.at.domain.end2.com#"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   180 - assertIsFalse "domain.part@with.hash.before#.point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   181 - assertIsFalse "domain.part@with.hash.after.#point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   182 - assertIsFalse "domain.part@with%percentage.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   183 - assertIsFalse "domain.part@%with.percentage.at.domain.start.com"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   184 - assertIsFalse "domain.part@with.percentage.at.domain.end1%.com"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   185 - assertIsFalse "domain.part@with.percentage.at.domain.end2.com%"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   186 - assertIsFalse "domain.part@with.percentage.before%.point.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   187 - assertIsFalse "domain.part@with.percentage.after.%point.com"               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   188 - assertIsFalse "domain.part@with|pipe.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   189 - assertIsFalse "domain.part@|with.pipe.at.domain.start.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   190 - assertIsFalse "domain.part@with.pipe.at.domain.end1|.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   191 - assertIsFalse "domain.part@with.pipe.at.domain.end2.com|"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   192 - assertIsFalse "domain.part@with.pipe.before|.point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   193 - assertIsFalse "domain.part@with.pipe.after.|point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   194 - assertIsFalse "domain.part@with+plus.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   195 - assertIsFalse "domain.part@+with.plus.at.domain.start.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   196 - assertIsFalse "domain.part@with.plus.at.domain.end1+.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   197 - assertIsFalse "domain.part@with.plus.at.domain.end2.com+"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   198 - assertIsFalse "domain.part@with.plus.before+.point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   199 - assertIsFalse "domain.part@with.plus.after.+point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   200 - assertIsFalse "domain.part@with{leftbracket.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   201 - assertIsFalse "domain.part@{with.leftbracket.at.domain.start.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   202 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1{.com"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   203 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com{"           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   204 - assertIsFalse "domain.part@with.leftbracket.before{.point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   205 - assertIsFalse "domain.part@with.leftbracket.after.{point.com"              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   206 - assertIsFalse "domain.part@with}rightbracket.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   207 - assertIsFalse "domain.part@}with.rightbracket.at.domain.start.com"         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   208 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1}.com"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   209 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com}"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   210 - assertIsFalse "domain.part@with.rightbracket.before}.point.com"            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   211 - assertIsFalse "domain.part@with.rightbracket.after.}point.com"             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   212 - assertIsFalse "domain.part@with(leftbracket.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   213 - assertIsFalse "domain.part@(with.leftbracket.at.domain.start.com"          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   214 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1(.com"           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   215 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com("           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   216 - assertIsFalse "domain.part@with.leftbracket.before(.point.com"             =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   217 - assertIsFalse "domain.part@with.leftbracket.after.(point.com"              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   218 - assertIsFalse "domain.part@with)rightbracket.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   219 - assertIsFalse "domain.part@)with.rightbracket.at.domain.start.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   220 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1).com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   221 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com)"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   222 - assertIsFalse "domain.part@with.rightbracket.before).point.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   223 - assertIsFalse "domain.part@with.rightbracket.after.)point.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   224 - assertIsFalse "domain.part@with[leftbracket.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   225 - assertIsFalse "domain.part@[with.leftbracket.at.domain.start.com"          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   226 - assertIsFalse "domain.part@with.leftbracket.at.domain.end1[.com"           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   227 - assertIsFalse "domain.part@with.leftbracket.at.domain.end2.com["           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   228 - assertIsFalse "domain.part@with.leftbracket.before[.point.com"             =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   229 - assertIsFalse "domain.part@with.leftbracket.after.[point.com"              =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   230 - assertIsFalse "domain.part@with]rightbracket.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   231 - assertIsFalse "domain.part@]with.rightbracket.at.domain.start.com"         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   232 - assertIsFalse "domain.part@with.rightbracket.at.domain.end1].com"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   233 - assertIsFalse "domain.part@with.rightbracket.at.domain.end2.com]"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   234 - assertIsFalse "domain.part@with.rightbracket.before].point.com"            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   235 - assertIsFalse "domain.part@with.rightbracket.after.]point.com"             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   236 - assertIsFalse "domain.part@with~tilde.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   237 - assertIsFalse "domain.part@~with.tilde.at.domain.start.com"                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   238 - assertIsFalse "domain.part@with.tilde.at.domain.end1~.com"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   239 - assertIsFalse "domain.part@with.tilde.at.domain.end2.com~"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   240 - assertIsFalse "domain.part@with.tilde.before~.point.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   241 - assertIsFalse "domain.part@with.tilde.after.~point.com"                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   242 - assertIsFalse "domain.part@with^xor.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   243 - assertIsFalse "domain.part@^with.xor.at.domain.start.com"                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   244 - assertIsFalse "domain.part@with.xor.at.domain.end1^.com"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   245 - assertIsFalse "domain.part@with.xor.at.domain.end2.com^"                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   246 - assertIsFalse "domain.part@with.xor.before^.point.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   247 - assertIsFalse "domain.part@with.xor.after.^point.com"                      =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   248 - assertIsFalse "domain.part@with:colon.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   249 - assertIsFalse "domain.part@:with.colon.at.domain.start.com"                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   250 - assertIsFalse "domain.part@with.colon.at.domain.end1:.com"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   251 - assertIsFalse "domain.part@with.colon.at.domain.end2.com:"                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   252 - assertIsFalse "domain.part@with.colon.before:.point.com"                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   253 - assertIsFalse "domain.part@with.colon.after.:point.com"                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   254 - assertIsFalse "domain.part@with space.com"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   255 - assertIsFalse "domain.part@ with.space.at.domain.start.com"                = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   256 - assertIsFalse "domain.part@with.space.at.domain.end1 .com"                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   257 - assertIsFalse "domain.part@with.space.at.domain.end2.com "                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   258 - assertIsFalse "domain.part@with.space.before .point.com"                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   259 - assertIsFalse "domain.part@with.space.after. point.com"                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   260 - assertIsFalse "DomainHyphen@-atstart"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   261 - assertIsFalse "DomainHyphen@atend-.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   262 - assertIsFalse "DomainHyphen@bb.-cc"                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   263 - assertIsFalse "DomainHyphen@bb.-cc-"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   264 - assertIsFalse "DomainHyphen@bb.cc-"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   265 - assertIsFalse "DomainHyphen@bb.c-c"                                        =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   266 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   267 - assertIsFalse "DomainNotAllowedCharacter@a.start"                          =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   268 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   269 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   270 - assertIsFalse "DomainNotAllowedCharacter@example'"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   271 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   272 - assertIsTrue  "domain.starts.with.digit@2domain.com"                       =   0 =  OK 
     *   273 - assertIsTrue  "domain.ends.with.digit@domain2.com"                         =   0 =  OK 
     *   274 - assertIsFalse "tld.starts.with.digit@domain.2com"                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   275 - assertIsTrue  "tld.ends.with.digit@domain.com2"                            =   0 =  OK 
     *   276 - assertIsFalse "email@=qowaiv.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   277 - assertIsFalse "email@plus+.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   278 - assertIsFalse "email@domain.com>"                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   279 - assertIsFalse "email@mailto:domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   280 - assertIsFalse "mailto:mailto:email@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   281 - assertIsFalse "email@-domain.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   282 - assertIsFalse "email@domain-.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   283 - assertIsFalse "email@domain.com-"                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   284 - assertIsFalse "email@{leftbracket.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   285 - assertIsFalse "email@rightbracket}.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   286 - assertIsFalse "email@pp|e.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   287 - assertIsTrue  "email@domain.domain.domain.com.com"                         =   0 =  OK 
     *   288 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                  =   0 =  OK 
     *   289 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"           =   0 =  OK 
     *   290 - assertIsFalse "unescaped white space@fake$com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   291 - assertIsFalse "\"Joe Smith email@domain.com"                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   292 - assertIsFalse "\"Joe Smith' email@domain.com"                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   293 - assertIsFalse "\"Joe Smith\"email@domain.com"                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   294 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   295 - assertIsTrue  "{john'doe}@my.server"                                       =   0 =  OK 
     *   296 - assertIsTrue  "email@domain-one.com"                                       =   0 =  OK 
     *   297 - assertIsTrue  "_______@domain.com"                                         =   0 =  OK 
     *   298 - assertIsTrue  "?????@domain.com"                                           =   0 =  OK 
     *   299 - assertIsFalse "local@?????.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   300 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                              =   1 =  OK 
     *   301 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                     =   1 =  OK 
     *   302 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                  =   0 =  OK 
     *   303 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"        =   1 =  OK 
     *   304 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                           =   0 =  OK 
     *   305 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   306 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2.4}"            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   307 - assertIsFalse "\"\"@[]"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   308 - assertIsFalse "\"\"@[1"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   309 - assertIsFalse "ABC.DEF@[]"                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   310 - assertIsTrue  "\"    \"@[1.2.3.4]"                                         =   3 =  OK 
     *   311 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                  =   2 =  OK 
     *   312 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                    =   3 =  OK 
     *   313 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                          =   2 =  OK 
     *   314 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   315 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   316 - assertIsFalse "ABC.DEF[1.2.3.4]"                                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   317 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   318 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   319 - assertIsFalse "ABC.DEF@[][][][]"                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   320 - assertIsFalse "ABC.DEF@[....]"                                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   321 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   322 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   323 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                    =   3 =  OK 
     *   324 - assertIsFalse "ABC.DEF@MyDomain[1.2.3.4]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   325 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                      =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   326 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   327 - assertIsFalse "ABC.DEF@[..]"                                               =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   328 - assertIsFalse "ABC.DEF@[.2.3.4]"                                           =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   329 - assertIsFalse "ABC.DEF@[1]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   330 - assertIsFalse "ABC.DEF@[1.2]"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   331 - assertIsFalse "ABC.DEF@[1.2.3]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   332 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                        =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   333 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                      =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   334 - assertIsFalse "ABC.DEF@[MyDomain.de]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   335 - assertIsFalse "ABC.DEF@[1.2.3.]"                                           =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   336 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   337 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   338 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   339 - assertIsFalse "ABC.DEF@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   340 - assertIsFalse "ABC.DEF@1.2.3.4]"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   341 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   342 - assertIsFalse "ABC.DEF@[12.34]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   343 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   344 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   345 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   346 - assertIsFalse "ip.v4.with.underscore@[123.14_5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   347 - assertIsFalse "ip.v4.with.underscore@[123.145_.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   348 - assertIsFalse "ip.v4.with.underscore@[123.145._178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   349 - assertIsFalse "ip.v4.with.underscore@[123.145.178.90_]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   350 - assertIsFalse "ip.v4.with.underscore@[_123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   351 - assertIsFalse "ip.v4.with.amp@[123.14&5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   352 - assertIsFalse "ip.v4.with.amp@[123.145&.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   353 - assertIsFalse "ip.v4.with.amp@[123.145.&178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   354 - assertIsFalse "ip.v4.with.amp@[123.145.178.90&]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   355 - assertIsFalse "ip.v4.with.amp@[&123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   356 - assertIsFalse "ip.v4.with.asterisk@[123.14*5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   357 - assertIsFalse "ip.v4.with.asterisk@[123.145*.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   358 - assertIsFalse "ip.v4.with.asterisk@[123.145.*178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   359 - assertIsFalse "ip.v4.with.asterisk@[123.145.178.90*]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   360 - assertIsFalse "ip.v4.with.asterisk@[*123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   361 - assertIsFalse "ip.v4.with.dollar@[123.14$5.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   362 - assertIsFalse "ip.v4.with.dollar@[123.145$.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   363 - assertIsFalse "ip.v4.with.dollar@[123.145.$178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   364 - assertIsFalse "ip.v4.with.dollar@[123.145.178.90$]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   365 - assertIsFalse "ip.v4.with.dollar@[$123.145.178.90]"                        =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   366 - assertIsFalse "ip.v4.with.equality@[123.14=5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   367 - assertIsFalse "ip.v4.with.equality@[123.145=.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   368 - assertIsFalse "ip.v4.with.equality@[123.145.=178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   369 - assertIsFalse "ip.v4.with.equality@[123.145.178.90=]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   370 - assertIsFalse "ip.v4.with.equality@[=123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   371 - assertIsFalse "ip.v4.with.exclamation@[123.14!5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   372 - assertIsFalse "ip.v4.with.exclamation@[123.145!.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   373 - assertIsFalse "ip.v4.with.exclamation@[123.145.!178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   374 - assertIsFalse "ip.v4.with.exclamation@[123.145.178.90!]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   375 - assertIsFalse "ip.v4.with.exclamation@[!123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   376 - assertIsFalse "ip.v4.with.question@[123.14?5.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   377 - assertIsFalse "ip.v4.with.question@[123.145?.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   378 - assertIsFalse "ip.v4.with.question@[123.145.?178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   379 - assertIsFalse "ip.v4.with.question@[123.145.178.90?]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   380 - assertIsFalse "ip.v4.with.question@[?123.145.178.90]"                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   381 - assertIsFalse "ip.v4.with.grave-accent@[123.14`5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   382 - assertIsFalse "ip.v4.with.grave-accent@[123.145`.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   383 - assertIsFalse "ip.v4.with.grave-accent@[123.145.`178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   384 - assertIsFalse "ip.v4.with.grave-accent@[123.145.178.90`]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   385 - assertIsFalse "ip.v4.with.grave-accent@[`123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   386 - assertIsFalse "ip.v4.with.hash@[123.14#5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   387 - assertIsFalse "ip.v4.with.hash@[123.145#.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   388 - assertIsFalse "ip.v4.with.hash@[123.145.#178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   389 - assertIsFalse "ip.v4.with.hash@[123.145.178.90#]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   390 - assertIsFalse "ip.v4.with.hash@[#123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   391 - assertIsFalse "ip.v4.with.percentage@[123.14%5.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   392 - assertIsFalse "ip.v4.with.percentage@[123.145%.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   393 - assertIsFalse "ip.v4.with.percentage@[123.145.%178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   394 - assertIsFalse "ip.v4.with.percentage@[123.145.178.90%]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   395 - assertIsFalse "ip.v4.with.percentage@[%123.145.178.90]"                    =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   396 - assertIsFalse "ip.v4.with.pipe@[123.14|5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   397 - assertIsFalse "ip.v4.with.pipe@[123.145|.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   398 - assertIsFalse "ip.v4.with.pipe@[123.145.|178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   399 - assertIsFalse "ip.v4.with.pipe@[123.145.178.90|]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   400 - assertIsFalse "ip.v4.with.pipe@[|123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   401 - assertIsFalse "ip.v4.with.plus@[123.14+5.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   402 - assertIsFalse "ip.v4.with.plus@[123.145+.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   403 - assertIsFalse "ip.v4.with.plus@[123.145.+178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   404 - assertIsFalse "ip.v4.with.plus@[123.145.178.90+]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   405 - assertIsFalse "ip.v4.with.plus@[+123.145.178.90]"                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   406 - assertIsFalse "ip.v4.with.leftbracket@[123.14{5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   407 - assertIsFalse "ip.v4.with.leftbracket@[123.145{.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   408 - assertIsFalse "ip.v4.with.leftbracket@[123.145.{178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   409 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90{]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   410 - assertIsFalse "ip.v4.with.leftbracket@[{123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   411 - assertIsFalse "ip.v4.with.rightbracket@[123.14}5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   412 - assertIsFalse "ip.v4.with.rightbracket@[123.145}.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   413 - assertIsFalse "ip.v4.with.rightbracket@[123.145.}178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   414 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90}]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   415 - assertIsFalse "ip.v4.with.rightbracket@[}123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   416 - assertIsFalse "ip.v4.with.leftbracket@[123.14(5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   417 - assertIsFalse "ip.v4.with.leftbracket@[123.145(.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   418 - assertIsFalse "ip.v4.with.leftbracket@[123.145.(178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   419 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90(]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   420 - assertIsFalse "ip.v4.with.leftbracket@[(123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   421 - assertIsFalse "ip.v4.with.rightbracket@[123.14)5.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   422 - assertIsFalse "ip.v4.with.rightbracket@[123.145).178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   423 - assertIsFalse "ip.v4.with.rightbracket@[123.145.)178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   424 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90)]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   425 - assertIsFalse "ip.v4.with.rightbracket@[)123.145.178.90]"                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   426 - assertIsFalse "ip.v4.with.leftbracket@[123.14[5.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   427 - assertIsFalse "ip.v4.with.leftbracket@[123.145[.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   428 - assertIsFalse "ip.v4.with.leftbracket@[123.145.[178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   429 - assertIsFalse "ip.v4.with.leftbracket@[123.145.178.90[]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   430 - assertIsFalse "ip.v4.with.leftbracket@[[123.145.178.90]"                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   431 - assertIsFalse "ip.v4.with.rightbracket@[123.14]5.178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   432 - assertIsFalse "ip.v4.with.rightbracket@[123.145].178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   433 - assertIsFalse "ip.v4.with.rightbracket@[123.145.]178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   434 - assertIsFalse "ip.v4.with.rightbracket@[123.145.178.90]]"                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   435 - assertIsFalse "ip.v4.with.rightbracket@[]123.145.178.90]"                  =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   436 - assertIsFalse "ip.v4.with.tilde@[123.14~5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   437 - assertIsFalse "ip.v4.with.tilde@[123.145~.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   438 - assertIsFalse "ip.v4.with.tilde@[123.145.~178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   439 - assertIsFalse "ip.v4.with.tilde@[123.145.178.90~]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   440 - assertIsFalse "ip.v4.with.tilde@[~123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   441 - assertIsFalse "ip.v4.with.xor@[123.14^5.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   442 - assertIsFalse "ip.v4.with.xor@[123.145^.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   443 - assertIsFalse "ip.v4.with.xor@[123.145.^178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   444 - assertIsFalse "ip.v4.with.xor@[123.145.178.90^]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   445 - assertIsFalse "ip.v4.with.xor@[^123.145.178.90]"                           =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   446 - assertIsFalse "ip.v4.with.colon@[123.14:5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   447 - assertIsFalse "ip.v4.with.colon@[123.145:.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   448 - assertIsFalse "ip.v4.with.colon@[123.145.:178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   449 - assertIsFalse "ip.v4.with.colon@[123.145.178.90:]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   450 - assertIsFalse "ip.v4.with.colon@[:123.145.178.90]"                         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   451 - assertIsFalse "ip.v4.with.space@[123.14 5.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   452 - assertIsFalse "ip.v4.with.space@[123.145 .178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   453 - assertIsFalse "ip.v4.with.space@[123.145. 178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   454 - assertIsFalse "ip.v4.with.space@[123.145.178.90 ]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   455 - assertIsFalse "ip.v4.with.space@[ 123.145.178.90]"                         =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   456 - assertIsTrue  "email@[123.123.123.123]"                                    =   2 =  OK 
     *   457 - assertIsFalse "email@111.222.333"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   458 - assertIsFalse "email@111.222.333.256"                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   459 - assertIsFalse "email@[123.123.123.123"                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   460 - assertIsFalse "email@[123.123.123].123"                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   461 - assertIsFalse "email@123.123.123.123]"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   462 - assertIsFalse "email@123.123.[123.123]"                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   463 - assertIsFalse "ab@988.120.150.10"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   464 - assertIsFalse "ab@120.256.256.120"                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   465 - assertIsFalse "ab@120.25.1111.120"                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   466 - assertIsFalse "ab@[188.120.150.10"                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   467 - assertIsFalse "ab@188.120.150.10]"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   468 - assertIsFalse "ab@[188.120.150.10].com"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   469 - assertIsTrue  "ab@188.120.150.10"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   470 - assertIsTrue  "ab@1.0.0.10"                                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   471 - assertIsTrue  "ab@120.25.254.120"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   472 - assertIsTrue  "ab@01.120.150.1"                                            =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   473 - assertIsTrue  "ab@88.120.150.021"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   474 - assertIsTrue  "ab@88.120.150.01"                                           =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   475 - assertIsTrue  "email@123.123.123.123"                                      =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   476 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                 =   4 =  OK 
     *   477 - assertIsFalse "ABC.DEF@[IP"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   478 - assertIsFalse "ABC.DEF@[IPv6]"                                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   479 - assertIsFalse "ABC.DEF@[IPv6:]"                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   480 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   481 - assertIsFalse "ABC.DEF@[IPv6:1]"                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   482 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                         =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   483 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                       =   4 =  OK 
     *   484 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                     =   4 =  OK 
     *   485 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                  =   4 =  OK 
     *   486 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                 =   4 =  OK 
     *   487 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                 =   4 =  OK 
     *   488 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                               =   4 =  OK 
     *   489 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   490 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                             =   4 =  OK 
     *   491 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *   492 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                     =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   493 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4::]"                                   =   4 =  OK 
     *   494 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   495 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   496 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   497 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   498 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   499 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                              =   4 =  OK 
     *   500 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   501 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   502 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   503 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   504 - assertIsTrue  "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]"                             =   4 =  OK 
     *   505 - assertIsFalse "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   506 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   507 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   508 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   509 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   510 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   511 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   512 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:2_2:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   513 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22_:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   514 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:_3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   515 - assertIsFalse "ip.v6.with.underscore@[IPv6:1:22:3:4:5:6:7_]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   516 - assertIsFalse "ip.v6.with.amp@[IPv6:1:2&2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   517 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22&:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   518 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:&3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   519 - assertIsFalse "ip.v6.with.amp@[IPv6:1:22:3:4:5:6:7&]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   520 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:2*2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   521 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22*:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   522 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:*3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   523 - assertIsFalse "ip.v6.with.asterisk@[IPv6:1:22:3:4:5:6:7*]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   524 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:2$2:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   525 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22$:3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   526 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:$3:4:5:6:7]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   527 - assertIsFalse "ip.v6.with.dollar@[IPv6:1:22:3:4:5:6:7$]"                   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   528 - assertIsFalse "ip.v6.with.equality@[IPv6:1:2=2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   529 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22=:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   530 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:=3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   531 - assertIsFalse "ip.v6.with.equality@[IPv6:1:22:3:4:5:6:7=]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   532 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:2!2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   533 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22!:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   534 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:!3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   535 - assertIsFalse "ip.v6.with.exclamation@[IPv6:1:22:3:4:5:6:7!]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   536 - assertIsFalse "ip.v6.with.question@[IPv6:1:2?2:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   537 - assertIsFalse "ip.v6.with.question@[IPv6:1:22?:3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   538 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:?3:4:5:6:7]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   539 - assertIsFalse "ip.v6.with.question@[IPv6:1:22:3:4:5:6:7?]"                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   540 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:2`2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   541 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22`:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   542 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:`3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   543 - assertIsFalse "ip.v6.with.grave-accent@[IPv6:1:22:3:4:5:6:7`]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   544 - assertIsFalse "ip.v6.with.hash@[IPv6:1:2#2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   545 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22#:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   546 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:#3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   547 - assertIsFalse "ip.v6.with.hash@[IPv6:1:22:3:4:5:6:7#]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   548 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:2%2:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   549 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22%:3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   550 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:%3:4:5:6:7]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   551 - assertIsFalse "ip.v6.with.percentage@[IPv6:1:22:3:4:5:6:7%]"               =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   552 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:2|2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   553 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22|:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   554 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:|3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   555 - assertIsFalse "ip.v6.with.pipe@[IPv6:1:22:3:4:5:6:7|]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   556 - assertIsFalse "ip.v6.with.plus@[IPv6:1:2+2:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   557 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22+:3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   558 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:+3:4:5:6:7]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   559 - assertIsFalse "ip.v6.with.plus@[IPv6:1:22:3:4:5:6:7+]"                     =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   560 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2{2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   561 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22{:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   562 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:{3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   563 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7{]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   564 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2}2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   565 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22}:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   566 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:}3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   567 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7}]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   568 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2(2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   569 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22(:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   570 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:(3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   571 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7(]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   572 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2)2:3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   573 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22):3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   574 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:)3:4:5:6:7]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   575 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7)]"             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   576 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:2[2:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   577 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22[:3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   578 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:[3:4:5:6:7]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   579 - assertIsFalse "ip.v6.with.leftbracket@[IPv6:1:22:3:4:5:6:7[]"              =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   580 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:2]2:3:4:5:6:7]"             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   581 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22]:3:4:5:6:7]"             =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   582 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:]3:4:5:6:7]"             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   583 - assertIsFalse "ip.v6.with.rightbracket@[IPv6:1:22:3:4:5:6:7]]"             =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   584 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:2~2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   585 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22~:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   586 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:~3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   587 - assertIsFalse "ip.v6.with.tilde@[IPv6:1:22:3:4:5:6:7~]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   588 - assertIsFalse "ip.v6.with.xor@[IPv6:1:2^2:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   589 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22^:3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   590 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:^3:4:5:6:7]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   591 - assertIsFalse "ip.v6.with.xor@[IPv6:1:22:3:4:5:6:7^]"                      =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   592 - assertIsFalse "ip.v6.with.space@[IPv6:1:2 2:3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   593 - assertIsFalse "ip.v6.with.space@[IPv6:1:22 :3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   594 - assertIsFalse "ip.v6.with.space@[IPv6:1:22: 3:4:5:6:7]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   595 - assertIsFalse "ip.v6.with.space@[IPv6:1:22:3:4:5:6:7 ]"                    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   596 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"              =   4 =  OK 
     *   597 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =   4 =  OK 
     *   598 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   599 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   600 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =   4 =  OK 
     *   601 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   602 - assertIsTrue  "ABC.DEF@[IPv6:0000:0000:0000:0000:0000:0000:0000:0000]"     =   4 =  OK 
     *   603 - assertIsTrue  "ABC.DEF@[IPv6:ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff]"     =   4 =  OK 
     *   604 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:0000:85a3:0000:0000:ac1f:8001]"     =   4 =  OK 
     *   605 - assertIsTrue  "ABC.DEF@[IPv6:fe80::217:f2ff:fe07:ed62]"                    =   4 =  OK 
     *   606 - assertIsTrue  "ABC.DEF@[IPv6:fe00::1]"                                     =   4 =  OK 
     *   607 - assertIsFalse "ABC.DEF@[IPv6:10.168.0001.100]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   608 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:00001:C1C0:ABCD:0876]"    =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   609 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234: 0000:0000:C1C0:ABCD:0876]"    =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   610 - assertIsFalse "ABC.DEF@[IPv6:2001:0000:1234:0000:0000:C1C0:ABCD:0876 0]"   =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   611 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                             =   4 =  OK 
     *   612 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                             =   4 =  OK 
     *   613 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                 =   4 =  OK 
     *   614 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                 =   4 =  OK 
     *   615 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   616 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   617 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   618 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   619 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *   620 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                         =   1 =  OK 
     *   621 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                         =   1 =  OK 
     *   622 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                         =   1 =  OK 
     *   623 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                       =   1 =  OK 
     *   624 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                        =   1 =  OK 
     *   625 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                        =   1 =  OK 
     *   626 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                        =   1 =  OK 
     *   627 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   628 - assertIsFalse "\"\"@GHI.DE"                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   629 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   630 - assertIsFalse "A@G\"HI.DE"                                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   631 - assertIsFalse "\"@GHI.DE"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   632 - assertIsFalse "ABC.DEF.\""                                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   633 - assertIsFalse "ABC.DEF@\"\""                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   634 - assertIsFalse "ABC.DEF@G\"HI.DE"                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   635 - assertIsFalse "ABC.DEF@GHI.DE\""                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   636 - assertIsFalse "ABC.DEF@\"GHI.DE"                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   637 - assertIsFalse "\"Escape.Sequenz.Ende\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   638 - assertIsFalse "ABC.DEF\"GHI.DE"                                            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   639 - assertIsFalse "ABC.DEF\"@GHI.DE"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   640 - assertIsFalse "ABC.DE\"F@GHI.DE"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   641 - assertIsFalse "\"ABC.DEF@GHI.DE"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   642 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                         =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   643 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                        =   1 =  OK 
     *   644 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                        =   1 =  OK 
     *   645 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                 =   1 =  OK 
     *   646 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   647 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   648 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   649 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   650 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   651 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                     =   1 =  OK 
     *   652 - assertIsFalse "\"Ende.am.Eingabeende\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   653 - assertIsFalse "0\"00.000\"@GHI.JKL"                                        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   654 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                       =   1 =  OK 
     *   655 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                             =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   656 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   657 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   658 - assertIsTrue  "\"()<>[]:.;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"      =   1 =  OK 
     *   659 - assertIsFalse "\"Joe Smith\" email@domain.com"                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   660 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   661 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *   662 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                           =   6 =  OK 
     *   663 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   664 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                           =   6 =  OK 
     *   665 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                       =   6 =  OK 
     *   666 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                 =   6 =  OK 
     *   667 - assertIsFalse "ABC.DEF@             (MNO)"                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   668 - assertIsFalse "ABC.DEF@   .         (MNO)"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   669 - assertIsFalse "ABC.DEF              (MNO)"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   670 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                 =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   671 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   672 - assertIsFalse "ABC.DEF@GHI.JKL          "                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   673 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   674 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                         =   6 =  OK 
     *   675 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                          =   6 =  OK 
     *   676 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                          =   6 =  OK 
     *   677 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                          =   6 =  OK 
     *   678 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                          =   6 =  OK 
     *   679 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   680 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                       = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   681 - assertIsFalse "(ABC).DEF@GHI.JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   682 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                          = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   683 - assertIsFalse "ABC.DEF@(GHI).JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   684 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                      = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   685 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   686 - assertIsFalse "AB(CD)EF@GHI.JKL"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   687 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   688 - assertIsFalse "(ABCDEF)@GHI.JKL"                                           =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   689 - assertIsFalse "(ABCDEF).@GHI.JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   690 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   691 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                          =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   692 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                         =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   693 - assertIsFalse "ABC(DEF@GHI.JKL"                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   694 - assertIsFalse "ABC.DEF@GHI)JKL"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   695 - assertIsFalse ")ABC.DEF@GHI.JKL"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   696 - assertIsFalse "ABC(DEF@GHI).JKL"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   697 - assertIsFalse "ABC(DEF.GHI).JKL"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   698 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                          =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   699 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   700 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   701 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   702 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   703 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   704 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                 =   2 =  OK 
     *   705 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *   706 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                 =   2 =  OK 
     *   707 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                             =   2 =  OK 
     *   708 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   709 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                     =   4 =  OK 
     *   710 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                     =   4 =  OK 
     *   711 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                 =   4 =  OK 
     *   712 - assertIsTrue  "(comment)john.smith@example.com"                            =   6 =  OK 
     *   713 - assertIsTrue  "john.smith(comment)@example.com"                            =   6 =  OK 
     *   714 - assertIsTrue  "john.smith@(comment)example.com"                            =   6 =  OK 
     *   715 - assertIsTrue  "john.smith@example.com(comment)"                            =   6 =  OK 
     *   716 - assertIsFalse "john.smith@exampl(comment)e.com"                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   717 - assertIsFalse "john.s(comment)mith@example.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   718 - assertIsFalse "john.smith(comment)@(comment)example.com"                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   719 - assertIsFalse "john.smith(com@ment)example.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   720 - assertIsFalse "email( (nested) )@plus.com"                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   721 - assertIsFalse "email)mirror(@plus.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   722 - assertIsFalse "email@plus.com (not closed comment"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   723 - assertIsFalse "email(with @ in comment)plus.com"                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   724 - assertIsTrue  "email@domain.com (joe Smith)"                               =   6 =  OK 
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *   725 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                  =   0 =  OK 
     *   726 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                  =   0 =  OK 
     *   727 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                   =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   728 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                   =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   729 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                              =   0 =  OK 
     *   730 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                        =   1 =  OK 
     *   731 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                =   1 =  OK 
     *   732 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   733 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   734 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   735 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   736 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   737 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   738 - assertIsFalse "ABC DEF <A@A>"                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   739 - assertIsFalse "<A@A> ABC DEF"                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   740 - assertIsFalse "ABC DEF <>"                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   741 - assertIsFalse "<> ABC DEF"                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   742 - assertIsFalse ">"                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   743 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                          =   0 =  OK 
     *   744 - assertIsTrue  "Joe Smith <email@domain.com>"                               =   0 =  OK 
     *   745 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   746 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   747 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *   748 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *   749 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" =   9 =  OK 
     *   750 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " =   9 =  OK 
     *   751 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   752 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>" =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   753 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   754 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   755 - assertIsFalse "Test |<gaaf <email@domain.com>"                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   756 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   757 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   758 - assertIsFalse "<null>@mail.com"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *   759 - assertIsTrue  "A@B.CD"                                                     =   0 =  OK 
     *   760 - assertIsFalse "A@B.C"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   761 - assertIsFalse "A@COM"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   762 - assertIsTrue  "ABC.DEF@GHI.JKL"                                            =   0 =  OK 
     *   763 - assertIsTrue  "ABC.DEF@GHI.J"                                              =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   764 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *   765 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *   766 - assertIsTrue  "A@B.CD"                                                     =   0 =  OK 
     *   767 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *   768 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *   769 - assertIsTrue  "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" =   0 =  OK 
     *   770 - assertIsFalse "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   771 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   772 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   773 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   774 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   775 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   776 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   777 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   778 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   779 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   780 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *   781 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   782 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   783 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   784 - assertIsTrue  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   785 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   786 - assertIsTrue  "email@domain.topleveldomain"                                =   0 =  OK 
     * 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   787 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                           =   6 =  OK 
     *   788 - assertIsTrue  "\"MaxMustermann\"@example.com"                              =   1 =  OK 
     *   789 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                 =   1 =  OK 
     *   790 - assertIsTrue  "\".John.Doe\"@example.com"                                  =   1 =  OK 
     *   791 - assertIsTrue  "\"John.Doe.\"@example.com"                                  =   1 =  OK 
     *   792 - assertIsTrue  "\"John..Doe\"@example.com"                                  =   1 =  OK 
     *   793 - assertIsTrue  "john.smith(comment)@example.com"                            =   6 =  OK 
     *   794 - assertIsTrue  "(comment)john.smith@example.com"                            =   6 =  OK 
     *   795 - assertIsTrue  "john.smith@(comment)example.com"                            =   6 =  OK 
     *   796 - assertIsTrue  "john.smith@example.com(comment)"                            =   6 =  OK 
     *   797 - assertIsTrue  "john.smith@example.com"                                     =   0 =  OK 
     *   798 - assertIsTrue  "jsmith@[192.168.2.1]"                                       =   2 =  OK 
     *   799 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                  =   4 =  OK 
     *   800 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                          =   0 =  OK 
     *   801 - assertIsTrue  "Marc Dupont <md118@example.com>"                            =   0 =  OK 
     *   802 - assertIsTrue  "simple@example.com"                                         =   0 =  OK 
     *   803 - assertIsTrue  "very.common@example.com"                                    =   0 =  OK 
     *   804 - assertIsTrue  "disposable.style.email.with+symbol@example.com"             =   0 =  OK 
     *   805 - assertIsTrue  "other.email-with-hyphen@example.com"                        =   0 =  OK 
     *   806 - assertIsTrue  "fully-qualified-domain@example.com"                         =   0 =  OK 
     *   807 - assertIsTrue  "user.name+tag+sorting@example.com"                          =   0 =  OK 
     *   808 - assertIsTrue  "user+mailbox/department=shipping@example.com"               =   0 =  OK 
     *   809 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                           =   0 =  OK 
     *   810 - assertIsTrue  "x@example.com"                                              =   0 =  OK 
     *   811 - assertIsTrue  "info@firma.org"                                             =   0 =  OK 
     *   812 - assertIsTrue  "example-indeed@strange-example.com"                         =   0 =  OK 
     *   813 - assertIsTrue  "admin@mailserver1"                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   814 - assertIsTrue  "example@s.example"                                          =   0 =  OK 
     *   815 - assertIsTrue  "\" \"@example.org"                                          =   1 =  OK 
     *   816 - assertIsTrue  "\"john..doe\"@example.org"                                  =   1 =  OK 
     *   817 - assertIsTrue  "mailhost!username@example.org"                              =   0 =  OK 
     *   818 - assertIsTrue  "user%example.com@example.org"                               =   0 =  OK 
     *   819 - assertIsTrue  "joe25317@NOSPAMexample.com"                                 =   0 =  OK 
     *   820 - assertIsTrue  "Peter.Zapfl@Telekom.DBP.De"                                 =   0 =  OK 
     *   821 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   822 - assertIsFalse "Abc..123@example.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   823 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   824 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   825 - assertIsFalse "just\"not\"right@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   826 - assertIsFalse "this is\"not\allowed@example.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   827 - assertIsFalse "this\ still\\"not\\allowed@example.com"                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   828 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   829 - assertIsFalse "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com" =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     * 
     * 
     * ---- Testclass EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   830 - assertIsFalse "nolocalpart.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   831 - assertIsFalse "test@example.com test"                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   832 - assertIsFalse "user  name@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   833 - assertIsFalse "user   name@example.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   834 - assertIsFalse "example.@example.co.uk"                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   835 - assertIsFalse "example@example@example.co.uk"                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   836 - assertIsFalse "(test_exampel@example.fr}"                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   837 - assertIsFalse "example(example)example@example.co.uk"                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   838 - assertIsFalse ".example@localhost"                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   839 - assertIsFalse "ex\ample@localhost"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   840 - assertIsFalse "example@local\host"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   841 - assertIsFalse "example@localhost."                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   842 - assertIsFalse "user name@example.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   843 - assertIsFalse "username@ example . com"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   844 - assertIsFalse "example@(fake}.com"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   845 - assertIsFalse "example@(fake.com"                                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   846 - assertIsTrue  "username@example.com"                                       =   0 =  OK 
     *   847 - assertIsTrue  "usern.ame@example.com"                                      =   0 =  OK 
     *   848 - assertIsFalse "user[na]me@example.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   849 - assertIsFalse "\"\"\"@iana.org"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   850 - assertIsFalse "\"\\"@iana.org"                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   851 - assertIsFalse "\"test\"test@iana.org"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   852 - assertIsFalse "\"test\"\"test\"@iana.org"                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   853 - assertIsTrue  "\"test\".\"test\"@iana.org"                                 =   1 =  OK 
     *   854 - assertIsTrue  "\"test\".test@iana.org"                                     =   1 =  OK 
     *   855 - assertIsFalse "\"test\\"@iana.org"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   856 - assertIsFalse "\r\ntest@iana.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   857 - assertIsFalse "\r\n test@iana.org"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   858 - assertIsFalse "\r\n \r\ntest@iana.org"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   859 - assertIsFalse "\r\n \r\n test@iana.org"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   860 - assertIsFalse "test@iana.org \r\n"                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   861 - assertIsFalse "test@iana.org \r\n "                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   862 - assertIsFalse "test@iana.org \r\n \r\n"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   863 - assertIsFalse "test@iana.org \r\n\r\n"                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   864 - assertIsFalse "test@iana.org  \r\n\r\n "                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   865 - assertIsFalse "test@iana/icann.org"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   866 - assertIsFalse "test@foo;bar.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   867 - assertIsFalse "a@test.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   868 - assertIsFalse "comment)example@example.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   869 - assertIsFalse "comment(example))@example.com"                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   870 - assertIsFalse "example@example)comment.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   871 - assertIsFalse "example@example(comment)).com"                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   872 - assertIsFalse "example@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   873 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                              =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   874 - assertIsFalse "exam(ple@exam).ple"                                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   875 - assertIsFalse "example@(example))comment.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   876 - assertIsTrue  "example@example.com"                                        =   0 =  OK 
     *   877 - assertIsTrue  "example@example.co.uk"                                      =   0 =  OK 
     *   878 - assertIsTrue  "example_underscore@example.fr"                              =   0 =  OK 
     *   879 - assertIsTrue  "exam'ple@example.com"                                       =   0 =  OK 
     *   880 - assertIsTrue  "exam\ ple@example.com"                                      =   0 =  OK 
     *   881 - assertIsFalse "example((example))@fakedfake.co.uk"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   882 - assertIsFalse "example@faked(fake).co.uk"                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   883 - assertIsTrue  "example+@example.com"                                       =   0 =  OK 
     *   884 - assertIsTrue  "example@with-hyphen.example.com"                            =   0 =  OK 
     *   885 - assertIsTrue  "with-hyphen@example.com"                                    =   0 =  OK 
     *   886 - assertIsTrue  "example@1leadingnum.example.com"                            =   0 =  OK 
     *   887 - assertIsTrue  "1leadingnum@example.com"                                    =   0 =  OK 
     *   888 - assertIsTrue  "инфо@письмо.рф"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   889 - assertIsTrue  "\"username\"@example.com"                                   =   1 =  OK 
     *   890 - assertIsTrue  "\"user.name\"@example.com"                                  =   1 =  OK 
     *   891 - assertIsTrue  "\"user name\"@example.com"                                  =   1 =  OK 
     *   892 - assertIsTrue  "\"user@name\"@example.com"                                  =   1 =  OK 
     *   893 - assertIsFalse "\"\a\"@iana.org"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   894 - assertIsTrue  "\"test\ test\"@iana.org"                                    =   1 =  OK 
     *   895 - assertIsFalse "\"\"@iana.org"                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   896 - assertIsFalse "\"\"@[]"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   897 - assertIsTrue  "\"\\"\"@iana.org"                                           =   1 =  OK 
     *   898 - assertIsTrue  "example@localhost"                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   899 - assertIsFalse "..@test.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   900 - assertIsFalse ".a@test.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   901 - assertIsFalse "ab@sd@dd"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   902 - assertIsFalse ".@s.dd"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   903 - assertIsFalse "a@b.-de.cc"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   904 - assertIsFalse "a@bde-.cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   905 - assertIsFalse "a@b._de.cc"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   906 - assertIsFalse "a@bde_.cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   907 - assertIsFalse "a@bde.cc."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   908 - assertIsFalse "ab@b+de.cc"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   909 - assertIsFalse "a..b@bde.cc"                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   910 - assertIsFalse "_@bde.cc."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   911 - assertIsFalse "plainaddress"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   912 - assertIsFalse "plain.address"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   913 - assertIsFalse ".email@domain.com"                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   914 - assertIsFalse "email.@domain.com"                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   915 - assertIsFalse "email..email@domain.com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   916 - assertIsFalse "email@.domain.com"                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   917 - assertIsFalse "email@domain.com."                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   918 - assertIsFalse "email@domain..com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   919 - assertIsFalse "MailTo:casesensitve@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   920 - assertIsFalse "mailto:email@domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   921 - assertIsFalse "email@domain"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   922 - assertIsTrue  "someone@somewhere.com"                                      =   0 =  OK 
     *   923 - assertIsTrue  "someone@somewhere.co.uk"                                    =   0 =  OK 
     *   924 - assertIsTrue  "someone+tag@somewhere.net"                                  =   0 =  OK 
     *   925 - assertIsTrue  "futureTLD@somewhere.fooo"                                   =   0 =  OK 
     *   926 - assertIsFalse "myemailsample.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   927 - assertIsTrue  "myemail@sample"                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   928 - assertIsTrue  "myemail@sample.com"                                         =   0 =  OK 
     *   929 - assertIsFalse "myemail@@sample.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   930 - assertIsFalse "myemail@sa@mple.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   931 - assertIsTrue  "\"myemail@sa\"@mple.com"                                    =   1 =  OK 
     *   932 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   933 - assertIsTrue  "\"foo\@bar\"@Something.com"                                 =   1 =  OK 
     *   934 - assertIsFalse "Foobar Some@thing.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   935 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   936 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                  =   0 =  OK 
     *   937 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   938 - assertIsTrue  "cog@wheel.com"                                              =   0 =  OK 
     *   939 - assertIsTrue  "\"cogwheel the orange\"@example.com"                        =   1 =  OK 
     *   940 - assertIsFalse "123@$.xyz"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   941 - assertIsTrue  "david.jones@proseware.com"                                  =   0 =  OK 
     *   942 - assertIsTrue  "d.j@server1.proseware.com"                                  =   0 =  OK 
     *   943 - assertIsTrue  "jones@ms1.proseware.com"                                    =   0 =  OK 
     *   944 - assertIsFalse "j.@server1.proseware.com"                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   945 - assertIsTrue  "j@proseware.com9"                                           =   0 =  OK 
     *   946 - assertIsTrue  "js#internal@proseware.com"                                  =   0 =  OK 
     *   947 - assertIsTrue  "j_9@[129.126.118.1]"                                        =   2 =  OK 
     *   948 - assertIsFalse "j..s@proseware.com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   949 - assertIsTrue  "js*@proseware.com"                                          =   0 =  OK 
     *   950 - assertIsFalse "js@proseware..com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   951 - assertIsTrue  "js@proseware.com9"                                          =   0 =  OK 
     *   952 - assertIsTrue  "j.s@server1.proseware.com"                                  =   0 =  OK 
     *   953 - assertIsTrue  "\"j\\"s\"@proseware.com"                                    =   1 =  OK 
     *   954 - assertIsFalse "dasddas-@.com"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   955 - assertIsTrue  "-asd@das.com"                                               =   0 =  OK 
     *   956 - assertIsFalse "as3d@dac.coas-"                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   957 - assertIsTrue  "dsq!a?@das.com"                                             =   0 =  OK 
     *   958 - assertIsTrue  "_dasd@sd.com"                                               =   0 =  OK 
     *   959 - assertIsFalse "dad@sds"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   960 - assertIsTrue  "asd-@asd.com"                                               =   0 =  OK 
     *   961 - assertIsTrue  "dasd_-@jdas.com"                                            =   0 =  OK 
     *   962 - assertIsFalse "asd@dasd@asd.cm"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   963 - assertIsFalse "da23@das..com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   964 - assertIsTrue  "_dasd_das_@9.com"                                           =   0 =  OK 
     *   965 - assertIsTrue  "d23d@da9.co9"                                               =   0 =  OK 
     *   966 - assertIsTrue  "dasd.dadas@dasd.com"                                        =   0 =  OK 
     *   967 - assertIsTrue  "dda_das@das-dasd.com"                                       =   0 =  OK 
     *   968 - assertIsTrue  "dasd-dasd@das.com.das"                                      =   0 =  OK 
     *   969 - assertIsFalse "fdsa"                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   970 - assertIsFalse "fdsa@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   971 - assertIsFalse "fdsa@fdsa"                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   972 - assertIsFalse "fdsa@fdsa."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   973 - assertIsFalse "@b.com"                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   974 - assertIsFalse "a@.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   975 - assertIsTrue  "a@bcom"                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   976 - assertIsTrue  "a.b@com"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   977 - assertIsFalse "a@b."                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   978 - assertIsTrue  "ab@c.com"                                                   =   0 =  OK 
     *   979 - assertIsTrue  "a@bc.com"                                                   =   0 =  OK 
     *   980 - assertIsTrue  "a@b.com"                                                    =   0 =  OK 
     *   981 - assertIsTrue  "a@b.c.com"                                                  =   0 =  OK 
     *   982 - assertIsTrue  "a+b@c.com"                                                  =   0 =  OK 
     *   983 - assertIsTrue  "a@123.45.67.89"                                             =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   984 - assertIsTrue  "%2@gmail.com"                                               =   0 =  OK 
     *   985 - assertIsTrue  "\"%2\"@gmail.com"                                           =   1 =  OK 
     *   986 - assertIsTrue  "\"a..b\"@gmail.com"                                         =   1 =  OK 
     *   987 - assertIsTrue  "\"a_b\"@gmail.com"                                          =   1 =  OK 
     *   988 - assertIsTrue  "_@gmail.com"                                                =   0 =  OK 
     *   989 - assertIsTrue  "1@gmail.com"                                                =   0 =  OK 
     *   990 - assertIsTrue  "1_example@something.gmail.com"                              =   0 =  OK 
     *   991 - assertIsTrue  "d._.___d@gmail.com"                                         =   0 =  OK 
     *   992 - assertIsTrue  "d.oy.smith@gmail.com"                                       =   0 =  OK 
     *   993 - assertIsTrue  "d_oy_smith@gmail.com"                                       =   0 =  OK 
     *   994 - assertIsTrue  "doysmith@gmail.com"                                         =   0 =  OK 
     *   995 - assertIsTrue  "D.Oy'Smith@gmail.com"                                       =   0 =  OK 
     *   996 - assertIsTrue  "%20f3v34g34@gvvre.com"                                      =   0 =  OK 
     *   997 - assertIsTrue  "piskvor@example.lighting"                                   =   0 =  OK 
     *   998 - assertIsTrue  "--@ooo.ooo"                                                 =   0 =  OK 
     *   999 - assertIsFalse "check@thiscom"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1000 - assertIsFalse "check@this..com"                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1001 - assertIsFalse " check@this.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1002 - assertIsTrue  "check@this.com"                                             =   0 =  OK 
     *  1003 - assertIsTrue  "Abc@example.com"                                            =   0 =  OK 
     *  1004 - assertIsFalse "Abc@example.com."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1005 - assertIsTrue  "Abc@10.42.0.1"                                              =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1006 - assertIsTrue  "user@localserver"                                           =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1007 - assertIsTrue  "Abc.123@example.com"                                        =   0 =  OK 
     *  1008 - assertIsTrue  "user+mailbox/department=shipping@example.com"               =   0 =  OK 
     *  1009 - assertIsTrue  "\" \"@example.org"                                          =   1 =  OK 
     *  1010 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                    =   4 =  OK 
     *  1011 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1012 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1013 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1014 - assertIsFalse "this\ still\\"not\allowed@example.com"                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1015 - assertIsTrue  "email@example.com"                                          =   0 =  OK 
     *  1016 - assertIsTrue  "email@example.co.uk"                                        =   0 =  OK 
     *  1017 - assertIsTrue  "email@mail.gmail.com"                                       =   0 =  OK 
     *  1018 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com" =   0 =  OK 
     *  1019 - assertIsFalse "email@example.co.uk."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1020 - assertIsFalse "email@example"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1021 - assertIsFalse " email@example.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1022 - assertIsFalse "email@example.com "                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1023 - assertIsFalse "invalid.email.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1024 - assertIsFalse "invalid@email@domain.com"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1025 - assertIsFalse "email@example..com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1026 - assertIsTrue  "yoursite@ourearth.com"                                      =   0 =  OK 
     *  1027 - assertIsTrue  "my.ownsite@ourearth.org"                                    =   0 =  OK 
     *  1028 - assertIsTrue  "mysite@you.me.net"                                          =   0 =  OK 
     *  1029 - assertIsTrue  "xxxx@gmail.com"                                             =   0 =  OK 
     *  1030 - assertIsTrue  "xxxxxx@yahoo.com"                                           =   0 =  OK 
     *  1031 - assertIsFalse "xxxx.ourearth.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1032 - assertIsFalse "xxxx@.com.my"                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1033 - assertIsFalse "@you.me.net"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1034 - assertIsFalse "xxxx123@gmail.b"                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1035 - assertIsFalse "xxxx@.org.org"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1036 - assertIsFalse ".xxxx@mysite.org"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1037 - assertIsFalse "xxxxx()*@gmail.com"                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1038 - assertIsFalse "xxxx..1234@yahoo.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1039 - assertIsTrue  "alex@example.com"                                           =   0 =  OK 
     *  1040 - assertIsTrue  "alireza@test.co.uk"                                         =   0 =  OK 
     *  1041 - assertIsTrue  "peter.example@yahoo.com.au"                                 =   0 =  OK 
     *  1042 - assertIsTrue  "peter_123@news.com"                                         =   0 =  OK 
     *  1043 - assertIsTrue  "hello7___@ca.com.pt"                                        =   0 =  OK 
     *  1044 - assertIsTrue  "example@example.co"                                         =   0 =  OK 
     *  1045 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1046 - assertIsFalse "hallo2ww22@example....caaaao"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1047 - assertIsTrue  "abcxyz123@qwert.com"                                        =   0 =  OK 
     *  1048 - assertIsTrue  "abc123xyz@asdf.co.in"                                       =   0 =  OK 
     *  1049 - assertIsTrue  "abc1_xyz1@gmail1.com"                                       =   0 =  OK 
     *  1050 - assertIsTrue  "abc.xyz@gmail.com.in"                                       =   0 =  OK 
     *  1051 - assertIsTrue  "pio_pio@factory.com"                                        =   0 =  OK 
     *  1052 - assertIsTrue  "~pio_pio@factory.com"                                       =   0 =  OK 
     *  1053 - assertIsTrue  "pio_~pio@factory.com"                                       =   0 =  OK 
     *  1054 - assertIsTrue  "pio_#pio@factory.com"                                       =   0 =  OK 
     *  1055 - assertIsFalse "pio_pio@#factory.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1056 - assertIsFalse "pio_pio@factory.c#om"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1057 - assertIsFalse "pio_pio@factory.c*om"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1058 - assertIsTrue  "pio^_pio@factory.com"                                       =   0 =  OK 
     *  1059 - assertIsTrue  "\"Abc\@def\"@example.com"                                   =   1 =  OK 
     *  1060 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                =   1 =  OK 
     *  1061 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                               =   1 =  OK 
     *  1062 - assertIsTrue  "Fred\ Bloggs@example.com"                                   =   0 =  OK 
     *  1063 - assertIsFalse "\"Joe\Blow\"@example.com"                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1064 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                  =   1 =  OK 
     *  1065 - assertIsTrue  "\"Abc@def\"@example.com"                                    =   1 =  OK 
     *  1066 - assertIsTrue  "customer/department=shipping@example.com"                   =   0 =  OK 
     *  1067 - assertIsFalse "\$A12345@example.com"                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1068 - assertIsTrue  "!def!xyz%abc@example.com"                                   =   0 =  OK 
     *  1069 - assertIsTrue  "_somename@example.com"                                      =   0 =  OK 
     *  1070 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                             =   1 =  OK 
     *  1071 - assertIsTrue  "\"abcdefghixyz\"@example.com"                               =   1 =  OK 
     *  1072 - assertIsFalse "abc\"defghi\"xyz@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1073 - assertIsFalse "abc\\"def\\"ghi@example.com"                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     * 
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1074 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                 =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1075 - assertIsTrue  "\"back\slash\"@sld.com"                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1076 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                =   1 =  OK 
     *  1077 - assertIsTrue  "\"quoted\"@sld.com"                                         =   1 =  OK 
     *  1078 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                         =   1 =  OK 
     *  1079 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"         =   0 =  OK 
     *  1080 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                        =   0 =  OK 
     *  1081 - assertIsTrue  "01234567890@numbers-in-local.net"                           =   0 =  OK 
     *  1082 - assertIsTrue  "a@single-character-in-local.org"                            =   0 =  OK 
     *  1083 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *  1084 - assertIsTrue  "backticksarelegit@test.com"                                 =   0 =  OK 
     *  1085 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                 =   2 =  OK 
     *  1086 - assertIsTrue  "country-code-tld@sld.rw"                                    =   0 =  OK 
     *  1087 - assertIsTrue  "country-code-tld@sld.uk"                                    =   0 =  OK 
     *  1088 - assertIsTrue  "letters-in-sld@123.com"                                     =   0 =  OK 
     *  1089 - assertIsTrue  "local@dash-in-sld.com"                                      =   0 =  OK 
     *  1090 - assertIsTrue  "local@sld.newTLD"                                           =   0 =  OK 
     *  1091 - assertIsTrue  "local@sub.domains.com"                                      =   0 =  OK 
     *  1092 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                           =   0 =  OK 
     *  1093 - assertIsTrue  "one-character-third-level@a.example.com"                    =   0 =  OK 
     *  1094 - assertIsTrue  "one-letter-sld@x.org"                                       =   0 =  OK 
     *  1095 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                   =   0 =  OK 
     *  1096 - assertIsTrue  "single-character-in-sld@x.org"                              =   0 =  OK 
     *  1097 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *  1098 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-length-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *  1099 - assertIsTrue  "uncommon-tld@sld.mobi"                                      =   0 =  OK 
     *  1100 - assertIsTrue  "uncommon-tld@sld.museum"                                    =   0 =  OK 
     *  1101 - assertIsTrue  "uncommon-tld@sld.travel"                                    =   0 =  OK 
     *  1102 - assertIsFalse "invalid"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1103 - assertIsFalse "invalid@"                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  1104 - assertIsFalse "invalid @"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1105 - assertIsFalse "invalid@[555.666.777.888]"                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1106 - assertIsFalse "invalid@[IPv6:123456]"                                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1107 - assertIsFalse "invalid@[127.0.0.1.]"                                       =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *  1108 - assertIsFalse "invalid@[127.0.0.1]."                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1109 - assertIsFalse "invalid@[127.0.0.1]x"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1110 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *  1111 - assertIsFalse "@missing-local.org"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1112 - assertIsFalse "IP-and-port@127.0.0.1:25"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1113 - assertIsFalse "another-invalid-ip@127.0.0.256"                             =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1114 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1115 - assertIsFalse "invalid-characters-in-sld@! \"#$%()./;<>_[]`|.org"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1116 - assertIsFalse "invalid-ip@127.0.0.1.26"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1117 - assertIsFalse "local-ends-with-dot.@sld.com"                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1118 - assertIsFalse "missing-at-sign.net"                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1119 - assertIsFalse "missing-sld@.com"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1120 - assertIsFalse "missing-tld@sld."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1121 - assertIsFalse "sld-ends-with-dash@sld-.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1122 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1123 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1124 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1125 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-255-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bl.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1126 - assertIsFalse "two..consecutive-dots@sld.com"                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1127 - assertIsFalse "unbracketed-IP@127.0.0.1"                                   =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1128 - assertIsFalse "underscore.error@example.com_"                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1129 - assertIsTrue  "first.last@iana.org"                                        =   0 =  OK 
     *  1130 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *  1131 - assertIsTrue  "\"first\\"last\"@iana.org"                                  =   1 =  OK 
     *  1132 - assertIsTrue  "\"first@last\"@iana.org"                                    =   1 =  OK 
     *  1133 - assertIsTrue  "\"first\\last\"@iana.org"                                   =   1 =  OK 
     *  1134 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *  1135 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *  1136 - assertIsTrue  "first.last@[12.34.56.78]"                                   =   2 =  OK 
     *  1137 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"          =   4 =  OK 
     *  1138 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"           =   4 =  OK 
     *  1139 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"          =   4 =  OK 
     *  1140 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"          =   4 =  OK 
     *  1141 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"  =   4 =  OK 
     *  1142 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *  1143 - assertIsTrue  "first.last@3com.com"                                        =   0 =  OK 
     *  1144 - assertIsTrue  "first.last@123.iana.org"                                    =   0 =  OK 
     *  1145 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"    =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1146 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"      =   4 =  OK 
     *  1147 - assertIsTrue  "\"Abc\@def\"@iana.org"                                      =   1 =  OK 
     *  1148 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                  =   1 =  OK 
     *  1149 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                    =   1 =  OK 
     *  1150 - assertIsTrue  "\"Abc@def\"@iana.org"                                       =   1 =  OK 
     *  1151 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                 =   1 =  OK 
     *  1152 - assertIsTrue  "user+mailbox@iana.org"                                      =   0 =  OK 
     *  1153 - assertIsTrue  "$A12345@iana.org"                                           =   0 =  OK 
     *  1154 - assertIsTrue  "!def!xyz%abc@iana.org"                                      =   0 =  OK 
     *  1155 - assertIsTrue  "_somename@iana.org"                                         =   0 =  OK 
     *  1156 - assertIsTrue  "dclo@us.ibm.com"                                            =   0 =  OK 
     *  1157 - assertIsTrue  "peter.piper@iana.org"                                       =   0 =  OK 
     *  1158 - assertIsTrue  "test@iana.org"                                              =   0 =  OK 
     *  1159 - assertIsTrue  "TEST@iana.org"                                              =   0 =  OK 
     *  1160 - assertIsTrue  "1234567890@iana.org"                                        =   0 =  OK 
     *  1161 - assertIsTrue  "test+test@iana.org"                                         =   0 =  OK 
     *  1162 - assertIsTrue  "test-test@iana.org"                                         =   0 =  OK 
     *  1163 - assertIsTrue  "t*est@iana.org"                                             =   0 =  OK 
     *  1164 - assertIsTrue  "+1~1+@iana.org"                                             =   0 =  OK 
     *  1165 - assertIsTrue  "{_test_}@iana.org"                                          =   0 =  OK 
     *  1166 - assertIsTrue  "test.test@iana.org"                                         =   0 =  OK 
     *  1167 - assertIsTrue  "\"test.test\"@iana.org"                                     =   1 =  OK 
     *  1168 - assertIsTrue  "test.\"test\"@iana.org"                                     =   1 =  OK 
     *  1169 - assertIsTrue  "\"test@test\"@iana.org"                                     =   1 =  OK 
     *  1170 - assertIsTrue  "test@123.123.123.x123"                                      =   0 =  OK 
     *  1171 - assertIsFalse "test@123.123.123.123"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1172 - assertIsTrue  "test@[123.123.123.123]"                                     =   2 =  OK 
     *  1173 - assertIsTrue  "test@example.iana.org"                                      =   0 =  OK 
     *  1174 - assertIsTrue  "test@example.example.iana.org"                              =   0 =  OK 
     *  1175 - assertIsTrue  "customer/department@iana.org"                               =   0 =  OK 
     *  1176 - assertIsTrue  "_Yosemite.Sam@iana.org"                                     =   0 =  OK 
     *  1177 - assertIsTrue  "~@iana.org"                                                 =   0 =  OK 
     *  1178 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                 =   1 =  OK 
     *  1179 - assertIsTrue  "Ima.Fool@iana.org"                                          =   0 =  OK 
     *  1180 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                      =   1 =  OK 
     *  1181 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                    =   1 =  OK 
     *  1182 - assertIsTrue  "\"first\".\"last\"@iana.org"                                =   1 =  OK 
     *  1183 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                         =   1 =  OK 
     *  1184 - assertIsTrue  "\"first\".last@iana.org"                                    =   1 =  OK 
     *  1185 - assertIsTrue  "first.\"last\"@iana.org"                                    =   1 =  OK 
     *  1186 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                     =   1 =  OK 
     *  1187 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                         =   1 =  OK 
     *  1188 - assertIsTrue  "\"first.middle.last\"@iana.org"                             =   1 =  OK 
     *  1189 - assertIsTrue  "\"first..last\"@iana.org"                                   =   1 =  OK 
     *  1190 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                         =   1 =  OK 
     *  1191 - assertIsFalse "first.last @iana.orgin"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1192 - assertIsTrue  "\"test blah\"@iana.orgin"                                   =   1 =  OK 
     *  1193 - assertIsTrue  "name.lastname@domain.com"                                   =   0 =  OK 
     *  1194 - assertIsTrue  "a@bar.com"                                                  =   0 =  OK 
     *  1195 - assertIsTrue  "aaa@[123.123.123.123]"                                      =   2 =  OK 
     *  1196 - assertIsTrue  "a-b@bar.com"                                                =   0 =  OK 
     *  1197 - assertIsFalse "+@b.c"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1198 - assertIsTrue  "+@b.com"                                                    =   0 =  OK 
     *  1199 - assertIsTrue  "a@b.co-foo.uk"                                              =   0 =  OK 
     *  1200 - assertIsTrue  "\"hello my name is\"@stutter.comin"                         =   1 =  OK 
     *  1201 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                         =   1 =  OK 
     *  1202 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                           =   0 =  OK 
     *  1203 - assertIsFalse "foobar@192.168.0.1"                                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1204 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"          =   6 =  OK 
     *  1205 - assertIsTrue  "user%uucp!path@berkeley.edu"                                =   0 =  OK 
     *  1206 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                  =   0 =  OK 
     *  1207 - assertIsTrue  "test@test.com"                                              =   0 =  OK 
     *  1208 - assertIsTrue  "test@xn--example.com"                                       =   0 =  OK 
     *  1209 - assertIsTrue  "test@example.com"                                           =   0 =  OK 
     *  1210 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                   =   0 =  OK 
     *  1211 - assertIsTrue  "first\@last@iana.org"                                       =   0 =  OK 
     *  1212 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                   =   0 =  OK 
     *  1213 - assertIsFalse "first.last@example.123"                                     =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1214 - assertIsFalse "first.last@comin"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1215 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                  =   1 =  OK 
     *  1216 - assertIsTrue  "Abc\@def@iana.org"                                          =   0 =  OK 
     *  1217 - assertIsTrue  "Fred\ Bloggs@iana.org"                                      =   0 =  OK 
     *  1218 - assertIsFalse "Joe.\Blow@iana.org"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1219 - assertIsFalse "first.last@sub.do.com"                                      =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1220 - assertIsFalse "first.last"                                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1221 - assertIsTrue  "wild.wezyr@best-server-ever.com"                            =   0 =  OK 
     *  1222 - assertIsTrue  "\"hello world\"@example.com"                                =   1 =  OK 
     *  1223 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1224 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"               =   1 =  OK 
     *  1225 - assertIsTrue  "example+tag@gmail.com"                                      =   0 =  OK 
     *  1226 - assertIsFalse ".ann..other.@example.com"                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1227 - assertIsTrue  "ann.other@example.com"                                      =   0 =  OK 
     *  1228 - assertIsTrue  "something@something.something"                              =   0 =  OK 
     *  1229 - assertIsTrue  "c@(Chris's host.)public.examplein"                          =   6 =  OK 
     *  1230 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                        =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1231 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1232 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1233 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1234 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1235 - assertIsFalse "first().last@iana.orgin"                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1236 - assertIsFalse "pete(his account)@silly.test(his host)"                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1237 - assertIsFalse "jdoe@machine(comment). examplein"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1238 - assertIsFalse "first(abc.def).last@iana.orgin"                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1239 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1240 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                       = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1241 - assertIsFalse "first(abc\(def)@iana.orgin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1242 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *  1243 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1244 - assertIsFalse "1234 @ local(blah) .machine .examplein"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1245 - assertIsFalse "a@bin"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1246 - assertIsFalse "a@barin"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1247 - assertIsFalse "@about.museum"                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1248 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1249 - assertIsFalse ".first.last@iana.org"                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1250 - assertIsFalse "first.last.@iana.org"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1251 - assertIsFalse "first..last@iana.org"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1252 - assertIsFalse "\"first\"last\"@iana.org"                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1253 - assertIsFalse "first.last@"                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *  1254 - assertIsFalse "first.last@-xample.com"                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1255 - assertIsFalse "first.last@exampl-.com"                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1256 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1257 - assertIsFalse "abc\@iana.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1258 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1259 - assertIsFalse "abc@def@iana.org"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1260 - assertIsFalse "@iana.org"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1261 - assertIsFalse "doug@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1262 - assertIsFalse "\"qu@iana.org"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1263 - assertIsFalse "ote\"@iana.org"                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1264 - assertIsFalse ".dot@iana.org"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1265 - assertIsFalse "dot.@iana.org"                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1266 - assertIsFalse "two..dot@iana.org"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1267 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1268 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1269 - assertIsFalse "hello world@iana.org"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1270 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1271 - assertIsFalse "test.iana.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1272 - assertIsFalse "test.@iana.org"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1273 - assertIsFalse "test..test@iana.org"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1274 - assertIsFalse ".test@iana.org"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1275 - assertIsFalse "test@test@iana.org"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1276 - assertIsFalse "test@@iana.org"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1277 - assertIsFalse "-- test --@iana.org"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1278 - assertIsFalse "[test]@iana.org"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1279 - assertIsFalse "\"test\"test\"@iana.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1280 - assertIsFalse "()[]\;:.><@iana.org"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1281 - assertIsFalse "test@."                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1282 - assertIsFalse "test@example."                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1283 - assertIsFalse "test@.org"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1284 - assertIsFalse "test@[123.123.123.123"                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1285 - assertIsFalse "test@123.123.123.123]"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1286 - assertIsFalse "NotAnEmail"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1287 - assertIsFalse "@NotAnEmail"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1288 - assertIsFalse "\"test\"blah\"@iana.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1289 - assertIsFalse ".wooly@iana.org"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1290 - assertIsFalse "wo..oly@iana.org"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1291 - assertIsFalse "pootietang.@iana.org"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1292 - assertIsFalse ".@iana.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1293 - assertIsFalse "Ima Fool@iana.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1294 - assertIsFalse "foo@[\1.2.3.4]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1295 - assertIsFalse "first.\"\".last@iana.org"                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1296 - assertIsFalse "first\last@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1297 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1298 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *  1299 - assertIsFalse "cal(foo(bar)@iamcal.com"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1300 - assertIsFalse "cal(foo)bar)@iamcal.com"                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1301 - assertIsFalse "cal(foo\)@iamcal.com"                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1302 - assertIsFalse "first(middle)last@iana.org"                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1303 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1304 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1305 - assertIsFalse ".@"                                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1306 - assertIsFalse "@bar.com"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1307 - assertIsFalse "@@bar.com"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1308 - assertIsFalse "aaa.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1309 - assertIsFalse "aaa@.com"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1310 - assertIsFalse "aaa@.123"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1311 - assertIsFalse "aaa@[123.123.123.123]a"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *  1312 - assertIsFalse "aaa@[123.123.123.333]"                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1313 - assertIsFalse "a@bar.com."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *  1314 - assertIsFalse "a@-b.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1315 - assertIsFalse "a@b-.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1316 - assertIsFalse "-@..com"                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1317 - assertIsFalse "-@a..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1318 - assertIsFalse "@about.museum-"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1319 - assertIsFalse "test@...........com"                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1320 - assertIsFalse "first.last@[IPv6::]"                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1321 - assertIsFalse "first.last@[IPv6::::]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1322 - assertIsFalse "first.last@[IPv6::b4]"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1323 - assertIsFalse "first.last@[IPv6::::b4]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1324 - assertIsFalse "first.last@[IPv6::b3:b4]"                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1325 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1326 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1327 - assertIsFalse "first.last@[IPv6:a1:]"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1328 - assertIsFalse "first.last@[IPv6:a1:::]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1329 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1330 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1331 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1332 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1333 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1334 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1335 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1336 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1337 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"        =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1338 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1339 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1340 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1341 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                        =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  1342 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1343 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1344 - assertIsFalse "first.last@[IPv6::a2::b4]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1345 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1346 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1347 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1348 - assertIsFalse "first.last@[.12.34.56.78]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1349 - assertIsFalse "first.last@[12.34.56.789]"                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1350 - assertIsFalse "first.last@[::12.34.56.78]"                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1351 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                            =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1352 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                            =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1353 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1354 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1355 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]" =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1356 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1357 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1358 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1359 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1360 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1361 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1362 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1363 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1364 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1365 - assertIsTrue  "first.last@[IPv6:::]"                                       =   4 =  OK 
     *  1366 - assertIsTrue  "first.last@[IPv6:::b4]"                                     =   4 =  OK 
     *  1367 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                  =   4 =  OK 
     *  1368 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                   =   4 =  OK 
     *  1369 - assertIsTrue  "first.last@[IPv6:a1::]"                                     =   4 =  OK 
     *  1370 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                  =   4 =  OK 
     *  1371 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                    =   4 =  OK 
     *  1372 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                    =   4 =  OK 
     *  1373 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1374 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1375 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1376 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1377 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                          =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1378 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1379 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1380 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1381 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1382 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"       =   4 =  OK 
     * 
     * 
     * ---- https://www.rohannagar.com/jmail/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1383 - assertIsFalse "\"qu@test.org"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1384 - assertIsFalse "ote\"@test.org"                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1385 - assertIsFalse "\"().:;<>[\]@example.com"                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1386 - assertIsFalse "\"\"\"@iana.org"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *  1387 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1388 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1389 - assertIsFalse "a\"b(c)d.e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1390 - assertIsFalse "this is\"not\allowed@example.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1391 - assertIsFalse "this\ still\"not\allowed@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1392 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1393 - assertIsFalse "QA[icon]CHOCOLATE[icon]@test.com"                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1394 - assertIsFalse "QA\[icon\]CHOCOLATE\[icon\]@test.com"                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1395 - assertIsFalse "plainaddress"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1396 - assertIsFalse "@example.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1397 - assertIsFalse ".email@example.com"                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1398 - assertIsFalse "email.@example.com"                                         =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1399 - assertIsFalse "email..email@example.com"                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1400 - assertIsFalse "email@-example.com"                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1401 - assertIsFalse "email@111.222.333.44444"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *  1402 - assertIsFalse "this\ is\"really\"not\allowed@example.com"                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1403 - assertIsFalse "email@[12.34.44.56"                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *  1404 - assertIsFalse "email@14.44.56.34]"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1405 - assertIsFalse "email@[1.1.23.5f]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1406 - assertIsFalse "email@[3.256.255.23]"                                       =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1407 - assertIsFalse "\"first\\"last\"@test.org"                                  =   1 =  #### FEHLER ####    eMail-Adresse korrekt (Local Part mit String)
     *  1408 - assertIsFalse "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1409 - assertIsFalse "first\@last@iana.org"                                       =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1410 - assertIsFalse "test@example.com "                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1411 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"       =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1412 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33. 44]"               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1413 - assertIsFalse "invalid@about.museum-"                                      =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1414 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.test.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1415 - assertIsFalse "abc@def@test.org"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1416 - assertIsFalse "abc\@def@test.org"                                          =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *  1417 - assertIsFalse "abc\@test.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1418 - assertIsFalse "@test.org"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1419 - assertIsFalse ".dot@test.org"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1420 - assertIsFalse "dot.@test.org"                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1421 - assertIsFalse "two..dot@test.org"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1422 - assertIsFalse "\"Doug \"Ace\" L.\"@test.org"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1423 - assertIsFalse "Doug\ \"Ace\"\ L\.@test.org"                                =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1424 - assertIsFalse "hello world@test.org"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1425 - assertIsFalse "first(12345678901234567890123456789012345678901234567890)last@(1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890)test.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1426 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@test.org"                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1427 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@test.org"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1428 - assertIsFalse "test.test.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1429 - assertIsFalse "test.@test.org"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1430 - assertIsFalse "test..test@test.org"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1431 - assertIsFalse ".test@test.org"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1432 - assertIsFalse "test@test@test.org"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1433 - assertIsFalse "test@@test.org"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1434 - assertIsFalse "-- test --@test.org"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1435 - assertIsFalse "[test]@test.org"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1436 - assertIsFalse "\"test\"test\"@test.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1437 - assertIsFalse "()[]\;:.><@test.org"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *  1438 - assertIsFalse "test@123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1439 - assertIsFalse ".@test.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1440 - assertIsFalse "Ima Fool@test.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1441 - assertIsFalse "\"first\\"last\"@test.org"                                  =   1 =  #### FEHLER ####    eMail-Adresse korrekt (Local Part mit String)
     *  1442 - assertIsFalse "foo@[.2.3.4]"                                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1443 - assertIsFalse "first\last@test.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1444 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)exa mple(abc(\"def\".ghi).mno).(abc(\"def\".ghi).mno)com(abc(\"def\".ghi).mno)" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1445 - assertIsFalse "first(middle)last@test.org"                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1446 - assertIsFalse "\"test\"test@test.com"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1447 - assertIsFalse "()@test.com"                                                =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *  1448 - assertIsFalse "test@really.long.topleveldomainisnotallowedunfortunatelyforpeoplewholikereallylongtopleveldomainnames" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *  1449 - assertIsFalse "test@really.long.domainpartisnotallowedunfortunatelyforpeoplewholikereallylongdomainnameparts.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *  1450 - assertIsFalse "invalid@[1]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *  1451 - assertIsFalse "ä📧@-foo"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1452 - assertIsFalse "ä📧@foo-"                                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1453 - assertIsFalse "first(comment(inner@comment.com"                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1454 - assertIsFalse "Joe A Smith <email@example.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1455 - assertIsFalse "Joe A Smith email@example.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1456 - assertIsFalse "Joe A Smith <email@example.com->"                           =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *  1457 - assertIsFalse "Joe A Smith <email@-example.com->"                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1458 - assertIsFalse "Joe A Smith <email>"                                        =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1459 - assertIsTrue  "\"email\"@example.com"                                      =   1 =  OK 
     *  1460 - assertIsTrue  "\"first@last\"@test.org"                                    =   1 =  OK 
     *  1461 - assertIsTrue  "very.unusual.\"@\".unusual.com@example.com"                 =   1 =  OK 
     *  1462 - assertIsTrue  "\"first\"last\"@test.org"                                   =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1463 - assertIsTrue  "much.\"more\ unusual\"@example.com"                         =   1 =  OK 
     *  1464 - assertIsTrue  "\"first\last\"@test.org"                                    =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1465 - assertIsTrue  "\"Abc\@def\"@test.org"                                      =   1 =  OK 
     *  1466 - assertIsTrue  "\"Fred\ Bloggs\"@test.org"                                  =   1 =  OK 
     *  1467 - assertIsTrue  "\"Joe.\Blow\"@test.org"                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1468 - assertIsTrue  "\"Abc@def\"@test.org"                                       =   1 =  OK 
     *  1469 - assertIsTrue  "\"Fred Bloggs\"@test.org"                                   =   1 =  OK 
     *  1470 - assertIsTrue  "\"Doug \"Ace\" L.\"@test.org"                               =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1471 - assertIsTrue  "\"[[ test ]]\"@test.org"                                    =   1 =  OK 
     *  1472 - assertIsTrue  "\"test.test\"@test.org"                                     =   1 =  OK 
     *  1473 - assertIsTrue  "test.\"test\"@test.org"                                     =   1 =  OK 
     *  1474 - assertIsTrue  "\"test@test\"@test.org"                                     =   1 =  OK 
     *  1475 - assertIsTrue  "\"test  est\"@test.org"                                      =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  1476 - assertIsTrue  "\"first\".\"last\"@test.org"                                =   1 =  OK 
     *  1477 - assertIsTrue  "\"first\".middle.\"last\"@test.org"                         =   1 =  OK 
     *  1478 - assertIsTrue  "\"first\".last@test.org"                                    =   1 =  OK 
     *  1479 - assertIsTrue  "first.\"last\"@test.org"                                    =   1 =  OK 
     *  1480 - assertIsTrue  "\"first\".\"middle\".\"last\"@test.org"                     =   1 =  OK 
     *  1481 - assertIsTrue  "\"first.middle\".\"last\"@test.org"                         =   1 =  OK 
     *  1482 - assertIsTrue  "\"first.middle.last\"@test.org"                             =   1 =  OK 
     *  1483 - assertIsTrue  "\"first..last\"@test.org"                                   =   1 =  OK 
     *  1484 - assertIsTrue  "\"Unicode NULL \"@char.com"                                 =   1 =  OK 
     *  1485 - assertIsTrue  "\"test\blah\"@test.org"                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1486 - assertIsTrue  "\"testlah\"@test.org"                                      =  89 =  #### FEHLER ####    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *  1487 - assertIsTrue  "\"test\"blah\"@test.org"                                    =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1488 - assertIsTrue  "\"first\\"last\"@test.org"                                  =   1 =  OK 
     *  1489 - assertIsTrue  "\"Test \"Fail\" Ing\"@test.org"                             =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1490 - assertIsTrue  "\"test blah\"@test.org"                                     =   1 =  OK 
     *  1491 - assertIsTrue  "first.last@test.org"                                        =   0 =  OK 
     *  1492 - assertIsTrue  "jdoe@machine(comment).example"                              = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  1493 - assertIsTrue  "first.\"\".last@test.org"                                   =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  1494 - assertIsTrue  "\"\"@test.org"                                              =  85 =  #### FEHLER ####    String: Leerstring in Anfuehrungszeichen
     *  1495 - assertIsTrue  "very.common@example.org"                                    =   0 =  OK 
     *  1496 - assertIsTrue  "test/test@test.com"                                         =   0 =  OK 
     *  1497 - assertIsTrue  "user-@example.org"                                          =   0 =  OK 
     *  1498 - assertIsTrue  "firstname.lastname@example.com"                             =   0 =  OK 
     *  1499 - assertIsTrue  "email@subdomain.example.com"                                =   0 =  OK 
     *  1500 - assertIsTrue  "firstname+lastname@example.com"                             =   0 =  OK 
     *  1501 - assertIsTrue  "1234567890@example.com"                                     =   0 =  OK 
     *  1502 - assertIsTrue  "email@example-one.com"                                      =   0 =  OK 
     *  1503 - assertIsTrue  "_______@example.com"                                        =   0 =  OK 
     *  1504 - assertIsTrue  "email@example.name"                                         =   0 =  OK 
     *  1505 - assertIsTrue  "email@example.museum"                                       =   0 =  OK 
     *  1506 - assertIsTrue  "email@example.co.jp"                                        =   0 =  OK 
     *  1507 - assertIsTrue  "firstname-lastname@example.com"                             =   0 =  OK 
     *  1508 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x2" =   0 =  OK 
     *  1509 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@12345678901234567890123456789012345678901234567890123456789.12345678901234567890123456789012345678901234567890123456789.123456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  1510 - assertIsTrue  "first.last@123.test.org"                                    =   0 =  OK 
     *  1511 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.test.org" =   0 =  OK 
     *  1512 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@test.org" =   0 =  OK 
     *  1513 - assertIsTrue  "user+mailbox@test.org"                                      =   0 =  OK 
     *  1514 - assertIsTrue  "customer/department=shipping@test.org"                      =   0 =  OK 
     *  1515 - assertIsTrue  "$A12345@test.org"                                           =   0 =  OK 
     *  1516 - assertIsTrue  "!def!xyz%abc@test.org"                                      =   0 =  OK 
     *  1517 - assertIsTrue  "_somename@test.org"                                         =   0 =  OK 
     *  1518 - assertIsTrue  "first.last@[IPv6:::12.34.56.78]"                            =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1519 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1520 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1521 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"    =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1522 - assertIsTrue  "+@b.c"                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  1523 - assertIsTrue  "TEST@test.org"                                              =   0 =  OK 
     *  1524 - assertIsTrue  "1234567890@test.org"                                        =   0 =  OK 
     *  1525 - assertIsTrue  "test-test@test.org"                                         =   0 =  OK 
     *  1526 - assertIsTrue  "t*est@test.org"                                             =   0 =  OK 
     *  1527 - assertIsTrue  "+1~1+@test.org"                                             =   0 =  OK 
     *  1528 - assertIsTrue  "{_test_}@test.org"                                          =   0 =  OK 
     *  1529 - assertIsTrue  "valid@about.museum"                                         =   0 =  OK 
     *  1530 - assertIsTrue  "a@bar"                                                      =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *  1531 - assertIsTrue  "cal(foo\@bar)@iamcal.com"                                   =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1532 - assertIsTrue  "(comment)test@test.org"                                     =   6 =  OK 
     *  1533 - assertIsTrue  "(foo)cal(bar)@(baz)iamcal.com(quux)"                        =  99 =  #### FEHLER ####    Kommentar: kein zweiter Kommentar gueltig
     *  1534 - assertIsTrue  "cal(foo\)bar)@iamcal.com"                                   =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1535 - assertIsTrue  "cal(woo(yay)hoopla)@iamcal.com"                             =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1536 - assertIsTrue  "first(Welcome to the (\"wonderf ul\" (!)) world of email)@test.org" =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1537 - assertIsTrue  "pete(his account)@silly.test(his host)"                     =  99 =  #### FEHLER ####    Kommentar: kein zweiter Kommentar gueltig
     *  1538 - assertIsTrue  "first(abc\(def)@test.org"                                   =  91 =  #### FEHLER ####    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *  1539 - assertIsTrue  "a(a(b(c)d(e(f))g)h(i)j)@test.org"                           =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1540 - assertIsTrue  "c@(Chris's host.)public.example"                            =   6 =  OK 
     *  1541 - assertIsTrue  "_Yosemite.Sam@test.org"                                     =   0 =  OK 
     *  1542 - assertIsTrue  "~@test.org"                                                 =   0 =  OK 
     *  1543 - assertIsTrue  "Iinsist@(that comments are allowed)this.is.ok"              =   6 =  OK 
     *  1544 - assertIsTrue  "test@Bücher.ch"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1545 - assertIsTrue  "あいうえお@example.com"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1546 - assertIsTrue  "Pelé@example.com"                                           =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1547 - assertIsTrue  "δοκιμή@παράδειγμα.δοκιμή"                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1548 - assertIsTrue  "我買@屋企.香港"                                                   =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1549 - assertIsTrue  "二ノ宮@黒川.日本"                                                  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1550 - assertIsTrue  "медведь@с-балалайкой.рф"                                    =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1551 - assertIsTrue  "संपर्क@डाटामेल.भारत"                                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1552 - assertIsTrue  "email@example.com (Joe Smith)"                              =   6 =  OK 
     *  1553 - assertIsTrue  "cal@iamcal(woo).(yay)com"                                   = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  1554 - assertIsTrue  "first(abc.def).last@test.org"                               = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  1555 - assertIsTrue  "first(a\"bc.def).last@test.org"                             =  92 =  #### FEHLER ####    Kommentar: Ungueltiges Zeichen im Kommentar
     *  1556 - assertIsTrue  "first.(\")middle.last(\")@test.org"                         = 101 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *  1557 - assertIsTrue  "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).com" = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  1558 - assertIsTrue  "first().last@test.org"                                      = 103 =  #### FEHLER ####    Kommentar: Falsche Zeichenkombination ")."
     *  1559 - assertIsTrue  "mymail\@hello@hotmail.com"                                  =   0 =  OK 
     *  1560 - assertIsTrue  "Abc\@def@test.org"                                          =   0 =  OK 
     *  1561 - assertIsTrue  "Fred\ Bloggs@test.org"                                      =   0 =  OK 
     *  1562 - assertIsTrue  "Joe.\\Blow@test.org"                                        =   0 =  OK 
     * 
     * 
     * ---- https://www.linuxjournal.com/article/9585 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1563 - assertIsTrue  "dclo@us.ibm.com"                                            =   0 =  OK 
     *  1564 - assertIsTrue  "abc\@def@example.com"                                       =   0 =  OK 
     *  1565 - assertIsTrue  "abc\\@example.com"                                          =   0 =  OK 
     *  1566 - assertIsTrue  "Fred\ Bloggs@example.com"                                   =   0 =  OK 
     *  1567 - assertIsTrue  "Joe.\\Blow@example.com"                                     =   0 =  OK 
     *  1568 - assertIsTrue  "\"Abc@def\"@example.com"                                    =   1 =  OK 
     *  1569 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                =   1 =  OK 
     *  1570 - assertIsTrue  "customer/department=shipping@example.com"                   =   0 =  OK 
     *  1571 - assertIsTrue  "$A12345@example.com"                                        =   0 =  OK 
     *  1572 - assertIsTrue  "!def!xyz%abc@example.com"                                   =   0 =  OK 
     *  1573 - assertIsTrue  "_somename@example.com"                                      =   0 =  OK 
     *  1574 - assertIsTrue  "user+mailbox@example.com"                                   =   0 =  OK 
     *  1575 - assertIsTrue  "peter.piper@example.com"                                    =   0 =  OK 
     *  1576 - assertIsTrue  "Doug\ \\"Ace\\"\ Lovell@example.com"                        =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *  1577 - assertIsTrue  "\"Doug \\"Ace\\" L.\"@example.com"                          =   1 =  OK 
     *  1578 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                   =   0 =  OK 
     *  1579 - assertIsFalse "abc@def@example.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1580 - assertIsFalse "abc\\@def@example.com"                                      =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1581 - assertIsFalse "abc\@example.com"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1582 - assertIsFalse "@example.com"                                               =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1583 - assertIsFalse "doug@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *  1584 - assertIsFalse "\"qu@example.com"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *  1585 - assertIsFalse "ote\"@example.com"                                          =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1586 - assertIsFalse ".dot@example.com"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1587 - assertIsFalse "dot.@example.com"                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1588 - assertIsFalse "two..dot@example.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1589 - assertIsFalse "\"Doug \"Ace\" L.\"@example.com"                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *  1590 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@example.com"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1591 - assertIsFalse "hello world@example.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1592 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     * 
     * 
     * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1593 - assertIsTrue  "jkt@gmail.com"                                              =   0 =  OK 
     *  1594 - assertIsFalse " jkt@gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1595 - assertIsFalse "jkt@ gmail.com"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1596 - assertIsFalse "jkt@g mail.com"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *  1597 - assertIsFalse "jkt @gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1598 - assertIsFalse "j kt@gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * 
     * ---- https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1599 - assertIsTrue  "jinujawad6s@gmail.com"                                      =   0 =  OK 
     *  1600 - assertIsTrue  "drp@drp.cz"                                                 =   0 =  OK 
     *  1601 - assertIsTrue  "tvf@tvf.cz"                                                 =   0 =  OK 
     *  1602 - assertIsTrue  "info@ermaelan.com"                                          =   0 =  OK 
     *  1603 - assertIsTrue  "begeddov@jfinity.com"                                       =   0 =  OK 
     *  1604 - assertIsTrue  "vdv@dyomedea.com"                                           =   0 =  OK 
     *  1605 - assertIsTrue  "me@aaronsw.com"                                             =   0 =  OK 
     *  1606 - assertIsTrue  "aaron@theinfo.org"                                          =   0 =  OK 
     *  1607 - assertIsTrue  "rss-dev@yahoogroups.com"                                    =   0 =  OK 
     * 
     * 
     * ---- https://www.journaldev.com/638/java-email-validation-regex ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1608 - assertIsTrue  "journaldev@yahoo.com"                                       =   0 =  OK 
     *  1609 - assertIsTrue  "journaldev-100@yahoo.com"                                   =   0 =  OK 
     *  1610 - assertIsTrue  "journaldev.100@yahoo.com"                                   =   0 =  OK 
     *  1611 - assertIsTrue  "journaldev111@journaldev.com"                               =   0 =  OK 
     *  1612 - assertIsTrue  "journaldev-100@journaldev.net"                              =   0 =  OK 
     *  1613 - assertIsTrue  "journaldev.100@journaldev.com.au"                           =   0 =  OK 
     *  1614 - assertIsTrue  "journaldev@1.com"                                           =   0 =  OK 
     *  1615 - assertIsTrue  "journaldev@gmail.com.com"                                   =   0 =  OK 
     *  1616 - assertIsTrue  "journaldev+100@gmail.com"                                   =   0 =  OK 
     *  1617 - assertIsTrue  "journaldev-100@yahoo-test.com"                              =   0 =  OK 
     *  1618 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                          =   0 =  OK 
     *  1619 - assertIsFalse "journaldev"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1620 - assertIsFalse "journaldev@.com.my"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1621 - assertIsFalse "journaldev123@gmail.a"                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1622 - assertIsFalse "journaldev123@.com"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1623 - assertIsFalse "journaldev123@.com.com"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1624 - assertIsFalse ".journaldev@journaldev.com"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *  1625 - assertIsFalse "journaldev()*@gmail.com"                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *  1626 - assertIsFalse "journaldev@%*.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *  1627 - assertIsFalse "journaldev..2002@gmail.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1628 - assertIsFalse "journaldev.@gmail.com"                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *  1629 - assertIsFalse "journaldev@journaldev@gmail.com"                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1630 - assertIsFalse "journaldev@gmail.com.1a"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1631 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1632 - assertIsTrue  "Ärger.Öde@Übel.de"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1633 - assertIsTrue  "Smørrebrød@danmark.dk"                                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1634 - assertIsTrue  "ip.without.brackets@1.2.3.4"                                =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1635 - assertIsTrue  "ip.without.brackets@1:2:3:4:5:6:7:8"                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1636 - assertIsTrue  "(space after comment) john.smith@example.com"               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1637 - assertIsTrue  "email.address.without@topleveldomain"                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1638 - assertIsTrue  "EmailAddressWithout@PointSeperator"                         =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1639 - assertIsTrue  "valid.email.from.nr622@fillup.tofalse.com"                  =   0 =  OK 
     *           ...
     *  1640 - assertIsTrue  "valid.email.to.nr1017@fillup.tofalse.com"                   =   0 =  OK 
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE  1017   KORREKT  935 =   91.937 % | FALSCH ERKANNT   82 =    8.063 % = Error 0
     *   ASSERT_IS_FALSE 1017   KORREKT 1002 =   98.525 % | FALSCH ERKANNT   15 =    1.475 % = Error 0
     * 
     *   GESAMT          2034   KORREKT 1937 =   95.231 % | FALSCH ERKANNT   97 =    4.769 % = Error 0
     * 
     * 
     *   Millisekunden     93 = 0.045722713864306784
     *  
     */

    /*
     * Variable fuer die Startzeit der Funktion deklarieren 
     * und die aktuellen System-Millisekunden speichern 
     */
    long time_stamp_start = System.currentTimeMillis();

    try
    {
      wlHeadline( "General Correct" );

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

      assertIsFalse( "DomainHyphen@-atstart" );
      assertIsFalse( "DomainHyphen@atend-.com" );
      assertIsFalse( "DomainHyphen@bb.-cc" );
      assertIsFalse( "DomainHyphen@bb.-cc-" );
      assertIsFalse( "DomainHyphen@bb.cc-" );
      assertIsFalse( "DomainHyphen@bb.c-c" ); // https://tools.ietf.org/id/draft-liman-tld-names-01.html
      assertIsFalse( "DomainNotAllowedCharacter@/atstart" );
      assertIsFalse( "DomainNotAllowedCharacter@a.start" );
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

      assertIsTrue( "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com" );
      assertIsFalse( "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" );
      assertIsFalse( "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" );
      assertIsFalse( "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" );

      assertIsTrue( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
      assertIsFalse( "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );

      assertIsTrue( "email@domain.topleveldomain" );

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
      wlHeadline( "Testclass EmailValidator4J" );
      wl( "" );

      /*
       * https://github.com/egulias/EmailValidator4J 
       */
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
      assertIsFalse( "\"\"@[]" );/* kind of an edge case. valid for RFC 5322 but address literal is not for 5321 */
      assertIsTrue( String.format( "\"\\%s\"@iana.org", "\"" ) );
      assertIsTrue( "example@localhost" );

      wl( "" );
      wlHeadline( "unsorted from the WEB" );
      wl( "" );

      /*
       * Various examples. Scraped from the Internet-Forums.
       * 
       * https://stackoverflow.com/questions/2049502/what-characters-are-allowed-in-an-email-address
       * https://stackoverflow.com/questions/25471114/how-to-validate-an-e-mail-address-in-swift?noredirect=1&lq=1
       * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
       * https://stackoverflow.com/questions/6850894/regex-split-email-address?noredirect=1&lq=1
       * https://stackoverflow.com/questions/46155/how-to-validate-an-email-address-in-javascript?page=3&tab=votes#tab-top
       * https://docs.microsoft.com/en-us/dotnet/api/system.net.mail.mailaddress.address?redirectedfrom=MSDN&view=netframework-4.8#System_Net_Mail_MailAddress_Address
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
      assertIsFalse( "a\"b(c)d.e:f;g<h>i[j\\k]l@example.com" ); // Certain characters cannot be in the local-part unquoted
      assertIsFalse( "this is\"not\\allowed@example.com" ); // Whitespace should be quoted or dot-separated
      assertIsFalse( "this\\ still\"not\\allowed@example.com" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" ); // The local-part cannot have more than 64 characters
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
     * Variable fuer die Endzeit der Funktion deklarieren 
     * und die aktuellen System-Millisekunden speichern 
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

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress ? " OK " : " #### FEHLER #### " ) );
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
        knz_is_valid_email_adress = TestClassSpeed.isEmailValid( TEST_B_TEST_NR, pString );

        //knz_is_valid_email_adress = TestTemp.isEmailValid( pString );

        //knz_is_valid_email_adress = JMail.isValid( pString );

        if ( KNZ_LOG_AUSGABE )
        {
          wl( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( FkString.getJavaString( pString ), BREITE_SPALTE_EMAIL_AUSGABE ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( knz_is_valid_email_adress == knz_soll_wert ? " OK " : " #### FEHLER #### " ) );
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
    generateTest( "~", "tilde" );
    generateTest( "^", "xor" );
    generateTest( ":", "colon" );
    generateTest( " ", "space" );
  }

  private static void generateTest( String pCharacter, String pName )
  {
    System.out.println( "\n      assertIsFalse( \"domain.part@with" + pCharacter + pName + ".com\" );" );
    System.out.println( "      assertIsFalse( \"domain.part@" + pCharacter + "with." + pName + ".at.domain.start.com\" );" );
    System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end1" + pCharacter + ".com\" );" );
    System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".at.domain.end2.com" + pCharacter + "\" );" );
    System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".before" + pCharacter + ".point.com\" );" );
    System.out.println( "      assertIsFalse( \"domain.part@with." + pName + ".after." + pCharacter + "point.com\" );" );
    
    //  System.out.println( "\n      assertIsFalse( \"ip.v4.with." + pName + "@[123.14" + pCharacter + "5.178.90]\" );" );
    //  System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145" + pCharacter + ".178.90]\" );" );
    //  System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145." + pCharacter + "178.90]\" );" );
    //  System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[123.145.178.90" + pCharacter + "]\" );" );
    //  System.out.println( "      assertIsFalse( \"ip.v4.with." + pName + "@[" + pCharacter + "123.145.178.90]\" );" );
    
    //  System.out.println( "\n      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:2" + pCharacter + "2:3:4:5:6:7]\" );" );
    //  System.out.println( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22" + pCharacter + ":3:4:5:6:7]\" );" );
    //  System.out.println( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:" + pCharacter + "3:4:5:6:7]\" );" );
    //  System.out.println( "      assertIsFalse( \"ip.v6.with." + pName + "@[IPv6:1:22:3:4:5:6:7" + pCharacter + "]\" );" );
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
}
