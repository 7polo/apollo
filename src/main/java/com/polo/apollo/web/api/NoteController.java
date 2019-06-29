package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.service.note.NoteService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "分享note ")
    @PostMapping("share")
    public Result share(String uid) {
        Note note = noteService.queryById(uid);
        if (note.getPublishDt() != null) {
            return Result.error("该笔已分享过， 无需再次分享");
        }
        noteService.share(note);
        return Result.success();
    }

    @ApiOperation(value = "根据 noteid 查询 note")
    @GetMapping("{uid}")
    public Result getNote(@PathVariable String uid) {
        return Result.success(noteService.queryById(uid));
    }

    @PostMapping("save")
    public Result save(NoteDto note) {
        noteService.save(note);
        return Result.success(note);
    }

    @ApiOperation(value = "根据主键id 删除 note")
    @GetMapping("delete/{uid}")
    public Result delete(@PathVariable String uid) {
        noteService.deleteByUid(uid);
        return Result.success();
    }

    @ApiOperation(value = "分页查询note")
    @GetMapping("queryPage")
    public Result queryPage(NoteDto noteDto, int start, int limit) {
        return Result.success(noteService.queryPage(noteDto, start, limit));
    }

    @ApiOperation(value = "查找note的所有父节点， 构建该note的目录树")
    @GetMapping("queryNoteTree/{uid}")
    public Result queryNoteTree(@PathVariable String uid) {
        return Result.success(noteService.queryNoteTree(uid));
    }

    @ApiOperation(value = "根据主键id 点赞blog")
    @GetMapping("good/{blogId}")
    public Result good(@PathVariable String blogId) {
        noteService.addGood(blogId);
        return Result.success();
    }
}
