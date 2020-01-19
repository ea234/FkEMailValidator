package de.fk.email;

class TestValidateEMailAdresse1
{
  public static void main( String[] args )
  {
    /*
     * https://stackoverflow.com/questions/1365407/c-sharp-code-to-validate-email-address?page=2&tab=votes#tab-top
     * 
     * Corniel Nobel answered Oct 30 '19 at 10:10 
     * 
     * NR      Fkt.          Input                                                Result
     * 
     * ---- General Correct ----------------------------------------------------------------------------------------------------
     * 
     *     1 - assertIsTrue  A.B@C.DE                                           =   0 =  OK 
     *     2 - assertIsTrue  A."B"@C.DE                                         =   1 =  OK 
     *     3 - assertIsTrue  A.B@[1.2.3.4]                                      =   2 =  OK 
     *     4 - assertIsTrue  A."B"@[1.2.3.4]                                    =   3 =  OK 
     *     5 - assertIsTrue  A.B@[IPv6:1:2:3:4:5:6:7:8]                         =   4 =  OK 
     *     6 - assertIsTrue  A."B"@[IPv6:1:2:3:4:5:6:7:8]                       =   5 =  OK 
     *     7 - assertIsTrue  (A)B@C.DE                                          =   6 =  OK 
     *     8 - assertIsTrue  A(B)@C.DE                                          =   6 =  OK 
     *     9 - assertIsTrue  (A)"B"@C.DE                                        =   7 =  OK 
     *    10 - assertIsTrue  "A"(B)@C.DE                                        =   7 =  OK 
     *    11 - assertIsTrue  (A)B@[1.2.3.4]                                     =   2 =  OK 
     *    12 - assertIsTrue  A(B)@[1.2.3.4]                                     =   2 =  OK 
     *    13 - assertIsTrue  (A)"B"@[1.2.3.4]                                   =   8 =  OK 
     *    14 - assertIsTrue  "A"(B)@[1.2.3.4]                                   =   8 =  OK 
     *    15 - assertIsTrue  (A)B@[IPv6:1:2:3:4:5:6:7:8]                        =   4 =  OK 
     *    16 - assertIsTrue  A(B)@[IPv6:1:2:3:4:5:6:7:8]                        =   4 =  OK 
     *    17 - assertIsTrue  (A)"B"@[IPv6:1:2:3:4:5:6:7:8]                      =   9 =  OK 
     *    18 - assertIsTrue  "A"(B)@[IPv6:1:2:3:4:5:6:7:8]                      =   9 =  OK 
     *    19 - assertIsTrue  2@bde.cc                                           =   0 =  OK 
     *    20 - assertIsTrue  -@bde.cc                                           =   0 =  OK 
     *    21 - assertIsTrue  a2@bde.cc                                          =   0 =  OK 
     *    22 - assertIsTrue  a-b@bde.cc                                         =   0 =  OK 
     *    23 - assertIsTrue  ab@b-de.cc                                         =   0 =  OK 
     *    24 - assertIsTrue  a+b@bde.cc                                         =   0 =  OK 
     *    25 - assertIsTrue  f.f.f@bde.cc                                       =   0 =  OK 
     *    26 - assertIsTrue  ab_c@bde.cc                                        =   0 =  OK 
     *    27 - assertIsTrue  _-_@bde.cc                                         =   0 =  OK 
     *    28 - assertIsTrue  k.haak@12move.nl                                   =   0 =  OK 
     *    29 - assertIsTrue  K.HAAK@12MOVE.NL                                   =   0 =  OK 
     *    30 - assertIsTrue  email@domain.com                                   =   0 =  OK 
     * 
     * ---- No Input ----------------------------------------------------------------------------------------------------
     * 
     *    31 - assertIsFalse null                                               =  10 =  OK    Laenge: Eingabe ist null
     *    32 - assertIsFalse                                                    =  11 =  OK    Laenge: Eingabe ist Leerstring
     *    33 - assertIsFalse                                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- AT-Character ----------------------------------------------------------------------------------------------------
     * 
     *    34 - assertIsFalse ABCDEFGHIJKLMNOP                                   =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    35 - assertIsFalse ABC.DEF.GHI.JKL                                    =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    36 - assertIsFalse @GHI.JKL                                           =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    37 - assertIsFalse ABC.DEF@                                           =  27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *    38 - assertIsFalse ABC.DEF@@GHI.JKL                                   =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     * 
     * ---- Seperator ----------------------------------------------------------------------------------------------------
     * 
     *    39 - assertIsFalse ABCDEF@GHIJKL                                      =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    40 - assertIsFalse ABC.DEF@GHIJKL                                     =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    41 - assertIsFalse .ABC.DEF@GHI.JKL                                   =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    42 - assertIsFalse ABC.DEF@GHI.JKL.                                   =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    43 - assertIsFalse ABC..DEF@GHI.JKL                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    44 - assertIsFalse ABC.DEF@GHI..JKL                                   =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    45 - assertIsFalse ABC.DEF@GHI.JKL..                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    46 - assertIsFalse ABC.DEF.@GHI.JKL                                   =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    47 - assertIsFalse ABC.DEF@.GHI.JKL                                   =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    48 - assertIsFalse ABC.DEF@.                                          =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     * 
     * ---- Characters ----------------------------------------------------------------------------------------------------
     * 
     *    49 - assertIsTrue  ABC1.DEF2@GHI3.JKL4                                =   0 =  OK 
     *    50 - assertIsTrue  ABC.DEF_@GHI.JKL                                   =   0 =  OK 
     *    51 - assertIsTrue  #ABC.DEF@GHI.JKL                                   =   0 =  OK 
     *    52 - assertIsTrue  ABC.DEF@GHI.JK2                                    =   0 =  OK 
     *    53 - assertIsTrue  ABC.DEF@2HI.JKL                                    =   0 =  OK 
     *    54 - assertIsFalse ABC.DEF@GHI.2KL                                    =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    55 - assertIsFalse ABC.DEF@GHI.JK-                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    56 - assertIsFalse ABC.DEF@GHI.JK_                                    =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    57 - assertIsFalse ABC.DEF@-HI.JKL                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    58 - assertIsFalse ABC.DEF@_HI.JKL                                    =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    59 - assertIsFalse ABC DEF@GHI.DE                                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    60 - assertIsFalse A . B & C . D                                      =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    61 - assertIsFalse (?).[!]@{&}.<:>                                    =  18 =  OK    Struktur: Fehler in Adress-String-X
     *    62 - assertIsTrue  {local{name{{with{@leftbracket.com                 =   0 =  OK 
     *    63 - assertIsTrue  }local}name}}with{@rightbracket.com                =   0 =  OK 
     *    64 - assertIsTrue  |local||name|with|@pipe.com                        =   0 =  OK 
     *    65 - assertIsTrue  %local%%name%with%@percentage.com                  =   0 =  OK 
     *    66 - assertIsTrue  $local$$name$with$@dollar.com                      =   0 =  OK 
     *    67 - assertIsTrue  &local&&name&with&$@amp.com                        =   0 =  OK 
     *    68 - assertIsTrue  #local##name#with#@hash.com                        =   0 =  OK 
     *    69 - assertIsTrue  ~local~~name~with~@tilde.com                       =   0 =  OK 
     *    70 - assertIsTrue  !local!!name!with!@exclamation.com                 =   0 =  OK 
     *    71 - assertIsTrue  ?local??name?with?@question.com                    =   0 =  OK 
     *    72 - assertIsTrue  *local**name*with*@asterisk.com                    =   0 =  OK 
     *    73 - assertIsTrue  `local``name`with`@grave-accent.com                =   0 =  OK 
     *    74 - assertIsTrue  ^local^^name^with^@xor.com                         =   0 =  OK 
     *    75 - assertIsTrue  =local==name=with=@equality.com                    =   0 =  OK 
     *    76 - assertIsTrue  +local++name+with+@equality.com                    =   0 =  OK 
     *    77 - assertIsFalse email@{leftbracket.com                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    78 - assertIsFalse email@rightbracket}.com                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    79 - assertIsFalse email@p|pe.com                                     =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    80 - assertIsFalse isis@100%.nl                                       =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    81 - assertIsFalse email@dollar$.com                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    82 - assertIsFalse email@r&amp;d.com                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    83 - assertIsFalse email@#hash.com                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    84 - assertIsFalse email@wave~tilde.com                               =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    85 - assertIsFalse email@exclamation!mark.com                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    86 - assertIsFalse email@question?mark.com                            =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    87 - assertIsFalse email@obelix*asterisk.com                          =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    88 - assertIsFalse email@grave`accent.com                             =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    89 - assertIsFalse email@colon:colon.com                              =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    90 - assertIsFalse email@caret^xor.com                                =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    91 - assertIsFalse email@=qowaiv.com                                  =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    92 - assertIsFalse email@plus+.com                                    =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    93 - assertIsFalse email@domain.com>                                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *    94 - assertIsFalse email@mailto:domain.com                            =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    95 - assertIsFalse mailto:mailto:email@domain.com                     =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    96 - assertIsFalse email@-domain.com                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    97 - assertIsFalse email@domain-.com                                  =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    98 - assertIsFalse email@domain.com-                                  =  24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    99 - assertIsFalse Joe Smith &lt;email@domain.com&gt;                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   100 - assertIsTrue  ?????@domain.com                                   =   0 =  OK 
     *   101 - assertIsTrue  local@?????.com                                    =  21 =  #### FEHLER ####    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   102 - assertIsTrue  email@domain-one.com                               =   0 =  OK 
     *   103 - assertIsTrue  _______@domain.com                                 =   0 =  OK 
     * 
     * ---- IP V4 ----------------------------------------------------------------------------------------------------
     * 
     *   104 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =   2 =  OK 
     *   105 - assertIsTrue  ABC.DEF@[001.002.003.004]                          =   2 =  OK 
     *   106 - assertIsTrue  "ABC.DEF"@[127.0.0.1]                              =   3 =  OK 
     *   107 - assertIsFalse ABC.DEF[1.2.3.4]                                   =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   108 - assertIsFalse [1.2.3.4]@[5.6.7.8]                                =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   109 - assertIsFalse ABC.DEF[@1.2.3.4]                                  =  51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   110 - assertIsFalse "[1.2.3.4]"@[5.6.7.8]                              =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   111 - assertIsFalse ABC.DEF@MyDomain[1.2.3.4]                          =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   112 - assertIsFalse ABC.DEF@[1.00002.3.4]                              =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   113 - assertIsFalse ABC.DEF@[1.2.3.456]                                =  54 =  OK    IP4-Adressteil: Byte-Overflow
     *   114 - assertIsFalse ABC.DEF@[..]                                       =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   115 - assertIsFalse ABC.DEF@[.2.3.4]                                   =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   116 - assertIsFalse ABC.DEF@[]                                         =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   117 - assertIsFalse ABC.DEF@[1]                                        =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   118 - assertIsFalse ABC.DEF@[1.2]                                      =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   119 - assertIsFalse ABC.DEF@[1.2.3]                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   120 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =   2 =  OK 
     *   121 - assertIsFalse ABC.DEF@[1.2.3.4.5]                                =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   122 - assertIsFalse ABC.DEF@[1.2.3.4.5.6]                              =  56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   123 - assertIsFalse ABC.DEF@[MyDomain.de]                              =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   124 - assertIsFalse ABC.DEF@[1.2.3.]                                   =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   125 - assertIsFalse ABC.DEF@[1.2.3. ]                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   126 - assertIsFalse ABC.DEF@[1.2.3.4].de                               =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   127 - assertIsFalse ABC.DE@[1.2.3.4][5.6.7.8]                          =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   128 - assertIsFalse ABC.DEF@[1.2.3.4                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   129 - assertIsFalse ABC.DEF@1.2.3.4]                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   130 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =   2 =  OK 
     *   131 - assertIsFalse ABC.DEF@[1.2.3.Z]                                  =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   132 - assertIsFalse ABC.DEF@[12.34]                                    =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   133 - assertIsFalse ABC.DEF@[1.2.3.]                                   =  58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   134 - assertIsFalse ABC.DEF@[1.2.3.4]                                  =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   135 - assertIsFalse ABC.DEF@[1.2.3.4                                   =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   136 - assertIsFalse ABC.DEF@[1234.5.6.7]                               =  53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   137 - assertIsFalse ABC.DEF@[1.2...3.4]                                =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   138 - assertIsFalse email@111.222.333                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   139 - assertIsFalse email@111.222.333.256                              =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   140 - assertIsFalse email@[123.123.123.123                             =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   141 - assertIsFalse email@[123.123.123].123                            =  57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   142 - assertIsFalse email@123.123.123.123]                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   143 - assertIsFalse email@123.123.[123.123]                            =  52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   144 - assertIsFalse ab@988.120.150.10                                  =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   145 - assertIsFalse ab@120.256.256.120                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   146 - assertIsFalse ab@120.25.1111.120                                 =  23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   147 - assertIsFalse ab@[188.120.150.10                                 =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   148 - assertIsFalse ab@188.120.150.10]                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   149 - assertIsFalse ab@[188.120.150.10].com                            =  60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     * 
     * ---- IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   150 - assertIsTrue  ABC.DEF@[IPv6:2001:db8::1]                         =   4 =  OK 
     *   151 - assertIsFalse ABC@[IP                                            =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   152 - assertIsFalse ABC@[IPv6]                                         =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   153 - assertIsFalse ABC@[IPv6:]                                        =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   154 - assertIsFalse ABC@[IPv6:1]                                       =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   155 - assertIsFalse ABC@[IPv6:1:2]                                     =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   156 - assertIsTrue  ABC@[IPv6:1:2:3]                                   =   4 =  OK 
     *   157 - assertIsTrue  ABC@[IPv6:1:2:3:4]                                 =   4 =  OK 
     *   158 - assertIsFalse ABC@[IPv6:1:2:3:4:5:]                              =  44 =  OK    IP6-Adressteil: ungueltige Kombination ":]"
     *   159 - assertIsTrue  ABC@[IPv6:1:2:3:4:5:6]                             =   4 =  OK 
     *   160 - assertIsTrue  ABC@[IPv6:1:2:3:4:5:6:7]                           =   4 =  OK 
     *   161 - assertIsTrue  ABC@[IPv6:1:2:3:4:5:6:7:8]                         =   4 =  OK 
     *   162 - assertIsFalse ABC@[IPv6:1:2:3:4:5:6:7:8:9]                       =  42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *   163 - assertIsFalse ABC@[IPv4:1:2:3:4]                                 =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   164 - assertIsFalse ABC@[I127.0.0.1]                                   =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   165 - assertIsFalse ABC@[D127.0.0.1]                                   =  59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   166 - assertIsFalse ABC@[iPv6:2001:db8::1]                             =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   167 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8]                      =   4 =  OK 
     *   168 - assertIsFalse ABC.DEF@[IPv6:1:2:3::5::7:8]                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   169 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6]                         =   4 =  OK 
     *   170 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:Z]                         =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   171 - assertIsFalse ABC.DEF@[IPv6:12:34]                               =  43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   172 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:]                          =  44 =  OK    IP6-Adressteil: ungueltige Kombination ":]"
     *   173 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6]                         =  45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   174 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6                          =  61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   175 - assertIsFalse ABC.DEF@[IPv6:12345:6:7:8:9]                       =  46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   176 - assertIsFalse ABC.DEF@[IPv6:1:2:3:::6:7:8]                       =  50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   177 - assertIsTrue  local@[2001:0db8:85a3:0000:0000:8a2e:0370:7334]    =   4 =  OK 
     *   178 - assertIsTrue  local@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] =   4 =  OK 
     *   179 - assertIsFalse local@[IPA6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] =  40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   180 - assertIsFalse local@[APv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] =  49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   181 - assertIsTrue  local@[aaa6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] =   4 =  OK 
     *   182 - assertIsTrue  local@2001:0db8:85a3:0000:0000:8a2e:0370:7334      =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- IP V4 embedded in IP V6 ----------------------------------------------------------------------------------------------------
     * 
     *   183 - assertIsTrue  ABC.DEF@[IPv6::FFFF:127.0.0.1]                     =   4 =  OK 
     *   184 - assertIsTrue  ABC.DEF@[IPv6::ffff:127.0.0.1]                     =   4 =  OK 
     *   185 - assertIsTrue  ABC.DEF@[::FFFF:127.0.0.1]                         =   4 =  OK 
     *   186 - assertIsTrue  ABC.DEF@[::ffff:127.0.0.1]                         =   4 =  OK 
     *   187 - assertIsFalse ABC.DEF@[IPv6::ffff:.127.0.1]                      =  55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   188 - assertIsFalse ABC.DEF@[IPv6::fff:127.0.0.1]                      =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   189 - assertIsFalse ABC.DEF@[IPv6::1234:127.0.0.1]                     =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   190 - assertIsFalse ABC.DEF@[IPv6:127.0.0.1]                           =  47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   191 - assertIsFalse ABC.DEF@[IPv6:::127.0.0.1]                         =  62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     * 
     * ---- Strings ----------------------------------------------------------------------------------------------------
     * 
     *   192 - assertIsTrue  "ABC.DEF"@GHI.DE                                   =   1 =  OK 
     *   193 - assertIsTrue  "ABC DEF"@GHI.DE                                   =   1 =  OK 
     *   194 - assertIsTrue  "ABC@DEF"@GHI.DE                                   =   1 =  OK 
     *   195 - assertIsFalse "ABC DEF@G"HI.DE                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   196 - assertIsFalse ""@GHI.DE                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   197 - assertIsFalse "ABC.DEF@G"HI.DE                                   =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   198 - assertIsFalse A@G"HI.DE                                          =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   199 - assertIsFalse "@GHI.DE                                           =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   200 - assertIsFalse ABC.DEF."                                          =  85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   201 - assertIsFalse ABC.DEF@""                                         =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   202 - assertIsFalse ABC.DEF@G"HI.DE                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   203 - assertIsFalse ABC.DEF@GHI.DE"                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   204 - assertIsFalse ABC.DEF@"GHI.DE                                    =  82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   205 - assertIsFalse "Escape.Sequenz.Ende"                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   206 - assertIsFalse ABC.DEF"GHI.DE                                     =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   207 - assertIsFalse ABC.DEF"@GHI.DE                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   208 - assertIsFalse ABC.DE"F@GHI.DE                                    =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   209 - assertIsFalse "ABC.DEF@GHI.DE                                    =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   210 - assertIsFalse "ABC.DEF@GHI.DE"                                   =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   211 - assertIsTrue  ".ABC.DEF"@GHI.DE                                  =   1 =  OK 
     *   212 - assertIsTrue  "ABC.DEF."@GHI.DE                                  =   1 =  OK 
     *   213 - assertIsTrue  "ABC".DEF."GHI"@JKL.de                             =   1 =  OK 
     *   214 - assertIsFalse A"BC".DEF."GHI"@JKL.de                             =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   215 - assertIsFalse "ABC".DEF.G"HI"@JKL.de                             =  81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   216 - assertIsFalse "AB"C.DEF."GHI"@JKL.de                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   217 - assertIsFalse "ABC".DEF."GHI"J@KL.de                             =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   218 - assertIsFalse "AB"C.D"EF"@GHI.DE                                 =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   219 - assertIsFalse "Ende.am.Eingabeende"                              =  88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   220 - assertIsFalse 0"00.000"@GHI.JKL                                  =  80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     * 
     * ---- Comments ----------------------------------------------------------------------------------------------------
     * 
     *   221 - assertIsTrue  (ABC)DEF@GHI.JKL                                   =   6 =  OK 
     *   222 - assertIsTrue  ABC(DEF)@GHI.JKL                                   =   6 =  OK 
     *   223 - assertIsTrue  ABC.DEF@GHI.JKL(MNO)                               =   6 =  OK 
     *   224 - assertIsFalse (ABC).DEF@GHI.JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   225 - assertIsFalse ABC.(DEF)@GHI.JKL                                  = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   226 - assertIsFalse ABC.DEF@(GHI).JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   227 - assertIsFalse ABC.DEF@GHI.(JKL).MNO                              = 102 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Local Part
     *   228 - assertIsFalse ABC.DEF@GHI.JK(L.M)NO                              = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   229 - assertIsFalse AB(CD)EF@GHI.JKL                                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   230 - assertIsFalse AB.(CD).EF@GHI.JKL                                 = 101 =  OK    Kommentar: Falsche Zeichenkombination ".(" im Domain Part
     *   231 - assertIsFalse AB."(CD)".EF@GHI.JKL                               =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   232 - assertIsFalse (ABCDEF)@GHI.JKL                                   =  98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   233 - assertIsFalse (ABCDEF).@GHI.JKL                                  = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   234 - assertIsFalse (AB"C)DEF@GHI.JKL                                  =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   235 - assertIsFalse (AB\C)DEF@GHI.JKL                                  =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   236 - assertIsFalse (AB\@C)DEF@GHI.JKL                                 =  91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   237 - assertIsFalse ABC(DEF@GHI.JKL                                    =  93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   238 - assertIsFalse ABC.DEF@GHI)JKL                                    =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   239 - assertIsFalse )ABC.DEF@GHI.JKL                                   =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   240 - assertIsFalse ABC(DEF@GHI).JKL                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   241 - assertIsFalse ABC(DEF.GHI).JKL                                   = 103 =  OK    Kommentar: Falsche Zeichenkombination ")."
     *   242 - assertIsFalse (ABC.DEF@GHI.JKL)                                  =  95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   243 - assertIsFalse (A(B(C)DEF@GHI.JKL                                 =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   244 - assertIsFalse (A)B)C)DEF@GHI.JKL                                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   245 - assertIsFalse (A)BCDE(F)@GHI.JKL                                 =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   246 - assertIsTrue  (comment)john.smith@example.com                    =   6 =  OK 
     *   247 - assertIsTrue  john.smith(comment)@example.com                    =   6 =  OK 
     *   248 - assertIsTrue  john.smith@(comment)example.com                    =   6 =  OK 
     *   249 - assertIsTrue  john.smith@example.com(comment)                    =  15 =  #### FEHLER ####    Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
     *   250 - assertIsFalse john.smith@exampl(comment)e.com                    = 100 =  OK    Kommentar: Kommentar muss am Stringende enden
     *   251 - assertIsFalse john.s(comment)mith@example.com                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   252 - assertIsFalse john.smith(comment)@(comment)example.com           =  99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   253 - assertIsFalse john.smith(com@ment)example.com                    =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   254 - assertIsFalse email( (nested) )@plus.com                         =  92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   255 - assertIsFalse email)mirror(@plus.com                             =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   256 - assertIsFalse email@plus.com (not closed comment                 =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   257 - assertIsFalse email(with @ in comment)plus.com                   =  97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   258 - assertIsTrue  email@domain.com (joe Smith)                       =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     * 
     * ---- Pointy Brackets ----------------------------------------------------------------------------------------------------
     * 
     *   259 - assertIsTrue  ABC DEF <ABC.DEF@GHI.JKL>                          =   0 =  OK 
     *   260 - assertIsTrue  <ABC.DEF@GHI.JKL> ABC DEF                          =   0 =  OK 
     *   261 - assertIsFalse ABC DEF ABC.DEF@GHI.JKL>                           =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   262 - assertIsFalse <ABC.DEF@GHI.JKL ABC DEF                           =  17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   263 - assertIsTrue  "ABC DEF "<ABC.DEF@GHI.JKL>                        =   0 =  OK 
     *   264 - assertIsFalse "ABC<DEF>"@JKL.DE                                  =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   265 - assertIsFalse >                                                  =  16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   266 - assertIsFalse "ABC<DEF@GHI.COM>"@JKL.DE                          =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   267 - assertIsFalse ABC DEF <ABC.<DEF@GHI.JKL>                         =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   268 - assertIsFalse <ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>             =  18 =  OK    Struktur: Fehler in Adress-String-X
     *   269 - assertIsFalse ABC DEF <ABC.DEF@GHI.JKL                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   270 - assertIsFalse ABC.DEF@GHI.JKL> ABC DEF                           =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   271 - assertIsFalse ABC DEF >ABC.DEF@GHI.JKL<                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   272 - assertIsFalse >ABC.DEF@GHI.JKL< ABC DEF                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   273 - assertIsFalse ABC DEF <A@A>                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   274 - assertIsFalse <A@A> ABC DEF                                      =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   275 - assertIsFalse ABC DEF <>                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   276 - assertIsFalse <> ABC DEF                                         =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   277 - assertIsTrue  Joe Smith <email@domain.com>                       =   0 =  OK 
     *   278 - assertIsTrue  Test |<gaaf <email@domain.com>                     =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *   279 - assertIsTrue  Joe Smith <mailto:email@domain.com>                =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   280 - assertIsTrue  Joe Smith <mailto:email(with comment)@domain.com>  =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   281 - assertIsTrue  "With extra < within quotes" Display Name<email@domain.com> =  18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     * 
     * ---- Length ----------------------------------------------------------------------------------------------------
     * 
     *   282 - assertIsTrue  A@B.CD                                             =   0 =  OK 
     *   283 - assertIsFalse A@B.C                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   284 - assertIsFalse A@COM                                              =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   285 - assertIsTrue  ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com =   0 =  OK 
     *   286 - assertIsTrue  abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de =   0 =  OK 
     *   287 - assertIsTrue  ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL =   0 =  OK 
     *   288 - assertIsFalse ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   289 - assertIsFalse 1234567890123456789012345678901234567890123456789012345678901234+x@example.com =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   290 - assertIsTrue  domain.label.with.63.characters@123456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   291 - assertIsFalse domain.label.with.64.characters@123456789012345678901234567890123456789012345678901234567890123A.com =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   292 - assertIsTrue  two.domain.labels.with.63.characters@123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   293 - assertIsFalse domain.label.with.63.and.64.characters@123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123A.com =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   294 - assertIsTrue  63.character.domain.label@123456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   295 - assertIsTrue  63.character.domain.label@123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   296 - assertIsTrue  12345678901234567890123456789012345678901234567890.1234567@123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.com =   0 =  OK 
     *   297 - assertIsFalse 12345678901234567890123456789012345678901234567890.12345678@123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.com =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   298 - assertIsFalse "very.(z),:;<>[]\".VERY.\"very@\\ \"very\".unusual"@strange.example.com =  89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   299 - assertIsFalse too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   300 - assertIsFalse Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de =  13 =  OK    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   301 - assertIsFalse 3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu =  63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   302 - assertIsTrue  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa =  13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   303 - assertIsFalse ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa =  12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   304 - assertIsTrue  w@com                                              =  12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     * 
     * 
     * ---- unsorted ----------------------------------------------------------------------------------------------------
     * 
     * 
     *   305 - assertIsFalse ..@test.com                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   306 - assertIsFalse .a@test.com                                        =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   307 - assertIsFalse ab@sd@dd                                           =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   308 - assertIsFalse .@s.dd                                             =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   309 - assertIsFalse a@b.-de.cc                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   310 - assertIsFalse a@bde-.cc                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   311 - assertIsFalse a@b._de.cc                                         =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   312 - assertIsFalse a@bde_.cc                                          =  20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   313 - assertIsFalse a@bde.c-c                                          =   0 =  #### FEHLER ####    eMail-Adresse korrekt
     *   314 - assertIsFalse a@bde.cc.                                          =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   315 - assertIsFalse ab@b+de.cc                                         =  21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   316 - assertIsFalse a..b@bde.cc                                        =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   317 - assertIsFalse _@bde.cc,                                          =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   318 - assertIsFalse plainaddress                                       =  34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   319 - assertIsFalse plain.address                                      =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   320 - assertIsFalse @%^%#$@#$@#.com                                    =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   321 - assertIsFalse @domain.com                                        =  26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   322 - assertIsFalse email.domain.com                                   =  28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *   323 - assertIsFalse email@domain@domain.com                            =  29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   324 - assertIsFalse .email@domain.com                                  =  30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   325 - assertIsFalse email.@domain.com                                  =  32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   326 - assertIsFalse email..email@domain.com                            =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   327 - assertIsFalse email@.domain.com                                  =  33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   328 - assertIsFalse email@domain.com.                                  =  36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   329 - assertIsFalse email@domain..com                                  =  31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   330 - assertIsFalse "Joe Smith email@domain.com                        =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   331 - assertIsFalse "Joe Smith' email@domain.com                       =  86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   332 - assertIsFalse "Joe Smith"email@domain.com                        =  87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   333 - assertIsFalse Display Name <email@plus.com> (after name with display) =  22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   334 - assertIsTrue  w.b.f@test.com                                     =   0 =  OK 
     *   335 - assertIsTrue  w.b.f@test.museum                                  =   0 =  OK 
     *   336 - assertIsTrue  a.a@test.com                                       =   0 =  OK 
     *   337 - assertIsTrue  ab@288.120.150.10.com                              =   0 =  OK 
     *   338 - assertIsTrue  ab@188.120.150.10                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   339 - assertIsTrue  ab@1.0.0.10                                        =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   340 - assertIsTrue  ab@120.25.254.120                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   341 - assertIsTrue  ab@01.120.150.1                                    =  14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   342 - assertIsTrue  ab@88.120.150.021                                  =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   343 - assertIsTrue  ab@88.120.150.01                                   =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   344 - assertIsTrue  ab@[120.254.254.120]                               =   2 =  OK 
     *   345 - assertIsTrue  email@domain                                       =  34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   346 - assertIsTrue  firstname.lastname@domain.com                      =   0 =  OK 
     *   347 - assertIsTrue  email@subdomain.domain.com                         =   0 =  OK 
     *   348 - assertIsTrue  firstname+lastname@domain.com                      =   0 =  OK 
     *   349 - assertIsTrue  email@123.123.123.123                              =  23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   350 - assertIsTrue  email@[123.123.123.123]                            =   2 =  OK 
     *   351 - assertIsTrue  1234567890@domain.com                              =   0 =  OK 
     *   352 - assertIsTrue  a@domain.com                                       =   0 =  OK 
     *   353 - assertIsTrue  a.b.c.d@domain.com                                 =   0 =  OK 
     *   354 - assertIsTrue  aap.123.noot.mies@domain.com                       =   0 =  OK 
     *   355 - assertIsTrue  1@domain.com                                       =   0 =  OK 
     *   356 - assertIsTrue  email@domain.co.jp                                 =   0 =  OK 
     *   357 - assertIsTrue  firstname-lastname@domain.com                      =   0 =  OK 
     *   358 - assertIsTrue  firstname-lastname@d.com                           =   0 =  OK 
     *   359 - assertIsTrue  FIRSTNAME-LASTNAME@d--n.com                        =   0 =  OK 
     *   360 - assertIsTrue  first-name-last-name@d-a-n.com                     =   0 =  OK 
     *   361 - assertIsTrue  john.smith@example.com                             =   0 =  OK 
     *   362 - assertIsTrue  domain.starts.with.digit@2domain.com               =   0 =  OK 
     *   363 - assertIsTrue  domain.ends.with.digit@domain2.com                 =   0 =  OK 
     *   364 - assertIsTrue  "Joe Smith" email@domain.com                       =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   365 - assertIsTrue  "Joe\tSmith" email@domain.com                      =  84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   366 - assertIsTrue  "Joe"Smith" email@domain.com                       =  87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   367 - assertIsTrue  MailTo:casesensitve@domain.com                     =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   368 - assertIsTrue  mailto:email@domain.com                            =  22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   369 - assertIsFalse ABC.DEF@GHI.J                                      =  14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   370 - assertIsTrue  ME@MYSELF.LOCALHOST                                =   0 =  OK 
     *   371 - assertIsFalse ME@MYSELF.LOCALHORST                               =  15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
     *   372 - assertIsTrue  email@domain.topleveldomain                        =  15 =  #### FEHLER ####    Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
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

      assertIsTrue( "2@bde.cc" );
      assertIsTrue( "-@bde.cc" );
      assertIsTrue( "a2@bde.cc" );
      assertIsTrue( "a-b@bde.cc" );
      assertIsTrue( "ab@b-de.cc" );
      assertIsTrue( "a+b@bde.cc" );
      assertIsTrue( "f.f.f@bde.cc" );
      assertIsTrue( "ab_c@bde.cc" );
      assertIsTrue( "_-_@bde.cc" );
      assertIsTrue( "k.haak@12move.nl" );
      assertIsTrue( "K.HAAK@12MOVE.NL" );
      assertIsTrue( "email@domain.com" );

      wlHeadline( "No Input" );

      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "        " );

      wlHeadline( "AT-Character" );

      assertIsFalse( "ABCDEFGHIJKLMNOP" );
      assertIsFalse( "ABC.DEF.GHI.JKL" );
      assertIsFalse( "@GHI.JKL" );
      assertIsFalse( "ABC.DEF@" );
      assertIsFalse( "ABC.DEF@@GHI.JKL" );

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
      assertIsFalse( "A . B & C . D" );
      assertIsFalse( "(?).[!]@{&}.<:>" );

      assertIsTrue( "{local{name{{with{@leftbracket.com" );
      assertIsTrue( "}local}name}}with{@rightbracket.com" );
      assertIsTrue( "|local||name|with|@pipe.com" );
      assertIsTrue( "%local%%name%with%@percentage.com" );
      assertIsTrue( "$local$$name$with$@dollar.com" );
      assertIsTrue( "&local&&name&with&$@amp.com" );
      assertIsTrue( "#local##name#with#@hash.com" );
      assertIsTrue( "~local~~name~with~@tilde.com" );
      assertIsTrue( "!local!!name!with!@exclamation.com" );
      assertIsTrue( "?local??name?with?@question.com" );
      assertIsTrue( "*local**name*with*@asterisk.com" );
      assertIsTrue( "`local``name`with`@grave-accent.com" );
      assertIsTrue( "^local^^name^with^@xor.com" );
      assertIsTrue( "=local==name=with=@equality.com" );
      assertIsTrue( "+local++name+with+@equality.com" );

      assertIsFalse( "email@{leftbracket.com" );
      assertIsFalse( "email@rightbracket}.com" );
      assertIsFalse( "email@p|pe.com" );
      assertIsFalse( "isis@100%.nl" );
      assertIsFalse( "email@dollar$.com" );
      assertIsFalse( "email@r&amp;d.com" );
      assertIsFalse( "email@#hash.com" );
      assertIsFalse( "email@wave~tilde.com" );
      assertIsFalse( "email@exclamation!mark.com" );
      assertIsFalse( "email@question?mark.com" );
      assertIsFalse( "email@obelix*asterisk.com" );
      assertIsFalse( "email@grave`accent.com" );
      assertIsFalse( "email@colon:colon.com" );
      assertIsFalse( "email@caret^xor.com" );
      assertIsFalse( "email@=qowaiv.com" );
      assertIsFalse( "email@plus+.com" );
      assertIsFalse( "email@domain.com>" );

      assertIsFalse( "email@mailto:domain.com" );
      assertIsFalse( "mailto:mailto:email@domain.com" );

      assertIsFalse( "email@-domain.com" );
      assertIsFalse( "email@domain-.com" );
      assertIsFalse( "email@domain.com-" );

      assertIsFalse( "Joe Smith &lt;email@domain.com&gt;" );

      assertIsTrue( "?????@domain.com" );
      assertIsTrue( "local@?????.com" );

      assertIsTrue( "email@domain-one.com" );
      assertIsTrue( "_______@domain.com" );

      wlHeadline( "IP V4" );

      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[001.002.003.004]" );
      assertIsTrue( "\"ABC.DEF\"@[127.0.0.1]" );
      assertIsFalse( "ABC.DEF[1.2.3.4]" );
      assertIsFalse( "[1.2.3.4]@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF[@1.2.3.4]" );
      assertIsFalse( "\"[1.2.3.4]\"@[5.6.7.8]" );
      assertIsFalse( "ABC.DEF@MyDomain[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.00002.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.456]" );
      assertIsFalse( "ABC.DEF@[..]" );
      assertIsFalse( "ABC.DEF@[.2.3.4]" );
      assertIsFalse( "ABC.DEF@[]" );
      assertIsFalse( "ABC.DEF@[1]" );
      assertIsFalse( "ABC.DEF@[1.2]" );
      assertIsFalse( "ABC.DEF@[1.2.3]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4.5.6]" );
      assertIsFalse( "ABC.DEF@[MyDomain.de]" );
      assertIsFalse( "ABC.DEF@[1.2.3.]" );
      assertIsFalse( "ABC.DEF@[1.2.3. ]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4].de" );
      assertIsFalse( "ABC.DE@[1.2.3.4][5.6.7.8]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4" );
      assertIsFalse( "ABC.DEF@1.2.3.4]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.Z]" );
      assertIsFalse( "ABC.DEF@[12.34]" );
      assertIsFalse( "ABC.DEF@[1.2.3.]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4] " );
      assertIsFalse( "ABC.DEF@[1.2.3.4" );
      assertIsFalse( "ABC.DEF@[1234.5.6.7]" );
      assertIsFalse( "ABC.DEF@[1.2...3.4]" );

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

      wlHeadline( "IP V6" );

      assertIsTrue( "ABC.DEF@[IPv6:2001:db8::1]" );
      assertIsFalse( "ABC@[IP" );
      assertIsFalse( "ABC@[IPv6]" );
      assertIsFalse( "ABC@[IPv6:]" );
      assertIsFalse( "ABC@[IPv6:1]" );
      assertIsFalse( "ABC@[IPv6:1:2]" );
      assertIsTrue( "ABC@[IPv6:1:2:3]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4]" );
      assertIsFalse( "ABC@[IPv6:1:2:3:4:5:]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4:5:6]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4:5:6:7]" );
      assertIsTrue( "ABC@[IPv6:1:2:3:4:5:6:7:8]" );
      assertIsFalse( "ABC@[IPv6:1:2:3:4:5:6:7:8:9]" );
      assertIsFalse( "ABC@[IPv4:1:2:3:4]" );
      assertIsFalse( "ABC@[I127.0.0.1]" );
      assertIsFalse( "ABC@[D127.0.0.1]" );
      assertIsFalse( "ABC@[iPv6:2001:db8::1]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3::5:6:7:8]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3::5::7:8]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:Z]" );
      assertIsFalse( "ABC.DEF@[IPv6:12:34]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6] " );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6" );
      assertIsFalse( "ABC.DEF@[IPv6:12345:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:::6:7:8]" );

      assertIsTrue( "local@[2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsTrue( "local@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsFalse( "local@[IPA6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsFalse( "local@[APv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsTrue( "local@[aaa6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );

      assertIsTrue( "local@2001:0db8:85a3:0000:0000:8a2e:0370:7334" );

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
      assertIsFalse( "\"Ende.am.Eingabeende\"" );
      assertIsFalse( "0\"00.000\"@GHI.JKL" );

      wlHeadline( "Comments" );

      assertIsTrue( "(ABC)DEF@GHI.JKL" );
      assertIsTrue( "ABC(DEF)@GHI.JKL" );
      assertIsTrue( "ABC.DEF@GHI.JKL(MNO)" );
      assertIsFalse( "(ABC).DEF@GHI.JKL" );
      assertIsFalse( "ABC.(DEF)@GHI.JKL" );
      assertIsFalse( "ABC.DEF@(GHI).JKL" );
      assertIsFalse( "ABC.DEF@GHI.(JKL).MNO" );
      assertIsFalse( "ABC.DEF@GHI.JK(L.M)NO" );
      assertIsFalse( "AB(CD)EF@GHI.JKL" );
      assertIsFalse( "AB.(CD).EF@GHI.JKL" );
      assertIsFalse( "AB.\"(CD)\".EF@GHI.JKL" );
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
      assertIsFalse( "\"ABC<DEF>\"@JKL.DE" );
      assertIsFalse( ">" );
      assertIsFalse( "\"ABC<DEF@GHI.COM>\"@JKL.DE" );
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

      assertIsTrue( "Joe Smith <email@domain.com>" );
      assertIsTrue( "Test |<gaaf <email@domain.com>" );
      assertIsTrue( "Joe Smith <mailto:email@domain.com>" );
      assertIsTrue( "Joe Smith <mailto:email(with comment)@domain.com>" );
      assertIsTrue( "\"With extra < within quotes\" Display Name<email@domain.com>" );

      wlHeadline( "Length" );

      assertIsTrue( "A@B.CD" );
      assertIsFalse( "A@B.C" );
      assertIsFalse( "A@COM" );

      assertIsTrue( "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@OK.com" );
      assertIsTrue( "abcdefghijklmnopqrstuvwxy.ABCDEFGHIJKLMNOPQRSTUVWXYZ@ABCDEFGHIJKLMNOPQRSTUVWXYZ12.de" );
      assertIsTrue( "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZX@ZZZZZZZZZX.ZL" );
      assertIsFalse( "ZZZZZZZZZXZZZZZZZZZZXZZZZ.ZZZZZXZZZZZZZZZZXZZZZZZZZZZXZZZZZZZZZXT@ZZZZZZZZZX.ZL" );
      assertIsFalse( "1234567890123456789012345678901234567890123456789012345678901234+x@example.com" );

      String str_50 = "12345678901234567890123456789012345678901234567890";
      String str_63 = "123456789012345678901234567890123456789012345678901234567890123";

      assertIsTrue( "domain.label.with.63.characters@" + str_63 + ".com" );
      assertIsFalse( "domain.label.with.64.characters@" + str_63 + "A.com" );
      assertIsTrue( "two.domain.labels.with.63.characters@" + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "domain.label.with.63.and.64.characters@" + str_63 + "." + str_63 + "A.com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + ".com" );
      assertIsTrue( "63.character.domain.label@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsTrue( "" + str_50 + ".1234567@" + str_63 + "." + str_63 + "." + str_63 + ".com" );
      assertIsFalse( "" + str_50 + ".12345678@" + str_63 + "." + str_63 + "." + str_63 + ".com" );

      assertIsFalse( "\"very.(z),:;<>[]\\\".VERY.\\\"very@\\\\ \\\"very\\\".unusual\"@strange.example.com" );
      assertIsFalse( "too_long_localpart_too_long_localpart_too_long_localpart_too_long_localpart@test.local.part" );
      assertIsFalse( "Dieser_local_Part_ist_zu_lang.Nach_RFC_5321_sind_maximal_64_Zeichen_erlaubt@Das_sind_hier_75_Zeichen_und_daher_zu_lang_und_falsch.de" );
      assertIsFalse( "3.141592653589793238462643383279502884197169399375105820974944@3.14159265358979323846264338327950288419716939937510582097494459266616C736368.eu" );

      assertIsTrue( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
      assertIsFalse( "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
      assertIsTrue( "w@com" );

      wl( "" );
      wlHeadline( "unsorted" );
      wl( "" );

      assertIsFalse( "..@test.com" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( "ab@sd@dd" );
      assertIsFalse( ".@s.dd" );
      assertIsFalse( "a@b.-de.cc" );
      assertIsFalse( "a@bde-.cc" );
      assertIsFalse( "a@b._de.cc" );
      assertIsFalse( "a@bde_.cc" );
      assertIsFalse( "a@bde.c-c" );
      assertIsFalse( "a@bde.cc." );
      assertIsFalse( "ab@b+de.cc" );
      assertIsFalse( "a..b@bde.cc" );
      assertIsFalse( "_@bde.cc," );
      assertIsFalse( "plainaddress" );
      assertIsFalse( "plain.address" );
      assertIsFalse( "@%^%#$@#$@#.com" );
      assertIsFalse( "@domain.com" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@domain@domain.com" );
      assertIsFalse( ".email@domain.com" );
      assertIsFalse( "email.@domain.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email@.domain.com" );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@domain..com" );
      assertIsFalse( "\"Joe Smith email@domain.com" );
      assertIsFalse( "\"Joe Smith' email@domain.com" );
      assertIsFalse( "\"Joe Smith\"email@domain.com" );
      assertIsFalse( "Display Name <email@plus.com> (after name with display)" );
      assertIsTrue( "w.b.f@test.com" );
      assertIsTrue( "w.b.f@test.museum" );
      assertIsTrue( "a.a@test.com" );
      assertIsTrue( "ab@288.120.150.10.com" );
      assertIsTrue( "ab@188.120.150.10" );
      assertIsTrue( "ab@1.0.0.10" );
      assertIsTrue( "ab@120.25.254.120" );
      assertIsTrue( "ab@01.120.150.1" );
      assertIsTrue( "ab@88.120.150.021" );
      assertIsTrue( "ab@88.120.150.01" );
      assertIsTrue( "ab@[120.254.254.120]" );
      assertIsTrue( "email@domain" );
      assertIsTrue( "firstname.lastname@domain.com" );
      assertIsTrue( "email@subdomain.domain.com" );
      assertIsTrue( "firstname+lastname@domain.com" );
      assertIsTrue( "email@123.123.123.123" );
      assertIsTrue( "email@[123.123.123.123]" );
      assertIsTrue( "1234567890@domain.com" );
      assertIsTrue( "a@domain.com" );
      assertIsTrue( "a.b.c.d@domain.com" );
      assertIsTrue( "aap.123.noot.mies@domain.com" );
      assertIsTrue( "1@domain.com" );
      assertIsTrue( "email@domain.co.jp" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "firstname-lastname@d.com" );
      assertIsTrue( "FIRSTNAME-LASTNAME@d--n.com" );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
      assertIsTrue( "john.smith@example.com" );
      assertIsTrue( "domain.starts.with.digit@2domain.com" );
      assertIsTrue( "domain.ends.with.digit@domain2.com" );
      assertIsTrue( "\"Joe Smith\" email@domain.com" );
      assertIsTrue( "\"Joe\\tSmith\" email@domain.com" );
      assertIsTrue( "\"Joe\"Smith\" email@domain.com" );
      assertIsTrue( "MailTo:casesensitve@domain.com" );
      assertIsTrue( "mailto:email@domain.com" );
      assertIsFalse( "ABC.DEF@GHI.J" );
      assertIsTrue( "ME@MYSELF.LOCALHOST" );
      assertIsFalse( "ME@MYSELF.LOCALHORST" );
      assertIsTrue( "email@domain.topleveldomain" );

    }
    catch ( Exception err_inst )
    {
      System.out.println( err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    System.exit( 0 );
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
    System.out.println( pString );
  }

  private static int LAUFENDE_ZAHL = 0;

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
    int return_code = FkEMail.checkEMailAdresse( pString );

    boolean knz_soll_wert = true;

    boolean is_true = return_code < 10;

    System.out.println( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER ####    " + FkEMail.getFehlerText( return_code ) ) );
  }

  private static void assertIsFalse( String pString )
  {
    int return_code = FkEMail.checkEMailAdresse( pString );

    boolean knz_soll_wert = false;

    boolean is_true = return_code < 10;

    System.out.println( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? "  " : ( return_code < 100 ? " " : "" ) ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER #### " ) + "   " + FkEMail.getFehlerText( return_code ) );
  }

}
