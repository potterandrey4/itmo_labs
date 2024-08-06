import re

class Lexer:
    def __init__(self, code):
        self.tokens = []
        self.current = 0
        self.code = code
        self.tokenize()

    def tokenize(self):
        token_specification = [
            ('NUMBER', r'\d+(\.\d*)?'), 
            ('IDENT',  r'[A-Za-z_][A-Za-z0-9_]*'), 
            ('STRING', r'"(?:\\.|[^"\\])*"'), 
            ('LPAREN', r'\('),           
            ('RPAREN', r'\)'),           
            ('QUOTE',  r'\''),
            ('OP',     r'[-+*/]'),       
            ('EQ',     r'='),            
            ('SKIP',   r'[ \t]+'),       
            ('NEWLINE',r'\n'),           
            ('MISMATCH', r'.'),          
        ]
        tok_regex = '|'.join('(?P<%s>%s)' % pair for pair in token_specification)
        get_token = re.compile(tok_regex).match
        line_number = 1
        mo = get_token(self.code)
        while mo is not None:
            kind = mo.lastgroup
            value = mo.group(kind)
            if kind == 'NUMBER':
                value = float(value) if '.' in value else int(value)
            elif kind == 'STRING':
                value = value[1:-1]
            elif kind == 'NEWLINE':
                line_number += 1
                kind = 'SKIP'
            elif kind == 'SKIP':
                pass
            elif kind == 'MISMATCH':
                raise RuntimeError(f'{value!r} unexpected on line {line_number}')
            if kind != 'SKIP':
                self.tokens.append((kind, value))
            mo = get_token(self.code, mo.end())
        self.tokens.append(('EOF', 'EOF'))

    def next_token(self):
        token = self.tokens[self.current]
        self.current += 1
        return token

class Parser:
    def __init__(self, lexer):
        self.lexer = lexer
        self.current_token = self.lexer.next_token()

    def eat(self, token_type):
        if self.current_token[0] == token_type:
            self.current_token = self.lexer.next_token()
        else:
            raise Exception(f"Expected token {token_type}, got {self.current_token}")

    def parse(self):
        return self.program()

    def program(self):
        nodes = []
        while self.current_token[0] != 'EOF':
            if self.current_token[0] == 'LPAREN':
                self.eat('LPAREN')
                node = self.expr()
                self.eat('RPAREN')
                nodes.append(node)
            else:
                self.eat('EOF')
        return nodes

    def expr(self):
        if self.current_token[0] == 'IDENT':
            ident = self.current_token[1]
            self.eat('IDENT')
            if ident == 'let':
                return self.let_expr()
            elif ident == 'setq':
                return self.setq_expr()
            elif ident == 'print':
                return self.print_expr()
            elif ident == 'if':
                return self.if_expr()
            elif ident == 'function':
                return self.function_expr()
            elif ident == 'run-maths':
                return self.run_maths_expr()
            elif ident == 'input':
                return ('input',)
        elif self.current_token[0] == 'NUMBER':
            number = self.current_token[1]
            self.eat('NUMBER')
            return ('number', number)
        elif self.current_token[0] == 'STRING':
            string = self.current_token[1]
            self.eat('STRING')
            return ('string', string)
        else:
            raise Exception(f"Unexpected token: {self.current_token}")

    def let_expr(self):
        self.eat('LPAREN')
        ident = self.current_token[1]
        self.eat('IDENT')
        expr = self.expr()
        self.eat('RPAREN')
        return ('let', ident, expr)

    def setq_expr(self):
        ident = self.current_token[1]
        self.eat('IDENT')
        expr = self.expr()
        return ('setq', ident, expr)

    def print_expr(self):
        expr = self.expr()
        return ('print', expr)

    def if_expr(self):
        cond = self.expr()
        true_expr = self.expr()
        false_expr = self.expr()
        return ('if', cond, true_expr, false_expr)

    def function_expr(self):
        name = self.current_token[1]
        self.eat('IDENT')
        self.eat('LPAREN')
        params = []
        while self.current_token[0] != 'RPAREN':
            params.append(self.current_token[1])
            self.eat('IDENT')
        self.eat('RPAREN')
        body = self.expr()
        return ('function', name, params, body)

    def run_maths_expr(self):
        return ('run-maths',)


