class Node:
    def __init__(self, symbol, left=None, right=None):
        self.symbol = symbol   # kan være tall, variabel eller operator
        self.left = left       # venstre barn
        self.right = right     # høyre barn

def treeToString(v):
    if v is None:
        return ""

    # hvis v er en løvnode (ingen barn), returner vi symbolet
    if v.left is None and v.right is None:
        return str(v.symbol)
    print("Node:", v.symbol)
    # ellers: bygg streng for venstre og høyre subtre
    left = treeToString(v.left)
    print(left)
    right = treeToString(v.right)
    print(right)

    # sett sammen med parenteser
    return "(" + left + str(v.symbol) + right + ")"

# bygger treet
tree = Node('*',
            Node('+', Node(3), Node(5)),
            Node(2))

# kaller funksjonen
treeToString(tree)


