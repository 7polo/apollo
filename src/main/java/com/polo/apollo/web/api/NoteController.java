package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.service.note.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author baoqianyong
 * @date 2019/05/25
 */
@RestController
@RequestMapping("note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("{uid}")
    public Result note(@PathVariable String uid){
        return Result.success(noteService.queryById(uid));
    }

    @PostMapping("save")
    public Result save(NoteDto note) {
        noteService.save(note);
        return Result.success(note);
    }

    @GetMapping("delete/{uid}")
    public Result delete(@PathVariable String uid) {
        noteService.deleteByUid(uid);
        return Result.success();
    }

    @GetMapping("queryPage")
    public Result queryPage(NoteDto noteDto, int start, int limit) {
        return Result.success(noteService.queryPage(noteDto, start, limit));
    }

    /**
     * 查找note的所有父节点
     * @param uid
     * @return
     */
    @GetMapping("queryNoteTree/{uid}")
    public Result queryNoteTree(@PathVariable String uid) {
        return Result.success(noteService.queryNoteTree(uid));
    }
}
