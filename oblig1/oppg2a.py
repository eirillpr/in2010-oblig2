
def balanserOutput(stdin): # stdin = array med heltall i sortert stigende rekkefølge
    n = len(stdin)
    if n == 0:
        return
        
    stack = []
    stack.append((0, n-1)) # push

    while stack:
        L,R = stack.pop()
        if L > R:
            continue # hopper over tomt intervall
        
        mid = (L+R+1)//2 # velger øvre median
        print(stdin[mid])

        stack.append((L, mid-1))
        stack.append((mid+1, R))

arr = [0,1,2,3,4,5,6,7,8,9,10]
balanserOutput(arr)






    