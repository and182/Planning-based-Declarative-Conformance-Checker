(define (problem Align) (:domain Mining)
(:objects
task10 - task1
task20 - task2
)
(:init
(first_task_of_the_trace task20)
(last_task_of_the_trace task20)
(traced task20)
)
(:goal
(and 
(existence-task1)
(absence-task2)
)
)
)