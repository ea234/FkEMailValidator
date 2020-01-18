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
     *     1 - assertIsFalse null                                               = 10 =  OK    Laenge: Eingabe ist null
     *     2 - assertIsFalse                                                    = 11 =  OK    Laenge: Eingabe ist Leerstring
     *     3 - assertIsFalse ..@test.com                                        = 30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *     4 - assertIsFalse .a@test.com                                        = 30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *     5 - assertIsFalse ab@sd@dd                                           = 29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *     6 - assertIsFalse .@s.dd                                             = 30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *     7 - assertIsFalse ab@988.120.150.10                                  = 23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *     8 - assertIsFalse ab@120.256.256.120                                 = 23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *     9 - assertIsFalse ab@120.25.1111.120                                 = 23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    10 - assertIsFalse ab@[188.120.150.10                                 = 61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *    11 - assertIsFalse ab@188.120.150.10]                                 = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    12 - assertIsFalse ab@[188.120.150.10].com                            = 60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *    13 - assertIsFalse a@b.-de.cc                                         = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    14 - assertIsFalse a@bde-.cc                                          = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    15 - assertIsFalse a@b._de.cc                                         = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    16 - assertIsFalse a@bde_.cc                                          = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    17 - assertIsFalse a@bde.c-c                                          =  0 =  #### FEHLER ####    eMail-Adresse korrekt
     *    18 - assertIsFalse a@bde.cc.                                          = 36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    19 - assertIsFalse ab@b+de.cc                                         = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    20 - assertIsFalse a..b@bde.cc                                        = 31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    21 - assertIsFalse _@bde.cc,                                          = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    22 - assertIsFalse plainaddress                                       = 34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *    23 - assertIsFalse plain.address                                      = 28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    24 - assertIsFalse @%^%#$@#$@#.com                                    = 26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    25 - assertIsFalse @domain.com                                        = 26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *    26 - assertIsFalse Joe Smith &lt;email@domain.com&gt;                 = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    27 - assertIsFalse email.domain.com                                   = 28 =  OK    AT-Zeichen: kein AT-Zeichen gefunden
     *    28 - assertIsFalse email@domain@domain.com                            = 29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *    29 - assertIsFalse .email@domain.com                                  = 30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *    30 - assertIsFalse email.@domain.com                                  = 32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *    31 - assertIsFalse email..email@domain.com                            = 31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    32 - assertIsFalse email@-domain.com                                  = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    33 - assertIsFalse email@domain-.com                                  = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *    34 - assertIsFalse email@domain.com-                                  = 24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *    35 - assertIsFalse email@.domain.com                                  = 33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *    36 - assertIsFalse email@domain.com.                                  = 36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *    37 - assertIsFalse email@domain..com                                  = 31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *    38 - assertIsFalse email@111.222.333                                  = 23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    39 - assertIsFalse email@111.222.333.256                              = 23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    40 - assertIsFalse email@[123.123.123.123                             = 61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *    41 - assertIsFalse email@[123.123.123].123                            = 57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *    42 - assertIsFalse email@123.123.123.123]                             = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    43 - assertIsFalse email@123.123.[123.123]                            = 52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *    44 - assertIsFalse email@{leftbracket.com                             = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    45 - assertIsFalse email@rightbracket}.com                            = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    46 - assertIsFalse email@p|pe.com                                     = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    47 - assertIsFalse isis@100%.nl                                       = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    48 - assertIsFalse email@dollar$.com                                  = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    49 - assertIsFalse email@r&amp;d.com                                  = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    50 - assertIsFalse email@#hash.com                                    = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    51 - assertIsFalse email@wave~tilde.com                               = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    52 - assertIsFalse email@exclamation!mark.com                         = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    53 - assertIsFalse email@question?mark.com                            = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    54 - assertIsFalse email@obelix*asterisk.com                          = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    55 - assertIsFalse email@grave`accent.com                             = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    56 - assertIsFalse email@colon:colon.com                              = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    57 - assertIsFalse email@caret^xor.com                                = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    58 - assertIsFalse email@=qowaiv.com                                  = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    59 - assertIsFalse email@plus+.com                                    = 21 =  OK    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *    60 - assertIsFalse email@domain.com>                                  = 16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *    61 - assertIsFalse email( (nested) )@plus.com                         = 92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *    62 - assertIsFalse email)mirror(@plus.com                             = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    63 - assertIsFalse email@plus.com (not closed comment                 = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    64 - assertIsFalse email(with @ in comment)plus.com                   = 97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *    65 - assertIsFalse "Joe Smith email@domain.com                        = 86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *    66 - assertIsFalse "Joe Smith' email@domain.com                       = 86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *    67 - assertIsFalse "Joe Smith"email@domain.com                        = 87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *    68 - assertIsFalse email@mailto:domain.com                            = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    69 - assertIsFalse mailto:mailto:email@domain.com                     = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    70 - assertIsFalse Display Name <email@plus.com> (after name with display) = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    71 - assertIsFalse ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa = 12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *    72 - assertIsTrue  w@com                                              = 12 =  #### FEHLER ####    Laenge: Laengenbegrenzungen stimmen nicht
     *    73 - assertIsTrue  w.b.f@test.com                                     =  0 =  OK 
     *    74 - assertIsTrue  w.b.f@test.museum                                  =  0 =  OK 
     *    75 - assertIsTrue  a.a@test.com                                       =  0 =  OK 
     *    76 - assertIsTrue  ab@288.120.150.10.com                              =  0 =  OK 
     *    77 - assertIsTrue  ab@188.120.150.10                                  = 23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    78 - assertIsTrue  ab@1.0.0.10                                        = 23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    79 - assertIsTrue  ab@120.25.254.120                                  = 23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    80 - assertIsTrue  ab@01.120.150.1                                    = 14 =  #### FEHLER ####    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *    81 - assertIsTrue  ab@88.120.150.021                                  = 23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    82 - assertIsTrue  ab@88.120.150.01                                   = 23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *    83 - assertIsTrue  ab@[120.254.254.120]                               =  2 =  OK 
     *    84 - assertIsTrue  local@2001:0db8:85a3:0000:0000:8a2e:0370:7334      = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *    85 - assertIsTrue  local@[2001:0db8:85a3:0000:0000:8a2e:0370:7334]    =  4 =  OK 
     *    86 - assertIsTrue  local@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] =  4 =  OK 
     *    87 - assertIsFalse local@[IPA6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] = 40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *    88 - assertIsFalse local@[APv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] = 49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *    89 - assertIsTrue  local@[aaa6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] =  4 =  OK 
     *    90 - assertIsTrue  2@bde.cc                                           =  0 =  OK 
     *    91 - assertIsTrue  -@bde.cc                                           =  0 =  OK 
     *    92 - assertIsTrue  a2@bde.cc                                          =  0 =  OK 
     *    93 - assertIsTrue  a-b@bde.cc                                         =  0 =  OK 
     *    94 - assertIsTrue  ab@b-de.cc                                         =  0 =  OK 
     *    95 - assertIsTrue  a+b@bde.cc                                         =  0 =  OK 
     *    96 - assertIsTrue  f.f.f@bde.cc                                       =  0 =  OK 
     *    97 - assertIsTrue  ab_c@bde.cc                                        =  0 =  OK 
     *    98 - assertIsTrue  _-_@bde.cc                                         =  0 =  OK 
     *    99 - assertIsTrue  k.haak@12move.nl                                   =  0 =  OK 
     *   100 - assertIsTrue  K.HAAK@12MOVE.NL                                   =  0 =  OK 
     *   101 - assertIsTrue  email@domain.com                                   =  0 =  OK 
     *   102 - assertIsTrue  email@domain                                       = 34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   103 - assertIsTrue  ?????@domain.com                                   =  0 =  OK 
     *   104 - assertIsTrue  local@?????.com                                    = 21 =  #### FEHLER ####    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   105 - assertIsTrue  firstname.lastname@domain.com                      =  0 =  OK 
     *   106 - assertIsTrue  email@subdomain.domain.com                         =  0 =  OK 
     *   107 - assertIsTrue  firstname+lastname@domain.com                      =  0 =  OK 
     *   108 - assertIsTrue  email@123.123.123.123                              = 23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   109 - assertIsTrue  email@[123.123.123.123]                            =  2 =  OK 
     *   110 - assertIsTrue  1234567890@domain.com                              =  0 =  OK 
     *   111 - assertIsTrue  a@domain.com                                       =  0 =  OK 
     *   112 - assertIsTrue  a.b.c.d@domain.com                                 =  0 =  OK 
     *   113 - assertIsTrue  aap.123.noot.mies@domain.com                       =  0 =  OK 
     *   114 - assertIsTrue  1@domain.com                                       =  0 =  OK 
     *   115 - assertIsTrue  email@domain-one.com                               =  0 =  OK 
     *   116 - assertIsTrue  _______@domain.com                                 =  0 =  OK 
     *   117 - assertIsTrue  email@domain.topleveldomain                        = 15 =  #### FEHLER ####    Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
     *   118 - assertIsTrue  email@domain.co.jp                                 =  0 =  OK 
     *   119 - assertIsTrue  firstname-lastname@domain.com                      =  0 =  OK 
     *   120 - assertIsTrue  firstname-lastname@d.com                           =  0 =  OK 
     *   121 - assertIsTrue  FIRSTNAME-LASTNAME@d--n.com                        =  0 =  OK 
     *   122 - assertIsTrue  first-name-last-name@d-a-n.com                     =  0 =  OK 
     *   123 - assertIsTrue  {local{name{{with{@leftbracket.com                 =  0 =  OK 
     *   124 - assertIsTrue  }local}name}}with{@rightbracket.com                =  0 =  OK 
     *   125 - assertIsTrue  |local||name|with|@pipe.com                        =  0 =  OK 
     *   126 - assertIsTrue  %local%%name%with%@percentage.com                  =  0 =  OK 
     *   127 - assertIsTrue  $local$$name$with$@dollar.com                      =  0 =  OK 
     *   128 - assertIsTrue  &local&&name&with&$@amp.com                        =  0 =  OK 
     *   129 - assertIsTrue  #local##name#with#@hash.com                        =  0 =  OK 
     *   130 - assertIsTrue  ~local~~name~with~@tilde.com                       =  0 =  OK 
     *   131 - assertIsTrue  !local!!name!with!@exclamation.com                 =  0 =  OK 
     *   132 - assertIsTrue  ?local??name?with?@question.com                    =  0 =  OK 
     *   133 - assertIsTrue  *local**name*with*@asterisk.com                    =  0 =  OK 
     *   134 - assertIsTrue  `local``name`with`@grave-accent.com                =  0 =  OK 
     *   135 - assertIsTrue  ^local^^name^with^@xor.com                         =  0 =  OK 
     *   136 - assertIsTrue  =local==name=with=@equality.com                    =  0 =  OK 
     *   137 - assertIsTrue  +local++name+with+@equality.com                    =  0 =  OK 
     *   138 - assertIsTrue  Joe Smith <email@domain.com>                       =  0 =  OK 
     *   139 - assertIsTrue  john.smith@example.com                             =  0 =  OK 
     *   140 - assertIsTrue  (comment)john.smith@example.com                    =  6 =  OK 
     *   141 - assertIsTrue  john.smith(comment)@example.com                    =  6 =  OK 
     *   142 - assertIsTrue  john.smith@(comment)example.com                    =  6 =  OK 
     *   143 - assertIsFalse john.smith@exampl(comment)e.com                    = 100 =  OK    Kommentar: Kommentar muss am Strinende enden
     *   144 - assertIsFalse john.s(comment)mith@example.com                    = 97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   145 - assertIsFalse john.smith(comment)@(comment)example.com           = 99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   146 - assertIsFalse john.smith(com@ment)example.com                    = 97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   147 - assertIsTrue  john.smith@example.com(comment)                    = 15 =  #### FEHLER ####    Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
     *   148 - assertIsTrue  domain.starts.with.digit@2domain.com               =  0 =  OK 
     *   149 - assertIsTrue  domain.ends.with.digit@domain2.com                 =  0 =  OK 
     *   150 - assertIsTrue  domain.label.with.63.characters@123456789012345678901234567890123456789012345678901234567890123.com =  0 =  OK 
     *   151 - assertIsFalse domain.label.with.64.characters@1234567890123456789012345678901234567890123456789012345678901234.com = 63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   152 - assertIsTrue  domain.label.with.63.characters@123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.com =  0 =  OK 
     *   153 - assertIsFalse domain.label.with.63.and.64.characters@123456789012345678901234567890123456789012345678901234567890123.1234567890123456789012345678901234567890123456789012345678901234.com = 63 =  OK    Domain-Part: Domain-Label zu lang (maximal 63 Zeichen)
     *   154 - assertIsTrue  email@domain.com (joe Smith)                       = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   155 - assertIsTrue  "Joe Smith" email@domain.com                       = 87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   156 - assertIsTrue  "Joe\tSmith" email@domain.com                      = 84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   157 - assertIsTrue  "Joe"Smith" email@domain.com                       = 87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   158 - assertIsTrue  Test |<gaaf <email@domain.com>                     = 18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *   159 - assertIsTrue  MailTo:casesensitve@domain.com                     = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   160 - assertIsTrue  mailto:email@domain.com                            = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   161 - assertIsTrue  Joe Smith <mailto:email@domain.com>                = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   162 - assertIsTrue  Joe Smith <mailto:email(with comment)@domain.com>  = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   163 - assertIsTrue  "With extra < within quotes" Display Name<email@domain.com> = 18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *   164 - assertIsTrue  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa = 13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     *   165 - assertIsTrue  A.B@C.DE                                           =  0 =  OK 
     *   166 - assertIsTrue  A."B"@C.DE                                         =  1 =  OK 
     *   167 - assertIsTrue  A.B@[1.2.3.4]                                      =  2 =  OK 
     *   168 - assertIsTrue  A."B"@[1.2.3.4]                                    =  3 =  OK 
     *   169 - assertIsTrue  A.B@[IPv6:1:2:3:4:5:6:7:8]                         =  4 =  OK 
     *   170 - assertIsTrue  A."B"@[IPv6:1:2:3:4:5:6:7:8]                       =  5 =  OK 
     *   171 - assertIsTrue  (A)B@C.DE                                          =  6 =  OK 
     *   172 - assertIsTrue  A(B)@C.DE                                          =  6 =  OK 
     *   173 - assertIsTrue  (A)"B"@C.DE                                        =  7 =  OK 
     *   174 - assertIsTrue  "A"(B)@C.DE                                        =  7 =  OK 
     *   175 - assertIsTrue  (A)B@[1.2.3.4]                                     =  2 =  OK 
     *   176 - assertIsTrue  A(B)@[1.2.3.4]                                     =  2 =  OK 
     *   177 - assertIsTrue  (A)"B"@[1.2.3.4]                                   =  8 =  OK 
     *   178 - assertIsTrue  "A"(B)@[1.2.3.4]                                   =  8 =  OK 
     *   179 - assertIsTrue  (A)B@[IPv6:1:2:3:4:5:6:7:8]                        =  4 =  OK 
     *   180 - assertIsTrue  A(B)@[IPv6:1:2:3:4:5:6:7:8]                        =  4 =  OK 
     *   181 - assertIsTrue  (A)"B"@[IPv6:1:2:3:4:5:6:7:8]                      =  9 =  OK 
     *   182 - assertIsTrue  "A"(B)@[IPv6:1:2:3:4:5:6:7:8]                      =  9 =  OK 
     *   183 - assertIsTrue  A@B.CD                                             =  0 =  OK 
     *   184 - assertIsTrue  ABC1.DEF2@GHI3.JKL4                                =  0 =  OK 
     *   185 - assertIsTrue  ABC.DEF_@GHI.JKL                                   =  0 =  OK 
     *   186 - assertIsTrue  #ABC.DEF@GHI.JKL                                   =  0 =  OK 
     *   187 - assertIsFalse null                                               = 10 =  OK    Laenge: Eingabe ist null
     *   188 - assertIsFalse                                                    = 12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   189 - assertIsFalse                                                    = 12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   190 - assertIsFalse ABCDEFGHIJKLMNOP                                   = 34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   191 - assertIsFalse ABC.DEF@GHI.J                                      = 14 =  OK    Laenge: Top-Level-Domain muss mindestens 2 Stellen lang sein.
     *   192 - assertIsTrue  ME@MYSELF.LOCALHOST                                =  0 =  OK 
     *   193 - assertIsFalse ME@MYSELF.LOCALHORST                               = 15 =  OK    Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
     *   194 - assertIsFalse ABC.DEF@GHI.2KL                                    = 23 =  OK    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   195 - assertIsFalse ABC.DEF@GHI.JK-                                    = 24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   196 - assertIsFalse ABC.DEF@GHI.JK_                                    = 24 =  OK    Zeichen: Kein Sonderzeichen am Ende der eMail-Adresse
     *   197 - assertIsTrue  ABC.DEF@GHI.JK2                                    =  0 =  OK 
     *   198 - assertIsTrue  ABC.DEF@2HI.JKL                                    =  0 =  OK 
     *   199 - assertIsFalse ABC.DEF@-HI.JKL                                    = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   200 - assertIsFalse ABC.DEF@_HI.JKL                                    = 20 =  OK    Zeichen: Zahl oder Sonderzeichen nur nach einem Buchstaben (Teilstring darf nicht mit Zahl oder Sonderzeichen beginnen)
     *   201 - assertIsFalse A . B & C . D                                      = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   202 - assertIsFalse (?).[!]@{&}.<:>                                    = 18 =  OK    Struktur: Fehler in Adress-String-X
     *   203 - assertIsFalse .ABC.DEF@GHI.JKL                                   = 30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   204 - assertIsFalse ABC..DEF@GHI.JKL                                   = 31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   205 - assertIsFalse ABC.DEF@GHI..JKL                                   = 31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   206 - assertIsFalse ABC.DEF@GHI.JKL..                                  = 31 =  OK    Trennzeichen: keine zwei Punkte hintereinander
     *   207 - assertIsFalse ABC.DEF.@GHI.JKL                                   = 32 =  OK    Trennzeichen: ungueltige Zeichenkombination ".@"
     *   208 - assertIsFalse ABC.DEF@.                                          = 33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   209 - assertIsFalse ABC.DEF@.GHI.JKL                                   = 33 =  OK    Trennzeichen: ungueltige Zeichenkombination "@."
     *   210 - assertIsFalse ABCDEF@GHIJKL                                      = 34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   211 - assertIsFalse ABC.DEF@GHIJKL                                     = 34 =  OK    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   212 - assertIsFalse ABC.DEF@GHI.JKL.                                   = 36 =  OK    Trennzeichen: der letzte Punkt darf nicht am Ende liegen
     *   213 - assertIsFalse @GHI.JKL                                           = 26 =  OK    AT-Zeichen: kein AT-Zeichen am Anfang
     *   214 - assertIsFalse ABC.DEF@                                           = 27 =  OK    AT-Zeichen: kein AT-Zeichen am Ende
     *   215 - assertIsFalse ABC.DEF@@GHI.JKL                                   = 29 =  OK    AT-Zeichen: kein weiteres AT-Zeichen zulassen, wenn schon AT-Zeichen gefunden wurde
     *   216 - assertIsTrue  "ABC.DEF"@GHI.DE                                   =  1 =  OK 
     *   217 - assertIsTrue  "ABC DEF"@GHI.DE                                   =  1 =  OK 
     *   218 - assertIsFalse ABC DEF@GHI.DE                                     = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   219 - assertIsTrue  "ABC@DEF"@GHI.DE                                   =  1 =  OK 
     *   220 - assertIsFalse "ABC DEF@G"HI.DE                                   = 87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   221 - assertIsFalse ""@GHI.DE                                          = 85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   222 - assertIsFalse "ABC.DEF@G"HI.DE                                   = 87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   223 - assertIsFalse A@G"HI.DE                                          = 82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   224 - assertIsFalse "@GHI.DE                                           = 86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   225 - assertIsFalse ABC.DEF."                                          = 85 =  OK    String: Leerstring in Anfuehrungszeichen
     *   226 - assertIsFalse ABC.DEF@""                                         = 82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   227 - assertIsFalse ABC.DEF@G"HI.DE                                    = 82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   228 - assertIsFalse ABC.DEF@GHI.DE"                                    = 82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   229 - assertIsFalse ABC.DEF@"GHI.DE                                    = 82 =  OK    String: kein Anfuehrungszeichen nach dem AT-Zeichen
     *   230 - assertIsFalse "Escape.Sequenz.Ende"                              = 88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   231 - assertIsFalse ABC.DEF"GHI.DE                                     = 81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   232 - assertIsFalse ABC.DEF"@GHI.DE                                    = 81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   233 - assertIsFalse ABC.DE"F@GHI.DE                                    = 81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   234 - assertIsFalse "ABC.DEF@GHI.DE                                    = 86 =  OK    String: kein abschliessendes Anfuehrungszeichen gefunden.
     *   235 - assertIsFalse "ABC.DEF@GHI.DE"                                   = 88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   236 - assertIsTrue  ".ABC.DEF"@GHI.DE                                  =  1 =  OK 
     *   237 - assertIsTrue  "ABC.DEF."@GHI.DE                                  =  1 =  OK 
     *   238 - assertIsTrue  "ABC".DEF."GHI"@JKL.de                             =  1 =  OK 
     *   239 - assertIsFalse A"BC".DEF."GHI"@JKL.de                             = 80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   240 - assertIsFalse "ABC".DEF.G"HI"@JKL.de                             = 81 =  OK    String: Ein startendes Anfuehrungezeichen muss direkt nach einem Punkt kommen
     *   241 - assertIsFalse "AB"C.DEF."GHI"@JKL.de                             = 87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   242 - assertIsFalse "ABC".DEF."GHI"J@KL.de                             = 87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   243 - assertIsFalse "AB"C.D"EF"@GHI.DE                                 = 87 =  OK    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   244 - assertIsFalse "Ende.am.Eingabeende"                              = 88 =  OK    String: Der String endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   245 - assertIsFalse 0"00.000"@GHI.JKL                                  = 80 =  OK    String: Ein startendes Anfuehrungszeichen muss am Anfang kommen, der Zeichenzaehler darf nicht groesser als 0 sein
     *   246 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =  2 =  OK 
     *   247 - assertIsTrue  ABC.DEF@[001.002.003.004]                          =  2 =  OK 
     *   248 - assertIsTrue  "ABC.DEF"@[127.0.0.1]                              =  3 =  OK 
     *   249 - assertIsFalse ABC.DEF[1.2.3.4]                                   = 51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   250 - assertIsFalse [1.2.3.4]@[5.6.7.8]                                = 51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   251 - assertIsFalse ABC.DEF[@1.2.3.4]                                  = 51 =  OK    IP-Adressteil: IP-Adresse vor AT-Zeichen
     *   252 - assertIsFalse "[1.2.3.4]"@[5.6.7.8]                              = 89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   253 - assertIsFalse ABC.DEF@MyDomain[1.2.3.4]                          = 52 =  OK    IP-Adressteil: IP-Adresse muss direkt nach dem AT-Zeichen kommen (korrekte Kombination "@[")
     *   254 - assertIsFalse ABC.DEF@[1.00002.3.4]                              = 53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   255 - assertIsFalse ABC.DEF@[1.2.3.456]                                = 54 =  OK    IP4-Adressteil: Byte-Overflow
     *   256 - assertIsFalse ABC.DEF@[..]                                       = 55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   257 - assertIsFalse ABC.DEF@[.2.3.4]                                   = 55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   258 - assertIsFalse ABC.DEF@[]                                         = 57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   259 - assertIsFalse ABC.DEF@[1]                                        = 57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   260 - assertIsFalse ABC.DEF@[1.2]                                      = 57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   261 - assertIsFalse ABC.DEF@[1.2.3]                                    = 57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   262 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =  2 =  OK 
     *   263 - assertIsFalse ABC.DEF@[1.2.3.4.5]                                = 56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   264 - assertIsFalse ABC.DEF@[1.2.3.4.5.6]                              = 56 =  OK    IP4-Adressteil: zu viele Trennzeichen
     *   265 - assertIsFalse ABC.DEF@[MyDomain.de]                              = 59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   266 - assertIsFalse ABC.DEF@[1.2.3.]                                   = 58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   267 - assertIsFalse ABC.DEF@[1.2.3. ]                                  = 59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   268 - assertIsFalse ABC.DEF@[1.2.3.4].de                               = 60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   269 - assertIsFalse ABC.DE@[1.2.3.4][5.6.7.8]                          = 60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   270 - assertIsFalse ABC.DEF@[1.2.3.4                                   = 61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   271 - assertIsFalse ABC.DEF@1.2.3.4]                                   = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   272 - assertIsTrue  ABC.DEF@[IPv6:2001:db8::1]                         =  4 =  OK 
     *   273 - assertIsFalse ABC@[IP                                            = 59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   274 - assertIsFalse ABC@[IPv6]                                         = 40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   275 - assertIsFalse ABC@[IPv6:]                                        = 43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   276 - assertIsFalse ABC@[IPv6:1]                                       = 43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   277 - assertIsFalse ABC@[IPv6:1:2]                                     = 43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   278 - assertIsTrue  ABC@[IPv6:1:2:3]                                   =  4 =  OK 
     *   279 - assertIsTrue  ABC@[IPv6:1:2:3:4]                                 =  4 =  OK 
     *   280 - assertIsFalse ABC@[IPv6:1:2:3:4:5:]                              = 44 =  OK    IP6-Adressteil: ungueltige Kombination ":]"
     *   281 - assertIsTrue  ABC@[IPv6:1:2:3:4:5:6]                             =  4 =  OK 
     *   282 - assertIsTrue  ABC@[IPv6:1:2:3:4:5:6:7]                           =  4 =  OK 
     *   283 - assertIsTrue  ABC@[IPv6:1:2:3:4:5:6:7:8]                         =  4 =  OK 
     *   284 - assertIsFalse ABC@[IPv6:1:2:3:4:5:6:7:8:9]                       = 42 =  OK    IP6-Adressteil: zu viele Trennzeichen, maximal 8 Trennzeichen
     *   285 - assertIsFalse ABC@[IPv4:1:2:3:4]                                 = 40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   286 - assertIsFalse ABC@[I127.0.0.1]                                   = 40 =  OK    IP6-Adressteil: String "IPv6:" erwartet
     *   287 - assertIsFalse ABC@[D127.0.0.1]                                   = 59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   288 - assertIsFalse ABC@[iPv6:2001:db8::1]                             = 49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   289 - assertIsTrue  ABC.DEF@[IPv6:1:2:3::5:6:7:8]                      =  4 =  OK 
     *   290 - assertIsFalse ABC.DEF@[IPv6:1:2:3::5::7:8]                       = 50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   291 - assertIsFalse ABC.DEF@[IPv6::ffff:.127.0.1]                      = 55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   292 - assertIsTrue  ABC.DEF@[IPv6::FFFF:127.0.0.1]                     =  4 =  OK 
     *   293 - assertIsTrue  ABC.DEF@[IPv6::ffff:127.0.0.1]                     =  4 =  OK 
     *   294 - assertIsFalse ABC.DEF@[IPv6::fff:127.0.0.1]                      = 62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   295 - assertIsFalse ABC.DEF@[IPv6::1234:127.0.0.1]                     = 62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   296 - assertIsFalse ABC.DEF@[IPv6:127.0.0.1]                           = 47 =  OK    IP6-Adressteil: IPv4 in IPv6 - Trennzeichenanzahl falsch
     *   297 - assertIsFalse ABC.DEF@[IPv6:::127.0.0.1]                         = 62 =  OK    IP6-Adressteil: IPv4 in IPv6 - falsche Angabe der IP4-Einbettung (Zeichenfolge 'ffff' erwartet)
     *   298 - assertIsTrue  ABC.DEF@[1.2.3.4]                                  =  2 =  OK 
     *   299 - assertIsFalse ABC.DEF@[1.2.3.Z]                                  = 59 =  OK    IP4-Adressteil: Falsches Zeichen in der IP-Adresse
     *   300 - assertIsFalse ABC.DEF@[12.34]                                    = 57 =  OK    IP4-Adressteil: IP-Adresse Trennzeichenanzahl muss 3 sein
     *   301 - assertIsFalse ABC.DEF@[1.2.3.]                                   = 58 =  OK    IP4-Adressteil: ungueltige Kombination ".]"
     *   302 - assertIsFalse ABC.DEF@[1.2.3.4]                                  = 60 =  OK    IP4-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   303 - assertIsFalse ABC.DEF@[1.2.3.4                                   = 61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   304 - assertIsFalse ABC.DEF@[1234.5.6.7]                               = 53 =  OK    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *   305 - assertIsFalse ABC.DEF@[1.2...3.4]                                = 55 =  OK    IP4-Adressteil: keine Ziffern vorhanden
     *   306 - assertIsTrue  ABC.DEF@[IPv6:1:2:3:4:5:6]                         =  4 =  OK 
     *   307 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:Z]                         = 49 =  OK    IP6-Adressteil: Falsches Zeichen in der IP-Adresse
     *   308 - assertIsFalse ABC.DEF@[IPv6:12:34]                               = 43 =  OK    IP6-Adressteil: Zu wenig Trennzeichen
     *   309 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:]                          = 44 =  OK    IP6-Adressteil: ungueltige Kombination ":]"
     *   310 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6]                         = 45 =  OK    IP6-Adressteil: Abschlusszeichen "]" muss am Ende stehen
     *   311 - assertIsFalse ABC.DEF@[IPv6:1:2:3:4:5:6                          = 61 =  OK    IP-Adressteil: Kein Abschluss der IP-Adresse auf ']'
     *   312 - assertIsFalse ABC.DEF@[IPv6:12345:6:7:8:9]                       = 46 =  OK    IP6-Adressteil: zu viele Ziffern, maximal 4 Ziffern
     *   313 - assertIsFalse ABC.DEF@[IPv6:1:2:3:::6:7:8]                       = 50 =  OK    IP6-Adressteil: Es darf nur einmal ein Zweier-Doppelpunkt vorhanden sein.
     *   314 - assertIsTrue  (ABC)DEF@GHI.JKL                                   =  6 =  OK 
     *   315 - assertIsTrue  ABC(DEF)@GHI.JKL                                   =  6 =  OK 
     *   316 - assertIsFalse AB(CD)EF@GHI.JKL                                   = 97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   317 - assertIsFalse AB.(CD).EF@GHI.JKL                                 = 97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   318 - assertIsFalse AB."(CD)".EF@GHI.JKL                               = 89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   319 - assertIsFalse (ABCDEF)@GHI.JKL                                   = 98 =  OK    Kommentar: Kein lokaler Part vorhanden
     *   320 - assertIsFalse (ABCDEF).@GHI.JKL                                  = 30 =  OK    Trennzeichen: kein Beginn mit einem Punkt
     *   321 - assertIsFalse (AB"C)DEF@GHI.JKL                                  = 92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   322 - assertIsFalse (AB\C)DEF@GHI.JKL                                  = 91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   323 - assertIsFalse (AB\@C)DEF@GHI.JKL                                 = 91 =  OK    Kommentar: Ungueltige Escape-Sequenz im Kommentar
     *   324 - assertIsFalse ABC(DEF@GHI.JKL                                    = 93 =  OK    Kommentar: kein abschliessendes Zeichen fuer den Komentar gefunden. ')' erwartet
     *   325 - assertIsFalse ABC.DEF@GHI)JKL                                    = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   326 - assertIsFalse )ABC.DEF@GHI.JKL                                   = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   327 - assertIsTrue  ABC.DEF@(GHI).JKL                                  =  6 =  OK 
     *   328 - assertIsFalse ABC(DEF@GHI).JKL                                   = 97 =  OK    Kommentar: Nach dem Kommentar muss ein AT-Zeichen kommen
     *   329 - assertIsFalse (ABC.DEF@GHI.JKL)                                  = 95 =  OK    Kommentar: Der Kommentar endet am Stringende (Vorzeitiges Ende der Eingabe)
     *   330 - assertIsFalse (A(B(C)DEF@GHI.JKL                                 = 92 =  OK    Kommentar: Ungueltiges Zeichen im Kommentar
     *   331 - assertIsFalse (A)B)C)DEF@GHI.JKL                                 = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   332 - assertIsFalse (A)BCDE(F)@GHI.JKL                                 = 99 =  OK    Kommentar: kein zweiter Kommentar gueltig
     *   333 - assertIsTrue  ABC DEF <ABC.DEF@GHI.JKL>                          =  0 =  OK 
     *   334 - assertIsTrue  <ABC.DEF@GHI.JKL> ABC DEF                          =  0 =  OK 
     *   335 - assertIsFalse ABC DEF ABC.DEF@GHI.JKL>                           = 16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   336 - assertIsFalse <ABC.DEF@GHI.JKL ABC DEF                           = 17 =  OK    Struktur: keine schliessende eckige Klammer gefunden.
     *   337 - assertIsTrue  "ABC DEF "<ABC.DEF@GHI.JKL>                        =  0 =  OK 
     *   338 - assertIsFalse "ABC<DEF>"@JKL.DE                                  = 89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   339 - assertIsFalse >                                                  = 16 =  OK    Struktur: keine oeffnende eckige Klammer gefunden.
     *   340 - assertIsFalse "ABC<DEF@GHI.COM>"@JKL.DE                          = 89 =  OK    String: Ungueltiges Zeichen innerhalb Anfuehrungszeichen
     *   341 - assertIsFalse ABC DEF <ABC.<DEF@GHI.JKL>                         = 18 =  OK    Struktur: Fehler in Adress-String-X
     *   342 - assertIsFalse <ABC.DEF@GHI.JKL> MNO <PQR.STU@VW.XYZ>             = 18 =  OK    Struktur: Fehler in Adress-String-X
     *   343 - assertIsFalse ABC DEF <ABC.DEF@GHI.JKL                           = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   344 - assertIsFalse ABC.DEF@GHI.JKL> ABC DEF                           = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   345 - assertIsFalse ABC DEF >ABC.DEF@GHI.JKL<                          = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   346 - assertIsFalse >ABC.DEF@GHI.JKL< ABC DEF                          = 22 =  OK    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   347 - assertIsFalse ABC DEF <A@A>                                      = 12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   348 - assertIsFalse <A@A> ABC DEF                                      = 12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   349 - assertIsFalse ABC DEF <>                                         = 12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     *   350 - assertIsFalse <> ABC DEF                                         = 12 =  OK    Laenge: Laengenbegrenzungen stimmen nicht
     */

    try
    {
      assertIsFalse( null );
      assertIsFalse( "" );
      assertIsFalse( "..@test.com" );
      assertIsFalse( ".a@test.com" );
      assertIsFalse( "ab@sd@dd" );
      assertIsFalse( ".@s.dd" );
      assertIsFalse( "ab@988.120.150.10" );
      assertIsFalse( "ab@120.256.256.120" );
      assertIsFalse( "ab@120.25.1111.120" );
      assertIsFalse( "ab@[188.120.150.10" );
      assertIsFalse( "ab@188.120.150.10]" );
      assertIsFalse( "ab@[188.120.150.10].com" );
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
      assertIsFalse( "Joe Smith &lt;email@domain.com&gt;" );
      assertIsFalse( "email.domain.com" );
      assertIsFalse( "email@domain@domain.com" );
      assertIsFalse( ".email@domain.com" );
      assertIsFalse( "email.@domain.com" );
      assertIsFalse( "email..email@domain.com" );
      assertIsFalse( "email@-domain.com" );
      assertIsFalse( "email@domain-.com" );
      assertIsFalse( "email@domain.com-" );
      assertIsFalse( "email@.domain.com" );
      assertIsFalse( "email@domain.com." );
      assertIsFalse( "email@domain..com" );
      assertIsFalse( "email@111.222.333" );
      assertIsFalse( "email@111.222.333.256" );
      assertIsFalse( "email@[123.123.123.123" );
      assertIsFalse( "email@[123.123.123].123" );
      assertIsFalse( "email@123.123.123.123]" );
      assertIsFalse( "email@123.123.[123.123]" );
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
      assertIsFalse( "email( (nested) )@plus.com" );
      assertIsFalse( "email)mirror(@plus.com" );
      assertIsFalse( "email@plus.com (not closed comment" );
      assertIsFalse( "email(with @ in comment)plus.com" );
      assertIsFalse( "\"Joe Smith email@domain.com" );
      assertIsFalse( "\"Joe Smith' email@domain.com" );
      assertIsFalse( "\"Joe Smith\"email@domain.com" );
      assertIsFalse( "email@mailto:domain.com" );
      assertIsFalse( "mailto:mailto:email@domain.com" );
      assertIsFalse( "Display Name <email@plus.com> (after name with display)" );
      assertIsFalse( "ReDoSaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );
      assertIsTrue( "w@com" );
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
      assertIsTrue( "local@2001:0db8:85a3:0000:0000:8a2e:0370:7334" );
      assertIsTrue( "local@[2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsTrue( "local@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsFalse( "local@[IPA6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsFalse( "local@[APv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
      assertIsTrue( "local@[aaa6:2001:0db8:85a3:0000:0000:8a2e:0370:7334]" );
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
      assertIsTrue( "email@domain" );
      assertIsTrue( "?????@domain.com" );
      assertIsTrue( "local@?????.com" );
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
      assertIsTrue( "email@domain-one.com" );
      assertIsTrue( "_______@domain.com" );
      assertIsTrue( "email@domain.topleveldomain" );
      assertIsTrue( "email@domain.co.jp" );
      assertIsTrue( "firstname-lastname@domain.com" );
      assertIsTrue( "firstname-lastname@d.com" );
      assertIsTrue( "FIRSTNAME-LASTNAME@d--n.com" );
      assertIsTrue( "first-name-last-name@d-a-n.com" );
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
      assertIsTrue( "Joe Smith <email@domain.com>" );
      assertIsTrue( "john.smith@example.com" );
      assertIsTrue( "(comment)john.smith@example.com" );
      assertIsTrue( "john.smith(comment)@example.com" );
      assertIsTrue( "john.smith@(comment)example.com" );
      assertIsFalse( "john.smith@exampl(comment)e.com" );
      assertIsFalse( "john.s(comment)mith@example.com" );
      assertIsFalse( "john.smith(comment)@(comment)example.com" );
      assertIsFalse( "john.smith(com@ment)example.com" );
      assertIsTrue( "john.smith@example.com(comment)" );
      assertIsTrue( "domain.starts.with.digit@2domain.com" );
      assertIsTrue( "domain.ends.with.digit@domain2.com" );
      assertIsTrue( "domain.label.with.63.characters@123456789012345678901234567890123456789012345678901234567890123.com" );
      assertIsFalse( "domain.label.with.64.characters@1234567890123456789012345678901234567890123456789012345678901234.com" );
      assertIsTrue( "domain.label.with.63.characters@123456789012345678901234567890123456789012345678901234567890123.123456789012345678901234567890123456789012345678901234567890123.com" );
      assertIsFalse( "domain.label.with.63.and.64.characters@123456789012345678901234567890123456789012345678901234567890123.1234567890123456789012345678901234567890123456789012345678901234.com" );
      assertIsTrue( "email@domain.com (joe Smith)" );
      assertIsTrue( "\"Joe Smith\" email@domain.com" );
      assertIsTrue( "\"Joe\\tSmith\" email@domain.com" );
      assertIsTrue( "\"Joe\"Smith\" email@domain.com" );
      assertIsTrue( "Test |<gaaf <email@domain.com>" );
      assertIsTrue( "MailTo:casesensitve@domain.com" );
      assertIsTrue( "mailto:email@domain.com" );
      assertIsTrue( "Joe Smith <mailto:email@domain.com>" );
      assertIsTrue( "Joe Smith <mailto:email(with comment)@domain.com>" );
      assertIsTrue( "\"With extra < within quotes\" Display Name<email@domain.com>" );
      assertIsTrue( "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" );

      ;
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
      assertIsTrue( "A@B.CD" );
      assertIsTrue( "ABC1.DEF2@GHI3.JKL4" );
      assertIsTrue( "ABC.DEF_@GHI.JKL" );
      assertIsTrue( "#ABC.DEF@GHI.JKL" );
      assertIsFalse( null );
      assertIsFalse( " " );
      assertIsFalse( " " );
      assertIsFalse( "ABCDEFGHIJKLMNOP" );
      assertIsFalse( "ABC.DEF@GHI.J" );
      assertIsTrue( "ME@MYSELF.LOCALHOST" );
      assertIsFalse( "ME@MYSELF.LOCALHORST" );
      assertIsFalse( "ABC.DEF@GHI.2KL" );
      assertIsFalse( "ABC.DEF@GHI.JK-" );
      assertIsFalse( "ABC.DEF@GHI.JK_" );
      assertIsTrue( "ABC.DEF@GHI.JK2" );
      assertIsTrue( "ABC.DEF@2HI.JKL" );
      assertIsFalse( "ABC.DEF@-HI.JKL" );
      assertIsFalse( "ABC.DEF@_HI.JKL" );
      assertIsFalse( "A . B & C . D" );
      assertIsFalse( "(?).[!]@{&}.<:>" );
      assertIsFalse( ".ABC.DEF@GHI.JKL" );
      assertIsFalse( "ABC..DEF@GHI.JKL" );
      assertIsFalse( "ABC.DEF@GHI..JKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL.." );
      assertIsFalse( "ABC.DEF.@GHI.JKL" );
      assertIsFalse( "ABC.DEF@." );
      assertIsFalse( "ABC.DEF@.GHI.JKL" );
      assertIsFalse( "ABCDEF@GHIJKL" );
      assertIsFalse( "ABC.DEF@GHIJKL" );
      assertIsFalse( "ABC.DEF@GHI.JKL." );
      assertIsFalse( "@GHI.JKL" );
      assertIsFalse( "ABC.DEF@" );
      assertIsFalse( "ABC.DEF@@GHI.JKL" );
      assertIsTrue( "\"ABC.DEF\"@GHI.DE" );
      assertIsTrue( "\"ABC DEF\"@GHI.DE" );
      assertIsFalse( "ABC DEF@GHI.DE" );
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
      assertIsFalse( "ABC.DEF@[IPv6::ffff:.127.0.1]" );
      assertIsTrue( "ABC.DEF@[IPv6::FFFF:127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[IPv6::ffff:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::fff:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6::1234:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6:127.0.0.1]" );
      assertIsFalse( "ABC.DEF@[IPv6:::127.0.0.1]" );
      assertIsTrue( "ABC.DEF@[1.2.3.4]" );
      assertIsFalse( "ABC.DEF@[1.2.3.Z]" );
      assertIsFalse( "ABC.DEF@[12.34]" );
      assertIsFalse( "ABC.DEF@[1.2.3.]" );
      assertIsFalse( "ABC.DEF@[1.2.3.4] " );
      assertIsFalse( "ABC.DEF@[1.2.3.4" );
      assertIsFalse( "ABC.DEF@[1234.5.6.7]" );
      assertIsFalse( "ABC.DEF@[1.2...3.4]" );
      assertIsTrue( "ABC.DEF@[IPv6:1:2:3:4:5:6]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:Z]" );
      assertIsFalse( "ABC.DEF@[IPv6:12:34]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6] " );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:4:5:6" );
      assertIsFalse( "ABC.DEF@[IPv6:12345:6:7:8:9]" );
      assertIsFalse( "ABC.DEF@[IPv6:1:2:3:::6:7:8]" );
      assertIsTrue( "(ABC)DEF@GHI.JKL" );
      assertIsTrue( "ABC(DEF)@GHI.JKL" );
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
      assertIsTrue( "ABC.DEF@(GHI).JKL" );
      assertIsFalse( "ABC(DEF@GHI).JKL" );
      assertIsFalse( "(ABC.DEF@GHI.JKL)" );
      assertIsFalse( "(A(B(C)DEF@GHI.JKL" );
      assertIsFalse( "(A)B)C)DEF@GHI.JKL" );
      assertIsFalse( "(A)BCDE(F)@GHI.JKL" );
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

    }
    catch ( Exception err_inst )
    {
      System.out.println( err_inst.getMessage() );

      err_inst.printStackTrace( System.out );
    }

    System.exit( 0 );
  }

  private static int LAUFENDE_ZAHL = 0;

  /**
   * Erhoeht die laufende Zahl und gibt diese zurueck.
   * @return eine fortlaufende Zahl
   */
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

    System.out.println( getID() + " - assertIsTrue  " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? " " : "" ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER ####    " + FkEMail.getFehlerText( return_code ) ) );
  }

  private static void assertIsFalse( String pString )
  {
    int return_code = FkEMail.checkEMailAdresse( pString );

    boolean knz_soll_wert = false;

    boolean is_true = return_code < 10;

    System.out.println( getID() + " - assertIsFalse " + FkString.getFeldLinksMin( ( pString == null ? "null" : pString ), 50 ) + " = " + ( return_code < 10 ? " " : "" ) + return_code + " = " + ( is_true == knz_soll_wert ? " OK " : " #### FEHLER #### " ) + "   " + FkEMail.getFehlerText( return_code ) );
  }

}
