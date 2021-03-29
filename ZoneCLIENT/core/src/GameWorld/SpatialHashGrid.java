package GameWorld;

import util.Rectang;

import java.util.ArrayList;
import java.util.List;

public class SpatialHashGrid {
    List<Rectang>[] dynamicCells;
    List<Rectang>[] staticCells;
    int cellsPerRow;
    int cellsPerCol;
    float cellSize;
    int[] cellIds = new int[4];
    List<Rectang> foundObjects;

    public SpatialHashGrid(float worldWidth, float worldHeight, float cellSize) {
        this.cellSize = cellSize;
        this.cellsPerRow = (int) (worldWidth / cellSize);
        this.cellsPerCol = (int) (worldHeight / cellSize);
        int numCells = cellsPerRow * cellsPerCol;
        dynamicCells = new List[numCells];
        staticCells = new List[numCells];
        for (int i = 0; i < numCells; i++) {
            dynamicCells[i] = new ArrayList<Rectang>(10);
            staticCells[i] = new ArrayList<Rectang>(10);
        }
        foundObjects = new ArrayList<Rectang>(10);
    }

    public void insertStaticObject(Rectang r) {
        int[] cellIds = getCellIds(r);
        int i = 0;
        int cellId = -1;
        while (i <= 3 && (cellId = cellIds[i++]) != -1) {
            staticCells[cellId].add(r);
        }
    }

    public void insertDynamicObject(Rectang r) {
        int[] cellIds = getCellIds(r);
        int i = 0;
        int cellId = -1;
        while (i <= 3 && (cellId = cellIds[i++]) != -1) {
            dynamicCells[cellId].add(r);
        }
    }
    public void removeObject(Rectang r) {
        int[] cellIds = getCellIds(r);
        int i = 0;
        int cellId = -1;
        while (i <= 3 && (cellId = cellIds[i++]) != -1) {
            dynamicCells[cellId].remove(r);
            staticCells[cellId].remove(r);
        }
    }

    public void clearDynamicCells() {
        int len = dynamicCells.length;
        for (int i = 0; i < len; i++) {
            dynamicCells[i].clear();
        }
    }

    public List<Rectang> getPotentialColliders(Rectang r) {
        foundObjects.clear();
        int[] cellIds = getCellIds(r);
        int i = 0;
        int cellId = -1;
        while (i <= 3 && (cellId = cellIds[i++]) != -1) {
            int len = dynamicCells[cellId].size();
            for (int j = 0; j < len; j++) {
                Rectang collider = dynamicCells[cellId].get(j);
                if (!foundObjects.contains(collider)) foundObjects.add(collider);
            }
            len = staticCells[cellId].size();
            for (int j = 0; j < len; j++) {
                Rectang collider = staticCells[cellId].get(j);
                if (!foundObjects.contains(collider)) foundObjects.add(collider);
            }
        }
        return foundObjects;
    }

    public int[] getCellIds(Rectang obj) {
        int x1 = (int) (obj.x / cellSize);
        int y1 = (int) (obj.y / cellSize);
        int x2 = (int) ((obj.x +
                obj.width) / cellSize);
        int y2 = (int) ((obj.y +
                obj.height) / cellSize);
        if (x1 == x2 && y1 == y2) {
            if (x1 >= 0 && x1 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol)
                cellIds[0] = x1 + y1 * cellsPerRow;
            else
                cellIds[0] = -1;
            cellIds[1] = -1;
            cellIds[2] = -1;
            cellIds[3] = -1;
        } else if (x1 == x2) {
            int i = 0;
            if (x1 >= 0 && x1 < cellsPerRow) {
                if (y1 >= 0 && y1 < cellsPerCol)
                    cellIds[i++] = x1 + y1 * cellsPerRow;
                if (y2 >= 0 && y2 < cellsPerCol)
                    cellIds[i++] = x1 + y2 * cellsPerRow;
            }
            while (i <= 3) cellIds[i++] = -1;
        } else if (y1 == y2) {
            int i = 0;
            if (y1 >= 0 && y1 < cellsPerCol) {
                if (x1 >= 0 && x1 < cellsPerRow)
                    cellIds[i++] = x1 + y1 * cellsPerRow;
                if (x2 >= 0 && x2 < cellsPerRow)
                    cellIds[i++] = x2 + y1 * cellsPerRow;
            }
            while (i <= 3) cellIds[i++] = -1;
        } else {
            int i = 0;
            int y1CellsPerRow = y1 * cellsPerRow;
            int y2CellsPerRow = y2 * cellsPerRow;
            if (x1 >= 0 && x1 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol)
                cellIds[i++] = x1 + y1CellsPerRow;
            if (x2 >= 0 && x2 < cellsPerRow && y1 >= 0 && y1 < cellsPerCol)
                cellIds[i++] = x2 + y1CellsPerRow;
            if (x2 >= 0 && x2 < cellsPerRow && y2 >= 0 && y2 < cellsPerCol)
                cellIds[i++] = x2 + y2CellsPerRow;
            if (x1 >= 0 && x1 < cellsPerRow && y2 >= 0 && y2 < cellsPerCol)
                cellIds[i++] = x1 + y2CellsPerRow;
            while (i <= 3) cellIds[i++] = -1;
        }
        return cellIds;
    }
}

