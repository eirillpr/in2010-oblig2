from queue import PriorityQueue

def balance(stdin):
    n = stdin.qsize()
    if n == 0:
        return
    
    mid = n//2

    L = PriorityQueue()

    for _ in range(mid):
        L.put(stdin.get())

    median = stdin.get()
    print(median)

    R = PriorityQueue()
    while not stdin.empty():
        R.put(stdin.get())

    balance(L)
    balance(R)

pq = PriorityQueue()
for x in [0,1,2,3,4,5,6,7,8,9,10]:
    pq.put(x)

balance(pq)