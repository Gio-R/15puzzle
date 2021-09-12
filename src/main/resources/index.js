window.onload = () => {
    // setup new button
    document.getElementById("new").onclick = e => {
        e.preventDefault()
        const n = document.getElementById("size").value
        const xhr = new XMLHttpRequest()
        xhr.onreadystatechange = () => {
            if (xhr.responseText !== "" && xhr.readyState == 4) {
                showPuzzle(JSON.parse(xhr.responseText))
            }
        }
        xhr.open('POST', '/puzzle/new')
        xhr.send(n)
    }
    // setup reset button
    document.getElementById("reset").onclick = e => {
        e.preventDefault()
        const xhr = new XMLHttpRequest()
        xhr.onreadystatechange = () => {
            if (xhr.responseText !== "" && xhr.readyState == 4) {
                showPuzzle(JSON.parse(xhr.responseText))
            }
        }
        xhr.open('POST', '/puzzle/current/reset')
        xhr.send()
    }
}

function showPuzzle(puzzle) {
    console.log(puzzle)
    const size = puzzle.length
    const side = Math.sqrt(size)
    const div = document.getElementById("puzzle")
    const table = document.createElement("table")
    const tiles = new Map()
    puzzle.forEach(element => {
        tiles.set((element[1] - 1) * side + element[2], element[0])
    })
    for (let i = 1; i <= side; i++) {
        const row = document.createElement("tr")
        for (let j = 1; j <= side; j++) {
            const cell = document.createElement("td")
            const tileValue = tiles.get((i - 1) * side + j)
            cell.innerHTML = tileValue == 0 ? "" : tileValue
            if (tileValue !== 0) {
                cell.onclick = () => moveTile(tileValue)
            }
            row.append(cell)
        }
        table.append(row)
    }
    div.innerHTML = ""
    div.append(table)
}

function moveTile(tileValue) {
    const xhr = new XMLHttpRequest()
    xhr.onreadystatechange = () => {
        if (xhr.responseText !== "" && xhr.readyState == 4 && xhr.status === 200) {
            showPuzzle(JSON.parse(xhr.responseText))
        }
    }
    xhr.open('POST', '/puzzle/current/moveTile')
    xhr.send(tileValue)
}