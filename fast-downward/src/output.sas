begin_version
3
end_version
begin_metric
0
end_metric
14
begin_variable
var0
1
2
Atom absence-task2()
NegatedAtom absence-task2()
end_variable
begin_variable
var1
0
2
Atom existence-task1()
NegatedAtom existence-task1()
end_variable
begin_variable
var2
-1
2
Atom first_task_of_the_trace(task10)
NegatedAtom first_task_of_the_trace(task10)
end_variable
begin_variable
var3
-1
2
Atom first_task_of_the_trace(task20)
NegatedAtom first_task_of_the_trace(task20)
end_variable
begin_variable
var4
-1
2
Atom last_task_of_the_trace(task10)
NegatedAtom last_task_of_the_trace(task10)
end_variable
begin_variable
var5
-1
2
Atom last_task_of_the_trace(task20)
NegatedAtom last_task_of_the_trace(task20)
end_variable
begin_variable
var6
1
2
Atom new-axiom@0()
NegatedAtom new-axiom@0()
end_variable
begin_variable
var7
1
2
Atom new-axiom@1()
NegatedAtom new-axiom@1()
end_variable
begin_variable
var8
-1
2
Atom pre(task10, task10)
NegatedAtom pre(task10, task10)
end_variable
begin_variable
var9
-1
2
Atom pre(task10, task20)
NegatedAtom pre(task10, task20)
end_variable
begin_variable
var10
-1
2
Atom pre(task20, task10)
NegatedAtom pre(task20, task10)
end_variable
begin_variable
var11
-1
2
Atom pre(task20, task20)
NegatedAtom pre(task20, task20)
end_variable
begin_variable
var12
-1
2
Atom traced(task10)
NegatedAtom traced(task10)
end_variable
begin_variable
var13
-1
2
Atom traced(task20)
NegatedAtom traced(task20)
end_variable
0
begin_state
1
1
1
0
1
0
0
0
1
1
1
1
1
0
end_state
begin_goal
2
0 0
1 0
end_goal
32
begin_operator
addatthebeginning task10 task20
1
12 0
4
0 2 0 1
0 3 -1 0
0 10 -1 0
0 13 1 0
0
end_operator
begin_operator
addatthebeginning task20 task10
1
13 0
4
0 2 -1 0
0 3 0 1
0 9 -1 0
0 12 1 0
0
end_operator
begin_operator
addattheend task10 task20
1
12 0
4
0 4 0 1
0 5 -1 0
0 9 -1 0
0 13 1 0
0
end_operator
begin_operator
addattheend task20 task10
1
13 0
4
0 4 -1 0
0 5 0 1
0 10 -1 0
0 12 1 0
0
end_operator
begin_operator
addbetween task10 task10 task10
1
8 0
1
0 12 1 0
0
end_operator
begin_operator
addbetween task10 task10 task20
1
9 0
2
0 8 -1 0
0 12 1 0
0
end_operator
begin_operator
addbetween task10 task20 task10
0
4
0 8 0 1
0 9 -1 0
0 10 -1 0
0 13 1 0
0
end_operator
begin_operator
addbetween task10 task20 task20
1
9 0
2
0 11 -1 0
0 13 1 0
0
end_operator
begin_operator
addbetween task20 task10 task10
1
10 0
2
0 8 -1 0
0 12 1 0
0
end_operator
begin_operator
addbetween task20 task10 task20
0
4
0 9 -1 0
0 10 -1 0
0 11 0 1
0 12 1 0
0
end_operator
begin_operator
addbetween task20 task20 task10
1
10 0
2
0 11 -1 0
0 13 1 0
0
end_operator
begin_operator
addbetween task20 task20 task20
1
11 0
1
0 13 1 0
0
end_operator
begin_operator
addtotheemptytrace task10
1
6 1
3
0 2 -1 0
0 4 -1 0
0 12 -1 0
0
end_operator
begin_operator
addtotheemptytrace task20
1
6 1
3
0 3 -1 0
0 5 -1 0
0 13 -1 0
0
end_operator
begin_operator
deleteatthebeginning task10 task10
1
2 0
2
0 8 0 1
0 12 0 1
0
end_operator
begin_operator
deleteatthebeginning task10 task20
1
13 0
4
0 2 0 1
0 3 -1 0
0 9 0 1
0 12 0 1
0
end_operator
begin_operator
deleteatthebeginning task20 task10
1
12 0
4
0 2 -1 0
0 3 0 1
0 10 0 1
0 13 0 1
0
end_operator
begin_operator
deleteatthebeginning task20 task20
1
3 0
2
0 11 0 1
0 13 0 1
0
end_operator
begin_operator
deleteattheend task10 task10
1
4 0
2
0 8 0 1
0 12 0 1
0
end_operator
begin_operator
deleteattheend task10 task20
1
12 0
4
0 4 -1 0
0 5 0 1
0 9 0 1
0 13 0 1
0
end_operator
begin_operator
deleteattheend task20 task10
1
13 0
4
0 4 0 1
0 5 -1 0
0 10 0 1
0 12 0 1
0
end_operator
begin_operator
deleteattheend task20 task20
1
5 0
2
0 11 0 1
0 13 0 1
0
end_operator
begin_operator
deletebetween task10 task10 task10
1
8 0
1
0 12 0 1
0
end_operator
begin_operator
deletebetween task10 task10 task20
2
9 0
13 0
2
0 8 0 1
0 12 0 1
0
end_operator
begin_operator
deletebetween task10 task20 task10
1
12 0
4
0 8 -1 0
0 9 0 1
0 10 0 1
0 13 0 1
0
end_operator
begin_operator
deletebetween task10 task20 task20
2
9 0
12 0
2
0 11 0 1
0 13 0 1
0
end_operator
begin_operator
deletebetween task20 task10 task10
2
10 0
13 0
2
0 8 0 1
0 12 0 1
0
end_operator
begin_operator
deletebetween task20 task10 task20
1
13 0
4
0 9 0 1
0 10 0 1
0 11 -1 0
0 12 0 1
0
end_operator
begin_operator
deletebetween task20 task20 task10
2
10 0
12 0
2
0 11 0 1
0 13 0 1
0
end_operator
begin_operator
deletebetween task20 task20 task20
1
11 0
1
0 13 0 1
0
end_operator
begin_operator
deletesingletask task10
0
3
0 2 0 1
0 4 0 1
0 12 0 1
0
end_operator
begin_operator
deletesingletask task20
0
3
0 3 0 1
0 5 0 1
0 13 0 1
0
end_operator
4
begin_rule
1
7 1
0 1 0
end_rule
begin_rule
1
12 0
1 1 0
end_rule
begin_rule
2
12 1
13 1
6 0 1
end_rule
begin_rule
1
13 1
7 0 1
end_rule
