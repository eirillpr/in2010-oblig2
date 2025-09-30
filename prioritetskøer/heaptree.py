# implementer binær heap som et tre
# innsetting og sletting skal ha kjøretidskompleksitet O(log(n))

class BinaryHeap:
    def __init__(self):
        self.n = 0
        self.root = None

    class Node:
        def __init__(self, e, p=None):
            self.element = e            # referanse/verdi
            self.parent = p             # forelder (Node eller None)
            self.left = None            # venstre barn (Node eller None)
            self.right = None           # høyre barn (Node eller None)

        def bubbleUp(self):
            """Boble opp: bytt med forelder så lenge heap-ordningen brytes."""
            if self.parent is None:
                return
            if self.element < self.parent.element:
                # bytt innhold (ikke pekere)
                self.element, self.parent.element = self.parent.element, self.element
                self.parent.bubbleUp()

        def bubbleDown(self):
            """Boble ned: bytt med minste barn så lenge heap-ordningen brytes."""
            left = self.left
            right = self.right

            # start med høyre barn, evt. bytt til venstre etter regelen i pseudokoden
            child = right
            # NB: vokt deg for None når du sammenligner
            if child is None or (left is not None and child.element >= left.element):
                child = left

            if child is not None and child.element < self.element:
                # bytt innhold (ikke pekere)
                self.element, child.element = child.element, self.element
                child.bubbleDown()

