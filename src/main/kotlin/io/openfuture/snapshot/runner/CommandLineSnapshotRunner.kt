package io.openfuture.snapshot.runner

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.defaultLazy
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.int
import io.openfuture.snapshot.dto.ExportSnapshotRequest
import io.openfuture.snapshot.exporter.SnapshotExporter

class CommandLineSnapshotRunner(private val snapshotExporter: SnapshotExporter) : CliktCommand(name = "snapshot") {

    private val contract: String? by option(help = "Address of token Smart contract", names = arrayOf("-c", "--contract")).required()
    private val decimals: Int by option(help = "Snapshot decimals value", names = arrayOf("-d", "--decimals")).int().default(8)
    private val from: Int? by option(help = "Start block number", names = arrayOf("-f", "--from")).int().default(0)
    private val to: Int? by option(help = "End block number", names = arrayOf("-t", "--to")).int().required()
    private val nodeAddress: String? by option(help = "Server url of node to connect", names = arrayOf("-n", "--node-address")).required()
    private val filename: String by option(help = "Name of csv file to save", names = arrayOf("-o", "--output")).defaultLazy { "snapshot_at_block_$to.csv" }

    override fun run() {
        val start = System.currentTimeMillis()

        println("Starting Snapshot with address $contract from block number $from to $to")

        snapshotExporter.export(buildExportSnapshotRequest())

        println("Process time: ${System.currentTimeMillis() - start} millis")
    }

    private fun buildExportSnapshotRequest(): ExportSnapshotRequest {
        return ExportSnapshotRequest(
                contract!!,
                from!!,
                to!!,
                filename,
                decimals,
                nodeAddress!!
        )
    }

}
