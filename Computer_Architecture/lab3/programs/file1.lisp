; program "maths"

(let (a 0))
(let (b 0))
(let (flag false))
(print "введите a")
(setq a (input))
(setq (b (if flag 2 -2)))

(function process-maths (a b k)
  (if (< a 0)
      (print (- (+ (* a k) (* -2 b)) (/ b a)))
      (print "a is negative")))

(function run-maths ()
  (let ((n (parse-int (input "Введите количество итераций: "))))
    (loop for i from 1 to n do
        (process-maths a b i)))

(run-maths)