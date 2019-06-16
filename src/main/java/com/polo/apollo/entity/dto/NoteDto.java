package com.polo.apollo.entity.dto;

import com.polo.apollo.entity.modal.note.Note;
import lombok.Data;

/**
 * @author baoqianyong
 * @date 2019/06/02
 */
@Data
public class NoteDto extends Note {

    private String tags;
}
