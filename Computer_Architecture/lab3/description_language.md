# Язык программирования (psln)

```ebnf
<program> ::= <expression>*

<expression> ::= <comment>
               | <let-expression>
               | <set-expression>
               | <function-definition>
               | <if-expression>
               | <loop-expression>
               | <print-expression>
               | <input-expression>
               | <parse-int-expression>

<comment> ::= "; " <text>

<let-expression> ::= "(let ((" <variable> <value> "))" <expression>* ")"

<set-expression> ::= "(setq " <variable> <expression> ")"

<function-definition> ::= "(function " <function-name> "(" <parameter-list> ") " <expression>* ")"

<if-expression> ::= "(if " <condition> <true-branch> <false-branch> ")"

<loop-expression> ::= "(loop for " <variable> " from " <start-value> " to " <end-value> " do" <expression>* ")"

<print-expression> ::= "(print " <expression> ")"

<input-expression> ::= "(input)"

<parse-int-expression> ::= "(parse-int " <expression> ")"

<group> ::= "(group " <expression>* ")"

<condition> ::= <expression>

<true-branch> ::= <expression>

<false-branch> ::= <expression>

<parameter-list> ::= <variable>*

<variable> ::= <identifier>

<value> ::= <integer>
          | <boolean>
          | <string>
          | <expression>

<identifier> ::= [a-zA-Z_][a-zA-Z0-9_]*

<integer> ::= [0-9]+

<boolean> ::= "true"
            | "false"

<string> ::= "\"" [^\"]* "\""
