SELECT df.tablespace_name "Tablespace Name",
df.file_name "File Name",
Round(df.bytes/1024/1024,2) "Size (M)",
Round(e.used_bytes/1024/1024,2) "Used (M)",
Round(e.used_bytes*100/df.bytes,2) "% Used"
FROM DBA_DATA_FILES DF,
(SELECT file_id,
Sum(Decode(bytes,NULL,0,bytes)) used_bytes
FROM dba_extents
GROUP by file_id) E,
(SELECT Max(bytes) free_bytes,
file_id
FROM dba_free_space
GROUP BY file_id) f
WHERE e.file_id (+) = df.file_id
AND df.file_id = f.file_id (+)
ORDER BY df.tablespace_name,
df.file_name;



(SELECT  Substr(df.tablespace_name,1,20) "Tablespace Name",
        Substr(df.file_name,1,80) "File Name",
        Round(df.bytes/1024/1024,2) "Size (M)",
        decode(e.used_bytes,NULL,0,Round(e.used_bytes/1024/1024,2)) "Used (M)",
        decode(e.used_bytes,NULL,0,Round((e.used_bytes/df.bytes)*100,2)) "% Used"
FROM    DBA_DATA_FILES DF,
       (SELECT file_id,
               sum(bytes) used_bytes
        FROM dba_extents
        GROUP by file_id) E,
       (SELECT Max(bytes) free_bytes,
               file_id
        FROM dba_free_space
        GROUP BY file_id) f
WHERE    e.file_id (+) = df.file_id
AND      df.file_id  = f.file_id (+)
)
UNION ALL
SELECT 
    TABLESPACE_NAME "TableSpace Name",
    FILE_NAME "File Name",
    round(BYTES/1024/1024,2) "SIZE (M)",
    round((BYTES/1024/1024)-(USER_BYTES/1024/1024),2) "USED (M)",
    round((((BYTES/1024/1024)-(USER_BYTES/1024/1024))/(BYTES/1024/1024))*100,2) "% USED"
    from dba_temp_files;
    
    
    
select * from DBA_SYS_PRIVS order by GRANTEE;