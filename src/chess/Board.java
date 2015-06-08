
public class Board {

    protected Piece[][] board = new Piece[8][8];
    public boolean iswhite = false;

    public Board() {
        setUpBoard();
    }

    /*
     * Moves a piece from one location to another
     */
    public void movePiece(int rowFrom, int colFrom, int rowTo, int colTo) {
        Piece temp = board[rowFrom][colFrom];
        // In capture sets the captured piece to empty
        // more efficient to always set than check and set
        makeEmpty(rowTo, colTo);
        // EN PASSANT CHECKS/CLEARS
        if ((((((rowTo - rowFrom == 1 || rowTo - rowFrom == -1) && (colTo - colFrom == -1)) && ((board[rowTo][colTo + 1].type == Piece.PieceType.PAWN)) &&
                (board[rowTo][colTo].type == Piece.PieceType.EMPTY))) &&
                (board[rowTo][colTo + 1].doubleMoved == true)) &&
                (board[rowTo][colTo + 1].moves == 1)) {
            makeEmpty(rowTo, colTo + 1);
        }
        if ((((((rowTo - rowFrom == 1 || rowTo - rowFrom == -1) && (colTo - colFrom == 1)) && ((board[rowTo][colTo - 1].type == Piece.PieceType.PAWN))) &&
                (board[rowTo][colTo].type == Piece.PieceType.EMPTY)) &&
                (board[rowTo][colTo - 1].doubleMoved == true)) &&
                (board[rowTo][colTo - 1].moves == 1)) {
            makeEmpty(rowTo, colTo - 1);
        }
        if (rowFrom == 3 && colFrom == 0 && board[rowFrom][colFrom].moves == 0) {
            if (rowTo == 1 && colTo == 0 && board[0][0].type == Piece.PieceType.ROOK && board[0][0].moves == 0) {
                movePiece(0, 0, 2, 0);
            } else if (rowTo == 6 && colTo == 0 && board[7][0].type == Piece.PieceType.ROOK && board[7][0].moves == 0) {
                movePiece(7, 0, 5, 0);
            }
        }
        if (rowFrom == 3 && colFrom == 7 && board[rowFrom][colFrom].moves == 0) {
            if (rowTo == 1 && colTo == 7 && board[0][7].type == Piece.PieceType.ROOK && board[0][7].moves == 0) {
                movePiece(0, 7, 2, 7);
            } else if (rowTo == 6 && colTo == 7 && board[7][7].type == Piece.PieceType.ROOK && board[7][7].moves == 0) {
                movePiece(7, 7, 5, 7);
            }
        }
        board[rowFrom][colFrom] = board[rowTo][colTo];
        board[rowTo][colTo] = temp;
        board[rowTo][colTo].moves += 1;
    }

