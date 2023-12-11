
def main():
    print("TESTTTTTT")
    print("Resultat : "+calculate_shortest_path_from_file('d11/d11.txt'))



def calculate_shortest_path_from_file(file_path):
    # Ouvrir le fichier texte et lire la matrice
    with open(file_path, 'r') as file:
        lines = file.readlines()
        matrix = [[num if num == '#' else '.' for num in line.strip()] for line in lines]

    n = len(matrix)
    dist = [[float('inf')] * n for _ in range(n)]

    # Initialiser la matrice de distance avec les valeurs de la matrice d'entrée
    for i in range(n):
        for j in range(n):
            if matrix[i][j] != ".":
                dist[i][j] = matrix[i][j]

    # Calculer le plus court chemin entre tous les points
    for k in range(n):
        for i in range(n):
            for j in range(n):
                dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])

    # Additionner les distances
    total_distance = sum(sum(row) for row in dist)

    return total_distance

