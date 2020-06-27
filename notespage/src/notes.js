import React, { useState, useEffect } from "react"
import noteService from "./services/noteservice"

const Notes = () => {
  const [notes, setNotes] = useState([])
  const [numToShow, setNumToShow] = useState("10")
  const [newNote, setNewNote] = useState("")
  const [currentCategory, setCategory] = useState("default")
  const [categories, setCategories] = useState([""])

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

  const handleNoteChange = (event) => {
    event.preventDefault()
    setNewNote(event.target.value)
  }

  const handleCategoryChange = (event) => {
    setCategory(event.target.value)
  }

  const handleNumShownChange = (event) => {
    setNumToShow(event.target.value)
  }

  const NumToDisplaySelect = () => {
    return (
      <div className="note-heading">
        <small>Select number of notes to grab </small>
        <select value={numToShow} onChange={handleNumShownChange}>
          <option value="5">5</option>
          <option value="10">10</option>
          <option value="20">20</option>
          <option value="50">50</option>
          <option value="all">all</option>
        </select>
      </div>
    )
  }

  const NoteCard = (note) => {
    console.log(note)
    const category = note.note.category
    const date = note.note.creationDateTime

    return (
      <div className="note-card">
        <p>
          <small>
            <b>Category:</b> {category} | <b>Created:</b> {date.monthValue}/
            {date.dayOfMonth}/{date.year} @{date.hour}:{date.minute}:
            {date.second}
          </small>
        </p>
        <p>{note.note.entry}</p>
      </div>
    )
  }

  const addNote = (event) => {
    const noteObject = {
      entry: newNote,
      category: currentCategory,
      creationDateTime: new Date().toISOString().replace("T", " ").slice(0, 19)
    }

    console.log(noteObject)

    noteService.insertNote(noteObject).then((resp) => {
      console.log(resp)
      setNewNote("")
    })
  }

  const CategorySelect = () => {
    return (
      <small>
        Select a category
        <select value={currentCategory} onChange={handleCategoryChange}>
          {categories.map((cat) => (
            <option value={cat} key={cat}>
              {cat}
            </option>
          ))}
        </select>
      </small>
    )
  }

  document.title = "Project 0 Notes"

  return (
    <>
      <h1>Notes</h1>
      <NumToDisplaySelect />
      <div className="note-list">
        {notes.map((note) => (
          <NoteCard key={note.id} note={note} />
        ))}
      </div>
      <div className="entryarea">
        <form onSubmit={addNote}>
          <small>Enter note:</small>
          <br />
          <textarea value={newNote} onChange={handleNoteChange} />
          <CategorySelect />
          <br />
          <button type="submit">Save</button>
        </form>
      </div>
    </>
  )
}

export default Notes