    /*
     * Checks if a legal move
     */
    public boolean isLegal(int rowFrom, int colFrom, int rowTo, int colTo) {
        Piece temp = board[rowFrom][colFrom];
        System.out.println(rowFrom + "," + colFrom + " to " + rowTo + "," + colTo);
        // out of bounds check
        if (rowTo == rowFrom && colTo == colFrom) {
            return false;
        }
        if (rowTo > 7 || rowTo < 0 || colTo > 7 || colTo < 0) {
            return false;
        }
        // Ensures your not capturing your own pieces
        switch (temp.color) {
            case WHITE:
                if (board[rowTo][colTo].color == Piece.ColorType.WHITE) {
                    return false;
                }
                break;
            case BLACK:
                if (board[rowTo][colTo].color == Piece.ColorType.BLACK) {
                    return false;
                }
        }
        switch (temp.type) {
            case PAWN:
                if (temp.color == Piece.ColorType.WHITE) {
                    // 1 Forward
                    if (((colTo - colFrom == -1) && (rowTo == rowFrom)) && board[rowTo][colTo].type == Piece.PieceType.EMPTY) {
                        return true;
                    } // 2 Forward
                    else if ((((colTo - colFrom == -2) && (rowTo == rowFrom)) && temp.doubleMoved != true) && board[rowTo][colTo].type == Piece.PieceType.EMPTY) {
                        board[rowTo][colTo].doubleMoved = true;
                        return true;
                    } // Capture
                    else if (((rowTo - rowFrom == 1 || rowTo - rowFrom == -1) && (colTo - colFrom == -1)) && board[rowTo][colTo].type != Piece.PieceType.EMPTY) {
                        return true;
                    } // En Passant
                    else if ((((((rowTo - rowFrom == 1 || rowTo - rowFrom == -1) && (colTo - colFrom == -1)) && ((board[rowTo][colTo + 1].type == Piece.PieceType.PAWN)) &&
                            (board[rowTo][colTo].type == Piece.PieceType.EMPTY))) &&
                            (board[rowTo][colTo + 1].doubleMoved == true)) &&
                            (board[rowTo][colTo + 1].moves == 1)) {
                        makeEmpty(rowTo, colTo + 1);
                        return true;
                    }
                    return false;
                } else if (temp.color == Piece.ColorType.BLACK) {
                    // 1 Forward
                    if (((colTo - colFrom == 1) && (rowTo == rowFrom)) && board[rowTo][colTo].type == Piece.PieceType.EMPTY) {
                        return true;
                    } // 2 Forward
                    else if (((colTo - colFrom == 2) && (rowTo == rowFrom)) && (temp.doubleMoved != true) && (board[rowTo][colTo].type == Piece.PieceType.EMPTY)) {
                        board[rowTo][colTo].doubleMoved = true;
                        return true;
                    } // Capture
                    else if ((((rowTo - rowFrom == 1) || (rowTo - rowFrom == -1)) && (colTo - colFrom == 1)) && board[rowTo][colTo].type != Piece.PieceType.EMPTY) {
                        return true;
                    } // En Passant
                    else if ((((((rowTo - rowFrom == 1 || rowTo - rowFrom == -1) && (colTo - colFrom == 1)) && ((board[rowTo][colTo - 1].type == Piece.PieceType.PAWN))) &&
                            (board[rowTo][colTo].type == Piece.PieceType.EMPTY)) &&
                            (board[rowTo][colTo - 1].doubleMoved == true)) &&
                            (board[rowTo][colTo - 1].moves == 1)) {
                        makeEmpty(rowTo, colTo - 1);
                        return true;
                    }
                    return false;
                }
                return false;
            case ROOK:
                // only horizontal or vertical axis changes, not both
                if (rowTo == rowFrom && colFrom != colTo) {
                    if (colFrom > colTo) {
                        for (int i = colFrom - 1; i > colTo; i--) {
                            if (board[rowTo][i].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else {
                        for (int i = colFrom + 1; i < colTo; i++) {
                            if (board[rowTo][i].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else if ((colFrom == colTo) && (rowFrom != rowTo)) {
                    if (rowFrom > rowTo) {
                        for (int i = rowFrom - 1; i > rowTo; i--) {
                            if (board[i][colTo].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else {
                        for (int i = rowFrom + 1; i < rowTo; i++) {
                            if (board[i][colTo].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else {
                    return false;
                }
            case KNIGHT:
                if (Math.abs(rowTo - rowFrom) == 2 && Math.abs(colTo - colFrom) == 1) {
                    return true;
                } else if (Math.abs(colTo - colFrom) == 2 && Math.abs(rowTo - rowFrom) == 1) {
                    return true;
                }
                return false;
            case BISHOP:
                if (rowTo - rowFrom == colTo - colFrom) {
                    if (rowTo > rowFrom) {
                        for (int i = rowFrom + 1, j = colFrom + 1; i < rowTo; i++, j++) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else if (rowFrom > rowTo) {
                        for (int i = rowFrom - 1, j = colFrom - 1; i > rowTo; i--, j--) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else if ((rowTo - rowFrom) + (colTo - colFrom) == 0) {
                    if (rowTo > rowFrom) {
                        for (int i = rowFrom + 1, j = colFrom - 1; i < rowTo; i++, j--) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else if (rowFrom > rowTo) {
                        for (int i = rowFrom - 1, j = colFrom + 1; i > rowTo; i--, j++) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return false;
            case QUEEN:
                // only horizontal or vertical axis changes, not both
                if (rowTo == rowFrom && colFrom != colTo) {
                    if (colFrom > colTo) {
                        for (int i = colFrom - 1; i > colTo; i--) {
                            if (board[rowTo][i].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else {
                        for (int i = colFrom + 1; i < colTo; i++) {
                            if (board[rowTo][i].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else if ((colFrom == colTo) && (rowFrom != rowTo)) {
                    if (rowFrom > rowTo) {
                        for (int i = rowFrom - 1; i > rowTo; i--) {
                            if (board[i][colTo].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else {
                        for (int i = rowFrom + 1; i < rowTo; i++) {
                            if (board[i][colTo].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else if (rowTo - rowFrom == colTo - colFrom) {
                    if (rowTo > rowFrom) {
                        for (int i = rowFrom + 1, j = colFrom + 1; i < rowTo; i++, j++) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else if (rowFrom > rowTo) {
                        for (int i = rowFrom - 1, j = colFrom - 1; i > rowTo; i--, j--) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else if ((rowTo - rowFrom) + (colTo - colFrom) == 0) {
                    if (rowTo > rowFrom) {
                        for (int i = rowFrom + 1, j = colFrom - 1; i < rowTo; i++, j--) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    } else if (rowFrom > rowTo) {
                        for (int i = rowFrom - 1, j = colFrom + 1; i > rowTo; i--, j++) {
                            if (board[i][j].type != Piece.PieceType.EMPTY) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return false;
            case KING:
                if (rowTo - rowFrom <= 1 && colTo - colFrom <= 1) {
                    return true;
                } // Castling
                else if (rowFrom == 3 && colFrom == 0 && board[rowFrom][colFrom].moves == 0) {
                    if (rowTo == 1 && colTo == 0 && board[0][0].type == Piece.PieceType.ROOK && board[0][0].moves == 0 && board[1][0].type == Piece.PieceType.EMPTY &&
                            board[2][0].type == Piece.PieceType.EMPTY) {
                        return true;
                    } else if (rowTo == 6 && colTo == 0 && board[7][0].type == Piece.PieceType.ROOK && board[7][0].moves == 0 && board[5][0].type == Piece.PieceType.EMPTY &&
                            board[6][0].type == Piece.PieceType.EMPTY) {
                        return true;
                    }
                } else if (rowFrom == 3 && colFrom == 7 && board[rowFrom][colFrom].moves == 0) {
                    if (rowTo == 1 && colTo == 7 && board[0][7].type == Piece.PieceType.ROOK && board[0][7].moves == 0 && board[1][7].type == Piece.PieceType.EMPTY &&
                            board[2][7].type == Piece.PieceType.EMPTY) {
                        return true;
                    } else if (rowTo == 6 && colTo == 7 && board[7][7].type == Piece.PieceType.ROOK && board[7][7].moves == 0 && board[5][7].type == Piece.PieceType.EMPTY &&
                            board[6][7].type == Piece.PieceType.EMPTY) {
                        return true;
                    }
                }
                return false;
            case EMPTY:
                return false;
            default:
                System.out.println("Error Determining Piece Type at " + rowFrom + "," + colFrom + ".");
                return false;
        }
    }

    /*
     * Removes the piece from the board
     */
    public void makeEmpty(int row, int col) {
        board[row][col].type = Piece.PieceType.EMPTY;
        board[row][col].id = "__Empty";
        board[row][col].color = Piece.ColorType.EMPTY;
    }

    /*
     * Sets up board with appropriate pieces
     */
    private void setUpBoard() {
        board[0][0] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.BLACK, "0_BRook");
        board[1][0] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.BLACK, "0_BKnight");
        board[2][0] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.BLACK, "0_BBishop");
        board[3][0] = new Piece(Piece.PieceType.KING, Piece.ColorType.BLACK, "0_BKing");
        board[4][0] = new Piece(Piece.PieceType.QUEEN, Piece.ColorType.BLACK, "0_BQueen");
        board[5][0] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.BLACK, "1_BBishop");
        board[6][0] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.BLACK, "1_BKnight");
        board[7][0] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.BLACK, "1_BRook");
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Piece(Piece.PieceType.PAWN, Piece.ColorType.BLACK, i + "_BPawn");
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[j][i] = new Piece(Piece.PieceType.EMPTY, Piece.ColorType.EMPTY, "__Empty");
            }
        }
        for (int i = 0; i < 8; i++) {
            board[i][6] = new Piece(Piece.PieceType.PAWN, Piece.ColorType.WHITE, i + "_WPawn");
        }
        board[0][7] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.WHITE, "0_WRook");
        board[1][7] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.WHITE, "0_WKnight");
        board[2][7] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.WHITE, "0_WBishop");
        board[3][7] = new Piece(Piece.PieceType.KING, Piece.ColorType.WHITE, "0_WKing");
        board[4][7] = new Piece(Piece.PieceType.QUEEN, Piece.ColorType.WHITE, "0_WQueen");
        board[5][7] = new Piece(Piece.PieceType.BISHOP, Piece.ColorType.WHITE, "1_WBishop");
        board[6][7] = new Piece(Piece.PieceType.KNIGHT, Piece.ColorType.WHITE, "1_WKnight");
        board[7][7] = new Piece(Piece.PieceType.ROOK, Piece.ColorType.WHITE, "1_WRook");
    }
}
