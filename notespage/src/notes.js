import React, { useState, useEffect } from "react"
import noteService from "./services/noteservice"

const Note = (note) => {
  console.log(note)
  let category = note.note.category
  category = `${category[0].toUpperCase()}${category.slice(1)}`
  const date = note.note.creationDateTime

  return (
    <div className="card">
      <p>
        <small>
          <b>Category:</b> {category} | <b>Created:</b> {date.monthValue}/
          {date.dayOfMonth}/{date.year} @{date.hour}:{date.minute}:{date.second}
        </small>
      </p>
      <p>{note.note.entry}</p>
    </div>
  )
}

const Notes = () => {
  const [notes, setNotes] = useState([])
  const [categories, setCategories] = useState([""])
  const [newNote, setNewNote] = useState("")
  const [currentCategory, setCategory] = useState("Default")
  const [numToShow, setNumToShow] = useState("10")

  useEffect(() => {
    if (numToShow === "all") {
      noteService.getAllNotes().then((allNotes) => {
        setNotes(allNotes)
      })
    } else {
      noteService.getLatest(numToShow).then((currentNotes) => {
        setNotes(currentNotes)
      })
    }
  }, [numToShow])

  useEffect(() => {
    noteService.getCategories().then((initialCategories) => {
      setCategories(initialCategories)
    })
  }, [])

  const addNote = (event) => {
    event.preventDefault()
    const noteObject = {
      entry: newNote,
      category: currentCategory,
      creationDateTime: new Date().toISOString().replace("T", " ").slice(0, 19),
    }

    console.log(noteObject)

    noteService.insertNote(noteObject).then((resp) => {
      console.log(resp)
      setNewNote("")
    })
  }

  const handleNoteChange = (event) => {
    setNewNote(event.target.value)
  }

  const handleCategoryChange = (event) => {
    setCategory(event.target.value)
  }

  const handleNumShownChange = (event) => {
    setNumToShow(event.target.value)
  }

  document.title = "Notes"

  return (
    <>
      <h1>Notes</h1>

      <div className="note-display">
        <div className="card">
          <small>Select number of notes to grab </small>
          <select value={numToShow} onChange={handleNumShownChange}>
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
            <option value="all">all</option>
          </select>
        </div>
        <div className="card-list">
          {notes.map((note) => (
            <Note key={note.key} note={note} />
          ))}
        </div>
      </div>

      <div className="note-entry">
        <form onSubmit={addNote}>
          <small>Enter note:</small>
          <br />
          <textarea input value={newNote} onChange={handleNoteChange} />

          <small>
            Select a category
            <select value={currentCategory} onChange={handleCategoryChange}>
              {categories
                .filter((cat) => !(cat === currentCategory))
                .map((cat) => {
                  const category = `${String(cat[0]).toUpperCase()}${cat.slice(
                    1
                  )}`
                  return (
                    <option value={category} key={category}>
                      {category}
                    </option>
                  )
                })}
            </select>
          </small>
          <br />
          <button type="submit">Save</button>
        </form>
      </div>
    </>
  )
}

export default Notes
