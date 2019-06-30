package com.polo.apollo.web.api;

import com.polo.apollo.common.result.Result;
import com.polo.apollo.entity.dto.NoteDto;
import com.polo.apollo.entity.modal.note.Note;
import com.polo.apollo.entity.vo.NoteVo;
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

    @ApiOperation(value = "根据 noteid 查询 note")
    @GetMapping("{uid}")
    public Result getNote(@PathVariable String uid) {
        return Result.success(noteService.queryById(uid));
    }

    @ApiOperation(value = "分享note ")
    @PostMapping("share/{uid}")
    public Result share(@PathVariable String uid) {
        Note note = noteService.queryById(uid);
        if (note.getPublishDt() != null) {
            return Result.error("该笔已分享过， 无需再次分享");
        }
        noteService.share(note);
        return Result.success();
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
    public Result queryPage(NoteVo vo, int start, int limit) {
        if (vo == null) {
            vo = new NoteVo();
        }
        return Result.success(noteService.queryPage(vo, start, limit));
    }

    @ApiOperation(value = "查找 note 目录信息")
    @GetMapping("queryCatalogDto/{uid}")
    public Result queryCatalogDto(@PathVariable String uid) {
        return Result.success(noteService.queryCatalogDto(uid));
    }
}