class CodeGenerator:
    def __init__(self):
        self.instructions = []
        self.variables = {}
        self.label_count = 0

    def generate(self, ast):
        for node in ast:
            self.eval(node)

    def eval(self, node):
        if node[0] == 'let':
            _, ident, expr = node
            self.eval(expr)
            self.variables[ident] = len(self.variables)
            self.instructions.append(('STORE', self.variables[ident]))
        elif node[0] == 'setq':
            _, ident, expr = node
            self.eval(expr)
            self.instructions.append(('STORE', self.variables[ident]))
        elif node[0] == 'print':
            _, expr = node
            self.eval(expr)
            self.instructions.append(('PRINT',))
        elif node[0] == 'if':
            _, cond, true_expr, false_expr = node
            self.eval(cond)
            false_label = self.new_label()
            end_label = self.new_label()
            self.instructions.append(('JZ', false_label))
            self.eval(true_expr)
            self.instructions.append(('JMP', end_label))
            self.instructions.append(('LABEL', false_label))
            self.eval(false_expr)
            self.instructions.append(('LABEL', end_label))
        elif node[0] == 'function':
            _, name, params, body = node
            self.instructions.append(('LABEL', name))
            for param in params:
                self.variables[param] = len(self.variables)
            self.eval(body)
            self.instructions.append(('RET',))
        elif node[0] == 'run-maths':
            self.instructions.append(('CALL', 'run-maths'))
        elif node[0] == 'number':
            _, value = node
            self.instructions.append(('PUSH', value))
        elif node[0] == 'string':
            _, value = node
            self.instructions.append(('PUSH', value))
        elif node[0] == 'input':
            self.instructions.append(('INPUT',))

    def new_label(self):
        label = f'label_{self.label_count}'
        self.label_count += 1
        return label

    def to_binary(self):
        binary_instructions = []
        for instr in self.instructions:
            if instr[0] == 'PUSH':
                binary_instructions.append(0b0001)
                binary_instructions.append(instr[1])
            elif instr[0] == 'STORE':
                binary_instructions.append(0b1110)
                binary_instructions.append(instr[1])
            elif instr[0] == 'PRINT':
                binary_instructions.append(0b1011)
            elif instr[0] == 'JZ':
                binary_instructions.append(0b1001)
                binary_instructions.append(instr[1])
            elif instr[0] == 'JMP':
                binary_instructions.append(0b1010)
                binary_instructions.append(instr[1])
            elif instr[0] == 'LABEL':
                binary_instructions.append(0b0110)
                binary_instructions.append(instr[1])
            elif instr[0] == 'CALL':
                binary_instructions.append(0b0111)
                binary_instructions.append(instr[1])
            elif instr[0] == 'RET':
                binary_instructions.append(0b1000)
            elif instr[0] == 'INPUT':
                binary_instructions.append(0b1100)
        return binary_instructions

# Пример использования:
code = """
(let ((a 0))
  (let ((b 0))
    (let ((flag false))
      (print "введите a")
      (setq a (input))
      (setq b (if flag 2 -2))

      (function process-maths (a b k)
        (if (< a 0)
            (print (- (+ (* a k) (* -2 b)) (/ b a)))
            (print "a is negative")))

      (function run-maths ()
        (let ((n (parse-int (input "Введите количество итераций: "))))
          (loop for i from 1 to n do
              (process-maths a b i))))

      (run-maths))))
"""

lexer = Lexer(code)
parser = Parser(lexer)
ast = parser.parse()
code_generator = CodeGenerator()
code_generator.generate(ast)
binary_code = code_generator.to_binary()

# Печать бинарного кода
for instr in binary_code:
    print(bin(instr))
