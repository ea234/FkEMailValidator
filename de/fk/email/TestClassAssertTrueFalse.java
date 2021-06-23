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

  public static void main( String[] args )
  {
    /*
     * NR      Fkt.          Input                                                Result
     * 
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
     *    61 - assertIsFalse "@%^%#$@#$@#.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    62 - assertIsFalse "@domain.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    63 - assertIsFalse "email.domain.com"                                           =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    64 - assertIsFalse "email@domain@domain.com"                                    =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    65 - assertIsFalse "@@@@@@gmail.com"                                            =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    66 - assertIsFalse "@test@a.com"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    67 - assertIsFalse "@\"someStringThatMightBe@email.com"                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    68 - assertIsFalse "test@@test.com"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    69 - assertIsFalse "ABCDEF@GHIJKL"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    70 - assertIsFalse "ABC.DEF@GHIJKL"                                             =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    71 - assertIsFalse ".ABC.DEF@GHI.JKL"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    72 - assertIsFalse "ABC.DEF@GHI.JKL."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    73 - assertIsFalse "ABC..DEF@GHI.JKL"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    74 - assertIsFalse "ABC.DEF@GHI..JKL"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    75 - assertIsFalse "ABC.DEF@GHI.JKL.."                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    76 - assertIsFalse "ABC.DEF.@GHI.JKL"                                           =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    77 - assertIsFalse "ABC.DEF@.GHI.JKL"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    78 - assertIsFalse "ABC.DEF@."                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    79 - assertIsFalse "john..doe@example.com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    80 - assertIsFalse "john.doe@example..com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    81 - assertIsTrue  "\"john..doe\"@example.com"                                  =   1 =  OK 
     *    82 - assertIsFalse "..........@domain."                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    83 - assertIsFalse "test.@test.com"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    84 - assertIsFalse ".test.@test.com"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    85 - assertIsFalse "asdf@asdf@asdf.com"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    86 - assertIsFalse "email@provider..com"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    87 - assertIsTrue  "ABC1.DEF2@GHI3.JKL4"                                        =   0 =  OK 
     *    88 - assertIsTrue  "ABC.DEF_@GHI.JKL"                                           =   0 =  OK 
     *    89 - assertIsTrue  "#ABC.DEF@GHI.JKL"                                           =   0 =  OK 
     *    90 - assertIsTrue  "ABC.DEF@GHI.JK2"                                            =   0 =  OK 
     *    91 - assertIsTrue  "ABC.DEF@2HI.JKL"                                            =   0 =  OK 
     *    92 - assertIsFalse "ABC.DEF@GHI.2KL"                                            =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    93 - assertIsFalse "ABC.DEF@GHI.JK-"                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    94 - assertIsFalse "ABC.DEF@GHI.JK_"                                            =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    95 - assertIsFalse "ABC.DEF@-HI.JKL"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    96 - assertIsFalse "ABC.DEF@_HI.JKL"                                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    97 - assertIsFalse "ABC DEF@GHI.DE"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    98 - assertIsFalse "ABC.DEF@GHI DE"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *    99 - assertIsFalse "A . B & C . D"                                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   100 - assertIsFalse " A . B & C . D"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   101 - assertIsFalse "(?).[!]@{&}.<:>"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   102 - assertIsTrue  "&local&&name&with&$@amp.com"                                =   0 =  OK 
     *   103 - assertIsTrue  "*local**name*with*@asterisk.com"                            =   0 =  OK 
     *   104 - assertIsTrue  "$local$$name$with$@dollar.com"                              =   0 =  OK 
     *   105 - assertIsTrue  "=local==name=with=@equality.com"                            =   0 =  OK 
     *   106 - assertIsTrue  "!local!!name!with!@exclamation.com"                         =   0 =  OK 
     *   107 - assertIsTrue  "`local``name`with`@grave-accent.com"                        =   0 =  OK 
     *   108 - assertIsTrue  "#local##name#with#@hash.com"                                =   0 =  OK 
     *   109 - assertIsTrue  "-local--name-with-@hypen.com"                               =   0 =  OK 
     *   110 - assertIsTrue  "{local{name{{with{@leftbracket.com"                         =   0 =  OK 
     *   111 - assertIsTrue  "%local%%name%with%@percentage.com"                          =   0 =  OK 
     *   112 - assertIsTrue  "|local||name|with|@pipe.com"                                =   0 =  OK 
     *   113 - assertIsTrue  "+local++name+with+@plus.com"                                =   0 =  OK 
     *   114 - assertIsTrue  "?local??name?with?@question.com"                            =   0 =  OK 
     *   115 - assertIsTrue  "}local}name}}with}@rightbracket.com"                        =   0 =  OK 
     *   116 - assertIsTrue  "~local~~name~with~@tilde.com"                               =   0 =  OK 
     *   117 - assertIsTrue  "^local^^name^with^@xor.com"                                 =   0 =  OK 
     *   118 - assertIsTrue  "_local__name_with_@underscore.com"                          =   0 =  OK 
     *   119 - assertIsFalse ":local::name:with:@colon.com"                               =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   120 - assertIsTrue  "domain.part@with-hyphen.com"                                =   0 =  OK 
     *   121 - assertIsTrue  "domain.part@with_underscore.com"                            =   0 =  OK 
     *   122 - assertIsFalse "domain.part@-starts.with.hyphen.com"                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   123 - assertIsFalse "domain.part@ends.with.hyphen.com-"                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   124 - assertIsFalse "domain.part@with&amp.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   125 - assertIsFalse "domain.part@with*asterisk.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   126 - assertIsFalse "domain.part@with$dollar.com"                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   127 - assertIsFalse "domain.part@with=equality.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   128 - assertIsFalse "domain.part@with!exclamation.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   129 - assertIsFalse "domain.part@with?question.com"                              =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   130 - assertIsFalse "domain.part@with`grave-accent.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   131 - assertIsFalse "domain.part@with#hash.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   132 - assertIsFalse "domain.part@with%percentage.com"                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   133 - assertIsFalse "domain.part@with|pipe.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   134 - assertIsFalse "domain.part@with+plus.com"                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   135 - assertIsFalse "domain.part@with{leftbracket.com"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   136 - assertIsFalse "domain.part@with}rightbracket.com"                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   137 - assertIsFalse "domain.part@with(leftbracket.com"                           =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   138 - assertIsFalse "domain.part@with)rightbracket.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   139 - assertIsFalse "domain.part@with[leftbracket.com"                           =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   140 - assertIsFalse "domain.part@with]rightbracket.com"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   141 - assertIsFalse "domain.part@with~tilde.com"                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   142 - assertIsFalse "domain.part@with^xor.com"                                   =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   143 - assertIsFalse "domain.part@with:colon.com"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   144 - assertIsFalse "DomainHyphen@-atstart"                                      =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   145 - assertIsFalse "DomainHyphen@atend-.com"                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   146 - assertIsFalse "DomainHyphen@bb.-cc"                                        =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   147 - assertIsFalse "DomainHyphen@bb.-cc-"                                       =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   148 - assertIsFalse "DomainHyphen@bb.cc-"                                        =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   149 - assertIsFalse "DomainHyphen@bb.c-c"                                        =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   150 - assertIsFalse "DomainNotAllowedCharacter@/atstart"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   151 - assertIsFalse "DomainNotAllowedCharacter@a,start"                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   152 - assertIsFalse "DomainNotAllowedCharacter@atst\art.com"                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   153 - assertIsFalse "DomainNotAllowedCharacter@exa\mple"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   154 - assertIsFalse "DomainNotAllowedCharacter@example'"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   155 - assertIsFalse "DomainNotAllowedCharacter@100%.de'"                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   156 - assertIsTrue  "domain.starts.with.digit@2domain.com"                       =   0 =  OK 
     *   157 - assertIsTrue  "domain.ends.with.digit@domain2.com"                         =   0 =  OK 
     *   158 - assertIsFalse "tld.starts.with.digit@domain.2com"                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   159 - assertIsTrue  "tld.ends.with.digit@domain.com2"                            =   0 =  OK 
     *   160 - assertIsFalse "email@=qowaiv.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   161 - assertIsFalse "email@plus+.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   162 - assertIsFalse "email@domain.com>"                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   163 - assertIsFalse "email@mailto:domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   164 - assertIsFalse "mailto:mailto:email@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   165 - assertIsFalse "email@-domain.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   166 - assertIsFalse "email@domain-.com"                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   167 - assertIsFalse "email@domain.com-"                                          =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   168 - assertIsFalse "email@{leftbracket.com"                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   169 - assertIsFalse "email@rightbracket}.com"                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   170 - assertIsFalse "email@pp|e.com"                                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   171 - assertIsTrue  "email@domain.domain.domain.com.com"                         =   0 =  OK 
     *   172 - assertIsTrue  "email@domain.domain.domain.domain.com.com"                  =   0 =  OK 
     *   173 - assertIsTrue  "email@domain.domain.domain.domain.domain.com.com"           =   0 =  OK 
     *   174 - assertIsFalse "unescaped white space@fake$com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   175 - assertIsFalse "\"Joe Smith email@domain.com"                               =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   176 - assertIsFalse "\"Joe Smith' email@domain.com"                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   177 - assertIsFalse "\"Joe Smith\"email@domain.com"                              =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   178 - assertIsFalse "Joe Smith &lt;email@domain.com&gt;"                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   179 - assertIsTrue  "{john'doe}@my.server"                                       =   0 =  OK 
     *   180 - assertIsTrue  "email@domain-one.com"                                       =   0 =  OK 
     *   181 - assertIsTrue  "_______@domain.com"                                         =   0 =  OK 
     *   182 - assertIsTrue  "?????@domain.com"                                           =   0 =  OK 
     *   183 - assertIsFalse "local@?????.com"                                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   184 - assertIsTrue  "\"B3V3RLY H1LL$\"@example.com"                              =   1 =  OK 
     *   185 - assertIsTrue  "\"-- --- .. -.\"@sh.de"                                     =   1 =  OK 
     *   186 - assertIsTrue  "{{-^-}{-=-}{-^-}}@GHI.JKL"                                  =   0 =  OK 
     *   187 - assertIsTrue  "\"\\" + \\"select * from user\\" + \\"\"@example.de"        =   1 =  OK 
     *   188 - assertIsTrue  "#!$%&'*+-/=?^_`{}|~@eksample.org"                           =   0 =  OK 
     *   189 - assertIsFalse "eksample@#!$%&'*+-/=?^_`{}|~.org"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   190 - assertIsFalse "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}"            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   191 - assertIsFalse "\"\"@[]"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   192 - assertIsFalse "\"\"@[1"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   193 - assertIsFalse "ABC.DEF@[]"                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   194 - assertIsTrue  "\"    \"@[1.2.3.4]"                                         =   3 =  OK 
     *   195 - assertIsTrue  "ABC.DEF@[001.002.003.004]"                                  =   2 =  OK 
     *   196 - assertIsTrue  "\"ABC.DEF\"@[127.0.0.1]"                                    =   3 =  OK 
     *   197 - assertIsTrue  "ABC.DEF@[1.2.3.4]"                                          =   2 =  OK 
     *   198 - assertIsFalse "ABC.DE[F@1.2.3.4]"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   199 - assertIsFalse "ABC.DEF@{1.2.3.4}"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   200 - assertIsFalse "ABC.DEF[1.2.3.4]"                                           =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   201 - assertIsFalse "ABC.DEF[1.2.3.4]    ABC.DEF[1.2.3.4]"                       =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   202 - assertIsFalse "ABC.DEF@[1.2.3.4][5.6.7.8]"                                 =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   203 - assertIsFalse "ABC.DEF@[][][][]"                                           =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   204 - assertIsFalse "ABC.DEF@[....]"                                             =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   205 - assertIsFalse "[1.2.3.4]@[5.6.7.8]"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   206 - assertIsFalse "ABC.DEF[@1.2.3.4]"                                          =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   207 - assertIsTrue  "\"[1.2.3.4]\"@[5.6.7.8]"                                    =   3 =  OK 
     *   208 - assertIsFalse "ABC.DEF@MyDomain[1.2.3.4]"                                  =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   209 - assertIsFalse "ABC.DEF@[1.00002.3.4]"                                      =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   210 - assertIsFalse "ABC.DEF@[1.2.3.456]"                                        =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   211 - assertIsFalse "ABC.DEF@[..]"                                               =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   212 - assertIsFalse "ABC.DEF@[.2.3.4]"                                           =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   213 - assertIsFalse "ABC.DEF@[]"                                                 =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   214 - assertIsFalse "ABC.DEF@[1]"                                                =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   215 - assertIsFalse "ABC.DEF@[1.2]"                                              =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   216 - assertIsFalse "ABC.DEF@[1.2.3]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   217 - assertIsFalse "ABC.DEF@[1.2.3.4.5]"                                        =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   218 - assertIsFalse "ABC.DEF@[1.2.3.4.5.6]"                                      =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   219 - assertIsFalse "ABC.DEF@[MyDomain.de]"                                      =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   220 - assertIsFalse "ABC.DEF@[1.2.3.]"                                           =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   221 - assertIsFalse "ABC.DEF@[1.2.3. ]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   222 - assertIsFalse "ABC.DEF@[1.2.3.4].de"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   223 - assertIsFalse "ABC.DE@[1.2.3.4][5.6.7.8]"                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   224 - assertIsFalse "ABC.DEF@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   225 - assertIsFalse "ABC.DEF@1.2.3.4]"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   226 - assertIsFalse "ABC.DEF@[1.2.3.Z]"                                          =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   227 - assertIsFalse "ABC.DEF@[12.34]"                                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   228 - assertIsFalse "ABC.DEF@[1.2.3.4]ABC"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   229 - assertIsFalse "ABC.DEF@[1234.5.6.7]"                                       =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   230 - assertIsFalse "ABC.DEF@[1.2...3.4]"                                        =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   231 - assertIsTrue  "email@[123.123.123.123]"                                    =   2 =  OK 
     *   232 - assertIsFalse "email@111.222.333"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   233 - assertIsFalse "email@111.222.333.256"                                      =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   234 - assertIsFalse "email@[123.123.123.123"                                     =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   235 - assertIsFalse "email@[123.123.123].123"                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   236 - assertIsFalse "email@123.123.123.123]"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   237 - assertIsFalse "email@123.123.[123.123]"                                    =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   238 - assertIsFalse "ab@988.120.150.10"                                          =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   239 - assertIsFalse "ab@120.256.256.120"                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   240 - assertIsFalse "ab@120.25.1111.120"                                         =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   241 - assertIsFalse "ab@[188.120.150.10"                                         =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   242 - assertIsFalse "ab@188.120.150.10]"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   243 - assertIsFalse "ab@[188.120.150.10].com"                                    =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   244 - assertIsTrue  "ab@188.120.150.10"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   245 - assertIsTrue  "ab@1.0.0.10"                                                =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   246 - assertIsTrue  "ab@120.25.254.120"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   247 - assertIsTrue  "ab@01.120.150.1"                                            =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   248 - assertIsTrue  "ab@88.120.150.021"                                          =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   249 - assertIsTrue  "ab@88.120.150.01"                                           =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   250 - assertIsTrue  "email@123.123.123.123"                                      =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   251 - assertIsTrue  "ABC.DEF@[IPv6:2001:db8::1]"                                 =   4 =  OK 
     *   252 - assertIsFalse "ABC.DEF@[IP"                                                =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   253 - assertIsFalse "ABC.DEF@[IPv6]"                                             =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   254 - assertIsFalse "ABC.DEF@[IPv6:]"                                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   255 - assertIsFalse "ABC.DEF@[IPv6::::::]"                                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   256 - assertIsFalse "ABC.DEF@[IPv6:1]"                                           =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   257 - assertIsFalse "ABC.DEF@[IPv6:1:2]"                                         =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   258 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3]"                                       =   4 =  OK 
     *   259 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4]"                                     =   4 =  OK 
     *   260 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:]"                                  =   4 =  OK 
     *   261 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5::]"                                 =   4 =  OK 
     *   262 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6]"                                 =   4 =  OK 
     *   263 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7]"                               =   4 =  OK 
     *   264 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7"                                =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   265 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8]"                             =   4 =  OK 
     *   266 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6:7:8:9]"                           =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *   267 - assertIsFalse "ABC.DEF@[IPv4:1:2:3:4]"                                     =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   268 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   269 - assertIsFalse "ABC.DEF@[IPv6:1:2::4:5::]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   270 - assertIsFalse "ABC.DEF@[I127.0.0.1]"                                       =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   271 - assertIsFalse "ABC.DEF@[D127.0.0.1]"                                       =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   272 - assertIsFalse "ABC.DEF@[iPv6:2001:db8::1]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   273 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]"                              =   4 =  OK 
     *   274 - assertIsFalse "ABC.DEF@[IPv6:1:2:3::5::7:8]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   275 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:Z]"                                 =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   276 - assertIsFalse "ABC.DEF@[IPv6:12:34]"                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   277 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6]ABC"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   278 - assertIsTrue  "ABC.DEF@[IPv6:1:ABC:3:4:5:6:7]"                             =   4 =  OK 
     *   279 - assertIsFalse "ABC.DEF@[IPv6:1:XYZ:3:4:5:6:7]"                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   280 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:4:5:6"                                  =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   281 - assertIsFalse "ABC.DEF@[IPv6:12345:6:7:8:9]"                               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   282 - assertIsFalse "ABC.DEF@[IPv6:1:2:3:::6:7:8]"                               =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   283 - assertIsFalse "ABC.DEF@[IPv6:1:2:3]:4:5:6:7]"                              =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   284 - assertIsFalse "ABC.DEF@[IPv6:1:2](:3:4:5:6:7])"                            =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   285 - assertIsFalse "ABC.DEF@[IPv6:1:2:3](:4:5:6:7])"                            =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   286 - assertIsTrue  "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]"              =   4 =  OK 
     *   287 - assertIsTrue  "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =   4 =  OK 
     *   288 - assertIsFalse "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   289 - assertIsFalse "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   290 - assertIsTrue  "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]"         =   4 =  OK 
     *   291 - assertIsTrue  "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334"            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   292 - assertIsTrue  "ABC.DEF@[IPv6::FFFF:127.0.0.1]"                             =   4 =  OK 
     *   293 - assertIsTrue  "ABC.DEF@[IPv6::ffff:127.0.0.1]"                             =   4 =  OK 
     *   294 - assertIsTrue  "ABC.DEF@[::FFFF:127.0.0.1]"                                 =   4 =  OK 
     *   295 - assertIsTrue  "ABC.DEF@[::ffff:127.0.0.1]"                                 =   4 =  OK 
     *   296 - assertIsFalse "ABC.DEF@[IPv6::ffff:.127.0.1]"                              =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   297 - assertIsFalse "ABC.DEF@[IPv6::fff:127.0.0.1]"                              =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   298 - assertIsFalse "ABC.DEF@[IPv6::1234:127.0.0.1]"                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   299 - assertIsFalse "ABC.DEF@[IPv6:127.0.0.1]"                                   =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   300 - assertIsFalse "ABC.DEF@[IPv6:::127.0.0.1]"                                 =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *   301 - assertIsTrue  "\"ABC.DEF\"@GHI.DE"                                         =   1 =  OK 
     *   302 - assertIsTrue  "\"ABC DEF\"@GHI.DE"                                         =   1 =  OK 
     *   303 - assertIsTrue  "\"ABC@DEF\"@GHI.DE"                                         =   1 =  OK 
     *   304 - assertIsTrue  "\"ABC\\"DEF\"@GHI.DE"                                       =   1 =  OK 
     *   305 - assertIsTrue  "\"ABC\@DEF\"@GHI.DE"                                        =   1 =  OK 
     *   306 - assertIsTrue  "\"ABC\'DEF\"@GHI.DE"                                        =   1 =  OK 
     *   307 - assertIsTrue  "\"ABC\\DEF\"@GHI.DE"                                        =   1 =  OK 
     *   308 - assertIsFalse "\"ABC DEF@G\"HI.DE"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   309 - assertIsFalse "\"\"@GHI.DE"                                                =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   310 - assertIsFalse "\"ABC.DEF@G\"HI.DE"                                         =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   311 - assertIsFalse "A@G\"HI.DE"                                                 =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   312 - assertIsFalse "\"@GHI.DE"                                                  =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   313 - assertIsFalse "ABC.DEF.\""                                                 =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   314 - assertIsFalse "ABC.DEF@\"\""                                               =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   315 - assertIsFalse "ABC.DEF@G\"HI.DE"                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   316 - assertIsFalse "ABC.DEF@GHI.DE\""                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   317 - assertIsFalse "ABC.DEF@\"GHI.DE"                                           =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   318 - assertIsFalse "\"Escape.Sequenz.Ende\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   319 - assertIsFalse "ABC.DEF\"GHI.DE"                                            =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   320 - assertIsFalse "ABC.DEF\"@GHI.DE"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   321 - assertIsFalse "ABC.DE\"F@GHI.DE"                                           =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   322 - assertIsFalse "\"ABC.DEF@GHI.DE"                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   323 - assertIsFalse "\"ABC.DEF@GHI.DE\""                                         =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   324 - assertIsTrue  "\".ABC.DEF\"@GHI.DE"                                        =   1 =  OK 
     *   325 - assertIsTrue  "\"ABC.DEF.\"@GHI.DE"                                        =   1 =  OK 
     *   326 - assertIsTrue  "\"ABC\".DEF.\"GHI\"@JKL.de"                                 =   1 =  OK 
     *   327 - assertIsFalse "A\"BC\".DEF.\"GHI\"@JKL.de"                                 =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   328 - assertIsFalse "\"ABC\".DEF.G\"HI\"@JKL.de"                                 =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   329 - assertIsFalse "\"AB\"C.DEF.\"GHI\"@JKL.de"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   330 - assertIsFalse "\"ABC\".DEF.\"GHI\"J@KL.de"                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   331 - assertIsFalse "\"AB\"C.D\"EF\"@GHI.DE"                                     =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   332 - assertIsTrue  "AB.\"(CD)\".EF@GHI.JKL"                                     =   1 =  OK 
     *   333 - assertIsFalse "\"Ende.am.Eingabeende\""                                    =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   334 - assertIsFalse "0\"00.000\"@GHI.JKL"                                        =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   335 - assertIsTrue  "\"A[B]C\".\"D<E>F\".\"G(H)I\"@JKL.de"                       =   1 =  OK 
     *   336 - assertIsFalse "\"A[B]C\".D(E)F.\"GHI\"@JKL.de"                             =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   337 - assertIsFalse "\"A[B]C\".D[E]F.\"GHI\"@JKL.de"                             =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   338 - assertIsFalse "\"A[B]C\".D<E>F.\"GHI\"@JKL.de"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   339 - assertIsTrue  "\"()<>[]:,;@\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org"      =   1 =  OK 
     *   340 - assertIsFalse "\"Joe Smith\" email@domain.com"                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   341 - assertIsFalse "\"Joe\tSmith\".email@domain.com"                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   342 - assertIsFalse "\"Joe\"Smith\" email@domain.com"                            =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *   343 - assertIsTrue  "(ABC)DEF@GHI.JKL"                                           =   6 =  OK 
     *   344 - assertIsTrue  "(ABC) DEF@GHI.JKL"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   345 - assertIsTrue  "ABC(DEF)@GHI.JKL"                                           =   6 =  OK 
     *   346 - assertIsTrue  "ABC.DEF@GHI.JKL(MNO)"                                       =   6 =  OK 
     *   347 - assertIsTrue  "ABC.DEF@GHI.JKL      (MNO)"                                 =   6 =  OK 
     *   348 - assertIsFalse "ABC.DEF@             (MNO)"                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   349 - assertIsFalse "ABC.DEF@   .         (MNO)"                                 = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   350 - assertIsFalse "ABC.DEF              (MNO)"                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   351 - assertIsFalse "ABC.DEF@GHI.         (MNO)"                                 =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   352 - assertIsFalse "ABC.DEF@GHI.JKL       MNO"                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   353 - assertIsFalse "ABC.DEF@GHI.JKL          "                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   354 - assertIsFalse "ABC.DEF@GHI.JKL       .  "                                  = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   355 - assertIsTrue  "ABC.DEF@GHI.JKL ()"                                         =   6 =  OK 
     *   356 - assertIsTrue  "ABC.DEF@GHI.JKL()"                                          =   6 =  OK 
     *   357 - assertIsTrue  "ABC.DEF@()GHI.JKL"                                          =   6 =  OK 
     *   358 - assertIsTrue  "ABC.DEF()@GHI.JKL"                                          =   6 =  OK 
     *   359 - assertIsTrue  "()ABC.DEF@GHI.JKL"                                          =   6 =  OK 
     *   360 - assertIsFalse "()()()ABC.DEF@GHI.JKL"                                      =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   361 - assertIsFalse "ABC.DEF@GHI.JKL ()()"                                       = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   362 - assertIsFalse "(ABC).DEF@GHI.JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   363 - assertIsFalse "ABC.(DEF)@GHI.JKL"                                          = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   364 - assertIsFalse "ABC.DEF@(GHI).JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   365 - assertIsFalse "ABC.DEF@GHI.(JKL).MNO"                                      = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   366 - assertIsFalse "ABC.DEF@GHI.JK(L.M)NO"                                      = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   367 - assertIsFalse "AB(CD)EF@GHI.JKL"                                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   368 - assertIsFalse "AB.(CD).EF@GHI.JKL"                                         = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   369 - assertIsFalse "(ABCDEF)@GHI.JKL"                                           =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   370 - assertIsFalse "(ABCDEF).@GHI.JKL"                                          = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   371 - assertIsFalse "(AB\"C)DEF@GHI.JKL"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   372 - assertIsFalse "(AB\C)DEF@GHI.JKL"                                          =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   373 - assertIsFalse "(AB\@C)DEF@GHI.JKL"                                         =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   374 - assertIsFalse "ABC(DEF@GHI.JKL"                                            =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   375 - assertIsFalse "ABC.DEF@GHI)JKL"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   376 - assertIsFalse ")ABC.DEF@GHI.JKL"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   377 - assertIsFalse "ABC(DEF@GHI).JKL"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   378 - assertIsFalse "ABC(DEF.GHI).JKL"                                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   379 - assertIsFalse "(ABC.DEF@GHI.JKL)"                                          =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   380 - assertIsFalse "(A(B(C)DEF@GHI.JKL"                                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   381 - assertIsFalse "(A)B)C)DEF@GHI.JKL"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   382 - assertIsFalse "(A)BCDE(F)@GHI.JKL"                                         =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   383 - assertIsFalse "ABC.DEF@(GH)I.JK(LM)"                                       =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   384 - assertIsFalse "ABC.DEF@(GH(I.JK)L)M"                                       =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   385 - assertIsTrue  "ABC.DEF@(comment)[1.2.3.4]"                                 =   2 =  OK 
     *   386 - assertIsFalse "ABC.DEF@(comment) [1.2.3.4]"                                = 106 =  OK    Kommentar: Domain-Part mit Kommentar nach AT-Zeichen. Erwartete Zeichenkombination ")[".
     *   387 - assertIsTrue  "ABC.DEF@[1.2.3.4](comment)"                                 =   2 =  OK 
     *   388 - assertIsTrue  "ABC.DEF@[1.2.3.4]    (comment)"                             =   2 =  OK 
     *   389 - assertIsFalse "ABC.DEF@[1.2.3(comment).4]"                                 =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   390 - assertIsTrue  "ABC.DEF@(comment)[IPv6:1:2:3::5:6:7:8]"                     =   4 =  OK 
     *   391 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8](comment)"                     =   4 =  OK 
     *   392 - assertIsTrue  "ABC.DEF@[IPv6:1:2:3::5:6:7:8]    (comment)"                 =   4 =  OK 
     *   393 - assertIsTrue  "(comment)john.smith@example.com"                            =   6 =  OK 
     *   394 - assertIsTrue  "john.smith(comment)@example.com"                            =   6 =  OK 
     *   395 - assertIsTrue  "john.smith@(comment)example.com"                            =   6 =  OK 
     *   396 - assertIsTrue  "john.smith@example.com(comment)"                            =   6 =  OK 
     *   397 - assertIsFalse "john.smith@exampl(comment)e.com"                            = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   398 - assertIsFalse "john.s(comment)mith@example.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   399 - assertIsFalse "john.smith(comment)@(comment)example.com"                   =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   400 - assertIsFalse "john.smith(com@ment)example.com"                            =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   401 - assertIsFalse "email( (nested) )@plus.com"                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   402 - assertIsFalse "email)mirror(@plus.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   403 - assertIsFalse "email@plus.com (not closed comment"                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   404 - assertIsFalse "email(with @ in comment)plus.com"                           =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   405 - assertIsTrue  "email@domain.com (joe Smith)"                               =   6 =  OK 
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *   406 - assertIsTrue  "ABC DEF <ABC.DEF@GHI.JKL>"                                  =   0 =  OK 
     *   407 - assertIsTrue  "<ABC.DEF@GHI.JKL> ABC DEF"                                  =   0 =  OK 
     *   408 - assertIsFalse "ABC DEF ABC.DEF@GHI.JKL>"                                   =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   409 - assertIsFalse "<ABC.DEF@GHI.JKL ABC DEF"                                   =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   410 - assertIsTrue  "\"ABC DEF \"<ABC.DEF@GHI.JKL>"                              =   0 =  OK 
     *   411 - assertIsTrue  "\"ABC<DEF>\"@JKL.DE"                                        =   1 =  OK 
     *   412 - assertIsTrue  "\"ABC<DEF@GHI.COM>\"@JKL.DE"                                =   1 =  OK 
     *   413 - assertIsFalse "ABC DEF <ABC.<DEF@GHI.JKL>"                                 =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   414 - assertIsFalse "<ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>"                     =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   415 - assertIsFalse "ABC DEF <ABC.DEF@GHI.JKL"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   416 - assertIsFalse "ABC.DEF@GHI.JKL> ABC DEF"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   417 - assertIsFalse "ABC DEF >ABC.DEF@GHI.JKL<"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   418 - assertIsFalse ">ABC.DEF@GHI.JKL< ABC DEF"                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   419 - assertIsFalse "ABC DEF <A@A>"                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   420 - assertIsFalse "<A@A> ABC DEF"                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   421 - assertIsFalse "ABC DEF <>"                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   422 - assertIsFalse "<> ABC DEF"                                                 =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   423 - assertIsFalse ">"                                                          =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   424 - assertIsTrue  "<ABC.DEF@GHI.JKL>"                                          =   0 =  OK 
     *   425 - assertIsTrue  "Joe Smith <email@domain.com>"                               =   0 =  OK 
     *   426 - assertIsFalse "Joe Smith <mailto:email@domain.com>"                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   427 - assertIsFalse "Joe Smith <mailto:email(with comment)@domain.com>"          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   428 - assertIsTrue  "Non EMail part <(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *   429 - assertIsTrue  "Non EMail part <Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]>" =   9 =  OK 
     *   430 - assertIsTrue  "<(comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" =   9 =  OK 
     *   431 - assertIsTrue  "<Local.\"Part\"(comment)@[IPv6::ffff:127.0.0.1]> Non EMail part " =   9 =  OK 
     *   432 - assertIsFalse "Non EMail part < (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]>" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   433 - assertIsFalse "Non EMail part <Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]>" =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   434 - assertIsFalse "< (comment)Local.\"Part\"@[IPv6::ffff:127.0.0.1]> Non EMail part" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   435 - assertIsFalse "<Local.\"Part\"(comment)B@[IPv6::ffff:127.0.0.1]> Non EMail part " =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   436 - assertIsFalse "Test |<gaaf <email@domain.com>"                             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   437 - assertIsFalse "Display Name <email@plus.com> (Comment after name with display)" =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   438 - assertIsFalse "\"With extra < within quotes\" Display Name<email@domain.com>" =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   439 - assertIsFalse "<null>@mail.com"                                            =  18 =  OK    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *   440 - assertIsTrue  "A@B.CD"                                                     =   0 =  OK 
     *   441 - assertIsFalse "A@B.C"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   442 - assertIsFalse "A@COM"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   443 - assertIsTrue  "ABC.DEF@GHI.JKL"                                            =   0 =  OK 
     *   444 - assertIsTrue  "ABC.DEF@GHI.J"                                              =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   445 - assertIsTrue  "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123" =   0 =  OK 
     *   446 - assertIsFalse "ABC.DEF@GHI.A23456789012345678901234567890123456789012345678901234567890123A" =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als 63-Stellen lang sein.
     *   447 - assertIsTrue  "A@B.CD"                                                     =   0 =  OK 
     *   448 - assertIsTrue  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" =   0 =  OK 
     *   449 - assertIsTrue  "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" =   0 =  OK 
     *   450 - assertIsTrue  "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" =   0 =  OK 
     *   451 - assertIsFalse "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   452 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   453 - assertIsTrue  "domain.label.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   454 - assertIsFalse "domain.label.with.64.characters@A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   455 - assertIsTrue  "two.domain.labels.with.63.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   456 - assertIsFalse "domain.label.with.63.and.64.characters@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123A.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   457 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   458 - assertIsTrue  "63.character.domain.label@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   459 - assertIsTrue  "12345678901234567890123456789012345678901234567890.1234567@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =   0 =  OK 
     *   460 - assertIsFalse "12345678901234567890123456789012345678901234567890.12345678@A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.A23456789012345678901234567890123456789012345678901234567890123.com" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   461 - assertIsTrue  "\"very.(z),:;<>[]\\".VERY.\\"very@\\ \\"very\\".unusual\"@strange.example.com" =   1 =  OK 
     *   462 - assertIsFalse "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   463 - assertIsFalse "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   464 - assertIsFalse "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   465 - assertIsTrue  "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   466 - assertIsFalse "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   467 - assertIsTrue  "email@domain.topleveldomain"                                =   0 =  OK 
     * 
     * 
     * ---- Testclass EmailValidator4J ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   468 - assertIsFalse "nolocalpart.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   469 - assertIsFalse "test@example.com test"                                      = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   470 - assertIsFalse "user  name@example.com"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   471 - assertIsFalse "user   name@example.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   472 - assertIsFalse "example.@example.co.uk"                                     =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   473 - assertIsFalse "example@example@example.co.uk"                              =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   474 - assertIsFalse "(test_exampel@example.fr}"                                  =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   475 - assertIsFalse "example(example)example@example.co.uk"                      =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   476 - assertIsFalse ".example@localhost"                                         =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   477 - assertIsFalse "ex\ample@localhost"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   478 - assertIsFalse "example@local\host"                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   479 - assertIsFalse "example@localhost."                                         =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   480 - assertIsFalse "user name@example.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   481 - assertIsFalse "username@ example . com"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   482 - assertIsFalse "example@(fake}.com"                                         =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   483 - assertIsFalse "example@(fake.com"                                          =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   484 - assertIsFalse "username@example,com"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   485 - assertIsFalse "usern,ame@example.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   486 - assertIsFalse "user[na]me@example.com"                                     =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   487 - assertIsFalse "\"\"\"@iana.org"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   488 - assertIsFalse "\"\\"@iana.org"                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   489 - assertIsFalse "\"test\"test@iana.org"                                      =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   490 - assertIsFalse "\"test\"\"test\"@iana.org"                                  =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   491 - assertIsTrue  "\"test\".\"test\"@iana.org"                                 =   1 =  OK 
     *   492 - assertIsTrue  "\"test\".test@iana.org"                                     =   1 =  OK 
     *   493 - assertIsFalse "\"test\\"@iana.org"                                         =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   494 - assertIsFalse "\r\ntest@iana.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   495 - assertIsFalse "\r\n test@iana.org"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   496 - assertIsFalse "\r\n \r\ntest@iana.org"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   497 - assertIsFalse "\r\n \r\ntest@iana.org"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   498 - assertIsFalse "\r\n \r\n test@iana.org"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   499 - assertIsFalse "test@iana.org \r\n"                                         = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   500 - assertIsFalse "test@iana.org \r\n "                                        = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   501 - assertIsFalse "test@iana.org \r\n \r\n"                                    = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   502 - assertIsFalse "test@iana.org \r\n\r\n"                                     = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   503 - assertIsFalse "test@iana.org  \r\n\r\n "                                   = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   504 - assertIsFalse "test@iana/icann.org"                                        =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   505 - assertIsFalse "test@foo;bar.com"                                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   506 - assertIsFalse "a@test.com"                                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   507 - assertIsFalse "comment)example@example.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   508 - assertIsFalse "comment(example))@example.com"                              =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   509 - assertIsFalse "example@example)comment.com"                                =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   510 - assertIsFalse "example@example(comment)).com"                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   511 - assertIsFalse "example@[1.2.3.4"                                           =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   512 - assertIsFalse "example@[IPv6:1:2:3:4:5:6:7:8"                              =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   513 - assertIsFalse "exam(ple@exam).ple"                                         = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   514 - assertIsFalse "example@(example))comment.com"                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   515 - assertIsTrue  "example@example.com"                                        =   0 =  OK 
     *   516 - assertIsTrue  "example@example.co.uk"                                      =   0 =  OK 
     *   517 - assertIsTrue  "example_underscore@example.fr"                              =   0 =  OK 
     *   518 - assertIsTrue  "exam'ple@example.com"                                       =   0 =  OK 
     *   519 - assertIsTrue  "exam\ ple@example.com"                                      =   0 =  OK 
     *   520 - assertIsFalse "example((example))@fakedfake.co.uk"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   521 - assertIsFalse "example@faked(fake).co.uk"                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   522 - assertIsTrue  "example+@example.com"                                       =   0 =  OK 
     *   523 - assertIsTrue  "example@with-hyphen.example.com"                            =   0 =  OK 
     *   524 - assertIsTrue  "with-hyphen@example.com"                                    =   0 =  OK 
     *   525 - assertIsTrue  "example@1leadingnum.example.com"                            =   0 =  OK 
     *   526 - assertIsTrue  "1leadingnum@example.com"                                    =   0 =  OK 
     *   527 - assertIsTrue  "инфо@письмо.рф"                                             =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   528 - assertIsTrue  "\"username\"@example.com"                                   =   1 =  OK 
     *   529 - assertIsTrue  "\"user,name\"@example.com"                                  =   1 =  OK 
     *   530 - assertIsTrue  "\"user name\"@example.com"                                  =   1 =  OK 
     *   531 - assertIsTrue  "\"user@name\"@example.com"                                  =   1 =  OK 
     *   532 - assertIsFalse "\"\a\"@iana.org"                                            =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   533 - assertIsTrue  "\"test\ test\"@iana.org"                                    =   1 =  OK 
     *   534 - assertIsFalse "\"\"@iana.org"                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   535 - assertIsFalse "\"\"@[]"                                                    =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   536 - assertIsTrue  "\"\\"\"@iana.org"                                           =   1 =  OK 
     *   537 - assertIsTrue  "example@localhost"                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- unsorted from the WEB ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   538 - assertIsFalse "..@test.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   539 - assertIsFalse ".a@test.com"                                                =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   540 - assertIsFalse "ab@sd@dd"                                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   541 - assertIsFalse ".@s.dd"                                                     =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   542 - assertIsFalse "a@b.-de.cc"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   543 - assertIsFalse "a@bde-.cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   544 - assertIsFalse "a@b._de.cc"                                                 =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   545 - assertIsFalse "a@bde_.cc"                                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   546 - assertIsFalse "a@bde.cc."                                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   547 - assertIsFalse "ab@b+de.cc"                                                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   548 - assertIsFalse "a..b@bde.cc"                                                =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   549 - assertIsFalse "_@bde.cc,"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   550 - assertIsFalse "plainaddress"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   551 - assertIsFalse "plain.address"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   552 - assertIsFalse ".email@domain.com"                                          =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   553 - assertIsFalse "email.@domain.com"                                          =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   554 - assertIsFalse "email..email@domain.com"                                    =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   555 - assertIsFalse "email@.domain.com"                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   556 - assertIsFalse "email@domain.com."                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   557 - assertIsFalse "email@domain..com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   558 - assertIsFalse "MailTo:casesensitve@domain.com"                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   559 - assertIsFalse "mailto:email@domain.com"                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   560 - assertIsFalse "email@domain"                                               =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   561 - assertIsTrue  "someone@somewhere.com"                                      =   0 =  OK 
     *   562 - assertIsTrue  "someone@somewhere.co.uk"                                    =   0 =  OK 
     *   563 - assertIsTrue  "someone+tag@somewhere.net"                                  =   0 =  OK 
     *   564 - assertIsTrue  "futureTLD@somewhere.fooo"                                   =   0 =  OK 
     *   565 - assertIsFalse "myemailsample.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   566 - assertIsTrue  "myemail@sample"                                             =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   567 - assertIsTrue  "myemail@sample.com"                                         =   0 =  OK 
     *   568 - assertIsFalse "myemail@@sample.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   569 - assertIsFalse "myemail@sa@mple.com"                                        =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   570 - assertIsTrue  "\"myemail@sa\"@mple.com"                                    =   1 =  OK 
     *   571 - assertIsFalse "a.\"b@c\".x.\"@\".d.e@f.g@"                                 =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   572 - assertIsTrue  "\"foo\@bar\"@Something.com"                                 =   1 =  OK 
     *   573 - assertIsFalse "Foobar Some@thing.com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   574 - assertIsFalse "foo@bar@machine.subdomain.example.museum"                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   575 - assertIsTrue  "foo\@bar@machine.subdomain.example.museum"                  =   0 =  OK 
     *   576 - assertIsFalse "foo.bar@machine.sub\@domain.example.museum"                 =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   577 - assertIsTrue  "cog@wheel.com"                                              =   0 =  OK 
     *   578 - assertIsTrue  "\"cogwheel the orange\"@example.com"                        =   1 =  OK 
     *   579 - assertIsFalse "123@$.xyz"                                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   580 - assertIsTrue  "david.jones@proseware.com"                                  =   0 =  OK 
     *   581 - assertIsTrue  "d.j@server1.proseware.com"                                  =   0 =  OK 
     *   582 - assertIsTrue  "jones@ms1.proseware.com"                                    =   0 =  OK 
     *   583 - assertIsFalse "j.@server1.proseware.com"                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   584 - assertIsTrue  "j@proseware.com9"                                           =   0 =  OK 
     *   585 - assertIsTrue  "js#internal@proseware.com"                                  =   0 =  OK 
     *   586 - assertIsTrue  "j_9@[129.126.118.1]"                                        =   2 =  OK 
     *   587 - assertIsFalse "j..s@proseware.com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   588 - assertIsTrue  "js*@proseware.com"                                          =   0 =  OK 
     *   589 - assertIsFalse "js@proseware..com"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   590 - assertIsTrue  "js@proseware.com9"                                          =   0 =  OK 
     *   591 - assertIsTrue  "j.s@server1.proseware.com"                                  =   0 =  OK 
     *   592 - assertIsTrue  "\"j\\"s\"@proseware.com"                                    =   1 =  OK 
     *   593 - assertIsFalse "dasddas-@.com"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   594 - assertIsTrue  "-asd@das.com"                                               =   0 =  OK 
     *   595 - assertIsFalse "as3d@dac.coas-"                                             =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   596 - assertIsTrue  "dsq!a?@das.com"                                             =   0 =  OK 
     *   597 - assertIsTrue  "_dasd@sd.com"                                               =   0 =  OK 
     *   598 - assertIsFalse "dad@sds"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   599 - assertIsTrue  "asd-@asd.com"                                               =   0 =  OK 
     *   600 - assertIsTrue  "dasd_-@jdas.com"                                            =   0 =  OK 
     *   601 - assertIsFalse "asd@dasd@asd.cm"                                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   602 - assertIsFalse "da23@das..com"                                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   603 - assertIsTrue  "_dasd_das_@9.com"                                           =   0 =  OK 
     *   604 - assertIsTrue  "d23d@da9.co9"                                               =   0 =  OK 
     *   605 - assertIsTrue  "dasd.dadas@dasd.com"                                        =   0 =  OK 
     *   606 - assertIsTrue  "dda_das@das-dasd.com"                                       =   0 =  OK 
     *   607 - assertIsTrue  "dasd-dasd@das.com.das"                                      =   0 =  OK 
     *   608 - assertIsFalse "fdsa"                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   609 - assertIsFalse "fdsa@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   610 - assertIsFalse "fdsa@fdsa"                                                  =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   611 - assertIsFalse "fdsa@fdsa."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   612 - assertIsFalse "@b.com"                                                     =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   613 - assertIsFalse "a@.com"                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   614 - assertIsTrue  "a@bcom"                                                     =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   615 - assertIsTrue  "a.b@com"                                                    =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   616 - assertIsFalse "a@b."                                                       =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   617 - assertIsTrue  "ab@c.com"                                                   =   0 =  OK 
     *   618 - assertIsTrue  "a@bc.com"                                                   =   0 =  OK 
     *   619 - assertIsTrue  "a@b.com"                                                    =   0 =  OK 
     *   620 - assertIsTrue  "a@b.c.com"                                                  =   0 =  OK 
     *   621 - assertIsTrue  "a+b@c.com"                                                  =   0 =  OK 
     *   622 - assertIsTrue  "a@123.45.67.89"                                             =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   623 - assertIsTrue  "%2@gmail.com"                                               =   0 =  OK 
     *   624 - assertIsTrue  "\"%2\"@gmail.com"                                           =   1 =  OK 
     *   625 - assertIsTrue  "\"a..b\"@gmail.com"                                         =   1 =  OK 
     *   626 - assertIsTrue  "\"a_b\"@gmail.com"                                          =   1 =  OK 
     *   627 - assertIsTrue  "_@gmail.com"                                                =   0 =  OK 
     *   628 - assertIsTrue  "1@gmail.com"                                                =   0 =  OK 
     *   629 - assertIsTrue  "1_example@something.gmail.com"                              =   0 =  OK 
     *   630 - assertIsTrue  "d._.___d@gmail.com"                                         =   0 =  OK 
     *   631 - assertIsTrue  "d.oy.smith@gmail.com"                                       =   0 =  OK 
     *   632 - assertIsTrue  "d_oy_smith@gmail.com"                                       =   0 =  OK 
     *   633 - assertIsTrue  "doysmith@gmail.com"                                         =   0 =  OK 
     *   634 - assertIsTrue  "D.Oy'Smith@gmail.com"                                       =   0 =  OK 
     *   635 - assertIsTrue  "%20f3v34g34@gvvre.com"                                      =   0 =  OK 
     *   636 - assertIsTrue  "piskvor@example.lighting"                                   =   0 =  OK 
     *   637 - assertIsTrue  "--@ooo.ooo"                                                 =   0 =  OK 
     *   638 - assertIsFalse "check@thiscom"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   639 - assertIsFalse "check@this..com"                                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   640 - assertIsFalse " check@this.com"                                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   641 - assertIsTrue  "check@this.com"                                             =   0 =  OK 
     *   642 - assertIsTrue  "Abc@example.com"                                            =   0 =  OK 
     *   643 - assertIsFalse "Abc@example.com."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   644 - assertIsTrue  "Abc@10.42.0.1"                                              =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   645 - assertIsTrue  "user@localserver"                                           =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   646 - assertIsTrue  "Abc.123@example.com"                                        =   0 =  OK 
     *   647 - assertIsTrue  "user+mailbox/department=shipping@example.com"               =   0 =  OK 
     *   648 - assertIsTrue  "\" \"@example.org"                                          =   1 =  OK 
     *   649 - assertIsTrue  "user@[IPv6:2001:DB8::1]"                                    =   4 =  OK 
     *   650 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   651 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   652 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   653 - assertIsFalse "just\"not\"right@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   654 - assertIsFalse "this is\"not\allowed@example.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   655 - assertIsFalse "this\ still\\"not\allowed@example.com"                      =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   656 - assertIsFalse "john..doe@example.com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   657 - assertIsFalse "john.doe@example..com"                                      =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   658 - assertIsTrue  "email@example.com"                                          =   0 =  OK 
     *   659 - assertIsTrue  "email@example.co.uk"                                        =   0 =  OK 
     *   660 - assertIsTrue  "email@mail.gmail.com"                                       =   0 =  OK 
     *   661 - assertIsTrue  "unusual+but+valid+email1900=/!#$%&\'*+-/=?^_`.{|}~@example.com" =   0 =  OK 
     *   662 - assertIsFalse "email@example.co.uk."                                       =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   663 - assertIsFalse "email@example"                                              =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   664 - assertIsFalse " email@example.com"                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   665 - assertIsFalse "email@example.com "                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   666 - assertIsFalse "email@example,com "                                         =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   667 - assertIsFalse "invalid.email.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   668 - assertIsFalse "invalid@email@domain.com"                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   669 - assertIsFalse "email@example..com"                                         =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   670 - assertIsTrue  "yoursite@ourearth.com"                                      =   0 =  OK 
     *   671 - assertIsTrue  "my.ownsite@ourearth.org"                                    =   0 =  OK 
     *   672 - assertIsTrue  "mysite@you.me.net"                                          =   0 =  OK 
     *   673 - assertIsTrue  "xxxx@gmail.com"                                             =   0 =  OK 
     *   674 - assertIsTrue  "xxxxxx@yahoo.com"                                           =   0 =  OK 
     *   675 - assertIsFalse "xxxx.ourearth.com"                                          =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   676 - assertIsFalse "xxxx@.com.my"                                               =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   677 - assertIsFalse "@you.me.net"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   678 - assertIsFalse "xxxx123@gmail.b"                                            =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   679 - assertIsFalse "xxxx@.org.org"                                              =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   680 - assertIsFalse ".xxxx@mysite.org"                                           =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   681 - assertIsFalse "xxxxx()*@gmail.com"                                         =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   682 - assertIsFalse "xxxx..1234@yahoo.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   683 - assertIsTrue  "alex@example.com"                                           =   0 =  OK 
     *   684 - assertIsTrue  "alireza@test.co.uk"                                         =   0 =  OK 
     *   685 - assertIsTrue  "peter.example@yahoo.com.au"                                 =   0 =  OK 
     *   686 - assertIsTrue  "alex@example.com"                                           =   0 =  OK 
     *   687 - assertIsTrue  "peter_123@news.com"                                         =   0 =  OK 
     *   688 - assertIsTrue  "hello7___@ca.com.pt"                                        =   0 =  OK 
     *   689 - assertIsTrue  "example@example.co"                                         =   0 =  OK 
     *   690 - assertIsFalse "hallo@example.coassjj#sswzazaaaa"                           =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   691 - assertIsFalse "hallo2ww22@example....caaaao"                               =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   692 - assertIsTrue  "abcxyz123@qwert.com"                                        =   0 =  OK 
     *   693 - assertIsTrue  "abc123xyz@asdf.co.in"                                       =   0 =  OK 
     *   694 - assertIsTrue  "abc1_xyz1@gmail1.com"                                       =   0 =  OK 
     *   695 - assertIsTrue  "abc.xyz@gmail.com.in"                                       =   0 =  OK 
     *   696 - assertIsTrue  "pio_pio@factory.com"                                        =   0 =  OK 
     *   697 - assertIsTrue  "~pio_pio@factory.com"                                       =   0 =  OK 
     *   698 - assertIsTrue  "pio_~pio@factory.com"                                       =   0 =  OK 
     *   699 - assertIsTrue  "pio_#pio@factory.com"                                       =   0 =  OK 
     *   700 - assertIsFalse "pio_pio@#factory.com"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   701 - assertIsFalse "pio_pio@factory.c#om"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   702 - assertIsFalse "pio_pio@factory.c*om"                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   703 - assertIsTrue  "pio^_pio@factory.com"                                       =   0 =  OK 
     *   704 - assertIsTrue  "\"Abc\@def\"@example.com"                                   =   1 =  OK 
     *   705 - assertIsTrue  "\"Fred Bloggs\"@example.com"                                =   1 =  OK 
     *   706 - assertIsTrue  "\"Fred\ Bloggs\"@example.com"                               =   1 =  OK 
     *   707 - assertIsTrue  "Fred\ Bloggs@example.com"                                   =   0 =  OK 
     *   708 - assertIsFalse "\"Joe\Blow\"@example.com"                                   =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   709 - assertIsTrue  "\"Joe\\Blow\"@example.com"                                  =   1 =  OK 
     *   710 - assertIsTrue  "\"Abc@def\"@example.com"                                    =   1 =  OK 
     *   711 - assertIsTrue  "customer/department=shipping@example.com"                   =   0 =  OK 
     *   712 - assertIsFalse "\$A12345@example.com"                                       =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   713 - assertIsTrue  "!def!xyz%abc@example.com"                                   =   0 =  OK 
     *   714 - assertIsTrue  "_somename@example.com"                                      =   0 =  OK 
     *   715 - assertIsTrue  "abc.\"defghi\".xyz@example.com"                             =   1 =  OK 
     *   716 - assertIsTrue  "\"abcdefghixyz\"@example.com"                               =   1 =  OK 
     *   717 - assertIsFalse "abc\"defghi\"xyz@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   718 - assertIsFalse "abc\\"def\\"ghi@example.com"                                =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     * 
     * 
     * ---- https://github.com/dotnet/docs/issues/6620 ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   719 - assertIsTrue  "jkt@gmail.com"                                              =   0 =  OK 
     *   720 - assertIsFalse " jkt@gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   721 - assertIsFalse "jkt@ gmail.com"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   722 - assertIsFalse "jkt@g mail.com"                                             = 105 =  OK    Kommentar: Leerzeichentrennung im Domain-Part. Oeffnende Klammer erwartet
     *   723 - assertIsFalse "jkt @gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   724 - assertIsFalse "j kt@gmail.com"                                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * 
     * ---- https://github.com/jstedfast/EmailValidation/blob/master/UnitTests/Test.cs ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   725 - assertIsTrue  "\"\e\s\c\a\p\e\d\"@sld.com"                                 =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   726 - assertIsTrue  "\"back\slash\"@sld.com"                                     =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   727 - assertIsTrue  "\"escaped\\"quote\"@sld.com"                                =   1 =  OK 
     *   728 - assertIsTrue  "\"quoted\"@sld.com"                                         =   1 =  OK 
     *   729 - assertIsTrue  "\"quoted-at-sign@sld.org\"@sld.com"                         =   1 =  OK 
     *   730 - assertIsTrue  "&'*+-./=?^_{}~@other-valid-characters-in-local.net"         =   0 =  OK 
     *   731 - assertIsTrue  "_.-+~^*'`{GEO}`'*^~+-._@example.com"                        =   0 =  OK 
     *   732 - assertIsTrue  "01234567890@numbers-in-local.net"                           =   0 =  OK 
     *   733 - assertIsTrue  "a@single-character-in-local.org"                            =   0 =  OK 
     *   734 - assertIsTrue  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@letters-in-local.org" =   0 =  OK 
     *   735 - assertIsTrue  "backticksarelegit@test.com"                                 =   0 =  OK 
     *   736 - assertIsTrue  "bracketed-IP-instead-of-domain@[127.0.0.1]"                 =   2 =  OK 
     *   737 - assertIsTrue  "country-code-tld@sld.rw"                                    =   0 =  OK 
     *   738 - assertIsTrue  "country-code-tld@sld.uk"                                    =   0 =  OK 
     *   739 - assertIsTrue  "letters-in-sld@123.com"                                     =   0 =  OK 
     *   740 - assertIsTrue  "local@dash-in-sld.com"                                      =   0 =  OK 
     *   741 - assertIsTrue  "local@sld.newTLD"                                           =   0 =  OK 
     *   742 - assertIsTrue  "local@sub.domains.com"                                      =   0 =  OK 
     *   743 - assertIsTrue  "mixed-1234-in-{+^}-local@sld.net"                           =   0 =  OK 
     *   744 - assertIsTrue  "one-character-third-level@a.example.com"                    =   0 =  OK 
     *   745 - assertIsTrue  "one-letter-sld@x.org"                                       =   0 =  OK 
     *   746 - assertIsTrue  "punycode-numbers-in-tld@sld.xn--3e0b707e"                   =   0 =  OK 
     *   747 - assertIsTrue  "single-character-in-sld@x.org"                              =   0 =  OK 
     *   748 - assertIsTrue  "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-three-characters-so-it-is-valid-blah-blah.com" =   0 =  OK 
     *   749 - assertIsTrue  "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-254-characters-exactly.so-it-should-be-valid.and-im-going-to-add-some-more-words-here.to-increase-the-length-blah-blah-blah-blah-bla.org" =   0 =  OK 
     *   750 - assertIsTrue  "uncommon-tld@sld.mobi"                                      =   0 =  OK 
     *   751 - assertIsTrue  "uncommon-tld@sld.museum"                                    =   0 =  OK 
     *   752 - assertIsTrue  "uncommon-tld@sld.travel"                                    =   0 =  OK 
     *   753 - assertIsFalse "invalid"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   754 - assertIsFalse "invalid@"                                                   =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *   755 - assertIsFalse "invalid @"                                                  =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   756 - assertIsFalse "invalid@[555.666.777.888]"                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   757 - assertIsFalse "invalid@[IPv6:123456]"                                      =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   758 - assertIsFalse "invalid@[127.0.0.1.]"                                       =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   759 - assertIsFalse "invalid@[127.0.0.1]."                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   760 - assertIsFalse "invalid@[127.0.0.1]x"                                       =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   761 - assertIsFalse "<>@[]`|@even-more-invalid-characters-in-local.org"          =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   762 - assertIsFalse "@missing-local.org"                                         =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   763 - assertIsFalse "IP-and-port@127.0.0.1:25"                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   764 - assertIsFalse "another-invalid-ip@127.0.0.256"                             =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   765 - assertIsFalse "ip.range.overflow@[127.0.0.256]"                            =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   766 - assertIsFalse "invalid"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   767 - assertIsFalse "invalid-characters-in-sld@! \"#$%(),/;<>_[]`|.org"          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   768 - assertIsFalse "invalid-ip@127.0.0.1.26"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   769 - assertIsFalse "local-ends-with-dot.@sld.com"                               =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   770 - assertIsFalse "missing-at-sign.net"                                        =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   771 - assertIsFalse "missing-sld@.com"                                           =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   772 - assertIsFalse "missing-tld@sld."                                           =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   773 - assertIsFalse "sld-ends-with-dash@sld-.com"                                =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   774 - assertIsFalse "sld-starts-with-dashsh@-sld.com"                            =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   775 - assertIsFalse "the-character-limit@for-each-part.of-the-domain.is-sixty-three-characters.this-is-exactly-sixty-four-characters-so-it-is-invalid-blah-blah.com" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   776 - assertIsFalse "the-local-part-is-invalid-if-it-is-longer-than-sixty-four-characters@sld.net" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   777 - assertIsFalse "the-total-length@of-an-entire-address.cannot-be-longer-than-two-hundred-and-fifty-four-characters.and-this-address-is-255-characters-exactly.so-it-should-be-invalid.and-im-going-to-add-some-more-words-here.to-increase-the-lenght-blah-blah-blah-blah-bl.org" =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   778 - assertIsFalse "two..consecutive-dots@sld.com"                              =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   779 - assertIsFalse "unbracketed-IP@127.0.0.1"                                   =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   780 - assertIsFalse "underscore.error@example.com_"                              =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     * 
     * 
     * ---- https://www.journaldev.com/638/java-email-validation-regex ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   781 - assertIsTrue  "journaldev@yahoo.com"                                       =   0 =  OK 
     *   782 - assertIsTrue  "journaldev-100@yahoo.com"                                   =   0 =  OK 
     *   783 - assertIsTrue  "journaldev.100@yahoo.com"                                   =   0 =  OK 
     *   784 - assertIsTrue  "journaldev111@journaldev.com"                               =   0 =  OK 
     *   785 - assertIsTrue  "journaldev-100@journaldev.net"                              =   0 =  OK 
     *   786 - assertIsTrue  "journaldev.100@journaldev.com.au"                           =   0 =  OK 
     *   787 - assertIsTrue  "journaldev@1.com"                                           =   0 =  OK 
     *   788 - assertIsTrue  "journaldev@gmail.com.com"                                   =   0 =  OK 
     *   789 - assertIsTrue  "journaldev+100@gmail.com"                                   =   0 =  OK 
     *   790 - assertIsTrue  "journaldev-100@yahoo-test.com"                              =   0 =  OK 
     *   791 - assertIsTrue  "journaldev_100@yahoo-test.ABC.CoM"                          =   0 =  OK 
     *   792 - assertIsFalse "journaldev"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   793 - assertIsFalse "journaldev@.com.my"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   794 - assertIsFalse "journaldev123@gmail.a"                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   795 - assertIsFalse "journaldev123@.com"                                         =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   796 - assertIsFalse "journaldev123@.com.com"                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   797 - assertIsFalse ".journaldev@journaldev.com"                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   798 - assertIsFalse "journaldev()*@gmail.com"                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   799 - assertIsFalse "journaldev@%*.com"                                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   800 - assertIsFalse "journaldev..2002@gmail.com"                                 =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   801 - assertIsFalse "journaldev.@gmail.com"                                      =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   802 - assertIsFalse "journaldev@journaldev@gmail.com"                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   803 - assertIsFalse "journaldev@gmail.com.1a"                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     * 
     * 
     * ---- https://fightingforalostcause.net/content/misc/2006/compare-email-regex.php ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   804 - assertIsTrue  "first.last@iana.org"                                        =   0 =  OK 
     *   805 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678901234@iana.org" =   0 =  OK 
     *   806 - assertIsTrue  "\"first\\"last\"@iana.org"                                  =   1 =  OK 
     *   807 - assertIsTrue  "\"first@last\"@iana.org"                                    =   1 =  OK 
     *   808 - assertIsTrue  "\"first\\last\"@iana.org"                                   =   1 =  OK 
     *   809 - assertIsTrue  "x@x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23456789.x23" =   0 =  OK 
     *   810 - assertIsTrue  "1234567890123456789012345678901234567890123456789012345678@1234567890123456789012345678901234567890123456.de" =   0 =  OK 
     *   811 - assertIsTrue  "first.last@[12.34.56.78]"                                   =   2 =  OK 
     *   812 - assertIsTrue  "first.last@[IPv6:::1111:2222:3333:4444:5555:6666]"          =   4 =  OK 
     *   813 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666]"           =   4 =  OK 
     *   814 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:]"          =   4 =  OK 
     *   815 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666::]"          =   4 =  OK 
     *   816 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888]"  =   4 =  OK 
     *   817 - assertIsTrue  "first.last@x23456789012345678901234567890123456789012345678901234567890123.iana.org" =   0 =  OK 
     *   818 - assertIsTrue  "first.last@3com.com"                                        =   0 =  OK 
     *   819 - assertIsTrue  "first.last@123.iana.org"                                    =   0 =  OK 
     *   820 - assertIsTrue  "\"first\\last\"@iana.org"                                   =   1 =  OK 
     *   821 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]"    =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   822 - assertIsTrue  "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]"      =   4 =  OK 
     *   823 - assertIsTrue  "\"Abc\@def\"@iana.org"                                      =   1 =  OK 
     *   824 - assertIsTrue  "\"Fred\ Bloggs\"@iana.org"                                  =   1 =  OK 
     *   825 - assertIsTrue  "\"Joe.\\Blow\"@iana.org"                                    =   1 =  OK 
     *   826 - assertIsTrue  "\"Abc@def\"@iana.org"                                       =   1 =  OK 
     *   827 - assertIsTrue  "\"Fred Bloggs\"@iana.orgin"                                 =   1 =  OK 
     *   828 - assertIsTrue  "\"Abc\@def\"@iana.org"                                      =   1 =  OK 
     *   829 - assertIsTrue  "user+mailbox@iana.org"                                      =   0 =  OK 
     *   830 - assertIsTrue  "$A12345@iana.org"                                           =   0 =  OK 
     *   831 - assertIsTrue  "!def!xyz%abc@iana.org"                                      =   0 =  OK 
     *   832 - assertIsTrue  "_somename@iana.org"                                         =   0 =  OK 
     *   833 - assertIsTrue  "dclo@us.ibm.com"                                            =   0 =  OK 
     *   834 - assertIsTrue  "peter.piper@iana.org"                                       =   0 =  OK 
     *   835 - assertIsTrue  "test@iana.org"                                              =   0 =  OK 
     *   836 - assertIsTrue  "TEST@iana.org"                                              =   0 =  OK 
     *   837 - assertIsTrue  "1234567890@iana.org"                                        =   0 =  OK 
     *   838 - assertIsTrue  "test+test@iana.org"                                         =   0 =  OK 
     *   839 - assertIsTrue  "test-test@iana.org"                                         =   0 =  OK 
     *   840 - assertIsTrue  "t*est@iana.org"                                             =   0 =  OK 
     *   841 - assertIsTrue  "+1~1+@iana.org"                                             =   0 =  OK 
     *   842 - assertIsTrue  "{_test_}@iana.org"                                          =   0 =  OK 
     *   843 - assertIsTrue  "test.test@iana.org"                                         =   0 =  OK 
     *   844 - assertIsTrue  "\"test.test\"@iana.org"                                     =   1 =  OK 
     *   845 - assertIsTrue  "test.\"test\"@iana.org"                                     =   1 =  OK 
     *   846 - assertIsTrue  "\"test@test\"@iana.org"                                     =   1 =  OK 
     *   847 - assertIsTrue  "test@123.123.123.x123"                                      =   0 =  OK 
     *   848 - assertIsFalse "test@123.123.123.123"                                       =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   849 - assertIsTrue  "test@[123.123.123.123]"                                     =   2 =  OK 
     *   850 - assertIsTrue  "test@example.iana.org"                                      =   0 =  OK 
     *   851 - assertIsTrue  "test@example.example.iana.org"                              =   0 =  OK 
     *   852 - assertIsTrue  "customer/department@iana.org"                               =   0 =  OK 
     *   853 - assertIsTrue  "_Yosemite.Sam@iana.org"                                     =   0 =  OK 
     *   854 - assertIsTrue  "~@iana.org"                                                 =   0 =  OK 
     *   855 - assertIsTrue  "\"Austin@Powers\"@iana.org"                                 =   1 =  OK 
     *   856 - assertIsTrue  "Ima.Fool@iana.org"                                          =   0 =  OK 
     *   857 - assertIsTrue  "\"Ima.Fool\"@iana.org"                                      =   1 =  OK 
     *   858 - assertIsTrue  "\"Ima Fool\"@iana.orgin"                                    =   1 =  OK 
     *   859 - assertIsTrue  "\"first\".\"last\"@iana.org"                                =   1 =  OK 
     *   860 - assertIsTrue  "\"first\".middle.\"last\"@iana.org"                         =   1 =  OK 
     *   861 - assertIsTrue  "\"first\".last@iana.org"                                    =   1 =  OK 
     *   862 - assertIsTrue  "first.\"last\"@iana.org"                                    =   1 =  OK 
     *   863 - assertIsTrue  "\"first\".\"middle\".\"last\"@iana.org"                     =   1 =  OK 
     *   864 - assertIsTrue  "\"first.middle\".\"last\"@iana.org"                         =   1 =  OK 
     *   865 - assertIsTrue  "\"first.middle.last\"@iana.org"                             =   1 =  OK 
     *   866 - assertIsTrue  "\"first..last\"@iana.org"                                   =   1 =  OK 
     *   867 - assertIsTrue  "\"first\\"last\"@iana.org"                                  =   1 =  OK 
     *   868 - assertIsTrue  "first.\"middle\".\"last\"@iana.org"                         =   1 =  OK 
     *   869 - assertIsFalse "first.last @iana.orgin"                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   870 - assertIsTrue  "\"test blah\"@iana.orgin"                                   =   1 =  OK 
     *   871 - assertIsTrue  "name.lastname@domain.com"                                   =   0 =  OK 
     *   872 - assertIsTrue  "a@bar.com"                                                  =   0 =  OK 
     *   873 - assertIsTrue  "aaa@[123.123.123.123]"                                      =   2 =  OK 
     *   874 - assertIsTrue  "a-b@bar.com"                                                =   0 =  OK 
     *   875 - assertIsFalse "+@b.c"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   876 - assertIsTrue  "+@b.com"                                                    =   0 =  OK 
     *   877 - assertIsTrue  "a@b.co-foo.uk"                                              =   0 =  OK 
     *   878 - assertIsTrue  "\"hello my name is\"@stutter.comin"                         =   1 =  OK 
     *   879 - assertIsTrue  "\"Test \\"Fail\\" Ing\"@iana.orgin"                         =   1 =  OK 
     *   880 - assertIsTrue  "shaitan@my-domain.thisisminekthx"                           =   0 =  OK 
     *   881 - assertIsFalse "foobar@192.168.0.1"                                         =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   882 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"          =   6 =  OK 
     *   883 - assertIsTrue  "HM2Kinsists@(that comments are allowed)this.is.ok"          =   6 =  OK 
     *   884 - assertIsTrue  "user%uucp!path@berkeley.edu"                                =   0 =  OK 
     *   885 - assertIsTrue  "cdburgess+!#$%&'*-/=?+_{}|~test@gmail.com"                  =   0 =  OK 
     *   886 - assertIsTrue  "test@test.com"                                              =   0 =  OK 
     *   887 - assertIsTrue  "test@xn--example.com"                                       =   0 =  OK 
     *   888 - assertIsTrue  "test@example.com"                                           =   0 =  OK 
     *   889 - assertIsTrue  "{^c\@**Dog^}@cartoon.com"                                   =   0 =  OK 
     *   890 - assertIsTrue  "first\@last@iana.org"                                       =   0 =  OK 
     *   891 - assertIsTrue  "phil.h\@\@ck@haacked.com"                                   =   0 =  OK 
     *   892 - assertIsFalse "first.last@example.123"                                     =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   893 - assertIsFalse "first.last@comin"                                           =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   894 - assertIsTrue  "\"[[ test ]]\"@iana.orgin"                                  =   1 =  OK 
     *   895 - assertIsTrue  "Abc\@def@iana.org"                                          =   0 =  OK 
     *   896 - assertIsTrue  "Fred\ Bloggs@iana.org"                                      =   0 =  OK 
     *   897 - assertIsFalse "Joe.\Blow@iana.org"                                         =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   898 - assertIsFalse "first.last@sub.do,com"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   899 - assertIsFalse "first.last"                                                 =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   900 - assertIsTrue  "wild.wezyr@best-server-ever.com"                            =   0 =  OK 
     *   901 - assertIsTrue  "\"hello world\"@example.com"                                =   1 =  OK 
     *   902 - assertIsFalse "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com"             =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   903 - assertIsTrue  "John.\"The*$hizzle*Bizzle\".Doe@whatever.com"               =   1 =  OK 
     *   904 - assertIsTrue  "example+tag@gmail.com"                                      =   0 =  OK 
     *   905 - assertIsFalse ".ann..other.@example.com"                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   906 - assertIsTrue  "ann.other@example.com"                                      =   0 =  OK 
     *   907 - assertIsTrue  "something@something.something"                              =   0 =  OK 
     *   908 - assertIsTrue  "D.Oy'Smith@gmail.com"                                       =   0 =  OK 
     *   909 - assertIsTrue  "d_oy_smith@gmail.com"                                       =   0 =  OK 
     * 
     * 
     *   910 - assertIsTrue  "c@(Chris's host.)public.examplein"                          =   6 =  OK 
     *   911 - assertIsFalse "(foo)cal(bar)@(baz)iamcal.com(quux)"                        =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   912 - assertIsFalse "cal@iamcal(woo).(yay)comin"                                 = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   913 - assertIsFalse "cal(woo(yay)hoopla)@iamcal.comin"                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   914 - assertIsFalse "cal(foo\@bar)@iamcal.comin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   915 - assertIsFalse "cal(foo\)bar)@iamcal.comin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   916 - assertIsFalse "first().last@iana.orgin"                                    = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   917 - assertIsFalse "pete(his account)@silly.test(his host)"                     =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   918 - assertIsFalse "jdoe@machine(comment). examplein"                           = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   919 - assertIsFalse "first(abc.def).last@iana.orgin"                             = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   920 - assertIsFalse "first(a\"bc.def).last@iana.orgin"                           =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   921 - assertIsFalse "first.(\")middle.last(\")@iana.orgin"                       = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   922 - assertIsFalse "first(abc\(def)@iana.orgin"                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   923 - assertIsFalse "first.last@x(1234567890123456789012345678901234567890123456789012345678901234567890).comin" = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   924 - assertIsFalse "a(a(b(c)d(e(f))g)h(i)j)@iana.orgin"                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   925 - assertIsFalse "1234 @ local(blah) .machine .examplein"                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   926 - assertIsFalse "a@bin"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   927 - assertIsFalse "a@barin"                                                    =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   928 - assertIsFalse "@about.museum"                                              =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     * 
     * 
     *   929 - assertIsFalse "12345678901234567890123456789012345678901234567890123456789012345@iana.org" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   930 - assertIsFalse ".first.last@iana.org"                                       =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   931 - assertIsFalse "first.last.@iana.org"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   932 - assertIsFalse "first..last@iana.org"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   933 - assertIsFalse "\"first\"last\"@iana.org"                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   934 - assertIsFalse "\"\"\"@iana.org"                                            =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   935 - assertIsFalse "\"\\"@iana.org"                                             =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   936 - assertIsFalse "\"\"@iana.org"                                              =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   937 - assertIsFalse "first.last@"                                                =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *   938 - assertIsFalse "first.last@-xample.com"                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   939 - assertIsFalse "first.last@exampl-.com"                                     =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   940 - assertIsFalse "first.last@x234567890123456789012345678901234567890123456789012345678901234.iana.org" =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   941 - assertIsFalse "abc\@iana.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   942 - assertIsFalse "Doug\ \\"Ace\\"\ Lovell@iana.org"                           =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   943 - assertIsFalse "abc@def@iana.org"                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   944 - assertIsFalse "@iana.org"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   945 - assertIsFalse "doug@"                                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   946 - assertIsFalse "\"qu@iana.org"                                              =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   947 - assertIsFalse "ote\"@iana.org"                                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   948 - assertIsFalse ".dot@iana.org"                                              =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   949 - assertIsFalse "dot.@iana.org"                                              =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   950 - assertIsFalse "two..dot@iana.org"                                          =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   951 - assertIsFalse "\"Doug \"Ace\" L.\"@iana.org"                               =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   952 - assertIsFalse "Doug\ \\"Ace\\"\ L\.@iana.org"                              =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   953 - assertIsFalse "hello world@iana.org"                                       =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   954 - assertIsFalse "gatsby@f.sc.ot.t.f.i.tzg.era.l.d."                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   955 - assertIsFalse "test.iana.org"                                              =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   956 - assertIsFalse "test.@iana.org"                                             =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   957 - assertIsFalse "test..test@iana.org"                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   958 - assertIsFalse ".test@iana.org"                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   959 - assertIsFalse "test@test@iana.org"                                         =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   960 - assertIsFalse "test@@iana.org"                                             =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   961 - assertIsFalse "-- test --@iana.org"                                        =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   962 - assertIsFalse "[test]@iana.org"                                            =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   963 - assertIsFalse "\"test\"test\"@iana.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   964 - assertIsFalse "()[]\;:,><@iana.org"                                        =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   965 - assertIsFalse "test@."                                                     =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   966 - assertIsFalse "test@example."                                              =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   967 - assertIsFalse "test@.org"                                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   968 - assertIsFalse "test@[123.123.123.123"                                      =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   969 - assertIsFalse "test@123.123.123.123]"                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   970 - assertIsFalse "NotAnEmail"                                                 =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   971 - assertIsFalse "@NotAnEmail"                                                =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   972 - assertIsFalse "\"test\"blah\"@iana.org"                                    =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   973 - assertIsFalse ".wooly@iana.org"                                            =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   974 - assertIsFalse "wo..oly@iana.org"                                           =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   975 - assertIsFalse "pootietang.@iana.org"                                       =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   976 - assertIsFalse ".@iana.org"                                                 =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   977 - assertIsFalse "Ima Fool@iana.org"                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   978 - assertIsFalse "foo@[\1.2.3.4]"                                             =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   979 - assertIsFalse "first.\"\".last@iana.org"                                   =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   980 - assertIsFalse "first\last@iana.org"                                        =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *   981 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.567.89]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   982 - assertIsFalse "\"foo\"(yay)@(hoopla)[1.2.3.4]"                             =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   983 - assertIsFalse "cal(foo(bar)@iamcal.com"                                    =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   984 - assertIsFalse "cal(foo)bar)@iamcal.com"                                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   985 - assertIsFalse "cal(foo\)@iamcal.com"                                       =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   986 - assertIsFalse "first(middle)last@iana.org"                                 =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   987 - assertIsFalse "first(abc(\"def\".ghi).mno)middle(abc(\"def\".ghi).mno).last@(abc(\"def\".ghi).mno)example(abc(\"def\".ghi).mno).com" =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   988 - assertIsFalse "a(a(b(c)d(e(f))g)(h(i)j)@iana.org"                          =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   989 - assertIsFalse ".@"                                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   990 - assertIsFalse "@bar.com"                                                   =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   991 - assertIsFalse "@@bar.com"                                                  =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   992 - assertIsFalse "aaa.com"                                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   993 - assertIsFalse "aaa@.com"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   994 - assertIsFalse "aaa@.123"                                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   995 - assertIsFalse "aaa@[123.123.123.123]a"                                     =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   996 - assertIsFalse "aaa@[123.123.123.333]"                                      =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   997 - assertIsFalse "a@bar.com."                                                 =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   998 - assertIsFalse "a@-b.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   999 - assertIsFalse "a@b-.com"                                                   =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *  1000 - assertIsFalse "-@..com"                                                    =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1001 - assertIsFalse "-@a..com"                                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1002 - assertIsFalse "@about.museum-"                                             =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *  1003 - assertIsFalse "test@...........com"                                        =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *  1004 - assertIsFalse "first.last@[IPv6::]"                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1005 - assertIsFalse "first.last@[IPv6::::]"                                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1006 - assertIsFalse "first.last@[IPv6::b4]"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1007 - assertIsFalse "first.last@[IPv6::::b4]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1008 - assertIsFalse "first.last@[IPv6::b3:b4]"                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1009 - assertIsFalse "first.last@[IPv6::::b3:b4]"                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1010 - assertIsFalse "first.last@[IPv6:a1:::b4]"                                  =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1011 - assertIsFalse "first.last@[IPv6:a1:]"                                      =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *  1012 - assertIsFalse "first.last@[IPv6:a1:::]"                                    =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1013 - assertIsFalse "first.last@[IPv6:a1:a2:]"                                   =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1014 - assertIsFalse "first.last@[IPv6:a1:a2:::]"                                 =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1015 - assertIsFalse "first.last@[IPv6::11.22.33.44]"                             =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1016 - assertIsFalse "first.last@[IPv6::::11.22.33.44]"                           =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1017 - assertIsFalse "first.last@[IPv6:a1:11.22.33.44]"                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1018 - assertIsFalse "first.last@[IPv6:a1:::11.22.33.44]"                         =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1019 - assertIsFalse "first.last@[IPv6:a1:a2:::11.22.33.44]"                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1020 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.xx]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1021 - assertIsFalse "first.last@[IPv6:0123:4567:89ab:CDEFF::11.22.33.44]"        =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1022 - assertIsFalse "first.last@[IPv6:a1::a4:b1::b4:11.22.33.44]"                =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1023 - assertIsFalse "first.last@[IPv6:a1::11.22.33]"                             =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1024 - assertIsFalse "first.last@[IPv6:a1::11.22.33.44.55]"                       =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1025 - assertIsFalse "first.last@[IPv6:a1::b211.22.33.44]"                        =  48 =  OK    IP6-Adressteil: IPv4 in IPv6 - zu viele Zeichen im ersten IP4-Block
     *  1026 - assertIsFalse "first.last@[IPv6:a1::b2::11.22.33.44]"                      =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1027 - assertIsFalse "first.last@[IPv6:a1::b3:]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1028 - assertIsFalse "first.last@[IPv6::a2::b4]"                                  =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1029 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3:]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1030 - assertIsFalse "first.last@[IPv6::a2:a3:a4:b1:b2:b3:b4]"                    =   4 =  #### FEHLER ####    eMail-Adresse korrekt (IP6-Adresse)
     *  1031 - assertIsFalse "first.last@[IPv6:a1:a2:a3:a4::b1:b2:b3:b4]"                 =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1032 - assertIsFalse "first.last@[.12.34.56.78]"                                  =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *  1033 - assertIsFalse "first.last@[12.34.56.789]"                                  =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *  1034 - assertIsFalse "first.last@[::12.34.56.78]"                                 =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1035 - assertIsFalse "first.last@[IPv6:::12.34.56.78]"                            =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1036 - assertIsFalse "first.last@[IPv5:::12.34.56.78]"                            =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *  1037 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:12.34.56.78]"     =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1038 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:12.34.56.78]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1039 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777:8888:9999]" =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1040 - assertIsFalse "first.last@[IPv6:1111:2222::3333::4444:5555:6666]"          =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1041 - assertIsFalse "first.last@[IPv6:1111:2222:333x::4444:5555]"                =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *  1042 - assertIsFalse "first.last@[IPv6:1111:2222:33333::4444:5555]"               =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *  1043 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:::]"              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1044 - assertIsFalse "first.last@[IPv6:1111:2222:3333::5555:6666::]"              =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *  1045 - assertIsFalse "first.last@[IPv6:1111:2222:3333::4444:12.34.56.78]"         =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1046 - assertIsFalse "first.last@[IPv6:1111:2222:3333:4444:5555:6666:12.34.56.78]" =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1047 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:b2:b3:b4]"                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1048 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1:b2:b3::]"                   =  42 =  #### FEHLER ####    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *  1049 - assertIsTrue  "first.last@[IPv6:::]"                                       =   4 =  OK 
     *  1050 - assertIsTrue  "first.last@[IPv6:::b4]"                                     =   4 =  OK 
     *  1051 - assertIsTrue  "first.last@[IPv6:::b3:b4]"                                  =   4 =  OK 
     *  1052 - assertIsTrue  "first.last@[IPv6:a1::b4]"                                   =   4 =  OK 
     *  1053 - assertIsTrue  "first.last@[IPv6:a1::]"                                     =   4 =  OK 
     *  1054 - assertIsTrue  "first.last@[IPv6:a1:a2::]"                                  =   4 =  OK 
     *  1055 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::]"                    =   4 =  OK 
     *  1056 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::]"                    =   4 =  OK 
     *  1057 - assertIsTrue  "first.last@[IPv6:::a3:a4:b1:ffff:11.22.33.44]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1058 - assertIsTrue  "first.last@[IPv6:::a2:a3:a4:b1:ffff:11.22.33.44]"           =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1059 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4::11.22.33.44]"                 =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1060 - assertIsTrue  "first.last@[IPv6:a1:a2:a3:a4:b1::11.22.33.44]"              =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1061 - assertIsTrue  "first.last@[IPv6:a1::11.22.33.44]"                          =  62 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *  1062 - assertIsTrue  "first.last@[IPv6:a1:a2::11.22.33.44]"                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1063 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:cdef::11.22.33.44]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1064 - assertIsTrue  "first.last@[IPv6:0123:4567:89ab:CDEF::11.22.33.44]"         =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1065 - assertIsTrue  "first.last@[IPv6:a1::b2:11.22.33.44]"                       =  47 =  #### FEHLER ####    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *  1066 - assertIsTrue  "first.last@[IPv6:1111:2222:3333:4444:5555:6666:7777]"       =   4 =  OK 
     * 
     * 
     * ---- https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1067 - assertIsTrue  "jinujawad6s@gmail.com"                                      =   0 =  OK 
     *  1068 - assertIsTrue  "drp@drp.cz"                                                 =   0 =  OK 
     *  1069 - assertIsTrue  "tvf@tvf.cz"                                                 =   0 =  OK 
     *  1070 - assertIsTrue  "info@ermaelan.com"                                          =   0 =  OK 
     *  1071 - assertIsTrue  "drp@drp.cz"                                                 =   0 =  OK 
     *  1072 - assertIsTrue  "begeddov@jfinity.com"                                       =   0 =  OK 
     *  1073 - assertIsTrue  "vdv@dyomedea.com"                                           =   0 =  OK 
     *  1074 - assertIsTrue  "me@aaronsw.com"                                             =   0 =  OK 
     *  1075 - assertIsTrue  "aaron@theinfo.org"                                          =   0 =  OK 
     *  1076 - assertIsTrue  "rss-dev@yahoogroups.com"                                    =   0 =  OK 
     *  1077 - assertIsTrue  "tvf@tvf.cz"                                                 =   0 =  OK 
     * 
     * 
     * ---- https://en.wikipedia.org/wiki/Email_address/ ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1078 - assertIsTrue  "MaxMuster(Kommentar)@example.com"                           =   6 =  OK 
     *  1079 - assertIsTrue  "\"MaxMustermann\"@example.com"                              =   1 =  OK 
     *  1080 - assertIsTrue  "Max.\"Musterjunge\".Mustermann@example.com"                 =   1 =  OK 
     *  1081 - assertIsTrue  "\".John.Doe\"@example.com"                                  =   1 =  OK 
     *  1082 - assertIsTrue  "\"John.Doe.\"@example.com"                                  =   1 =  OK 
     *  1083 - assertIsTrue  "\"John..Doe\"@example.com"                                  =   1 =  OK 
     *  1084 - assertIsTrue  "john.smith(comment)@example.com"                            =   6 =  OK 
     *  1085 - assertIsTrue  "(comment)john.smith@example.com"                            =   6 =  OK 
     *  1086 - assertIsTrue  "john.smith@(comment)example.com"                            =   6 =  OK 
     *  1087 - assertIsTrue  "john.smith@example.com(comment)"                            =   6 =  OK 
     *  1088 - assertIsTrue  "john.smith@example.com"                                     =   0 =  OK 
     *  1089 - assertIsTrue  "jsmith@[192.168.2.1]"                                       =   2 =  OK 
     *  1090 - assertIsTrue  "jsmith@[IPv6:2001:db8::1]"                                  =   4 =  OK 
     *  1091 - assertIsTrue  "surelsaya@surabaya.vibriel.net.id"                          =   0 =  OK 
     *  1092 - assertIsTrue  "Marc Dupont <md118@example.com>"                            =   0 =  OK 
     *  1093 - assertIsTrue  "simple@example.com"                                         =   0 =  OK 
     *  1094 - assertIsTrue  "very.common@example.com"                                    =   0 =  OK 
     *  1095 - assertIsTrue  "disposable.style.email.with+symbol@example.com"             =   0 =  OK 
     *  1096 - assertIsTrue  "other.email-with-hyphen@example.com"                        =   0 =  OK 
     *  1097 - assertIsTrue  "fully-qualified-domain@example.com"                         =   0 =  OK 
     *  1098 - assertIsTrue  "user.name+tag+sorting@example.com"                          =   0 =  OK 
     *  1099 - assertIsTrue  "user+mailbox/department=shipping@example.com"               =   0 =  OK 
     *  1100 - assertIsTrue  "!#$%&'*+-/=?^_`.{|}~@example.com"                           =   0 =  OK 
     *  1101 - assertIsTrue  "x@example.com"                                              =   0 =  OK 
     *  1102 - assertIsTrue  "info@firma.org"                                             =   0 =  OK 
     *  1103 - assertIsTrue  "example-indeed@strange-example.com"                         =   0 =  OK 
     *  1104 - assertIsTrue  "admin@mailserver1"                                          =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1105 - assertIsTrue  "example@s.example"                                          =   0 =  OK 
     *  1106 - assertIsTrue  "\" \"@example.org"                                          =   1 =  OK 
     *  1107 - assertIsTrue  "\"john..doe\"@example.org"                                  =   1 =  OK 
     *  1108 - assertIsTrue  "mailhost!username@example.org"                              =   0 =  OK 
     *  1109 - assertIsTrue  "user%example.com@example.org"                               =   0 =  OK 
     *  1110 - assertIsTrue  "joe25317@NOSPAMexample.com"                                 =   0 =  OK 
     *  1111 - assertIsFalse "Abc.example.com"                                            =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *  1112 - assertIsFalse "Abc..123@example.com"                                       =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *  1113 - assertIsFalse "A@b@c@example.com"                                          =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *  1114 - assertIsFalse "a\"b(c)d,e:f;g<h>i[j\k]l@example.com"                       =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1115 - assertIsFalse "just\"not\"right@example.com"                               =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *  1116 - assertIsFalse "this is\"not\allowed@example.com"                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1117 - assertIsFalse "this\ still\\"not\\allowed@example.com"                     =  84 =  OK    String: Ungueltige Escape-Sequenz im String
     *  1118 - assertIsFalse "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *  1119 - assertIsFalse "i_like_underscore@but_Underscore_is_not_allowed_in_domain_part.com" =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     * 
     * 
     * ---- unsupported ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1120 - assertIsTrue  "Loïc.Accentué@voilà.fr"                                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1121 - assertIsTrue  "Ärger.Öde@Übel.de"                                          =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1122 - assertIsTrue  "Smørrebrød@danmark.dk"                                      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1123 - assertIsTrue  "ip.without.brackets@1.2.3.4"                                =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *  1124 - assertIsTrue  "ip.without.brackets@1:2:3:4:5:6:7:8"                        =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1125 - assertIsTrue  "(space after comment) john.smith@example.com"               =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *  1126 - assertIsTrue  "email.address.without@topleveldomain"                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *  1127 - assertIsTrue  "EmailAddressWithout@PointSeperator"                         =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     * 
     * 
     * ---- Fillup ----------------------------------------------------------------------------------------------------
     * 
     * 
     *  1128 - assertIsTrue  "valid.email.from.nr502@fillup.tofalse.com"                  =   0 =  OK 
     *           ...
     *  1129 - assertIsTrue  "valid.email.to.nr626@fillup.tofalse.com"                    =   0 =  OK 
     * 
     * 
     * ---- Statistik ----------------------------------------------------------------------------------------------------
     * 
     *   ASSERT_IS_TRUE   626   KORREKT  585 =   93.450 % | FALSCH ERKANNT   41 =    6.550 % 
     *   ASSERT_IS_FALSE  626   KORREKT  618 =   98.722 % | FALSCH ERKANNT    8 =    1.278 % 
     * 
     *   GESAMT          1252   KORREKT 1203 =   96.086 % | FALSCH ERKANNT   49 =    3.914 %
     *  
     */

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

      assertIsFalse( "@%^%#$@#$@#.com" );
      assertIsFalse( "@domain.com" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@domain@domain.com" );
      assertIsFalse( "@@@@@@gmail.com" );
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
      assertIsTrue( "domain.part@with_underscore.com" );
      assertIsFalse( "domain.part@-starts.with.hyphen.com" );
      assertIsFalse( "domain.part@ends.with.hyphen.com-" );
      assertIsFalse( "domain.part@with&amp.com" );
      assertIsFalse( "domain.part@with*asterisk.com" );
      assertIsFalse( "domain.part@with$dollar.com" );
      assertIsFalse( "domain.part@with=equality.com" );
      assertIsFalse( "domain.part@with!exclamation.com" );
      assertIsFalse( "domain.part@with?question.com" );
      assertIsFalse( "domain.part@with`grave-accent.com" );
      assertIsFalse( "domain.part@with#hash.com" );
      assertIsFalse( "domain.part@with%percentage.com" );
      assertIsFalse( "domain.part@with|pipe.com" );
      assertIsFalse( "domain.part@with+plus.com" );
      assertIsFalse( "domain.part@with{leftbracket.com" );
      assertIsFalse( "domain.part@with}rightbracket.com" );
      assertIsFalse( "domain.part@with(leftbracket.com" );
      assertIsFalse( "domain.part@with)rightbracket.com" );
      assertIsFalse( "domain.part@with[leftbracket.com" );
      assertIsFalse( "domain.part@with]rightbracket.com" );
      assertIsFalse( "domain.part@with~tilde.com" );
      assertIsFalse( "domain.part@with^xor.com" );
      assertIsFalse( "domain.part@with:colon.com" );

      assertIsFalse( "DomainHyphen@-atstart" );
      assertIsFalse( "DomainHyphen@atend-.com" );
      assertIsFalse( "DomainHyphen@bb.-cc" );
      assertIsFalse( "DomainHyphen@bb.-cc-" );
      assertIsFalse( "DomainHyphen@bb.cc-" );
      assertIsFalse( "DomainHyphen@bb.c-c" ); // https://tools.ietf.org/id/draft-liman-tld-names-01.html
      assertIsFalse( "DomainNotAllowedCharacter@/atstart" );
      assertIsFalse( "DomainNotAllowedCharacter@a,start" );
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
      assertIsFalse( "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}" );

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
      assertIsFalse( "ABC.DEF@[]" );
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
      assertIsTrue( "ABC.DEF@[2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[IPv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[IPA6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsFalse( "ABC.DEF@[APv6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@[aaa6:2001:0db8:85a3:0000::8a2e:0370:7334]" );
      assertIsTrue( "ABC.DEF@2001:0db8:85a3:0000:0000:8a2e:0370:7334" );

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

      assertIsTrue( "\"()<>[]:,;@\\\\\\\"!#$%&'*+-/=?^_`{}| ~.a\"@example.org" );

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
      assertIsFalse( "username@example,com" );
      assertIsFalse( "usern,ame@example.com" );
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
      assertIsTrue( "\"user,name\"@example.com" );
      assertIsTrue( "\"user name\"@example.com" );
      assertIsTrue( "\"user@name\"@example.com" );
      assertIsFalse( "\"\\a\"@iana.org" );
      assertIsTrue( "\"test\\ test\"@iana.org" );
      assertIsFalse( "\"\"@iana.org" );
      assertIsFalse( "\"\"@[]" );/* kind of an edge case, valid for RFC 5322 but address literal is not for 5321 */
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
      assertIsFalse( "_@bde.cc," );
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
      assertIsFalse( "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com" );
      assertIsFalse( "just\"not\"right@example.com" );
      assertIsFalse( "this is\"not\\allowed@example.com" );
      assertIsFalse( "this\\ still\\\"not\\allowed@example.com" );
      assertIsFalse( "john..doe@example.com" );
      assertIsFalse( "john.doe@example..com" );

      assertIsTrue( "email@example.com" );
      assertIsTrue( "email@example.co.uk" );
      assertIsTrue( "email@mail.gmail.com" );
      assertIsTrue( "unusual+but+valid+email1900=/!#$%&\\'*+-/=?^_`.{|}~@example.com" );
      assertIsFalse( "email@example.co.uk." );
      assertIsFalse( "email@example" );
      assertIsFalse( " email@example.com" );
      assertIsFalse( "email@example.com " );
      assertIsFalse( "email@example,com " );
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
      assertIsTrue( "alex@example.com" );
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
      wlHeadline( "https://github.com/dotnet/docs/issues/6620" );
      wl( "" );

      assertIsTrue( "jkt@gmail.com" );

      assertIsFalse( " jkt@gmail.com" );
      assertIsFalse( "jkt@ gmail.com" );
      assertIsFalse( "jkt@g mail.com" );
      assertIsFalse( "jkt @gmail.com" );
      assertIsFalse( "j kt@gmail.com" );

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
      assertIsFalse( "invalid" );
      assertIsFalse( "invalid-characters-in-sld@! \"#$%(),/;<>_[]`|.org" );
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
      assertIsTrue( "\"first\\\\last\"@iana.org" );
      assertIsFalse( "first.last@[IPv6:1111:2222:3333::4444:5555:12.34.56.78]" );
      assertIsTrue( "first.last@[IPv6:1111:2222:3333::4444:5555:6666:7777]" );
      assertIsTrue( "\"Abc\\@def\"@iana.org" );
      assertIsTrue( "\"Fred\\ Bloggs\"@iana.org" );
      assertIsTrue( "\"Joe.\\\\Blow\"@iana.org" );
      assertIsTrue( "\"Abc@def\"@iana.org" );
      assertIsTrue( "\"Fred Bloggs\"@iana.orgin" );
      assertIsTrue( "\"Abc\\@def\"@iana.org" );
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
      assertIsTrue( "\"first\\\"last\"@iana.org" );
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
      assertIsFalse( "first.last@sub.do,com" );
      assertIsFalse( "first.last" );

      assertIsTrue( "wild.wezyr@best-server-ever.com" );
      assertIsTrue( "\"hello world\"@example.com" );
      assertIsFalse( "John..\"The*$hizzle*Bizzle\"..Doe@whatever.com" );
      assertIsTrue( "John.\"The*$hizzle*Bizzle\".Doe@whatever.com" );
      assertIsTrue( "example+tag@gmail.com" );
      assertIsFalse( ".ann..other.@example.com" );
      assertIsTrue( "ann.other@example.com" );
      assertIsTrue( "something@something.something" );
      assertIsTrue( "D.Oy'Smith@gmail.com" );
      assertIsTrue( "d_oy_smith@gmail.com" );

      wl( "" );
      wl( "" );

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

      wl( "" );
      wl( "" );

      assertIsFalse( "12345678901234567890123456789012345678901234567890123456789012345@iana.org" );
      assertIsFalse( ".first.last@iana.org" );
      assertIsFalse( "first.last.@iana.org" );
      assertIsFalse( "first..last@iana.org" );
      assertIsFalse( "\"first\"last\"@iana.org" );
      assertIsFalse( "\"\"\"@iana.org" );
      assertIsFalse( "\"\\\"@iana.org" );
      assertIsFalse( "\"\"@iana.org" );
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
      assertIsFalse( "()[]\\;:,><@iana.org" );
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
      wlHeadline( "https://chillyfacts.com/check-an-email-exist-using-java-java-email-verification-and-validation/" );
      wl( "" );

      assertIsTrue( "jinujawad6s@gmail.com" );
      assertIsTrue( "drp@drp.cz" );
      assertIsTrue( "tvf@tvf.cz" );
      assertIsTrue( "info@ermaelan.com" );
      assertIsTrue( "drp@drp.cz" );
      assertIsTrue( "begeddov@jfinity.com" );
      assertIsTrue( "vdv@dyomedea.com" );
      assertIsTrue( "me@aaronsw.com" );
      assertIsTrue( "aaron@theinfo.org" );
      assertIsTrue( "rss-dev@yahoogroups.com" );
      assertIsTrue( "tvf@tvf.cz" );

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

    wl( "  ASSERT_IS_TRUE  " + getStringZahl( COUNT_ASSERT_IS_TRUE ) + "   KORREKT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_ok_proz_true_korrekt_erkannt ) + m_number_format.format( email_ok_proz_true_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( T_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_true_korrekt_erkannt ) + m_number_format.format( email_false_proz_true_korrekt_erkannt ) + " % = " + T_RESULT_COUNT_ERROR );
    wl( "  ASSERT_IS_FALSE " + getStringZahl( COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_ok_proz_false_korrekt_erkannt ) + m_number_format.format( email_ok_proz_false_korrekt_erkannt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE ) + " = " + getEinzug( email_false_proz_false_korrekt_erkannt ) + m_number_format.format( email_false_proz_false_korrekt_erkannt ) + " % = " + F_RESULT_COUNT_ERROR );
    wl( "" );
    wl( "  GESAMT          " + getStringZahl( COUNT_ASSERT_IS_TRUE + COUNT_ASSERT_IS_FALSE ) + "   KORREKT " + getStringZahl( ( T_RESULT_COUNT_EMAIL_IS_TRUE + F_RESULT_COUNT_EMAIL_IS_FALSE ) ) + " = " + getEinzug( email_ok_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_ok_proz_korrekt_erkannt_insgesamt ) + " % | FALSCH ERKANNT " + getStringZahl( F_RESULT_COUNT_EMAIL_IS_TRUE + T_RESULT_COUNT_EMAIL_IS_FALSE ) + " = " + getEinzug( email_false_proz_korrekt_erkannt_insgesamt ) + m_number_format.format( email_false_proz_korrekt_erkannt_insgesamt ) + " % = " + ( T_RESULT_COUNT_ERROR + F_RESULT_COUNT_ERROR ) );
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
