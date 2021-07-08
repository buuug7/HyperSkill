import React, {
  forwardRef,
  useEffect,
  useImperativeHandle,
  useMemo,
  useRef,
  useState,
} from "react";
import { getAroundCells, getAroundMines, openIfAroundNotMines } from "./util";
import bomb from "./bomb.svg";
import angry from "./angry.svg";
import fired from "./fired.svg";
import target from "./target.svg";
import "./App.css";

function generateCells(width, height) {
  const data = [];
  let index = 0;
  for (let i = 0; i < height; i++) {
    data.push([]);
    for (let j = 0; j < width; j++) {
      data[i][j] = {
        key: index++,
        x: j,
        y: i,
        mine: false,
        flag: false,
        open: false,
      };
    }
  }

  return data;
}

function generateRandomMines(width, height, data) {
  let planted = 0;
  while (planted < 10) {
    console.log("planted", planted);
    const x = Math.floor(Math.random() * width + 1);
    const y = Math.floor(Math.random() * height + 1);

    if (x >= 0 && y >= 0 && x < width && y < height && !data[y][x].mine) {
      data[y][x].mine = true;
      planted += 1;
    }
  }

  return data;
}

function App() {
  const width = 8;
  const height = 9;

  let initCells = generateCells(8, 9);
  const fillMinesCells = generateRandomMines(width, height, initCells);
  const [cells, setCells] = useState(fillMinesCells);
  const [start, setStart] = useState(false);
  const [stop, setStop] = useState(false);
  const [flags, setFlags] = useState(10);

  const controlPanelRef = useRef(null);

  const showAllMines = () => {
    return cells.map((item) => {
      return item.map((it) => {
        return it.mine
          ? {
              ...it,
              open: true,
              flag: false,
            }
          : {
              ...it,
              flag: false,
            };
      });
    });
  };

  return (
    <div className="App">
      <div className="box">
        <Header />
        <ControlPanelWrapper
          ref={controlPanelRef}
          flags={flags}
          reset={() => {
            console.log('clicked reset btn');
            controlPanelRef.current.stopTimer();
            controlPanelRef.current.resetTimer();
            setStart(false);
            setStop(false);
            setFlags(10);
            setCells(initCells);
          }}
        />
        <Panel
          cells={cells}
          cellClicked={(currentCell, type) => {
            let _cells = [...cells];
            if (stop) {
              return;
            }

            if (type === "onClick") {
              if (!start) {
                controlPanelRef.current.startTimer();
                setStart(true);
              }
            }

            if (type === "onContextMenu") {
              if (!start) {
                return
              }
              if (flags >= 1) {
                _cells[currentCell.y][currentCell.x] = currentCell;
                setFlags((prevState) => prevState - 1);
                setCells(_cells);
              }

              return;
            }

            _cells = openIfAroundNotMines(_cells, currentCell);

            setCells(_cells);

            if (currentCell.mine) {
              controlPanelRef.current.stopTimer();
              setStart(false);
              setStop(true);
              const mines = showAllMines();
              setCells(mines);
            }
          }}
        />
      </div>
    </div>
  );
}

function Header() {
  return (
    <div className="Header">
      <span className="title">Minesweeper</span>
      <img src={bomb} alt="bomb" style={{ width: "2rem" }} />
    </div>
  );
}

function ControlPanel({ flags, reset }, ref) {
  const [timer, setTimer] = useState(0);
  const [timerId, setTimerId] = useState(null);

  useImperativeHandle(ref, () => ({
    startTimer: () => {
      const id = setInterval(() => {
        setTimer((prevState) => prevState + 1);
      }, 1000);

      setTimerId(id);
    },

    stopTimer: () => {
      clearTimeout(timerId);
      // setTimer(0);
    },

    resetTimer: () => {
      setTimer(0);
    },
  }));

  return (
    <div className="Control">
      <div className="flagCounter">
        <img src={bomb} alt="" />
        <span>{flags}</span>
      </div>
      <a
        className="reset"
        onClick={(e) => {
          // e.preventDefault();
          console.log('clicked reset btn innnn');
          reset();
        }}
        href="#"
      >
        <img src={angry} alt="" />
      </a>
      <span className="Timer">{`${Math.floor(timer / 60)}:${
        timer % 60 < 10 ? "0" + (timer % 60) : timer % 60
      }`}</span>
    </div>
  );
}

const ControlPanelWrapper = forwardRef(ControlPanel);

function Panel({ cells, cellClicked }) {
  const fields = useMemo(
    () => (
      <div className="Panel">
        {cells.map((row, rowIndex) => (
          <div className="Row" key={rowIndex}>
            {row.map((cell, cellIndex) => (
              <Field
                key={cellIndex}
                sourceCells={cells}
                cell={cell}
                cellClicked={cellClicked}
              />
            ))}
          </div>
        ))}
      </div>
    ),
    [cells]
  );

  return <div>{fields}</div>;
}

function Field({ cell, cellClicked, sourceCells }) {
  // cell value:
  // 0 closed
  // 1 opened
  // 2 mine
  console.log("render filed");

  const aroundCells = getAroundCells(sourceCells, cell);
  const mines = getAroundMines(aroundCells);

  const content = () => {
    if (cell.flag) {
      return <img src={target} />;
    }

    if (cell.open) {
      if (cell.mine) {
        return <img src={fired} />;
      }

      if (mines > 0) {
        return mines;
      }
    }

    return null;
  };

  return (
    <div
      x={cell.x}
      y={cell.y}
      value={cell.mine}
      className={`cell ${cell.open ? "open" : ""}`}
      onClick={(e) => {
        if (!cell.open && !cell.flag) {
          cellClicked({ ...cell, open: true }, "onClick");
        }
      }}
      onContextMenu={(e) => {
        e.preventDefault();
        if (!cell.open && !cell.flag) {
          cellClicked({ ...cell, flag: true }, "onContextMenu");
        }
      }}
    >
      {content()}
    </div>
  );
}

export default App;
