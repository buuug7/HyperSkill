export function getAroundCells(cells, currentCell) {
  const around = [
    [0, -1], // top
    [0, 1], // bottom
    [-1, 0], // left
    [1, 0], // right
    [-1, -1], // left top
    [-1, 1], // left bottom
    [1, -1], // right top
    [1, 1], // right bottom
  ];

  const height = cells.length;
  const width = cells[0].length;

  return around.map((item) => {
    const x = currentCell.x + item[0];
    const y = currentCell.y + item[1];

    let rs = null;

    if (x >= 0 && x < width && y >= 0 && y < height) {
      rs = cells[y][x];
    }

    return rs;
  });
}

export function getAroundMines(aroundMines) {
  let mines = 0;

  aroundMines.forEach((item) => {
    if (item && item.mine) {
      mines++;
    }
  });

  return mines;
}

export function openIfAroundNotMines(cells, currentCell) {
  let _cells = [...cells];

  const openNearCell = (cellType) => {
    if (!cellType) {
      return;
    }

    if (!cellType.open) {
      cellType = {
        ...cellType,
        open: true,
      };

      let typeAroundCells = getAroundCells(_cells, cellType);
      let typeMines = getAroundMines(typeAroundCells);

      if (typeMines === 0) {
        _cells = openIfAroundNotMines(_cells, cellType);
      } else {
        _cells[cellType.y][cellType.x] = cellType;
      }
    }
  };


  // if the opened cell doesn't contain a mine, it should show the number of mines around it.
  if (!currentCell.mine) {
    currentCell = { ...currentCell, open: true };
    _cells[currentCell.y][currentCell.x] = currentCell;

    const aroundCells = getAroundCells(_cells, currentCell);
    const mines = getAroundMines(aroundCells);

    console.log("currentCell", currentCell);
    console.log("aroundCells", aroundCells);
    console.log("mines", mines);


    // If there are no mines near the just opened cell, the area of cells around it should be opened.
    if (mines === 0) {
      aroundCells.forEach(item => {
        openNearCell(item)
      })
    }
  } else {
    _cells[currentCell.y][currentCell.x] = currentCell;
  }

  return _cells;
}
