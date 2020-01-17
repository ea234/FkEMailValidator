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
     * 
     * ----------------------------------------------------------------------------------------------------
     * 
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
     *    85 - assertIsTrue  local@[2001:0db8:85a3:0000:0000:8a2e:0370:7334]    = 53 =  #### FEHLER ####    IP4-Adressteil: zu viele Ziffern, maximal 3 Ziffern
     *    86 - assertIsTrue  local@[IPv6:2001:0db8:85a3:0000:0000:8a2e:0370:7334] =  4 =  OK 
     *    87 - assertIsTrue  2@bde.cc                                           =  0 =  OK 
     *    88 - assertIsTrue  -@bde.cc                                           =  0 =  OK 
     *    89 - assertIsTrue  a2@bde.cc                                          =  0 =  OK 
     *    90 - assertIsTrue  a-b@bde.cc                                         =  0 =  OK 
     *    91 - assertIsTrue  ab@b-de.cc                                         =  0 =  OK 
     *    92 - assertIsTrue  a+b@bde.cc                                         =  0 =  OK 
     *    93 - assertIsTrue  f.f.f@bde.cc                                       =  0 =  OK 
     *    94 - assertIsTrue  ab_c@bde.cc                                        =  0 =  OK 
     *    95 - assertIsTrue  _-_@bde.cc                                         =  0 =  OK 
     *    96 - assertIsTrue  k.haak@12move.nl                                   =  0 =  OK 
     *    97 - assertIsTrue  K.HAAK@12MOVE.NL                                   =  0 =  OK 
     *    98 - assertIsTrue  email@domain.com                                   =  0 =  OK 
     *    99 - assertIsTrue  email@domain                                       = 34 =  #### FEHLER ####    Trennzeichen: keinen Punkt gefunden (Es muss mindestens ein Punkt fuer den Domain-Trenner vorhanden sein)
     *   100 - assertIsTrue  ?????@domain.com                                   =  0 =  OK 
     *   101 - assertIsTrue  local@?????.com                                    = 21 =  #### FEHLER ####    Zeichen: Sonderzeichen im Domain-Part nicht erlaubt
     *   102 - assertIsTrue  firstname.lastname@domain.com                      =  0 =  OK 
     *   103 - assertIsTrue  email@subdomain.domain.com                         =  0 =  OK 
     *   104 - assertIsTrue  firstname+lastname@domain.com                      =  0 =  OK 
     *   105 - assertIsTrue  email@123.123.123.123                              = 23 =  #### FEHLER ####    Zeichen: Top-Level-Domain darf nicht mit Zahl beginnen
     *   106 - assertIsTrue  email@[123.123.123.123]                            =  2 =  OK 
     *   107 - assertIsTrue  1234567890@domain.com                              =  0 =  OK 
     *   108 - assertIsTrue  a@domain.com                                       =  0 =  OK 
     *   109 - assertIsTrue  a.b.c.d@domain.com                                 =  0 =  OK 
     *   110 - assertIsTrue  aap.123.noot.mies@domain.com                       =  0 =  OK 
     *   111 - assertIsTrue  1@domain.com                                       =  0 =  OK 
     *   112 - assertIsTrue  email@domain-one.com                               =  0 =  OK 
     *   113 - assertIsTrue  _______@domain.com                                 =  0 =  OK 
     *   114 - assertIsTrue  email@domain.topleveldomain                        = 15 =  #### FEHLER ####    Laenge: Top-Level-Domain darf nicht mehr als X-Stellen lang sein. (X ist hier 10)
     *   115 - assertIsTrue  email@domain.co.jp                                 =  0 =  OK 
     *   116 - assertIsTrue  firstname-lastname@domain.com                      =  0 =  OK 
     *   117 - assertIsTrue  firstname-lastname@d.com                           =  0 =  OK 
     *   118 - assertIsTrue  FIRSTNAME-LASTNAME@d--n.com                        =  0 =  OK 
     *   119 - assertIsTrue  first-name-last-name@d-a-n.com                     =  0 =  OK 
     *   120 - assertIsTrue  {local{name{{with{@leftbracket.com                 =  0 =  OK 
     *   121 - assertIsTrue  }local}name}}with{@rightbracket.com                =  0 =  OK 
     *   122 - assertIsTrue  |local||name|with|@pipe.com                        =  0 =  OK 
     *   123 - assertIsTrue  %local%%name%with%@percentage.com                  =  0 =  OK 
     *   124 - assertIsTrue  $local$$name$with$@dollar.com                      =  0 =  OK 
     *   125 - assertIsTrue  &local&&name&with&$@amp.com                        =  0 =  OK 
     *   126 - assertIsTrue  #local##name#with#@hash.com                        =  0 =  OK 
     *   127 - assertIsTrue  ~local~~name~with~@tilde.com                       =  0 =  OK 
     *   128 - assertIsTrue  !local!!name!with!@exclamation.com                 =  0 =  OK 
     *   129 - assertIsTrue  ?local??name?with?@question.com                    =  0 =  OK 
     *   130 - assertIsTrue  *local**name*with*@asterisk.com                    =  0 =  OK 
     *   131 - assertIsTrue  `local``name`with`@grave-accent.com                =  0 =  OK 
     *   132 - assertIsTrue  ^local^^name^with^@xor.com                         =  0 =  OK 
     *   133 - assertIsTrue  =local==name=with=@equality.com                    =  0 =  OK 
     *   134 - assertIsTrue  +local++name+with+@equality.com                    =  0 =  OK 
     *   135 - assertIsTrue  Joe Smith <email@domain.com>                       =  0 =  OK 
     *   136 - assertIsTrue  email@domain.com (joe Smith)                       = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   137 - assertIsTrue  "Joe Smith" email@domain.com                       = 87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   138 - assertIsTrue  "Joe\tSmith" email@domain.com                      = 84 =  #### FEHLER ####    String: Ungueltige Escape-Sequenz im String
     *   139 - assertIsTrue  "Joe"Smith" email@domain.com                       = 87 =  #### FEHLER ####    String: Nach einem abschliessendem Anfuehrungszeichen muss ein AT-Zeichen oder ein Punkt folgen
     *   140 - assertIsTrue  Test |<gaaf <email@domain.com>                     = 18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *   141 - assertIsTrue  MailTo:casesensitve@domain.com                     = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   142 - assertIsTrue  mailto:email@domain.com                            = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   143 - assertIsTrue  Joe Smith <mailto:email@domain.com>                = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   144 - assertIsTrue  Joe Smith <mailto:email(with comment)@domain.com>  = 22 =  #### FEHLER ####    Zeichen: ungueltiges Zeichen in der Eingabe gefunden
     *   145 - assertIsTrue  "With extra < within quotes" Display Name<email@domain.com> = 18 =  #### FEHLER ####    Struktur: Fehler in Adress-String-X
     *   146 - assertIsTrue  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa.aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa = 13 =  #### FEHLER ####    Laenge: RFC 5321 = SMTP-Protokoll = maximale Laenge des Local-Parts sind 64 Bytes
     * 
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

      System.out.println( "" );
      System.out.println( "----------------------------------------------------------------------------------------------------" );
      System.out.println( "" );
      
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
